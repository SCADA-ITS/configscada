import com.revenga.rits.back.data.core.util.ResourcesUtil;
import com.revenga.rits.back.data.core.model.command.SignallingCommand;
import com.revenga.rits.back.mango.core.model.XidPointValueTimeModel;
import com.revenga.rits.back.io.controller.driver.mango.MangoDriver;
import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.ElementValue;
import com.revenga.rits.back.io.controller.service.EntitiesManager;

import com.fasterxml.jackson.databind.ObjectMapper;
  
def void txt1line(SignallingCommand signallingCommand, String dataSourceXid, MangoDriver driver, Element element) {	
	final String TEXT_ALIGN_CENTER = "[jl3]";
	final String TEXT_ALIGN_LEFT = "[jl2]";
	final String TEXT_ALIGN_RIGHT = "[jl4]";
	final String NEW_LINE = "[nl]";
	final String NEW_PAGE = "[np]";
	final String DEFAULT_PAGEH_ON_TIME = "defaultPagehOnTime"
	
	final Long PARAM_MEASURE = 2L;
	final Long PARAM_MEASURE_BEACONS = 5L;
	final Long PARAM_MEASURE_PIXEL_SERVICE = 6L;
	final String SIGNALLING = "signalling";

	boolean beaconsB, pixelServiceB;
	ElementValue beacons = EntitiesManager.getInstance().getElementValue(element, PARAM_MEASURE_BEACONS, PARAM_MEASURE);
	ElementValue pixelService = EntitiesManager.getInstance().getElementValue(element, PARAM_MEASURE_PIXEL_SERVICE, PARAM_MEASURE);
	
	List<XidPointValueTimeModel> xidPointValueTimeModels = new ArrayList<>();
	XidPointValueTimeModel xidPointValueTimeModel;
	
	ObjectMapper mapper = new ObjectMapper();
	Object object = mapper.readValue(signallingCommand.signallingParams.get(0).getValue(), Zone[].class);		
	List<String> texts = new ArrayList();
	String textValue = "";
	String textAlternance = "";
	String space = "";
	String spaceAlternate = "";
	String message;
	Long defaultPagehOnTime;
	
	GroovyShell shell = new GroovyShell();
	def signallingCommandUtils = shell.parse(new File(ResourcesUtil.getPath("io-controller/groovy/mango/SignallingCommandUtils.groovy")));
	def pmv = shell.parse(new File(ResourcesUtil.getPath("io-controller/groovy/mango/pmv_subtypes/utils/utilsPMV.groovy")));
	
	//Recorro cada zona del panel
	for (int i = 0; i < object.size(); i++){
		if(object[i].getTexts()){
			for(int j = 0; j < object[i].getTexts().size(); j++){
				
				textValue+=TEXT_ALIGN_LEFT
				int centerText = (16 - object[i].getTexts()[j].getValue().size()) / 2
				for (int n = 0; n < centerText; n++){
					space = space + ' '
				}
				
				textValue +=  "[tr1,1]" + space + object[i].getTexts()[j].getValue().toUpperCase();
				if(j < object[i].getTexts().size() - 1){
					textValue+=NEW_LINE;
				}		
				if(object[i].getTexts()[j].getAlternance()) {
					defaultPagehOnTime = object[i].getAlternance_ms()*0.01;
					
					xidPointValueTimeModel = signallingCommandUtils.getXidPointValueTimeModel(signallingCommand, dataSourceXid + "_" + DEFAULT_PAGEH_ON_TIME, defaultPagehOnTime);
				
					xidPointValueTimeModels.clear();		
					xidPointValueTimeModels.add(xidPointValueTimeModel);
					message = mapper.writeValueAsString(xidPointValueTimeModels);
					if (driver != null) {
						driver.send(message);
					}
					
					textAlternance+=TEXT_ALIGN_LEFT
					int centerTextAlternate = (16 - object[i].getTexts()[j].getAlternance().size()) / 2
					for (int n = 0; n < centerTextAlternate; n++){
						spaceAlternate = spaceAlternate + ' '
					}
				
					textAlternance += spaceAlternate +object[i].getTexts()[j].getAlternance().toUpperCase();
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

	pmv.activarVMSNTCIP(multi, dataSourceXid, signallingCommand, driver, pixelServiceB, beaconsB, 255);
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
