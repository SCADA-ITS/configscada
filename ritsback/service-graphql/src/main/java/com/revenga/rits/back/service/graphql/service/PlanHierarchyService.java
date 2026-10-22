package com.revenga.rits.back.service.graphql.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import com.revenga.rits.back.data.core.model.PlanHierarchy;
import com.revenga.rits.back.data.core.model.PlanNode;
import com.revenga.rits.back.front.core.model.vo.PlanHierarchyVO;
import com.revenga.rits.back.service.graphql.type.PlanHierarchyGraphql;

@Service
public class PlanHierarchyService {

	public List<PlanHierarchyVO> getAll(Long managementAreaId) {

		List<PlanHierarchyVO> planHierarchiesVO = new ArrayList<>();

		List<PlanHierarchy> planHierarchies = EntitiesManager.getInstance().getPlanHierarchyRepository().getAll(false);

		if (!CollectionUtils.isEmpty(planHierarchies)) {

			for (PlanHierarchy planHierarchy : planHierarchies) {

				if (checkManagementArea(planHierarchy, managementAreaId)) {

					planHierarchiesVO.add(new PlanHierarchyGraphql(planHierarchy));
				}
			}

		}
		return planHierarchiesVO;
	}

	private boolean checkManagementArea(final PlanHierarchy planHierarchy, final Long managementAreaId) {

		boolean resp = false;

		PlanNode nodeParent = EntitiesManager.getInstance().getPlanNodeRepository().get(planHierarchy.getNodeParentId(),
				false);

		if (nodeParent != null && (nodeParent.getManagementAreaId() == null
				|| nodeParent.getManagementAreaId().equals(managementAreaId))) {

			PlanNode nodeChild = EntitiesManager.getInstance().getPlanNodeRepository()
					.get(planHierarchy.getNodeChildId(), false);

			if (nodeChild != null && (nodeChild.getManagementAreaId() == null
					|| nodeChild.getManagementAreaId().equals(managementAreaId))) {

				resp = true;

			}
		}
		return resp;
	}
}
