
import java.time.Instant;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revenga.rits.back.data.core.model.AlarmConfig;
import com.revenga.rits.back.data.core.model.AlarmFiltered;
import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.command.SetAlarmsFilteredCommand;
import com.revenga.rits.back.data.core.model.command.SetAlarmsFilteredCommand.Action;
import com.revenga.rits.back.plan.manager.service.EntitiesManager;

class ActivateAlarmsDai {
	
	GroovyShell shell;
	org.apache.logging.log4j.Logger log;
		
	ActivateAlarmsDai(org.apache.logging.log4j.Logger log) {

		shell = new GroovyShell();
		this.log = log;
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	static class JsonData {

		@JsonProperty("elementList")
		List<Element> elements;

		@JsonProperty("alarmConfigList")
		List<AlarmConfig> alarmConfigs;

		List<Element> getElements() {
	        return elements;
	    }

	    void setElements(List<Element> elements) {
	        this.elements = elements;
	    }

	    List<AlarmConfig> getAlarmConfigs() {
	        return alarmConfigs;
	    }

	    void setAlarmConfigs(List<AlarmConfig> alarmConfigs) {
	        this.alarmConfigs = alarmConfigs;
	    }
	}

	boolean process(String value) {
		
		ObjectMapper mapper = new ObjectMapper();
		
		JsonData jsonData = new JsonData();
		
		try {
			
			jsonData = mapper.readValue(value, JsonData.class);

			List<Element> elements = jsonData.getElements();
			List<AlarmConfig> alarmConfigs = jsonData.getAlarmConfigs();

			SetAlarmsFilteredCommand alarmsFilteredCommand = new SetAlarmsFilteredCommand();

			for (Element element : elements) {

				for (AlarmConfig alarmConfig : alarmConfigs) {

					AlarmFiltered alarmFiltered = new AlarmFiltered();
					alarmFiltered.setAlarmConfigId(alarmConfig.getId());
					alarmFiltered.setElementTypeId(element.getElementTypeId());
					alarmFiltered.setElementId(element.getId());
						
					alarmsFilteredCommand.addAlarmFilteredAction(alarmFiltered, Action.FILTER_OFF);
				}
			}

			EntitiesManager.getInstance().sendCommand(alarmsFilteredCommand);

			return true;

		} catch (JsonProcessingException e) {
			
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			
		}
		
		return false;
	}
}