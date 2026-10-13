import com.revenga.rits.back.data.core.util.ResourcesUtil;
import com.revenga.rits.back.data.core.model.command.SignallingCommand;
import com.revenga.rits.back.mango.core.model.XidPointValueTimeModel;
    
def XidPointValueTimeModel shutdownContact(SignallingCommand signallingCommand, String dataSourceXid) {

	final String DO_SWITCH_ON = "do_switch_on"
	final boolean SWICHT_OFF = false;

	GroovyShell shell = new GroovyShell();
	
	def signallingCommandUtils = shell.parse(new File(ResourcesUtil.getPath("io-controller/groovy/mango/SignallingCommandUtils.groovy")))
					
	XidPointValueTimeModel xidPointValueTimeModel = signallingCommandUtils.getXidPointValueTimeModel(signallingCommand, dataSourceXid + "_" + DO_SWITCH_ON, SWICHT_OFF)

	return xidPointValueTimeModel;
}
