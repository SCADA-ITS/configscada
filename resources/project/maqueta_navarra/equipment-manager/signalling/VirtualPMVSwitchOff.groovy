import java.util.ArrayList;
import java.util.List;
import com.revenga.rits.back.equipment.manager.service.EntitiesManager;
import com.revenga.rits.back.data.core.model.command.Command;
import com.revenga.rits.back.data.core.model.PmCommandElement;
import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.ElementValue;
import com.revenga.rits.back.data.core.model.PmCommandElementValue;
import com.revenga.rits.back.data.core.model.command.SetElementValuesPublishCommand;

 class VirtualPMVSignalling {
	
    //BBDD Params
	static final Long TYPE_PARAM_CONFIG = 1L;
	static final Long TYPE_PARAM_MEASURE = 2L;
	static final Long ELEMENT_TYPE_ID_PMV = 71L;
	
	//Detector Params
	static final Long PARAM_MEASURE_DATA_JSON = 3L;

	org.apache.logging.log4j.Logger log;
	
	 VirtualPMVSignalling(org.apache.logging.log4j.Logger log) {

		this.log = log;
	}
	
	 List<Command> process(PmCommandElement command) {
		
		List<Command> commands = null;
		List<ElementValue> listElements = new ArrayList();
		
		listElements.add(elementSetValue(PARAM_MEASURE_DATA_JSON, TYPE_PARAM_MEASURE, command.getElementId(), ""));	

		
		ElementValue[] itemsArray = new ElementValue[listElements.size()];
		itemsArray = listElements.toArray(itemsArray);
		EntitiesManager.getInstance().putElementValues(itemsArray);	
		
		SetElementValuesPublishCommand setElementValuesPublishCommand = new SetElementValuesPublishCommand();
		setElementValuesPublishCommand.setElementValues(new HashSet<>(listElements));

		Command cmd = setElementValuesPublishCommand;

		if(cmd != null){

			commands = new ArrayList<> ();
			commands.add(cmd);
		}
		
		return commands;
	}	
	
	ElementValue elementSetValue(Long elementTypeParamId, Long paramTypeId, Long elementId, String value){
		ElementValue element = new ElementValue();
	    element.setElementTypeId(ELEMENT_TYPE_ID_PMV);
	    element.setElementTypeParamId(elementTypeParamId);
	    element.setParamTypeId(paramTypeId);
	    element.setElementId(elementId);
	    element.setValue(value);
	    
	    return element;
	}
}
