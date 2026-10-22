package com.revenga.rits.back.ep.pub.stomp.service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import com.revenga.rits.back.adapter.lib.model.PubStompAdapterV1.Publishers;
import com.revenga.rits.back.adapter.lib.model.PubStompAdapterV1.Publishers.Publisher;
import com.revenga.rits.back.cache.core.manager.CacheManager;
import com.revenga.rits.back.cache.core.manager.RedisConnection.Message;
import com.revenga.rits.back.data.core.model.command.Command;
import com.revenga.rits.back.ep.pub.stomp.model.PubStompEP;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class PublisherService {

	@Autowired
	ElementCacheManager elementCacheManager;
	
	@Autowired
	RouterService routerService;
	
	@Autowired
	PubStompService pubStompService;
	
	@Getter
	Map<Publisher, StompSession> publisherStompSessions = Collections
			.synchronizedMap(new HashMap<Publisher, StompSession>());
	
	@Getter
	boolean attempingConnection = false;
	
	private static final long[] STOMP_HEARTBEAT = new long[] {10000L, 10000L};
	
	public void load(Publishers publishers) {
		
		try {
			
			attempingConnection = true;
			this.publisherStompSessions.clear();
			
			if (publishers != null) {
				
				List<Publisher> publisherList = publishers.getPublisher();
				
				ExecutorService executor = Executors.newSingleThreadExecutor();
				
				Runnable task = () -> {
					
					for (Publisher publisher : publisherList) {
						
						connect(publisher);
					}
					
					String threadNameRouterService = "Thread-" + routerService.getClass().getSimpleName();
					
					(new Thread(routerService, threadNameRouterService)).start();
					
					attempingConnection = false;
				};
				
				executor.submit(task);
			}
			
		} catch (Exception e) {
			
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}
	}
	
	private void connect(Publisher publisher) {
		
		ListenableFuture<StompSession> stompSession = null;
		WebSocketStompClient stompClient;
		StompSessionHandler publisherHnd;
		WebSocketClient client;
		int connectionAttempts = 0;
		long waitTimeConnect = 0;
		boolean connected = false;
			
		while (!connected) {
			
			try {	
				
				connectionAttempts++;
				
				if (waitTimeConnect < 60000) {
					
					waitTimeConnect = 5000 * connectionAttempts;
				}
				else {
					
					waitTimeConnect = 60000;
				}
			
				client = new StandardWebSocketClient();
				stompClient = new WebSocketStompClient(client);
				
				ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
				taskScheduler.afterPropertiesSet();
				stompClient.setTaskScheduler(taskScheduler); // for heartbeats, receipts
				stompClient.setDefaultHeartbeat(STOMP_HEARTBEAT);
				stompClient.setMessageConverter(new MappingJackson2MessageConverter());
				publisherHnd = new PublisherHnd(this, publisher);
				
				if (!StringUtils.isEmpty(publisher.getUser())) {
					
					WebSocketHttpHeaders headers = null;
				    
					StompHeaders sHeaders = new StompHeaders();
					
					sHeaders.add("login", publisher.getUser());
				    sHeaders.add("passcode", publisher.getPassword());
				    
				    stompSession = stompClient.connect(publisher.getBroker(), headers, sHeaders, publisherHnd);
				}
				else {
				
					stompSession = stompClient.connect(publisher.getBroker(), publisherHnd);
				}
				
				if (stompSession != null) {
					
					StompSession stompSes = stompSession.get();
					
					log.info("Connected to stomp session for publisher " + publisher.getId());
					
				    publisherStompSessions.put(publisher, stompSes);
				    
				    connected = true;
				}
				
			} catch (InterruptedException e) {
				
				log.error(e.getMessage());
				log.debug(ExceptionUtils.getStackTrace(e));
				
				log.warn("Attemping to connect in " + waitTimeConnect / 1000 + " secs. to " + publisher.getBroker() + " (" + connectionAttempts + " times) ...");
				
				try {
					Thread.sleep(waitTimeConnect);
				} catch (InterruptedException e1) {
					log.error(e.getMessage());
					log.debug(ExceptionUtils.getStackTrace(e));
					Thread.currentThread().interrupt();
				}
				
				Thread.currentThread().interrupt();
			} catch (ExecutionException e) {
				
				log.error(e.getMessage());
				log.debug(ExceptionUtils.getStackTrace(e));
				
				log.warn("Attemping to in connect " + waitTimeConnect / 1000 + " secs. to " + publisher.getBroker() + " (" + connectionAttempts + " times) ...");
				
				try {
					Thread.sleep(waitTimeConnect);
				} catch (InterruptedException e1) {
					log.error(e.getMessage());
					log.debug(ExceptionUtils.getStackTrace(e));
					Thread.currentThread().interrupt();
				}
			}
		}	
	}
	
	public void disconnect(Throwable exception) {
		
		if (exception != null) {
			
			log.error(exception.getMessage());
			log.debug(ExceptionUtils.getStackTrace(exception));
		}
		
		reconnect();
	}
	
	private void reconnect() {
		
		PubStompEP pubStompEP = pubStompService.getPubStompEP();
		
		if (pubStompEP != null) {
				
			routerService.stop();
			
			load(pubStompEP.getPubStompAdapter().getPublishers());
		}
	}

	public void send(String channel, Message message) {
		
		Object object = null;
	
		try {
			
			if (message == Message.HSET) {
			
				object = CacheManager.getInstance().getFromChannel(channel);
			}
			else if (message == Message.SET) {
				
				object = CacheManager.getInstance().getFromChannel(channel);
			}
			else if (message == Message.DEL) {
				
				object = elementCacheManager.getDeleted(channel);
			}
			
			if (object != null) {
			
				routerService.add(object, message);
			}
			
		} catch (Exception e) {
			
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}
	}
	
	public void send(byte[] channel, Message message) {
		
		Object object = null;
	
		try {
			
			if (message == Message.HSET) {
			
				object = CacheManager.getInstance().getFromChannel(channel);
			}
			else if (message == Message.SET) {
				
				object = CacheManager.getInstance().getFromChannel(channel);
			}
			else if (message == Message.DEL) {
				
				object = elementCacheManager.getDeleted(channel);
			}
			
			if (object != null) {
			
				routerService.add(object, message);
			}
			
		} catch (Exception e) {
			
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}
	}
	
	public void send(Class<? extends Command> clazz, String message) {
		
		Object object = null;
	
		try {
			
			object = CacheManager.getInstance().getMessage(clazz, message);
			
			if (object != null) {
			
				routerService.add(object, null);
			}
			
		} catch (Exception e) {
			
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}
	}
	
	public void send(byte[] message) {
		
		Object object = null;
	
		try {
			
			object = CacheManager.getInstance().getMessage(message);
			
			if (object != null) {
			
				routerService.add(object, null);
			}
			
		} catch (Exception e) {
			
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}
	}
}
