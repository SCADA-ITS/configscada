package com.revenga.rits.front.openits.bo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revenga.rits.front.openits.adapter.impl.GraphqlAdapter;
import com.revenga.rits.front.openits.adapter.impl.VODataSourceAdapter;
import com.revenga.rits.front.openits.model.EntityChangedUI;

@Service
public class AlarmConfigBO extends GenericBO {

	@Autowired
	GraphqlAdapter graphqlAdapter;
	
	@Autowired 
	SessionBO sessionBO;
	
	public static final int ENTITY_TYPE_ALARM_CONFIG = getPropertyInt("entityType.alarmConfig");

	@PostConstruct
	private void initialize() {
		
		dataSourceList = VODataSourceAdapter.DATA_SOURCE_GET_ALARM_CONFIGS;
		dataSourceItem = VODataSourceAdapter.DATA_SOURCE_GET_ALARM_CONFIG;
	}

	public List<Map<String, Object>> getAlarmConfigsByAlarmType(String alarmTypeId) {

		List<Map<String, Object>> alarmConfigs = voDataSourceAdapter
				.getList(VODataSourceAdapter.DATA_SOURCE_GET_ALARM_CONFIGS_BY_ALARM_TYPE, new Object[] { alarmTypeId });

		return vo2uiAdapter.convert(VODataSourceAdapter.DATA_SOURCE_GET_ALARM_CONFIGS_BY_ALARM_TYPE,
				alarmConfigs);
	}

	public List<Map<String, Object>> getAlarmConfigsByAlarmLevel(String alarmTypeId) {

		List<Map<String, Object>> alarmConfigs = voDataSourceAdapter.getList(
				VODataSourceAdapter.DATA_SOURCE_GET_ALARM_CONFIGS_BY_ALARM_LEVEL, new Object[] { alarmTypeId });

		return vo2uiAdapter.convert(VODataSourceAdapter.DATA_SOURCE_GET_ALARM_CONFIGS_BY_ALARM_LEVEL,
				alarmConfigs);
	}

	public List<Map<String, Object>> getAlarmConfigsByElementType(String elementTypeId) {

		List<Map<String, Object>> alarmConfigs = voDataSourceAdapter
				.getList(VODataSourceAdapter.DATA_SOURCE_GET_ALARM_CONFIGS_BY_ELEMENT_TYPE, new Object[] { elementTypeId });

		return vo2uiAdapter.convert(VODataSourceAdapter.DATA_SOURCE_GET_ALARM_CONFIGS_BY_ELEMENT_TYPE,
				alarmConfigs);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> createAlarmConfig(Object input) {

		Map<String, Object> createdAlarmConfig = voDataSourceAdapter
				.put(VODataSourceAdapter.DATA_SOURCE_CREATE_ALARM_CONFIG, (Map<String, Object>) input);
		
		sessionBO.addEntityChanged(ENTITY_TYPE_ALARM_CONFIG, (String) createdAlarmConfig.get("id"), EntityChangedUI.ENTITY_CREATED,
				this.getItem((String) createdAlarmConfig.get("id")));

		return vo2uiAdapter.convert(VODataSourceAdapter.DATA_SOURCE_CREATE_ALARM_CONFIG, createdAlarmConfig);
	}

	@SuppressWarnings("unchecked")
	public Map<String, Object> updateAlarmConfig(Object input) {

		Map<String, Object> updatedAlarmConfig = voDataSourceAdapter
				.put(VODataSourceAdapter.DATA_SOURCE_UPDATE_ALARM_CONFIG, (Map<String, Object>) input);
		
		sessionBO.addEntityChanged(ENTITY_TYPE_ALARM_CONFIG, (String) updatedAlarmConfig.get("id"), EntityChangedUI.ENTITY_UPDATED,
				this.getItem((String) updatedAlarmConfig.get("id")));

		return vo2uiAdapter.convert(VODataSourceAdapter.DATA_SOURCE_UPDATE_ALARM_CONFIG, updatedAlarmConfig);
	}

	public Map<String, Object> deleteAlarmConfig(String id) {

		Map<String, Object> input = new HashMap<>();

		input.put("alarmConfigId", id);

		Map<String, Object> deletedAlarmConfig = voDataSourceAdapter
				.put(VODataSourceAdapter.DATA_SOURCE_DELETE_ALARM_CONFIG, input);
		
		sessionBO.addEntityChanged(ENTITY_TYPE_ALARM_CONFIG, (String) deletedAlarmConfig.get("id"), EntityChangedUI.ENTITY_DELETED,
				this.getItem((String) deletedAlarmConfig.get("id")));

		return vo2uiAdapter.convert(VODataSourceAdapter.DATA_SOURCE_DELETE_ALARM_CONFIG, deletedAlarmConfig);
	}
}
