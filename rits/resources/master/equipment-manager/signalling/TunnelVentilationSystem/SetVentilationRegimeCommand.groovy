import java.util.ArrayList;
import java.util.List;
import com.revenga.rits.back.equipment.manager.helper.SetVentilationRegimeCommandHelper;
import com.revenga.rits.back.data.core.model.command.Command;
import com.revenga.rits.back.data.core.model.PmCommandElement;

class SetVentilationRegimeCommand {

	org.apache.logging.log4j.Logger log;

	SetVentilationRegimeCommand(org.apache.logging.log4j.Logger log) {

		this.log = log;
	}

	List<Command> process(PmCommandElement command) {

		List<Command> commands = null;

		Command cmd = SetVentilationRegimeCommandHelper.get(command);

		if(cmd != null){

			commands = new ArrayList<> ();
			commands.add(cmd);
		}

		return commands;
	}
}
