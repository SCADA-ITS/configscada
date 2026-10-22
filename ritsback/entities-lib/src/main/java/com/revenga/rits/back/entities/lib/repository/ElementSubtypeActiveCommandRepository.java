package com.revenga.rits.back.entities.lib.repository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;

import com.revenga.rits.back.data.core.model.BasicElement;
import com.revenga.rits.back.data.core.model.ElementSubtypeActiveCommand;
import com.revenga.rits.back.entities.lib.repository.entity.EntityCacheable;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository;

public class ElementSubtypeActiveCommandRepository extends EntityRepository 
									implements EntityCacheable<ElementSubtypeActiveCommand> {

	// region Constructors
	/**
	 * 
	 */
	public ElementSubtypeActiveCommandRepository() {
	
		super(ElementSubtypeActiveCommand.class);
	}
	
	/**
	 * 
	 * @param cacheable
	 */
	public ElementSubtypeActiveCommandRepository(Boolean cacheable) {
	
		super(ElementSubtypeActiveCommand.class, cacheable);
	}
	
	/**
	 * 
	 * @param cacheable
	 * @param cacheRetentionTime
	 */
	public ElementSubtypeActiveCommandRepository(Boolean cacheable, Long cacheRetentionTime) {
	
		super(ElementSubtypeActiveCommand.class, cacheable, cacheRetentionTime);
	}
	// endregion Constructors

	// region getters
	/**
	 * 
	 * @param elementSubtypeId
	 * @param commandElementTypeId
	 * @param elementTypeId
	 * @param fillObject
	 * @return
	 */
	public ElementSubtypeActiveCommand get(Long elementSubtypeId, Long commandElementTypeId, Long elementTypeId,
			Boolean fillObject) {
	
		return (ElementSubtypeActiveCommand) getFromCache(new String[] { String.valueOf(elementSubtypeId),
				String.valueOf(commandElementTypeId), String.valueOf(elementTypeId) }, fillObject);
	}
	
	/**
	 * 
	 * @param elementTypeId
	 * @param elementSubtypeId
	 * @param fillObject
	 * @return
	 */
	public List<ElementSubtypeActiveCommand> getsByElementSubtype(Long elementTypeId, Long elementSubtypeId, Boolean fillObject) {
	
		List<BasicElement> basicElements = getsFromCache(
				new String[] { String.valueOf(elementSubtypeId), null, String.valueOf(elementTypeId) },
				fillObject);
	
		if (!CollectionUtils.isEmpty(basicElements)) {
	
			return basicElements.stream().map(be -> (ElementSubtypeActiveCommand) be).collect(Collectors.toList());
		}
	
		return Collections.emptyList();
	}
	
	
	/**
	 * 
	 */
	@Override
	public List<ElementSubtypeActiveCommand> getAll(Boolean fillObject) {
	
		List<BasicElement> basicElements = getsFromCache(fillObject);
	
		if (!CollectionUtils.isEmpty(basicElements)) {
	
			return basicElements.stream().map(be -> (ElementSubtypeActiveCommand) be).collect(Collectors.toList());
		}
	
		return Collections.emptyList();
	}
	// endregion getters

	// region puts
	/**
	 * 
	 */
	@Override
	public ElementSubtypeActiveCommand put(ElementSubtypeActiveCommand entity) {
	
		return (ElementSubtypeActiveCommand) putFromCache(entity);
	}
	
	/**
	 * 
	 */
	@Override
	public List<ElementSubtypeActiveCommand> put(List<ElementSubtypeActiveCommand> entities) {
	
		List<BasicElement> basicElements = entities.stream().map(elementTypeParam -> (BasicElement) elementTypeParam)
				.collect(Collectors.toList());
	
		basicElements = putsFromCache(basicElements);
	
		return basicElements.stream().map(be -> (ElementSubtypeActiveCommand) be).collect(Collectors.toList());
	}
	// endregion puts

	// region deletes
	/**
	 * 
	 */
	@Override
	public ElementSubtypeActiveCommand del(ElementSubtypeActiveCommand entity) {
	
		return (ElementSubtypeActiveCommand) delFromCache(entity);
	
	}
	
	/**
	 * 
	 */
	@Override
	public List<ElementSubtypeActiveCommand> del(List<ElementSubtypeActiveCommand> entities) {
	
		List<BasicElement> basicElements = entities.stream().map(elementTypeParam -> (BasicElement) elementTypeParam)
				.collect(Collectors.toList());
	
		basicElements = delFromCache(basicElements);
	
		return basicElements.stream().map(be -> (ElementSubtypeActiveCommand) be).collect(Collectors.toList());
	}
	// endregion deletes
}


