package com.revenga.rits.back.manager.lib.executor;

import java.lang.reflect.Field;
import java.time.Duration;
import java.time.Instant;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

import com.revenga.rits.back.adapter.lib.model.ManagerAdapterV1.Executors.Executor;
import com.revenga.rits.back.data.core.model.BasicElement;
import com.revenga.rits.back.data.core.model.command.Command;
import com.revenga.rits.back.data.core.util.ClassesUtil;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;

@Getter
@Log4j2
public abstract class GenericExecutor implements Runnable {
	
	protected BlockingQueue<Object> queueItems;
	protected Queue<Object> queuePreparedMessages;
	protected Executor executor;
	protected Class<?> threadRedirectCriteriaClass;
	protected Field threadRedirectCriteriaField;
	
	protected boolean working;
	protected boolean stop;
	protected boolean alive;
	protected short threadNum;
	protected boolean ready;
	
	public GenericExecutor(Executor executor, short threadNum) {
		super();
		
		this.executor = executor;
		this.queueItems = new LinkedBlockingQueue<>();
		this.threadNum = threadNum;
		this.queuePreparedMessages = new LinkedList<>();
		this.ready=false;
		
		loadThreadRedirectCriteria();
	}
	
	private void loadThreadRedirectCriteria() {
		
		String threadRedirectCriteria = executor.getThreadRedirectCriteria();
		
		try {
			
			if (!StringUtils.isEmpty(threadRedirectCriteria)) {
			
				String[] threadRedirectCriteriaParts = threadRedirectCriteria.split("\\.");
				
				if (ArrayUtils.getLength(threadRedirectCriteriaParts) == 2) {
					
					threadRedirectCriteriaClass = ClassesUtil.getModelClassBySimpleClassName(threadRedirectCriteriaParts[0]);
					
					if (threadRedirectCriteriaClass != null) {
						
						threadRedirectCriteriaField = ClassesUtil.getField(threadRedirectCriteriaClass, threadRedirectCriteriaParts[1]);
						
						threadRedirectCriteriaField.setAccessible(true);
					}
				}
			}
		} catch (ClassNotFoundException e) {
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}
	}
	
	public boolean isCriteriaValue(Object obj, int totalThreads) {
		
		boolean res = false;
		
		try {
			
			if (threadRedirectCriteriaField != null) {
				
				Object objValue = threadRedirectCriteriaField.get(obj);
				
				if (objValue instanceof Long) {
					
					Long longValue = (Long) objValue;
					
					res = ((longValue.longValue() % totalThreads) == threadNum);
				}
			}	
			
		} catch (IllegalArgumentException | IllegalAccessException e) {
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}
		
		return res;
	}
	
	public Boolean isCriteriaValue(Long refId, int totalThreads) {
		
		return ((refId % totalThreads) == threadNum);	
	}
	
	public void stop() {
		
		this.stop = true;
	}
	
	public void add(Object queueItem) {
		
		this.queueItems.add(queueItem);
	}
	
	public void run() {
		
		Instant start;
		Instant end;
		Duration timeElapsed;
		Long mergeWaitTime = null;
		
		this.alive = true;
		this.working = false;
		
		log.info(Thread.currentThread().getName() + " is running...");
		
		mergeWaitTime = executor.getMergeWaitTime();
		
		waitToStart();
		
		initialize();
		
		if (executor.isCustomReadyExecutor() == null || !executor.isCustomReadyExecutor()) {
			
			this.ready = true;
		}
			
		while (!stop) {
			
			try {
	            	
				start = Instant.now();		
				
				do {
					
					Object queueItem = queueItems.poll();
					
					if (queueItem != null) {
						
						this.working = true;
						
						String className = queueItem.getClass().getSimpleName();
						Long dequeuedTime = 0L;
						Long createdAt = 0L;
						
						if (queueItem instanceof Command) {

							createdAt = ((Command) queueItem).getCreatedAt();
							
							dequeuedTime = Duration.between(Instant.ofEpochMilli(createdAt), Instant.now()).toMillis();
							
						} else if (queueItem instanceof BasicElement) {
							
							createdAt = ((BasicElement) queueItem).getCreatedAt();

							dequeuedTime = Duration.between(Instant.ofEpochMilli(createdAt), Instant.now()).toMillis();
						}
						
						log.debug(className + " dequeued in " + dequeuedTime + " ms. Preparing...");

						Instant instant = Instant.now();
						
						prepare(queueItem);
						
						log.debug(className + " prepared in " + Duration.between(instant, Instant.now()).toMillis()
								+ " ms. Total time to process item from queue = "
								+ Duration.between(Instant.ofEpochMilli(createdAt), Instant.now()).toMillis()
								+ " ms. Queue size to be processed = " + queueItems.size() + " items.");
					}
					
					Thread.sleep(0, 1);
					
					end = Instant.now();
					timeElapsed = Duration.between(start, end);
					
				} while ((timeElapsed.toMillis() < mergeWaitTime) && !stop);
				
				if (!stop && !queuePreparedMessages.isEmpty()) {
					
					publish();
				}
				
				evaluate();
				
				this.working = false;
				
				Thread.sleep(0, 1);
				
	        } catch (InterruptedException e) {
	            
	        	log.error(e.getMessage());
				log.debug(ExceptionUtils.getStackTrace(e));
				Thread.currentThread().interrupt();
	        }
		}
		
		end();
		
		this.alive = false;
		
		log.info(Thread.currentThread().getName() + " is stopped");
	}
	
	private void waitToStart() {

		Long timeToStartSecs = null;
		
		timeToStartSecs = executor.getTimeToStartS();
		
		if (timeToStartSecs != null && timeToStartSecs > 0) {
			
			try {
				
				log.info("Waiting " + timeToStartSecs + " secs. to start " + Thread.currentThread().getName());
				
				Thread.sleep(timeToStartSecs * 1000);
				
			} catch (InterruptedException e) {
				
				log.error(e.getMessage());
				log.debug(ExceptionUtils.getStackTrace(e));
				Thread.currentThread().interrupt();
			}
		}
	}
	
	public String getParamValue(String param) {
		
		if (executor != null && executor.getExecutorParams() != null) {
			
			List<String> paramValues = executor.getExecutorParams().getExecutorParamValue();
			
			if (!CollectionUtils.isEmpty(paramValues)) {
				
				for (String paramValue : paramValues) {
					
					if (paramValue.contains("=")) {
						
						int idx = paramValue.indexOf('=');
						String key = paramValue.substring(0, idx);
						
						if (key.equals(param)) {
							
							String value = paramValue.substring(idx + 1, paramValue.length());;
							
							return value; 
						}
					}
				}
			}
		}
		
		return null;
	}
		
	protected abstract Object prepare(Object queueItem);
	
	protected abstract Object publish();
	
	protected abstract void initialize();
	
	protected abstract void evaluate();
	
	protected abstract void end();
}
