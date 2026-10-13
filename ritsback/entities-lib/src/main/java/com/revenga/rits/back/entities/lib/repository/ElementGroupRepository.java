package com.revenga.rits.back.entities.lib.repository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;

import com.revenga.rits.back.data.core.model.BasicElement;
import com.revenga.rits.back.data.core.model.ElementGroup;
import com.revenga.rits.back.entities.lib.repository.entity.EntityCacheable;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository;

public class ElementGroupRepository extends EntityRepository implements EntityCacheable<ElementGroup> {

	// region Constructors
	public ElementGroupRepository() {

		super(ElementGroup.class);
	}

	public ElementGroupRepository(Boolean cacheable) {

		super(ElementGroup.class, cacheable);
	}

	public ElementGroupRepository(Boolean cacheable, Long cacheRetentionTime) {

		super(ElementGroup.class, cacheable, cacheRetentionTime);
	}
	// endregion Constructors

	// region getters
	public ElementGroup get(Long id, Boolean fillObject) {

		return (ElementGroup) getFromCache(new String[] { String.valueOf(id) }, fillObject);
	}

	@Override
	public List<ElementGroup> getAll(Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(be -> (ElementGroup) be).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}

	@Override
	public ElementGroup put(ElementGroup entity) {
		return (ElementGroup) putFromCache(entity);
	}

	@Override
	public List<ElementGroup> put(List<ElementGroup> entities) {
		List<BasicElement> basicElements = entities.stream().map(be -> (BasicElement) be).collect(Collectors.toList());

		basicElements = putsFromCache(basicElements);

		return basicElements.stream().map(be -> (ElementGroup) be).collect(Collectors.toList());
	}

	@Override
	public ElementGroup del(ElementGroup entity) {

		return (ElementGroup) delFromCache(entity);
	}

	@Override
	public List<ElementGroup> del(List<ElementGroup> entities) {
		List<BasicElement> basicElements = entities.stream().map(be -> (BasicElement) be).collect(Collectors.toList());

		basicElements = delFromCache(basicElements);

		return basicElements.stream().map(be -> (ElementGroup) be).collect(Collectors.toList());
	}

}
