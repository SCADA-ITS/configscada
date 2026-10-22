package com.revenga.rits.front.openits.bo;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.revenga.rits.front.openits.adapter.impl.VODataSourceAdapter;

@Service
public class CCTVScenarioBO extends GenericBO {
			
	@PostConstruct
	private void initialize() {
		
		dataSourceItem = VODataSourceAdapter.DATA_SOURCE_GET_CCTV_SCENARIO;
	}
		
	@Override
	public List<Map<String, Object>> getList() {
		
		return Collections.emptyList();
	}

	public List<Map<String, Object>> getScenarioHierarchies() {
		
		List<Map<String, Object>> cctvScenarioHierarchies = voDataSourceAdapter.getList(VODataSourceAdapter.DATA_SOURCE_GET_CCTV_SCENARIO_HIERARCHIES, null);
		
		return vo2uiAdapter.convert(VODataSourceAdapter.DATA_SOURCE_GET_CCTV_SCENARIO_HIERARCHIES, cctvScenarioHierarchies);
	}
}
