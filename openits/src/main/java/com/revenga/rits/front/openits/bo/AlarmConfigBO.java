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
public class AlarmConfigBO extends GenericBO {
	
	@Autowired
	GraphqlAdapter graphqlAdapter;
	
	@Autowired 
	VO2UIAdapter vo2uiAdapter;
	
	@Autowired
	VODataSourceAdapter voDataSourceAdapter;
	
	@Override
	public List<Map<String, Object>> getList() {
		
		List<Map<String, Object>> alarmConfigs = voDataSourceAdapter.getList(VODataSourceAdapter.DATA_SOURCE_GET_ALARM_CONFIGS, null);
		
		return vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(VODataSourceAdapter.DATA_SOURCE_GET_ALARM_CONFIGS).getFieldId(), alarmConfigs);
	}

	@Override
	public Map<String, Object> getItem(String id) {
		
		Map<String, Object> alarmConfig = voDataSourceAdapter.get(VODataSourceAdapter.DATA_SOURCE_GET_ALARM_CONFIG, new Object[] { id });
		
		return vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(VODataSourceAdapter.DATA_SOURCE_GET_ALARM_CONFIG).getFieldId(), alarmConfig);
	}
	
	public List<Map<String, Object>> getAlarmConfigsByAlarmType(String alarmTypeId) {
		
		List<Map<String, Object>> alarmConfigs = voDataSourceAdapter.getList(VODataSourceAdapter.DATA_SOURCE_GET_ALARM_CONFIGS_BY_ALARM_TYPE, new Object[] { alarmTypeId });
		
		return vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(VODataSourceAdapter.DATA_SOURCE_GET_ALARM_CONFIGS_BY_ALARM_TYPE).getFieldId(), alarmConfigs);
	}
	
	public List<Map<String, Object>> getAlarmConfigsByAlarmLevel(String alarmTypeId) {
		
		List<Map<String, Object>> alarmConfigs = voDataSourceAdapter.getList(VODataSourceAdapter.DATA_SOURCE_GET_ALARM_CONFIGS_BY_ALARM_LEVEL, new Object[] { alarmTypeId });
		
		return vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(VODataSourceAdapter.DATA_SOURCE_GET_ALARM_CONFIGS_BY_ALARM_LEVEL).getFieldId(), alarmConfigs);
	}
	
	public Map<String, Object> createAlarmConfig(Object input) {
		
		Map<String, Object> createdAlarmConfig = voDataSourceAdapter.put(VODataSourceAdapter.DATA_SOURCE_CREATE_ALARM_CONFIG, (Map<String, Object>) input);
		
		return vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(VODataSourceAdapter.DATA_SOURCE_CREATE_ALARM_CONFIG).getFieldId(), createdAlarmConfig);
	}
	
	public Map<String, Object> updateAlarmConfig(Object input) {
			
		Map<String, Object> updatedAlarmConfig = voDataSourceAdapter.put(VODataSourceAdapter.DATA_SOURCE_UPDATE_ALARM_CONFIG, (Map<String, Object>) input);
		
		return vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(VODataSourceAdapter.DATA_SOURCE_UPDATE_ALARM_CONFIG).getFieldId(), updatedAlarmConfig);
	}
	
	public Map<String, Object> deleteAlarmConfig(String id) {
		
		Map<String, Object> input = new HashMap<>();
		
		input.put("alarmConfigId", id);
		
		Map<String, Object> deletedAlarmConfig = voDataSourceAdapter.put(VODataSourceAdapter.DATA_SOURCE_DELETE_ALARM_CONFIG, input);
		
		return vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(VODataSourceAdapter.DATA_SOURCE_DELETE_ALARM_CONFIG).getFieldId(), deletedAlarmConfig);
	}
}
