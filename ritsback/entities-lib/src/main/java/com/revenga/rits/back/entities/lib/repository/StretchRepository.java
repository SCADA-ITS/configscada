package com.revenga.rits.back.entities.lib.repository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;

import com.revenga.rits.back.data.core.model.BasicElement;
import com.revenga.rits.back.data.core.model.Stretch;
import com.revenga.rits.back.entities.lib.repository.entity.EntityCacheable;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository;

public class StretchRepository extends EntityRepository implements EntityCacheable<Stretch> {

	// region Constructors
	public StretchRepository() {

		super(Stretch.class);
	}

	public StretchRepository(Boolean cacheable) {

		super(Stretch.class, cacheable);
	}

	public StretchRepository(Boolean cacheable, Long cacheRetentionTime) {

		super(Stretch.class, cacheable, cacheRetentionTime);
	}
	// endregion Constructors

	// region getters
	public Stretch get(Long id, Boolean fillObject) {

		return (Stretch) getFromCache(new String[] { String.valueOf(id) }, fillObject);
	}

	@Override
	public List<Stretch> getAll(Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(be -> (Stretch) be).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}

	public List<Stretch> getsByRoad(Long roadId, Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			List<Stretch> stretchs = basicElements.stream().map(be -> (Stretch) be).collect(Collectors.toList());

			return stretchs.stream().filter(e -> e.getRoadId().equals(roadId)).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}

	@Override
	public Stretch put(Stretch entity) {
		return (Stretch) putFromCache(entity);
	}

	@Override
	public List<Stretch> put(List<Stretch> entities) {
		List<BasicElement> basicElements = entities.stream().map(be -> (BasicElement) be).collect(Collectors.toList());

		basicElements = putsFromCache(basicElements);

		return basicElements.stream().map(be -> (Stretch) be).collect(Collectors.toList());
	}

	@Override
	public Stretch del(Stretch entity) {

		return (Stretch) delFromCache(entity);
	}

	@Override
	public List<Stretch> del(List<Stretch> entities) {
		List<BasicElement> basicElements = entities.stream().map(be -> (BasicElement) be).collect(Collectors.toList());

		basicElements = delFromCache(basicElements);

		return basicElements.stream().map(be -> (Stretch) be).collect(Collectors.toList());
	}

}
