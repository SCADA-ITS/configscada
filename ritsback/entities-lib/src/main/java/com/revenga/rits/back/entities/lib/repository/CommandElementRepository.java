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
import com.revenga.rits.back.data.core.model.CommandElement;
import com.revenga.rits.back.data.core.model.CommandElementValue;
import com.revenga.rits.back.entities.lib.exception.EntityDBException;
import com.revenga.rits.back.entities.lib.exception.EntityPersistenceException;
import com.revenga.rits.back.entities.lib.repository.entity.EntityCacheable;
import com.revenga.rits.back.entities.lib.repository.entity.EntityPersistent;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepositoryOptions;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class CommandElementRepository extends EntityRepository
		implements EntityCacheable<CommandElement>, EntityPersistent<CommandElement> {

	// region Constructors
	public CommandElementRepository() {

		super(CommandElement.class);
	}

	public CommandElementRepository(EntityRepositoryOptions options) {

		super(CommandElement.class, options);
	}
	
	public CommandElementRepository(Boolean cacheable) {

		super(CommandElement.class, cacheable);
	}

	public CommandElementRepository(Boolean cacheable, Long cacheRetentionTime) {

		super(CommandElement.class, cacheable, cacheRetentionTime);
	}

	public CommandElementRepository(Boolean cacheable, List<EntityRepository> repositories) {

		super(CommandElement.class, cacheable, repositories);
	}

	public CommandElementRepository(Boolean cacheable, Long cacheRetentionTime, List<EntityRepository> repositories) {

		super(CommandElement.class, cacheable, cacheRetentionTime, repositories);
	}

	// endregion Constructors

	// region getters
	public CommandElement get(Long commandElementTypeId, Long elementTypeId, Long id, Boolean fillObject) {

		return (CommandElement) getFromCache(new String[] { String.valueOf(commandElementTypeId),
				String.valueOf(elementTypeId), String.valueOf(id) }, fillObject);
	}

	@Override
	public List<CommandElement> getAll(Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(be -> (CommandElement) be).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}
	// endregion getters

	// region puts
	@Override
	public CommandElement put(CommandElement entity) {

		return (CommandElement) putFromCache(entity);
	}

	@Override
	public List<CommandElement> put(List<CommandElement> entities) {

		List<BasicElement> basicElements = entities.stream()
				.map(commandElementType -> (BasicElement) commandElementType).collect(Collectors.toList());

		basicElements = putsFromCache(basicElements);

		return basicElements.stream().map(be -> (CommandElement) be).collect(Collectors.toList());
	}
	// endregion puts

	// region dels
	@Override
	public CommandElement del(CommandElement entity) {

		return (CommandElement) delFromCache(entity);

	}

	@Override
	public List<CommandElement> del(List<CommandElement> entities) {

		List<BasicElement> basicElements = entities.stream()
				.map(commandElementType -> (BasicElement) commandElementType).collect(Collectors.toList());

		basicElements = delFromCache(basicElements);

		return basicElements.stream().map(be -> (CommandElement) be).collect(Collectors.toList());
	}
	// endregion dels

	@Override
	public CommandElement create(CommandElement commandElement) {
		try {

			return create(commandElement, null, null);

		} catch (EntityPersistenceException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			return null;
		}
	}

	@Override
	public CommandElement create(CommandElement commandElement, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {
		Connection conn = null;
		boolean closeConnection = false;
		boolean commit = false;
		conn = connection;
		CommandElement commandElementResult = null;

		try {

			if (conn == null) {

				conn = DataSourceConnection.getInstance().getConnection();
				conn.setAutoCommit(false);
				closeConnection = true;
				commit = true;
				persistOperations = new ArrayList<>();
			}

			// Insert commandElement in BBDD
			CommandElement commandElementToInserted = new CommandElement();
			commandElementToInserted.setId(commandElement.getId());
			commandElementToInserted.setCommandElementTypeId(commandElement.getCommandElementTypeId());
			commandElementToInserted.setElementTypeId(commandElement.getElementTypeId());
			commandElementToInserted.setElementId(commandElement.getElementId());

			commandElementToInserted = (CommandElement) getObjectDao().insert(conn, commandElementToInserted);

			if (commandElementToInserted == null) {

				throw new EntityDBException(EntityDBException.Operation.INSERT, commandElement);
			}

			
			persistOperations.add(new MutablePair<>(PersistOperation.PUT, commandElementToInserted));

			// Insert all CommandElementValues
			if (!CollectionUtils.isEmpty(commandElement.getCommandElementValues())
					&& !CollectionUtils.isEmpty(getRepositories())) {

				CommandElementValueRepository entityRepository = (CommandElementValueRepository) getRepositories()
						.stream().filter(repository -> repository.getClazz().equals(CommandElementValue.class))
						.findFirst().orElse(null);

				if (entityRepository != null) {

					for (CommandElementValue commandElementValue : commandElement.getCommandElementValues()) {

						commandElementValue.setCommandElementId(commandElementToInserted.getId());
						entityRepository.create(commandElementValue, conn, persistOperations);
					}
				}
			}

			if (commit) {
				conn.commit();

				persistOperations(persistOperations);
			}

			commandElementResult = commandElementToInserted;

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
			throw new EntityPersistenceException(EntityPersistenceException.Operation.CREATE, commandElement);

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

		return commandElementResult;

	}

	@Override
	public CommandElement clone(CommandElement entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CommandElement clone(CommandElement entity, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CommandElement update(CommandElement entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CommandElement update(CommandElement entity, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CommandElement delete(CommandElement commandElement) {
		try {

			return delete(commandElement, null, null);

		} catch (EntityPersistenceException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			return null;
		}
	}

	@Override
	public CommandElement delete(CommandElement commandElement, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {

		Connection conn = null;
		boolean closeConnection = false;
		boolean commit = false;
		conn = connection;
		CommandElement commandElementResult = null;
		CommandElement commandElementAux = null;
		List<CommandElementValue> commandElementValues = null;

		try {

			if (commandElement == null || !ObjectUtils.allNotNull(commandElement.getCommandElementTypeId(), commandElement.getElementTypeId(),
					commandElement.getId())) {

				throw new NullPointerException("CommandElement is null");
			}

			commandElementAux = get(commandElement.getCommandElementTypeId(), commandElement.getElementTypeId(),
					commandElement.getId(), false);

			if (commandElementAux == null) {

				throw new NullPointerException("CommandElement not found");
			}

			if (conn == null) {

				conn = DataSourceConnection.getInstance().getConnection();
				conn.setAutoCommit(false);
				closeConnection = true;
				commit = true;
				persistOperations = new ArrayList<>();
			}

			// Delete all commandElementValues
			if (!CollectionUtils.isEmpty(getRepositories())) {

				CommandElementValueRepository entityRepository = (CommandElementValueRepository) getRepositories()
						.stream().filter(repository -> repository.getClazz().equals(CommandElementValue.class))
						.findFirst().orElse(null);

				if (entityRepository != null) {

					commandElementValues = entityRepository.getsByCommandElement(
							commandElementAux.getCommandElementTypeId(), commandElementAux.getElementTypeId(),
							commandElementAux.getId(), false);

					if (!CollectionUtils.isEmpty(commandElementValues)) {

						for (CommandElementValue commandElementValue : commandElementValues) {

							entityRepository.delete(commandElementValue, conn, persistOperations);
						}
					}
				}
			}

			// Delete all commandElement

			CommandElement commandElementDeleted = null;

			commandElementDeleted = (CommandElement) getObjectDao().delete(conn, commandElementAux);

			if (commandElementDeleted == null) {

				throw new EntityDBException(EntityDBException.Operation.DELETE, commandElementAux);
			}

			persistOperations.add(new MutablePair<>(PersistOperation.DELETE, commandElementAux));

			if (commit) {

				conn.commit();

				// Update cache
				persistOperations(persistOperations);
			}

			commandElementResult = commandElementDeleted;

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
			throw new EntityPersistenceException(EntityPersistenceException.Operation.DELETE, commandElement);

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

		return commandElementResult;
	}

}
