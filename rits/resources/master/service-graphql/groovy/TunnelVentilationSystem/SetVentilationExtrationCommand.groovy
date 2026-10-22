import java.util.ArrayList;
import java.util.List;
import com.revenga.rits.back.front.core.model.helper.command.SetElementStatesToVentilationCommandHelper;
import com.revenga.rits.back.front.core.model.vo.CommandElementVO;

import com.revenga.rits.back.data.core.model.command.Command;
import com.revenga.rits.back.data.core.model.ElementTypeState;

class SetVentilationExtrationCommand {

	org.apache.logging.log4j.Logger log;

	SetVentilationExtrationCommand(org.apache.logging.log4j.Logger log) {

		this.log = log;
	}

	List<Command> process(CommandElementVO command) {

		List<Command> commands = null;

		Command cmd = SetElementStatesToVentilationCommandHelper.get(command.getElementId(), ElementTypeState.STATE_EXTRACTION);

		if(cmd != null){

			commands = new ArrayList<> ();
			commands.add(cmd);
		}

		return commands;
	}
}
