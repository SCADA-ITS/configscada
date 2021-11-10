package com.revenga.rits.front.openits.adapter.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.revenga.rits.front.openits.service.CacheManager;
import com.revenga.rits.front.openitsdao.model.VoEntity;
import com.revenga.rits.front.openitsdao.model.VoEntityValue;
import com.revenga.rits.front.openitsdao.service.VoEntityService;

@Component
public class VO2UIAdapter {
	
	@Autowired
	VoEntityService voEntityService;
	
	@Autowired
	CacheManager cacheManager;
	
	@Autowired
	VODataSourceAdapter voDataSourceAdapter;
	
	public List<Map<String, Object>> convert(Integer datasourceId, List<Map<String, Object>> entities) {
		
		String fieldId = getFieldIdFromDatasource(datasourceId);
		
		List<Map<String, Object>> entitiesUI = null;
		
		if (cacheManager.getEntitiesCached(datasourceId) != null) {
			
			entitiesUI = entities;
		}
		else {
			
			if (!CollectionUtils.isEmpty(entities)) {
				
				entitiesUI = new ArrayList<>();
				
				List<VoEntity> listVoEntity = cacheManager.getVoEntities();
				
				if (CollectionUtils.isEmpty(listVoEntity)) {
					
					listVoEntity = voEntityService.getAll();
				}
				
				for (Map<String, Object> entity : entities) {
					
					entitiesUI.add(convert(fieldId, entity, listVoEntity));
				}
			}
		}
		
		return entitiesUI;
	}
	
	public Map<String, Object> convert(Integer datasourceId, Map<String, Object> entity) {
		
		return convert(getFieldIdFromDatasource(datasourceId), entity, null);
	}
	
	public String getFieldIdFromDatasource(Integer datasourceId) {
		
		return voDataSourceAdapter.getDataSourceService().getById(datasourceId).getFieldId();
	}
	
	@SuppressWarnings("unchecked")
	private Map<String, Object> convert(String fieldId, Map<String, Object> entity, List<VoEntity> listVoEntity) {
		
		Map<String, Object> entityUI = null;
		VoEntity voEntity = null;
		
		if (!MapUtils.isEmpty(entity)) {
			
			entityUI = new HashMap<>();
			
			if (!StringUtils.isEmpty(fieldId)) {
			
				Object entityObj = entity.get(fieldId);
				
				if (entityObj != null) {
					
					if (!CollectionUtils.isEmpty(listVoEntity)) {
						
						voEntity = listVoEntity.stream().filter(e -> e.getId().equals(entityObj.toString())).findFirst()
								.orElse(null);
					}
					else {
					
						voEntity = voEntityService.getById(entityObj.toString());
					}
				}
			}
			
			if (voEntity != null) {
				
				if  (voEntity.getEnabled().booleanValue()) {
					
					//entityUI.putAll(entity);
					entityUI.put("visible", voEntity.getVisible());
					
					if  (!CollectionUtils.isEmpty(voEntity.getVoEntityValues())) {
						
						Map<String, Object> mapAttrib = new HashMap<>();

						for (VoEntityValue voEntityValue : voEntity.getVoEntityValues()) {
					
							mapAttrib.put(voEntityValue.getVoEntityField().getField(), voEntityValue.getValue());
						}
						
						entityUI.putAll(mapAttrib);
					}
					
					addEnum(voEntity, entityUI);
				}
			}
			else {
				
				entityUI.put("visible", true);
			}
			
			for (Map.Entry<String, Object> entry : entity.entrySet()) {
				
				String key = entry.getKey();
				Object value = entry.getValue();
				
				if (!key.equals(fieldId)) {
					
					List<Object> listNew = new ArrayList<>();
					
					if (value != null && value.getClass().getSimpleName().contains(List.class.getSimpleName())) {
						
						List<?> list = (List<?>) value; 
						
						for (Object mapObjectFromList : list) {
							
							Map<String, Object> m = (Map<String, Object>) mapObjectFromList;
							
							listNew.add(convert(fieldId, m, listVoEntity));
						}
						
						if (!CollectionUtils.isEmpty(listNew)) {
							
							entry.setValue(listNew);
						}
					
//						log.debug(String.format("%s instance of %s", key, value.getClass().getSimpleName()));
					}
					else if (value != null && value.getClass().getSimpleName().contains(Map.class.getSimpleName())) {
						
						entry.setValue(convert(fieldId, (Map<String, Object>) value, listVoEntity));
						
//						log.debug(String.format("%s instance of %s", key, value.getClass().getSimpleName()));
					}
				}
				
				entityUI.put(key, entry.getValue());
			}
		}
		
		return entityUI;
	}
	
	private void addEnum(final VoEntity voEntity, Map<String, Object> entityUI) {
		
		if (voEntity != null && !CollectionUtils.isEmpty(voEntity.getVoEntityEnumTypes())) {
			
			entityUI.put("enum", voEntity.getVoEntityEnumTypes().stream().map(e -> e.getVoEnumType()).collect(Collectors.toList()));
		}
	}
}
