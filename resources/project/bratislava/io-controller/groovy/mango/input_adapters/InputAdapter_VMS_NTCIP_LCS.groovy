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
	final int POS1 = 0;
	final int POS2 = 1;
	final int NUMLINE = 1;
	final String ALIGN = "center";
	
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
		String aux_grap, aux_zonas, aux_grap2;
		List<String> aux_Content = new ArrayList();
		List<String> aux_text = new ArrayList();
		List<Zone> zones = new ArrayList();
		int alternance, flashing_on, flashing_off, numzone, align;
		Zone zone = null;
		List<String> cad_character = new ArrayList();
		List<String> cad_ascii = new ArrayList();
		String character = "↑,°,Á,Â,Ä,Ç,É,Ë,Í,Î,Ó,Ô,Ö,×,Ú,Ü,Ý,ß,á,â,ä,ç,é,ë,í,î,ó,ô,ö,÷,ú,ü,ý,Ă,ă,Ą,ą,Ć,ć,Č,č,Ď,ď,Đ,đ,Ę,Ĺ,ĺ,Ľ,ľ,Ł,ł,Ń,Ň,ň,Ő,ő,Ŕ,ŕ,Ř,ř,Ś,ś,Ş,ş,Š,š,Ţ,ţ,Ť,ť,Ů,ů,Ű,ű,Ź,ź,Ż,ż,ž,ˇ,˘,˙,˛,˜,˝";
	    String ascii = "15,B0,C1,C2,C4,C7,C9,CB,CD,CE,D3,D4,D6,D7,DA,DC,DD,DF,E1,E2,E4,E7,E9,EB,ED,EE,F3,F4,F6,F7,FA,FC,FD,102,103,104,105,106,107,10C,10D,10E,10F,110,111,118,139,13A,13D,13E,141,142,143,147,148,150,151,154,155,158,159,15A,15B,15E,15F,160,161,162,163,164,165,16E,16F,170,171,179,17A,17B,17C,17D,17E,2C7,2D8,2D9,2DB,2DC,2DD";
		cad_character = character.split(",");
		cad_ascii = ascii.split(",");		

		CONTENT_COPY = sustituirCaracter(CONTENT_COPY,cad_character,cad_ascii);
		for (int i = 0; i < 2; i++)
			aux_text.add("");
		while(CONTENT_COPY)
		{
			if (CONTENT_COPY.charAt(0) == "[")
			{
				aux_grap = (CONTENT_COPY.substring(CONTENT_COPY.indexOf("["), CONTENT_COPY.indexOf("]") + 1));
				aux_Content.add(aux_grap);
				CONTENT_COPY = CONTENT_COPY - aux_grap;
			}
			else
			{
				if(CONTENT_COPY.contains("["))
				{

					aux_grap2 = aux_grap.split(",")
					numzone = obtenerZona(aux_grap2, element);
					
					aux_grap = CONTENT_COPY.substring(CONTENT_COPY.indexOf(""), CONTENT_COPY.indexOf("["));
					if (numzone == 4 || numzone == 2)
					{
						aux_text.set(0,aux_grap);
					}
					else
					{
						aux_text.set(1,aux_grap);
					}
					
					CONTENT_COPY = CONTENT_COPY - aux_grap;

				}
				else
				{
					aux_grap2 = aux_grap.split(",")

					numzone = obtenerZona(aux_grap2, element);
					aux_grap = CONTENT_COPY;
					if (numzone == 4 || numzone == 2)
					{
						aux_text.set(0,aux_grap);
					}
					else
					{
						aux_text.set(1,aux_grap);
					}
					
					CONTENT_COPY = CONTENT_COPY - aux_grap;
				}		
			}
		}



		String propertiesPath = new File(ResourcesUtil.getPath("io-controller/groovy/mango/input_adapters/vms.properties")).toString();
		Properties p = new Properties();
		p.load(new FileReader(propertiesPath));

		for (int n = 0; n < aux_Content.size(); n++)
		{
			alternance = Integer.parseInt(p.getProperty("vms.subtype_" + String.valueOf(element.getElementSubtypeId()) + ".alternance"));
			flashing_on = Integer.parseInt(p.getProperty("vms.subtype_" + String.valueOf(element.getElementSubtypeId()) + ".flashing_on"));
			flashing_off = Integer.parseInt(p.getProperty("vms.subtype_" + String.valueOf(element.getElementSubtypeId()) + ".flashing_off"));
			
			if(alternance != null && flashing_on != null &&  flashing_off != null )
			{
				aux_zonas = aux_Content.get(n).split(",");
		
				zone = new Zone(obtenerZona(aux_zonas,element), alternance, flashing_on, flashing_off, ALIGN);
			}
			else
			{	
				log.debug("Problema al encontrar alguno de los parametros (alternance, flashing_on, flashing_off), para el Element:" + element.getElementTypeId() + ":" + element.getId());
				return "";
			}
			zone.parser(aux_Content, n, element, alternance, aux_text);
			zones.add(zone);
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
	
	class Zone
	{
		Integer zone;
		Integer alternance_ms;
		Integer flashing_on_ms;
		Integer flashing_off_ms;
		List<Graphic> graphics;
		List<Text> texts;
		String align;
		
		public Zone(Integer zone, Integer alternance_ms, Integer flashing_on_ms, Integer flashing_off_ms, String align)
		{
			this.zone = zone;
			this.alternance_ms = alternance_ms;
			this.flashing_on_ms = flashing_on_ms;
			this.flashing_off_ms = flashing_off_ms;
			graphics = new ArrayList();
			texts = new ArrayList();
			this.align = align;
		}

		public void parser(List<String> message, int n, Element element, int alternance, List<String> text)
		{
		
			String message_aux;
			String aux_graphic;
			String aux_value;
			Integer aux_zone_text;
			Integer values_graphic = 0;

			if(element.getElementSubtypeId() == null || !element.getElementSubtypeId())
				log.debug("No se encuentra subtipo para el Element:" + element.getElementTypeId() + ":" + element.getId());

			if (message.get(n).charAt(1) == "g")
			{
				message_aux = (message.get(n).split(","));
				aux_graphic = message_aux.substring(message_aux.indexOf("g") + 1, message_aux.indexOf(","));

				if(aux_graphic == "null")
				{
					values_graphic = null;
				}
				else{
					values_graphic = obtenerGraphic(element,Integer.parseInt(aux_graphic),obtenerZona(message_aux, element));
				}

				Graphic graphic = new Graphic(1, values_graphic, alternance);
				graphics.add(graphic);

			}
			else if (message.get(n).charAt(1) == "t")
			{
				Text textLine;

				message_aux = (message.get(n).split(","));

				aux_zone_text = obtenerZona(message_aux, element);

				if(aux_zone_text == 4 || aux_zone_text == 2)
				{
					for(int contador = 1; contador <= NUMLINE; contador++) 
					{
						if(contador <= text.size() && text.get(0) != null) 
						{
							aux_value = text.get(0);
						}
						else 
						{
							aux_value = "";
						}
						textLine = new Text(contador, aux_value, null, "#FFFFFF");
						texts.add(textLine);
					}
				}
				else if(aux_zone_text == 8)
				{
					for(int contador = 1; contador <= NUMLINE; contador++) 
					{
						if(contador <= text.size() && text.get(1) != null) 
						{
							aux_value = text.get(1);
						}
						else 
						{
							aux_value = "";
						}
						textLine = new Text(contador, aux_value, null, "#FFFFFF");
						
						texts.add(textLine);
					}
				}
				else if (message.isEmpty())
				{
					values_graphic = null;
					aux_value = null;
				}

			}
		}
		public int obtenerGraphic(Element element, Integer pictoValue, Integer numZone)
		{
			Long group;

			ElementValue elementDataJson = EntitiesManager.getInstance().getElementValueConfig(element, PARAM_CONFIG_JSONCONFIG);

            def jsonObject = new JsonSlurper().parseText(elementDataJson.getValue());

				group = jsonObject.vms_group_id[numZone - 1];

			List<VmsGraphicGraphicGroupValue> vmsGraphicGraphicGroupValue = EntitiesManager.getInstance().getByGroup(group);

			for(VmsGraphicGraphicGroupValue groupValue : vmsGraphicGraphicGroupValue)
			{
				if(pictoValue.equals(Integer.parseInt(groupValue.getValue())))
				{
					return groupValue.getGraphicId();
				}
			}
		}
	}
	public Integer obtenerZona(String zones, Element element)  
	{
		Integer zone = 0;
		int x_zone;
		int y_zone;
		String aux_String;

		if(zones.charAt(2) == "t")
		{
			aux_String = zones - (zones.substring(zones.indexOf("["), zones.indexOf("tr")));

		}
		else
		{
			aux_String = zones - (zones.substring(zones.indexOf("["), zones.indexOf(" ")));

		}
		
		
		if (aux_String.charAt(0) == "t")
		{
			x_zone = Integer.parseInt(aux_String.substring(aux_String.indexOf("r") + 1, aux_String.indexOf(",")));
			aux_String = aux_String.replace("tr" + x_zone + "," , "");
			y_zone = Integer.parseInt(aux_String.substring(aux_String.indexOf(" ") + 1, aux_String.indexOf(",")));
		}
		else
		{
			x_zone = Integer.parseInt(aux_String.substring(aux_String.indexOf(" ") + 1, aux_String.indexOf(",")));
			aux_String = aux_String - (aux_String.substring(aux_String.indexOf(" "), aux_String.indexOf(",") + 1));

			y_zone = Integer.parseInt(aux_String.substring(aux_String.indexOf(" ") + 1, aux_String.indexOf("]")));
		}
		if(element.elementSubtypeId == 2){

		if (((x_zone >= 1) && (x_zone <= 48)) && (y_zone <= 48))
		{
				zone= 1;
		}
		else if((y_zone >= 49) && ((x_zone >= 1) && (x_zone <= 48)))
		{
				zone = 2;
		}
		else if(((x_zone >= 49) && (x_zone <= 96)) && ((y_zone <= 48)))
		{	
				zone = 3;
		}
		else if((y_zone >= 49 ) && ((x_zone >= 49) && (x_zone <= 96)))
		{
				zone = 4;
		}
		else if(((x_zone >= 97) && (x_zone <= 144)) && (y_zone <= 48))
		{
				zone = 5;
		}
		else if((y_zone >= 49) && ((x_zone >= 97) && (x_zone <= 144)))
		{
				zone = 6;
		}
		else if(((x_zone >= 145) && (x_zone <= 192)) && (y_zone <= 48))
		{	
				zone = 7;
		}
		else if((y_zone >= 49) && ((x_zone >= 145) && (x_zone <= 192)))
		{
				zone = 8;
		}
		else if((x_zone >= 193) && (y_zone <= 48))
		{
				zone = 9;
		}
		else if((y_zone >= 49) && (x_zone >= 193))
		{
				zone = 10;
		}
		}else if (element.elementSubtypeId == 1){

			if (((x_zone >= 1) && (x_zone <= 48)) && (y_zone <= 48))
		{
				zone= 1;
		}
		else if((y_zone >= 49) && ((x_zone >= 1) && (x_zone <= 48)))
		{
				zone = 2;
		}
		else if(((x_zone >= 49) && (x_zone <= 96)) && ((y_zone <= 48)))
		{	
				zone = 3;
		}
		else if((y_zone >= 49 ) && ((x_zone >= 49) && (x_zone <= 96)))
		{
				zone = 4;
		}
		else if(((x_zone >= 97) && (x_zone <= 144)) && (y_zone <= 48))
		{
				zone = 5;
		}
		else if((y_zone >= 49) && ((x_zone >= 97) && (x_zone <= 144)))
		{
				zone = 6;
		}
		}else if (element.elementSubtypeId == 3){

			if (((x_zone >= 1) && (x_zone <= 48)) && (y_zone <= 48))
		{
				zone= 1;
		}
		else if((y_zone >= 49) && ((x_zone >= 1) && (x_zone <= 48)))
		{
				zone = 2;
		}
		else if(((x_zone >= 49) && (x_zone <= 96)) && ((y_zone <= 48)))
		{	
				zone = 3;
		}
		else if((y_zone >= 49 ) && ((x_zone >= 49) && (x_zone <= 96)))
		{
				zone = 4;
		}
		}

		return zone;
	
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