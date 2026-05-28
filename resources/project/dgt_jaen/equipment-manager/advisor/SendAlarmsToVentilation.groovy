import java.util.List;
import java.util.Arrays;
import java.util.Collections;

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

	final long ALARM_CONFIGS_ALARM_MAX_VIBRATION = 3020009L;
	final long ALARM_CONFIGS_ALARM_MAX_FRONT_BEARING_TEMP = 3030009L;
	final long ALARM_CONFIGS_ALARM_MAX_BACK_BEARING_TEMP = 3040009L;
	final long ALARM_CONFIGS_SIGNALLING_NOT_ARRIVED = 92000L;
	final long ALARM_CONFIGS_ALARM_JETFAN_SWITCH = 60009L;
	final long ALARM_CONFIGS_ALARM_JETFAN_GEAR = 70009L;
	final long ALARM_CONFIGS_ALARM_JETFAN_FAIL = 40009L;
	final long ALARM_CONFIGS_ALARM_JETFAN_EMERGENCY_STOP = 170009L
	final long ALARM_CONFIGS_ALARM_JETFAN_STARTER_SHOT = 180009L
	final long ALARM_CONFIGS_ALARM_JETFAN_NO_AUTO = 190009L
	final long ALARM_CONFIGS_ALARM_JETFAN_GE_START = 200009L;


	final List<Long> alarmConfigs = Collections.unmodifiableList(Arrays.asList(
		ALARM_CONFIGS_ALARM_MAX_VIBRATION,
		ALARM_CONFIGS_ALARM_MAX_FRONT_BEARING_TEMP,
		ALARM_CONFIGS_ALARM_MAX_BACK_BEARING_TEMP,
		ALARM_CONFIGS_SIGNALLING_NOT_ARRIVED,
		ALARM_CONFIGS_ALARM_JETFAN_SWITCH,
		ALARM_CONFIGS_ALARM_JETFAN_GEAR,
		ALARM_CONFIGS_ALARM_JETFAN_FAIL,
		ALARM_CONFIGS_ALARM_JETFAN_STARTER_SHOT,
		ALARM_CONFIGS_ALARM_JETFAN_GE_START,
		ALARM_CONFIGS_ALARM_JETFAN_NO_AUTO));

	SendAlarmsToVentilation(org.apache.logging.log4j.Logger log) {

		this.log = log;
	}

	boolean process(Element element, Alarm alarm) {

		try {

			if (element != null && alarm != null && alarm.getAlarmConfigId() && 
				alarmConfigs.contains(alarm.getAlarmConfigId())) {

				EntitiesManager.getInstance().sendAlarmToVentilation(alarm);
			}

			return true;
		} catch (Exception e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}

		return false;
	}
}
