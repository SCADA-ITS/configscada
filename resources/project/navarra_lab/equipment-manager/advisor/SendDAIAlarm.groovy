import org.apache.commons.lang3.exception.ExceptionUtils;

import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.Alarm;
import com.revenga.rits.back.data.core.model.command.Command;
import com.revenga.rits.back.data.core.model.command.SignallingCommand;
import com.revenga.rits.back.data.core.model.command.SignallingCommand.SignallingParam;
import com.revenga.rits.back.equipment.manager.service.EntitiesManager;

/**
 *
 * sendDAIAlarm.groovy: Groovy for change state of equipment
 *
 */
class sendDAIAlarm {

	static final Long SEND_DAI_ALARM = 10L;
	static final Long ELEMENT_TYPE_CAMERA = 2L;
	static final String ACTIVATION_VALUE = "1";
	static final String DEACTIVATION_VALUE = "0";
	static final Integer PARAM_ID_1 = 1;
	static final Integer DATA_TYPE_ALPHANUMERIC = 2;
	static final Integer ALARM_CONFIG_ID1 = 1550002L;
	static final Integer ALARM_CONFIG_ID2 = 1560002L;
	static final Integer ALARM_CONFIG_ID3 = 1570002L;
	static final Integer ALARM_CONFIG_ID4 = 1580002L;
	static final Integer ALARM_CONFIG_ID5 = 1590002L;
	static final Integer ALARM_CONFIG_ID6 = 1600002L;
	static final Integer ALARM_CONFIG_ID7 = 1610002L;
	static final Integer ALARM_CONFIG_ID8 = 1620002L;
	static final Integer ALARM_CONFIG_ID9 = 1630002L;
	static final Integer ALARM_CONFIG_ID10 = 1640002L;
	static final Integer ALARM_CONFIG_ID11 = 1650002L;
	static final Integer ALARM_CONFIG_ID12 = 1660002L;

	org.apache.logging.log4j.Logger log;
	
	sendDAIAlarm(org.apache.logging.log4j.Logger log) {

		this.log = log;
	}
	
	boolean process(Element element, Alarm alarm) {	
		try {
			if(alarm.getAlarmConfigId() == ALARM_CONFIG_ID1 ||
				alarm.getAlarmConfigId() == ALARM_CONFIG_ID2 ||
				alarm.getAlarmConfigId() == ALARM_CONFIG_ID3 ||
				alarm.getAlarmConfigId() == ALARM_CONFIG_ID4 ||
				alarm.getAlarmConfigId() == ALARM_CONFIG_ID5 ||
				alarm.getAlarmConfigId() == ALARM_CONFIG_ID6 ||
				alarm.getAlarmConfigId() == ALARM_CONFIG_ID7 ||
				alarm.getAlarmConfigId() == ALARM_CONFIG_ID8 ||
				alarm.getAlarmConfigId() == ALARM_CONFIG_ID9 ||
				alarm.getAlarmConfigId() == ALARM_CONFIG_ID10 ||
				alarm.getAlarmConfigId() == ALARM_CONFIG_ID11 ||
				alarm.getAlarmConfigId() == ALARM_CONFIG_ID12){
				List<Command> commands = null;
				
				SignallingCommand signallingCommand = new SignallingCommand();
					
				signallingCommand.setSignallingCommandId(SEND_DAI_ALARM);
				signallingCommand.setElementTypeId(ELEMENT_TYPE_CAMERA);
				signallingCommand.setElementId(element.getId());
				
				List<SignallingParam> signallingParams = new ArrayList<>();
				SignallingParam signallingParam = new SignallingParam();
		
				signallingParam.setId(PARAM_ID_1);
				signallingParam.setType(DATA_TYPE_ALPHANUMERIC);
				
				if (alarm.getDeactivation() == null){
					signallingParam.setValue(ACTIVATION_VALUE);
				}else{
					signallingParam.setValue(DEACTIVATION_VALUE);
				}
				signallingParams.add(signallingParam);
				
				signallingCommand.setSignallingParams(signallingParams);
				
				if (commands == null) {
					
					commands = new ArrayList<>();
				}
				
				commands.add(signallingCommand);
				
				EntitiesManager.getInstance().sendCommand(commands);
				
				log.error("sendDAIAlarm::process(Element:" + element.getElementTypeId() + ":" + element.getId()
						+ " => " + alarm);
	
				return true;
			}
			
		} catch (Exception e) {
			
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}
		
		return false;
	}
}
