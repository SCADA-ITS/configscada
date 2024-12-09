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
import com.revenga.rits.back.data.core.model.command.SetActivationAlarmsCommand;
import com.revenga.rits.back.data.core.model.command.SetDeactivationAlarmsCommand;
import com.revenga.rits.back.data.core.util.ResourcesUtil;
import com.revenga.rits.back.io.controller.service.EntitiesManager;
import org.apache.commons.collections4.CollectionUtils;

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
	static final String CMD_VALUE_POWER_OFF_DGT = "02209b30748403";
	final String TEXT_COLOR = "#FFFFFF";
	
	static final Long PARAM_CONFIG_JSONCONFIG = 4L;
	static final Long PARAM_MEASURE_DATAJSON = 3L;
	static final Long PARAM_TYPE_MEASURE = 2L;
	
	//DGT protocol values	
	static final int COD_EST_PMV = 0x9B;
	static final int EST_ALT = 0x33;
	static final int PARAM_LINEA = 0x18;
	static final int PARAM_LINEA_INT = 0x58;
	static final int LITERAL_H = 0x5E;
	static final int LITERAL_L = 0x4C;
	static final int INTERMITENCIA_NO = 0x4E;
	static final int INTERMITENCIA_SI = 0x53;
	static final int ESTADO_GENERAL = 0x97;
	
	static final int POS_GRAPHIC = 8;
	static final int POS_FLASHING = 9;
	static final int POS_GRAPHIC_ALT = 12;
	
	static final int STX = 0x02;
	static final int ETX = 0x03;
	static final int ENQ = 0x05;
	static final int ACK = 0x06;
	static final int CTRL = 0x10;
	static final int SPECIAL = 0x80;

	static final int BYTE_SIZE = 8;
	static final int ALARMAS_BYTE_3 = 5;
	static final int ALARMAS_BYTE_6 = 8;

	static final int BIT_ALARMA_PUERTA_ABIERTA = 7;
	static final int BIT_ALARMA_VENTILACION_ACTIVADA = 4;
	static final int BIT_ALARMA_FALLO_VENTILACION = 3;
	static final int BIT_ALARMA_ERROR_INTERNO_HARDWARE = 2;
	static final int BIT_ALARMA_EXCESO_TEMPERATURA = 0;
	static final int BIT_ALARMA_FOTOCELULA = 4;

	static final Long ALARM_CONFIG_PUERTA_ABIERTA = 20071L;
	static final Long ALARM_CONFIG_VENTILACION_ACTIVADA = 80071L;
	static final Long ALARM_CONFIG_FALLO_VENTILACION = 90071L;
	static final Long ALARM_CONFIG_ERROR_INTERNO_HARDWARE = 100071L;
	static final Long ALARM_CONFIG_EXCESO_TEMPERATURA = 70071L;
	static final Long ALARM_CONFIG_FOTOCELULA = 50071L;

	
	InputAdapter_VMS(org.apache.logging.log4j.Logger log) {

		this.log = log;
	}
	
	boolean content(Element element, String value, List<ElementValue> elementValues) {
		if(value == null || value.equals(CMD_VALUE_POWER_OFF_DGT) || value.equals("") || value.contains(CMD_VALUE_POWER_OFF_DGT)) {
			ElementValue elementValue = new ElementValue();
			elementValue.setElementTypeId(element.getElementTypeId());
			elementValue.setElementTypeParamId(PARAM_MEASURE_DATAJSON);
			elementValue.setParamTypeId(PARAM_TYPE_MEASURE);
			elementValue.setElementId(element.getId());
			elementValue.setValue("[]");
			elementValues.add(elementValue);
			return true;
		}
		
		String result = construirVMS(value, element);
		
		if (result == null || result.equals("")){
			return true;
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

	boolean state(Element element, String value, List<ElementValue> elementValues){
		List<Integer> contentByte = new ArrayList<Integer>();
		SetActivationAlarmsCommand setActivationAlarmsCommandList = new SetActivationAlarmsCommand();
    	SetDeactivationAlarmsCommand setDeactivationAlarmsCommandList = new SetDeactivationAlarmsCommand();
		
		if(value == null || value.equals("")) {
			log.debug("Trama recibida no válida")
			return true
		}

		contentByte = comprobarTrama(value);

		if (contentByte.isEmpty()){
			log.debug("Trama recibida no válida")
			return true
		}

		contentByte = controlCharacters(contentByte);

		if(!(contentByte.get(2) & 0xFF).equals(ESTADO_GENERAL)){
			log.debug("Trama recibida no es de estado general");
			return true;
		}

		int[] arrayByte3Binario = conversionIntToArrayIntBinary(contentByte, ALARMAS_BYTE_3)
		int[] arrayByte6Binario = conversionIntToArrayIntBinary(contentByte, ALARMAS_BYTE_6)

		generarAlarma(element, arrayByte3Binario, BIT_ALARMA_PUERTA_ABIERTA, ALARM_CONFIG_PUERTA_ABIERTA, setActivationAlarmsCommandList, setDeactivationAlarmsCommandList)
		generarAlarma(element, arrayByte3Binario, BIT_ALARMA_VENTILACION_ACTIVADA, ALARM_CONFIG_VENTILACION_ACTIVADA, setActivationAlarmsCommandList, setDeactivationAlarmsCommandList)
		generarAlarma(element, arrayByte3Binario, BIT_ALARMA_FALLO_VENTILACION, ALARM_CONFIG_FALLO_VENTILACION, setActivationAlarmsCommandList, setDeactivationAlarmsCommandList)
		generarAlarma(element, arrayByte3Binario, BIT_ALARMA_ERROR_INTERNO_HARDWARE, ALARM_CONFIG_ERROR_INTERNO_HARDWARE, setActivationAlarmsCommandList, setDeactivationAlarmsCommandList)
		generarAlarma(element, arrayByte3Binario, BIT_ALARMA_EXCESO_TEMPERATURA, ALARM_CONFIG_EXCESO_TEMPERATURA, setActivationAlarmsCommandList, setDeactivationAlarmsCommandList)
		generarAlarma(element, arrayByte6Binario, BIT_ALARMA_FOTOCELULA, ALARM_CONFIG_FOTOCELULA, setActivationAlarmsCommandList, setDeactivationAlarmsCommandList)

		EntitiesManager.getInstance().sendCommand(setActivationAlarmsCommandList);
		EntitiesManager.getInstance().sendCommand(setDeactivationAlarmsCommandList);

		return true
	}

	public int[] conversionIntToArrayIntBinary (List<Integer> contentByte, int posicionByte){
		String byteBinario = String.format("%${BYTE_SIZE}s", Integer.toBinaryString(contentByte[posicionByte])).replace(' ', '0')
		int[] arrayByteBinario = byteBinario.collect { it as Integer }
		log.debug("arrayByteBinario -> " + arrayByteBinario);

		return arrayByteBinario
	}

	public void generarAlarma (Element element, int[] arrayBinario, int posicionBit, Long alarma, SetActivationAlarmsCommand setActivationAlarmsCommandList, SetDeactivationAlarmsCommand setDeactivationAlarmsCommandList){
		
		if(arrayBinario[posicionBit] == 1){

			setActivationAlarmsCommandList.add(element, EntitiesManager.getInstance().getAlarmConfig(alarma));
		}else{

			setDeactivationAlarmsCommandList.add(element, EntitiesManager.getInstance().getAlarmConfig(alarma));
		}
		
	}
	
	
	public String construirVMS(String content, Element element) {
		List<String> graphics = new ArrayList();
		List<String> graphics_alt = new ArrayList();
		List<String> texts = new ArrayList();
		List<Zone> zones = new ArrayList();
		List<Long> intermitence = new ArrayList();
		Zone zone = null;
		int alternance, flashing_on, flashing_off, num_lineas;
		List<Integer> contentByte = new ArrayList<Integer>();
		String  zone_type = "", resultado = "";
		boolean hayAlternancia = false;

		contentByte = comprobarTrama(content);
		if (contentByte.isEmpty()){
			return ""
		}
		
		contentByte = controlCharacters(contentByte);
		
		String propertiesPath = new File(ResourcesUtil.getPath("io-controller/groovy/mango/input_adapters/vms.properties")).toString();
		Properties p = new Properties();
		p.load(new FileReader(propertiesPath));
		
		if(!(contentByte.get(2) & 0xFF).equals(COD_EST_PMV)){
			log.debug("Trama recibida no es de estado del panel");
			return "";
		}
		
		if((contentByte.get(3) & 0xFF).equals(EST_ALT)){
			hayAlternancia = true;
		}
		
		graphics.add(String.valueOf((contentByte.get(POS_GRAPHIC) & 0xFF).intValue()));
		if(hayAlternancia){
			graphics.add(String.valueOf((contentByte.get(contentByte.size() - 9) & 0xFF).intValue()));
			graphics_alt.add(String.valueOf((contentByte.get(POS_GRAPHIC_ALT) & 0xFF).intValue()));
			graphics_alt.add(String.valueOf((contentByte.get(contentByte.size() - 5) & 0xFF).intValue()));
		}else{
			graphics.add(String.valueOf((contentByte.get(contentByte.size() - 5) & 0xFF).intValue()));
		}
		
		//Fijo las intermitencias		
		if((contentByte.get(POS_FLASHING) & 0xFF).equals(INTERMITENCIA_SI)){
			intermitence.add(1000);
	    }else{
			intermitence.add(0);
	    }
	    intermitence.add(0);
	    if((contentByte.get(contentByte.size() - 4) & 0xFF).equals(INTERMITENCIA_SI)){
			intermitence.add(1000);
	    }else{
			intermitence.add(0);
	    }
		
		int contador = 0, tam = 0;
		List<Integer> textArray = new ArrayList<Integer>();
		for(byte b : contentByte){
			if(contador >= 2){
				if((b & 0xFF) == PARAM_LINEA && (contentByte.get(contador - 1) & 0xFF) == PARAM_LINEA && (contentByte.get(contador - 2) & 0xFF) == PARAM_LINEA){
					tam = contador + 1;
				}
				if((b & 0xFF) == PARAM_LINEA_INT && (contentByte.get(contador - 1) & 0xFF) == PARAM_LINEA_INT && (contentByte.get(contador - 2) & 0xFF) == PARAM_LINEA_INT){
					tam = contador + 1;
					intermitence.set(1,1000);
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
			contentByte = contentByte.subList(contador + 1, contentByte.size() - 1);
			contador = 0;
			resultado = "";
			for(byte b : contentByte){
				if(contador >= 2){
					if((b & 0xFF) == PARAM_LINEA && (contentByte.get(contador - 1) & 0xFF) == PARAM_LINEA && (contentByte.get(contador - 2) & 0xFF) == PARAM_LINEA){
						tam = contador + 1;
					}
					if((b & 0xFF) == PARAM_LINEA_INT && (contentByte.get(contador - 1) & 0xFF) == PARAM_LINEA_INT && (contentByte.get(contador - 2) & 0xFF) == PARAM_LINEA_INT){
						tam = contador + 1;
						intermitence.set(1,1000);
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
		}
				
		if(element.getElementSubtypeId() == null || !element.getElementSubtypeId()){
			log.error("No se encuentra subtipo para el Element:" + element.getElementTypeId() + ":" + element.getId());
			return "";
		}
		
		int num_zones = Integer.parseInt(p.getProperty("vms.subtype_" + String.valueOf(element.getElementSubtypeId()) + ".num_zones"));
		int zones_graphic = 0;
		if(num_zones && num_zones != null){
			for(int n_zone = 1; n_zone <= num_zones; n_zone++){
			    alternance = Integer.parseInt(p.getProperty("vms.subtype_" + String.valueOf(element.getElementSubtypeId()) + ".alternance"));
			    flashing_on = intermitence.get(n_zone - 1);
			    
			    if(alternance != null && flashing_on != null &&  flashing_off != null){	    
					zone = new Zone(n_zone, alternance, flashing_on, flashing_off);
				}else{
					log.debug("Problema al encontrar alguno de los parametros (alternance, flashing_on, flashing_off), para el Element:" + element.getElementTypeId() + ":" + element.getId());
					return "";
				}
				
				zone_type = p.getProperty("vms.subtype_" + String.valueOf(element.getElementSubtypeId()) + ".typezone_" + String.valueOf(n_zone));
				if(zone_type && zone_type != null){
					if(zone_type.equals(GRAPHIC)){
						zone.fillGraphic(graphics, graphics_alt, element, n_zone, zones_graphic);	
						zones_graphic++;
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
	
		public void fillGraphic(List<String> message, List<String> message_alt, Element element, int numZone, int idGraphic) {
			
			Integer values = 0;
			Integer alternances = 0;

			values = obtenerGraphic(element,Integer.parseInt(message.get(idGraphic)),numZone);
			
			if (values != -1){
				if (message_alt.size() > 1) {
					alternances = obtenerGraphic(element,Integer.parseInt(message_alt.get(idGraphic)),numZone);
				}
				
				Graphic graphic = new Graphic(1, values, alternances);
				graphics.add(graphic);
			}
		
		}
		
		public int obtenerGraphic(Element element, Integer pictoValue, Integer numZone){
		
			ElementValue elementDataJson = EntitiesManager.getInstance().getElementValueConfig(element, PARAM_CONFIG_JSONCONFIG);
			
            def jsonObject = new JsonSlurper().parseText(elementDataJson.getValue());
			Long group = jsonObject.vms_group_id[numZone - 1];
			
			List<VmsGraphicGraphicGroupValue> vmsGraphicGraphicGroupValue = EntitiesManager.getInstance().getByGroup(group);
			
			if (!CollectionUtils.isEmpty(vmsGraphicGraphicGroupValue)) {
				for(VmsGraphicGraphicGroupValue groupValue : vmsGraphicGraphicGroupValue){
					if(!groupValue.getValue().equals("") || !groupValue.getValue() == null){
						if(pictoValue.equals(Integer.parseInt(groupValue.getValue()))){
							return groupValue.getGraphicId();
						}
					}
				}
				log.debug("Trama recibida no está completa, o llegan varias tramas juntas. Se ignoran.");
				return -1;
			}else{
				log.debug("Trama recibida no está completa, o llegan varias tramas juntas. Se ignoran.");
				return -1;
			}
		}
	}
	
	public List<Integer> controlCharacters(List<Integer> data){
		List<Integer> resultado = new ArrayList<Integer>();
		int aux;
		
		for(int i=0; i<data.size();i++){
			aux = data[i];
			if(aux == CTRL){
				resultado.add(data[i+1] - SPECIAL);
				i++;
			}else{
				resultado.add(aux);
			}
		}
		
		return resultado;
	}

	public List<Integer> comprobarTrama(String value){
		String aux = ""
		int num_signallings = 0
		List<Integer> contentByte = new ArrayList<Integer>();


		if(value.length() < 6){
			log.debug("Trama recibida no válida");
			return [];
		}
		
		for(int i = 0; i<value.length(); i+=2){
			aux = value.substring(i,i+2);
			if (aux == "03"){
				num_signallings += 1;
			}

			contentByte.add(Integer.parseInt(aux, 16));
		}

		if (num_signallings > 1 || num_signallings == 0){
			log.debug("Trama recibida no válida");
			return [];
		}
		
		return contentByte;
	}

}
