import java.util.ArrayList;
import java.util.List;

import groovy.json.*

import org.apache.commons.lang3.exception.ExceptionUtils;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revenga.rits.back.data.core.model.command.SignallingCommand;
import com.revenga.rits.back.io.controller.driver.mango.MangoDriver;
import com.revenga.rits.back.mango.core.model.XidPointValueTimeModel;
import com.revenga.rits.back.mango.core.model.XidPointValueTimeModel.DataTypeEnum;
import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.ElementValue;
import com.revenga.rits.back.data.core.model.VmsGraphicGraphicGroupValue
import com.revenga.rits.back.io.controller.service.EntitiesManager;
import com.revenga.rits.back.data.core.util.ResourcesUtil;

/**
 * 
 * SignallingCommand_71_2: Signalling command for VMS (elementTypeId = 71) 
 * 
 */
class SignallingCommand_71_2 {
	//Commands params
	
	SignallingCommand_71_2(org.apache.logging.log4j.Logger log) {
	
		this.log = log;
		shell = new GroovyShell();
		signallingCommandUtils = shell.parse(new File(ResourcesUtil.getPath("io-controller/groovy/mango/SignallingCommandUtils.groovy")));
	}
	
	boolean process(String dataSourceXid, SignallingCommand signallingCommand, MangoDriver driver) {
		try {	

				List<Byte> data = new ArrayList<Byte>();
				
				data.add(DIR_LOGICA); 			//Dir. Logica del panel. Habitualmente la 0x20 = 32Dec
				data.add(COD_MODIFICAR_PMV);	//Codigo de protocolo para modificar señalización = 0x1A
				data.add(0x01);  		//Número de memoria gráfica. 
				data.add(64);			//Número de filas del gráfico
				data.add(64);			//Número de columnas del gráfico
				data.add(0);			//Fila inicial
				data.add(0);			//Columna inicial
				data.add(3);			//Planos de color del grafico
				
				data.addAll(CRC_XModem(data)); 	//Calculo el CRC
				data = controlCharacters(data);	//Transformo los caracteres especiales del protocolo 
				
				//Añado los caracteres de principio y fin		
				data.add(0, STX);
				data.add(ETX);
				
				for(byte aux : data){
					resultado += String.format("%02X", aux);
				}
				
				xidPointValueTimeModel = signallingCommandUtils.getXidPointValueTimeModel(signallingCommand, dataSourceXid + "_" +  SIGNALLING, resultado);
				xidPointValueTimeModels.add(xidPointValueTimeModel);
							
				mapper.setSerializationInclusion(Include.NON_NULL);
				message = mapper.writeValueAsString(xidPointValueTimeModels);
	
				if (driver != null) {

					driver.send(message);
				}
			}
		}catch (NumberFormatException | JsonProcessingException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}
				
