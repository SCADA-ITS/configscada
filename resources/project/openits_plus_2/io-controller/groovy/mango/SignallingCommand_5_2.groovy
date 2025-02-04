import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.exception.ExceptionUtils;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revenga.rits.back.data.core.model.CommandElement;
import com.revenga.rits.back.data.core.model.CommandElementValue;
import com.revenga.rits.back.data.core.model.CommandElementTypeParam;
import com.revenga.rits.back.data.core.model.command.SignallingCommand;
import com.revenga.rits.back.data.core.model.command.SignallingCommand.SignallingParam;
import com.revenga.rits.back.data.core.model.command.SignallingCommand;
import com.revenga.rits.back.io.controller.driver.mango.MangoDriver;
import com.revenga.rits.back.mango.core.model.XidPointValueTimeModel;
import com.revenga.rits.back.mango.core.model.XidPointValueTimeModel.DataTypeEnum
import com.revenga.rits.back.data.core.model.DataType;
import com.revenga.rits.back.io.controller.service.EntitiesManager;
import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.ElementValue;

import com.revenga.rits.back.data.core.util.ResourcesUtil;

import groovy.json.*;
import groovy.util.*;
/**
 *
 * SignallingCommand_5_2: PA system play audio message command
 *
 */
class SignallingCommand_5_2 {
	
	//Commands params
	static final Integer PA_CMD_VALUE_ZONES = 1;
	static final Integer PA_CMD_VALUE_MESSAGE = 2;
	
	static final String PA_SYSTEM_CMD = "start_paging_command";
	
	// BBDD params
	static final Long TYPE_PARAM_CONFIG = 1L;
	static final Long TYPE_PARAM_MEASURE = 2L;
	static final Long GRAPHIC_TYPE_AUDIO = 3L;
	static final Long GRAPHIC_PROTOCOL_TYPE_PARAM_ID_AUDIO = 2L;
	
	static final Long PARAM_MEASURE_AUDIO = 1001L;
	
	static final Long PARAM_COMMAND_STOP = 1L;
	static final Long PARAM_NUM_ZONES = 1L;
	static final Long ELEMENT_TYPE_PA_SYSTEM = 5L;
	
	static final Long ELEMENT_SUBTYPE_PASYSTEM_ZONES_JSON = 1;
	static final Long ELEMENT_SUBTYPE_PASYSTEM_GROUPS_XML = 2;
	
	static final Long PARAM_CONFIG_SENDER_IP= 5L;
	
	
	GroovyShell shell;
	def signallingCommandUtils;
	

	org.apache.logging.log4j.Logger log;
	
	SignallingCommand_5_2(org.apache.logging.log4j.Logger log) {
	
		shell = new GroovyShell();
		signallingCommandUtils = shell.parse(new File(ResourcesUtil.getPath("io-controller/groovy/mango/SignallingCommandUtils.groovy")));
		this.log = log;
	}
	
