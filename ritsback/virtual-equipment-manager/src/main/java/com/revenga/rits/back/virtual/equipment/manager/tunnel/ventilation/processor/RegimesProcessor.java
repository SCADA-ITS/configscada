package com.revenga.rits.back.virtual.equipment.manager.tunnel.ventilation.processor;

import java.time.Duration;
import java.time.Instant;

import org.apache.commons.lang3.exception.ExceptionUtils;

import com.revenga.rits.back.virtual.equipment.manager.tunnel.TubeEntityManager;
import com.revenga.rits.back.virtual.equipment.manager.tunnel.ventilation.entity.manager.FanEntityManager;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class RegimesProcessor {

	private TubeEntityManager tubeEntityManager;
	private FanEntityManager fanEntityManager;
	private RegimesProcessorThread regimesProcessorThread;

	public RegimesProcessor(FanEntityManager fanEntityManager, TubeEntityManager tubeEntityManager) {

		this.fanEntityManager = fanEntityManager;
		this.tubeEntityManager = tubeEntityManager;
	}

	public Boolean init() {

		Boolean ok = true;

		if (regimesProcessorThread != null) {

			regimesProcessorThread.stop();
		}

		regimesProcessorThread = new RegimesProcessorThread(fanEntityManager, tubeEntityManager);

		String threadNameService = "Thread" + regimesProcessorThread.getClass().getSimpleName();

		(new Thread(regimesProcessorThread, threadNameService)).start();

		return ok;
	}

	public Boolean deInit() {

		Boolean ok = true;

		if (regimesProcessorThread != null) {

			try {
				regimesProcessorThread.stop();

				Instant start;
				Duration timeElapsed;
				boolean stopped;
				start = Instant.now();

				do {
					stopped = !regimesProcessorThread.isAlive();
					Thread.sleep(1L);

					timeElapsed = Duration.between(start, Instant.now());

				} while (timeElapsed.toMillis() < 5000L && !stopped);
			}

			catch (InterruptedException e) {

				log.error(e.getMessage());
				log.debug(ExceptionUtils.getStackTrace(e));
				Thread.currentThread().interrupt();
				ok = false;
			}
		}
		return ok;
	}


}
