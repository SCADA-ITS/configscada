package com.revenga.rits.back.equipment.manager.data;

import com.revenga.rits.back.data.core.util.ConfigProperty;

import lombok.Getter;

public abstract class ThreadDataGeneric implements Runnable{

	public static final long KEEP_ALIVE_DEBUG_MS = Long.parseLong(
			ConfigProperty.getInstance().getProperty("application.properties", "thread.keepAlive.trace"));
	
	protected boolean stop;

	@Getter
	protected boolean alive = true;
	
	protected ThreadDataGeneric() {
		
	}
	
	public void stop() {

		this.stop = true;
	}
}
