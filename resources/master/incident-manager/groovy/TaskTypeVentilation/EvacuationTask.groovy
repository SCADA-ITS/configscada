import java.util.ArrayList;
import java.util.Set;
import java.util.List;

import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.ElementType;
import com.revenga.rits.back.data.core.model.ImsIncidentReport;
import com.revenga.rits.back.data.core.model.ImsIncidentTypeTask;
import com.revenga.rits.back.data.core.model.ImsIncidentTypeTaskValue;
import com.revenga.rits.back.data.core.model.Stretch;
import com.revenga.rits.back.data.core.model.command.Command;
import com.revenga.rits.back.data.core.model.command.virtual.SetFireAlarmCommand;
import com.revenga.rits.back.incident.manager.service.EntitiesManager;

class EvacuationTask {

	org.apache.logging.log4j.Logger log;

	EvacuationTask(org.apache.logging.log4j.Logger log) {

		this.log = log;
	}

	List<Command> process(ImsIncidentReport incidentReport, ImsIncidentTypeTask incidentTypeTask, List<ImsIncidentTypeTaskValue> values) {

		List<Command> commands = null;

		if (incidentReport.getAffectionStretchId() != null) {

			Set<Long> elements = EntitiesManager.getInstance()
					.getElementByStretch(ElementType.ELEMENT_TYPE_VENTILATION, incidentReport.getAffectionStretchId());

			for (Long elementId : elements) {

				SetFireAlarmCommand setFireAlarmCommand = new SetFireAlarmCommand();

				Element element = new Element();
				element.setId(elementId);
				element.setElementTypeId(ElementType.ELEMENT_TYPE_VENTILATION);

				Stretch stretch = new Stretch();
				stretch.setId(incidentReport.getAffectionStretchId());

				setFireAlarmCommand.setElement(element);
				setFireAlarmCommand.setStretch(stretch);

				if (commands == null) {
					commands = new ArrayList<>();
				}
				commands.add(setFireAlarmCommand);
			}
		}

		return commands;
	}
}
