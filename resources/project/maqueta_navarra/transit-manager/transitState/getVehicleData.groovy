import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import com.revenga.rits.back.data.core.model.Transit;
import com.revenga.rits.back.data.core.model.TransitValue;
import com.revenga.rits.back.data.core.model.TransitTypeStateTransition;
import com.revenga.rits.back.transit.manager.cgi.api.multas.client.helper.CgiApiMultasClientHelper;
import com.revenga.rits.back.transit.manager.cgi.api.multas.client.CgiApiMultasClient;
import com.revenga.rits.back.transit.manager.cgi.api.multas.client.dto.CgiApiMultasPlateNumberResponseDto;
import com.revenga.rits.back.transit.manager.service.EntitiesManager;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

class getVehicleData {

	final Long TRANSIT_TYPE_ID = 1L;
	final Long TRANSIT_TYPE_PARAM_API_VEHICLE_BRAND = 2L;
	final Long TRANSIT_TYPE_PARAM_API_VEHICLE_MODEL = 3L;
	final Long TRANSIT_TYPE_PARAM_API_VEHICLE_COLOR = 4L;
	final Long TRANSIT_TYPE_PARAM_API_VEHICLE_TYPE = 5L;
	final Long TRANSIT_TYPE_PARAM_API_VEHICLE_ITV_DATE = 6L;

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

		if (transit.getVehiclePlateNumber()){
			try{
				client = CgiApiMultasClientHelper.getClient();		
				cgiApiMultasPlateNumberResponseDto = client.getByPlateNumber(transit.getVehiclePlateNumber());		
				log.debug("--> cgiApiMultasPlateNumberResponseDto: " + cgiApiMultasPlateNumberResponseDto);
				
				transit.setVehicleBrandName(cgiApiMultasPlateNumberResponseDto.getIdentificacion().getDescripcionVehiculo().getMarca().getDescripcion());
				transit.setVehicleModelName(cgiApiMultasPlateNumberResponseDto.getIdentificacion().getDescripcionVehiculo().getModelo());
				transit.setVehicleColor(cgiApiMultasPlateNumberResponseDto.getIdentificacion().getDescripcionVehiculo().getColor().getDescripcion());
				transit.setVehicleTypeName(cgiApiMultasPlateNumberResponseDto.getIdentificacion().getDescripcionVehiculo().getTipoVehiculo().getDescripcion());
				
				TransitValue apiVehicleITV = new TransitValue();
				apiVehicleITV.setTransitTypeId(TRANSIT_TYPE_ID);		
				apiVehicleITV.setTransitTypeParamId(TRANSIT_TYPE_PARAM_API_VEHICLE_ITV_DATE);						
				String fechaOriginal = "2014-12-28T23:00:00.000+00:00";
				OffsetDateTime odt = OffsetDateTime.parse(fechaOriginal);
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
				String fechaFormateada = odt.format(formatter);	
				apiVehicleITV.setValue(fechaFormateada);	
				
				transitValues.add(apiVehicleITV);
				transit.setTransitValues(transitValues);
				
				EntitiesManager.getInstance().updateTransit(transit);
						  
				log.debug("onAfterChangeTransitState out");
			}catch(Exception e){
				log.error("Se ha producido un error en la petición de datos de vehículo con la matrícula: " + transit.getVehiclePlateNumber());
			}
		}else{
			log.debug("No se realiza la consulta de datos de vehículo por no tener ninguna matrícula asociada");
		}
			
        return true;
    }
    
}