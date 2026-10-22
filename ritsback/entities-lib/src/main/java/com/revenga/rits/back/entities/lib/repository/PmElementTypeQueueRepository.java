package com.revenga.rits.back.entities.lib.repository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;

import com.revenga.rits.back.data.core.model.BasicElement;
import com.revenga.rits.back.data.core.model.PmElementTypeQueue;
import com.revenga.rits.back.entities.lib.repository.entity.EntityCacheable;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository;

public class PmElementTypeQueueRepository extends EntityRepository implements EntityCacheable<PmElementTypeQueue> {

	// region Constructors
	public PmElementTypeQueueRepository() {

		super(PmElementTypeQueue.class);
	}

	public PmElementTypeQueueRepository(Boolean cacheable) {

		super(PmElementTypeQueue.class, cacheable);
	}

	public PmElementTypeQueueRepository(Boolean cacheable, Long cacheRetentionTime) {

		super(PmElementTypeQueue.class, cacheable, cacheRetentionTime);
	}

	public PmElementTypeQueueRepository(Boolean cacheable, List<EntityRepository> repositories) {

		super(PmElementTypeQueue.class, cacheable, repositories);
	}

	public PmElementTypeQueueRepository(Boolean cacheable, Long cacheRetentionTime,
			List<EntityRepository> repositories) {

		super(PmElementTypeQueue.class, cacheable, cacheRetentionTime, repositories);
	}

	// endregion Constructors

	// region getters
	public PmElementTypeQueue get(Long elementTypeId, Long queueId, Boolean fillObject) {

		return (PmElementTypeQueue) getFromCache(
				new String[] { String.valueOf(elementTypeId), String.valueOf(queueId) }, fillObject);
	}

	public List<PmElementTypeQueue> getsByQueue(Long queueId, Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(new String[] { null, String.valueOf(queueId) }, fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(PmElementTypeQueue.class::cast).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}

	public List<PmElementTypeQueue> getsByElementType(Long elementTypeId, Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(new String[] { String.valueOf(elementTypeId), null },
				fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(PmElementTypeQueue.class::cast).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}

	@Override
	public List<PmElementTypeQueue> getAll(Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(PmElementTypeQueue.class::cast).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}
	// endregion getters

	// region puts
	@Override
	public PmElementTypeQueue put(PmElementTypeQueue entity) {

		return (PmElementTypeQueue) putFromCache(entity);
	}

	@Override
	public List<PmElementTypeQueue> put(List<PmElementTypeQueue> entities) {

		List<BasicElement> basicElements = entities.stream().map(BasicElement.class::cast).collect(Collectors.toList());

		basicElements = putsFromCache(basicElements);

		return basicElements.stream().map(PmElementTypeQueue.class::cast).collect(Collectors.toList());
	}
	// endregion puts

	// region dels
	@Override
	public PmElementTypeQueue del(PmElementTypeQueue entity) {

		return (PmElementTypeQueue) delFromCache(entity);

	}

	@Override
	public List<PmElementTypeQueue> del(List<PmElementTypeQueue> entities) {

		List<BasicElement> basicElements = entities.stream().map(BasicElement.class::cast).collect(Collectors.toList());

		basicElements = delFromCache(basicElements);

		return basicElements.stream().map(PmElementTypeQueue.class::cast).collect(Collectors.toList());
	}
}
