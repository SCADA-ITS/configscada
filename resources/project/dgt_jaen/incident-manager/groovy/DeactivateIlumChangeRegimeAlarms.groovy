import java.util.List;
import java.util.ArrayList;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.AlarmConfig;
import com.revenga.rits.back.data.core.model.ImsIncidentReport;
import com.revenga.rits.back.data.core.model.ImsIncidentTypeTask;
import com.revenga.rits.back.data.core.model.ImsIncidentTypeTaskValue;
import com.revenga.rits.back.data.core.model.command.Command;
import com.revenga.rits.back.data.core.model.command.SetDeactivationAlarmsCommand;
import com.revenga.rits.back.incident.manager.service.EntitiesManager;

class DeactivateIlumChangeRegimeAlarms {

    static final Long ELEMENT_TYPE_ILLUMINATION = 2000L;

    static final Long ALARM_SOLEADO_ID = 301002000L;
    static final Long ALARM_CREPUSCULAR_ID = 302002000L;
    static final Long ALARM_NUBLADO_ID = 303002000L;
    static final Long ALARM_NOCTURNO_ID = 304002000L;
    static final Long ALARM_EXTERIOR_NOCTURNO_ID = 305002000L;

    static final List<Long> ALARM_CONFIG_IDS = [
        ALARM_SOLEADO_ID,
        ALARM_CREPUSCULAR_ID,
        ALARM_NUBLADO_ID,
        ALARM_NOCTURNO_ID,
        ALARM_EXTERIOR_NOCTURNO_ID
    ];

    org.apache.logging.log4j.Logger log;

    DeactivateIlumChangeRegimeAlarms(org.apache.logging.log4j.Logger log) {
        this.log = log;
    }

    List<Command> process(ImsIncidentReport incidentReport,
                          ImsIncidentTypeTask incidentTypeTask,
                          List<ImsIncidentTypeTaskValue> values) {

        List<Command> commands = new ArrayList<>();

        try {

            if (incidentReport == null) {
                log.warn("DeactivateIlumChangeRegimeAlarms: incidentReport null");
                return commands;
            }

            if (incidentReport.getLocationId() == null) {
                log.warn("DeactivateIlumChangeRegimeAlarms: locationId null para incidentReport "
                    + incidentReport.getId());
                return commands;
            }

            Long locationId = incidentReport.getLocationId();

            log.info("DeactivateIlumChangeRegimeAlarms: buscando elemento de iluminación con locationId="
                + locationId
                + " para incidentReport "
                + incidentReport.getId());

            Element illuminationElement = getIlluminationElementByLocation(locationId);

            if (illuminationElement == null) {
                log.warn("DeactivateIlumChangeRegimeAlarms: no se ha encontrado elemento de iluminación con locationId="
                    + locationId);
                return commands;
            }

            SetDeactivationAlarmsCommand command = new SetDeactivationAlarmsCommand();

            for (Long alarmConfigId : ALARM_CONFIG_IDS) {

                AlarmConfig alarmConfig = new AlarmConfig();
                alarmConfig.setId(alarmConfigId);

                command.add(illuminationElement, alarmConfig);

                log.info("DeactivateIlumChangeRegimeAlarms: desactivar alarmConfigId="
                    + alarmConfigId
                    + " sobre Element "
                    + illuminationElement.getElementTypeId() + ":" + illuminationElement.getId());
            }

            commands.add(command);

            log.info("DeactivateIlumChangeRegimeAlarms: añadido SetDeactivationAlarmsCommand para Element "
                + illuminationElement.getElementTypeId() + ":" + illuminationElement.getId()
                + " con " + ALARM_CONFIG_IDS.size() + " alarmas");

        } catch (Exception e) {

            log.error("DeactivateIlumChangeRegimeAlarms error: " + e.getMessage());
            log.debug(ExceptionUtils.getStackTrace(e));
        }

        return commands;
    }

    Element getIlluminationElementByLocation(Long locationId) {

        List<Element> illuminationElements = EntitiesManager.getInstance().getElementByElementType(
            ELEMENT_TYPE_ILLUMINATION
        );

        if (CollectionUtils.isEmpty(illuminationElements)) {
            log.warn("DeactivateIlumChangeRegimeAlarms: no existen elementos de iluminación en caché");
            return null;
        }

        List<Element> matchedElements = new ArrayList<>();

        for (Element element : illuminationElements) {

            if (element == null) {
                continue;
            }

            if (locationId.equals(element.getLocationId())) {
                matchedElements.add(element);
            }
        }

        if (CollectionUtils.isEmpty(matchedElements)) {
            return null;
        }

        if (matchedElements.size() > 1) {
            log.warn("DeactivateIlumChangeRegimeAlarms: hay más de un elemento de iluminación con locationId="
                + locationId
                + ". Se usará el primero. Total encontrados="
                + matchedElements.size());
        }

        Element element = matchedElements.get(0);

        log.info("DeactivateIlumChangeRegimeAlarms: elemento encontrado Element "
            + element.getElementTypeId() + ":" + element.getId()
            + ", locationId=" + element.getLocationId());

        return element;
    }
}