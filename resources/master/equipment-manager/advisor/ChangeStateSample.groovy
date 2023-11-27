import org.apache.commons.lang3.exception.ExceptionUtils;

import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.ElementState;

/**
 *
 * ChangeStateSample.groovy: Groovy for change state of equipment
 *
 */
class ChangeStateSample {

	org.apache.logging.log4j.Logger log;
	
	ChangeStateSample(org.apache.logging.log4j.Logger log) {

		this.log = log;
	}
	
	boolean process(Element element, ElementState elementState) {
		
		try {

			log.debug("ChangeStateSample::process(Element:" + element.getElementTypeId() + ":" + element.getId()
					+ " => " + elementState.getPreviousStateId() + " [Old] " + elementState.getStateId()+ " [New])");

			return true;
			
		} catch (Exception e) {
			
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}
		
		return false;
	}
}
