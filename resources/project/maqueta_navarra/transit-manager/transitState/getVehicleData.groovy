import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import com.revenga.rits.back.data.core.model.Transit;
import com.revenga.rits.back.data.core.model.TransitValue;
import com.revenga.rits.back.data.core.model.TransitTypeStateTransition;
import com.revenga.rits.back.data.core.dao.manager.DataSourceConnection;
import com.revenga.rits.back.transit.manager.cgi.api.multas.client.helper.CgiApiMultasClientHelper;
import com.revenga.rits.back.transit.manager.cgi.api.multas.client.CgiApiMultasClient;
import com.revenga.rits.back.transit.manager.cgi.api.multas.client.dto.CgiApiMultasPlateNumberResponseDto;
import com.revenga.rits.back.transit.manager.service.EntitiesManager;
import com.revenga.rits.back.transit.manager.transit.persistence.TransitPersistenceService;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

class getVehicleData {

	final Long TRANSIT_TYPE_ID = 1L;
	final Long TRANSIT_TYPE_PARAM_API_VEHICLE_BRAND = 2L;
	final Long TRANSIT_TYPE_PARAM_API_VEHICLE_MODEL = 3L;
	final Long TRANSIT_TYPE_PARAM_API_VEHICLE_COLOR = 4L;
	final Long TRANSIT_TYPE_PARAM_API_VEHICLE_TYPE = 5L;
	final Long TRANSIT_TYPE_PARAM_API_VEHICLE_ITV_DATE = 6L;	
	final Long TRANSIT_STATE_IN_REVIEW = 3L;		
	final Long TRANSIT_STATE_WHITE_LIST = 11L;	
	final Long TRANSIT_STATE_BLACK_LIST = 12L;

	org.apache.logging.log4j.Logger log;		
	private CgiApiMultasClient client;	
	private static final String CONNECTION_URL = "jdbc:postgresql://192.168.88.160:5430/rits";
	private static final String DB_USER = "rits";
	private static final String DB_PASSWORD = "rits";
	private static final String DB_SCHEMA = "transits_bo";
	private TransitPersistenceService service;

	getVehicleData(org.apache.logging.log4j.Logger log) {
    
        	this.log = log;
	}

	boolean onBeforeChangeTransitState(Transit transit, TransitTypeStateTransition transitTypeStateTransition) {

        	return true;
    	}
    
    	boolean onAfterChangeTransitState(Transit transit, TransitTypeStateTransition transitTypeStateTransition) {

		log.debug("transitId = " + transit.getId() + 
				  " currentState = " + transitTypeStateTransition.getParentTransitStateId() + 
				  " nextState = " + transitTypeStateTransition.getChildTransitStateId());	
		
		Connection connection;
	
		if (transit.getVehiclePlateNumber() != null && transit.getVehiclePlateNumber() != ""){
	
			try{
				
				//Primero obtenemos la información del vehículo a través de la petición a la DGT.
				pedirDatosDGT(transit);

				//Consulto las BBDD de listas blancas y negras a ver si se encuentra en alguna
				service = new TransitPersistenceService(null);

				connection = DataSourceConnection.getInstance().getConnection();
				String sql = "SELECT 1 FROM " + DB_SCHEMA + ".white_list WHERE matricula = '" + transit.getVehiclePlateNumber() + "'";

				PreparedStatement wl = connection.prepareStatement(sql);
				ResultSet rwl = wl.executeQuery();
				boolean ewl = rwl.next();  // true si existe al menos una fila

				sql = "SELECT 1 FROM " + DB_SCHEMA + ".black_list WHERE matricula = '" + transit.getVehiclePlateNumber() + "'";

				PreparedStatement bl = connection.prepareStatement(sql);
				ResultSet rbl = bl.executeQuery();
				boolean ebl = rbl.next();  // true si existe al menos una fila
				
				log.debug("Resultado de petición de listas. Lista blanca: " + ewl + ". Lista negra: " + ebl);
				if(ewl) service.changeStateTransit(transit.getId(), TRANSIT_STATE_WHITE_LIST, null);
				else if(ebl) service.changeStateTransit(transit.getId(), TRANSIT_STATE_BLACK_LIST, null);
				else if(!ewl && !ebl) service.changeStateTransit(transit.getId(), TRANSIT_STATE_IN_REVIEW, null);
		
			}catch(Exception e){
				log.error(e.getMessage());
				service.changeStateTransit(transit.getId(), TRANSIT_STATE_IN_REVIEW, null);
			}finally {
				
				try {
					if (connection != null && !connection.isClosed()) {
					
						connection.close();
					}
					
				} catch (SQLException e) {
					log.error(e.getMessage());
					log.debug(ExceptionUtils.getStackTrace(e));
				}
			}
		}else{
			log.debug("No se realiza la consulta de datos de vehículo por no tener ninguna matrícula asociada");
		}
					
        	return true;
    	}

	void pedirDatosDGT(Transit transit) {
		
		CgiApiMultasPlateNumberResponseDto cgiApiMultasPlateNumberResponseDto;
				
		List<TransitValue> transitValues = EntitiesManager.getInstance().getTransitValues(transit);
	
		if (transit.getVehiclePlateNumber()){
			
			try {
				
				client = CgiApiMultasClientHelper.getClient();		
				cgiApiMultasPlateNumberResponseDto = client.getByPlateNumber(transit.getVehiclePlateNumber());		
				log.debug("Resultado de petición de datos para la matrícula " + transit.getVehiclePlateNumber() + ": " + cgiApiMultasPlateNumberResponseDto);
				
				transit.setVehicleBrandName(cgiApiMultasPlateNumberResponseDto.getIdentificacion().getDescripcionVehiculo().getMarca().getDescripcion());
				transit.setVehicleModelName(cgiApiMultasPlateNumberResponseDto.getIdentificacion().getDescripcionVehiculo().getModelo());
				transit.setVehicleColor(cgiApiMultasPlateNumberResponseDto.getIdentificacion().getDescripcionVehiculo().getColor().getDescripcion());
				transit.setVehicleTypeName(cgiApiMultasPlateNumberResponseDto.getIdentificacion().getDescripcionVehiculo().getTipoVehiculo().getDescripcion());	
				
				log.debug("--> " + cgiApiMultasPlateNumberResponseDto.getItvs().get(0).getFechaCaducidad());
				
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
			}catch(Exception e){
				log.error("Se ha producido un error en la petición de datos de vehículo con la matrícula: " + transit.getVehiclePlateNumber());
				log.error(e.getMessage());
			}
		}
	}
    
}
