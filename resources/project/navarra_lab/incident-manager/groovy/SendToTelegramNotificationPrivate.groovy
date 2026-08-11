import java.util.List;

import org.apache.logging.log4j.Logger;

import com.revenga.rits.back.data.core.model.Location;
import com.revenga.rits.back.data.core.model.Stretch;
import com.revenga.rits.back.data.core.model.command.SignallingCommand;
import com.revenga.rits.back.data.core.model.ImsIncidentReport;
import com.revenga.rits.back.data.core.model.ImsIncidentTypeTask;
import com.revenga.rits.back.data.core.model.ImsIncidentTypeTaskValue;
import com.revenga.rits.back.data.core.model.SystemParam;
import com.revenga.rits.back.data.core.model.SystemParamValue;
import com.revenga.rits.back.data.core.model.command.Command;
import com.revenga.rits.back.data.core.util.TelegramUtil;
import com.revenga.rits.back.entities.lib.repository.SystemParamValueRepository;
import com.revenga.rits.back.incident.manager.service.IncidentEntitiesManager;
import com.revenga.rits.back.incident.manager.service.EntitiesManager;

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

    List<Command> process(ImsIncidentReport incidentReport, ImsIncidentTypeTask incidentTypeTask, List<ImsIncidentTypeTaskValue> values) {
		
        List<Command> commands = null;
        String telegramConfig = getTelegramConfig();
        String message = null;
		boolean respuesta = false;
        
        if (incidentReport.getAffectionStretchId() != null) {

			Location imsIncidentLocation = EntitiesManager.getInstance().getLocation(incidentReport.getLocationId());
			Stretch imsIncidentStretch = IncidentEntitiesManager.getInstance().getStretch(incidentReport.getAffectionStretchId());
	
			message = IncidentEntitiesManager.getInstance().getIncidentTypeTaskValue(values, TASK_TYPE_PARAM_MESSAGE);
								
			if (incidentReport.getAlias() != null) 
				message = message.replace("@alias", incidentReport.getAlias());
			else
				message = message.replace("@alias", "");
			if (incidentReport.getIncidentLevelId() == 2) 
				message = message.replace("@tipo", "⚠️ " + IncidentEntitiesManager.getInstance().getImsIncidentType(incidentReport.getIncidentTypeId()).getDescription());
			else				
				message = message.replace("@tipo", IncidentEntitiesManager.getInstance().getImsIncidentType(incidentReport.getIncidentTypeId()).getDescription());
			message = message.replace("@localizacion", "🚩 " + imsIncidentStretch.getAlias() + " - " + imsIncidentLocation.getAlias());
			
			if (IncidentEntitiesManager.getInstance().getImsRoadImpact(incidentReport.getRoadImpactId()) != null){
				if (IncidentEntitiesManager.getInstance().getImsRoadImpact(incidentReport.getRoadImpactId()).getDescription().contains("interrumpida")){
					message = message.replace("@afeccion", "⚫️ " + IncidentEntitiesManager.getInstance().getImsRoadImpact(incidentReport.getRoadImpactId()).getDescription());
				}else if (IncidentEntitiesManager.getInstance().getImsRoadImpact(incidentReport.getRoadImpactId()).getDescription().contains("difícil")){
					message = message.replace("@afeccion", "🔴 " + IncidentEntitiesManager.getInstance().getImsRoadImpact(incidentReport.getRoadImpactId()).getDescription());
				}else if (IncidentEntitiesManager.getInstance().getImsRoadImpact(incidentReport.getRoadImpactId()).getDescription().contains("irregular")){
					message = message.replace("@afeccion", "🟡 " + IncidentEntitiesManager.getInstance().getImsRoadImpact(incidentReport.getRoadImpactId()).getDescription());
				}else if (IncidentEntitiesManager.getInstance().getImsRoadImpact(incidentReport.getRoadImpactId()).getDescription().contains("condicionada")){
					message = message.replace("@afeccion", "🟢 " + IncidentEntitiesManager.getInstance().getImsRoadImpact(incidentReport.getRoadImpactId()).getDescription());
				}else{
					message = message.replace("@afeccion", "Sin información de la circulación");
				}
			} else {
				message = message.replace("@afeccion", "Sin información de la circulación");
			}
					
		    message = message.replace("@fecha", "📅 " + fecha(incidentReport.getGeneratedAt()));
				
			if (telegramConfig != null && message != null) {
	        
        		try {
		            respuesta = TelegramUtil.sendConfigured(telegramConfig, "private", message);
		
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

    String getTelegramConfig() {

        try {
            SystemParamValue telegramConfig = new SystemParamValueRepository(false)
                    .get(Long.valueOf(SystemParam.SYSTEM_PARAM_TELEGRAM_CONFIG), false);
            return telegramConfig != null ? telegramConfig.getValue() : null;
        } catch (Exception e) {
            log.error("No se ha podido cargar la configuracion de Telegram", e);
            return null;
        }
    }

    String fecha(Long timestamp) {

        ZonedDateTime dateTime = Instant.ofEpochMilli(timestamp)
                                        .atZone(ZoneId.of("Europe/Madrid"));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss", new Locale("es", "ES"));

        String formattedDate = dateTime.format(formatter);

        return formattedDate;
    }
}
