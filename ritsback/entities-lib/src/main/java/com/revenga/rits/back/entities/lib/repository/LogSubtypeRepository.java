package com.revenga.rits.back.entities.lib.repository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;

import com.revenga.rits.back.data.core.model.BasicElement;
import com.revenga.rits.back.data.core.model.LogSubtype;
import com.revenga.rits.back.entities.lib.repository.entity.EntityCacheable;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository;

public class LogSubtypeRepository extends EntityRepository implements EntityCacheable<LogSubtype> {

	// region Constructors
	public LogSubtypeRepository() {

		super(LogSubtype.class);
	}

	public LogSubtypeRepository(Boolean cacheable) {

		super(LogSubtype.class, cacheable);
	}

	public LogSubtypeRepository(Boolean cacheable, Long cacheRetentionTime) {

		super(LogSubtype.class, cacheable, cacheRetentionTime);
	}
	// endregion Constructors

	// region getters
	public LogSubtype get(Long id, Boolean fillObject) {

		return (LogSubtype) getFromCache(new String[] { String.valueOf(id) }, fillObject);
	}

	@Override
	public List<LogSubtype> getAll(Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(be -> (LogSubtype) be).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}

	public List<LogSubtype> getByLogType(Long LongTypeId, Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(fillObject);

		if (LongTypeId != null && !CollectionUtils.isEmpty(basicElements)) {

			List<LogSubtype> logSubtypes = getAll(fillObject);

			return logSubtypes.stream().filter(e -> LongTypeId.equals(e.getLogTypeId())).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}

	@Override
	public LogSubtype put(LogSubtype entity) {
		return (LogSubtype) putFromCache(entity);
	}

	@Override
	public List<LogSubtype> put(List<LogSubtype> entities) {
		List<BasicElement> basicElements = entities.stream().map(be -> (BasicElement) be).collect(Collectors.toList());

		basicElements = putsFromCache(basicElements);

		return basicElements.stream().map(be -> (LogSubtype) be).collect(Collectors.toList());
	}

	@Override
	public LogSubtype del(LogSubtype entity) {

		return (LogSubtype) delFromCache(entity);
	}

	@Override
	public List<LogSubtype> del(List<LogSubtype> entities) {
		List<BasicElement> basicElements = entities.stream().map(be -> (BasicElement) be).collect(Collectors.toList());

		basicElements = delFromCache(basicElements);

		return basicElements.stream().map(be -> (LogSubtype) be).collect(Collectors.toList());
	}

}
