import java.util.List;
import java.util.Set;
import java.util.ArrayList;

import org.apache.commons.lang3.exception.ExceptionUtils;

import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.ElementValue;
import com.revenga.rits.back.data.core.model.Alarm;
import com.revenga.rits.back.data.core.model.ElementHierarchy;
import com.revenga.rits.back.data.core.model.command.SetActivationAlarmsCommand;
import com.revenga.rits.back.data.core.model.command.SetDeactivationAlarmsCommand;
import com.revenga.rits.back.equipment.manager.service.EntitiesManager;

class GeAlarmFan {

    static final Long ALARM_CONFIG_ID_GE_ENGINE_ON = 40075L;
    static final Long ALARM_CONFIG_ID_FAN_TO_SET = 200009L;

    static final Long PARAM_MEASURE_TOTAL_OPERATING_HOURS = 15L;

    static final Set<Long> FAN_ALARM_CONFIG_IDS = [
        10009L,
        40009L,
        170009L,
        180009L,
        190009L
    ] as Set;

    org.apache.logging.log4j.Logger log;

    GeAlarmFan(org.apache.logging.log4j.Logger log) {
        this.log = log;
    }

    boolean process(Element element, Alarm alarm) {
        boolean res = true;

        try {
            if (element == null || alarm == null) {
                log.warn("GeAlarmFan: element o alarm null");
                return false;
            }

            if (alarm.getAlarmConfigId() != ALARM_CONFIG_ID_GE_ENGINE_ON) {
                log.info("GeAlarmFan: alarma ignorada. alarmConfigId=" + alarm.getAlarmConfigId());
                return true;
            }

            boolean activation = alarm.getDeactivation() == null;

            log.info("GeAlarmFan: procesando "
                + (activation ? "ACTIVACION" : "DESACTIVACION")
                + " de alarma " + ALARM_CONFIG_ID_GE_ENGINE_ON
                + " para Element " + element.getElementTypeId() + ":" + element.getId());

            List<ElementHierarchy> childs = EntitiesManager.getInstance().getsByParent(
                element.getElementTypeId(),
                element.getId(),
                false
            );

            log.info("GeAlarmFan: hijos encontrados = " + (childs == null ? 0 : childs.size()));

            if (childs == null || childs.isEmpty()) {
                return true;
            }

            List<Map> fans = new ArrayList();

            for (ElementHierarchy child : childs) {
                Element childElement = EntitiesManager.getInstance().getElement(
                    child.getChildElementTypeId(),
                    child.getChildElementId()
                );

                if (childElement == null) {
                    log.warn("GeAlarmFan: no se pudo recuperar hijo "
                        + child.getChildElementTypeId() + ":" + child.getChildElementId());
                    continue;
                }

                Long hours = getTotalOperatingHours(childElement);
                boolean hasConcreteAlarm = hasConcreteFanAlarm(childElement);

                fans.add([
                    element: childElement,
                    hours: hours,
                    hasConcreteAlarm: hasConcreteAlarm
                ]);

                log.info("GeAlarmFan: fan "
                    + childElement.getElementTypeId() + ":" + childElement.getId()
                    + ", alias=" + childElement.getAlias()
                    + ", hasConcreteAlarm=" + hasConcreteAlarm
                    + ", totalOperatingHours=" + hours);
            }

            if (activation) {
                activateSelectedFansByPairs(element, fans);
            } else {
                deactivateAllFanAlarms(fans);
            }

        } catch (Exception e) {
            log.error("GeAlarmFan error: " + e.getMessage());
            log.debug(ExceptionUtils.getStackTrace(e));
        }

        return res;
    }

    Long getTotalOperatingHours(Element fan) {
        ElementValue value = EntitiesManager.getInstance().getElementValueMeasure(
            fan,
            PARAM_MEASURE_TOTAL_OPERATING_HOURS
        );

        if (value == null || value.getValue() == null) {
            return 0L;
        }

        try {
            return Long.parseLong(value.getValue());
        } catch (Exception e) {
            log.warn("GeAlarmFan: no se pudieron parsear horas para fan "
                + fan.getElementTypeId() + ":" + fan.getId()
                + ", value=" + value.getValue());

            return 0L;
        }
    }

    boolean hasConcreteFanAlarm(Element fan) {
        if (fan.getAlarms() == null || fan.getAlarms().isEmpty()) {
            return false;
        }

        for (Alarm fanAlarm : fan.getAlarms()) {
            if (fanAlarm == null) {
                continue;
            }

            if (fanAlarm.getDeactivation() == null
                && FAN_ALARM_CONFIG_IDS.contains(fanAlarm.getAlarmConfigId())) {

                log.info("GeAlarmFan: fan "
                    + fan.getElementTypeId() + ":" + fan.getId()
                    + " tiene alarma activa concreta "
                    + fanAlarm.getAlarmConfigId());

                return true;
            }
        }

        return false;
    }

    int getMaxRunningFans(Element geElement) {
        if (geElement.getId() == 1L) {
            return 6;
        }

        if (geElement.getId() == 2L) {
            return 7;
        }

        log.warn("GeAlarmFan: GE no configurado. Element "
            + geElement.getElementTypeId() + ":" + geElement.getId()
            + ". Se usa maxRunningFans=7 por defecto");

        return 7;
    }

