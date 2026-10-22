package com.revenga.rits.back.entities.lib.repository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;

import com.revenga.rits.back.data.core.model.BasicElement;
import com.revenga.rits.back.data.core.model.SmCondition;
import com.revenga.rits.back.entities.lib.repository.entity.EntityCacheable;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository;

public class SmConditionRepository extends EntityRepository implements EntityCacheable<SmCondition> {
	
	
	// region Constructors
	public SmConditionRepository() {

		super(SmCondition.class);
	}

	public SmConditionRepository(Boolean cacheable) {

		super(SmCondition.class, cacheable);
	}

	public SmConditionRepository(Boolean cacheable, Long cacheRetentionTime) {

		super(SmCondition.class, cacheable, cacheRetentionTime);
	}
	// endregion Constructors
	
	// region getters
	public SmCondition get(Long smConditionId, Boolean fillObject) {
		
		return (SmCondition) getFromCache(new String[] { String.valueOf(smConditionId) },
				fillObject);
	}
	
	@Override
	public List<SmCondition> getAll(Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(be -> (SmCondition) be).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}

	@Override
	public SmCondition put(SmCondition entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SmCondition> put(List<SmCondition> entities) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SmCondition del(SmCondition entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SmCondition> del(List<SmCondition> entities) {
		// TODO Auto-generated method stub
		return null;
	}

}
