import org.apache.commons.lang3.exception.ExceptionUtils;

import com.revenga.rits.back.data.core.model.ElementValue;
import com.revenga.rits.back.data.core.model.command.SignallingCommand;
import com.revenga.rits.back.data.core.model.command.SignallingCommand.SignallingParam;
import com.revenga.rits.back.plan.manager.service.EntitiesManager;

class CopyBrightness {
	org.apache.logging.log4j.Logger log;
	
	static final Long ELEMENT_TYPE_PMV = 71L;
	static final Long PMV_ID_1 = 1L;
	static final Long PMV_ID_2 = 2L;
	static final Long ELEMENT_TYPE_PARAM_BRIGHTNESS = 1L;
	static final Long PARAM_TYPE_MEASURE = 2L;
	static final Long PARAM_COMMAND_SIGNALLING = 5L;
	static final Integer PARAM_TYPE_INT = 1;
	static final Integer PARAM_ID_1 = 1;
	
	CopyBrightness(org.apache.logging.log4j.Logger log) {
		this.log = log;
	}
	
	boolean process(String value) {	
	
		try {
			
			ElementValue brightness = EntitiesManager.getInstance().getElementValue(ELEMENT_TYPE_PMV, PMV_ID_2, ELEMENT_TYPE_PARAM_BRIGHTNESS, PARAM_TYPE_MEASURE);
			EntitiesManager.getInstance().sendCommand(rellenaSignallingCommand(brightness));
			
		}
		catch (Exception e) {
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}
		return true;
	}
	
	SignallingCommand rellenaSignallingCommand(ElementValue elementValue){
		SignallingCommand signallingCommand = new SignallingCommand();
		
		signallingCommand.setSignallingCommandId(PARAM_COMMAND_SIGNALLING);
		signallingCommand.setElementTypeId(ELEMENT_TYPE_PMV);
		signallingCommand.setElementId(PMV_ID_1);

		List<SignallingParam> signallingParams = new ArrayList<>();
		SignallingParam signallingParam = new SignallingParam();

		signallingParam.setId(PARAM_ID_1);
		signallingParam.setType(PARAM_TYPE_INT);
		signallingParam.setValue(elementValue.getValue());

		signallingParams.add(signallingParam);
		signallingCommand.setSignallingParams(signallingParams);

		return signallingCommand;
	}
}
