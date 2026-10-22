package com.revenga.rits.back.entities.lib.repository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;

import com.revenga.rits.back.data.core.model.BasicElement;
import com.revenga.rits.back.data.core.model.ParamType;
import com.revenga.rits.back.entities.lib.repository.entity.EntityCacheable;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository;

public class ParamTypeRepository extends EntityRepository implements EntityCacheable<ParamType> {

	// region Constructors
	public ParamTypeRepository() {

		super(ParamType.class);
	}

	public ParamTypeRepository(Boolean cacheable) {

		super(ParamType.class, cacheable);
	}

	public ParamTypeRepository(Boolean cacheable, Long cacheRetentionTime) {

		super(ParamType.class, cacheable, cacheRetentionTime);
	}
	// endregion Constructors

	// region getters
	public ParamType get(Long id, Boolean fillObject) {

		return (ParamType) getFromCache(new String[] { String.valueOf(id) }, fillObject);
	}

	@Override
	public List<ParamType> getAll(Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(be -> (ParamType) be).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}

	@Override
	public ParamType put(ParamType entity) {
		return (ParamType) putFromCache(entity);
	}

	@Override
	public List<ParamType> put(List<ParamType> entities) {
		List<BasicElement> basicElements = entities.stream().map(be -> (BasicElement) be).collect(Collectors.toList());

		basicElements = putsFromCache(basicElements);

		return basicElements.stream().map(be -> (ParamType) be).collect(Collectors.toList());
	}

	@Override
	public ParamType del(ParamType entity) {

		return (ParamType) delFromCache(entity);
	}

	@Override
	public List<ParamType> del(List<ParamType> entities) {
		List<BasicElement> basicElements = entities.stream().map(be -> (BasicElement) be).collect(Collectors.toList());

		basicElements = delFromCache(basicElements);

		return basicElements.stream().map(be -> (ParamType) be).collect(Collectors.toList());
	}

}
