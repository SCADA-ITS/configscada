package com.revenga.rits.back.entities.lib.repository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;

import com.revenga.rits.back.data.core.model.BasicElement;
import com.revenga.rits.back.data.core.model.RoadType;
import com.revenga.rits.back.entities.lib.repository.entity.EntityCacheable;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository;

public class RoadTypeRepository extends EntityRepository
		implements EntityCacheable<RoadType> {

	// region Constructors
	public RoadTypeRepository() {

		super(RoadType.class);
	}

	public RoadTypeRepository(Boolean cacheable) {

		super(RoadType.class, cacheable);
	}

	public RoadTypeRepository(Boolean cacheable, Long cacheRetentionTime) {

		super(RoadType.class, cacheable, cacheRetentionTime);
	}
	// endregion Constructors

	// region getters
	public RoadType get(Long id, Boolean fillObject) {

		return (RoadType) getFromCache(new String[] { String.valueOf(id) }, fillObject);
	}

	@Override
	public List<RoadType> getAll(Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(be -> (RoadType) be).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}

	@Override
	public RoadType put(RoadType entity) {
		return (RoadType) putFromCache(entity);
	}

	@Override
	public List<RoadType> put(List<RoadType> entities) {
		List<BasicElement> basicElements = entities.stream().map(be -> (BasicElement) be).collect(Collectors.toList());

		basicElements = putsFromCache(basicElements);

		return basicElements.stream().map(be -> (RoadType) be).collect(Collectors.toList());
	}

	@Override
	public RoadType del(RoadType entity) {

		return (RoadType) delFromCache(entity);
	}

	@Override
	public List<RoadType> del(List<RoadType> entities) {
		List<BasicElement> basicElements = entities.stream().map(be -> (BasicElement) be).collect(Collectors.toList());

		basicElements = delFromCache(basicElements);

		return basicElements.stream().map(be -> (RoadType) be).collect(Collectors.toList());
	}

}
