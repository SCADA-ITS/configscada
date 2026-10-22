package com.revenga.rits.back.cache.core.service.impl;

import com.revenga.rits.back.cache.core.subscriptor.BinarySubscriptorEventHandler;
import com.revenga.rits.back.cache.core.subscriptor.SubscriptorEventHandler;
import com.revenga.rits.back.cache.core.subscriptor.SubscriptorEventHandler.Type;
import com.revenga.rits.back.cache.core.subscriptor.SubscriptorManager;

public class SubscriptorCache {

	/**
	 * 
	 * Method to subscribe to channel of elements
	 * 
	 * @param subscriptorEventHandler
	 * 
	 * @return Subscriptor runnable instance
	 */
	public SubscriptorManager subscribe(SubscriptorEventHandler subscriptorEventHandler) {
		
		String threadName = null;
		SubscriptorManager listenerRunnable = new SubscriptorManager(subscriptorEventHandler);
				
		if (subscriptorEventHandler.getType() == Type.NOTIFY_KEYSPACE_EVENTS) {
		
			threadName = "Thread-SubscriptorManager " + String.join(", ", subscriptorEventHandler.getPatterns());
		}
		else if (subscriptorEventHandler.getType() == Type.CHANNELS) {
			
			threadName = "Thread-SubscriptorManager " + String.join(", ", subscriptorEventHandler.getChannels());
		}
		
		Thread threadListener = new Thread(listenerRunnable, threadName);
		threadListener.start();
		
		return listenerRunnable;
	}
	
	public SubscriptorManager subscribe(BinarySubscriptorEventHandler binarySubscriptorEventHandler) {
		
		String threadName = null;
		SubscriptorManager binaryListenerRunnable = new SubscriptorManager(binarySubscriptorEventHandler);
				
		if (binarySubscriptorEventHandler.getType() == BinarySubscriptorEventHandler.Type.NOTIFY_KEYSPACE_EVENTS) {
		
			threadName = "Thread-BinarySubscriptorManager ";
			
			for (byte[] pattern : binarySubscriptorEventHandler.getPatterns()) {
				
				threadName += new String(pattern) + ",";
			}
			
		}
		else if (binarySubscriptorEventHandler.getType() == BinarySubscriptorEventHandler.Type.CHANNELS) {
			
			threadName = "Thread-BinarySubscriptorManager ";
			
			for (byte[] channel : binarySubscriptorEventHandler.getChannels()) {
				
				threadName += new String(channel) + ",";
			}
		}
		
		Thread threadBinaryListener = new Thread(binaryListenerRunnable, threadName);
		threadBinaryListener.start();
		
		return binaryListenerRunnable;
	}

	/**
	 * 
	 * Method to unsubscribe to channel
	 * 
	 * @param subscriptorManager
	 */
	public void unsubscribe(SubscriptorManager subscriptorManager) {

		subscriptorManager.stop();
	}

	
}
