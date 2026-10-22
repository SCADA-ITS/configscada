package com.revenga.rits.back.entities.lib.repository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;

import com.revenga.rits.back.data.core.model.BasicElement;
import com.revenga.rits.back.data.core.model.DataType;
import com.revenga.rits.back.entities.lib.repository.entity.EntityCacheable;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository;

public class DataTypeRepository extends EntityRepository implements EntityCacheable<DataType> {

	// region Constructors
	public DataTypeRepository() {

		super(DataType.class);
	}

	public DataTypeRepository(Boolean cacheable) {

		super(DataType.class, cacheable);
	}

	public DataTypeRepository(Boolean cacheable, Long cacheRetentionTime) {

		super(DataType.class, cacheable, cacheRetentionTime);
	}
	// endregion Constructors

	// region getters
	public DataType get(Long id, Boolean fillObject) {

		return (DataType) getFromCache(new String[] { String.valueOf(id) }, fillObject);
	}

	@Override
	public List<DataType> getAll(Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(be -> (DataType) be).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}

	@Override
	public DataType put(DataType entity) {
		return (DataType) putFromCache(entity);
	}

	@Override
	public List<DataType> put(List<DataType> entities) {
		List<BasicElement> basicElements = entities.stream().map(be -> (BasicElement) be).collect(Collectors.toList());

		basicElements = putsFromCache(basicElements);

		return basicElements.stream().map(be -> (DataType) be).collect(Collectors.toList());
	}

	@Override
	public DataType del(DataType entity) {

		return (DataType) delFromCache(entity);
	}

	@Override
	public List<DataType> del(List<DataType> entities) {
		List<BasicElement> basicElements = entities.stream().map(be -> (BasicElement) be).collect(Collectors.toList());

		basicElements = delFromCache(basicElements);

		return basicElements.stream().map(be -> (DataType) be).collect(Collectors.toList());
	}
}
