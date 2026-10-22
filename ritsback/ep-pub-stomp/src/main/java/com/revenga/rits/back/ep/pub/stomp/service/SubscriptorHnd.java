package com.revenga.rits.back.ep.pub.stomp.service;

import org.apache.commons.lang3.exception.ExceptionUtils;

import com.revenga.rits.back.cache.core.manager.RedisConnection;
import com.revenga.rits.back.cache.core.manager.RedisConnection.Message;
import com.revenga.rits.back.cache.core.model.GenericKey;
import com.revenga.rits.back.cache.core.subscriptor.SubscriptorEventHandler;
import com.revenga.rits.back.data.core.model.command.Command;
import com.revenga.rits.back.data.core.util.ClassesUtil;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class SubscriptorHnd extends SubscriptorEventHandler {

	PublisherService publisherService;
	
	public SubscriptorHnd(PublisherService publisherService, GenericKey... patternKeys) {
		super(patternKeys);
		
		this.publisherService = publisherService;
	}
	
	public SubscriptorHnd(PublisherService publisherService, String... channels) {
		super(channels);
		
		this.publisherService = publisherService;
	}

	@Override
	public void onPMessage(String pattern, String channel, String message) {
		// TODO Auto-generated method stub
		super.onPMessage(pattern, channel, message);
		
		log.debug("onPMessage(" + pattern + ", " + channel + ", " + message +")");
		
		if (getType() == Type.NOTIFY_KEYSPACE_EVENTS) {
			
			receiveFromPattern(channel, message);
		}
	}
	
	@Override
	public void onMessage(String channel, String message) {
		// TODO Auto-generated method stub
		super.onMessage(channel, message);
		
		//log.debug("onMessage(" + channel + ", " + message +")");
		
		if (getType() == Type.CHANNELS) {
			
			receiveFromChannel(channel, message);
		}
	}
	
	private void receiveFromPattern(String channel, String message) {
		
		try {
			
			if (message.equals(RedisConnection.Message.HSET.getValue())) {
				
				publisherService.send(channel, RedisConnection.Message.HSET);
			}
			else if (message.equals(Message.SET.getValue())) {
				
				publisherService.send(channel, RedisConnection.Message.SET);
			}
			else if (message.equals(RedisConnection.Message.DEL.getValue())) {
				
				// TODO: Nothing to do?
			}
			else if (message.equals(RedisConnection.Message.HDEL.getValue())) {
				
				// TODO: Nothing to do?
			}
			else if (message.equals(RedisConnection.Message.EXPIRE.getValue())) {
				
				// TODO: Nothing to do?
			}
			else if (message.equals(RedisConnection.Message.EXPIRED.getValue())) {
				
				// TODO: Nothing to do?
			}
			else {
				
				throw new Exception("Unknown message type: " + message);
			}
			
		} catch (Exception e) {
			
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}
	}
	
	@SuppressWarnings("unchecked")
	private void receiveFromChannel(String channel, String message) {
		
		try {
			
			publisherService.send((Class<? extends Command>) ClassesUtil.getModelClassBySimpleClassName(channel), message);
			
		} catch (ClassNotFoundException e) {
			
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}
	}

	@Override
	public void onPSubscribe(String pattern, int subscribedChannels) {

		super.onPSubscribe(pattern, subscribedChannels);
		
		log.info("onPSubscribe(" + pattern + ", " + subscribedChannels +")");
	}
	
	@Override
	public void onSubscribe(String channel, int subscribedChannels) {

		super.onSubscribe(channel, subscribedChannels);
		
		log.info("onSubscribe(" + channel + ", " + subscribedChannels +")");
	}

	@Override
	public void onPUnsubscribe(String pattern, int subscribedChannels) {
		// TODO Auto-generated method stub
		super.onPUnsubscribe(pattern, subscribedChannels);
		
		log.info("onPUnsubscribe(" + pattern + ", " + subscribedChannels +")");
	}
	
	@Override
	public void onUnsubscribe(String channel, int subscribedChannels) {
		
		super.onUnsubscribe(channel, subscribedChannels);
		
		log.info("onUnsubscribe(" + channel + ", " + subscribedChannels +")");
	}
}
