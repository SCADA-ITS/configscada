package com.revenga.rits.back.entities.lib.repository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;

import com.revenga.rits.back.data.core.model.BasicElement;
import com.revenga.rits.back.data.core.model.CCTVScenarioElement;
import com.revenga.rits.back.entities.lib.repository.entity.EntityCacheable;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository;

public class CCTVScenarioElementRepository extends EntityRepository implements EntityCacheable<CCTVScenarioElement> {

	// region Constructors
	public CCTVScenarioElementRepository() {

		super(CCTVScenarioElement.class);
	}

	public CCTVScenarioElementRepository(Boolean cacheable) {

		super(CCTVScenarioElement.class, cacheable);
	}

	public CCTVScenarioElementRepository(Boolean cacheable, Long cacheRetentionTime) {

		super(CCTVScenarioElement.class, cacheable, cacheRetentionTime);
	}
	// endregion Constructors

	// region getters
	/**
	 * 
	 * @param scenarioId
	 * @param elementTypeId
	 * @param elementId
	 * @param fillObject
	 * @return
	 */
	public CCTVScenarioElement get(Long scenarioId, Long elementTypeId, Long elementId, Boolean fillObject) {

		return (CCTVScenarioElement) getFromCache(new String[] { String.valueOf(scenarioId), String.valueOf(elementTypeId),	String.valueOf(elementId) }, 
												  fillObject);
	}
	
	/**
	 * 
	 * @param scenarioConfigId
	 * @param fillObject
	 * @return
	 */
	public List<CCTVScenarioElement> getsByScenario(Long scenarioConfigId, Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(new String[] { String.valueOf(scenarioConfigId) }, fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(be -> (CCTVScenarioElement) be).collect(Collectors.toList());
		}
		return Collections.emptyList();
	}

	/**
	 * 
	 * @param elementTypeId
	 * @param elementId
	 * @param fillObject
	 * @return
	 */
	public List<CCTVScenarioElement> getsByElement(Long elementTypeId, Long elementId, Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(new String[] { "elementTypeId", "elementId" },
				new String[] { String.valueOf(elementTypeId), String.valueOf(elementId) }, fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(be -> (CCTVScenarioElement) be).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}	

	@Override
	public List<CCTVScenarioElement> getAll(Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(be -> (CCTVScenarioElement) be).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}
	// endregion getters

	// region dels
	@Override
	public CCTVScenarioElement del(CCTVScenarioElement entity) {

		return (CCTVScenarioElement) delFromCache(entity);

	}

	@Override
	public List<CCTVScenarioElement> del(List<CCTVScenarioElement> entities) {

		List<BasicElement> basicElements = entities.stream()
				.map(be -> (BasicElement) be).collect(Collectors.toList());

		basicElements = delFromCache(basicElements);

		return basicElements.stream().map(be -> (CCTVScenarioElement) be).collect(Collectors.toList());
	}
	// endregion dels

	@Override
	public CCTVScenarioElement put(CCTVScenarioElement entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CCTVScenarioElement> put(List<CCTVScenarioElement> entities) {
		// TODO Auto-generated method stub
		return null;
	}

}
