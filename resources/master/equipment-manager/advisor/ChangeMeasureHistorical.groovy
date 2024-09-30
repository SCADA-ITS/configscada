import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.tuple.Pair;

import com.revenga.rits.back.equipment.manager.helper.GroovyHelper;
import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.ElementValue;
import com.revenga.rits.back.equipment.manager.service.EntitiesManager;

/**
 *
 * ChangeMeasureSample.groovy: Groovy for change measures of equipment
 *
 */
class ChangeMeasureHistorical {
	
	org.apache.logging.log4j.Logger log;
	
	ChangeMeasureHistorical(org.apache.logging.log4j.Logger log) {

		this.log = log;
	}
	
	boolean process(Element element, List<Pair<ElementValue, ElementValue>> elementValues) {
		try {

			List<ElementValue> values = new ArrayList();
			values = EntitiesManager.instance.getElementValues(element.getElementTypeId(), element.id)
			ElementValue elementValue = new ElementValue();

			if (!CollectionUtils.isEmpty(elementValues)) {
				
				for (Pair<ElementValue, ElementValue> pair : elementValues) {

					ElementValue newElementValue = pair.getRight();

					elementValue.setElementTypeId(newElementValue.getElementTypeId());
					elementValue.setElementTypeParamId(newElementValue.getElementTypeParamId());
					elementValue.setParamTypeId(newElementValue.getParamTypeId());
					elementValue.setElementId(newElementValue.getId());
					elementValue.setValue(newElementValue.getValue());
					
					values.add(elementValue);
				}
				
				element.setElementValues(values);
				
				GroovyHelper.sendElementToHistoriclManager(element);

			}
			
			return true;
			
		} catch (Exception e) {
			
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}
		
		return false;
	}
}
