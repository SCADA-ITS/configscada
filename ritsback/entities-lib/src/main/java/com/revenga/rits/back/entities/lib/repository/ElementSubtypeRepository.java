package com.revenga.rits.back.entities.lib.repository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;

import com.revenga.rits.back.data.core.model.BasicElement;
import com.revenga.rits.back.data.core.model.ElementSubtype;
import com.revenga.rits.back.entities.lib.repository.entity.EntityCacheable;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository;

public class ElementSubtypeRepository extends EntityRepository implements EntityCacheable<ElementSubtype> {

	// region Constructors
	public ElementSubtypeRepository() {

		super(ElementSubtype.class);
	}

	public ElementSubtypeRepository(Boolean cacheable) {

		super(ElementSubtype.class, cacheable);
	}

	public ElementSubtypeRepository(Boolean cacheable, Long cacheRetentionTime) {

		super(ElementSubtype.class, cacheable, cacheRetentionTime);
	}
	// endregion Constructors

	// region getters
	public ElementSubtype get(Long elementSubtypeId, Long elementTypeId, Boolean fillObject) {

		return (ElementSubtype) getFromCache(new String[] { String.valueOf(elementSubtypeId), String.valueOf(elementTypeId)  }, fillObject);
	}
		
	public List<ElementSubtype> getsByElementType(Long elementTypeId, Boolean fillObject) {
		
		List<BasicElement> basicElements = getsFromCache(new String[] { null, String.valueOf(elementTypeId)}, fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(be -> (ElementSubtype) be).collect(Collectors.toList());
		}

		return Collections.emptyList();

	}

	@Override
	public List<ElementSubtype> getAll(Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(be -> (ElementSubtype) be).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}
	// endregion getters

	// region puts
	@Override
	public ElementSubtype put(ElementSubtype entity) {

		return (ElementSubtype) putFromCache(entity);
	}

	@Override
	public List<ElementSubtype> put(List<ElementSubtype> entities) {

		List<BasicElement> basicElements = entities.stream().map(element -> (BasicElement) element)
				.collect(Collectors.toList());

		basicElements = putsFromCache(basicElements);

		return basicElements.stream().map(be -> (ElementSubtype) be).collect(Collectors.toList());
	}
	// endregion puts

	// region dels
	@Override
	public ElementSubtype del(ElementSubtype entity) {

		return (ElementSubtype) delFromCache(entity);

	}

	@Override
	public List<ElementSubtype> del(List<ElementSubtype> entities) {

		List<BasicElement> basicElements = entities.stream().map(element -> (BasicElement) element)
				.collect(Collectors.toList());

		basicElements = delFromCache(basicElements);

		return basicElements.stream().map(be -> (ElementSubtype) be).collect(Collectors.toList());
	}
	// endregion dels

}
