package com.revenga.rits.back.entities.lib.repository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;

import com.revenga.rits.back.data.core.model.BasicElement;
import com.revenga.rits.back.data.core.model.CCTVScenarioHierarchy;
import com.revenga.rits.back.entities.lib.repository.entity.EntityCacheable;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository;

public class CCTVScenarioHierarchyRepository extends EntityRepository implements EntityCacheable<CCTVScenarioHierarchy> {

	// region Constructors
	public CCTVScenarioHierarchyRepository() {

		super(CCTVScenarioHierarchy.class);
	}

	public CCTVScenarioHierarchyRepository(Boolean cacheable) {

		super(CCTVScenarioHierarchy.class, cacheable);
	}

	public CCTVScenarioHierarchyRepository(Boolean cacheable, Long cacheRetentionTime) {

		super(CCTVScenarioHierarchy.class, cacheable, cacheRetentionTime);
	}
	// endregion Constructors

	// region getters
	public CCTVScenarioHierarchy get(Long nodeParentId, Long nodeChildId, Boolean fillObject) {

		return (CCTVScenarioHierarchy) getFromCache(new String[] { String.valueOf(nodeParentId), String.valueOf(nodeChildId)}, fillObject);
	}

	@Override
	public List<CCTVScenarioHierarchy> getAll(Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(be -> (CCTVScenarioHierarchy) be).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}
	// endregion getters

	@Override
	public CCTVScenarioHierarchy put(CCTVScenarioHierarchy entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CCTVScenarioHierarchy> put(List<CCTVScenarioHierarchy> entities) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CCTVScenarioHierarchy del(CCTVScenarioHierarchy entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CCTVScenarioHierarchy> del(List<CCTVScenarioHierarchy> entities) {
		// TODO Auto-generated method stub
		return null;
	}

}
