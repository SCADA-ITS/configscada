package com.revenga.rits.back.entities.lib.repository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;

import com.revenga.rits.back.data.core.model.BasicElement;
import com.revenga.rits.back.data.core.model.PlanStateType;
import com.revenga.rits.back.entities.lib.repository.entity.EntityCacheable;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository;

public class PlanStateTypeRepository extends EntityRepository implements EntityCacheable<PlanStateType> {

	// region Constructors
	public PlanStateTypeRepository() {

		super(PlanStateType.class);
	}

	public PlanStateTypeRepository(Boolean cacheable) {

		super(PlanStateType.class, cacheable);
	}

	public PlanStateTypeRepository(Boolean cacheable, Long cacheRetentionTime) {

		super(PlanStateType.class, cacheable, cacheRetentionTime);
	}
	// endregion Constructors

	// region getters
	public PlanStateType get(Long id, Boolean fillObject) {

		return (PlanStateType) getFromCache(new String[] { String.valueOf(id) }, fillObject);
	}

	@Override
	public List<PlanStateType> getAll(Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(be -> (PlanStateType) be).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}
	// endregion getters

	// region puts
	@Override
	public PlanStateType put(PlanStateType entity) {

		return (PlanStateType) putFromCache(entity);
	}

	@Override
	public List<PlanStateType> put(List<PlanStateType> entities) {

		List<BasicElement> basicElements = entities.stream().map(element -> (BasicElement) element)
				.collect(Collectors.toList());

		basicElements = putsFromCache(basicElements);

		return basicElements.stream().map(be -> (PlanStateType) be).collect(Collectors.toList());
	}
	// endregion puts

	// region dels
	@Override
	public PlanStateType del(PlanStateType entity) {

		return (PlanStateType) delFromCache(entity);

	}

	@Override
	public List<PlanStateType> del(List<PlanStateType> entities) {

		List<BasicElement> basicElements = entities.stream().map(element -> (BasicElement) element)
				.collect(Collectors.toList());

		basicElements = delFromCache(basicElements);

		return basicElements.stream().map(be -> (PlanStateType) be).collect(Collectors.toList());
	}
	// endregion dels

}
