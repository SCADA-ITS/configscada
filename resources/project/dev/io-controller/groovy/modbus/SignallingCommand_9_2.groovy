import org.apache.commons.lang3.exception.ExceptionUtils;

import com.revenga.rits.back.data.core.model.command.SignallingCommand;
import com.revenga.rits.back.io.controller.driver.modbustcp.ModbusTcpDriver;
import com.revenga.rits.back.io.controller.driver.modbustcp.ModbusTcpDriver.ModbusResult;
import com.revenga.rits.back.io.controller.driver.modbustcp.ModbusTcpDriver.ModbusType;
import com.revenga.rits.back.io.controller.driver.modbustcp.ModbusTcpDriver.ModbusWriteRequest;

/**
 *  
 * SignallingCommand_9_2: Fan start signalling command 
 * 
 */
class SignallingCommand_9_2 {

	static final Integer FAN_CMD_VALUE_START = 1;	
	
	org.apache.logging.log4j.Logger log;
	
	SignallingCommand_9_2(org.apache.logging.log4j.Logger log) {
	
		this.log = log;
	}
	
	boolean process(final Integer xAddress, final SignallingCommand signallingCommand, final ModbusTcpDriver driver) {

		boolean res = false;
		
		try {
			
			int[] values = new int[1];
			values[0] = FAN_CMD_VALUE_START;
			
			ModbusWriteRequest request = new ModbusWriteRequest(ModbusType.HOLDING_REGISTER, xAddress, values);

			ModbusResult result = driver.write(request);
			
			log.debug("SignallingCommand_9_2: Fan start signalling command result: " + result.toString());
			
			res = true;
			
		} catch (Exception e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}

		return res;
	}
}
