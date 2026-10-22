package com.revenga.rits.back.entities.lib.repository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;

import com.revenga.rits.back.data.core.model.BasicElement;
import com.revenga.rits.back.data.core.model.PlanOrderState;
import com.revenga.rits.back.entities.lib.repository.entity.EntityCacheable;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository;

public class PlanOrderStateRepository extends EntityRepository implements EntityCacheable<PlanOrderState> {

	// region Constructors
	public PlanOrderStateRepository() {

		super(PlanOrderState.class);
	}

	public PlanOrderStateRepository(Boolean cacheable) {

		super(PlanOrderState.class, cacheable);
	}

	public PlanOrderStateRepository(Boolean cacheable, Long cacheRetentionTime) {

		super(PlanOrderState.class, cacheable, cacheRetentionTime);
	}
	// endregion Constructors

	// region getters
	public PlanOrderState get(Long planId, Long planOrderId, Boolean fillObject) {

		return (PlanOrderState) getFromCache(new String[] { String.valueOf(planId), String.valueOf(planOrderId) },
				fillObject);
	}

	public List<PlanOrderState> getsByPlan(Long planId, Boolean fillObject) {
		
		List<BasicElement> basicElements = getsFromCache(new String[] { String.valueOf(planId), null }, fillObject);
		
		if (!CollectionUtils.isEmpty(basicElements)) {
			
			return basicElements.stream()
					.map(PlanOrderState.class::cast).collect(Collectors.toList());
		}
		
		return Collections.emptyList();
	}
	
	@Override
	public List<PlanOrderState> getAll(Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(PlanOrderState.class::cast).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}
	
	// endregion getters

	// region puts
	@Override
	public PlanOrderState put(PlanOrderState entity) {

		return (PlanOrderState) putFromCache(entity);
	}

	@Override
	public List<PlanOrderState> put(List<PlanOrderState> entities) {

		List<BasicElement> basicElements = entities.stream().map(BasicElement.class::cast)
				.collect(Collectors.toList());

		basicElements = putsFromCache(basicElements);

		return basicElements.stream().map(PlanOrderState.class::cast).collect(Collectors.toList());
	}
	// endregion puts

	// region dels
	@Override
	public PlanOrderState del(PlanOrderState entity) {

		return (PlanOrderState) delFromCache(entity);

	}

	@Override
	public List<PlanOrderState> del(List<PlanOrderState> entities) {

		List<BasicElement> basicElements = entities.stream().map(BasicElement.class::cast)
				.collect(Collectors.toList());

		basicElements = delFromCache(basicElements);

		return basicElements.stream().map(PlanOrderState.class::cast).collect(Collectors.toList());
	}
	// endregion dels

}
