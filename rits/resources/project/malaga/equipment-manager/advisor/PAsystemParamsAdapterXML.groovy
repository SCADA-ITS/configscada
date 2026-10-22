import com.revenga.rits.back.data.core.model.command.SetElementStatesChangeCommand

import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.exception.ExceptionUtils;
import groovy.xml.*;
import java.time.Instant;
import org.apache.commons.lang3.tuple.Pair;
import groovy.json.JsonSlurper;

import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.ElementValue;
import com.revenga.rits.back.data.core.model.ElementState;
import com.revenga.rits.back.data.core.model.ElementTypeState;
import com.revenga.rits.back.data.core.model.ElementHierarchy;
import com.revenga.rits.back.equipment.manager.service.EntitiesManager;
import com.revenga.rits.back.data.core.model.command.SetElementValuesPublishCommand;
import com.revenga.rits.back.data.core.model.Alarm;
import com.revenga.rits.back.data.core.model.AlarmConfig;
import com.revenga.rits.back.data.core.model.command.SetDeactivationAlarmsCommand;
import com.revenga.rits.back.data.core.model.command.SetActivationAlarmsCommand;


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
	static final Long PARAM_CONFIG_GROUPS_ZONES = 6L;
	
	
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

	// ID Elements Params
	static final Long PARAM_MEASURE_MESSAGES_ON_AIR = 1L;
	static final Long PARAM_MEASURE_ZONES_LIST = 2L;
	
	//Status PA SYSTEM
	static final Long PA_SYSTEM_PLAYING = 8L;
	
	//Alarms
	static final Long ALARM_ZONE_BASE = 1600L;
	
	static final String STATUS_OK = "OK";
	
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
			
			
				if(elementValues.get(0).getRight().getElementTypeParamId() == PARAM_MEASURE_MESSAGES_ON_AIR) {
				
				
					// Parse the xml value
					String pa_message_xml = elementValues.get(0).getRight().getValue();
					
					def pa_message = new XmlSlurper().parseText(pa_message_xml);
	
		
					if(childs)
					{
						for (ElementHierarchy child : childs)
						{
							Element childElement = EntitiesManager.getInstance().getElement(child.getChildElementTypeId(), child.getChildElementId());
							ElementValue zoneId = EntitiesManager.getInstance().getElementValueConfig(childElement, PARAM_CONFIG_ZONE_ID);
							boolean bZoneFound = false;
						
							for (int i = 0; i < pa_message.Data.Pagings.size();i++)
							{
									def groups = [];
									for (int j = 0; j < pa_message.Data.Pagings[i].InfoMessage.size();j++){
										String groupsStr = String.valueOf(pa_message.Data.Pagings[i].InfoMessage[j].@Groups);
										def groupsSplit = groupsStr.split(",");
										for(int k = 0; k < groupsSplit.size();k++){
											groups.add(groupsSplit[k]);
											if(zoneId.getValue() == groupsSplit[k])
											{
												
												List<ElementValue> listElements = new ArrayList();
												listElements.add(getElementValue(PARAM_MEASURE_MESSAGE_ID,PARAM_TYPE_MEASURE,childElement.getId(),childElement.getElementTypeId(),String.valueOf(pa_message.Data.Pagings[i].InfoMessage[j].@Id)));
												
												//listElements.add(getElementValue(PARAM_MEASURE_MESSAGE_TEXT,PARAM_TYPE_MEASURE,childElement.getId(),childElement.getElementTypeId(),String.valueOf(pa_message.Data.Pagings[i].InfoMessage.InfoPrerecorded.@Messages)));
												listElements.add(getElementValue(PARAM_MEASURE_MESSAGE_DESC,PARAM_TYPE_MEASURE,childElement.getId(),childElement.getElementTypeId(),String.valueOf(pa_message.Data.Pagings[i].InfoMessage[j].InfoPrerecorded.@Messages)));
						
													
												listElements.add(getElementValue(PARAM_MEASURE_MESSAGE_TAG,PARAM_TYPE_MEASURE,childElement.getId(),childElement.getElementTypeId(),String.valueOf(pa_message.Data.Pagings[i].InfoMessage[j].@Type)));
												
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
								listElements.add(getElementValue(PARAM_MEASURE_MESSAGE_TAG,PARAM_TYPE_MEASURE,childElement.getId(),childElement.getElementTypeId()," "));
								//listElements.add(getElementValue(PARAM_MEASURE_MESSAGE_TEXT,PARAM_TYPE_MEASURE,childElement.getId(),childElement.getElementTypeId()," "));
								listElements.add(getElementValue(PARAM_MEASURE_MESSAGE_DESC,PARAM_TYPE_MEASURE,childElement.getId(),childElement.getElementTypeId()," "));
								SendMeasures(listElements);
							
							}
							
						}
					}
					
					
				}else if(elementValues.get(0).getRight().getElementTypeParamId() == PARAM_MEASURE_ZONES_LIST) {
					// Parse the xml value
					String zones_list_xml = elementValues.get(0).getRight().getValue();
					
					def zones_list = new XmlSlurper().parseText(zones_list_xml);
					
					SetActivationAlarmsCommand setActivationAlarmsCommandList = new SetActivationAlarmsCommand();
					SetDeactivationAlarmsCommand setDeactivationAlarmsCommandList = new SetDeactivationAlarmsCommand();
					AlarmConfig alarmConfig = new AlarmConfig();
					
					if(childs)
					{
						for (ElementHierarchy child : childs)
						{
							Element childElement = EntitiesManager.getInstance().getElement(child.getChildElementTypeId(), child.getChildElementId());
							ElementValue groups_zones = EntitiesManager.getInstance().getElementValueConfig(childElement, PARAM_CONFIG_GROUPS_ZONES);
							boolean bZoneFound = false;
						
							for (int i = 0; i < zones_list.Data.ZonesList.size();i++)
							{
									
								for (int j = 0; j < zones_list.Data.ZonesList[i].Zone.size();j++)
								{
									bZoneFound = false;
									int Id = Integer.parseInt(String.valueOf(zones_list.Data.ZonesList[i].Zone[j].@Id));
									boolean Alarm =  Boolean.parseBoolean(String.valueOf(zones_list.Data.ZonesList[i].Zone[j].@Alarm));
									String statusStr =  String.valueOf(zones_list.Data.ZonesList[i].Zone[j].@Status);
									boolean statusZone = statusStr.equals(STATUS_OK);
				
									if(!groups_zones.getValue().isEmpty()) {
										def groups_zones_vec = new JsonSlurper().parseText(groups_zones.getValue());
										if (groups_zones_vec instanceof ArrayList) {
										
											for(int k = 0; k < groups_zones_vec.size();k++){
												if(groups_zones_vec.get(k) == Id) {
													bZoneFound = true;
												}
											}
										} else if(groups_zones_vec == Id) {
												bZoneFound = true;
												
											}
									}
									
									
									
									
									if(bZoneFound) {
										
										alarmConfig.setId(ALARM_ZONE_BASE + Id);
										if(Alarm || (!statusZone)) {
											setActivationAlarmsCommandList.add(childElement, alarmConfig);
											
										}else {
											setDeactivationAlarmsCommandList.add(childElement, alarmConfig);
										}
									}
										
										
								}
									
						
									
							}
						}
							
					}
					
					EntitiesManager.getInstance().sendCommand(setActivationAlarmsCommandList);
					EntitiesManager.getInstance().sendCommand(setDeactivationAlarmsCommandList);
					
					
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
	private static String hexToAscii(String hexStr) {
		StringBuilder output = new StringBuilder("");
		
		for (int i = 0; i < hexStr.length(); i += 2) {
			String str = hexStr.substring(i, i + 2);
			output.append((char) Integer.parseInt(str, 16));
		}
		
		return output.toString();
	}
}