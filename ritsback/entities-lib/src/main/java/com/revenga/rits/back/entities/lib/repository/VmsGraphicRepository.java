package com.revenga.rits.back.entities.lib.repository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;

import com.revenga.rits.back.data.core.model.BasicElement;
import com.revenga.rits.back.data.core.model.VmsGraphic;
import com.revenga.rits.back.entities.lib.repository.entity.EntityCacheable;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository;

public class VmsGraphicRepository extends EntityRepository implements EntityCacheable<VmsGraphic> {

	// region Constructors
	public VmsGraphicRepository() {

		super(VmsGraphic.class);
	}

	public VmsGraphicRepository(Boolean cacheable) {

		super(VmsGraphic.class, cacheable);
	}

	public VmsGraphicRepository(Boolean cacheable, Long cacheRetentionTime) {

		super(VmsGraphic.class, cacheable, cacheRetentionTime);
	}
	// endregion Constructors
	
	// region getters
	public VmsGraphic get(Long graphicTypeId, Long graphicId, Boolean fillObject) {
		
		return (VmsGraphic) getFromCache(new String[] { String.valueOf(graphicTypeId), String.valueOf(graphicId) },
				fillObject);
	}

	@Override
	public List<VmsGraphic> getAll(Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(be -> (VmsGraphic) be).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}

	@Override
	public VmsGraphic put(VmsGraphic entity) {
		return (VmsGraphic) putFromCache(entity);
	}

	@Override
	public List<VmsGraphic> put(List<VmsGraphic> entities) {
		List<BasicElement> basicElements = entities.stream().map(be -> (BasicElement) be).collect(Collectors.toList());

		basicElements = putsFromCache(basicElements);

		return basicElements.stream().map(be -> (VmsGraphic) be).collect(Collectors.toList());
	}

	@Override
	public VmsGraphic del(VmsGraphic entity) {

		return (VmsGraphic) delFromCache(entity);
	}

	@Override
	public List<VmsGraphic> del(List<VmsGraphic> entities) {
		List<BasicElement> basicElements = entities.stream().map(be -> (BasicElement) be).collect(Collectors.toList());

		basicElements = delFromCache(basicElements);

		return basicElements.stream().map(be -> (VmsGraphic) be).collect(Collectors.toList());
	}

}
