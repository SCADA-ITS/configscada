package com.revenga.rits.back.entities.lib.repository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;

import com.revenga.rits.back.data.core.model.BasicElement;
import com.revenga.rits.back.data.core.model.CommandElementTypeParam;
import com.revenga.rits.back.entities.lib.repository.entity.EntityCacheable;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository;

public class CommandElementTypeParamRepository extends EntityRepository implements EntityCacheable<CommandElementTypeParam> {

	// region Constructors
	public CommandElementTypeParamRepository() {
		
		super(CommandElementTypeParam.class);
	}
	
	public CommandElementTypeParamRepository(Boolean cacheable) {
		
		super(CommandElementTypeParam.class, cacheable);
	}
	
	public CommandElementTypeParamRepository(Boolean cacheable, Long cacheRetentionTime) {
		
		super(CommandElementTypeParam.class, cacheable, cacheRetentionTime);
	}
	// endregion Constructors

	// region getters
	public CommandElementTypeParam get(Long commandElementTypeId, Long elementTypeId, Long id, Boolean fillObject) {
		
		return (CommandElementTypeParam) getFromCache(
				new String[] { String.valueOf(commandElementTypeId), String.valueOf(elementTypeId), String.valueOf(id) }, fillObject);
	}
	
	public List<CommandElementTypeParam> getsByCommandElementType(Long commandElementTypeId, Long elementTypeId, Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(new String[] { String.valueOf(commandElementTypeId), String.valueOf(elementTypeId), null }, fillObject);
		
		if (!CollectionUtils.isEmpty(basicElements)) {
			
			return basicElements.stream()
					.map(be -> (CommandElementTypeParam) be).collect(Collectors.toList());
		}
		
		return Collections.emptyList();
	}
	
	@Override
	public List<CommandElementTypeParam> getAll(Boolean fillObject) {
		
		List<BasicElement> basicElements = getsFromCache(fillObject);
		
		if (!CollectionUtils.isEmpty(basicElements)) {
			
			return basicElements.stream()
					.map(be -> (CommandElementTypeParam) be).collect(Collectors.toList());
		}
		
		return Collections.emptyList();
	}
	// endregion getters

	// region puts
	@Override
	public CommandElementTypeParam put(CommandElementTypeParam entity) {

		return (CommandElementTypeParam) putFromCache(entity);
	}

	@Override
	public List<CommandElementTypeParam> put(List<CommandElementTypeParam> entities) {
		
		List<BasicElement> basicElements = entities.stream()
												   .map(commandElementTypeParam -> (BasicElement) commandElementTypeParam)
												   .collect(Collectors.toList());
		
		
		basicElements = putsFromCache(basicElements);
		
		return basicElements.stream()
						    .map(be -> (CommandElementTypeParam) be)
						    .collect(Collectors.toList());
	}
	// endregion puts

	// region dels
	@Override
	public CommandElementTypeParam del(CommandElementTypeParam entity) {
		
		return (CommandElementTypeParam) delFromCache(entity);
		
	}

	@Override
	public List<CommandElementTypeParam> del(List<CommandElementTypeParam> entities) {
		
		List<BasicElement> basicElements = entities.stream()
												   .map(commandElementTypeParam -> (BasicElement) commandElementTypeParam)
												   .collect(Collectors.toList());

		basicElements = delFromCache(basicElements);
		
		return basicElements.stream()
							.map(be -> (CommandElementTypeParam) be)
							.collect(Collectors.toList());
	}
	// endregion dels

}
