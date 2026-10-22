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
	static final String INTERMITENCE_TAG = "[flt";
	static final String END_INTERMITENCE_TAG = "[/fl]";
	static final String TEXT_ALIGN_CENTER = "[jl3]";
	static final String TEXT_ALIGN_LEFT = "[jl2]";
	static final String TEXT_ALIGN_RIGHT = "[jl4]";
	
	static final Long PARAM_CONFIG_JSONCONFIG = 4L;
	static final Long PARAM_MEASURE_DATAJSON = 3L;
	static final Long PARAM_MEASURE_ENABLED = 7L;
	static final Long PARAM_TYPE_MEASURE = 2L;
	static final String JSON_EXCESO_GALIBO = "[{\"zone\":1,\"alternance_ms\":5000, \"flashing_on_ms\":0, \"flashing_off_ms\":0,\"graphics\":[{\"id\":1,\"value\":23}]},{\"zone\":2,\"alternance_ms\":5000, \"flashing_on_ms\":0, \"flashing_off_ms\":0,\"align\": \"left\",\"texts\":[{\"id\":1,\"value\":\"PRECAUCION\",\"alternance\":\"\",\"color\":\"#E2943A\"},{\"id\":2,\"value\":\"EXCESO DE\",\"alternance\":\"\",\"color\":\"#E2943A\"},{\"id\":3,\"value\":\"GALIBO\",\"alternance\":\"\",\"color\":\"#E2943A\"}]}]";
	
	InputAdapter_VMS(org.apache.logging.log4j.Logger log) {

		this.log = log;
	}
	
	boolean content(Element element, String value, List<ElementValue> elementValues) {
		boolean intermitence = false;
		String result = "";
		
		ElementValue enableGalibo = EntitiesManager.getInstance().getElementValue(element, PARAM_MEASURE_ENABLED, PARAM_TYPE_MEASURE);
		
		if(enableGalibo != null && enableGalibo.getValue().equals("true")){
			result = JSON_EXCESO_GALIBO;
		}else{
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
			value = (new String(value.getBytes(), "ISO-8859-1")).replace("ï¿½", "Ñ");
	
			if(value.indexOf(INTERMITENCE_TAG) != -1){
				value = value.replace(INTERMITENCE_TAG, "").replace(END_INTERMITENCE_TAG, "");
				intermitence = true;
			}
			
			result = construirVMS(value, element, intermitence);
		}
		
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

	public String construirVMS(String content, Element element, boolean intermitence) {
		List<String> messages = Arrays.asList(content.replace("[tr70,1,0,0]","").replace("[np]", "&").split("&"));
		List<String> graphics = new ArrayList();
		List<String> texts = new ArrayList();
		List<Zone> zones = new ArrayList();
		Zone zone = null;
		int flashing_on, flashing_off, num_lineas, alternance;
		String zone_type, aux_grap, color, align;
		String flashon, flashoff;
		for (String message : messages) {
			//textos
			if (intermitence) {
				flashon = message.substring(message.indexOf(""), message.indexOf("o"));
				flashoff = message.substring(message.indexOf("o") + 1, message.indexOf("]"));
			}
			
			if (message.indexOf("g") == -1){
				
				if(message.contains(TEXT_ALIGN_LEFT)){
					message = message.replace(TEXT_ALIGN_LEFT,"");
					align = "left";

				}else if(message.contains(TEXT_ALIGN_CENTER)){
					message = message.replace(TEXT_ALIGN_CENTER,"");
					align = "center";

				}else if(message.contains(TEXT_ALIGN_RIGHT)){
					message = message.replace(TEXT_ALIGN_RIGHT,"");
					align = "right";				
				}
				if (intermitence){
					message = message.substring(message.indexOf("]") + 1) - message
				}			
				texts.add(message.substring(message.indexOf("")).replace("[nl8]","€"));
			}else{

				if(message.contains(TEXT_ALIGN_LEFT)){
					message = message.replace(TEXT_ALIGN_LEFT,"");
					align = "left";

				}else if(message.contains(TEXT_ALIGN_CENTER)){
					message = message.replace(TEXT_ALIGN_CENTER,"");
					align = "center";

				}else if(message.contains(TEXT_ALIGN_RIGHT)){
					message = message.replace(TEXT_ALIGN_RIGHT,"");
					align = "right";				
				}
				if (intermitence){
					message = message.substring(message.indexOf("]") + 1) - message
				}
				
				aux_grap = message.substring(message.indexOf("["), message.indexOf("]") + 1);
				message = message - aux_grap;

				if (intermitence){
					texts.add(message.substring(message.indexOf("]") + 1).replace("[nl8]","€"));
				}else {
					texts.add(message.substring(message.indexOf("")).replace("[nl8]","€"));
				}
			}
			//graficos	
			if(aux_grap != null){

				graphics.add(aux_grap.substring(aux_grap.indexOf("g") + 1, aux_grap.indexOf(",")));
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
				ElementValue alternance_element = EntitiesManager.getInstance().getElementValue(element, PARAM_MEASURE_DATAJSON, PARAM_TYPE_MEASURE);
				
				if (alternance_element.getValue() != "[]" && alternance_element.getValue() != ""){
					
					def jsonObject = new JsonSlurper().parseText(alternance_element.getValue());
					Long alternance_json = jsonObject[n_zone - 1].alternance_ms
			   		alternance = alternance_json;
		
				}else{
					alternance = Integer.parseInt(p.getProperty("vms.subtype_" + String.valueOf(element.getElementSubtypeId()) + ".alternance"));
				}
				color = p.getProperty("vms.subtype_" + String.valueOf(element.getElementSubtypeId()) + ".color");
			    if(intermitence){
				    flashing_on = Integer.parseInt(flashon) * 100;
				    flashing_off = Integer.parseInt(flashoff) * 100;
			    }else{
				    flashing_on = 0;
				    flashing_off = 0;
				}
			    if(alternance != null && flashing_on != null &&  flashing_off != null){	    
					zone = new Zone(n_zone, alternance, flashing_on, flashing_off, align);
				}else{
					log.debug("Problema al encontrar alguno de los parametros (alternance, flashing_on, flashing_off), para el Element:" + element.getElementTypeId() + ":" + element.getId());
					return "";
				}
				
				zone_type = p.getProperty("vms.subtype_" + String.valueOf(element.getElementSubtypeId()) + ".typezone_" + String.valueOf(n_zone));
				if(zone_type && zone_type != null){
					if(zone_type.equals(GRAPHIC) && !graphics.isEmpty()){
						zone.fillGraphic(graphics, element, n_zone);	
					}else if(zone_type.equals(TEXT)){
						num_lineas = Integer.parseInt(p.getProperty("vms.subtype_" + String.valueOf(element.getElementSubtypeId()) + ".zone_" + String.valueOf(n_zone) + "." + TEXT + ".num_lines"));
						if(num_lineas && num_lineas != null){
							zone.fillText(texts, num_lineas, color);
						}else{
							log.debug("No se encuenta el numero de lineas de la zona de texto " + num_zone + " para el Element:" + element.getElementTypeId() + ":" + element.getId());
							return "";
						}
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

	boolean defaultPagehOnTime(Element element, String value, List<ElementValue> elementValues) {
		
		String result;
		String[] value_aux = value.split('\\.0')
		ElementValue elementDataJson = EntitiesManager.getInstance().getElementValue(element, PARAM_MEASURE_DATAJSON, PARAM_TYPE_MEASURE);

		if (elementDataJson.value != "[]" && elementDataJson.value != "") {
			result = insertAlternance(value_aux[0], element);
		}else{
			result = "[]";
		}
		
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

	public String insertAlternance(String alternance, Element element) {
		int alternance_json = Integer.parseInt(alternance) * 100;
		
		String propertiesPath = new File(ResourcesUtil.getPath("io-controller/groovy/mango/input_adapters/vms.properties")).toString();
		Properties p = new Properties();
		p.load(new FileReader(propertiesPath));
		ElementValue alternance_element = EntitiesManager.getInstance().getElementValue(element, PARAM_MEASURE_DATAJSON, PARAM_TYPE_MEASURE);
		
		int num_zones = Integer.parseInt(p.getProperty("vms.subtype_" + String.valueOf(element.getElementSubtypeId()) + ".num_zones"));

		if(num_zones && num_zones != null && alternance_element.value != null){
			def jsonObject = new JsonSlurper().parseText(alternance_element.getValue());
			for(int n_zone = 1; n_zone <= num_zones; n_zone++){

				if(alternance_element != null) {

					jsonObject[n_zone - 1].alternance_ms = alternance_json;
				}
			}
			try {
				ObjectMapper Obj = new ObjectMapper();
				String json = Obj.writeValueAsString(jsonObject);
				return json;
			}catch(Exception e) {
				log.debug(e.getMessage());
				return "";
			}
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
		String align;
		
		public Zone(Integer zone, Integer alternance_ms, Integer flashing_on_ms, Integer flashing_off_ms, String align){
			this.zone = zone;
			this.alternance_ms = alternance_ms;
			this.flashing_on_ms = flashing_on_ms;
			this.flashing_off_ms = flashing_off_ms;
			graphics = new ArrayList();
			texts = new ArrayList();
			this.align = align;
		}
		
		public void fillText(List<String> messages, Integer numLineas, String color) {
			
			List<String> values = Arrays.asList(messages.get(0).split("€"));
			List<String> alternances;
			if (messages.size() > 1) alternances = Arrays.asList(messages.get(1).split("€"));
			
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
					textLine = new Text(contador, aux_value, aux_alternance, color);
				}else{
					textLine = new Text(contador, aux_value, null, color);
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
