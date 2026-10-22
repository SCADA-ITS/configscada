package com.revenga.rits.back.entities.lib.repository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;

import com.revenga.rits.back.data.core.model.BasicElement;
import com.revenga.rits.back.data.core.model.PmQueue;
import com.revenga.rits.back.entities.lib.repository.entity.EntityCacheable;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository;

public class PmQueueRepository extends EntityRepository implements EntityCacheable<PmQueue> {

	// region Constructors
	public PmQueueRepository() {

		super(PmQueue.class);
	}

	public PmQueueRepository(Boolean cacheable) {

		super(PmQueue.class, cacheable);
	}

	public PmQueueRepository(Boolean cacheable, Long cacheRetentionTime) {

		super(PmQueue.class, cacheable, cacheRetentionTime);
	}
	// endregion Constructors

	// region getters
	public PmQueue get(Long id, Boolean fillObject) {

		return (PmQueue) getFromCache(new String[] { String.valueOf(id) }, fillObject);
	}

	@Override
	public List<PmQueue> getAll(Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(PmQueue.class::cast).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}

	@Override
	public PmQueue put(PmQueue entity) {
		return (PmQueue) putFromCache(entity);
	}

	@Override
	public List<PmQueue> put(List<PmQueue> entities) {
		List<BasicElement> basicElements = entities.stream().map(BasicElement.class::cast).collect(Collectors.toList());

		basicElements = putsFromCache(basicElements);

		return basicElements.stream().map(PmQueue.class::cast).collect(Collectors.toList());
	}

	@Override
	public PmQueue del(PmQueue entity) {

		return (PmQueue) delFromCache(entity);
	}

	@Override
	public List<PmQueue> del(List<PmQueue> entities) {
		List<BasicElement> basicElements = entities.stream().map(BasicElement.class::cast).collect(Collectors.toList());

		basicElements = delFromCache(basicElements);

		return basicElements.stream().map(PmQueue.class::cast).collect(Collectors.toList());
	}
}
