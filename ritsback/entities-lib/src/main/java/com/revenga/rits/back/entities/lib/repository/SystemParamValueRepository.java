package com.revenga.rits.back.entities.lib.repository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;

import com.revenga.rits.back.data.core.model.BasicElement;
import com.revenga.rits.back.data.core.model.SystemParamValue;
import com.revenga.rits.back.entities.lib.repository.entity.EntityCacheable;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository;

public class SystemParamValueRepository extends EntityRepository implements EntityCacheable<SystemParamValue> {

	// region Constructors
	public SystemParamValueRepository() {

		super(SystemParamValue.class);
	}

	public SystemParamValueRepository(Boolean cacheable) {

		super(SystemParamValue.class, cacheable);
	}

	public SystemParamValueRepository(Boolean cacheable, Long cacheRetentionTime) {

		super(SystemParamValue.class, cacheable, cacheRetentionTime);
	}
	// endregion Constructors

	// region getters
	public SystemParamValue get(Long id, Boolean fillObject) {

		return (SystemParamValue) getFromCache(new String[] { String.valueOf(id) }, fillObject);
	}

	@Override
	public List<SystemParamValue> getAll(Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(be -> (SystemParamValue) be).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}
	// endregion getters

	// region puts
	@Override
	public SystemParamValue put(SystemParamValue entity) {

		return (SystemParamValue) putFromCache(entity);
	}

	@Override
	public List<SystemParamValue> put(List<SystemParamValue> entities) {

		List<BasicElement> basicElements = entities.stream()
				.map(be -> (BasicElement) be).collect(Collectors.toList());

		basicElements = putsFromCache(basicElements);

		return basicElements.stream().map(be -> (SystemParamValue) be).collect(Collectors.toList());
	}
	// endregion puts

	// region dels
	@Override
	public SystemParamValue del(SystemParamValue entity) {

		return (SystemParamValue) delFromCache(entity);

	}

	@Override
	public List<SystemParamValue> del(List<SystemParamValue> entities) {

		List<BasicElement> basicElements = entities.stream()
				.map(be -> (BasicElement) be).collect(Collectors.toList());

		basicElements = delFromCache(basicElements);

		return basicElements.stream().map(be -> (SystemParamValue) be).collect(Collectors.toList());
	}
	// endregion dels

}
