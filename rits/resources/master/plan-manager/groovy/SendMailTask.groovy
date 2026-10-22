import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.text.SimpleDateFormat;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.exception.ExceptionUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revenga.rits.back.data.core.model.Alarm;
import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.SystemParam;
import com.revenga.rits.back.data.core.model.SystemParamValue;
import com.revenga.rits.back.data.core.model.command.Command;
import com.revenga.rits.back.data.core.util.MailUtil;
import com.revenga.rits.back.entities.lib.repository.SystemParamValueRepository;
import com.revenga.rits.back.plan.manager.helper.GroovyHelper;


class SendMailTask {

	org.apache.logging.log4j.Logger log;
	
	static final Long TASK_TYPE_PARAM_ADDRESS_TO = 1L;
	static final Long TASK_TYPE_PARAM_ADDRESS_CC = 2L;
	static final Long TASK_TYPE_PARAM_ADDRESS_BCC = 3L;
	static final Long TASK_TYPE_PARAM_SUBJECT = 4L;
	static final Long TASK_TYPE_PARAM_BODY = 5L;

	SendMailTask(org.apache.logging.log4j.Logger log) {

		this.log = log;
	}

	boolean process(String value) {	
		try {
			
			List<List<Pair<Alarm, Element>>> array_total = GroovyHelper.initialize();
			int totales = GroovyHelper.getTotales(array_total);

			GroovyHelper.PropertiesReader.initialize();

			SystemParamValue systemParamValueSmtpConfig = (new SystemParamValueRepository(false))
				.get(Long.valueOf(SystemParam.SYSTEM_PARAM_SMTP_CONFIG), false);
			
			if (systemParamValueSmtpConfig != null && systemParamValueSmtpConfig.getValue() != null) {
				Map<String, Object> mapProps = (new ObjectMapper()).readValue(systemParamValueSmtpConfig.getValue(), new TypeReference<HashMap<String, Object>>() {});
				
				if (!MapUtils.isEmpty(mapProps)) {
					
					Properties props = new Properties();
					
					for (Map.Entry<String, Object> entry : mapProps.entrySet()) {
						
						props.put(entry.getKey(), entry.getValue());
					}
					
					if(value == null){
						log.debug("Los destinatarios del correo están vacíos");
						return false;
					}
					
					

					String body = '<!DOCTYPE html>\n' +
									'<html>\n' +
									'<body>\n';
													
					body += '<h2>NÚMERO DE ALARMAS ACTIVAS TOTALES: ' + totales + '</h2>\n';				
					body += '<table>\n';
					body += GroovyHelper.PropertiesReader.getBody("RIO VERDE", array_total.get(0));
					body += GroovyHelper.PropertiesReader.getBody("MONTEMAYOR", array_total.get(1));
					body += GroovyHelper.PropertiesReader.getBody("STA MARIA I", array_total.get(2));
					body += GroovyHelper.PropertiesReader.getBody("STA MARIA II", array_total.get(3));
					body += GroovyHelper.PropertiesReader.getBody("CASARES", array_total.get(4));
					body += GroovyHelper.PropertiesReader.getBody("COROMINAS", array_total.get(5));
					body += GroovyHelper.PropertiesReader.getBody("LA QUINTA", array_total.get(6));
					body += GroovyHelper.PropertiesReader.getBody("ESTEPONA", array_total.get(7));
					body += GroovyHelper.PropertiesReader.getBody("CALAHONDA", array_total.get(8));					
					body += GroovyHelper.PropertiesReader.getBody("NAGÜELES", array_total.get(9));

					body += '</table>\n' +															
					    	'</body>\n' + 
							'</html>';

					SimpleDateFormat subjectDate = new SimpleDateFormat("dd/MM/yyyy");
					SimpleDateFormat subjectHour = new SimpleDateFormat("HH:mm");
					
					
					ObjectMapper  mapper = new ObjectMapper();
					if (!value.startsWith("\"{")){
						value = "{\"addressTO\": \"" + value  + "\"}"
					}else{
						value = value.substring(1,value.length()-1);
					}
					Object mail = mapper.readValue(value, GroovyHelper.Mail.class);
					if (mail.getSubject() == null){
						String subject_aux = "Listado de alarmas activas " + subjectDate.format(new Date(System.currentTimeMillis()));
						mail.setSubject("Listado de alarmas activas " + subjectDate.format(new Date(System.currentTimeMillis())))
					}
					if (mail.getSubject().contains("%DATE%")){
						String date = mail.getSubject().replace("%DATE%", subjectDate.format(new Date(System.currentTimeMillis())))
						mail.setSubject(date)
					}
					if (mail.getSubject().contains("%HOUR%")){
						String hora = mail.getSubject().replace("%HOUR%", subjectHour.format(new Date(System.currentTimeMillis())))
						mail.setSubject(hora)
					}
					
					MailUtil.send(props, mail.getAddressTO(), mail.getAddressCC(), mail.getAddressBCC(), mail.getSubject(), body);
					//MailUtil.send(props, addressTO, addressCC, addressBCC, subject, body);
				}
	
			} else {
	
				log.error("Not fount system param smtp to send task mail");
			}
		}
		catch (Exception e) {
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}

		return true;
	}

	
}