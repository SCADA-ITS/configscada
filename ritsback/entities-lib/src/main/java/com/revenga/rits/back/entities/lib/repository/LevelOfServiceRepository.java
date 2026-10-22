package com.revenga.rits.back.entities.lib.repository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;

import com.revenga.rits.back.data.core.model.BasicElement;
import com.revenga.rits.back.data.core.model.LevelOfService;
import com.revenga.rits.back.entities.lib.repository.entity.EntityCacheable;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository;

public class LevelOfServiceRepository extends EntityRepository implements EntityCacheable<LevelOfService> {

	// region Constructors
	public LevelOfServiceRepository() {

		super(LevelOfService.class);
	}

	public LevelOfServiceRepository(Boolean cacheable) {

		super(LevelOfService.class, cacheable);
	}

	public LevelOfServiceRepository(Boolean cacheable, Long cacheRetentionTime) {

		super(LevelOfService.class, cacheable, cacheRetentionTime);
	}
	// endregion Constructors

	// region getters
	public LevelOfService get(Long id, Boolean fillObject) {

		return (LevelOfService) getFromCache(new String[] { String.valueOf(id) }, fillObject);
	}

	@Override
	public List<LevelOfService> getAll(Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(be -> (LevelOfService) be).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}
	// endregion getters

	// region puts
	@Override
	public LevelOfService put(LevelOfService entity) {

		return (LevelOfService) putFromCache(entity);
	}

	@Override
	public List<LevelOfService> put(List<LevelOfService> entities) {

		List<BasicElement> basicElements = entities.stream().map(element -> (BasicElement) element)
				.collect(Collectors.toList());

		basicElements = putsFromCache(basicElements);

		return basicElements.stream().map(be -> (LevelOfService) be).collect(Collectors.toList());
	}
	// endregion puts

	// region dels
	@Override
	public LevelOfService del(LevelOfService entity) {

		return (LevelOfService) delFromCache(entity);

	}

	@Override
	public List<LevelOfService> del(List<LevelOfService> entities) {

		List<BasicElement> basicElements = entities.stream().map(element -> (BasicElement) element)
				.collect(Collectors.toList());

		basicElements = delFromCache(basicElements);

		return basicElements.stream().map(be -> (LevelOfService) be).collect(Collectors.toList());
	}
	// endregion dels

}
