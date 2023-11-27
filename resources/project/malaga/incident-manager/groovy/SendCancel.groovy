import java.util.List;

import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.command.Command;
import com.revenga.rits.back.data.core.model.command.SignallingCommand;
import com.revenga.rits.back.data.core.model.ImsIncidentReport;
import com.revenga.rits.back.data.core.model.ImsIncidentReportAlarm;
import com.revenga.rits.back.data.core.model.ImsIncidentTypeTask;
import com.revenga.rits.back.data.core.model.ImsIncidentTypeTaskValue;
import com.revenga.rits.back.incident.manager.service.IncidentEntitiesManager;

class SendCancel {

	static final Long ELEMENT_TYPE_TUBE = 2000L;
	static final Long CANCEL_COMMAND = 17L;
	static final Long DEFAULT_ELEMENT_ID = 1L;

	org.apache.logging.log4j.Logger log;

	SendCancel(org.apache.logging.log4j.Logger log) {

		this.log = log;
	}

	List<Command> process(ImsIncidentReport incidentReport, ImsIncidentTypeTask incidentTypeTask, List<ImsIncidentTypeTaskValue> values) {
		List<Command> commands = null;
		List<ImsIncidentReportAlarm> incidentReportAlarms = IncidentEntitiesManager.getInstance()
			.getIncidentReportAlarms(incidentReport.getId());
			
		if (incidentReportAlarms.isEmpty()){
		
		}else{
			SignallingCommand signallingCommand = new SignallingCommand();
			
			signallingCommand.setSignallingCommandId(CANCEL_COMMAND);
			signallingCommand.setElementTypeId(ELEMENT_TYPE_TUBE);
			signallingCommand.setElementId(DEFAULT_ELEMENT_ID);
			
			if (commands == null) {
				
				commands = new ArrayList<>();
			}
			
			commands.add(signallingCommand);
		}	
		
		return commands;
	}
}
