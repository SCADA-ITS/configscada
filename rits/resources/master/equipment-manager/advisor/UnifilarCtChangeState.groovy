import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.ArrayUtils;

import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.ElementState;
import com.revenga.rits.back.data.core.model.ElementHierarchy;
import com.revenga.rits.back.data.core.model.Alarm;
import com.revenga.rits.back.data.core.model.command.SetDeactivationAlarmsCommand;
import com.revenga.rits.back.data.core.model.command.SetActivationAlarmsCommand;
import com.revenga.rits.back.equipment.manager.service.EntitiesManager;
import com.revenga.rits.back.equipment.manager.service.AlarmsManager;






/**
 *
 * UnifilarCtChangeState.groovy: Groovy for change state of equipment
 *
 */
class UnifilarCtChangeState {


	//Alarms
	static final Long ALARM_EQUIPMENT_NO_CONNECTION = 1L;
	static final Long ALARM_EQUIPMENT_ALARMED = 2L;

	//States
	static final Long STATE_DOWN = 2L;
	static final Long STATE_ALARM = 3L;

	org.apache.logging.log4j.Logger log;
	
	UnifilarCtChangeState(org.apache.logging.log4j.Logger log) {

		this.log = log;
	}
	
	boolean process(Element element, ElementState elementState) {
		
		try {
			List<ElementHierarchy> padres = EntitiesManager.getInstance().getsByChild(element.getElementTypeId(), element.getId())
			SetActivationAlarmsCommand setActivationAlarmsCommandList = new SetActivationAlarmsCommand();
			SetDeactivationAlarmsCommand setDeactivationAlarmsCommandList = new SetDeactivationAlarmsCommand();

			for (ElementHierarchy padre : padres){
				Integer contador = 0;
				Integer contadorAlarmas = 0;
				Integer numeroHijos = 0;

				Element elementoPadre = EntitiesManager.getInstance().getElement(padre.getParentElementTypeId(), padre.getParentElementId());
				Long alarmEquipmentAlarmed_ = Long.valueOf(String.valueOf(ALARM_EQUIPMENT_ALARMED*100) + String.valueOf(elementoPadre.getElementTypeId()));
				Long alarmEquipmentNoConnection_ = Long.valueOf(String.valueOf(ALARM_EQUIPMENT_NO_CONNECTION*100) + String.valueOf(elementoPadre.getElementTypeId()));

				List<ElementHierarchy> childs = EntitiesManager.getInstance().getsByParent(padre.getParentElementTypeId(), padre.getParentElementId(), false);
				List<Alarm> alarms = AlarmsManager.getInstance().getActiveAlarms(elementoPadre.getElementTypeId(), elementoPadre.getId());

				for (ElementHierarchy child : childs){
					numeroHijos++;
					Element elemento = EntitiesManager.getInstance().getElement(child.getChildElementTypeId(), child.getChildElementId());
					ElementState estado = EntitiesManager.getInstance().getElementState(elemento);

					if(estado.stateId == STATE_ALARM){
						contadorAlarmas++;
					}

					if(estado.stateId == STATE_DOWN){
						contador++;
					}
				}

				if (contadorAlarmas > 0){	
					if(alarms.isEmpty()){
						setActivationAlarmsCommandList.add(elementoPadre, EntitiesManager.getInstance().getAlarmConfig(alarmEquipmentAlarmed_));
						log.debug("Alarma nueva para el equipo " + elementoPadre.getElementTypeId() + ":"  + elementoPadre.getId() + " -> Alarma " +alarmEquipmentAlarmed_);
					}else{
						for (Alarm alarm : alarms){
							if(alarm.alarmConfigId.equals(alarmEquipmentAlarmed_)){
								log.debug("La alarma: " + alarm.alarmConfigId + " ya esta activadapara el equipo " +elementoPadre.getElementTypeId() + ":"  + elementoPadre.getId());
							}else{
								setActivationAlarmsCommandList.add(elementoPadre, EntitiesManager.getInstance().getAlarmConfig(alarmEquipmentAlarmed_));
								log.debug("Alarma nueva para el equipo " + elementoPadre.getElementTypeId() + ":"  + elementoPadre.getId() + " -> Alarma " +alarmEquipmentAlarmed_);
							}
						}
					}
				}else{
					setDeactivationAlarmsCommandList.add(elementoPadre, EntitiesManager.getInstance().getAlarmConfig(alarmEquipmentAlarmed_));
					log.debug("Se desactiva la alarma para el equipo " + elementoPadre.getElementTypeId() + ":"  + elementoPadre.getId() + " -> Alarma " +alarmEquipmentAlarmed_);
				}

				log.debug("#################################################################")

				if (contador == numeroHijos){
					if(alarms.isEmpty()){
						setActivationAlarmsCommandList.add(elementoPadre, EntitiesManager.getInstance().getAlarmConfig(alarmEquipmentNoConnection_));
						log.debug("Alarma nueva para el equipo " + elementoPadre.getElementTypeId() + ":"  + elementoPadre.getId() + " -> Alarma " +alarmEquipmentNoConnection_);				
						}else{
							for (Alarm alarm : alarms){
								if(alarm.alarmConfigId.equals(alarmEquipmentNoConnection_)){
									log.debug("La alarma: " + alarm.alarmConfigId + " ya esta activadapara el equipo " +elementoPadre.getElementTypeId() + ":"  + elementoPadre.getId());
								}else{
									setActivationAlarmsCommandList.add(elementoPadre, EntitiesManager.getInstance().getAlarmConfig(alarmEquipmentNoConnection_));
									log.debug("Alarma nueva para el equipo " + elementoPadre.getElementTypeId() + ":"  + elementoPadre.getId() + " -> Alarma " +alarmEquipmentNoConnection_);							
							}
						}
					}
				}else{
					setDeactivationAlarmsCommandList.add(elementoPadre, EntitiesManager.getInstance().getAlarmConfig(alarmEquipmentNoConnection_));
					log.debug("Se desactiva la alarma para el equipo " + elementoPadre.getElementTypeId() + ":"  + elementoPadre.getId() + " -> Alarma " +alarmEquipmentNoConnection_);
				}
			}

			EntitiesManager.getInstance().sendCommand(setActivationAlarmsCommandList);
			EntitiesManager.getInstance().sendCommand(setDeactivationAlarmsCommandList);

			return true;
			
		} catch (Exception e) {
			
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}
		
		return false;
	}
}
