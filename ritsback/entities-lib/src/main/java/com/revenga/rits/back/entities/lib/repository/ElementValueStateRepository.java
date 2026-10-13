package com.revenga.rits.back.entities.lib.repository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;

import com.revenga.rits.back.data.core.model.BasicElement;
import com.revenga.rits.back.data.core.model.ElementValueState;
import com.revenga.rits.back.entities.lib.repository.entity.EntityCacheable;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository;

public class ElementValueStateRepository extends EntityRepository implements EntityCacheable<ElementValueState> {

	// region Constructors
	public ElementValueStateRepository() {

		super(ElementValueState.class);
	}

	public ElementValueStateRepository(Boolean cacheable) {

		super(ElementValueState.class, cacheable);
	}

	public ElementValueStateRepository(Boolean cacheable, Long cacheRetentionTime) {

		super(ElementValueState.class, cacheable, cacheRetentionTime);
	}
	// endregion Constructors

	// region getters
	public ElementValueState get(Long id, Boolean fillObject) {

		return (ElementValueState) getFromCache(new String[] { String.valueOf(id) }, fillObject);
	}

	@Override
	public List<ElementValueState> getAll(Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(be -> (ElementValueState) be).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}
	// endregion getters

	// region puts
	@Override
	public ElementValueState put(ElementValueState entity) {

		return (ElementValueState) putFromCache(entity);
	}

	@Override
	public List<ElementValueState> put(List<ElementValueState> entities) {

		List<BasicElement> basicElements = entities.stream().map(element -> (BasicElement) element)
				.collect(Collectors.toList());

		basicElements = putsFromCache(basicElements);

		return basicElements.stream().map(be -> (ElementValueState) be).collect(Collectors.toList());
	}
	// endregion puts

	// region dels
	@Override
	public ElementValueState del(ElementValueState entity) {

		return (ElementValueState) delFromCache(entity);

	}

	@Override
	public List<ElementValueState> del(List<ElementValueState> entities) {

		List<BasicElement> basicElements = entities.stream().map(element -> (BasicElement) element)
				.collect(Collectors.toList());

		basicElements = delFromCache(basicElements);

		return basicElements.stream().map(be -> (ElementValueState) be).collect(Collectors.toList());
	}
	// endregion dels

}
