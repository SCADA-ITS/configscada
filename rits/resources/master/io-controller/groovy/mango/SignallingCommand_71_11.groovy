import java.util.ArrayList;
import java.util.List;

import com.revenga.rits.back.data.core.model.command.SignallingCommand;
import com.revenga.rits.back.io.controller.driver.mango.MangoDriver;
import com.revenga.rits.back.mango.core.model.XidPointValueTimeModel;
import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.ElementValue;
import com.revenga.rits.back.data.core.util.ResourcesUtil;
import com.revenga.rits.back.io.controller.service.EntitiesManager;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


/**
 * 
 * SignallingCommand_71_11: Switch off command for VMS (elementTypeId = 71) ROTAPANEL
 * 
 */
class SignallingCommand_71_11 {

	static final Long SUBTYPE_PANEL_ROTAPANEL = 23;


	GroovyShell shell;
	def signallingCommandUtils;
	org.apache.logging.log4j.Logger log;
	
	SignallingCommand_71_11(org.apache.logging.log4j.Logger log) {
	
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
			
			if(element.elementSubtypeId == SUBTYPE_PANEL_ROTAPANEL) {
				
				xidPointValueTimeModel = signallingCommandUtils.getXidPointValueTimeModel(signallingCommand, 
				dataSourceXid + "_" + "Q1", false);
						
				xidPointValueTimeModels.add(xidPointValueTimeModel);
				xidPointValueTimeModel = signallingCommandUtils.getXidPointValueTimeModel(signallingCommand, 
				dataSourceXid + "_" + "Q2", false);
						
				xidPointValueTimeModels.add(xidPointValueTimeModel);

				xidPointValueTimeModel = signallingCommandUtils.getXidPointValueTimeModel(signallingCommand, 
				dataSourceXid + "_" + "Q3", false);
						
			}

			xidPointValueTimeModels.add(xidPointValueTimeModel);
			
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.setSerializationInclusion(Include.NON_NULL);
			String message = objectMapper.writeValueAsString(xidPointValueTimeModels);
			if (driver != null) {
				log.debug("SignallingCommand_71_11.groovy message: " + message);
				driver.send(message);
			}

				Thread.sleep(5000);

				xidPointValueTimeModels.clear();

				xidPointValueTimeModel = signallingCommandUtils.getXidPointValueTimeModel(signallingCommand, 
				dataSourceXid + "_" + "Q1", true);
						
				xidPointValueTimeModels.add(xidPointValueTimeModel);
				mapper.setSerializationInclusion(Include.NON_NULL);
				message = mapper.writeValueAsString(xidPointValueTimeModels);

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
