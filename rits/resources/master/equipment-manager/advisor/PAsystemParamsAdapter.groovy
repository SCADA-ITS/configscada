import com.revenga.rits.back.data.core.model.command.SetElementStatesChangeCommand

import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.exception.ExceptionUtils;
import groovy.json.JsonSlurper;
import java.time.Instant;
import org.apache.commons.lang3.tuple.Pair;

import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.ElementValue;
import com.revenga.rits.back.data.core.model.ElementState;
import com.revenga.rits.back.data.core.model.ElementTypeState;
import com.revenga.rits.back.data.core.model.ElementHierarchy;
import com.revenga.rits.back.equipment.manager.service.EntitiesManager;
import com.revenga.rits.back.data.core.model.command.SetElementValuesPublishCommand;


/**
 *
 * PAsystemParamsAdapter: Save de measures of PA zones
 *
 */

class PAsystemParamsAdapter {

 	//BBDD Params
	static final Long TYPE_PARAM_CONFIG = 1L;
	static final Long TYPE_PARAM_MEASURE = 2L;
	static final Long PARAM_CONFIG_ZONE_ID = 5L;
	
	
	// ID Elements Params
	static final Long PARAM_TYPE_MEASURE = 2L;
	static final Long GRAPHIC_TYPE_AUDIO = 3L;
	static final Long GRAPHIC_PROTOCOL_TYPE_PARAM_AUDIO = 1L;
	static final Long GRAPHIC_PROTOCOL_TYPE_PARAM_TEXT = 2L;
	static final Long PARAM_MEASURE_AUDIO = 1001L;
	
	// Child ID Elements Params
	static final Long PARAM_MEASURE_MESSAGE_ID = 1001L;
	static final Long PARAM_MEASURE_MESSAGE_DESC = 1002L;
	static final Long PARAM_MEASURE_MESSAGE_TEXT = 1003L;
	static final Long PARAM_MEASURE_MESSAGE_TAG = 1004L;
	
	//Status PA SYSTEM
	static final Long PA_SYSTEM_PLAYING = 8L;
	
	
	static final Long ELEMENT_TYPE_PA_ZONE = 16L;
	static final Long ELEMENT_TYPE_PA_GROUP = 96L;

	org.apache.logging.log4j.Logger log;
	
	PAsystemParamsAdapter(org.apache.logging.log4j.Logger log) {

		this.log = log;
	}
	
