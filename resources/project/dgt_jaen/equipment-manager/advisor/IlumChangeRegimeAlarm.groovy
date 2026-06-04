import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.tuple.Pair;

import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.ElementValue;
import com.revenga.rits.back.data.core.model.command.SetActivationAlarmsCommand;
import com.revenga.rits.back.equipment.manager.service.EntitiesManager;

class IlumChangeRegimeAlarm {

    static final Long PARAM_TYPE_MEASURE = 2L;
    static final Long PARAM_REGIME = 4L;

    static final String REGIME_SOLEADO = "2";
    static final String REGIME_NUBLADO = "3";
    static final String REGIME_CREPUSCULAR = "4";
    static final String REGIME_NOCTURNO = "5";
    static final String REGIME_EXTERIOR_NOCTURNO = "6";

    static final Long ALARM_SOLEADO_ID = 301002000L;
    static final Long ALARM_CREPUSCULAR_ID = 302002000L;
    static final Long ALARM_NUBLADO_ID = 303002000L;
    static final Long ALARM_NOCTURNO_ID = 304002000L;
    static final Long ALARM_EXTERIOR_NOCTURNO_ID = 305002000L;

    org.apache.logging.log4j.Logger log;

    IlumChangeRegimeAlarm(org.apache.logging.log4j.Logger log) {
        this.log = log;
    }

    boolean process(Element element, List<Pair<ElementValue, ElementValue>> elementValues) {
        boolean res = true;

        try {

            log.info("IlumChangeRegimeAlarm: ejecutando para Element "
                + element.getElementTypeId() + ":" + element.getId());

            if (elementValues == null || elementValues.isEmpty()) {
                log.warn("IlumChangeRegimeAlarm: elementValues vacío");
                return false;
            }

            Map<String, String> regimeNames = [
                (REGIME_SOLEADO)           : "SOLEADO",
                (REGIME_NUBLADO)           : "NUBLADO",
                (REGIME_CREPUSCULAR)       : "CREPUSCULAR",
                (REGIME_NOCTURNO)          : "NOCTURNO",
                (REGIME_EXTERIOR_NOCTURNO) : "EXTERIOR/NOCTURNO"
            ];

            Map<String, Long> regimeAlarms = [
                (REGIME_SOLEADO)           : ALARM_SOLEADO_ID,
                (REGIME_NUBLADO)           : ALARM_NUBLADO_ID,
                (REGIME_CREPUSCULAR)       : ALARM_CREPUSCULAR_ID,
                (REGIME_NOCTURNO)          : ALARM_NOCTURNO_ID,
                (REGIME_EXTERIOR_NOCTURNO) : ALARM_EXTERIOR_NOCTURNO_ID
            ];

            ElementValue regimeElementValue = null;

            for (Pair<ElementValue, ElementValue> pair : elementValues) {

                ElementValue oldValue = pair.getLeft();
                ElementValue newValue = pair.getRight();

                log.info("IlumChangeRegimeAlarm: valor recibido"
                    + " OLD=" + (oldValue == null ? "null" : oldValue.getValue())
                    + " NEW=" + (newValue == null ? "null" : newValue.getValue())
                    + " paramTypeId=" + (newValue == null ? "null" : newValue.getParamTypeId())
                    + " elementTypeParamId=" + (newValue == null ? "null" : newValue.getElementTypeParamId()));

                if (newValue != null
                    && PARAM_TYPE_MEASURE.equals(newValue.getParamTypeId())
                    && PARAM_REGIME.equals(newValue.getElementTypeParamId())) {

                    regimeElementValue = newValue;
                }
            }

            if (regimeElementValue == null) {
                log.warn("IlumChangeRegimeAlarm: no se ha encontrado medida de régimen "
                    + "paramTypeId=" + PARAM_TYPE_MEASURE
                    + ", elementTypeParamId=" + PARAM_REGIME);
                return false;
            }

            if (regimeElementValue.getValue() == null) {
                log.warn("IlumChangeRegimeAlarm: régimen con valor null");
                return false;
            }

            String regimeValue = regimeElementValue.getValue().trim();

            if (!regimeNames.containsKey(regimeValue)) {
                log.warn("IlumChangeRegimeAlarm: régimen no reconocido value=" + regimeValue);
                return false;
            }

            String regimeName = regimeNames.get(regimeValue);
            Long alarmToActivate = regimeAlarms.get(regimeValue);

            log.info("IlumChangeRegimeAlarm: régimen detectado "
                + regimeName
                + " value=" + regimeValue
                + " alarmConfigId=" + alarmToActivate);

            SetActivationAlarmsCommand activationCommand = new SetActivationAlarmsCommand();

            activationCommand.add(
                element,
                EntitiesManager.getInstance().getAlarmConfig(alarmToActivate)
            );

            EntitiesManager.getInstance().sendCommand(activationCommand);

            log.info("IlumChangeRegimeAlarm: activada alarma "
                + regimeName
                + " alarmConfigId=" + alarmToActivate);

        } catch (Exception e) {

            log.error("IlumChangeRegimeAlarm error: " + e.getMessage());
            log.debug(ExceptionUtils.getStackTrace(e));
        }

        return res;
    }
}