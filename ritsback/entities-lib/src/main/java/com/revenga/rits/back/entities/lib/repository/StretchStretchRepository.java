package com.revenga.rits.back.entities.lib.repository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;

import com.revenga.rits.back.data.core.model.BasicElement;
import com.revenga.rits.back.data.core.model.StretchStretch;
import com.revenga.rits.back.entities.lib.repository.entity.EntityCacheable;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository;

public class StretchStretchRepository extends EntityRepository implements EntityCacheable<StretchStretch> {

	// region Constructors
	public StretchStretchRepository() {

		super(StretchStretch.class);
	}
	
	public StretchStretchRepository(Boolean cacheable, Long cacheRetentionTime) {

		super(StretchStretch.class, cacheable, cacheRetentionTime);
	}

	// endregion Constructors

	// region getters
	public StretchStretch get(Long id, Boolean fillObject) {

		return (StretchStretch) getFromCache(new String[] { String.valueOf(id) }, fillObject);
	}

	@Override
	public List<StretchStretch> getAll(Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(be -> (StretchStretch) be).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}

	@Override
	public StretchStretch put(StretchStretch entity) {
		return (StretchStretch) putFromCache(entity);
	}

	@Override
	public List<StretchStretch> put(List<StretchStretch> entities) {
		List<BasicElement> basicElements = entities.stream().map(be -> (BasicElement) be).collect(Collectors.toList());

		basicElements = putsFromCache(basicElements);

		return basicElements.stream().map(be -> (StretchStretch) be).collect(Collectors.toList());
	}

	@Override
	public StretchStretch del(StretchStretch entity) {

		return (StretchStretch) delFromCache(entity);
	}

	@Override
	public List<StretchStretch> del(List<StretchStretch> entities) {
		List<BasicElement> basicElements = entities.stream().map(be -> (BasicElement) be).collect(Collectors.toList());

		basicElements = delFromCache(basicElements);

		return basicElements.stream().map(be -> (StretchStretch) be).collect(Collectors.toList());
	}

}
