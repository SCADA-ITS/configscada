package com.revenga.rits.back.equipment.manager.service;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.apache.commons.lang3.exception.ExceptionUtils;

import com.revenga.rits.back.data.core.model.command.Command;

import lombok.extern.log4j.Log4j2;

@Log4j2
public abstract class ThreadSendCommand implements Runnable {

	ConcurrentLinkedQueue<Command> queueCommands;
	protected Queue<Command> queuePreparedCommands;

	boolean stop;
	boolean alive;

	long maxCommandsQueued;
	long maxMergeTimeMs;

	private final Object lock = new Object();

	protected ThreadSendCommand(long maxCommandsQueued, long maxMergeTimeMs) {
		super();

		queueCommands = new ConcurrentLinkedQueue<>();
		queuePreparedCommands = new LinkedList<>();

		this.maxCommandsQueued = maxCommandsQueued;
		this.maxMergeTimeMs = maxMergeTimeMs;
	}

	public void stop() {

		this.stop = true;
	}

	public void add(Command queueCommand) {

		if (queueCommand != null) {

			this.queueCommands.add(queueCommand);
		}
	}

	@Override
	public void run() {

		log.info(Thread.currentThread().getName() + " is running...");
		this.alive = true;

		long lastProcessedTime = System.currentTimeMillis();
		int processedElements = 0;

		synchronized (lock) {

			try {

				while (!stop) {

					long currentTime = System.currentTimeMillis();

					if (currentTime - lastProcessedTime >= this.maxMergeTimeMs) {

						Command queueCommand = queueCommands.poll();

						while (queueCommand != null) {

							queuePreparedCommands.offer(queueCommand);

							if (++processedElements >= this.maxCommandsQueued) {
								break;
							}

							queueCommand = queueCommands.poll();
						}

						if (processedElements > 0) {

							sendPreparedCommands();
							processedElements = 0;
						}

						lastProcessedTime = currentTime;
					} else {

						lock.wait(maxMergeTimeMs - (currentTime - lastProcessedTime));
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
//		Instant start;
//		Instant end;
//		Duration timeElapsed;
//
//		this.alive = true;
//
//		log.info(Thread.currentThread().getName() + " is running...");
//
//		while (!stop) {
//
//			try {
//
//				start = Instant.now();
//
//				do {
//
//					Command queueCommand = queueCommands.poll();
//
//					if (queueCommand != null) {
//						
//						queuePreparedCommands.add(queueCommand);
//					}
//
//					Thread.sleep(0, 1);
//
//					end = Instant.now();
//					timeElapsed = Duration.between(start, end);
//
//				} while ((timeElapsed.toMillis() < maxMergeTimeMs) && queuePreparedCommands.size() < maxCommandsQueued
//						&& !stop);
//
//				if (!stop && !queuePreparedCommands.isEmpty()) {
//
//					sendPreparedCommands();
//				}
//
//				Thread.sleep(1);
//
//			} catch (InterruptedException e) {
//
//				log.error(e.getMessage());
//				log.debug(ExceptionUtils.getStackTrace(e));
//				Thread.currentThread().interrupt();
//			}
//		}
//
//		this.alive = false;
//
//		log.info(Thread.currentThread().getName() + " is stopped");
//	}

	protected abstract void sendPreparedCommands();
}
