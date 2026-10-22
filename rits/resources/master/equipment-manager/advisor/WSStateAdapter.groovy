import org.apache.commons.lang3.exception.ExceptionUtils;

import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.ElementState;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.tuple.Pair;

import com.revenga.rits.back.data.core.model.DataType;
import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.ElementValue;
import com.revenga.rits.back.data.core.model.ElementHierarchy;
import com.revenga.rits.back.data.core.model.CommandElement;
import com.revenga.rits.back.data.core.model.CommandElementValue;
import com.revenga.rits.back.data.core.model.CommandElementTypeParam;
import com.revenga.rits.back.data.core.model.command.SignallingCommand;
import com.revenga.rits.back.data.core.model.command.SignallingCommand.SignallingParam;
import com.revenga.rits.back.equipment.manager.service.EntitiesManager;


/**
 *
 * WSStateAdapter.groovy: Groovy for turn off the associated VMS of WS when is down
 *
 */

class WSStateAdapter {

	
	
	// ID Elements type States
	static final Long STATE_UNKNOWN = 0L;
	static final Long STATE_UP = 1L;
	static final Long STATE_DOWN = 2L;
	static final Long STATE_ALARMS = 3L;
	
	
	// ID Elements Params
	static final Long PARAM_TYPE_MEASURE = 2L;
	static final Long PARAM_MEASURE_SURFACE_TEMPERATURE = 1L;
	static final Long PARAM_MEASURE_AIR_TEMPERATURE = 17L;
	static final Long PARAM_COMMAND_SIGNALLING = 5L;
	static final Long ELEMENT_TYPE_VMS_TEMPERATURE = 31L;
	static final Long PARAM_ID_1 = 1L;
	
	
	org.apache.logging.log4j.Logger log;
	
	WSStateAdapter(org.apache.logging.log4j.Logger log) {

		this.log = log;
	}
	
	boolean process(Element element, ElementState elementState) {
		
		try {

			log.debug("ChangeStateSample::process(Element:" + element.getElementTypeId() + ":" + element.getId()
					+ " => " + elementState.getPreviousStateId() + " [Old] " + elementState.getStateId()+ " [New])");
			if(elementState.getStateId() == STATE_DOWN)	
			{
				//Creo el comando que realizará la señalizacion
				log.debug(elementState);
				List<CommandElement> commandElementList = rellenaCommandElement(element);
				log.debug(commandElementList);
				for(CommandElement commandElement : commandElementList){
					log.debug(commandElement);
					
					EntitiesManager.getInstance().sendCommand(rellenaSignallingCommand(commandElement));
				}
			}
			return true;
			
		} catch (Exception e) {
			
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}
		
		return false;
	}
	List<CommandElement> rellenaCommandElement(Element element){
		//Creo el comando que realizará la señalizacion
		List<ElementHierarchy> childs;
		childs = EntitiesManager.getInstance().getsByParent(element.getElementTypeId(), element.getId(), false);
		
		List<CommandElement> commandElementList = new ArrayList<>();
		
		for(ElementHierarchy child : childs){
			
			int elementId_son = child.getChildElementId();			

			CommandElement commandElement = new CommandElement();
			commandElement.setCommandElementTypeId(PARAM_COMMAND_SIGNALLING);
			commandElement.setElementTypeId(ELEMENT_TYPE_VMS_TEMPERATURE);
			commandElement.setElementId(elementId_son);
			commandElementList.add(commandElement);
		}
	
		
		return commandElementList;
	}
	
	
	SignallingCommand rellenaSignallingCommand(CommandElement commandElement){
		SignallingCommand signallingCommand = new SignallingCommand();
		
		signallingCommand.setSignallingCommandId(commandElement.getCommandElementTypeId());
		signallingCommand.setElementTypeId(commandElement.getElementTypeId());
		signallingCommand.setElementId(commandElement.getElementId());

		return signallingCommand;
	}
	
	
	
}

