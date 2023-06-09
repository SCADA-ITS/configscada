package com.revenga.rits.front.openits.service;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revenga.rits.front.openits.adapter.impl.VO2UIAdapter;
import com.revenga.rits.front.openits.adapter.impl.VODataSourceAdapter;
import com.revenga.rits.front.openitsdao.model.DataSource;
import com.revenga.rits.front.openitsdao.model.VoEntity;
import com.revenga.rits.front.openitsdao.service.VoEntityService;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class CacheManager extends FrontManager {
	
	private static final String CACHE_ENABLED = props.get("cache.enabled");
	private static final String CACHE_CRON = props.get("cache.cron");
	
	private static final String CACHE_DATA_SOURCES = props.get("cache.datasources");
	private static final String CACHE_RETRY_TIMES = props.get("cache.retry.times");
	private static final String CACHE_RETRY_TIMEOUT_SECS = props.get("cache.retry.timeout_secs");
	
	@Autowired
	VODataSourceAdapter voDataSourceAdapter;
	
	@Autowired 
	VO2UIAdapter vo2uiAdapter;
	
	@Autowired
	VoEntityService voEntityService;
	
	Map<Integer, List<?>> entitiesCached = Collections.synchronizedMap(new HashMap<>());
	
	@Getter
	List<VoEntity> voEntities;
	
	@PostConstruct
	private void afterLoad() {
		
		this.enabled = StringUtils.equals(CACHE_ENABLED, Boolean.TRUE.toString());
		this.cronExpression = CACHE_CRON;
	}
	
		
	@Override
	protected void load() {
		
		cacheVOEntities();
	}
	
	private void cacheVOEntities() {
		
		Instant timeRef = Instant.now();
		
		log.info(this.getClass().getSimpleName() + ": Caching VOEntities...");
		
		this.voEntities = voEntityService.getAll();
		
		if (!CollectionUtils.isEmpty(voEntities)) {
			
			log.info(this.getClass().getSimpleName() + ": Cached VOEntities. Items = " + this.voEntities.size() + " ("
					+ Duration.between(timeRef, Instant.now()).toMillis() + " ms.)");
		}
		else {
			
			log.warn(this.getClass().getSimpleName() + ": Cached VOEntities without items (" + Duration.between(timeRef, Instant.now()).toMillis()
					+ " ms.)");
		}
	}
	
	@Override
	protected void launchScheduled() {
		
		try {
			
			SchedulerFactory sf = new StdSchedulerFactory();
			scheduler = sf.getScheduler();
			
		} catch (SchedulerException e) {
			
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}
	}
	
	@Override
	protected void afterStart() {
		
		(new Thread() {
			
			@Override
			public void run() {
				
				try {
					
					updateEntitiesRepository();
				
				} catch (Exception e) {
					log.error(e.getMessage());
					log.debug(ExceptionUtils.getStackTrace(e));
				}
			}
		}).start();
		
	}
	
	public Object getEntitiesCached(Integer entityId) {
		
		return entitiesCached.get(entityId);
	}
	
	public Object getEntities(Integer entityId) {
		
		List<Map<String, Object>> listEntities = null;
		
		listEntities = voDataSourceAdapter.getList(entityId, null);
		
		listEntities = vo2uiAdapter.convert(entityId, listEntities);
		
		return listEntities;
	}
	
	@SuppressWarnings("unchecked")
	public Object getEntity(Integer entityId, String field, Object value) {
		
		Object entities = null;
		
		List<Map<String, Object>> listEntities = (List<Map<String, Object>>) getEntitiesCached(entityId);
		
		if (CollectionUtils.isEmpty(listEntities)) {
			
			listEntities = voDataSourceAdapter.getList(entityId, null);
			
			listEntities = vo2uiAdapter.convert(entityId, listEntities);
		}
		
		if (!CollectionUtils.isEmpty(listEntities)) {
			
			entities = listEntities.stream().filter(e -> e.get(field).equals(value)).findFirst().orElse(null);
		}
		
		return entities;
	}
	
	@SuppressWarnings("unchecked")
	public void updateEntitiesRepository() {
		
		Instant instant = Instant.now();
		
		if (!StringUtils.isEmpty(CACHE_DATA_SOURCES)) {
			
			String[] emCacheDatasources = CACHE_DATA_SOURCES.split(",");
			
			if (!ArrayUtils.isEmpty(emCacheDatasources)) {
				
				Integer[] datasources = Arrays.stream(emCacheDatasources).map(e -> Integer.valueOf(e)).toArray(Integer[]::new);
				
				Arrays.stream(datasources).forEach(dataSourceId -> {
					
					int retries = Integer.parseInt(CACHE_RETRY_TIMES);
					Object data = null;
					
					do {
						
						try {
							
							Instant timeRef = Instant.now();
							
							DataSource dataSource = voDataSourceAdapter.getDataSourceService().getById(dataSourceId);
							
							if (dataSource != null) {
								
								data = voDataSourceAdapter.getList(dataSourceId, null);
								
								if (data != null) {
									
									if (data instanceof List<?>) {
										
										List<?> list = vo2uiAdapter.convert(dataSourceId, (List<Map<String, Object>>) data);
										
										entitiesCached.put(dataSourceId, list);
										
										log.debug(this.getClass().getSimpleName() + ": Cached datasource " + dataSource.getId() + " [" + dataSource.getName()
												+ "]: " + list.size() + " " + dataSource.getEntityVo() + " ("
												+ Duration.between(timeRef, Instant.now()).toMillis() + " ms." + ")");
									}
									else {
										
										log.error(this.getClass().getSimpleName() + ": Not allow caching of single entities");
									}
								}
								else {
									
									log.warn(this.getClass().getSimpleName() + ": Cached datasource " + dataSource.getId() + " [" + dataSource.getName()
											+ "]: Not response. Try again in " + CACHE_RETRY_TIMEOUT_SECS + " secs...");
									
									Thread.sleep(Long.parseLong(CACHE_RETRY_TIMEOUT_SECS));
								}
							}
							
						} catch (InterruptedException e1) {
							log.error(e1.getMessage());
							log.debug(ExceptionUtils.getStackTrace(e1));
							Thread.currentThread().interrupt();
						}
						
						retries--;
						
					} while (retries > 0 && data == null);
					
				});
				
				log.info(this.getClass().getSimpleName() + ": Total time to sync entities repository = "
						+ Duration.between(instant, Instant.now()).toMillis() + " ms.");
			}
		}
	}
}
