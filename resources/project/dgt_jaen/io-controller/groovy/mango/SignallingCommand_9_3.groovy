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

import com.revenga.rits.back.data.core.util.ResourcesUtil;

/**
 * 
 * SignallingCommand_9_3: Fan stop signalling command 
 * 
 */
class SignallingCommand_9_3 {

	GroovyShell shell;
	def signallingCommandUtils;
	
	static final String FAN_DIRECT_ORDER = "fan_direct_order";
	static final String FAN_REVERSE_ORDER = "fan_reverse_order";
	static final String FAN_STOP_ORDER = "fan_stop_order";
	static final boolean ON = true;	
	static final boolean OFF = false;	
	
	org.apache.logging.log4j.Logger log;
	
	SignallingCommand_9_3(org.apache.logging.log4j.Logger log) {
	
		shell = new GroovyShell();
		signallingCommandUtils = shell.parse(new File(ResourcesUtil.getPath("io-controller/groovy/mango/SignallingCommandUtils.groovy")));
		this.log = log;
	}
	
	boolean process(String dataSourceXid, SignallingCommand signallingCommand, MangoDriver driver) {

		try {
			List<XidPointValueTimeModel> xidPointValueTimeModels = new ArrayList<>();
			XidPointValueTimeModel xidPointValueTimeModel = null;
			
			if (dataSourceXid.contains("CZ")) {
				xidPointValueTimeModel = signallingCommandUtils.getXidPointValueTimeModel(signallingCommand, 
					dataSourceXid + "_" + FAN_REVERSE_ORDER, ON);

				xidPointValueTimeModels.add(xidPointValueTimeModel);

				ObjectMapper objectMapper = new ObjectMapper();
				objectMapper.setSerializationInclusion(Include.NON_NULL);
				String onMessage = objectMapper.writeValueAsString(xidPointValueTimeModels);
				log.debug("ENVIO EL ON-----> " + onMessage)
				if (driver != null) {
					driver.send(onMessage);
				}

				Thread.sleep(1000);

				xidPointValueTimeModels.clear(); // Borra todos los elementos previos de la lista

				xidPointValueTimeModel = signallingCommandUtils.getXidPointValueTimeModel(signallingCommand, 
					dataSourceXid + "_" + FAN_REVERSE_ORDER, OFF);
				
				xidPointValueTimeModels.add(xidPointValueTimeModel);

				objectMapper = new ObjectMapper();
				objectMapper.setSerializationInclusion(Include.NON_NULL);

				String offMessage = objectMapper.writeValueAsString(xidPointValueTimeModels);
				log.debug("ENVIO EL OFF-----> " + offMessage)
				if (driver != null) {
					driver.send(offMessage);
				}
			}else{
				xidPointValueTimeModel = signallingCommandUtils.getXidPointValueTimeModel(signallingCommand, 
						dataSourceXid + "_" + FAN_REVERSE_ORDER, ON);
							
				xidPointValueTimeModels.add(xidPointValueTimeModel);

				xidPointValueTimeModel = signallingCommandUtils.getXidPointValueTimeModel(signallingCommand, 
						dataSourceXid + "_" + FAN_STOP_ORDER, ON);
							
				xidPointValueTimeModels.add(xidPointValueTimeModel);

				xidPointValueTimeModel = signallingCommandUtils.getXidPointValueTimeModel(signallingCommand, 
						dataSourceXid + "_" + FAN_DIRECT_ORDER, OFF);
							
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

