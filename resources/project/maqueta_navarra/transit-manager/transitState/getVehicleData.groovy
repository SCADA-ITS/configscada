import org.apache.commons.lang3.StringUtils;
import com.revenga.rits.back.data.core.model.Transit;
import com.revenga.rits.back.data.core.model.TransitTypeStateTransition;
import com.revenga.rits.back.transit.manager.cgi.api.multas.client.helper.CgiApiMultasClientHelper;
import com.revenga.rits.back.transit.manager.cgi.api.multas.client.CgiApiMultasClient;
import com.revenga.rits.back.transit.manager.cgi.api.multas.client.dto.CgiApiMultasPlateNumberResponseDto;
import com.revenga.rits.back.transit.manager.service.EntitiesManager;


class getVehicleData {

	org.apache.logging.log4j.Logger log;		
	private CgiApiMultasClient client;

	getVehicleData(org.apache.logging.log4j.Logger log) {
    
        	this.log = log;
	}

	boolean onBeforeChangeTransitState(Transit transit, TransitTypeStateTransition transitTypeStateTransition) {

		CgiApiMultasPlateNumberResponseDto cgiApiMultasPlateNumberResponseDto;

		log.debug("onBeforeChangeTransitState in");

		client = CgiApiMultasClientHelper.getClient();		
		cgiApiMultasPlateNumberResponseDto = client.getByPlateNumber("6206CXP");
		transit.setVehiclePlateNumber(cgiApiMultasPlateNumberResponseDto.getIdentificacion().getMatriculacion().getMatricula());
		EntitiesManager.getInstance().updateTransit(transit);
		
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