import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.exception.ExceptionUtils;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revenga.rits.back.data.core.model.command.SignallingCommand;
import com.revenga.rits.back.io.controller.driver.mango.MangoDriver;
import com.revenga.rits.back.mango.core.model.XidPointValueTimeModel;
import com.revenga.rits.back.mango.core.model.XidPointValueTimeModel.DataTypeEnum
import com.revenga.rits.back.io.controller.service.EntitiesManager;

import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.ElementValue;
import com.revenga.rits.back.data.core.util.ResourcesUtil;

import groovy.json.*;
import groovy.util.*;
import groovy.xml.*;

/**
 *
 * SignallingCommand_5_1: PA system stop audio message command
 *
 */
class SignallingCommand_5_1 {
	
	//Commands params
	static final Integer PA_CMD_VALUE_ZONES = 1;
	static final Integer PA_CMD_VALUE_MESSAGE = 2;
	
	static final String PA_SYSTEM_CMD = "stop_paging_command";
	
	// BBDD params
	static final Long TYPE_PARAM_CONFIG = 1L;
	static final Long TYPE_PARAM_MEASURE = 2L;
	static final Long GRAPHIC_TYPE_AUDIO = 3L;
	static final Long GRAPHIC_PROTOCOL_TYPE_PARAM_ID_AUDIO = 2L;
	
	static final Long PARAM_CONFIG_SENDER_IP= 5L;
	static final Long PARAM_MEASURE_MESSAGES_ON_AIR= 1L;
	
	static final Long PA_SYSTEM_STATUS_PLAYING= 8L;
	
	static final Long ELEMENT_SUBTYPE_PASYSTEM_ZONES_JSON = 1;
	static final Long ELEMENT_SUBTYPE_PASYSTEM_GROUPS_XML = 2;
	
	GroovyShell shell;
	def signallingCommandUtils;
	

	org.apache.logging.log4j.Logger log;
	
	SignallingCommand_5_1(org.apache.logging.log4j.Logger log) {
	
		shell = new GroovyShell();
		signallingCommandUtils = shell.parse(new File(ResourcesUtil.getPath("io-controller/groovy/mango/SignallingCommandUtils.groovy")));
		this.log = log;
	}
	
