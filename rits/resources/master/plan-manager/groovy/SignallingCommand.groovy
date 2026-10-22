import org.apache.commons.lang3.exception.ExceptionUtils
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import java.util.ArrayList;
import java.util.List;

import com.revenga.rits.back.data.core.model.helper.command.SignallingCommandHelper;
import com.revenga.rits.back.data.core.model.command.Command;
import com.revenga.rits.back.data.core.model.CommandElement;
import com.revenga.rits.back.data.core.model.CommandElementType;
import com.revenga.rits.back.data.core.model.LogLevel
import com.revenga.rits.back.data.core.model.LogSubtype
import com.revenga.rits.back.data.core.model.LogType
import com.revenga.rits.back.data.core.model.User
import com.revenga.rits.back.entities.lib.helper.AuditHelper

class SignallingCommand {

	org.apache.logging.log4j.Logger log;

	SignallingCommand(org.apache.logging.log4j.Logger log) {

		this.log = log;
	}

	List<Command> process(CommandElement command) {

		List<Command> commands = null;

		Command signallingCommand = SignallingCommandHelper.get(command);

		if(signallingCommand != null){

			commands = new ArrayList<> ();
			commands.add(signallingCommand);

			try {

				if(command.commandElementType != null &&   command.element != null){

					AuditHelper.Audit(LogType.LOG_TYPE_COMMANDS, LogSubtype.LOG_SUBTYPE_EXECUTE_COMMAND,
							User.USER_SYSTEM, LogLevel.INFO, command.commandElementType.getLabelAlias(), command.element.getAlias(),
							new ObjectMapper().writeValueAsString(signallingCommand.getSignallingParams()));
				}
			} catch (JsonProcessingException e) {

				log.error(e.getMessage());
				log.debug(ExceptionUtils.getStackTrace(e));
			}
		}

		return commands;
	}
}
