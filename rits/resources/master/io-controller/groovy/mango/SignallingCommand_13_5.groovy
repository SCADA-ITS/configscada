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
 * SignallingCommand_13_5: FLASHING AMBER signalling command 
 * 
 */
class SignallingCommand_13_4 {

	GroovyShell shell;
	def signallingCommandUtils;

	static final String LIGHT_CMD = "light";
	static final Long SEM_CMD_FLASHING_LIGHT = 0L;
	
	org.apache.logging.log4j.Logger log;
	
	SignallingCommand_13_4(org.apache.logging.log4j.Logger log) {
	
		shell = new GroovyShell();
		signallingCommandUtils = shell.parse(new File(ResourcesUtil.getPath("io-controller/groovy/mango/SignallingCommandUtils.groovy")));
		this.log = log;
	}
	
	boolean process(String dataSourceXid, SignallingCommand signallingCommand, MangoDriver driver) {

		try {
			List<XidPointValueTimeModel> xidPointValueTimeModels = new ArrayList<>();

			XidPointValueTimeModel xidPointValueTimeModel = signallingCommandUtils.getXidPointValueTimeModel(signallingCommand, 
					dataSourceXid + "_" + LIGHT_CMD, SEM_CMD_FLASHING_LIGHT);
						
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
