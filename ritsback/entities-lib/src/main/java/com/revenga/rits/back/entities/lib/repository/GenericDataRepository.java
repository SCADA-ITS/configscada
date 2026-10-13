package com.revenga.rits.back.entities.lib.repository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;

import com.revenga.rits.back.data.core.model.BasicElement;
import com.revenga.rits.back.data.core.model.GenericData;
import com.revenga.rits.back.entities.lib.repository.entity.EntityCacheable;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository;

public class GenericDataRepository extends EntityRepository implements EntityCacheable<GenericData> {

	// region Constructors
	public GenericDataRepository() {
		
		super(GenericData.class);
	}
	
	public GenericDataRepository(Boolean cacheable) {
		
		super(GenericData.class, cacheable);
	}
	
	public GenericDataRepository(Boolean cacheable, Long cacheRetentionTime) {
		
		super(GenericData.class, cacheable, cacheRetentionTime);
	}
	// endregion Constructors

	// region getters
	public GenericData get(Long id, Boolean fillObject) {
		
		return (GenericData) getFromCache(new String[] { String.valueOf(id) },
				fillObject);
	}
	
	@Override
	public List<GenericData> getAll(Boolean fillObject) {
		
		List<BasicElement> basicElements = getsFromCache(fillObject);
		
		if (!CollectionUtils.isEmpty(basicElements)) {
			
			return basicElements.stream()
					.map(be -> (GenericData) be).collect(Collectors.toList());
		}
		
		return Collections.emptyList();
	}
	// endregion getters

	// region puts
	@Override
	public GenericData put(GenericData entity) {

		return (GenericData) putFromCache(entity);
	}

	@Override
	public List<GenericData> put(List<GenericData> entities) {
		
		List<BasicElement> basicElements = entities.stream()
												   .map(element -> (BasicElement) element)
												   .collect(Collectors.toList());
		
		
		basicElements = putsFromCache(basicElements);
		
		return basicElements.stream()
						    .map(be -> (GenericData) be)
						    .collect(Collectors.toList());
	}
	// endregion puts

	// region dels
	@Override
	public GenericData del(GenericData entity) {
		
		return (GenericData) delFromCache(entity);
		
	}

	@Override
	public List<GenericData> del(List<GenericData> entities) {
		
		List<BasicElement> basicElements = entities.stream()
												   .map(element -> (BasicElement) element)
												   .collect(Collectors.toList());

		basicElements = delFromCache(basicElements);
		
		return basicElements.stream()
							.map(be -> (GenericData) be)
							.collect(Collectors.toList());
	}
	// endregion dels

}
