package com.revenga.rits.back.entities.lib.repository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;

import com.revenga.rits.back.data.core.model.BasicElement;
import com.revenga.rits.back.data.core.model.ImsIncidentEnumParam;
import com.revenga.rits.back.entities.lib.repository.entity.EntityCacheable;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository;

public class ImsIncidentEnumParamRepository extends EntityRepository implements EntityCacheable<ImsIncidentEnumParam> {

	// region Constructors
	public ImsIncidentEnumParamRepository() {

		super(ImsIncidentEnumParam.class);
	}

	public ImsIncidentEnumParamRepository(Boolean cacheable) {

		super(ImsIncidentEnumParam.class, cacheable);
	}

	public ImsIncidentEnumParamRepository(Boolean cacheable, Long cacheRetentionTime) {

		super(ImsIncidentEnumParam.class, cacheable, cacheRetentionTime);
	}

	// endregion Constructors

	// region getters
	public ImsIncidentEnumParam get(Long id, Long incidentParamId, Boolean fillObject) {

		return (ImsIncidentEnumParam) getFromCache(new String[] { String.valueOf(id), String.valueOf(incidentParamId) }, fillObject);
	}
	
	public List<ImsIncidentEnumParam> gets(Long incidentParamId, Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(
				new String[] { String.valueOf(incidentParamId), null, null }, fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(ImsIncidentEnumParam.class::cast).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}

	@Override
	public List<ImsIncidentEnumParam> getAll(Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(be -> (ImsIncidentEnumParam) be).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}
	// endregion getters

	// region puts
	@Override
	public ImsIncidentEnumParam put(ImsIncidentEnumParam entity) {

		return (ImsIncidentEnumParam) putFromCache(entity);
	}

	@Override
	public List<ImsIncidentEnumParam> put(List<ImsIncidentEnumParam> entities) {

		List<BasicElement> basicElements = entities.stream().map(BasicElement.class::cast)
				.collect(Collectors.toList());

		basicElements = putsFromCache(basicElements);

		return basicElements.stream().map(ImsIncidentEnumParam.class::cast).collect(Collectors.toList());
	}
	// endregion puts

	// region dels
	@Override
	public ImsIncidentEnumParam del(ImsIncidentEnumParam entity) {

		return (ImsIncidentEnumParam) delFromCache(entity);

	}

	@Override
	public List<ImsIncidentEnumParam> del(List<ImsIncidentEnumParam> entities) {

		List<BasicElement> basicElements = entities.stream().map(BasicElement.class::cast)
				.collect(Collectors.toList());

		basicElements = delFromCache(basicElements);

		return basicElements.stream().map(ImsIncidentEnumParam.class::cast).collect(Collectors.toList());
	}
	// endregion dels

}