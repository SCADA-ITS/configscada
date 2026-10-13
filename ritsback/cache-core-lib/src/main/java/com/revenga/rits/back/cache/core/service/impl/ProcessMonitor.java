package com.revenga.rits.back.cache.core.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.Optional;
import java.util.Scanner;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.util.StringUtils;

import com.revenga.rits.back.cache.core.manager.RedisConnection;
import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.BasicElement;
import com.revenga.rits.back.data.core.model.ProcessInfo;
import com.sun.management.OperatingSystemMXBean;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class ProcessMonitor implements Runnable {

	boolean stop;
	boolean alive;
	DataCache dataCache;
	private static final String GIT_VERSION = "git.version";
	public static final int LINE_GIT_COMMIT = 1;
	public static final int LINE_GIT_AUTHOR = 2;
	public static final int LINE_GIT_DATE = 3;
	
	@Getter
	ProcessInfo processInfo;
	
	public ProcessMonitor(RedisConnection redisConnection) {
		
		super();
		
		this.stop = false;
		this.alive = false;
		
		dataCache = new DataCache(redisConnection);
		
		init();
	}
	
	private void init() {
		
		ProcessInfo currentProcessInfo = (ProcessInfo) getCurrentProcessInfo();
		
		processInfo = new ProcessInfo();
		processInfo.setMain(System.getProperty("sun.java.command"));
		
		if (currentProcessInfo == null) {
			
			processInfo.setNumRestarts(0);
			
		} else {
			
			processInfo.setNumRestarts(currentProcessInfo.getNumRestarts());
		}
		
		processInfo.setReady(false);
		
		processInfo.setPid(ProcessHandle.current().pid());
		
		Optional<String> commandLine = ProcessHandle.current().info().commandLine();
		
		if (commandLine.isPresent()) {
			
			processInfo.setCommandLine(commandLine.get());
		}
		
		//Fill Git fields
		String gitInfoStr = getGitInfo();
		int lineOffset = 0;
		
		if (!StringUtils.isEmpty(gitInfoStr)) {
			
			try (Scanner scanner = new Scanner(gitInfoStr)) {
				
				while (scanner.hasNextLine()) {
					
					lineOffset++;
					
					String line = scanner.nextLine().trim();
					
					if (!StringUtils.isEmpty(line)) {
						
						if (lineOffset == LINE_GIT_COMMIT) {
							
							processInfo.setGitCommit(line);
						}
						else if (lineOffset == LINE_GIT_AUTHOR) {
							
							processInfo.setGitAuthor(line);
						}
						else if (lineOffset == LINE_GIT_DATE) {
							
							processInfo.setGitDate(line);
						}
						else {

							processInfo.setGitMessage(line);
						}
					}
				}					
			}
		}
	}
	
	@Override
	public void run() {
		
		Double cpuAverageSum = 0.0;
		int timesMonitored = 1;
		
		Runtime rt = Runtime.getRuntime();
		OperatingSystemMXBean operatingSystemMXBean = (OperatingSystemMXBean) ManagementFactory
				.getOperatingSystemMXBean();
		
		ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
		
		this.alive = true;
		
		log.info(Thread.currentThread().getName() + " is running...");
		
		while (!stop) {
			
			try {

				processInfo.setHeapMax(rt.maxMemory());
				processInfo.setHeapSize(rt.totalMemory());
				processInfo.setHeapUsed(rt.totalMemory() - rt.freeMemory());
				processInfo.setCpuLoad(operatingSystemMXBean.getProcessCpuLoad() * 100);
				processInfo.setCpuTime(operatingSystemMXBean.getProcessCpuTime() / (int) Math.pow(10, 6));
				
				cpuAverageSum += processInfo.getCpuLoad();
				
				processInfo.setCpuAverage(cpuAverageSum / timesMonitored++);
				processInfo.setThreadsLive(threadMXBean.getThreadCount());
				
				if (!publish(processInfo)) {
					
					log.error("Error on write ProcessInfo: " + processInfo);
				}

				Thread.sleep(5000L);
				
	        } catch (InterruptedException e) {
	            
	        	log.error(e.getMessage());
				log.debug(ExceptionUtils.getStackTrace(e));
				Thread.currentThread().interrupt();
	        }
		}
		
		this.alive = false;
		
		log.info(Thread.currentThread().getName() + " is stopped");
	}
	
	
	private boolean publish(ProcessInfo processInfo) {
	
		return (dataCache.put(processInfo) != null);
	}
	
	private BasicElement getCurrentProcessInfo() {

		ProcessInfo processInfoPattern = new ProcessInfo(); 
			
		processInfoPattern.setMain(System.getProperty("sun.java.command"));
		
		Key keyProcessInfo = new Key(processInfoPattern);
			
		return dataCache.get(keyProcessInfo);
	}
	
	private String getGitInfo() {
		
		String gitInfoStr = null;
		
		InputStream is = this.getClass().getClassLoader().getResourceAsStream(GIT_VERSION);
		
		if (is != null) {
		
			try (BufferedReader buf = new BufferedReader(new InputStreamReader(is))) {
				
				String line = buf.readLine(); 
				StringBuilder sb = new StringBuilder();

				while(line != null) { 

					sb.append(line).append(System.lineSeparator()); 
					line = buf.readLine(); 
				} 
				
				gitInfoStr = sb.toString();
				
			} catch (IOException e) {
				
				log.error(e.getMessage());
				log.debug(ExceptionUtils.getStackTrace(e));
			}
		}
		
		return gitInfoStr;
	}
}
