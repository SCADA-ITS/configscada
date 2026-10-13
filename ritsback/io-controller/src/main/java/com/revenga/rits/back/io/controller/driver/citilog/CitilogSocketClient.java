package com.revenga.rits.back.io.controller.driver.citilog;

import java.io.IOException;
import java.net.Socket;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

import com.revenga.rits.back.data.core.model.IOControllerModule;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;

@Getter
@Log4j2
public class CitilogSocketClient implements Runnable {
	
	private static final Long TIMEOUT_SOCKET_MS = 10000L;
	private static final Long SOCKET_RECONNECT_MS = 30000L;
	
	CitilogDriver citilogDriver;
	boolean connected = false;
	
	Socket socket;
	CitilogSocketClientReader socketReader;
	CitilogSocketClientWriter socketWriter;
	
	boolean stop;
	boolean alive;
	
	
	public CitilogSocketClient(CitilogDriver citilogDriver) {
		
		this.citilogDriver = citilogDriver;
	}
	
	public void stop() {
		
		this.stop = true;
	}
	
	@Override
	public void run() {
		
		log.info(Thread.currentThread().getName() + " is running...");
		
		this.alive = true;
			
		while (!this.stop) {
			
			try {
				
				boolean socketDisconnected = (this.socket == null || !socket.isConnected() || socket.isClosed());
				boolean connectionReset = (this.socketReader != null && this.socketReader.isConnectionReset());
				
				if (socketDisconnected || connectionReset) {
					
					this.citilogDriver.disconnect();
					
					this.connected = false;
					
					this.reconnect();
				}
			
				Thread.sleep(TIMEOUT_SOCKET_MS);
				
			} catch (InterruptedException e) {
	            
	        	log.error(e.getMessage());
				log.debug(ExceptionUtils.getStackTrace(e));
				Thread.currentThread().interrupt();
	        }
		}
		
		this.alive = false;
		
		log.info(Thread.currentThread().getName() + " is stopped");
		
	}
	
	private void connect() {
		
		try {
			
			while (!this.connected) {
				
				try {
					
					this.socket = new Socket(citilogDriver.getHost(), citilogDriver.getPort());
						
					this.connected = socket.isConnected();
					
					if (this.connected) {
						
						log.info("Connection stablished to citilog server " + citilogDriver.getHost() + ":" + citilogDriver.getPort());
						log.info("Citilog client " + socket.getLocalSocketAddress().toString());
						
						IOControllerModule ioControllerModule = this.citilogDriver.getIoControllerModule();
						
						if (ioControllerModule != null) {
							
							socketReader = new CitilogSocketClientReader(this);
							socketWriter = new CitilogSocketClientWriter(this);
							
							(new Thread(socketReader, CitilogSocketClientReader.class.getSimpleName() + "_" + ioControllerModule.getAlias())).start();
							(new Thread(socketWriter, CitilogSocketClientWriter.class.getSimpleName() + "_" + ioControllerModule.getAlias())).start();
						}
					}
					
				} 
				catch (Exception e) {
					
					if (e instanceof IOException) {
						
						log.warn("Connection refused from citilog server " + citilogDriver.getHost() + ":" + citilogDriver.getPort());
						log.warn("Check citilog server status !!!");
					}
					else {
						
						log.warn(e.getMessage());
						log.debug(ExceptionUtils.getStackTrace(e));
					}
					
					this.connected = false;
					
					Thread.sleep(SOCKET_RECONNECT_MS);
				}
			}
			
		} 
		catch (InterruptedException e) {
			
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			Thread.currentThread().interrupt();
		}
	}
	
	private void disconnect() {
		
		try {
			
			if (this.connected) {
				
				this.socket.close();
				this.connected = false;
			}
			
			if (socketReader != null) {
				
				socketReader.stop();
				socketReader = null;
			}
			
			if (socketWriter != null) {
				
				socketWriter.stop();
				socketWriter = null;
			}
		} 
		catch (IOException e) {
			
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}
	}
	
	public void reconnect() {
		
		disconnect();
		connect();
	}
		
	public void send(String xml) {
		
		if (this.connected && this.socketWriter != null && !StringUtils.isEmpty(xml)) {
			
			socketWriter.add(xml);
		}
	}

	
}
