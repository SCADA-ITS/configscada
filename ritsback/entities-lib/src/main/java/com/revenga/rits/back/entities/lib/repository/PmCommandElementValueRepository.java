package com.revenga.rits.back.entities.lib.repository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;

import com.revenga.rits.back.data.core.model.BasicElement;
import com.revenga.rits.back.data.core.model.PmCommandElementValue;
import com.revenga.rits.back.entities.lib.repository.entity.EntityCacheable;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository;

public class PmCommandElementValueRepository extends EntityRepository
		implements EntityCacheable<PmCommandElementValue> {

	// region Constructors
	public PmCommandElementValueRepository() {

		super(PmCommandElementValue.class);
	}

	public PmCommandElementValueRepository(Boolean cacheable) {

		super(PmCommandElementValue.class, cacheable);
	}

	public PmCommandElementValueRepository(Boolean cacheable, Long cacheRetentionTime) {

		super(PmCommandElementValue.class, cacheable, cacheRetentionTime);
	}
	// endregion Constructors

	// region getters
	public PmCommandElementValue get(Long queueId, Long commandElementTypeId, Long pmCommandElementId,
			Long elementTypeId, Long elementId, Long commandElementTypeParamId, Boolean fillObject) {

		return (PmCommandElementValue) getFromCache(new String[] { String.valueOf(queueId),
				String.valueOf(commandElementTypeId), String.valueOf(pmCommandElementId), String.valueOf(elementTypeId),
				String.valueOf(elementId), String.valueOf(commandElementTypeParamId) }, fillObject);
	}

	public List<PmCommandElementValue> getsByPmCommandElement(Long queueId, Long commandElementTypeId,
			Long pmCommandElementId, Long elementTypeId, Long elementId, Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(new String[] { String.valueOf(queueId),
				String.valueOf(commandElementTypeId), String.valueOf(pmCommandElementId), String.valueOf(elementTypeId),
				String.valueOf(elementId), null }, fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(PmCommandElementValue.class::cast).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}

	public List<PmCommandElementValue> gets(Long queueId, Long elementTypeId, Long elementId, Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(new String[] { String.valueOf(queueId), null, null,
				String.valueOf(elementTypeId), String.valueOf(elementId), null }, fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(PmCommandElementValue.class::cast).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}

	public List<PmCommandElementValue> getsByElementType(Long elementTypeId, Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(
				new String[] { null, null, null, String.valueOf(elementTypeId), null, null }, fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(PmCommandElementValue.class::cast).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}

	@Override
	public List<PmCommandElementValue> getAll(Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(PmCommandElementValue.class::cast).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}
	// endregion getters

	// region puts
	@Override
	public PmCommandElementValue put(PmCommandElementValue entity) {

		return (PmCommandElementValue) putFromCache(entity);
	}

	@Override
	public List<PmCommandElementValue> put(List<PmCommandElementValue> entities) {

		List<BasicElement> basicElements = entities.stream().map(BasicElement.class::cast).collect(Collectors.toList());

		basicElements = putsFromCache(basicElements);

		return basicElements.stream().map(PmCommandElementValue.class::cast).collect(Collectors.toList());
	}
	// endregion puts

	// region dels
	@Override
	public PmCommandElementValue del(PmCommandElementValue entity) {

		return (PmCommandElementValue) delFromCache(entity);

	}

	@Override
	public List<PmCommandElementValue> del(List<PmCommandElementValue> entities) {

		List<BasicElement> basicElements = entities.stream().map(BasicElement.class::cast).collect(Collectors.toList());

		basicElements = delFromCache(basicElements);

		return basicElements.stream().map(PmCommandElementValue.class::cast).collect(Collectors.toList());
	}

}
