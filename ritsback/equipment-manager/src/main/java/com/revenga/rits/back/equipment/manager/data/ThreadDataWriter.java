package com.revenga.rits.back.equipment.manager.data;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import com.revenga.rits.back.cache.core.manager.RedisConnection;
import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.cache.core.service.impl.DataCache;
import com.revenga.rits.back.data.core.model.Alarm;
import com.revenga.rits.back.data.core.model.AlarmFiltered;
import com.revenga.rits.back.data.core.model.BasicElement;
import com.revenga.rits.back.data.core.model.ElementState;
import com.revenga.rits.back.data.core.model.ElementValue;
import com.revenga.rits.back.data.core.util.ConfigProperty;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class ThreadDataWriter extends ThreadDataGeneric {

	public static final long MAX_ITEMS = Long.parseLong(
			ConfigProperty.getInstance().getProperty("application.properties", "thread.dataWriter.max_items"));

	public static final long MAX_TIME_MS = Long.parseLong(
			ConfigProperty.getInstance().getProperty("application.properties", "thread.dataWriter.max_time_ms"));

	public static final boolean WRITE_PARALLEL = Boolean.parseBoolean(
			ConfigProperty.getInstance().getProperty("application.properties", "thread.dataWriter.write_parallel"));

	public enum Action {
		PUT, DEL
	}

	private final Class<?>[] classesAllowed = new Class<?>[] { ElementState.class, ElementValue.class, Alarm.class,
			AlarmFiltered.class };

	@Getter
	private boolean working = false;

	@Getter
	private long queued = 0;

	@Getter
	private long dequeued = 0;

	@Getter
	private long persisted = 0;

	@Getter
	private long deleted = 0;

	@Getter
	private long omitted = 0;

	private DataCache dataCache;
	private ConcurrentLinkedQueue<Pair<BasicElement, Action>> queue;

	private final Object lock = new Object();

	private ExecutorService executor;

	public ThreadDataWriter() {

		super();

		this.dataCache = new DataCache(RedisConnection.getInstance());
		this.queue = new ConcurrentLinkedQueue<>();

		this.executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
	}

	public void add(BasicElement be, Action action) {

		if (Arrays.stream(classesAllowed).anyMatch(be.getClass()::equals)) {

			if (be != null) {

				this.queue.add(new ImmutablePair<>(be, action));
			}

			incQueued();
		} else {

			log.warn("Class type " + be.getClass().getSimpleName() + " not allowed to write redis");
		}
	}

	public int size() {

		return this.queue.size();
	}

	private synchronized void incPersisted(long persisted) {

		this.persisted += persisted;
	}

	private synchronized void incQueued() {

		this.queued++;
	}

	@Override
	public void run() {

		Map<String, Pair<BasicElement, Action>> basicElementsToPersist = new HashMap<>();
		Map<String, BasicElement> basicElementsToPut = new HashMap<>();
		Map<String, BasicElement> basicElementsToDel = new HashMap<>();

		Instant instantKeepAlive = Instant.now();
		this.alive = true;

		log.info(Thread.currentThread().getName() + " is running...");

		long lastProcessedTime = System.currentTimeMillis();
		int processedElements = 0;

		long prevQueued = this.queued;
		long prevDequeued = this.dequeued;
		long prevPersisted = this.persisted;

		synchronized (lock) {

			try {

				while (!stop) {

					long diffDuration = Duration.between(instantKeepAlive, Instant.now()).toMillis();

					if (diffDuration > KEEP_ALIVE_DEBUG_MS) {

						instantKeepAlive = Instant.now();

						log.debug(String.format(
								"%s is alive. [Queued: %d (%d val/s); Dequeued: %d (%.2f%%) (%d val/s); Persist: %d (%d val/s); Omitted: %d; Deleted: %d]",
								Thread.currentThread().getName(), this.queued,
								((this.queued - prevQueued) / (diffDuration / 1000)), this.dequeued,
								this.dequeued > 0 && this.queued > 0
										? ((float) (this.dequeued * 100) / (float) this.queued)
										: 0,
								this.dequeued > 0 ? ((this.dequeued - prevDequeued) / (diffDuration / 1000)) : 0,
								this.persisted,
								this.persisted > 0 ? ((this.persisted - prevPersisted) / (diffDuration / 1000)) : 0,
								this.omitted, this.deleted));

						prevQueued = this.queued;
						prevDequeued = this.dequeued;
						prevPersisted = this.persisted;
					}

					long currentTime = System.currentTimeMillis();

					if (currentTime - lastProcessedTime >= MAX_TIME_MS) {

						processedElements = 0;
						basicElementsToPersist.clear();
						basicElementsToPut.clear();
						basicElementsToDel.clear();

						Pair<BasicElement, Action> beAction = queue.poll();

						while (beAction != null) {

							String keyStr = (new Key(beAction.getLeft())).getSimpleValue();

							if (basicElementsToPersist.containsKey(keyStr)) {

								this.omitted++;

							}

							basicElementsToPersist.put(keyStr, beAction);
							this.dequeued++;

							if (++processedElements >= MAX_ITEMS) {

								break;
							}

							beAction = queue.poll();
						}

						if (!MapUtils.isEmpty(basicElementsToPersist)) {

							for (Entry<String, Pair<BasicElement, Action>> entry : basicElementsToPersist.entrySet()) {

								beAction = entry.getValue();
								String keyStr = entry.getKey();
								BasicElement be = beAction.getLeft();
								Action action = beAction.getRight();

								if (Action.PUT.equals(action)) {

									basicElementsToPut.put(keyStr, be);
									
								} else if (Action.DEL.equals(action)) {
									
									basicElementsToDel.put(keyStr, be);
								}
							}
						}

						if (!MapUtils.isEmpty(basicElementsToPut)) {

							persist(basicElementsToPut, Action.PUT);

							basicElementsToPut.clear();
						}

						if (!MapUtils.isEmpty(basicElementsToDel)) {

							persist(basicElementsToDel, Action.DEL);

							basicElementsToDel.clear();
						}

						lastProcessedTime = currentTime;

					} else {

						lock.wait(MAX_TIME_MS - (currentTime - lastProcessedTime));
					}
				}
			} catch (InterruptedException e) {

				log.error(e.getMessage());
				log.debug(ExceptionUtils.getStackTrace(e));
				Thread.currentThread().interrupt();
			}
		}

		this.alive = false;

		log.info(Thread.currentThread().getName() + " is stopped");
	}

//	@Override
//	public void run() {
//
//		Map<String, BasicElement> basicElementsToPersist = new HashMap<>();
//
//		this.alive = true;
//
//		log.info(Thread.currentThread().getName() + " is running...");
//		
//		Instant instantKeepAlive = Instant.now();
//		
//		long prevQueued = this.queued;
//		long prevDequeued = this.dequeued;
//		long prevPersisted = this.persisted;
//
//		while (!stop) {
//
//			try {
//				
//				long diffDuration = Duration.between(instantKeepAlive, Instant.now()).toMillis(); 
//				
//				if (diffDuration > KEEP_ALIVE_DEBUG_MS) {
//				
//					instantKeepAlive = Instant.now();
//					
//					log.debug(String.format("%s is alive. [Queued: %d (%d val/s); Dequeued: %d (%.2f%%) (%d val/s); Persist: %d (%d val/s); Omitted: %d; Deleted: %d]",
//							Thread.currentThread().getName(), 
//							this.queued, ((this.queued - prevQueued) / (diffDuration / 1000)), 
//							this.dequeued, 
//							this.dequeued > 0 && this.queued > 0 ? ((float)(this.dequeued * 100) / (float)this.queued) : 0, 
//							this.dequeued > 0 ? ((this.dequeued - prevDequeued) / (diffDuration / 1000)) : 0, 
//							this.persisted, 
//							this.persisted > 0 ? ((this.persisted - prevPersisted) / (diffDuration / 1000)) : 0, 
//							this.omitted, this.deleted));				
//					
//					prevQueued = this.queued;
//					prevDequeued = this.dequeued;
//					prevPersisted = this.persisted;
//				}
//
//				this.working = !this.queue.isEmpty();
//				Instant instantIni = Instant.now();
//				Integer itemsProcessed = 0;
//				Action currentAction = null;
//				
//				while (!this.queue.isEmpty()
//						&& Duration.between(instantIni, Instant.now()).toMillis() < MAX_TIME_MS
//						&& itemsProcessed++ < MAX_ITEMS) {
//					
//					this.working = true;
//
//					Pair<BasicElement, Action> beAction = this.queue.poll();
//					
//					if (beAction != null) {
//						
//						this.dequeued++;
//
//						BasicElement be = beAction.getLeft();
//						Action action = beAction.getRight();
//
//						if (action != null && be != null) {
//
//							if (currentAction != null && !currentAction.equals(action)) {
//
//								persist(basicElementsToPersist, currentAction);
//								basicElementsToPersist.clear();
//							}
//
//							currentAction = action;
//							
//							String keyStr = (new Key(be)).getSimpleValue();
//							
//							if (basicElementsToPersist.containsKey(keyStr)) {
//								
//								this.omitted++;
//								
//								log.debug("Ommited " + currentAction.toString() + " for " + keyStr);
//							}
//							
//							basicElementsToPersist.put(keyStr, be);
//						}
//					}
//					
//					Thread.sleep(0, 1);
//				}
//
//				if (!MapUtils.isEmpty(basicElementsToPersist)) {
//					
//					persist(basicElementsToPersist, currentAction);
//
//					basicElementsToPersist.clear();
//				}
//				
//				Thread.sleep(0, 1);
//
//			} catch (InterruptedException e) {
//				log.error(e.getMessage());
//				log.debug(ExceptionUtils.getStackTrace(e));
//				Thread.currentThread().interrupt();
//			}
//		}
//
//		this.alive = false;
//
//		log.info(Thread.currentThread().getName() + " is stopped");
//
//	}

	private void persist(Map<String, BasicElement> basicElementsToPersist, Action action) {

		if (action != null && !MapUtils.isEmpty(basicElementsToPersist)) {

			List<BasicElement> bes = basicElementsToPersist.values().stream().collect(Collectors.toList());

			if (action.equals(Action.PUT)) {

				if (WRITE_PARALLEL) {

					parallelPersist(bes);
				} else {

					this.dataCache.puts(bes.stream().toArray(BasicElement[]::new));

					incPersisted(bes.size());
				}

			} else if (action.equals(Action.DEL)) {

				bes.forEach(be -> this.dataCache.del(new Key(be)));

				deleted += bes.size();
			}
		}
	}

	private void parallelPersist(List<BasicElement> bes) {

		int availableProcessors = Runtime.getRuntime().availableProcessors();

		Map<Integer, List<BasicElement>> partitions = new HashMap<>();

		for (int i = 0; i < bes.size(); i++) {

			int partitionIdx = i % availableProcessors;

			List<BasicElement> partition = partitions.computeIfAbsent(partitionIdx, k -> new ArrayList<>());

			// BasicElement beCloned = (BasicElement)
			// CacheModelHelper.cloneBasicElementForPut(bes.get(i));

			partition.add(bes.get(i));
		}

		List<Future<?>> futures = new ArrayList<>();

		for (Map.Entry<Integer, List<BasicElement>> partitionEntry : partitions.entrySet()) {

			List<BasicElement> partition = partitionEntry.getValue();

			Runnable task = () -> {

				this.dataCache.puts(partition.stream().toArray(BasicElement[]::new));

				incPersisted(partition.size());
			};

			Future<?> f = executor.submit(task);

			futures.add(f);
		}

		// A) Await all runnables to be done (blocking)
		for (Future<?> future : futures) {

			try {

				future.get(); // get will block until the future is done

			} catch (InterruptedException e) {

				log.error(e.getMessage());
				log.debug(ExceptionUtils.getStackTrace(e));
				Thread.currentThread().interrupt();

			} catch (ExecutionException e) {

				log.error(e.getMessage());
				log.debug(ExceptionUtils.getStackTrace(e));
			}
		}
	}
}
