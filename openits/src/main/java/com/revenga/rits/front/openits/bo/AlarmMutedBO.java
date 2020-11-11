package com.revenga.rits.front.openits.bo;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revenga.rits.front.openits.adapter.impl.GraphqlAdapter;
import com.revenga.rits.front.openits.adapter.impl.VO2UIAdapter;
import com.revenga.rits.front.openits.adapter.impl.VODataSourceAdapter;

@Service
public class AlarmMutedBO {
	
	@Autowired
	GraphqlAdapter graphqlAdapter;
	
	@Autowired 
	VO2UIAdapter vo2uiAdapter;
	
	@Autowired
	VODataSourceAdapter voDataSourceAdapter;
	
	public List<Map<String, Object>> getAlarmMuteds() {
		
		List<Map<String, Object>> alarmMuteds = voDataSourceAdapter.getList(voDataSourceAdapter.getDataSourceGetAlarmMuteds(), null);
		
		return vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(voDataSourceAdapter.getDataSourceGetAlarmMuteds()).getFieldId(), alarmMuteds);
	}
				
	public Map<String, Object> getAlarmMuted(String id) {
		
		Map<String, Object> alarmMuted = voDataSourceAdapter.get(voDataSourceAdapter.getDataSourceGetAlarmMuted(), new Object[] { id });
		
		return vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(voDataSourceAdapter.getDataSourceGetAlarmMuted()).getFieldId(), alarmMuted);
	}
	
	public List<Map<String, Object>> getAlarmMutedsByAlarmConfig(String id) {
		
		List<Map<String, Object>> alarmMuteds = voDataSourceAdapter.getList(voDataSourceAdapter.getDataSourceGetAlarmMutedsByAlarmConfig(), new Object[] { id });
		
		return vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(voDataSourceAdapter.getDataSourceGetAlarmMutedsByAlarmConfig()).getFieldId(), alarmMuteds);
	}
	
	public List<Map<String, Object>> getAlarmMutedsByElement(String id) {
		
		List<Map<String, Object>> alarmMuteds = voDataSourceAdapter.getList(voDataSourceAdapter.getDataSourceGetAlarmMutedsByElement(), new Object[] { id });
		
		return vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(voDataSourceAdapter.getDataSourceGetAlarmMutedsByElement()).getFieldId(), alarmMuteds);
	}

}
