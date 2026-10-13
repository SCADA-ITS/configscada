package com.revenga.rits.back.service.graphql.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import com.revenga.rits.back.data.core.model.CCTVScenarioHierarchy;
import com.revenga.rits.back.front.core.model.vo.CCTVScenarioHierarchyVO;
import com.revenga.rits.back.service.graphql.helper.MapRequests;
import com.revenga.rits.back.service.graphql.type.CCTVScenarioHierarchyGraphql;


@Service
public class CCTVScenarioHierarchyService implements QueryInterface<CCTVScenarioHierarchyVO> {

	@Override
	public List<CCTVScenarioHierarchyVO> getAll() {

		List<CCTVScenarioHierarchyVO> scenarioHierarchiesVO = new ArrayList<>();

		List<CCTVScenarioHierarchy> scenarioHierarchies = EntitiesManager.getInstance().getCctvScenarioHierarchyRepository().getAll(false);

		if (!CollectionUtils.isEmpty(scenarioHierarchies)) {

			MapRequests mapRequests =  new MapRequests(MapRequests.Mode.GET_ALL);
			for (CCTVScenarioHierarchy scenarioHierarchy : scenarioHierarchies) {

				scenarioHierarchiesVO.add(new CCTVScenarioHierarchyGraphql(scenarioHierarchy, mapRequests));
			}
		}

		return scenarioHierarchiesVO;
	}


	@Override
	public List<CCTVScenarioHierarchyVO> gets(List<String> idItems) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Optional<CCTVScenarioHierarchyVO> get(String idItem) {
		// TODO Auto-generated method stub
		return null;
	}
}
