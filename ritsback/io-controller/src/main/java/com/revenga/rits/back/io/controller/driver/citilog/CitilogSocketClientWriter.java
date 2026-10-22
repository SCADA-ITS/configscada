package com.revenga.rits.back.io.controller.driver.citilog;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;

@Getter
@Log4j2
public class CitilogSocketClientWriter implements Runnable {

	CitilogSocketClient citilogSocketClient;
	BlockingQueue<String> queueWriter;
	
	private int txCount;
	
	boolean stop;
	boolean alive;
	
	public CitilogSocketClientWriter(CitilogSocketClient citilogSocketClient) {
		super();
		
		this.citilogSocketClient = citilogSocketClient;
		
		this.queueWriter = new LinkedBlockingQueue<>();
		
		this.txCount = 0;
		
		loadProperties();
	}
	
	private void loadProperties() {
		
		try {
			
			
		} catch (NumberFormatException e) {
			
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}
	}
	
	public void stop() {
		
		this.stop = true;
	}
	
	public void add(String xml) {
		
		if (this.queueWriter != null) {
			
			this.queueWriter.add(xml);
		}
	}
	
	@Override
	public void run() {
		
		log.info(Thread.currentThread().getName() + " is running...");
		
		this.alive = true;
		
		Socket socket = this.citilogSocketClient.getSocket();
			
		while (!this.stop) {
			
			try {
				
				if (!socket.isClosed()) {
					
					while (!this.queueWriter.isEmpty() && !this.stop) {
						
						try {
						
							String xml = this.queueWriter.poll();
							
							if (!StringUtils.isEmpty(xml)) {
								
								PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
								
								out.println(xml);
								
								log.debug("[ " + this.citilogSocketClient.getCitilogDriver().getIoControllerModule().getAlias() + "](" + (++this.txCount) + ") >> " + xml);
							}
						} 
						catch (IOException e) {
							
							log.error(e.getMessage());
							log.debug(ExceptionUtils.getStackTrace(e));
						}
					}
				}
			
				Thread.sleep(0, 1);
				
			} catch (InterruptedException e) {
	            
	        	log.error(e.getMessage());
				log.debug(ExceptionUtils.getStackTrace(e));
				Thread.currentThread().interrupt();
	        }
		}
		
		this.alive = false;
		
		log.info(Thread.currentThread().getName() + " is stopped");
	}
}

