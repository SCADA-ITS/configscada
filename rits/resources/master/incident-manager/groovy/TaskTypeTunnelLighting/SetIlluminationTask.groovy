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
import com.revenga.rits.back.data.core.model.command.virtual.SetIlluminationModeCommand;
import com.revenga.rits.back.data.core.model.command.virtual.SetIlluminationRegimeCommand;
import com.revenga.rits.back.incident.manager.service.EntitiesManager;

class SetIlluminationTask {

	// Values
	static final Long TASK_TYPE_PARAM_MODE = 1L;
	static final Long TASK_TYPE_PARAM_REGIME = 2L;

	org.apache.logging.log4j.Logger log;

	SetIlluminationTask(org.apache.logging.log4j.Logger log) {

		this.log = log;
	}

	List<Element> getElements(ImsIncidentTypeTask incidentTypeTask, ImsIncidentTypeStretch incidentTypeStretch) {

		List<Element> elements = null;

		if (incidentTypeTask != null && incidentTypeStretch != null ) {

			Set<Long> illuminationsId = EntitiesManager.getInstance().getElementByStretch(ElementType.ELEMENT_TYPE_ILLUMINATION,
					incidentTypeStretch.getStretchId());

			if (!CollectionUtils.isEmpty(illuminationsId)) {

				elements = new ArrayList<>();

				for (Long illuminationId : illuminationsId) {

					List<ElementHierarchy> elementHierarchies = EntitiesManager.getInstance().getsByParent(
							ElementType.ELEMENT_TYPE_ILLUMINATION, illuminationId, ElementType.ELEMENT_TYPE_TUBE);

					if (!CollectionUtils.isEmpty(elementHierarchies)) {

						for (ElementHierarchy elementHierarchie : elementHierarchies) {

							elements.add(new Element(ElementType.ELEMENT_TYPE_TUBE, elementHierarchie.getChildElementId()));
						}
					}

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

				elements = EntitiesManager.getInstance().getElementByStretch(ElementType.ELEMENT_TYPE_ILLUMINATION,
						incidentReport.getAffectionStretchId());

				if (!CollectionUtils.isEmpty(elements)) {

					for (Long elementId : elements) {

						List<ElementHierarchy> elementHierarchies = EntitiesManager.getInstance().getsByParent(
								ElementType.ELEMENT_TYPE_ILLUMINATION, elementId, ElementType.ELEMENT_TYPE_TUBE);

						if (!CollectionUtils.isEmpty(elementHierarchies)) {

							for (ElementHierarchy elementHierarchie : elementHierarchies) {

								if (valueMode != null) {

									try {
										SetIlluminationModeCommand setIlluminationModeCommand = new SetIlluminationModeCommand();

										setIlluminationModeCommand.setElementTypeId(ElementType.ELEMENT_TYPE_TUBE);
										setIlluminationModeCommand.setElementId(elementHierarchie.getChildElementId());
										setIlluminationModeCommand.setMode(Long.parseLong(valueMode));

										if (commands == null) {
											commands = new ArrayList<>();
										}
										commands.add(setIlluminationModeCommand);

									} catch (NumberFormatException e) {

										log.error(e.getMessage());
										log.debug(ExceptionUtils.getStackTrace(e));
									}
								}

								if (valueRegime != null) {

									try {
										SetIlluminationRegimeCommand setIlluminationRegimeCommand = new SetIlluminationRegimeCommand();

										setIlluminationRegimeCommand.setElementTypeId(ElementType.ELEMENT_TYPE_TUBE);
										setIlluminationRegimeCommand.setElementId(elementHierarchie.getChildElementId());
										setIlluminationRegimeCommand.setRegime(Long.parseLong(valueRegime));

										if (commands == null) {
											commands = new ArrayList<>();
										}
										commands.add(setIlluminationRegimeCommand);

									} catch (NumberFormatException e) {

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

		return commands;
	}
}
