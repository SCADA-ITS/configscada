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
 * SignallingCommand_5_3: PA system stop audio message command by group
 *
 */
class SignallingCommand_5_3 {
	
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
	
	SignallingCommand_5_3(org.apache.logging.log4j.Logger log) {
	
		shell = new GroovyShell();
		signallingCommandUtils = shell.parse(new File(ResourcesUtil.getPath("io-controller/groovy/mango/SignallingCommandUtils.groovy")));
		this.log = log;
	}
	
	boolean process(String dataSourceXid, SignallingCommand signallingCommand, MangoDriver driver) {

		try {
			List<XidPointValueTimeModel> xidPointValueTimeModels = new ArrayList<>();
			XidPointValueTimeModel xidPointValueTimeModel;
			String groups;
			
			xidPointValueTimeModel = signallingCommandUtils.getXidPointValueTimeModel(signallingCommand, PA_CMD_VALUE_ZONES,
				dataSourceXid + "_" + PA_SYSTEM_CMD);
			
			groups = xidPointValueTimeModel.getValue();
		
			Element element = EntitiesManager.getInstance().getElement(signallingCommand.getElementTypeId(), signallingCommand.getElementId());
			if(element == null){
				log.debug("No se encuentra elemento del tipo " + signallingCommand.getElementTypeId() + " con Id = " + signallingCommand.getElementId());
				return false;
			}
			
			ElementValue messages_on_air = EntitiesManager.getInstance().getElementValue(element, PARAM_MEASURE_MESSAGES_ON_AIR, TYPE_PARAM_MEASURE);
			ElementValue sender_ip = EntitiesManager.getInstance().getElementValue(element, PARAM_CONFIG_SENDER_IP, TYPE_PARAM_CONFIG);
			
			
			
			// Parse the response
			 def message_list = new JsonSlurper().parseText( messages_on_air.getValue() )
			 def groups_list = new JsonSlurper().parseText( groups )
		
			 if(message_list)
			 {
				 def messages_ids = [];
				 
				 for (int i = 0; i < message_list.messages.size();i++)
				 {
					 boolean bFind= false;
					 if(message_list.messages.get(i).sender_ip ==  sender_ip.getValue() && message_list.messages.get(i).status ==  PA_SYSTEM_STATUS_PLAYING)
					 {
						
						 for (int j = 0; (j < groups_list.size() && !bFind );j++)
						 {
							 for (int k = 0; (k < message_list.messages.get(i).groups.size() && !bFind);k++)
							 {
								
								 int group = Integer.parseInt(groups_list.get(j));							
								 if(message_list.messages.get(i).groups.get(k) == group)
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
		
		
			
			
			
			
	

		} catch (NumberFormatException | JsonProcessingException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}

		return true;
	}
}
