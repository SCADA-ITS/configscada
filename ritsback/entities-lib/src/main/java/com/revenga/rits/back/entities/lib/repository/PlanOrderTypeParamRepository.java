package com.revenga.rits.back.entities.lib.repository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;

import com.revenga.rits.back.data.core.model.BasicElement;
import com.revenga.rits.back.data.core.model.PlanOrderTypeParam;
import com.revenga.rits.back.entities.lib.repository.entity.EntityCacheable;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository;

public class PlanOrderTypeParamRepository extends EntityRepository implements EntityCacheable<PlanOrderTypeParam> {

	// region Constructors
	public PlanOrderTypeParamRepository() {
		
		super(PlanOrderTypeParam.class);
	}
	
	public PlanOrderTypeParamRepository(Boolean cacheable) {
		
		super(PlanOrderTypeParam.class, cacheable);
	}
	
	public PlanOrderTypeParamRepository(Boolean cacheable, Long cacheRetentionTime) {
		
		super(PlanOrderTypeParam.class, cacheable, cacheRetentionTime);
	}
	// endregion Constructors

	// region getters
	public PlanOrderTypeParam get(Long planOrderTypeId, Long id, Boolean fillObject) {
		
		return (PlanOrderTypeParam) getFromCache(
				new String[] { String.valueOf(planOrderTypeId), String.valueOf(id) },
				fillObject);
	}
	
	public List<PlanOrderTypeParam> getsByPlanOrderType(Long planOrderTypeId, Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(new String[] { String.valueOf(planOrderTypeId), null }, fillObject);
		
		if (!CollectionUtils.isEmpty(basicElements)) {
			
			return basicElements.stream()
					.map(be -> (PlanOrderTypeParam) be).collect(Collectors.toList());
		}
		
		return Collections.emptyList();
	}
	
	@Override
	public List<PlanOrderTypeParam> getAll(Boolean fillObject) {
		
		List<BasicElement> basicElements = getsFromCache(fillObject);
		
		if (!CollectionUtils.isEmpty(basicElements)) {
			
			return basicElements.stream()
					.map(be -> (PlanOrderTypeParam) be).collect(Collectors.toList());
		}
		
		return Collections.emptyList();
	}
	// endregion getters

	// region puts
	@Override
	public PlanOrderTypeParam put(PlanOrderTypeParam entity) {

		return (PlanOrderTypeParam) putFromCache(entity);
	}

	@Override
	public List<PlanOrderTypeParam> put(List<PlanOrderTypeParam> entities) {
		
		List<BasicElement> basicElements = entities.stream()
												   .map(elementTypeParam -> (BasicElement) elementTypeParam)
												   .collect(Collectors.toList());
		
		
		basicElements = putsFromCache(basicElements);
		
		return basicElements.stream()
						    .map(be -> (PlanOrderTypeParam) be)
						    .collect(Collectors.toList());
	}
	// endregion puts

	// region dels
	@Override
	public PlanOrderTypeParam del(PlanOrderTypeParam entity) {
		
		return (PlanOrderTypeParam) delFromCache(entity);
		
	}

	@Override
	public List<PlanOrderTypeParam> del(List<PlanOrderTypeParam> entities) {
		
		List<BasicElement> basicElements = entities.stream()
												   .map(elementTypeParam -> (BasicElement) elementTypeParam)
												   .collect(Collectors.toList());

		basicElements = delFromCache(basicElements);
		
		return basicElements.stream()
							.map(be -> (PlanOrderTypeParam) be)
							.collect(Collectors.toList());
	}
	// endregion dels

}
