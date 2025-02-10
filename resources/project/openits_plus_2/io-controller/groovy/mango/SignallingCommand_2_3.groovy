import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import org.apache.logging.log4j.Logger;

import com.revenga.rits.back.io.controller.driver.mango.MangoDriver;
import com.revenga.rits.back.data.core.model.command.SignallingCommand;
import com.revenga.rits.back.data.core.model.command.SignallingCommand.SignallingParam;

public class SignallingCommand_2_3 {

    private static final Long PARAM_CONFIG_JSON = 12L;
    private static final int STOP = 0, UP = 1, DOWN = 2, LEFT = 3, RIGHT = 4, ZOOM_I = 5, ZOOM_D = 6;
    private static final int PTZ_VALUE = 1, SPEED_ID = 2;
    private static final String CAMERA_IP = "192.168.88.74";
    private static final int PORT = 5000;

    private final Logger log;

    public SignallingCommand_2_3(Logger log) {
        this.log = log;
    }

    public boolean process(String dataSourceXid, SignallingCommand signallingCommand, MangoDriver driver) {
        SignallingParam moveParam = null, speedParam = null;

        for (SignallingParam param : signallingCommand.getSignallingParams()) {
            if (param.getId() == SPEED_ID) speedParam = param;
            if (param.getId() == PTZ_VALUE) moveParam = param;
        }

        if (moveParam == null || speedParam == null) {
            log.error("Missing parameters for PTZ control.");
            return false;
        }

        double speed = speedParam.getValue().equals("1") ? 0.5 : 1.0;
        String ptzCommand = generateOnvifCommand(moveParam.getValue(), speed);

        return sendOnvifCommand(ptzCommand);
    }

    private String generateOnvifCommand(String direction, double speed) {
        log.debug("Direction: {} | Speed: {}", direction, speed);
        
        String movementTag = "";
        switch (direction) {
            case "1":
            	movementTag = "<tt:PanTilt x=\"0.0\" y=\"" + speed + "\" xmlns:tt=\"http://www.onvif.org/ver10/schema\"/>";
            	break;
            case "2": 
            	movementTag = "<tt:PanTilt x=\"0.0\" y=\"-" + speed + "\" xmlns:tt=\"http://www.onvif.org/ver10/schema\"/>";
            	break;
            case "3": 
            	movementTag = "<tt:PanTilt x=\"-" + speed + "\" y=\"0.0\" xmlns:tt=\"http://www.onvif.org/ver10/schema\"/>";
            	break;
            case "4": 
            	movementTag = "<tt:PanTilt x=\"" + speed + "\" y=\"0.0\" xmlns:tt=\"http://www.onvif.org/ver10/schema\"/>";
            	break;
            case "5": 
            	movementTag = "<tt:Zoom x=\"" + speed + "\" xmlns:tt=\"http://www.onvif.org/ver10/schema\"/>";
            	break;
            case "6": 
            	movementTag = "<tt:Zoom x=\"-" + speed + "\" xmlns:tt=\"http://www.onvif.org/ver10/schema\"/>";
            	break;
            default: 
            	movementTag = "<tt:PanTilt x=\"0.0\" y=\"0.0\" xmlns:tt=\"http://www.onvif.org/ver10/schema\"/>";
            	break;
        };
        
        return "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
             "<s:Envelope xmlns:s=\"http://www.w3.org/2003/05/soap-envelope\">\n" +
             "    <s:Body>\n" +
             "        <tptz:ContinuousMove xmlns:tptz=\"http://www.onvif.org/ver20/ptz/wsdl\">\n" +
             "            <tptz:ProfileToken>Profile_1</tptz:ProfileToken>\n" +
             "            <tptz:Velocity>\n" +
             movementTag + "\n" +
             "            </tptz:Velocity>\n" +
             "        </tptz:ContinuousMove>\n" +
             "    </s:Body>\n" +
             "</s:Envelope>";
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