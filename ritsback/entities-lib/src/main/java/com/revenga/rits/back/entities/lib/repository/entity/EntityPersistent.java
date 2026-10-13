package com.revenga.rits.back.entities.lib.repository.entity;

import java.sql.Connection;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import com.revenga.rits.back.data.core.model.BasicElement;
import com.revenga.rits.back.entities.lib.exception.EntityPersistenceException;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository.PersistOperation;

public interface EntityPersistent <T> {

	T create(T entity);
	T create(T entity, Connection connection, List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException;
	T clone(T entity);
	T clone(T entity, Connection connection, List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException;
	T update(T entity);
	T update(T entity, Connection connection, List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException;
	T delete(T entity);
	T delete(T entity, Connection connection, List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException;
}
