import com.revenga.rits.back.data.core.util.ResourcesUtil;
import com.revenga.rits.back.data.core.model.command.SignallingCommand;
import com.revenga.rits.back.mango.core.model.XidPointValueTimeModel;
import com.revenga.rits.back.io.controller.driver.mango.MangoDriver;
import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.ElementValue;
import com.revenga.rits.back.io.controller.service.EntitiesManager;

import com.fasterxml.jackson.databind.ObjectMapper;
  
def void grtxtgr(SignallingCommand signallingCommand, String dataSourceXid, MangoDriver driver, Element element) {	
	final String SIGNALLING = "signalling";

	List<XidPointValueTimeModel> xidPointValueTimeModels = new ArrayList<>();

	String resultado = dgt_gr_txt_gr(element, signallingCommand, dataSourceXid, driver);
	
	GroovyShell shell = new GroovyShell();
	def signallingCommandUtils = shell.parse(new File(ResourcesUtil.getPath("io-controller/groovy/mango/SignallingCommandUtils.groovy")));
					
	XidPointValueTimeModel xidPointValueTimeModel = signallingCommandUtils.getXidPointValueTimeModel(signallingCommand, dataSourceXid + "_" +  SIGNALLING, resultado);
	xidPointValueTimeModels.add(xidPointValueTimeModel);
	
	ObjectMapper mapper = new ObjectMapper();
	Object object = mapper.readValue(signallingCommand.signallingParams.get(0).getValue(), Zone[].class);	
	
	message = mapper.writeValueAsString(xidPointValueTimeModels);
	if (driver != null) {
		driver.send(message);
	}
}

