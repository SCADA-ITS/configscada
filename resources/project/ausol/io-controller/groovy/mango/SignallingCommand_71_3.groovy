import com.revenga.rits.back.data.core.model.command.SignallingCommand;
import com.revenga.rits.back.io.controller.driver.mango.MangoDriver;
import com.revenga.rits.back.mango.core.model.XidPointValueTimeModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revenga.rits.back.data.core.model.Element;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.revenga.rits.back.data.core.util.ResourcesUtil;
import com.revenga.rits.back.io.controller.service.EntitiesManager;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * 
 * SignallingCommand_71_3: Switch off command for VMS (elementTypeId = 71) 
 * 
 */
class SignallingCommand_71_3 {
	
	GroovyShell shell;
	def signallingCommandUtils;
	org.apache.logging.log4j.Logger log;
	
	SignallingCommand_71_3(org.apache.logging.log4j.Logger log) {
	
		this.log = log;
		shell = new GroovyShell();
		signallingCommandUtils = shell.parse(new File(ResourcesUtil.getPath("io-controller/groovy/mango/SignallingCommandUtils.groovy")));
	}
	
	boolean process(String dataSourceXid, SignallingCommand signallingCommand, MangoDriver driver) {
		try {
			List<XidPointValueTimeModel> xidPointValueTimeModels = new ArrayList<>();
			ObjectMapper mapper = new ObjectMapper();
			XidPointValueTimeModel xidPointValueTimeModel;		
			Element element = EntitiesManager.getInstance().getElement(signallingCommand.elementTypeId, signallingCommand.elementId);
			
			if(element.elementSubtypeId == 11 || element.elementSubtypeId == 12 || element.elementSubtypeId == 16 || element.elementSubtypeId == 18 || element.elementSubtypeId == 22){
				def pmv = shell.parse(new File(ResourcesUtil.getPath("io-controller/groovy/mango/pmv_subtypes/dgt/shutdownDGT.groovy")));
				xidPointValueTimeModel = pmv.shutdownDGT(signallingCommand, dataSourceXid);	
			}else if(element.elementSubtypeId == 9){
				def pmv = shell.parse(new File(ResourcesUtil.getPath("io-controller/groovy/mango/pmv_subtypes/raspberry/shutdownRaspberry.groovy")));
				xidPointValueTimeModel = pmv.shutdownRaspberry(signallingCommand, dataSourceXid);
			}
			xidPointValueTimeModels.add(xidPointValueTimeModel);
				
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.setSerializationInclusion(Include.NON_NULL);
			String message = objectMapper.writeValueAsString(xidPointValueTimeModels);
			if (driver != null) {
				log.debug("SignallingCommand_71_3.groovy message: " + message);
				driver.send(message);
			}
		} catch (NumberFormatException | JsonProcessingException e) {
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}
		return true;
	}
	
}