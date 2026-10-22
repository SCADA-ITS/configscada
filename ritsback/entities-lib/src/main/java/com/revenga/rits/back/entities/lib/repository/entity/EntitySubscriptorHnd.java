package com.revenga.rits.back.entities.lib.repository.entity;

import org.apache.commons.lang3.exception.ExceptionUtils;

import com.revenga.rits.back.cache.core.subscriptor.SubscriptorEventHandler;
import com.revenga.rits.back.data.core.model.command.Command;
import com.revenga.rits.back.data.core.util.ClassesUtil;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class EntitySubscriptorHnd extends SubscriptorEventHandler {

	EntityRepository entityRepository;
	
	public EntitySubscriptorHnd(EntityRepository entityRepository, String... channels) {
		super(channels);
		
		this.entityRepository = entityRepository;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public void onMessage(String channel, String message) {

		super.onMessage(channel, message);
		
		if (getType() == Type.CHANNELS) {
			
			try {
	
				entityRepository.onReceiveFromChannel((Class<? extends Command>) ClassesUtil.getModelClassBySimpleClassName(channel), message);
			
			} catch (ClassNotFoundException e) {
			
				log.error(e.getMessage());
				log.debug(ExceptionUtils.getStackTrace(e));
			}
		}
	}
	
	@Override
	public void onSubscribe(String channel, int subscribedChannels) {

		super.onSubscribe(channel, subscribedChannels);
		
		log.info("onSubscribe(" + channel + ", " + subscribedChannels +")");
	}

	@Override
	public void onUnsubscribe(String channel, int subscribedChannels) {
		
		super.onUnsubscribe(channel, subscribedChannels);
		
		log.info("onUnsubscribe(" + channel + ", " + subscribedChannels +")");
	}
}
