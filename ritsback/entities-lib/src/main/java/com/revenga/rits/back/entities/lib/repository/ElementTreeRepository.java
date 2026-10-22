package com.revenga.rits.back.entities.lib.repository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;

import com.revenga.rits.back.data.core.model.BasicElement;
import com.revenga.rits.back.data.core.model.ElementTree;
import com.revenga.rits.back.entities.lib.repository.entity.EntityCacheable;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepositoryOptions;

public class ElementTreeRepository extends EntityRepository implements EntityCacheable<ElementTree> {

	// region Constructors
	public ElementTreeRepository() {

		super(ElementTree.class);
	}
	
	public ElementTreeRepository(EntityRepositoryOptions options) {

		super(ElementTree.class, options);
	}

	// endregion Constructors

	// region getters
	public ElementTree get(Long id, Boolean fillObject) {

		return (ElementTree) getFromCache(new String[] { String.valueOf(id) }, fillObject);
	}

	@Override
	public List<ElementTree> getAll(Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(ElementTree.class::cast).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}
	// endregion getters

	// region puts
	@Override
	public ElementTree put(ElementTree entity) {

		return (ElementTree) putFromCache(entity);
	}

	@Override
	public List<ElementTree> put(List<ElementTree> entities) {

		List<BasicElement> basicElements = entities.stream()
				.map(BasicElement.class::cast).collect(Collectors.toList());

		basicElements = putsFromCache(basicElements);

		return basicElements.stream().map(ElementTree.class::cast).collect(Collectors.toList());
	}
	// endregion puts

	// region dels
	@Override
	public ElementTree del(ElementTree entity) {

		return (ElementTree) delFromCache(entity);

	}

	@Override
	public List<ElementTree> del(List<ElementTree> entities) {

		List<BasicElement> basicElements = entities.stream()
				.map(BasicElement.class::cast).collect(Collectors.toList());

		basicElements = delFromCache(basicElements);

		return basicElements.stream().map(ElementTree.class::cast).collect(Collectors.toList());
	}
	// endregion dels

}
