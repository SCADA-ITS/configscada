import java.util.List
import java.util.ArrayList
import java.util.Properties
import java.util.HashSet
import org.apache.commons.collections4.CollectionUtils;
import com.revenga.rits.back.data.core.model.Element
import com.revenga.rits.back.data.core.model.ElementValue
import com.revenga.rits.back.data.core.model.ElementHierarchy;
import com.revenga.rits.back.data.core.model.VmsGraphicGraphicGroupValue
import com.revenga.rits.back.data.core.util.ResourcesUtil
import com.revenga.rits.back.io.controller.service.EntitiesManager
import com.revenga.rits.back.data.core.model.command.SetElementValuesPublishCommand;
import com.revenga.rits.back.data.core.model.command.SetActivationAlarmsCommand;
import com.revenga.rits.back.data.core.model.command.SetDeactivationAlarmsCommand;

import java.net.URLDecoder

/**
 *
 * InputAdapter_SOS.groovy: Save the measurement with the current signaling value of the panel
 *
 */
class InputAdapter_SOS {

    org.apache.logging.log4j.Logger log;

    static final Long ELEMENT_TYPE_SOS = 11L
    static final Long PARAM_TYPE_MEASURE = 2L
    static final Long ELEMENT_TYPE_PARAM_VOL_AUDIO = 4L
    static final Long ELEMENT_TYPE_PARAM_MEG_STATE = 15L
    static final Long ELEMENT_TYPE_PARAM_VOL_MEG = 16L

    static final Integer POS_ORD = 1
    //Trama de megafonía 0x56
    static final Integer POS_MEG_STATE = 2
    static final Integer POS_VOL_MEG = 3
    static final Integer POS_STATE_MASTER = 2
    static final Integer POS_STATE_SLAVE = 3

    static final Integer POS_VOL_SOS_MASTER = 6
    static final Integer POS_VOL_SOS_SLAVE = 7
    
    static final String FRAME_MEG = "0056";
    static final String FRAME_AUDIO = "0089";
    static final int SIZE_FRAME_AUDIO = 20;
    static final int SIZE_FRAME_MEG = 24;

    static final Long[] alarm = [130011L, 120011L, 110011L, 100011L, 0, 20011L, 90011L, 0];

    InputAdapter_SOS(org.apache.logging.log4j.Logger log) {
        this.log = log;
    }

    String extractValidFrame(String value, String frameType) {
        int frameSize = frameType.equals(FRAME_AUDIO) ? SIZE_FRAME_AUDIO : SIZE_FRAME_MEG;
        int index = value.indexOf(frameType);
        if (index != -1 && index + frameSize <= value.length()) {
            return value.substring(index, index + frameSize);
        }
        return null;
    }


    boolean res_meg_qry(Element element, String value, List<ElementValue> elementValues) {
        String frame = extractValidFrame(value, FRAME_MEG);
        if (frame != null) {
            List<Integer> contentByte = checkFrame(frame, 0x56, FRAME_MEG);
            if (!contentByte.isEmpty()) {
                elementValues.add(elementSetValue(ELEMENT_TYPE_PARAM_MEG_STATE, PARAM_TYPE_MEASURE, element.getId(), String.valueOf(contentByte.get(POS_MEG_STATE))));
                elementValues.add(elementSetValue(ELEMENT_TYPE_PARAM_VOL_MEG, PARAM_TYPE_MEASURE, element.getId(), String.valueOf(contentByte.get(POS_VOL_MEG))));
                processChildElements(element, contentByte, FRAME_MEG);
                publishElementValues(elementValues);
            }
        }
        return true;
    }

    boolean res_tst_man(Element element, String value, List<ElementValue> elementValues) {

        String frame = extractValidFrame(value, FRAME_AUDIO);

        if (frame != null) {
            List<Integer> contentByte = checkFrame(frame, 0x89, FRAME_AUDIO);
            if (!contentByte.isEmpty()) {
                publishAlarms(element, contentByte, POS_STATE_MASTER);
                elementValues.add(elementSetValue(ELEMENT_TYPE_PARAM_VOL_AUDIO, PARAM_TYPE_MEASURE, element.getId(), String.valueOf(contentByte.get(POS_VOL_SOS_MASTER))));
                processChildElements(element, contentByte, FRAME_AUDIO);
                publishElementValues(elementValues);
            }
        }
        return true;
    }