public String dgt_gr_txt_gr(Element element, SignallingCommand signallingCommand, String dataSourceXid, MangoDriver driver){
	final int COD_MODIFICAR_PMV = 0x1A;
	final int DIR_LOGICA = 0x20;
	final int EST_FIJO = 0x31;
	final int EST_ALT = 0x33;
	final int TOP_GR_TXT_GR = 0x31;
	final int NUM_SUBPANELES_UNO = 0x31;
	final int NUM_SUBPANELES_DOS = 0x32;
	final int NUM_SUBPANELES_TRES = 0x33;
	final int CONT_MEM = 0x31;
	final int CONT_LIT = 0x33;
	final int PRIMER_SUBPANEL = 0x31;
	final int SEGUNDO_SUBPANEL = 0x32;
	final int TERCER_SUBPANEL = 0x33;
	final int INTERMITENCIA_NO = 0x4E;
	final int INTERMITENCIA_SI = 0x53;
	final int STX = 0x02;
	final int ETX = 0x03;
	final int PRIMERA_ZONA = 1;
	final int SEGUNDA_ZONA = 2;
	final int TERCERA_ZONA = 3;
	final Long GRAPHIC_EMPTY = 255L;
	final Long MEM_VOLATIL = 127L;
	final int LITERAL_H = 0x5E;
	final int LITERAL_L = 0x4C;
	final int NUM_LINEAS_TRES = 3;
	final int NUM_LINEAS_UNA = 1;
	final int PARAM_LINEA = 0x18;
	final int PARAM_LINEA_INT = 0x58;
	final int FIN_LINEA = 0x0A;
	final int FIN_TEXTO = 0x00;

	ObjectMapper mapper = new ObjectMapper();	
	List<XidPointValueTimeModel> xidPointValueTimeModels = new ArrayList<>();	
	XidPointValueTimeModel xidPointValueTimeModel;	
	Object object = mapper.readValue(signallingCommand.signallingParams.get(0).getValue(), Zone[].class);	
	
	GroovyShell shell = new GroovyShell();
	def pmv = shell.parse(new File(ResourcesUtil.getPath("io-controller/groovy/mango/pmv_subtypes/utils/utilsPMV.groovy")))
	
	List<Byte> data = new ArrayList<Byte>();
	List<Byte> aux_text = new ArrayList<Byte>();
	Long graphic_id = 0L;
	Long flashing = null;
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
					graphic_id = pmv.getGraphic(element, object[i].getZone()-1, object[i].getGraphics()[k].getValue());	
					flashing = object[i].getFlashing_on_ms();
				}
			}
		}
	}
	if(graphic_id == 0){
		graphic_id = GRAPHIC_EMPTY;
	}

	if(graphic_id < MEM_VOLATIL){
		xidPointValueTimeModel = signallingCommandUtils.getXidPointValueTimeModel(signallingCommand, dataSourceXid + "_" +  SIGNALLING, cargar_picto_mem_pmv(graphic_id));
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
	if (flashing != null){
		data.add(INTERMITENCIA_SI);		//Queremos que el gráfico tenga intermitencia
	}else{		
		data.add(INTERMITENCIA_NO);		//No queremos que el gráfico tenga intermitencia
	}
	
	if(alternance){
		data.add(PRIMER_SUBPANEL);		//Rellenamos contenido del primer subpanel para la alternancia
		data.add(CONT_MEM);				//Este subpanel es de tipo grafico, ponemos que el contenido es por tanto una posición de mem del panel
						
		//Añado el gráfico. Si no tiene, pongo uno en negro.
		for (int i = 0; i < object.size(); i++){
			if(object[i].getGraphics()){
				for(int k = 0; k < object[i].getGraphics().size(); k++){	
					if(object[i].getGraphics()[k].getAlternance()){
						if(object[i].getZone() == PRIMERA_ZONA){
							graphic_id = pmv.getGraphic(element, object[i].getZone()-1, object[i].getGraphics()[k].getAlternance());								
							flashing = object[i].getFlashing_on_ms();
						}
					}
				}
			}
		}
		
		if(graphic_id == 0){
			graphic_id = GRAPHIC_EMPTY;
		}
		
		if(graphic_id < MEM_VOLATIL){
			xidPointValueTimeModel = signallingCommandUtils.getXidPointValueTimeModel(signallingCommand, dataSourceXid + "_" +  SIGNALLING, cargar_picto_mem_pmv(graphic_id));
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
		if (flashing != null){
			data.add(INTERMITENCIA_SI);		//Queremos que el gráfico tenga intermitencia
		}else{		
			data.add(INTERMITENCIA_NO);		//No queremos que el gráfico tenga intermitencia
		}
	}
	
	data.add(SEGUNDO_SUBPANEL);		//Rellenamos el segundo subpanel
	data.add(CONT_LIT);				//Contenido Literal = Texto
	
	ini_texto = data.size();
	
	data.add(0x00);					//Tamaño del texto
	data.add(LITERAL_H);			//Indicamos el tag de texto libre (^L). Corresponde con el "^"
	data.add(LITERAL_L);			//Indicamos el tag de texto libre (^L). Corresponde con la "L"
	data.add(NUM_LINEAS_TRES);			//Panel de 3 lineas
	
	//Miro la intermitencia
	for (int i = 0; i < object.size(); i++){
		if(object[i].getTexts()){
			for(int j = 0; j < object[i].getTexts().size(); j++){
				if(j < object[i].getTexts().size()-1){
					flashing = object[i].getFlashing_on_ms();
				}		
			}
		}
	}
	
	if (flashing != null){
		data.add(PARAM_LINEA_INT);			//Ponemos por defecto que las lineas sean de color ambar = 0x58
		data.add(PARAM_LINEA_INT);			//Ponemos por defecto que las lineas sean de color ambar = 0x58
		data.add(PARAM_LINEA_INT);			//Ponemos por defecto que las lineas sean de color ambar = 0x58
	}else{		
		data.add(PARAM_LINEA);			//Ponemos por defecto que las lineas sean de color ambar = 0x18
		data.add(PARAM_LINEA);			//Ponemos por defecto que las lineas sean de color ambar = 0x18
		data.add(PARAM_LINEA);			//Ponemos por defecto que las lineas sean de color ambar = 0x18
	}
	
	//Añado el texto a señalizar, linea a linea
	for (int i = 0; i < object.size(); i++){
		if(object[i].getTexts()){
			for(int j = 0; j < object[i].getTexts().size(); j++){
				aux_text.addAll(object[i].getTexts()[j].getValue().getBytes("Cp437"));
				if(j < object[i].getTexts().size()-1){
					aux_text.add(FIN_LINEA);
					flashing = object[i].getFlashing_on_ms();
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
	
	//Añado el gráfico. Si no tiene, pongo uno en negro.
	for (int i = 0; i < object.size(); i++){
		if(object[i].getGraphics()){
			for(int k = 0; k < object[i].getGraphics().size(); k++){	
				if(object[i].getZone() == TERCERA_ZONA){
					graphic_id = pmv.getGraphic(element, object[i].getZone()-1, object[i].getGraphics()[k].getValue());						
					flashing = object[i].getFlashing_on_ms();
				}
			}
		}
	}
	if(graphic_id == 0){
		graphic_id = GRAPHIC_EMPTY;
	}
	
	if(graphic_id < MEM_VOLATIL){
		xidPointValueTimeModel = signallingCommandUtils.getXidPointValueTimeModel(signallingCommand, dataSourceXid + "_" +  SIGNALLING, cargar_picto_mem_pmv(graphic_id));
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
	if (flashing != null){
		data.add(INTERMITENCIA_SI);		//Queremos que el gráfico tenga intermitencia
	}else{		
		data.add(INTERMITENCIA_NO);		//No queremos que el gráfico tenga intermitencia
	}
	
	if(alternance){
		data.add(TERCER_SUBPANEL);		//Rellenamos contenido del primer subpanel para la alternancia
		data.add(CONT_MEM);				//Este subpanel es de tipo grafico, ponemos que el contenido es por tanto una posición de mem del panel
						
		//Añado el gráfico. Si no tiene, pongo uno en negro.
		for (int i = 0; i < object.size(); i++){
			if(object[i].getGraphics()){
				for(int k = 0; k < object[i].getGraphics().size(); k++){	
					if(object[i].getGraphics()[k].getAlternance()){
						if(object[i].getZone() == TERCERA_ZONA){
							graphic_id = pmv.getGraphic(element, object[i].getZone()-1, object[i].getGraphics()[k].getAlternance());						
							flashing = object[i].getFlashing_on_ms();
						}
					}
				}
			}
		}
		
		if(graphic_id == 0){
			graphic_id = GRAPHIC_EMPTY;
		}
		
		if(graphic_id < MEM_VOLATIL){
			xidPointValueTimeModel = signallingCommandUtils.getXidPointValueTimeModel(signallingCommand, dataSourceXid + "_" +  SIGNALLING, cargar_picto_mem_pmv(graphic_id));
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
		if (flashing != null){
			data.add(INTERMITENCIA_SI);		//Queremos que el gráfico tenga intermitencia
		}else{		
			data.add(INTERMITENCIA_NO);		//No queremos que el gráfico tenga intermitencia
		}
	}
	
	data.addAll(pmv.CRC_XModem(data)); 	//Calculo el CRC
	data = pmv.controlCharacters(data);	//Transformo los caracteres especiales del protocolo 
	
	//Añado los caracteres de principio y fin		
	data.add(0, STX);
	data.add(ETX);
	
	for(byte aux : data){
		resultado += String.format("%02X", aux);
	}
	
	return resultado;
}

class Graphic{
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

class Text{
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

class Zone{
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

			