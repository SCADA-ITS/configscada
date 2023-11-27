import org.apache.commons.lang3.exception.ExceptionUtils;

import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.Alarm;
import com.revenga.rits.back.equipment.manager.service.EntitiesManager;

/**
 *
 * SendAlarmsToIllumination.groovy
 *
 */
class SendAlarmsToIllumination {

	org.apache.logging.log4j.Logger log;


	SendAlarmsToIllumination(org.apache.logging.log4j.Logger log) {

		this.log = log;
	}

	boolean process(Element element, Alarm alarm) {

		try {

			if (element != null && alarm != null) {

				EntitiesManager.getInstance().sendAlarmToIllumination(alarm);
			}

			return true;
		} catch (Exception e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}

		return false;
	}
}
