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
	static final String MESSAGE_HEX = "message_hex";
	static final String MULTI = "";
	static final String CMD_VALUE_POWER_OFF_NTCIP = "FFFFFF07000100007F000001";
	static final String CMD_VALUE_POWER_OFF_NTCIP_PRIO5 = "FFFF0507000100007F000001";
	static final String CMD_VALUE_POWER_OFF_DGT = "02201A305C2D03";
	static final boolean SWICHT_OFF = false;
	static final String DO_SWITCH_ON = "do_switch_on"
	static final Long SHUTDOWN = 0L;
	static final Long ALARM_DISABLE = 71381L;
	static final Long PARAM_CONFIG_PROTOCOLO = 2L;
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
			
			if (element.elementSubtypeId == 1 || element.elementSubtypeId == 2 || element.elementSubtypeId == 3 ||  element.elementSubtypeId == 4 || element.elementSubtypeId == 6 || element.elementSubtypeId == 13 || element.elementSubtypeId == 14 || element.elementSubtypeId == 15 || element.elementSubtypeId == 17 || element.elementSubtypeId == 19){
				xidPointValueTimeModel = signallingCommandUtils.getXidPointValueTimeModel(signallingCommand, dataSourceXid + "_" + MESSAGE_HEX, CMD_VALUE_POWER_OFF_NTCIP);
				xidPointValueTimeModels.add(xidPointValueTimeModel);
			}else if(element.elementSubtypeId == 7){
				List<ElementValue> listElements = new ArrayList();
				listElements.add(elementSetValue(PARAM_MEASURE_ENABLED, TYPE_PARAM_MEASURE, element.getId(), true));
				EntitiesManager.getInstance().putElementValues(listElements);
			}else if(element.elementSubtypeId == 8){
				xidPointValueTimeModel = signallingCommandUtils.getXidPointValueTimeModel(signallingCommand, dataSourceXid + "_" + DO_SWITCH_ON, SWICHT_OFF);
				xidPointValueTimeModels.add(xidPointValueTimeModel);
			}else if(element.elementSubtypeId == 11 || element.elementSubtypeId == 12){
				ElementValue protocolo = EntitiesManager.getInstance().getElementValueConfig(element, PARAM_CONFIG_PROTOCOLO);
				
				if(protocolo != null && protocolo.getValue().equals("DGT")){
					xidPointValueTimeModel = signallingCommandUtils.getXidPointValueTimeModel(signallingCommand, dataSourceXid + "_" + SIGNALLING, CMD_VALUE_POWER_OFF_DGT);
				}else{
					xidPointValueTimeModel = signallingCommandUtils.getXidPointValueTimeModel(signallingCommand, dataSourceXid + "_" + CONTENT, SHUTDOWN);
				}
				xidPointValueTimeModels.add(xidPointValueTimeModel);
			}else if(element.elementSubtypeId == 16){
				ElementValue protocolo = EntitiesManager.getInstance().getElementValueConfig(element, PARAM_CONFIG_PROTOCOLO);
				
				if(protocolo != null && protocolo.getValue().equals("DGT")){
					xidPointValueTimeModel = signallingCommandUtils.getXidPointValueTimeModel(signallingCommand, dataSourceXid + "_" + SIGNALLING, CMD_VALUE_POWER_OFF_DGT);
				}else{
					xidPointValueTimeModel = signallingCommandUtils.getXidPointValueTimeModel(signallingCommand, dataSourceXid + "_" + CONTENT, MULTI);
				}
				xidPointValueTimeModels.add(xidPointValueTimeModel);
			}else if(element.elementSubtypeId == 18){
				ElementValue protocolo = EntitiesManager.getInstance().getElementValueConfig(element, PARAM_CONFIG_PROTOCOLO);
				
				if(protocolo != null && protocolo.getValue().equals("DGT")){
					xidPointValueTimeModel = signallingCommandUtils.getXidPointValueTimeModel(signallingCommand, dataSourceXid + "_" + SIGNALLING, CMD_VALUE_POWER_OFF_DGT);
				}
				xidPointValueTimeModels.add(xidPointValueTimeModel);
			}else if (element.elementSubtypeId == 20 || element.elementSubtypeId == 21){
				xidPointValueTimeModel = signallingCommandUtils.getXidPointValueTimeModel(signallingCommand, dataSourceXid + "_" + MESSAGE_HEX, CMD_VALUE_POWER_OFF_NTCIP_PRIO5);
				xidPointValueTimeModels.add(xidPointValueTimeModel);
			}else{
				
				xidPointValueTimeModel = signallingCommandUtils.getXidPointValueTimeModel(signallingCommand, dataSourceXid + "_" + XID_SHUTDOWN, "1");
				xidPointValueTimeModels.add(xidPointValueTimeModel);
			}
			
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.setSerializationInclusion(Include.NON_NULL);
			String message = objectMapper.writeValueAsString(xidPointValueTimeModels);
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