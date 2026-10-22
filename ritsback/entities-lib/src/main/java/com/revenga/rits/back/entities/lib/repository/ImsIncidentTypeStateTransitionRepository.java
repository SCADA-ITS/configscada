package com.revenga.rits.back.entities.lib.repository;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;

import com.revenga.rits.back.data.core.dao.manager.DataSourceConnection;
import com.revenga.rits.back.data.core.model.BasicElement;
import com.revenga.rits.back.data.core.model.ImsIncidentTypeStateTransition;
import com.revenga.rits.back.entities.lib.exception.EntityDBException;
import com.revenga.rits.back.entities.lib.exception.EntityPersistenceException;
import com.revenga.rits.back.entities.lib.repository.entity.EntityCacheable;
import com.revenga.rits.back.entities.lib.repository.entity.EntityPersistent;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository.PersistOperation;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class ImsIncidentTypeStateTransitionRepository extends EntityRepository
		implements EntityCacheable<ImsIncidentTypeStateTransition>, EntityPersistent<ImsIncidentTypeStateTransition> {

	// region Constructors
	public ImsIncidentTypeStateTransitionRepository() {

		super(ImsIncidentTypeStateTransition.class);
	}

	public ImsIncidentTypeStateTransitionRepository(Boolean cacheable) {

		super(ImsIncidentTypeStateTransition.class, cacheable);
	}

	public ImsIncidentTypeStateTransitionRepository(Boolean cacheable, Long cacheRetentionTime) {

		super(ImsIncidentTypeStateTransition.class, cacheable, cacheRetentionTime);
	}

	// endregion Constructors

	// region getters
	public ImsIncidentTypeStateTransition get(Long incidentTypeId, Long iniIncidentStateId, Boolean fillObject) {

		return (ImsIncidentTypeStateTransition) getFromCache(
				new String[] { String.valueOf(incidentTypeId), String.valueOf(iniIncidentStateId) }, fillObject);
	}
	
	public List<ImsIncidentTypeStateTransition> gets(Long incidentTypeId, Long iniIncidentStateId, Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(new String[] { String.valueOf(incidentTypeId),
				String.valueOf(iniIncidentStateId)}, fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(be -> (ImsIncidentTypeStateTransition) be).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}
	
	/**
	 * 
	 * @param incidentTypeId
	 * @param fillObject
	 * @return
	 */
	public List<ImsIncidentTypeStateTransition> getsByIncidentType(Long incidentTypeId, Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(new String[] { String.valueOf(incidentTypeId), null },
				fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(be -> (ImsIncidentTypeStateTransition) be).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}

	@Override
	public List<ImsIncidentTypeStateTransition> getAll(Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(be -> (ImsIncidentTypeStateTransition) be).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}
	// endregion getters

	// region puts
	@Override
	public ImsIncidentTypeStateTransition put(ImsIncidentTypeStateTransition entity) {

		return (ImsIncidentTypeStateTransition) putFromCache(entity);
	}

	@Override
	public List<ImsIncidentTypeStateTransition> put(List<ImsIncidentTypeStateTransition> entities) {

		List<BasicElement> basicElements = entities.stream().map(alarmConfig -> (BasicElement) alarmConfig)
				.collect(Collectors.toList());

		basicElements = putsFromCache(basicElements);

		return basicElements.stream().map(be -> (ImsIncidentTypeStateTransition) be).collect(Collectors.toList());
	}
	// endregion puts

	// region dels
	@Override
	public ImsIncidentTypeStateTransition del(ImsIncidentTypeStateTransition entity) {

		return (ImsIncidentTypeStateTransition) delFromCache(entity);

	}

	@Override
	public List<ImsIncidentTypeStateTransition> del(List<ImsIncidentTypeStateTransition> entities) {

		List<BasicElement> basicElements = entities.stream().map(alarmConfig -> (BasicElement) alarmConfig)
				.collect(Collectors.toList());

		basicElements = delFromCache(basicElements);

		return basicElements.stream().map(be -> (ImsIncidentTypeStateTransition) be).collect(Collectors.toList());
	}
	// endregion dels

	@Override
	public ImsIncidentTypeStateTransition create(ImsIncidentTypeStateTransition imsIncidentTypeStateTransition) {

		try {

			return create(imsIncidentTypeStateTransition, null, null);

		} catch (EntityPersistenceException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			return null;
		}
	}

	@Override
	public ImsIncidentTypeStateTransition create(ImsIncidentTypeStateTransition imsIncidentTypeStateTransition,
			Connection connection, List<Pair<PersistOperation, BasicElement>> persistOperations)
			throws EntityPersistenceException {

		Connection conn = null;
		boolean closeConnection = false;
		boolean commit = false;
		conn = connection;
		ImsIncidentTypeStateTransition imsIncidentTypeStateTransitionResult = null;

		try {

			if (conn == null) {

				conn = DataSourceConnection.getInstance().getConnection();
				conn.setAutoCommit(false);
				closeConnection = true;
				commit = true;
				persistOperations = new ArrayList<>();
			}

			// Insert imsIncidentTypeStateTransition in BBDD
			ImsIncidentTypeStateTransition imsIncidentTypeStateTransitionInserted = null;

			imsIncidentTypeStateTransitionInserted = (ImsIncidentTypeStateTransition) getObjectDao().insert(conn,
					imsIncidentTypeStateTransition);

			if (imsIncidentTypeStateTransitionInserted == null) {

				throw new EntityDBException(EntityDBException.Operation.INSERT, imsIncidentTypeStateTransition);
			}

			persistOperations.add(new MutablePair<>(PersistOperation.PUT, imsIncidentTypeStateTransitionInserted));

			if (commit) {
				conn.commit();

				persistOperations(persistOperations);
				
				imsIncidentTypeStateTransitionResult = get(imsIncidentTypeStateTransitionInserted.getIncidentTypeId(),
						imsIncidentTypeStateTransitionInserted.getIniIncidentStateId(), false);
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
			throw new EntityPersistenceException(EntityPersistenceException.Operation.CREATE,
					imsIncidentTypeStateTransition);

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

		return imsIncidentTypeStateTransitionResult;
	}

	@Override
	public ImsIncidentTypeStateTransition clone(ImsIncidentTypeStateTransition entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ImsIncidentTypeStateTransition clone(ImsIncidentTypeStateTransition entity, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ImsIncidentTypeStateTransition update(ImsIncidentTypeStateTransition imsIncidentTypeStateTransition) {

		try {

			return update(imsIncidentTypeStateTransition, null, null);

		} catch (EntityPersistenceException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			return null;
		}
	}

	@Override
	public ImsIncidentTypeStateTransition update(ImsIncidentTypeStateTransition imsIncidentTypeStateTransition,
			Connection connection, List<Pair<PersistOperation, BasicElement>> persistOperations)
			throws EntityPersistenceException {

		Connection conn = null;
		boolean closeConnection = false;
		boolean commit = false;
		conn = connection;
		ImsIncidentTypeStateTransition imsIncidentTypeStateTransitionResult = null;
		ImsIncidentTypeStateTransition imsIncidentTypeStateTransitionAux = null;

		try {

			if (imsIncidentTypeStateTransition == null || (imsIncidentTypeStateTransition.getIncidentTypeId() == null
					|| imsIncidentTypeStateTransition.getIniIncidentStateId() == null)) {

				throw new NullPointerException("imsIncidentTypeStateTransition is null");
			}

			imsIncidentTypeStateTransitionAux = get(imsIncidentTypeStateTransition.getIncidentTypeId(),
					imsIncidentTypeStateTransition.getIniIncidentStateId(), false);

			if (imsIncidentTypeStateTransitionAux == null) {

				throw new NullPointerException("imsIncidentTypeStateTransition not found");
			} else {

				imsIncidentTypeStateTransitionAux = (ImsIncidentTypeStateTransition) imsIncidentTypeStateTransitionAux.clone();
			}

			if (imsIncidentTypeStateTransition.getAlias() != null)
				imsIncidentTypeStateTransitionAux.setAlias(imsIncidentTypeStateTransition.getAlias());
			if (imsIncidentTypeStateTransition.getDescription() != null)
				imsIncidentTypeStateTransitionAux.setDescription(imsIncidentTypeStateTransition.getDescription());
			if (imsIncidentTypeStateTransition.getEndIncidentStateId() != null)
				imsIncidentTypeStateTransitionAux.setEndIncidentStateId(imsIncidentTypeStateTransition.getEndIncidentStateId());
			if (imsIncidentTypeStateTransition.getAuto() != null)
				imsIncidentTypeStateTransitionAux.setAuto(imsIncidentTypeStateTransition.getAuto());

			if (conn == null) {

				conn = DataSourceConnection.getInstance().getConnection();
				conn.setAutoCommit(false);
				closeConnection = true;
				commit = true;
				persistOperations = new ArrayList<>();
			}

			// Update imsIncidentTypeStateTransition in BBDD
			ImsIncidentTypeStateTransition imsIncidentTypeStateTransitionUpdated = null;

			imsIncidentTypeStateTransitionUpdated = (ImsIncidentTypeStateTransition) getObjectDao().update(conn,
					imsIncidentTypeStateTransitionAux);

			if (imsIncidentTypeStateTransitionUpdated == null) {

				throw new EntityDBException(EntityDBException.Operation.UPDATE, imsIncidentTypeStateTransitionAux);
			}

			persistOperations.add(new MutablePair<>(PersistOperation.PUT, imsIncidentTypeStateTransitionUpdated));

			if (commit) {

				conn.commit();

				persistOperations(persistOperations);
			}

			imsIncidentTypeStateTransitionResult = imsIncidentTypeStateTransitionUpdated;

		} catch (EntityDBException | SQLException | NullPointerException | IOException | CloneNotSupportedException e) {

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
			throw new EntityPersistenceException(EntityPersistenceException.Operation.UPDATE,
					imsIncidentTypeStateTransition);

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

		return imsIncidentTypeStateTransitionResult;
	}

	@Override
	public ImsIncidentTypeStateTransition delete(ImsIncidentTypeStateTransition imsIncidentTypeStateTransition) {

		try {

			return delete(imsIncidentTypeStateTransition, null, null);

		} catch (EntityPersistenceException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			return null;
		}
	}

	@Override
	public ImsIncidentTypeStateTransition delete(ImsIncidentTypeStateTransition imsIncidentTypeStateTransition,
			Connection connection, List<Pair<PersistOperation, BasicElement>> persistOperations)
			throws EntityPersistenceException {

		Connection conn = null;
		boolean closeConnection = false;
		boolean commit = false;
		conn = connection;
		ImsIncidentTypeStateTransition imsIncidentTypeStateTransitionResult = null;
		ImsIncidentTypeStateTransition imsIncidentTypeStateTransitionAux = null;

		try {

			if (imsIncidentTypeStateTransition == null || imsIncidentTypeStateTransition.getIncidentTypeId() == null || imsIncidentTypeStateTransition.getIniIncidentStateId() == null ) {

				throw new NullPointerException("imsIncidentTypeStateTransition is null");
			}

			imsIncidentTypeStateTransitionAux = get(imsIncidentTypeStateTransition.getIncidentTypeId(),
					imsIncidentTypeStateTransition.getIniIncidentStateId(), false);

			if (imsIncidentTypeStateTransitionAux == null) {

				throw new NullPointerException("imsIncidentTypeStateTransition not found");
			}

			if (conn == null) {

				conn = DataSourceConnection.getInstance().getConnection();
				conn.setAutoCommit(false);
				closeConnection = true;
				commit = true;
				persistOperations = new ArrayList<>();
			}

			// Delete imsIncidentTypeStateTransition in BBDD
			ImsIncidentTypeStateTransition imsIncidentTypeStateTransitionDeleted = null;

			imsIncidentTypeStateTransitionDeleted = (ImsIncidentTypeStateTransition) getObjectDao().delete(conn,
					imsIncidentTypeStateTransitionAux);

			if (imsIncidentTypeStateTransitionDeleted == null) {

				throw new EntityDBException(EntityDBException.Operation.DELETE, imsIncidentTypeStateTransitionAux);
			}

			persistOperations.add(new MutablePair<>(PersistOperation.DELETE, imsIncidentTypeStateTransitionAux));

			if (commit) {

				conn.commit();

				// Update cache
				persistOperations(persistOperations);
			}

			imsIncidentTypeStateTransitionResult = imsIncidentTypeStateTransitionDeleted;

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
			throw new EntityPersistenceException(EntityPersistenceException.Operation.DELETE,
					imsIncidentTypeStateTransition);

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

		return imsIncidentTypeStateTransitionResult;
	}

}
