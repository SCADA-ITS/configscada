import java.util.ArrayList
import java.util.List

import org.apache.commons.collections4.CollectionUtils
import org.apache.commons.lang3.exception.ExceptionUtils

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.revenga.rits.back.data.core.model.CommandElementType
import com.revenga.rits.back.data.core.model.Element
import com.revenga.rits.back.data.core.model.ImsIncidentReport
import com.revenga.rits.back.data.core.model.ImsIncidentTypeStretch
import com.revenga.rits.back.data.core.model.ImsIncidentTypeTask
import com.revenga.rits.back.data.core.model.ImsIncidentTypeTaskValue
import com.revenga.rits.back.data.core.model.StretchLocation
import com.revenga.rits.back.data.core.model.command.Command
import com.revenga.rits.back.data.core.model.command.SignallingCommand
import com.revenga.rits.back.incident.manager.service.EntitiesManager
import com.revenga.rits.back.incident.manager.service.IncidentEntitiesManager
import com.revenga.rits.back.incident.manager.task.model.SignallingCommandToStretch

class SendSignallingToStretchByLocation {

	static final Long TASK_TYPE_PARAM_SIGNALING = 1L

	org.apache.logging.log4j.Logger log

	SendSignallingToStretchByLocation(org.apache.logging.log4j.Logger log) {
		this.log = log
	}

	private Long resolveStretchId(ImsIncidentTypeStretch incidentTypeStretch, Long locationId) {
		Long stretchIdToSearch = null

		if (locationId != null) {
			List<StretchLocation> stretchLocations = EntitiesManager.getInstance().getStretchLocationByLocation(locationId)

			if (CollectionUtils.isNotEmpty(stretchLocations)) {
				for (StretchLocation stretchLocation : stretchLocations) {
					if (stretchLocation != null
							&& stretchLocation.getPosition() != null
							&& stretchLocation.getPosition().longValue() == 0L
							&& stretchLocation.getStretchId() != null) {
						stretchIdToSearch = stretchLocation.getStretchId()
						break
					}
				}
			}
		}

		if (stretchIdToSearch == null) {
			log.debug("No stretch resolved. locationId=${locationId}, incidentTypeStretchId=${incidentTypeStretch != null ? incidentTypeStretch.getStretchId() : null}")
		}

		return stretchIdToSearch
	}

	List<Element> getElements(ImsIncidentTypeTask incidentTypeTask, ImsIncidentTypeStretch incidentTypeStretch, Long locationId) {
		List<Element> elements = new ArrayList<>()
		Long stretchIdToSearch = null

		stretchIdToSearch = resolveStretchId(incidentTypeStretch, locationId)

		if (incidentTypeTask != null && stretchIdToSearch != null) {
			String value = IncidentEntitiesManager.getInstance().getIncidentTypeTaskValue(
					incidentTypeTask.getTaskTypeId(),
					incidentTypeTask.getId(),
					TASK_TYPE_PARAM_SIGNALING)

			if (value != null) {
				List<SignallingCommandToStretch> signallingCommandToStretchs = null
				ObjectMapper objectMapper = new ObjectMapper()

				try {
					signallingCommandToStretchs = objectMapper.readValue(value,
							new TypeReference<List<SignallingCommandToStretch>>() {})
				} catch (JsonProcessingException e) {
					log.error(e.getMessage())
					log.debug(ExceptionUtils.getStackTrace(e))
				}

				if (CollectionUtils.isNotEmpty(signallingCommandToStretchs)) {
					for (SignallingCommandToStretch signallingCommandToStretch : signallingCommandToStretchs) {
						Command command = EntitiesManager.getInstance()
								.getCommandByCommandElement(signallingCommandToStretch.getCommand())

						if (command instanceof SignallingCommand) {
							SignallingCommand signallingCommand = (SignallingCommand) command

							List<Element> elementsToAdd = EntitiesManager.getInstance().getElements(
									signallingCommandToStretch,
									stretchIdToSearch,
									signallingCommand.getElementTypeId())

							if (CollectionUtils.isNotEmpty(elementsToAdd)) {
								elements.addAll(elementsToAdd)
							}
						}
					}
				}
			}
		}

		return elements
	}

	List<Command> process(ImsIncidentReport incidentReport, ImsIncidentTypeTask incidentTypeTask,
			List<ImsIncidentTypeTaskValue> values) {
		String value = null
		List<Command> commands = new ArrayList<>()
		Long stretchIdToSearch = null
		Long locationId = incidentReport?.getLocationId()

		stretchIdToSearch = resolveStretchId(null, locationId)

		if (incidentReport != null && stretchIdToSearch != null) {
			value = IncidentEntitiesManager.getInstance().getIncidentTypeTaskValue(values, TASK_TYPE_PARAM_SIGNALING)

			if (value != null) {
				List<SignallingCommandToStretch> signallingCommandToStretchs = null
				ObjectMapper objectMapper = new ObjectMapper()

				try {
					signallingCommandToStretchs = objectMapper.readValue(value,
							new TypeReference<List<SignallingCommandToStretch>>() {})
				} catch (JsonProcessingException e) {
					log.error(e.getMessage())
					log.debug(ExceptionUtils.getStackTrace(e))
				}

				if (CollectionUtils.isNotEmpty(signallingCommandToStretchs)) {
					for (SignallingCommandToStretch signallingCommandToStretch : signallingCommandToStretchs) {
						Command command = EntitiesManager.getInstance()
								.getCommandByCommandElement(signallingCommandToStretch.getCommand())

						if (command instanceof SignallingCommand) {
							SignallingCommand signallingCommand = (SignallingCommand) command

							CommandElementType commandElementType = EntitiesManager.getInstance()
									.getCommandElementType(
											signallingCommand.getSignallingCommandId(),
											signallingCommand.getElementTypeId())

							if (commandElementType != null) {
								List<Element> elements = EntitiesManager.getInstance().getElements(
										signallingCommandToStretch,
										stretchIdToSearch,
										signallingCommand.getElementTypeId())

								if (CollectionUtils.isNotEmpty(elements)) {
									for (Element element : elements) {
										try {
											SignallingCommand cmd = (SignallingCommand) signallingCommand.clone()
											cmd.setElementId(element.getId())
											cmd.setElementTypeId(element.getElementTypeId())
											commands.add(cmd)
										} catch (CloneNotSupportedException e) {
											log.error(e.getMessage())
											log.debug(ExceptionUtils.getStackTrace(e))
										}
									}
								}
							}
						}
					}
				}
			}
		}

		return commands
	}
}