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
import com.revenga.rits.back.data.core.model.SystemParam;
import com.revenga.rits.back.data.core.model.SystemParamValue;
import com.revenga.rits.back.data.core.model.command.Command;
import com.revenga.rits.back.data.core.util.MailUtil;
import com.revenga.rits.back.entities.lib.repository.SystemParamValueRepository;

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

	List<Command> process(ImsIncidentReport incidentReport, ImsIncidentTypeTask incidentTypeTask, List<ImsIncidentTypeTaskValue> values) {

		List<Command> commands = null;
		
		try {
			
			SystemParamValue systemParamValueSmtpConfig = (new SystemParamValueRepository(false))
				.get(Long.valueOf(SystemParam.SYSTEM_PARAM_SMTP_CONFIG), false);
			
			if (systemParamValueSmtpConfig != null && systemParamValueSmtpConfig.getValue() != null) {
				
				Map<String, Object> mapProps = (new ObjectMapper()).readValue(systemParamValueSmtpConfig.getValue(), new TypeReference<HashMap<String, Object>>() {});
				
				if (!MapUtils.isEmpty(mapProps)) {
					
					Properties props = new Properties();
					
					for (Map.Entry<String, Object> entry : mapProps.entrySet()) {
						
						props.put(entry.getKey(), entry.getValue());
					}
					
					String addressTO = getIncidentTypeTaskValue(values, TASK_TYPE_PARAM_ADDRESS_TO);
					String addressCC = getIncidentTypeTaskValue(values, TASK_TYPE_PARAM_ADDRESS_CC);
					String addressBCC = getIncidentTypeTaskValue(values, TASK_TYPE_PARAM_ADDRESS_BCC);
					String subject = getIncidentTypeTaskValue(values, TASK_TYPE_PARAM_SUBJECT);
					String body = getIncidentTypeTaskValue(values, TASK_TYPE_PARAM_BODY);
					
					MailUtil.send(props, addressTO, addressCC, addressBCC, subject, body);
					
					if (commands == null) {
						commands = new ArrayList<>();
					}
					
					commands.add(new SignallingCommand());
				}
	
			} else {
	
				log.error("Not fount system param smtp to send task mail");
			}
		}
		catch (Exception e) {
			
		}

		return commands;
	}
	
	String getIncidentTypeTaskValue(List<ImsIncidentTypeTaskValue> values, Long taskTypeParamId) {
		
		String returnVal = null;
		
		if (!CollectionUtils.isEmpty(values)) {
			
			for (ImsIncidentTypeTaskValue value : values) {
				
				if (value.getTaskTypeParamId().equals(taskTypeParamId) && !StringUtils.isEmpty(value.getValue())) {

					returnVal = value.getValue();
						
					break;
				}
			}
		}
		
		return returnVal;
	}
}
