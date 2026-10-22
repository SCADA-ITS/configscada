package com.revenga.rits.back.entities.lib.repository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;

import com.revenga.rits.back.data.core.model.BasicElement;
import com.revenga.rits.back.data.core.model.ElementSpreadState;
import com.revenga.rits.back.entities.lib.repository.entity.EntityCacheable;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository;

public class ElementSpreadStateRepository extends EntityRepository implements EntityCacheable<ElementSpreadState> {

	// region Constructors
	public ElementSpreadStateRepository() {
		
		super(ElementSpreadState.class);
	}
	
	public ElementSpreadStateRepository(Boolean cacheable) {
		
		super(ElementSpreadState.class, cacheable);
	}
	
	public ElementSpreadStateRepository(Boolean cacheable, Long cacheRetentionTime) {
		
		super(ElementSpreadState.class, cacheable, cacheRetentionTime);
	}
	// endregion Constructors

	// region getters
	public List<ElementSpreadState> getsBySourceElementType(Long sourceElementTypeId, Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(new String[] { "sourceElementTypeId" },
				new String[] { String.valueOf(sourceElementTypeId) }, fillObject);
		
		if (!CollectionUtils.isEmpty(basicElements)) {
			
			return basicElements.stream()
					.map(be -> (ElementSpreadState) be).collect(Collectors.toList());
		}
		
		return Collections.emptyList();	
	}
	
	public List<ElementSpreadState> getsByTargetElementType(Long targetElementTypeId, Boolean fillObject) {

		
		List<BasicElement> basicElements = getsFromCache(new String[] { "targetElementTypeId" },
				new String[] { String.valueOf(targetElementTypeId) }, fillObject);
		
		if (!CollectionUtils.isEmpty(basicElements)) {
			
			return basicElements.stream()
					.map(be -> (ElementSpreadState) be).collect(Collectors.toList());
		}
		
		return Collections.emptyList();	
	}
	
	@Override
	public List<ElementSpreadState> getAll(Boolean fillObject) {
		
		List<BasicElement> basicElements = getsFromCache(fillObject);
		
		if (!CollectionUtils.isEmpty(basicElements)) {
			
			return basicElements.stream()
					.map(be -> (ElementSpreadState) be).collect(Collectors.toList());
		}
		
		return Collections.emptyList();
	}
	// endregion getters

	// region puts
	@Override
	public ElementSpreadState put(ElementSpreadState entity) {

		return (ElementSpreadState) putFromCache(entity);
	}

	@Override
	public List<ElementSpreadState> put(List<ElementSpreadState> entities) {
		
		List<BasicElement> basicElements = entities.stream()
												   .map(be -> (BasicElement) be)
												   .collect(Collectors.toList());
		
		
		basicElements = putsFromCache(basicElements);
		
		return basicElements.stream()
						    .map(be -> (ElementSpreadState) be)
						    .collect(Collectors.toList());
	}
	// endregion puts

	// region dels
	@Override
	public ElementSpreadState del(ElementSpreadState entity) {
		
		return (ElementSpreadState) delFromCache(entity);
		
	}

	@Override
	public List<ElementSpreadState> del(List<ElementSpreadState> entities) {
		
		List<BasicElement> basicElements = entities.stream()
												   .map(be -> (BasicElement) be)
												   .collect(Collectors.toList());

		basicElements = delFromCache(basicElements);
		
		return basicElements.stream()
							.map(be -> (ElementSpreadState) be)
							.collect(Collectors.toList());
	}
	// endregion dels

}
