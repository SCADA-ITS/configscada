package com.revenga.rits.back.ep.pub.stomp.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.map.HashedMap;

import com.revenga.rits.back.data.core.model.AlarmConfig;
import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.ElementTypeParam;
import com.revenga.rits.back.data.core.model.ElementValue;
import com.revenga.rits.back.data.core.model.ManagementArea;
import com.revenga.rits.back.data.core.model.ManagementAreaElement;
import com.revenga.rits.back.data.core.model.PlanScheduleStateType;
import com.revenga.rits.back.entities.lib.annotation.EntityRepositoryParam;
import com.revenga.rits.back.entities.lib.helper.EntitiesManagerHelper;
import com.revenga.rits.back.entities.lib.repository.AlarmConfigRepository;
import com.revenga.rits.back.entities.lib.repository.ElementRepository;
import com.revenga.rits.back.entities.lib.repository.ElementTypeParamRepository;
import com.revenga.rits.back.entities.lib.repository.ManagementAreaElementRepository;
import com.revenga.rits.back.entities.lib.repository.ManagementAreaRepository;
import com.revenga.rits.back.entities.lib.repository.PlanScheduleStateTypeRepository;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository;
import com.revenga.rits.back.ep.pub.stomp.data.maintenance.ElementOnChange;
import com.revenga.rits.back.front.core.model.helper.BasicElementHelper;

import lombok.Getter;

public class EntitiesManager {
	
	@EntityRepositoryParam(subscribeChanges = true, entityOnChange = ElementOnChange.class)
	ElementRepository elementRepository;
	ElementTypeParamRepository elementTypeParamRepository;
	@EntityRepositoryParam(subscribeChanges = true)
	AlarmConfigRepository alarmConfigRepository;
	PlanScheduleStateTypeRepository planScheduleStateTypeRepository;
	ManagementAreaRepository managementAreaRepository;
	ManagementAreaElementRepository managementAreaElementRepository; 

	@Getter
	private Map<Class<?>, EntityRepository> repositories = new HashMap<>();
	private List<EntityRepository> repositoryList = new ArrayList<>();
	private Map<String, List<Long>> managementAreasMap = new HashedMap<>();
	
	private static EntitiesManager instance;
	
	public static EntitiesManager getInstance() {
		
		if (instance == null) {
			
			instance = new EntitiesManager();
		}
		
		return instance;
	}
	
	private EntitiesManager() {
		
		EntitiesManagerHelper.initialize(this, repositoryList, repositories);
		EntitiesManagerHelper.startSubscriptions(repositoryList);
		
		initializeManagementAreasMap();
	}
	
	private void initializeManagementAreasMap() {
		
		List<ManagementAreaElement> managementAreaElements = managementAreaElementRepository.getAll(false);
		
		if (CollectionUtils.isNotEmpty(managementAreaElements)) {
			
			managementAreaElements.forEach(managementAreaElement -> {
				
				Element element = new Element(managementAreaElement.getElementTypeId(), managementAreaElement.getElementId());
				
				String strElement = BasicElementHelper.getIdFromElement(element);
				
				managementAreasMap.computeIfAbsent(strElement, k -> new ArrayList<>());
				managementAreasMap.get(strElement).add(managementAreaElement.getId());
			});	
		}
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
	
	public List<ManagementArea> getManagementAreas(){
		
		return   managementAreaRepository.getAll(false);
	}
	
	public List<Long> getManagementAreasByElement(final String elementId){
		
		return managementAreasMap.get(elementId);
	}
	
	public void updateElementManagementAreaMap(Element element) {
		
		String strElement = BasicElementHelper.getIdFromElement(element);
		
		managementAreasMap.remove(strElement);
		
		managementAreaElementRepository = new ManagementAreaElementRepository();
		
		List<ManagementAreaElement> managementAreaElements = managementAreaElementRepository.gets(element.getElementTypeId(), element.getId(), false);
		
		if (!CollectionUtils.isEmpty(managementAreaElements)) {
			
			managementAreasMap.computeIfAbsent(strElement, k -> new ArrayList<>());
			
			for (ManagementAreaElement managementAreaElement : managementAreaElements) {
				
				managementAreasMap.get(strElement).add(managementAreaElement.getId());
			}
		}
	}
}
