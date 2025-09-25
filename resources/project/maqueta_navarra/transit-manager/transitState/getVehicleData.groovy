import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import com.revenga.rits.back.data.core.model.Transit;
import com.revenga.rits.back.data.core.model.TransitValue;
import com.revenga.rits.back.data.core.model.TransitTypeStateTransition;
import com.revenga.rits.back.transit.manager.cgi.api.multas.client.helper.CgiApiMultasClientHelper;
import com.revenga.rits.back.transit.manager.cgi.api.multas.client.CgiApiMultasClient;
import com.revenga.rits.back.transit.manager.cgi.api.multas.client.dto.CgiApiMultasPlateNumberResponseDto;
import com.revenga.rits.back.transit.manager.service.EntitiesManager;


class getVehicleData {

	final Long TRANSIT_TYPE_ID = 1L;
	final Long TRANSIT_TYPE_PARAM_API_VEHICLE_PLATE_NUMBER = 2L;
	final Long TRANSIT_TYPE_PARAM_API_VEHICLE_BRAND = 3L;
	final Long TRANSIT_TYPE_PARAM_API_VEHICLE_MODEL = 4L;
	final Long TRANSIT_TYPE_PARAM_API_VEHICLE_COLOR = 5L;

	org.apache.logging.log4j.Logger log;		
	private CgiApiMultasClient client;

	getVehicleData(org.apache.logging.log4j.Logger log) {
    
        	this.log = log;
	}

	boolean onBeforeChangeTransitState(Transit transit, TransitTypeStateTransition transitTypeStateTransition) {

		log.debug("onBeforeChangeTransitState in");
		
		log.debug("onBeforeChangeTransitState out");
			
        return true;
    }
    
    boolean onAfterChangeTransitState(Transit transit, TransitTypeStateTransition transitTypeStateTransition) {

		CgiApiMultasPlateNumberResponseDto cgiApiMultasPlateNumberResponseDto;
		List<TransitValue> transitValues = new ArrayList<>();
		
		log.debug("onAfterChangeTransitState in");
		log.debug("transitId = " + transit.getId() + 
				  " currentState = " + transitTypeStateTransition.getParentTransitStateId() + 
				  " nextState = " + transitTypeStateTransition.getChildTransitStateId());		

		client = CgiApiMultasClientHelper.getClient();		
		cgiApiMultasPlateNumberResponseDto = client.getByPlateNumber(transit.getVehiclePlateNumber());		
		log.debug("--> cgiApiMultasPlateNumberResponseDto: " + cgiApiMultasPlateNumberResponseDto);
		
		
		TransitValue apiVehiclePlateNumber = new TransitValue();
		apiVehiclePlateNumber.setTransitTypeId(TRANSIT_TYPE_ID);		
		apiVehiclePlateNumber.setTransitTypeParamId(TRANSIT_TYPE_PARAM_API_VEHICLE_PLATE_NUMBER);
		apiVehiclePlateNumber.setValue(cgiApiMultasPlateNumberResponseDto.getIdentificacion().getMatriculacion().getMatricula());
		transitValues.add(apiVehiclePlateNumber);
		
		TransitValue apiVehicleBrand = new TransitValue();
		apiVehicleBrand.setTransitTypeId(TRANSIT_TYPE_ID);		
		apiVehicleBrand.setTransitTypeParamId(TRANSIT_TYPE_PARAM_API_VEHICLE_BRAND);
		apiVehicleBrand.setValue(cgiApiMultasPlateNumberResponseDto.getIdentificacion().getDescripcionVehiculo().getMarca().getDescripcion());
		transitValues.add(apiVehicleBrand);
		
		TransitValue apiVehicleModel = new TransitValue();
		apiVehicleModel.setTransitTypeId(TRANSIT_TYPE_ID);		
		apiVehicleModel.setTransitTypeParamId(TRANSIT_TYPE_PARAM_API_VEHICLE_MODEL);
		apiVehicleModel.setValue(cgiApiMultasPlateNumberResponseDto.getIdentificacion().getDescripcionVehiculo().getModelo());
		transitValues.add(apiVehicleModel);
		
		TransitValue apiVehicleColor = new TransitValue();
		apiVehicleColor.setTransitTypeId(TRANSIT_TYPE_ID);		
		apiVehicleColor.setTransitTypeParamId(TRANSIT_TYPE_PARAM_API_VEHICLE_COLOR);
		apiVehicleColor.setValue(cgiApiMultasPlateNumberResponseDto.getIdentificacion().getDescripcionVehiculo().getColor().getDescripcion());
		transitValues.add(apiVehicleColor);
		
		transit.setTransitValues(transitValues);
		log.debug("--> transit: " + transit);
		
		transit.setVehiclePlateNumber(cgiApiMultasPlateNumberResponseDto.getIdentificacion().getMatriculacion().getMatricula());
		EntitiesManager.getInstance().updateTransit(transit);
				  
		log.debug("onAfterChangeTransitState out");
			
        return true;
    }
    
}