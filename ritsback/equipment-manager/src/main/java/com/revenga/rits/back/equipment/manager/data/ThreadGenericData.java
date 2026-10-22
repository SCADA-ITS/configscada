package com.revenga.rits.back.equipment.manager.data;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.GenericData;
import com.revenga.rits.back.data.core.model.GenericData.ManagementAreaValue;
import com.revenga.rits.back.data.core.model.ManagementArea;
import com.revenga.rits.back.data.core.model.ManagementAreaElement;
import com.revenga.rits.back.data.core.model.command.SetGenericData;
import com.revenga.rits.back.data.core.util.GenericDataManager;
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

	private final Object lock = new Object();

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
		GenericData genericDataConflictSignalings = null;

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

			genericDataConflictSignalings = new GenericData();
			genericDataConflictSignalings.setId(GenericData.GENERIC_DATA_CONFLICT_SIGNALINGS);
			genericDataConflictSignalings.setValue(String.valueOf(0L));

			List<ManagementArea> managementAreas = this.em.getManagementAreas();

			if (CollectionUtils.isNotEmpty(managementAreas)) {

				for (ManagementArea managementArea : managementAreas) {

					genericDataActiveAlarms.addManagementAreaValue(managementArea.getId(), String.valueOf(0L));
					genericDataNoRecognizeAlarms.addManagementAreaValue(managementArea.getId(), String.valueOf(0L));
					genericDataFilteredAlarms.addManagementAreaValue(managementArea.getId(), String.valueOf(0L));
					genericDataConflictSignalings.addManagementAreaValue(managementArea.getId(), String.valueOf(0L));
				}
			}

			genericDataRepository.put(Arrays.asList(genericDataActiveAlarms, genericDataNoRecognizeAlarms,
					genericDataFilteredAlarms, genericDataConflictSignalings));
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

		synchronized (lock) {

			try {

				while (!stop) {

					if (Duration.between(instantKeepAlive, Instant.now()).toMillis() > KEEP_ALIVE_DEBUG_MS) {

						instantKeepAlive = Instant.now();

						log.debug(String.format("%s is alive. [Queue size: %d; Times updated: %d%s] ",
								Thread.currentThread().getName(), this.queue.size(), this.timesUpdated,
								(this.timesUpdated > 0 && lastInstant.toEpochMilli() > 0)
										? " (Last at " + lastInstant.toString() + ")"
										: ""));
					}

					boolean mustUpdate = false;

					while (!this.queue.isEmpty() && !mustUpdate) {

						Instant instantItem = this.queue.poll();

						if (instantItem.toEpochMilli() > lastInstant.toEpochMilli()) {

							lastInstant = instantItem;

							mustUpdate = true;
						}
					}

					if (mustUpdate) {

						updateAlarmCounters();
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

	private void updateAlarmCounters() {

		try {

			if (genericDataRepository != null && !CollectionUtils.isEmpty(elements)) {

				GenericData genericDataActiveAlarms = genericDataRepository.get(GenericData.GENERIC_DATA_ACTIVE_ALARMS,
						false);
				GenericData genericDataNoRecognizeAlarms = genericDataRepository
						.get(GenericData.GENERIC_DATA_NO_RECOGNIZE_ALARMS, false);
				GenericData genericDataFilteredAlarms = genericDataRepository
						.get(GenericData.GENERIC_DATA_FILTERED_ALARMS, false);
				GenericData genericDataConflictSignalings = genericDataRepository
						.get(GenericData.GENERIC_DATA_CONFLICT_SIGNALINGS, false);

				if (genericDataActiveAlarms != null && genericDataNoRecognizeAlarms != null
						&& genericDataFilteredAlarms != null && genericDataConflictSignalings != null) {

					GenericDataManager managerActiveAlarms = new GenericDataManager(genericDataActiveAlarms);
					GenericDataManager managerNoRecognizeAlarms = new GenericDataManager(genericDataNoRecognizeAlarms);
					GenericDataManager managerFilteredAlarms = new GenericDataManager(genericDataFilteredAlarms);
					GenericDataManager managerConflictSignalings = new GenericDataManager(
							genericDataConflictSignalings);

					for (Element element : elements) {

						if (element.getVisible() == null || element.getVisible().booleanValue()) {

							if (CollectionUtils.isNotEmpty(element.getManagementAreaElement())) {

								for (ManagementAreaElement managementAreaElement : element.getManagementAreaElement()) {

									if (managementAreaElement.getId() != null) {

										long aux = managerActiveAlarms.getNewMap().get(managementAreaElement.getId());
										managerActiveAlarms.getNewMap().put(managementAreaElement.getId(),
												aux + element.getActiveAlarms());

										aux = managerNoRecognizeAlarms.getNewMap().get(managementAreaElement.getId());
										managerNoRecognizeAlarms.getNewMap().put(managementAreaElement.getId(),
												aux + element.getNoRecognizeAlarms());

										aux = managerFilteredAlarms.getNewMap().get(managementAreaElement.getId());
										managerFilteredAlarms.getNewMap().put(managementAreaElement.getId(),
												aux + element.getFilteredAlarms());

										aux = managerConflictSignalings.getNewMap().get(managementAreaElement.getId());
										managerConflictSignalings.getNewMap().put(managementAreaElement.getId(),
												aux + element.getConflictSignalins());
									}
								}
							} else {

								if (CollectionUtils.isNotEmpty(genericDataActiveAlarms.getManagementAreaValues())) {

									for (ManagementAreaValue managementAreaValue : genericDataActiveAlarms
											.getManagementAreaValues()) {

										long aux = managerActiveAlarms.getNewMap()
												.get(managementAreaValue.getManagementAreaId());
										managerActiveAlarms.getNewMap().put(managementAreaValue.getManagementAreaId(),
												aux + element.getActiveAlarms());

										aux = managerNoRecognizeAlarms.getNewMap()
												.get(managementAreaValue.getManagementAreaId());
										managerNoRecognizeAlarms.getNewMap().put(
												managementAreaValue.getManagementAreaId(),
												aux + element.getNoRecognizeAlarms());

										aux = managerFilteredAlarms.getNewMap()
												.get(managementAreaValue.getManagementAreaId());
										managerFilteredAlarms.getNewMap().put(managementAreaValue.getManagementAreaId(),
												aux + element.getFilteredAlarms());

										aux = managerConflictSignalings.getNewMap()
												.get(managementAreaValue.getManagementAreaId());
										managerConflictSignalings.getNewMap().put(
												managementAreaValue.getManagementAreaId(),
												aux + element.getConflictSignalins());
									}
								}

								managerActiveAlarms
										.setNewValue(managerActiveAlarms.getNewValue() + element.getActiveAlarms());
								managerNoRecognizeAlarms.setNewValue(
										managerNoRecognizeAlarms.getNewValue() + element.getNoRecognizeAlarms());
								managerFilteredAlarms
										.setNewValue(managerFilteredAlarms.getNewValue() + element.getFilteredAlarms());
								managerConflictSignalings.setNewValue(
										managerConflictSignalings.getNewValue() + element.getConflictSignalins());
							}
						}
					}

					GenericData activeAlarmsToPublish = managerActiveAlarms.updateGenericData();
					GenericData noRecognizeAlarmToPublish = managerNoRecognizeAlarms.updateGenericData();
					GenericData filteredAlarmsToPublish = managerFilteredAlarms.updateGenericData();
					GenericData conflictSignalingsToPublish = managerConflictSignalings.updateGenericData();

					if (activeAlarmsToPublish != null || noRecognizeAlarmToPublish != null
							|| filteredAlarmsToPublish != null || conflictSignalingsToPublish != null) {

						List<GenericData> genericDatasToPut = new ArrayList<>();
						List<GenericData> genericDataToPublish = new ArrayList<>();

						if (activeAlarmsToPublish != null) {

							genericDatasToPut.add(genericDataActiveAlarms);
							genericDataToPublish.add(activeAlarmsToPublish);
							log.debug("updateAlarmCounters => GENERIC_DATA_ACTIVE_ALARMS = " + genericDataToPublish);
						}

						if (noRecognizeAlarmToPublish != null) {

							genericDatasToPut.add(genericDataNoRecognizeAlarms);
							genericDataToPublish.add(noRecognizeAlarmToPublish);
							log.debug("updateAlarmCounters => GENERIC_DATA_NO_RECOGNIZE_ALARMS = "
									+ noRecognizeAlarmToPublish);
						}

						if (filteredAlarmsToPublish != null) {

							genericDatasToPut.add(genericDataFilteredAlarms);
							genericDataToPublish.add(filteredAlarmsToPublish);
							log.debug(
									"updateAlarmCounters => GENERIC_DATA_FILTERED_ALARMS = " + filteredAlarmsToPublish);
						}

						if (conflictSignalingsToPublish != null) {

							genericDatasToPut.add(genericDataConflictSignalings);
							genericDataToPublish.add(conflictSignalingsToPublish);
							log.debug(
									"updateAlarmCounters => GENERIC_DATA_CONFLICT_SIGNALINGS = " + conflictSignalingsToPublish);
						}
						
						genericDataRepository.put(genericDatasToPut);

						SetGenericData setGenericData = new SetGenericData();

						setGenericData.setGenericDatas(genericDataToPublish);

						this.em.sendCommand(setGenericData);

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
