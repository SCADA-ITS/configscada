import groovy.json.JsonSlurper
import groovy.json.JsonOutput

import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.Logger;

import com.revenga.rits.back.data.core.model.command.SignallingCommand;
import com.revenga.rits.back.data.core.model.ImsIncidentReport;
import com.revenga.rits.back.data.core.model.ImsIncidentTypeTask;
import com.revenga.rits.back.data.core.model.ImsIncidentTypeTaskValue;
import com.revenga.rits.back.data.core.model.command.Command;
import com.revenga.rits.back.incident.manager.service.IncidentEntitiesManager;

class SendToTelegramNotification {

    static final Long TASK_TYPE_PARAM_MESSAGE = 1L;

    Logger log;

    // Constructor
    SendToTelegramNotification(Logger log) {
        this.log = log;
    }

    // Notificar al canal
    boolean notifyUsers(String api_token, String chat_id, String message) {
	
	String api_url = "https://api.telegram.org/bot${api_token}/sendMessage"

        // 🔹 **Enviar también al canal**
        log.info("📢 Enviando notificación al canal ${chat_id}...");

        try {
            def url = new URL(api_url)
            def connection = url.openConnection()
            connection.setRequestMethod("POST")
            connection.setDoOutput(true)
            connection.setRequestProperty("Content-Type", "application/json")

            def payload = JsonOutput.toJson([chat_id: chat_id, text: message])
            connection.outputStream.withWriter("UTF-8") { it.write(payload) }

            def responseCode = connection.responseCode
            if (responseCode == 200) {
                log.info("✅ Mensaje enviado a ${chat_id}")
                return true
            } else {
                log.warn("⚠️ No se pudo enviar el mensaje a ${chat_id}. Código: ${responseCode}")
                return false
            }
        } catch (Exception e) {
            log.error("⚠️ Error enviando mensaje a ${chat_id}: ${e.message}")
            return false
        }

    }

    List<Command> process(ImsIncidentReport incidentReport, ImsIncidentTypeTask incidentTypeTask, List<ImsIncidentTypeTaskValue> values) {
		
        List<Command> commands = null;
        String api_token = '7673259805:AAEqCaKMZ-SvxE8wpUriuGLegpNxLlOLgRM';
        String chat_id = '@TraficoNavarra';
        String message = null;
		boolean respuesta = false;
        
        if (incidentReport.getAffectionStretchId() != null) {

		message = IncidentEntitiesManager.getInstance().getIncidentTypeTaskValue(values, TASK_TYPE_PARAM_MESSAGE);
			
		if (api_token != null && chat_id != null && message != null) {
        
        		try {
		            respuesta = notifyUsers(api_token, chat_id, message);            
		
		            if (commands == null && respuesta) {
		                commands = new ArrayList<>();
		            }
		
		        } catch (Exception e) {
		            log.error("⚠️ Error en process(): ${e.message}")
		        }
        	}
        }

        return commands;
    }
}
