package com.revenga.rits.back.entities.lib.repository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;

import com.revenga.rits.back.data.core.model.BasicElement;
import com.revenga.rits.back.data.core.model.ImsTaskType;
import com.revenga.rits.back.entities.lib.repository.entity.EntityCacheable;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository;

public class ImsTaskTypeRepository extends EntityRepository implements EntityCacheable<ImsTaskType> {

	// region Constructors
	public ImsTaskTypeRepository() {

		super(ImsTaskType.class);
	}

	public ImsTaskTypeRepository(Boolean cacheable) {

		super(ImsTaskType.class, cacheable);
	}

	public ImsTaskTypeRepository(Boolean cacheable, Long cacheRetentionTime) {

		super(ImsTaskType.class, cacheable, cacheRetentionTime);
	}

	// endregion Constructors

	// region getters
	public ImsTaskType get(Long id, Boolean fillObject) {

		return (ImsTaskType) getFromCache(new String[] { String.valueOf(id) }, fillObject);
	}

	@Override
	public List<ImsTaskType> getAll(Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(be -> (ImsTaskType) be).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}
	// endregion getters

	// region puts
	@Override
	public ImsTaskType put(ImsTaskType entity) {

		return (ImsTaskType) putFromCache(entity);
	}

	@Override
	public List<ImsTaskType> put(List<ImsTaskType> entities) {

		List<BasicElement> basicElements = entities.stream().map(alarmConfig -> (BasicElement) alarmConfig)
				.collect(Collectors.toList());

		basicElements = putsFromCache(basicElements);

		return basicElements.stream().map(be -> (ImsTaskType) be).collect(Collectors.toList());
	}
	// endregion puts

	// region dels
	@Override
	public ImsTaskType del(ImsTaskType entity) {

		return (ImsTaskType) delFromCache(entity);

	}

	@Override
	public List<ImsTaskType> del(List<ImsTaskType> entities) {

		List<BasicElement> basicElements = entities.stream().map(alarmConfig -> (BasicElement) alarmConfig)
				.collect(Collectors.toList());

		basicElements = delFromCache(basicElements);

		return basicElements.stream().map(be -> (ImsTaskType) be).collect(Collectors.toList());
	}
	// endregion dels

}
