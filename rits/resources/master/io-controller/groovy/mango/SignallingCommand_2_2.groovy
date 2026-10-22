import com.revenga.rits.back.io.controller.service.OnvifEntitiesManager
import com.revenga.rits.back.io.controller.service.OnvifEntitiesManager.OnvifCamera;
import com.revenga.rits.back.io.controller.driver.mango.MangoDriver;
import com.revenga.rits.back.data.core.model.command.SignallingCommand;

/**
 * 
 * SignallingCommand_2_2: PRESET Camera  
 * 
 */
class SignallingCommand_2_2 {

	GroovyShell shell;

	org.apache.logging.log4j.Logger log;

	SignallingCommand_2_2(org.apache.logging.log4j.Logger log) {

		shell = new GroovyShell();
		this.log = log;
	}

	boolean process(String dataSourceXid, SignallingCommand signallingCommand, MangoDriver driver) {

		boolean resp = false;

		if( signallingCommand != null && signallingCommand.getElementId() != null &&
				signallingCommand.getSignallingParams() != null && !signallingCommand.getSignallingParams().isEmpty() &&
				signallingCommand.getSignallingParams().get(0) != null && signallingCommand.getSignallingParams().get(0).getValue() != null) {

			String presetId = signallingCommand.getSignallingParams().get(0).getValue();
			OnvifCamera onvifCamera = OnvifEntitiesManager.getInstance().getCamera(signallingCommand.getElementId());

			if(onvifCamera != null){

				resp = onvifCamera.gotoPreset(presetId);
			}
		}

		return resp;
	}
}
