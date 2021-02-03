package com.revenga.rits.front.openits.bo;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revenga.rits.front.openits.adapter.impl.GraphqlAdapter;
import com.revenga.rits.front.openits.adapter.impl.VO2UIAdapter;
import com.revenga.rits.front.openits.adapter.impl.VODataSourceAdapter;

@Service
public class RoadImpactBO extends GenericBO {
	
	@Autowired
	GraphqlAdapter graphqlAdapter;
	
	@Autowired 
	VO2UIAdapter vo2uiAdapter;
	
	@Autowired
	VODataSourceAdapter voDataSourceAdapter;
	
	public List<Map<String, Object>> getRoadImpacts() {
		
		List<Map<String, Object>> roadImpacts = voDataSourceAdapter.getList(VODataSourceAdapter.DATA_SOURCE_GET_ROAD_IMPACTS, null);
		
		return vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(VODataSourceAdapter.DATA_SOURCE_GET_ROAD_IMPACTS).getFieldId(), roadImpacts);
	}
				
	public Map<String, Object> getRoadImpact(String id) {
		
		Map<String, Object> roadImpact = voDataSourceAdapter.get(VODataSourceAdapter.DATA_SOURCE_GET_ROAD_IMPACT, new Object[] { id });
		
		return vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(VODataSourceAdapter.DATA_SOURCE_GET_ROAD_IMPACT).getFieldId(), roadImpact);
	}
	
	public List<Map<String, Object>> getInitRoadImpacts(String id) {
		
		List<Map<String, Object>> initRoadImpacts = voDataSourceAdapter.getList(VODataSourceAdapter.DATA_SOURCE_GET_INIT_ROAD_IMPACTS, new Object[] { id });
		
		return vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(VODataSourceAdapter.DATA_SOURCE_GET_INIT_ROAD_IMPACTS).getFieldId(), initRoadImpacts);
	}
	
	public Map<String, Object> getRoadImpactsByIncident(String id) {
		
		Map<String, Object> roadImpactsByIncident = voDataSourceAdapter.get(VODataSourceAdapter.DATA_SOURCE_GET_ROAD_IMPACTS_BY_INCIDENT, new Object[] { id });
		
		return vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(VODataSourceAdapter.DATA_SOURCE_GET_ROAD_IMPACTS_BY_INCIDENT).getFieldId(), roadImpactsByIncident);
	}

	@Override
	public List<Map<String, Object>> getList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> getItem(String id) {
		// TODO Auto-generated method stub
		return null;
	}


}
