import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.tuple.Pair;

import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.ElementValue;
import com.revenga.rits.back.equipment.manager.service.EntitiesManager;
import com.revenga.rits.back.data.core.model.command.SignallingCommand;

/**
 *
 * Reset.groovy: Send reset alarms when barrier not low
 *
 */
class Apagar {

	// ID Elements Params
	static final Long PARAM_COMMAND_SIGNALLING = 1L;
	static final Long ELEMENT_TYPE_PARAM_ID = 7L;
	static final String CHANGE_VALUE = "false";

	org.apache.logging.log4j.Logger log;
	
	Apagar(org.apache.logging.log4j.Logger log) {

		this.log = log;
	}
	
	boolean process(Element element, List<Pair<ElementValue, ElementValue>> elementValues) {
		
		try {

			ElementValue newElementValue;
			
			for (Pair<ElementValue, ElementValue> pair : elementValues) {
				newElementValue = pair.getRight();
				
				if (newElementValue.getElementTypeParamId() == ELEMENT_TYPE_PARAM_ID && newElementValue.getValue().equals(CHANGE_VALUE))
					EntitiesManager.getInstance().sendCommand(rellenaSignallingCommand(element));
			}	
			return true;
			
		} catch (Exception e) {
			
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}
		
		return false;
	}
	
	SignallingCommand rellenaSignallingCommand(Element element){
		SignallingCommand signallingCommand = new SignallingCommand();
		
		signallingCommand.setSignallingCommandId(PARAM_COMMAND_SIGNALLING);
		signallingCommand.setElementTypeId(element.getElementTypeId());
		signallingCommand.setElementId(element.getId());

		return signallingCommand;
	}
}
