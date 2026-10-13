package com.revenga.rits.back.entities.lib.repository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;

import com.revenga.rits.back.data.core.model.AlarmLevel;
import com.revenga.rits.back.data.core.model.BasicElement;
import com.revenga.rits.back.entities.lib.repository.entity.EntityCacheable;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository;

public class AlarmLevelRepository extends EntityRepository implements EntityCacheable<AlarmLevel> {

	// region Constructors
	public AlarmLevelRepository() {

		super(AlarmLevel.class);
	}

	public AlarmLevelRepository(Boolean cacheable) {

		super(AlarmLevel.class, cacheable);
	}

	public AlarmLevelRepository(Boolean cacheable, Long cacheRetentionTime) {

		super(AlarmLevel.class, cacheable, cacheRetentionTime);
	}
	// endregion Constructors

	// region getters
	public AlarmLevel get(Long id, Boolean fillObject) {

		return (AlarmLevel) getFromCache(new String[] { String.valueOf(id) }, fillObject);
	}

	@Override
	public List<AlarmLevel> getAll(Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(be -> (AlarmLevel) be).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}

	@Override
	public AlarmLevel put(AlarmLevel entity) {
		return (AlarmLevel) putFromCache(entity);
	}

	@Override
	public List<AlarmLevel> put(List<AlarmLevel> entities) {
		List<BasicElement> basicElements = entities.stream().map(be -> (BasicElement) be).collect(Collectors.toList());

		basicElements = putsFromCache(basicElements);

		return basicElements.stream().map(be -> (AlarmLevel) be).collect(Collectors.toList());
	}

	@Override
	public AlarmLevel del(AlarmLevel entity) {

		return (AlarmLevel) delFromCache(entity);
	}

	@Override
	public List<AlarmLevel> del(List<AlarmLevel> entities) {
		List<BasicElement> basicElements = entities.stream().map(be -> (BasicElement) be).collect(Collectors.toList());

		basicElements = delFromCache(basicElements);

		return basicElements.stream().map(be -> (AlarmLevel) be).collect(Collectors.toList());
	}

}
