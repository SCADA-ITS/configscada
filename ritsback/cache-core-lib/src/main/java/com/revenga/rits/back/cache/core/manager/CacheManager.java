package com.revenga.rits.back.cache.core.manager;

import org.apache.commons.lang3.SerializationUtils;

import com.revenga.rits.back.cache.core.manager.RedisConnection;
import com.revenga.rits.back.cache.core.service.impl.CommandCache;
import com.revenga.rits.back.cache.core.service.impl.DataCache;
import com.revenga.rits.back.cache.core.service.impl.SubscriptorCache;
import com.revenga.rits.back.cache.core.subscriptor.BinarySubscriptorEventHandler;
import com.revenga.rits.back.cache.core.subscriptor.SubscriptorEventHandler;
import com.revenga.rits.back.cache.core.subscriptor.SubscriptorManager;
import com.revenga.rits.back.data.core.model.BasicElement;
import com.revenga.rits.back.data.core.model.command.Command;

public class CacheManager {

	DataCache dataCache;
	CommandCache cmdCache;
	SubscriptorCache subscriptorCache;
	
	private static CacheManager instance;
	
	public static CacheManager getInstance() {
		
		if (instance == null) {
			
			instance = new CacheManager();
		}
		
		return instance;
	}
	
	private CacheManager() {
		
		this.dataCache = new DataCache(RedisConnection.getInstance());
		this.cmdCache = new CommandCache(RedisConnection.getInstance());
		this.subscriptorCache = new SubscriptorCache();
	}
	
	public Object getFromChannel(String channel) {
		
		BasicElement basicElement = null;
		
		basicElement = dataCache.getFromChannel(channel);
		
		return basicElement;
	}
	
	public Object getFromChannel(byte[] channel) {
		
		BasicElement basicElement = null;
		
		basicElement = dataCache.getFromChannel(channel);
		
		return basicElement;
	}
	
	public Object getMessage(Class<? extends Command> clazz, String message) {
		
		Command command = null;
		
		command = cmdCache.get(clazz, message);
		
		return command;
	}
	
	public Object getMessage(byte[] message) {
		
		return SerializationUtils.deserialize(message);
	}
	
	public Command sendCommand(Command command) {
		
		return (cmdCache.publish(command) != null) ? command : null;
	}
	
	public Command sendCommand(Command command, int expire) {
	
		return (cmdCache.publish(command) != null) ? command : null;
	}
	
	public SubscriptorManager subscribe(SubscriptorEventHandler subscriptorEventHandler) {
		
		return subscriptorCache.subscribe(subscriptorEventHandler);
	}
	
	public SubscriptorManager subscribe(BinarySubscriptorEventHandler binarySubscriptorEventHandler) {
		
		return subscriptorCache.subscribe(binarySubscriptorEventHandler);
	}
}
