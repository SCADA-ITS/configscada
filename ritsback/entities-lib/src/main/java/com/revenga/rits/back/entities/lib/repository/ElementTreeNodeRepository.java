package com.revenga.rits.back.entities.lib.repository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;

import com.revenga.rits.back.data.core.model.BasicElement;
import com.revenga.rits.back.data.core.model.ElementTreeNode;
import com.revenga.rits.back.entities.lib.repository.entity.EntityCacheable;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepositoryOptions;

public class ElementTreeNodeRepository extends EntityRepository implements EntityCacheable<ElementTreeNode> {

	// region Constructors
	public ElementTreeNodeRepository() {

		super(ElementTreeNode.class);
	}

	public ElementTreeNodeRepository(EntityRepositoryOptions options) {

		super(ElementTreeNode.class, options);
	}
	
	// endregion Constructors

	// region getters
	public ElementTreeNode get(Long id, Boolean fillObject) {

		return (ElementTreeNode) getFromCache(new String[] { String.valueOf(id) }, fillObject);
	}

	@Override
	public List<ElementTreeNode> getAll(Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(ElementTreeNode.class::cast).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}
	
	// endregion getters

	// region puts
	@Override
	public ElementTreeNode put(ElementTreeNode entity) {

		return (ElementTreeNode) putFromCache(entity);
	}

	@Override
	public List<ElementTreeNode> put(List<ElementTreeNode> entities) {

		List<BasicElement> basicElements = entities.stream()
				.map(BasicElement.class::cast).collect(Collectors.toList());

		basicElements = putsFromCache(basicElements);

		return basicElements.stream().map(ElementTreeNode.class::cast).collect(Collectors.toList());
	}
	// endregion puts

	// region dels
	@Override
	public ElementTreeNode del(ElementTreeNode entity) {

		return (ElementTreeNode) delFromCache(entity);

	}

	@Override
	public List<ElementTreeNode> del(List<ElementTreeNode> entities) {

		List<BasicElement> basicElements = entities.stream()
				.map(BasicElement.class::cast).collect(Collectors.toList());

		basicElements = delFromCache(basicElements);

		return basicElements.stream().map(ElementTreeNode.class::cast).collect(Collectors.toList());
	}
	// endregion dels

}
