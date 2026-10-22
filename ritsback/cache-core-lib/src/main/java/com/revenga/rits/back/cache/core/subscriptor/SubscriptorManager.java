package com.revenga.rits.back.cache.core.subscriptor;

import org.apache.commons.lang3.exception.ExceptionUtils;

import com.revenga.rits.back.cache.core.manager.RedisConnection;

import lombok.extern.log4j.Log4j2;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisConnectionException;

/**
 * 
 * Subscriptor runnable class. This class handles subscriptions through SubscriptorEventHandler 
 * in which we will implement the callbacks.
 * 
 * @author alvaro
 *
 */
@Log4j2
public class SubscriptorManager implements Runnable {

	SubscriptorEventHandler listenerEventHandler = null;
	BinarySubscriptorEventHandler binaryListenerEventHandler = null;
	
	boolean stop;
	
	public SubscriptorManager(SubscriptorEventHandler listenerEventHandler) {
		super();
		
		this.stop = false;
		
		this.listenerEventHandler = listenerEventHandler;
	}
	
	public SubscriptorManager(BinarySubscriptorEventHandler binaryListenerEventHandler) {
		super();
		
		this.stop = false;
		
		this.binaryListenerEventHandler = binaryListenerEventHandler;
	}

	/**
	 * 
	 * Run Subscription thread
	 * 
	 */
	@Override
	public void run() {

		int reconnectionAttempts = 0;
		
		while (!this.stop) {
		
			try (Jedis jedis = RedisConnection.getInstance().getConnectionPool().getResource()) {

				if (jedis.isConnected()) {
					
					reconnectionAttempts = 0;
					
					if (listenerEventHandler != null) {
					
						if (listenerEventHandler.getType() == SubscriptorEventHandler.Type.NOTIFY_KEYSPACE_EVENTS) {
							
							jedis.configSet("notify-keyspace-events", "KEA");
							jedis.psubscribe(listenerEventHandler, listenerEventHandler.getPatterns());
						}
						else if (listenerEventHandler.getType() == SubscriptorEventHandler.Type.CHANNELS) {
							
							jedis.subscribe(listenerEventHandler, listenerEventHandler.getChannels());
						}
					}
					else if (binaryListenerEventHandler != null) {

						if (binaryListenerEventHandler.getType() == BinarySubscriptorEventHandler.Type.NOTIFY_KEYSPACE_EVENTS) {
							
							jedis.configSet("notify-keyspace-events", "KEA");
							jedis.psubscribe(binaryListenerEventHandler,binaryListenerEventHandler.getPatterns());
						}
						else if (binaryListenerEventHandler.getType() == BinarySubscriptorEventHandler.Type.CHANNELS) {
							
							jedis.subscribe(binaryListenerEventHandler, binaryListenerEventHandler.getChannels());
						}
					}
				}
				
			} catch (JedisConnectionException e) {
				
				if (reconnectionAttempts++ < RedisConnection.RECONNECTION_ATTEMPTS) {
				
					log.warn(e.getMessage());
					log.warn("Trying (" + reconnectionAttempts + "/" + RedisConnection.RECONNECTION_ATTEMPTS
							+ ") to reconnect to " + RedisConnection.HOST + ":" + RedisConnection.PORT + " ...");
					
					try {
						
						Thread.sleep(RedisConnection.RECONNECTION_TIMEOUT_MS);
						
					} catch (InterruptedException e1) {
						
						log.error(e.getMessage());
						log.debug(ExceptionUtils.getStackTrace(e));
						Thread.currentThread().interrupt();
					}
				}
				else {
					
					log.error(e.getMessage());
					log.error("Exceeded attempts to reconnect to " + RedisConnection.HOST + ":" + RedisConnection.PORT + " ...");
					log.debug(ExceptionUtils.getStackTrace(e));
					
					this.stop = true;
				}
				
			} catch (Exception e) {
				
				log.error(e.getMessage());
				log.debug(ExceptionUtils.getStackTrace(e));
				
				this.stop = true;
			}
		}
	}
	
	/**
	 * 
	 * Stop Subscription thread
	 * 
	 */
	public void stop() {
		
		if (listenerEventHandler != null) {
		
			listenerEventHandler.punsubscribe();
		}
		else if (binaryListenerEventHandler != null) {
			
			binaryListenerEventHandler.punsubscribe();
		}
		
		this.stop = true;
	}

}
