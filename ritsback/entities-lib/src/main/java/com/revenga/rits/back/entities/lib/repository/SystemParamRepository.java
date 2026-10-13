package com.revenga.rits.back.entities.lib.repository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;

import com.revenga.rits.back.data.core.model.BasicElement;
import com.revenga.rits.back.data.core.model.SystemParam;
import com.revenga.rits.back.entities.lib.repository.entity.EntityCacheable;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository;

public class SystemParamRepository extends EntityRepository implements EntityCacheable<SystemParam> {

	// region Constructors
	public SystemParamRepository() {

		super(SystemParam.class);
	}

	public SystemParamRepository(Boolean cacheable) {

		super(SystemParam.class, cacheable);
	}

	public SystemParamRepository(Boolean cacheable, Long cacheRetentionTime) {

		super(SystemParam.class, cacheable, cacheRetentionTime);
	}
	// endregion Constructors

	// region getters
	public SystemParam get(Long id, Boolean fillObject) {

		return (SystemParam) getFromCache(new String[] { String.valueOf(id) }, fillObject);
	}

	@Override
	public List<SystemParam> getAll(Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(be -> (SystemParam) be).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}
	// endregion getters

	// region puts
	@Override
	public SystemParam put(SystemParam entity) {

		return (SystemParam) putFromCache(entity);
	}

	@Override
	public List<SystemParam> put(List<SystemParam> entities) {

		List<BasicElement> basicElements = entities.stream()
				.map(be -> (BasicElement) be).collect(Collectors.toList());

		basicElements = putsFromCache(basicElements);

		return basicElements.stream().map(be -> (SystemParam) be).collect(Collectors.toList());
	}
	// endregion puts

	// region dels
	@Override
	public SystemParam del(SystemParam entity) {

		return (SystemParam) delFromCache(entity);

	}

	@Override
	public List<SystemParam> del(List<SystemParam> entities) {

		List<BasicElement> basicElements = entities.stream()
				.map(be -> (BasicElement) be).collect(Collectors.toList());

		basicElements = delFromCache(basicElements);

		return basicElements.stream().map(be -> (SystemParam) be).collect(Collectors.toList());
	}
	// endregion dels

}
