package com.revenga.rits.front.openits.bo;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revenga.rits.front.openits.adapter.impl.GraphqlAdapter;
import com.revenga.rits.front.openits.adapter.impl.VO2UIAdapter;
import com.revenga.rits.front.openits.adapter.impl.VODataSourceAdapter;

@Service
public class AlarmLevelBO {
	
	@Autowired
	GraphqlAdapter graphqlAdapter;
	
	@Autowired 
	VO2UIAdapter vo2uiAdapter;
	
	@Autowired
	VODataSourceAdapter voDataSourceAdapter;
	
	public List<Map<String, Object>> getAlarmLevels() {
		
		List<Map<String, Object>> alarmLevels = voDataSourceAdapter.getList(voDataSourceAdapter.getDataSourceGetAlarmLevels(), null);
		
		return vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(voDataSourceAdapter.getDataSourceGetAlarmLevels()).getFieldId(), alarmLevels);
	}
				
	public Map<String, Object> getAlarmLevel(String id) {
		
		Map<String, Object> alarmLevel = voDataSourceAdapter.get(voDataSourceAdapter.getDataSourceGetAlarmLevel(), new Object[] { id });
		
		return vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(voDataSourceAdapter.getDataSourceGetAlarmLevel()).getFieldId(), alarmLevel);
	}
}
