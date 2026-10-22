package com.revenga.rits.back.incident.manager.executor.processor;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Semaphore;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;

import com.revenga.rits.back.data.core.model.Alarm;
import com.revenga.rits.back.data.core.model.ImsIncidentAlarmCondition;
import com.revenga.rits.back.data.core.model.ImsIncidentAlarmConditionAlarm;
import com.revenga.rits.back.data.core.model.ImsIncidentAlarmConditionAlarmType;
import com.revenga.rits.back.data.core.model.ImsIncidentReport;
import com.revenga.rits.back.data.core.model.ImsIncidentReportAlarm;
import com.revenga.rits.back.data.core.model.LogLevel;
import com.revenga.rits.back.data.core.model.LogSubtype;
import com.revenga.rits.back.incident.manager.executor.IncidentAlarmExecutor;
import com.revenga.rits.back.incident.manager.helper.IncidentHeper;
import com.revenga.rits.back.incident.manager.service.IncidentAlarmEntitiesManager;
import com.revenga.rits.back.incident.manager.service.IncidentEntitiesManager;
import com.revenga.rits.back.manager.lib.executor.GenericExecutor;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class AlarmsProcessor {

	GenericExecutor genericExecutor;

	public AlarmsProcessor(IncidentAlarmExecutor incidentAlarmExecutor) {

		this.genericExecutor = incidentAlarmExecutor;

		if (this.genericExecutor.getThreadNum() == 0) {

			IncidentAlarmEntitiesManager.getInstance().initMaps();
		}
	}

	public List<ImsIncidentReport> processAlarms(Set<Alarm> alarms) {

		List<ImsIncidentReport> incidentReports = null;

		Set<Long> incidentAlarmConditionIds = new HashSet<>();

		Instant instant = Instant.now();

		for (Alarm alarm : alarms) {

			// Source and target have to be the same element
			if (ObjectUtils.allNotNull(alarm.getAlarmConfigId(), alarm.getTargetElementId(),
					alarm.getTargetElementTypeId(), alarm.getSourceElementId(), alarm.getSourceElementTypeId())
					&& alarm.getTargetElementId().equals(alarm.getSourceElementId())
					&& alarm.getTargetElementTypeId().equals(alarm.getSourceElementTypeId())) {

				List<ImsIncidentAlarmConditionAlarm> incidentAlarmConditionAlarms = IncidentAlarmEntitiesManager
						.getInstance().getAlarmConditionAlarmByAlarmConfig(alarm.getAlarmConfigId());

				if (CollectionUtils.isNotEmpty(incidentAlarmConditionAlarms)) {

					for (ImsIncidentAlarmConditionAlarm incidentAlarmConditionAlarm : incidentAlarmConditionAlarms) {

						long alarmType = incidentAlarmConditionAlarm.getIncidentAlarmConditionAlarmTypeId().longValue();

						if (alarmType == ImsIncidentAlarmConditionAlarmType.EQUIPMENT_ALERTED) {

							if (alarm.getTargetElementId().equals(incidentAlarmConditionAlarm.getElementId()) && alarm
									.getTargetElementTypeId().equals(incidentAlarmConditionAlarm.getElementTypeId())) {

								// Condition satisfied
								if (alarm.getDeactivation() == null) {

									if (alarm.getRecognized() == null || !IncidentAlarmEntitiesManager.getInstance()
											.getAlarmConfigWithtreatment().contains(alarm.getAlarmConfigId())) {

										IncidentAlarmEntitiesManager.getInstance().addUseCase1(
												incidentAlarmConditionAlarm.getIncidentAlarmConditionId(),
												incidentAlarmConditionAlarm.getId(), alarm);

										incidentAlarmConditionIds
												.add(incidentAlarmConditionAlarm.getIncidentAlarmConditionId());
									}

								}

								// Condition is no satisfied
								else {

									IncidentAlarmEntitiesManager.getInstance().removeUseCase1(
											incidentAlarmConditionAlarm.getIncidentAlarmConditionId(),
											incidentAlarmConditionAlarm.getId(), alarm);
								}
							}

						} else if (alarmType == ImsIncidentAlarmConditionAlarmType.ANY_EQUIPMENT_TYPE_ALARMED) {

							if (IncidentAlarmEntitiesManager.getInstance().isElementInAlarmConditionAlarm(
									incidentAlarmConditionAlarm.getId(), null, alarm.getTargetElementId())) {

								// Condition satisfied
								if (alarm.getDeactivation() == null) {

									if (alarm.getRecognized() == null || !IncidentAlarmEntitiesManager.getInstance()
											.getAlarmConfigWithtreatment().contains(alarm.getAlarmConfigId())) {
										IncidentAlarmEntitiesManager.getInstance()
												.addUseCase2(incidentAlarmConditionAlarm, alarm);

										incidentAlarmConditionIds
												.add(incidentAlarmConditionAlarm.getIncidentAlarmConditionId());
									}
								}

								// Condition is no satisfied
								else {

									IncidentAlarmEntitiesManager.getInstance()
											.removeUseCase2(incidentAlarmConditionAlarm, alarm);
								}

							}

						} else if (alarmType == ImsIncidentAlarmConditionAlarmType.ALL_EQUIPMENT_TYPE_ALARMED) {

							if (IncidentAlarmEntitiesManager.getInstance().isElementInAlarmConditionAlarm(
									incidentAlarmConditionAlarm.getId(),
									incidentAlarmConditionAlarm.getIncidentAlarmConditionId(),
									alarm.getTargetElementId())) {

								// Condition satisfied
								if (alarm.getDeactivation() == null) {

									if (alarm.getRecognized() == null || !IncidentAlarmEntitiesManager.getInstance()
											.getAlarmConfigWithtreatment().contains(alarm.getAlarmConfigId())) {
										
										IncidentAlarmEntitiesManager.getInstance().addUseCase3(
												incidentAlarmConditionAlarm.getId(),
												incidentAlarmConditionAlarm.getIncidentAlarmConditionId(), alarm);

										incidentAlarmConditionIds
												.add(incidentAlarmConditionAlarm.getIncidentAlarmConditionId());
									}
								}

								// Condition is no satisfied
								else {

									IncidentAlarmEntitiesManager.getInstance().removeUseCase3(
											incidentAlarmConditionAlarm.getId(),
											incidentAlarmConditionAlarm.getIncidentAlarmConditionId(), alarm);
								}

							}
						}
					}
				}
			}
		}

		// Conditions that have changed are checked
		incidentAlarmConditionIds = incidentAlarmConditionIds.stream()
				.filter(incidentAlarmConditionId -> IncidentAlarmEntitiesManager.getInstance()
						.checkAlarmCondition(incidentAlarmConditionId))
				.collect(Collectors.toSet());

		log.info(this.getClass().getSimpleName() + " => Time to process " + alarms.size() + " alarms = "
				+ Duration.between(instant, Instant.now()).toMillis() + " ms.");

		// Create new incident report
		instant = Instant.now();

		for (Long incidentAlarmConditionId : incidentAlarmConditionIds) {

			ImsIncidentAlarmCondition incidentAlarmCondition = IncidentAlarmEntitiesManager.getInstance()
					.getAlarmCondition(incidentAlarmConditionId);

			Long locationId = IncidentAlarmEntitiesManager.getInstance()
					.getLocationByAlarmCondition(incidentAlarmConditionId);

			List<ImsIncidentReportAlarm> incidentReportAlarms = IncidentAlarmEntitiesManager.getInstance()
					.getIncidentReportAlarmsByAlarmCondition(incidentAlarmConditionId);

			List<ImsIncidentReport> newReports = IncidentEntitiesManager.getInstance().createIncidentReport(
					incidentAlarmCondition.getIncidentTypeId(), locationId,
					Arrays.asList(new Long[] { incidentAlarmCondition.getStretchId() }), incidentReportAlarms,
					genericExecutor, null, incidentAlarmCondition.getRoadImpactId(), false);

			if (CollectionUtils.isNotEmpty(newReports)) {

				if (incidentReports == null) {

					incidentReports = new ArrayList<>();
				}
				incidentReports.addAll(newReports);

				if (!CollectionUtils.isEmpty(newReports)) {

					for (ImsIncidentReport incidentReport : newReports) {

						if (incidentReport.getId() != null) {

							log.info("IncidentReport created: " + incidentReport.getId());
							IncidentEntitiesManager.getInstance().addLogIncidentReport(null, incidentReport.getId(),
									LogSubtype.LOG_SUBTYPE_INCIDENT_REPORT_CREATED, LogLevel.INFO);

							Semaphore mutex = IncidentHeper.acquire(genericExecutor, incidentReport.getId());

							if (mutex != null) {

								IncidentHeper.evalueIncidentReport(incidentReport.getId());
								mutex.release();

							} else {
								log.error("Mutex not found: " + incidentReport.getId());
							}
						}
					}
				}
			}
		}
		log.info(this.getClass().getSimpleName() + " => " + (incidentReports != null ? incidentReports.size() : 0)
				+ " reports generated in => " + Duration.between(instant, Instant.now()).toMillis() + " ms.");

		return incidentReports;
	}

}
