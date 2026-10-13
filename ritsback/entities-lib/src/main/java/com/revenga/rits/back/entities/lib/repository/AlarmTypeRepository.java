package com.revenga.rits.back.entities.lib.repository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;

import com.revenga.rits.back.data.core.model.AlarmType;
import com.revenga.rits.back.data.core.model.BasicElement;
import com.revenga.rits.back.entities.lib.repository.entity.EntityCacheable;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository;

public class AlarmTypeRepository extends EntityRepository implements EntityCacheable<AlarmType> {

	// region Constructors
	public AlarmTypeRepository() {

		super(AlarmType.class);
	}

	public AlarmTypeRepository(Boolean cacheable) {

		super(AlarmType.class, cacheable);
	}

	public AlarmTypeRepository(Boolean cacheable, Long cacheRetentionTime) {

		super(AlarmType.class, cacheable, cacheRetentionTime);
	}
	// endregion Constructors

	// region getters
	public AlarmType get(Long id, Boolean fillObject) {

		return (AlarmType) getFromCache(new String[] { String.valueOf(id) }, fillObject);
	}

	@Override
	public List<AlarmType> getAll(Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(be -> (AlarmType) be).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}

	@Override
	public AlarmType put(AlarmType entity) {
		return (AlarmType) putFromCache(entity);
	}

	@Override
	public List<AlarmType> put(List<AlarmType> entities) {
		List<BasicElement> basicElements = entities.stream().map(be -> (BasicElement) be).collect(Collectors.toList());

		basicElements = putsFromCache(basicElements);

		return basicElements.stream().map(be -> (AlarmType) be).collect(Collectors.toList());
	}

	@Override
	public AlarmType del(AlarmType entity) {

		return (AlarmType) delFromCache(entity);
	}

	@Override
	public List<AlarmType> del(List<AlarmType> entities) {
		List<BasicElement> basicElements = entities.stream().map(be -> (BasicElement) be).collect(Collectors.toList());

		basicElements = delFromCache(basicElements);

		return basicElements.stream().map(be -> (AlarmType) be).collect(Collectors.toList());
	}

//	@Override
//	public AlarmType expire(AlarmType entity, int expire) {
//
//		throw new NotImplementedException(
//				"Method " + Thread.currentThread().getStackTrace()[1].getMethodName() + " without implementation");
//	}
//
//	@Override
//	public List<AlarmType> expire(List<AlarmType> entities, int expire) {
//
//		throw new NotImplementedException(
//				"Method " + Thread.currentThread().getStackTrace()[1].getMethodName() + " without implementation");
//	}

}
