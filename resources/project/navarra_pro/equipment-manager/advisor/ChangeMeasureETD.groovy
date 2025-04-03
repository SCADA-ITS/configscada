import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.tuple.Pair;

import com.revenga.rits.back.equipment.manager.service.EntitiesManager;
import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.ElementValue;
import com.revenga.rits.back.data.core.model.ElementHierarchy;
import com.revenga.rits.back.data.core.model.command.SetElementValuesPublishCommand;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import java.util.Random;

/**
 *
 * ChangeMeasureSample.groovy: Groovy for change measures of equipment
 *
 */
class ChangeMeasureETD {

    //BBDD Params
	static final Long TYPE_PARAM_CONFIG = 1L;
	static final Long TYPE_PARAM_MEASURE = 2L;
	static final Long PARAM_CONFIG_ORDER = 2L;
	static final Long ELEMENT_TYPE_ID_DET = 3L;
	
	//Detector Params
	static final Long PARAM_MEASURE_DATE = 1L;
	static final Long PARAM_MEASURE_PERIOD =2L;
	static final Long PARAM_MEASURE_CURRENT_DIRECTION = 3L;
	static final Long PARAM_MEASURE_NVEHICLES = 4L;
	static final Long PARAM_MEASURE_OCCUPANCY = 6L;
	static final Long PARAM_MEASURE_CONGESTION = 7L;
	static final Long PARAM_MEASURE_KAMIKAZE = 8L;
	static final Long PARAM_MEASURE_SPEED = 9L;
	static final Long PARAM_MEASURE_GAP = 10L;
	static final Long PARAM_MEASURE_FAIL_DATA = 13L;
	static final Long PARAM_MEASURE_LENGTH = 17L;
	
	static final Long PARAM_MEASURE_VEH_LENGTH_1 = 20L;
	static final Long PARAM_MEASURE_VEH_LENGTH_2 = 21L;

	static final Long PARAM_MEASURE_VEH_SPEED_1 = 40L;
	static final Long PARAM_MEASURE_VEH_SPEED_2 = 41L;
	static final Long PARAM_MEASURE_VEH_SPEED_3 = 42L;	
	
	//Values
	static final String ENABLE = "1";
	static final String DISABLE = "0";
	
	org.apache.logging.log4j.Logger log;
	
	ChangeMeasureETD(org.apache.logging.log4j.Logger log) {

		this.log = log;
	}
	
