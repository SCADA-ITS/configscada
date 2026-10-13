package com.revenga.rits.back.entities.lib.repository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;

import com.revenga.rits.back.data.core.model.BasicElement;
import com.revenga.rits.back.data.core.model.ElementState;
import com.revenga.rits.back.entities.lib.repository.entity.EntityCacheable;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository;

public class ElementStateRepository extends EntityRepository implements EntityCacheable<ElementState> {

	// region Constructors
	public ElementStateRepository() {

		super(ElementState.class);
	}

	public ElementStateRepository(Boolean cacheable) {

		super(ElementState.class, cacheable);
	}

	public ElementStateRepository(Boolean cacheable, Long cacheRetentionTime) {

		super(ElementState.class, cacheable, cacheRetentionTime);
	}
	// endregion Constructors

	// region getters
	public ElementState get(Long elementTypeId, Long elementId, Boolean fillObject) {

		return (ElementState) getFromCache(new String[] { String.valueOf(elementTypeId), String.valueOf(elementId) },
				fillObject);
	}

	public List<ElementState> getsByElementType(Long elementTypeId, Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(new String[] { String.valueOf(elementTypeId), null },
				fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(be -> (ElementState) be).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}

	@Override
	public List<ElementState> getAll(Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(be -> (ElementState) be).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}
	// endregion getters

	// region puts
	@Override
	public ElementState put(ElementState entity) {

		return (ElementState) putFromCache(entity);
	}

	@Override
	public List<ElementState> put(List<ElementState> entities) {

		List<BasicElement> basicElements = entities.stream().map(element -> (BasicElement) element)
				.collect(Collectors.toList());

		basicElements = putsFromCache(basicElements);

		return basicElements.stream().map(be -> (ElementState) be).collect(Collectors.toList());
	}
	// endregion puts

	// region dels
	@Override
	public ElementState del(ElementState entity) {

		return (ElementState) delFromCache(entity);

	}

	@Override
	public List<ElementState> del(List<ElementState> entities) {

		List<BasicElement> basicElements = entities.stream().map(element -> (BasicElement) element)
				.collect(Collectors.toList());

		basicElements = delFromCache(basicElements);

		return basicElements.stream().map(be -> (ElementState) be).collect(Collectors.toList());
	}
	// endregion dels

}
