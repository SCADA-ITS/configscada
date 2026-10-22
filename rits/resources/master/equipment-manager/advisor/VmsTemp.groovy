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
	static final Long ELEMENT_TYPE_VMS_TEMPERATURE = 71L;
	static final Long PARAM_ID_1 = 1L;
	
	
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
					
					
					//Creo el comando que realizará la señalizacion
					List<CommandElement> commandElementList = rellenaCommandElement(element, surface_temperature, air_temperature);
					for(CommandElement commandElement : commandElementList){
			
						EntitiesManager.getInstance().sendCommand(rellenaSignallingCommand(commandElement));
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
	
	List<CommandElement> rellenaCommandElement(Element element, String surfaceTemp, String airTemp){
		//Creo el comando que realizará la señalizacion
		List<ElementHierarchy> childs;
		childs = EntitiesManager.getInstance().getsByParent(element.getElementTypeId(), element.getId(), false);

		List<CommandElement> commandElementList = new ArrayList<>();
		
		for(ElementHierarchy child : childs){
			
			int elementId_son = child.getChildElementId();			

			CommandElement commandElement = new CommandElement();
			commandElement.setCommandElementTypeId(PARAM_COMMAND_SIGNALLING);
			commandElement.setElementTypeId(ELEMENT_TYPE_VMS_TEMPERATURE);
			commandElement.setElementId(elementId_son);
			commandElement.setCommandElementValues(rellenaCommandElementValues(element, surfaceTemp, airTemp,child));
			commandElementList.add(commandElement);
		}
	
		
		return commandElementList;
	}
	
	List<CommandElementValue> rellenaCommandElementValues(Element element, String surfaceTemp, String airTemp,ElementHierarchy child){
		List<CommandElementValue> commandElementValues = new ArrayList<>();

		CommandElementValue commandElementValue = new CommandElementValue();
		commandElementValue.setCommandElementTypeId(PARAM_COMMAND_SIGNALLING);
		commandElementValue.setElementTypeId(ELEMENT_TYPE_VMS_TEMPERATURE);
		commandElementValue.setCommandElementTypeParamId(PARAM_ID_1);
		commandElementValue.setCommandElementId(child.getChildElementId());
		commandElementValue.setValue(airTemp + "#" + surfaceTemp);
		commandElementValue.setCommandElementTypeParam(rellenaCommandElementTypeParam());
		commandElementValues.add(commandElementValue);

		return commandElementValues;
	}
	
	CommandElementTypeParam rellenaCommandElementTypeParam(){
		CommandElementTypeParam commandElementTypeParam = new CommandElementTypeParam();
		
		commandElementTypeParam.setCommandElementTypeId(PARAM_COMMAND_SIGNALLING);
		commandElementTypeParam.setElementTypeId(ELEMENT_TYPE_VMS_TEMPERATURE);
		commandElementTypeParam.setDataTypeId(DataType.DATA_TYPE_ALPHANUMERIC);
		
		return commandElementTypeParam;
	}
	
	SignallingCommand rellenaSignallingCommand(CommandElement commandElement){
		SignallingCommand signallingCommand = new SignallingCommand();
		
		signallingCommand.setSignallingCommandId(commandElement.getCommandElementTypeId());
		signallingCommand.setElementTypeId(commandElement.getElementTypeId());
		signallingCommand.setElementId(commandElement.getElementId());

		signallingCommand.setSignallingParams(rellenaSignallingParam(commandElement));

		return signallingCommand;
	}
	
	List<SignallingParam> rellenaSignallingParam(CommandElement commandElement){
		List<SignallingParam> signallingParams = new ArrayList<>();
		
		for (CommandElementValue commandElementValue : commandElement.getCommandElementValues()) {
			if (commandElementValue.getCommandElementTypeParam() != null) {
		
				SignallingParam signallingParam = new SignallingParam();
		
				signallingParam
						.setId(commandElementValue.getCommandElementTypeParamId().intValue());
				signallingParam.setType(commandElementValue.getCommandElementTypeParam()
						.getDataTypeId().intValue());
				signallingParam.setValue(commandElementValue.getValue());
		
				signallingParams.add(signallingParam);
			}
		}

		return signallingParams;
	}
}