    void activateSelectedFansByPairs(Element geElement, List<Map> fans) {

        fans.sort { a, b ->
            Element fanA = a.element as Element;
            Element fanB = b.element as Element;

            return fanA.getId() <=> fanB.getId();
        }

        int maxRunningFans = getMaxRunningFans(geElement);
        int fansToAlarm = fans.size() - maxRunningFans;

        if (fansToAlarm <= 0) {
            log.info("GeAlarmFan: no hay que alarmar ventiladores. fans="
                + fans.size() + ", maxRunningFans=" + maxRunningFans);
            return;
        }

        List<Map> pairs = buildFanPairs(fans);

        pairs.sort { a, b ->
            int alarmCompare = Boolean.compare(
                b.hasConcreteAlarm as boolean,
                a.hasConcreteAlarm as boolean
            );

            if (alarmCompare != 0) {
                return alarmCompare;
            }

            return (b.totalHours as Long) <=> (a.totalHours as Long);
        };

        List<Map> selectedFans = new ArrayList();

        int completePairsToAlarm = fansToAlarm / 2;
        boolean needsSingleFan = (fansToAlarm % 2) != 0;

        int selectedPairs = 0;

        for (Map pairInfo : pairs) {

            if (selectedPairs >= completePairsToAlarm) {
                break;
            }

            for (Map fanInfo : pairInfo.fans) {
                selectedFans.add(fanInfo);
            }

            selectedPairs++;

            log.info("GeAlarmFan: pareja seleccionada para alarmar. hasConcreteAlarm="
                + pairInfo.hasConcreteAlarm
                + ", totalHours=" + pairInfo.totalHours);
        }

        if (needsSingleFan) {

            List<Map> remainingFans = fans.findAll { fanInfo ->
                !selectedFans.any { selected ->
                    (selected.element as Element).getId() == (fanInfo.element as Element).getId()
                }
            };

            remainingFans.sort { a, b ->

                int alarmCompare = Boolean.compare(
                    b.hasConcreteAlarm as boolean,
                    a.hasConcreteAlarm as boolean
                );

                if (alarmCompare != 0) {
                    return alarmCompare;
                }

                return (b.hours as Long) <=> (a.hours as Long);
            };

            if (!remainingFans.isEmpty()) {

                Map singleFan = remainingFans.get(0);
                selectedFans.add(singleFan);

                Element fan = singleFan.element as Element;

                log.info("GeAlarmFan: ventilador suelto seleccionado para completar impar "
                    + fan.getElementTypeId() + ":" + fan.getId()
                    + ", hasConcreteAlarm=" + singleFan.hasConcreteAlarm
                    + ", hours=" + singleFan.hours);
            }
        }

        log.info("GeAlarmFan: GE "
            + geElement.getElementTypeId() + ":" + geElement.getId()
            + " maxRunningFans=" + maxRunningFans
            + ", totalFans=" + fans.size()
            + ", fansToAlarm=" + fansToAlarm
            + ", selectedFans=" + selectedFans.size());

        SetActivationAlarmsCommand command = new SetActivationAlarmsCommand();

        for (Map fanInfo : selectedFans) {

            Element fan = fanInfo.element as Element;

            log.info("GeAlarmFan: ACTIVAR alarma "
                + ALARM_CONFIG_ID_FAN_TO_SET
                + " en fan "
                + fan.getElementTypeId() + ":" + fan.getId()
                + ", hasConcreteAlarm=" + fanInfo.hasConcreteAlarm
                + ", hours=" + fanInfo.hours);

            command.add(
                fan,
                EntitiesManager.getInstance().getAlarmConfig(ALARM_CONFIG_ID_FAN_TO_SET)
            );
        }

        EntitiesManager.getInstance().sendCommand(command);
    }

    List<Map> buildFanPairs(List<Map> fans) {

        List<Map> pairs = new ArrayList();

        for (int i = 0; i < fans.size(); i += 2) {

            List<Map> pairFans = new ArrayList();

            Map firstFan = fans.get(i);
            pairFans.add(firstFan);

            if ((i + 1) < fans.size()) {
                Map secondFan = fans.get(i + 1);
                pairFans.add(secondFan);
            }

            boolean pairHasConcreteAlarm = false;
            Long pairTotalHours = 0L;

            for (Map fanInfo : pairFans) {

                if (fanInfo.hasConcreteAlarm) {
                    pairHasConcreteAlarm = true;
                }

                pairTotalHours = pairTotalHours + (fanInfo.hours as Long);
            }

            pairs.add([
                fans: pairFans,
                hasConcreteAlarm: pairHasConcreteAlarm,
                totalHours: pairTotalHours
            ]);

            log.info("GeAlarmFan: pareja creada index=" + pairs.size()
                + ", numFans=" + pairFans.size()
                + ", hasConcreteAlarm=" + pairHasConcreteAlarm
                + ", totalHours=" + pairTotalHours);
        }

        return pairs;
    }

    void deactivateAllFanAlarms(List<Map> fans) {

        SetDeactivationAlarmsCommand command = new SetDeactivationAlarmsCommand();

        for (Map fanInfo : fans) {

            Element fan = fanInfo.element as Element;

            log.info("GeAlarmFan: DESACTIVAR alarma "
                + ALARM_CONFIG_ID_FAN_TO_SET
                + " en fan "
                + fan.getElementTypeId() + ":" + fan.getId());

            command.add(
                fan,
                EntitiesManager.getInstance().getAlarmConfig(ALARM_CONFIG_ID_FAN_TO_SET)
            );
        }

        EntitiesManager.getInstance().sendCommand(command);
    }
}