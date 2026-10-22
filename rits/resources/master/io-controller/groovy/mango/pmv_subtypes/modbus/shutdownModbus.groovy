import com.revenga.rits.back.data.core.util.ResourcesUtil;
import com.revenga.rits.back.data.core.model.command.SignallingCommand;
import com.revenga.rits.back.mango.core.model.XidPointValueTimeModel;
    
def XidPointValueTimeModel shutdownModbus(SignallingCommand signallingCommand, String dataSourceXid) {

	final String CONTENT = "content";
	final Long SHUTDOWN = 0L;

	GroovyShell shell = new GroovyShell();
	
	def signallingCommandUtils = shell.parse(new File(ResourcesUtil.getPath("io-controller/groovy/mango/SignallingCommandUtils.groovy")))
					
	XidPointValueTimeModel xidPointValueTimeModel = signallingCommandUtils.getXidPointValueTimeModel(signallingCommand, dataSourceXid + "_" + CONTENT, SHUTDOWN);

	return xidPointValueTimeModel;
}
