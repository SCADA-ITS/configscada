package com.revenga.rits.back.cache.core.subscriptor;

import java.util.ArrayList;
import java.util.List;

import com.revenga.rits.back.cache.core.manager.RedisConnection;
import com.revenga.rits.back.cache.core.model.GenericKey;

import lombok.Getter;
import redis.clients.jedis.JedisPubSub;

/**
 * 
 * SubscriptorEventHandler is an implementation of JedisPubSub 
 * to manage subscriptions and notifications to redis channels
 * 
 * @author alvaro
 *
 */
public abstract class SubscriptorEventHandler extends JedisPubSub {

	GenericKey[] patternKeys;
	@Getter
	String[] channels;
	
	public enum Type { NOTIFY_KEYSPACE_EVENTS, CHANNELS }
	
	@Getter
	Type type;
	
	@Getter
	Integer counterMessage;

	/**
	 * 
	 * SubscriptorEventHandler constructor 
	 * 
	 * @param patternKeys array with key patterns
	 */
	public SubscriptorEventHandler(GenericKey... patternKeys) {
		super();
		this.type = Type.NOTIFY_KEYSPACE_EVENTS;
		this.patternKeys = patternKeys;
		this.counterMessage = 0;
	}
	
	public SubscriptorEventHandler(String...channels) {
		super();
		this.type = Type.CHANNELS;
		this.channels = channels;
		this.counterMessage = 0;
	}

	/**
	 * 
	 * Get string array of patterns
	 * 
	 * @return String array
	 */
	public String[] getPatterns() {

		String keySpaceStr = "";
		List<String> patternValues = new ArrayList<String>();

		for (GenericKey key : patternKeys) {

			keySpaceStr = "__keyspace@" + RedisConnection.DATABASE + "__:" + key.getValue();
			patternValues.add(keySpaceStr);
		}

		return patternValues.stream().toArray(String[]::new);
	}
	
	/**
	 *
	 * Callback implementation on receive message from channel
	 * 
	 */
	@Override
	public void onPMessage(String pattern, String channel, String message) {
		// TODO Auto-generated method stub
		super.onPMessage(pattern, channel, message);
		
		counterMessage++;
	}
	
	/**
	 *
	 * Callback implementation for subcription event to channel
	 * 
	 */
	@Override
	public void onPSubscribe(String pattern, int subscribedChannels) {
		// TODO Auto-generated method stub
		super.onPSubscribe(pattern, subscribedChannels);
	}
	
	/**
	 *
	 * Callback implementation for unsubscription event to channel
	 * 
	 */
	@Override
	public void onPUnsubscribe(String pattern, int subscribedChannels) {
		// TODO Auto-generated method stub
		super.onPUnsubscribe(pattern, subscribedChannels);
	}
	
	@Override
	public void onMessage(String channel, String message) {
		// TODO Auto-generated method stub
		super.onMessage(channel, message);
	}

	@Override
	public void onSubscribe(String channel, int subscribedChannels) {
		// TODO Auto-generated method stub
		super.onSubscribe(channel, subscribedChannels);
	}

	@Override
	public void onUnsubscribe(String channel, int subscribedChannels) {
		// TODO Auto-generated method stub
		super.onUnsubscribe(channel, subscribedChannels);
	}
}
