package com.revenga.rits.back.virtual.equipment.manager.vms;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.ElementType;
import com.revenga.rits.back.data.core.model.ElementValue;
import com.revenga.rits.back.data.core.model.ParamType;
import com.revenga.rits.back.data.core.util.ConfigProperty;
import com.revenga.rits.back.virtual.equipment.manager.service.EntityManager;
import com.revenga.rits.back.virtual.equipment.manager.vms.model.TravelTime;

import lombok.NonNull;

public class VmsEntityManager {

	private static final String FILE_CONSTANTS = "constants.properties";
	private static final String APPLICATION_PROPERTIES = "application.properties";
	
	public static final long ELEMENT_TYPE_PARAM_VMS_DATA = Long
			.parseLong(ConfigProperty.getInstance().getProperty(FILE_CONSTANTS, "element.type.vms.data"));
	
	public static final long ELEMENT_TYPE_PARAM_VMS_ENABLED_TRAVEL_TIME = Long
			.parseLong(ConfigProperty.getInstance().getProperty(FILE_CONSTANTS, "element.type.vms.enabled_travel_time"));
	
	public static final long ELEMENT_TYPE_PARAM_TRAVEL_TIME_TIME_MIN = Long
			.parseLong(ConfigProperty.getInstance().getProperty(FILE_CONSTANTS, "element.type.travel_time.time_min"));
	
	public static final long ELEMENT_TYPE_PARAM_TRAVEL_TIME_NAME = Long
			.parseLong(ConfigProperty.getInstance().getProperty(FILE_CONSTANTS, "element.type.travel_time.name"));
	
	public static final int COMMAND_TYPE_VMS_SEND_MESSAGE = Integer.parseInt(
			ConfigProperty.getInstance().getProperty(FILE_CONSTANTS, "command.vms.send_message"));
	
	public static final int COMMAND_TYPE_VMS_SEND_MESSAGE_PARAM_JSON_VALUE = Integer.parseInt(
			ConfigProperty.getInstance().getProperty(FILE_CONSTANTS, "command.vms.send_message.param.json_value"));
	
	public static final long VMS_QUEUE_ID = Long.parseLong(
			ConfigProperty.getInstance().getProperty(APPLICATION_PROPERTIES, "vms.queue_id"));
	
	public static final long VMS_QUEUE_PRIORITY_ID = Long.parseLong(
			ConfigProperty.getInstance().getProperty(APPLICATION_PROPERTIES, "vms.queue_priority_id"));
	
	private static VmsEntityManager instance;
	
	private VmsEntityManager() {

	}

	public static VmsEntityManager getInstance() {

		if (instance == null) {

			instance = new VmsEntityManager();
		}

		return instance;
	}
	
	public List<TravelTime> getTravelTimes(@NonNull Element vms) {
		
		List<TravelTime> travelTimes = null;
		List<Element> travelTimeElements = null;
		
		travelTimeElements = EntityManager.getInstance().getChildByType(ElementType.ELEMENT_TYPE_VMS, vms.getId(),
				ElementType.ELEMENT_TYPE_TRAVEL_TIME);
		
		if (!CollectionUtils.isEmpty(travelTimeElements)) {
			
			for (Element travelTimeElement : travelTimeElements) {
				
				String name = null;
				Integer timeMin = null;
				
				ElementValue evName = EntityManager.getInstance().getElementValue(
						ElementType.ELEMENT_TYPE_TRAVEL_TIME, travelTimeElement.getId(), ELEMENT_TYPE_PARAM_TRAVEL_TIME_NAME,
						ParamType.CONFIG);
				
				if (evName != null) {
					
					name = evName.getValue();
				}
				
				ElementValue evTimeMin = EntityManager.getInstance().getElementValue(
						ElementType.ELEMENT_TYPE_TRAVEL_TIME, travelTimeElement.getId(), ELEMENT_TYPE_PARAM_TRAVEL_TIME_TIME_MIN,
						ParamType.MEASURE);
				
				if (evTimeMin != null && evTimeMin.getValue() != null) {
					
					timeMin = Integer.valueOf(evTimeMin.getValue());
				}
				
				if (!StringUtils.isEmpty(name) && timeMin != null) {
					
					if (travelTimes == null) {
						
						travelTimes = new ArrayList<>();
					}
					
					travelTimes.add(new TravelTime(name, timeMin));					
				}
			}
		}
		
		return travelTimes;
	}
}