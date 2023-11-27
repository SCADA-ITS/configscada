import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.revenga.rits.back.data.core.model.CommandElementType;
import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.ImsIncidentReport;
import com.revenga.rits.back.data.core.model.ImsIncidentTypeStretch;
import com.revenga.rits.back.data.core.model.ImsIncidentTypeTask;
import com.revenga.rits.back.data.core.model.ImsIncidentTypeTaskValue;
import com.revenga.rits.back.data.core.model.LogLevel;
import com.revenga.rits.back.data.core.model.LogSubtype;
import com.revenga.rits.back.data.core.model.LogType;
import com.revenga.rits.back.data.core.model.User;
import com.revenga.rits.back.data.core.model.AlarmConfig;
import com.revenga.rits.back.data.core.model.command.Command;
import com.revenga.rits.back.data.core.model.command.SignallingCommand;
import com.revenga.rits.back.data.core.model.command.SetActivationAlarmsCommand;
import com.revenga.rits.back.data.core.model.ImsIncidentReportAlarm;
import com.revenga.rits.back.entities.lib.helper.AuditHelper;
import com.revenga.rits.back.incident.manager.service.EntitiesManager;
import com.revenga.rits.back.incident.manager.service.IncidentEntitiesManager;
import com.revenga.rits.back.incident.manager.task.model.SignallingCommandToStretch;

class SendSignallingToStretchWS {

	// Values
	static final Long TASK_TYPE_PARAM_SIGNALING = 1L;
	static final Long WS_ELEMENT_TYPE_ID = 1L;
	static final Long ALARM_CONFIG_INCIDENT_IN_WS_PRECIPITATION = 3390001L;
	static final Long ALARM_CONFIG_INCIDENT_IN_WS_WIND = 3400001L;
	static final Long ALARM_CONFIG_INCIDENT_IN_WS_VISIBILITY = 3410001L;
	static final Long PRECIPITATION_MIN = 3220001L;
	static final Long PRECIPITATION_MAX = 3310001L;
	static final Long VISIBILITY_MIN = 3320001L;
	static final Long VISIBILITY_MAX = 3330001L;
	static final Long WIND_MIN = 3340001L;
	static final Long WIND_MAX = 3350001L;

	org.apache.logging.log4j.Logger log;

	SendSignallingToStretchWS(org.apache.logging.log4j.Logger log) {

		this.log = log;
	}

	List<Element> getElements(ImsIncidentTypeTask incidentTypeTask, ImsIncidentTypeStretch incidentTypeStretch) {

		List<Element> elements = new ArrayList<>();

		if (incidentTypeTask != null && incidentTypeStretch != null && incidentTypeStretch.getStretchId() != null) {

			String value = null;

			value = IncidentEntitiesManager.getInstance().getIncidentTypeTaskValue(incidentTypeTask.getTaskTypeId(),
					incidentTypeTask.getId(), TASK_TYPE_PARAM_SIGNALING);

			if (value != null) {

				List<SignallingCommandToStretch> signallingCommandToStretchs = null;
				ObjectMapper objectMapper = new ObjectMapper();

				try {

					signallingCommandToStretchs = objectMapper.readValue(value,
							new TypeReference<List<SignallingCommandToStretch>>() {
							});

				} catch (JsonProcessingException e) {

					log.error(e.getMessage());
					log.debug(ExceptionUtils.getStackTrace(e));
				}

				if (CollectionUtils.isNotEmpty(signallingCommandToStretchs)) {

					for (SignallingCommandToStretch signallingCommandToStretch : signallingCommandToStretchs) {

						Command command = EntitiesManager.getInstance().getCommandByCommandElement(signallingCommandToStretch.getCommand());

						if (command instanceof SignallingCommand) {

							SignallingCommand signallingCommand = (SignallingCommand) command;

							List<Element> elementsToAdd = EntitiesManager.getInstance().getElements(
									signallingCommandToStretch, incidentTypeStretch.getStretchId(),
									signallingCommand.getElementTypeId());

							if (CollectionUtils.isNotEmpty(elementsToAdd)) {

								elements.addAll(elementsToAdd);
							}

						}
					}
				}
			}

		}
		return elements;
	}

