package com.revenga.rits.back.entities.lib.repository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;

import com.revenga.rits.back.data.core.model.BasicElement;
import com.revenga.rits.back.data.core.model.ImsTaskTypeParam;
import com.revenga.rits.back.entities.lib.repository.entity.EntityCacheable;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository;

public class ImsTaskTypeParamRepository extends EntityRepository implements EntityCacheable<ImsTaskTypeParam> {

	// region Constructors
	public ImsTaskTypeParamRepository() {
		
		super(ImsTaskTypeParam.class);
	}
	
	public ImsTaskTypeParamRepository(Boolean cacheable) {
		
		super(ImsTaskTypeParam.class, cacheable);
	}
	
	public ImsTaskTypeParamRepository(Boolean cacheable, Long cacheRetentionTime) {
		
		super(ImsTaskTypeParam.class, cacheable, cacheRetentionTime);
	}
	// endregion Constructors

	// region getters
	public ImsTaskTypeParam get(Long taskTypeId, Long id, Boolean fillObject) {
		
		return (ImsTaskTypeParam) getFromCache(
				new String[] { String.valueOf(taskTypeId), String.valueOf(id) },
				fillObject);
	}
	
	public List<ImsTaskTypeParam> gets(Long taskTypeId, Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(new String[] { String.valueOf(taskTypeId), null }, fillObject);
		
		if (!CollectionUtils.isEmpty(basicElements)) {
			
			return basicElements.stream()
					.map(be -> (ImsTaskTypeParam) be).collect(Collectors.toList());
		}
		
		return Collections.emptyList();
	}
	
	@Override
	public List<ImsTaskTypeParam> getAll(Boolean fillObject) {
		
		List<BasicElement> basicElements = getsFromCache(fillObject);
		
		if (!CollectionUtils.isEmpty(basicElements)) {
			
			return basicElements.stream()
					.map(be -> (ImsTaskTypeParam) be).collect(Collectors.toList());
		}
		
		return Collections.emptyList();
	}
	// endregion getters

	// region puts
	@Override
	public ImsTaskTypeParam put(ImsTaskTypeParam entity) {

		return (ImsTaskTypeParam) putFromCache(entity);
	}

	@Override
	public List<ImsTaskTypeParam> put(List<ImsTaskTypeParam> entities) {
		
		List<BasicElement> basicElements = entities.stream()
												   .map(imsTaskypeParam -> (BasicElement) imsTaskypeParam)
												   .collect(Collectors.toList());
		
		
		basicElements = putsFromCache(basicElements);
		
		return basicElements.stream()
						    .map(be -> (ImsTaskTypeParam) be)
						    .collect(Collectors.toList());
	}
	// endregion puts

	// region dels
	@Override
	public ImsTaskTypeParam del(ImsTaskTypeParam entity) {
		
		return (ImsTaskTypeParam) delFromCache(entity);
		
	}

	@Override
	public List<ImsTaskTypeParam> del(List<ImsTaskTypeParam> entities) {
		
		List<BasicElement> basicElements = entities.stream()
												   .map(imsTaskypeParam -> (BasicElement) imsTaskypeParam)
												   .collect(Collectors.toList());

		basicElements = delFromCache(basicElements);
		
		return basicElements.stream()
							.map(be -> (ImsTaskTypeParam) be)
							.collect(Collectors.toList());
	}
	// endregion dels

}
