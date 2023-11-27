import com.revenga.rits.back.plan.manager.service.EntitiesManager;
import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.ElementValue;

class ChangeElementValue {	

    static final WEATHER_STATION_ELEMENT_TYPE = 1L
    static final WEATHER_STATION_ID = 1L
    static final CONFIG_PARAM = 1L
    static final BRAND_ELEMENT_TYPE_PARAM_ID = 1L
    static final MODEL_ELEMENT_TYPE_PARAM_ID = 2L
	
	org.apache.logging.log4j.Logger log;
	
	ChangeElementValue(org.apache.logging.log4j.Logger log) {
	
		this.log = log;
	}
	
	boolean process(String value) {

        Element element
        String brand, model

        try{

            element = EntitiesManager.getInstance().getElement(WEATHER_STATION_ELEMENT_TYPE,WEATHER_STATION_ID) //Recojo la meteo
            brand = value.split("\n")[0].split("Marca: ")[1]
            model = value.split("\n")[1].split("Modelo: ")[1]

            EntitiesManager.getInstance().updateElementValue(WEATHER_STATION_ELEMENT_TYPE, WEATHER_STATION_ID, BRAND_ELEMENT_TYPE_PARAM_ID,
			    CONFIG_PARAM, brand)
            EntitiesManager.getInstance().updateElementValue(WEATHER_STATION_ELEMENT_TYPE, WEATHER_STATION_ID, MODEL_ELEMENT_TYPE_PARAM_ID,
			    CONFIG_PARAM, model)


            return true

        }catch(Exception e) {
			
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			
		}
		
		return false;
    }
}