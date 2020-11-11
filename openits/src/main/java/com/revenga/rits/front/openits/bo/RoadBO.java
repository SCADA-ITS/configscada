package com.revenga.rits.front.openits.bo;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revenga.rits.front.openits.adapter.impl.GraphqlAdapter;
import com.revenga.rits.front.openits.adapter.impl.VO2UIAdapter;
import com.revenga.rits.front.openits.adapter.impl.VODataSourceAdapter;

@Service
public class RoadBO {
	
	@Autowired
	GraphqlAdapter graphqlAdapter;
	
	@Autowired 
	VO2UIAdapter vo2uiAdapter;
	
	@Autowired
	VODataSourceAdapter voDataSourceAdapter;
	
	public List<Map<String, Object>> getRoads() {
		
		List<Map<String, Object>> roads = voDataSourceAdapter.getList(voDataSourceAdapter.getDataSourceGetRoads(), null);
		
		return vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(voDataSourceAdapter.getDataSourceGetRoads()).getFieldId(), roads);
	}
				
	public Map<String, Object> getRoad(String id) {
		
		Map<String, Object> road = voDataSourceAdapter.get(voDataSourceAdapter.getDataSourceGetRoad(), new Object[] { id });
		
		return vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(voDataSourceAdapter.getDataSourceGetRoad()).getFieldId(), road);
	}

}
