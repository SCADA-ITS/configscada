import java.util.List;

import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.command.SetActivationAlarmsCommand;
import com.revenga.rits.back.data.core.model.command.SetDeactivationAlarmsCommand;
import com.revenga.rits.back.virtual.equipment.manager.une.thread.ThreadSendManager;
import com.revenga.rits.back.virtual.equipment.manager.service.EntityManager;


class ProcessorUneResponse_ElementSubtype_18_71 {

	static final Long ALARM_CONFIG_PUERTA_ABIERTA = 20071L;
	static final Long ALARM_CONFIG_VENTILACION_ACTIVADA = 80071L;
	static final Long ALARM_CONFIG_FALLO_VENTILACION = 90071L;
	static final Long ALARM_CONFIG_ERROR_INTERNO_HARDWARE = 100071L;
	static final Long ALARM_CONFIG_EXCESO_TEMPERATURA = 70071L;
	static final Long ALARM_CONFIG_FOTOCELULA = 50071L;

	static final int BIT_ALARMA_PUERTA_ABIERTA = 7;
	static final int BIT_ALARMA_VENTILACION_ACTIVADA = 4;
	static final int BIT_ALARMA_FALLO_VENTILACION = 3;
	static final int BIT_ALARMA_ERROR_INTERNO_HARDWARE = 2;
	static final int BIT_ALARMA_EXCESO_TEMPERATURA = 0;
	static final int BIT_ALARMA_FOTOCELULA = 4;

	static final int ALARMAS_BYTE_3 = 2;
	static final int ALARMAS_BYTE_6 = 5;
	static final int BYTE_SIZE = 8;

	org.apache.logging.log4j.Logger log;

	ProcessorUneResponse_ElementSubtype_18_71(org.apache.logging.log4j.Logger log) {
		this.log = log;
	}

	void processResponse_0x97(String serviceId, Long elementId, Long elementTypeId, List<Byte> data) {
		Element element = null;
		SetActivationAlarmsCommand setActivationAlarmsCommandList = new SetActivationAlarmsCommand();
    	SetDeactivationAlarmsCommand setDeactivationAlarmsCommandList = new SetDeactivationAlarmsCommand();
		
		if (data != null && data.size() > 0) {
			element = new Element(elementTypeId, elementId);
		
			Byte alarmsByte3 = data[ALARMAS_BYTE_3];
			Byte alarmsByte6 = data[ALARMAS_BYTE_6];

			int[] arrayByte3Binary = conversionIntToArrayIntBinary(alarmsByte3)
			int[] arrayByte6Binary = conversionIntToArrayIntBinary(alarmsByte6)

			generateAlarm(serviceId, element, arrayByte3Binary, BIT_ALARMA_PUERTA_ABIERTA, ALARM_CONFIG_PUERTA_ABIERTA, setActivationAlarmsCommandList, setDeactivationAlarmsCommandList)
			generateAlarm(serviceId, element, arrayByte3Binary, BIT_ALARMA_VENTILACION_ACTIVADA, ALARM_CONFIG_VENTILACION_ACTIVADA, setActivationAlarmsCommandList, setDeactivationAlarmsCommandList)
			generateAlarm(serviceId, element, arrayByte3Binary, BIT_ALARMA_FALLO_VENTILACION, ALARM_CONFIG_FALLO_VENTILACION, setActivationAlarmsCommandList, setDeactivationAlarmsCommandList)
			generateAlarm(serviceId, element, arrayByte3Binary, BIT_ALARMA_ERROR_INTERNO_HARDWARE, ALARM_CONFIG_ERROR_INTERNO_HARDWARE, setActivationAlarmsCommandList, setDeactivationAlarmsCommandList)
			generateAlarm(serviceId, element, arrayByte3Binary, BIT_ALARMA_EXCESO_TEMPERATURA, ALARM_CONFIG_EXCESO_TEMPERATURA, setActivationAlarmsCommandList, setDeactivationAlarmsCommandList)
			generateAlarm(serviceId, element, arrayByte6Binary, BIT_ALARMA_FOTOCELULA, ALARM_CONFIG_FOTOCELULA, setActivationAlarmsCommandList, setDeactivationAlarmsCommandList)

			EntityManager.getInstance().sendCommand(setActivationAlarmsCommandList);
			EntityManager.getInstance().sendCommand(setDeactivationAlarmsCommandList);
		}
	}

	public int[] conversionIntToArrayIntBinary (Byte alarms){
		String byteBinario = String.format("%${BYTE_SIZE}s", Integer.toBinaryString(alarms & 0xFF)).replace(' ', '0')
		int[] arrayByteBinario = byteBinario.collect { it as Integer }

		return arrayByteBinario
	}

	public void generateAlarm (String serviceId, Element element, int[] binaryArray, int bitPosition, Long alarm, SetActivationAlarmsCommand setActivationAlarmsCommandList, 
								SetDeactivationAlarmsCommand setDeactivationAlarmsCommandList){
		if(binaryArray[bitPosition] == 1){

			setActivationAlarmsCommandList.add(element, ThreadSendManager.getInstance().addActivateAlarm(serviceId, element, EntityManager.getInstance().getAlarmConfig(alarm)));
		}else{

			setDeactivationAlarmsCommandList.add(element, ThreadSendManager.getInstance().addDeactivateAlarm(serviceId, element, EntityManager.getInstance().getAlarmConfig(alarm)));
		}
	}
}