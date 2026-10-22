package com.revenga.rits.back.entities.lib.repository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;

import com.revenga.rits.back.data.core.model.BasicElement;
import com.revenga.rits.back.data.core.model.PlanScheduleStateType;
import com.revenga.rits.back.entities.lib.repository.entity.EntityCacheable;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository;

public class PlanScheduleStateTypeRepository extends EntityRepository implements EntityCacheable<PlanScheduleStateType> {
	
	// region Constructors
	public PlanScheduleStateTypeRepository() {

		super(PlanScheduleStateType.class);
	}

	public PlanScheduleStateTypeRepository(Boolean cacheable) {

		super(PlanScheduleStateType.class, cacheable);
	}

	public PlanScheduleStateTypeRepository(Boolean cacheable, Long cacheRetentionTime) {

		super(PlanScheduleStateType.class, cacheable, cacheRetentionTime);
	}
	// endregion Constructors

	// region getters
	public PlanScheduleStateType get(Long id, Boolean fillObject) {

		return (PlanScheduleStateType) getFromCache(new String[] { String.valueOf(id) }, fillObject);
	}

	@Override
	public List<PlanScheduleStateType> getAll(Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(be -> (PlanScheduleStateType) be).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}
	// endregion getters

	// region puts
	@Override
	public PlanScheduleStateType put(PlanScheduleStateType entity) {

		return (PlanScheduleStateType) putFromCache(entity);
	}

	@Override
	public List<PlanScheduleStateType> put(List<PlanScheduleStateType> entities) {

		List<BasicElement> basicElements = entities.stream().map(element -> (BasicElement) element)
				.collect(Collectors.toList());

		basicElements = putsFromCache(basicElements);

		return basicElements.stream().map(be -> (PlanScheduleStateType) be).collect(Collectors.toList());
	}
	// endregion puts

	// region dels
	@Override
	public PlanScheduleStateType del(PlanScheduleStateType entity) {

		return (PlanScheduleStateType) delFromCache(entity);

	}

	@Override
	public List<PlanScheduleStateType> del(List<PlanScheduleStateType> entities) {

		List<BasicElement> basicElements = entities.stream().map(element -> (BasicElement) element)
				.collect(Collectors.toList());

		basicElements = delFromCache(basicElements);

		return basicElements.stream().map(be -> (PlanScheduleStateType) be).collect(Collectors.toList());
	}
	// endregion dels

}
