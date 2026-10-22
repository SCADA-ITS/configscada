package com.revenga.rits.front.openits.bo;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revenga.rits.front.openits.adapter.impl.GraphqlAdapter;
import com.revenga.rits.front.openits.adapter.impl.VODataSourceAdapter;

@Service
public class SmThresholdBO extends GenericBO {
	
	@Autowired
	GraphqlAdapter graphqlAdapter;
	
	public List<Map<String, Object>> getSmThresholds(String stateMachineId) {

		List<Map<String, Object>> stateMachine = voDataSourceAdapter
				.getList(VODataSourceAdapter.DATA_SOURCE_GET_THRESHOLD_BY_STATE_MACHINE_ID, new Object[] { stateMachineId });

		return vo2uiAdapter.convert(VODataSourceAdapter.DATA_SOURCE_GET_THRESHOLD_BY_STATE_MACHINE_ID, stateMachine);
	}
	
	public List<Map<String, Object>> getSmStateMachines(String smStateMachineIds) {

		List<Map<String, Object>> stateMachineIds = voDataSourceAdapter
				.getList(VODataSourceAdapter.DATA_SOURCE_GET_THRESHOLD_BY_STATE_MACHINE, new Object[] { smStateMachineIds });

		return vo2uiAdapter.convert(VODataSourceAdapter.DATA_SOURCE_GET_THRESHOLD_BY_STATE_MACHINE, stateMachineIds);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> updateSmThresholds(Object input) {

		Map<String, Object> updatedSmThresholds = voDataSourceAdapter
				.put(VODataSourceAdapter.DATA_SOURCE_UPDATE_THRESHOLD, (Map<String, Object>) input);
		
		return vo2uiAdapter.convert(VODataSourceAdapter.DATA_SOURCE_UPDATE_THRESHOLD, updatedSmThresholds);
	}
}
