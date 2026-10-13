package com.revenga.rits.back.entities.lib.repository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;

import com.revenga.rits.back.data.core.model.BasicElement;
import com.revenga.rits.back.data.core.model.ImsExtIncidentGroup;
import com.revenga.rits.back.entities.lib.repository.entity.EntityCacheable;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository;

public class ImsExtIncidentGroupRepository extends EntityRepository implements EntityCacheable<ImsExtIncidentGroup> {

	// region Constructors
	public ImsExtIncidentGroupRepository() {

		super(ImsExtIncidentGroup.class);
	}

	public ImsExtIncidentGroupRepository(Boolean cacheable) {

		super(ImsExtIncidentGroup.class, cacheable);
	}

	public ImsExtIncidentGroupRepository(Boolean cacheable, Long cacheRetentionTime) {

		super(ImsExtIncidentGroup.class, cacheable, cacheRetentionTime);
	}
	// endregion Constructors

	// region getters
	public ImsExtIncidentGroup get(Long id, Boolean fillObject) {

		return (ImsExtIncidentGroup) getFromCache(new String[] { String.valueOf(id) }, fillObject);
	}

	@Override
	public List<ImsExtIncidentGroup> getAll(Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(be -> (ImsExtIncidentGroup) be).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}

	@Override
	public ImsExtIncidentGroup put(ImsExtIncidentGroup entity) {
		return (ImsExtIncidentGroup) putFromCache(entity);
	}

	@Override
	public List<ImsExtIncidentGroup> put(List<ImsExtIncidentGroup> entities) {
		List<BasicElement> basicElements = entities.stream().map(be -> (BasicElement) be).collect(Collectors.toList());

		basicElements = putsFromCache(basicElements);

		return basicElements.stream().map(be -> (ImsExtIncidentGroup) be).collect(Collectors.toList());
	}

	@Override
	public ImsExtIncidentGroup del(ImsExtIncidentGroup entity) {

		return (ImsExtIncidentGroup) delFromCache(entity);
	}

	@Override
	public List<ImsExtIncidentGroup> del(List<ImsExtIncidentGroup> entities) {
		List<BasicElement> basicElements = entities.stream().map(be -> (BasicElement) be).collect(Collectors.toList());

		basicElements = delFromCache(basicElements);

		return basicElements.stream().map(be -> (ImsExtIncidentGroup) be).collect(Collectors.toList());
	}

}
