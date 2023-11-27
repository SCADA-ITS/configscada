import com.revenga.rits.back.data.core.util.ResourcesUtil;
import com.revenga.rits.back.data.core.model.command.SignallingCommand;
import com.revenga.rits.back.mango.core.model.XidPointValueTimeModel;
import com.revenga.rits.back.io.controller.driver.mango.MangoDriver;
import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.ElementValue;
import com.revenga.rits.back.io.controller.service.EntitiesManager;
import org.apache.commons.collections4.CollectionUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
  
def void signalling3LCS(SignallingCommand signallingCommand, String dataSourceXid, MangoDriver driver, Element element,org.apache.logging.log4j.Logger log) {	
	final int NUM_ZONES_5LCS = 10;
	final String character = "↑,°,Á,Â,Ä,Ç,É,Ë,Í,Î,Ó,Ô,Ö,×,Ú,Ü,Ý,ß,á,â,ä,ç,é,ë,í,î,ó,ô,ö,÷,ú,ü,ý,Ă,ă,Ą,ą,Ć,ć,Č,č,Ď,ď,Đ,đ,Ę,Ĺ,ĺ,Ľ,ľ,Ł,ł,Ń,Ň,ň,Ő,ő,Ŕ,ŕ,Ř,ř,Ś,ś,Ş,ş,Š,š,Ţ,ţ,Ť,ť,Ů,ů,Ű,ű,Ź,ź,Ż,ż,ž,ˇ,˘,˙,˛,˜,˝";
	final String ascii = "15,B0,C1,C2,C4,C7,C9,CB,CD,CE,D3,D4,D6,D7,DA,DC,DD,DF,E1,E2,E4,E7,E9,EB,ED,EE,F3,F4,F6,F7,FA,FC,FD,102,103,104,105,106,107,10C,10D,10E,10F,110,111,118,139,13A,13D,13E,141,142,143,147,148,150,151,154,155,158,159,15A,15B,15E,15F,160,161,162,163,164,165,16E,16F,170,171,179,17A,17B,17C,17D,17E,2C7,2D8,2D9,2DB,2DC,2DD";
	
	final Long PARAM_MEASURE_PRIORITY = 11L;
	final Long PARAM_TYPE_MEASURE = 2L;
	final Integer PARAM_MESSAGE_ID = 0;
	final Integer PARAM_PRIORITY_ID = 1;
	final String MESSAGE_PRIORITY = 'priority';
				
	Long priorityNow = 0;
	Long priorityNew = 0;
	String incidentType = " "
	
	List<XidPointValueTimeModel> xidPointValueTimeModels = new ArrayList<>();
	XidPointValueTimeModel xidPointValueTimeModel;
	String message;
	GroovyShell shell = new GroovyShell();
	
	log.debug("Jony signallingCommand.getSignallingParams(): " + signallingCommand.getSignallingParams()); 			
	if( signallingCommand != null && signallingCommand.getElementId() != null &&
	    signallingCommand.getSignallingParams() != null && !signallingCommand.getSignallingParams().isEmpty() &&
	    signallingCommand.getSignallingParams().size > 1 &&
	    signallingCommand.getSignallingParams().get(PARAM_PRIORITY_ID) != null && signallingCommand.getSignallingParams().get(PARAM_PRIORITY_ID).getValue() != null) {
	    
		priorityNew = Long.parseLong(signallingCommand.getSignallingParams().get(PARAM_PRIORITY_ID).getValue().split("-")[1]);
		priorityNow = Long.parseLong(EntitiesManager.getInstance().getElementValue(element, PARAM_MEASURE_PRIORITY, PARAM_TYPE_MEASURE).getValue());
		incidentType = signallingCommand.getSignallingParams().get(PARAM_PRIORITY_ID).getValue().split("-")[0];	

	}
	
	ObjectMapper mapper = new ObjectMapper();
	mapper.setSerializationInclusion(Include.NON_NULL);
		
	def signallingCommandUtils = shell.parse(new File(ResourcesUtil.getPath("io-controller/groovy/mango/SignallingCommandUtils.groovy")));

	if ((incidentType.substring(0,1).equals("t")) && (priorityNew == 0L)){
		
		for(int i=1;i<=7;i++){
			xidPointValueTimeModel = signallingCommandUtils.getXidPointValueTimeModel(signallingCommand, dataSourceXid + "_t" + i, false);
			xidPointValueTimeModels.add(xidPointValueTimeModel);
		}
	}else if ((incidentType.substring(0,1).equals("w")) && (priorityNew == 0L)){
		
		for(int i=1;i<=12;i++){
		
			xidPointValueTimeModel = signallingCommandUtils.getXidPointValueTimeModel(signallingCommand, dataSourceXid + "_w" + i, false);
			xidPointValueTimeModels.add(xidPointValueTimeModel);
		}
	}else if ((incidentType.substring(0,1).equals("r")) && (priorityNew == 0L)){
		
		for(int i=1;i<=11;i++){
			xidPointValueTimeModel = signallingCommandUtils.getXidPointValueTimeModel(signallingCommand, dataSourceXid + "_r" + i, false);
			xidPointValueTimeModels.add(xidPointValueTimeModel);
		}	
	}else if (!incidentType.equals(" ")){
		xidPointValueTimeModel = signallingCommandUtils.getXidPointValueTimeModel(signallingCommand, dataSourceXid + "_" + incidentType, true);
		xidPointValueTimeModels.add(xidPointValueTimeModel);
	}
	
	message = mapper.writeValueAsString(xidPointValueTimeModels);
	
	if (driver != null) {
		log.debug("Jony message: " + message); 
		driver.send(message);
	}
	
	if (priorityNew  == 0 && !incidentType.equals(" ")){
		xidPointValueTimeModel = signallingCommandUtils.getXidPointValueTimeModel(signallingCommand, 
			dataSourceXid + "_" + MESSAGE_PRIORITY, priorityNew);
		
		xidPointValueTimeModels.clear();
		xidPointValueTimeModels.add(xidPointValueTimeModel);
		message = mapper.writeValueAsString(xidPointValueTimeModels);
		
		if (driver != null) {
			driver.send(message);
		}	
	}else if (priorityNew < priorityNow || priorityNow == null || priorityNow == 0){
		log.debug("Prioridad mayor, envio la señalización");
		ObjectMapper objectMapper = new ObjectMapper();
		Object object = objectMapper.readValue(signallingCommand.signallingParams.get(0).getValue(), Zone[].class);		

		def pmv = shell.parse(new File(ResourcesUtil.getPath("io-controller/groovy/mango/pmv_subtypes/utils/utilsPMV.groovy")));
	
		List<String> graphics = new ArrayList();
		List<String> texts = new ArrayList();
		List<String> cad_character = new ArrayList();
		List<String> cad_ascii = new ArrayList();
		
		int zone;
		String multi = "";
		
		cad_character = character.split(",");
		cad_ascii = ascii.split(",");
		
		//Recorro cada zona del panel
		for(int j = 0; j < NUM_ZONES_5LCS; j++){
			graphics.add("");
			texts.add("");
		}
		
		for (int i = 0; i < object.size(); i++){
			zone = object[i].getZone()-1;
			
			if(!CollectionUtils.isEmpty(object[i].getGraphics())){
				for(int k = 0; k < object[i].getGraphics().size(); k++){
					graphics.set(zone, pmv.getGraphic(element, zone, object[i].getGraphics()[k].getValue()));
				}
			}else if(object[i].getTexts()){
				for(int j = 0; j < object[i].getTexts().size() ; j++){
					textValue = "[tr" + ((49*((int)(zone/2))) + 4*(7-object[i].getTexts()[j].getValue().size())) + ",49,0,0]";
					textValue += object[i].getTexts()[j].getValue();	
					texts.set(zone, pmv.sustituirCaracter(textValue, cad_character, cad_ascii));                 
				}
			}
			
			if(graphics.get(zone)!=""){
				if(zone%2 == 0)multi += "[g" + graphics.get(zone) + "," + (((zone/2)*48)+1) + "," + (((zone%2)*48)+1) + "]";
				else multi += "[g" + graphics.get(zone) + "," + (((int)(zone/2)*48)+17) + "," + (((zone%2)*48)+1) + "]"; 
			}
			
			if(texts.get(zone)!="")
				multi += texts.get(zone);
		}
		
		xidPointValueTimeModel = signallingCommandUtils.getXidPointValueTimeModel(signallingCommand, 
			dataSourceXid + "_" + MESSAGE_PRIORITY, priorityNew);
		
		xidPointValueTimeModels.clear();
		xidPointValueTimeModels.add(xidPointValueTimeModel);
		message = mapper.writeValueAsString(xidPointValueTimeModels);
		
		if (driver != null) {
			driver.send(message);
		}
		log.debug("Jony multi: " + multi);
		pmv.activarVMSNTCIP(multi, dataSourceXid, signallingCommand, driver, false, false, 255);
	}else{
		log.debug("Prioridad menor o igual, no cambio la señalización");
	}
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
