 import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.exception.ExceptionUtils;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revenga.rits.back.data.core.model.command.SignallingCommand;
import com.revenga.rits.back.io.controller.driver.mango.MangoDriver;
import com.revenga.rits.back.mango.core.model.XidPointValueTimeModel;
import com.revenga.rits.back.mango.core.model.XidPointValueTimeModel.DataTypeEnum;
import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.ElementValue;
import com.revenga.rits.back.io.controller.service.EntitiesManager;
import com.revenga.rits.back.data.core.model.command.SetActivationAlarmsCommand;
import com.revenga.rits.back.data.core.util.ResourcesUtil;
/**
 * 
 * SignallingCommand_71_3: Switch off command for VMS (elementTypeId = 71) 
 * 
 */
class SignallingCommand_71_3 {
	
	static final String XID_SHUTDOWN = "shutdown";
	static final String CONTENT = "content";
	static final String SIGNALLING = "signalling";
	static final String CROSS_ORDER = "cross_order";
	static final String ARROW_ORDER = "arrow_order";
	static final String BIT_A = "bit_a_order";
	static final String BIT_B = "bit_b_order";
	static final String BIT_C = "bit_c_order";
	static final String BIT_D = "bit_d_order";
	static final String STOP_ORDER = "stop_order";
	static final String MULTI = "";
	static final String CMD_VALUE_POWER_OFF_DGT = "02201A305C2D03";
	static final boolean SWICHT_OFF = false;
	static final Long SHUTDOWN = 0L;
	static final Long PARAM_CONFIG_PROTOCOLO = 3L;
	static final String SIGNAL = "signal";
	//Element Types ID
	static final Long CLV = 11L;
	static final Long AF = 12L;
	static final Long GRAPHIC_TEXT = 18L;
	static final Long STOP = 26L;

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
			
			if(element.elementSubtypeId == CLV){
				ElementValue protocolo = EntitiesManager.getInstance().getElementValueConfig(element, PARAM_CONFIG_PROTOCOLO);

				if(protocolo != null && protocolo.getValue().equals("Modbus")){
					xidPointValueTimeModel = signallingCommandUtils.getXidPointValueTimeModel(signallingCommand, dataSourceXid + "_" + BIT_A, SWICHT_OFF);
					xidPointValueTimeModels.add(xidPointValueTimeModel);
					xidPointValueTimeModel = signallingCommandUtils.getXidPointValueTimeModel(signallingCommand, dataSourceXid + "_" + BIT_B, SWICHT_OFF);
					xidPointValueTimeModels.add(xidPointValueTimeModel);
					xidPointValueTimeModel = signallingCommandUtils.getXidPointValueTimeModel(signallingCommand, dataSourceXid + "_" + BIT_C, SWICHT_OFF);
					xidPointValueTimeModels.add(xidPointValueTimeModel);
					xidPointValueTimeModel = signallingCommandUtils.getXidPointValueTimeModel(signallingCommand, dataSourceXid + "_" + BIT_D, SWICHT_OFF);
					xidPointValueTimeModels.add(xidPointValueTimeModel);
					xidPointValueTimeModel = signallingCommandUtils.getXidPointValueTimeModel(signallingCommand, dataSourceXid + "_" + SIGNAL, SHUTDOWN);
					xidPointValueTimeModels.add(xidPointValueTimeModel);
					log.debug("ENTRA")
				}else{
					log.error("No se encuentra protocolo");	
					return true;			
				}
				
			}else if(element.elementSubtypeId == AF){
				
				ElementValue protocolo = EntitiesManager.getInstance().getElementValueConfig(element, PARAM_CONFIG_PROTOCOLO);
				
				if(protocolo != null && protocolo.getValue().equals("Modbus")){
					xidPointValueTimeModel = signallingCommandUtils.getXidPointValueTimeModel(signallingCommand, dataSourceXid + "_" + CROSS_ORDER, SWICHT_OFF);
					xidPointValueTimeModels.add(xidPointValueTimeModel);
					xidPointValueTimeModel = signallingCommandUtils.getXidPointValueTimeModel(signallingCommand, dataSourceXid + "_" + ARROW_ORDER, SWICHT_OFF);
					xidPointValueTimeModels.add(xidPointValueTimeModel);
				}else{
					log.error("No se encuentra protocolo");	
					return true;			
				}

			}else if(element.elementSubtypeId == GRAPHIC_TEXT){
				ElementValue protocolo = EntitiesManager.getInstance().getElementValueConfig(element, PARAM_CONFIG_PROTOCOLO);
				
				if(protocolo != null && protocolo.getValue().equals("DGT")){
					xidPointValueTimeModel = signallingCommandUtils.getXidPointValueTimeModel(signallingCommand, dataSourceXid + "_" + SIGNALLING, CMD_VALUE_POWER_OFF_DGT);
				}
				xidPointValueTimeModels.add(xidPointValueTimeModel);
			
			}else if(element.elementSubtypeId == STOP){
				ElementValue protocolo = EntitiesManager.getInstance().getElementValueConfig(element, PARAM_CONFIG_PROTOCOLO);
				
				if(protocolo != null && protocolo.getValue().equals("Modbus")){
					xidPointValueTimeModel = signallingCommandUtils.getXidPointValueTimeModel(signallingCommand, dataSourceXid + "_" + STOP_ORDER, SWICHT_OFF);
				}else{
					log.error("No se encuentra protocolo");	
					return true;			
				}
				xidPointValueTimeModels.add(xidPointValueTimeModel);
			}else{
				
				xidPointValueTimeModel = signallingCommandUtils.getXidPointValueTimeModel(signallingCommand, dataSourceXid + "_" + XID_SHUTDOWN, "1");
				xidPointValueTimeModels.add(xidPointValueTimeModel);
			}
			
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.setSerializationInclusion(Include.NON_NULL);
			String message = objectMapper.writeValueAsString(xidPointValueTimeModels);
			log.debug("MENSAJE------> " + message)
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