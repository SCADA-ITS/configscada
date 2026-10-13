package com.revenga.rits.back.manager.lib.service;

import org.apache.commons.lang3.exception.ExceptionUtils;

import com.revenga.rits.back.adapter.lib.model.ManagerAdapterV1.Subscriptors.Subscriptor;
import com.revenga.rits.back.cache.core.manager.RedisConnection.Message;
import com.revenga.rits.back.cache.core.model.GenericKey;
import com.revenga.rits.back.cache.core.subscriptor.BinarySubscriptorEventHandler;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class BinarySubscriptorHnd extends BinarySubscriptorEventHandler {

	ExecutorService managerService;
	Subscriptor subscriptor;
	
	public BinarySubscriptorHnd(ExecutorService managerService, Subscriptor subscriptor, GenericKey... patternKeys) {
		super(patternKeys);
		
		this.managerService = managerService;
		this.subscriptor = subscriptor;
	}
	
	public BinarySubscriptorHnd(ExecutorService managerService, Subscriptor subscriptor, byte[]... channels) {
		super(channels);
		
		this.managerService = managerService;
		this.subscriptor = subscriptor;
	}

	@Override
	public void onPMessage(byte[] pattern, byte[] channel, byte[] message) {
		// TODO Auto-generated method stub
		super.onPMessage(pattern, channel, message);
		
		if (getType() == Type.NOTIFY_KEYSPACE_EVENTS) {
			
			receiveFromPattern(channel, message);
		}
	}
	
	@Override
	public void onMessage(byte[] channel, byte[] message) {
		// TODO Auto-generated method stub
		super.onMessage(channel, message);
		
		if (getType() == Type.CHANNELS) {
			
			receiveFromChannel(channel, message);
		}
	}

	private void receiveFromPattern(byte[] channel, byte[] message) {
		
		String messageStr = null;
		
		try {
			
			if (message != null) {
				
				messageStr = new String(message);
				
				if (messageStr.equals(Message.HSET.getValue())) {
					
					managerService.send(this.subscriptor, channel);
				}
				else if (messageStr.equals(Message.SET.getValue())) {
					
					managerService.send(this.subscriptor, channel);
				}
				else if (messageStr.equals(Message.DEL.getValue())) {
					
					// TODO: Nothing to do?
				}
				else if (messageStr.equals(Message.EXPIRE.getValue())) {
					
					// TODO: Nothing to do?
				}
				else if (messageStr.equals(Message.EXPIRED.getValue())) {
					
					// TODO: Nothing to do?
				}
				else {
					
					throw new Exception("Unknown message type: " + messageStr);
				}
			}

		} catch (Exception e) {
			
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}
	}
	
	private void receiveFromChannel(byte[] channel, byte[] message) {
		
		managerService.send(subscriptor, null, message);
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
