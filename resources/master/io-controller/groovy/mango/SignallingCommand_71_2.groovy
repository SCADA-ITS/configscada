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
	static final Integer PARAM_ID_1 = 1;
	static final Long PARAM_CONFIG_JSONCONFIG = 4L;
	static final Long PARAM_CONFIG_PROTOCOLO = 2L;
	static final Long PARAM_MEASURE = 2L;
	static final Long PARAM_MEASURE_BEACONS = 5L;
	static final Long PARAM_MEASURE_PIXEL_SERVICE = 6L;
	static final Long PARAM_MEASURE_ENABLED = 7L;
	
	// XIDs
	static final String CONTENT = "content";
	static final String SIGNALLING = "signalling";
	static final String MESSAGE_MULTISTRING = "message_multistring";
	static final String MESSAGE_STATUS = 'message_status';
	static final String MESSAGE_HEX = 'message_hex';
	static final String LOCALHOST = "7F000001";
	static final String MESSAGE_VOLATILE = "FFFFFF030001";
	static final String DO_SWITCH_ON = "do_switch_on";
	static final boolean VMS_SNOW_ON = true;
	static final String ACTIVE_MESSAGE = 'str_message';
	static final String XID_SHUTDOWN = "shutdown";
	
	//NTCIP protocol values
	static final Long MODIFYREQ = 6L;
	static final Long VALIDATEREQ = 7L;
	static final String NEW_LINE = "[nl]";
	static final String NEW_PAGE = "[np]";
	static final String INTERMITENCE_TAG = "[flt10o10]";
	static final String END_INTERMITENCE_TAG = "[/fl]";
	static final String FONT1 = "[fo1]";
	static final String ROAD_GRAPHIC = "[g2,2,5]";
	static final String CLOUD_GRAPHIC = "[g1,1,26]";
	static final String WHITE = "[cf255,0,0]";
	static final String RED = "[cf255,255,255]";
	static final String ROAD_TEMP_POS = "[tr24,6,0,0]";
	static final String CLOUD_TEMP_POS = "[tr24,27,0,0]";
	static final String DEGREE = "[hcB0]C";
	static final String DEGREE1_POS = "[tr48,6,0,0]";
	static final String DEGREE2_POS = "[tr48,27,0,0]";
	static final String COLOR_FONT = "[cf9]";
	static final String TEXT_ALIGN_CENTER = "[jl3]";
	static final String TEXT_ALIGN_LEFT = "[jl2]";
	static final String TEXT_ALIGN_RIGHT = "[jl4]";
	static final int AIR_TEMP = 0;
	static final int ROAD_TEMP = 1;
	static final String DEFAULT_FLASHING_ON = "defaultFlashOn"
	static final String DEFAULT_FLASHING_OFF = "defaultFlashOff"
	static final String DEFAULT_PAGEH_ON_TIME = "defaultPagehOnTime"
	static final String DEFAULT_PAGEH_OFF_TIME = "defaultPagehOffTime"
	static final Long DEFAULTPAGEHOFFTIME = 1L;
	
	//DGT protocol values
	static final int PRIMERA_ZONA = 1;
	static final int SEGUNDA_ZONA = 2;
	static final int TERCERA_ZONA = 3;
	static final int STX = 0x02;
	static final int ETX = 0x03;
	static final int ENQ = 0x05;
	static final int ACK = 0x06;
	static final int CTRL = 0x10;
	static final int FIN_LINEA = 0x0A;
	static final int FIN_TEXTO = 0x00;
	static final int SPECIAL = 0x80;
	static final int DIR_LOGICA = 0x20;
	static final int COD_MODIFICAR_PMV = 0x1A;
	static final int COD_CARGAR_MEM = 0x60;
	static final int EST_FIJO = 0x31;
	static final int EST_ALT = 0x33;
	static final int TOP_GR_TXT_GR = 0x31;
	static final int CONT_MEM = 0x31;
	static final int CONT_LIT = 0x33;
	static final int NUM_SUBPANELES_TRES = 0x33;
	static final int NUM_SUBPANELES_DOS = 0x32;
	static final int NUM_SUBPANELES_UNO = 0x31;
	static final int PRIMER_SUBPANEL = 0x31;
	static final int SEGUNDO_SUBPANEL = 0x32;
	static final int TERCER_SUBPANEL = 0x33;
	static final int INTERMITENCIA_NO = 0x4E;
	static final int NUM_LINEAS_TRES = 3;
	static final int NUM_LINEAS_UNA = 1;
	static final int PARAM_LINEA = 0x18;
	static final int LITERAL_H = 0x5E;
	static final int LITERAL_L = 0x4C;
	static final Long GRAPHIC_EMPTY = 255L;
	static final Long MEM_VOLATIL = 127L;
	static final String character = "↑,°,Á,Â,Ä,Ç,É,Ë,Í,Î,Ó,Ô,Ö,×,Ú,Ü,Ý,ß,á,â,ä,ç,é,ë,í,î,ó,ô,ö,÷,ú,ü,ý,Ă,ă,Ą,ą,Ć,ć,Č,č,Ď,ď,Đ,đ,Ę,Ĺ,ĺ,Ľ,ľ,Ł,ł,Ń,Ň,ň,Ő,ő,Ŕ,ŕ,Ř,ř,Ś,ś,Ş,ş,Š,š,Ţ,ţ,Ť,ť,Ů,ů,Ű,ű,Ź,ź,Ż,ż,ž,ˇ,˘,˙,˛,˜,˝";
	static final String ascii = "15,B0,C1,C2,C4,C7,C9,CB,CD,CE,D3,D4,D6,D7,DA,DC,DD,DF,E1,E2,E4,E7,E9,EB,ED,EE,F3,F4,F6,F7,FA,FC,FD,102,103,104,105,106,107,10C,10D,10E,10F,110,111,119,11,11B,139,13A,13D,13E,141,143,147,148,150,151,154,155,158,159,15A,15B,15E,15F,160,161,162,163,164,165,16E,16F,170,171,179,17A,17B,17C,17D,17E,2C7,2D8,2D9,2DB,2DC,2DD";
	static final Long ZERO = 0L;
			
	GroovyShell shell;
	def signallingCommandUtils;
	org.apache.logging.log4j.Logger log;
	
	SignallingCommand_71_2(org.apache.logging.log4j.Logger log) {
	
		this.log = log;
		shell = new GroovyShell();
		signallingCommandUtils = shell.parse(new File(ResourcesUtil.getPath("io-controller/groovy/mango/SignallingCommandUtils.groovy")));
	}
	
	boolean process(String dataSourceXid, SignallingCommand signallingCommand, MangoDriver driver) {
		try {
			List<XidPointValueTimeModel> xidPointValueTimeModels = new ArrayList<>();	
			ObjectMapper  mapper = new ObjectMapper();
								
			XidPointValueTimeModel xidPointValueTimeModel;		
			boolean alternance_graph = false;
			boolean alternance_text = false;
			String multi = "";
			String message = "";
			Element element = EntitiesManager.getInstance().getElement(signallingCommand.elementTypeId, signallingCommand.elementId);
			
			if (element.elementSubtypeId == 2 || element.elementSubtypeId == 17){			
				Object object = mapper.readValue(signallingCommand.signallingParams.get(0).getValue(), Zone[].class);		
				List<String> cad_character = new ArrayList();
				List<String> cad_ascii = new ArrayList();
				List<String> graphics = new ArrayList();
				List<String> texts = new ArrayList();
				
				String textValue = "";
				String textValue2 = "";
				String textValueAux = ""
				String textValueAux2 = ""
				String textAlternance = "";
				
				int zone;
				cad_character = character.split(",");
				cad_ascii = ascii.split(",");
				
				//Recorro cada zona del panel
				for(int j = 0; j < 10; j++)
					graphics.add("");
				for(int z = 0; z < 2; z++)
					texts.add("");
				for (int i = 0; i < object.size(); i++){
					if(object[i].getGraphics()){
						zone = object[i].getZone();
						for(int k = 0; k < object[i].getGraphics().size(); k++){
							graphics.set(zone - 1, getGraphic(element, object[i].getZone(), object[i].getGraphics()[k].getValue()));
							if(object[i].getGraphics()[k].getAlternance()) {
								graphics.set(zone,getGraphic(element, object[i].getZone(), object[i].getGraphics()[k].getAlternance()));
							}
						}
					}
					else if(object[i].getTexts()){
						if(object[i].getZone() == 8){
							for(int j = 0; j < object[i].getTexts().size() ; j++){
								if(j == 0 | j == object[i].getTexts().size()){
									if (object[i].getTexts()[j].getValue().size() == 5){
										textValue += "[tr155,49,0,0]";
									}else if (object[i].getTexts()[j].getValue().size() == 4){
										textValue += "[tr158,49,0,0]";
									}else if (object[i].getTexts()[j].getValue().size() == 3){
										textValue += "[tr161,49,0,0]";
									}else if (object[i].getTexts()[j].getValue().size() == 2){
										textValue += "[tr163,49,0,0]";
									}else if (object[i].getTexts()[j].getValue().size() == 1){
										textValue += "[tr167,49,0,0]";
									}
								}
								textValue += object[i].getTexts()[j].getValue();	
							}
							textValue += textValueAux;
							textValue = sustituirCaracter(textValue, cad_character, cad_ascii);
							texts.set(0,textValue);
						}else if (object[i].getZone() == 4){
							for(int j = 0; j < object[i].getTexts().size() ; j++){
								if(j == 0 | j == object[i].getTexts().size()){
									if (object[i].getTexts()[j].getValue().size() == 5){
										textValue2 +="[tr58,49,0,0]";
									}else if (object[i].getTexts()[j].getValue().size() == 4){
										textValue2 +="[tr61,49,0,0]";
									}else if (object[i].getTexts()[j].getValue().size() == 3){
										textValue2 +="[tr64,49,0,0]";
									}else if (object[i].getTexts()[j].getValue().size() == 2){
										textValue2 +="[tr67,49,0,0]";
									}else if (object[i].getTexts()[j].getValue().size() == 1){
										textValue2 +="[tr70,49,0,0]";
									}		
								}
								textValue2 += object[i].getTexts()[j].getValue();
							}
								textValue2 += textValueAux2;
								textValue2 = sustituirCaracter(textValue2, cad_character, cad_ascii);
								texts.set(1,textValue2);
							}
						}
					}
				
				if(!textAlternance.equals(""))texts.add(textAlternance);
				if (graphics){
					for (int n = 0; n < graphics.size(); n++){
						if (n == 0 && graphics.get(n) != ""){
							multi += "[g" + graphics.get(n) + ",1,1]";
						}else if(n == 1 && graphics.get(n) != ""){
							multi += "[g" + graphics.get(n) + ",1,49]";
						}else if(n == 2 && graphics.get(n) != ""){
							multi += "[g" + graphics.get(n) + ",49,1]" + texts.get(0);
						}else if(n == 4 && graphics.get(n) != ""){
							multi += "[g" + graphics.get(n) + ",97,1]";
						}else if(n == 5 && graphics.get(n) != ""){
							multi += "[g" + graphics.get(n) + ",97,49]";
						}else if(n == 6 && graphics.get(n) != ""){
							multi += "[g" + graphics.get(n) + ",145,1]" + texts.get(1);
						}else if(n == 8 && graphics.get(n) != ""){
							multi += "[g" + graphics.get(n) + ",193,1]";
						}else if(n == 9 && graphics.get(n) != ""){
							multi += "[g" + graphics.get(n) + ",193,49]";
						}
					}
				}	
				
				activarVMSNTCIP(multi, dataSourceXid, signallingCommand, driver, false, false, 255);
							}else if (element.elementSubtypeId == 1){			
				Object object = mapper.readValue(signallingCommand.signallingParams.get(0).getValue(), Zone[].class);		
				List<String> cad_character = new ArrayList();
				List<String> cad_ascii = new ArrayList();
				List<String> graphics = new ArrayList();
				List<String> texts = new ArrayList();
				
				String textValue = "";
				String textValueAux = ""
				String textAlternance = "";
				
				int zone;
				cad_character = character.split(",");
				cad_ascii = ascii.split(",");
				
				//Recorro cada zona del panel
				for(int j = 0; j < 10; j++)
					graphics.add("");
				for(int z = 0; z < 2; z++)
					texts.add("");
				for (int i = 0; i < object.size(); i++){
					if(object[i].getGraphics()){
						zone = object[i].getZone();
						for(int k = 0; k < object[i].getGraphics().size(); k++){
							graphics.set(zone - 1, getGraphic(element, object[i].getZone(), object[i].getGraphics()[k].getValue()));
							if(object[i].getGraphics()[k].getAlternance()) {
								graphics.set(zone,getGraphic(element, object[i].getZone(), object[i].getGraphics()[k].getAlternance()));
							}
						}
					}else if(object[i].getTexts()){
						if(object[i].getZone() == 4){
							for(int j = 0; j < object[i].getTexts().size() ; j++){
								if(j == 0 | j == object[i].getTexts().size()){
									if (object[i].getTexts()[j].getValue().size() == 5){
										textValue +="[tr58,49,0,0]";
									}else if (object[i].getTexts()[j].getValue().size() == 4){
										textValue +="[tr61,49,0,0]";
									}else if (object[i].getTexts()[j].getValue().size() == 3){
										textValue +="[tr64,49,0,0]";
									}else if (object[i].getTexts()[j].getValue().size() == 2){
										textValue +="[tr67,49,0,0]";
									}else if (object[i].getTexts()[j].getValue().size() == 1){
										textValue +="[tr70,49,0,0]";
									}	
								}
								textValue += object[i].getTexts()[j].getValue();
							}
									
						}
							textValue += textValueAux;
							textValue = sustituirCaracter(textValue, cad_character, cad_ascii);
							texts.set(0,textValue);
					}
				}
					
				if(!textAlternance.equals(""))texts.add(textAlternance);
				if (graphics){
					for (int n = 0; n < graphics.size(); n++){
						if (n == 0 && graphics.get(n) != ""){
							multi += "[g" + graphics.get(n) + ",1,1]";
						}else if(n == 1 && graphics.get(n) != ""){
							multi += "[g" + graphics.get(n) + ",1,49]";
						}else if(n == 2 && graphics.get(n) != ""){
							multi += "[g" + graphics.get(n) + ",49,1]" + texts.get(0);
						}else if(n == 4 && graphics.get(n) != ""){
							multi += "[g" + graphics.get(n) + ",97,1]";
						}else if(n == 5 && graphics.get(n) != ""){
							multi += "[g" + graphics.get(n) + ",97,49]";
						}
					}
				}
				activarVMSNTCIP(multi, dataSourceXid, signallingCommand, driver, false, false, 255);
			}else if (element.elementSubtypeId == 3){			
				Object object = mapper.readValue(signallingCommand.signallingParams.get(0).getValue(), Zone[].class);		
				List<String> cad_character = new ArrayList();
				List<String> cad_ascii = new ArrayList();
				List<String> graphics = new ArrayList();
				List<String> texts = new ArrayList();
				
				String textValue = "";
				String textValueAux = ""
				String textAlternance = "";
				
				int zone;
				cad_character = character.split(",");
				cad_ascii = ascii.split(",");
				
				//Recorro cada zona del panel
				for(int j = 0; j < 10; j++)
					graphics.add("");
				for(int z = 0; z < 2; z++)
					texts.add("");
				for (int i = 0; i < object.size(); i++){
					if(object[i].getGraphics()){
						zone = object[i].getZone();
						for(int k = 0; k < object[i].getGraphics().size(); k++){
							graphics.set(zone - 1, getGraphic(element, object[i].getZone(), object[i].getGraphics()[k].getValue()));
							if(object[i].getGraphics()[k].getAlternance()) {
								graphics.set(zone,getGraphic(element, object[i].getZone(), object[i].getGraphics()[k].getAlternance()));
							}
						}
					}else if(object[i].getTexts()){
						if(object[i].getZone() == 2){
							for(int j = 0; j < object[i].getTexts().size() ; j++){
								if(j == 0 | j == object[i].getTexts().size()){
									if (object[i].getTexts()[j].getValue().size() == 5){
										textValue +="[tr58,49,0,0]";
									}else if (object[i].getTexts()[j].getValue().size() == 4){
										textValue +="[tr61,49,0,0]";
									}else if (object[i].getTexts()[j].getValue().size() == 3){
										textValue +="[tr64,49,0,0]";
									}else if (object[i].getTexts()[j].getValue().size() == 2){
										textValue +="[tr67,49,0,0]";
									}else if (object[i].getTexts()[j].getValue().size() == 1){
										textValue +="[tr70,49,0,0]";
									}	
								}
								textValue += object[i].getTexts()[j].getValue();
							}
									
						}
							textValue += textValueAux;
							textValue = sustituirCaracter(textValue, cad_character, cad_ascii);
							texts.set(0,textValue);
					}
				}
					
				if(!textAlternance.equals(""))texts.add(textAlternance);
				if (graphics){
					for (int n = 0; n < graphics.size(); n++){
						if (n == 0 && graphics.get(n) != ""){
							multi += "[g" + graphics.get(n) + ",1,1]";
						}else if(n == 3 && graphics.get(n) != ""){
							multi += "[g" + graphics.get(n) + ",1,49]";
						}else if(n == 2 && graphics.get(n) != ""){
							multi += "[g" + graphics.get(n) + ",49,1]" + texts.get(0);
						}
						}
					
					}
			
					activarVMSNTCIP(multi, dataSourceXid, signallingCommand, driver, false, false, 255);
			}else if (element.elementSubtypeId == 4){
				Object object = mapper.readValue(signallingCommand.signallingParams.get(0).getValue(), Zone[].class);		
				
				List<String> graphics = new ArrayList();
				List<String> texts = new ArrayList();
				List<String> cad_character = new ArrayList();
				List<String> cad_ascii = new ArrayList();
				
				String textValue = "";
				String textValueAux = ""
				String textAlternance = "";
				cad_character = character.split(",");
				cad_ascii = ascii.split(",");
				
				//Recorro cada zona del panel
				for (int i = 0; i < object.size(); i++){
					if(object[i].getGraphics()){
						for(int k = 0; k < object[i].getGraphics().size(); k++){					
							graphics.add(getGraphic(element, 1, object[i].getGraphics()[k].getValue()));
							if(object[i].getGraphics()[k].getAlternance()) {
								graphics.add(getGraphic(element, i+1, object[i].getGraphics()[k].getAlternance()));
							}
						}
					}
					else if(object[i].getTexts()){
						if( object[i].getZone() == 2){
							for(int j = 0; j < object[i].getTexts().size() ; j++){
								if(j == 0 | j == object[i].getTexts().size() ){
									textValue+="[fo1][tr45,1,0,0]";
								}
								if (j != object[i].getTexts().size() ){
									textValue += object[i].getTexts()[j].getValue();
									if(j < object[i].getTexts().size() - 1){
										textValue+=NEW_LINE;
									}
								}
								else {
									textValue += object[i].getTexts()[j].getValue();
								}		
							}
						}
						else if( object[i].getZone() == 3){
							for(int j = 0; j < object[i].getTexts().size() ; j++){
								if(j == 0 | j == object[i].getTexts().size() ){
										textValueAux+="[fo2][tr18,33,0,0]";
									}
									if (j != object[i].getTexts().size() ){
										textValueAux += object[i].getTexts()[j].getValue();
										if(j < object[i].getTexts().size() - 1){
											textValueAux+=NEW_LINE;
										}
									}
									else {
										textValueAux += object[i].getTexts()[j].getValue();
								}
							}
						}		
					}
				}
				
				textValue += textValueAux;
				textValue = sustituirCaracter(textValue, cad_character, cad_ascii);
				texts.add(textValue);
				
				if(!textAlternance.equals(""))texts.add(textAlternance);
				if (graphics){
					multi = "[g" + graphics.get(0) + ",1,1]" + texts.get(0);
				}else{
					multi = texts.get(0);
				}
			
				if(texts.size() > 1) multi += texts.get(1);
						
				activarVMSNTCIP(multi, dataSourceXid, signallingCommand, driver, false, false, 255);
						
			}else if (element.elementSubtypeId == 5){
				List<String> cad_character = new ArrayList();
				List<String> cad_ascii = new ArrayList();
				cad_character = character.split(",");
				cad_ascii = ascii.split(",");
				Object object = mapper.readValue(signallingCommand.signallingParams.get(0).getValue(), Zone[].class);
				//Recorro cada zona del panel
				for (int i = 0; i < object.size(); i++){
					if(object[i].getTexts()){
						multi = "[fo1][tr1,1,0,0]"
						for(int j = 0; j < object[i].getTexts().size(); j++){
							if (j > 0){
								multi += "[nl]"
							}
							multi +=  object[i].getTexts()[j].getValue()
							multi = sustituirCaracter(multi, cad_character, cad_ascii);
						}
					}
					else if(object[i].getGraphics()){
						multi = sustituirGraphic(element, 1, object[i].getGraphics()[0].getValue());
					}	
				}
				activarVMSNTCIP(multi, dataSourceXid, signallingCommand, driver, false, false, 255);
			}else if (element.elementSubtypeId == 6){
				Object object = mapper.readValue(signallingCommand.signallingParams.get(0).getValue(), Zone[].class);		
				
				List<String> graphics = new ArrayList();
				String textAlternance = "";
				
				//Recorro cada zona del panel
				for (int i = 0; i < object.size(); i++){
					if(object[i].getGraphics()){
						for(int k = 0; k < object[i].getGraphics().size(); k++){						
							graphics.add(getGraphic(element, i+1, object[i].getGraphics()[k].getValue()));
							if(object[i].getGraphics()[k].getAlternance()) {
								graphics.add(getGraphic(element, i+1, object[i].getGraphics()[k].getAlternance()));
							}
						}
					}
					
				}
				if (graphics){
					multi = "[g" + graphics.get(0) + ",1,1]";
				}
							
				activarVMSNTCIP(multi, dataSourceXid, signallingCommand, driver, false, false, 255);					
			}else if (element.elementSubtypeId == 7){
				List<String> values = signallingCommand.signallingParams.get(0).getValue().split("#");
				multi = ROAD_GRAPHIC + FONT1;
				if(Double.parseDouble(values.get(AIR_TEMP)) < 0){
					multi += RED;
				}else{
					multi += WHITE;
				}
				multi+= CLOUD_TEMP_POS + String.valueOf((int)Double.parseDouble(values.get(AIR_TEMP))) + WHITE + DEGREE1_POS + DEGREE + CLOUD_GRAPHIC + FONT1;
				if(Double.parseDouble(values.get(ROAD_TEMP)) < 0){
					multi += RED;
				}else{
					multi += WHITE;
				}
				multi+= ROAD_TEMP_POS + String.valueOf((int)Double.parseDouble(values.get(ROAD_TEMP))) + WHITE + DEGREE2_POS + DEGREE;
				
				ElementValue enable = EntitiesManager.getInstance().getElementValue(element, PARAM_MEASURE_ENABLED, PARAM_MEASURE);
				
				if(enable.getValue()){
					activarVMSNTCIP(multi, dataSourceXid, signallingCommand, driver, false, false, 255);
				}
			}else if (element.elementSubtypeId == 9){
				Object object = mapper.readValue(signallingCommand.signallingParams.get(0).getValue(), Zone[].class);		
				
				xidPointValueTimeModel = signallingCommandUtils.getXidPointValueTimeModel(signallingCommand, dataSourceXid + "_" + XID_SHUTDOWN, "1");
				xidPointValueTimeModels.add(xidPointValueTimeModel);
				
				mapper.setSerializationInclusion(Include.NON_NULL);
				message = mapper.writeValueAsString(xidPointValueTimeModels);
	
				if (driver != null) {
	
					driver.send(message);
				}
				sleep(4000);
				for (int i = 0; i < object.size(); i++){
					if(object[i].getTexts()){
						for(int j = 0; j < object[i].getTexts().size(); j++){
							if(object[i].getTexts()[j].getAlternance()) {
								alternance_text = true;
							}
						}
					}
					if(object[i].getGraphics()){
						for(int k = 0; k < object[i].getGraphics().size(); k++){
							if(object[i].getGraphics()[k].getAlternance()) {
								alternance_graph = true;
							}
						}
					}
				}
				//Recorro cada zona del panel
				for (int i = 0; i < object.size(); i++){
					if(object[i].getTexts()){
						for(int j = 0; j < object[i].getTexts().size(); j++){
							if(object[i].getTexts()[j].getAlternance()) {
								xidPointValueTimeModel = signallingCommandUtils.getXidPointValueTimeModel(signallingCommand, dataSourceXid + "_l1" + String.valueOf(j+1) + "_alt" , object[i].getTexts()[j].getValue());
								xidPointValueTimeModels.add(xidPointValueTimeModel);
								xidPointValueTimeModel = signallingCommandUtils.getXidPointValueTimeModel(signallingCommand, dataSourceXid + "_l2" + String.valueOf(j+1) +"_alt", object[i].getTexts()[j].getAlternance());
								xidPointValueTimeModels.add(xidPointValueTimeModel);
							} else if(alternance_graph) {
								xidPointValueTimeModel = signallingCommandUtils.getXidPointValueTimeModel(signallingCommand, dataSourceXid + "_l1" + String.valueOf(j+1) + "_alt" , object[i].getTexts()[j].getValue());
								xidPointValueTimeModels.add(xidPointValueTimeModel);
								xidPointValueTimeModel = signallingCommandUtils.getXidPointValueTimeModel(signallingCommand, dataSourceXid + "_l2" + String.valueOf(j+1) + "_alt" , "");
								xidPointValueTimeModels.add(xidPointValueTimeModel);
							}
							else {
								xidPointValueTimeModel = signallingCommandUtils.getXidPointValueTimeModel(signallingCommand, dataSourceXid + "_l1" + String.valueOf(j+1) , object[i].getTexts()[j].getValue());
								xidPointValueTimeModels.add(xidPointValueTimeModel);
							}
						}
					}
					if(object[i].getGraphics()){
						for(int k = 0; k < object[i].getGraphics().size(); k++){
						
							Long picto = getGraphic(element, i+1, object[i].getGraphics()[k].getValue());
							if(object[i].getGraphics()[k].getAlternance()) {
								xidPointValueTimeModel = signallingCommandUtils.getXidPointValueTimeModel(signallingCommand, dataSourceXid + "_p" + String.valueOf(k+1) +"_alt", "-" + picto.toString()+".bmp");
								xidPointValueTimeModels.add(xidPointValueTimeModel);
								picto = getGraphic(element, i+1, object[i].getGraphics()[k].getAlternance());
								xidPointValueTimeModel = signallingCommandUtils.getXidPointValueTimeModel(signallingCommand, dataSourceXid + "_p" + String.valueOf(k+2) +"_alt", "-" + picto.toString() +".bmp");
								xidPointValueTimeModels.add(xidPointValueTimeModel);
							}else if (alternance_text){
								xidPointValueTimeModel = signallingCommandUtils.getXidPointValueTimeModel(signallingCommand, dataSourceXid + "_p" + String.valueOf(k+1) +"_alt", "-" + picto.toString()+".bmp");
								xidPointValueTimeModels.add(xidPointValueTimeModel);
								xidPointValueTimeModel = signallingCommandUtils.getXidPointValueTimeModel(signallingCommand, dataSourceXid + "_p" + String.valueOf(k+2) +"_alt", "-" + picto.toString()+".bmp");
								xidPointValueTimeModels.add(xidPointValueTimeModel);
							} else {
								xidPointValueTimeModel = signallingCommandUtils.getXidPointValueTimeModel(signallingCommand, dataSourceXid + "_p" + String.valueOf(k+1) , "-" + picto.toString()+".bmp");
								xidPointValueTimeModels.add(xidPointValueTimeModel);
							}
						}
					}
				}
			
				mapper.setSerializationInclusion(Include.NON_NULL);
				message = mapper.writeValueAsString(xidPointValueTimeModels);
	
				if (driver != null) {
	
					driver.send(message);
				}
			}else if (element.elementSubtypeId == 11 || element.elementSubtypeId == 12){
				ElementValue protocolo = EntitiesManager.getInstance().getElementValueConfig(element, PARAM_CONFIG_PROTOCOLO);
				
				if(protocolo != null && protocolo.getValue().equals("DGT")){
					String resultado = dgt_af_clv(element, signallingCommand);
					
					xidPointValueTimeModel = signallingCommandUtils.getXidPointValueTimeModel(signallingCommand, dataSourceXid + "_" +  SIGNALLING, resultado);
					xidPointValueTimeModels.add(xidPointValueTimeModel);
				}else{
					Object object = mapper.readValue(signallingCommand.signallingParams.get(0).getValue(), Zone[].class);		
				//Recorro cada zona del panel
					for (int i = 0; i < object.size(); i++){
						if(object[i].getGraphics()){
							if (object[i].getFlashing_on_ms()){
								for(int k = 0; k < object[i].getGraphics().size(); k++){		
								xidPointValueTimeModel = signallingCommandUtils.getXidPointValueTimeModel(signallingCommand, dataSourceXid + "_" +  CONTENT, (getGraphic(element, i+1, object[i].getGraphics()[k].getValue()) + 256));
								xidPointValueTimeModels.add(xidPointValueTimeModel);
								}
							}else{
								for(int k = 0; k < object[i].getGraphics().size(); k++){		
								xidPointValueTimeModel = signallingCommandUtils.getXidPointValueTimeModel(signallingCommand, dataSourceXid + "_" +  CONTENT, getGraphic(element, i+1, object[i].getGraphics()[k].getValue()));
								xidPointValueTimeModels.add(xidPointValueTimeModel);
								}	
							}
						}else{
							xidPointValueTimeModel = signallingCommandUtils.getXidPointValueTimeModel(signallingCommand, dataSourceXid + "_" +  CONTENT, ZERO);
							xidPointValueTimeModels.add(xidPointValueTimeModel);
						}
					}
				}
			
				mapper.setSerializationInclusion(Include.NON_NULL);
				message = mapper.writeValueAsString(xidPointValueTimeModels);
				
				if (driver != null) {
				
					driver.send(message);
				}
			}else if (element.elementSubtypeId == 16){
				boolean beaconsB, pixelServiceB;
				ElementValue beacons = EntitiesManager.getInstance().getElementValue(element, PARAM_MEASURE_BEACONS, PARAM_MEASURE);
				ElementValue pixelService = EntitiesManager.getInstance().getElementValue(element, PARAM_MEASURE_PIXEL_SERVICE, PARAM_MEASURE);
				ElementValue protocolo = EntitiesManager.getInstance().getElementValueConfig(element, PARAM_CONFIG_PROTOCOLO);

				if(protocolo != null && protocolo.getValue().equals("DGT")){
					String resultado = dgt_txt(element, signallingCommand);
					
					xidPointValueTimeModel = signallingCommandUtils.getXidPointValueTimeModel(signallingCommand, dataSourceXid + "_" +  SIGNALLING, resultado);
					xidPointValueTimeModels.add(xidPointValueTimeModel);
					
					mapper.setSerializationInclusion(Include.NON_NULL);
	
					message = mapper.writeValueAsString(xidPointValueTimeModels);
	
					if (driver != null) {
		
						driver.send(message);
					}
				}else{
					Object object = mapper.readValue(signallingCommand.signallingParams.get(0).getValue(), Zone[].class);		
					List<String> texts = new ArrayList();
					String textValue = "";
					String textAlternance = "";
					String space = "";
					String spaceAlternate = "";
					//Recorro cada zona del panel
					for (int i = 0; i < object.size(); i++){
						if(object[i].getTexts()){
							for(int j = 0; j < object[i].getTexts().size(); j++){
								
								textValue+=TEXT_ALIGN_LEFT
								int centerText = (16 - object[i].getTexts()[j].getValue().size()) / 2
								for (int n = 0; n < centerText; n++){
									space = space + ' '
								}
								
								textValue +=  "[tr1,1]" + space + object[i].getTexts()[j].getValue();
								if(j < object[i].getTexts().size() - 1){
									textValue+=NEW_LINE;
								}		
								if(object[i].getTexts()[j].getAlternance()) {

									textAlternance+=TEXT_ALIGN_LEFT
									int centerTextAlternate = (16 - object[i].getTexts()[j].getAlternance().size()) / 2
									for (int n = 0; n < centerTextAlternate; n++){
										spaceAlternate = spaceAlternate + ' '
									}
								
									textAlternance += spaceAlternate +object[i].getTexts()[j].getAlternance();
									if(j < object[i].getTexts().size() - 1){
										textAlternance+=NEW_LINE;
									}
							}
						}
					}
				}
					texts.add(textValue); 
					if(!textAlternance.equals(""))texts.add(textAlternance);
					multi = texts.get(0);
					if(texts.size() > 1) multi += NEW_PAGE + texts.get(1);	
					xidPointValueTimeModel = signallingCommandUtils.getXidPointValueTimeModel(signallingCommand, dataSourceXid + "_" +  CONTENT, multi);
					xidPointValueTimeModels.add(xidPointValueTimeModel);
					mapper.setSerializationInclusion(Include.NON_NULL);
					message = mapper.writeValueAsString(xidPointValueTimeModels);
					if (driver != null) {
					
						driver.send(message);
					}
					if(beacons == null){
						beaconsB = false;
					}else{
						beaconsB = Boolean.parseBoolean(beacons.getValue())
					}
					if(pixelService == null){
						pixelServiceB = false;
					}else{
						pixelServiceB ="1".equals(pixelService.getValue())
					}

					activarVMSNTCIP(multi, dataSourceXid, signallingCommand, driver, pixelServiceB, beaconsB, 255);
				}
			}else if (element.elementSubtypeId == 18){
				String resultado = dgt_gr_txt(element, signallingCommand, dataSourceXid, driver);
				
				xidPointValueTimeModel = signallingCommandUtils.getXidPointValueTimeModel(signallingCommand, dataSourceXid + "_" +  SIGNALLING, resultado);
				xidPointValueTimeModels.add(xidPointValueTimeModel);
							
				mapper.setSerializationInclusion(Include.NON_NULL);
				message = mapper.writeValueAsString(xidPointValueTimeModels);
	
				if (driver != null) {

					driver.send(message);
				}
			}else if (element.elementSubtypeId == 19){
				boolean beaconsB, pixelServiceB;
				Object object = mapper.readValue(signallingCommand.signallingParams.get(0).getValue(), Zone[].class);		
				ElementValue beacons = EntitiesManager.getInstance().getElementValue(element, PARAM_MEASURE_BEACONS, PARAM_MEASURE);
				ElementValue pixelService = EntitiesManager.getInstance().getElementValue(element, PARAM_MEASURE_PIXEL_SERVICE, PARAM_MEASURE);
				
				String multi_main_graphic=""; String multi_alternate_graphic="";
				String multi_main_text=""; String multi_alternate_text="";
				String message_aux, align;
				Long flashOn, flashOff, defaultPagehOnTime;
				int n = 0;
				//Recorro cada zona del panel
				for (int i = 0; i < object.size(); i++){
					if(object[i].getAlternance_ms()){
						defaultPagehOnTime = object[i].getAlternance_ms();
						defaultPagehOnTime = defaultPagehOnTime * 0.01;
						xidPointValueTimeModel = signallingCommandUtils.getXidPointValueTimeModel(signallingCommand, 
						dataSourceXid + "_" + DEFAULT_PAGEH_ON_TIME, defaultPagehOnTime);
				
						xidPointValueTimeModels.clear();		
						xidPointValueTimeModels.add(xidPointValueTimeModel);
						message = mapper.writeValueAsString(xidPointValueTimeModels);
						if (driver != null) {
							driver.send(message);
						}
						
						xidPointValueTimeModel = signallingCommandUtils.getXidPointValueTimeModel(signallingCommand, 
						dataSourceXid + "_" + DEFAULT_PAGEH_OFF_TIME, DEFAULTPAGEHOFFTIME);
				
						xidPointValueTimeModels.clear();		
						xidPointValueTimeModels.add(xidPointValueTimeModel);
						message = mapper.writeValueAsString(xidPointValueTimeModels);
						if (driver != null) {
							driver.send(message);
						}
					}
					if(object[i].getGraphics()){
						if(object[i].getFlashing_on_ms()){
							flashOn = object[i].getFlashing_on_ms();
							flashOff = object[i].getFlashing_off_ms();
							flashOn = flashOn * 0.01;
							flashOff = flashOff * 0.01;
						
							xidPointValueTimeModel = signallingCommandUtils.getXidPointValueTimeModel(signallingCommand, 
							dataSourceXid + "_" + DEFAULT_FLASHING_OFF, flashOff);
				
							xidPointValueTimeModels.clear();		
							xidPointValueTimeModels.add(xidPointValueTimeModel);
							message = mapper.writeValueAsString(xidPointValueTimeModels);
							if (driver != null) {
							driver.send(message);
							}
							multi_main_graphic += "[flt" + flashOn + "o" + flashOff + "]";
						}
						for(int k = 0; k < object[i].getGraphics().size(); k++){	
							multi_main_graphic += "[g" + getGraphic(element, object[i].getZone(), object[i].getGraphics()[k].getValue()) + ",1,1]"	
							if(object[i].getGraphics()[k].getAlternance()) {
								multi_alternate_graphic += "[g" + getGraphic(element, object[i].getZone(), object[i].getGraphics()[k].getAlternance()) + ",1,1]";
							}
						}
						if(object[i].getFlashing_on_ms()){
							multi_main_graphic+=(END_INTERMITENCE_TAG);
						}
					}
					if(object[i].getTexts()){
						for(int j = 0; j < object[i].getTexts().size(); j++){
							if(object[i].getFlashing_on_ms() && j == 0){
								flashOn = object[i].getFlashing_on_ms();
								flashOff = object[i].getFlashing_off_ms();
								flashOn = flashOn * 0.01;
								flashOff = flashOff * 0.01;
								xidPointValueTimeModel = signallingCommandUtils.getXidPointValueTimeModel(signallingCommand, 
								dataSourceXid + "_" + DEFAULT_FLASHING_ON, flashOn);
				
								xidPointValueTimeModels.clear();		
								xidPointValueTimeModels.add(xidPointValueTimeModel);
								message = mapper.writeValueAsString(xidPointValueTimeModels);
								if (driver != null) {
									driver.send(message);
								}
								xidPointValueTimeModel = signallingCommandUtils.getXidPointValueTimeModel(signallingCommand, 
								dataSourceXid + "_" + DEFAULT_FLASHING_OFF, flashOff);
				
								xidPointValueTimeModels.clear();		
								xidPointValueTimeModels.add(xidPointValueTimeModel);
								message = mapper.writeValueAsString(xidPointValueTimeModels);
								if (driver != null) {
									driver.send(message);
								}
								multi_main_text+= "[flt" + flashOn + "o" + flashOff + "]";
							}
							if(object[i].getAlign()){
								if(object[i].getAlign() == "center"){
									multi_main_text+=TEXT_ALIGN_CENTER
								}else if(object[i].getAlign() == "right"){
									multi_main_text+=TEXT_ALIGN_RIGHT
								}else if(object[i].getAlign() == "left"){
									multi_main_text+=TEXT_ALIGN_LEFT
								}
							}else{
								multi_main_text+=TEXT_ALIGN_CENTER
							}
							multi_main_text += object[i].getTexts()[j].getValue();
							if(j < object[i].getTexts().size() - 1){
								multi_main_text += NEW_LINE;
							}	
							if(j < object[i].getTexts().size() - 1 && !(object[i].getTexts()[j].getAlternance())){
									multi_alternate_text += NEW_LINE;
							}
							if(object[i].getTexts()[j].getAlternance()) {
								if(object[i].getAlign()){
									if(object[i].getAlign() == "center"){
										multi_alternate_text+=TEXT_ALIGN_CENTER
									}else if(object[i].getAlign() == "right"){
										multi_alternate_text+=TEXT_ALIGN_RIGHT
									}else if(object[i].getAlign() == "left"){
										multi_alternate_text+=TEXT_ALIGN_LEFT
									}
								}else{
									multi_alternate_text+=TEXT_ALIGN_CENTER
								}
								multi_alternate_text += object[i].getTexts()[j].getAlternance();
								if(j < object[i].getTexts().size() - 1){
									multi_alternate_text += NEW_LINE;
								}
								n += 1;
							}						
							if(object[i].getFlashing_on_ms() && j == (object[i].getTexts().size() - 1)){
								multi_main_text+=(END_INTERMITENCE_TAG);
							}
						}
					}
				}
				if (n == 0) {
					multi_alternate_text = "";
				}
				if(multi_alternate_text.equals("") && multi_alternate_graphic.equals("")){
					multi = multi_main_graphic + multi_main_text;
				}else if(multi_alternate_graphic && multi_alternate_text.equals("")){
					multi = multi_main_graphic + NEW_PAGE + multi_alternate_graphic;
				}else if(multi_alternate_text && multi_alternate_graphic.equals("")){
					multi = multi_main_text + NEW_PAGE + multi_alternate_text;
					
				}else{	
					multi = multi_main_graphic + multi_main_text + NEW_PAGE + multi_alternate_graphic + multi_alternate_text;
				}	
				if(beacons == null){
					beaconsB = false;
				}else{
					beaconsB = Boolean.parseBoolean(beacons.getValue())
				}
				
				if(pixelService == null){
					pixelServiceB = false;
				}else{
					pixelServiceB = Boolean.parseBoolean(pixelService.getValue())
				}
				activarVMSNTCIP(multi, dataSourceXid, signallingCommand, driver, pixelServiceB, beaconsB, 255);
			}else if (element.elementSubtypeId == 20 || element.elementSubtypeId == 21){
				Object object = mapper.readValue(signallingCommand.signallingParams.get(0).getValue(), Zone[].class);		
				boolean beaconsB, pixelServiceB;
				
				ElementValue beacons = EntitiesManager.getInstance().getElementValue(element, PARAM_MEASURE_BEACONS, PARAM_MEASURE);
				ElementValue pixelService = EntitiesManager.getInstance().getElementValue(element, PARAM_MEASURE_PIXEL_SERVICE, PARAM_MEASURE);
				
				String multi_main=""; String multi_alternate="";
				
				//Recorro cada zona del panel
				for (int i = 0; i < object.size(); i++){
					if(object[i].getTexts()){
						for(int j = 0; j < object[i].getTexts().size(); j++){
							multi_main+=object[i].getTexts()[j].getValue();
							if(j < object[i].getTexts().size() - 1){
								multi_main+=NEW_LINE;
							}		
							if(object[i].getTexts()[j].getAlternance()) {
								multi_alternate += object[i].getTexts()[j].getAlternance();
								if(j < object[i].getTexts().size() - 1){
									multi_alternate+=NEW_LINE;
								}
							}
						}
					}
				}
				
				if(multi_alternate.equals("")){
					multi = COLOR_FONT + multi_main;
				}else{
					multi = COLOR_FONT + multi_main + NEW_PAGE + multi_alternate;
				}
				
				if(beacons == null){
					beaconsB = false;
				}else{
					beaconsB = Boolean.parseBoolean(beacons.getValue())
				}
				
				if(pixelService == null){
					pixelServiceB = false;
				}else{
					pixelServiceB = Boolean.parseBoolean(pixelService.getValue())
				}
				
				activarVMSNTCIP(multi, dataSourceXid, signallingCommand, driver, beaconsB, pixelServiceB, 5);
			}else if (element.elementSubtypeId == 22){					
				String resultado = dgt_gr_txt_gr(element, signallingCommand, dataSourceXid, driver);
								
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
	
		//SUBTYPES FUNCTIONS
	//Subtype 11/12
	public String dgt_af_clv(Element element, SignallingCommand signallingCommand){	
		ObjectMapper mapper = new ObjectMapper();	
		Object object = mapper.readValue(signallingCommand.signallingParams.get(0).getValue(), Zone[].class);	
	
		List<Byte> data = new ArrayList<Byte>();
		List<Byte> aux_text = new ArrayList<Byte>();
		String resultado = "";
		
		data.add(DIR_LOGICA); 			//Dir. Logica del panel. Habitualmente la 0x20 = 32Dec
		data.add(COD_MODIFICAR_PMV);	//Codigo de protocolo para modificar señalización = 0x1A			
		data.add(EST_FIJO);				//Utilizaremos una señalización fija			
		data.add(TOP_GR_TXT_GR);		//Utilizamos topologia GR + TXT + GR siempre, dejando sin rellenar las zonas que no necesitemos o no tengamos
		data.add(NUM_SUBPANELES_UNO);	//Este panel tiene 2 subpaneles = GR + TXT
		data.add(PRIMER_SUBPANEL);		//Empezamos rellenando el primer subpanel
		data.add(CONT_MEM);				//El primer subpanel es de tipo grafico, ponemos que el contenido es por tanto una posición de mem del panel
		data.add(getGraphic(element,0,object[0].getGraphics()[0].getValue()));
		data.add(INTERMITENCIA_NO);		//No queremos que el gráfico tenga intermitencia
		
		data.addAll(CRC_XModem(data)); 	//Calculo el CRC
		data = controlCharacters(data);	//Transformo los caracteres especiales del protocolo 
		
		//Añado los caracteres de principio y fin		
		data.add(0, STX);
		data.add(ETX);
		
		for(byte aux : data){
			resultado += String.format("%02X", aux);
		}
		
		return resultado;
	}
	
	//Subtype 16
	public String dgt_txt(Element element, SignallingCommand signallingCommand){
		ObjectMapper mapper = new ObjectMapper();	
		Object object = mapper.readValue(signallingCommand.signallingParams.get(0).getValue(), Zone[].class);	
		
		List<Byte> data = new ArrayList<Byte>();
		List<Byte> aux_text = new ArrayList<Byte>();
		String resultado = "";
		int ini_texto; int tam_texto;
		boolean alternance = false;
		
		data.add(DIR_LOGICA); 			//Dir. Logica del panel. Habitualmente la 0x20 = 32Dec
		data.add(COD_MODIFICAR_PMV);	//Codigo de protocolo para modificar señalización = 0x1A
		
		//Compruebo si tengo alternancia
		for (int i = 0; i < object.size(); i++){
			if(object[i].getTexts()){
				for(int j = 0; j < object[i].getTexts().size(); j++){	
					if(object[i].getTexts()[j].getAlternance()) {
						alternance = true;
					}
				}
			}
		}
		
		if (alternance){
			data.add(EST_ALT);				//Utilizaremos una señalización alternante
		}else{
			data.add(EST_FIJO);				//Utilizaremos una señalización fija
		}
		data.add(TOP_GR_TXT_GR);		//Utilizamos topologia GR + TXT + GR siempre, dejando sin rellenar las zonas que no necesitemos o no tengamos
		data.add(NUM_SUBPANELES_UNO);	//Este panel tiene 1 subpaneles = TXT					
		data.add(SEGUNDO_SUBPANEL);		//Rellenamos el segundo subpanel
		data.add(CONT_LIT);				//Contenido Literal = Texto
		
		ini_texto = data.size();
		
		data.add(0x00);					//Tamaño del texto
		data.add(LITERAL_H);			//Indicamos el tag de texto libre (^L). Corresponde con el "^"
		data.add(LITERAL_L);			//Indicamos el tag de texto libre (^L). Corresponde con la "L"
		data.add(NUM_LINEAS_UNA);		//Panel de 1 lineas
		data.add(PARAM_LINEA);			//Ponemos por defecto que las lineas sean de color ambar = 0x18
		
		//Añado el texto a señalizar, linea a linea
		for (int i = 0; i < object.size(); i++){
			if(object[i].getTexts()){
				for(int j = 0; j < object[i].getTexts().size(); j++){
						aux_text.addAll(object[i].getTexts()[j].getValue().getBytes("Cp437"));
					if(j < object[i].getTexts().size()-1){
						aux_text.add(FIN_LINEA);
					}		
				}
			}
		}
		aux_text.add(FIN_TEXTO);
		
		data.addAll(aux_text);	
		
		//Coloco el tamaño del texto
		tam_texto = data.size() - ini_texto - 1; 
		data.set(ini_texto, tam_texto); 
		
		if(alternance){
			aux_text.clear();
			data.add(SEGUNDO_SUBPANEL);		//Rellenamos el segundo subpanel
			data.add(CONT_LIT);				//Contenido Literal = Texto
			
			ini_texto = data.size();
			
			data.add(0x00);					//Tamaño del texto
			data.add(LITERAL_H);			//Indicamos el tag de texto libre (^L). Corresponde con el "^"
			data.add(LITERAL_L);			//Indicamos el tag de texto libre (^L). Corresponde con la "L"
			data.add(NUM_LINEAS_UNA);		//Panel de 3 lineas
			data.add(PARAM_LINEA);			//Ponemos por defecto que las lineas sean de color ambar = 0x18
			
			//Añado el texto a señalizar, linea a linea
			for (int i = 0; i < object.size(); i++){
				if(object[i].getTexts()){
					for(int j = 0; j < object[i].getTexts().size(); j++){
						if(object[i].getTexts()[j].getAlternance()) {
							aux_text.addAll(object[i].getTexts()[j].getAlternance().getBytes("Cp437"));
						}
						if(j < object[i].getTexts().size()-1){
							aux_text.add(FIN_LINEA);
						}		
					}
				}
			}
			aux_text.add(FIN_TEXTO);
			
			data.addAll(aux_text);	
		
			//Coloco el tamaño del texto
			tam_texto = data.size() - ini_texto - 1; 
			data.set(ini_texto, tam_texto); 
		}
		
		data.addAll(CRC_XModem(data)); 	//Calculo el CRC
		data = controlCharacters(data);	//Transformo los caracteres especiales del protocolo 
		
		//Añado los caracteres de principio y fin		
		data.add(0, STX);
		data.add(ETX);
		
		for(byte aux : data){
			resultado += String.format("%02X", aux);
		}
		
		return resultado;
	}
	
	
	//Subtype 22
	public String dgt_gr_txt_gr(Element element, SignallingCommand signallingCommand, String dataSourceXid, MangoDriver driver){
		ObjectMapper mapper = new ObjectMapper();	
		List<XidPointValueTimeModel> xidPointValueTimeModels = new ArrayList<>();	
		XidPointValueTimeModel xidPointValueTimeModel;	
		Object object = mapper.readValue(signallingCommand.signallingParams.get(0).getValue(), Zone[].class);	
		
		List<Byte> data = new ArrayList<Byte>();
		List<Byte> aux_text = new ArrayList<Byte>();
		Long graphic_id = 0L;
		String resultado = "";
		int ini_texto; int tam_texto;
		boolean alternance = false;
		
		data.add(DIR_LOGICA); 			//Dir. Logica del panel. Habitualmente la 0x20 = 32Dec
		data.add(COD_MODIFICAR_PMV);	//Codigo de protocolo para modificar señalización = 0x1A
		
		//Compruebo si tengo alternancia
		for (int i = 0; i < object.size(); i++){
			if(object[i].getGraphics()){
				for(int k = 0; k < object[i].getGraphics().size(); k++){	
					if(object[i].getGraphics()[k].getAlternance()) {
						alternance = true;
					}
				}
			}
		
			if(object[i].getTexts()){
				for(int j = 0; j < object[i].getTexts().size(); j++){	
					if(object[i].getTexts()[j].getAlternance()) {
						alternance = true;
					}
				}
			}
		}
		
		if (alternance){
			data.add(EST_ALT);				//Utilizaremos una señalización alternante
		}else{
			data.add(EST_FIJO);				//Utilizaremos una señalización fija
		}
		data.add(TOP_GR_TXT_GR);		//Utilizamos topologia GR + TXT + GR siempre, dejando sin rellenar las zonas que no necesitemos o no tengamos
		data.add(NUM_SUBPANELES_TRES);		//Este panel tiene 3 subpaneles = GR + TXT + GR
		data.add(PRIMER_SUBPANEL);		//Empezamos rellenando el primer subpanel
		data.add(CONT_MEM);				//El primer subpanel es de tipo grafico, ponemos que el contenido es por tanto una posición de mem del panel
		
		//Añado el gráfico. Si no tiene, pongo uno en negro.
		for (int i = 0; i < object.size(); i++){
			if(object[i].getGraphics()){
				for(int k = 0; k < object[i].getGraphics().size(); k++){	
					if(object[i].getZone() == PRIMERA_ZONA){
						graphic_id = getGraphic(element, object[i].getZone(), object[i].getGraphics()[k].getValue());	
					}
				}
			}
		}
		if(graphic_id == 0){
			graphic_id = GRAPHIC_EMPTY;
		}

		if(graphic_id < MEM_VOLATIL){
			ElementValue protocolo = EntitiesManager.getInstance().getElementValueConfig(element, PARAM_CONFIG_PROTOCOLO);
		
			xidPointValueTimeModel = signallingCommandUtils.getXidPointValueTimeModel(signallingCommand, dataSourceXid + "_" +  SIGNALLING, cargar_picto_mem_pmv(graphic_id, protocolo.getValue()));
			xidPointValueTimeModels.add(xidPointValueTimeModel);
				
			mapper.setSerializationInclusion(Include.NON_NULL);
			resultado = mapper.writeValueAsString(xidPointValueTimeModels);
	
			if (driver != null) {

				driver.send(resultado);
			}
			sleep(5000);
			resultado="";
		}
		
		data.add(graphic_id);
		data.add(INTERMITENCIA_NO);		//No queremos que el gráfico tenga intermitencia
		
		if(alternance){
			data.add(PRIMER_SUBPANEL);		//Rellenamos contenido del primer subpanel para la alternancia
			data.add(CONT_MEM);				//Este subpanel es de tipo grafico, ponemos que el contenido es por tanto una posición de mem del panel
							
			//Añado el gráfico. Si no tiene, pongo uno en negro.
			for (int i = 0; i < object.size(); i++){
				if(object[i].getGraphics()){
					for(int k = 0; k < object[i].getGraphics().size(); k++){	
						if(object[i].getGraphics()[k].getAlternance()){
							if(object[i].getZone() == PRIMERA_ZONA){
								graphic_id = getGraphic(element, object[i].getZone(), object[i].getGraphics()[k].getAlternance());	
							}
						}
					}
				}
			}
			
			if(graphic_id == 0){
				graphic_id = GRAPHIC_EMPTY;
			}
			
			if(graphic_id < MEM_VOLATIL){
				ElementValue protocolo = EntitiesManager.getInstance().getElementValueConfig(element, PARAM_CONFIG_PROTOCOLO);
		
				xidPointValueTimeModel = signallingCommandUtils.getXidPointValueTimeModel(signallingCommand, dataSourceXid + "_" +  SIGNALLING, cargar_picto_mem_pmv(graphic_id, protocolo.getValue()));
				xidPointValueTimeModels.add(xidPointValueTimeModel);
					
				mapper.setSerializationInclusion(Include.NON_NULL);
				resultado = mapper.writeValueAsString(xidPointValueTimeModels);
		
				if (driver != null) {

					driver.send(resultado);
				}
				sleep(5000);
				resultado="";
			}
			data.add(graphic_id);
			data.add(INTERMITENCIA_NO);		//No queremos que el gráfico tenga intermitencia
		}
		
		data.add(SEGUNDO_SUBPANEL);		//Rellenamos el segundo subpanel
		data.add(CONT_LIT);				//Contenido Literal = Texto
		
		ini_texto = data.size();
		
		data.add(0x00);					//Tamaño del texto
		data.add(LITERAL_H);			//Indicamos el tag de texto libre (^L). Corresponde con el "^"
		data.add(LITERAL_L);			//Indicamos el tag de texto libre (^L). Corresponde con la "L"
		data.add(NUM_LINEAS_TRES);			//Panel de 3 lineas
		data.add(PARAM_LINEA);			//Ponemos por defecto que las lineas sean de color ambar = 0x18
		data.add(PARAM_LINEA);			//Ponemos por defecto que las lineas sean de color ambar = 0x18
		data.add(PARAM_LINEA);			//Ponemos por defecto que las lineas sean de color ambar = 0x18
		
		//Añado el texto a señalizar, linea a linea
		for (int i = 0; i < object.size(); i++){
			if(object[i].getTexts()){
				for(int j = 0; j < object[i].getTexts().size(); j++){
					aux_text.addAll(object[i].getTexts()[j].getValue().getBytes("Cp437"));
					if(j < object[i].getTexts().size()-1){
						aux_text.add(FIN_LINEA);
					}		
				}
			}
		}
		aux_text.add(FIN_TEXTO);
		
		data.addAll(aux_text);	
		
		//Coloco el tamaño del texto
		tam_texto = data.size() - ini_texto - 1; 
		data.set(ini_texto, tam_texto); 
		
		if(alternance){
			aux_text.clear();
			data.add(SEGUNDO_SUBPANEL);		//Rellenamos el segundo subpanel
			data.add(CONT_LIT);				//Contenido Literal = Texto
			
			ini_texto = data.size();
			
			data.add(0x00);					//Tamaño del texto
			data.add(LITERAL_H);			//Indicamos el tag de texto libre (^L). Corresponde con el "^"
			data.add(LITERAL_L);			//Indicamos el tag de texto libre (^L). Corresponde con la "L"
			data.add(NUM_LINEAS_TRES);			//Panel de 3 lineas
			data.add(PARAM_LINEA);			//Ponemos por defecto que las lineas sean de color ambar = 0x18
			data.add(PARAM_LINEA);			//Ponemos por defecto que las lineas sean de color ambar = 0x18
			data.add(PARAM_LINEA);			//Ponemos por defecto que las lineas sean de color ambar = 0x18
			
			//Añado el texto a señalizar, linea a linea
			for (int i = 0; i < object.size(); i++){
				if(object[i].getTexts()){
					for(int j = 0; j < object[i].getTexts().size(); j++){
						aux_text.addAll(object[i].getTexts()[j].getAlternance().getBytes("Cp437"));
						if(j < object[i].getTexts().size()-1){
							aux_text.add(FIN_LINEA);
						}		
					}
				}
			}
			aux_text.add(FIN_TEXTO);
			
			data.addAll(aux_text);	
		
			//Coloco el tamaño del texto
			tam_texto = data.size() - ini_texto - 1; 
			data.set(ini_texto, tam_texto); 
		}
		
		data.add(TERCER_SUBPANEL);		//Empezamos rellenando el primer subpanel
		data.add(CONT_MEM);				//El primer subpanel es de tipo grafico, ponemos que el contenido es por tanto una posición de mem del panel
		
		graphic_id = 0
		//Añado el gráfico. Si no tiene, pongo uno en negro.
		for (int i = 0; i < object.size(); i++){
			if(object[i].getGraphics()){
				for(int k = 0; k < object[i].getGraphics().size(); k++){	
					if(object[i].getZone() == TERCERA_ZONA){
						graphic_id = getGraphic(element, object[i].getZone(), object[i].getGraphics()[k].getValue());	
					}
				}
			}
		}
		if(graphic_id == 0){
			graphic_id = GRAPHIC_EMPTY;
		}
		
		if(graphic_id < MEM_VOLATIL){
			ElementValue protocolo = EntitiesManager.getInstance().getElementValueConfig(element, PARAM_CONFIG_PROTOCOLO);
			
			xidPointValueTimeModel = signallingCommandUtils.getXidPointValueTimeModel(signallingCommand, dataSourceXid + "_" +  SIGNALLING, cargar_picto_mem_pmv(graphic_id, protocolo.getValue()));
			xidPointValueTimeModels.add(xidPointValueTimeModel);
				
			mapper.setSerializationInclusion(Include.NON_NULL);
			resultado = mapper.writeValueAsString(xidPointValueTimeModels);
	
			if (driver != null) {

				driver.send(resultado);
			}
			sleep(5000);
			resultado="";
		}
		
		data.add(graphic_id);
		data.add(INTERMITENCIA_NO);		//No queremos que el gráfico tenga intermitencia
		
		if(alternance){
			data.add(TERCER_SUBPANEL);		//Rellenamos contenido del primer subpanel para la alternancia
			data.add(CONT_MEM);				//Este subpanel es de tipo grafico, ponemos que el contenido es por tanto una posición de mem del panel
							
			//Añado el gráfico. Si no tiene, pongo uno en negro.
			for (int i = 0; i < object.size(); i++){
				if(object[i].getGraphics()){
					for(int k = 0; k < object[i].getGraphics().size(); k++){	
						if(object[i].getGraphics()[k].getAlternance()){
							if(object[i].getZone() == TERCERA_ZONA){
								graphic_id = getGraphic(element, object[i].getZone(), object[i].getGraphics()[k].getAlternance());	
							}
						}
					}
				}
			}
			
			if(graphic_id == 0){
				graphic_id = GRAPHIC_EMPTY;
			}
			
			if(graphic_id < MEM_VOLATIL){
				ElementValue protocolo = EntitiesManager.getInstance().getElementValueConfig(element, PARAM_CONFIG_PROTOCOLO);
				
				xidPointValueTimeModel = signallingCommandUtils.getXidPointValueTimeModel(signallingCommand, dataSourceXid + "_" +  SIGNALLING, cargar_picto_mem_pmv(graphic_id, protocolo.getValue()));
				xidPointValueTimeModels.add(xidPointValueTimeModel);
					
				mapper.setSerializationInclusion(Include.NON_NULL);
				resultado = mapper.writeValueAsString(xidPointValueTimeModels);
		
				if (driver != null) {

					driver.send(resultado);
				}
				sleep(5000);
				resultado="";
			}
			data.add(graphic_id);
			data.add(INTERMITENCIA_NO);		//No queremos que el gráfico tenga intermitencia
		}
		
		data.addAll(CRC_XModem(data)); 	//Calculo el CRC
		data = controlCharacters(data);	//Transformo los caracteres especiales del protocolo 
		
		//Añado los caracteres de principio y fin		
		data.add(0, STX);
		data.add(ETX);
		
		for(byte aux : data){
			resultado += String.format("%02X", aux);
		}
		
		return resultado;
	}
	
	//Subtype 18
	public String dgt_gr_txt(Element element, SignallingCommand signallingCommand, String dataSourceXid, MangoDriver driver){
		ObjectMapper mapper = new ObjectMapper();	
		Object object = mapper.readValue(signallingCommand.signallingParams.get(0).getValue(), Zone[].class);			
		List<XidPointValueTimeModel> xidPointValueTimeModels = new ArrayList<>();	
		XidPointValueTimeModel xidPointValueTimeModel;	
		List<Byte> data = new ArrayList<Byte>();
		List<Byte> aux_text = new ArrayList<Byte>();
		Long graphic_id = 0L;
		String resultado = "";
		int ini_texto; int tam_texto;
		boolean alternance = false;
		
		data.add(DIR_LOGICA); 			//Dir. Logica del panel. Habitualmente la 0x20 = 32Dec
		data.add(COD_MODIFICAR_PMV);	//Codigo de protocolo para modificar señalización = 0x1A
		
		//Compruebo si tengo alternancia
		for (int i = 0; i < object.size(); i++){
			if(object[i].getGraphics()){
				for(int k = 0; k < object[i].getGraphics().size(); k++){	
					if(object[i].getGraphics()[k].getAlternance()) {
						alternance = true;
					}
				}
			}
		
			if(object[i].getTexts()){
				for(int j = 0; j < object[i].getTexts().size(); j++){	
					if(object[i].getTexts()[j].getAlternance()) {
						alternance = true;
					}
				}
			}
		}
		
		if (alternance){
			data.add(EST_ALT);				//Utilizaremos una señalización alternante
		}else{
			data.add(EST_FIJO);				//Utilizaremos una señalización fija
		}
		data.add(TOP_GR_TXT_GR);		//Utilizamos topologia GR + TXT + GR siempre, dejando sin rellenar las zonas que no necesitemos o no tengamos
		data.add(NUM_SUBPANELES_DOS);		//Este panel tiene 2 subpaneles = GR + TXT
		data.add(PRIMER_SUBPANEL);		//Empezamos rellenando el primer subpanel
		data.add(CONT_MEM);				//El primer subpanel es de tipo grafico, ponemos que el contenido es por tanto una posición de mem del panel
		
		//Añado el gráfico. Si no tiene, pongo uno en negro.
		for (int i = 0; i < object.size(); i++){
			if(object[i].getGraphics()){
				for(int k = 0; k < object[i].getGraphics().size(); k++){	
					graphic_id = getGraphic(element, object[i].getZone(), object[i].getGraphics()[k].getValue());	
				}
			}
		}
		if(graphic_id == 0){
			graphic_id = GRAPHIC_EMPTY;
		}

		if(graphic_id < MEM_VOLATIL){

			ElementValue protocolo = EntitiesManager.getInstance().getElementValueConfig(element, PARAM_CONFIG_PROTOCOLO);
			
			xidPointValueTimeModel = signallingCommandUtils.getXidPointValueTimeModel(signallingCommand, dataSourceXid + "_" +  SIGNALLING, cargar_picto_mem_pmv(graphic_id, protocolo.getValue()));
			xidPointValueTimeModels.add(xidPointValueTimeModel);
				
			mapper.setSerializationInclusion(Include.NON_NULL);
			resultado = mapper.writeValueAsString(xidPointValueTimeModels);
	
			if (driver != null) {

				driver.send(resultado);
			}
			sleep(5000);
			resultado="";
		}
		data.add(graphic_id);
		data.add(INTERMITENCIA_NO);		//No queremos que el gráfico tenga intermitencia
		
		if(alternance){
			data.add(PRIMER_SUBPANEL);		//Rellenamos contenido del primer subpanel para la alternancia
			data.add(CONT_MEM);				//Este subpanel es de tipo grafico, ponemos que el contenido es por tanto una posición de mem del panel
							
			//Añado el gráfico. Si no tiene, pongo uno en negro.
			for (int i = 0; i < object.size(); i++){
				if(object[i].getGraphics()){
					for(int k = 0; k < object[i].getGraphics().size(); k++){	
						if(object[i].getGraphics()[k].getAlternance()){
							graphic_id = getGraphic(element, object[i].getZone(), object[i].getGraphics()[k].getAlternance());	
						}
					}
				}
			}
			
			if(graphic_id == 0){
				graphic_id = GRAPHIC_EMPTY;
			}
			if(graphic_id < MEM_VOLATIL){
				ElementValue protocolo = EntitiesManager.getInstance().getElementValueConfig(element, PARAM_CONFIG_PROTOCOLO);
				
				xidPointValueTimeModel = signallingCommandUtils.getXidPointValueTimeModel(signallingCommand, dataSourceXid + "_" +  SIGNALLING, cargar_picto_mem_pmv(graphic_id, protocolo.getValue()));
				xidPointValueTimeModels.add(xidPointValueTimeModel);
					
				mapper.setSerializationInclusion(Include.NON_NULL);
				resultado = mapper.writeValueAsString(xidPointValueTimeModels);
		
				if (driver != null) {
				
					driver.send(resultado);
				}
				sleep(5000);
				resultado="";
			}
			data.add(graphic_id);
			data.add(INTERMITENCIA_NO);		//No queremos que el gráfico tenga intermitencia
		}
		
		data.add(SEGUNDO_SUBPANEL);		//Rellenamos el segundo subpanel
		data.add(CONT_LIT);				//Contenido Literal = Texto
		
		ini_texto = data.size();
		
		data.add(0x00);					//Tamaño del texto
		data.add(LITERAL_H);			//Indicamos el tag de texto libre (^L). Corresponde con el "^"
		data.add(LITERAL_L);			//Indicamos el tag de texto libre (^L). Corresponde con la "L"
		data.add(NUM_LINEAS_TRES);			//Panel de 3 lineas
		data.add(PARAM_LINEA);			//Ponemos por defecto que las lineas sean de color ambar = 0x18
		data.add(PARAM_LINEA);			//Ponemos por defecto que las lineas sean de color ambar = 0x18
		data.add(PARAM_LINEA);			//Ponemos por defecto que las lineas sean de color ambar = 0x18
		
		//Añado el texto a señalizar, linea a linea
		for (int i = 0; i < object.size(); i++){
			if(object[i].getTexts()){
				for(int j = 0; j < object[i].getTexts().size(); j++){					
					aux_text.addAll(object[i].getTexts()[j].getValue().getBytes("Cp437"));
					if(j < object[i].getTexts().size()-1){
						aux_text.add(FIN_LINEA);
					}		
				}
			}
		}
		aux_text.add(FIN_TEXTO);
		
		data.addAll(aux_text);	
		
		//Coloco el tamaño del texto
		tam_texto = data.size() - ini_texto - 1; 
		data.set(ini_texto, tam_texto); 
		
		if(alternance){
			aux_text.clear();
			data.add(SEGUNDO_SUBPANEL);		//Rellenamos el segundo subpanel
			data.add(CONT_LIT);				//Contenido Literal = Texto
			
			ini_texto = data.size();
			
			data.add(0x00);					//Tamaño del texto
			data.add(LITERAL_H);			//Indicamos el tag de texto libre (^L). Corresponde con el "^"
			data.add(LITERAL_L);			//Indicamos el tag de texto libre (^L). Corresponde con la "L"
			data.add(NUM_LINEAS_TRES);			//Panel de 3 lineas
			data.add(PARAM_LINEA);			//Ponemos por defecto que las lineas sean de color ambar = 0x18
			data.add(PARAM_LINEA);			//Ponemos por defecto que las lineas sean de color ambar = 0x18
			data.add(PARAM_LINEA);			//Ponemos por defecto que las lineas sean de color ambar = 0x18
			
			//Añado el texto a señalizar, linea a linea
			for (int i = 0; i < object.size(); i++){
				if(object[i].getTexts()){
					for(int j = 0; j < object[i].getTexts().size(); j++){
						aux_text.addAll(object[i].getTexts()[j].getAlternance().getBytes("Cp437"));
						if(j < object[i].getTexts().size()-1){
							aux_text.add(FIN_LINEA);
						}		
					}
				}
			}
			aux_text.add(FIN_TEXTO);
			
			data.addAll(aux_text);	
		
			//Coloco el tamaño del texto
			tam_texto = data.size() - ini_texto - 1; 
			data.set(ini_texto, tam_texto); 
		}
		
		data.addAll(CRC_XModem(data)); 	//Calculo el CRC
		data = controlCharacters(data);	//Transformo los caracteres especiales del protocolo 
		
		//Añado los caracteres de principio y fin		
		data.add(0, STX);
		data.add(ETX);
		
		for(byte aux : data){
			resultado += String.format("%02X", aux);
		}
		
		return resultado;
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
    
    String cargar_picto_mem_pmv(graphic_id, type){
    	ObjectMapper mapper = new ObjectMapper();		
		List<Byte> data = new ArrayList<Byte>();
		List<Byte> contentByte = new ArrayList<Byte>();
		String resultado="", aux="", frame="";
		
		data.add(DIR_LOGICA); 			//Dir. Logica del panel. Habitualmente la 0x20 = 32Dec
		data.add(COD_CARGAR_MEM);		//Codigo de protocolo para cargar memoria panel = 0x60
		data.add(graphic_id);
		
		String propertiesPath = new File(ResourcesUtil.getPath("io-controller/groovy/mango/volatiles.properties")).toString();
		Properties p = new Properties();
		p.load(new FileReader(propertiesPath));
		
		if(type == "DGT_64")
			frame = p.getProperty("volatil." + graphic_id + ".value64");
		else
			frame = p.getProperty("volatil." + graphic_id + ".value");
			
		for(int i = 0; i<frame.length(); i+=2){
			aux = frame.substring(i,i+2);
			contentByte.add(Integer.parseInt(aux, 16));
		}
		
		data.addAll(contentByte);		
		data.addAll(CRC_XModem(data)); 	//Calculo el CRC
		data = controlCharacters(data);	//Transformo los caracteres especiales del protocolo 
		
		//Añado los caracteres de principio y fin		
		data.add(0, STX);
		data.add(ETX);
		
		for(byte b : data){
			resultado += String.format("%02X", b);
		}
		
		return resultado;
    }
}