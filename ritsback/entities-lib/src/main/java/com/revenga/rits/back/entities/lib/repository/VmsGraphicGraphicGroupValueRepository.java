package com.revenga.rits.back.entities.lib.repository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;

import com.revenga.rits.back.data.core.model.BasicElement;
import com.revenga.rits.back.data.core.model.VmsGraphicGraphicGroupValue;
import com.revenga.rits.back.entities.lib.repository.entity.EntityCacheable;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository;

public class VmsGraphicGraphicGroupValueRepository extends EntityRepository implements EntityCacheable<VmsGraphicGraphicGroupValue> {

	// region Constructors
	public VmsGraphicGraphicGroupValueRepository() {

		super(VmsGraphicGraphicGroupValueRepository.class);
	}

	public VmsGraphicGraphicGroupValueRepository(Boolean cacheable) {

		super(VmsGraphicGraphicGroupValue.class, cacheable);
	}

	public VmsGraphicGraphicGroupValueRepository(Boolean cacheable, Long cacheRetentionTime) {

		super(VmsGraphicGraphicGroupValue.class, cacheable, cacheRetentionTime);
	}
	// endregion Constructors
	
	// region getters
	public VmsGraphicGraphicGroupValue get(Long graphicTypeId, Long graphicId, Long graphicGroupId, Boolean fillObject) {
		
		return (VmsGraphicGraphicGroupValue) getFromCache(new String[] { String.valueOf(graphicTypeId), String.valueOf(graphicId), String.valueOf(graphicGroupId) },
				fillObject);
	}
	
	public List<VmsGraphicGraphicGroupValue> getByGroup(Long graphicGroupId, Boolean fillObject) {
		
		List<BasicElement> basicElements = getsFromCache(new String[] { null, null, String.valueOf(graphicGroupId) },
				 fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {
		
		return basicElements.stream().map(VmsGraphicGraphicGroupValue.class::cast).collect(Collectors.toList());
		}
		
		return Collections.emptyList();	

	}	
	
	@Override
	public List<VmsGraphicGraphicGroupValue> getAll(Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(VmsGraphicGraphicGroupValue.class::cast).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}

	@Override
	public VmsGraphicGraphicGroupValue put(VmsGraphicGraphicGroupValue entity) {
		
		return (VmsGraphicGraphicGroupValue) putFromCache(entity);
	}

	@Override
	public List<VmsGraphicGraphicGroupValue> put(List<VmsGraphicGraphicGroupValue> entities) {
		List<BasicElement> basicElements = entities.stream().map(BasicElement.class::cast).collect(Collectors.toList());

		basicElements = putsFromCache(basicElements);

		return basicElements.stream().map(VmsGraphicGraphicGroupValue.class::cast).collect(Collectors.toList());
	}

	@Override
	public VmsGraphicGraphicGroupValue del(VmsGraphicGraphicGroupValue entity) {

		return (VmsGraphicGraphicGroupValue) delFromCache(entity);
	}

	@Override
	public List<VmsGraphicGraphicGroupValue> del(List<VmsGraphicGraphicGroupValue> entities) {
		List<BasicElement> basicElements = entities.stream().map(BasicElement.class::cast).collect(Collectors.toList());

		basicElements = delFromCache(basicElements);

		return basicElements.stream().map(VmsGraphicGraphicGroupValue.class::cast).collect(Collectors.toList());
	}

}
