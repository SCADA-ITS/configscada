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
	static final String SWITCH_ON = "switch_on_read";
	static final Long VMS_ROTAPANEL_A = 1L;
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
	static final Long SUBTYPE_PANEL_ROTAPANEL = 23;
	static final Long SUBTYPE_PANEL_METEO_TRAFICO = 24;
	static final boolean ALARM_FALSE = false;
	static final Long PRIORITY_OFF = 0L;	
	static final String PRIORITY = "priority";
	static final ArrayList DATAPOINTS = ["r1","r2","r3","r4","r5","r6","r7","r8","r9","r10","r11","t1","t2","t3","t4","t5","t6","t7","w1","w2","w3","w4","w5","w6","w7","w8","w9"];


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
			
			if(element.elementSubtypeId == SUBTYPE_PANEL_NTCIP_3LCS || element.elementSubtypeId == SUBTYPE_PANEL_NTCIP_5LCS || element.elementSubtypeId == SUBTYPE_PANEL_NTCIP_2LCS || 
			   element.elementSubtypeId == SUBTYPE_PANEL_NTCIP_MAT_192_64 || element.elementSubtypeId == SUBTYPE_PANEL_NTCIP_MAT_128_64 || element.elementSubtypeId == SUBTYPE_PANEL_NTCIP_MAT_64_64 || 
			   element.elementSubtypeId == SUBTYPE_PANEL_NTCIP_OVERWEIGHT || element.elementSubtypeId == SUBTYPE_PANEL_NTCIP_RED_BEACON ||
			   element.elementSubtypeId == SUBTYPE_PANEL_NTCIP_BLUE_ARROW || element.elementSubtypeId == SUBTYPE_PANEL_NTCIP_AB){
			   
				def pmv = shell.parse(new File(ResourcesUtil.getPath("io-controller/groovy/mango/pmv_subtypes/ntcip/shutdownNTCIP.groovy")));
				xidPointValueTimeModel = pmv.shutdownNTCIP(signallingCommand, dataSourceXid);	
			}else if(element.elementSubtypeId == SUBTYPE_PANEL_NTCIP_METEOROLOGICO || element.elementSubtypeId == SUBTYPE_PANEL_METEO_TRAFICO){
				def pmv = shell.parse(new File(ResourcesUtil.getPath("io-controller/groovy/mango/pmv_subtypes/utils/utilsPMV.groovy")));
				List<ElementValue> listElements = new ArrayList();
				listElements.add(pmv.elementSetValue(PARAM_MEASURE_ENABLED, PARAM_MEASURE, element.getId(), 'false'));

				pmv = shell.parse(new File(ResourcesUtil.getPath("io-controller/groovy/mango/pmv_subtypes/ntcip/shutdownNTCIP.groovy")));
				xidPointValueTimeModel = pmv.shutdownNTCIP(signallingCommand, dataSourceXid);

				EntitiesManager.getInstance().putElementValues(listElements);
			}else if(element.elementSubtypeId == SUBTYPE_PANEL_NTCIP_SNOW){
			
				def pmv = shell.parse(new File(ResourcesUtil.getPath("io-controller/groovy/mango/pmv_subtypes/contact/shutdownContact.groovy")));
				xidPointValueTimeModel = pmv.shutdownContact(signallingCommand, dataSourceXid);
			}

			xidPointValueTimeModels.add(xidPointValueTimeModel);
			
			if(element.elementSubtypeId == SUBTYPE_PANEL_NTCIP_3LCS || element.elementSubtypeId == SUBTYPE_PANEL_NTCIP_5LCS || element.elementSubtypeId == SUBTYPE_PANEL_NTCIP_2LCS){
				XidPointValueTimeModel xidPointValueTimeModel_priority;
				xidPointValueTimeModel_priority = signallingCommandUtils.getXidPointValueTimeModel(signallingCommand, 
					dataSourceXid + "_" + PRIORITY, PRIORITY_OFF);
				
				xidPointValueTimeModels.add(xidPointValueTimeModel_priority);

				XidPointValueTimeModel xidPointValueTimeModel_dp;
				for (int i = 0; i < DATAPOINTS.size();i++){
					xidPointValueTimeModel_dp = signallingCommandUtils.getXidPointValueTimeModel(signallingCommand, 
					dataSourceXid + "_" + DATAPOINTS[i], ALARM_FALSE);
					xidPointValueTimeModels.add(xidPointValueTimeModel_dp);
				}
			}

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
