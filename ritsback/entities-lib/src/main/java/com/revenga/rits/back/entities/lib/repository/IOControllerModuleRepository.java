package com.revenga.rits.back.entities.lib.repository;

import java.sql.Connection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.tuple.Pair;

import com.revenga.rits.back.data.core.model.BasicElement;
import com.revenga.rits.back.data.core.model.IOControllerModule;
import com.revenga.rits.back.entities.lib.repository.entity.EntityCacheable;
import com.revenga.rits.back.entities.lib.repository.entity.EntityPersistent;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository.PersistOperation;

public class IOControllerModuleRepository extends EntityRepository implements EntityCacheable<IOControllerModule>, EntityPersistent<IOControllerModule> {

	// region Constructors
	public IOControllerModuleRepository() {

		super(IOControllerModule.class);
	}

	public IOControllerModuleRepository(Boolean cacheable) {

		super(IOControllerModule.class, cacheable);
	}

	public IOControllerModuleRepository(Boolean cacheable, Long cacheRetentionTime) {

		super(IOControllerModule.class, cacheable, cacheRetentionTime);
	}
	// endregion Constructors

	// region getters
	public IOControllerModule get(Long moduleTypeId, Long id, Boolean fillObject) {

		return (IOControllerModule) getFromCache(new String[] { String.valueOf(moduleTypeId), String.valueOf(id) }, fillObject);
	}

	public List<IOControllerModule> gets(Long moduleTypeId, Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(new String[] { String.valueOf(moduleTypeId), null }, fillObject);
		
		if (!CollectionUtils.isEmpty(basicElements)) {
			
			return basicElements.stream()
					.map(be -> (IOControllerModule) be).collect(Collectors.toList());
		}
		
		return Collections.emptyList();
	}

	@Override
	public List<IOControllerModule> getAll(Boolean fillObject) {
		
		List<BasicElement> basicElements = getsFromCache(fillObject);
		
		if (!CollectionUtils.isEmpty(basicElements)) {
			
			return basicElements.stream()
					.map(be -> (IOControllerModule) be).collect(Collectors.toList());
		}
		
		return Collections.emptyList();
	}
	// endregion getters

	// region puts
	@Override
	public IOControllerModule put(IOControllerModule entity) {

		return (IOControllerModule) putFromCache(entity);
	}

	@Override
	public List<IOControllerModule> put(List<IOControllerModule> entities) {

		List<BasicElement> basicElements = entities.stream().map(module -> (BasicElement) module)
				.collect(Collectors.toList());

		basicElements = putsFromCache(basicElements);

		return basicElements.stream().map(be -> (IOControllerModule) be).collect(Collectors.toList());
	}
	// endregion puts

	// region dels
	@Override
	public IOControllerModule del(IOControllerModule entity) {

		return (IOControllerModule) delFromCache(entity);

	}

	@Override
	public List<IOControllerModule> del(List<IOControllerModule> entities) {

		List<BasicElement> basicElements = entities.stream().map(module -> (BasicElement) module)
				.collect(Collectors.toList());

		basicElements = delFromCache(basicElements);

		return basicElements.stream().map(be -> (IOControllerModule) be).collect(Collectors.toList());
	}
	// endregion dels

	public IOControllerModule create(IOControllerModule entity) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public IOControllerModule clone(IOControllerModule entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IOControllerModule create(IOControllerModule entity, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IOControllerModule clone(IOControllerModule entity, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IOControllerModule update(IOControllerModule entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IOControllerModule update(IOControllerModule entity, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IOControllerModule delete(IOControllerModule entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IOControllerModule delete(IOControllerModule entity, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) {
		// TODO Auto-generated method stub
		return null;
	}

}
