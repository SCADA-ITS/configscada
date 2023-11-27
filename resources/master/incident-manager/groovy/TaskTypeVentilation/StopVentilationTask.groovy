import java.util.ArrayList;
import java.util.Set;
import java.util.List;

import com.revenga.rits.back.data.core.model.ElementState;
import com.revenga.rits.back.data.core.model.ElementType;
import com.revenga.rits.back.data.core.model.ElementTypeState;
import com.revenga.rits.back.data.core.model.ImsIncidentReport;
import com.revenga.rits.back.data.core.model.ImsIncidentTypeTask;
import com.revenga.rits.back.data.core.model.ImsIncidentTypeTaskValue;
import com.revenga.rits.back.data.core.model.command.Command;
import com.revenga.rits.back.data.core.model.command.virtual.SetElementStatesToVentilationCommand;
import com.revenga.rits.back.incident.manager.service.EntitiesManager;

class StopVentilationTask {

	org.apache.logging.log4j.Logger log;
	
	StopVentilationTask(org.apache.logging.log4j.Logger log) {

		this.log = log;
	}

	List<Command> process(ImsIncidentReport incidentReport, ImsIncidentTypeTask incidentTypeTask, List<ImsIncidentTypeTaskValue> values) {

		List<Command> commands = null;

		if (incidentReport.getAffectionStretchId() != null) {

			Set<Long> elements = EntitiesManager.getInstance()
					.getElementByStretch(ElementType.ELEMENT_TYPE_VENTILATION, incidentReport.getAffectionStretchId());

			for (Long elementId : elements) {

				SetElementStatesToVentilationCommand setElementStatesToVentilationCommand = new SetElementStatesToVentilationCommand();

				ElementState elementState = new ElementState();
				elementState.setElementTypeId(ElementType.ELEMENT_TYPE_VENTILATION);
				elementState.setElementId(elementId);
				elementState.setStateId(ElementTypeState.STATE_STOPPED);
				
				setElementStatesToVentilationCommand.addElementState(elementState);

				if (commands == null) {
					commands = new ArrayList<>();
				}
				commands.add(setElementStatesToVentilationCommand);
			}
		}

		return commands;
	}
}
