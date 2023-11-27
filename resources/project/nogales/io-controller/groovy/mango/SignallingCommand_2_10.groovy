import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.exception.ExceptionUtils;

import com.revenga.rits.back.io.controller.driver.mango.MangoDriver;
import com.revenga.rits.back.data.core.model.command.SignallingCommand;
import com.revenga.rits.back.mango.core.model.XidPointValueTimeModel;
import com.revenga.rits.back.data.core.util.ResourcesUtil;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


/**
 * 
 * SignallingCommand_2_10: Send DAI alarm Camera  
 * 
 */
class SignallingCommand_2_10 {
	static final String XID = "_alarmDAI_CT0";


	GroovyShell shell;
	def signallingCommandUtils;

	org.apache.logging.log4j.Logger log;

	SignallingCommand_2_10(org.apache.logging.log4j.Logger log) {

		shell = new GroovyShell();
		signallingCommandUtils = shell.parse(new File(ResourcesUtil.getPath("io-controller/groovy/mango/SignallingCommandUtils.groovy")));
		this.log = log;
	}

	boolean process(String dataSourceXid, SignallingCommand signallingCommand, MangoDriver driver) {
		boolean value_alarm = false;

		if( signallingCommand != null && signallingCommand.getElementId() != null &&
				signallingCommand.getSignallingParams() != null && !signallingCommand.getSignallingParams().isEmpty() &&
				signallingCommand.getSignallingParams().get(0) != null && signallingCommand.getSignallingParams().get(0).getValue() != null) {

			String value = signallingCommand.getSignallingParams().get(0).getValue();
			
			if(value.equals("1")) value_alarm = true;

			List<XidPointValueTimeModel> xidPointValueTimeModels = new ArrayList<>();
			XidPointValueTimeModel xidPointValueTimeModel;
			
			for(int i = 1; i <= 8; i++){
				xidPointValueTimeModel = signallingCommandUtils.getXidPointValueTimeModel(signallingCommand, 
						dataSourceXid + XID + String.valueOf(i), value_alarm);
							
				xidPointValueTimeModels.add(xidPointValueTimeModel);
	
				ObjectMapper objectMapper = new ObjectMapper();
				objectMapper.setSerializationInclusion(Include.NON_NULL);
				String message = objectMapper.writeValueAsString(xidPointValueTimeModels);
				
				if (driver != null) {
				
					driver.send(message);
				}
				
				xidPointValueTimeModels.clear();
			}
		}

		return true;
	}
}
