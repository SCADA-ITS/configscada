
import java.util.List;
import com.revenga.rits.back.equipment.manager.helper.SignallingCommandHelper;
import com.revenga.rits.back.data.core.model.command.Command;
import com.revenga.rits.back.data.core.model.PmCommandElement;

class SetFansStartReverseCommand {

	org.apache.logging.log4j.Logger log;
		static final Long COMMAND_ELEMENT_TYPE_ID = 3L;

	SetFansStartReverseCommand(org.apache.logging.log4j.Logger log) {
		
		this.log = log;
	}
	List<Command> process(PmCommandElement command) {
		
		return SignallingCommandHelper.get(command, COMMAND_ELEMENT_TYPE_ID);
	}
	
}