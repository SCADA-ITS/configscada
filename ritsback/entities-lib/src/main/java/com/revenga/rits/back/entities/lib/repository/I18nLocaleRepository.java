package com.revenga.rits.back.entities.lib.repository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;

import com.revenga.rits.back.data.core.model.BasicElement;
import com.revenga.rits.back.data.core.model.I18nLocale;
import com.revenga.rits.back.entities.lib.repository.entity.EntityCacheable;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository;

public class I18nLocaleRepository extends EntityRepository implements EntityCacheable<I18nLocale> {

	// region Constructors
	public I18nLocaleRepository() {

		super(I18nLocale.class);
	}

	public I18nLocaleRepository(Boolean cacheable) {

		super(I18nLocale.class, cacheable);
	}

	public I18nLocaleRepository(Boolean cacheable, Long cacheRetentionTime) {

		super(I18nLocale.class, cacheable, cacheRetentionTime);
	}
	// endregion Constructors
	
	// region getters
	public I18nLocale get(String code, Boolean fillObject) {
		
		return (I18nLocale) getFromCache(new String[] { code },
				fillObject);
	}

	@Override
	public List<I18nLocale> getAll(Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(be -> (I18nLocale) be).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}

	@Override
	public I18nLocale put(I18nLocale entity) {
		return (I18nLocale) putFromCache(entity);
	}

	@Override
	public List<I18nLocale> put(List<I18nLocale> entities) {
		List<BasicElement> basicElements = entities.stream().map(be -> (BasicElement) be).collect(Collectors.toList());

		basicElements = putsFromCache(basicElements);

		return basicElements.stream().map(be -> (I18nLocale) be).collect(Collectors.toList());
	}

	@Override
	public I18nLocale del(I18nLocale entity) {

		return (I18nLocale) delFromCache(entity);
	}

	@Override
	public List<I18nLocale> del(List<I18nLocale> entities) {
		List<BasicElement> basicElements = entities.stream().map(be -> (BasicElement) be).collect(Collectors.toList());

		basicElements = delFromCache(basicElements);

		return basicElements.stream().map(be -> (I18nLocale) be).collect(Collectors.toList());
	}

}
