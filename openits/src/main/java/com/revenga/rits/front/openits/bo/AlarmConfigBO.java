package com.revenga.rits.front.openits.bo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revenga.rits.front.openits.adapter.impl.GraphqlAdapter;
import com.revenga.rits.front.openits.adapter.impl.VO2UIAdapter;
import com.revenga.rits.front.openits.adapter.impl.VODataSourceAdapter;

@Service
public class AlarmConfigBO {
	
	@Autowired
	GraphqlAdapter graphqlAdapter;
	
	@Autowired 
	VO2UIAdapter vo2uiAdapter;
	
	@Autowired
	VODataSourceAdapter voDataSourceAdapter;
	
	public List<Map<String, Object>> getAlarmConfigs() {
		
		List<Map<String, Object>> alarmConfigs = voDataSourceAdapter.getList(voDataSourceAdapter.getDataSourceGetAlarmConfigs(), null);
		
		return vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(voDataSourceAdapter.getDataSourceGetAlarmConfigs()).getFieldId(), alarmConfigs);
	}
				
	public Map<String, Object> getAlarmConfig(String id) {
		
		Map<String, Object> alarmConfig = voDataSourceAdapter.get(voDataSourceAdapter.getDataSourceGetAlarmConfig(), new Object[] { id });
		
		return vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(voDataSourceAdapter.getDataSourceGetAlarmConfig()).getFieldId(), alarmConfig);
	}
	
	public Map<String, Object> createAlarmConfig(Object input) {
		
		Map<String, Object> createdAlarmConfig = voDataSourceAdapter.put(voDataSourceAdapter.getDataSourceCreateAlarmConfig(), (Map<String, Object>) input);
		
		return vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(voDataSourceAdapter.getDataSourceCreateAlarmConfig()).getFieldId(), createdAlarmConfig);
	}
	
	public Map<String, Object> updateAlarmConfig(Object input) {
			
		Map<String, Object> updatedAlarmConfig = voDataSourceAdapter.put(voDataSourceAdapter.getDataSourceUpdateAlarmConfig(), (Map<String, Object>) input);
		
		return vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(voDataSourceAdapter.getDataSourceUpdateAlarmConfig()).getFieldId(), updatedAlarmConfig);
	}
	
	public Map<String, Object> deleteAlarmConfig(String id) {
		
		Map<String, Object> input = new HashMap<>();
		
		input.put("alarmConfigId", id);
		
		Map<String, Object> deletedAlarmConfig = voDataSourceAdapter.put(voDataSourceAdapter.getDataSourceDeleteAlarmConfig(), input);
		
		return vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(voDataSourceAdapter.getDataSourceDeleteAlarmConfig()).getFieldId(), deletedAlarmConfig);
	}
}
