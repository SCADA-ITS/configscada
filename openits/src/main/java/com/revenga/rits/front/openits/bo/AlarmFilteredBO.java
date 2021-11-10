package com.revenga.rits.front.openits.bo;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revenga.rits.front.openits.adapter.impl.GraphqlAdapter;
import com.revenga.rits.front.openits.adapter.impl.VODataSourceAdapter;

@Service
public class AlarmFilteredBO extends GenericBO {
	
	@Autowired
	GraphqlAdapter graphqlAdapter;
	
	@PostConstruct
	private void initialize() {
		
		dataSourceList = VODataSourceAdapter.DATA_SOURCE_GET_ALARM_FILTEREDS;
		dataSourceItem = VODataSourceAdapter.DATA_SOURCE_GET_ALARM_FILTERED;
	}
	
	public List<Map<String, Object>> getAlarmFilteredsByAlarmConfig(String id) {
		
		List<Map<String, Object>> alarmFiltereds = voDataSourceAdapter.getList(VODataSourceAdapter.DATA_SOURCE_GET_ALARM_FILTEREDS_BY_ALARM_CONFIG, new Object[] { id });
		
		return vo2uiAdapter.convert(VODataSourceAdapter.DATA_SOURCE_GET_ALARM_FILTEREDS_BY_ALARM_CONFIG, alarmFiltereds);
	}
	
	public List<Map<String, Object>> getAlarmFilteredsByElement(String id) {
		
		List<Map<String, Object>> alarmFiltereds = voDataSourceAdapter.getList(VODataSourceAdapter.DATA_SOURCE_GET_ALARM_FILTEREDS_BY_ELEMENT, new Object[] { id });
		
		return vo2uiAdapter.convert(VODataSourceAdapter.DATA_SOURCE_GET_ALARM_FILTEREDS_BY_ELEMENT, alarmFiltereds);
	}

	@SuppressWarnings("unchecked")
	public Boolean sendAlarmFilteredActions(Object input) {
	    
	    Boolean res = false;
	    
	    Map<String, Object> sendAlarmFilteredActions = voDataSourceAdapter.put(VODataSourceAdapter.DATA_SOURCE_SEND_ALARM_FILTERED_ACTIONS, (Map<String, Object>) input);
	    
	    res = checkMapIsBoolean(sendAlarmFilteredActions);

	    return res;
	    
	  }

}
