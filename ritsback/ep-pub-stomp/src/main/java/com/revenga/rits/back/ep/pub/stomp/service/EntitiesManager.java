package com.revenga.rits.back.ep.pub.stomp.service;

import org.springframework.stereotype.Service;

import com.revenga.rits.back.data.core.model.AlarmConfig;
import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.ElementTypeParam;
import com.revenga.rits.back.data.core.model.ElementValue;
import com.revenga.rits.back.data.core.model.PlanScheduleStateType;
import com.revenga.rits.back.data.core.util.ConfigProperty;
import com.revenga.rits.back.entities.lib.helper.EntitiesManagerHelper;
import com.revenga.rits.back.entities.lib.repository.AlarmConfigRepository;
import com.revenga.rits.back.entities.lib.repository.ElementRepository;
import com.revenga.rits.back.entities.lib.repository.ElementTypeParamRepository;
import com.revenga.rits.back.entities.lib.repository.PlanScheduleStateTypeRepository;

@Service
public class EntitiesManager {
	
	ElementRepository elementRepository;
	ElementTypeParamRepository elementTypeParamRepository;
	AlarmConfigRepository alarmConfigRepository;
	PlanScheduleStateTypeRepository planScheduleStateTypeRepository;

	private static final String FILE_PROPERTIES = "application.properties";

	private static final Long CACHE_RETENTION_TIME_CONF = Long.parseLong(
			ConfigProperty.getInstance().getProperty(FILE_PROPERTIES, "entities.manager.retention_time_conf"));
	private static final Long CACHE_RETENTION_TIME_MASTER = Long.parseLong(
			ConfigProperty.getInstance().getProperty(FILE_PROPERTIES, "entities.manager.retention_time_master"));
	
	private static EntitiesManager instance; 
	
	public static EntitiesManager getInstance() {
		
		if (instance == null) {
			
			instance = new EntitiesManager();
		}
		
		return instance;
	}
	
	private EntitiesManager() {
		
		this.elementRepository = new ElementRepository(true, CACHE_RETENTION_TIME_CONF);
		this.elementTypeParamRepository = new ElementTypeParamRepository(true, CACHE_RETENTION_TIME_MASTER);
		this.alarmConfigRepository = new AlarmConfigRepository(true, CACHE_RETENTION_TIME_CONF);
		this.planScheduleStateTypeRepository = new PlanScheduleStateTypeRepository(true, CACHE_RETENTION_TIME_MASTER);
		
		EntitiesManagerHelper.cacheRepository(this);
	}
	
	public PlanScheduleStateType getPlanScheduleStateType(Long planScheduleId) {
		
		return planScheduleStateTypeRepository.get(planScheduleId, false);
	}
	
	public Element getElement(Long elementTypeId, Long elementId) {
		
		return elementRepository.get(elementTypeId, elementId, false);
	}

	public AlarmConfig getAlarmConfig(Long alarmConfigId) {
		
		return alarmConfigRepository.get(alarmConfigId, false);
	}
	
	public ElementTypeParam getElementTypeParam(ElementValue elementValue) {
		
		return elementTypeParamRepository.get(elementValue.getElementTypeId(), elementValue.getParamTypeId(), elementValue.getElementTypeParamId(), false);
	}
}