	boolean process(String dataSourceXid, SignallingCommand signallingCommand, MangoDriver driver) {

		try {
			
			List<XidPointValueTimeModel> xidPointValueTimeModels = new ArrayList<>();
			XidPointValueTimeModel xidPointValueTimeModel;
			String message_id,zones;
			
			xidPointValueTimeModel = signallingCommandUtils.getXidPointValueTimeModel(signallingCommand, PA_CMD_VALUE_MESSAGE,
				dataSourceXid + "_" + PA_SYSTEM_CMD);
			
			message_id = xidPointValueTimeModel.getValue();
		
			xidPointValueTimeModel = signallingCommandUtils.getXidPointValueTimeModel(signallingCommand, PA_CMD_VALUE_ZONES,
				dataSourceXid + "_" + PA_SYSTEM_CMD);
			
			zones = xidPointValueTimeModel.getValue();
			
				
			// Parse the response
			def zones_list = new JsonSlurper().parseText( zones )
			def message_list = new JsonSlurper().parseText( message_id )
			log.debug(" zones_list " + zones_list)
			log.debug(" message_list  " + message_list)
			if(zones_list)
			{
				
				
				Element element = EntitiesManager.getInstance().getElement(signallingCommand.elementTypeId, signallingCommand.elementId);
				
				// First stop command
				/*
				CommandElement commandElement = new CommandElement();
				commandElement.setCommandElementTypeId(PARAM_COMMAND_STOP);
				commandElement.setElementTypeId(signallingCommand.getElementTypeId());
				commandElement.setElementId(signallingCommand.getElementId());
				
				
				SignallingCommand signallingCommandStop = new SignallingCommand();
				
				signallingCommandStop.setSignallingCommandId(commandElement.getCommandElementTypeId());
				signallingCommandStop.setElementTypeId(commandElement.getElementTypeId());
				signallingCommandStop.setElementId(commandElement.getElementId());
				
				
				CommandElementTypeParam commandElementTypeParam = new CommandElementTypeParam();
				
				commandElementTypeParam.setCommandElementTypeId(PARAM_COMMAND_STOP);
				commandElementTypeParam.setElementTypeId(signallingCommand.getElementTypeId());
				commandElementTypeParam.setDataTypeId(DataType.DATA_TYPE_ALPHANUMERIC);
				
				CommandElementValue commandElementValue = new CommandElementValue();
				commandElementValue.setCommandElementTypeId(PARAM_COMMAND_STOP);
				commandElementValue.setElementTypeId(signallingCommand.getElementTypeId());
				commandElementValue.setCommandElementTypeParamId(PARAM_NUM_ZONES);
				commandElementValue.setCommandElementId(commandElement.getElementId());
				commandElementValue.setValue(zones);
				commandElementValue.setCommandElementTypeParam(commandElementTypeParam);
				
				List<SignallingParam> signallingParams = new ArrayList<>();
				SignallingParam signallingParam = new SignallingParam();
				signallingParam.setId(commandElementValue.getCommandElementTypeParamId().intValue());
				signallingParam.setType(commandElementValue.getCommandElementTypeParam().getDataTypeId().intValue());
				signallingParam.setValue(commandElementValue.getValue());
				signallingParams.add(signallingParam);
				
				signallingCommandStop.setSignallingParams(signallingParams);
				EntitiesManager.getInstance().sendCommand(signallingCommandStop);
				*/
				
				if(element.elementSubtypeId == ELEMENT_SUBTYPE_PASYSTEM_GROUPS_XML) {
					String zonesListStr = String.join(",", zones_list);
					String messageListStr = String.join(",", message_list);
					ElementValue sender_ip = EntitiesManager.getInstance().getElementValue(element, PARAM_CONFIG_SENDER_IP, TYPE_PARAM_CONFIG);

					
					String xmlDoc = '<?xml version="1.0" encoding="utf-8"?><XMLRequest xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema">'
					xmlDoc+='<Version>1</Version><Command>startpaging</Command><Type>set</Type>'
					xmlDoc+='<Data xsi:type="InfoMessage" Id="-1" InputId="-1" Priority="2" '
					xmlDoc+= 'SenderIP=' + "\""+ sender_ip.getValue().toString() + "\"";
					xmlDoc+=' Status="MSG_UNKNOWN" Type="PRIORITY" Chime="OFF" Zones="" ';
					xmlDoc+='Groups=' + "\"" + zonesListStr +  "\""; 
					xmlDoc+= '><InfoPrerecorded Repetitions="0" Interval="0" Period="0" ';
					xmlDoc+= 'Messages=' + "\"" + messageListStr + "\"" + ' /></Data>'
					xmlDoc+= '</XMLRequest>'; 
				
			
					xidPointValueTimeModel = signallingCommandUtils.getXidPointValueTimeModel(signallingCommand,
						dataSourceXid + "_" + PA_SYSTEM_CMD, xmlDoc);
					
				}else {
					//Send the command by individual zones
					def builder = new groovy.json.JsonBuilder();
					builder zones: zones_list, repetitions: 0, messages: message_list;
								
					xidPointValueTimeModel = signallingCommandUtils.getXidPointValueTimeModel(signallingCommand,
						dataSourceXid + "_" + PA_SYSTEM_CMD, builder.toString());
					
				}
				
				xidPointValueTimeModels.clear();
				xidPointValueTimeModels.add(xidPointValueTimeModel);

				ObjectMapper objectMapper = new ObjectMapper();
				objectMapper.setSerializationInclusion(Include.NON_NULL);
				String message = objectMapper.writeValueAsString(xidPointValueTimeModels);

				if (driver != null) {
				
					driver.send(message);
				}
				
			}
			
	

		} catch (NumberFormatException | JsonProcessingException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}

		return true;
	}
}
