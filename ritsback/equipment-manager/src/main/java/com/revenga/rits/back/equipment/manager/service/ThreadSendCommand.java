package com.revenga.rits.back.equipment.manager.service;

import java.time.Duration;
import java.time.Instant;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.commons.lang3.SerializationException;
import org.apache.commons.lang3.SerializationUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

import com.revenga.rits.back.data.core.model.command.Command;

import lombok.extern.log4j.Log4j2;

@Log4j2
public abstract class ThreadSendCommand implements Runnable {

	BlockingQueue<Command> queueCommands;
	protected Queue<Command> queuePreparedCommands;

	boolean stop;
	boolean alive;

	long maxCommandsQueued;
	long maxMergeTimeMs;

	protected ThreadSendCommand(long maxCommandsQueued, long maxMergeTimeMs) {
		super();

		queueCommands = new LinkedBlockingQueue<>();
		queuePreparedCommands = new LinkedList<>();

		this.maxCommandsQueued = maxCommandsQueued;
		this.maxMergeTimeMs = maxMergeTimeMs;
	}

	public void stop() {

		this.stop = true;
	}

	public void add(Command queueCommand) {

		try {
			
			Command queueCommandCloned = SerializationUtils.clone(queueCommand);
			
			if (queueCommandCloned != null) {
				
				this.queueCommands.add(queueCommandCloned);
			}
		
		} catch (SerializationException  e) {
			
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}
	}

	@Override
	public void run() {

		Instant start;
		Instant end;
		Duration timeElapsed;

		this.alive = true;

		log.info(Thread.currentThread().getName() + " is running...");

		while (!stop) {

			try {

				start = Instant.now();

				do {

					Command queueCommand = queueCommands.poll();

					if (queueCommand != null) {
						
						queuePreparedCommands.add(queueCommand);
					}

					Thread.sleep(0, 1);

					end = Instant.now();
					timeElapsed = Duration.between(start, end);

				} while ((timeElapsed.toMillis() < maxMergeTimeMs) && queuePreparedCommands.size() < maxCommandsQueued
						&& !stop);

				if (!stop && !queuePreparedCommands.isEmpty()) {

					sendPreparedCommands();
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

	protected abstract void sendPreparedCommands();
}
