import org.apache.commons.lang3.exception.ExceptionUtils;

import com.revenga.rits.back.data.core.model.Alarm;

/**
 *
 * CreateAlarmSample.groovy: Groovy for change state of equipment
 *
 */
class CreateAlarmSample {

	org.apache.logging.log4j.Logger log;
	
	CreateAlarmSample(org.apache.logging.log4j.Logger log) {

		this.log = log;
	}
	
	boolean process(Alarm alarm) {
		
		try {

			log.info("GROOVY EXECUTED!!!");
			log.info("CreateAlarmSample::process(Alarm:" + alarm.getAlarmConfigId() + ":" + alarm.getId());

			return true;
			
		} catch (Exception e) {
			
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}
		
		return false;
	}
}
