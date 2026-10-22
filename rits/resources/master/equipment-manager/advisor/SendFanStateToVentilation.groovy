import org.apache.commons.lang3.exception.ExceptionUtils;


import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.ElementValue;
import com.revenga.rits.back.data.core.model.ElementState;
import com.revenga.rits.back.data.core.model.command.virtual.SetElementValuesToVentilationCommand;
import com.revenga.rits.back.equipment.manager.service.EntitiesManager;

class SendFanStateToVentilation {

	org.apache.logging.log4j.Logger log;

	final long ELEMENT_TYPE_PARAM_FAN_STATE = 1L; // fan_state

	SendFanStateToVentilation(org.apache.logging.log4j.Logger log) {

		this.log = log;
	}

	boolean process(Element element, ElementState elementState) {

		try {

			if (element != null && elementState != null) {

				SetElementValuesToVentilationCommand cmd = new SetElementValuesToVentilationCommand();

				ElementValue elementValue = EntitiesManager.getInstance().getElementValueMeasure(element, ELEMENT_TYPE_PARAM_FAN_STATE);

				if(elementValue != null){
					
					cmd.addElementValue(elementValue);
					EntitiesManager.getInstance().sendCommand(cmd);
					return true;
				}
			}
		} catch (Exception e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}

		return false;
	}
}
