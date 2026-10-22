package com.revenga.rits.back.entities.lib.repository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;

import com.revenga.rits.back.data.core.model.BasicElement;
import com.revenga.rits.back.data.core.model.SmTimer;
import com.revenga.rits.back.entities.lib.repository.entity.EntityCacheable;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository;

public class SmTimerRepository extends EntityRepository implements EntityCacheable<SmTimer> {
	
	
	// region Constructors
	public SmTimerRepository() {

		super(SmTimer.class);
	}

	public SmTimerRepository(Boolean cacheable) {

		super(SmTimer.class, cacheable);
	}

	public SmTimerRepository(Boolean cacheable, Long cacheRetentionTime) {

		super(SmTimer.class, cacheable, cacheRetentionTime);
	}
	// endregion Constructors
	
	// region getters
	public SmTimer get(Long smTimerId, Boolean fillObject) {
		
		return (SmTimer) getFromCache(new String[] { String.valueOf(smTimerId) },
				fillObject);
	}
	
	@Override
	public List<SmTimer> getAll(Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(be -> (SmTimer) be).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}

	@Override
	public SmTimer put(SmTimer entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SmTimer> put(List<SmTimer> entities) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SmTimer del(SmTimer entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SmTimer> del(List<SmTimer> entities) {
		// TODO Auto-generated method stub
		return null;
	}

}
