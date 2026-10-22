package com.revenga.rits.back.entities.lib.repository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;

import com.revenga.rits.back.data.core.model.BasicElement;
import com.revenga.rits.back.data.core.model.SmStateTimer;
import com.revenga.rits.back.data.core.model.SmTimer;
import com.revenga.rits.back.entities.lib.repository.entity.EntityCacheable;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository;

public class SmStateTimerRepository extends EntityRepository implements EntityCacheable<SmStateTimer> {
	
	
	// region Constructors
	public SmStateTimerRepository() {

		super(SmStateTimer.class);
	}

	public SmStateTimerRepository(Boolean cacheable) {

		super(SmTimer.class, cacheable);
	}

	public SmStateTimerRepository(Boolean cacheable, Long cacheRetentionTime) {

		super(SmStateTimer.class, cacheable, cacheRetentionTime);
	}
	// endregion Constructors
	
	// region getters
	public SmStateTimer get(Long stateMachineId, Long stateId, Long timerId, Boolean fillObject) {
		
		return (SmStateTimer) getFromCache(new String[] { String.valueOf(stateMachineId),  String.valueOf(stateId), String.valueOf(timerId)},
										   fillObject);
	}
	
	public List<SmStateTimer> getsByStateMachine(Long stateMachineId, Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(new String[] { String.valueOf(stateMachineId), null, null}, fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(be -> (SmStateTimer) be).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}
	
	public List<SmStateTimer> getsByState(Long stateId, Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(new String[] { null, String.valueOf(stateId), null}, fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(be -> (SmStateTimer) be).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}
	
	public List<SmStateTimer> getsByTimer(Long timerId, Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(new String[] { null, null, String.valueOf(timerId)}, fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(be -> (SmStateTimer) be).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}		


	@Override
	public List<SmStateTimer> getAll(Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(be -> (SmStateTimer) be).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}

	@Override
	public SmStateTimer put(SmStateTimer entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SmStateTimer> put(List<SmStateTimer> entities) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SmStateTimer del(SmStateTimer entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SmStateTimer> del(List<SmStateTimer> entities) {
		// TODO Auto-generated method stub
		return null;
	}

}
