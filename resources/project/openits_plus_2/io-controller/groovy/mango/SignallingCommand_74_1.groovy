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
 * SignallingCommand_74_1: ERU get peripheral ctrl
 * 
 */
class SignallingCommand_74_1 {

	GroovyShell shell;
	def signallingCommandUtils;
	
	static final String CTRL_CMD = "peripheral_ctrl";
	static final Boolean GET_CRL = true;
	static final Boolean LET_CRL = false;

	static final String MASTER_XID = "DP_TUN-ERU-1-MASTER";
	static final String SLAVE_XID = "DP_TUN-ERU-1-SLAVE";
	
	org.apache.logging.log4j.Logger log;
	
	SignallingCommand_74_1(org.apache.logging.log4j.Logger log) {
	
		shell = new GroovyShell();
		signallingCommandUtils = shell.parse(new File(ResourcesUtil.getPath("io-controller/groovy/mango/SignallingCommandUtils.groovy")));
		this.log = log;
	}
	
	boolean process(String dataSourceXid, SignallingCommand signallingCommand, MangoDriver driver) {

		try {

			List<XidPointValueTimeModel> xidPointValueTimeModels = new ArrayList<>();
			XidPointValueTimeModel xidPointValueTimeModel = null;
			if (dataSourceXid == MASTER_XID){

				xidPointValueTimeModel = signallingCommandUtils.getXidPointValueTimeModel(signallingCommand, 
						MASTER_XID + "_" + CTRL_CMD, GET_CRL);
							
				xidPointValueTimeModels.add(xidPointValueTimeModel);

				xidPointValueTimeModel = signallingCommandUtils.getXidPointValueTimeModel(signallingCommand, 
						SLAVE_XID + "_" + CTRL_CMD, LET_CRL);
							
				xidPointValueTimeModels.add(xidPointValueTimeModel);
			}else if (dataSourceXid == SLAVE_XID){
				xidPointValueTimeModel = signallingCommandUtils.getXidPointValueTimeModel(signallingCommand, 
						SLAVE_XID + "_" + CTRL_CMD, GET_CRL);
							
				xidPointValueTimeModels.add(xidPointValueTimeModel);

				xidPointValueTimeModel = signallingCommandUtils.getXidPointValueTimeModel(signallingCommand, 
						MASTER_XID + "_" + CTRL_CMD, LET_CRL);
							
				xidPointValueTimeModels.add(xidPointValueTimeModel);
			}

			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.setSerializationInclusion(Include.NON_NULL);
			String message = objectMapper.writeValueAsString(xidPointValueTimeModels);

			if (driver != null) {
				log.debug(message);
				driver.send(message);
			}

		} catch (NumberFormatException | JsonProcessingException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}

		return true;
	}
}
