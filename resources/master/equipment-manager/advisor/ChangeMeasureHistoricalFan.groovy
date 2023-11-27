import org.apache.commons.collections4.CollectionUtils
import org.apache.commons.lang3.exception.ExceptionUtils
import org.apache.commons.lang3.tuple.Pair

import com.revenga.rits.back.equipment.manager.helper.GroovyHelper
import com.revenga.rits.back.data.core.model.Element
import com.revenga.rits.back.data.core.model.ElementValue
import com.revenga.rits.back.equipment.manager.service.EntitiesManager;


/**
 * ChangeMeasureHistoricalFan.groovy: Groovy for change measures of equipment
 */
class ChangeMeasureHistoricalFan {

    //Las posiciones de los arrays corresponden con las medidas
    static final Integer[] FAN_PARAMS = [2, 3, 4, 8, 9, 16, 17, 18, 19]
    static final Integer[] FAN_PERCENTS = [5, 5, 5, 5, 5, 5, 5, 5, 5]
    static final Long FAN = 9
    static final Integer ZERO = 0

    org.apache.logging.log4j.Logger log

    ChangeMeasureHistoricalFan(org.apache.logging.log4j.Logger log) {
        this.log = log
    }

    boolean process(Element element, List<Pair<ElementValue, ElementValue>> elementValues) {
        List<ElementValue> listElementsValues
        float porcentaje

        try {
            listElementsValues = []

            if (!CollectionUtils.isEmpty(elementValues)) {
                for (Pair<ElementValue, ElementValue> pair : elementValues) {
                    if (FAN_PARAMS.contains(pair.right.elementTypeParamId)) {
                        if (pair.left.value == '' || pair.left.value == null) {
                            listElementsValues = EntitiesManager.instance.getElementValues(FAN, element.id)
                        } else {
                            if (pair.right.value.indexOf('.') == -1) {
                                int oldValueInt = Integer.valueOf(pair.left.value)
                                int newValueInt = Integer.valueOf(pair.right.value)
                                log.debug('DEBUG oldValueInt -> ' + oldValueInt)
                                log.debug('DEBUG newValueInt -> ' + newValueInt)
                                porcentaje = calcularPorcentaje(oldValueInt, newValueInt)
                            } else {
                                float oldValueFloat = Float.valueOf(pair.left.value.substring(ZERO, pair.left.value.indexOf('.')))
                                float newValueFloat = Float.valueOf(pair.right.value.substring(ZERO, pair.right.value.indexOf('.')))
                                log.debug('DEBUG oldValueFloat -> ' + oldValueFloat)
                                log.debug('DEBUG newValueFloat -> ' + newValueFloat)
                                porcentaje = calcularPorcentaje(oldValueFloat, newValueFloat)
                            }

                            int posArray = FAN_PARAMS.findIndexOf {
                                pair.right.elementTypeParamId % pair.right.elementTypeParamId == ZERO
                            }

                            if (porcentaje > FAN_PERCENTS[posArray]) {
                                listElementsValues = EntitiesManager.instance.getElementValues(FAN, element.id)
                            } else {
                                return true
                            }
                        }
					} else {
                        listElementsValues = EntitiesManager.instance.getElementValues(FAN, element.id)
                    }
                }

                GroovyHelper.sendElementToHistoriclManager(element)
            }

            return true
		} catch (Exception e) {
            log.error(e.getMessage())
            log.debug(ExceptionUtils.getStackTrace(e))
        }

        return false
    }

    float calcularPorcentaje(def oldValue, def newValue) {
        float porcentaje = Math.abs(((newValue - oldValue) / oldValue) * 100)
        log.debug('DEBUG porcentaje -> ' + porcentaje)
        return porcentaje
    }

}
