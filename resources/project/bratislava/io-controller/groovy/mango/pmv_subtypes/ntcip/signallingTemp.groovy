import com.revenga.rits.back.data.core.util.ResourcesUtil;
import com.revenga.rits.back.data.core.model.command.SignallingCommand;
import com.revenga.rits.back.mango.core.model.XidPointValueTimeModel;
import com.revenga.rits.back.io.controller.driver.mango.MangoDriver;
import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.ElementValue;
import com.revenga.rits.back.io.controller.service.EntitiesManager;

import com.fasterxml.jackson.databind.ObjectMapper;
  
def void signallingTemp(SignallingCommand signallingCommand, String dataSourceXid, MangoDriver driver, Element element, org.apache.logging.log4j.Logger log) {		
	//NTCIP protocol values
	final String RED = "[cf255,0,0]";
	final String WHITE = "[cf255,255,255]";
	final String CLOUD_GRAPHIC = "[g2,2,5]";
	final String ROAD_GRAPHIC = "[g1,1,26]";
	final String CLOUD_TEMP_POS = "[tr24,6,0,0]";
	final String ROAD_TEMP_POS = "[tr24,27,0,0]";
	final int AIR_TEMP = 1;
	final int ROAD_TEMP = 0;
	final String FONT1 = "[fo1]";
	final String DEGREE = "[hcB0]C";
	final String DEGREE2_POS = "[tr48,6,0,0]";
	final String DEGREE1_POS = "[tr48,27,0,0]";
	final int FUERA_RANGO = 50;
	//Commands params
	final Long PARAM_MEASURE = 2L;
	final Long PARAM_MEASURE_ENABLED = 7L;

	String multi;

	GroovyShell shell = new GroovyShell();
	def pmv = shell.parse(new File(ResourcesUtil.getPath("io-controller/groovy/mango/pmv_subtypes/utils/utilsPMV.groovy")));

	List<String> values = signallingCommand.signallingParams.get(0).getValue().split("#");

	multi = ROAD_GRAPHIC + FONT1;
		
	if (!(values.get(AIR_TEMP).isNumber()) || !(values.get(ROAD_TEMP).isNumber())) {

			multi = ""
			ElementValue enable = EntitiesManager.getInstance().getElementValue(element, PARAM_MEASURE_ENABLED, PARAM_MEASURE);
			if(enable.getValue().equals('true')){
				pmv.activarVMSNTCIP(multi, dataSourceXid, signallingCommand, driver, false, false, 255);
			}
	}else{
			if (values != null && values != ""){

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

				if ((int)Double.parseDouble(values.get(AIR_TEMP)) > FUERA_RANGO || (int)Double.parseDouble(values.get(ROAD_TEMP)) > FUERA_RANGO) {
					multi = "";
				
				}
			}

			ElementValue enable = EntitiesManager.getInstance().getElementValue(element, PARAM_MEASURE_ENABLED, PARAM_MEASURE);

			if(enable.getValue().equals('true')){
				pmv.activarVMSNTCIP(multi, dataSourceXid, signallingCommand, driver, false, false, 255);
			}
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
