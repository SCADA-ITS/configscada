package com.revenga.rits.back.entities.lib.repository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;

import com.revenga.rits.back.data.core.model.BasicElement;
import com.revenga.rits.back.data.core.model.ThresholdOperation;
import com.revenga.rits.back.entities.lib.repository.entity.EntityCacheable;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository;

public class ThresholdOperationRepository extends EntityRepository implements EntityCacheable<ThresholdOperation> {

	// region Constructors
	public ThresholdOperationRepository() {

		super(ThresholdOperation.class);
	}

	public ThresholdOperationRepository(Boolean cacheable) {

		super(ThresholdOperation.class, cacheable);
	}

	public ThresholdOperationRepository(Boolean cacheable, Long cacheRetentionTime) {

		super(ThresholdOperation.class, cacheable, cacheRetentionTime);
	}
	// endregion Constructors

	// region getters
	public ThresholdOperation get(Long id, Boolean fillObject) {

		return (ThresholdOperation) getFromCache(new String[] { String.valueOf(id) }, fillObject);
	}

	@Override
	public List<ThresholdOperation> getAll(Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(be -> (ThresholdOperation) be).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}

	@Override
	public ThresholdOperation put(ThresholdOperation entity) {
		return (ThresholdOperation) putFromCache(entity);
	}

	@Override
	public List<ThresholdOperation> put(List<ThresholdOperation> entities) {
		List<BasicElement> basicElements = entities.stream().map(be -> (BasicElement) be).collect(Collectors.toList());

		basicElements = putsFromCache(basicElements);

		return basicElements.stream().map(be -> (ThresholdOperation) be).collect(Collectors.toList());
	}

	@Override
	public ThresholdOperation del(ThresholdOperation entity) {

		return (ThresholdOperation) delFromCache(entity);
	}

	@Override
	public List<ThresholdOperation> del(List<ThresholdOperation> entities) {
		List<BasicElement> basicElements = entities.stream().map(be -> (BasicElement) be).collect(Collectors.toList());

		basicElements = delFromCache(basicElements);

		return basicElements.stream().map(be -> (ThresholdOperation) be).collect(Collectors.toList());
	}

}
