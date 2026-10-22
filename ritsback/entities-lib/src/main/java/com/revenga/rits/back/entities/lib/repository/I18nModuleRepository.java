package com.revenga.rits.back.entities.lib.repository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;

import com.revenga.rits.back.data.core.model.BasicElement;
import com.revenga.rits.back.data.core.model.I18nModule;
import com.revenga.rits.back.entities.lib.repository.entity.EntityCacheable;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository;

public class I18nModuleRepository extends EntityRepository implements EntityCacheable<I18nModule> {

	// region Constructors
	public I18nModuleRepository() {

		super(I18nModule.class);
	}

	public I18nModuleRepository(Boolean cacheable) {

		super(I18nModule.class, cacheable);
	}

	public I18nModuleRepository(Boolean cacheable, Long cacheRetentionTime) {

		super(I18nModule.class, cacheable, cacheRetentionTime);
	}
	// endregion Constructors
	
	// region getters
	public I18nModule get(Long id, Boolean fillObject) {
		
		return (I18nModule) getFromCache(new String[] { String.valueOf(id) }, fillObject);
	}

	@Override
	public List<I18nModule> getAll(Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(be -> (I18nModule) be).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}

	@Override
	public I18nModule put(I18nModule entity) {
		return (I18nModule) putFromCache(entity);
	}

	@Override
	public List<I18nModule> put(List<I18nModule> entities) {
		List<BasicElement> basicElements = entities.stream().map(be -> (BasicElement) be).collect(Collectors.toList());

		basicElements = putsFromCache(basicElements);

		return basicElements.stream().map(be -> (I18nModule) be).collect(Collectors.toList());
	}

	@Override
	public I18nModule del(I18nModule entity) {

		return (I18nModule) delFromCache(entity);
	}

	@Override
	public List<I18nModule> del(List<I18nModule> entities) {
		List<BasicElement> basicElements = entities.stream().map(be -> (BasicElement) be).collect(Collectors.toList());

		basicElements = delFromCache(basicElements);

		return basicElements.stream().map(be -> (I18nModule) be).collect(Collectors.toList());
	}

}
