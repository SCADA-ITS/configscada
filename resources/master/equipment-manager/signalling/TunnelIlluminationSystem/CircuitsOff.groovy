import java.util.ArrayList;
import java.util.List;
import com.revenga.rits.back.equipment.manager.helper.SetCircuitsOffCommandHelper;
import com.revenga.rits.back.data.core.model.command.Command;
import com.revenga.rits.back.data.core.model.PmCommandElement;

class SetCircuitsOffCommand {

	org.apache.logging.log4j.Logger log;

	SetCircuitsOffCommand(org.apache.logging.log4j.Logger log) {

		this.log = log;
	}

	List<Command> process(PmCommandElement command) {

		List<Command> commands = null;

		Command cmd = SetCircuitsOffCommandHelper.get(command != null ? command.getElementId() : null);

		if(cmd != null){

			commands = new ArrayList<> ();
			commands.add(cmd);
		}

		return commands;
	}
}
