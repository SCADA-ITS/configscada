import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.tuple.Pair;

import com.revenga.rits.back.equipment.manager.service.EntitiesManager;
import com.revenga.rits.back.equipment.manager.helper.GroovyHelper;
import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.ElementValue;

/**
 *
 * ChangeMeasureSample.groovy: Groovy for change measures of equipment
 *
 */
class ChangeMeasurePmv {
	
	static final Long PARAM_MEASURE_DATAJSON = 3L;
	org.apache.logging.log4j.Logger log;
	
	ChangeMeasurePmv(org.apache.logging.log4j.Logger log) {

		this.log = log;
	}
	
	boolean process(Element element, List<Pair<ElementValue, ElementValue>> elementValues) {
		ElementValue elementValues_aux = elementValues.get(0).getRight()
		int element_param_id = elementValues_aux.elementTypeParamId

		List<ElementValue> listElementsValues;
		try {

			if (element_param_id == 3){
				listElementsValues = new ArrayList();

				if (!CollectionUtils.isEmpty(elementValues)) {

					ElementValue newElementValue = EntitiesManager.getInstance().getElementValueMeasure(element, PARAM_MEASURE_DATAJSON);
					listElementsValues.add(newElementValue);

					element.setElementValues(listElementsValues);
				
					GroovyHelper.sendElementToHistoriclManager(element);
				}
			}
			
			return true;
			
		} catch (Exception e) {
			
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}
		
		return false;
	}
}
