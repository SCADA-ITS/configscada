package com.revenga.rits.front.openits.bo;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revenga.rits.front.openits.adapter.impl.GraphqlAdapter;
import com.revenga.rits.front.openits.adapter.impl.VO2UIAdapter;
import com.revenga.rits.front.openits.adapter.impl.VODataSourceAdapter;

@Service
public class AlarmThresoldOperationBO {
	
	@Autowired
	GraphqlAdapter graphqlAdapter;
	
	@Autowired 
	VO2UIAdapter vo2uiAdapter;
	
	@Autowired
	VODataSourceAdapter voDataSourceAdapter;
	
	public List<Map<String, Object>> getAlarmThresholdOperations() {
		
		List<Map<String, Object>> alarmThresholdOperations = voDataSourceAdapter.getList(voDataSourceAdapter.getDataSourceGetAlarmThresholdOperations(), null);
		
		return vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(voDataSourceAdapter.getDataSourceGetAlarmThresholdOperations()).getFieldId(), alarmThresholdOperations);
	}
				
	public Map<String, Object> getAlarmThresholdOperation(String id) {
		
		Map<String, Object> alarmThresholdOperation = voDataSourceAdapter.get(voDataSourceAdapter.getDataSourceGetAlarmThresholdOperation(), new Object[] { id });
		
		return vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(voDataSourceAdapter.getDataSourceGetAlarmThresholdOperation()).getFieldId(), alarmThresholdOperation);
	}

}
