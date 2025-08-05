import groovy.json.*
import org.apache.commons.lang3.exception.ExceptionUtils;
import com.revenga.rits.back.data.core.model.command.SignallingCommand;
import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.util.ResourcesUtil;


String sendMessage(Element element, SignallingCommand signallingCommand, org.apache.logging.log4j.Logger log) {
	GroovyShell shell;
    def pmv;

    final Long GR_TXT_PMV = 18L;
	final Long GR_TXT_GR_PMV = 22L;

    try {
        
        shell = new GroovyShell();  

        if(element.getElementSubtypeId() == GR_TXT_PMV){

            pmv = shell.parse(new File(ResourcesUtil.getPath("io-controller/groovy/une/signallingCommand/pmv_subtypes/dgt/ElementSubtype_18_71.groovy")));
            frame = pmv.signalling(element, signallingCommand, log);
        }else if(element.getElementSubtypeId() == GR_TXT_GR_PMV){

            pmv = shell.parse(new File(ResourcesUtil.getPath("io-controller/groovy/une/signallingCommand/pmv_subtypes/dgt/ElementSubtype_22_71.groovy")));
            frame = pmv.signalling(element, signallingCommand, log);
        }else{
            log.debug("Subtipo no encontrado")
        }

        return frame;

    }catch (Exception e) {
        log.error(e.getMessage());
        log.debug(ExceptionUtils.getStackTrace(e));
    }		
}