package com.revenga.rits.back.entities.lib.repository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;

import com.revenga.rits.back.data.core.model.BasicElement;
import com.revenga.rits.back.data.core.model.ImsIncidentParamGroup;
import com.revenga.rits.back.entities.lib.repository.entity.EntityCacheable;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository;

public class ImsIncidentParamGroupRepository extends EntityRepository implements EntityCacheable<ImsIncidentParamGroup> {

	// region Constructors
	public ImsIncidentParamGroupRepository() {

		super(ImsIncidentParamGroup.class);
	}

	public ImsIncidentParamGroupRepository(Boolean cacheable) {

		super(ImsIncidentParamGroup.class, cacheable);
	}

	public ImsIncidentParamGroupRepository(Boolean cacheable, Long cacheRetentionTime) {

		super(ImsIncidentParamGroup.class, cacheable, cacheRetentionTime);
	}

	// endregion Constructors

	// region getters
	public ImsIncidentParamGroup get(Long id, Boolean fillObject) {

		return (ImsIncidentParamGroup) getFromCache(new String[] { String.valueOf(id) }, fillObject);
	}

	@Override
	public List<ImsIncidentParamGroup> getAll(Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(be -> (ImsIncidentParamGroup) be).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}
	// endregion getters

	// region puts
	@Override
	public ImsIncidentParamGroup put(ImsIncidentParamGroup entity) {

		return (ImsIncidentParamGroup) putFromCache(entity);
	}

	@Override
	public List<ImsIncidentParamGroup> put(List<ImsIncidentParamGroup> entities) {

		List<BasicElement> basicElements = entities.stream().map(BasicElement.class::cast)
				.collect(Collectors.toList());

		basicElements = putsFromCache(basicElements);

		return basicElements.stream().map(ImsIncidentParamGroup.class::cast).collect(Collectors.toList());
	}
	// endregion puts

	// region dels
	@Override
	public ImsIncidentParamGroup del(ImsIncidentParamGroup entity) {

		return (ImsIncidentParamGroup) delFromCache(entity);

	}

	@Override
	public List<ImsIncidentParamGroup> del(List<ImsIncidentParamGroup> entities) {

		List<BasicElement> basicElements = entities.stream().map(BasicElement.class::cast)
				.collect(Collectors.toList());

		basicElements = delFromCache(basicElements);

		return basicElements.stream().map(ImsIncidentParamGroup.class::cast).collect(Collectors.toList());
	}
	// endregion dels

}