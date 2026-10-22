package com.revenga.rits.back.entities.lib.repository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;

import com.revenga.rits.back.data.core.model.BasicElement;
import com.revenga.rits.back.data.core.model.ElementSubtypeActiveParam;
import com.revenga.rits.back.entities.lib.repository.entity.EntityCacheable;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository;

public class ElementSubtypeActiveParamRepository extends EntityRepository
		implements EntityCacheable<ElementSubtypeActiveParam> {

	// region Constructors
	public ElementSubtypeActiveParamRepository() {

		super(ElementSubtypeActiveParam.class);
	}

	public ElementSubtypeActiveParamRepository(Boolean cacheable) {

		super(ElementSubtypeActiveParam.class, cacheable);
	}

	public ElementSubtypeActiveParamRepository(Boolean cacheable, Long cacheRetentionTime) {

		super(ElementSubtypeActiveParam.class, cacheable, cacheRetentionTime);
	}
	// endregion Constructors

	// region getters
	public ElementSubtypeActiveParam get(Long elementSubtypeId, Long elementTypeId, Long paramTypeId, Long id,
			Boolean fillObject) {

		return (ElementSubtypeActiveParam) getFromCache(new String[] { String.valueOf(elementSubtypeId),
				String.valueOf(elementTypeId), String.valueOf(paramTypeId), String.valueOf(id) }, fillObject);
	}

	public List<ElementSubtypeActiveParam> gets(Long elementTypeId, Long elementSubtypeId, Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(
				new String[] { String.valueOf(elementSubtypeId), String.valueOf(elementTypeId), null, null },
				fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(be -> (ElementSubtypeActiveParam) be).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}

	@Override
	public List<ElementSubtypeActiveParam> getAll(Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(be -> (ElementSubtypeActiveParam) be).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}
	// endregion getters

	// region puts
	@Override
	public ElementSubtypeActiveParam put(ElementSubtypeActiveParam entity) {

		return (ElementSubtypeActiveParam) putFromCache(entity);
	}

	@Override
	public List<ElementSubtypeActiveParam> put(List<ElementSubtypeActiveParam> entities) {

		List<BasicElement> basicElements = entities.stream().map(elementTypeParam -> (BasicElement) elementTypeParam)
				.collect(Collectors.toList());

		basicElements = putsFromCache(basicElements);

		return basicElements.stream().map(be -> (ElementSubtypeActiveParam) be).collect(Collectors.toList());
	}
	// endregion puts

	// region dels
	@Override
	public ElementSubtypeActiveParam del(ElementSubtypeActiveParam entity) {

		return (ElementSubtypeActiveParam) delFromCache(entity);

	}

	@Override
	public List<ElementSubtypeActiveParam> del(List<ElementSubtypeActiveParam> entities) {

		List<BasicElement> basicElements = entities.stream().map(elementTypeParam -> (BasicElement) elementTypeParam)
				.collect(Collectors.toList());

		basicElements = delFromCache(basicElements);

		return basicElements.stream().map(be -> (ElementSubtypeActiveParam) be).collect(Collectors.toList());
	}
	// endregion dels

}
