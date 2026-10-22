import com.revenga.rits.back.data.core.model.ElementValue;
import com.revenga.rits.back.data.core.model.command.SignallingCommand;
import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.VmsGraphicGraphicGroupValue;
import com.revenga.rits.back.data.core.util.ResourcesUtil;
import com.revenga.rits.back.io.controller.driver.mango.MangoDriver;
import com.revenga.rits.back.mango.core.model.XidPointValueTimeModel;
import com.revenga.rits.back.io.controller.service.EntitiesManager;
    
import com.fasterxml.jackson.databind.ObjectMapper;    
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import groovy.json.*
    
/* Function to set value for a PMV Param*/    
def ElementValue elementSetValue(Long elementTypeParamId, Long paramTypeId, Long elementId, String value){
	
	final Long ELEMENT_TYPE_ID_PMV = 71L;
	final String SHUTDOWN = "1";
	
	ElementValue element = new ElementValue();
    element.setElementTypeId(ELEMENT_TYPE_ID_PMV);
    element.setElementTypeParamId(elementTypeParamId);
    element.setParamTypeId(paramTypeId);
    element.setElementId(elementId);
    element.setValue(value);
    
    return element;
}

def void activarVMSNTCIP(String multi, String dataSourceXid, SignallingCommand signallingCommand, MangoDriver driver, boolean pixelService, boolean beacon, int prioridad){
	// XIDs
	final String MESSAGE_STATUS = 'message_status';
	final String MESSAGE_MULTISTRING = "message_multistring";
	final String MESSAGE_HEX = 'message_hex';
	final String MESSAGE_VOLATILE = "FFFFFF030001";
	final String LOCALHOST = "7F000001";
	
	//NTCIP protocol values
	final Long MODIFYREQ = 6L;
	final Long VALIDATEREQ = 7L;
	
	List<XidPointValueTimeModel> xidPointValueTimeModels = new ArrayList<>();
	XidPointValueTimeModel xidPointValueTimeModel;
	String message;
	
	ObjectMapper mapper = new ObjectMapper();
	mapper.setSerializationInclusion(Include.NON_NULL);
	
	GroovyShell shell = new GroovyShell();
	def signallingCommandUtils = shell.parse(new File(ResourcesUtil.getPath("io-controller/groovy/mango/SignallingCommandUtils.groovy")));
		
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

def int CRC_Message_Multi(String message, boolean pixelService, boolean beacon) {
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

def int CRC16_2_X25(byte[] buffer) {
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

def List<Byte> CRC_XModem(List<Byte>  bytes){
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

def List<Byte> controlCharacters(List<Byte> data){
	final int STX = 0x02;
	final int ETX = 0x03;
	final int ENQ = 0x05;
	final int ACK = 0x06;
	final int CTRL = 0x10;
	final int SPECIAL = 0x80;
	
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

def Long getGraphic(Element element, Long numZone, Long picto){
	final Long PARAM_CONFIG_JSONCONFIG = 4L;

	ElementValue elementDataJson = EntitiesManager.getInstance().getElementValueConfig(element, PARAM_CONFIG_JSONCONFIG);
    def jsonObject = new JsonSlurper().parseText(elementDataJson.getValue());
    
	Long group = jsonObject.vms_group_id[numZone];
	List<VmsGraphicGraphicGroupValue> vmsGraphicGraphicGroupValue = EntitiesManager.getInstance().getByGroup(group);
	
	for(VmsGraphicGraphicGroupValue groupValue : vmsGraphicGraphicGroupValue){
		if(picto.equals(groupValue.getGraphicId())){
			return Long.parseLong(groupValue.getValue());
		}
	}
	
	return 0L;
}

def String sustituirCaracter(String textValue, List<String> cad_character, List<String> cad_ascii){
	String aux = "";
	for(int z = 0; z < cad_character.size() ; z++){
		if (textValue.indexOf(cad_character[z]) != -1){
			aux = "[hc" + cad_ascii[z] + "]";
			textValue = textValue.replace(cad_character[z],aux);
		}
	}
	return textValue;	
}

def String sustituirGraphic(Element element, Long numZone, Long picto){
	final Long PARAM_CONFIG_JSONCONFIG = 4L;
	
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