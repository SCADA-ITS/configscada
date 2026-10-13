package com.revenga.rits.back.ep.pub.stomp.publisher;

import java.lang.reflect.Field;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.messaging.simp.stomp.StompSession;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revenga.rits.back.adapter.lib.model.PubStompAdapterV1.Publishers.Publisher.Topics.Topic;
import com.revenga.rits.back.cache.core.manager.RedisConnection.Message;
import com.revenga.rits.back.data.core.util.ClassesUtil;
import com.revenga.rits.back.data.core.util.ConfigProperty;
import com.revenga.rits.back.ep.pub.stomp.service.ElementCacheManager;
import com.revenga.rits.back.front.core.model.vo.BasicElementVO;
import com.revenga.rits.back.front.core.model.vo.StompMessage;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;

@Getter
@Log4j2
public abstract class GenericPublisher implements Runnable {

	protected BlockingQueue<Pair<Object, Message>> queueElements;
	protected Map<String, StompMessage> preparedElements;
	protected StompSession stompSession;
	protected Topic topic;
	protected ElementCacheManager elementCacheManager;
	protected boolean stop;
	protected boolean alive;
	protected boolean ready;
	
	private static final int MAX_MESSAGE_SIZE = Integer.parseInt(ConfigProperty.getInstance()
			.getProperty("application.properties", "max_message_size"));
	
	protected GenericPublisher(StompSession stompSession, Topic topic, ElementCacheManager elementCacheManager) {
		super();
		
		this.stompSession = stompSession;
		this.topic = topic;
		this.elementCacheManager = elementCacheManager;
		this.queueElements = new LinkedBlockingQueue<>();
		this.preparedElements = new HashMap<>();
		this.ready = false;
	}
	
	public void stop() {
		
		this.stop = true;
	}
	
	public void add(Pair<Object, Message> queueElement) {
		
		this.queueElements.add(queueElement);
	}
	
	private void publish() {
		
		if (!MapUtils.isEmpty(preparedElements)) {
						
			for (Map.Entry<String, StompMessage> entry : preparedElements.entrySet()) {
		
				ObjectMapper objectMapper = new ObjectMapper();
				
				objectMapper.setSerializationInclusion(Include.NON_NULL);
				
				try {
					
					String destination = getDestination(entry.getValue());
					
					if (!StringUtils.isEmpty(destination)) {
						
						StompMessage[] stompMessages = splitMessage(entry.getValue());
						
						synchronized (stompSession) {
							
							for (StompMessage stompMessage : stompMessages) {
								
								String message = objectMapper.writeValueAsString(stompMessage);
								
								if (message.length() <= MAX_MESSAGE_SIZE) {
									
									stompSession.send(destination, message);
									
									log.debug("(Length: " + message.length() + ") " + destination + " => " + message);
								}
								else {
									
									log.warn("(Exceeded length: " + message.length() + ") " + destination + " => " + message);
								}
							}
						}
					}
					
				} catch (Exception e) {
					
					log.error(e.getMessage());
					log.debug(ExceptionUtils.getStackTrace(e));
					
				} 
			}
			
			preparedElements.clear();
		}
	}
	
	private StompMessage[] splitMessage(StompMessage stompMessage) {
		
		List<StompMessage> stompMessageList = new ArrayList<>();
		
		if (!CollectionUtils.isEmpty(stompMessage.getBasicElementsVO())) {
			
			int numMessagesTotal = stompMessage.getBasicElementsVO().size();
			
			try {
				
				ObjectMapper objectMapper = new ObjectMapper();
				BasicElementVO firstElement = stompMessage.getBasicElementsVO().get(0);
				
				objectMapper.setSerializationInclusion(Include.NON_NULL);
				
				int messageSizeEstimate = objectMapper.writeValueAsString(firstElement).length() * numMessagesTotal;
				int numToSplit = (messageSizeEstimate / MAX_MESSAGE_SIZE) + 1;
				int numMessagesToSplit = (numMessagesTotal >= numToSplit) ? numMessagesTotal / numToSplit : 1;
				int messageCount = 0;
				
				while (messageCount < numMessagesTotal) {
					
					BasicElementVO be = stompMessage.getBasicElementsVO().get(messageCount);
					
					StompMessage sm = null;
					
					if (messageCount % numMessagesToSplit == 0) {
						
						sm = new StompMessage();
						
						sm.setTimestamp(Instant.now().toEpochMilli());
						sm.setAction(stompMessage.getAction());
						
						
						stompMessageList.add(sm);
					}
					else {
						
						sm = stompMessageList.get(stompMessageList.size() - 1);
					}
					
					sm.getBasicElementsVO().add(be);
					
					messageCount++;
				}
				
				
			} catch (JsonProcessingException e) {
				
				log.error(e.getMessage());
				log.debug(ExceptionUtils.getStackTrace(e));
			}
		}
		
		return stompMessageList.stream().toArray(StompMessage[]::new);
	}
	
	private String getDestination(StompMessage stompMessage) {
		
		BasicElementVO basicElementVO;
		String topicPath = null;
		
		try {
			
			if (!topic.isCustomPath()) {
				
				basicElementVO = stompMessage.getFirst();
				topicPath = topic.getPath();
					
				if (basicElementVO != null && !StringUtils.isEmpty(topicPath)) {
				
					while (topicPath.contains("{")) {
					
						String expToEval = topicPath.substring(topicPath.indexOf('{') + 1, topicPath.indexOf('}'));
						
						if (!StringUtils.isEmpty(expToEval)) {
							
							Field fieldToEval = ClassesUtil.getField(basicElementVO.getClass(), expToEval);					
							fieldToEval.setAccessible(true);
							
							String fieldValue = String.valueOf(fieldToEval.get(basicElementVO));
							topicPath = topicPath.replaceFirst("\\{(.*?)\\}", fieldValue);
						}
					}
				}				
			}
			else {
				
				String customDestination = stompMessage.getCustomDestination();
				
				if (!StringUtils.isEmpty(customDestination)) {
					
					topicPath = customDestination;
				}
			}
			
		} catch (IllegalArgumentException | IllegalAccessException e) {
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}
		
		return topicPath;
	}

	@Override
	public void run() {

		Instant start;
		Instant end;
		Duration timeElapsed;
		
		this.alive = true;
		
		log.info(Thread.currentThread().getName() + " is running...");
		
		while (!stop) {
		
			try {
	            
				start = Instant.now();
				end = start;			
				timeElapsed = Duration.between(start, end);
				
				this.ready = true;

				while (timeElapsed.toMillis() < topic.getMergeWaitTime() && !stop) {
				
					Pair<Object, Message> queueElement = queueElements.poll(); 
					
					if (queueElement != null) {
						
						prepare(queueElement);
					}
					
					Thread.sleep(1);
					
					end = Instant.now();
					timeElapsed = Duration.between(start, end);
				}
				
				if (!stop && !MapUtils.isEmpty(preparedElements)) {
				
//					log.info(Thread.currentThread().getName() + " queue size = " + queueElements.size());
					
					publish();
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
	
	protected abstract BasicElementVO prepare(Pair<Object, Message> queueElement);
}
