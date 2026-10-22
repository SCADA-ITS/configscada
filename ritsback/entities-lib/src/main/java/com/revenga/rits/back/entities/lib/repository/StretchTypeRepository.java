package com.revenga.rits.back.entities.lib.repository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;

import com.revenga.rits.back.data.core.model.BasicElement;
import com.revenga.rits.back.data.core.model.StretchType;
import com.revenga.rits.back.entities.lib.repository.entity.EntityCacheable;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository;

public class StretchTypeRepository extends EntityRepository implements EntityCacheable<StretchType> {

	// region Constructors
	public StretchTypeRepository() {

		super(StretchType.class);
	}

	public StretchTypeRepository(Boolean cacheable) {

		super(StretchType.class, cacheable);
	}

	public StretchTypeRepository(Boolean cacheable, Long cacheRetentionTime) {

		super(StretchType.class, cacheable, cacheRetentionTime);
	}
	// endregion Constructors

	// region getters
	public StretchType get(Long id, Boolean fillObject) {

		return (StretchType) getFromCache(new String[] { String.valueOf(id) }, fillObject);
	}

	@Override
	public List<StretchType> getAll(Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(be -> (StretchType) be).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}

	@Override
	public StretchType put(StretchType entity) {
		return (StretchType) putFromCache(entity);
	}

	@Override
	public List<StretchType> put(List<StretchType> entities) {
		List<BasicElement> basicElements = entities.stream().map(be -> (BasicElement) be).collect(Collectors.toList());

		basicElements = putsFromCache(basicElements);

		return basicElements.stream().map(be -> (StretchType) be).collect(Collectors.toList());
	}

	@Override
	public StretchType del(StretchType entity) {

		return (StretchType) delFromCache(entity);
	}

	@Override
	public List<StretchType> del(List<StretchType> entities) {
		List<BasicElement> basicElements = entities.stream().map(be -> (BasicElement) be).collect(Collectors.toList());

		basicElements = delFromCache(basicElements);

		return basicElements.stream().map(be -> (StretchType) be).collect(Collectors.toList());
	}

}
