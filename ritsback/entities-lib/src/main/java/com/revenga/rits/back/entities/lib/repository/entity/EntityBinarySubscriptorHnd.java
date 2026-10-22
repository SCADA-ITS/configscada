package com.revenga.rits.back.entities.lib.repository.entity;

import com.revenga.rits.back.cache.core.subscriptor.BinarySubscriptorEventHandler;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class EntityBinarySubscriptorHnd extends BinarySubscriptorEventHandler {

	EntityRepository entityRepository;
	
	public EntityBinarySubscriptorHnd(EntityRepository entityRepository, byte[]... channels) {
		super(channels);
		
		this.entityRepository = entityRepository;
	}

	@Override
	public void onMessage(byte[] channel, byte[] message) {
		super.onMessage(channel, message);
		
		if (getType() == Type.CHANNELS) {
			
			entityRepository.onReceiveFromChannel(channel, message);
		}
	}
	
	@Override
	public void onSubscribe(byte[] channel, int subscribedChannels) {

		super.onSubscribe(channel, subscribedChannels);
		
		log.info("onSubscribe(" + new String(channel) + ", " + subscribedChannels +")");
	}

	@Override
	public void onUnsubscribe(byte[] channel, int subscribedChannels) {
		
		super.onUnsubscribe(channel, subscribedChannels);
		
		log.info("onUnsubscribe(" + new String(channel) + ", " + subscribedChannels +")");
	}
}
