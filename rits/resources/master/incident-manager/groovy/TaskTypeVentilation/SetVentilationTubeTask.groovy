import java.util.ArrayList;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

import java.util.List;

import com.revenga.rits.back.data.core.model.ElementHierarchy;
import com.revenga.rits.back.data.core.model.ElementType;
import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.ImsIncidentReport;
import com.revenga.rits.back.data.core.model.ImsIncidentTypeTask;
import com.revenga.rits.back.data.core.model.ImsIncidentTypeTaskValue;
import com.revenga.rits.back.data.core.model.ImsIncidentTypeStretch;
import com.revenga.rits.back.data.core.model.command.Command;
import com.revenga.rits.back.data.core.model.command.virtual.SetVentilationModeCommand;
import com.revenga.rits.back.data.core.model.command.virtual.SetVentilationRegimeCommand;
import com.revenga.rits.back.incident.manager.service.EntitiesManager;

class SetVentilationTubeTask {

	// Values
	static final Long TASK_TYPE_PARAM_MODE = 1L;
	static final Long TASK_TYPE_PARAM_REGIME = 2L;

	org.apache.logging.log4j.Logger log;

	SetVentilationTubeTask(org.apache.logging.log4j.Logger log) {

		this.log = log;
	}

	List<Element> getElements(ImsIncidentTypeTask incidentTypeTask, ImsIncidentTypeStretch incidentTypeStretch) {

		List<Element> elements = null;

		if (incidentTypeTask != null && incidentTypeStretch != null ) {

			Set<Long> elementsId = EntitiesManager.getInstance().getElementByStretch(ElementType.ELEMENT_TYPE_TUBE,
					incidentTypeStretch.getStretchId());

			if (!CollectionUtils.isEmpty(elementsId)) {

				elements = new ArrayList<>();

				for (Long elementId : elementsId) {

					elements.add(new Element(ElementType.ELEMENT_TYPE_TUBE, elementId));
				}
			}

		}
		return elements;
	}

	List<Command> process(ImsIncidentReport incidentReport, ImsIncidentTypeTask incidentTypeTask,
			List<ImsIncidentTypeTaskValue> values) {

		List<Command> commands = null;
		Set<Long> elements = null;
		String valueMode = null;
		String valueRegime = null;

		if (incidentReport.getAffectionStretchId() != null && !CollectionUtils.isEmpty(values)) {

			ImsIncidentTypeTaskValue incidentTypeTaskValue = null;

			for (ImsIncidentTypeTaskValue value : values) {

				if (TASK_TYPE_PARAM_MODE.equals(value.getTaskTypeParamId())) {

					incidentTypeTaskValue = value;
					break;
				}
			}

			valueMode = incidentTypeTaskValue != null ? incidentTypeTaskValue.getValue() : null;

			incidentTypeTaskValue = null;
			for (ImsIncidentTypeTaskValue value : values) {

				if (TASK_TYPE_PARAM_REGIME.equals(value.getTaskTypeParamId())) {

					incidentTypeTaskValue = value;
					break;
				}
			}

			valueRegime = incidentTypeTaskValue != null ? incidentTypeTaskValue.getValue() : null;

			if (valueMode != null || valueRegime != null) {

				elements = EntitiesManager.getInstance().getElementByStretch(ElementType.ELEMENT_TYPE_TUBE,
						incidentReport.getAffectionStretchId());

				if (!CollectionUtils.isEmpty(elements)) {

					for (Long elementId : elements) {

						if (valueMode != null) {

							try {
								SetVentilationModeCommand setVentilationModeCommand = new SetVentilationModeCommand();

								setVentilationModeCommand.setElementTypeId(ElementType.ELEMENT_TYPE_TUBE);
								setVentilationModeCommand.setElementId(elementId);
								setVentilationModeCommand.setMode(Long.parseLong(valueMode));

								if (commands == null) {
									commands = new ArrayList<>();
								}
								commands.add(setVentilationModeCommand);

							} catch (NumberFormatException e) {

								log.error(e.getMessage());
								log.debug(ExceptionUtils.getStackTrace(e));
							}
						}

						if (valueRegime != null) {

							try {
								SetVentilationRegimeCommand setVentilationRegimeCommand = new SetVentilationRegimeCommand();

								setVentilationRegimeCommand.setElementTypeId(ElementType.ELEMENT_TYPE_TUBE);
								setVentilationRegimeCommand.setElementId(elementId);
								setVentilationRegimeCommand.setRegime(Long.parseLong(valueRegime));

								if (commands == null) {
									commands = new ArrayList<>();
								}
								commands.add(setVentilationRegimeCommand);

							} catch (NumberFormatException e) {

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
