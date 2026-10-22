package com.revenga.rits.back.entities.lib.repository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;

import com.revenga.rits.back.data.core.model.BasicElement;
import com.revenga.rits.back.data.core.model.ManagementAreaElement;
import com.revenga.rits.back.entities.lib.repository.entity.EntityCacheable;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepositoryOptions;

public class ManagementAreaElementRepository extends EntityRepository
		implements EntityCacheable<ManagementAreaElement> {

	// region Constructors
	public ManagementAreaElementRepository() {

		super(ManagementAreaElement.class);
	}

	public ManagementAreaElementRepository(EntityRepositoryOptions options) {

		super(ManagementAreaElement.class, options);
	}

	// endregion Constructors

	// region getters
	public ManagementAreaElement get(Long id, Long elementTypeId, Long elementId, Boolean fillObject) {

		return (ManagementAreaElement) getFromCache(
				new String[] { String.valueOf(id), String.valueOf(elementTypeId), String.valueOf(elementId) },
				fillObject);
	}

	@Override
	public List<ManagementAreaElement> getAll(Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(ManagementAreaElement.class::cast).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}

	public List<ManagementAreaElement> gets(Long elementTypeId, Long elementId, Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(
				new String[] { null, String.valueOf(elementTypeId), String.valueOf(elementId) }, fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(ManagementAreaElement.class::cast).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}

	@Override
	public ManagementAreaElement put(ManagementAreaElement entity) {
		return (ManagementAreaElement) putFromCache(entity);
	}

	@Override
	public List<ManagementAreaElement> put(List<ManagementAreaElement> entities) {
		List<BasicElement> basicElements = entities.stream().map(BasicElement.class::cast).collect(Collectors.toList());

		basicElements = putsFromCache(basicElements);

		return basicElements.stream().map(ManagementAreaElement.class::cast).collect(Collectors.toList());
	}

	@Override
	public ManagementAreaElement del(ManagementAreaElement entity) {

		return (ManagementAreaElement) delFromCache(entity);
	}

	@Override
	public List<ManagementAreaElement> del(List<ManagementAreaElement> entities) {
		List<BasicElement> basicElements = entities.stream().map(BasicElement.class::cast).collect(Collectors.toList());

		basicElements = delFromCache(basicElements);

		return basicElements.stream().map(ManagementAreaElement.class::cast).collect(Collectors.toList());
	}
}
