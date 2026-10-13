package com.revenga.rits.back.entities.lib.repository;

import java.sql.Connection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.tuple.Pair;

import com.revenga.rits.back.data.core.model.BasicElement;
import com.revenga.rits.back.data.core.model.IOControllerModuleValue;
import com.revenga.rits.back.entities.lib.repository.entity.EntityCacheable;
import com.revenga.rits.back.entities.lib.repository.entity.EntityPersistent;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository.PersistOperation;

public class IOControllerModuleValueRepository extends EntityRepository implements EntityCacheable<IOControllerModuleValue>, EntityPersistent<IOControllerModuleValue> {

	// region Constructors
	public IOControllerModuleValueRepository() {

		super(IOControllerModuleValue.class);
	}

	public IOControllerModuleValueRepository(Boolean cacheable) {

		super(IOControllerModuleValue.class, cacheable);
	}

	public IOControllerModuleValueRepository(Boolean cacheable, Long cacheRetentionTime) {

		super(IOControllerModuleValue.class, cacheable, cacheRetentionTime);
	}
	// endregion Constructors

	// region getters
	public IOControllerModuleValue get(Long moduleTypeId, Long moduleId, Long moduleTypeParamId, Boolean fillObject) {

		return (IOControllerModuleValue) getFromCache(new String[] { String.valueOf(moduleTypeId), String.valueOf(moduleId),
				String.valueOf(moduleTypeParamId) }, fillObject);
	}

	public List<IOControllerModuleValue> gets(Long moduleTypeId, Long moduleId, Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(
				new String[] { String.valueOf(moduleTypeId), String.valueOf(moduleId), null }, fillObject);
		
		if (!CollectionUtils.isEmpty(basicElements)) {
			
			return basicElements.stream()
					.map(be -> (IOControllerModuleValue) be).collect(Collectors.toList());
		}
		
		return Collections.emptyList();
	}

	@Override
	public List<IOControllerModuleValue> getAll(Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(fillObject);
		
		if (!CollectionUtils.isEmpty(basicElements)) {
			
			return basicElements.stream()
					.map(be -> (IOControllerModuleValue) be).collect(Collectors.toList());
		}
		
		return Collections.emptyList();
	}
	// endregion getters

	// region puts
	@Override
	public IOControllerModuleValue put(IOControllerModuleValue entity) {

		return (IOControllerModuleValue) putFromCache(entity);
	}

	@Override
	public List<IOControllerModuleValue> put(List<IOControllerModuleValue> entities) {

		List<BasicElement> basicElements = entities.stream().map(elementControllerValue -> (BasicElement) elementControllerValue)
				.collect(Collectors.toList());

		basicElements = putsFromCache(basicElements);

		return basicElements.stream().map(be -> (IOControllerModuleValue) be).collect(Collectors.toList());
	}
	// endregion puts

	// region dels
	@Override
	public IOControllerModuleValue del(IOControllerModuleValue entity) {

		return (IOControllerModuleValue) delFromCache(entity);

	}

	@Override
	public List<IOControllerModuleValue> del(List<IOControllerModuleValue> entities) {

		List<BasicElement> basicElements = entities.stream().map(elementControllerValue -> (BasicElement) elementControllerValue)
				.collect(Collectors.toList());

		basicElements = delFromCache(basicElements);

		return basicElements.stream().map(be -> (IOControllerModuleValue) be).collect(Collectors.toList());
	}
	// endregion dels

	@Override
	public IOControllerModuleValue create(IOControllerModuleValue entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IOControllerModuleValue create(IOControllerModuleValue entity, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IOControllerModuleValue clone(IOControllerModuleValue entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IOControllerModuleValue clone(IOControllerModuleValue entity, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IOControllerModuleValue update(IOControllerModuleValue entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IOControllerModuleValue update(IOControllerModuleValue entity, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IOControllerModuleValue delete(IOControllerModuleValue entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IOControllerModuleValue delete(IOControllerModuleValue entity, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) {
		// TODO Auto-generated method stub
		return null;
	}

}
