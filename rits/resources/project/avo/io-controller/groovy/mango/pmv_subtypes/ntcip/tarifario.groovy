import com.revenga.rits.back.data.core.util.ResourcesUtil;
import com.revenga.rits.back.data.core.model.command.SignallingCommand;
import com.revenga.rits.back.mango.core.model.XidPointValueTimeModel;
import com.revenga.rits.back.io.controller.driver.mango.MangoDriver;
import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.ElementValue;
import com.revenga.rits.back.io.controller.service.EntitiesManager;

import groovy.json.JsonSlurper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.annotation.JsonInclude.Include
  
def void tarifario(SignallingCommand signallingCommand, String dataSourceXid, MangoDriver driver, Element element, String param, org.apache.logging.log4j.Logger log) {	
	final Long PARAM_MEASURE = 2L;
	final Long PARAM_CONFIG = 1L;
	final Long PARAM_MEASURE_ENABLED = 7L;
	final Long PARAM_MEASURE_PANEL_CODE = 8L;
	final String PARAM_RATE = "rate";
	final String NEW_LINE = "[nl2]";
	final String NEW_PAGE = "[np]";	
	
	final String signalCaseOne = "[jp3][jl3][fo3]<codPortico>  AUTO[nl2]<alias> - <money>  x Km"
	final String signalCaseTwo = "[jp3][jl3][fo3]Norte    P107   AUTO[nl2]Sur       P207   AUTO[np]Norte    <money1>  x Km  <alias1>[nl2]Sur        <money2>  x Km  <alias2>"
	final String signalCaseThree = "[jp3][jl3][fo3]P110 AUTO[nl2]<alias> - <money>"
	
	final String signalCaseOneOff = "[jp3][jl3][fo3]<codPortico>  AUTO"
	final String signalCaseTwoOff = "[jp3][jl3][fo3]Norte    P107   AUTO[nl2]Sur       P207   AUTO"
	
	ElementValue enabled = EntitiesManager.getInstance().getElementValue(element, PARAM_MEASURE_ENABLED, PARAM_MEASURE);
	ElementValue panelCode = EntitiesManager.getInstance().getElementValue(element, PARAM_MEASURE_PANEL_CODE, PARAM_CONFIG);
	
	if(enabled != null && ("false").equals(enabled.getValue()) && !signallingCommand.signallingParams.get(0).getValue().contains("Codigo_de_tarifa")){
		String multi = "[jp3][jl3][fo3]";
		String multi_alternate_text = "";

		ObjectMapper mapper = new ObjectMapper();
		Object object = mapper.readValue(signallingCommand.signallingParams.get(0).getValue(), Zone[].class);	
		
		for (int i = 0; i < object.size(); i++){
			if(object[i].getTexts()){
				for(int j = 0; j < object[i].getTexts().size(); j++){
					multi += object[i].getTexts()[j].getValue().toUpperCase();
					if(object[i].getTexts()[j].getValue() != null && j < object[i].getTexts().size() - 1) multi += NEW_LINE; 
					
					if(object[i].getTexts()[j].getAlternance()) {
						multi_alternate_text += object[i].getTexts()[j].getAlternance().toUpperCase();
						if(object[i].getTexts()[j].getAlternance() != null && j < object[i].getTexts().size() - 1) multi_alternate_text += NEW_LINE; 
					}	
				}		
			}
		}
		
		if(!multi_alternate_text.equals("")){
			multi = multi + NEW_PAGE + multi_alternate_text;
		}
		
		activarVMSNTCIP(multi, dataSourceXid, signallingCommand, driver);
	}else{
		String result = "";
		
		def valueJson = new JsonSlurper().parseText(param);
		
		GroovyShell shell = new GroovyShell();
		def signallingCommandUtils = shell.parse(new File(ResourcesUtil.getPath("io-controller/groovy/mango/SignallingCommandUtils.groovy")));
		
		if(valueJson != null && !valueJson.isEmpty()){
			List<XidPointValueTimeModel> xidPointValueTimeModels = new ArrayList<>()
			XidPointValueTimeModel xidPointValueTimeModel;
			
			if(panelCode.getValue() == "1207"){
		    	xidPointValueTimeModel = signallingCommandUtils.getXidPointValueTimeModel(signallingCommand,
		            dataSourceXid + '_' + PARAM_RATE, valueJson.Codigo_de_tarifa.get(0) + "/" + valueJson.Codigo_de_tarifa.get(1));
		    }else{
		    	xidPointValueTimeModel = signallingCommandUtils.getXidPointValueTimeModel(signallingCommand,
		            dataSourceXid + '_' + PARAM_RATE, valueJson.Codigo_de_tarifa.get(0))
		    }
			
		    xidPointValueTimeModels.add(xidPointValueTimeModel)
		
		    ObjectMapper objectMapper = new ObjectMapper()
		    objectMapper.setSerializationInclusion(Include.NON_NULL)
		    String message = objectMapper.writeValueAsString(xidPointValueTimeModels)
		    
		    if (driver != null) {
		    
		        driver.send(message)
		    }
		}
	    
	    if(valueJson != null && !valueJson.isEmpty()){
		    if(panelCode.getValue() == "1207"){
		    	result = signalCaseTwo.replace("<money1>", valueJson.Importe_a_cobrar.get(0));
		    	result = result.replace("<alias1>", valueJson.Alias_tipo_de_tarifa.get(0));
		    	result = result.replace("<codPortico1>", valueJson.Codigo_de_portico.get(0));	
		    	result = result.replace("<money2>", valueJson.Importe_a_cobrar.get(1));
		    	result = result.replace("<alias2>", valueJson.Alias_tipo_de_tarifa.get(1));
		    	result = result.replace("<codPortico2>", valueJson.Codigo_de_portico.get(1));	
		    }else if(panelCode.getValue() == "110"){
		    	result = signalCaseThree.replace("<money>", valueJson.Importe_a_cobrar.get(0));
		    	result = result.replace("<alias>", valueJson.Alias_tipo_de_tarifa.get(0));	
		    }else{
		    	result = signalCaseOne.replace("<money>", valueJson.Importe_a_cobrar.get(0));
		    	result = result.replace("<alias>", valueJson.Alias_tipo_de_tarifa.get(0));
		    	result = result.replace("<codPortico>", valueJson.Codigo_de_portico.get(0));
		    }
		    
			activarVMSNTCIP(result, dataSourceXid, signallingCommand, driver);
		}else{
			if(panelCode.getValue() == "1207"){
		    	result = signalCaseTwoOff;
		    }else{
		    	result = signalCaseOneOff.replace("<codPortico>", "P" + panelCode.getValue());
		    }
		    
			activarVMSNTCIP(result, dataSourceXid, signallingCommand, driver);
		}
	}
}

def void activarVMSNTCIP(String multi, String dataSourceXid, SignallingCommand signallingCommand, MangoDriver driver){
	// XIDs
	final String MESSAGE_STATUS = 'message_status';
	final String MESSAGE_MULTISTRING = "message_multistring";
	
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

			