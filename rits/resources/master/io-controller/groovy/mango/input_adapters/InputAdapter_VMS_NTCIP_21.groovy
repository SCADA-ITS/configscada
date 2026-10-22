import java.util.List;
import java.util.ArrayList;
import java.util.Properties;

import groovy.json.*;

import java.io.FileReader;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.math.NumberUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.ElementValue;
import com.revenga.rits.back.data.core.model.ElementTypeParam;
import com.revenga.rits.back.data.core.model.VmsGraphicGraphicGroupValue;
import com.revenga.rits.back.data.core.util.ResourcesUtil;
import com.revenga.rits.back.io.controller.service.EntitiesManager;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;


/**
 *
 * InputAdapter_VMS.groovy: Save the measurement with the current signaling value of the panel
 *
 */
class InputAdapter_VMS {
	org.apache.logging.log4j.Logger log;
	
	final String GRAPHIC = "graphic";
	final String TEXT = "text";
	final String TEXT_COLOR = "#FFFFFF";
	
	static final Long PARAM_CONFIG_JSONCONFIG = 4L;
	static final Long PARAM_MEASURE_DATAJSON = 3L;
	static final Long PARAM_TYPE_MEASURE = 2L;
	
	
	InputAdapter_VMS(org.apache.logging.log4j.Logger log) {

		this.log = log;
	}
	
	boolean content(Element element, String value, List<ElementValue> elementValues) {		
		if(value == null || value.equals("")) {
			ElementValue elementValue = new ElementValue();
			elementValue.setElementTypeId(element.getElementTypeId());
			elementValue.setElementTypeParamId(PARAM_MEASURE_DATAJSON);
			elementValue.setParamTypeId(PARAM_TYPE_MEASURE);
			elementValue.setElementId(element.getId());
			elementValue.setValue("[]");
			elementValues.add(elementValue);
			return true;
		}
		value = URLDecoder.decode(value);
		
		String result = construirVMS(value, element);
		
		if (result == null || result.equals("")){
			return false;
		}else{
			ElementValue elementValue = new ElementValue();
			elementValue.setElementTypeId(element.getElementTypeId());
			elementValue.setElementTypeParamId(PARAM_MEASURE_DATAJSON);
			elementValue.setParamTypeId(PARAM_TYPE_MEASURE);
			elementValue.setElementId(element.getId());
			elementValue.setValue(result);
			elementValues.add(elementValue);
		
			return true;
		}
	}
	
	public String construirVMS(String content, Element element) {
		List<String> messages = Arrays.asList(content.replace("[np]", "&").split("&"));
		List<String> texts = new ArrayList();
		List<Zone> zones = new ArrayList();
		Zone zone = null;
		int alternance, flashing_on, flashing_off, num_lineas;
		String zone_type, aux_grap;

		for (String message : messages) {
			//textos
			if(message.indexOf("[") == 0) {
				
				texts.add(message.substring(message.indexOf("]") + 1).replace("[nl]","#"));
			}else {
				texts.add(message.replace("[nl]","#"));
			}
		}
		
		String propertiesPath = new File(ResourcesUtil.getPath("io-controller/groovy/mango/input_adapters/vms.properties")).toString();
		Properties p = new Properties();
		p.load(new FileReader(propertiesPath));
		
		if(element.getElementSubtypeId() == null || !element.getElementSubtypeId()){
			log.debug("No se encuentra subtipo para el Element:" + element.getElementTypeId() + ":" + element.getId());
			return "";
		}
		
		int num_zones = Integer.parseInt(p.getProperty("vms.subtype_" + String.valueOf(element.getElementSubtypeId()) + ".num_zones"));
		if(num_zones && num_zones != null){
			for(int n_zone = 1; n_zone <= num_zones; n_zone++){
			    alternance = Integer.parseInt(p.getProperty("vms.subtype_" + String.valueOf(element.getElementSubtypeId()) + ".alternance"));
			    flashing_on = Integer.parseInt(p.getProperty("vms.subtype_" + String.valueOf(element.getElementSubtypeId()) + ".flashing_on"));
			    flashing_off = Integer.parseInt(p.getProperty("vms.subtype_" + String.valueOf(element.getElementSubtypeId()) + ".flashing_off"));
			    
			    if(alternance != null && flashing_on != null &&  flashing_off != null){	    
					zone = new Zone(n_zone, alternance, flashing_on, flashing_off);
				}else{
					log.debug("Problema al encontrar alguno de los parametros (alternance, flashing_on, flashing_off), para el Element:" + element.getElementTypeId() + ":" + element.getId());
					return "";
				}
				
				zone_type = p.getProperty("vms.subtype_" + String.valueOf(element.getElementSubtypeId()) + ".typezone_" + String.valueOf(n_zone));
				if(zone_type && zone_type != null){
					if(zone_type.equals(GRAPHIC)){
						zone.fillGraphic(graphics, element, n_zone);	
					}else if(zone_type.equals(TEXT)){
						num_lineas = Integer.parseInt(p.getProperty("vms.subtype_" + String.valueOf(element.getElementSubtypeId()) + ".zone_" + String.valueOf(n_zone) + "." + TEXT + ".num_lines"));
						if(num_lineas && num_lineas != null){
							zone.fillText(texts, num_lineas);
						}else{
							log.debug("No se encuenta el numero de lineas de la zona de texto " + num_zone + " para el Element:" + element.getElementTypeId() + ":" + element.getId());
							return "";
						}
					}else{
						log.debug("Tipo de zona " + zone_type + " DESCONOCIDO para el Element:" + element.getElementTypeId() + ":" + element.getId());
						return "";
					}
				}else{
					log.debug("No se ha encontrado el tipo de zona " + n_zone + " para el Element:" + element.getElementTypeId() + ":" + element.getId());
					return "";
				}
				zones.add(zone);
			}	
		}else{
			log.debug("No se ha encontrado el parametro num_zonas para el Element:" + element.getElementTypeId() + ":" + element.getId());
			return "";
		}
		
		try {
			ObjectMapper Obj = new ObjectMapper();
			String json = Obj.writeValueAsString(zones);
			return json;
		}catch(Exception e) {
			log.debug(e.getMessage());
			return "";
		}
	}
		
