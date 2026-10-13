package com.revenga.rits.back.entities.lib.repository;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;

import com.revenga.rits.back.data.core.dao.manager.DataSourceConnection;
import com.revenga.rits.back.data.core.model.BasicElement;
import com.revenga.rits.back.data.core.model.CommandElementValue;
import com.revenga.rits.back.entities.lib.exception.EntityDBException;
import com.revenga.rits.back.entities.lib.exception.EntityPersistenceException;
import com.revenga.rits.back.entities.lib.repository.entity.EntityCacheable;
import com.revenga.rits.back.entities.lib.repository.entity.EntityPersistent;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepositoryOptions;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class CommandElementValueRepository extends EntityRepository
		implements EntityCacheable<CommandElementValue>, EntityPersistent<CommandElementValue> {

	// region Constructors
	public CommandElementValueRepository() {

		super(CommandElementValue.class);
	}

	public CommandElementValueRepository(EntityRepositoryOptions options) {

		super(CommandElementValue.class, options);
	}
	
	public CommandElementValueRepository(Boolean cacheable) {

		super(CommandElementValue.class, cacheable);
	}

	public CommandElementValueRepository(Boolean cacheable, Long cacheRetentionTime) {

		super(CommandElementValue.class, cacheable, cacheRetentionTime);
	}
	// endregion Constructors

	// region getters
	public CommandElementValue get(Long commandElementTypeId, Long elementTypeId, Long commandElementTypeParamId,
			Long commandElementId, Boolean fillObject) {

		return (CommandElementValue) getFromCache(
				new String[] { String.valueOf(commandElementTypeId), String.valueOf(elementTypeId),
						String.valueOf(commandElementTypeParamId), String.valueOf(commandElementId) },
				fillObject);
	}

	public List<CommandElementValue> getsByCommandElement(Long commandElementTypeId, Long elementTypeId,
			Long commandElementId, Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(new String[] { String.valueOf(commandElementTypeId),
				String.valueOf(elementTypeId), null, String.valueOf(commandElementId) }, fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(be -> (CommandElementValue) be).collect(Collectors.toList());
		}

		return Collections.emptyList();

	}

	@Override
	public List<CommandElementValue> getAll(Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(be -> (CommandElementValue) be).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}
	// endregion getters

	// region puts
	@Override
	public CommandElementValue put(CommandElementValue entity) {

		return (CommandElementValue) putFromCache(entity);
	}

	@Override
	public List<CommandElementValue> put(List<CommandElementValue> entities) {

		List<BasicElement> basicElements = entities.stream()
				.map(commandElementType -> (BasicElement) commandElementType).collect(Collectors.toList());

		basicElements = putsFromCache(basicElements);

		return basicElements.stream().map(be -> (CommandElementValue) be).collect(Collectors.toList());
	}
	// endregion puts

	// region dels
	@Override
	public CommandElementValue del(CommandElementValue entity) {

		return (CommandElementValue) delFromCache(entity);

	}

	@Override
	public List<CommandElementValue> del(List<CommandElementValue> entities) {

		List<BasicElement> basicElements = entities.stream()
				.map(commandElementType -> (BasicElement) commandElementType).collect(Collectors.toList());

		basicElements = delFromCache(basicElements);

		return basicElements.stream().map(be -> (CommandElementValue) be).collect(Collectors.toList());
	}
	// endregion dels

	@Override
	public CommandElementValue create(CommandElementValue commandElementValue) {
		try {

			return create(commandElementValue, null, null);

		} catch (EntityPersistenceException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			return null;
		}
	}

	@Override
	public CommandElementValue create(CommandElementValue commandElementValue, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {
		Connection conn = null;
		boolean closeConnection = false;
		boolean commit = false;
		conn = connection;
		CommandElementValue commandElementValueResult = null;

		try {

			if (conn == null) {

				conn = DataSourceConnection.getInstance().getConnection();
				conn.setAutoCommit(false);
				closeConnection = true;
				commit = true;
				persistOperations = new ArrayList<>();
			}

			// Insert CommandElementValue in BBDD
			CommandElementValue commandElementValueInserted = null;

			commandElementValueInserted = (CommandElementValue) getObjectDao().insert(conn, commandElementValue);

			if (commandElementValueInserted == null) {

				throw new EntityDBException(EntityDBException.Operation.INSERT, commandElementValue);
			}

			persistOperations.add(new MutablePair<>(PersistOperation.PUT, commandElementValueInserted));

			if (commit) {
				conn.commit();

				persistOperations(persistOperations);
				
				commandElementValueResult = get(commandElementValueInserted.getCommandElementTypeId(),
						commandElementValueInserted.getElementTypeId(),
						commandElementValueInserted.getCommandElementTypeParamId(),
						commandElementValueInserted.getCommandElementId(), false);
			}
			

		} catch (EntityDBException | SQLException | IOException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));

			if (closeConnection) {

				try {
					conn.rollback();

				} catch (SQLException e1) {

					log.error(e1.getMessage());
					log.debug(ExceptionUtils.getStackTrace(e1));
				}
			}
			throw new EntityPersistenceException(EntityPersistenceException.Operation.CREATE, commandElementValue);

		} finally {

			try {

				if (closeConnection) {

					conn.close();
				}

			} catch (SQLException e) {

				log.error(e.getMessage());
				log.debug(ExceptionUtils.getStackTrace(e));
			}
		}

		return commandElementValueResult;
	}

	@Override
	public CommandElementValue clone(CommandElementValue entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CommandElementValue clone(CommandElementValue entity, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CommandElementValue update(CommandElementValue entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CommandElementValue update(CommandElementValue entity, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CommandElementValue delete(CommandElementValue commandElementValue) {
		try {

			return delete(commandElementValue, null, null);

		} catch (EntityPersistenceException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			return null;
		}
	}

	@Override
	public CommandElementValue delete(CommandElementValue commandElementValue, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {

		Connection conn = null;
		boolean closeConnection = false;
		boolean commit = false;
		conn = connection;
		CommandElementValue commandElementValueResult = null;
		CommandElementValue commandElementValueAux = null;

		try {

			if (commandElementValue == null || !ObjectUtils.allNotNull(commandElementValue.getCommandElementTypeId(),
					commandElementValue.getElementTypeId(),commandElementValue.getCommandElementTypeParamId(), commandElementValue.getCommandElementId())) {

				throw new NullPointerException("CommandElementValue is null");
			}

			commandElementValueAux = get(commandElementValue.getCommandElementTypeId(),
					commandElementValue.getElementTypeId(), commandElementValue.getCommandElementTypeParamId(),
					commandElementValue.getCommandElementId(), false);

			if (commandElementValueAux == null) {

				throw new NullPointerException("CommandElementValue not found");
			}

			if (conn == null) {

				conn = DataSourceConnection.getInstance().getConnection();
				conn.setAutoCommit(false);
				closeConnection = true;
				commit = true;
				persistOperations = new ArrayList<>();
			}

			// Delete all commandElement

			CommandElementValue commandElementValueDeleted = null;

			commandElementValueDeleted = (CommandElementValue) getObjectDao().delete(conn, commandElementValueAux);

			if (commandElementValueDeleted == null) {

				throw new EntityDBException(EntityDBException.Operation.DELETE, commandElementValueAux);
			}

			persistOperations.add(new MutablePair<>(PersistOperation.DELETE, commandElementValueAux));

			if (commit) {

				conn.commit();

				// Update cache
				persistOperations(persistOperations);
			}

			commandElementValueResult = commandElementValueDeleted;

		} catch (EntityDBException | SQLException | NullPointerException | IOException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));

			if (closeConnection) {

				try {
					conn.rollback();

				} catch (SQLException e1) {

					log.error(e1.getMessage());
					log.debug(ExceptionUtils.getStackTrace(e1));
				}
			}
			throw new EntityPersistenceException(EntityPersistenceException.Operation.DELETE, commandElementValue);

		} finally {

			try {

				if (closeConnection) {

					conn.close();
				}

			} catch (SQLException e) {

				log.error(e.getMessage());
				log.debug(ExceptionUtils.getStackTrace(e));
			}
		}

		return commandElementValueResult;
	}

}
