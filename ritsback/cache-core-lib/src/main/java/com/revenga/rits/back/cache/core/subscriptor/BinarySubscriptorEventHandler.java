package com.revenga.rits.back.cache.core.subscriptor;

import java.util.ArrayList;
import java.util.List;

import com.revenga.rits.back.cache.core.manager.RedisConnection;
import com.revenga.rits.back.cache.core.model.GenericKey;

import lombok.Getter;
import redis.clients.jedis.BinaryJedisPubSub;

/**
 * 
 * SubscriptorEventHandler is an implementation of JedisPubSub 
 * to manage subscriptions and notifications to redis channels
 * 
 * @author alvaro
 *
 */
public abstract class BinarySubscriptorEventHandler extends BinaryJedisPubSub {

	GenericKey[] patternKeys;
	@Getter
	byte[][] channels;
	
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
	public BinarySubscriptorEventHandler(GenericKey... patternKeys) {
		super();
		this.type = Type.NOTIFY_KEYSPACE_EVENTS;
		this.patternKeys = patternKeys;
		this.counterMessage = 0;
	}
	
	public BinarySubscriptorEventHandler(byte[]...channels) {
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
	public byte[][] getPatterns() {

		byte[] keySpaceBin;
		List<byte[]> patternValues = new ArrayList<>();

		for (GenericKey key : patternKeys) {

			keySpaceBin = ("__keyspace@" + RedisConnection.DATABASE + "__:" + key.getValue()).getBytes();
			patternValues.add(keySpaceBin);
		}

		return patternValues.stream().toArray(byte[][]::new);
	}
	
	/**
	 *
	 * Callback implementation on receive message from channel
	 * 
	 */
	@Override
	public void onPMessage(byte[] pattern, byte[] channel, byte[] message) {
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
	public void onPSubscribe(byte[] pattern, int subscribedChannels) {
		// TODO Auto-generated method stub
		super.onPSubscribe(pattern, subscribedChannels);
	}
	
	/**
	 *
	 * Callback implementation for unsubscription event to channel
	 * 
	 */
	@Override
	public void onPUnsubscribe(byte[] pattern, int subscribedChannels) {
		// TODO Auto-generated method stub
		super.onPUnsubscribe(pattern, subscribedChannels);
	}
	
	@Override
	public void onMessage(byte[] channel, byte[] message) {
		// TODO Auto-generated method stub
		super.onMessage(channel, message);
	}

	@Override
	public void onSubscribe(byte[] channel, int subscribedChannels) {
		// TODO Auto-generated method stub
		super.onSubscribe(channel, subscribedChannels);
	}

	@Override
	public void onUnsubscribe(byte[] channel, int subscribedChannels) {
		// TODO Auto-generated method stub
		super.onUnsubscribe(channel, subscribedChannels);
	}
}
