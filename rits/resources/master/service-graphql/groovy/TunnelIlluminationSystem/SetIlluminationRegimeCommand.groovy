import java.util.ArrayList;
import java.util.List;
import com.revenga.rits.back.front.core.model.helper.command.SetIlluminationRegimeCommandHelper;
import com.revenga.rits.back.front.core.model.vo.CommandElementVO;

import com.revenga.rits.back.data.core.model.command.Command;

class SetIlluminationRegimeCommand {

	org.apache.logging.log4j.Logger log;

	SetIlluminationRegimeCommand(org.apache.logging.log4j.Logger log) {

		this.log = log;
	}

	List<Command> process(CommandElementVO command) {

		List<Command> commands = null;

		Command cmd = SetIlluminationRegimeCommandHelper.get(command);

		if(cmd != null){

			commands = new ArrayList<> ();
			commands.add(cmd);
		}

		return commands;
	}
}
