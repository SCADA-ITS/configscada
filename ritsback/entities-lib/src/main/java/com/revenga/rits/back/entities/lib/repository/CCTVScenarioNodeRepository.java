package com.revenga.rits.back.entities.lib.repository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;

import com.revenga.rits.back.data.core.model.BasicElement;
import com.revenga.rits.back.data.core.model.CCTVScenarioNode;
import com.revenga.rits.back.entities.lib.repository.entity.EntityCacheable;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository;

public class CCTVScenarioNodeRepository extends EntityRepository implements EntityCacheable<CCTVScenarioNode> {

	// region Constructors
	public CCTVScenarioNodeRepository() {

		super(CCTVScenarioNode.class);
	}

	public CCTVScenarioNodeRepository(Boolean cacheable) {

		super(CCTVScenarioNode.class, cacheable);
	}

	public CCTVScenarioNodeRepository(Boolean cacheable, Long cacheRetentionTime) {

		super(CCTVScenarioNode.class, cacheable, cacheRetentionTime);
	}
	// endregion Constructors

	// region getters
	public CCTVScenarioNode get(Long id, Boolean fillObject) {

		return (CCTVScenarioNode) getFromCache(new String[] { String.valueOf(id) }, fillObject);
	}

	@Override
	public List<CCTVScenarioNode> getAll(Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(be -> (CCTVScenarioNode) be).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}
	// endregion getters

	@Override
	public CCTVScenarioNode put(CCTVScenarioNode entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CCTVScenarioNode> put(List<CCTVScenarioNode> entities) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CCTVScenarioNode del(CCTVScenarioNode entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CCTVScenarioNode> del(List<CCTVScenarioNode> entities) {
		// TODO Auto-generated method stub
		return null;
	}

}
