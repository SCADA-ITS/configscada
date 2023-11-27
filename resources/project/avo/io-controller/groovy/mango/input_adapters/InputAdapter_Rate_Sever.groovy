import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.ElementValue;
import com.revenga.rits.back.io.controller.service.EntitiesManager;

import com.revenga.rits.back.data.core.model.command.SignallingCommand;
import com.revenga.rits.back.data.core.model.command.SignallingCommand.SignallingParam;
import com.revenga.rits.back.data.core.model.command.Command;

import groovy.json.JsonSlurper;
import java.time.LocalDateTime;
import com.google.gson.*;

/**
 *
 * InputAdapter_VMS.groovy: Save the measurement with the current signaling value of the panel
 *
 */
class InputAdapter_VMS {
	org.apache.logging.log4j.Logger log;
	
	static final Long PARAM_MEASURE_DATAJSON = 1L;
	static final Long PARAM_TYPE_MEASURE = 2L;
	static final Integer PARAM_ID_1 = 1;
	static final Integer DATA_TYPE_ALPHANUMERIC = 2;
	static final Long SIGNALLING_PMV_COMMNAND = 2L;
	static final Long ELEMENT_TYPE_PMV = 71L;
	
	InputAdapter_VMS(org.apache.logging.log4j.Logger log) {

		this.log = log;
	}
	
	boolean data_json(Element element, String value, List<ElementValue> elementValues) {	
		List<String> panelCodes = ["101","102","104","1207","201","202","205","206","209","110"];
		List<Long> panelIds = [394L,393L,392L,391L,396L,395L,390L,397L,398L,389L];
		List<String> ratesNow = new ArrayList();
		
		def valueOldJson = "";
		
		ElementValue ratesOld = EntitiesManager.getInstance().getElementValue(element, PARAM_MEASURE_DATAJSON, PARAM_TYPE_MEASURE);
		
		if(ratesOld != null && ratesOld.getValue() != null && ratesOld.getValue() != ""){
			valueOldJson = new JsonSlurper().parseText(ratesOld.getValue());
		}	
		
		def valueNowJson = new JsonSlurper().parseText(value);	
		
		int minute = LocalDateTime.now().getMinute() + LocalDateTime.now().getHour()*60;
		
		log.debug("TARIFICADOR - Minuto actual tarifas: " + minute);
		valueNowJson.each{ item ->
			if(Integer.parseInt(item.Fecha.split("-")[0]) == LocalDateTime.now().getDayOfMonth() && 
				minute > (Integer.parseInt(item.Minuto_de_inicio)) && minute < Integer.parseInt(item.Minuto_de_fin)){
				log.debug("TARIFICADOR - Tarifa vigente para el elemento " + item.Codigo_de_portico + ": " + item.Codigo_de_tarifa + " -> " + item.Importe_a_cobrar);
				ratesNow.add(item);
			}
		}
		
		int contador = 0;
		for (String id : panelCodes){
			log.debug("TARIFICADOR - actualizando señalizacion: " + id);
			SignallingCommand signallingCommand = new SignallingCommand();
		
			signallingCommand.setSignallingCommandId(SIGNALLING_PMV_COMMNAND);
			signallingCommand.setElementTypeId(ELEMENT_TYPE_PMV);
			signallingCommand.setElementId(panelIds[contador]);
			
			List<SignallingParam> signallingParams = new ArrayList<>();
			SignallingParam signallingParam = new SignallingParam();

			signallingParam.setId(PARAM_ID_1);
			signallingParam.setType(DATA_TYPE_ALPHANUMERIC);
			signallingParam.setValue(new Gson().toJson(ratesNow.findAll{ it.Codigo_de_Panel == id}));
	
			signallingParams.add(signallingParam);
	
			signallingCommand.setSignallingParams(signallingParams);
			
			log.debug("TARIFICADOR - signallingCommand: " + signallingCommand);
			EntitiesManager.getInstance().sendCommand(signallingCommand);
			contador++;
		}
		
		valueNowJson = new Gson().toJson(ratesNow);	
		ElementValue elementValue = new ElementValue();
		elementValue.setElementTypeId(element.getElementTypeId());
		elementValue.setElementTypeParamId(PARAM_MEASURE_DATAJSON);
		elementValue.setParamTypeId(PARAM_TYPE_MEASURE);
		elementValue.setElementId(element.getId());
		elementValue.setValue(valueNowJson);
		elementValues.add(elementValue);
		
		return true;		
	}
}
