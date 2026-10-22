package com.revenga.rits.back.entities.lib.repository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;

import com.revenga.rits.back.data.core.model.BasicElement;
import com.revenga.rits.back.data.core.model.SmTransition;
import com.revenga.rits.back.entities.lib.repository.entity.EntityCacheable;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository;

public class SmTransitionRepository extends EntityRepository implements EntityCacheable<SmTransition> {
	
	
	// region Constructors
	public SmTransitionRepository() {

		super(SmTransition.class);
	}

	public SmTransitionRepository(Boolean cacheable) {

		super(SmTransition.class, cacheable);
	}

	public SmTransitionRepository(Boolean cacheable, Long cacheRetentionTime) {

		super(SmTransition.class, cacheable, cacheRetentionTime);
	}
	// endregion Constructors
	
	// region getters

	public SmTransition get(Long stateMachineId, Long stateId, Long transitionId, Boolean fillObject) {
		
		return (SmTransition) getFromCache(new String[] { String.valueOf(stateMachineId),  String.valueOf(stateId), String.valueOf(transitionId)},
										   fillObject);
	}
	
	public List<SmTransition> getsByStateMachine(Long stateMachineId, Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(new String[] { String.valueOf(stateMachineId), null, null}, fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(be -> (SmTransition) be).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}
	
	public List<SmTransition> getsByState(Long stateId, Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(new String[] { null, String.valueOf(stateId), null}, fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(be -> (SmTransition) be).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}
	
	public List<SmTransition> getsByTransition(Long transitionId, Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(new String[] { null, null, String.valueOf(transitionId)}, fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(be -> (SmTransition) be).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}	
	
	
	@Override
	public List<SmTransition> getAll(Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(be -> (SmTransition) be).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}

	@Override
	public SmTransition put(SmTransition entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SmTransition> put(List<SmTransition> entities) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SmTransition del(SmTransition entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SmTransition> del(List<SmTransition> entities) {
		// TODO Auto-generated method stub
		return null;
	}

}
