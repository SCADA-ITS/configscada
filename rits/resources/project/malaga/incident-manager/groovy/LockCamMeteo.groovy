import java.util.ArrayList;
import java.util.Set;
import java.util.List;

import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.ElementType;
import com.revenga.rits.back.data.core.model.ElementHierarchy;
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
import com.revenga.rits.back.incident.manager.service.IncidentEntitiesManager;
import com.revenga.rits.back.data.core.model.ImsIncidentReportAlarm;

class LockCamera {

	static final Long ELEMENT_TYPE_CAMERA = 2L;
	static final Long LOCK_CAMERA_COMMAND = 1L;
	static final Long ELEMENT_ID_CAMERA_METEO = 43L;  //S1TN-DOM-009

	org.apache.logging.log4j.Logger log;

	LockCamera(org.apache.logging.log4j.Logger log) {

		this.log = log;
	}

	List<Command> process(ImsIncidentReport incidentReport, ImsIncidentTypeTask incidentTypeTask, List<ImsIncidentTypeTaskValue> values) {
		List<Command> commands = null;
		
		if (commands == null) {
			commands = new ArrayList<>();
		}	
		commands.add(rellenaSignallingCommand(ELEMENT_ID_CAMERA_METEO));

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
