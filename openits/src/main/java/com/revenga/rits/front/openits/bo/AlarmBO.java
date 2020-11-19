package com.revenga.rits.front.openits.bo;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revenga.rits.front.openits.adapter.impl.GraphqlAdapter;
import com.revenga.rits.front.openits.adapter.impl.VO2UIAdapter;
import com.revenga.rits.front.openits.adapter.impl.VODataSourceAdapter;

@Service
public class AlarmBO extends GenericBO {
	
	@Autowired
	GraphqlAdapter graphqlAdapter;
	
	@Autowired 
	VO2UIAdapter vo2uiAdapter;
	
	@Autowired
	VODataSourceAdapter voDataSourceAdapter;
	
	@Override
	public List<Map<String, Object>> getList() {
		
		List<Map<String, Object>> alarms = voDataSourceAdapter.getList(voDataSourceAdapter.getDataSourceGetAlarms(), null);
		
		return vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(voDataSourceAdapter.getDataSourceGetAlarms()).getFieldId(), alarms);
	}

	@Override
	public Map<String, Object> getItem(String id) {
		
		Map<String, Object> alarm = voDataSourceAdapter.get(voDataSourceAdapter.getDataSourceGetAlarm(), new Object[] { id });
		
		return vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(voDataSourceAdapter.getDataSourceGetAlarm()).getFieldId(), alarm);
	}
	
	public List<Map<String, Object>> getAlarmsByAlarmType(String id) {
		
		List<Map<String, Object>> alarms = voDataSourceAdapter.getList(voDataSourceAdapter.getDataSourceGetAlarmsByAlarmType(), new Object[] { id });
		
		return vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(voDataSourceAdapter.getDataSourceGetAlarmsByAlarmType()).getFieldId(), alarms);
	}
	
	public List<Map<String, Object>> getAlarmsBySource(String sourceElementId) {
		
		List<Map<String, Object>> alarms = voDataSourceAdapter.getList(voDataSourceAdapter.getDataSourceGetAlarmsBySource(), new Object[] { sourceElementId });
		
		return vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(voDataSourceAdapter.getDataSourceGetAlarmsBySource()).getFieldId(), alarms);
	}
	
	public List<Map<String, Object>> getAlarmsBySourceAndAlarmType(String sourceElementId, String alarmTypeId) {
		
		List<Map<String, Object>> alarms = voDataSourceAdapter.getList(voDataSourceAdapter.getDataSourceGetAlarmsBySourceAndAlarmType(), new Object[] { sourceElementId, alarmTypeId });
		
		return vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(voDataSourceAdapter.getDataSourceGetAlarmsBySourceAndAlarmType()).getFieldId(), alarms);
	}

}
