package com.revenga.rits.back.service.graphql.query;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.revenga.rits.back.front.core.model.vo.ElementHierarchyVO;
import com.revenga.rits.back.front.core.model.vo.ElementTreeVO;
import com.revenga.rits.back.front.core.model.vo.ElementTypeStateVO;
import com.revenga.rits.back.front.core.model.vo.ElementTypeVO;
import com.revenga.rits.back.front.core.model.vo.ElementVO;
import com.revenga.rits.back.front.core.model.vo.ElementValueStateVO;
import com.revenga.rits.back.service.graphql.service.ElementHierarchyService;
import com.revenga.rits.back.service.graphql.service.ElementService;
import com.revenga.rits.back.service.graphql.service.ElementTreeService;
import com.revenga.rits.back.service.graphql.service.ElementTypeService;

import graphql.schema.DataFetchingEnvironment;
import graphql.servlet.GraphQLContext;
import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
public class ElementQuery implements GraphQLQueryResolver {

	@Autowired
	private ElementService elementservice;

	@Autowired
	private ElementTypeService elementTypeService;
	
	@Autowired
	private ElementHierarchyService elementHierarchyService;
	
	@Autowired
	private ElementTreeService elementTreeService;

	public List<ElementVO> getElements(final List<String> elementIds, DataFetchingEnvironment env) {

	    GraphQLContext context = env.getContext();
	    log.debug("SessionId = " + context.getHttpServletRequest().get().getSession().getId());

		return elementservice.gets(elementIds);
	}

	public Optional<ElementVO> getElement(final String elementId) {

		return elementservice.get(elementId);
	}

	public List<ElementVO> getElementsByType(final String elementTypeId) {

		return elementservice.getByType(elementTypeId);
	}

	public List<ElementTypeVO> getElementTypes() {

		return elementTypeService.getAll();
	}

	public Optional<ElementTypeVO> getElementType(final String elementTypeId) {

		return elementTypeService.get(elementTypeId);
	}
	
	public List<ElementTypeStateVO> getElementTypeStates(final String elementTypeId) {

		return elementservice.getElementTypeStates(elementTypeId);
	}
	
	public List<ElementValueStateVO> getElementValueStates() {

		return elementservice.getElementValueStates();
	}
	
			  
	/**
	 * Method tha return all the ElementHierarchy record values stored into the database.
	 * @return: List of ElementHierarchyVO objects.
	 */
	public List<ElementHierarchyVO> getElementHierarchies() {

		return elementHierarchyService.getAll();
	}
	
	public List<ElementVO> getElementsByStretchs(final List<String> stretchIds, final String elementTypeId) {
		
		return elementservice.getByStretchs(stretchIds, elementTypeId);
	}
	
	public List<ElementTreeVO> getElementTrees(final String userId) {

		return elementTreeService.getsByUser(userId);
	}
	
	public Optional<ElementTreeVO> getElementTree(final String elementTreeId) {

		return elementTreeService.get(elementTreeId);
	}
}