		return true;
	}
	
	static class Graphic{
		Long id;
		Long value;
		Long alternance;

		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public Long getValue() {
			return value;
		}
		public void setValue(Long value) {
			this.value = value;
		}
		public Long getAlternance() {
			return alternance;
		}
		public void setAlternance(Long alternance) {
			this.alternance = alternance;
		}
	}
	
	static class Text{
		Long id;
		String value;
		String alternance;
		String color;
		
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
		public String getAlternance() {
			return alternance;
		}
		public void setAlternance(String alternance) {
			this.alternance = alternance;
		}
		public String getColor() {
			return color;
		}
		public void setColor(String color) {
			this.color = color;
		}
	}
	
	static class Zone{
		Long zone;
		String align;
		Long alternance_ms;
		Long flashing_on_ms;
		Long flashing_off_ms;
		List<Graphic> graphics;
		List<Text> texts;

		public Long getZone() {
			return zone;
		}
		public void setZone(Long zone) {
			this.zone = zone;
		}
		public String getAlign() {
			return align;
		}
		public void setAlign() {
			this.align = align;
		}
		public Long getAlternance_ms() {
			return alternance_ms;
		}
		public void setAlternance_ms(Long alternance_ms) {
			this.alternance_ms = alternance_ms;
		}
		public Long getFlashing_on_ms() {
			return flashing_on_ms;
		}
		public void setFlashing_on_ms(Long flashing_on_ms) {
			this.flashing_on_ms = flashing_on_ms;
		}
		public Long getFlashing_off_ms() {
			return flashing_off_ms;
		}
		public void setFlashing_off_ms(Long flashing_off_ms) {
			this.flashing_off_ms = flashing_off_ms;
		}
		public List<Graphic> getGraphics() {
			return graphics;
		}
		public void setGraphics(List<Graphic> graphics) {
			this.graphics = graphics;
		}
		public List<Text> getTexts() {
			return texts;
		}
		public void setTexts(List<Text> texts) {
			this.texts = texts;
		}
	}
	
	public List<Byte> controlCharacters(List<Byte> data){
		List<Byte> resultado = new ArrayList<Byte>();
		
		for(byte aux : data){
			if(aux == STX || aux == ETX || aux == ACK || aux == ENQ || aux == CTRL){
				resultado.add(CTRL);
				resultado.add(aux + SPECIAL);
			}else{
				resultado.add(aux);
			}
		}
		
		return resultado;
	}

	public List<Byte> CRC_XModem(List<Byte>  bytes){
		List<Byte> resultado = new ArrayList<Byte>();
		
        int crc = 0x00;         //initial value
        int polynomial = 0x1021;  
        for (int index = 0 ; index< bytes.size(); index++) {
            byte b = bytes[index];
            for (int i = 0; i < 8; i++) {
                boolean bit = ((b   >> (7-i) & 1) == 1);
                boolean c15 = ((crc >> 15    & 1) == 1);
                crc <<= 1;
                if (c15 ^ bit) crc ^= polynomial;
             }
        }
        crc &= 0xffff;
        
        resultado.add((crc & 0xFF00) >> 8);
        resultado.add(crc & 0x00FF);
        return resultado;
	}
				
	public Long getGraphic(Element element, Long numZone, Long picto){
		ElementValue elementDataJson = EntitiesManager.getInstance().getElementValueConfig(element, PARAM_CONFIG_JSONCONFIG);
			
        def jsonObject = new JsonSlurper().parseText(elementDataJson.getValue());
		Long group = jsonObject.vms_group_id[numZone - 1];
		
		List<VmsGraphicGraphicGroupValue> vmsGraphicGraphicGroupValue = EntitiesManager.getInstance().getByGroup(group);
		
		for(VmsGraphicGraphicGroupValue groupValue : vmsGraphicGraphicGroupValue){
			if(picto.equals(groupValue.getGraphicId())){
				return Long.parseLong(groupValue.getValue());
			}
		}
		
		return 0L;
	}
	
	public void activarVMSNTCIP(String multi, String dataSourceXid, SignallingCommand signallingCommand, MangoDriver driver, boolean pixelService, boolean beacon, int prioridad){
		List<XidPointValueTimeModel> xidPointValueTimeModels = new ArrayList<>();
		XidPointValueTimeModel xidPointValueTimeModel;
		String message;
		
		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(Include.NON_NULL);
			
		// Enable PMV to accept a new message
		xidPointValueTimeModel = signallingCommandUtils.getXidPointValueTimeModel(signallingCommand, 
			dataSourceXid + "_" + MESSAGE_STATUS, MODIFYREQ);
				
		xidPointValueTimeModels.add(xidPointValueTimeModel);

		message = mapper.writeValueAsString(xidPointValueTimeModels);

		if (driver != null) {
			driver.send(message);
		}
		
		// Write New Message
		xidPointValueTimeModel = signallingCommandUtils.getXidPointValueTimeModel(signallingCommand, 
			dataSourceXid + "_" + MESSAGE_MULTISTRING, multi);
				
		xidPointValueTimeModels.clear();		
		xidPointValueTimeModels.add(xidPointValueTimeModel);

		message = mapper.writeValueAsString(xidPointValueTimeModels);

		if (driver != null) {
			driver.send(message);
		}
		
		// Confirm PMV new message
		xidPointValueTimeModel = signallingCommandUtils.getXidPointValueTimeModel(signallingCommand, 
			dataSourceXid + "_" + MESSAGE_STATUS, VALIDATEREQ);
				
		xidPointValueTimeModels.clear();			
		xidPointValueTimeModels.add(xidPointValueTimeModel);

		message = mapper.writeValueAsString(xidPointValueTimeModels);

		if (driver != null) {
			driver.send(message);
		}
		
		//Activamos el mensaje
		String activeMessage;
		activeMessage = MESSAGE_VOLATILE.substring(0,4) + String.format("%02X",prioridad) +  MESSAGE_VOLATILE.substring(6) + String.format("%04X", CRC_Message_Multi(multi, pixelService, beacon)) + LOCALHOST;
		
		xidPointValueTimeModel = signallingCommandUtils.getXidPointValueTimeModel(signallingCommand, 
			dataSourceXid + "_" + MESSAGE_HEX, activeMessage);
			
		xidPointValueTimeModels.clear();			
		xidPointValueTimeModels.add(xidPointValueTimeModel);

		message = mapper.writeValueAsString(xidPointValueTimeModels);

		if (driver != null) {
			driver.send(message);
		}
	}
	
	int CRC_Message_Multi(String message, boolean pixelService, boolean beacon) {
		byte[] crc = new byte[message.getBytes().size() + 2];
		byte[] fill = new byte[2];
		
		if(beacon)
			fill[0] = 0x01;
		else 
			fill[0] = 0x00;
			
		if(pixelService)
			fill[1] = 0x01;
		else
			fill[1] = 0x00;
		
		System.arraycopy(message.getBytes(), 0, crc, 0, message.getBytes().size()); 
		System.arraycopy(fill, 0, crc, message.getBytes().size(), fill.length);
		
		return CRC16_2_X25(crc); 
	}
	
	int CRC16_2_X25(byte[] buffer) {
        int wCRCin = 0xffff;
        int wCPoly = 0x8408;
        for (byte b : buffer) {
            wCRCin ^= ((int) b & 0x00ff);
            for (int j = 0; j < 8; j++) {
                if ((wCRCin & 0x0001) != 0) {
                    wCRCin >>= 1;
                    wCRCin ^= wCPoly;
                } else {
                    wCRCin >>= 1;
                }
            }
        }
        wCRCin ^= 0xffff;		
        return 256*(wCRCin & 0xFF) + ((wCRCin >> 8) & 0xFF);
    }

	 String sustituirCaracter(String textValue, List<String> cad_character, List<String> cad_ascii){
		String aux = "";
		for(int z = 0; z < cad_character.size() ; z++){
			if (textValue.indexOf(cad_character[z]) != -1){
				aux = "[hc" + cad_ascii[z] + "]";
				textValue = textValue.replace(cad_character[z],aux);
			}
		}
		return textValue;	
    }
	String sustituirGraphic(Element element, Long numZone, Long picto){
		ElementValue elementDataJson = EntitiesManager.getInstance().getElementValueConfig(element, PARAM_CONFIG_JSONCONFIG);
			
        def jsonObject = new JsonSlurper().parseText(elementDataJson.getValue());
		Long group = jsonObject.vms_group_id[numZone - 1];
		String aux = "";
		List<VmsGraphicGraphicGroupValue> vmsGraphicGraphicGroupValue = EntitiesManager.getInstance().getByGroup(group);
		for(VmsGraphicGraphicGroupValue groupValue : vmsGraphicGraphicGroupValue){
			if(picto.equals(groupValue.getGraphicId())){
				return groupValue.getValue();
			}
		}
    }

}
