import com.revenga.rits.back.data.core.util.ResourcesUtil;
import com.revenga.rits.back.data.core.model.command.SignallingCommand;
import com.revenga.rits.back.mango.core.model.XidPointValueTimeModel;
import com.revenga.rits.back.io.controller.driver.mango.MangoDriver;
import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.ElementValue;
import com.revenga.rits.back.io.controller.service.EntitiesManager;

import com.fasterxml.jackson.databind.ObjectMapper;
  
def void gr(SignallingCommand signallingCommand, String dataSourceXid, MangoDriver driver, Element element) {	
	final String SIGNALLING = "signalling";

	List<XidPointValueTimeModel> xidPointValueTimeModels = new ArrayList<>();

	String resultado = dgt_af_clv(element, signallingCommand);
	
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

public String dgt_af_clv(Element element, SignallingCommand signallingCommand){	
	final int COD_MODIFICAR_PMV = 0x1A;
	final int DIR_LOGICA = 0x20;
	final int EST_FIJO = 0x31;
	final int EST_ALT = 0x33;
	final int TOP_GR_TXT_GR = 0x31;
	final int NUM_SUBPANELES_UNO = 0x31;
	final int CONT_MEM = 0x31;
	final int PRIMER_SUBPANEL = 0x31;
	final int INTERMITENCIA_NO = 0x4E;
	final int INTERMITENCIA_SI = 0x53;
	final int STX = 0x02;
	final int ETX = 0x03;
	final int GRAFICO_VACIO = 0xFF;

	ObjectMapper mapper = new ObjectMapper();	
	Object object = mapper.readValue(signallingCommand.signallingParams.get(0).getValue(), Zone[].class);	
	
	GroovyShell shell = new GroovyShell();
	def pmv = shell.parse(new File(ResourcesUtil.getPath("io-controller/groovy/mango/pmv_subtypes/utils/utilsPMV.groovy")))

	boolean alternance = false;
	List<Byte> data = new ArrayList<Byte>();
	List<Byte> aux_text = new ArrayList<Byte>();
	String resultado = "";
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
	}		
		
	if (alternance){
		data.add(EST_ALT);				//Utilizaremos una señalización alternante
	}else{
		data.add(EST_FIJO);				//Utilizaremos una señalización fija
	}	
	
	data.add(TOP_GR_TXT_GR);		//Utilizamos topologia GR + TXT + GR siempre, dejando sin rellenar las zonas que no necesitemos o no tengamos
	data.add(NUM_SUBPANELES_UNO);	//Este panel tiene 2 subpaneles = GR + TXT
	data.add(PRIMER_SUBPANEL);		//Empezamos rellenando el primer subpanel
	data.add(CONT_MEM);				//El primer subpanel es de tipo grafico, ponemos que el contenido es por tanto una posición de mem del panel
	
	if(object[0].getGraphics()[0] == null){
		data.add(GRAFICO_VACIO);
	}else{
		data.add(pmv.getGraphic(element,0,object[0].getGraphics()[0].getValue()));
	}
	
	if (object[0].getFlashing_on_ms()){
		data.add(INTERMITENCIA_SI);		//Queremos que el gráfico tenga intermitencia
	}else{		
		data.add(INTERMITENCIA_NO);		//No queremos que el gráfico tenga intermitencia
	}
	
	if(alternance){
		data.add(PRIMER_SUBPANEL);		//Rellenamos contenido del primer subpanel para la alternancia
		data.add(CONT_MEM);				//Este subpanel es de tipo grafico, ponemos que el contenido es por tanto una posición de mem del panel
		
		if(object[0].getGraphics()[0] == null){
			data.add(GRAFICO_VACIO);
		}else{
			data.add(pmv.getGraphic(element,0,object[0].getGraphics()[0].getAlternance()));
		}

		data.add(INTERMITENCIA_NO);
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

			