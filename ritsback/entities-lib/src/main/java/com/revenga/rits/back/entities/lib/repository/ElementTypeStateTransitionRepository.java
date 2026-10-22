package com.revenga.rits.back.entities.lib.repository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;

import com.revenga.rits.back.data.core.model.BasicElement;
import com.revenga.rits.back.data.core.model.ElementTypeStateTransition;
import com.revenga.rits.back.entities.lib.repository.entity.EntityCacheable;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository;

public class ElementTypeStateTransitionRepository extends EntityRepository implements EntityCacheable<ElementTypeStateTransition> {

	// region Constructors
	public ElementTypeStateTransitionRepository() {
		
		super(ElementTypeStateTransition.class);
	}
	
	public ElementTypeStateTransitionRepository(Boolean cacheable) {
		
		super(ElementTypeStateTransition.class, cacheable);
	}
	
	public ElementTypeStateTransitionRepository(Boolean cacheable, Long cacheRetentionTime) {
		
		super(ElementTypeStateTransition.class, cacheable, cacheRetentionTime);
	}
	// endregion Constructors

	// region getters
	public ElementTypeStateTransition get(Long elementTypeId, Long currentElementTypeStateId, Long nextElementTypeStateId, Boolean fillObject) {
		
		return (ElementTypeStateTransition) getFromCache(new String[] { String.valueOf(elementTypeId),
				String.valueOf(currentElementTypeStateId), String.valueOf(nextElementTypeStateId) }, fillObject);
	}
	
	public List<ElementTypeStateTransition> getsByElementType(Long elementTypeId, Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(fillObject);

	    if (!CollectionUtils.isEmpty(basicElements)) {

	      List<ElementTypeStateTransition> elementTypeStateTransitions = basicElements.stream().map(ElementTypeStateTransition.class::cast)
	          .collect(Collectors.toList());

	      return elementTypeStateTransitions.stream().filter(e -> e.getElementTypeId().equals(elementTypeId)).collect(Collectors.toList());
	    }

	    return Collections.emptyList();
	}
	
	@Override
	public List<ElementTypeStateTransition> getAll(Boolean fillObject) {
		
		List<BasicElement> basicElements = getsFromCache(fillObject);
		
		if (!CollectionUtils.isEmpty(basicElements)) {
			
			return basicElements.stream()
					.map(be -> (ElementTypeStateTransition) be).collect(Collectors.toList());
		}
		
		return Collections.emptyList();
	}
	// endregion getters

	// region puts
	@Override
	public ElementTypeStateTransition put(ElementTypeStateTransition entity) {

		return (ElementTypeStateTransition) putFromCache(entity);
	}

	@Override
	public List<ElementTypeStateTransition> put(List<ElementTypeStateTransition> entities) {
		
		List<BasicElement> basicElements = entities.stream()
												   .map(be -> (BasicElement) be)
												   .collect(Collectors.toList());
		
		
		basicElements = putsFromCache(basicElements);
		
		return basicElements.stream()
						    .map(be -> (ElementTypeStateTransition) be)
						    .collect(Collectors.toList());
	}
	// endregion puts

	// region dels
	@Override
	public ElementTypeStateTransition del(ElementTypeStateTransition entity) {
		
		return (ElementTypeStateTransition) delFromCache(entity);
		
	}

	@Override
	public List<ElementTypeStateTransition> del(List<ElementTypeStateTransition> entities) {
		
		List<BasicElement> basicElements = entities.stream()
												   .map(be -> (BasicElement) be)
												   .collect(Collectors.toList());

		basicElements = delFromCache(basicElements);
		
		return basicElements.stream()
							.map(be -> (ElementTypeStateTransition) be)
							.collect(Collectors.toList());
	}
	// endregion dels

}
