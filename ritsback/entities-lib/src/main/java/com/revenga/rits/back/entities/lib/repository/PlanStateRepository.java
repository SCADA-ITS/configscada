package com.revenga.rits.back.entities.lib.repository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;

import com.revenga.rits.back.data.core.model.BasicElement;
import com.revenga.rits.back.data.core.model.PlanState;
import com.revenga.rits.back.entities.lib.repository.entity.EntityCacheable;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository;

public class PlanStateRepository extends EntityRepository implements EntityCacheable<PlanState> {

	// region Constructors
	public PlanStateRepository() {

		super(PlanState.class);
	}

	public PlanStateRepository(Boolean cacheable) {

		super(PlanState.class, cacheable);
	}

	public PlanStateRepository(Boolean cacheable, Long cacheRetentionTime) {

		super(PlanState.class, cacheable, cacheRetentionTime);
	}
	// endregion Constructors

	// region getters
	public PlanState get(Long planId, Boolean fillObject) {

		return (PlanState) getFromCache(new String[] { String.valueOf(planId) }, fillObject);
	}

	@Override
	public List<PlanState> getAll(Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(PlanState.class::cast).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}
	// endregion getters

	// region puts
	@Override
	public PlanState put(PlanState entity) {

		return (PlanState) putFromCache(entity);
	}

	@Override
	public List<PlanState> put(List<PlanState> entities) {

		List<BasicElement> basicElements = entities.stream().map(BasicElement.class::cast)
				.collect(Collectors.toList());

		basicElements = putsFromCache(basicElements);

		return basicElements.stream().map(PlanState.class::cast).collect(Collectors.toList());
	}
	// endregion puts

	// region dels
	@Override
	public PlanState del(PlanState entity) {

		return (PlanState) delFromCache(entity);

	}

	@Override
	public List<PlanState> del(List<PlanState> entities) {

		List<BasicElement> basicElements = entities.stream().map(BasicElement.class::cast)
				.collect(Collectors.toList());

		basicElements = delFromCache(basicElements);

		return basicElements.stream().map(PlanState.class::cast).collect(Collectors.toList());
	}
	// endregion dels

}
