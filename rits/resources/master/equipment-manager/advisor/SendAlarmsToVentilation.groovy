import org.apache.commons.lang3.exception.ExceptionUtils;

import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.Alarm;
import com.revenga.rits.back.data.core.model.command.virtual.SetAlarmsToVentilationCommand;
import com.revenga.rits.back.equipment.manager.service.EntitiesManager;

/**
 *
 * SendAlarmsToVentilation.groovy
 *
 */
class SendAlarmsToVentilation {

	org.apache.logging.log4j.Logger log;
	
	SendAlarmsToVentilation(org.apache.logging.log4j.Logger log) {

		this.log = log;
	}
	
	boolean process(Element element, Alarm alarm) {
		
		try {

			if (element != null && alarm != null) {

				SetAlarmsToVentilationCommand cmd = new SetAlarmsToVentilationCommand();

				cmd.addAlarm(alarm);
				
				EntitiesManager.getInstance().sendCommand(cmd);
			}

			return true;
		} catch (Exception e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}

		return false;
	}
}
