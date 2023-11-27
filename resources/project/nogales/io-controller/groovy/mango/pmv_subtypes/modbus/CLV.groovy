import com.revenga.rits.back.data.core.util.ResourcesUtil;
import com.revenga.rits.back.data.core.model.command.SignallingCommand;
import com.revenga.rits.back.mango.core.model.XidPointValueTimeModel;
import com.revenga.rits.back.io.controller.driver.mango.MangoDriver;
import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.ElementValue;
import com.revenga.rits.back.io.controller.service.EntitiesManager;

import com.fasterxml.jackson.databind.ObjectMapper;
  
def void CLV(SignallingCommand signallingCommand, String dataSourceXid, MangoDriver driver, Element element) {	
	final String CONTENT = "content";
	final Long ZERO = 0L;
	
	List<XidPointValueTimeModel> xidPointValueTimeModels = new ArrayList<>();
	XidPointValueTimeModel xidPointValueTimeModel;	
	
	ObjectMapper mapper = new ObjectMapper();
	Object object = mapper.readValue(signallingCommand.signallingParams.get(0).getValue(), Zone[].class);		

	shell = new GroovyShell();
	def signallingCommandUtils = shell.parse(new File(ResourcesUtil.getPath("io-controller/groovy/mango/SignallingCommandUtils.groovy")));
	def pmv = shell.parse(new File(ResourcesUtil.getPath("io-controller/groovy/mango/pmv_subtypes/utils/utilsPMV.groovy")));
	
	for (int i = 0; i < object.size(); i++){
		if(object[i].getGraphics()){
			if (object[i].getFlashing_on_ms()){
				for(int k = 0; k < object[i].getGraphics().size(); k++){		
					xidPointValueTimeModel = signallingCommandUtils.getXidPointValueTimeModel(signallingCommand, dataSourceXid + "_" +  CONTENT, (pmv.getGraphic(element, i, object[i].getGraphics()[k].getValue()) + 256));
					xidPointValueTimeModels.add(xidPointValueTimeModel);
				}
			}else{
				for(int k = 0; k < object[i].getGraphics().size(); k++){		
					xidPointValueTimeModel = signallingCommandUtils.getXidPointValueTimeModel(signallingCommand, dataSourceXid + "_" +  CONTENT, pmv.getGraphic(element, i, object[i].getGraphics()[k].getValue()));
					xidPointValueTimeModels.add(xidPointValueTimeModel);
				}	
			}
		}else{
			xidPointValueTimeModel = signallingCommandUtils.getXidPointValueTimeModel(signallingCommand, dataSourceXid + "_" +  CONTENT, ZERO);
			xidPointValueTimeModels.add(xidPointValueTimeModel);
		}
	}
		
	xidPointValueTimeModels.clear();			
	xidPointValueTimeModels.add(xidPointValueTimeModel);
	message = mapper.writeValueAsString(xidPointValueTimeModels);
	if (driver != null) {
		driver.send(message);
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
