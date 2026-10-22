package com.revenga.rits.back.ep.pub.stomp.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revenga.rits.back.adapter.lib.model.PubStompAdapterV1;
import com.revenga.rits.back.adapter.lib.model.PubStompAdapterV1.Publishers.Publisher;
import com.revenga.rits.back.adapter.lib.model.PubStompAdapterV1.Publishers.Publisher.Topics.Topic;
import com.revenga.rits.back.adapter.lib.model.PubStompAdapterV1.Subscriptors.Subscriptor;
import com.revenga.rits.back.adapter.lib.service.SessionAdapter;
import com.revenga.rits.back.cache.core.manager.RedisConnection;
import com.revenga.rits.back.cache.core.service.impl.CommandCache;
import com.revenga.rits.back.ep.pub.stomp.model.PubStompEP;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class PubStompService {
	
	@Getter
	@Autowired
	ElementCacheManager elementCacheManager;
	
	@Autowired
	PublisherService publisherService;
	
	@Autowired
	SubscriptorService subscriptorService;
	
	@Autowired
	BinarySubscriptorService binarySubscriptorService;
	
	@Getter
	PubStompEP pubStompEP;
	
	public void load(String xmlAdapter) {
		
		try {
			
			if (StringUtils.isEmpty(xmlAdapter)) {
				
				throw new NullPointerException ("xmlAdapter is null");
			}
							
			SessionAdapter sessionAdapter = new SessionAdapter();
			
			PubStompAdapterV1 pubStompAdapterV1 = (PubStompAdapterV1) sessionAdapter.getAdapter("adapters/pub-stomp/" + xmlAdapter, PubStompAdapterV1.class);
			
			if (pubStompAdapterV1 != null) {
				
				discardDisabled(pubStompAdapterV1);
				
				pubStompEP = new PubStompEP();
				
				pubStompEP.setXmlAdapter(xmlAdapter);
				pubStompEP.setPubStompAdapter(pubStompAdapterV1);
				
				publisherService.load(pubStompAdapterV1.getPublishers());

				if ((new CommandCache(RedisConnection.getInstance()).isCommandCacheBin())) {
					RedisConnection redisConnection = RedisConnection.getInstance();
					redisConnection.getProcessMonitor().getProcessInfo().setReady(true);
					binarySubscriptorService.load(pubStompAdapterV1.getSubscriptors());
				}
				else {
					
					subscriptorService.load(pubStompAdapterV1.getSubscriptors());
				}
			}
			
		} catch (Exception e) {
			
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}
	}
	
	private void discardDisabled(PubStompAdapterV1 pubStompAdapterV1) {
		
		List<Subscriptor> subscriptorsToRemove = new ArrayList<>();
		List<Publisher> publishersToRemove = new ArrayList<>();
		List<Topic> topicsToRemove = new ArrayList<>();
		
		if (pubStompAdapterV1.getSubscriptors() != null) {
		
			List<Subscriptor> subscriptors = pubStompAdapterV1.getSubscriptors().getSubscriptor();
			
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
		
		if (pubStompAdapterV1.getPublishers() != null) {
			
			List<Publisher> publishers = pubStompAdapterV1.getPublishers().getPublisher();
			
			if (!CollectionUtils.isEmpty(publishers)) {
				
				for (Publisher publisher : publishers) {
				
					if (!publisher.isEnabled()) {
					
						publishersToRemove.add(publisher);
					}
					else {
						
						if (publisher.getTopics() != null) {
							
							List<Topic> topics = publisher.getTopics().getTopic();
								
							if (!CollectionUtils.isEmpty(topics)) {
								
								for (Topic topic : topics) {
									
									if (!topic.isEnabled()) {
										
										topicsToRemove.add(topic);
									}
								}
								
								// Clean topics disabled for subscription
								for (Topic topicToRemove : topicsToRemove) {
									
									topics.remove(topicToRemove);
								}
								
								topicsToRemove.clear();
							}							
						}
					}
				}
				
				// Clear publishers disabled
				for (Publisher publisherToRemove : publishersToRemove) {
					
					publishers.remove(publisherToRemove);
				}
				
				publishersToRemove.clear();
			}
		}		
	}
}
