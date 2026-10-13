import com.revenga.rits.back.data.core.util.ResourcesUtil;
import com.revenga.rits.back.data.core.model.command.SignallingCommand;
import com.revenga.rits.back.mango.core.model.XidPointValueTimeModel;
    
def XidPointValueTimeModel shutdownRaspberry(SignallingCommand signallingCommand, String dataSourceXid) {

	final String XID_SHUTDOWN = "shutdown";
	final String SHUTDOWN = "1";

	GroovyShell shell = new GroovyShell();
	
	def signallingCommandUtils = shell.parse(new File(ResourcesUtil.getPath("io-controller/groovy/mango/SignallingCommandUtils.groovy")))
					
	XidPointValueTimeModel xidPointValueTimeModel = signallingCommandUtils.getXidPointValueTimeModel(signallingCommand, dataSourceXid + "_" + XID_SHUTDOWN, SHUTDOWN);

	return xidPointValueTimeModel;
}
