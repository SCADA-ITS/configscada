import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.exception.ExceptionUtils

import com.revenga.rits.back.data.core.model.command.SignallingCommand;
import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.util.ResourcesUtil;
import com.revenga.rits.back.io.controller.driver.mango.MangoDriver;
import com.revenga.rits.back.io.controller.service.EntitiesManager;
import com.revenga.rits.back.mango.core.model.XidPointValueTimeModel;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * 
 * SignallingCommand_71_2: Signalling command for VMS (elementTypeId = 71) 
 * 
 */
class SignallingCommand_71_2 {
	static final Long SUBTYPE_PANEL_DGT_GR = 12;
	static final Long SUBTYPE_PANEL_DGT_GR_TXT_GR = 22;

	GroovyShell shell;
	def signallingCommandUtils;
	org.apache.logging.log4j.Logger log;
	
	SignallingCommand_71_2(org.apache.logging.log4j.Logger log) {
	
		this.log = log;
		shell = new GroovyShell();
		signallingCommandUtils = shell.parse(new File(ResourcesUtil.getPath("io-controller/groovy/mango/SignallingCommandUtils.groovy")));
	}
	
	boolean process(String dataSourceXid, SignallingCommand signallingCommand, MangoDriver driver) {
		List<XidPointValueTimeModel> xidPointValueTimeModels = new ArrayList<>();
		ObjectMapper mapper = new ObjectMapper();
		XidPointValueTimeModel xidPointValueTimeModel;		
		Element element = EntitiesManager.getInstance().getElement(signallingCommand.elementTypeId, signallingCommand.elementId);

		try {
			if(element.elementSubtypeId == SUBTYPE_PANEL_DGT_GR){
			   
				def pmv = shell.parse(new File(ResourcesUtil.getPath("io-controller/groovy/mango/pmv_subtypes/dgt/gr.groovy")));
				pmv.gr(signallingCommand, dataSourceXid, driver, element);	
			} else if(element.elementSubtypeId == SUBTYPE_PANEL_DGT_GR_TXT_GR){
			   
				def pmv = shell.parse(new File(ResourcesUtil.getPath("io-controller/groovy/mango/pmv_subtypes/dgt/grtxtgr.groovy")));
				pmv.grtxtgr(signallingCommand, dataSourceXid, driver, element);	
			}
		}catch (NumberFormatException | JsonProcessingException e) {
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}
				
		return true;
	}
}