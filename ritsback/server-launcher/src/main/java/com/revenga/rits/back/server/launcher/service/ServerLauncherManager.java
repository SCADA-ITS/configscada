package com.revenga.rits.back.server.launcher.service;

import java.time.Duration;
import java.time.Instant;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

import com.revenga.rits.back.adapter.lib.model.ServerLauncherAdapterV1;
import com.revenga.rits.back.adapter.lib.service.SessionAdapter;
import com.revenga.rits.back.server.launcher.service.data.DataLoader;
import com.revenga.rits.back.server.launcher.service.process.ProcessLauncher;
import com.revenga.rits.back.server.launcher.service.process.ProcessLauncherException;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Getter
public class ServerLauncherManager {

	private static ServerLauncherManager instance;
	
	private ProcessLauncher processLauncher;

	public static ServerLauncherManager getInstance() {

		if (instance == null) {

			instance = new ServerLauncherManager();
		}

		return instance;
	}

	private ServerLauncherManager() {
		
		super();
	}

	public void load(String xmlAdapter) {
		
		try {
			
			if (StringUtils.isEmpty(xmlAdapter)) {
				
				throw new NullPointerException ("xmlAdapter is null");
			}

			SessionAdapter sessionAdapter = new SessionAdapter();
			
			ServerLauncherAdapterV1 serverLauncherAdapterV1 = (ServerLauncherAdapterV1) sessionAdapter.getAdapter("adapters/server-launcher/" + xmlAdapter, ServerLauncherAdapterV1.class);
			
			if (serverLauncherAdapterV1 == null) {
				
				throw new NullPointerException ("Not found xml adapter: " + "adapters/server-launcher/" + xmlAdapter);
			}
			
			loadData();
				
			loadProcesses(serverLauncherAdapterV1);
			
		} catch (Exception e) {
			
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}
		
	}
	
	private void loadData() {
		
		DataLoader dataLoader = new DataLoader();
		
		dataLoader.load();
	}

	private void loadProcesses(ServerLauncherAdapterV1 serverLauncherAdapterV1) {
		
		try {
			
			String threadNameProcessLauncher = "Thread-ProcessLauncher";
			
			this.processLauncher = new ProcessLauncher(serverLauncherAdapterV1);
			
			(new Thread(this.processLauncher, threadNameProcessLauncher)).start();
			
		} catch (ProcessLauncherException e) {
			
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}
	}

	public void stop() {
		
		try {
			
			this.processLauncher.stop();
			
			Instant start;
			Duration timeElapsed;
			boolean stopped;
			start = Instant.now();
			
			do {
				
				stopped = !this.processLauncher.isAlive();
		
				Thread.sleep(1L);
				
				timeElapsed = Duration.between(start, Instant.now());
				
			} while (timeElapsed.toMillis() < 5000L && !stopped);
			
		} catch (InterruptedException e) {
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			Thread.currentThread().interrupt();
		}
	}
}
