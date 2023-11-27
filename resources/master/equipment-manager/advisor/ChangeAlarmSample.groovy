import org.apache.commons.lang3.exception.ExceptionUtils;

import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.Alarm;

/**
 *
 * ChangeAlarmSample.groovy: Groovy for change state of equipment
 *
 */
class ChangeAlarmSample {

	org.apache.logging.log4j.Logger log;
	
	ChangeAlarmSample(org.apache.logging.log4j.Logger log) {

		this.log = log;
	}
	
	boolean process(Element element, Alarm alarm) {
		
		try {

			log.error("ChangeAlarmSample::process(Element:" + element.getElementTypeId() + ":" + element.getId()
					+ " => " + alarm);

			return true;
			
		} catch (Exception e) {
			
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}
		
		return false;
	}
}
