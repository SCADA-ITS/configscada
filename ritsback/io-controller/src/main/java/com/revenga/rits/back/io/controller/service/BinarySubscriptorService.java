package com.revenga.rits.back.io.controller.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

import com.revenga.rits.back.adapter.lib.model.ControllerAdapterV1.Subscriptors;
import com.revenga.rits.back.adapter.lib.model.ControllerAdapterV1.Subscriptors.Subscriptor;
import com.revenga.rits.back.cache.core.manager.CacheManager;
import com.revenga.rits.back.cache.core.subscriptor.BinarySubscriptorEventHandler;
import com.revenga.rits.back.cache.core.subscriptor.SubscriptorManager;
import com.revenga.rits.back.data.core.model.command.Command;
import com.revenga.rits.back.data.core.util.ClassesUtil;

import lombok.Setter;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class BinarySubscriptorService {
	
	enum SubscriptorType {
	
		KEY, CMD
	}
	
	@Setter
	ControllerService controllerService;
	
	List<SubscriptorManager> subscriptors = new ArrayList<>();
	
	public BinarySubscriptorService() {
		
		super();
	}

	public BinarySubscriptorService(ControllerService controllerService) {
		
		super();
		
		this.controllerService = controllerService;
	}
		
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
	
	private void connect(Subscriptor subscriptor) {
		
		boolean connected = false;
		Class<?> clazz = null;
		Object instanceSubscriptor = null;
		
		clazz = getSubscriptorType(subscriptor);
		
		try {
		
			if (clazz == null) {
				
				throw new NullPointerException("Check xml subscriptor " + subscriptor.getId() + " for entity value. Class not found");
			}
			
			instanceSubscriptor = clazz.getDeclaredConstructor().newInstance();
			
			if (instanceSubscriptor instanceof Command) {
				
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
		
	private Boolean subscribe(Subscriptor subscriptor, Command command) throws Exception {
		
		List<byte[]> channels = new ArrayList<>();
		Boolean res = false;

		channels.add(command.getClass().getSimpleName().getBytes());

		BinarySubscriptorEventHandler binarySubscriptorHnd = new BinarySubscriptorHnd(controllerService, subscriptor,
				channels.toArray(byte[][]::new));

		subscriptors.add(CacheManager.getInstance().subscribe(binarySubscriptorHnd));

		res = true;

		return res;
	}
}
