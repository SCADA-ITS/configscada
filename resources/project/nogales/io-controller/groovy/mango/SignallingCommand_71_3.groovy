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
 * SignallingCommand_71_3: Switch off command for VMS (elementTypeId = 71) 
 * 
 */
class SignallingCommand_71_3 {
	static final Long PARAM_MEASURE = 2L;
	static final Long PARAM_MEASURE_ENABLED = 7L;
		
	static final Long SUBTYPE_PANEL_MODBUS_CLV = 11;
	static final Long SUBTYPE_PANEL_MODBUS_AF = 12;
	static final Long SUBTYPE_PANEL_NTCIP_TXT = 16;
	static final Long SUBTYPE_PANEL_NTCIP_GR_TXT = 19;
	static final Long SUBTYPE_PANEL_NTCIP_TARIFARIO = 21;
	
	static final String CMD_ENABLED = "enabled";
	static final Long ENABLED_VALUE = 0L;
	
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

			if(element.elementSubtypeId == SUBTYPE_PANEL_MODBUS_CLV || element.elementSubtypeId == SUBTYPE_PANEL_MODBUS_AF){

				def pmv = shell.parse(new File(ResourcesUtil.getPath("io-controller/groovy/mango/pmv_subtypes/modbus/shutdownModbus.groovy")));
				xidPointValueTimeModel = pmv.shutdownModbus(signallingCommand, dataSourceXid);	
			}else if(element.elementSubtypeId == SUBTYPE_PANEL_NTCIP_TXT || element.elementSubtypeId == SUBTYPE_PANEL_NTCIP_GR_TXT ||
					element.elementSubtypeId == SUBTYPE_PANEL_NTCIP_TARIFARIO){

				def pmv = shell.parse(new File(ResourcesUtil.getPath("io-controller/groovy/mango/pmv_subtypes/ntcip/shutdownNTCIP.groovy")));
				xidPointValueTimeModel = pmv.shutdownNTCIP(signallingCommand, dataSourceXid);
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