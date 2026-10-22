package com.revenga.rits.back.entities.lib.repository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;

import com.revenga.rits.back.data.core.model.BasicElement;
import com.revenga.rits.back.data.core.model.VmsGraphicType;
import com.revenga.rits.back.entities.lib.repository.entity.EntityCacheable;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository;

public class VmsGraphicTypeRepository extends EntityRepository implements EntityCacheable<VmsGraphicType> {

	// region Constructors
	public VmsGraphicTypeRepository() {

		super(VmsGraphicType.class);
	}

	public VmsGraphicTypeRepository(Boolean cacheable) {

		super(VmsGraphicType.class, cacheable);
	}

	public VmsGraphicTypeRepository(Boolean cacheable, Long cacheRetentionTime) {

		super(VmsGraphicType.class, cacheable, cacheRetentionTime);
	}
	// endregion Constructors
	
	// region getters
	public VmsGraphicType get(Long graphicTypeId, Boolean fillObject) {
		
		return (VmsGraphicType) getFromCache(new String[] { String.valueOf(graphicTypeId) },
				fillObject);
	}

	@Override
	public List<VmsGraphicType> getAll(Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(be -> (VmsGraphicType) be).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}

	@Override
	public VmsGraphicType put(VmsGraphicType entity) {
		return (VmsGraphicType) putFromCache(entity);
	}

	@Override
	public List<VmsGraphicType> put(List<VmsGraphicType> entities) {
		List<BasicElement> basicElements = entities.stream().map(be -> (BasicElement) be).collect(Collectors.toList());

		basicElements = putsFromCache(basicElements);

		return basicElements.stream().map(be -> (VmsGraphicType) be).collect(Collectors.toList());
	}

	@Override
	public VmsGraphicType del(VmsGraphicType entity) {

		return (VmsGraphicType) delFromCache(entity);
	}

	@Override
	public List<VmsGraphicType> del(List<VmsGraphicType> entities) {
		List<BasicElement> basicElements = entities.stream().map(be -> (BasicElement) be).collect(Collectors.toList());

		basicElements = delFromCache(basicElements);

		return basicElements.stream().map(be -> (VmsGraphicType) be).collect(Collectors.toList());
	}

}
