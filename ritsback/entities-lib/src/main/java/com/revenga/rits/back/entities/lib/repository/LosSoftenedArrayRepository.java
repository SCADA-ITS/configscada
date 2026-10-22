package com.revenga.rits.back.entities.lib.repository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;

import com.revenga.rits.back.data.core.model.BasicElement;
import com.revenga.rits.back.data.core.model.LosSoftenedArray;
import com.revenga.rits.back.entities.lib.repository.entity.EntityCacheable;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository;

public class LosSoftenedArrayRepository extends EntityRepository implements EntityCacheable<LosSoftenedArray> {

	// region Constructors
	public LosSoftenedArrayRepository() {

		super(LosSoftenedArray.class);
	}

	public LosSoftenedArrayRepository(Boolean cacheable) {

		super(LosSoftenedArray.class, cacheable);
	}

	public LosSoftenedArrayRepository(Boolean cacheable, Long cacheRetentionTime) {

		super(LosSoftenedArray.class, cacheable, cacheRetentionTime);
	}
	// endregion Constructors

	// region getters
	public LosSoftenedArray get(Long id, Boolean fillObject) {

		return (LosSoftenedArray) getFromCache(new String[] { String.valueOf(id) }, fillObject);
	}

	@Override
	public List<LosSoftenedArray> getAll(Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(be -> (LosSoftenedArray) be).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}

	// endregion getters

	// region puts
	@Override
	public LosSoftenedArray put(LosSoftenedArray entity) {

		return (LosSoftenedArray) putFromCache(entity);
	}

	@Override
	public List<LosSoftenedArray> put(List<LosSoftenedArray> entities) {

		List<BasicElement> basicElements = entities.stream().map(element -> (BasicElement) element)
				.collect(Collectors.toList());

		basicElements = putsFromCache(basicElements);

		return basicElements.stream().map(be -> (LosSoftenedArray) be).collect(Collectors.toList());
	}
	// endregion puts

	// region dels
	@Override
	public LosSoftenedArray del(LosSoftenedArray entity) {

		return (LosSoftenedArray) delFromCache(entity);

	}

	@Override
	public List<LosSoftenedArray> del(List<LosSoftenedArray> entities) {

		List<BasicElement> basicElements = entities.stream().map(element -> (BasicElement) element)
				.collect(Collectors.toList());

		basicElements = delFromCache(basicElements);

		return basicElements.stream().map(be -> (LosSoftenedArray) be).collect(Collectors.toList());
	}
	// endregion dels

}
