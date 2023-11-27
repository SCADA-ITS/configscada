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
	static final Long SUBTYPE_PANEL_MODBUS_CLV = 11;
	static final Long SUBTYPE_PANEL_MODBUS_AF = 12;
	static final Long SUBTYPE_PANEL_NTCIP_TXT = 16;
	static final Long SUBTYPE_PANEL_NTCIP_GR_TXT = 19;
	static final Long SUBTYPE_PANEL_NTCIP_TARIFARIO = 21;

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
			if(element.elementSubtypeId == SUBTYPE_PANEL_MODBUS_CLV){
			   
				def pmv = shell.parse(new File(ResourcesUtil.getPath("io-controller/groovy/mango/pmv_subtypes/modbus/CLV.groovy")));
				pmv.CLV(signallingCommand, dataSourceXid, driver, element);	
			} else if(element.elementSubtypeId == SUBTYPE_PANEL_MODBUS_AF){
			   
				def pmv = shell.parse(new File(ResourcesUtil.getPath("io-controller/groovy/mango/pmv_subtypes/modbus/AF.groovy")));
				pmv.AF(signallingCommand, dataSourceXid, driver, element);	
			}else if(element.elementSubtypeId == SUBTYPE_PANEL_NTCIP_TXT){
			   
				def pmv = shell.parse(new File(ResourcesUtil.getPath("io-controller/groovy/mango/pmv_subtypes/ntcip/txt1line.groovy")));
				pmv.txt1line(signallingCommand, dataSourceXid, driver, element);	
			}else if(element.elementSubtypeId == SUBTYPE_PANEL_NTCIP_GR_TXT){
			
				def pmv = shell.parse(new File(ResourcesUtil.getPath("io-controller/groovy/mango/pmv_subtypes/ntcip/grtxt.groovy")));
				pmv.grtxt(signallingCommand, dataSourceXid, driver, element);	
			}else if(element.elementSubtypeId == SUBTYPE_PANEL_NTCIP_TARIFARIO){
				String multi = signallingCommand.getSignallingParams().get(0).getValue();

				def pmv = shell.parse(new File(ResourcesUtil.getPath("io-controller/groovy/mango/pmv_subtypes/ntcip/tarifario.groovy")));
				pmv.tarifario(signallingCommand, dataSourceXid, driver, element, multi, log);	
			}
		}catch (NumberFormatException | JsonProcessingException e) {
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}
				
		return true;
	}
}