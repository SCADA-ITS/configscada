import com.revenga.rits.back.io.controller.driver.mango.MangoDriver;
import com.revenga.rits.back.data.core.model.command.SignallingCommand;
import com.revenga.rits.back.data.core.model.command.SignallingCommand.SignallingParam;

/**
 * 
 * SignallingCommand_2_2: PRESET Camera  
 * 
 */
class SignallingCommand_2_2 {
	
	private static final String CAMERA_IP = "192.168.88.74";
    private static final int PORT = 5000;

	GroovyShell shell;

	org.apache.logging.log4j.Logger log;

	SignallingCommand_2_2(org.apache.logging.log4j.Logger log) {

		shell = new GroovyShell();
		this.log = log;
	}

	boolean process(String dataSourceXid, SignallingCommand signallingCommand, MangoDriver driver) {
	    boolean resp = true;
	
	    if (signallingCommand?.elementId && signallingCommand?.signallingParams && !signallingCommand.signallingParams.isEmpty()) {
	        SignallingParam param = signallingCommand.signallingParams.get(0);
	        if (param?.value) {
	            String presetId = param.value;
	            String command = """<?xml version="1.0" encoding="utf-8"?>
                <s:Envelope xmlns:s="http://www.w3.org/2003/05/soap-envelope">
                    <s:Body>
                        <tptz:GotoPreset xmlns:tptz="http://www.onvif.org/ver20/ptz/wsdl">
                            <tptz:ProfileToken>Profile_1</tptz:ProfileToken>
                            <tptz:PresetToken>Preset_${presetId}</tptz:PresetToken>
                        </tptz:GotoPreset>
                    </s:Body>
                </s:Envelope>""";
	
	            // Aquí debería llamarse el método que envía el comando a la cámara
	            resp = sendOnvifCommand(command);
	        }
	    }
	    return resp;
	}
	
	private boolean sendOnvifCommand(String command) {
        Socket socket = null

        try {
            socket = new Socket(CAMERA_IP, PORT)
            def writer = new PrintWriter(socket.getOutputStream(), true)

            writer.println("POST /onvif/PTZ HTTP/1.1")
            writer.println("Host: ${CAMERA_IP}")
            writer.println("Content-Type: application/soap+xml; charset=utf-8")
            writer.println("Content-Length: ${command.length()}")
            writer.println()
            writer.println(command)

            log.info("ONVIF Command sent successfully.")
            return true
        } catch (Exception e) {
            log.error("Error sending ONVIF command: ", e)
            return false
        } finally {
            socket?.close()
        }
    }
}
