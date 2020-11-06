package com.revenga.rits.front.openits.adapter.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.revenga.rits.front.openitsdao.model.VoEntity;
import com.revenga.rits.front.openitsdao.model.VoEntityValue;
import com.revenga.rits.front.openitsdao.service.VoEntityService;

import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
public class VO2UIAdapter {
	
	@Autowired
	VoEntityService voEntityService;
	
	public List<Map<String, Object>> convert(String fieldId, List<Map<String, Object>> entities) {
		
		List<Map<String, Object>> entitiesUI = null;
		
		if (!CollectionUtils.isEmpty(entities)) {
			
			entitiesUI = new ArrayList<>();
			
			for (Map<String, Object> entity : entities) {
				
				entitiesUI.add(convert(fieldId, entity));
			}
		}
		
		return entitiesUI;
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> convert(String fieldId, Map<String, Object> entity) {
		
		Map<String, Object> entityUI = null;
		VoEntity voEntity = null;
		
		if (!MapUtils.isEmpty(entity)) {
			
			entityUI = new HashMap<>();
			
			if (!StringUtils.isEmpty(fieldId)) {
			
				Object entityObj = entity.get(fieldId);
				
				if (entityObj != null) {
				
					voEntity = voEntityService.getById(entityObj.toString());
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
				}
			}
			else {
				
				//entityUI.putAll(entity);
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
							
							listNew.add(convert(fieldId, m));
						}
						
						if (!CollectionUtils.isEmpty(listNew)) {
							
							entry.setValue(listNew);
						}
					
//						log.debug(String.format("%s instance of %s", key, value.getClass().getSimpleName()));
					}
					else if (value != null && value.getClass().getSimpleName().contains(Map.class.getSimpleName())) {
						
						entry.setValue(convert(fieldId, (Map<String, Object>) value));
						
//						log.debug(String.format("%s instance of %s", key, value.getClass().getSimpleName()));
					}
				}
				
				entityUI.put(key, entry.getValue());
			}
		}
		
		return entityUI;
	}
}
