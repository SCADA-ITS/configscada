package com.revenga.rits.front.openits.service;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revenga.rits.front.openits.adapter.impl.VO2UIAdapter;
import com.revenga.rits.front.openits.adapter.impl.VODataSourceAdapter;
import com.revenga.rits.front.openits.scheduler.ScheduledEntitiesResositoryJob;
import com.revenga.rits.front.openits.util.ConfigProperty;
import com.revenga.rits.front.openitsdao.model.VoEntity;
import com.revenga.rits.front.openitsdao.service.VoEntityService;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class CacheManager {
	
	protected static final String FILE_DATASOURCES_CONSTANTS = "datasources.properties";
	
	private static Map<String, String> props = ConfigProperty.getInstance().getProperties(FILE_DATASOURCES_CONSTANTS);
	
	private static final Boolean CACHE_ENABLED = props.get("cache.enabled").equals("true");
	private static final String CACHE_CRON = props.get("cache.cron");
	private static final String CACHE_DATA_SOURCES = props.get("cache.datasources");
	private static final Integer CACHE_RETRY_TIMES = Integer.valueOf(props.get("cache.retry.times"));
	private static final Integer CACHE_RETRY_TIMEOUT_SECS = Integer.valueOf(props.get("cache.retry.timeout_secs"));
	
	@Autowired
	VODataSourceAdapter voDataSourceAdapter;
	
	@Autowired 
	VO2UIAdapter vo2uiAdapter;
	
	@Autowired
	VoEntityService voEntityService;
	
	Map<Integer, List<?>> entitiesCached = Collections.synchronizedMap(new HashMap<>());
	
	@Getter
	List<VoEntity> voEntities;

	Scheduler scheduler;

	public void start() {
		
		if (!CACHE_ENABLED.booleanValue()) {
			
			return;
		}
		
		log.info("Enabled cache for entities");
		
		cacheVOEntities();
		
		initQuartzScheduler();
		
		launchScheduledCache();
		
		// All of the jobs have been added to the scheduler, but none of the jobs
	    // will run until the scheduler has been started
		try {
			if (!scheduler.isStarted()) {
				
				scheduler.start();
			}
		} catch (SchedulerException e1) {
			log.error(e1.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e1));
		}
		
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
	
	private void cacheVOEntities() {
		
		this.voEntities = voEntityService.getAll();
	}
	
	private void initQuartzScheduler() {
		
		try {
			
			SchedulerFactory sf = new StdSchedulerFactory();
			scheduler = sf.getScheduler();
			
		} catch (SchedulerException e) {
			
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}
	}
	
	private void launchScheduledCache() {
		
		try {
			
			// First we must get a reference to a scheduler

			scheduler.getContext().put(this.getClass().getSimpleName(), this);
			createJob(ScheduledEntitiesResositoryJob.class, CACHE_CRON);
			
		} catch (SchedulerException e) {
			
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}
	}
	
	private void createJob(Class<? extends Job> jobClass, String cronExpression) {
		
		try {
			
			// jobs can be scheduled before sched.start() has been called
			// job 1 will run every cron value
			
			JobDetail job = newJob(jobClass).withIdentity("job" + jobClass.getSimpleName(), "group" + jobClass.getSimpleName()).build();
			
			if (!StringUtils.isEmpty(cronExpression)) {
				
				CronTrigger trigger = newTrigger().withIdentity("trigger" + jobClass.getSimpleName(), "group" + jobClass.getSimpleName())
						.withSchedule(cronSchedule(cronExpression)).build();
				
				Date dt = scheduler.scheduleJob(job, trigger);
				
				log.debug(job.getKey() + " has been scheduled to run at: " + dt + " and repeat based on expression: "
						+ trigger.getCronExpression());
			}
			else {
				
				log.debug(job.getKey() + " is not scheduled");
			}
			
		} catch (SchedulerException e) {
			
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}
	}
	
	public Object getEntitiesCached(Integer entityId) {
		
		return entitiesCached.get(entityId);
	}
	
	public Object getEntities(Integer entityId) {
		
		List<Map<String, Object>> listEntities = null;
		
		listEntities = voDataSourceAdapter.getList(entityId, null);
		
		listEntities = vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(entityId).getFieldId(), listEntities);
		
		return listEntities;
	}
	
	@SuppressWarnings("unchecked")
	public Object getEntity(Integer entityId, String field, Object value) {
		
		Object entities = null;
		
		List<Map<String, Object>> listEntities = (List<Map<String, Object>>) getEntitiesCached(entityId);
		
		if (CollectionUtils.isEmpty(listEntities)) {
			
			listEntities = voDataSourceAdapter.getList(entityId, null);
			
			listEntities = vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
					.getById(entityId).getFieldId(), listEntities);
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
				
				Arrays.stream(datasources).forEach(entityId -> {
					
					int retries = (int) CACHE_RETRY_TIMES;
					Object data = null;
					
					do {
						
						try {
							
							Instant timeRef = Instant.now();
							
							data = voDataSourceAdapter.getList(entityId, null);
							
							if (data != null) {
								
								if (data instanceof List<?>) {
									
									List<?> list = null;
									
									list = vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
											.getById(entityId).getFieldId(), (List<Map<String, Object>>) data);
									
									entitiesCached.put(entityId, list);
									
									log.info(list.size() + " entities from datasource " + entityId + " cached in "
											+ Duration.between(timeRef, Instant.now()).toMillis() + "ms.");
								}
								else {
									
									log.error("Not allow caching of single entities");
								}
							}
							else {
								
								log.warn("Entity datasource = " + entityId + " not response. Try again in "
										+ CACHE_RETRY_TIMEOUT_SECS + " secs...");
								
								Thread.sleep(CACHE_RETRY_TIMEOUT_SECS);
							}
							
						} catch (InterruptedException e1) {
							log.error(e1.getMessage());
							log.debug(ExceptionUtils.getStackTrace(e1));
							Thread.currentThread().interrupt();
						}
						
						retries--;
						
					} while (retries > 0 && data == null);
					
				});
				
				log.info(this.getClass().getSimpleName() + " => Total time to sync entities repository = "
						+ Duration.between(instant, Instant.now()).toMillis() + " ms.");
			}
		}
	}
}
