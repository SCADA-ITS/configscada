package com.revenga.rits.back.entities.lib.repository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;

import com.revenga.rits.back.data.core.model.BasicElement;
import com.revenga.rits.back.data.core.model.ImsIncidentGroup;
import com.revenga.rits.back.entities.lib.repository.entity.EntityCacheable;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository;

public class ImsIncidentGroupRepository extends EntityRepository implements EntityCacheable<ImsIncidentGroup> {
	
	// region Constructors
	/**
	 * 
	 */
	public ImsIncidentGroupRepository() {

		super(ImsIncidentGroup.class);
	}

	/**
	 * 
	 * @param cacheable
	 */
	public ImsIncidentGroupRepository(Boolean cacheable) {

		super(ImsIncidentGroup.class, cacheable);
	}

	/**
	 * 
	 * @param cacheable
	 * @param cacheRetentionTime
	 */
	public ImsIncidentGroupRepository(Boolean cacheable, Long cacheRetentionTime) {

		super(ImsIncidentGroup.class, cacheable, cacheRetentionTime);
	}
	// endregion Constructors

	// region getters
	/**
	 * 
	 * @param id
	 * @param fillObject
	 * @return
	 */
	public ImsIncidentGroup get(Long id, Boolean fillObject) {

		return (ImsIncidentGroup) getFromCache(new String[] { String.valueOf(id) }, fillObject);
	}

	/**
	 * 
	 */
	@Override
	public List<ImsIncidentGroup> getAll(Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(be -> (ImsIncidentGroup) be).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}

	/**
	 * 
	 */
	@Override
	public ImsIncidentGroup put(ImsIncidentGroup entity) {
		return (ImsIncidentGroup) putFromCache(entity);
	}

	/**
	 * 
	 */
	@Override
	public List<ImsIncidentGroup> put(List<ImsIncidentGroup> entities) {
		List<BasicElement> basicElements = entities.stream().map(be -> (BasicElement) be).collect(Collectors.toList());

		basicElements = putsFromCache(basicElements);

		return basicElements.stream().map(be -> (ImsIncidentGroup) be).collect(Collectors.toList());
	}

	/**
	 * 
	 */
	@Override
	public ImsIncidentGroup del(ImsIncidentGroup entity) {

		return (ImsIncidentGroup) delFromCache(entity);
	}

	/**
	 * 
	 */
	@Override
	public List<ImsIncidentGroup> del(List<ImsIncidentGroup> entities) {
		List<BasicElement> basicElements = entities.stream().map(be -> (BasicElement) be).collect(Collectors.toList());

		basicElements = delFromCache(basicElements);

		return basicElements.stream().map(be -> (ImsIncidentGroup) be).collect(Collectors.toList());
	}	

}
