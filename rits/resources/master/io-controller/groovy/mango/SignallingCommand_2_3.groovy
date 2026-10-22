import com.revenga.rits.back.io.controller.service.OnvifEntitiesManager
import com.revenga.rits.back.io.controller.service.OnvifEntitiesManager.OnvifCamera;
import com.revenga.rits.back.io.controller.driver.mango.MangoDriver;
import com.revenga.rits.back.data.core.model.command.SignallingCommand;
import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.ElementValue;
import groovy.json.*;
import com.revenga.rits.back.io.controller.service.EntitiesManager;


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

	GroovyShell shell;

	org.apache.logging.log4j.Logger log;

	SignallingCommand_2_3(org.apache.logging.log4j.Logger log) {

		shell = new GroovyShell();
		this.log = log;
	}

	boolean process(String dataSourceXid, SignallingCommand signallingCommand, MangoDriver driver) {

		boolean resp = false;

		if( signallingCommand != null && signallingCommand.getElementId() != null &&
			signallingCommand.getSignallingParams() != null && !signallingCommand.getSignallingParams().isEmpty() &&
			signallingCommand.getSignallingParams().get(0) != null && signallingCommand.getSignallingParams().get(0).getValue() != null) {

			try {
				int intValue = Integer.parseInt(signallingCommand.getSignallingParams().get(0).getValue());
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
					
					def jsonObject = new JsonSlurper().parseText(jsonValue);
					switch (intValue) {

						case STOP:
							resp = onvifCamera.relativeMove(0L, 0L, 0L);
							break;
						
						case UP:
							resp = onvifCamera.relativeMove(0L, jsonObject.up, 0L);
							break;

						case DOWN:
							resp = onvifCamera.relativeMove(0L, jsonObject.down, 0L);
							break;

						case LEFT:
							resp = onvifCamera.relativeMove(jsonObject.left, 0L, 0L);
							break;

						case RIGHT:
							resp = onvifCamera.relativeMove(jsonObject.right, 0L, 0L);
							break;

						case ZOOM_I:
							resp = onvifCamera.relativeMove(0L, 0L, jsonObject.zoom_in);
							break;

						case ZOOM_D:
							resp = onvifCamera.relativeMove(0L, 0L, jsonObject.zoom_out);
							break;
					}
				}
			} catch(NumberFormatException e) {

				log.error(e.getMessage());
				log.debug(ExceptionUtils.getStackTrace(e));
			}
		}

		return resp;
	}
}
