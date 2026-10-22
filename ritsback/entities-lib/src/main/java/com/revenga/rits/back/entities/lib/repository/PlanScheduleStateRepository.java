package com.revenga.rits.back.entities.lib.repository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;

import com.revenga.rits.back.data.core.model.BasicElement;
import com.revenga.rits.back.data.core.model.PlanScheduleState;
import com.revenga.rits.back.entities.lib.repository.entity.EntityCacheable;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository;

public class PlanScheduleStateRepository extends EntityRepository implements EntityCacheable<PlanScheduleState> {
	
	// region Constructors
		public PlanScheduleStateRepository() {

			super(PlanScheduleState.class);
		}

		public PlanScheduleStateRepository(Boolean cacheable) {

			super(PlanScheduleState.class, cacheable);
		}

		public PlanScheduleStateRepository(Boolean cacheable, Long cacheRetentionTime) {

			super(PlanScheduleState.class, cacheable, cacheRetentionTime);
		}
		// endregion Constructors

	// region getters
	public PlanScheduleState get(Long planScheduleId, Boolean fillObject) {

		return (PlanScheduleState) getFromCache(new String[] { String.valueOf(planScheduleId) }, fillObject);
	}

	@Override
	public List<PlanScheduleState> getAll(Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(be -> (PlanScheduleState) be).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}
	// endregion getters

	// region puts
	@Override
	public PlanScheduleState put(PlanScheduleState entity) {

		return (PlanScheduleState) putFromCache(entity);
	}

	@Override
	public List<PlanScheduleState> put(List<PlanScheduleState> entities) {

		List<BasicElement> basicElements = entities.stream().map(element -> (BasicElement) element)
				.collect(Collectors.toList());

		basicElements = putsFromCache(basicElements);

		return basicElements.stream().map(be -> (PlanScheduleState) be).collect(Collectors.toList());
	}
	// endregion puts

	// region dels
		@Override
		public PlanScheduleState del(PlanScheduleState entity) {

			return (PlanScheduleState) delFromCache(entity);

		}

		@Override
		public List<PlanScheduleState> del(List<PlanScheduleState> entities) {

			List<BasicElement> basicElements = entities.stream().map(element -> (BasicElement) element)
					.collect(Collectors.toList());

			basicElements = delFromCache(basicElements);

			return basicElements.stream().map(be -> (PlanScheduleState) be).collect(Collectors.toList());
		}
		// endregion dels

}
