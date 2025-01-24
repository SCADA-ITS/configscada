import java.util.List;

import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.AlarmConfig;
import com.revenga.rits.back.data.core.model.command.SetActivationAlarmsCommand;
import com.revenga.rits.back.data.core.model.command.SetDeactivationAlarmsCommand;
import com.revenga.rits.back.virtual.equipment.manager.une.thread.ThreadSendManager;


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
		if (data && !data.isEmpty()) {
			Element element = new Element(elementTypeId, elementId);

			AlarmConfig doorOpen = createAlarm(ALARM_CONFIG_PUERTA_ABIERTA);
			AlarmConfig ventilationOn = createAlarm(ALARM_CONFIG_VENTILACION_ACTIVADA);
			AlarmConfig ventilationError = createAlarm(ALARM_CONFIG_FALLO_VENTILACION);
			AlarmConfig internalHardwareError = createAlarm(ALARM_CONFIG_ERROR_INTERNO_HARDWARE);
			AlarmConfig temperatureExcess = createAlarm(ALARM_CONFIG_EXCESO_TEMPERATURA);
			AlarmConfig photocellAlarm = createAlarm(ALARM_CONFIG_FOTOCELULA);

			Byte alarmsByte3 = data[ALARMAS_BYTE_3];
			Byte alarmsByte6 = data[ALARMAS_BYTE_6];

			int[] arrayByte3Binary = conversionIntToArrayIntBinary(alarmsByte3);
			int[] arrayByte6Binary = conversionIntToArrayIntBinary(alarmsByte6);

			generateAlarm(serviceId, element, arrayByte3Binary, BIT_ALARMA_PUERTA_ABIERTA, doorOpen);
			generateAlarm(serviceId, element, arrayByte3Binary, BIT_ALARMA_VENTILACION_ACTIVADA, ventilationOn);
			generateAlarm(serviceId, element, arrayByte3Binary, BIT_ALARMA_FALLO_VENTILACION, ventilationError);
			generateAlarm(serviceId, element, arrayByte3Binary, BIT_ALARMA_ERROR_INTERNO_HARDWARE, internalHardwareError);
			generateAlarm(serviceId, element, arrayByte3Binary, BIT_ALARMA_EXCESO_TEMPERATURA, temperatureExcess);

			generateAlarm(serviceId, element, arrayByte6Binary, BIT_ALARMA_FOTOCELULA, photocellAlarm);
        }
    }

    AlarmConfig createAlarm(Long alarmId) {
        AlarmConfig alarmConfig = new AlarmConfig()
        alarmConfig.setId(alarmId);
        return alarmConfig;
    }

    int[] conversionIntToArrayIntBinary(Byte alarms) {
        String binaryString = String.format("%${BYTE_SIZE}s", Integer.toBinaryString(alarms & 0xFF)).replace(' ', '0');
        return binaryString.collect { it as Integer };
    }

	void generateAlarm(String serviceId, Element element, int[] binaryArray, int bitPosition, AlarmConfig alarm) {
		if (binaryArray[bitPosition] == 1) {
			ThreadSendManager.getInstance().addActivateAlarm(serviceId, element, alarm);
        } else {
			ThreadSendManager.getInstance().addDeactivateAlarm(serviceId, element, alarm);
		}
	}
}