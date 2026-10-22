package com.revenga.rits.back.entities.lib.repository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;

import com.revenga.rits.back.data.core.model.BasicElement;
import com.revenga.rits.back.data.core.model.PmQueuePriority;
import com.revenga.rits.back.entities.lib.repository.entity.EntityCacheable;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository;

public class PmQueuePriorityRepository extends EntityRepository implements EntityCacheable<PmQueuePriority> {

	// region Constructors
	public PmQueuePriorityRepository() {

		super(PmQueuePriority.class);
	}

	public PmQueuePriorityRepository(Boolean cacheable) {

		super(PmQueuePriority.class, cacheable);
	}

	public PmQueuePriorityRepository(Boolean cacheable, Long cacheRetentionTime) {

		super(PmQueuePriority.class, cacheable, cacheRetentionTime);
	}

	public PmQueuePriorityRepository(Boolean cacheable, List<EntityRepository> repositories) {

		super(PmQueuePriority.class, cacheable, repositories);
	}

	public PmQueuePriorityRepository(Boolean cacheable, Long cacheRetentionTime, List<EntityRepository> repositories) {

		super(PmQueuePriority.class, cacheable, cacheRetentionTime, repositories);
	}

	// endregion Constructors

	// region getters
	public PmQueuePriority get(Long queueId, Long id, Boolean fillObject) {

		return (PmQueuePriority) getFromCache(new String[] { String.valueOf(queueId), String.valueOf(id) }, fillObject);
	}

	public List<PmQueuePriority> getsByQueue(Long queueId, Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(new String[] { String.valueOf(queueId), null }, fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(PmQueuePriority.class::cast).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}

	public List<PmQueuePriority> getsPlanPriorityByQueue(Long queueId, Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(new String[] { String.valueOf(queueId), null }, fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(PmQueuePriority.class::cast)
					.filter(be -> Boolean.TRUE.equals(be.getPlanPriority())).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}

	public List<PmQueuePriority> getsIncidentPriorityByQueue(Long queueId, Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(new String[] { String.valueOf(queueId), null }, fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(PmQueuePriority.class::cast)
					.filter(be -> Boolean.TRUE.equals(be.getIncidentPriority())).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}

	@Override
	public List<PmQueuePriority> getAll(Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(PmQueuePriority.class::cast).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}
	// endregion getters

	// region puts
	@Override
	public PmQueuePriority put(PmQueuePriority entity) {

		return (PmQueuePriority) putFromCache(entity);
	}

	@Override
	public List<PmQueuePriority> put(List<PmQueuePriority> entities) {

		List<BasicElement> basicElements = entities.stream().map(BasicElement.class::cast).collect(Collectors.toList());

		basicElements = putsFromCache(basicElements);

		return basicElements.stream().map(PmQueuePriority.class::cast).collect(Collectors.toList());
	}
	// endregion puts

	// region dels
	@Override
	public PmQueuePriority del(PmQueuePriority entity) {

		return (PmQueuePriority) delFromCache(entity);

	}

	@Override
	public List<PmQueuePriority> del(List<PmQueuePriority> entities) {

		List<BasicElement> basicElements = entities.stream().map(BasicElement.class::cast).collect(Collectors.toList());

		basicElements = delFromCache(basicElements);

		return basicElements.stream().map(PmQueuePriority.class::cast).collect(Collectors.toList());
	}
}
