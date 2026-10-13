package com.revenga.rits.back.entities.lib.repository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;

import com.revenga.rits.back.data.core.model.BasicElement;
import com.revenga.rits.back.data.core.model.ImsIncidentState;
import com.revenga.rits.back.entities.lib.repository.entity.EntityCacheable;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository;

public class ImsIncidentStateRepository extends EntityRepository implements EntityCacheable<ImsIncidentState> {

	// region Constructors
	public ImsIncidentStateRepository() {

		super(ImsIncidentState.class);
	}

	public ImsIncidentStateRepository(Boolean cacheable) {

		super(ImsIncidentState.class, cacheable);
	}

	public ImsIncidentStateRepository(Boolean cacheable, Long cacheRetentionTime) {

		super(ImsIncidentState.class, cacheable, cacheRetentionTime);
	}

	// endregion Constructors

	// region getters
	public ImsIncidentState get(Long id, Boolean fillObject) {

		return (ImsIncidentState) getFromCache(new String[] { String.valueOf(id) }, fillObject);
	}

	@Override
	public List<ImsIncidentState> getAll(Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(be -> (ImsIncidentState) be).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}
	// endregion getters

	// region puts
	@Override
	public ImsIncidentState put(ImsIncidentState entity) {

		return (ImsIncidentState) putFromCache(entity);
	}

	@Override
	public List<ImsIncidentState> put(List<ImsIncidentState> entities) {

		List<BasicElement> basicElements = entities.stream().map(alarmConfig -> (BasicElement) alarmConfig)
				.collect(Collectors.toList());

		basicElements = putsFromCache(basicElements);

		return basicElements.stream().map(be -> (ImsIncidentState) be).collect(Collectors.toList());
	}
	// endregion puts

	// region dels
	@Override
	public ImsIncidentState del(ImsIncidentState entity) {

		return (ImsIncidentState) delFromCache(entity);

	}

	@Override
	public List<ImsIncidentState> del(List<ImsIncidentState> entities) {

		List<BasicElement> basicElements = entities.stream().map(alarmConfig -> (BasicElement) alarmConfig)
				.collect(Collectors.toList());

		basicElements = delFromCache(basicElements);

		return basicElements.stream().map(be -> (ImsIncidentState) be).collect(Collectors.toList());
	}
	// endregion dels

}
