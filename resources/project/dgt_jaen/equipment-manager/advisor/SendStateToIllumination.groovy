import org.apache.commons.lang3.exception.ExceptionUtils;
import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.ElementState;
import com.revenga.rits.back.data.core.model.ElementTypeState;
import com.revenga.rits.back.equipment.manager.service.EntitiesManager;

class SendStateToIllumination {
	
    org.apache.logging.log4j.Logger log;
	
    SendStateToIllumination(org.apache.logging.log4j.Logger log) {
		
        this.log = log;
	}
	
    boolean process(Element element, ElementState elementState) {
		
        try {
			
            if (elementState != null && elementState.getStateId() != null && 
				(elementState.getStateId().equals(ElementTypeState.STATE_UP) || elementState.getStateId().equals(ElementTypeState.STATE_ALARMS)) && 
				(elementState.getPreviousStateId() == null) || elementState.getPreviousStateId().equals(ElementTypeState.STATE_DOWN)) {
				
                EntitiesManager.getInstance().sendElementStateToIllumination(elementState);
			}
			
            return true;
		
        } catch (Exception e) {
			
            log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}
		
        return false;
	}
}