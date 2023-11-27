import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.map.HashedMap;

import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.ElementValue;
import com.revenga.rits.back.data.core.model.ImsIncidentReport;
import com.revenga.rits.back.data.core.model.ImsIncidentTypeStretch;
import com.revenga.rits.back.data.core.model.ImsIncidentTypeTask;
import com.revenga.rits.back.data.core.model.ImsIncidentTypeTaskValue;
import com.revenga.rits.back.data.core.model.command.Command;
import com.revenga.rits.back.data.core.model.command.SignallingCommand;
import com.revenga.rits.back.incident.manager.service.EntitiesManager;

class SendPressurizationOff {

	static final Long ELEMENT_TYPE_PRESSURIZACION = 32L;
	static final Long COMMAND_ELEMENT_TYPE_PRESSURIZACION_OFF = 2L;
	

	org.apache.logging.log4j.Logger log;

	SendPressurizationOff(org.apache.logging.log4j.Logger log) {

		this.log = log;
	}

	List<Element> getElements(ImsIncidentTypeTask incidentTypeTask, ImsIncidentTypeStretch incidentTypeStretch) {

		List<Element> elements = new ArrayList<>();

		if (incidentTypeTask != null && incidentTypeStretch != null && incidentTypeStretch.getStretchId() != null) {

			List<Long> stretchIds = new ArrayList<>();

			stretchIds.add(incidentTypeStretch.getStretchId());
			elements = EntitiesManager.getInstance().getElementByStretchs(ELEMENT_TYPE_PRESSURIZACION, stretchIds);
		}
		return elements;
	}

	List<Command> process(ImsIncidentReport incidentReport, ImsIncidentTypeTask incidentTypeTask,
			List<ImsIncidentTypeTaskValue> values) {

		List<Command> commands = new ArrayList<>();

		if (incidentReport.getAffectionStretchId() != null) {

			List<Long> stretchIds = new ArrayList<>();

			stretchIds.add(incidentReport.getAffectionStretchId());
			Set<Long> elements = EntitiesManager.getInstance().getElementByStretch(ELEMENT_TYPE_PRESSURIZACION,
					incidentReport.getAffectionStretchId());

			if (!CollectionUtils.isEmpty(elements)) {

				for (Long elementId : elements) {

					SignallingCommand signallingCommand = new SignallingCommand();
					signallingCommand.setElementTypeId(ELEMENT_TYPE_PRESSURIZACION);
					signallingCommand.setSignallingCommandId(COMMAND_ELEMENT_TYPE_PRESSURIZACION_OFF);
					signallingCommand.setElementId(elementId);

					commands.add(signallingCommand);		
				}
			}
		}

		return commands;
	}
}
