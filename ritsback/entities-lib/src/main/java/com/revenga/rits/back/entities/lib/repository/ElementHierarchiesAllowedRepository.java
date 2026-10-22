package com.revenga.rits.back.entities.lib.repository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;

import com.revenga.rits.back.data.core.model.BasicElement;
import com.revenga.rits.back.data.core.model.ElementHierarchyAllowed;
import com.revenga.rits.back.entities.lib.repository.entity.EntityCacheable;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository;

public class ElementHierarchiesAllowedRepository extends EntityRepository
		implements EntityCacheable<ElementHierarchyAllowed> {

	// region Constructors
	public ElementHierarchiesAllowedRepository() {

		super(ElementHierarchyAllowed.class);
	}

	public ElementHierarchiesAllowedRepository(Boolean cacheable) {

		super(ElementHierarchyAllowed.class, cacheable);
	}

	public ElementHierarchiesAllowedRepository(Boolean cacheable, Long cacheRetentionTime) {

		super(ElementHierarchyAllowed.class, cacheable, cacheRetentionTime);
	}
	// endregion Constructors

	// region getters
	public ElementHierarchyAllowed get(Long parentElementTypeId, Long childElemenTypetId, Boolean fillObject) {

		return (ElementHierarchyAllowed) getFromCache(
				new String[] { String.valueOf(parentElementTypeId), String.valueOf(childElemenTypetId) }, fillObject);
	}

	public List<ElementHierarchyAllowed> getsByChildElementType(Long childElementTypeId, Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(new String[] { null, String.valueOf(childElementTypeId) },
				fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(be -> (ElementHierarchyAllowed) be).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}

	@Override
	public List<ElementHierarchyAllowed> getAll(Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(be -> (ElementHierarchyAllowed) be).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}

	@Override
	public ElementHierarchyAllowed put(ElementHierarchyAllowed entity) {

		return (ElementHierarchyAllowed) putFromCache(entity);
	}

	@Override
	public List<ElementHierarchyAllowed> put(List<ElementHierarchyAllowed> entities) {

		List<BasicElement> basicElements = entities.stream().map(element -> (BasicElement) element)
				.collect(Collectors.toList());

		basicElements = putsFromCache(basicElements);

		return basicElements.stream().map(be -> (ElementHierarchyAllowed) be).collect(Collectors.toList());
	}

	@Override
	public ElementHierarchyAllowed del(ElementHierarchyAllowed entity) {

		return (ElementHierarchyAllowed) delFromCache(entity);
	}

	@Override
	public List<ElementHierarchyAllowed> del(List<ElementHierarchyAllowed> entities) {

		List<BasicElement> basicElements = entities.stream().map(element -> (BasicElement) element)
				.collect(Collectors.toList());

		basicElements = delFromCache(basicElements);

		return basicElements.stream().map(be -> (ElementHierarchyAllowed) be).collect(Collectors.toList());
	}

}
