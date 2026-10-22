package com.revenga.rits.back.entities.lib.repository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;

import com.revenga.rits.back.data.core.model.BasicElement;
import com.revenga.rits.back.data.core.model.ImsIncidentAlarmConditionAlarmType;
import com.revenga.rits.back.entities.lib.repository.entity.EntityCacheable;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository;

public class ImsIncidentAlarmConditionAlarmTypeRepository extends EntityRepository implements EntityCacheable<ImsIncidentAlarmConditionAlarmType>  {

	// region Constructors
	public ImsIncidentAlarmConditionAlarmTypeRepository() {

		super(ImsIncidentAlarmConditionAlarmType.class);
	}

	public ImsIncidentAlarmConditionAlarmTypeRepository(Boolean cacheable) {

		super(ImsIncidentAlarmConditionAlarmType.class, cacheable);
	}

	public ImsIncidentAlarmConditionAlarmTypeRepository(Boolean cacheable, Long cacheRetentionTime) {

		super(ImsIncidentAlarmConditionAlarmType.class, cacheable, cacheRetentionTime);
	}

	// endregion Constructors

	// region getters
	public ImsIncidentAlarmConditionAlarmType get(Long id, Boolean fillObject) {

		return (ImsIncidentAlarmConditionAlarmType) getFromCache(new String[] { String.valueOf(id) }, fillObject);
	}

	@Override
	public List<ImsIncidentAlarmConditionAlarmType> getAll(Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(be -> (ImsIncidentAlarmConditionAlarmType) be).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}
	// endregion getters

	// region puts
	@Override
	public ImsIncidentAlarmConditionAlarmType put(ImsIncidentAlarmConditionAlarmType entity) {

		return (ImsIncidentAlarmConditionAlarmType) putFromCache(entity);
	}

	@Override
	public List<ImsIncidentAlarmConditionAlarmType> put(List<ImsIncidentAlarmConditionAlarmType> entities) {

		List<BasicElement> basicElements = entities.stream().map(alarmConfig -> (BasicElement) alarmConfig)
				.collect(Collectors.toList());

		basicElements = putsFromCache(basicElements);

		return basicElements.stream().map(be -> (ImsIncidentAlarmConditionAlarmType) be).collect(Collectors.toList());
	}
	// endregion puts

	// region dels
	@Override
	public ImsIncidentAlarmConditionAlarmType del(ImsIncidentAlarmConditionAlarmType entity) {

		return (ImsIncidentAlarmConditionAlarmType) delFromCache(entity);

	}

	@Override
	public List<ImsIncidentAlarmConditionAlarmType> del(List<ImsIncidentAlarmConditionAlarmType> entities) {

		List<BasicElement> basicElements = entities.stream().map(alarmConfig -> (BasicElement) alarmConfig)
				.collect(Collectors.toList());

		basicElements = delFromCache(basicElements);

		return basicElements.stream().map(be -> (ImsIncidentAlarmConditionAlarmType) be).collect(Collectors.toList());
	}
	// endregion dels

}
