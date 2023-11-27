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
import com.revenga.rits.back.data.core.model.command.SetDeactivationAlarmsCommand;
import com.revenga.rits.back.data.core.model.command.SetActivationAlarmsCommand;
import com.revenga.rits.back.data.core.model.ImsIncidentReportAlarm;
import com.revenga.rits.back.entities.lib.helper.AuditHelper;
import com.revenga.rits.back.incident.manager.service.EntitiesManager;
import com.revenga.rits.back.incident.manager.service.IncidentEntitiesManager;
import com.revenga.rits.back.incident.manager.task.model.SignallingCommandToStretch;

class SendSignallingToStretch {

	// Values
	static final Long TASK_TYPE_PARAM_SIGNALING = 1L;
	static final Long WS_ELEMENT_TYPE_ID = 1L;
	static final Long IMANAGER_ELEMENT_TYPE_ID = 23L;
	static final Long IMANAGER_ELEMENT_ID = 1L;
	static final Long ALARM_CONFIG_INCIDENT_IN_WS_PRECIPITATION = 3390001L;
	static final Long ALARM_CONFIG_INCIDENT_IN_WS_WIND = 3400001L;
	static final Long ALARM_CONFIG_INCIDENT_IN_WS_VISIBILITY = 3410001L;
	static final Long ALARM_CONFIG_IMANAGER = 2301L;
	static final Long PRECIPITATION = 3360001L;
	static final Long VISIBILITY = 3370001L;
	static final Long WIND = 3380001L;

	org.apache.logging.log4j.Logger log;

	SendSignallingToStretch(org.apache.logging.log4j.Logger log) {

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
		List<SignallingCommandToStretch> signallingCommandToStretchs = null;

		if (incidentReport.getAffectionStretchId() != null) {

			value = IncidentEntitiesManager.getInstance().getIncidentTypeTaskValue(values,
					TASK_TYPE_PARAM_SIGNALING);

			if (value != null) {
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
					
					SetDeactivationAlarmsCommand setDeactivationAlarmsCommand = new SetDeactivationAlarmsCommand();
					SetActivationAlarmsCommand setActivationAlarmsCommand = new SetActivationAlarmsCommand();
					
					Set<Long> alarmElementIds = EntitiesManager.getInstance()
						.getElementByStretch(WS_ELEMENT_TYPE_ID, incidentReport.getAffectionStretchId());
					
					if (CollectionUtils.isNotEmpty(alarmElementIds)) {
		
						for (Long alarmElementId : alarmElementIds) {
		
							try {
								Element element = EntitiesManager.getInstance().getElement(WS_ELEMENT_TYPE_ID, alarmElementId)
							
								List<ImsIncidentReportAlarm> incidentReportAlarms = IncidentEntitiesManager.getInstance()
		                    		.getIncidentAlarmsByIncidentReport(incidentReport.getId());
		                    		
								AlarmConfig alarmConfig = new AlarmConfig();
		                    	if (!incidentReportAlarms.isEmpty()) {
		                    		
		                    		for(ImsIncidentReportAlarm al:incidentReportAlarms){
			                    		if (al.getAlarmConfigId() == PRECIPITATION){
				                    		alarmConfig.setId(ALARM_CONFIG_INCIDENT_IN_WS_PRECIPITATION);
			                    		}else if (al.getAlarmConfigId() == WIND){
				                    		alarmConfig.setId(ALARM_CONFIG_INCIDENT_IN_WS_WIND);
			                    		}else if (al.getAlarmConfigId() == VISIBILITY){
				                    		alarmConfig.setId(ALARM_CONFIG_INCIDENT_IN_WS_VISIBILITY);
				                    	}
			                    	}
			                    }
			                    	            																
								setDeactivationAlarmsCommand.add(element, alarmConfig);	
								
								element = EntitiesManager.getInstance().getElement(IMANAGER_ELEMENT_TYPE_ID, IMANAGER_ELEMENT_ID);
								alarmConfig.setId(ALARM_CONFIG_IMANAGER);
								
								setDeactivationAlarmsCommand.add(element, alarmConfig);																			
								
								commands.add(setDeactivationAlarmsCommand);
								
								SignallingCommand signallingCommand = new SignallingCommand();
		
								signallingCommand.setSignallingCommandId(1L);
								signallingCommand.setElementTypeId(23L);
								signallingCommand.setElementId(1L);
								commands.add(signallingCommand);	
		
							} catch (Exception e) {
		
								log.error(e.getMessage());
								log.debug(ExceptionUtils.getStackTrace(e));
							}
						}
					}
				}
			}
		}

		return commands;
	}
}
