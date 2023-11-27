import java.util.ArrayList;
import java.util.List;
import com.revenga.rits.back.equipment.manager.helper.SetDaliIlluminationRegimeCommandHelper;
import com.revenga.rits.back.data.core.model.PmCommandElement;
import com.revenga.rits.back.data.core.model.command.Command;

class SetDaliIlluminationRegimeCommand {

	org.apache.logging.log4j.Logger log;

	SetDaliIlluminationRegimeCommand(org.apache.logging.log4j.Logger log) {

		this.log = log;
	}

	List<Command> process(PmCommandElement command) {

		List<Command> commands = null;

		Command cmd = SetDaliIlluminationRegimeCommandHelper.get(command);

		if(cmd != null){

			commands = new ArrayList<> ();
			commands.add(cmd);
		}

		return commands;
	}
}