	List<Command> process(ImsIncidentReport incidentReport, ImsIncidentTypeTask incidentTypeTask,
			List<ImsIncidentTypeTaskValue> values) {

		String value = null;
		List<Command> commands = new ArrayList<>();

		if (incidentReport.getAffectionStretchId() != null) {

			value = IncidentEntitiesManager.getInstance().getIncidentTypeTaskValue(values,
					TASK_TYPE_PARAM_SIGNALING);

			if (value != null) {
				List<SignallingCommandToStretch> signallingCommandToStretchs = null;
				ObjectMapper objectMapper = new ObjectMapper();

				try {

					signallingCommandToStretchs = objectMapper.readValue(value,
							new TypeReference<List<SignallingCommandToStretch>>() {
							});

				} catch (JsonProcessingException e) {

					log.error(e.getMessage());
					log.debug(ExceptionUtils.getStackTrace(e));
				}

				if (CollectionUtils.isNotEmpty(signallingCommandToStretchs)) {

					for (SignallingCommandToStretch signallingCommandToStretch : signallingCommandToStretchs) {

						Command command = EntitiesManager.getInstance().getCommandByCommandElement(signallingCommandToStretch.getCommand());

						if (command instanceof SignallingCommand) {

							SignallingCommand signallingCommand = (SignallingCommand) command;

							CommandElementType commandElementType = EntitiesManager.getInstance()
									.getCommandElementType(signallingCommand.getSignallingCommandId(),
									signallingCommand.getElementTypeId());

							if (commandElementType != null) {

								List<Element> elements = EntitiesManager.getInstance().getElements(
										signallingCommandToStretch, incidentReport.getAffectionStretchId(),
										signallingCommand.getElementTypeId());

								if (CollectionUtils.isNotEmpty(elements)) {

									for (Element element : elements) {

										try {

											SignallingCommand cmd = (SignallingCommand) signallingCommand
													.clone();
											cmd.setElementId(element.getId());
											cmd.setElementTypeId(element.getElementTypeId());

											commands.add(cmd);

										} catch (CloneNotSupportedException e) {

											log.error(e.getMessage());
											log.debug(ExceptionUtils.getStackTrace(e));
										}
									}
								}
							}
						}
					}
				}
			}
			
			Set<Long> alarmElementIds = EntitiesManager.getInstance()
				.getElementByStretch(WS_ELEMENT_TYPE_ID, incidentReport.getAffectionStretchId());
			
			if (CollectionUtils.isNotEmpty(alarmElementIds)) {

				for (Long alarmElementId : alarmElementIds) {

					try {
						Element element = EntitiesManager.getInstance().getElement(WS_ELEMENT_TYPE_ID, alarmElementId)
						element.setId(alarmElementId)
						
						List<ImsIncidentReportAlarm> incidentReportAlarms = IncidentEntitiesManager.getInstance()
                    		.getIncidentAlarmsByIncidentReport(incidentReport.getId());
                    		
						AlarmConfig alarmConfig = new AlarmConfig();
                    	if (!incidentReportAlarms.isEmpty()) {
                    		
                    		for(ImsIncidentReportAlarm al:incidentReportAlarms){
	                    		if (al.getAlarmConfigId() >= PRECIPITATION_MIN && al.getAlarmConfigId() <= PRECIPITATION_MAX){
		                    		alarmConfig.setId(ALARM_CONFIG_INCIDENT_IN_WS_PRECIPITATION);
	                    		}else if (al.getAlarmConfigId() >= WIND_MIN && al.getAlarmConfigId() <= WIND_MAX){
		                    		alarmConfig.setId(ALARM_CONFIG_INCIDENT_IN_WS_WIND);
	                    		}else if (al.getAlarmConfigId() >= VISIBILITY_MIN && al.getAlarmConfigId() <= VISIBILITY_MAX){
		                    		alarmConfig.setId(ALARM_CONFIG_INCIDENT_IN_WS_VISIBILITY);
		                    	}
	                    	}
	                    }
						
						SetActivationAlarmsCommand setActivationAlarmsCommand = new SetActivationAlarmsCommand();
						setActivationAlarmsCommand.add(element, alarmConfig);
						
						commands.add(setActivationAlarmsCommand);

					} catch (Exception e) {

						log.error(e.getMessage());
						log.debug(ExceptionUtils.getStackTrace(e));
					}
				}
			}
		}

		return commands;
	}
}
