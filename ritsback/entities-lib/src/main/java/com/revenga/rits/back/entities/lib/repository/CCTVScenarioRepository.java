package com.revenga.rits.back.entities.lib.repository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;

import com.revenga.rits.back.data.core.model.BasicElement;
import com.revenga.rits.back.data.core.model.CCTVScenario;
import com.revenga.rits.back.entities.lib.repository.entity.EntityCacheable;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository;

public class CCTVScenarioRepository extends EntityRepository implements EntityCacheable<CCTVScenario> {

	// region Constructors
	public CCTVScenarioRepository() {

		super(CCTVScenario.class);
	}

	public CCTVScenarioRepository(Boolean cacheable) {

		super(CCTVScenario.class, cacheable);
	}

	public CCTVScenarioRepository(Boolean cacheable, Long cacheRetentionTime) {

		super(CCTVScenario.class, cacheable, cacheRetentionTime);
	}
	// endregion Constructors

	// region getters
	public CCTVScenario get(Long id, Boolean fillObject) {

		return (CCTVScenario) getFromCache(new String[] { String.valueOf(id) }, fillObject);
	}

	@Override
	public List<CCTVScenario> getAll(Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(be -> (CCTVScenario) be).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}
	// endregion getters

	// region puts
	@Override
	public CCTVScenario put(CCTVScenario entity) {

		return (CCTVScenario) putFromCache(entity);
	}

	@Override
	public List<CCTVScenario> put(List<CCTVScenario> entities) {

		List<BasicElement> basicElements = entities.stream()
				.map(be -> (BasicElement) be).collect(Collectors.toList());

		basicElements = putsFromCache(basicElements);

		return basicElements.stream().map(be -> (CCTVScenario) be).collect(Collectors.toList());
	}
	// endregion puts

	// region dels
	@Override
	public CCTVScenario del(CCTVScenario entity) {

		return (CCTVScenario) delFromCache(entity);

	}

	@Override
	public List<CCTVScenario> del(List<CCTVScenario> entities) {

		List<BasicElement> basicElements = entities.stream()
				.map(be -> (BasicElement) be).collect(Collectors.toList());

		basicElements = delFromCache(basicElements);

		return basicElements.stream().map(be -> (CCTVScenario) be).collect(Collectors.toList());
	}
	// endregion dels

}
