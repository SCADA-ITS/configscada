import java.util.List
import java.util.ArrayList
import java.util.Properties

import org.apache.commons.collections4.CollectionUtils;

import com.revenga.rits.back.data.core.model.Element
import com.revenga.rits.back.data.core.model.ElementValue
import com.revenga.rits.back.data.core.model.ElementHierarchy;
import com.revenga.rits.back.data.core.model.VmsGraphicGraphicGroupValue
import com.revenga.rits.back.data.core.util.ResourcesUtil
import com.revenga.rits.back.io.controller.service.EntitiesManager
import com.revenga.rits.back.data.core.model.command.SetElementValuesPublishCommand;
import com.revenga.rits.back.data.core.model.command.SetActivationAlarmsCommand;
import com.revenga.rits.back.data.core.model.command.SetDeactivationAlarmsCommand;

import java.net.URLDecoder

/**
 *
 * InputAdapter_SOS.groovy: Save the measurement with the current signaling value of the panel
 *
 */
class InputAdapter_SOS {

    org.apache.logging.log4j.Logger log;

	static final Long ELEMENT_TYPE_SOS = 11L
    static final Long PARAM_TYPE_MEASURE = 2L

    static final Long ELEMENT_TYPE_PARAM_VOL_AUDIO = 1L
    static final Long ELEMENT_TYPE_PARAM_MEG_STATE = 15L
    static final Long ELEMENT_TYPE_PARAM_VOL_MEG = 16L
    
    static final Integer POS_ORD = 1; //Posición del byte que indica la orden
    //Trama de megafonía 0x56
    static final Integer POS_MEG_STATE = 2; //Posición del byte con la información del estado de la megafonía
    static final Integer POS_VOL_MEG = 3; //Posición del byte con la información del volumen de la megafonía
    //Trama de estado SOS 0x89
    static final Integer POS_STATE_MASTER = 2; //Posición del volumen de salida del poste principal
    static final Integer POS_STATE_SLAVE = 3; //Posición del volumen de salida del poste principal
    static final Integer POS_VOL_SOS_MASTER = 6; //Posición del volumen de salida del poste principal
    static final Integer POS_VOL_SOS_SLAVE = 7; //Posición del volumen de salida del poste secundario
    
    static final Long[] alarm = [70011L,60011L,50011L,40011L,0,20011L,30011L,0];

    InputAdapter_SOS(org.apache.logging.log4j.Logger log) {
        this.log = log
    }

    boolean res_meg_qry(Element element, String value, List<ElementValue> elementValues) {

		List<Integer> contentByte = checkFrame(value, 0x56, "megafonía")

		if (!CollectionUtils.isEmpty(contentByte)){

			//Para el poste principal
			elementValues.add(elementSetValue(ELEMENT_TYPE_PARAM_MEG_STATE, PARAM_TYPE_MEASURE, element.getId(),String.valueOf(contentByte.get(POS_MEG_STATE))));
			elementValues.add(elementSetValue(ELEMENT_TYPE_PARAM_VOL_MEG, PARAM_TYPE_MEASURE, element.getId(),String.valueOf(contentByte.get(POS_VOL_MEG))));
			
			publishElementValues(elementValues)
			
			//Para el poste secundario en caso de existir
			List<ElementHierarchy> elementHierarchies = EntitiesManager.getInstance().getsByParent(
								element.getElementTypeId(), element.getId());
			
			if (!CollectionUtils.isEmpty(elementHierarchies)) {

				for (ElementHierarchy elementHierarchie : elementHierarchies) {
					
					Element elementChild = EntitiesManager.getInstance().getElement(elementHierarchie.getChildElementTypeId(),  elementHierarchie.getChildElementId());
					
					if(elementChild != null) {
						List<ElementValue> listElements = new ArrayList();
						
						listElements.add(elementSetValue(ELEMENT_TYPE_PARAM_VOL_MEG, PARAM_TYPE_MEASURE, elementChild.getId(),String.valueOf(contentByte.get(POS_VOL_MEG))));
						listElements.add(elementSetValue(ELEMENT_TYPE_PARAM_MEG_STATE, PARAM_TYPE_MEASURE, elementChild.getId(),String.valueOf(contentByte.get(POS_MEG_STATE))));
						
						publishElementValues(listElements)
					}
				}
			}

		}		
        
        return true;
    }

