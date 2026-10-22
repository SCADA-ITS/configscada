import java.util.ArrayList;
import java.util.Set;
import java.util.List;
import org.apache.commons.collections4.CollectionUtils;

import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.ElementType;
import com.revenga.rits.back.data.core.model.ImsIncidentReport;
import com.revenga.rits.back.data.core.model.ImsIncidentTypeTask;
import com.revenga.rits.back.data.core.model.ImsIncidentTypeTaskValue;
import com.revenga.rits.back.data.core.model.ImsIncidentTypeStretch;
import com.revenga.rits.back.data.core.model.command.SignallingCommand;
import com.revenga.rits.back.data.core.model.command.Command;
import com.revenga.rits.back.incident.manager.service.EntitiesManager;

class OpenToxicGate {

	static final Long ELEMENT_TYPE_TOXIC_CONTROL = 48L;
	static final Long OPEN_GATE_COMMAND = 1L;

	org.apache.logging.log4j.Logger log;

	OpenToxicGate(org.apache.logging.log4j.Logger log) {

		this.log = log;
	}
	List<Element> getElements(ImsIncidentTypeTask incidentTypeTask, ImsIncidentTypeStretch incidentTypeStretch) {

		List<Element> elements = new ArrayList<>();

		if (incidentTypeTask != null && incidentTypeStretch != null && incidentTypeStretch.getStretchId() != null) {

			List<Long> stretchIds = new ArrayList<>();

			stretchIds.add(incidentTypeStretch.getStretchId());
			elements = EntitiesManager.getInstance().getElementByStretchs(ELEMENT_TYPE_TOXIC_CONTROL, stretchIds);
		}
		return elements;
	}

	List<Command> process(ImsIncidentReport incidentReport, ImsIncidentTypeTask incidentTypeTask, List<ImsIncidentTypeTaskValue> values) {
		List<Command> commands = null;

		if (incidentReport.getAffectionStretchId() != null) {

			Set<Long> elements = EntitiesManager.getInstance().getElementByStretch(ELEMENT_TYPE_TOXIC_CONTROL, incidentReport.getAffectionStretchId());
			
			if (commands == null) {
				commands = new ArrayList<>();
			}
			if (!CollectionUtils.isEmpty(elements)) {

				for (Long elementId : elements) {

					commands.add(rellenaSignallingCommand(elementId));		
				}
			}
		}

		return commands;
	}
	
	SignallingCommand rellenaSignallingCommand(Long element){
		SignallingCommand signallingCommand = new SignallingCommand();
		
		signallingCommand.setSignallingCommandId(OPEN_GATE_COMMAND);
		signallingCommand.setElementTypeId(ELEMENT_TYPE_TOXIC_CONTROL);
		signallingCommand.setElementId(element);
		
		return signallingCommand;
	}
}
