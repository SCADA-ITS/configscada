import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.net.Socket;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

import com.revenga.rits.back.data.core.model.ImsIncidentReport;
import com.revenga.rits.back.data.core.model.ImsIncidentTypeTask;
import com.revenga.rits.back.data.core.model.ImsIncidentTypeTaskValue;
import org.apache.logging.log4j.Logger;

class LockCamera {

    static final Long ELEMENT_TYPE_CAMERA = 2L;
    static final Long LOCK_CAMERA_COMMAND = 1L;
    private static final String DOCKLIGHT_IP = "192.168.88.74"; 
    private static final int DOCKLIGHT_PORT = 6000; 

    Logger log;

    LockCamera(Logger log) {
        this.log = log;
    }

    void process(ImsIncidentReport incidentReport, ImsIncidentTypeTask incidentTypeTask, List<ImsIncidentTypeTaskValue> values) {

        if (incidentReport.getAffectionStretchId() != null) {
            Long stretch = incidentReport.getAffectionStretchId();

            List<String> tunTv1CAList = List.of("TUN-TV-1CA", "TUN-TV-2CA", "TUN-TV-3CA", "TUN-TV-4CA", "TUN-TV-5CA", "TUN-TV-6CA", "TUN-TV-7CA", "TUN-TV-8CA", "TUN-TV-9CA", "TUN-TV-10CA", "TUN-TV-11CA");
            List<String> tunTv1MAList = List.of("TUN-TV-1MA", "TUN-TV-2MA", "TUN-TV-3MA", "TUN-TV-4MA", "TUN-TV-5MA", "TUN-TV-6MA", "TUN-TV-7MA", "TUN-TV-8MA", "TUN-TV-9MA");

            Random random = new Random();
            String selectedName = null;
            String direction = null;

            if (stretch == 4001) {
                selectedName = tunTv1CAList.get(random.nextInt(tunTv1CAList.size()));
                direction = "Direccion sur";
            } else if (stretch == 4002) {
                selectedName = tunTv1MAList.get(random.nextInt(tunTv1MAList.size()));
                direction = "Direccion norte";
            }

            if (selectedName != null && direction != null) {
                sendToDocklight(selectedName, direction);
            }
        }
    }

    private void sendToDocklight(String cameraName, String direction) {
        String message = "Camara: " + cameraName + " | " + direction;
        Socket socket = null;
        OutputStream outputStream = null;
        
        try {
            socket = new Socket(DOCKLIGHT_IP, DOCKLIGHT_PORT);
            outputStream = socket.getOutputStream();
            outputStream.write(message.getBytes(StandardCharsets.UTF_8));
            outputStream.flush();
        } catch (Exception e) {
            log.error("Error al enviar mensaje", e);
        } finally {
            try {
                if (outputStream != null) outputStream.close();
                if (socket != null) socket.close();
            } catch (Exception e) {
                log.error("Error al cerrar el socket", e);
            }
        }
    }
}