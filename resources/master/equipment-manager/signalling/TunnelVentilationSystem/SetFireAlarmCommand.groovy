import java.util.ArrayList;
import java.util.List;
import com.revenga.rits.back.equipment.manager.helper.SetFireAlarmCommandHelper;
import com.revenga.rits.back.data.core.model.command.Command;
import com.revenga.rits.back.data.core.model.PmCommandElement;

class SetFireAlarmCommand {

	org.apache.logging.log4j.Logger log;

	SetFireAlarmCommand(org.apache.logging.log4j.Logger log) {

		this.log = log;
	}

	List<Command> process(PmCommandElement command) {

		List<Command> commands = null;

		Command cmd = SetFireAlarmCommandHelper.get(command);

		if(cmd != null){

			commands = new ArrayList<> ();
			commands.add(cmd);
		}

		return commands;
	}
}