	boolean process(Element element, List<Pair<ElementValue, ElementValue>> elementValues) {
		int detArrayPos;		
		List<ElementValue> listElements;
		Random random = new Random();
		
		try {

			if (!CollectionUtils.isEmpty(elementValues)) {	
				
				List<ElementHierarchy> childs;
				childs = EntitiesManager.getInstance().getsByParent(element.getElementTypeId(), element.getId(), false);	
				
				listElements = new ArrayList();			
				
				for (ElementHierarchy child : childs){
					Element childElement = EntitiesManager.getInstance().getElement(child.getChildElementTypeId(), child.getChildElementId());					
					ElementValue detOrder = EntitiesManager.getInstance().getElementValueConfig(childElement, PARAM_CONFIG_ORDER);	
					
					detArrayPos = Integer.parseInt(detOrder.getValue()) - 1;
					
					LocalDateTime now = LocalDateTime.now();
					LocalDateTime roundedTime = now.withSecond(0).withNano(0);
					ZoneId zoneId = ZoneId.systemDefault();
			        ZonedDateTime zonedDateTime = roundedTime.atZone(zoneId);
			        long timestamp = zonedDateTime.toInstant().toEpochMilli();
					
					int intensidad = (int)(Math.random() * 10) + 1;
					int ocupacion = (int) (Math.random() * 100) + 1;
					int velocidad = (int) (Math.random() * (130 - 90 + 1)) + 90;
					int longitud = (int) (Math.random() * (47 - 44 + 1)) + 44;
					int gap = (int) (Math.random() * (50 - 10 + 1)) + 50;
					
					listElements.add(elementSetValue(PARAM_MEASURE_DATE, TYPE_PARAM_MEASURE, childElement.getId(),String.valueOf(timestamp)));	
					listElements.add(elementSetValue(PARAM_MEASURE_PERIOD, TYPE_PARAM_MEASURE, childElement.getId(),ENABLE));	
					listElements.add(elementSetValue(PARAM_MEASURE_CURRENT_DIRECTION, TYPE_PARAM_MEASURE, childElement.getId(),ENABLE));	
					listElements.add(elementSetValue(PARAM_MEASURE_NVEHICLES, TYPE_PARAM_MEASURE, childElement.getId(),String.valueOf(intensidad)));	
					listElements.add(elementSetValue(PARAM_MEASURE_OCCUPANCY, TYPE_PARAM_MEASURE, childElement.getId(),String.valueOf(ocupacion)));	
					listElements.add(elementSetValue(PARAM_MEASURE_KAMIKAZE, TYPE_PARAM_MEASURE, childElement.getId(),DISABLE));	
					listElements.add(elementSetValue(PARAM_MEASURE_SPEED, TYPE_PARAM_MEASURE, childElement.getId(),String.valueOf(velocidad)));	
					listElements.add(elementSetValue(PARAM_MEASURE_GAP, TYPE_PARAM_MEASURE, childElement.getId(),String.valueOf(gap)));	
					listElements.add(elementSetValue(PARAM_MEASURE_LENGTH, TYPE_PARAM_MEASURE, childElement.getId(),String.valueOf(longitud)));
					
					listElements.add(elementSetValue(PARAM_MEASURE_CONGESTION, TYPE_PARAM_MEASURE, childElement.getId(),DISABLE));						
					listElements.add(elementSetValue(PARAM_MEASURE_FAIL_DATA, TYPE_PARAM_MEASURE, childElement.getId(),DISABLE));	

					int long1 = random.nextInt(intensidad + 1);
					int long2 = intensidad - long1;
					int vel1 = random.nextInt(intensidad + 1);
					int vel2 = random.nextInt(intensidad - vel1 + 1);
					int vel3 = intensidad - vel1 - vel2;
					
					listElements.add(elementSetValue(PARAM_MEASURE_VEH_LENGTH_1, TYPE_PARAM_MEASURE, childElement.getId(),String.valueOf(long1)));
					listElements.add(elementSetValue(PARAM_MEASURE_VEH_LENGTH_2, TYPE_PARAM_MEASURE, childElement.getId(),String.valueOf(long2)));
					listElements.add(elementSetValue(PARAM_MEASURE_VEH_SPEED_1, TYPE_PARAM_MEASURE, childElement.getId(),String.valueOf(vel1)));
					listElements.add(elementSetValue(PARAM_MEASURE_VEH_SPEED_2, TYPE_PARAM_MEASURE, childElement.getId(),String.valueOf(vel2)));
					listElements.add(elementSetValue(PARAM_MEASURE_VEH_SPEED_3, TYPE_PARAM_MEASURE, childElement.getId(),String.valueOf(vel3)));
					
					ElementValue[] itemsArray = new ElementValue[listElements.size()];
					itemsArray = listElements.toArray(itemsArray);
					EntitiesManager.getInstance().putElementValues(itemsArray);
					
					SetElementValuesPublishCommand setElementValuesPublishCommand = new SetElementValuesPublishCommand();
					setElementValuesPublishCommand.setElementValues(new HashSet<>(listElements));	
					
					EntitiesManager.getInstance().sendCommand(setElementValuesPublishCommand);
				}	
			}
			
			return true;
			
		} catch (Exception e) {
			
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}
		
		return false;
	}
	
	ElementValue elementSetValue(Long elementTypeParamId, Long paramTypeId, Long elementId, String value){
		ElementValue element = new ElementValue();
	    element.setElementTypeId(ELEMENT_TYPE_ID_DET);
	    element.setElementTypeParamId(elementTypeParamId);
	    element.setParamTypeId(paramTypeId);
	    element.setElementId(elementId);
	    element.setValue(value);
	    
	    return element;
	}
}
