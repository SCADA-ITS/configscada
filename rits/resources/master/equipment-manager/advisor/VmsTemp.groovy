import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.tuple.Pair;

import com.revenga.rits.back.data.core.model.DataType;
import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.ElementValue;
import com.revenga.rits.back.data.core.model.ElementHierarchy;
import com.revenga.rits.back.data.core.model.CommandElement;
import com.revenga.rits.back.data.core.model.CommandElementValue;
import com.revenga.rits.back.data.core.model.CommandElementTypeParam;
import com.revenga.rits.back.data.core.model.command.SignallingCommand;
import com.revenga.rits.back.data.core.model.command.SignallingCommand.SignallingParam;
import com.revenga.rits.back.equipment.manager.service.EntitiesManager;

/**
 *
 * VmsTemp.groovy: Save the measurement with the current sigRnaling value of the panel
 *
 */
class VmsTemp {

	// ID Elements Params
	static final Long PARAM_TYPE_MEASURE = 2L;
	static final Long PARAM_MEASURE_SURFACE_TEMPERATURE = 1L;
	static final Long PARAM_MEASURE_AIR_TEMPERATURE = 17L;
	static final Long PARAM_COMMAND_SIGNALLING = 2L;
	static final int DATA_TYPE_ALPHANUMERIC = 1;
	static final Long ELEMENT_TYPE_VMS_TEMPERATURE = 71L;
	static final int PARAM_ID_1 = 1;
	
	
	org.apache.logging.log4j.Logger log;
	
	VmsTemp(org.apache.logging.log4j.Logger log) {

		this.log = log;
	}
	
	boolean process(Element element, List<Pair<ElementValue, ElementValue>> elementValues) {
		String surface_temperature, air_temperature;

		try{
			if (!CollectionUtils.isEmpty(elementValues)) {
				
				for (Pair<ElementValue, ElementValue> pair : elementValues) {
					
					ElementValue elementValue = pair.getRight(); //Actual value
					
					switch(elementValue.getElementTypeParamId()){
						case PARAM_MEASURE_SURFACE_TEMPERATURE:
							surface_temperature = elementValue.getValue();
							break;
						case PARAM_MEASURE_AIR_TEMPERATURE:
							air_temperature = elementValue.getValue();
							break;
						default:
						break;
					}
				}
				
				if(surface_temperature.equals(null) ^ air_temperature.equals(null)) { // Si uno de los dos no es nulo (XOR)
					//Obtenemos el ultimo valor
					List<ElementValue> elementValueList;
					elementValueList = EntitiesManager.getInstance().getElementValues(element.getElementTypeId(), element.getId());
											
					for (ElementValue elementValue : elementValueList) {
						if(elementValue.getParamTypeId() == PARAM_TYPE_MEASURE)
						{
							
							switch(elementValue.getElementTypeParamId()){
								case PARAM_MEASURE_SURFACE_TEMPERATURE:
									if(surface_temperature.equals(null)){
										surface_temperature = elementValue.getValue();
									
									}			
									 
									break;
								case PARAM_MEASURE_AIR_TEMPERATURE:
									if(air_temperature.equals(null)){
										air_temperature = elementValue.getValue();		
									}
								
									break;
								default:
								break;
							}
							
						}
								
					}
				}
				
				
				//Llamo al comando de señalizacion
				if (!(surface_temperature.equals(null) && air_temperature.equals(null))){
					List<ElementHierarchy> childs = EntitiesManager.getInstance().getsByParent(element.getElementTypeId(), element.getId(), false);
	
					for (ElementHierarchy child : childs){
						
						//Creo el comando que realizará la señalizacion
						EntitiesManager.getInstance().sendCommand(rellenaSignallingCommand(child, surface_temperature + "#" + air_temperature));
					}		
				}
			}
			return true;
		} catch (Exception e) {
			
			log.error(e.getMessage());
			log.error(ExceptionUtils.getStackTrace(e));
		}
		
		return false;
	}
	
	SignallingCommand rellenaSignallingCommand(ElementHierarchy element, String paramValue){
		SignallingCommand signallingCommand = new SignallingCommand();
		
		signallingCommand.setSignallingCommandId(PARAM_COMMAND_SIGNALLING);
		signallingCommand.setElementTypeId(element.getChildElementTypeId());
		signallingCommand.setElementId(element.getChildElementId());

		signallingCommand.setSignallingParams(rellenaSignallingParam(paramValue));

		return signallingCommand;
	}
	
	List<SignallingParam> rellenaSignallingParam(String paramValue){
		List<SignallingParam> signallingParams = new ArrayList<>();
		
		SignallingParam signallingParam = new SignallingParam();

		signallingParam.setId(PARAM_ID_1);
		signallingParam.setType(DATA_TYPE_ALPHANUMERIC);
		signallingParam.setValue(paramValue);

		signallingParams.add(signallingParam);

		return signallingParams;
	}
}
