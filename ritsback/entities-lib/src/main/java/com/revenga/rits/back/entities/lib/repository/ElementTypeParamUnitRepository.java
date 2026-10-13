package com.revenga.rits.back.entities.lib.repository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;

import com.revenga.rits.back.data.core.model.BasicElement;
import com.revenga.rits.back.data.core.model.ElementTypeParamUnit;
import com.revenga.rits.back.entities.lib.repository.entity.EntityCacheable;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository;

public class ElementTypeParamUnitRepository extends EntityRepository implements EntityCacheable<ElementTypeParamUnit> {

	// region Constructors
	public ElementTypeParamUnitRepository() {

		super(ElementTypeParamUnit.class);
	}

	public ElementTypeParamUnitRepository(Boolean cacheable) {

		super(ElementTypeParamUnit.class, cacheable);
	}

	public ElementTypeParamUnitRepository(Boolean cacheable, Long cacheRetentionTime) {

		super(ElementTypeParamUnit.class, cacheable, cacheRetentionTime);
	}
	// endregion Constructors

	// region getters
	public ElementTypeParamUnit get(Long id, Boolean fillObject) {

		return (ElementTypeParamUnit) getFromCache(new String[] { String.valueOf(id) }, fillObject);
	}

	@Override
	public List<ElementTypeParamUnit> getAll(Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(be -> (ElementTypeParamUnit) be).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}
	// endregion getters

	// region puts
	@Override
	public ElementTypeParamUnit put(ElementTypeParamUnit entity) {

		return (ElementTypeParamUnit) putFromCache(entity);
	}

	@Override
	public List<ElementTypeParamUnit> put(List<ElementTypeParamUnit> entities) {

		List<BasicElement> basicElements = entities.stream().map(elementTypeParam -> (BasicElement) elementTypeParam)
				.collect(Collectors.toList());

		basicElements = putsFromCache(basicElements);

		return basicElements.stream().map(be -> (ElementTypeParamUnit) be).collect(Collectors.toList());
	}
	// endregion puts

	// region dels
	@Override
	public ElementTypeParamUnit del(ElementTypeParamUnit entity) {

		return (ElementTypeParamUnit) delFromCache(entity);

	}

	@Override
	public List<ElementTypeParamUnit> del(List<ElementTypeParamUnit> entities) {

		List<BasicElement> basicElements = entities.stream().map(elementTypeParam -> (BasicElement) elementTypeParam)
				.collect(Collectors.toList());

		basicElements = delFromCache(basicElements);

		return basicElements.stream().map(be -> (ElementTypeParamUnit) be).collect(Collectors.toList());
	}
	// endregion dels

}
