package com.revenga.rits.back.entities.lib.repository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;

import com.revenga.rits.back.data.core.model.BasicElement;
import com.revenga.rits.back.data.core.model.Location;
import com.revenga.rits.back.entities.lib.repository.entity.EntityCacheable;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository;

public class LocationRepository extends EntityRepository implements EntityCacheable<Location> {

	// region Constructors
	public LocationRepository() {

		super(Location.class);
	}

	public LocationRepository(Boolean cacheable) {

		super(Location.class, cacheable);
	}

	public LocationRepository(Boolean cacheable, Long cacheRetentionTime) {

		super(Location.class, cacheable, cacheRetentionTime);
	}
	// endregion Constructors

	// region getters
	public Location get(Long id, Boolean fillObject) {

		return (Location) getFromCache(new String[] { String.valueOf(id) }, fillObject);
	}

	@Override
	public List<Location> getAll(Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(be -> (Location) be).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}
	
	@Override
	public Location put(Location entity) {
		return (Location) putFromCache(entity);
	}

	@Override
	public List<Location> put(List<Location> entities) {
		List<BasicElement> basicElements = entities.stream().map(be -> (BasicElement) be).collect(Collectors.toList());

		basicElements = putsFromCache(basicElements);

		return basicElements.stream().map(be -> (Location) be).collect(Collectors.toList());
	}

	@Override
	public Location del(Location entity) {

		return (Location) delFromCache(entity);
	}

	@Override
	public List<Location> del(List<Location> entities) {
		List<BasicElement> basicElements = entities.stream().map(be -> (BasicElement) be).collect(Collectors.toList());

		basicElements = delFromCache(basicElements);

		return basicElements.stream().map(be -> (Location) be).collect(Collectors.toList());
	}
}
