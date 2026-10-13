package com.revenga.rits.back.ep.pub.stomp.service;

import org.apache.commons.lang3.exception.ExceptionUtils;

import com.revenga.rits.back.cache.core.manager.RedisConnection;
import com.revenga.rits.back.cache.core.manager.RedisConnection.Message;
import com.revenga.rits.back.cache.core.model.GenericKey;
import com.revenga.rits.back.cache.core.subscriptor.BinarySubscriptorEventHandler;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class BinarySubscriptorHnd extends BinarySubscriptorEventHandler {

	PublisherService publisherService;
	
	public BinarySubscriptorHnd(PublisherService publisherService, GenericKey... patternKeys) {
		super(patternKeys);
		
		this.publisherService = publisherService;
	}
	
	public BinarySubscriptorHnd(PublisherService publisherService, byte[]... channels) {
		super(channels);
		
		this.publisherService = publisherService;
	}

	@Override
	public void onPMessage(byte[] pattern, byte[] channel, byte[] message) {
		// TODO Auto-generated method stub
		super.onPMessage(pattern, channel, message);
		
		log.debug("onPMessage(" + new String(pattern) + ", " + new String(channel) +")");
		
		if (getType() == Type.NOTIFY_KEYSPACE_EVENTS) {
			
			receiveFromPattern(channel, message);
		}
	}
	
	@Override
	public void onMessage(byte[] channel, byte[] message) {
		// TODO Auto-generated method stub
		super.onMessage(channel, message);
		
		//log.debug("onMessage(" + new String(channel) +")");
		
		if (getType() == Type.CHANNELS) {
			
			receiveFromChannel(channel, message);
		}
	}
	
	private void receiveFromPattern(byte[] channel, byte[] message) {
		
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
	
	private void receiveFromChannel(byte[] channel, byte[] message) {

		publisherService.send(message);
	}

	@Override
	public void onPSubscribe(byte[] pattern, int subscribedChannels) {

		super.onPSubscribe(pattern, subscribedChannels);
		
		log.info("onPSubscribe(" + new String(pattern) + ", " + subscribedChannels +")");
	}
	
	@Override
	public void onSubscribe(byte[] channel, int subscribedChannels) {

		super.onSubscribe(channel, subscribedChannels);
		
		log.info("onSubscribe(" + new String(channel) + ", " + subscribedChannels +")");
	}

	@Override
	public void onPUnsubscribe(byte[] pattern, int subscribedChannels) {
		// TODO Auto-generated method stub
		super.onPUnsubscribe(pattern, subscribedChannels);
		
		log.info("onPUnsubscribe(" + new String(pattern) + ", " + subscribedChannels +")");
	}
	
	@Override
	public void onUnsubscribe(byte[] channel, int subscribedChannels) {
		
		super.onUnsubscribe(channel, subscribedChannels);
		
		log.info("onUnsubscribe(" + new String(channel) + ", " + subscribedChannels +")");
	}
}
