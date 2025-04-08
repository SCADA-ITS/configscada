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
import com.revenga.rits.back.data.core.model.AlarmConfig;
import com.revenga.rits.back.data.core.model.command.SignallingCommand;
import com.revenga.rits.back.data.core.util.FrameHelper;
import com.revenga.rits.back.data.core.model.ElementTypeParam;
import com.revenga.rits.back.data.core.model.VmsGraphicGraphicGroupValue;
import com.revenga.rits.back.data.core.model.command.SetActivationAlarmsCommand;
import com.revenga.rits.back.data.core.model.command.SetDeactivationAlarmsCommand;
import com.revenga.rits.back.data.core.util.ResourcesUtil;
import com.revenga.rits.back.io.controller.service.EntitiesManager;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;



public void content(Element element, List<Byte> data, List<ElementValue> elementValuesToSend, org.apache.logging.log4j.Logger log){

	final List<Byte> CMD_VALUE_POWER_OFF_DGT = List.of((byte) 0x30);	
	final Long PARAM_MEASURE_DATAJSON = 3L;
	final Long PARAM_TYPE_MEASURE = 2L;

	byte[] newData = FrameHelper.byteListToArray(data)

	boolean intermitence = false;
	
	if(data == null || data.equals(CMD_VALUE_POWER_OFF_DGT)) {
		ElementValue elementValue = new ElementValue();
		elementValue.setElementTypeId(element.getElementTypeId());
		elementValue.setElementTypeParamId(PARAM_MEASURE_DATAJSON);
		elementValue.setParamTypeId(PARAM_TYPE_MEASURE);
		elementValue.setElementId(element.getId());
		elementValue.setValue("[]");
		elementValuesToSend.add(elementValue);
	}
	
	String result = construirVMS(newData, element, intermitence, log);
	
	if (result == null || result.equals("")){
		
		ElementValue elementValue = new ElementValue();
		elementValue.setElementTypeId(element.getElementTypeId());
		elementValue.setElementTypeParamId(PARAM_MEASURE_DATAJSON);
		elementValue.setParamTypeId(PARAM_TYPE_MEASURE);
		elementValue.setElementId(element.getId());
		elementValue.setValue("[]");
		elementValuesToSend.add(elementValue);
	}else{
		
		ElementValue elementValue = new ElementValue();
		elementValue.setElementTypeId(element.getElementTypeId());
		elementValue.setElementTypeParamId(PARAM_MEASURE_DATAJSON);
		elementValue.setParamTypeId(PARAM_TYPE_MEASURE);
		elementValue.setElementId(element.getId());
		elementValue.setValue(result);
		elementValuesToSend.add(elementValue);
	}

}

