package com.revenga.rits.back.entities.lib.repository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;

import com.revenga.rits.back.data.core.model.BasicElement;
import com.revenga.rits.back.data.core.model.ImsIncidentParam;
import com.revenga.rits.back.entities.lib.repository.entity.EntityCacheable;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository;

public class ImsIncidentParamRepository extends EntityRepository implements EntityCacheable<ImsIncidentParam> {

	// region Constructors
	public ImsIncidentParamRepository() {

		super(ImsIncidentParam.class);
	}

	public ImsIncidentParamRepository(Boolean cacheable) {

		super(ImsIncidentParam.class, cacheable);
	}

	public ImsIncidentParamRepository(Boolean cacheable, Long cacheRetentionTime) {

		super(ImsIncidentParam.class, cacheable, cacheRetentionTime);
	}

	// endregion Constructors

	// region getters
	public ImsIncidentParam get(Long id, Boolean fillObject) {

		return (ImsIncidentParam) getFromCache(new String[] { String.valueOf(id) }, fillObject);
	}

	@Override
	public List<ImsIncidentParam> getAll(Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(be -> (ImsIncidentParam) be).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}
	// endregion getters

	// region puts
	@Override
	public ImsIncidentParam put(ImsIncidentParam entity) {

		return (ImsIncidentParam) putFromCache(entity);
	}

	@Override
	public List<ImsIncidentParam> put(List<ImsIncidentParam> entities) {

		List<BasicElement> basicElements = entities.stream().map(BasicElement.class::cast)
				.collect(Collectors.toList());

		basicElements = putsFromCache(basicElements);

		return basicElements.stream().map(ImsIncidentParam.class::cast).collect(Collectors.toList());
	}
	// endregion puts

	// region dels
	@Override
	public ImsIncidentParam del(ImsIncidentParam entity) {

		return (ImsIncidentParam) delFromCache(entity);

	}

	@Override
	public List<ImsIncidentParam> del(List<ImsIncidentParam> entities) {

		List<BasicElement> basicElements = entities.stream().map(BasicElement.class::cast)
				.collect(Collectors.toList());

		basicElements = delFromCache(basicElements);

		return basicElements.stream().map(ImsIncidentParam.class::cast).collect(Collectors.toList());
	}
	// endregion dels

}