package com.revenga.rits.back.entities.lib.repository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;

import com.revenga.rits.back.data.core.model.BasicElement;
import com.revenga.rits.back.data.core.model.ImsIncidentLevel;
import com.revenga.rits.back.entities.lib.repository.entity.EntityCacheable;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository;

public class ImsIncidentLevelRepository extends EntityRepository implements EntityCacheable<ImsIncidentLevel> {

	// region Constructors
	public ImsIncidentLevelRepository() {

		super(ImsIncidentLevel.class);
	}

	public ImsIncidentLevelRepository(Boolean cacheable) {

		super(ImsIncidentLevel.class, cacheable);
	}

	public ImsIncidentLevelRepository(Boolean cacheable, Long cacheRetentionTime) {

		super(ImsIncidentLevel.class, cacheable, cacheRetentionTime);
	}

	// endregion Constructors

	// region getters
	public ImsIncidentLevel get(Long id, Boolean fillObject) {

		return (ImsIncidentLevel) getFromCache(new String[] { String.valueOf(id) }, fillObject);
	}

	@Override
	public List<ImsIncidentLevel> getAll(Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(be -> (ImsIncidentLevel) be).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}
	// endregion getters

	// region puts
	@Override
	public ImsIncidentLevel put(ImsIncidentLevel entity) {

		return (ImsIncidentLevel) putFromCache(entity);
	}

	@Override
	public List<ImsIncidentLevel> put(List<ImsIncidentLevel> entities) {

		List<BasicElement> basicElements = entities.stream().map(alarmConfig -> (BasicElement) alarmConfig)
				.collect(Collectors.toList());

		basicElements = putsFromCache(basicElements);

		return basicElements.stream().map(be -> (ImsIncidentLevel) be).collect(Collectors.toList());
	}
	// endregion puts

	// region dels
	@Override
	public ImsIncidentLevel del(ImsIncidentLevel entity) {

		return (ImsIncidentLevel) delFromCache(entity);

	}

	@Override
	public List<ImsIncidentLevel> del(List<ImsIncidentLevel> entities) {

		List<BasicElement> basicElements = entities.stream().map(alarmConfig -> (BasicElement) alarmConfig)
				.collect(Collectors.toList());

		basicElements = delFromCache(basicElements);

		return basicElements.stream().map(be -> (ImsIncidentLevel) be).collect(Collectors.toList());
	}
	// endregion dels

}