public String construirVMS(byte[] content, Element element, boolean intermitence, org.apache.logging.log4j.Logger log) {
	final String GRAPHIC = "graphic";
	final String TEXT = "text";
	final int EST_ALT = 0x33;
	final int PARAM_LINEA = 0x18;
	final int LITERAL_H = 0x5E;
	final int LITERAL_L = 0x4C;
	final int ESTADO_GENERAL = 0x97;

	final int POS_GRAPHIC = 5;
	final int POS_GRAPHIC_ALT = 9;


	List<String> graphics = new ArrayList();
	List<String> texts = new ArrayList();
	List<Zone> zones = new ArrayList();
	Zone zone = null;
	int alternance, flashing_on, flashing_off, num_lineas;
	List<Integer> contentByte = new ArrayList<Integer>();

	String aux = "", zone_type = "", resultado = "";
	boolean hayAlternancia = false;		
	
	String propertiesPath = new File(ResourcesUtil.getPath("io-controller/groovy/mango/input_adapters/vms.properties")).toString();
	Properties p = new Properties();
	p.load(new FileReader(propertiesPath));
	
	contentByte = content.collect { it & 0xFF }


	if((contentByte.get(0) & 0xFF).equals(EST_ALT)){
		hayAlternancia = true;
	}

	graphics.add(String.valueOf((contentByte.get(POS_GRAPHIC) & 0xFF).intValue()));
	if(hayAlternancia){
		graphics.add(String.valueOf((contentByte.get(POS_GRAPHIC_ALT) & 0xFF).intValue()));
	}
	
	int contador = 0, tam = 0;
	List<Integer> textArray = new ArrayList<Integer>();
	for(byte b : contentByte){
		if(contador >= 2){
			if((b & 0xFF) == PARAM_LINEA && (contentByte.get(contador - 1) & 0xFF) == PARAM_LINEA && (contentByte.get(contador - 2) & 0xFF) == PARAM_LINEA){
				tam = contador + 1;
			}
			if((b & 0xFF) == 0x00){
				textArray = contentByte.subList(tam, contador);
				
				for(int str : textArray){
					if (str > 127) resultado += new String(Arrays.copyOfRange(String.format("%c", str).getBytes("UTF-8"),1,2), "Cp437");
					else resultado += String.format("%c", str);
				}
				break;
			}
		}
		contador++;
		
	}
	texts.add(resultado);
	
	if(hayAlternancia){
		log.debug("ENTRA en alternancia")
		contentByte = contentByte.subList(contador + 1, contentByte.size() );
		contador = 0;
		resultado = "";

		for(byte b : contentByte){
			if(contador >= 2){
				
				if((b & 0xFF) == PARAM_LINEA && (contentByte.get(contador - 1) & 0xFF) == PARAM_LINEA && (contentByte.get(contador - 2) & 0xFF) == PARAM_LINEA){
					tam = contador + 1
				}
				if((b & 0xFF) == 0x00){
					textArray = contentByte.subList(tam, contador);
					
					for(byte str : textArray){
						resultado += String.format("%c", str);
					}
					break;
				}
			}
			contador++;
		}
		texts.add(resultado);
		log.debug("TEXT_ALTERNANCIA-------> " + texts)
	}
			
	if(element.getElementSubtypeId() == null || !element.getElementSubtypeId()){
		log.error("No se encuentra subtipo para el Element:" + element.getElementTypeId() + ":" + element.getId());
		return "";
	}
	
	int num_zones = Integer.parseInt(p.getProperty("vms.subtype_" + String.valueOf(element.getElementSubtypeId()) + ".num_zones"));
	if(num_zones && num_zones != null){
		for(int n_zone = 1; n_zone <= num_zones; n_zone++){
			alternance = Integer.parseInt(p.getProperty("vms.subtype_" + String.valueOf(element.getElementSubtypeId()) + ".alternance"));
			if(intermitence){
				flashing_on = Integer.parseInt(p.getProperty("vms.subtype_" + String.valueOf(element.getElementSubtypeId()) + ".flashing_on"));
				flashing_off = Integer.parseInt(p.getProperty("vms.subtype_" + String.valueOf(element.getElementSubtypeId()) + ".flashing_off"));
			}else{
				flashing_on = 0;
				flashing_off = 0;
			}
			
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
			log.debug("zone------> "+ zones)
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
		final String TEXT_COLOR = "#FFBF00";

		List<String> values = Arrays.asList(messages.get(0).split("\n"));
		List<String> alternances;
		if (messages.size() > 1) alternances = Arrays.asList(messages.get(1).split("\n"));
		
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
		final Long PARAM_CONFIG_JSONCONFIG = 4L;
	
		ElementValue elementDataJson = EntitiesManager.getInstance().getElementValueConfig(element, PARAM_CONFIG_JSONCONFIG);
		
		def jsonObject = new JsonSlurper().parseText(elementDataJson.getValue());
		Long group = jsonObject.vms_group_id[numZone - 1];
		
		List<VmsGraphicGraphicGroupValue> vmsGraphicGraphicGroupValue = EntitiesManager.getInstance().getByGroup(group);
		for(VmsGraphicGraphicGroupValue groupValue : vmsGraphicGraphicGroupValue){
			if(!groupValue.getValue().equals("") || !groupValue.getValue() == null){
				if(pictoValue.equals(Integer.parseInt(groupValue.getValue()))){
					return groupValue.getGraphicId();
				}
			}
		}
	}
}
