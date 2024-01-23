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
	static final Long PARAM_CONFIG_JSONCONFIG = 4L;
	static final Long PARAM_CONFIG_PROTOCOLO = 2L;
	static final Long PARAM_MEASURE = 2L;
	static final Long PARAM_MEASURE_BEACONS = 5L;
	static final Long PARAM_MEASURE_PIXEL_SERVICE = 6L;
	
	// XIDs
	static final String CONTENT = "content";
	static final String SIGNALLING = "signalling";
	static final String CROSS_ORDER = "cross_order";
	static final String ARROW_ORDER = "arrow_order";
	static final String BIT_A = "bit_A_order";
	static final String BIT_B = "bit_B_order";
	static final String BIT_C = "bit_C_order";
	static final String BIT_D = "bit_D_order";

	static final boolean ACTIVATION_ON = true;
	static final boolean ACTIVATION_OFF = false;
		
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

	//Element Types ID
	static final Long CLV = 11L;
	static final Long AF = 12L;

	//PICTO VALUES
	static final Long ARROW = 1;
	static final Long CROSS = 2;
			
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
			
			if (element.elementSubtypeId == CLV){
				List bitValue = [];
				def signalling = [
					10: [0,0,0,1],
					20: [1,0,0,1],
					30: [1,0,0,0],
					40: [0,1,0,0],
					50: [1,1,0,0],
					60: [0,0,1,0],
					70: [1,0,1,0],
					80: [0,1,1,0],
					90: [1,1,1,0],
					100: [0,1,0,1],
					110: [1,1,0,1],
					120: [0,0,1,1],
					130: [1,0,1,1],
					140: [1,0,1,1]
				]
				
				// Iterar sobre las entradas del diccionario y cambiar 0 y 1 por true y false
				signalling.each { clave, valores ->
					signalling[clave] = valores.collect { it == 1 ? true : false }
				}

				Object object = mapper.readValue(signallingCommand.signallingParams.get(0).getValue(), Zone[].class);		
				//Recorro cada zona del panel
				for (int i = 0; i < object.size(); i++){
					if(object[i].getGraphics()){
						Long pictoValue = getGraphic(element, i+1, object[i].getGraphics()[0].getValue())
						def position = signalling.find { clave, valor -> pictoValue == clave }?.key

						if (position != null) {
							// Acceder al valor asociado usando el nombre del diccionario
							bitValue = signalling[position]
						}

						xidPointValueTimeModel = signallingCommandUtils.getXidPointValueTimeModel(signallingCommand, dataSourceXid + "_" +  BIT_A, bitValue[0]);
						xidPointValueTimeModels.add(xidPointValueTimeModel);
						xidPointValueTimeModel = signallingCommandUtils.getXidPointValueTimeModel(signallingCommand, dataSourceXid + "_" +  BIT_B, bitValue[1]);
						xidPointValueTimeModels.add(xidPointValueTimeModel);
						xidPointValueTimeModel = signallingCommandUtils.getXidPointValueTimeModel(signallingCommand, dataSourceXid + "_" +  BIT_C, bitValue[2]);
						xidPointValueTimeModels.add(xidPointValueTimeModel);
						xidPointValueTimeModel = signallingCommandUtils.getXidPointValueTimeModel(signallingCommand, dataSourceXid + "_" +  BIT_D, bitValue[3]);
						xidPointValueTimeModels.add(xidPointValueTimeModel);

					}else{
						log.error("No hay gráfico en el objeto")
					}
				}
				
			
				mapper.setSerializationInclusion(Include.NON_NULL);
				message = mapper.writeValueAsString(xidPointValueTimeModels);
				if (driver != null) {
				
					driver.send(message);
				}

			}else if (element.elementSubtypeId == AF){
				List bitValue = [];
				def signalling = [
					(ARROW): [1, 0],
					(CROSS): [0, 1]
				]

				// Iterar sobre las entradas del diccionario y cambiar 0 y 1 por true y false
				signalling.each { clave, valores ->
					signalling[clave] = valores.collect { it == 1 ? true : false }
				}
				
				Object object = mapper.readValue(signallingCommand.signallingParams.get(0).getValue(), Zone[].class);		
				//Recorro cada zona del panel
				for (int i = 0; i < object.size(); i++){
					if(object[i].getGraphics()){
						Long pictoValue = getGraphic(element, i+1, object[i].getGraphics()[0].getValue())
						def position = signalling.find {clave, valor -> pictoValue == clave}?.key

						if (position != null) {
							// Acceder al valor asociado usando el nombre del diccionario
							bitValue = signalling[position]
						}
						
						xidPointValueTimeModel = signallingCommandUtils.getXidPointValueTimeModel(signallingCommand, dataSourceXid + "_" +  ARROW_ORDER, bitValue[0]);
						xidPointValueTimeModels.add(xidPointValueTimeModel);
						xidPointValueTimeModel = signallingCommandUtils.getXidPointValueTimeModel(signallingCommand, dataSourceXid + "_" +  CROSS_ORDER, bitValue[1]);
						xidPointValueTimeModels.add(xidPointValueTimeModel);

					}else{
						log.error("No hay gráfico en el objeto")
					}
				}
				
			
				mapper.setSerializationInclusion(Include.NON_NULL);
				message = mapper.writeValueAsString(xidPointValueTimeModels);

				if (driver != null) {
				
					driver.send(message);
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