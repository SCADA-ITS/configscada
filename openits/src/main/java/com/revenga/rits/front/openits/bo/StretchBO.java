package com.revenga.rits.front.openits.bo;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revenga.rits.front.openits.adapter.impl.GraphqlAdapter;
import com.revenga.rits.front.openits.adapter.impl.VO2UIAdapter;
import com.revenga.rits.front.openits.adapter.impl.VODataSourceAdapter;

@Service
public class StretchBO {
	
	@Autowired
	GraphqlAdapter graphqlAdapter;
	
	@Autowired 
	VO2UIAdapter vo2uiAdapter;
	
	@Autowired
	VODataSourceAdapter voDataSourceAdapter;
	
	public List<Map<String, Object>> getStretchs() {
		
		List<Map<String, Object>> stretchs = voDataSourceAdapter.getList(voDataSourceAdapter.getDataSourceGetStretchs(), null);
		
		return vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(voDataSourceAdapter.getDataSourceGetStretchs()).getFieldId(), stretchs);
	}
				
	public Map<String, Object> getStretch(String id) {
		
		Map<String, Object> stretch = voDataSourceAdapter.get(voDataSourceAdapter.getDataSourceGetStretch(), new Object[] { id });
		
		return vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(voDataSourceAdapter.getDataSourceGetStretch()).getFieldId(), stretch);
	}
	
	public List<Map<String, Object>> getStretchByRoad(String id) {
		
		List<Map<String, Object>> stretchs = voDataSourceAdapter.getList(voDataSourceAdapter.getDataSourceGetStretchByRoad(), new Object[] { id });
		
		return vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(voDataSourceAdapter.getDataSourceGetStretchByRoad()).getFieldId(), stretchs);
	}

}
