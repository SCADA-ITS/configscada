package com.revenga.rits.back.entities.lib.repository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;

import com.revenga.rits.back.data.core.model.BasicElement;
import com.revenga.rits.back.data.core.model.LocationType;
import com.revenga.rits.back.entities.lib.repository.entity.EntityCacheable;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository;

public class LocationTypeRepository extends EntityRepository implements EntityCacheable<LocationType> {

	// region Constructors
	public LocationTypeRepository() {

		super(LocationType.class);
	}

	public LocationTypeRepository(Boolean cacheable) {

		super(LocationType.class, cacheable);
	}

	public LocationTypeRepository(Boolean cacheable, Long cacheRetentionTime) {

		super(LocationType.class, cacheable, cacheRetentionTime);
	}
	// endregion Constructors

	// region getters
	public LocationType get(Long id, Boolean fillObject) {

		return (LocationType) getFromCache(new String[] { String.valueOf(id) }, fillObject);
	}

	@Override
	public List<LocationType> getAll(Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(be -> (LocationType) be).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}

	@Override
	public LocationType put(LocationType entity) {
		return (LocationType) putFromCache(entity);
	}

	@Override
	public List<LocationType> put(List<LocationType> entities) {
		List<BasicElement> basicElements = entities.stream().map(be -> (BasicElement) be).collect(Collectors.toList());

		basicElements = putsFromCache(basicElements);

		return basicElements.stream().map(be -> (LocationType) be).collect(Collectors.toList());
	}

	@Override
	public LocationType del(LocationType entity) {

		return (LocationType) delFromCache(entity);
	}

	@Override
	public List<LocationType> del(List<LocationType> entities) {
		List<BasicElement> basicElements = entities.stream().map(be -> (BasicElement) be).collect(Collectors.toList());

		basicElements = delFromCache(basicElements);

		return basicElements.stream().map(be -> (LocationType) be).collect(Collectors.toList());
	}

}
