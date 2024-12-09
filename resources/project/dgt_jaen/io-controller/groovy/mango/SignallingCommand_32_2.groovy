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

/**
 * 
 * SignallingCommand_32_1: Fire pressurization ON
 * 
 */
class SignallingCommand_32_1 {

	GroovyShell shell;
	def signallingCommandUtils;
	
	static final String FIRE_PRESSURIZATION_CMD = "fan_activation_order";
	static final String FIRE_VENTILATION_GATE_CMD = "ventilation_gate_closure_order";
	static final String FIRE_GRID_GATE_CMD = "grid_gate_closure_order";
	
	static final Boolean FIRE_PRESSURIZATION_CMD_VALUE_OFF = 0;
	static final Boolean FIRE_VENTILATION_GATE_CMD_VALUE_OFF = 1;
	static final Boolean FIRE_GRID_GATE_CMD_VALUE_OFF = 1;
	
	org.apache.logging.log4j.Logger log;
	
	SignallingCommand_32_1(org.apache.logging.log4j.Logger log) {
	
		shell = new GroovyShell();
		signallingCommandUtils = shell.parse(new File(ResourcesUtil.getPath("io-controller/groovy/mango/SignallingCommandUtils.groovy")));
		this.log = log;
	}
	
	boolean process(String dataSourceXid, SignallingCommand signallingCommand, MangoDriver driver) {
		
		try {
			String dataSourceXid_aux;

			if (dataSourceXid[-1] == 'S'){
				dataSourceXid_aux = dataSourceXid[0..-2] + 'N'
			} else if (dataSourceXid[-1] == 'N') {
				dataSourceXid_aux = dataSourceXid[0..-2] + 'S'
			}
	
			List<XidPointValueTimeModel> xidPointValueTimeModels = new ArrayList<>();

			XidPointValueTimeModel xidPointValueTimeModel = signallingCommandUtils.getXidPointValueTimeModel(signallingCommand, 
					dataSourceXid_aux + "_" + FIRE_PRESSURIZATION_CMD, FIRE_PRESSURIZATION_CMD_VALUE_OFF);
						
			xidPointValueTimeModels.add(xidPointValueTimeModel);

			xidPointValueTimeModel = signallingCommandUtils.getXidPointValueTimeModel(signallingCommand, 
					dataSourceXid + "_" + FIRE_VENTILATION_GATE_CMD, FIRE_VENTILATION_GATE_CMD_VALUE_OFF);

			xidPointValueTimeModels.add(xidPointValueTimeModel);

			xidPointValueTimeModel = signallingCommandUtils.getXidPointValueTimeModel(signallingCommand, 
					dataSourceXid + "_" + FIRE_GRID_GATE_CMD, FIRE_GRID_GATE_CMD_VALUE_OFF);

			xidPointValueTimeModels.add(xidPointValueTimeModel);

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
