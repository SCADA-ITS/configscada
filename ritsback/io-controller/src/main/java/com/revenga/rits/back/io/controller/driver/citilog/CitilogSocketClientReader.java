package com.revenga.rits.back.io.controller.driver.citilog;

import java.io.IOException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;

@Getter
@Log4j2
public class CitilogSocketClientReader implements Runnable {

	private static final int SOCKET_BUFFER_SIZE = 0x100000;
	
	CitilogSocketClient citilogSocketClient;
	
	boolean stop;
	boolean alive;
	boolean connectionReset = false;
	
	private int rxCount;
	
	public CitilogSocketClientReader(CitilogSocketClient citilogSocketClient) {
		super();
		
		this.citilogSocketClient = citilogSocketClient;
		
		this.rxCount = 0;
		
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
	
	@Override
	public void run() {

		log.info(Thread.currentThread().getName() + " is running...");
		
		Socket socket = citilogSocketClient.getSocket();
		
		this.alive = true;
			
		while (!this.stop && !this.connectionReset) {
			
			try {
				
				if (!socket.isClosed()) {
					
					byte[] bFrame = new byte[SOCKET_BUFFER_SIZE];
					
					try {
					
						int length = socket.getInputStream().read(bFrame);
						
						if (length == -1) {
							
							throw new IOException("Socket reader EOF");
						}

						if (length > 0) {
							
							byte[] bXml = Arrays.copyOf(bFrame, length);
							
							String strXml = new String(bXml, StandardCharsets.UTF_8);
							
							if (!StringUtils.isEmpty(strXml)) {
								
								log.debug("[ " + this.getCitilogSocketClient().getCitilogDriver().getIoControllerModule().getAlias() + "](" + (++this.rxCount) + ") << " + strXml);
								
								citilogSocketClient.getCitilogDriver().receive(strXml);
							}
						}
						
			        } 
					catch (IOException e) {
						
			        	log.error(e.getMessage());
						log.debug(ExceptionUtils.getStackTrace(e));
						
						connectionReset = true;
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

