package com.revenga.rits.back.entities.lib.repository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;

import com.revenga.rits.back.data.core.model.BasicElement;
import com.revenga.rits.back.data.core.model.LosArray;
import com.revenga.rits.back.entities.lib.repository.entity.EntityCacheable;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository;

public class LosArrayRepository extends EntityRepository implements EntityCacheable<LosArray> {

	// region Constructors
	public LosArrayRepository() {

		super(LosArray.class);
	}

	public LosArrayRepository(Boolean cacheable) {

		super(LosArray.class, cacheable);
	}

	public LosArrayRepository(Boolean cacheable, Long cacheRetentionTime) {

		super(LosArray.class, cacheable, cacheRetentionTime);
	}
	// endregion Constructors

	// region getters
	public LosArray get(Integer losThresholdSpeed, Integer losThresholdOccupancy, Boolean fillObject) {

		return (LosArray) getFromCache(
				new String[] { String.valueOf(losThresholdSpeed), String.valueOf(losThresholdOccupancy) }, fillObject);
	}

	@Override
	public List<LosArray> getAll(Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(be -> (LosArray) be).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}
	// endregion getters

	// region puts
	@Override
	public LosArray put(LosArray entity) {

		return (LosArray) putFromCache(entity);
	}

	@Override
	public List<LosArray> put(List<LosArray> entities) {

		List<BasicElement> basicElements = entities.stream().map(element -> (BasicElement) element)
				.collect(Collectors.toList());

		basicElements = putsFromCache(basicElements);

		return basicElements.stream().map(be -> (LosArray) be).collect(Collectors.toList());
	}
	// endregion puts

	// region dels
	@Override
	public LosArray del(LosArray entity) {

		return (LosArray) delFromCache(entity);

	}

	@Override
	public List<LosArray> del(List<LosArray> entities) {

		List<BasicElement> basicElements = entities.stream().map(element -> (BasicElement) element)
				.collect(Collectors.toList());

		basicElements = delFromCache(basicElements);

		return basicElements.stream().map(be -> (LosArray) be).collect(Collectors.toList());
	}
	// endregion dels

}
