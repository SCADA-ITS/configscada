package com.revenga.rits.back.entities.lib.repository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;

import com.revenga.rits.back.data.core.model.BasicElement;
import com.revenga.rits.back.data.core.model.VmsGraphicCategory;
import com.revenga.rits.back.entities.lib.repository.entity.EntityCacheable;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository;

public class VmsGraphicCategoryRepository extends EntityRepository implements EntityCacheable<VmsGraphicCategory> {

	// region Constructors
	public VmsGraphicCategoryRepository() {

		super(VmsGraphicCategory.class);
	}

	public VmsGraphicCategoryRepository(Boolean cacheable) {

		super(VmsGraphicCategory.class, cacheable);
	}

	public VmsGraphicCategoryRepository(Boolean cacheable, Long cacheRetentionTime) {

		super(VmsGraphicCategory.class, cacheable, cacheRetentionTime);
	}
	// endregion Constructors
	
	// region getters
	public VmsGraphicCategory get(Long graphicCategoryId, Boolean fillObject) {
		
		return (VmsGraphicCategory) getFromCache(new String[] { String.valueOf(graphicCategoryId) },
				fillObject);
	}

	@Override
	public List<VmsGraphicCategory> getAll(Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(be -> (VmsGraphicCategory) be).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}

	@Override
	public VmsGraphicCategory put(VmsGraphicCategory entity) {
		return (VmsGraphicCategory) putFromCache(entity);
	}

	@Override
	public List<VmsGraphicCategory> put(List<VmsGraphicCategory> entities) {
		List<BasicElement> basicElements = entities.stream().map(be -> (BasicElement) be).collect(Collectors.toList());

		basicElements = putsFromCache(basicElements);

		return basicElements.stream().map(be -> (VmsGraphicCategory) be).collect(Collectors.toList());
	}

	@Override
	public VmsGraphicCategory del(VmsGraphicCategory entity) {

		return (VmsGraphicCategory) delFromCache(entity);
	}

	@Override
	public List<VmsGraphicCategory> del(List<VmsGraphicCategory> entities) {
		List<BasicElement> basicElements = entities.stream().map(be -> (BasicElement) be).collect(Collectors.toList());

		basicElements = delFromCache(basicElements);

		return basicElements.stream().map(be -> (VmsGraphicCategory) be).collect(Collectors.toList());
	}

}
