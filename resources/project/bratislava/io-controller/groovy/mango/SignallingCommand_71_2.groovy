import java.util.ArrayList;
import java.util.List;

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
	static final Long SUBTYPE_PANEL_NTCIP_3LCS = 1;
	static final Long SUBTYPE_PANEL_NTCIP_5LCS = 2;
	static final Long SUBTYPE_PANEL_NTCIP_2LCS = 3;
	static final Long SUBTYPE_PANEL_NTCIP_MAT_192_64 = 4;
	static final Long SUBTYPE_PANEL_NTCIP_MAT_128_64 = 5;
	static final Long SUBTYPE_PANEL_NTCIP_MAT_64_64 = 6;
	static final Long SUBTYPE_PANEL_NTCIP_METEOROLOGICO = 7;
	static final Long SUBTYPE_PANEL_NTCIP_SNOW = 8;
	static final Long SUBTYPE_PANEL_NTCIP_OVERWEIGHT = 13;
	static final Long SUBTYPE_PANEL_NTCIP_RED_BEACON = 14;
	static final Long SUBTYPE_PANEL_NTCIP_BLUE_ARROW = 15;
	static final Long SUBTYPE_PANEL_NTCIP_AB = 17;
	static final Long SUBTYPE_PANEL_METEO_TRAFICO = 24;


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
			if(element.elementSubtypeId == SUBTYPE_PANEL_NTCIP_3LCS){
			   
				def pmv = shell.parse(new File(ResourcesUtil.getPath("io-controller/groovy/mango/pmv_subtypes/ntcip/signalling3LCS.groovy")));
				pmv.signalling3LCS(signallingCommand, dataSourceXid, driver, element, log);	
			} else if(element.elementSubtypeId == SUBTYPE_PANEL_NTCIP_5LCS){
			   
				def pmv = shell.parse(new File(ResourcesUtil.getPath("io-controller/groovy/mango/pmv_subtypes/ntcip/signalling5LCS.groovy")));
				pmv.signalling3LCS(signallingCommand, dataSourceXid, driver, element, log);	
			}else if(element.elementSubtypeId == SUBTYPE_PANEL_NTCIP_2LCS){
			   
				def pmv = shell.parse(new File(ResourcesUtil.getPath("io-controller/groovy/mango/pmv_subtypes/ntcip/signalling2LCS.groovy")));
				pmv.signalling2LCS(signallingCommand, dataSourceXid, driver, element, log);	
			}else if(element.elementSubtypeId == SUBTYPE_PANEL_NTCIP_MAT_192_64){
			
				def pmv = shell.parse(new File(ResourcesUtil.getPath("io-controller/groovy/mango/pmv_subtypes/ntcip/signallingmat192x64.groovy")));
				pmv.signallingmat192x64(signallingCommand, dataSourceXid, driver, element);	
			}else if(element.elementSubtypeId == SUBTYPE_PANEL_NTCIP_MAT_128_64){
			
				def pmv = shell.parse(new File(ResourcesUtil.getPath("io-controller/groovy/mango/pmv_subtypes/ntcip/signallingmat128x64.groovy")));
				pmv.signallingmat128x64(signallingCommand, dataSourceXid, driver, element);	
			}else if(element.elementSubtypeId == SUBTYPE_PANEL_NTCIP_MAT_64_64){
			
				def pmv = shell.parse(new File(ResourcesUtil.getPath("io-controller/groovy/mango/pmv_subtypes/ntcip/signallingmat64x64.groovy")));
				pmv.signallingmat64x64(signallingCommand, dataSourceXid, driver, element);	
			}else if(element.elementSubtypeId == SUBTYPE_PANEL_NTCIP_METEOROLOGICO){
			
				def pmv = shell.parse(new File(ResourcesUtil.getPath("io-controller/groovy/mango/pmv_subtypes/ntcip/signallingTemp.groovy")));
				pmv.signallingTemp(signallingCommand, dataSourceXid, driver, element, log);	
			}else if(element.elementSubtypeId == SUBTYPE_PANEL_METEO_TRAFICO){
			
				def pmv = shell.parse(new File(ResourcesUtil.getPath("io-controller/groovy/mango/pmv_subtypes/ntcip/signallingMeteo_trafico.groovy")));
				pmv.signallingMeteo_trafico(signallingCommand, dataSourceXid, driver, element, log);	
			}
		}catch (NumberFormatException | JsonProcessingException e) {
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}
				
		return true;
	}
}