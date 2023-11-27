import com.revenga.rits.back.data.core.util.ResourcesUtil;
import com.revenga.rits.back.data.core.model.command.SignallingCommand;
import com.revenga.rits.back.mango.core.model.XidPointValueTimeModel;
import com.revenga.rits.back.io.controller.driver.mango.MangoDriver;
import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.ElementValue;
import com.revenga.rits.back.io.controller.service.EntitiesManager;
    
import com.fasterxml.jackson.databind.ObjectMapper;    
    
def void signallingmat192x64(SignallingCommand signallingCommand, String dataSourceXid, MangoDriver driver, Element element) {	
	final String NEW_LINE = "[nl]";
	final int NUM_ZONES = 3;
	final Long ZONA_TEXTO_PEQUEÑA = 3L;
	final Long ZONA_TEXTO_GRANDE = 2L;
	final Long GRAPHIC_PELIGRO = 85L;
	final String character = "↑,°,Á,Â,Ä,Ç,É,Ë,Í,Î,Ó,Ô,Ö,×,Ú,Ü,Ý,ß,á,â,ä,ç,é,ë,í,î,ó,ô,ö,÷,ú,ü,ý,Ă,ă,Ą,ą,Ć,ć,Č,č,Ď,ď,Đ,đ,Ę,Ĺ,ĺ,Ľ,ľ,Ł,ł,Ń,Ň,ň,Ő,ő,Ŕ,ŕ,Ř,ř,Ś,ś,Ş,ş,Š,š,Ţ,ţ,Ť,ť,Ů,ů,Ű,ű,Ź,ź,Ż,ż,ž,ˇ,˘,˙,˛,˜,˝";
	final String ascii = "15,B0,C1,C2,C4,C7,C9,CB,CD,CE,D3,D4,D6,D7,DA,DC,DD,DF,E1,E2,E4,E7,E9,EB,ED,EE,F3,F4,F6,F7,FA,FC,FD,102,103,104,105,106,107,10C,10D,10E,10F,110,111,118,139,13A,13D,13E,141,142,143,147,148,150,151,154,155,158,159,15A,15B,15E,15F,160,161,162,163,164,165,16E,16F,170,171,179,17A,17B,17C,17D,17E,2C7,2D8,2D9,2DB,2DC,2DD";
			
	ObjectMapper mapper = new ObjectMapper();
	Object object = mapper.readValue(signallingCommand.signallingParams.get(0).getValue(), Zone[].class);			
				
	GroovyShell shell = new GroovyShell();
	def pmv = shell.parse(new File(ResourcesUtil.getPath("io-controller/groovy/mango/pmv_subtypes/utils/utilsPMV.groovy")));			
				
	List<String> graphics = new ArrayList();
	List<String> texts = new ArrayList();
	List<String> cad_character = new ArrayList();
	List<String> cad_ascii = new ArrayList();
	

	int zone;
	Long tam_graphic;
	String multi = "";
	String textMin = "";
	
	cad_character = character.split(",");
	cad_ascii = ascii.split(",");
	
	for(int j = 0; j < NUM_ZONES; j++){
		graphics.add("");
		texts.add("");
	}
				
	//Recorro cada zona del panel
	for (int i = 0; i < object.size(); i++){
		zone = object[i].getZone()-1;
		
		if(object[i].getGraphics()){
			for(int k = 0; k < object[i].getGraphics().size(); k++){					
				graphics.set(zone, "[g" + pmv.getGraphic(element, zone, object[i].getGraphics()[k].getValue()) + ",1,1]");
				
				if(object[i].getGraphics()[k].getValue() == GRAPHIC_PELIGRO) tam_graphic = 64;
				else tam_graphic = 32;
			}
		}
		
		if(object[i].getTexts()){
			if( object[i].getZone() == ZONA_TEXTO_GRANDE){
				textValue = "[fo1][tr40,1,0,0]";
				for (int j=0; j<object[i].getTexts().size();j++){
					textValue += object[i].getTexts()[j].getValue();
					if(j < object[i].getTexts().size() - 1){
						textValue+=NEW_LINE;
					}
				}
				texts.set(zone, pmv.sustituirCaracter(textValue, cad_character, cad_ascii));
			}
			else if( object[i].getZone() == ZONA_TEXTO_PEQUEÑA){
				textMin="[fo2][tr" + ((4*(7-object[i].getTexts()[0].getValue().size()))+1) + ",45,0,0]";
				textMin += object[i].getTexts()[0].getValue();
			}		
		}
		
		multi+=graphics.get(zone) + texts.get(zone);
	}
	
	if(tam_graphic==64L){
		multi = multi.replace('tr40','tr70');
	}else{
		multi+=pmv.sustituirCaracter(textMin, cad_character, cad_ascii);
	}
	
	pmv.activarVMSNTCIP(multi, dataSourceXid, signallingCommand, driver, false, false, 255);
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

