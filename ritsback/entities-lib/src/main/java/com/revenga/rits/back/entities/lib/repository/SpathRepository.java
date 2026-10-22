package com.revenga.rits.back.entities.lib.repository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;

import com.revenga.rits.back.data.core.model.BasicElement;
import com.revenga.rits.back.data.core.model.Spath;
import com.revenga.rits.back.entities.lib.repository.entity.EntityCacheable;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository;

public class SpathRepository extends EntityRepository implements EntityCacheable<Spath> {

	// region Constructors
	public SpathRepository() {

		super(Spath.class);
	}

	public SpathRepository(Boolean cacheable) {

		super(Spath.class, cacheable);
	}

	public SpathRepository(Boolean cacheable, Long cacheRetentionTime) {

		super(Spath.class, cacheable, cacheRetentionTime);
	}
	// endregion Constructors

	// region getters
	public Spath get(Long id, Boolean fillObject) {

		return (Spath) getFromCache(new String[] { String.valueOf(id) }, fillObject);
	}

	@Override
	public List<Spath> getAll(Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(be -> (Spath) be).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}

	@Override
	public Spath put(Spath entity) {
		return (Spath) putFromCache(entity);
	}

	@Override
	public List<Spath> put(List<Spath> entities) {
		List<BasicElement> basicElements = entities.stream().map(be -> (BasicElement) be).collect(Collectors.toList());

		basicElements = putsFromCache(basicElements);

		return basicElements.stream().map(be -> (Spath) be).collect(Collectors.toList());
	}

	@Override
	public Spath del(Spath entity) {

		return (Spath) delFromCache(entity);
	}

	@Override
	public List<Spath> del(List<Spath> entities) {
		List<BasicElement> basicElements = entities.stream().map(be -> (BasicElement) be).collect(Collectors.toList());

		basicElements = delFromCache(basicElements);

		return basicElements.stream().map(be -> (Spath) be).collect(Collectors.toList());
	}

}
