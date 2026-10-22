package com.revenga.rits.back.entities.lib.repository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;

import com.revenga.rits.back.data.core.model.BasicElement;
import com.revenga.rits.back.data.core.model.SmStateMachine;
import com.revenga.rits.back.entities.lib.repository.entity.EntityCacheable;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository;

public class SmStateMachineRepository extends EntityRepository implements EntityCacheable<SmStateMachine> {
	
	
	// region Constructors
	public SmStateMachineRepository() {

		super(SmStateMachine.class);
	}

	public SmStateMachineRepository(Boolean cacheable) {

		super(SmStateMachine.class, cacheable);
	}

	public SmStateMachineRepository(Boolean cacheable, Long cacheRetentionTime) {

		super(SmStateMachine.class, cacheable, cacheRetentionTime);
	}
	// endregion Constructors
	
	// region getters
	public SmStateMachine get(Long smStateMachineId, Boolean fillObject) {
		
		return (SmStateMachine) getFromCache(new String[] { String.valueOf(smStateMachineId) },
				fillObject);
	}
	
	@Override
	public List<SmStateMachine> getAll(Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(be -> (SmStateMachine) be).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}

	@Override
	public SmStateMachine put(SmStateMachine entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SmStateMachine> put(List<SmStateMachine> entities) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SmStateMachine del(SmStateMachine entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SmStateMachine> del(List<SmStateMachine> entities) {
		// TODO Auto-generated method stub
		return null;
	}

}
