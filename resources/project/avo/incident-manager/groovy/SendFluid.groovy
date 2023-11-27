import java.util.List;

import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.command.Command;
import com.revenga.rits.back.data.core.model.command.SignallingCommand;
import com.revenga.rits.back.data.core.model.command.SignallingCommand.SignallingParam;
import com.revenga.rits.back.data.core.model.ImsIncidentReport;
import com.revenga.rits.back.data.core.model.ImsIncidentReportAlarm;
import com.revenga.rits.back.data.core.model.ImsIncidentTypeTask;
import com.revenga.rits.back.data.core.model.ImsIncidentTypeTaskValue;
import com.revenga.rits.back.incident.manager.service.EntitiesManager;
import com.revenga.rits.back.incident.manager.service.IncidentEntitiesManager;

class SendFluid {

	static final Long TRAFFIC_STATE_COMMAND = 15L;
	static final Long ELEMENT_TYPE_TUBE = 2000L;
	static final String FLUID_VALUE = "1";
	static final Integer PARAM_ID_1 = 1;
	static final Integer DATA_TYPE_ALPHANUMERIC = 2;
	static final Long DEFAULT_ELEMENT_ID = 1L;

	org.apache.logging.log4j.Logger log;

	SendFluid(org.apache.logging.log4j.Logger log) {

		this.log = log;
	}

	List<Command> process(ImsIncidentReport incidentReport, ImsIncidentTypeTask incidentTypeTask, List<ImsIncidentTypeTaskValue> values) {
		List<Command> commands = null;
		List<ImsIncidentReportAlarm> incidentReportAlarms = IncidentEntitiesManager.getInstance()
			.getIncidentReportAlarms(incidentReport.getId());
		
		SignallingCommand signallingCommand = new SignallingCommand();
			
		signallingCommand.setSignallingCommandId(TRAFFIC_STATE_COMMAND);
		signallingCommand.setElementTypeId(ELEMENT_TYPE_TUBE);
		signallingCommand.setElementId(DEFAULT_ELEMENT_ID);
		
		List<SignallingParam> signallingParams = new ArrayList<>();
		SignallingParam signallingParam = new SignallingParam();

		signallingParam.setId(PARAM_ID_1);
		signallingParam.setType(DATA_TYPE_ALPHANUMERIC);
		signallingParam.setValue(FLUID_VALUE);

		signallingParams.add(signallingParam);
		
		signallingCommand.setSignallingParams(signallingParams);
		
		if (commands == null) {
			
			commands = new ArrayList<>();
		}
		
		commands.add(signallingCommand);
		
		return commands;
	}
}
