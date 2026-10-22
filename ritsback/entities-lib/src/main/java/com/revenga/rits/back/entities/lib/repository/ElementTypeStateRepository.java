package com.revenga.rits.back.entities.lib.repository;

import java.sql.Connection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.tuple.Pair;

import com.revenga.rits.back.data.core.model.BasicElement;
import com.revenga.rits.back.data.core.model.ElementTypeState;
import com.revenga.rits.back.entities.lib.exception.EntityPersistenceException;
import com.revenga.rits.back.entities.lib.repository.entity.EntityCacheable;
import com.revenga.rits.back.entities.lib.repository.entity.EntityPersistent;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository.PersistOperation;

public class ElementTypeStateRepository extends EntityRepository implements EntityCacheable<ElementTypeState>, EntityPersistent<ElementTypeState> {

	// region Constructors
	public ElementTypeStateRepository() {

		super(ElementTypeState.class);
	}

	public ElementTypeStateRepository(Boolean cacheable) {

		super(ElementTypeState.class, cacheable);
	}

	public ElementTypeStateRepository(Boolean cacheable, Long cacheRetentionTime) {

		super(ElementTypeState.class, cacheable, cacheRetentionTime);
	}
	// endregion Constructors

	// region getterselementTypeid
	public ElementTypeState get(Long elementTypeid, Long stateId, Boolean fillObject) {

		return (ElementTypeState) getFromCache(new String[] { String.valueOf(elementTypeid), String.valueOf(stateId) }, fillObject);
	}
	
	public List<ElementTypeState> getsByElementType(Long elementTypeId, Boolean fillObject) {
		
		List<BasicElement> basicElements = getsFromCache(new String[] { String.valueOf(elementTypeId), null }, fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(be -> (ElementTypeState) be).collect(Collectors.toList());
		}

		return Collections.emptyList();

	}

	@Override
	public List<ElementTypeState> getAll(Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(be -> (ElementTypeState) be).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}
	// endregion getters

	// region puts
	@Override
	public ElementTypeState put(ElementTypeState entity) {

		return (ElementTypeState) putFromCache(entity);
	}

	@Override
	public List<ElementTypeState> put(List<ElementTypeState> entities) {

		List<BasicElement> basicElements = entities.stream().map(elementTypeState -> (BasicElement) elementTypeState)
				.collect(Collectors.toList());

		basicElements = putsFromCache(basicElements);

		return basicElements.stream().map(be -> (ElementTypeState) be).collect(Collectors.toList());
	}
	// endregion puts

	// region dels
	@Override
	public ElementTypeState del(ElementTypeState entity) {

		return (ElementTypeState) delFromCache(entity);

	}

	@Override
	public List<ElementTypeState> del(List<ElementTypeState> entities) {

		List<BasicElement> basicElements = entities.stream().map(elementTypeState -> (BasicElement) elementTypeState)
				.collect(Collectors.toList());

		basicElements = delFromCache(basicElements);

		return basicElements.stream().map(be -> (ElementTypeState) be).collect(Collectors.toList());
	}
	// endregion dels

	@Override
	public ElementTypeState create(ElementTypeState entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ElementTypeState create(ElementTypeState entity, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ElementTypeState clone(ElementTypeState entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ElementTypeState clone(ElementTypeState entity, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ElementTypeState update(ElementTypeState entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ElementTypeState update(ElementTypeState entity, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ElementTypeState delete(ElementTypeState entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ElementTypeState delete(ElementTypeState entity, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {
		// TODO Auto-generated method stub
		return null;
	}

}