    boolean res_tst_man(Element element, String value, List<ElementValue> elementValues) {
    
		List<Integer> contentByte = checkFrame(value, 0x89, "SOS")

		if (!CollectionUtils.isEmpty(contentByte)){

			publishAlarms(element, contentByte, POS_STATE_MASTER)

			elementValues.add(elementSetValue(ELEMENT_TYPE_PARAM_VOL_AUDIO, PARAM_TYPE_MEASURE, element.getId(),String.valueOf(contentByte.get(POS_VOL_SOS_MASTER))));
			
			publishElementValues(elementValues)

			//Para el poste secundario en caso de existir
			List<ElementHierarchy> elementHierarchies = EntitiesManager.getInstance().getsByParent(
								element.getElementTypeId(), element.getId());
			
			if (!CollectionUtils.isEmpty(elementHierarchies)) {

				for (ElementHierarchy elementHierarchie : elementHierarchies) {
					
					Element elementChild = EntitiesManager.getInstance().getElement(elementHierarchie.getChildElementTypeId(),  elementHierarchie.getChildElementId());
					
					if(elementChild != null) {
						List<ElementValue> listElements = new ArrayList();
						
						listElements.add(elementSetValue(ELEMENT_TYPE_PARAM_VOL_AUDIO, PARAM_TYPE_MEASURE, elementChild.getId(),String.valueOf(contentByte.get(POS_VOL_SOS_SLAVE))));
						
						publishElementValues(listElements)

						publishAlarms(elementChild, contentByte, POS_STATE_SLAVE)
					}
				}
			}
		}
        return true;
    }

    ElementValue elementSetValue(Long elementTypeParamId, Long paramTypeId, Long elementId, String value){
		ElementValue elementValue = new ElementValue();
	    elementValue.setElementTypeId(ELEMENT_TYPE_SOS);
	    elementValue.setElementTypeParamId(elementTypeParamId);
	    elementValue.setParamTypeId(paramTypeId);
	    elementValue.setElementId(elementId);
	    elementValue.setValue(value);
	    
	    return elementValue;
	}
	
	void publishElementValues(List<ElementValue> elementValues){
		EntitiesManager.getInstance().putElementValues(elementValues);
		
		SetElementValuesPublishCommand setElementValuesPublishCommand = new SetElementValuesPublishCommand();
		setElementValuesPublishCommand.setElementValues(new HashSet<>(elementValues));
		
		EntitiesManager.getInstance().sendCommand(setElementValuesPublishCommand);

	}

	void publishAlarms(Element element, List<Integer> contentByte, Integer position){
		SetActivationAlarmsCommand setActivationAlarmsCommandList = new SetActivationAlarmsCommand();
		SetDeactivationAlarmsCommand setDeactivationAlarmsCommandList = new SetDeactivationAlarmsCommand();
		
		for (int i = 0; i < 8; i++) {
			boolean isBitSet = (contentByte.get(position) & (1 << i)) != 0;
			
			if (isBitSet){
			
				setActivationAlarmsCommandList.add(element, EntitiesManager.getInstance().getAlarmConfig(alarm[i]));	
			} else {
			
				setDeactivationAlarmsCommandList.add(element, EntitiesManager.getInstance().getAlarmConfig(alarm[i]));
			}
		}
					
		EntitiesManager.getInstance().sendCommand(setActivationAlarmsCommandList);
		EntitiesManager.getInstance().sendCommand(setDeactivationAlarmsCommandList);

	}

	List<Integer> checkFrame(String value, int state, String equipment){
		List<Integer> contentByte = new ArrayList<Integer>();
		String aux = "";
		
		for(int i = 0; i<value.length(); i+=2){
			aux = value.substring(i,i+2);
			contentByte.add(Integer.parseInt(aux, 16));
		}
		
		if(!(contentByte.get(POS_ORD) & 0xFF).equals(state)){
			log.debug("Trama recibida no es de estado de " + equipment);
			contentByte = []
		}

		return contentByte

	}
}