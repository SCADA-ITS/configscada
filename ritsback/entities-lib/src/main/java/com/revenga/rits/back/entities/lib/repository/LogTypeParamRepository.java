package com.revenga.rits.back.entities.lib.repository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;

import com.revenga.rits.back.data.core.model.BasicElement;
import com.revenga.rits.back.data.core.model.LogTypeParam;
import com.revenga.rits.back.entities.lib.repository.entity.EntityCacheable;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository;

public class LogTypeParamRepository extends EntityRepository implements EntityCacheable<LogTypeParam> {

	// region Constructors
	public LogTypeParamRepository() {

		super(LogTypeParam.class);
	}

	public LogTypeParamRepository(Boolean cacheable) {

		super(LogTypeParam.class, cacheable);
	}

	public LogTypeParamRepository(Boolean cacheable, Long cacheRetentionTime) {

		super(LogTypeParam.class, cacheable, cacheRetentionTime);
	}
	// endregion Constructors

	// region getters
	public LogTypeParam get(Long logTypeId, Long id, Boolean fillObject) {

		return (LogTypeParam) getFromCache(new String[] { String.valueOf(logTypeId), String.valueOf(id) }, fillObject);
	}

	@Override
	public List<LogTypeParam> getAll(Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(be -> (LogTypeParam) be).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}
	
	public List<LogTypeParam> getsByLogType(Long logTypeId, Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(new String[] { String.valueOf(logTypeId), null }, fillObject);
		
		if (!CollectionUtils.isEmpty(basicElements)) {
			
			return basicElements.stream()
					.map(be -> (LogTypeParam) be).collect(Collectors.toList());
		}
		
		return Collections.emptyList();
	}

	@Override
	public LogTypeParam put(LogTypeParam entity) {
		return (LogTypeParam) putFromCache(entity);
	}

	@Override
	public List<LogTypeParam> put(List<LogTypeParam> entities) {
		List<BasicElement> basicElements = entities.stream().map(be -> (BasicElement) be).collect(Collectors.toList());

		basicElements = putsFromCache(basicElements);

		return basicElements.stream().map(be -> (LogTypeParam) be).collect(Collectors.toList());
	}

	@Override
	public LogTypeParam del(LogTypeParam entity) {

		return (LogTypeParam) delFromCache(entity);
	}

	@Override
	public List<LogTypeParam> del(List<LogTypeParam> entities) {
		List<BasicElement> basicElements = entities.stream().map(be -> (BasicElement) be).collect(Collectors.toList());

		basicElements = delFromCache(basicElements);

		return basicElements.stream().map(be -> (LogTypeParam) be).collect(Collectors.toList());
	}

}
