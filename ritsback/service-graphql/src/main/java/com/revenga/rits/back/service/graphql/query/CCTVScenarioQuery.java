package com.revenga.rits.back.service.graphql.query;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.revenga.rits.back.front.core.model.vo.CCTVScenarioHierarchyVO;
import com.revenga.rits.back.front.core.model.vo.CCTVScenarioVO;
import com.revenga.rits.back.service.graphql.service.CCTVScenarioHierarchyService;
import com.revenga.rits.back.service.graphql.service.CCTVScenarioService;

@Component
public class CCTVScenarioQuery implements GraphQLQueryResolver {

	@Autowired
	private CCTVScenarioService cctvScenarioService;
	
	@Autowired
	private CCTVScenarioHierarchyService cctvScenarioHierarchyService;	

	
	public Optional<CCTVScenarioVO> getCCTVScenario(final String scenarioId) {

		return cctvScenarioService.get(scenarioId);
	}
	
	public List<CCTVScenarioHierarchyVO> getCCTVScenarioHierarchies() {

		return cctvScenarioHierarchyService.getAll();
	}
	
	public List<CCTVScenarioVO> getCCTVScenarios(){
		
		return cctvScenarioService.getAll();
	}
}
