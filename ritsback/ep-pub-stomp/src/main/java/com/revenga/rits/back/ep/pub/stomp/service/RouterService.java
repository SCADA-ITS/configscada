package com.revenga.rits.back.ep.pub.stomp.service;

import java.lang.reflect.InvocationTargetException;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.stereotype.Service;

import com.revenga.rits.back.adapter.lib.model.PubStompAdapterV1.Publishers.Publisher;
import com.revenga.rits.back.adapter.lib.model.PubStompAdapterV1.Publishers.Publisher.Topics.Topic;
import com.revenga.rits.back.adapter.lib.model.PubStompAdapterV1.Subscriptors.Subscriptor;
import com.revenga.rits.back.cache.core.manager.RedisConnection;
import com.revenga.rits.back.cache.core.manager.RedisConnection.Message;
import com.revenga.rits.back.data.core.util.ClassesUtil;
import com.revenga.rits.back.ep.pub.stomp.publisher.GenericPublisher;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class RouterService implements Runnable {

	private static final long MAX_TIME_PUBLISHER_READY = 60000L;
	
	@Autowired
	PubStompService pubStompService;
	
	@Autowired
	PublisherService publisherService;
	
	BlockingQueue<Pair<Object, Message>> queueElements;
	
	Map<Pair<Publisher, Topic>, GenericPublisher> publishers = new HashMap<>();
	
	boolean stop;
	@Getter
	boolean alive;
	
	public RouterService() {
		
		super();
		
		this.queueElements = new LinkedBlockingQueue<>();
		this.stop = false;
		this.alive = false;
	}
	
	public void stop() {
		
		stopPublishers();
		
		this.stop = true;
	}

	public void add(Object object, Message message) {
		
		this.queueElements.add(new MutablePair<>(object, message));
	}

	@Override
	public void run() {
		
		startPublishers();
		
		this.alive = true;
		
		log.info(Thread.currentThread().getName() + " is running...");
		
		while (!stop) {
			
			try {
				
				if (!queueElements.isEmpty()) {
					
					Pair<Object, Message> queueElement = this.queueElements.poll();
										
					if (queueElement != null) {
						
						route(queueElement);
					}
				}
				
				Thread.sleep(1);
				
			} catch (InterruptedException e) {
				
				log.error(e.getMessage());
				log.debug(ExceptionUtils.getStackTrace(e));
				Thread.currentThread().interrupt();
			}
		}
		
		this.alive = false;
		this.stop = false;
		
		log.info(Thread.currentThread().getName() + " is stopped");
	}
	
	private void route(Pair<Object, Message> queueElement) {
	
		List<Topic> topics = getTopics(queueElement.getKey());
		
		for (Topic topic : topics) {
			
			Publisher publisher = getPublisher(topic);
			
			GenericPublisher genericPublisher = publishers.get(new MutablePair<Publisher, Topic>(publisher, topic));
			
			if (genericPublisher != null) {
			
				genericPublisher.add(queueElement);
			}
		}
	}
	
	private void startPublishers() {
		
		try {
			
			for (Pair<Publisher, Topic> publisherTopic : getPublisherWithTopics()) {
			
				Publisher publisher = publisherTopic.getKey();
				Topic topic = publisherTopic.getValue();
				
				String publisherClassName = topic.getPublisherClass();
							
				Class<?> publisherClass = Class
						.forName("com.revenga.rits.back.ep.pub.stomp.publisher." + publisherClassName);
				
				StompSession stompSession = publisherService.getPublisherStompSessions().get(publisher);
				
				if (stompSession != null) {

					GenericPublisher genericPublisher = (GenericPublisher) publisherClass
							.getDeclaredConstructor(StompSession.class, Topic.class, ElementCacheManager.class)
							.newInstance(stompSession, topic, pubStompService.getElementCacheManager());

					String threadNameGenericPublisher = "Thread-" + publisherClassName + "-Topic-" + topic.getId(); 
					
					(new Thread(genericPublisher, threadNameGenericPublisher)).start();
					
					publishers.put(publisherTopic, genericPublisher);
				}
			}
			
			checkPublishersReady();
			
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException | NoSuchMethodException | SecurityException e) {
		
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}
	}
	
	private void stopPublishers() {
		
		for (Entry<Pair<Publisher, Topic>, GenericPublisher> entry : publishers.entrySet()) {
			
			GenericPublisher genericPublisher = entry.getValue();
			
			genericPublisher.stop();
		}
	}
	
	private List<Pair<Publisher, Topic>> getPublisherWithTopics() {
		
		List<Publisher> pubs = pubStompService.getPubStompEP().getPubStompAdapter().getPublishers().getPublisher();
		List<Pair<Publisher, Topic>> topics = new ArrayList<>();
		
		for (Publisher pub : pubs) {
						
			if (pub.getTopics() != null && !CollectionUtils.isEmpty(pub.getTopics().getTopic())) {
				
				for (Topic topic : pub.getTopics().getTopic()) {
					
					topics.add(new MutablePair<>(pub, topic));
				}
			}
		}
		
		return topics;
	}
	
	private Publisher getPublisher(Topic topic) {
		
		List<Publisher> pubs = pubStompService.getPubStompEP().getPubStompAdapter().getPublishers().getPublisher();
		
		for (Publisher pub : pubs) {
				
			if (pub.getTopics() != null && !CollectionUtils.isEmpty(pub.getTopics().getTopic())) {
				
				for (Topic topicItem : pub.getTopics().getTopic()) {
					
					if (topicItem == topic) {
					
						return pub;
					}
				}
			}
		}
		
		return null;
	}

	private List<Topic> getTopics(Object object) {
		
		List<Topic> topics = new ArrayList<>();
		
		for (Pair<Publisher, Topic> publisherTopic : getPublisherWithTopics()) {
			
			Topic topic = publisherTopic.getValue();
			
			if (topic.getSubscriptorsRef() != null && !CollectionUtils.isEmpty(topic.getSubscriptorsRef().getSubscriptorIdRef())) {
				
				for (Short subscriptorRef : topic.getSubscriptorsRef().getSubscriptorIdRef()) {
					
					Class<?> clazz = getClass(subscriptorRef);
					
					if (object.getClass() == clazz) {
						
						topics.add(topic);		
					}
				}
			}
		}
		
		return topics;
	}
	
	private Class<?> getClass(Short subscriptorRef) {
		
		List<Subscriptor> subscriptors;
		Class<?> clazz = null;
		
		subscriptors = pubStompService.getPubStompEP().getPubStompAdapter().getSubscriptors().getSubscriptor();
		
		Subscriptor subscriptor = subscriptors.stream().filter(e -> e.getId() == subscriptorRef).findFirst()
				.orElse(null);
		
		if (subscriptor != null) {

			try {
			
				clazz = ClassesUtil.getModelClassBySimpleClassName(subscriptor.getEntity());
				
			} catch (ClassNotFoundException e) {
				
				log.error(e.getMessage());
				log.debug(ExceptionUtils.getStackTrace(e));
			}
		}
		
		return clazz;
	}
	
	private void checkPublishersReady() {
		
		new Thread("Thread-ChkPublishersReady") {

			@Override
			public void run() {

				boolean allReady = true;

				try {

					if (!MapUtils.isEmpty(publishers)) {

						Instant instantIni = Instant.now();

						for (Entry<Pair<Publisher, Topic>, GenericPublisher> entry : publishers.entrySet()) {
							
							if (allReady) {
							
								GenericPublisher genericPublisher = entry.getValue();

								boolean localStop = false;

								while (!localStop) {

									if (genericPublisher.isReady()) {

										localStop = true;
										
									} else {

										if (Duration.between(instantIni, Instant.now())
												.toMillis() > MAX_TIME_PUBLISHER_READY) {

											log.error("Time exceeded for " + genericPublisher.getClass().getSimpleName()
													+ " to be ready");

											allReady = false;
										}
									}

									Thread.sleep(1);
								}
							}
						}

						RedisConnection.getInstance().getProcessMonitor().getProcessInfo().setReady(allReady);
					}

				} catch (InterruptedException e) {

					log.error(e.getMessage());
					log.debug(ExceptionUtils.getStackTrace(e));
					Thread.currentThread().interrupt();
				}
			}

		}.start();
	}
}
