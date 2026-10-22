package com.revenga.rits.back.manager.lib.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revenga.rits.back.adapter.lib.model.ManagerAdapterV1;
import com.revenga.rits.back.adapter.lib.model.ManagerAdapterV1.Executors.Executor;
import com.revenga.rits.back.adapter.lib.model.ManagerAdapterV1.Subscriptors.Subscriptor;
import com.revenga.rits.back.adapter.lib.service.SessionAdapter;
import com.revenga.rits.back.cache.core.manager.RedisConnection;
import com.revenga.rits.back.cache.core.service.impl.CommandCache;
import com.revenga.rits.back.manager.lib.model.ManagerModel;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class ManagerService {

	@Getter
	@Autowired
	ExecutorService executorService;
	
	@Autowired
	SubscriptorService subscriptorService;
	
	@Autowired
	BinarySubscriptorService binarySubscriptorService;
	
	@Getter
	private ManagerModel managerModel;
	
	public ManagerService() {
		super();

	}
	
	public void stop() {
		
		executorService.stop();
	}

	public void load(String xmlAdapter) {
		
		try {
				
			if (StringUtils.isEmpty(xmlAdapter)) {
				
				throw new NullPointerException ("xmlAdapter is null");
			}
			
			SessionAdapter sessionAdapter = new SessionAdapter();
			
			ManagerAdapterV1 managerAdapterV1 = (ManagerAdapterV1) sessionAdapter.getAdapter("adapters/manager/" + xmlAdapter, ManagerAdapterV1.class);
			
			discardDisabled(managerAdapterV1);
			
			managerModel = new ManagerModel();
			
			managerModel.setXmlAdapter(xmlAdapter);
			managerModel.setManagerAdapterV1(managerAdapterV1);
			
			executorService.load(managerAdapterV1.getExecutors());
			
			if ((new CommandCache(RedisConnection.getInstance()).isCommandCacheBin())) {
			
				binarySubscriptorService.load(managerAdapterV1.getSubscriptors());
			}
			else {
				
				subscriptorService.load(managerAdapterV1.getSubscriptors());
			}
			
					
		} catch (Exception e) {
			
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}
	}
	
	private void discardDisabled(ManagerAdapterV1 managerAdapterV1) {
		
		List<Subscriptor> subscriptorsToRemove = new ArrayList<>();
		List<Executor> executorsToRemove = new ArrayList<>();
		
		if (managerAdapterV1.getSubscriptors() != null) {
		
			List<Subscriptor> subscriptors = managerAdapterV1.getSubscriptors().getSubscriptor();
			
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
		
		if (managerAdapterV1.getExecutors() != null) {
			
			List<Executor> executors = managerAdapterV1.getExecutors().getExecutor();
			
			if (!CollectionUtils.isEmpty(executors)) {
				
				for (Executor executor : executors) {
				
					if (!executor.isEnabled()) {
					
						executorsToRemove.add(executor);
					}
				}
				
				// Clear publishers disabled
				for (Executor executorToRemove : executorsToRemove) {
					
					executors.remove(executorToRemove);
				}
				
				executorsToRemove.clear();
			}
		}		
	}
}
