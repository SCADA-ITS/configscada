package com.revenga.rits.back.entities.lib.repository;

import java.sql.Connection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.tuple.Pair;

import com.revenga.rits.back.data.core.model.BasicElement;
import com.revenga.rits.back.data.core.model.CommandElementType;
import com.revenga.rits.back.entities.lib.exception.EntityPersistenceException;
import com.revenga.rits.back.entities.lib.repository.entity.EntityCacheable;
import com.revenga.rits.back.entities.lib.repository.entity.EntityPersistent;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository;

public class CommandElementTypeRepository extends EntityRepository implements EntityCacheable<CommandElementType>, EntityPersistent<CommandElementType> {

	// region Constructors
	public CommandElementTypeRepository() {

		super(CommandElementType.class);
	}

	public CommandElementTypeRepository(Boolean cacheable) {

		super(CommandElementType.class, cacheable);
	}

	public CommandElementTypeRepository(Boolean cacheable, Long cacheRetentionTime) {

		super(CommandElementType.class, cacheable, cacheRetentionTime);
	}
	// endregion Constructors

	// region getters
	public CommandElementType get(Long id, Long elementTypeId, Boolean fillObject) {

		return (CommandElementType) getFromCache(new String[] { String.valueOf(id), String.valueOf(elementTypeId) }, fillObject);
	}
	
	public List<CommandElementType> getsByElementType(Long elementTypeId, Boolean fillObject) {
		
		List<BasicElement> basicElements = getsFromCache(new String[] { null, String.valueOf(elementTypeId) }, fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(be -> (CommandElementType) be).collect(Collectors.toList());
		}

		return Collections.emptyList();

	}

	@Override
	public List<CommandElementType> getAll(Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(be -> (CommandElementType) be).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}
	// endregion getters

	// region puts
	@Override
	public CommandElementType put(CommandElementType entity) {

		return (CommandElementType) putFromCache(entity);
	}

	@Override
	public List<CommandElementType> put(List<CommandElementType> entities) {

		List<BasicElement> basicElements = entities.stream().map(commandElementType -> (BasicElement) commandElementType)
				.collect(Collectors.toList());

		basicElements = putsFromCache(basicElements);

		return basicElements.stream().map(be -> (CommandElementType) be).collect(Collectors.toList());
	}
	// endregion puts

	// region dels
	@Override
	public CommandElementType del(CommandElementType entity) {

		return (CommandElementType) delFromCache(entity);

	}

	@Override
	public List<CommandElementType> del(List<CommandElementType> entities) {

		List<BasicElement> basicElements = entities.stream().map(commandElementType -> (BasicElement) commandElementType)
				.collect(Collectors.toList());

		basicElements = delFromCache(basicElements);

		return basicElements.stream().map(be -> (CommandElementType) be).collect(Collectors.toList());
	}
	// endregion dels

	@Override
	public CommandElementType create(CommandElementType entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CommandElementType create(CommandElementType entity, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CommandElementType clone(CommandElementType entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CommandElementType clone(CommandElementType entity, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CommandElementType update(CommandElementType entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CommandElementType update(CommandElementType entity, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CommandElementType delete(CommandElementType entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CommandElementType delete(CommandElementType entity, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {
		// TODO Auto-generated method stub
		return null;
	}

}
