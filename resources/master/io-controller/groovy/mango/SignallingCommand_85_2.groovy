import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.exception.ExceptionUtils;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.revenga.rits.back.data.core.model.command.SignallingCommand;

import com.revenga.rits.back.io.controller.driver.mango.MangoDriver;
import com.revenga.rits.back.io.controller.service.EntitiesManager;

import com.revenga.rits.back.mango.core.model.XidPointValueTimeModel;
import com.revenga.rits.back.mango.core.model.XidPointValueTimeModel.DataTypeEnum

import com.revenga.rits.back.data.core.util.ResourcesUtil;

import groovy.json.JsonSlurper;
import com.revenga.rits.back.io.controller.service.EntitiesManager;
import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.ElementValue;

/**
 * 
 * SignallingCommand_85_2: Send Groups Values
 * 
 */
class SignallingCommand_85_2 {

	GroovyShell shell;
	def signallingCommandUtils;
	
	static final String DALI_CMD = "dali_cmd";
	static final String DATA_JSON_GROUP = "data_group";
	
	static final Long PARAM_TYPE_MEASURE = 2L;
	static final int COMMAND_SEND_GROUP_VALUES_TYPE_PARAM_JSON_VALUE = 1;
	
	org.apache.logging.log4j.Logger log;
	
	SignallingCommand_85_2(org.apache.logging.log4j.Logger log) {
	
		shell = new GroovyShell();
		signallingCommandUtils = shell.parse(new File(ResourcesUtil.getPath("io-controller/groovy/mango/SignallingCommandUtils.groovy")));
		this.log = log;
	}
	
	boolean process(String dataSourceXid, SignallingCommand signallingCommand, MangoDriver driver) {

		try {			
			List<XidPointValueTimeModel> xidPointValueTimeModels = new ArrayList<>();
			XidPointValueTimeModel xidPointValueTimeModel;

			
			
			
			def groupsJson = new JsonSlurper().parseText(signallingCommand.getSignallingParams().get(0).getValue());
			
			for (int i = 0; i < groupsJson.groups.size();i++) {
				Long value = groupsJson.groups.get(i).value;
				xidPointValueTimeModel = signallingCommandUtils.getXidPointValueTimeModel(signallingCommand, dataSourceXid + "-G" + groupsJson.groups.get(i).id.toString() + "_" + DALI_CMD, value);
				xidPointValueTimeModels.add(xidPointValueTimeModel);
				
			}
			
		
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.setSerializationInclusion(Include.NON_NULL);
			String message = objectMapper.writeValueAsString(xidPointValueTimeModels);

			if (driver != null) {

				driver.send(message);
			}

		} catch (NumberFormatException | JsonProcessingException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}

		return true;
	}
}
