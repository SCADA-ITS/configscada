import org.apache.commons.lang3.exception.ExceptionUtils

import com.revenga.rits.back.plan.manager.service.EntitiesManager
import com.revenga.rits.back.data.core.model.command.SignallingCommand
import com.revenga.rits.back.data.core.model.command.SignallingCommand.SignallingParam

class SosPostAlarmProcessor {

    org.apache.logging.log4j.Logger log

    // IDs que debes ajustar a vuestro máster si son otros
    static final Long   ELEMENT_TYPE_CAM        = 2L     // Tipo de elemento CÁMARA (si lo necesitáis)
    static final Long   COMMAND_ID_PRESET_CAM   = 2L     // SignallingCommand 2 -> SignallingCommand_2_2 (PRESET Camera)
    static final String SOS_PRESET_ID           = "3"    // Id de preset SOS que queréis disparar
    static final Integer ALARM_CONFIG_SOS_POST  = 80011  // AlarmConfigId de llamada de poste SOS

    SosPostAlarmProcessor(org.apache.logging.log4j.Logger log) {
        this.log = log
    }

    /**
     * Lógica:
     *  - Si la alarma asociada al poste es la 80011 (SOS)
     *  - Se obtiene su único hijo (cámara asociada)
     *  - Se manda SignallingCommand 2 con preset "3" a esa cámara
     */
    boolean process(Element element, Alarm alarm) {
        try {
            if (alarm == null || alarm.getAlarmConfigId() == null) {
                log.warn("SosPostAlarmProcessor: alarma nula para el elemento {}", element?.getAlias())
                return false
            }

            // 1) Sólo actuar si es la alarma de poste SOS
            if (alarm.getAlarmConfigId() != ALARM_CONFIG_SOS_POST) {
                return false
            }

            // 2) Obtener hijos del poste: sabemos que tendrá sólo uno y será su cámara
            List<ElementHierarchy> childs = EntitiesManager.getInstance()
                    .getsByParent(element.getElementTypeId(), element.getId(), false)

            if (childs == null || childs.isEmpty()) {
                log.warn("SosPostAlarmProcessor: el poste SOS {} no tiene hijo (cámara asociada)", element.getAlias())
                return false
            }

            ElementHierarchy camChild = childs.get(0)  // único hijo = cámara asociada
            Long camId = camChild.getElementId()

            // 3) Construir el SignallingCommand 2 para preset de cámara
            SignallingCommand cmd = new SignallingCommand()
            cmd.setSignallingCommandId(COMMAND_ID_PRESET_CAM)  // -> SignallingCommand_2_2
            cmd.setElementId(camId)

            // 4) Crear el parámetro de preset con value "3" (String)
            SignallingParam presetParam = new SignallingParam()
            presetParam.setValue(SOS_PRESET_ID) // IMPORTANTE: String, porque luego getValue() -> presetId

            // Añadimos el param a la lista de params del comando
            cmd.setSignallingParams([presetParam])

            // 5) Enviar comando (ajusta el método a vuestro EntitiesManager real)
            EntitiesManager.getInstance().sendSignallingCommand(cmd)

            log.info("SosPostAlarmProcessor: enviado SignallingCommand 2 (preset SOS={}) a la cámara {} del poste {}",
                    SOS_PRESET_ID, camId, element.getAlias())

            return true

        } catch (Exception e) {
            log.error("SosPostAlarmProcessor: error en process():\n{}", ExceptionUtils.getStackTrace(e))
            return false
        }
    }
}
