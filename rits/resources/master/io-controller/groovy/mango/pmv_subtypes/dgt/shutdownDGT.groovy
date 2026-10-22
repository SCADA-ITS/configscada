import com.revenga.rits.back.data.core.util.ResourcesUtil;
import com.revenga.rits.back.data.core.model.command.SignallingCommand;
import com.revenga.rits.back.mango.core.model.XidPointValueTimeModel;
    
def XidPointValueTimeModel shutdownDGT(SignallingCommand signallingCommand, String dataSourceXid) {

	final String SIGNALLING = "signalling";
	final String CMD_VALUE_POWER_OFF_DGT = "02201A305C2D03";

	GroovyShell shell = new GroovyShell();
	
	def signallingCommandUtils = shell.parse(new File(ResourcesUtil.getPath("io-controller/groovy/mango/SignallingCommandUtils.groovy")))
					
	XidPointValueTimeModel xidPointValueTimeModel = signallingCommandUtils.getXidPointValueTimeModel(signallingCommand, dataSourceXid + "_" + SIGNALLING, CMD_VALUE_POWER_OFF_DGT);

	return xidPointValueTimeModel;
}
