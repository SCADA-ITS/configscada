package com.revenga.rits.back.entities.lib.repository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;

import com.revenga.rits.back.data.core.model.BasicElement;
import com.revenga.rits.back.data.core.model.ImsExtIncidentTypeParam;
import com.revenga.rits.back.entities.lib.repository.entity.EntityCacheable;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository;

public class ImsExtIncidentTypeParamRepository extends EntityRepository
		implements EntityCacheable<ImsExtIncidentTypeParam> {

	// region Constructors
	public ImsExtIncidentTypeParamRepository() {

		super(ImsExtIncidentTypeParam.class);
	}

	public ImsExtIncidentTypeParamRepository(Boolean cacheable) {

		super(ImsExtIncidentTypeParam.class, cacheable);
	}

	public ImsExtIncidentTypeParamRepository(Boolean cacheable, Long cacheRetentionTime) {

		super(ImsExtIncidentTypeParam.class, cacheable, cacheRetentionTime);
	}
	// endregion Constructors

	// region getters
	public ImsExtIncidentTypeParam get(Long extIncidentTypeId, Long id, Boolean fillObject) {

		return (ImsExtIncidentTypeParam) getFromCache(
				new String[] { String.valueOf(extIncidentTypeId), String.valueOf(id) }, fillObject);
	}
	
	public List<ImsExtIncidentTypeParam> getsByExtIncidentType(Long extIncidentTypeId, Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(new String[] { String.valueOf(extIncidentTypeId), null }, fillObject);
		
		if (!CollectionUtils.isEmpty(basicElements)) {
			
			return basicElements.stream()
					.map(be -> (ImsExtIncidentTypeParam) be).collect(Collectors.toList());
		}
		
		return Collections.emptyList();
	}

	@Override
	public List<ImsExtIncidentTypeParam> getAll(Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(be -> (ImsExtIncidentTypeParam) be).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}

	@Override
	public ImsExtIncidentTypeParam put(ImsExtIncidentTypeParam entity) {
		return (ImsExtIncidentTypeParam) putFromCache(entity);
	}

	@Override
	public List<ImsExtIncidentTypeParam> put(List<ImsExtIncidentTypeParam> entities) {
		List<BasicElement> basicElements = entities.stream().map(be -> (BasicElement) be).collect(Collectors.toList());

		basicElements = putsFromCache(basicElements);

		return basicElements.stream().map(be -> (ImsExtIncidentTypeParam) be).collect(Collectors.toList());
	}

	@Override
	public ImsExtIncidentTypeParam del(ImsExtIncidentTypeParam entity) {

		return (ImsExtIncidentTypeParam) delFromCache(entity);
	}

	@Override
	public List<ImsExtIncidentTypeParam> del(List<ImsExtIncidentTypeParam> entities) {
		List<BasicElement> basicElements = entities.stream().map(be -> (BasicElement) be).collect(Collectors.toList());

		basicElements = delFromCache(basicElements);

		return basicElements.stream().map(be -> (ImsExtIncidentTypeParam) be).collect(Collectors.toList());
	}

}
