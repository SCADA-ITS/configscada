import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.ElementHierarchy;
import com.revenga.rits.back.data.core.model.ElementType;
import com.revenga.rits.back.data.core.model.ImsIncidentReport;
import com.revenga.rits.back.data.core.model.ImsIncidentTypeStretch;
import com.revenga.rits.back.data.core.model.ImsIncidentTypeTask;
import com.revenga.rits.back.data.core.model.ImsIncidentTypeTaskValue;
import com.revenga.rits.back.data.core.model.command.Command;
import com.revenga.rits.back.data.core.model.command.virtual.SetIlluminationModeCommand;
import com.revenga.rits.back.data.core.model.command.virtual.SetIlluminationRegimeCommand;
import com.revenga.rits.back.incident.manager.service.EntitiesManager;

class SetIlluminationEvacuation {

	static final Long TASK_TYPE_PARAM_MODE = 1L;
	static final Long TASK_TYPE_PARAM_REGIME = 2L;

	org.apache.logging.log4j.Logger log;

	SetIlluminationEvacuation(org.apache.logging.log4j.Logger log) {

		this.log = log;
	}

	List<Element> getElements(ImsIncidentTypeTask incidentTypeTask, ImsIncidentTypeStretch incidentTypeStretch) {

		List<Element> elements = null;

		if (incidentTypeTask != null && incidentTypeStretch != null) {

			Set<Long> tubesId = EntitiesManager.getInstance().getElementByStretch(ElementType.ELEMENT_TYPE_TUBE,
					incidentTypeStretch.getStretchId());

			if (!CollectionUtils.isEmpty(tubesId)) {

				elements = new ArrayList<>();

				for (Long tubeId : tubesId) {
				
					log.debug("SetIlluminationEvacuation(getElements) tubo afectado 2000:" + tubeId);

					List<ElementHierarchy> elementHierarchies = EntitiesManager.getInstance().getsByChild(
							ElementType.ELEMENT_TYPE_ILLUMINATION, ElementType.ELEMENT_TYPE_TUBE, tubeId);

					if (!CollectionUtils.isEmpty(elementHierarchies)) {

						Long Id = elementHierarchies.get(0).getParentElementId();
						log.debug("SetIlluminationEvacuation(getElements) tunnel afectado 2002:" + Id);

						elements = new ArrayList<>();
						elements.add(new Element(ElementType.ELEMENT_TYPE_ILLUMINATION, Id));
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

				elements = EntitiesManager.getInstance().getElementByStretch(ElementType.ELEMENT_TYPE_TUBE,
						incidentReport.getAffectionStretchId());

				if (!CollectionUtils.isEmpty(elements)) {

					for (Long elementId : elements) {
						
						log.debug("SetIlluminationEvacuation(process) - El tubo afectado es 2000:" + elementId);

						List<ElementHierarchy> illuminationsId = EntitiesManager.getInstance().getsByChild(
								ElementType.ELEMENT_TYPE_ILLUMINATION, ElementType.ELEMENT_TYPE_TUBE, elementId);
								
						Long illuminationId = illuminationsId.get(0).getParentElementId();
						log.debug("SetIlluminationEvacuation(process) - El elemento de iluminación afectado es 2002:" + illuminationId);
						
						List<ElementHierarchy> elementHierarchies = EntitiesManager.getInstance().getsByParent(
								ElementType.ELEMENT_TYPE_ILLUMINATION, illuminationId, ElementType.ELEMENT_TYPE_TUBE);
						
						if (!CollectionUtils.isEmpty(elementHierarchies)) {

							for (ElementHierarchy elementHierarchie : elementHierarchies) {

								log.debug("SetIlluminationEvacuation(process) - El tubo sobre el que voy a actuar es 2000:" + elementHierarchie.getParentElementId());
								if (valueMode != null) {

									try {
										SetIlluminationModeCommand setIlluminationModeCommand = new SetIlluminationModeCommand();

										setIlluminationModeCommand.setElementTypeId(ElementType.ELEMENT_TYPE_TUBE);
										setIlluminationModeCommand.setElementId(elementHierarchie.getChildElementId());
										setIlluminationModeCommand.setMode(Long.parseLong(valueMode));
										
										log.debug("   SetIlluminationEvacuation(process) - Cambio el modo a valor:" + valueMode);

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
										setIlluminationRegimeCommand
												.setElementId(elementHierarchie.getChildElementId());
										setIlluminationRegimeCommand.setRegime(Long.parseLong(valueRegime));
										
										log.debug("   SetIlluminationEvacuation(process) - Cambio el regiemn a valor:" + valueRegime);

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
