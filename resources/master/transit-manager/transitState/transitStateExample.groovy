import com.revenga.rits.back.data.core.model.Transit;
import com.revenga.rits.back.data.core.model.TransitTypeStateTransition;

class transitStateExample {

	org.apache.logging.log4j.Logger log

	transitStateExample(org.apache.logging.log4j.Logger log) {
    
        this.log = log;
	}

	boolean onBeforeChangeTransitState(Transit transit, TransitTypeStateTransition transitTypeStateTransition) {

		log.debug("onBeforeChangeTransitState in");
		log.debug("transitId = " + transit.getId() + 
				  " currentState = " + transitTypeStateTransition.getParentTransitStateId() + 
				  " nextState = " + transitTypeStateTransition.getChildTransitStateId());
		log.debug("onBeforeChangeTransitState out");
			
        return true;
    }
    
    boolean onAfterChangeTransitState(Transit transit, TransitTypeStateTransition transitTypeStateTransition) {

		log.debug("onAfterChangeTransitState in");
		log.debug("transitId = " + transit.getId() + 
				  " currentState = " + transitTypeStateTransition.getParentTransitStateId() + 
				  " nextState = " + transitTypeStateTransition.getChildTransitStateId());
		log.debug("onAfterChangeTransitState out");
			
        return true;
    }
    
}