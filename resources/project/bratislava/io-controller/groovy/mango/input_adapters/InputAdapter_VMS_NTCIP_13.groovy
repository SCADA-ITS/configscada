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
class InputAdapter_VMS 
{
	org.apache.logging.log4j.Logger log;
	
	final String GRAPHIC = "graphic";
	final String TEXT = "text";
	final int PICTO = 1;
	final int ZONEGRAPHIC = 1;
	final int NUMZONES = 2;
	
	static final Long PARAM_CONFIG_JSONCONFIG = 4L;
	static final Long PARAM_TYPE_MEASURE = 2L;

	InputAdapter_VMS(org.apache.logging.log4j.Logger log) 
	{
		this.log = log;
	}
	
	boolean content(Element element, String value, List<ElementValue> elementValues) 
	{
		if(value == null || value.equals("")) 
		{
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
		
		if (result == null || result.equals(""))
		{
			return false;
		}
		else
		{
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

	public String construirVMS(String content, Element element)
	{
		String CONTENT_COPY = content;
		int alternance, flashing_on, flashing_off, num_lineas;
		List<String> aux_text = new ArrayList();
		Zone zone = null;
		String aux_grap, zone_type;
		List<Zone> zones = new ArrayList();
		List<String> cad_character = new ArrayList();
		List<String> cad_ascii = new ArrayList();
		String character = "↑,°,Á,Â,Ä,Ç,É,Ë,Í,Î,Ó,Ô,Ö,×,Ú,Ü,Ý,ß,á,â,ä,ç,é,ë,í,î,ó,ô,ö,÷,ú,ü,ý,Ă,ă,Ą,ą,Ć,ć,Č,č,Ď,ď,Đ,đ,Ę,Ĺ,ĺ,Ľ,ľ,Ł,ł,Ń,Ň,ň,Ő,ő,Ŕ,ŕ,Ř,ř,Ś,ś,Ş,ş,Š,š,Ţ,ţ,Ť,ť,Ů,ů,Ű,ű,Ź,ź,Ż,ż,ž,ˇ,˘,˙,˛,˜,˝";
	    String ascii = "15,B0,C1,C2,C4,C7,C9,CB,CD,CE,D3,D4,D6,D7,DA,DC,DD,DF,E1,E2,E4,E7,E9,EB,ED,EE,F3,F4,F6,F7,FA,FC,FD,102,103,104,105,106,107,10C,10D,10E,10F,110,111,118,139,13A,13D,13E,141,142,143,147,148,150,151,154,155,158,159,15A,15B,15E,15F,160,161,162,163,164,165,16E,16F,170,171,179,17A,17B,17C,17D,17E,2C7,2D8,2D9,2DB,2DC,2DD";
		cad_character = character.split(",");
		cad_ascii = ascii.split(",");

		CONTENT_COPY = sustituirCaracter(CONTENT_COPY,cad_character,cad_ascii);
		while(CONTENT_COPY)
		{
			if (CONTENT_COPY.charAt(0) == "[")
			{
				aux_grap = (CONTENT_COPY.substring(CONTENT_COPY.indexOf("["), CONTENT_COPY.indexOf("]") + 1));
				CONTENT_COPY = CONTENT_COPY - aux_grap;
			}
			else
			{
				aux_text.add(CONTENT_COPY);
				CONTENT_COPY = CONTENT_COPY - CONTENT_COPY;
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
		if(num_zones && num_zones != null)
		{
			for(int n_zone = 1; n_zone <= num_zones; n_zone++)
			{
			    alternance = Integer.parseInt(p.getProperty("vms.subtype_" + String.valueOf(element.getElementSubtypeId()) + ".alternance"));
			    flashing_on = Integer.parseInt(p.getProperty("vms.subtype_" + String.valueOf(element.getElementSubtypeId()) + ".flashing_on"));
			    flashing_off = Integer.parseInt(p.getProperty("vms.subtype_" + String.valueOf(element.getElementSubtypeId()) + ".flashing_off"));
			    
			    if(alternance != null && flashing_on != null &&  flashing_off != null)
				{	    
					zone = new Zone(n_zone, alternance, flashing_on, flashing_off);
				}
				else
				{
					log.debug("Problema al encontrar alguno de los parametros (alternance, flashing_on, flashing_off), para el Element:" + element.getElementTypeId() + ":" + element.getId());
					return "";
				}

				zone_type = p.getProperty("vms.subtype_" + String.valueOf(element.getElementSubtypeId()) + ".typezone_" + String.valueOf(n_zone));
				if(zone_type && zone_type != null)
				{
					if(zone_type.equals(TEXT))
					{
						num_lineas = Integer.parseInt(p.getProperty("vms.subtype_" + String.valueOf(element.getElementSubtypeId()) + ".zone_" + String.valueOf(n_zone) + "." + TEXT + ".num_lines"));
						if(num_lineas && num_lineas != null)
						{
							zone.fillText(aux_text, num_lineas, n_zone);
						}	
						else
						{
							log.debug("No se encuenta el numero de lineas de la zona de texto " + num_zone + " para el Element:" + element.getElementTypeId() + ":" + element.getId());
							return "";
						}
					}
					else
					{
						log.debug("Tipo de zona " + zone_type + " DESCONOCIDO para el Element:" + element.getElementTypeId() + ":" + element.getId());
						return "";
					}
				}else{
					log.debug("No se ha encontrado el tipo de zona " + n_zone + " para el Element:" + element.getElementTypeId() + ":" + element.getId());
					return "";
				}
				zones.add(zone);
			}	
		}
		else
		{
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


	class Text
	{
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
	
	class Zone
	{
		Integer zone;
		Integer alternance_ms;
		Integer flashing_on_ms;
		Integer flashing_off_ms;
		List<Text> texts;
		
		
		public Zone(Integer zone, Integer alternance_ms, Integer flashing_on_ms, Integer flashing_off_ms)
		{
			this.zone = zone;
			this.alternance_ms = alternance_ms;
			this.flashing_on_ms = flashing_on_ms;
			this.flashing_off_ms = flashing_off_ms;
		
			texts = new ArrayList();
		}

		public void fillText(List<String> messages, Integer numLineas, Integer n_zone) 
		{
			List<String> alternances;
			
			String aux_value;
			
			Text textLine;
			
			for(int contador = 1; contador <= numLineas; contador++){
				if(contador <= messages.size() && messages.get(contador - 1) != null) {
					aux_value = messages.get(contador - 1);
				}else {
					aux_value = "";
				}
				textLine = new Text(contador, aux_value, null, "#FFFFFF");
				texts.add(textLine);
				
				}
		}
	}

	String sustituirCaracter(String textValue, List<String> cad_character, List<String> cad_ascii)
	{
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