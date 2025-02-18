import java.util.List;
import java.util.ArrayList;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.ElementValue;
import com.revenga.rits.back.data.core.model.AlarmConfig;
import com.revenga.rits.back.data.core.model.command.SignallingCommand;
import com.revenga.rits.back.data.core.util.FrameHelper;
import com.revenga.rits.back.data.core.util.ResourcesUtil;


public class ProcessFrame_71{

	static final Long GR_TXT_PMV = 18L;
	static final Long GR_TXT_GR_PMV = 22L;

	GroovyShell shell;

    private org.apache.logging.log4j.Logger log;

    public ProcessFrame_71(org.apache.logging.log4j.Logger log) {
		shell = new GroovyShell();
		this.log = log;
	}

    public void processResponse_0x9B(Element element, List<Byte> data, List<ElementValue> elementValuesToSend,
			List<AlarmConfig> activateAlarmsToSend, List<AlarmConfig> deactivateAlarmsToSend) {
		
		def pmv;

		try{

			if(element.getElementSubtypeId() == GR_TXT_PMV){
				pmv = shell.parse(new File(ResourcesUtil.getPath("io-controller/groovy/une/pmv_subtypes/dgt/ElementSubtype_18_71.groovy")));
				pmv.content(element, data, elementValuesToSend, log);
			
			}else if(element.getElementSubtypeId() == GR_TXT_GR_PMV){
				pmv = shell.parse(new File(ResourcesUtil.getPath("io-controller/groovy/une/pmv_subtypes/dgt/ElementSubtype_22_71.groovy")));
				pmv.content(element, data, elementValuesToSend, log);
			
			}else{
				log.debug("No se encuentra el subtipo del panel")
			}

		}catch(Exception e) {
			log.debug(e.getMessage());
		}

	}

    public List<Byte> processSignallingCommand_2(SignallingCommand signallingCommand, Element element) {

		List<Byte> frame = new ArrayList<>();
		String result = "";

		def pmv;

		try{
			pmv = shell.parse(new File(ResourcesUtil.getPath("io-controller/groovy/une/signallingCommand/SignallingCommand_71_2.groovy")));

			result = pmv.sendMessage(element, signallingCommand, log);

			frame = result.decodeHex()

		}catch(Exception e) {
			log.debug(e.getMessage());
		}
		
		return frame;
	}

	public List<Byte> processSignallingCommand_3(SignallingCommand signallingCommand, Element element) {

		//TODO
		List<Byte> frame = new ArrayList<>();
		String result = "";

		def pmv;

		try{
			pmv = shell.parse(new File(ResourcesUtil.getPath("io-controller/groovy/une/signallingCommand/SignallingCommand_71_3.groovy")));
			result = pmv.shutdown(element, signallingCommand);

			frame = result.decodeHex()

		}catch(Exception e) {
			log.debug(e.getMessage());
		}
		
		return frame;
	}

}
