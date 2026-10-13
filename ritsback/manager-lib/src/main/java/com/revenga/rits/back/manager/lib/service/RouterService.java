package com.revenga.rits.back.manager.lib.service;

import java.lang.reflect.InvocationTargetException;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revenga.rits.back.adapter.lib.model.ManagerAdapterV1.Executors.Executor;
import com.revenga.rits.back.adapter.lib.model.ManagerAdapterV1.Subscriptors.Subscriptor;
import com.revenga.rits.back.adapter.lib.service.SessionAdapter;
import com.revenga.rits.back.cache.core.manager.RedisConnection;
import com.revenga.rits.back.data.core.model.BasicElement;
import com.revenga.rits.back.data.core.model.command.Command;
import com.revenga.rits.back.data.core.util.ClassesUtil;
import com.revenga.rits.back.manager.lib.executor.GenericExecutor;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class RouterService implements Runnable {

	@Autowired
	ManagerService managerService;
	
	@Autowired
	ExecutorService executorService;
	
	BlockingQueue<Pair<Subscriptor, BasicElement>> queueElements;
	BlockingQueue<Pair<Subscriptor, Command>> queueCommands;
	
	Map<Executor, List<GenericExecutor>> executors = new HashMap<>();
		
	SessionAdapter sessionAdapter;
	
	Random rand;
	
	boolean stop;
	@Getter
	boolean alive;
	
	public RouterService() {
		
		super();
		
		this.queueElements = new LinkedBlockingQueue<>();
		this.queueCommands = new LinkedBlockingQueue<>();
		this.stop = false;
		this.alive = false;
		
		this.rand = new Random();
		
		sessionAdapter = new SessionAdapter(); 
	}
	
	public void stop() {
		
		stopExecutors();
		
		this.stop = true;
	}

	public void add(Subscriptor subcriptor, Object obj) {
		
		if (obj instanceof BasicElement) {
			
			this.queueElements.add(new MutablePair<>(subcriptor, (BasicElement) obj));
		}
		else if (obj instanceof Command) {
			
			this.queueCommands.add(new MutablePair<>(subcriptor, (Command) obj));
		}
	}

	@Override
	public void run() {
		
		startExecutors();
		
		new Thread("Check Executors Status") {
			
			public void run() {
				
				try {
					setProcessReady();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}.start();
		
		this.alive = true;
		
		log.info(Thread.currentThread().getName() + " is running...");
		
		while (!stop) {
			
			try {
				
				if (!CollectionUtils.isEmpty(queueElements)) {
					
					Pair<Subscriptor, BasicElement> pairBasicElement = this.queueElements.poll();
										
					if (pairBasicElement != null) {
						
						route(pairBasicElement.getLeft(), pairBasicElement.getRight());
					}
				}
				
				if (!CollectionUtils.isEmpty(queueCommands)) {
					
					Pair<Subscriptor, Command> pairCommand = this.queueCommands.poll();
										
					if (pairCommand != null) {
						
						route(pairCommand.getLeft(), pairCommand.getRight());
					}
				}
				
				Thread.sleep(1);
				
			} catch (InterruptedException e) {
				log.error(e.getMessage());
				log.debug(ExceptionUtils.getStackTrace(e));
				Thread.currentThread().interrupt();
			}
		}
		
		this.alive = false;
		
		log.info(Thread.currentThread().getName() + " is stopped");
	}
	
	private GenericExecutor getLessBusyExecutor(List<GenericExecutor> genericExecutors) {
		
		GenericExecutor genericExecutor = null;
		
		if (!CollectionUtils.isEmpty(genericExecutors)) {
			
			// 1st priority => Only one executor
			if (genericExecutors.size() == 1) {
				
				genericExecutor = genericExecutors.get(0);
			}
			else {
			
				// 2nd priority => Select first not working executor and queued is empty
				GenericExecutor genericExecutorNotWorking = genericExecutors.stream()
						.filter(e -> !e.isWorking() && e.getQueueItems().isEmpty()).findFirst().orElse(null);
				
				if (genericExecutorNotWorking != null) {
					
					genericExecutor = genericExecutorNotWorking; 
				}
				// 3rd priority => All executor are working. Select executor with the least queue size
				else {
					
					Comparator<GenericExecutor> compareBySize = (GenericExecutor o1, GenericExecutor o2) -> Integer
							.valueOf(o1.getQueueItems().size())
							.compareTo(Integer.valueOf(o2.getQueueItems().size()));
					
					Collections.sort(genericExecutors, compareBySize);
									
					genericExecutor = genericExecutors.get(0);
					
					Comparator<GenericExecutor> compareByThreadNum = (GenericExecutor o1, GenericExecutor o2) -> Integer
							.valueOf(o1.getThreadNum())
							.compareTo(Integer.valueOf(o2.getThreadNum()));
					
					Collections.sort(genericExecutors, compareByThreadNum);
				}
			}
		}
	
		return genericExecutor;
	}
	
	private void route(Subscriptor subscriptor, BasicElement basicElement) {
	
		List<Executor> execs = getExecutors(basicElement);
		
		if (!CollectionUtils.isEmpty(execs)) {
			
			for (Executor exec : execs) {
				
				if (exec.getSubscriptorsRef() != null && !CollectionUtils.isEmpty(exec.getSubscriptorsRef().getSubscriptorIdRef())) {
					
					for (Short subscriptorIdRef : exec.getSubscriptorsRef().getSubscriptorIdRef()) {
						
						if (subscriptorIdRef.equals(subscriptor.getId())) {
						
							List<GenericExecutor> genericExecutors = this.executors.get(exec);
							
							if (!CollectionUtils.isEmpty(genericExecutors)) {
									
								GenericExecutor genericExecutor = getLessBusyExecutor(genericExecutors);
								
								if (genericExecutor != null) {
								
									genericExecutor.add(basicElement);
								}
							}
						}
					}
				}
			}
		}
		else {
			
			log.error("Check xml adapter. Not found executor for subscriptor id = " + subscriptor.getId());
		}
	}
	
	private void route(Subscriptor subscriptor, Command command) {
		
		List<Executor> execs = getExecutors(command);
		
		if (!CollectionUtils.isEmpty(execs)) {
			
			for (Executor executor : execs) {
				
				if (executor.getSubscriptorsRef() != null && !CollectionUtils.isEmpty(executor.getSubscriptorsRef().getSubscriptorIdRef())) {
					
					for (Short subscriptorIdRef : executor.getSubscriptorsRef().getSubscriptorIdRef()) {
						
						if (subscriptorIdRef.equals(subscriptor.getId())) {
				
							List<GenericExecutor> genericExecutors = this.executors.get(executor);
							
							if (!CollectionUtils.isEmpty(genericExecutors)) {
								
								GenericExecutor genericExecutor = getLessBusyExecutor(genericExecutors);
								
								if (genericExecutor != null) {
								
									genericExecutor.add(command);
								}
							}
						}
					}
				}
			}
		}
		else {
			
			log.error("Check xml adapter. Not found executor for subscriptor id = " + subscriptor.getId());
		}
	}
	
	private void startExecutors() {
		
		try {
			
			for (Executor executor : getExecutors()) {
			
				String executorClassName = executor.getManagerClass();
							
				Class<?> managerClass = Class
						.forName(executorClassName);
				
				short threads = (executor.getThreads() != null) ? executor.getThreads() : 1;  
				
				for (short thread = 0; thread < threads; thread++) {
					
					GenericExecutor genericExecutor;

					genericExecutor = (GenericExecutor) managerClass
								.getDeclaredConstructor(Executor.class, short.class)
								.newInstance(executor, thread);

					String threadNameGenericManager = managerClass.getSimpleName() + "_" + "Thread-" + (thread + 1); 
					
					(new Thread(genericExecutor, threadNameGenericManager)).start();
					
					List<GenericExecutor> genericExecutors = executors.get(executor);
					
					if (genericExecutors == null) {
						
						genericExecutors = new ArrayList<>();
						
						executors.put(executor, genericExecutors);
					}

					genericExecutors.add(genericExecutor);
				}
			}

		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException | NoSuchMethodException | SecurityException e) {
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}
	}
	
	private void stopExecutors() {
				
		try {
			
			for (Entry<Executor, List<GenericExecutor>> entry : executors.entrySet()) {
				
				List<GenericExecutor> genericExecutors = entry.getValue();
				
				if (!CollectionUtils.isEmpty(genericExecutors)) {
					
					for (GenericExecutor genericExecutor : genericExecutors) {
				
						genericExecutor.stop();
					}
				}
			}
			
			Instant start;
			Duration timeElapsed;
			boolean stopped;
			start = Instant.now();
			
			do {
				
				stopped = true;
			
				for (Entry<Executor, List<GenericExecutor>> entry : executors.entrySet()) {
					
					List<GenericExecutor> genericExecutors = entry.getValue();
					
					if (!CollectionUtils.isEmpty(genericExecutors)) {
						
						for (GenericExecutor genericExecutor : genericExecutors) {
					
							stopped &= !genericExecutor.isAlive();
							
							if (!stopped) break;
						}
					}
					
					if (!stopped) break;
				}
				
				Thread.sleep(1L);
				
				timeElapsed = Duration.between(start, Instant.now());
				
			} while (timeElapsed.toMillis() < 5000L && !stopped);
			
		} catch (InterruptedException e) {
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			Thread.currentThread().interrupt();
		}
	}
	
	private List<Executor> getExecutors() {
		
		return managerService.getManagerModel().getManagerAdapterV1().getExecutors().getExecutor();
	}
	
	private List<Executor> getExecutors(BasicElement basicElement) {
		
		List<Executor> execs = getExecutors();
		List<Executor> returnExecutors = new ArrayList<>();
		
		for (Executor exec : execs) {
			
			if (exec.getSubscriptorsRef() != null) {
			
				List<Short> subscriptorRefs = exec.getSubscriptorsRef().getSubscriptorIdRef();
				
				if (!CollectionUtils.isEmpty(subscriptorRefs)) {
				
					for (Short subscriptorRef : subscriptorRefs) {
						
						Class<?> clazz = getClass(subscriptorRef, BasicElement.class);
						
						if (basicElement.getClass() == clazz) {
							
							returnExecutors.add(exec);		
						}
					}
				}
			}
		}
		
		return returnExecutors;
	}
	
	private List<Executor> getExecutors(Command command) {
		
		List<Executor> execs = getExecutors();
		List<Executor> returnExecutors = new ArrayList<>();
		
		for (Executor exec : execs) {
			
			if (exec.getSubscriptorsRef() != null) {
			
				List<Short> subscriptorRefs = exec.getSubscriptorsRef().getSubscriptorIdRef();
				
				if (!CollectionUtils.isEmpty(subscriptorRefs)) {
				
					for (Short subscriptorRef : subscriptorRefs) {
						
						Class<?> clazz = getClass(subscriptorRef, Command.class);
						
						if (command.getClass() == clazz) {
							
							returnExecutors.add(exec);		
						}
					}
				}
			}
		}
		
		return returnExecutors;
	}

	private void setProcessReady() throws InterruptedException {

		for (Entry<Executor, List<GenericExecutor>> entry : executors.entrySet()) {

			List<GenericExecutor> genericExecutors = entry.getValue();

			if (!CollectionUtils.isEmpty(genericExecutors)) {

				for (GenericExecutor genericExecutor : genericExecutors) {

					boolean localStop = false;
					
					while(!localStop) {
						
						if (genericExecutor.isReady()) {
							
							localStop=true;
						}
						
						Thread.sleep(10L);
					}
				}
			}
		}
		
		RedisConnection redisConnection = RedisConnection.getInstance();
		redisConnection.getProcessMonitor().getProcessInfo().setReady(true);
	}
	
	private Class<?> getClass(Short subscriptorRef, Class<?> clazzRef) {
		
		List<Subscriptor> subscriptors;
		Class<?> clazz = null;
		
		if (managerService.getManagerModel().getManagerAdapterV1().getSubscriptors() != null) {
		
			subscriptors = managerService.getManagerModel().getManagerAdapterV1().getSubscriptors().getSubscriptor();
			
			for (Subscriptor subscriptor : subscriptors) {
				
				if (subscriptor.getId() == subscriptorRef) {
						
					try {
						
						clazz =  ClassesUtil.getModelClassBySimpleClassName(subscriptor.getEntity());
					
					} catch (ClassNotFoundException e) {

					}
					
					if (clazz != null) {
						
						break;
					}
				}
			}
		}
		
		return clazz;
	}
}
