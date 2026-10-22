package com.revenga.rits.back.entities.lib.repository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;

import com.revenga.rits.back.data.core.model.BasicElement;
import com.revenga.rits.back.data.core.model.ElementTreeNodeHierarchy;
import com.revenga.rits.back.entities.lib.repository.entity.EntityCacheable;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepositoryOptions;

public class ElementTreeNodeHierarchyRepository extends EntityRepository implements EntityCacheable<ElementTreeNodeHierarchy> {

	// region Constructors
	public ElementTreeNodeHierarchyRepository() {

		super(ElementTreeNodeHierarchy.class);
	}

	public ElementTreeNodeHierarchyRepository(EntityRepositoryOptions options) {

		super(ElementTreeNodeHierarchy.class, options);
	}
	
	// endregion Constructors

	// region getters
	public ElementTreeNodeHierarchy get(Long id, Boolean fillObject) {

		return (ElementTreeNodeHierarchy) getFromCache(new String[] { String.valueOf(id) }, fillObject);
	}

	@Override
	public List<ElementTreeNodeHierarchy> getAll(Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(ElementTreeNodeHierarchy.class::cast).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}
	// endregion getters

	// region puts
	@Override
	public ElementTreeNodeHierarchy put(ElementTreeNodeHierarchy entity) {

		return (ElementTreeNodeHierarchy) putFromCache(entity);
	}

	@Override
	public List<ElementTreeNodeHierarchy> put(List<ElementTreeNodeHierarchy> entities) {

		List<BasicElement> basicElements = entities.stream()
				.map(BasicElement.class::cast).collect(Collectors.toList());

		basicElements = putsFromCache(basicElements);

		return basicElements.stream().map(ElementTreeNodeHierarchy.class::cast).collect(Collectors.toList());
	}
	// endregion puts

	// region dels
	@Override
	public ElementTreeNodeHierarchy del(ElementTreeNodeHierarchy entity) {

		return (ElementTreeNodeHierarchy) delFromCache(entity);

	}

	@Override
	public List<ElementTreeNodeHierarchy> del(List<ElementTreeNodeHierarchy> entities) {

		List<BasicElement> basicElements = entities.stream()
				.map(BasicElement.class::cast).collect(Collectors.toList());

		basicElements = delFromCache(basicElements);

		return basicElements.stream().map(ElementTreeNodeHierarchy.class::cast).collect(Collectors.toList());
	}
	// endregion dels

}
