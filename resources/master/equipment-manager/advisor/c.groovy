import org.apache.commons.lang3.exception.ExceptionUtils;

import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.Alarm;

/**
 *
 * ChangeAlarmSample.groovy: Groovy for changes in alarms
 *
 */
class ChangeAlarmSample {

	org.apache.logging.log4j.Logger log;
	
	ChangeAlarmSample(org.apache.logging.log4j.Logger log) {

		this.log = log;
	}
	
	boolean process(Element element, Alarm alarm) {
		
		try {

			log.debug("ChangeAlarmSample::process(Element:" + element.getElementTypeId() + ":" + element.getId() + ", Alarm:" + alarm.getId() + ")");
			
			return true;
			
		} catch (Exception e) {
			
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}
		
		return false;
	}
}
