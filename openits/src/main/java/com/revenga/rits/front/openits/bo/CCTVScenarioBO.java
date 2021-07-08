package com.revenga.rits.front.openits.bo;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revenga.rits.front.openits.adapter.impl.VO2UIAdapter;
import com.revenga.rits.front.openits.adapter.impl.VODataSourceAdapter;

@Service
public class CCTVScenarioBO extends GenericBO {
			
	@Autowired 
	VO2UIAdapter vo2uiAdapter;
	
	@Autowired
	VODataSourceAdapter voDataSourceAdapter;
		
	@Override
	public List<Map<String, Object>> getList() {
		
		return Collections.emptyList();
	}

	@Override
	public Map<String, Object> getItem(String id) {
		
		List<Map<String, Object>> cctvScenario = voDataSourceAdapter
				.getList(VODataSourceAdapter.DATA_SOURCE_GET_CCTV_SCENARIO, new String[] { id } );
		
		if (!CollectionUtils.isEmpty(cctvScenario)) {
		
			return vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
					.getById(VODataSourceAdapter.DATA_SOURCE_GET_CCTV_SCENARIO).getFieldId(), cctvScenario.get(0));
		}
		
		return null;
	}
	
	public List<Map<String, Object>> getScenarioHierarchies() {
		
		List<Map<String, Object>> cctvScenarioHierarchies = voDataSourceAdapter.getList(VODataSourceAdapter.DATA_SOURCE_GET_CCTV_SCENARIO_HIERARCHIES, null);
		
		return vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(VODataSourceAdapter.DATA_SOURCE_GET_CCTV_SCENARIO_HIERARCHIES).getFieldId(), cctvScenarioHierarchies);
	}
}
