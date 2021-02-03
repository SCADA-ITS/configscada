package com.revenga.rits.front.openits.bo;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revenga.rits.front.openits.adapter.impl.GraphqlAdapter;
import com.revenga.rits.front.openits.adapter.impl.VO2UIAdapter;
import com.revenga.rits.front.openits.adapter.impl.VODataSourceAdapter;

@Service
public class StretchBO extends GenericBO {
	
	@Autowired
	GraphqlAdapter graphqlAdapter;
	
	@Autowired 
	VO2UIAdapter vo2uiAdapter;
	
	@Autowired
	VODataSourceAdapter voDataSourceAdapter;
	
	@Override
	public List<Map<String, Object>> getList() {
	
		List<Map<String, Object>> stretchs = voDataSourceAdapter.getList(VODataSourceAdapter.DATA_SOURCE_GET_STRETCHS, null);
		
		return vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(VODataSourceAdapter.DATA_SOURCE_GET_STRETCHS).getFieldId(), stretchs);
	}

	@Override
	public Map<String, Object> getItem(String id) {

		Map<String, Object> stretch = voDataSourceAdapter.get(VODataSourceAdapter.DATA_SOURCE_GET_STRETCH, new Object[] { id });
		
		return vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(VODataSourceAdapter.DATA_SOURCE_GET_STRETCH).getFieldId(), stretch);
	}
	
	public List<Map<String, Object>> getStretchByRoad(String id) {
		
		List<Map<String, Object>> stretchs = voDataSourceAdapter.getList(VODataSourceAdapter.DATA_SOURCE_GET_STRETCH_BY_ROAD, new Object[] { id });
		
		return vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(VODataSourceAdapter.DATA_SOURCE_GET_STRETCH_BY_ROAD).getFieldId(), stretchs);
	}

}
