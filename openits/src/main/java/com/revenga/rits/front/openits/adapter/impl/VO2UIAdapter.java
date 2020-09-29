package com.revenga.rits.front.openits.adapter.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.revenga.rits.front.openitsdao.model.VoEntity;
import com.revenga.rits.front.openitsdao.model.VoEntityValue;
import com.revenga.rits.front.openitsdao.service.VoEntityService;

@Component
public class VO2UIAdapter {
	
	@Autowired
	VoEntityService voEntityService;
	
	public List<Map<String, Object>> convert(List<Map<String, Object>> entities) {
		
		List<Map<String, Object>> entitiesUI = null;
		
		if (!CollectionUtils.isEmpty(entities)) {
			
			entitiesUI = new ArrayList<>();
			
			for (Map<String, Object> entity : entities) {
				
				entitiesUI.add(convert(entity));
			}
		}
		
		return entitiesUI;
	}
	
	public Map<String, Object> convert(Map<String, Object> entity) {
		
		Map<String, Object> entityUI = null;
		
		if (!MapUtils.isEmpty(entity)) {
			
			entityUI = new HashMap<>();
			
			VoEntity voEntity = voEntityService.getById(entity.get("id").toString());
			
			if (voEntity != null) {
				
				if  (voEntity.getEnabled().booleanValue()) {
					
					
					
					entityUI.putAll(entity);
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
				
				entityUI.putAll(entity);
				entityUI.put("visible", true);
			}
		}
		
		return entityUI;
	}
}
