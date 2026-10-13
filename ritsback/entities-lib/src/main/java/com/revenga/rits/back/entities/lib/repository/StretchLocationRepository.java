package com.revenga.rits.back.entities.lib.repository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;

import com.revenga.rits.back.data.core.model.BasicElement;
import com.revenga.rits.back.data.core.model.StretchLocation;
import com.revenga.rits.back.entities.lib.repository.entity.EntityCacheable;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository;

public class StretchLocationRepository extends EntityRepository implements EntityCacheable<StretchLocation> {

	// region Constructors
	public StretchLocationRepository() {

		super(StretchLocation.class);
	}

	public StretchLocationRepository(Boolean cacheable) {

		super(StretchLocation.class, cacheable);
	}

	public StretchLocationRepository(Boolean cacheable, Long cacheRetentionTime) {

		super(StretchLocation.class, cacheable, cacheRetentionTime);
	}
	// endregion Constructors

	// region getters
	public StretchLocation get(Long stretchId, Long locationId, Boolean fillObject) {

		return (StretchLocation) getFromCache(new String[] { String.valueOf(stretchId), String.valueOf(locationId) },
				fillObject);
	}

	@Override
	public List<StretchLocation> getAll(Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(be -> (StretchLocation) be).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}
	
	public List<StretchLocation> getByStretch(Long stretchId, Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(new String[] { String.valueOf(stretchId), null },
				fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(be -> (StretchLocation) be).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}
	
	public List<StretchLocation> getByLocation(Long locationId, Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(new String[] { null, String.valueOf(locationId) },
				fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(be -> (StretchLocation) be).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}

	@Override
	public StretchLocation put(StretchLocation entity) {
		return (StretchLocation) putFromCache(entity);
	}

	@Override
	public List<StretchLocation> put(List<StretchLocation> entities) {
		List<BasicElement> basicElements = entities.stream().map(be -> (BasicElement) be).collect(Collectors.toList());

		basicElements = putsFromCache(basicElements);

		return basicElements.stream().map(be -> (StretchLocation) be).collect(Collectors.toList());
	}

	@Override
	public StretchLocation del(StretchLocation entity) {

		return (StretchLocation) delFromCache(entity);
	}

	@Override
	public List<StretchLocation> del(List<StretchLocation> entities) {
		List<BasicElement> basicElements = entities.stream().map(be -> (BasicElement) be).collect(Collectors.toList());

		basicElements = delFromCache(basicElements);

		return basicElements.stream().map(be -> (StretchLocation) be).collect(Collectors.toList());
	}

}
