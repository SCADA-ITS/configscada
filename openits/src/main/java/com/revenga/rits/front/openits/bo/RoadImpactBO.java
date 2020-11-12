package com.revenga.rits.front.openits.bo;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revenga.rits.front.openits.adapter.impl.GraphqlAdapter;
import com.revenga.rits.front.openits.adapter.impl.VO2UIAdapter;
import com.revenga.rits.front.openits.adapter.impl.VODataSourceAdapter;

@Service
public class RoadImpactBO {
	
	@Autowired
	GraphqlAdapter graphqlAdapter;
	
	@Autowired 
	VO2UIAdapter vo2uiAdapter;
	
	@Autowired
	VODataSourceAdapter voDataSourceAdapter;
	
	public List<Map<String, Object>> getRoadImpacts() {
		
		List<Map<String, Object>> roadImpacts = voDataSourceAdapter.getList(voDataSourceAdapter.getDataSourceGetRoadImpacts(), null);
		
		return vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(voDataSourceAdapter.getDataSourceGetRoadImpacts()).getFieldId(), roadImpacts);
	}
				
	public Map<String, Object> getRoadImpact(String id) {
		
		Map<String, Object> roadImpact = voDataSourceAdapter.get(voDataSourceAdapter.getDataSourceGetRoadImpact(), new Object[] { id });
		
		return vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(voDataSourceAdapter.getDataSourceGetRoadImpact()).getFieldId(), roadImpact);
	}
	
	public List<Map<String, Object>> getInitRoadImpacts(String id) {
		
		List<Map<String, Object>> initRoadImpacts = voDataSourceAdapter.getList(voDataSourceAdapter.getDataSourceGetInitRoadImpacts(), new Object[] { id });
		
		return vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(voDataSourceAdapter.getDataSourceGetInitRoadImpacts()).getFieldId(), initRoadImpacts);
	}
	
	public Map<String, Object> getRoadImpactsByIncident(String id) {
		
		Map<String, Object> roadImpactsByIncident = voDataSourceAdapter.get(voDataSourceAdapter.getDataSourceGetRoadImpactsByIncident(), new Object[] { id });
		
		return vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(voDataSourceAdapter.getDataSourceGetRoadImpactsByIncident()).getFieldId(), roadImpactsByIncident);
	}


}
