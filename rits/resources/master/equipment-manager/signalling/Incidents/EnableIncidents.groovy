import java.util.List;
import com.revenga.rits.back.data.core.model.command.SetDeactivationAlarmsCommand;
import com.revenga.rits.back.data.core.model.command.SetActivationAlarmsCommand;
import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.ElementState;
import com.revenga.rits.back.data.core.model.ElementTypeState;
import com.revenga.rits.back.data.core.model.PmCommandElement;
import com.revenga.rits.back.equipment.manager.helper.GroovyHelper;

class Enable {

	org.apache.logging.log4j.Logger log;

	final Long ELEMENT_TYPE_ID = 23L;
	final Long ELEMENT_ID = 1L;
	static final Long ALARM_CONFIG_ENABLE_INCIDENT = 2301L;
	static final Long ALARM_CONFIG_IMANAGER_RESTITUTION = 2302L;


	Enable(org.apache.logging.log4j.Logger log) {

		this.log = log;
	}

	void process(PmCommandElement command) {
		

		ElementState elementState = new ElementState();
		Element element = GroovyHelper.getElement(ELEMENT_TYPE_ID, ELEMENT_ID);
		
		elementState.setElementTypeId(ELEMENT_TYPE_ID);
		elementState.setElementId(ELEMENT_ID);
		elementState.setStateId(ElementTypeState.STATE_UP);
		GroovyHelper.setChangeStateLock(element, elementState)

		Thread.sleep(5000);		
		SetActivationAlarmsCommand setActivationAlarmsCommand = new SetActivationAlarmsCommand();
		setActivationAlarmsCommand.add(element, GroovyHelper.getAlarmConfig(ALARM_CONFIG_ENABLE_INCIDENT));
		GroovyHelper.activateAlarms(setActivationAlarmsCommand);	

		Thread.sleep(2000);		
		SetDeactivationAlarmsCommand setDeactivationAlarmsCommand = new SetDeactivationAlarmsCommand();		
		setDeactivationAlarmsCommand.add(element, GroovyHelper.getAlarmConfig(ALARM_CONFIG_IMANAGER_RESTITUTION));
		GroovyHelper.deactivateAlarms(setDeactivationAlarmsCommand);			
	}
	
}
