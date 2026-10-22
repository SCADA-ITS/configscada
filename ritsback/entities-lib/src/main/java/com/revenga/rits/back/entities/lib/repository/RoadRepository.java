package com.revenga.rits.back.entities.lib.repository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;

import com.revenga.rits.back.data.core.model.BasicElement;
import com.revenga.rits.back.data.core.model.Road;
import com.revenga.rits.back.entities.lib.repository.entity.EntityCacheable;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository;

public class RoadRepository extends EntityRepository implements EntityCacheable<Road> {

	// region Constructors
	public RoadRepository() {

		super(Road.class);
	}

	public RoadRepository(Boolean cacheable) {

		super(Road.class, cacheable);
	}

	public RoadRepository(Boolean cacheable, Long cacheRetentionTime) {

		super(Road.class, cacheable, cacheRetentionTime);
	}
	// endregion Constructors

	// region getters
	public Road get(Long id, Boolean fillObject) {

		return (Road) getFromCache(new String[] { String.valueOf(id) }, fillObject);
	}

	@Override
	public List<Road> getAll(Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(be -> (Road) be).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}

	@Override
	public Road put(Road entity) {
		return (Road) putFromCache(entity);
	}

	@Override
	public List<Road> put(List<Road> entities) {
		List<BasicElement> basicElements = entities.stream().map(be -> (BasicElement) be).collect(Collectors.toList());

		basicElements = putsFromCache(basicElements);

		return basicElements.stream().map(be -> (Road) be).collect(Collectors.toList());
	}

	@Override
	public Road del(Road entity) {

		return (Road) delFromCache(entity);
	}

	@Override
	public List<Road> del(List<Road> entities) {
		List<BasicElement> basicElements = entities.stream().map(be -> (BasicElement) be).collect(Collectors.toList());

		basicElements = delFromCache(basicElements);

		return basicElements.stream().map(be -> (Road) be).collect(Collectors.toList());
	}

}
