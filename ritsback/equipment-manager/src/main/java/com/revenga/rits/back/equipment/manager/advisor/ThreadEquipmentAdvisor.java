package com.revenga.rits.back.equipment.manager.advisor;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.commons.lang3.exception.ExceptionUtils;

import com.revenga.rits.back.equipment.manager.advisor.model.EquipmentMessage;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class ThreadEquipmentAdvisor implements Runnable {

	BlockingQueue<EquipmentMessage> queueEquipmentMessages;

	boolean stop;
	boolean alive;

	private final Object lock = new Object();

	public ThreadEquipmentAdvisor() {
		super();

		queueEquipmentMessages = new LinkedBlockingQueue<>();
	}

	public void stop() {

		this.stop = true;
	}

	public void add(EquipmentMessage equipmentMessage) {

		if (equipmentMessage != null) {

			this.queueEquipmentMessages.add(equipmentMessage);
		}

	}

	@Override
	public void run() {

		ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
		this.alive = true;
		EquipmentAdvisorProcessor equipmentAdvisorProcessor = new EquipmentAdvisorProcessor();

		log.info(Thread.currentThread().getName() + " is running...");

		synchronized (lock) {

			try {

				while (!stop) {

					while (!queueEquipmentMessages.isEmpty()) {

						EquipmentMessage equipmentMessage = queueEquipmentMessages.poll();

						Runnable task = () -> equipmentAdvisorProcessor.process(equipmentMessage);

						executor.submit(task);
					}

					lock.wait(1000);
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
}
