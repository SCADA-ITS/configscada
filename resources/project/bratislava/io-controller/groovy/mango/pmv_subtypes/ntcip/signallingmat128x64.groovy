import com.revenga.rits.back.data.core.util.ResourcesUtil;
import com.revenga.rits.back.data.core.model.command.SignallingCommand;
import com.revenga.rits.back.mango.core.model.XidPointValueTimeModel;
import com.revenga.rits.back.io.controller.driver.mango.MangoDriver;
import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.ElementValue;
import com.revenga.rits.back.io.controller.service.EntitiesManager;

import com.fasterxml.jackson.databind.ObjectMapper;    
    
def void signallingmat128x64(SignallingCommand signallingCommand, String dataSourceXid, MangoDriver driver, Element element) {		
	final String character = "↑,°,Á,Â,Ä,Ç,É,Ë,Í,Î,Ó,Ô,Ö,×,Ú,Ü,Ý,ß,á,â,ä,ç,é,ë,í,î,ó,ô,ö,÷,ú,ü,ý,Ă,ă,Ą,ą,Ć,ć,Č,č,Ď,ď,Đ,đ,Ę,Ĺ,ĺ,Ľ,ľ,Ł,ł,Ń,Ň,ň,Ő,ő,Ŕ,ŕ,Ř,ř,Ś,ś,Ş,ş,Š,š,Ţ,ţ,Ť,ť,Ů,ů,Ű,ű,Ź,ź,Ż,ż,ž,ˇ,˘,˙,˛,˜,˝";
	final String ascii = "15,B0,C1,C2,C4,C7,C9,CB,CD,CE,D3,D4,D6,D7,DA,DC,DD,DF,E1,E2,E4,E7,E9,EB,ED,EE,F3,F4,F6,F7,FA,FC,FD,102,103,104,105,106,107,10C,10D,10E,10F,110,111,118,139,13A,13D,13E,141,142,143,147,148,150,151,154,155,158,159,15A,15B,15E,15F,160,161,162,163,164,165,16E,16F,170,171,179,17A,17B,17C,17D,17E,2C7,2D8,2D9,2DB,2DC,2DD";
	
	ObjectMapper mapper = new ObjectMapper();
	Object object = mapper.readValue(signallingCommand.signallingParams.get(0).getValue(), Zone[].class);
	
	List<String> cad_character = new ArrayList();
	List<String> cad_ascii = new ArrayList();

	GroovyShell shell = new GroovyShell();
	def pmv = shell.parse(new File(ResourcesUtil.getPath("io-controller/groovy/mango/pmv_subtypes/utils/utilsPMV.groovy")));
	
	cad_character = character.split(",");
	cad_ascii = ascii.split(",");
	
	String multi = "";

	//Recorro cada zona del panel
	for (int i = 0; i < object.size(); i++){
		if(object[i].getTexts()){
			multi = "[fo1][tr1,1,0,0]"
			for(int j = 0; j < object[i].getTexts().size(); j++){
				if (j > 0){
					multi += "[nl]"
				}
				multi +=  object[i].getTexts()[j].getValue()
				multi = pmv.sustituirCaracter(multi, cad_character, cad_ascii);
			}
		}
		else if(object[i].getGraphics()){
			multi = pmv.sustituirGraphic(element, 1, object[i].getGraphics()[0].getValue());
		}	
	}

	pmv.activarVMSNTCIP(multi, dataSourceXid, signallingCommand, driver, false, false, 255);
}
