package com.revenga.rits.back.entities.lib.repository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;

import com.revenga.rits.back.data.core.model.BasicElement;
import com.revenga.rits.back.data.core.model.SmTransition;
import com.revenga.rits.back.data.core.model.SmTransitionCondition;
import com.revenga.rits.back.entities.lib.repository.entity.EntityCacheable;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository;

public class SmTransitionConditionRepository extends EntityRepository implements EntityCacheable<SmTransitionCondition> {
	
	
	// region Constructors
	public SmTransitionConditionRepository() {

		super(SmTransitionCondition.class);
	}

	public SmTransitionConditionRepository(Boolean cacheable) {

		super(SmTransitionCondition.class, cacheable);
	}

	public SmTransitionConditionRepository(Boolean cacheable, Long cacheRetentionTime) {

		super(SmTransitionCondition.class, cacheable, cacheRetentionTime);
	}
	// endregion Constructors
	
	// region getters

	public SmTransitionCondition get(Long stateMachineId, Long stateId, Long transitionId, Long conditionId, Boolean fillObject) {
		
		return (SmTransitionCondition) getFromCache(new String[] { String.valueOf(stateMachineId),  String.valueOf(stateId), String.valueOf(transitionId), String.valueOf(conditionId)},
										   fillObject);
	}
	
	public List<SmTransitionCondition> getsByStateMachine(Long stateMachineId, Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(new String[] { String.valueOf(stateMachineId), null, null, null}, fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(be -> (SmTransitionCondition) be).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}
	
	public List<SmTransitionCondition> getsByState(Long stateId, Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(new String[] { null, String.valueOf(stateId), null, null}, fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(be -> (SmTransitionCondition) be).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}
	
	public List<SmTransitionCondition> getsByTransition(Long transitionId, Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(new String[] { null, null, String.valueOf(transitionId), null}, fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(be -> (SmTransitionCondition) be).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}
	
	public List<SmTransition> getsByCondition(Long conditionId, Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(new String[] { null, null, null, String.valueOf(conditionId)}, fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(be -> (SmTransition) be).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}	
	
	
	
	@Override
	public List<SmTransitionCondition> getAll(Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(be -> (SmTransitionCondition) be).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}

	@Override
	public SmTransitionCondition put(SmTransitionCondition entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SmTransitionCondition> put(List<SmTransitionCondition> entities) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SmTransitionCondition del(SmTransitionCondition entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SmTransitionCondition> del(List<SmTransitionCondition> entities) {
		// TODO Auto-generated method stub
		return null;
	}

}
