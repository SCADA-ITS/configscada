package com.revenga.rits.back.io.controller.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

import com.revenga.rits.back.adapter.lib.model.ControllerAdapterV1;
import com.revenga.rits.back.adapter.lib.model.ControllerAdapterV1.Subscriptors.Subscriptor;
import com.revenga.rits.back.adapter.lib.service.SessionAdapter;
import com.revenga.rits.back.cache.core.manager.RedisConnection;
import com.revenga.rits.back.cache.core.service.impl.CommandCache;
import com.revenga.rits.back.io.controller.model.ControllerModel;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class ControllerManager {

	@Getter
	private ControllerService controllerService;
	private SubscriptorService subscriptorService;
	private BinarySubscriptorService binarySubscriptorService;
	
	private boolean commandCacheBin = false;
		
	private static ControllerManager instance;

	public static ControllerManager getInstance() {

		if (instance == null) {

			instance = new ControllerManager();
		}

		return instance;
	}

	private ControllerManager() {
		
		super();
		
		commandCacheBin = (new CommandCache(RedisConnection.getInstance())).isCommandCacheBin();
		
		if (commandCacheBin) {
		
			binarySubscriptorService = new BinarySubscriptorService();
		}
		else {
		
			subscriptorService = new SubscriptorService();
		}
	}
	
	public void stop() {
		
		controllerService.stop();
	}

	public void load(String xmlAdapter) {
		
		try {
				
			if (StringUtils.isEmpty(xmlAdapter)) {
				
				throw new NullPointerException ("xmlAdapter is null");
			}
			
			SessionAdapter sessionAdapter = new SessionAdapter();
			
			ControllerAdapterV1 controllerAdapterV1 = (ControllerAdapterV1) sessionAdapter.getAdapter("adapters/controller/" + xmlAdapter, ControllerAdapterV1.class);
			
			discardDisabled(controllerAdapterV1);
			
			ControllerModel controllerModel = new ControllerModel();
			
			controllerModel.setXmlAdapter(xmlAdapter);
			controllerModel.setControllerAdapterV1(controllerAdapterV1);
			
			if (subscriptorService != null) {
				
				controllerService = new ControllerService(subscriptorService, controllerModel);
			
				subscriptorService.setControllerService(controllerService);
				
				subscriptorService.load(controllerAdapterV1.getSubscriptors());
			}
			else if (binarySubscriptorService != null) {
				
				controllerService = new ControllerService(binarySubscriptorService, controllerModel);
				
				binarySubscriptorService.setControllerService(controllerService);
				
				binarySubscriptorService.load(controllerAdapterV1.getSubscriptors());
			}
			
			controllerService.load(controllerAdapterV1.getMods());
			
					
		} catch (Exception e) {
			
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}
	}
	
	private void discardDisabled(ControllerAdapterV1 controllerAdapterV1) {
		
		List<Subscriptor> subscriptorsToRemove = new ArrayList<>();
		
		if (controllerAdapterV1.getSubscriptors() != null) {
		
			List<Subscriptor> subscriptors = controllerAdapterV1.getSubscriptors().getSubscriptor();
			
			if (!CollectionUtils.isEmpty(subscriptors)) {
				
				for (Subscriptor subscriptor : subscriptors) {
					
					if (!subscriptor.isEnabled()) {
						
						subscriptorsToRemove.add(subscriptor);
					}
				}
				
				// Clear publishers disabled
				for (Subscriptor subscriptorToRemove : subscriptorsToRemove) {
					
					subscriptors.remove(subscriptorToRemove);
				}
				
				subscriptorsToRemove.clear();
			}
		}		
	}
}
