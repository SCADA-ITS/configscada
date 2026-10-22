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
import com.revenga.rits.back.data.core.model.SpathStretch;
import com.revenga.rits.back.data.core.model.User;
import com.revenga.rits.back.data.core.model.command.Command;
import com.revenga.rits.back.data.core.model.command.SignallingCommand;
import com.revenga.rits.back.entities.lib.helper.AuditHelper;
import com.revenga.rits.back.incident.manager.service.EntitiesManager;
import com.revenga.rits.back.incident.manager.service.IncidentEntitiesManager;
import com.revenga.rits.back.incident.manager.task.model.SignallingCommandToSpath;

class SendSignallingToSpath {

	// Values
	static final Long TASK_TYPE_PARAM_SIGNALING_TO_SPATH = 1L;

	org.apache.logging.log4j.Logger log;

	SendSignallingToSpath(org.apache.logging.log4j.Logger log) {

		this.log = log;
	}

	List<Element> getElements(ImsIncidentTypeTask incidentTypeTask, ImsIncidentTypeStretch incidentTypeStretch) {

		List<Element> elements = new ArrayList<>();

		if (incidentTypeTask != null && incidentTypeStretch != null) {

			String value = null;

			value = IncidentEntitiesManager.getInstance().getIncidentTypeTaskValue(incidentTypeTask.getTaskTypeId(),
					incidentTypeTask.getId(), TASK_TYPE_PARAM_SIGNALING_TO_SPATH);

			if (value != null) {

				List<SignallingCommandToSpath> signallingCommandToSpaths = null;
				ObjectMapper objectMapper = new ObjectMapper();

				try {

					signallingCommandToSpaths = objectMapper.readValue(value,
							new TypeReference<List<SignallingCommandToSpath>>() {
							});

				} catch (JsonProcessingException e) {

					log.error(e.getMessage());
					log.debug(ExceptionUtils.getStackTrace(e));
				}

				if (CollectionUtils.isNotEmpty(signallingCommandToSpaths)) {

					for (SignallingCommandToSpath signallingCommandToSpath : signallingCommandToSpaths) {

						Long spathId = EntitiesManager.getInstance().getSpathId(signallingCommandToSpath, incidentTypeStretch);

						if(spathId != null){

							List<SpathStretch> spathStretchs = IncidentEntitiesManager.getInstance()
									.getSpathStretchs(spathId, incidentTypeStretch.getStretchId());

							if (CollectionUtils.isNotEmpty(spathStretchs)) {

								Command command = EntitiesManager.getInstance()
										.getCommandByCommandElement(signallingCommandToSpath.getCommand());

								if (command instanceof SignallingCommand) {

									SignallingCommand signallingCommand = (SignallingCommand) command;

									List<Element> elementsToAdd = EntitiesManager.getInstance().getElements(
											signallingCommandToSpath, spathStretchs,
											signallingCommand.getElementTypeId());

									if (CollectionUtils.isNotEmpty(elementsToAdd)) {

										elements.addAll(elementsToAdd);
									}

								}

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
					TASK_TYPE_PARAM_SIGNALING_TO_SPATH);

			if (value != null) {
				List<SignallingCommandToSpath> signallingCommandToSpaths = null;
				ObjectMapper objectMapper = new ObjectMapper();

				try {

					signallingCommandToSpaths = objectMapper.readValue(value,
							new TypeReference<List<SignallingCommandToSpath>>() {
							});

				} catch (JsonProcessingException e) {

					log.error(e.getMessage());
					log.debug(ExceptionUtils.getStackTrace(e));
				}

				if (CollectionUtils.isNotEmpty(signallingCommandToSpaths)) {

					for (SignallingCommandToSpath signallingCommandToSpath : signallingCommandToSpaths) {

						Long spathId = IncidentEntitiesManager.getInstance()
								.getSpathIdByIncidentReport(signallingCommandToSpath, incidentReport);

						if(spathId != null){

							List<SpathStretch> spathStretchs = IncidentEntitiesManager.getInstance()
									.getSpathStretchs(spathId, incidentReport.getAffectionStretchId());

							if (CollectionUtils.isNotEmpty(spathStretchs)) {

								Command command = EntitiesManager.getInstance()
										.getCommandByCommandElement(signallingCommandToSpath.getCommand());

								if (command instanceof SignallingCommand) {

									SignallingCommand signallingCommand = (SignallingCommand) command;

									CommandElementType commandElementType = EntitiesManager.getInstance()
											.getCommandElementType(signallingCommand.getSignallingCommandId(),
											signallingCommand.getElementTypeId());

									if (commandElementType != null) {

										List<Element> elements = EntitiesManager.getInstance().getElements(
												signallingCommandToSpath, spathStretchs,
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
				}
			}
		}

		return commands;
	}
}
