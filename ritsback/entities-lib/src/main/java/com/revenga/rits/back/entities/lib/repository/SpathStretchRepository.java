package com.revenga.rits.back.entities.lib.repository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;

import com.revenga.rits.back.data.core.model.BasicElement;
import com.revenga.rits.back.data.core.model.SpathStretch;
import com.revenga.rits.back.entities.lib.repository.entity.EntityCacheable;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository;

public class SpathStretchRepository extends EntityRepository implements EntityCacheable<SpathStretch> {

	// region Constructors
	public SpathStretchRepository() {

		super(SpathStretch.class);
	}

	public SpathStretchRepository(Boolean cacheable) {

		super(SpathStretch.class, cacheable);
	}

	public SpathStretchRepository(Boolean cacheable, Long cacheRetentionTime) {

		super(SpathStretch.class, cacheable, cacheRetentionTime);
	}
	// endregion Constructors

	// region getters
	public SpathStretch get(Long spathId, Long stretchId, Long relStretchId, Boolean fillObject) {

		return (SpathStretch) getFromCache(
				new String[] { String.valueOf(spathId), String.valueOf(stretchId), String.valueOf(relStretchId) },
				fillObject);
	}

	@Override
	public List<SpathStretch> getAll(Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(SpathStretch.class::cast).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}

	public List<SpathStretch> gets(Long spathId, Long stretchId, Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(
				new String[] { String.valueOf(spathId), String.valueOf(stretchId), null }, fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(SpathStretch.class::cast).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}
	
	public List<SpathStretch> getsByStretch(Long stretchId, Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(
				new String[] { null, String.valueOf(stretchId), null }, fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(SpathStretch.class::cast).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}
	
	public List<SpathStretch> getsBySpath(Long spathId, Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(
				new String[] { String.valueOf(spathId), null, null }, fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(SpathStretch.class::cast).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}

	@Override
	public SpathStretch put(SpathStretch entity) {
		return (SpathStretch) putFromCache(entity);
	}

	@Override
	public List<SpathStretch> put(List<SpathStretch> entities) {
		List<BasicElement> basicElements = entities.stream().map(BasicElement.class::cast).collect(Collectors.toList());

		basicElements = putsFromCache(basicElements);

		return basicElements.stream().map(SpathStretch.class::cast).collect(Collectors.toList());
	}

	@Override
	public SpathStretch del(SpathStretch entity) {

		return (SpathStretch) delFromCache(entity);
	}

	@Override
	public List<SpathStretch> del(List<SpathStretch> entities) {
		List<BasicElement> basicElements = entities.stream().map(BasicElement.class::cast).collect(Collectors.toList());

		basicElements = delFromCache(basicElements);

		return basicElements.stream().map(SpathStretch.class::cast).collect(Collectors.toList());
	}

}
