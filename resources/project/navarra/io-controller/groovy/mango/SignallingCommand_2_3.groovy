import com.revenga.rits.back.io.controller.service.EntitiesManager;
import com.revenga.rits.back.io.controller.service.OnvifEntitiesManager
import com.revenga.rits.back.io.controller.service.OnvifEntitiesManager.OnvifCamera;
import com.revenga.rits.back.io.controller.driver.mango.MangoDriver;
import com.revenga.rits.back.data.core.model.command.SignallingCommand;
import com.revenga.rits.back.data.core.model.command.SignallingCommand.SignallingParam;
import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.ElementValue;
import groovy.json.*;

import org.apache.commons.lang3.exception.ExceptionUtils;
/**
 * 
 * SignallingCommand_2_3: PTZ Camera  
 * 
 */
class SignallingCommand_2_3 {
	static final Long PARAM_CONFIG_JSON = 12L;
	
	static final int STOP = 0;
	static final int UP = 1;
	static final int DOWN = 2;
	static final int LEFT = 3;
	static final int RIGHT = 4;
	static final int ZOOM_I = 5;
	static final int ZOOM_D = 6;
	
	static final int PTZ_VALUE = 1;
	static final int SPEED_ID = 2;
	
	GroovyShell shell;
	
	def signallingCommandUtils;
	
	org.apache.logging.log4j.Logger log;
	
	SignallingCommand_2_3(org.apache.logging.log4j.Logger log) {
	
		shell = new GroovyShell();
		this.log = log;
	}
	
	boolean process(String dataSourceXid, SignallingCommand signallingCommand, MangoDriver driver) {
		boolean resp = false;
		Long speedId;
		
		if( signallingCommand != null && signallingCommand.getElementId() != null &&
				signallingCommand.getSignallingParams() != null && !signallingCommand.getSignallingParams().isEmpty()) {

			SignallingParam signallingParamPTZValue = null;
			for (SignallingParam signallingParam : signallingCommand.getSignallingParams()) {

				if (signallingParam.getId() == PTZ_VALUE) {

					signallingParamPTZValue = signallingParam;
					break;
				}
			}
			
			if(signallingParamPTZValue != null && signallingParamPTZValue.getValue() != null) {
			
				try{
					
					int intValue = Integer.parseInt(signallingParamPTZValue.getValue());
					OnvifCamera onvifCamera = OnvifEntitiesManager.getInstance().getCamera(signallingCommand.getElementId());
					
					if(onvifCamera != null){
						
						Element element = EntitiesManager.getInstance().getElement(signallingCommand.getElementTypeId(), signallingCommand.getElementId())
						ElementValue elementDataJson = EntitiesManager.getInstance().getElementValueConfig(element, PARAM_CONFIG_JSON);
						String jsonValue = "";
						
						if(elementDataJson != null){
							jsonValue = elementDataJson.getValue();
						}else{
							jsonValue = '{"up":0.02,"down":-0.02,"right":0.02,"left":-0.02,"zoom_in":0.02,"zoom_out":-0.02}';
						}
						
						SignallingParam signallingParamSpeedValue = null;
						for (SignallingParam signallingParam : signallingCommand.getSignallingParams()) {
							if (signallingParam.getId() == SPEED_ID) {
								signallingParamSpeedValue = signallingParam;
								break;
							}
						}
						
						def jsonObject;
						if(signallingParamSpeedValue != null) {
						
							speedId =Long.parseLong(signallingParamSpeedValue.getValue());		

							def jsonArrayObject = new JsonSlurper().parseText(jsonValue);
							if(jsonArrayObject != null) {
								for (def obj : jsonArrayObject) {
									if (obj.id == speedId) {
										jsonObject = obj;
										break;
									}
								}
							}
						} else {
							jsonObject = new JsonSlurper().parseText(jsonValue);
						}
						
						if(jsonObject != null){
							log.info(jsonObject);
							Long speedMove = speedId * 0.02;
							
							switch (Integer.parseInt(signallingParamPTZValue.getValue())) {

								case STOP:
									resp = onvifCamera.relativeMove(0L, 0L, 0L);
									break;

								case UP:
									resp = onvifCamera.relativeMove(0L, speedMove, 0L);
									break;

								case DOWN:
									resp = onvifCamera.relativeMove(0L, -speedMove, 0L);
									break;

								case LEFT:
									resp = onvifCamera.relativeMove(-speedMove, 0L, 0L);
									break;

								case RIGHT:
									resp = onvifCamera.relativeMove(speedMove, 0L, 0L);
									break;

								case ZOOM_I:
									resp = onvifCamera.relativeMove(0L, 0L, speedMove);
									break;

								case ZOOM_D:
									resp = onvifCamera.relativeMove(0L, 0L, -speedMove);
									break;
							}
						}
					}
				}catch (Exception e){
				
					log.debug(ExceptionUtils.getStackTrace(e));
				}
			}
		}
		return resp;
	}
}