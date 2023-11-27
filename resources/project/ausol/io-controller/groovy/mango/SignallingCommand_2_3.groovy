import com.revenga.rits.back.io.controller.driver.mango.MangoDriver;
import com.revenga.rits.back.data.core.model.command.SignallingCommand;
import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.ElementValue;
import groovy.json.*;
import com.revenga.rits.back.io.controller.service.EntitiesManager;
import com.revenga.rits.back.mango.core.model.XidPointValueTimeModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revenga.rits.back.data.core.util.ResourcesUtil;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


/**
 * 
 * SignallingCommand_2_3: PTZ Camera  
 * 
 */
class SignallingCommand_2_3 {

	static final Long PARAM_CONFIG_BRAND = 1L;
	static final Long PARAM_CONFIG_IP = 9L;
	static final Long PARAM_CONFIG_USER = 10L;
	static final Long PARAM_CONFIG_PASSWORD = 11L;
	static final Long PARAM_CONFIG_PARAMETERS = 12L;
		
	static final int ID_UP_MOVE_MAX = 4;
	
	static final String XID_auth = "DP_Camera_PTZ_parameters_authorization";
	static final String XID_ip = "DP_Camera_PTZ_parameters_ip";
	static final String XID_move = "DP_Camera_PTZ_parameters_move";
	static final String XID_zoom = "DP_Camera_PTZ_parameters_zoom";
	static final String XID_speed = "DP_Camera_PTZ_parameters_speed";
	static final String XID_brand = "DP_Camera_PTZ_parameters_brand";
	
	static final String AUTH_NORMAL = "normal";
	static final String AUTH_B64 = "base64";
	
	
	GroovyShell shell;
	def signallingCommandUtils;
	org.apache.logging.log4j.Logger log;

	SignallingCommand_2_3(org.apache.logging.log4j.Logger log) {

		shell = new GroovyShell();
		this.log = log;
		signallingCommandUtils = shell.parse(new File(ResourcesUtil.getPath("io-controller/groovy/mango/SignallingCommandUtils.groovy")));
	}

	boolean process(String dataSourceXid, SignallingCommand signallingCommand, MangoDriver driver) {

		boolean resp = false;

		if( signallingCommand != null && signallingCommand.getElementId() != null &&
			signallingCommand.getSignallingParams() != null && !signallingCommand.getSignallingParams().isEmpty() &&
			signallingCommand.getSignallingParams().get(0) != null && signallingCommand.getSignallingParams().get(0).getValue() != null) {

			try {
				Element element = EntitiesManager.getInstance().getElement(signallingCommand.getElementTypeId(), signallingCommand.getElementId());
				ElementValue elementBrand;
				ElementValue elementUser;
				ElementValue elementPassword;
				ElementValue elementIP;
				ElementValue elementParameters;
				
				if (element!=null){
					elementBrand = EntitiesManager.getInstance().getElementValueConfig(element, PARAM_CONFIG_BRAND);
					elementUser = EntitiesManager.getInstance().getElementValueConfig(element, PARAM_CONFIG_USER);
					elementPassword = EntitiesManager.getInstance().getElementValueConfig(element, PARAM_CONFIG_PASSWORD);
					elementIP = EntitiesManager.getInstance().getElementValueConfig(element, PARAM_CONFIG_IP);
					elementParameters = EntitiesManager.getInstance().getElementValueConfig(element, PARAM_CONFIG_PARAMETERS);
				} else {
					log.error("No se encuentra Element" + signallingCommand.getElementTypeId() + ":" + signallingCommand.getElementId());
					return false;
				}
				
				if(elementUser!=null && elementPassword!=null){					
					List<XidPointValueTimeModel> xidPointValueTimeModels = new ArrayList<>();
					XidPointValueTimeModel xidPointValueTimeModel;
										
					int commandId = Integer.parseInt(signallingCommand.getSignallingParams().get(0).getValue());
					int speedId = Integer.parseInt(signallingCommand.getSignallingParams().get(1).getValue());
					
					def parametersJSON = new JsonSlurper().parseText(elementParameters.getValue());
					
					String auth = elementUser.getValue() + ":" + elementPassword.getValue();
					
					if ((parametersJSON[speedId-1].authType).equals(AUTH_B64))
						auth = Base64.getEncoder().encodeToString(auth.getBytes());	
					
					xidPointValueTimeModel = signallingCommandUtils.getXidPointValueTimeModel(signallingCommand, XID_brand, elementBrand.getValue());
					xidPointValueTimeModels.add(xidPointValueTimeModel);
					
					xidPointValueTimeModel = signallingCommandUtils.getXidPointValueTimeModel(signallingCommand, XID_auth, auth);
					xidPointValueTimeModels.add(xidPointValueTimeModel);
												
					xidPointValueTimeModel = signallingCommandUtils.getXidPointValueTimeModel(signallingCommand, XID_ip, elementIP.getValue());
					xidPointValueTimeModels.add(xidPointValueTimeModel);
										
					xidPointValueTimeModel = signallingCommandUtils.getXidPointValueTimeModel(signallingCommand, XID_speed, String.valueOf(parametersJSON[speedId-1].commands[commandId-1].value));
					xidPointValueTimeModels.add(xidPointValueTimeModel);
											
					//Diferencio entre un comando de movimiento de cámara o uno de zoom
					if (commandId > ID_UP_MOVE_MAX)
						xidPointValueTimeModel = signallingCommandUtils.getXidPointValueTimeModel(signallingCommand, XID_zoom, String.valueOf(parametersJSON[speedId-1].commands[commandId-1].name));
					else
						xidPointValueTimeModel = signallingCommandUtils.getXidPointValueTimeModel(signallingCommand, XID_move, String.valueOf(parametersJSON[speedId-1].commands[commandId-1].name));
							
					xidPointValueTimeModels.add(xidPointValueTimeModel);
					
					ObjectMapper objectMapper = new ObjectMapper();
					objectMapper.setSerializationInclusion(Include.NON_NULL);
					String message = objectMapper.writeValueAsString(xidPointValueTimeModels);

					if (driver != null) {
						
						driver.send(message);
					}
					
					return true;
				}
			} catch(NumberFormatException e) {

				log.error(e.getMessage());
				log.debug(ExceptionUtils.getStackTrace(e));
			}
		}

		return resp;
	}
}