    void processChildElements(Element element, List<Integer> contentByte, String frameType) {
        List<ElementHierarchy> elementHierarchies = EntitiesManager.getInstance().getsByParent(element.getElementTypeId(), element.getId())
        if (!CollectionUtils.isEmpty(elementHierarchies)) {
            for (ElementHierarchy elementHierarchie : elementHierarchies) {
                Element elementChild = EntitiesManager.getInstance().getElement(elementHierarchie.getChildElementTypeId(), elementHierarchie.getChildElementId())
                if (elementChild != null) {
                    List<ElementValue> listElements = new ArrayList()

                    // Mantener el volumen de megafonía solo si la trama es 0056
                    if (FRAME_MEG.equals(frameType)) {
                        listElements.add(elementSetValue(ELEMENT_TYPE_PARAM_VOL_MEG, PARAM_TYPE_MEASURE, elementChild.getId(), String.valueOf(contentByte.get(POS_VOL_MEG))))
                    }

                    // Mantener el estado de megafonía solo si la trama es 0056
                    if (FRAME_MEG.equals(frameType)) {
                        listElements.add(elementSetValue(ELEMENT_TYPE_PARAM_MEG_STATE, PARAM_TYPE_MEASURE, elementChild.getId(), String.valueOf(contentByte.get(POS_MEG_STATE))))
                    }

                    // Procesar el volumen de audio del esclavo solo si la trama es 0089
                    if (FRAME_AUDIO.equals(frameType)) {
                        listElements.add(elementSetValue(ELEMENT_TYPE_PARAM_VOL_AUDIO, PARAM_TYPE_MEASURE, elementChild.getId(), String.valueOf(contentByte.get(POS_VOL_SOS_SLAVE))))
                        publishAlarms(elementChild, contentByte, POS_STATE_SLAVE);
                    }

                    // Publicar los valores para el hijo
                    publishElementValues(listElements)
                }
            }
        }
    }

    ElementValue elementSetValue(Long elementTypeParamId, Long paramTypeId, Long elementId, String value) {
        log.debug("Asignando valor: tipoElementoParamId=" + elementTypeParamId + ", paramTypeId=" + paramTypeId + ", elementId=" + elementId + ", valor=" + value);
        ElementValue elementValue = new ElementValue();
        elementValue.setElementTypeId(ELEMENT_TYPE_SOS);
        elementValue.setElementTypeParamId(elementTypeParamId);
        elementValue.setParamTypeId(paramTypeId);
        elementValue.setElementId(elementId);
        elementValue.setValue(value);
        return elementValue;
    }

    void publishElementValues(List<ElementValue> elementValues) {
        EntitiesManager.getInstance().putElementValues(elementValues);
        SetElementValuesPublishCommand command = new SetElementValuesPublishCommand();
        command.setElementValues(new HashSet<>(elementValues));
        EntitiesManager.getInstance().sendCommand(command);
    }

    void publishAlarms(Element element, List<Integer> contentByte, Integer position) {
        SetActivationAlarmsCommand activateCmd = new SetActivationAlarmsCommand();
        SetDeactivationAlarmsCommand deactivateCmd = new SetDeactivationAlarmsCommand();

        log.debug("Procesando alarmas para el elemento: " + element.getId() + " con estado: " + contentByte.get(position));
        for (int i = 0; i < 8; i++) {
            boolean isBitSet = (contentByte.get(position) & (1 << i)) != 0;
            Long alarmId = alarm[i];
            if (alarmId != 0) {
                if (isBitSet) {
                    activateCmd.add(element, EntitiesManager.getInstance().getAlarmConfig(alarmId));
                } else {
                    deactivateCmd.add(element, EntitiesManager.getInstance().getAlarmConfig(alarmId));
                }
            }
        }
        EntitiesManager.getInstance().sendCommand(activateCmd);
        EntitiesManager.getInstance().sendCommand(deactivateCmd);
    }

    List<Integer> checkFrame(String value, int state, String equipment) {
        List<Integer> contentByte = new ArrayList<Integer>();
        String aux = "";

        for (int i = 0; i < value.length(); i += 2) {
            aux = value.substring(i, i + 2);
            contentByte.add(Integer.parseInt(aux, 16));
        }

        if (contentByte.size() > POS_ORD && (contentByte.get(POS_ORD) & 0xFF) == state) {
            return contentByte;
        } else {
            log.debug("Trama recibida no es válida para el equipo " + equipment);
            return new ArrayList<Integer>();
        }
    }
}
