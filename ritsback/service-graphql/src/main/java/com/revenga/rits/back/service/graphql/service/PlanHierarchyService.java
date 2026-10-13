package com.revenga.rits.back.service.graphql.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import com.revenga.rits.back.data.core.model.PlanHierarchy;
import com.revenga.rits.back.front.core.model.vo.PlanHierarchyVO;
import com.revenga.rits.back.service.graphql.type.PlanHierarchyGraphql;


@Service
public class PlanHierarchyService implements QueryInterface<PlanHierarchyVO> {

	@Override
	public List<PlanHierarchyVO> getAll() {

		List<PlanHierarchyVO> planHierarchiesVO = new ArrayList<>();

		List<PlanHierarchy> planHierarchies = EntitiesManager.getInstance().getPlanHierarchyRepository().getAll(false);

		if (!CollectionUtils.isEmpty(planHierarchies)) {

			for (PlanHierarchy planHierarchy : planHierarchies) {

				planHierarchiesVO.add(new PlanHierarchyGraphql(planHierarchy));
			}
		}
		return planHierarchiesVO;
	}


	@Override
	public List<PlanHierarchyVO> gets(List<String> idItems) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Optional<PlanHierarchyVO> get(String idItem) {
		// TODO Auto-generated method stub
		return null;
	}
}
