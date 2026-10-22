package com.revenga.rits.back.entities.lib.repository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;

import com.revenga.rits.back.data.core.model.BasicElement;
import com.revenga.rits.back.data.core.model.PlanOrderStateType;
import com.revenga.rits.back.entities.lib.repository.entity.EntityCacheable;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository;

public class PlanOrderStateTypeRepository extends EntityRepository implements EntityCacheable<PlanOrderStateType> {

	// region Constructors
	public PlanOrderStateTypeRepository() {

		super(PlanOrderStateType.class);
	}

	public PlanOrderStateTypeRepository(Boolean cacheable) {

		super(PlanOrderStateType.class, cacheable);
	}

	public PlanOrderStateTypeRepository(Boolean cacheable, Long cacheRetentionTime) {

		super(PlanOrderStateType.class, cacheable, cacheRetentionTime);
	}
	// endregion Constructors

	// region getters
	public PlanOrderStateType get(Long id, Boolean fillObject) {

		return (PlanOrderStateType) getFromCache(new String[] { String.valueOf(id) }, fillObject);
	}

	@Override
	public List<PlanOrderStateType> getAll(Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(be -> (PlanOrderStateType) be).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}
	// endregion getters

	// region puts
	@Override
	public PlanOrderStateType put(PlanOrderStateType entity) {

		return (PlanOrderStateType) putFromCache(entity);
	}

	@Override
	public List<PlanOrderStateType> put(List<PlanOrderStateType> entities) {

		List<BasicElement> basicElements = entities.stream().map(element -> (BasicElement) element)
				.collect(Collectors.toList());

		basicElements = putsFromCache(basicElements);

		return basicElements.stream().map(be -> (PlanOrderStateType) be).collect(Collectors.toList());
	}
	// endregion puts

	// region dels
	@Override
	public PlanOrderStateType del(PlanOrderStateType entity) {

		return (PlanOrderStateType) delFromCache(entity);

	}

	@Override
	public List<PlanOrderStateType> del(List<PlanOrderStateType> entities) {

		List<BasicElement> basicElements = entities.stream().map(element -> (BasicElement) element)
				.collect(Collectors.toList());

		basicElements = delFromCache(basicElements);

		return basicElements.stream().map(be -> (PlanOrderStateType) be).collect(Collectors.toList());
	}
	// endregion dels

}
