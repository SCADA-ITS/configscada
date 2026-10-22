package com.revenga.rits.back.entities.lib.repository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;

import com.revenga.rits.back.data.core.model.BasicElement;
import com.revenga.rits.back.data.core.model.SmState;
import com.revenga.rits.back.data.core.model.SmStateTimer;
import com.revenga.rits.back.entities.lib.repository.entity.EntityCacheable;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository;

public class SmStateRepository extends EntityRepository implements EntityCacheable<SmState> {
	
	
	// region Constructors
	public SmStateRepository() {

		super(SmState.class);
	}

	public SmStateRepository(Boolean cacheable) {

		super(SmState.class, cacheable);
	}

	public SmStateRepository(Boolean cacheable, Long cacheRetentionTime) {

		super(SmState.class, cacheable, cacheRetentionTime);
	}
	// endregion Constructors
	
	// region getters
	public SmState get(Long smStatemachineId, Long smStateId, Boolean fillObject) {
		
		return (SmState) getFromCache(new String[] { String.valueOf(smStatemachineId), String.valueOf(smStateId) },
				fillObject);
	}
	
	public List<SmState> getsByStateMachine(Long stateMachineId, Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(new String[] { String.valueOf(stateMachineId), null}, fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(be -> (SmState) be).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}
	
	public List<SmStateTimer> getsByState(Long stateId, Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(new String[] { null, String.valueOf(stateId)}, fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(be -> (SmStateTimer) be).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}	
	
	@Override
	public List<SmState> getAll(Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(be -> (SmState) be).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}

	@Override
	public SmState put(SmState entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SmState> put(List<SmState> entities) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SmState del(SmState entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SmState> del(List<SmState> entities) {
		// TODO Auto-generated method stub
		return null;
	}

}
