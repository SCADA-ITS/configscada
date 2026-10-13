import org.apache.commons.lang3.exception.ExceptionUtils;

import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.Alarm;
import com.revenga.rits.back.equipment.manager.service.EntitiesManager;
import com.revenga.rits.back.data.core.model.command.SignallingCommand;

/**
 *
 * Reset.groovy: Send reset alarms when barrier not low
 *
 */
class Reset {

	// ID Elements Params
	static final Long PARAM_COMMAND_SIGNALLING = 1L;
	static final Long ELEMENT_TYPE = 55L;
	static final Long ELEMENT_ID = 1L;
	static final Long ALARM_LOWERING_FAIL = 284L;

	org.apache.logging.log4j.Logger log;
	
	Reset(org.apache.logging.log4j.Logger log) {

		this.log = log;
	}
	
	boolean process(Element element, Alarm alarm) {
		
		try {
			
			if(alarm.getAlarmConfigId() == ALARM_LOWERING_FAIL && alarm.getDeactivation()==null){		
				EntitiesManager.getInstance().sendCommand(rellenaSignallingCommand(element,ELEMENT_TYPE,ELEMENT_ID));	
				//DESCOMENTAR LA SIGUIENTE LINEA SI SE REQUIERE BAJADA DE BAJADA AUTOMATICA TRAS RESET
				//EntitiesManager.getInstance().sendCommand(rellenaSignallingCommand(element, element.getElementTypeId(), element.getId()));
			}
			
		} catch (Exception e) {
			
			log.error(e.getMessage());
			
			log.debug(ExceptionUtils.getStackTrace(e));
		}
		
		return false;
	}
	
	SignallingCommand rellenaSignallingCommand(Element element, Long elementType, Long elementId){
		SignallingCommand signallingCommand = new SignallingCommand();
		
		signallingCommand.setSignallingCommandId(PARAM_COMMAND_SIGNALLING);
		signallingCommand.setElementTypeId(elementType);
		signallingCommand.setElementId(elementId);

		return signallingCommand;
	}
}
