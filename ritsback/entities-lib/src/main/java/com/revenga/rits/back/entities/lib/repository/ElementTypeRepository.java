package com.revenga.rits.back.entities.lib.repository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;

import com.revenga.rits.back.data.core.model.BasicElement;
import com.revenga.rits.back.data.core.model.ElementType;
import com.revenga.rits.back.entities.lib.repository.entity.EntityCacheable;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository;

public class ElementTypeRepository extends EntityRepository implements EntityCacheable<ElementType> {

	// region Constructors
	public ElementTypeRepository() {
		
		super(ElementType.class);
	}
	
	public ElementTypeRepository(Boolean cacheable) {
		
		super(ElementType.class, cacheable);
	}
	
	public ElementTypeRepository(Boolean cacheable, Long cacheRetentionTime) {
		
		super(ElementType.class, cacheable, cacheRetentionTime);
	}
	// endregion Constructors

	// region getters
	public ElementType get(Long id, Boolean fillObject) {
		
		return (ElementType) getFromCache(new String[] { String.valueOf(id) }, fillObject);
	}
	
	@Override
	public List<ElementType> getAll(Boolean fillObject) {
		
		List<BasicElement> basicElements = getsFromCache(fillObject);
		
		if (!CollectionUtils.isEmpty(basicElements)) {
			
			return basicElements.stream()
					.map(be -> (ElementType) be).collect(Collectors.toList());
		}
		
		return Collections.emptyList();
	}
	// endregion getters

	// region puts
	@Override
	public ElementType put(ElementType entity) {

		return (ElementType) putFromCache(entity);
	}

	@Override
	public List<ElementType> put(List<ElementType> entities) {
		
		List<BasicElement> basicElements = entities.stream()
												   .map(element -> (BasicElement) element)
												   .collect(Collectors.toList());
		
		
		basicElements = putsFromCache(basicElements);
		
		return basicElements.stream()
						    .map(be -> (ElementType) be)
						    .collect(Collectors.toList());
	}
	// endregion puts

	// region dels
	@Override
	public ElementType del(ElementType entity) {
		
		return (ElementType) delFromCache(entity);
		
	}

	@Override
	public List<ElementType> del(List<ElementType> entities) {
		
		List<BasicElement> basicElements = entities.stream()
												   .map(element -> (BasicElement) element)
												   .collect(Collectors.toList());

		basicElements = delFromCache(basicElements);
		
		return basicElements.stream()
							.map(be -> (ElementType) be)
							.collect(Collectors.toList());
	}
	// endregion dels

}
