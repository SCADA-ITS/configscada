package com.revenga.rits.back.service.graphql.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import com.revenga.rits.back.data.core.model.CCTVScenarioHierarchy;
import com.revenga.rits.back.data.core.model.CCTVScenarioNode;
import com.revenga.rits.back.front.core.model.vo.CCTVScenarioHierarchyVO;
import com.revenga.rits.back.service.graphql.helper.MapRequests;
import com.revenga.rits.back.service.graphql.type.CCTVScenarioHierarchyGraphql;

@Service
public class CCTVScenarioHierarchyService {

	public List<CCTVScenarioHierarchyVO> getAll(final Long managementAreaId) {

		List<CCTVScenarioHierarchyVO> scenarioHierarchiesVO = new ArrayList<>();

		List<CCTVScenarioHierarchy> scenarioHierarchies = EntitiesManager.getInstance()
				.getCctvScenarioHierarchyRepository().getAll(false);

		if (!CollectionUtils.isEmpty(scenarioHierarchies)) {

			MapRequests mapRequests = new MapRequests(MapRequests.Mode.GET_ALL);
			for (CCTVScenarioHierarchy scenarioHierarchy : scenarioHierarchies) {

				if (checkManagementArea(scenarioHierarchy, managementAreaId)) {
					
					scenarioHierarchiesVO.add(new CCTVScenarioHierarchyGraphql(scenarioHierarchy, mapRequests));
				}
			}
		}

		return scenarioHierarchiesVO;
	}

	private boolean checkManagementArea(final CCTVScenarioHierarchy scenarioHierarchy, final Long managementAreaId) {

		boolean resp = false;

		CCTVScenarioNode nodeParent = EntitiesManager.getInstance().getCctvScenarioNodeRepository()
				.get(scenarioHierarchy.getNodeParentId(), false);

		if (nodeParent != null && (nodeParent.getManagementAreaId() == null
				|| nodeParent.getManagementAreaId().equals(managementAreaId))) {

			CCTVScenarioNode nodeChild = EntitiesManager.getInstance().getCctvScenarioNodeRepository()
					.get(scenarioHierarchy.getNodeChildId(), false);

			if (nodeChild != null && (nodeChild.getManagementAreaId() == null
					|| nodeChild.getManagementAreaId().equals(managementAreaId))) {

				resp = true;

			}
		}

		return resp;
	}
}
