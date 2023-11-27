import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;

import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.ElementHierarchy;
import com.revenga.rits.back.data.core.model.ElementType;
import com.revenga.rits.back.data.core.model.ImsIncidentReport;
import com.revenga.rits.back.data.core.model.ImsIncidentTypeTask;
import com.revenga.rits.back.data.core.model.ImsIncidentTypeTaskValue;
import com.revenga.rits.back.data.core.model.command.Command;
import com.revenga.rits.back.data.core.model.command.virtual.SetFansStopCommand;
import com.revenga.rits.back.incident.manager.service.EntitiesManager;

class StopAllFansInTunnelTask {

	org.apache.logging.log4j.Logger log;

	StopAllFansInTunnelTask(org.apache.logging.log4j.Logger log) {

		this.log = log;
	}

	List<Command> process(ImsIncidentReport incidentReport, ImsIncidentTypeTask incidentTypeTask,
			List<ImsIncidentTypeTaskValue> values) {

		List<Command> commands = null;
		Set<Long> elements = null;

		if (incidentReport.getAffectionStretchId() != null) {

			elements = EntitiesManager.getInstance().getElementByStretch(ElementType.ELEMENT_TYPE_VENTILATION,
					incidentReport.getAffectionStretchId());

			if (!CollectionUtils.isEmpty(elements)) {

				for (Long elementId : elements) {

					List<ElementHierarchy> elementHierarchies = EntitiesManager.getInstance().getsByParent(
							ElementType.ELEMENT_TYPE_VENTILATION, elementId, ElementType.ELEMENT_TYPE_TUBE);

					if (!CollectionUtils.isEmpty(elementHierarchies)) {

						for (ElementHierarchy elementHierarchie : elementHierarchies) {

							List<ElementHierarchy> hierarchiesTubeFans = EntitiesManager.getInstance().getsByParent(
									ElementType.ELEMENT_TYPE_TUBE, elementHierarchie.getChildElementId(),
									ElementType.ELEMENT_TYPE_FAN);

							if (!CollectionUtils.isEmpty(hierarchiesTubeFans)) {

								SetFansStopCommand setFansStopCommand = new SetFansStopCommand();

								setFansStopCommand.setElementTypeId(ElementType.ELEMENT_TYPE_TUBE);
								setFansStopCommand.setElementId(elementHierarchie.getChildElementId());

								for (ElementHierarchy hierarchiesTubeFan : hierarchiesTubeFans) {

									setFansStopCommand.addElement(new Element(ElementType.ELEMENT_TYPE_FAN,
											hierarchiesTubeFan.getChildElementId()));
								}

								if (commands == null) {
									commands = new ArrayList<>();
								}
								commands.add(setFansStopCommand);
							}

						}

					}

				}
			}
		}
		return commands;
	}
}
