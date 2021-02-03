package com.revenga.rits.front.openits.bo;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revenga.rits.front.openits.adapter.impl.GraphqlAdapter;
import com.revenga.rits.front.openits.adapter.impl.VO2UIAdapter;
import com.revenga.rits.front.openits.adapter.impl.VODataSourceAdapter;

@Service
public class AlarmMutedBO extends GenericBO {
	
	@Autowired
	GraphqlAdapter graphqlAdapter;
	
	@Autowired 
	VO2UIAdapter vo2uiAdapter;
	
	@Autowired
	VODataSourceAdapter voDataSourceAdapter;
	
	@Override
	public List<Map<String, Object>> getList() {

		List<Map<String, Object>> alarmMuteds = voDataSourceAdapter.getList(VODataSourceAdapter.DATA_SOURCE_GET_ALARM_MUTEDS, null);
		
		return vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(VODataSourceAdapter.DATA_SOURCE_GET_ALARM_MUTEDS).getFieldId(), alarmMuteds);
	}

	@Override
	public Map<String, Object> getItem(String id) {

		Map<String, Object> alarmMuted = voDataSourceAdapter.get(VODataSourceAdapter.DATA_SOURCE_GET_ALARM_MUTED, new Object[] { id });
		
		return vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(VODataSourceAdapter.DATA_SOURCE_GET_ALARM_MUTED).getFieldId(), alarmMuted);
	}
	
	public List<Map<String, Object>> getAlarmMutedsByAlarmConfig(String id) {
		
		List<Map<String, Object>> alarmMuteds = voDataSourceAdapter.getList(VODataSourceAdapter.DATA_SOURCE_GET_ALARM_MUTEDS_BY_ALARM_CONFIG, new Object[] { id });
		
		return vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(VODataSourceAdapter.DATA_SOURCE_GET_ALARM_MUTEDS_BY_ALARM_CONFIG).getFieldId(), alarmMuteds);
	}
	
	public List<Map<String, Object>> getAlarmMutedsByElement(String id) {
		
		List<Map<String, Object>> alarmMuteds = voDataSourceAdapter.getList(VODataSourceAdapter.DATA_SOURCE_GET_ALARM_MUTEDS_BY_ELEMENT, new Object[] { id });
		
		return vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(VODataSourceAdapter.DATA_SOURCE_GET_ALARM_MUTEDS_BY_ELEMENT).getFieldId(), alarmMuteds);
	}


	public Boolean sendAlarmMutedActions(Object input) {
	    
	    Boolean res = false;
	    
	    Map<String, Object> sendAlarmMutedActions = voDataSourceAdapter.put(VODataSourceAdapter.DATA_SOURCE_SEND_ALARM_MUTED_ACTIONS, (Map<String, Object>) input);
	    
	    res = checkMapIsBoolean(sendAlarmMutedActions);

	    return res;
	    
	  }

}
