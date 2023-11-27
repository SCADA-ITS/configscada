import java.util.ArrayList;
import java.util.Set;
import java.util.List;

import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.ElementType;
import com.revenga.rits.back.data.core.model.ImsIncidentReport;
import com.revenga.rits.back.data.core.model.ImsIncidentTypeTask;
import com.revenga.rits.back.data.core.model.ImsIncidentTypeTaskValue;
import com.revenga.rits.back.data.core.model.Stretch;
import com.revenga.rits.back.data.core.model.CommandElement;
import com.revenga.rits.back.data.core.model.CommandElementValue;
import com.revenga.rits.back.data.core.model.CommandElementTypeParam;
import com.revenga.rits.back.data.core.model.command.SignallingCommand;
import com.revenga.rits.back.data.core.model.command.SignallingCommand.SignallingParam;
import com.revenga.rits.back.data.core.model.command.Command;
import com.revenga.rits.back.data.core.model.command.virtual.SetFireAlarmCommand;
import com.revenga.rits.back.incident.manager.service.EntitiesManager;

class LockCamera {

	static final Long ELEMENT_TYPE_CAMERA = 2L;
	static final Long LOCK_CAMERA_COMMAND = 1L;

	org.apache.logging.log4j.Logger log;

	LockCamera(org.apache.logging.log4j.Logger log) {

		this.log = log;
	}

	List<Command> process(ImsIncidentReport incidentReport, ImsIncidentTypeTask incidentTypeTask, List<ImsIncidentTypeTaskValue> values) {

		List<Command> commands = null;

		if (incidentReport.getAffectionStretchId() != null) {

			Set<Long> elements = EntitiesManager.getInstance()
					.getElementByStretch(ElementType.ELEMENT_TYPE_CAMERA, incidentReport.getAffectionStretchId());
			
			if (commands == null) {
				commands = new ArrayList<>();
			}		
			commands.add(rellenaSignallingCommand(elements.getAt(0)));
		}

		return commands;
	}
	
	SignallingCommand rellenaSignallingCommand(Long element){
		SignallingCommand signallingCommand = new SignallingCommand();
		
		signallingCommand.setSignallingCommandId(LOCK_CAMERA_COMMAND);
		signallingCommand.setElementTypeId(ELEMENT_TYPE_CAMERA);
		signallingCommand.setElementId(element);
		
		return signallingCommand;
	}
}
