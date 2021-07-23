package com.revenga.rits.front.openits.bo;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revenga.rits.front.openits.adapter.impl.GraphqlAdapter;
import com.revenga.rits.front.openits.adapter.impl.VO2UIAdapter;
import com.revenga.rits.front.openits.adapter.impl.VODataSourceAdapter;

@Service
public class AlarmFilteredBO extends GenericBO {
	
	@Autowired
	GraphqlAdapter graphqlAdapter;
	
	@Autowired 
	VO2UIAdapter vo2uiAdapter;
	
	@Autowired
	VODataSourceAdapter voDataSourceAdapter;
	
	@Override
	public List<Map<String, Object>> getList() {

		List<Map<String, Object>> alarmFiltereds = voDataSourceAdapter.getList(VODataSourceAdapter.DATA_SOURCE_GET_ALARM_FILTEREDS, null);
		
		return vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(VODataSourceAdapter.DATA_SOURCE_GET_ALARM_FILTEREDS).getFieldId(), alarmFiltereds);
	}

	@Override
	public Map<String, Object> getItem(String id) {

		Map<String, Object> alarmFiltered = voDataSourceAdapter.get(VODataSourceAdapter.DATA_SOURCE_GET_ALARM_FILTERED, new Object[] { id });
		
		return vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(VODataSourceAdapter.DATA_SOURCE_GET_ALARM_FILTERED).getFieldId(), alarmFiltered);
	}
	
	public List<Map<String, Object>> getAlarmFilteredsByAlarmConfig(String id) {
		
		List<Map<String, Object>> alarmFiltereds = voDataSourceAdapter.getList(VODataSourceAdapter.DATA_SOURCE_GET_ALARM_FILTEREDS_BY_ALARM_CONFIG, new Object[] { id });
		
		return vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(VODataSourceAdapter.DATA_SOURCE_GET_ALARM_FILTEREDS_BY_ALARM_CONFIG).getFieldId(), alarmFiltereds);
	}
	
	public List<Map<String, Object>> getAlarmFilteredsByElement(String id) {
		
		List<Map<String, Object>> alarmFiltereds = voDataSourceAdapter.getList(VODataSourceAdapter.DATA_SOURCE_GET_ALARM_FILTEREDS_BY_ELEMENT, new Object[] { id });
		
		return vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(VODataSourceAdapter.DATA_SOURCE_GET_ALARM_FILTEREDS_BY_ELEMENT).getFieldId(), alarmFiltereds);
	}

	@SuppressWarnings("unchecked")
	public Boolean sendAlarmFilteredActions(Object input) {
	    
	    Boolean res = false;
	    
	    Map<String, Object> sendAlarmFilteredActions = voDataSourceAdapter.put(VODataSourceAdapter.DATA_SOURCE_SEND_ALARM_FILTERED_ACTIONS, (Map<String, Object>) input);
	    
	    res = checkMapIsBoolean(sendAlarmFilteredActions);

	    return res;
	    
	  }

}
