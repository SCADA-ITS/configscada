package com.revenga.rits.back.service.graphql.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.revenga.rits.back.data.core.model.ElementHierarchy;
import com.revenga.rits.back.front.core.model.vo.ElementHierarchyVO;
import com.revenga.rits.back.service.graphql.helper.MapRequests;
import com.revenga.rits.back.service.graphql.type.ElementHierarchyGraphql;

@Service
public class ElementHierarchyService implements QueryInterface<ElementHierarchyVO> {

	@Override
	public List<ElementHierarchyVO> getAll() {
		
		List<ElementHierarchyVO> elementHierarchyes = new ArrayList<>();

		MapRequests mapRequests =  new MapRequests(MapRequests.Mode.GET_ALL);
		
		for (ElementHierarchy elementHierarchy : EntitiesManager.getInstance().getElementHierarchiesRepository().getAll(false)) {

			elementHierarchyes.add(new ElementHierarchyGraphql(elementHierarchy, mapRequests));
		}

		return elementHierarchyes;		
	}

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
}
