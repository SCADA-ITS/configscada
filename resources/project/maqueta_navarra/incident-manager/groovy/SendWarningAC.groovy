import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revenga.rits.back.data.core.model.command.SignallingCommand;
import com.revenga.rits.back.data.core.model.ImsIncidentReport;
import com.revenga.rits.back.data.core.model.ImsIncidentTypeTask;
import com.revenga.rits.back.data.core.model.ImsIncidentTypeTaskValue;
import com.revenga.rits.back.data.core.model.command.Command;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

class SendMailTask {

	org.apache.logging.log4j.Logger log;

	SendMailTask(org.apache.logging.log4j.Logger log) {

		this.log = log;
	}

	List<Command> process(ImsIncidentReport incidentReport, ImsIncidentTypeTask incidentTypeTask, List<ImsIncidentTypeTaskValue> values) {

		List<Command> commands = null;
		
		try {
		
			sendToTelegram("Se ha producido una incidencia de tipo Asistencia en Carretera en la N-121A y ha sido confirmada por parte del personal de mantenimiento");			
							
			if (commands == null) {
				commands = new ArrayList<>();
			}
			
			commands.add(new SignallingCommand());
				
		}
		catch (Exception e) {
			
		}

		return commands;
	}
	
	public static void sendToTelegram(String message) {
    	String urlString = "https://api.telegram.org/bot%s/sendMessage?chat_id=%s&text=%s";

    	//Add Telegram token 
    	String apiToken = "6399005960:AAFKJ22L5NKTjYeg99JyLIkv3DaKqZ6sBYI";
  
    	//Add chatId 
    	String chatId = "-4158773189";

    	urlString = String.format(urlString, apiToken, chatId, message);

    	try {
    	    URL url = new URL(urlString);
    	    URLConnection conn = url.openConnection();
   	     	    InputStream is = new BufferedInputStream(conn.getInputStream());
   	 	} catch (IOException e) {
        		e.printStackTrace();
    	}
	}
}
