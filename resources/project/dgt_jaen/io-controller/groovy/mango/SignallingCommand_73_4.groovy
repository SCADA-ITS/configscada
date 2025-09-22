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
 * SignallingCommand_73_4: ON 0.1% signalling command 
 * 
 */
class SignallingCommand_73_4 {

	GroovyShell shell;
	def signallingCommandUtils;
	
	//XIDs
	static final String ARC_LEVEL = "arc_level";
	static final String APAGADO = "apagado";
	static final String REMOTE = "remote";
	
	//Values
	static final boolean ON = true;
	static final boolean OFF = false;
	static final long SEVEN = 10;
	
	
	org.apache.logging.log4j.Logger log;
	
	SignallingCommand_73_4(org.apache.logging.log4j.Logger log) {
	
		shell = new GroovyShell();
		signallingCommandUtils = shell.parse(new File(ResourcesUtil.getPath("io-controller/groovy/mango/SignallingCommandUtils.groovy")));
		this.log = log;
	}
	
	boolean process(String dataSourceXid, SignallingCommand signallingCommand, MangoDriver driver) {

		try {
			List<XidPointValueTimeModel> xidPointValueTimeModels = new ArrayList<>();
			XidPointValueTimeModel xidPointValueTimeModel;

			xidPointValueTimeModel = signallingCommandUtils.getXidPointValueTimeModel(signallingCommand, 
					dataSourceXid + "_" + APAGADO, OFF);
			xidPointValueTimeModels.add(xidPointValueTimeModel);

			xidPointValueTimeModel = signallingCommandUtils.getXidPointValueTimeModel(signallingCommand, 
					dataSourceXid + "_" + REMOTE, OFF);
			xidPointValueTimeModels.add(xidPointValueTimeModel);

			xidPointValueTimeModel = signallingCommandUtils.getXidPointValueTimeModel(signallingCommand, 
					dataSourceXid + "_" + ARC_LEVEL, SEVEN);
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