	boolean process(String dataSourceXid, SignallingCommand signallingCommand, MangoDriver driver) {

		try {
			List<XidPointValueTimeModel> xidPointValueTimeModels = new ArrayList<>();
			XidPointValueTimeModel xidPointValueTimeModel;
			String zones;
			
			xidPointValueTimeModel = signallingCommandUtils.getXidPointValueTimeModel(signallingCommand, PA_CMD_VALUE_ZONES,
				dataSourceXid + "_" + PA_SYSTEM_CMD);
			
			zones = xidPointValueTimeModel.getValue();
		
			Element element = EntitiesManager.getInstance().getElement(signallingCommand.getElementTypeId(), signallingCommand.getElementId());
			if(element == null){
				log.debug("No se encuentra elemento del tipo " + signallingCommand.getElementTypeId() + " con Id = " + signallingCommand.getElementId());
				return false;
			}
			
			ElementValue messages_on_air = EntitiesManager.getInstance().getElementValue(element, PARAM_MEASURE_MESSAGES_ON_AIR, TYPE_PARAM_MEASURE);
			ElementValue sender_ip = EntitiesManager.getInstance().getElementValue(element, PARAM_CONFIG_SENDER_IP, TYPE_PARAM_CONFIG);
			
			
			
			// Parse the response
			if(element.elementSubtypeId == ELEMENT_SUBTYPE_PASYSTEM_GROUPS_XML) {
				def message_list = new XmlSlurper().parseText(messages_on_air.getValue());
				def groups_list = new JsonSlurper().parseText( zones );
				
			
				
				if(message_list)
				{
					def messages_ids = [];
					
					for (int i = 0; i < message_list.Data.Pagings.size();i++)
					{
						boolean bFind= false;

						for (int k = 0; k < message_list.Data.Pagings[i].InfoMessage.size();k++){
							if(message_list.Data.Pagings[i].InfoMessage[k].@SenderIP ==  sender_ip.getValue())
							{
								for (int j = 0; (j < groups_list.size() && !bFind );j++)
								{
									String groupsStr = String.valueOf(message_list.Data.Pagings[i].InfoMessage[k].@Groups);
									
									def groups = groupsStr.split(",");
									
									for (int l = 0; (l < groups.size() && !bFind);l++)
									{
										if(groups[l] == groups_list.get(j))
										{
											messages_ids.add(String.valueOf(message_list.Data.Pagings[i].InfoMessage[k].@Id));
										
											bFind = true;
										}
									}
									
								}
								
						
							}
							else
							{
								messages_ids.add(String.valueOf(message_list.Data.Pagings[i].InfoMessage.@Id));
							}

						}
						
					
					}
					if(messages_ids.size()) {
						
						for (int i = 0; i < messages_ids.size();i++) {
							String xmlDoc = '<?xml version="1.0" encoding="utf-8"?><XMLRequest xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema"><Version>1</Version><Command>stoppaging</Command><Type>set</Type><Data xsi:type="XMLParams"><Parameters><Param Name="Id" ';
							xmlDoc+= 'Value=' + "\"" + String.valueOf(messages_ids[i]) + "\"" + ' /></Parameters></Data></XMLRequest>';
							
							
							xidPointValueTimeModel = signallingCommandUtils.getXidPointValueTimeModel(signallingCommand,
								dataSourceXid + "_" + PA_SYSTEM_CMD, xmlDoc);
							xidPointValueTimeModels.clear();
							xidPointValueTimeModels.add(xidPointValueTimeModel);
			
							ObjectMapper objectMapper = new ObjectMapper();
							objectMapper.setSerializationInclusion(Include.NON_NULL);
							String message = objectMapper.writeValueAsString(xidPointValueTimeModels);
		
							if (driver != null) {
							
								driver.send(message);
							}
						}
						
					}
						
				}
			} else {
				 def message_list = new JsonSlurper().parseText( messages_on_air.getValue() )
				 def zones_list = new JsonSlurper().parseText( zones )
			
				 if(message_list)
				 {
					 def messages_ids = [];
					 
					 for (int i = 0; i < message_list.messages.size();i++)
					 {
						 boolean bFind= false;
						 if(message_list.messages.get(i).sender_ip ==  sender_ip.getValue() && message_list.messages.get(i).status ==  PA_SYSTEM_STATUS_PLAYING)
						 {
							
							 for (int j = 0; (j < zones_list.size() && !bFind );j++)
							 {
								 for (int k = 0; (k < message_list.messages.get(i).zones.size() && !bFind);k++)
								 {
									
									 int zone = Integer.parseInt(zones_list.get(j));							
									 if(message_list.messages.get(i).zones.get(k) == zone)
									 {
										 messages_ids.add(message_list.messages.get(i).system_id.toString());
										 bFind = true;
									 }
								 }
								 
							 }
							 
					 
						 }
						 else
						 {
							 messages_ids.add(message_list.messages.get(i).system_id.toString());
						 }
					 }
				
					 if(messages_ids.size()) {
						 def builder = new groovy.json.JsonBuilder();
						 
						 builder id: messages_ids;
						 
						 xidPointValueTimeModel = signallingCommandUtils.getXidPointValueTimeModel(signallingCommand,
							 dataSourceXid + "_" + PA_SYSTEM_CMD, builder.toString());
						 xidPointValueTimeModels.clear();
						 xidPointValueTimeModels.add(xidPointValueTimeModel);
		 
						 ObjectMapper objectMapper = new ObjectMapper();
						 objectMapper.setSerializationInclusion(Include.NON_NULL);
						 String message = objectMapper.writeValueAsString(xidPointValueTimeModels);
	 
						 if (driver != null) {
						 
							 driver.send(message);
						 }
					 }
					 
				 }
			}
			
			
			
			
			
	

		} catch (NumberFormatException | JsonProcessingException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}

		return true;
	}
}
