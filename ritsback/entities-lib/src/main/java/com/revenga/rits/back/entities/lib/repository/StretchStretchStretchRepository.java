package com.revenga.rits.back.entities.lib.repository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;

import com.revenga.rits.back.data.core.model.BasicElement;
import com.revenga.rits.back.data.core.model.StretchStretchStretch;
import com.revenga.rits.back.entities.lib.repository.entity.EntityCacheable;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository;

public class StretchStretchStretchRepository extends EntityRepository
		implements EntityCacheable<StretchStretchStretch> {

	// region Constructors
	public StretchStretchStretchRepository() {

		super(StretchStretchStretch.class);
	}

	public StretchStretchStretchRepository(Boolean cacheable, Long cacheRetentionTime) {

		super(StretchStretchStretch.class, cacheable, cacheRetentionTime);
	}

	// endregion Constructors

	// region getters
	public StretchStretchStretch get(Long stretchStretchId, Long stretchId, Boolean fillObject) {

		return (StretchStretchStretch) getFromCache(
				new String[] { String.valueOf(stretchStretchId), String.valueOf(stretchId) }, fillObject);
	}

	public List<StretchStretchStretch> getsByStretchStretch(Long stretchStretchId, Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(new String[] { String.valueOf(stretchStretchId), null },
				fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(StretchStretchStretch.class::cast).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}

	public List<StretchStretchStretch> getsByStretch(Long stretchId, Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(new String[] { null, String.valueOf(stretchId) }, fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(StretchStretchStretch.class::cast).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}

	@Override
	public List<StretchStretchStretch> getAll(Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(StretchStretchStretch.class::cast).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}

	@Override
	public StretchStretchStretch put(StretchStretchStretch entity) {
		return (StretchStretchStretch) putFromCache(entity);
	}

	@Override
	public List<StretchStretchStretch> put(List<StretchStretchStretch> entities) {
		List<BasicElement> basicElements = entities.stream().map(BasicElement.class::cast).collect(Collectors.toList());

		basicElements = putsFromCache(basicElements);

		return basicElements.stream().map(StretchStretchStretch.class::cast).collect(Collectors.toList());
	}

	@Override
	public StretchStretchStretch del(StretchStretchStretch entity) {

		return (StretchStretchStretch) delFromCache(entity);
	}

	@Override
	public List<StretchStretchStretch> del(List<StretchStretchStretch> entities) {
		List<BasicElement> basicElements = entities.stream().map(BasicElement.class::cast).collect(Collectors.toList());

		basicElements = delFromCache(basicElements);

		return basicElements.stream().map(StretchStretchStretch.class::cast).collect(Collectors.toList());
	}

}
