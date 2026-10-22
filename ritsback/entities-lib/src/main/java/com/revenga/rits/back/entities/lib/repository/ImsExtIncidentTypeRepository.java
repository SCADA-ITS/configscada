package com.revenga.rits.back.entities.lib.repository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;

import com.revenga.rits.back.data.core.model.BasicElement;
import com.revenga.rits.back.data.core.model.ImsExtIncidentType;
import com.revenga.rits.back.entities.lib.repository.entity.EntityCacheable;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository;

public class ImsExtIncidentTypeRepository extends EntityRepository implements EntityCacheable<ImsExtIncidentType> {

	// region Constructors
	public ImsExtIncidentTypeRepository() {

		super(ImsExtIncidentType.class);
	}

	public ImsExtIncidentTypeRepository(Boolean cacheable) {

		super(ImsExtIncidentType.class, cacheable);
	}

	public ImsExtIncidentTypeRepository(Boolean cacheable, Long cacheRetentionTime) {

		super(ImsExtIncidentType.class, cacheable, cacheRetentionTime);
	}
	// endregion Constructors

	// region getters
	public ImsExtIncidentType get(Long id, Boolean fillObject) {

		return (ImsExtIncidentType) getFromCache(new String[] { String.valueOf(id) }, fillObject);
	}

	@Override
	public List<ImsExtIncidentType> getAll(Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(be -> (ImsExtIncidentType) be).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}

	@Override
	public ImsExtIncidentType put(ImsExtIncidentType entity) {
		return (ImsExtIncidentType) putFromCache(entity);
	}

	@Override
	public List<ImsExtIncidentType> put(List<ImsExtIncidentType> entities) {
		List<BasicElement> basicElements = entities.stream().map(be -> (BasicElement) be).collect(Collectors.toList());

		basicElements = putsFromCache(basicElements);

		return basicElements.stream().map(be -> (ImsExtIncidentType) be).collect(Collectors.toList());
	}

	@Override
	public ImsExtIncidentType del(ImsExtIncidentType entity) {

		return (ImsExtIncidentType) delFromCache(entity);
	}

	@Override
	public List<ImsExtIncidentType> del(List<ImsExtIncidentType> entities) {
		List<BasicElement> basicElements = entities.stream().map(be -> (BasicElement) be).collect(Collectors.toList());

		basicElements = delFromCache(basicElements);

		return basicElements.stream().map(be -> (ImsExtIncidentType) be).collect(Collectors.toList());
	}

}
