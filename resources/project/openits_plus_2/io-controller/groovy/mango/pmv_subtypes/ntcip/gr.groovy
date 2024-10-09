import com.revenga.rits.back.data.core.util.ResourcesUtil;
import com.revenga.rits.back.data.core.model.command.SignallingCommand;
import com.revenga.rits.back.mango.core.model.XidPointValueTimeModel;
import com.revenga.rits.back.io.controller.driver.mango.MangoDriver;
import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.ElementValue;
import com.revenga.rits.back.io.controller.service.EntitiesManager;

import com.fasterxml.jackson.databind.ObjectMapper;
  
def void gr(SignallingCommand signallingCommand, String dataSourceXid, MangoDriver driver, Element element, org.apache.logging.log4j.Logger log) {	
	final String TEXT_ALIGN_CENTER = "[jl3]";
	final String TEXT_ALIGN_LEFT = "[jl2]";
	final String TEXT_ALIGN_RIGHT = "[jl4]";
	final String NEW_LINE = "[nl8]";
	final String NEW_PAGE = "[np]";	
	final String DEFAULT_PAGEH_ON_TIME = "defaultPagehOnTime"
	final Long PARAM_MEASURE = 2L;
	final Long PARAM_MEASURE_BEACONS = 5L;
	final Long PARAM_MEASURE_PIXEL_SERVICE = 6L;
	
	boolean beaconsB = false, pixelServiceB = false;
	String multi_main_graphic = "", multi_alternate_graphic = "";
	Long flash, defaultPagehOnTime;
	
	List<XidPointValueTimeModel> xidPointValueTimeModels = new ArrayList<>();
	XidPointValueTimeModel xidPointValueTimeModel;
	
	ObjectMapper mapper = new ObjectMapper();
	Object object = mapper.readValue(signallingCommand.signallingParams.get(0).getValue(), Zone[].class);	

	GroovyShell shell = new GroovyShell();
	def signallingCommandUtils = shell.parse(new File(ResourcesUtil.getPath("io-controller/groovy/mango/SignallingCommandUtils.groovy")));
	def pmv = shell.parse(new File(ResourcesUtil.getPath("io-controller/groovy/mango/pmv_subtypes/utils/utilsPMV.groovy")))
	//Recorro cada zona del panel
	for (int i = 0; i < object.size(); i++){
		if(object[i].getGraphics()){
			for(int k = 0; k < object[i].getGraphics().size(); k++){	
				if(object[i].getFlashing_on_ms()){
					flash = object[i].getFlashing_on_ms()*0.01;
					multi_main_graphic+= "[flt" + flash + "o" + flash + "]";
				}
				multi_main_graphic += "[g" + pmv.getGraphic(element, object[i].getZone()-1, object[i].getGraphics()[k].getValue()) + ",1,1]";	
				if(object[i].getGraphics()[k].getAlternance()) {
					multi_alternate_graphic += "[g" + pmv.getGraphic(element, object[i].getZone()-1, object[i].getGraphics()[k].getAlternance()) + ",1,1]";
					if(object[i].getAlternance_ms()){
						defaultPagehOnTime = object[i].getAlternance_ms()*0.01;
						
						xidPointValueTimeModel = signallingCommandUtils.getXidPointValueTimeModel(signallingCommand, dataSourceXid + "_" + DEFAULT_PAGEH_ON_TIME, defaultPagehOnTime);
				
						xidPointValueTimeModels.clear();		
						xidPointValueTimeModels.add(xidPointValueTimeModel);
						message = mapper.writeValueAsString(xidPointValueTimeModels);
						if (driver != null) {
							driver.send(message);
						}
					}
				}
			}
		}		
	}
	pmv.activarVMSNTCIP(multi_main_graphic, dataSourceXid, signallingCommand, driver, false, false, 255);
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


class Zone{
	Long zone;
	String align;
	Long alternance_ms;
	Long flashing_on_ms;
	Long flashing_off_ms;
	List<Graphic> graphics;
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

}

			