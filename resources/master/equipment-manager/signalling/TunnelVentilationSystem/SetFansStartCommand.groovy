import java.util.ArrayList;
import java.util.List;
import com.revenga.rits.back.equipment.manager.helper.SetFansStartCommandHelper;
import com.revenga.rits.back.data.core.model.command.Command;
import com.revenga.rits.back.data.core.model.PmCommandElement;

class SetFansStartCommand {

	org.apache.logging.log4j.Logger log;

	SetFansStartCommand(org.apache.logging.log4j.Logger log) {

		this.log = log;
	}

	List<Command> process(PmCommandElement command) {

		List<Command> commands = null;

		Command cmd = SetFansStartCommandHelper.get(command);

		if(cmd != null){

			commands = new ArrayList<> ();
			commands.add(cmd);
		}

		return commands;
	}
}