	boolean process(Element element, List<Pair<ElementValue, ElementValue>> elementValues) {
		boolean res = true;
		try {
			
			if (elementValues != null) {
				// Search childs
				List<ElementHierarchy> childs;
				childs = EntitiesManager.getInstance().getsByParent(element.getElementTypeId(), element.getId(), false);
				
				// Parse the json value
				def pa_message = new JsonSlurper().parseText(elementValues.get(0).getRight().getValue());
			
				if(childs)	
				{
					
		
					for (ElementHierarchy child : childs)
					{
						if(child.getChildElementTypeId() == ELEMENT_TYPE_PA_ZONE) {
							
							Element childElement = EntitiesManager.getInstance().getElement(child.getChildElementTypeId(), child.getChildElementId());
							ElementValue zoneId = EntitiesManager.getInstance().getElementValueConfig(childElement, PARAM_CONFIG_ZONE_ID);
							boolean bZoneFound = false;
							
							for (int i = 0; i < pa_message.messages.size();i++)
							{
									
								if(pa_message.messages.get(i).status == PA_SYSTEM_PLAYING)
								{
									def zones = pa_message.messages.get(i).zones;
														
									for (int j = 0; j < zones.size();j++)
									{
									
										if(Integer.parseInt(zoneId.getValue()) == zones.get(j))
										{
											
											List<ElementValue> listElements = new ArrayList();
											listElements.add(getElementValue(PARAM_MEASURE_MESSAGE_ID,PARAM_TYPE_MEASURE,childElement.getId(),childElement.getElementTypeId(),pa_message.messages.get(i).messagesIds.toString()));
											listElements.add(getElementValue(PARAM_MEASURE_MESSAGE_DESC,PARAM_TYPE_MEASURE,childElement.getId(),childElement.getElementTypeId(),pa_message.messages.get(i).messages_names.toString()));
											listElements.add(getElementValue(PARAM_MEASURE_MESSAGE_TAG,PARAM_TYPE_MEASURE,childElement.getId(),childElement.getElementTypeId(),pa_message.messages.get(i).type_message.toString()));
																				
											SendMeasures(listElements);
																																
											bZoneFound = true;
											
										}
									}
								}
								
							}
							if(!bZoneFound)
							{
								
								List<ElementValue> listElements = new ArrayList();
								listElements.add(getElementValue(PARAM_MEASURE_MESSAGE_ID,PARAM_TYPE_MEASURE,childElement.getId(),childElement.getElementTypeId()," "));
								listElements.add(getElementValue(PARAM_MEASURE_MESSAGE_DESC,PARAM_TYPE_MEASURE,childElement.getId(),childElement.getElementTypeId()," "));
								listElements.add(getElementValue(PARAM_MEASURE_MESSAGE_TAG,PARAM_TYPE_MEASURE,childElement.getId(),childElement.getElementTypeId()," "));
								listElements.add(getElementValue(PARAM_MEASURE_MESSAGE_TEXT,PARAM_TYPE_MEASURE,childElement.getId(),childElement.getElementTypeId()," "));
								SendMeasures(listElements);
							
							}
							
						}
						
						else if(child.getChildElementTypeId() == ELEMENT_TYPE_PA_GROUP) {
							
							Element childElement = EntitiesManager.getInstance().getElement(child.getChildElementTypeId(), child.getChildElementId());
							ElementValue groupId = EntitiesManager.getInstance().getElementValueConfig(childElement, PARAM_CONFIG_ZONE_ID);
							boolean bGroupFound = false;
							
							for (int i = 0; i < pa_message.messages.size();i++)
							{
									
								if(pa_message.messages.get(i).status == PA_SYSTEM_PLAYING)
								{
									def groups = pa_message.messages.get(i).groups;
														
									for (int j = 0; j < groups.size();j++)
									{
									
										if(Integer.parseInt(groupId.getValue()) == groups.get(j))
										{
											
											List<ElementValue> listElements = new ArrayList();
											listElements.add(getElementValue(PARAM_MEASURE_MESSAGE_ID,PARAM_TYPE_MEASURE,childElement.getId(),childElement.getElementTypeId(),pa_message.messages.get(i).messagesIds.toString()));
											listElements.add(getElementValue(PARAM_MEASURE_MESSAGE_DESC,PARAM_TYPE_MEASURE,childElement.getId(),childElement.getElementTypeId(),pa_message.messages.get(i).messages_names.toString()));
											listElements.add(getElementValue(PARAM_MEASURE_MESSAGE_TAG,PARAM_TYPE_MEASURE,childElement.getId(),childElement.getElementTypeId(),pa_message.messages.get(i).type_message.toString()));
																				
											SendMeasures(listElements);
																																
											bGroupFound = true;
											
										}
									}
								}
								
							}
							if(!bGroupFound)
							{
								
								List<ElementValue> listElements = new ArrayList();
								listElements.add(getElementValue(PARAM_MEASURE_MESSAGE_ID,PARAM_TYPE_MEASURE,childElement.getId(),childElement.getElementTypeId()," "));
								listElements.add(getElementValue(PARAM_MEASURE_MESSAGE_DESC,PARAM_TYPE_MEASURE,childElement.getId(),childElement.getElementTypeId()," "));
								listElements.add(getElementValue(PARAM_MEASURE_MESSAGE_TAG,PARAM_TYPE_MEASURE,childElement.getId(),childElement.getElementTypeId()," "));
								listElements.add(getElementValue(PARAM_MEASURE_MESSAGE_TEXT,PARAM_TYPE_MEASURE,childElement.getId(),childElement.getElementTypeId()," "));
								SendMeasures(listElements);
							
							}
							
						}
					
					}
				}
			}
	
			
		} catch (Exception e) {
			
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}
		
			return res;
		}
		
	ElementValue getElementValue(Long elementTypeParamId, Long paramTypeId, Long elementId, Long elementTypeId,String value){
		ElementValue element = new ElementValue();
		element.setElementTypeId(elementTypeId);
		element.setElementTypeParamId(elementTypeParamId);
		element.setParamTypeId(paramTypeId);
		element.setElementId(elementId);
		element.setValue(value);
		
			
		return element;
	}
	void SendMeasures(List<ElementValue> listElements){
		ElementValue[] itemsArray = new ElementValue[listElements.size()];
		itemsArray = listElements.toArray(itemsArray);
		EntitiesManager.getInstance().putElementValues(itemsArray);
		
		SetElementValuesPublishCommand setElementValuesPublishCommand = new SetElementValuesPublishCommand();
		setElementValuesPublishCommand.setElementValues(new HashSet<>(listElements));
		EntitiesManager.getInstance().sendCommand(setElementValuesPublishCommand);
	}
}