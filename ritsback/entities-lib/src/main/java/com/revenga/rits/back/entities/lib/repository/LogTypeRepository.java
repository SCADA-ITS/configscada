package com.revenga.rits.back.entities.lib.repository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;

import com.revenga.rits.back.data.core.model.BasicElement;
import com.revenga.rits.back.data.core.model.LogType;
import com.revenga.rits.back.entities.lib.repository.entity.EntityCacheable;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository;

public class LogTypeRepository extends EntityRepository implements EntityCacheable<LogType> {

	// region Constructors
	public LogTypeRepository() {

		super(LogType.class);
	}

	public LogTypeRepository(Boolean cacheable) {

		super(LogType.class, cacheable);
	}

	public LogTypeRepository(Boolean cacheable, Long cacheRetentionTime) {

		super(LogType.class, cacheable, cacheRetentionTime);
	}
	// endregion Constructors

	// region getters
	public LogType get(Long id, Boolean fillObject) {

		return (LogType) getFromCache(new String[] { String.valueOf(id) }, fillObject);
	}

	@Override
	public List<LogType> getAll(Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(be -> (LogType) be).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}

	@Override
	public LogType put(LogType entity) {
		return (LogType) putFromCache(entity);
	}

	@Override
	public List<LogType> put(List<LogType> entities) {
		List<BasicElement> basicElements = entities.stream().map(be -> (BasicElement) be).collect(Collectors.toList());

		basicElements = putsFromCache(basicElements);

		return basicElements.stream().map(be -> (LogType) be).collect(Collectors.toList());
	}

	@Override
	public LogType del(LogType entity) {

		return (LogType) delFromCache(entity);
	}

	@Override
	public List<LogType> del(List<LogType> entities) {
		List<BasicElement> basicElements = entities.stream().map(be -> (BasicElement) be).collect(Collectors.toList());

		basicElements = delFromCache(basicElements);

		return basicElements.stream().map(be -> (LogType) be).collect(Collectors.toList());
	}

}
