import groovy.json.JsonSlurper
import groovy.json.JsonOutput

import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.Logger;

import com.revenga.rits.back.data.core.model.Location;
import com.revenga.rits.back.data.core.model.Stretch;
import com.revenga.rits.back.data.core.model.command.SignallingCommand;
import com.revenga.rits.back.data.core.model.ImsIncidentReport;
import com.revenga.rits.back.data.core.model.ImsIncidentTypeTask;
import com.revenga.rits.back.data.core.model.ImsIncidentTypeTaskValue;
import com.revenga.rits.back.data.core.model.command.Command;
import com.revenga.rits.back.incident.manager.service.IncidentEntitiesManager;
import com.revenga.rits.back.incident.manager.service.EntitiesManager;
import com.revenga.rits.back.data.core.model.StretchLocation;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

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

			Location imsIncidentLocation = EntitiesManager.getInstance().getLocation(incidentReport.getLocationId());
			Stretch imsIncidentStretch = IncidentEntitiesManager.getInstance().getStretch(incidentReport.getAffectionStretchId());
	
			message = IncidentEntitiesManager.getInstance().getIncidentTypeTaskValue(values, TASK_TYPE_PARAM_MESSAGE);
						
			message = message.replace("@alias", incidentReport.getAlias());
			message = message.replace("@tipo", "⚠️ " + IncidentEntitiesManager.getInstance().getImsIncidentType(incidentReport.getIncidentTypeId()).getDescription());
			message = message.replace("@localizacion", "🚩 " + imsIncidentStretch.getAlias() + " - " + imsIncidentLocation.getAlias());
			
			if (IncidentEntitiesManager.getInstance().getImsRoadImpact(incidentReport.getRoadImpactId()) != null){
				if (IncidentEntitiesManager.getInstance().getImsRoadImpact(incidentReport.getRoadImpactId()).getDescription().contains("interrumpida")){
					message = message.replace("@afeccion", "⚫️ " + IncidentEntitiesManager.getInstance().getImsRoadImpact(incidentReport.getRoadImpactId()).getDescription());
				}else if (IncidentEntitiesManager.getInstance().getImsRoadImpact(incidentReport.getRoadImpactId()).getDescription().contains("difícil")){
					message = message.replace("@afeccion", "🔴 " + IncidentEntitiesManager.getInstance().getImsRoadImpact(incidentReport.getRoadImpactId()).getDescription());
				}else if (IncidentEntitiesManager.getInstance().getImsRoadImpact(incidentReport.getRoadImpactId()).getDescription().contains("irregular")){
					message = message.replace("@afeccion", "🟡 " + IncidentEntitiesManager.getInstance().getImsRoadImpact(incidentReport.getRoadImpactId()).getDescription());
				}else if (IncidentEntitiesManager.getInstance().getImsRoadImpact(incidentReport.getRoadImpactId()).getDescription().contains("condicionada")){
					message = message.replace("@afeccion", "🟠 " + IncidentEntitiesManager.getInstance().getImsRoadImpact(incidentReport.getRoadImpactId()).getDescription());
				}else{
					message = message.replace("@afeccion", "Sin información de la circulación");
				}
			} else {
				message = message.replace("@afeccion", "Sin información de la circulación");
			}
					
		    message = message.replace("@fecha", "📅 " + fecha(incidentReport.getGeneratedAt()));
		
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

    String fecha(Long timestamp) {

        ZonedDateTime dateTime = Instant.ofEpochMilli(timestamp)
                                        .atZone(ZoneId.of("Europe/Madrid"));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss", new Locale("es", "ES"));

        String formattedDate = dateTime.format(formatter);

        return formattedDate;
    }
}
