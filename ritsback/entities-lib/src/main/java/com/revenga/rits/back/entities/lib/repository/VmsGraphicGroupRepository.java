package com.revenga.rits.back.entities.lib.repository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;

import com.revenga.rits.back.data.core.model.BasicElement;
import com.revenga.rits.back.data.core.model.VmsGraphicGroup;
import com.revenga.rits.back.entities.lib.repository.entity.EntityCacheable;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository;

public class VmsGraphicGroupRepository extends EntityRepository implements EntityCacheable<VmsGraphicGroup> {

	// region Constructors
	public VmsGraphicGroupRepository() {

		super(VmsGraphicGroup.class);
	}

	public VmsGraphicGroupRepository(Boolean cacheable) {

		super(VmsGraphicGroup.class, cacheable);
	}

	public VmsGraphicGroupRepository(Boolean cacheable, Long cacheRetentionTime) {

		super(VmsGraphicGroup.class, cacheable, cacheRetentionTime);
	}
	// endregion Constructors
	
	// region getters
	public VmsGraphicGroup get(Long graphicGroupId, Boolean fillObject) {
		
		return (VmsGraphicGroup) getFromCache(new String[] { String.valueOf(graphicGroupId) },
				fillObject);
	}

	@Override
	public List<VmsGraphicGroup> getAll(Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(be -> (VmsGraphicGroup) be).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}

	@Override
	public VmsGraphicGroup put(VmsGraphicGroup entity) {
		return (VmsGraphicGroup) putFromCache(entity);
	}

	@Override
	public List<VmsGraphicGroup> put(List<VmsGraphicGroup> entities) {
		List<BasicElement> basicElements = entities.stream().map(be -> (BasicElement) be).collect(Collectors.toList());

		basicElements = putsFromCache(basicElements);

		return basicElements.stream().map(be -> (VmsGraphicGroup) be).collect(Collectors.toList());
	}

	@Override
	public VmsGraphicGroup del(VmsGraphicGroup entity) {

		return (VmsGraphicGroup) delFromCache(entity);
	}

	@Override
	public List<VmsGraphicGroup> del(List<VmsGraphicGroup> entities) {
		List<BasicElement> basicElements = entities.stream().map(be -> (BasicElement) be).collect(Collectors.toList());

		basicElements = delFromCache(basicElements);

		return basicElements.stream().map(be -> (VmsGraphicGroup) be).collect(Collectors.toList());
	}

}
