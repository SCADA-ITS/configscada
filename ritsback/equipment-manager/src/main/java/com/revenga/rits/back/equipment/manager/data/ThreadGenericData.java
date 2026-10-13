package com.revenga.rits.back.equipment.manager.data;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.GenericData;
import com.revenga.rits.back.data.core.model.command.SetGenericData;
import com.revenga.rits.back.entities.lib.repository.GenericDataRepository;
import com.revenga.rits.back.equipment.manager.service.EntitiesManager;

import lombok.Setter;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class ThreadGenericData extends ThreadDataGeneric {

	private EntitiesManager em;
	private GenericDataRepository genericDataRepository;
	
	@Setter
	private List<Element> elements;

	private BlockingQueue<Instant> queue;
	private long timesUpdated = 0;
	
	public ThreadGenericData(EntitiesManager em) {

		super();

		this.em = em;
		this.genericDataRepository = (GenericDataRepository) em.getRepository(GenericData.class);
		this.queue = new LinkedBlockingQueue<>();
		
		initializeGenericDataRepository();
	}
	
	private void initializeGenericDataRepository() {
		
		GenericData genericDataActiveAlarms = null;
		GenericData genericDataNoRecognizeAlarms = null;
		GenericData genericDataFilteredAlarms = null;
		
		if (this.genericDataRepository != null) {
			
			genericDataActiveAlarms = new GenericData();
			genericDataActiveAlarms.setId(GenericData.GENERIC_DATA_ACTIVE_ALARMS);
			genericDataActiveAlarms.setValue(String.valueOf(0L));
			
			genericDataNoRecognizeAlarms = new GenericData();
			genericDataNoRecognizeAlarms.setId(GenericData.GENERIC_DATA_NO_RECOGNIZE_ALARMS);
			genericDataNoRecognizeAlarms.setValue(String.valueOf(0L));
			
			genericDataFilteredAlarms = new GenericData();
			genericDataFilteredAlarms.setId(GenericData.GENERIC_DATA_FILTERED_ALARMS);
			genericDataFilteredAlarms.setValue(String.valueOf(0L));
			
			genericDataRepository.put(
					Arrays.asList(genericDataActiveAlarms, genericDataNoRecognizeAlarms, genericDataFilteredAlarms));
		}
	}

	public void requestUpdate() {

		this.queue.add(Instant.now());
	}
	
	public int size() {

		return this.queue.size();
	}

	@Override
	public void run() {

		this.alive = true;
		log.info(Thread.currentThread().getName() + " is running...");
		Instant lastInstant = Instant.ofEpochMilli(0);
		Instant instantKeepAlive = Instant.now();

		try {

			while (!stop) {

				try {

					if (Duration.between(instantKeepAlive, Instant.now()).toMillis() > KEEP_ALIVE_DEBUG_MS) {

						instantKeepAlive = Instant.now();

						log.debug(String.format("%s is alive. [Queue size: %d; Times updated: %d%s] ",
								Thread.currentThread().getName(), 
								this.queue.size(),
								this.timesUpdated,
								(this.timesUpdated > 0 && lastInstant.toEpochMilli() > 0) ? " (Last at " + lastInstant.toString() + ")": ""));
					}

					boolean mustUpdate = false;

					while (!this.queue.isEmpty()) {

						Instant instantItem = this.queue.poll();

						if (instantItem.toEpochMilli() > lastInstant.toEpochMilli()) {

							lastInstant = instantItem;

							mustUpdate = true;
						}

						Thread.sleep(0, 1);
					}

					if (mustUpdate) {

						updateAlarmCounters();
					}

				} catch (InterruptedException e) {

					log.error(e.getMessage());
					log.debug(ExceptionUtils.getStackTrace(e));
					Thread.currentThread().interrupt();
				}

				Thread.sleep(1000);
			}

		} catch (InterruptedException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			Thread.currentThread().interrupt();
		}

		this.alive = false;
		log.info(Thread.currentThread().getName() + " is stopped");
	}

	private void updateAlarmCounters() {

		GenericData genericDataActiveAlarms = null;
		GenericData genericDataNoRecognizeAlarms = null;
		GenericData genericDataFilteredAlarms = null;

		long newActiveAlarms = 0;
		long newNoRecognizeAlarms = 0;
		long newFilteredAlarms = 0;
		long currentActiveAlarms = 0;
		long currentNoRecognizeAlarms = 0;
		long currentFilteredAlarms = 0;

		try {

			if (genericDataRepository != null && !CollectionUtils.isEmpty(elements)) {

				genericDataActiveAlarms = genericDataRepository.get(GenericData.GENERIC_DATA_ACTIVE_ALARMS, false);
				genericDataNoRecognizeAlarms = genericDataRepository.get(GenericData.GENERIC_DATA_NO_RECOGNIZE_ALARMS,
						false);
				genericDataFilteredAlarms = genericDataRepository.get(GenericData.GENERIC_DATA_FILTERED_ALARMS, false);
				
				if (genericDataActiveAlarms != null && genericDataNoRecognizeAlarms != null && genericDataFilteredAlarms != null) {
					
					currentActiveAlarms = Long.parseLong(genericDataActiveAlarms.getValue());
					currentNoRecognizeAlarms = Long.parseLong(genericDataNoRecognizeAlarms.getValue());
					currentFilteredAlarms = Long.parseLong(genericDataFilteredAlarms.getValue());

					for (Element element : elements) {

						if (element.getVisible() == null || element.getVisible().booleanValue()) {
							
							newActiveAlarms += element.getActiveAlarms();
							newNoRecognizeAlarms += element.getNoRecognizeAlarms();
							newFilteredAlarms += element.getFilteredAlarms();
						}
					}

					if (currentActiveAlarms != newActiveAlarms || 
						currentNoRecognizeAlarms != newNoRecognizeAlarms || 
						currentFilteredAlarms != newFilteredAlarms) {

						genericDataActiveAlarms.setValue(String.valueOf(newActiveAlarms));
						genericDataNoRecognizeAlarms.setValue(String.valueOf(newNoRecognizeAlarms));
						genericDataFilteredAlarms.setValue(String.valueOf(newFilteredAlarms));
						
						List<GenericData> genericDatas = Arrays.asList(genericDataActiveAlarms,
								genericDataNoRecognizeAlarms, genericDataFilteredAlarms); 
						
						genericDataRepository.put(genericDatas);

						SetGenericData setGenericData = new SetGenericData();

						setGenericData.setGenericDatas(genericDatas);

						this.em.sendCommand(setGenericData);

						log.debug("updateAlarmCounters => GENERIC_DATA_ACTIVE_ALARMS = " + newActiveAlarms);
						log.debug("updateAlarmCounters => GENERIC_DATA_NO_RECOGNIZE_ALARMS = " + newNoRecognizeAlarms);
						log.debug("updateAlarmCounters => GENERIC_DATA_FILTERED_ALARMS = " + newFilteredAlarms);
						
						timesUpdated++;
					}
				}
			}

		} catch (Exception e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}
	}
}
