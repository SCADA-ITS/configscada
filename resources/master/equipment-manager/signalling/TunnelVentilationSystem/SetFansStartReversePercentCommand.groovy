import java.util.List;

import com.revenga.rits.back.equipment.manager.helper.SignallingCommandHelper;
import com.revenga.rits.back.data.core.model.command.Command;
import com.revenga.rits.back.data.core.model.PmCommandElement;
import com.revenga.rits.back.data.core.model.CommandElementType
import com.revenga.rits.back.data.core.model.CommandElementTypeParam

class SetFansStartReversePercentCommand {

	org.apache.logging.log4j.Logger log;

		static final Long ELEMENT_TYPE_ID = 9L;
		static final Long COMMAND_ELEMENT_TYPE_ID = 10L;
		static final Long COMMAND_ELEMENT_TYPE_PARAM_ID = 1L;
		static final Long DATA_TYPE_ID = 1L;


	SetFansStartReversePercentCommand(org.apache.logging.log4j.Logger log) {

		this.log = log;
	}

	List<Command> process(PmCommandElement command) {
		
		CommandElementType commandElementType = new CommandElementType();
		commandElementType.setId(COMMAND_ELEMENT_TYPE_ID);
		commandElementType.setElementTypeId(ELEMENT_TYPE_ID);
		commandElementType.setCommandElementTypeParams(new ArrayList<>());
		
		CommandElementTypeParam commandElementTypeParam = new CommandElementTypeParam();
		commandElementTypeParam.setCommandElementTypeId(COMMAND_ELEMENT_TYPE_ID);
		commandElementTypeParam.setElementTypeId(ELEMENT_TYPE_ID);
		commandElementTypeParam.setId(COMMAND_ELEMENT_TYPE_PARAM_ID);
		commandElementTypeParam.setDataTypeId(DATA_TYPE_ID);
		
		commandElementType.getCommandElementTypeParams().add(commandElementTypeParam);
		
		
		return SignallingCommandHelper.get(command, commandElementType);
	}
	
}
