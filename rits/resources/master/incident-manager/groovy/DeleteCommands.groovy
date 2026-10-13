import java.util.ArrayList;
import java.util.List;

import com.revenga.rits.back.data.core.model.ImsIncidentReport;
import com.revenga.rits.back.data.core.model.ImsIncidentTypeTask;
import com.revenga.rits.back.data.core.model.ImsIncidentTypeTaskValue;
import com.revenga.rits.back.data.core.model.command.Command;
import com.revenga.rits.back.data.core.model.command.SetDeleteCommandsCommand;

class DeleteCommands {

	org.apache.logging.log4j.Logger log;

	DeleteCommands(org.apache.logging.log4j.Logger log) {

		this.log = log;
	}

	List<Command> process(ImsIncidentReport incidentReport, ImsIncidentTypeTask incidentTypeTask,
			List<ImsIncidentTypeTaskValue> values) {
			
		List<Command> commands = new ArrayList<>();
		commands.add(new SetDeleteCommandsCommand());

		return commands;
	}
}
