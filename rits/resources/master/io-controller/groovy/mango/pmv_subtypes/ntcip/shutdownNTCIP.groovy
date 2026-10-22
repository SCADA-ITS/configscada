import com.revenga.rits.back.data.core.util.ResourcesUtil;
import com.revenga.rits.back.data.core.model.command.SignallingCommand;
import com.revenga.rits.back.mango.core.model.XidPointValueTimeModel;
    
def XidPointValueTimeModel shutdownNTCIP(SignallingCommand signallingCommand, String dataSourceXid) {

	final String MESSAGE_HEX = "message_hex";
	final String CMD_VALUE_POWER_OFF_NTCIP = "FFFFFF07000100007F000001";

	GroovyShell shell = new GroovyShell();
	
	def signallingCommandUtils = shell.parse(new File(ResourcesUtil.getPath("io-controller/groovy/mango/SignallingCommandUtils.groovy")))
					
	XidPointValueTimeModel xidPointValueTimeModel = signallingCommandUtils.getXidPointValueTimeModel(signallingCommand, dataSourceXid + "_" + MESSAGE_HEX, CMD_VALUE_POWER_OFF_NTCIP);

	return xidPointValueTimeModel;
}
