package com.revenga.rits.back.entities.lib.repository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;

import com.revenga.rits.back.data.core.model.BasicElement;
import com.revenga.rits.back.data.core.model.ManagementArea;
import com.revenga.rits.back.entities.lib.repository.entity.EntityCacheable;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepositoryOptions;

public class ManagementAreaRepository extends EntityRepository implements EntityCacheable<ManagementArea> {

	// region Constructors
	public ManagementAreaRepository() {

		super(ManagementArea.class);
	}

	// endregion Constructors

	public ManagementAreaRepository(EntityRepositoryOptions options) {

		super(ManagementArea.class, options);
	}

	// region getters
	public ManagementArea get(Long id, Boolean fillObject) {

		return (ManagementArea) getFromCache(new String[] { String.valueOf(id) }, fillObject);
	}

	@Override
	public List<ManagementArea> getAll(Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(ManagementArea.class::cast).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}

	@Override
	public ManagementArea put(ManagementArea entity) {
		return (ManagementArea) putFromCache(entity);
	}

	@Override
	public List<ManagementArea> put(List<ManagementArea> entities) {
		List<BasicElement> basicElements = entities.stream().map(BasicElement.class::cast).collect(Collectors.toList());

		basicElements = putsFromCache(basicElements);

		return basicElements.stream().map(ManagementArea.class::cast).collect(Collectors.toList());
	}

	@Override
	public ManagementArea del(ManagementArea entity) {

		return (ManagementArea) delFromCache(entity);
	}

	@Override
	public List<ManagementArea> del(List<ManagementArea> entities) {
		List<BasicElement> basicElements = entities.stream().map(BasicElement.class::cast).collect(Collectors.toList());

		basicElements = delFromCache(basicElements);

		return basicElements.stream().map(ManagementArea.class::cast).collect(Collectors.toList());
	}
}