	class Graphic{
		Integer id;
		Integer value;
		Integer alternance;
		
		public Graphic(Integer id, Integer value, Integer alternance){
			this.id = id;
			this.value = value;
			this.alternance = alternance;
		}
	}
	
	class Text{
		Integer id;
		String value;
		String alternance;
		String color;
		
		public Text(Integer id, String value, String alternance, String color){
			this.id = id;
			this.value = value;
			this.alternance = alternance;
			this.color = color;
		}
	}
	
	class Zone{
		Integer zone;
		Integer alternance_ms;
		Integer flashing_on_ms;
		Integer flashing_off_ms;
		List<Text> texts;
		List<Graphic> graphics;
		
		public Zone(Integer zone, Integer alternance_ms, Integer flashing_on_ms, Integer flashing_off_ms){
			this.zone = zone;
			this.alternance_ms = alternance_ms;
			this.flashing_on_ms = flashing_on_ms;
			this.flashing_off_ms = flashing_off_ms;
			graphics = new ArrayList();
			texts = new ArrayList();
		}
		
		public void fillText(List<String> messages, Integer numLineas) {
			
			List<String> values = Arrays.asList(messages.get(0).split("#"));
			List<String> alternances;
			if (messages.size() > 1) alternances = Arrays.asList(messages.get(1).split("#"));
			
			String aux_value;
			String aux_alternance;
			
			Text textLine;
			
			for(int contador = 1; contador <= numLineas; contador++) {
				if(contador <= values.size() && values.get(contador - 1) != null) {
					aux_value = values.get(contador - 1);
				}else {
					aux_value = "";
				}
				
				if (messages.size() > 1){
					if(contador <= alternances.size() && alternances.get(contador - 1) != null) {
						aux_alternance = alternances.get(contador - 1);
					}else {
						aux_alternance = "";
					}
					textLine = new Text(contador, aux_value, aux_alternance, TEXT_COLOR);
				}else{
					textLine = new Text(contador, aux_value, null, TEXT_COLOR);
				}
			
				texts.add(textLine);
			}
		}
	
		public void fillGraphic(List<String> message, Element element, int numZone) {
			
			Integer values = 0;
			Integer alternances = 0;

			
			values = obtenerGraphic(element,Integer.parseInt(message.get(0)),numZone);
			
			
			if (message.size() > 1) {
				alternances = obtenerGraphic(element,Integer.parseInt(message.get(1)),numZone);
			}
			
			Graphic graphic = new Graphic(1, values, alternances);
			graphics.add(graphic);
		
		}
		
		public int obtenerGraphic(Element element, Integer pictoValue, Integer numZone){
		
			ElementValue elementDataJson = EntitiesManager.getInstance().getElementValueConfig(element, PARAM_CONFIG_JSONCONFIG);
			
            def jsonObject = new JsonSlurper().parseText(elementDataJson.getValue());
			Long group = jsonObject.vms_group_id[numZone - 1];
			
			List<VmsGraphicGraphicGroupValue> vmsGraphicGraphicGroupValue = EntitiesManager.getInstance().getByGroup(group);
			for(VmsGraphicGraphicGroupValue groupValue : vmsGraphicGraphicGroupValue){
				if(pictoValue.equals(Integer.parseInt(groupValue.getValue()))){
					return groupValue.getGraphicId();
				}
			}
		}
	}
}
