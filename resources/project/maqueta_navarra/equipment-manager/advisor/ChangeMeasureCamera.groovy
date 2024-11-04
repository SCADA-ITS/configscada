import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.tuple.Pair;

import com.revenga.rits.back.equipment.manager.helper.GroovyHelper;
import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.ElementValue;

/**
 *
 * ChangeMeasureSample.groovy: Groovy for change measures of equipment
 *
 */
class ChangeMeasureCamera {
	
	org.apache.logging.log4j.Logger log;
	
	ChangeMeasureCamera(org.apache.logging.log4j.Logger log) {

		this.log = log;
	}
	
	boolean process(Element element, List<Pair<ElementValue, ElementValue>> elementValues) {

		List<ElementValue> listElementsValues;
		try {

			listElementsValues = new ArrayList();

			if (!CollectionUtils.isEmpty(elementValues)) {
				
				for (Pair<ElementValue, ElementValue> pair : elementValues) {

					ElementValue oldElementValue = pair.getLeft();
					ElementValue newElementValue = pair.getRight();
					
					listElementsValues.add(pair.getRight());

					String elementValueKey = "ElementValue:" +  oldElementValue.getElementTypeId() + ":" + oldElementValue.getElementId() + ":" + oldElementValue.getElementTypeParamId() + ":" + oldElementValue.getParamTypeId();

					log.debug("ChangeMeasureCamera -->  " + elementValueKey + " => " + oldElementValue.getValue() + " [Old] " + newElementValue.getValue() + " [New]");

				}
				element.setElementValues(listElementsValues);
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
