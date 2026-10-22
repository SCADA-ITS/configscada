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
import com.revenga.rits.back.data.core.model.PlanOrderCommandElement;
import com.revenga.rits.back.entities.lib.exception.EntityDBException;
import com.revenga.rits.back.entities.lib.exception.EntityPersistenceException;
import com.revenga.rits.back.entities.lib.repository.entity.EntityCacheable;
import com.revenga.rits.back.entities.lib.repository.entity.EntityPersistent;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository.PersistOperation;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepositoryOptions;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class PlanOrderCommandElementRepository extends EntityRepository
		implements EntityCacheable<PlanOrderCommandElement>, EntityPersistent<PlanOrderCommandElement> {

	// region Constructors
	public PlanOrderCommandElementRepository() {

		super(PlanOrderCommandElement.class);
	}

	public PlanOrderCommandElementRepository(EntityRepositoryOptions options) {

		super(PlanOrderCommandElement.class, options);
	}
	
	public PlanOrderCommandElementRepository(Boolean cacheable) {

		super(PlanOrderCommandElement.class, cacheable);
	}

	public PlanOrderCommandElementRepository(Boolean cacheable, Long cacheRetentionTime) {

		super(PlanOrderCommandElement.class, cacheable, cacheRetentionTime);
	}

	public PlanOrderCommandElementRepository(Boolean cacheable, List<EntityRepository> repositories) {

		super(PlanOrderCommandElement.class, cacheable, repositories);
	}

	public PlanOrderCommandElementRepository(Boolean cacheable, Long cacheRetentionTime,
			List<EntityRepository> repositories) {

		super(PlanOrderCommandElement.class, cacheable, cacheRetentionTime, repositories);
	}

	// endregion Constructors

	// region getters
	public PlanOrderCommandElement get(Long planId, Long planOrderId, Boolean fillObject) {

		return (PlanOrderCommandElement) getFromCache(
				new String[] { String.valueOf(planId), String.valueOf(planOrderId) }, fillObject);
	}

	@Override
	public List<PlanOrderCommandElement> getAll(Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(be -> (PlanOrderCommandElement) be).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}
	// endregion getters

	// region puts
	@Override
	public PlanOrderCommandElement put(PlanOrderCommandElement entity) {

		return (PlanOrderCommandElement) putFromCache(entity);
	}

	@Override
	public List<PlanOrderCommandElement> put(List<PlanOrderCommandElement> entities) {

		List<BasicElement> basicElements = entities.stream()
				.map(commandElementType -> (BasicElement) commandElementType).collect(Collectors.toList());

		basicElements = putsFromCache(basicElements);

		return basicElements.stream().map(be -> (PlanOrderCommandElement) be).collect(Collectors.toList());
	}
	// endregion puts

	// region dels
	@Override
	public PlanOrderCommandElement del(PlanOrderCommandElement entity) {

		return (PlanOrderCommandElement) delFromCache(entity);

	}

	@Override
	public List<PlanOrderCommandElement> del(List<PlanOrderCommandElement> entities) {

		List<BasicElement> basicElements = entities.stream()
				.map(commandElementType -> (BasicElement) commandElementType).collect(Collectors.toList());

		basicElements = delFromCache(basicElements);

		return basicElements.stream().map(be -> (PlanOrderCommandElement) be).collect(Collectors.toList());
	}
	// endregion dels

	@Override
	public PlanOrderCommandElement create(PlanOrderCommandElement planOrderCommandElement) {
		try {

			return create(planOrderCommandElement, null, null);

		} catch (EntityPersistenceException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			return null;
		}
	}

	@Override
	public PlanOrderCommandElement create(PlanOrderCommandElement planOrderCommandElement, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {

		Connection conn = null;
		boolean closeConnection = false;
		boolean commit = false;
		conn = connection;
		PlanOrderCommandElement planOrderCommandElementResult = null;
		CommandElement commandElement = null;

		try {

			if (conn == null) {

				conn = DataSourceConnection.getInstance().getConnection();
				conn.setAutoCommit(false);
				closeConnection = true;
				commit = true;
				persistOperations = new ArrayList<>();
			}

			if (planOrderCommandElement.getCommandElement() != null && !CollectionUtils.isEmpty(getRepositories())) {

				CommandElementRepository entityRepository = (CommandElementRepository) getRepositories().stream()
						.filter(repository -> repository.getClazz().equals(CommandElement.class)).findFirst()
						.orElse(null);

				if (entityRepository != null) {

					commandElement = entityRepository.create(planOrderCommandElement.getCommandElement(), conn,
							persistOperations);

					if (commandElement != null && commandElement.getId() != null) {

						planOrderCommandElement.setCommandElementId(commandElement.getId());

						// Insert planOrderCommandElement in BBDD
						PlanOrderCommandElement planOrderCommandElementToInserted = new PlanOrderCommandElement();
						planOrderCommandElementToInserted
								.setCommandElementTypeId(planOrderCommandElement.getCommandElementTypeId());
						planOrderCommandElementToInserted.setElementTypeId(planOrderCommandElement.getElementTypeId());
						planOrderCommandElementToInserted
								.setCommandElementId(planOrderCommandElement.getCommandElementId());
						planOrderCommandElementToInserted.setPlanId(planOrderCommandElement.getPlanId());
						planOrderCommandElementToInserted.setPlanOrderId(planOrderCommandElement.getPlanOrderId());

						planOrderCommandElementToInserted = (PlanOrderCommandElement) getObjectDao().insert(conn,
								planOrderCommandElementToInserted);

						if (planOrderCommandElementToInserted == null) {

							throw new EntityDBException(EntityDBException.Operation.INSERT, planOrderCommandElement);
						}

						persistOperations
								.add(new MutablePair<>(PersistOperation.PUT, planOrderCommandElementToInserted));

						if (commit) {
							conn.commit();

							persistOperations(persistOperations);
						}

						planOrderCommandElementResult = get(planOrderCommandElementToInserted.getPlanId(),
								planOrderCommandElementToInserted.getPlanOrderId(), false);
					}
				}

			}

		} catch (EntityDBException | SQLException | IOException  e) {

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
			throw new EntityPersistenceException(EntityPersistenceException.Operation.CREATE, planOrderCommandElement);

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

		return planOrderCommandElementResult;

	}

	@Override
	public PlanOrderCommandElement clone(PlanOrderCommandElement entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PlanOrderCommandElement clone(PlanOrderCommandElement entity, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PlanOrderCommandElement update(PlanOrderCommandElement planOrderCommandElement) {
		try {

			return update(planOrderCommandElement, null, null);

		} catch (EntityPersistenceException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			return null;
		}
	}

	@Override
	public PlanOrderCommandElement delete(PlanOrderCommandElement planOrderCommandElement) {
		try {

			return delete(planOrderCommandElement, null, null);

		} catch (EntityPersistenceException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			return null;
		}
	}

	@Override
	public PlanOrderCommandElement delete(PlanOrderCommandElement planOrderCommandElement, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {
		
		Connection conn = null;
		boolean closeConnection = false;
		boolean commit = false;
		conn = connection;
		PlanOrderCommandElement planOrderCommandElementResult = null;
		PlanOrderCommandElement planOrderCommandElementAux = null;
		CommandElement commandElement = null;

		try {

			if (planOrderCommandElement == null || !ObjectUtils.allNotNull(planOrderCommandElement.getPlanId(),
					planOrderCommandElement.getPlanOrderId())) {

				throw new NullPointerException("PlanOrderCommandElement is null");
			}

			planOrderCommandElementAux = get(planOrderCommandElement.getPlanId(),
					planOrderCommandElement.getPlanOrderId(), false);

			if (planOrderCommandElementAux == null) {

				throw new NullPointerException("PlanOrderCommandElement not found");
			}

			if (conn == null) {

				conn = DataSourceConnection.getInstance().getConnection();
				conn.setAutoCommit(false);
				closeConnection = true;
				commit = true;
				persistOperations = new ArrayList<>();
			}

			// Delete all planOrdercommandElement

			PlanOrderCommandElement planOrderCommandElementDeleted = null;

			planOrderCommandElementDeleted = (PlanOrderCommandElement) getObjectDao().delete(conn,
					planOrderCommandElementAux);

			if (planOrderCommandElementDeleted == null) {

				throw new EntityDBException(EntityDBException.Operation.DELETE, planOrderCommandElementAux);
			}

			persistOperations.add(new MutablePair<>(PersistOperation.DELETE, planOrderCommandElementAux));

			if (ObjectUtils.allNotNull(planOrderCommandElementAux.getCommandElementTypeId(),
					planOrderCommandElementAux.getElementTypeId(), planOrderCommandElementAux.getCommandElementId())
					&& !CollectionUtils.isEmpty(getRepositories())) {

				CommandElementRepository entityRepository = (CommandElementRepository) getRepositories().stream()
						.filter(repository -> repository.getClazz().equals(CommandElement.class)).findFirst()
						.orElse(null);

				if (entityRepository != null) {

					commandElement = entityRepository.get(planOrderCommandElementAux.getCommandElementTypeId(),
							planOrderCommandElementAux.getElementTypeId(),
							planOrderCommandElementAux.getCommandElementId(), false);

					entityRepository.delete(commandElement, conn, persistOperations);

				}
			}

			if (commit) {

				conn.commit();

				// Update cache
				persistOperations(persistOperations);
			}

			planOrderCommandElementResult = planOrderCommandElementDeleted;

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
			throw new EntityPersistenceException(EntityPersistenceException.Operation.DELETE, planOrderCommandElement);

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

		return planOrderCommandElementResult;
	}

	@Override
	public PlanOrderCommandElement update(PlanOrderCommandElement entity, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {
		// TODO Auto-generated method stub
		return null;
	}

}
