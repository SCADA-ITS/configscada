package com.revenga.rits.back.entities.lib.repository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;

import com.revenga.rits.back.data.core.model.BasicElement;
import com.revenga.rits.back.data.core.model.VmsGraphicGroupElement;
import com.revenga.rits.back.entities.lib.repository.entity.EntityCacheable;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository;

public class VmsGraphicGroupElementRepository extends EntityRepository implements EntityCacheable<VmsGraphicGroupElement> {

	// region Constructors
	public VmsGraphicGroupElementRepository() {

		super(VmsGraphicGroupElement.class);
	}

	public VmsGraphicGroupElementRepository(Boolean cacheable) {

		super(VmsGraphicGroupElement.class, cacheable);
	}

	public VmsGraphicGroupElementRepository(Boolean cacheable, Long cacheRetentionTime) {

		super(VmsGraphicGroupElement.class, cacheable, cacheRetentionTime);
	}
	// endregion Constructors
	
	// region getters
	public VmsGraphicGroupElement get(Long graphicGroupId, Long elementTypeId, Long elementId, Boolean fillObject) {
		
		return (VmsGraphicGroupElement) getFromCache(new String[] { String.valueOf(graphicGroupId), String.valueOf(elementTypeId), String.valueOf(elementId) },
				fillObject);
	}
	
	// region getters
	public List<VmsGraphicGroupElement> getsByElement(Long elementTypeId, Long elementId, Boolean fillObject) {
		
		
		List<BasicElement> basicElements = getsFromCache(new String[] { null, String.valueOf(elementTypeId),  String.valueOf(elementId) },
														 fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(be -> (VmsGraphicGroupElement) be).collect(Collectors.toList());
		}

		return Collections.emptyList();		
		
	}	

	@Override
	public List<VmsGraphicGroupElement> getAll(Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(be -> (VmsGraphicGroupElement) be).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}

	@Override
	public VmsGraphicGroupElement put(VmsGraphicGroupElement entity) {
		
		return (VmsGraphicGroupElement) putFromCache(entity);
	}

	@Override
	public List<VmsGraphicGroupElement> put(List<VmsGraphicGroupElement> entities) {
		List<BasicElement> basicElements = entities.stream().map(be -> (BasicElement) be).collect(Collectors.toList());

		basicElements = putsFromCache(basicElements);

		return basicElements.stream().map(be -> (VmsGraphicGroupElement) be).collect(Collectors.toList());
	}

	@Override
	public VmsGraphicGroupElement del(VmsGraphicGroupElement entity) {

		return (VmsGraphicGroupElement) delFromCache(entity);
	}

	@Override
	public List<VmsGraphicGroupElement> del(List<VmsGraphicGroupElement> entities) {
		List<BasicElement> basicElements = entities.stream().map(be -> (BasicElement) be).collect(Collectors.toList());

		basicElements = delFromCache(basicElements);

		return basicElements.stream().map(be -> (VmsGraphicGroupElement) be).collect(Collectors.toList());
	}

}
