// Uncomment following line for groovy file
//Rpackage config.groovy.backoffice.navarra.command;
// or Uncomment following line for java file
package com.revenga.rits.front.openits.backoffice.groovy.command;

import com.revenga.rits.front.openits.backoffice.groovy.AbstractBackOfficeCommand;
import com.revenga.rits.front.openits.backoffice.model.Row;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class ConfirmCommand extends AbstractBackOfficeCommand {

	public ConfirmCommand(org.apache.logging.log4j.Logger log) {
		super(log);
	}

	@Override
	public boolean onExecute() {
		
		Row oldRow = this.getOldRow();
		Row newRow = this.getNewRow();
		
		String matricula = getFieldValue(newRow, "matricula");
		String fecha = getFieldValue(newRow, "fecha");

		sendToTelegram("Confirmada sanción con fecha " + fecha + " para vehículo con matrícula " + matricula);
		
		if (oldRow != null && newRow != null) {

			setFieldValue(newRow, "valoracion", "true");
			
		}

		return true;
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