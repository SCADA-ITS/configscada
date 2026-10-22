package com.revenga.rits.back.entities.lib.repository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;

import com.revenga.rits.back.data.core.model.BasicElement;
import com.revenga.rits.back.data.core.model.ElementTypeParam;
import com.revenga.rits.back.entities.lib.repository.entity.EntityCacheable;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository;

public class ElementTypeParamRepository extends EntityRepository implements EntityCacheable<ElementTypeParam> {

	// region Constructors
	public ElementTypeParamRepository() {
		
		super(ElementTypeParam.class);
	}
	
	public ElementTypeParamRepository(Boolean cacheable) {
		
		super(ElementTypeParam.class, cacheable);
	}
	
	public ElementTypeParamRepository(Boolean cacheable, Long cacheRetentionTime) {
		
		super(ElementTypeParam.class, cacheable, cacheRetentionTime);
	}
	// endregion Constructors

	// region getters
	public ElementTypeParam get(Long elementTypeId, Long paramTypeId, Long id, Boolean fillObject) {
		
		return (ElementTypeParam) getFromCache(
				new String[] { String.valueOf(elementTypeId), String.valueOf(paramTypeId), String.valueOf(id) },
				fillObject);
	}
	
	public List<ElementTypeParam> gets(Long elementTypeId, Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(new String[] { String.valueOf(elementTypeId), null, null }, fillObject);
		
		if (!CollectionUtils.isEmpty(basicElements)) {
			
			return basicElements.stream()
					.map(ElementTypeParam.class::cast).collect(Collectors.toList());
		}
		
		return Collections.emptyList();
	}
	
	public List<ElementTypeParam> gets(Long elementTypeId, Long paramTypeId, Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(
				new String[] { String.valueOf(elementTypeId), String.valueOf(paramTypeId), null }, fillObject);
		
		if (!CollectionUtils.isEmpty(basicElements)) {
			
			return basicElements.stream()
					.map(ElementTypeParam.class::cast).collect(Collectors.toList());
		}
		
		return Collections.emptyList();
		
	}
	
	@Override
	public List<ElementTypeParam> getAll(Boolean fillObject) {
		
		List<BasicElement> basicElements = getsFromCache(fillObject);
		
		if (!CollectionUtils.isEmpty(basicElements)) {
			
			return basicElements.stream()
					.map(ElementTypeParam.class::cast).collect(Collectors.toList());
		}
		
		return Collections.emptyList();
	}
	// endregion getters

	// region puts
	@Override
	public ElementTypeParam put(ElementTypeParam entity) {

		return (ElementTypeParam) putFromCache(entity);
	}

	@Override
	public List<ElementTypeParam> put(List<ElementTypeParam> entities) {
		
		List<BasicElement> basicElements = entities.stream()
												   .map(BasicElement.class::cast)
												   .collect(Collectors.toList());
		
		
		basicElements = putsFromCache(basicElements);
		
		return basicElements.stream()
						    .map(ElementTypeParam.class::cast)
						    .collect(Collectors.toList());
	}
	// endregion puts

	// region dels
	@Override
	public ElementTypeParam del(ElementTypeParam entity) {
		
		return (ElementTypeParam) delFromCache(entity);
		
	}

	@Override
	public List<ElementTypeParam> del(List<ElementTypeParam> entities) {
		
		List<BasicElement> basicElements = entities.stream()
												   .map(BasicElement.class::cast)
												   .collect(Collectors.toList());

		basicElements = delFromCache(basicElements);
		
		return basicElements.stream()
							.map(ElementTypeParam.class::cast)
							.collect(Collectors.toList());
	}
	// endregion dels

}
