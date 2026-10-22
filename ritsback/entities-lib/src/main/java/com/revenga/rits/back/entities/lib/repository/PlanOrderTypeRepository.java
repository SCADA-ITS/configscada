package com.revenga.rits.back.entities.lib.repository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;

import com.revenga.rits.back.data.core.model.BasicElement;
import com.revenga.rits.back.data.core.model.PlanOrderType;
import com.revenga.rits.back.entities.lib.repository.entity.EntityCacheable;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository;

public class PlanOrderTypeRepository extends EntityRepository implements EntityCacheable<PlanOrderType>{

	// region Constructors
	public PlanOrderTypeRepository() {

		super(PlanOrderType.class);
	}

	public PlanOrderTypeRepository(Boolean cacheable) {

		super(PlanOrderType.class, cacheable);
	}

	public PlanOrderTypeRepository(Boolean cacheable, Long cacheRetentionTime) {

		super(PlanOrderType.class, cacheable, cacheRetentionTime);
	}
	// endregion Constructors

	// region getters
	public PlanOrderType get(Long id, Boolean fillObject) {

		return (PlanOrderType) getFromCache(new String[] { String.valueOf(id) }, fillObject);
	}

	@Override
	public List<PlanOrderType> getAll(Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(be -> (PlanOrderType) be).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}
	// endregion getters

	// region puts
	@Override
	public PlanOrderType put(PlanOrderType entity) {

		return (PlanOrderType) putFromCache(entity);
	}

	@Override
	public List<PlanOrderType> put(List<PlanOrderType> entities) {

		List<BasicElement> basicElements = entities.stream().map(element -> (BasicElement) element)
				.collect(Collectors.toList());

		basicElements = putsFromCache(basicElements);

		return basicElements.stream().map(be -> (PlanOrderType) be).collect(Collectors.toList());
	}
	// endregion puts

	// region dels
	@Override
	public PlanOrderType del(PlanOrderType entity) {

		return (PlanOrderType) delFromCache(entity);

	}

	@Override
	public List<PlanOrderType> del(List<PlanOrderType> entities) {

		List<BasicElement> basicElements = entities.stream().map(element -> (BasicElement) element)
				.collect(Collectors.toList());

		basicElements = delFromCache(basicElements);

		return basicElements.stream().map(be -> (PlanOrderType) be).collect(Collectors.toList());
	}
	// endregion dels

}
