package com.revenga.rits.back.service.graphql.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import com.revenga.rits.back.data.core.model.ElementHierarchy;
import com.revenga.rits.back.data.core.model.ManagementAreaElement;
import com.revenga.rits.back.front.core.model.vo.ElementHierarchyVO;
import com.revenga.rits.back.service.graphql.helper.MapRequests;
import com.revenga.rits.back.service.graphql.type.ElementHierarchyGraphql;

@Service
public class ElementHierarchyService implements QueryInterface<ElementHierarchyVO> {

	@Override
	public Optional<ElementHierarchyVO> get(String idItem) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ElementHierarchyVO> gets(List<String> idItems) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<ElementHierarchyVO> getAll(Long managementAreaId) {

		List<ElementHierarchyVO> elementHierarchyes = new ArrayList<>();

		MapRequests mapRequests = new MapRequests(MapRequests.Mode.GET_ALL);

		for (ElementHierarchy elementHierarchy : EntitiesManager.getInstance().getElementHierarchiesRepository()
				.getAll(false)) {

			if (checkManagementArea(elementHierarchy, managementAreaId)) {
				
				elementHierarchyes.add(new ElementHierarchyGraphql(elementHierarchy, mapRequests));
			}
		}

		return elementHierarchyes;
	}

	@Override
	public List<ElementHierarchyVO> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	private boolean checkManagementArea(final ElementHierarchy elementHierarchy, final Long managementAreaId) {

		boolean resp = false;

		List<ManagementAreaElement> managementAreaParent = EntitiesManager.getInstance()
				.getManagementAreaElementRepository()
				.gets(elementHierarchy.getParentElementTypeId(), elementHierarchy.getParentElementId(), false);

		if (CollectionUtils.isEmpty(managementAreaParent) || (managementAreaId != null
				&& managementAreaParent.stream().filter(e -> e.getId().equals(managementAreaId)).count() > 0)) {

			List<ManagementAreaElement> managementAreaChild = EntitiesManager.getInstance()
					.getManagementAreaElementRepository()
					.gets(elementHierarchy.getChildElementTypeId(), elementHierarchy.getChildElementId(), false);

			if (CollectionUtils.isEmpty(managementAreaChild) || (managementAreaId != null
					&& managementAreaChild.stream().filter(e -> e.getId().equals(managementAreaId)).count() > 0)) {

				resp = true;
			}
		}

		return resp;
	}
}
