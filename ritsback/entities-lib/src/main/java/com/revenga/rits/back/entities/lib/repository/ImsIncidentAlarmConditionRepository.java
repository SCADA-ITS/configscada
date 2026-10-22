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
import com.revenga.rits.back.data.core.model.ImsIncidentAlarmCondition;
import com.revenga.rits.back.data.core.model.ImsIncidentAlarmConditionAlarm;
import com.revenga.rits.back.entities.lib.exception.EntityDBException;
import com.revenga.rits.back.entities.lib.exception.EntityPersistenceException;
import com.revenga.rits.back.entities.lib.repository.entity.EntityCacheable;
import com.revenga.rits.back.entities.lib.repository.entity.EntityPersistent;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class ImsIncidentAlarmConditionRepository extends EntityRepository
		implements EntityCacheable<ImsIncidentAlarmCondition>, EntityPersistent<ImsIncidentAlarmCondition> {

	// region Constructors
	public ImsIncidentAlarmConditionRepository() {

		super(ImsIncidentAlarmCondition.class);
	}

	public ImsIncidentAlarmConditionRepository(Boolean cacheable) {

		super(ImsIncidentAlarmCondition.class, cacheable);
	}

	public ImsIncidentAlarmConditionRepository(Boolean cacheable, Long cacheRetentionTime) {

		super(ImsIncidentAlarmCondition.class, cacheable, cacheRetentionTime);
	}

	public ImsIncidentAlarmConditionRepository(Boolean cacheable, List<EntityRepository> repositories) {

		super(ImsIncidentAlarmCondition.class, cacheable, repositories);
	}

	public ImsIncidentAlarmConditionRepository(Boolean cacheable, Long cacheRetentionTime,
			List<EntityRepository> repositories) {

		super(ImsIncidentAlarmCondition.class, cacheable, cacheRetentionTime, repositories);
	}

	// endregion Constructors

	// region getters
	public ImsIncidentAlarmCondition get(Long id, Boolean fillObject) {

		return (ImsIncidentAlarmCondition) getFromCache(new String[] { String.valueOf(id) }, fillObject);
	}

	@Override
	public List<ImsIncidentAlarmCondition> getAll(Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(be -> (ImsIncidentAlarmCondition) be).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}

	public List<ImsIncidentAlarmCondition> getsIncidentTypeAndStretch(Long stretchId, Long incidentTypeId,
			Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			List<ImsIncidentAlarmCondition> imsIncidentTypeDocument = basicElements.stream()
					.map(be -> (ImsIncidentAlarmCondition) be).collect(Collectors.toList());

			return imsIncidentTypeDocument.stream()
					.filter(e -> e.getStretchId().equals(stretchId) && e.getIncidentTypeId().equals(incidentTypeId))
					.collect(Collectors.toList());
		}

		return Collections.emptyList();
	}
	
	public List<ImsIncidentAlarmCondition> getsByTypeStretch(Long incidentTypeId, Long stretchId,
			Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			List<ImsIncidentAlarmCondition> imsIncidentTypeDocument = basicElements.stream()
					.map(be -> (ImsIncidentAlarmCondition) be).collect(Collectors.toList());

			return imsIncidentTypeDocument.stream()
					.filter(e -> e.getStretchId().equals(stretchId) && e.getIncidentTypeId().equals(incidentTypeId))
					.collect(Collectors.toList());
		}

		return Collections.emptyList();
	}

	// endregion getters

	// region puts
	@Override
	public ImsIncidentAlarmCondition put(ImsIncidentAlarmCondition entity) {

		return (ImsIncidentAlarmCondition) putFromCache(entity);
	}

	@Override
	public List<ImsIncidentAlarmCondition> put(List<ImsIncidentAlarmCondition> entities) {

		List<BasicElement> basicElements = entities.stream().map(alarmConfig -> (BasicElement) alarmConfig)
				.collect(Collectors.toList());

		basicElements = putsFromCache(basicElements);

		return basicElements.stream().map(be -> (ImsIncidentAlarmCondition) be).collect(Collectors.toList());
	}
	// endregion puts

	// region dels
	@Override
	public ImsIncidentAlarmCondition del(ImsIncidentAlarmCondition entity) {

		return (ImsIncidentAlarmCondition) delFromCache(entity);

	}

	@Override
	public List<ImsIncidentAlarmCondition> del(List<ImsIncidentAlarmCondition> entities) {

		List<BasicElement> basicElements = entities.stream().map(alarmConfig -> (BasicElement) alarmConfig)
				.collect(Collectors.toList());

		basicElements = delFromCache(basicElements);

		return basicElements.stream().map(be -> (ImsIncidentAlarmCondition) be).collect(Collectors.toList());
	}
	// endregion dels

	@Override
	public ImsIncidentAlarmCondition create(ImsIncidentAlarmCondition imsIncidentAlarmCondition) {

		try {

			return create(imsIncidentAlarmCondition, null, null);

		} catch (EntityPersistenceException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			return null;
		}
	}

	@Override
	public ImsIncidentAlarmCondition create(ImsIncidentAlarmCondition imsIncidentAlarmCondition, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {

		Connection conn = null;
		boolean closeConnection = false;
		boolean commit = false;
		conn = connection;
		ImsIncidentAlarmCondition imsIncidentAlarmConditionResult = null;

		try {

			if (conn == null) {

				conn = DataSourceConnection.getInstance().getConnection();
				conn.setAutoCommit(false);
				closeConnection = true;
				commit = true;
				persistOperations = new ArrayList<>();
			}

			// Insert imsIncidentAlarmCondition in BBDD
			ImsIncidentAlarmCondition imsIncidentAlarmConditionInserted = null;

			imsIncidentAlarmConditionInserted = (ImsIncidentAlarmCondition) getObjectDao().insert(conn,
					imsIncidentAlarmCondition);

			if (imsIncidentAlarmConditionInserted == null) {

				throw new EntityDBException(EntityDBException.Operation.INSERT, imsIncidentAlarmCondition);
			}

			persistOperations.add(new MutablePair<>(PersistOperation.PUT, imsIncidentAlarmConditionInserted));

			// Insert all imsIncidentAlarmConditionAlarms
			if (!CollectionUtils.isEmpty(imsIncidentAlarmCondition.getImsIncidentAlarmConditionAlarms())
					&& !CollectionUtils.isEmpty(getRepositories())) {

				ImsIncidentAlarmConditionAlarmRepository entityRepository = (ImsIncidentAlarmConditionAlarmRepository) getRepositories()
						.stream()
						.filter(repository -> repository.getClazz().equals(ImsIncidentAlarmConditionAlarm.class))
						.findFirst().orElse(null);

				if (entityRepository != null) {

					for (ImsIncidentAlarmConditionAlarm imsIncidentAlarmConditionAlarm : imsIncidentAlarmCondition
							.getImsIncidentAlarmConditionAlarms()) {

						imsIncidentAlarmConditionAlarm
								.setIncidentAlarmConditionId(imsIncidentAlarmConditionInserted.getId());
						entityRepository.create(imsIncidentAlarmConditionAlarm, conn, persistOperations);
					}
				}

			}

			if (commit) {
				conn.commit();

				persistOperations(persistOperations);
			}

			imsIncidentAlarmConditionResult = get(imsIncidentAlarmConditionInserted.getId(), false);

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
					imsIncidentAlarmCondition);

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

		return imsIncidentAlarmConditionResult;
	}

	@Override
	public ImsIncidentAlarmCondition clone(ImsIncidentAlarmCondition entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ImsIncidentAlarmCondition clone(ImsIncidentAlarmCondition entity, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ImsIncidentAlarmCondition update(ImsIncidentAlarmCondition imsIncidentAlarmCondition) {

		try {

			return update(imsIncidentAlarmCondition, null, null);

		} catch (EntityPersistenceException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			return null;
		}
	}

	@Override
	public ImsIncidentAlarmCondition update(ImsIncidentAlarmCondition imsIncidentAlarmCondition, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {

		Connection conn = null;
		boolean closeConnection = false;
		boolean commit = false;
		conn = connection;
		ImsIncidentAlarmCondition imsIncidentAlarmConditionResult = null;
		ImsIncidentAlarmCondition imsIncidentAlarmConditionAux = null;
		List<ImsIncidentAlarmConditionAlarm> imsIncidentAlarmConditionAlarms = null;

		try {

			if (imsIncidentAlarmCondition == null || imsIncidentAlarmCondition.getId() == null) {

				throw new NullPointerException("ImsIncidentAlarmCondition is null");
			}

			imsIncidentAlarmConditionAux = get(imsIncidentAlarmCondition.getId(), false);

			if (imsIncidentAlarmConditionAux == null) {

				throw new NullPointerException("ImsIncidentAlarmCondition not found");
			}

			if (imsIncidentAlarmCondition.getAuto() != null)
				imsIncidentAlarmConditionAux.setAuto(imsIncidentAlarmCondition.getAuto());
			if (imsIncidentAlarmCondition.getStretchId() != null)
				imsIncidentAlarmConditionAux.setStretchId(imsIncidentAlarmCondition.getStretchId());
			if (imsIncidentAlarmCondition.getIncidentTypeId() != null)
				imsIncidentAlarmConditionAux.setIncidentTypeId(imsIncidentAlarmCondition.getIncidentTypeId());
			if (imsIncidentAlarmCondition.getRoadImpactId() != null) {
				imsIncidentAlarmConditionAux.setRoadImpactId(imsIncidentAlarmCondition.getRoadImpactId());
			}else {
				imsIncidentAlarmConditionAux.setRoadImpactId(null);
			}

			if (conn == null) {

				conn = DataSourceConnection.getInstance().getConnection();
				conn.setAutoCommit(false);
				closeConnection = true;
				commit = true;
				persistOperations = new ArrayList<>();
			}

			// Update all ImsIncidentAlarmConditionAlarms
			if (imsIncidentAlarmCondition.getImsIncidentAlarmConditionAlarms() != null
					&& !CollectionUtils.isEmpty(getRepositories())) {

				// Delete all ImsIncidentAlarmConditionAlarms
				ImsIncidentAlarmConditionAlarmRepository entityRepository = (ImsIncidentAlarmConditionAlarmRepository) getRepositories()
						.stream()
						.filter(repository -> repository.getClazz().equals(ImsIncidentAlarmConditionAlarm.class))
						.findFirst().orElse(null);

				if (entityRepository != null) {

					imsIncidentAlarmConditionAlarms = entityRepository
							.getsByIncidentAlarmCondition(imsIncidentAlarmCondition.getId(), false);

					if (!CollectionUtils.isEmpty(imsIncidentAlarmConditionAlarms)) {

						for (ImsIncidentAlarmConditionAlarm imsIncidentAlarmConditionAlarmDeleted : imsIncidentAlarmConditionAlarms) {

							entityRepository.delete(imsIncidentAlarmConditionAlarmDeleted, conn, persistOperations);
						}
					}

					if (!CollectionUtils.isEmpty(imsIncidentAlarmCondition.getImsIncidentAlarmConditionAlarms())) {

						for (ImsIncidentAlarmConditionAlarm imsIncidentAlarmConditionAlarmCreated : imsIncidentAlarmCondition
								.getImsIncidentAlarmConditionAlarms()) {

							imsIncidentAlarmConditionAlarmCreated
									.setIncidentAlarmConditionId(imsIncidentAlarmCondition.getId());
							entityRepository.create(imsIncidentAlarmConditionAlarmCreated, conn, persistOperations);
						}

					}
				}
			}

			// Update imsIncidentAlarmCondition in BBDD
			ImsIncidentAlarmCondition imsIncidentAlarmConditionUpdated = null;

			imsIncidentAlarmConditionUpdated = (ImsIncidentAlarmCondition) getObjectDao().update(conn,
					imsIncidentAlarmConditionAux);

			if (imsIncidentAlarmConditionUpdated == null) {

				throw new EntityDBException(EntityDBException.Operation.UPDATE, imsIncidentAlarmConditionAux);
			}

			persistOperations.add(new MutablePair<>(PersistOperation.PUT, imsIncidentAlarmConditionUpdated));

			if (commit) {

				conn.commit();

				persistOperations(persistOperations);
			}

			imsIncidentAlarmConditionResult = imsIncidentAlarmConditionUpdated;

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
			throw new EntityPersistenceException(EntityPersistenceException.Operation.UPDATE,
					imsIncidentAlarmCondition);

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

		return imsIncidentAlarmConditionResult;
	}

	@Override
	public ImsIncidentAlarmCondition delete(ImsIncidentAlarmCondition imsIncidentAlarmCondition) {

		try {

			return delete(imsIncidentAlarmCondition, null, null);

		} catch (EntityPersistenceException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			return null;
		}
	}

	@Override
	public ImsIncidentAlarmCondition delete(ImsIncidentAlarmCondition imsIncidentAlarmCondition, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {

		Connection conn = null;
		boolean closeConnection = false;
		boolean commit = false;
		conn = connection;
		ImsIncidentAlarmCondition imsIncidentAlarmConditionResult = null;
		ImsIncidentAlarmCondition imsIncidentAlarmConditionAux = null;
		List<ImsIncidentAlarmConditionAlarm> imsIncidentAlarmConditionAlarms = null;

		try {

			if (imsIncidentAlarmCondition == null || imsIncidentAlarmCondition.getId() == null) {

				throw new NullPointerException("imsIncidentAlarmCondition is null");
			}

			imsIncidentAlarmConditionAux = get(imsIncidentAlarmCondition.getId(), false);

			if (imsIncidentAlarmConditionAux == null) {

				throw new NullPointerException("imsIncidentAlarmCondition not found");
			}

			if (conn == null) {

				conn = DataSourceConnection.getInstance().getConnection();
				conn.setAutoCommit(false);
				closeConnection = true;
				commit = true;
				persistOperations = new ArrayList<>();
			}

			// Delete all ImsIncidentAlarmConditionAlarms
			if (!CollectionUtils.isEmpty(getRepositories())) {

				ImsIncidentAlarmConditionAlarmRepository entityRepository = (ImsIncidentAlarmConditionAlarmRepository) getRepositories()
						.stream()
						.filter(repository -> repository.getClazz().equals(ImsIncidentAlarmConditionAlarm.class))
						.findFirst().orElse(null);

				if (entityRepository != null) {

					imsIncidentAlarmConditionAlarms = entityRepository
							.getsByIncidentAlarmCondition(imsIncidentAlarmCondition.getId(), false);

					if (!CollectionUtils.isEmpty(imsIncidentAlarmConditionAlarms)) {

						for (ImsIncidentAlarmConditionAlarm imsIncidentAlarmConditionAlarm : imsIncidentAlarmConditionAlarms) {

							entityRepository.delete(imsIncidentAlarmConditionAlarm, conn, persistOperations);
						}
					}
				}
			}

			// Delete imsIncidentAlarmCondition in BBDD
			ImsIncidentAlarmCondition imsIncidentAlarmConditionDeleted = null;

			imsIncidentAlarmConditionDeleted = (ImsIncidentAlarmCondition) getObjectDao().delete(conn,
					imsIncidentAlarmConditionAux);

			if (imsIncidentAlarmConditionDeleted == null) {

				throw new EntityDBException(EntityDBException.Operation.DELETE, imsIncidentAlarmConditionAux);
			}

			persistOperations.add(new MutablePair<>(PersistOperation.DELETE, imsIncidentAlarmConditionAux));

			if (commit) {

				conn.commit();

				// Update cache
				persistOperations(persistOperations);
			}

			imsIncidentAlarmConditionResult = imsIncidentAlarmConditionDeleted;

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
					imsIncidentAlarmCondition);

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

		return imsIncidentAlarmConditionResult;
	}

}
