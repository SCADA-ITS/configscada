package com.revenga.rits.back.manager.lib.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revenga.rits.back.adapter.lib.model.ManagerAdapterV1.Subscriptors;
import com.revenga.rits.back.adapter.lib.model.ManagerAdapterV1.Subscriptors.Subscriptor;
import com.revenga.rits.back.cache.core.manager.CacheManager;
import com.revenga.rits.back.cache.core.model.Cmd;
import com.revenga.rits.back.cache.core.model.GenericKey;
import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.cache.core.subscriptor.SubscriptorEventHandler;
import com.revenga.rits.back.cache.core.subscriptor.SubscriptorManager;
import com.revenga.rits.back.data.core.model.BasicElement;
import com.revenga.rits.back.data.core.model.command.Command;
import com.revenga.rits.back.data.core.util.ClassesUtil;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class SubscriptorService {
	
	enum SubscriptorType {
	
		KEY, CMD
	}
	
	@Autowired
	ExecutorService managerService;
	
	List<SubscriptorManager> subscriptors = new ArrayList<>();
		
	public void load(Subscriptors subscriptors) {
		
		try {
			
			if (subscriptors != null) {
				
				List<Subscriptor> subscriptorList = subscriptors.getSubscriptor();
				
				if (!CollectionUtils.isEmpty(subscriptorList)) {
					
					for (Subscriptor subscriptor : subscriptorList) {
						
						connect(subscriptor);
					} 
				}
			}

		} catch (Exception e) {
			
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}
	}
	
	private Class<?> getSubscriptorType(Subscriptor subscriptor) {
		
		Class<?> clazz = null;
		
		try {
			
			clazz = ClassesUtil.getModelClassBySimpleClassName(subscriptor.getEntity());
			
		} catch (ClassNotFoundException e1) {

		}
			
		return clazz;
	}
	
	public void connect(Subscriptor subscriptor) {
		
		boolean connected = false;
		Class<?> clazz = null;
		Object instanceSubscriptor = null;
		
		clazz = getSubscriptorType(subscriptor);
		
		try {
		
			if (clazz == null) {
				
				throw new NullPointerException("Check xml subscriptor " + subscriptor.getId() + " for entity value. Class not found");
			}
			
			instanceSubscriptor = clazz.getDeclaredConstructor().newInstance();
			
			if (instanceSubscriptor instanceof BasicElement) {
				
				connected = subscribe(subscriptor, (BasicElement) instanceSubscriptor);
			}
			else if (instanceSubscriptor instanceof Command) {
				
				connected = subscribe(subscriptor, (Command) instanceSubscriptor);
			}
			
			if (!connected) {
			
				throw new IOException("Check xml subscriptor " + subscriptor.getId() + " for entity value. Class not found");
			}
			
		} catch (Exception e) {
			
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}
	}
	
	private Boolean subscribe(Subscriptor subscriptor, BasicElement basicElement) throws Exception {
		
		List<GenericKey> keys = new ArrayList<>();
		Boolean res = false;
		
		GenericKey genericKey = getGenericKey(subscriptor, basicElement);
		
		keys.add(genericKey);
		
		if (!CollectionUtils.isEmpty(keys)) {
			
			SubscriptorEventHandler subscriptorHnd = new SubscriptorHnd(managerService, subscriptor, 
					keys.toArray(new Key[keys.size()]));
			
			subscriptors.add(CacheManager.getInstance().subscribe(subscriptorHnd));
			
			res = true;
		}
		else {
			
			throw new Exception ("sources is empty. Configure sources in xml");
		}
		
		return res;
	}
	
	private Boolean subscribe(Subscriptor subscriptor, Command command) throws Exception {
		
		List<String> channels = new ArrayList<>();
		Boolean res = false;

		channels.add(command.getClass().getSimpleName());

		SubscriptorEventHandler subscriptorHnd = new SubscriptorHnd(managerService, subscriptor,
				channels.toArray(new String[channels.size()]));

		subscriptors.add(CacheManager.getInstance().subscribe(subscriptorHnd));

		res = true;

		return res;
	}
	
	private GenericKey getGenericKey(Subscriptor subscriptor, Object object) {
		
		GenericKey genericKey = null;
		
		if (object instanceof BasicElement) {
			
			BasicElement basicElement = (BasicElement) object;
			
			genericKey = new Key(basicElement);

			if (subscriptor.getEntityPks() != null) {
				
				for (String entityPk : subscriptor.getEntityPks().getEntityPk()) {
					
					if (entityPk.contains("=")) {
						
						String[] entityPkSplited = entityPk.split("=");
						
						if (entityPkSplited.length == 2) {
							
							String pkName = entityPkSplited[0];
							String pkValue = entityPkSplited[1];
							
							((Key) genericKey).setId(pkName, pkValue);
						}
					}
				}
			}
		}
		else if (object instanceof Command) {

			Command command = (Command) object;
			
			genericKey = new Cmd(command);
			
			if (subscriptor.getCmdTimestampFilter() != null) {
				
				((Cmd) genericKey).setTimestampFilter((long) subscriptor.getCmdTimestampFilter());
			}
		}
		
		return genericKey;
	}
}
