package com.revenga.rits.back.incident.manager.executor.service;

import java.time.Instant;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

import com.revenga.rits.back.cache.core.manager.CacheManager;
import com.revenga.rits.back.data.core.model.GenericData;
import com.revenga.rits.back.data.core.model.ImsIncidentReport;
import com.revenga.rits.back.data.core.model.ManagementArea;
import com.revenga.rits.back.data.core.model.SystemParam;
import com.revenga.rits.back.data.core.model.SystemParamValue;
import com.revenga.rits.back.data.core.model.command.Command;
import com.revenga.rits.back.data.core.model.command.SetGenericData;
import com.revenga.rits.back.data.core.util.GenericDataManager;
import com.revenga.rits.back.entities.lib.repository.GenericDataRepository;
import com.revenga.rits.back.entities.lib.repository.SystemParamValueRepository;
import com.revenga.rits.back.incident.manager.service.EntitiesManager;
import com.revenga.rits.back.incident.manager.service.IncidentEntitiesManager;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class MaintenacePendingIncidentsThread implements Runnable {

	private IncidentService incidentService;
	private boolean stop;
	private boolean alive;
	private GenericDataManager managerIncidentPendingToConfirm;
	private GenericData genericDataIncidentPendingToConfirm;
	
	private List<ManagementArea> managementAreas;

	public MaintenacePendingIncidentsThread(IncidentService incidentService) {

		super();
		this.incidentService = incidentService;

		this.genericDataIncidentPendingToConfirm = new GenericData();
		this.genericDataIncidentPendingToConfirm.setId(GenericData.GENERIC_DATA_INCIDENT_PENDING_TO_CONFIRM);
		this.genericDataIncidentPendingToConfirm.setValue(String.valueOf(0L));

		this.managementAreas = EntitiesManager.getInstance().getManagementAreas();

		if (CollectionUtils.isNotEmpty(this.managementAreas)) {

			for (ManagementArea managementArea : this.managementAreas) {

				genericDataIncidentPendingToConfirm.addManagementAreaValue(managementArea.getId(), String.valueOf(0L));

			}
		}

		(new GenericDataRepository(false)).put(genericDataIncidentPendingToConfirm);

		this.managerIncidentPendingToConfirm = new GenericDataManager(genericDataIncidentPendingToConfirm);
	}

	@Override
	public void run() {

		try {

			log.info(Thread.currentThread().getName() + " is running");

			SystemParamValue systemParamValuePeriod = (new SystemParamValueRepository(false))
					.get(Long.valueOf(SystemParam.SYSTEM_PARAM_WAITING_TIME_UNTIL_AUTO_CONFIRM_INCIDENTS), false);

			Long timeToConfirmIncidentsS = systemParamValuePeriod != null && systemParamValuePeriod.getValue() != null
					? Long.parseLong(systemParamValuePeriod.getValue())
					: null;

			Long count = 0L;

			while (!stop) {

				Long now = Instant.now().getEpochSecond();

				if (count % 60 == 0) {

					List<ImsIncidentReport> incidentsExpired = incidentService.checkIncidentExpired(now);

					IncidentEntitiesManager.getInstance().publishIncidentReports(Command.ACTION_UPDATE,
							incidentsExpired);

					count = 0L;
				}
				count++;

				List<ImsIncidentReport> incidentReports = incidentService.checkIncidentPendingToConfirm(now,
						timeToConfirmIncidentsS);

				IncidentEntitiesManager.getInstance().publishIncidentReports(Command.ACTION_UPDATE, incidentReports);

				List<ImsIncidentReport> incidentPendingToConfirm = IncidentEntitiesManager.getInstance()
						.getIncidentPendingToConfirm();

				if (CollectionUtils.isNotEmpty(incidentPendingToConfirm)) {

					for (ImsIncidentReport incidentReport : incidentPendingToConfirm) {

						if (incidentReport != null && incidentReport.getManagementAreaId() != null) {

							long aux = managerIncidentPendingToConfirm.getNewMap()
									.get(incidentReport.getManagementAreaId());
							managerIncidentPendingToConfirm.getNewMap().put(incidentReport.getManagementAreaId(),
									aux + 1);
						} else {

							if (CollectionUtils.isNotEmpty(this.managementAreas)) {

								for (ManagementArea managementArea : this.managementAreas) {

									long aux = managerIncidentPendingToConfirm.getNewMap().get(managementArea.getId());
									managerIncidentPendingToConfirm.getNewMap().put(managementArea.getId(), aux + 1);

								}
							}
							managerIncidentPendingToConfirm
									.setNewValue(managerIncidentPendingToConfirm.getNewValue() + 1);

						}
					}
				}

				GenericData genericDataToPublish = managerIncidentPendingToConfirm.updateGenericData();

				if (genericDataToPublish != null) {

					(new GenericDataRepository(false)).put(genericDataIncidentPendingToConfirm);

					SetGenericData setGenericData = new SetGenericData();

					setGenericData.addGenericData(genericDataToPublish);
					
					CacheManager.getInstance().sendCommand(setGenericData, Command.DEFAULT_EXPIRE_TIME);
				}

				Thread.sleep(1000);
			}

		} catch (Exception e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			Thread.currentThread().interrupt();

		} finally {

			this.setAlive(false);

			log.info(Thread.currentThread().getName() + " is stopped");
		}
	}

	public void stop() {

		this.stop = true;
	}

	public boolean isAlive() {

		return alive;
	}

	public void setAlive(boolean alive) {

		this.alive = alive;
	}

	public boolean getStop() {

		return this.stop;
	}

}
