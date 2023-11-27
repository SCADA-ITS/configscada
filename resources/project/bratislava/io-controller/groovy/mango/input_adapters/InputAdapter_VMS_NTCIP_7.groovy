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
	
	static final Long PARAM_CONFIG_JSONCONFIG = 4L;
	static final Long PARAM_TYPE_MEASURE = 2L;

	static final Integer POS_TEXT_1 = 6;
	static final Integer POS_TEXT_2 = 10;

	InputAdapter_VMS(org.apache.logging.log4j.Logger log) {

		this.log = log;
	}
	
	boolean content(Element element, String value, List<ElementValue> elementValues) {
		
		if(value == null || value.equals("")) {
			ElementValue elementValue = new ElementValue();
			elementValue.setElementTypeId(element.getElementTypeId());
			elementValue.setElementTypeParamId(3L);
			elementValue.setParamTypeId(2L);
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
			elementValue.setElementTypeParamId(3L);
			elementValue.setParamTypeId(2L);
			elementValue.setElementId(element.getId());
			elementValue.setValue(result);
			elementValues.add(elementValue);
			return true;
		}
	}
	
	public String construirVMS(String content, Element element) {
		List<String> messages = Arrays.asList(content.replace("[np]", "&").split("&"));
		List<String> graphics = new ArrayList();
		List<String> texts = new ArrayList();
		List<Zone> zones = new ArrayList();
		List<String> cad_character = new ArrayList();
		List<String> cad_ascii = new ArrayList();
		Zone zone = null;
		Integer x_zone;
		Integer y_zone;
		int alternance, flashing_on, flashing_off, num_lineas, zona_text;
		String zone_type, aux_grap;
		String character = "↑,°,Á,Â,Ä,Ç,É,Ë,Í,Î,Ó,Ô,Ö,×,Ú,Ü,Ý,ß,á,â,ä,ç,é,ë,í,î,ó,ô,ö,÷,ú,ü,ý,Ă,ă,Ą,ą,Ć,ć,Č,č,Ď,ď,Đ,đ,Ę,Ĺ,ĺ,Ľ,ľ,Ł,ł,Ń,Ň,ň,Ő,ő,Ŕ,ŕ,Ř,ř,Ś,ś,Ş,ş,Š,š,Ţ,ţ,Ť,ť,Ů,ů,Ű,ű,Ź,ź,Ż,ż,ž,ˇ,˘,˙,˛,˜,˝";
	    String ascii = "15,B0,C1,C2,C4,C7,C9,CB,CD,CE,D3,D4,D6,D7,DA,DC,DD,DF,E1,E2,E4,E7,E9,EB,ED,EE,F3,F4,F6,F7,FA,FC,FD,102,103,104,105,106,107,10C,10D,10E,10F,110,111,118,139,13A,13D,13E,141,142,143,147,148,150,151,154,155,158,159,15A,15B,15E,15F,160,161,162,163,164,165,16E,16F,170,171,179,17A,17B,17C,17D,17E,2C7,2D8,2D9,2DB,2DC,2DD";
		cad_character = character.split(",");
		cad_ascii = ascii.split(",");		
		//graphics.add("");
		//graphics.add("");
		for (int i = 0; i < 4; i++){
			texts.add("")
		}
		for (String message : messages) {
			message = sustituirCaracter(message,cad_character,cad_ascii);
			messages = message.split("]|\\[");
			messages.removeAll(Arrays.asList("", "fo1"));
			
			for(int i = 0; i < messages.size(); i++){
				if (messages[i].charAt(0) == "g"){
					graphics.add(messages[i].substring(messages[i].indexOf("g") + 1, messages[i].indexOf(",")))
					//graphics.set(i, messages[i].substring(messages[i].indexOf("g") + 1, messages[i].indexOf(",")))
				}else if(messages[i].charAt(0) == "t"){
					List <String> aux_text = new ArrayList();
					aux_text = messages[i].split(",");
					x_zone = Integer.parseInt(aux_text[0].substring(aux_text[0].indexOf("r")+1))
					y_zone = Integer.parseInt(aux_text[1])
					if(x_zone == 24 && y_zone == 6){
						texts.set(0,messages[i+1])
					}else if(x_zone == 24 && y_zone == 27){
						texts.set(1,messages[i+1])
					}else if(x_zone == 48 && y_zone == 6){
						texts.set(2,messages[i+1])
					}else if(x_zone == 48 && y_zone == 27){
						texts.set(3,messages[i+1])
					}
				}
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
			for(int n_zone = 1; n_zone < num_zones + 1; n_zone++){

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
						if(n_zone <= 4){
							zone.fillText(texts, num_lineas, n_zone-3);
						}else if(n_zone > 4){
							zone.fillText(texts, num_lineas, n_zone-3);
						}
						else{
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
		List<Graphic> graphics;
		List<Text> texts;
		
		public Zone(Integer zone, Integer alternance_ms, Integer flashing_on_ms, Integer flashing_off_ms){
			this.zone = zone;
			this.alternance_ms = alternance_ms;
			this.flashing_on_ms = flashing_on_ms;
			this.flashing_off_ms = flashing_off_ms;
			graphics = new ArrayList();
			texts = new ArrayList();
		}
		
		public void fillText(List<String> messages, Integer numLineas, Integer n_zone) {
			if (messages.get(n_zone)){

				List<String> values = Arrays.asList(messages.get(n_zone).split("#"));
				List<String> alternances;

				String aux_value;

				Text textLine;
				for(int contador = 1; contador <= numLineas; contador++) {
					if(contador <= values.size() && values.get(contador - 1) != null) {
						aux_value = values.get(contador - 1);
					}else {
						aux_value = "";
					}
					if (n_zone <= 1 && aux_value.charAt(0) == "-" ){
						textLine = new Text(contador, aux_value, null, "#FF0000");
					}else{
						textLine = new Text(contador, aux_value, null, "#FFFFFF");
					}
					texts.add(textLine);
					}
			}else{
				Text textLine;
				textLine = new Text(1, "", null, "#FFFFFF");
				texts.add(textLine);

			}
		}

	
		public void fillGraphic(List<String> message, Element element, int numZone) {
			Integer values = 0;
			Integer alternances = 0;
			values = obtenerGraphic(element,Integer.parseInt(message.get(numZone - 1)),numZone);
			Graphic graphic = new Graphic(1, values, alternances);
			graphics.add(graphic);
			
		}
		
		public int obtenerGraphic(Element element, Integer pictoValue, Integer numZone){
		
			ElementValue elementDataJson = EntitiesManager.getInstance().getElementValueConfig(element, PARAM_CONFIG_JSONCONFIG);
            def jsonObject = new JsonSlurper().parseText(elementDataJson.getValue());
			Long group = jsonObject.vms_group_id[0];
			List<VmsGraphicGraphicGroupValue> vmsGraphicGraphicGroupValue = EntitiesManager.getInstance().getByGroup(group);
			for(VmsGraphicGraphicGroupValue groupValue : vmsGraphicGraphicGroupValue){
				if(pictoValue.equals(Integer.parseInt(groupValue.getValue()))){
					return groupValue.getGraphicId();
				}
			}
		}

	}
	String sustituirCaracter(String textValue, List<String> cad_character, List<String> cad_ascii){
		String aux = "";
		for(int z = 0; z < cad_ascii.size() ; z++){
			aux = "[hc" + cad_ascii[z] + "]";
			if (textValue.indexOf(aux) != -1){
				textValue = textValue.replace(aux, cad_character[z]);
			}
		}
		return textValue;	
    }
}
