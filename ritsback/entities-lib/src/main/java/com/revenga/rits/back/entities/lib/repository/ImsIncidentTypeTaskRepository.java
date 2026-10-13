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
import com.revenga.rits.back.data.core.model.ImsIncidentTaskPlan;
import com.revenga.rits.back.data.core.model.ImsIncidentTypeTask;
import com.revenga.rits.back.data.core.model.ImsIncidentTypeTaskValue;
import com.revenga.rits.back.entities.lib.exception.EntityDBException;
import com.revenga.rits.back.entities.lib.exception.EntityPersistenceException;
import com.revenga.rits.back.entities.lib.repository.entity.EntityCacheable;
import com.revenga.rits.back.entities.lib.repository.entity.EntityPersistent;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class ImsIncidentTypeTaskRepository extends EntityRepository
		implements EntityCacheable<ImsIncidentTypeTask>, EntityPersistent<ImsIncidentTypeTask> {

	// region Constructors
	public ImsIncidentTypeTaskRepository() {

		super(ImsIncidentTypeTask.class);
	}

	public ImsIncidentTypeTaskRepository(Boolean cacheable, Long cacheRetentionTime,
			List<EntityRepository> repositories) {

		super(ImsIncidentTypeTask.class, cacheable, cacheRetentionTime, repositories);
	}

	public ImsIncidentTypeTaskRepository(Boolean cacheable) {

		super(ImsIncidentTypeTask.class, cacheable);
	}

	public ImsIncidentTypeTaskRepository(Boolean cacheable, Long cacheRetentionTime) {

		super(ImsIncidentTypeTask.class, cacheable, cacheRetentionTime);
	}

	// endregion Constructors

	// region getters
	public ImsIncidentTypeTask get(Long id, Boolean fillObject) {

		return (ImsIncidentTypeTask) getFromCache(new String[] { String.valueOf(id) }, fillObject);
	}

	@Override
	public List<ImsIncidentTypeTask> getAll(Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(be -> (ImsIncidentTypeTask) be).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}

	public List<ImsIncidentTypeTask> gets(Long incidentTypeId, Long incidentStateId, Long stretchId,
			Boolean fillObject) {

		if (ObjectUtils.allNotNull(incidentTypeId)) {

			List<BasicElement> basicElements = getsFromCache(fillObject);

			if (!CollectionUtils.isEmpty(basicElements)) {

				List<ImsIncidentTypeTask> imsIncidentTypeTask = basicElements.stream()
						.map(ImsIncidentTypeTask.class::cast).collect(Collectors.toList());

				List<ImsIncidentTypeTask> imsIncidentTypeTaskStretch = null;

				if (stretchId != null) {

					imsIncidentTypeTaskStretch = imsIncidentTypeTask.stream()
							.filter(e -> incidentTypeId.equals(e.getIncidentTypeId())
									&& ((incidentStateId != null && incidentStateId.equals(e.getIncidentStateId()))
											|| (incidentStateId == null && e.getIncidentStateId() == null))
									&& stretchId.equals(e.getStretchId()))
							.collect(Collectors.toList());

					if (CollectionUtils.isEmpty(imsIncidentTypeTaskStretch)) {

						return imsIncidentTypeTask.stream()
								.filter(e -> incidentTypeId.equals(e.getIncidentTypeId())
										&& ((incidentStateId != null && incidentStateId.equals(e.getIncidentStateId()))
												|| (incidentStateId == null && e.getIncidentStateId() == null))
										&& e.getStretchId() == null)
								.collect(Collectors.toList());
					} else {

						return imsIncidentTypeTaskStretch;
					}

				} else {

					return imsIncidentTypeTask.stream()
							.filter(e -> incidentTypeId.equals(e.getIncidentTypeId())
									&& ((incidentStateId != null && incidentStateId.equals(e.getIncidentStateId()))
											|| (incidentStateId == null
													&& e.getIncidentStateId() == null)))
							.collect(Collectors.toList());
				}

			}
		}
		return Collections.emptyList();
	}

	public List<ImsIncidentTypeTask> getByIncidentType(Long incidentTypeId, Boolean fillObject) {

		if (incidentTypeId != null) {

			List<BasicElement> basicElements = getsFromCache(fillObject);

			if (!CollectionUtils.isEmpty(basicElements)) {

				List<ImsIncidentTypeTask> imsIncidentTypeTask = basicElements.stream()
						.map(ImsIncidentTypeTask.class::cast).collect(Collectors.toList());

				return imsIncidentTypeTask.stream().filter(e -> incidentTypeId.equals(e.getIncidentTypeId()))
						.collect(Collectors.toList());

			}
		}

		return Collections.emptyList();
	}
	// endregion getters

	// region puts
	@Override
	public ImsIncidentTypeTask put(ImsIncidentTypeTask entity) {

		return (ImsIncidentTypeTask) putFromCache(entity);
	}

	@Override
	public List<ImsIncidentTypeTask> put(List<ImsIncidentTypeTask> entities) {

		List<BasicElement> basicElements = entities.stream().map(alarmConfig -> (BasicElement) alarmConfig)
				.collect(Collectors.toList());

		basicElements = putsFromCache(basicElements);

		return basicElements.stream().map(be -> (ImsIncidentTypeTask) be).collect(Collectors.toList());
	}
	// endregion puts

	// region dels
	@Override
	public ImsIncidentTypeTask del(ImsIncidentTypeTask entity) {

		return (ImsIncidentTypeTask) delFromCache(entity);

	}

	@Override
	public List<ImsIncidentTypeTask> del(List<ImsIncidentTypeTask> entities) {

		List<BasicElement> basicElements = entities.stream().map(alarmConfig -> (BasicElement) alarmConfig)
				.collect(Collectors.toList());

		basicElements = delFromCache(basicElements);

		return basicElements.stream().map(be -> (ImsIncidentTypeTask) be).collect(Collectors.toList());
	}
	// endregion dels

	@Override
	public ImsIncidentTypeTask create(ImsIncidentTypeTask imsIncidentTypeTask) {

		try {

			return create(imsIncidentTypeTask, null, null);

		} catch (EntityPersistenceException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			return null;
		}
	}

	@Override
	public ImsIncidentTypeTask create(ImsIncidentTypeTask imsIncidentTypeTask, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {

		Connection conn = null;
		boolean closeConnection = false;
		boolean commit = false;
		conn = connection;
		ImsIncidentTypeTask imsIncidentTypeTaskResult = null;

		try {

			if (conn == null) {

				conn = DataSourceConnection.getInstance().getConnection();
				conn.setAutoCommit(false);
				closeConnection = true;
				commit = true;
				persistOperations = new ArrayList<>();
			}

			// Insert imsIncidentTypeTask in BBDD
			ImsIncidentTypeTask imsIncidentTypeTaskInserted = null;

			imsIncidentTypeTaskInserted = (ImsIncidentTypeTask) getObjectDao().insert(conn, imsIncidentTypeTask);

			if (imsIncidentTypeTaskInserted == null) {

				throw new EntityDBException(EntityDBException.Operation.INSERT, imsIncidentTypeTask);
			}

			persistOperations.add(new MutablePair<>(PersistOperation.PUT, imsIncidentTypeTaskInserted));

			if (commit) {
				conn.commit();

				persistOperations(persistOperations);

				imsIncidentTypeTaskResult = get(imsIncidentTypeTaskInserted.getId(), false);
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
			throw new EntityPersistenceException(EntityPersistenceException.Operation.CREATE, imsIncidentTypeTask);

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

		return imsIncidentTypeTaskResult;
	}

	@Override
	public ImsIncidentTypeTask clone(ImsIncidentTypeTask entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ImsIncidentTypeTask clone(ImsIncidentTypeTask entity, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ImsIncidentTypeTask update(ImsIncidentTypeTask imsIncidentTypeTask) {

		try {

			return update(imsIncidentTypeTask, null, null);

		} catch (EntityPersistenceException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			return null;
		}
	}

	@Override
	public ImsIncidentTypeTask update(ImsIncidentTypeTask imsIncidentTypeTask, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {

		Connection conn = null;
		boolean closeConnection = false;
		boolean commit = false;
		conn = connection;
		ImsIncidentTypeTask imsIncidentTypeTaskResult = null;
		ImsIncidentTypeTask imsIncidentTypeTaskAux = null;

		try {

			if (imsIncidentTypeTask == null || imsIncidentTypeTask.getId() == null) {

				throw new NullPointerException("imsIncidentTypeTask is null");
			}

			imsIncidentTypeTaskAux = get(imsIncidentTypeTask.getId(), false);

			if (imsIncidentTypeTaskAux == null) {

				throw new NullPointerException("imsIncidentTypeTask not found");
			} else {

				imsIncidentTypeTaskAux = (ImsIncidentTypeTask) imsIncidentTypeTaskAux.clone();
			}

			if (imsIncidentTypeTask.getAlias() != null)
				imsIncidentTypeTaskAux.setAlias(imsIncidentTypeTask.getAlias());
			if (imsIncidentTypeTask.getDescription() != null)
				imsIncidentTypeTaskAux.setDescription(imsIncidentTypeTask.getDescription());
			if (imsIncidentTypeTask.getIncidentTypeId() != null)
				imsIncidentTypeTaskAux.setIncidentTypeId(imsIncidentTypeTask.getIncidentTypeId());
			if (imsIncidentTypeTask.getIncidentStateId() != null)
				imsIncidentTypeTaskAux.setIncidentStateId(imsIncidentTypeTask.getIncidentStateId());
			if (imsIncidentTypeTask.getTaskTypeId() != null)
				imsIncidentTypeTaskAux.setTaskTypeId(imsIncidentTypeTask.getTaskTypeId());
			if (imsIncidentTypeTask.getMandatory() != null)
				imsIncidentTypeTaskAux.setMandatory(imsIncidentTypeTask.getMandatory());
			if (imsIncidentTypeTask.getPosition() != null)
				imsIncidentTypeTaskAux.setPosition(imsIncidentTypeTask.getPosition());
			if (imsIncidentTypeTask.getAuto() != null)
				imsIncidentTypeTaskAux.setAuto(imsIncidentTypeTask.getAuto());
			if (imsIncidentTypeTask.getRoadImpactId() != null) {
				imsIncidentTypeTaskAux.setRoadImpactId(imsIncidentTypeTask.getRoadImpactId());
			} else {
				imsIncidentTypeTaskAux.setRoadImpactId(null);
			}

			if (conn == null) {

				conn = DataSourceConnection.getInstance().getConnection();
				conn.setAutoCommit(false);
				closeConnection = true;
				commit = true;
				persistOperations = new ArrayList<>();
			}

			// Update imsIncidentTypeTask in BBDD
			ImsIncidentTypeTask imsIncidentTypeTaskUpdated = null;

			imsIncidentTypeTaskUpdated = (ImsIncidentTypeTask) getObjectDao().update(conn, imsIncidentTypeTaskAux);

			if (imsIncidentTypeTaskUpdated == null) {

				throw new EntityDBException(EntityDBException.Operation.UPDATE, imsIncidentTypeTaskAux);
			}

			persistOperations.add(new MutablePair<>(PersistOperation.PUT, imsIncidentTypeTaskUpdated));

			if (commit) {

				conn.commit();

				persistOperations(persistOperations);
			}

			imsIncidentTypeTaskResult = imsIncidentTypeTaskUpdated;

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
			throw new EntityPersistenceException(EntityPersistenceException.Operation.UPDATE, imsIncidentTypeTask);

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

		return imsIncidentTypeTaskResult;
	}

	@Override
	public ImsIncidentTypeTask delete(ImsIncidentTypeTask imsIncidentAlarmConditionAlarm) {

		try {

			return delete(imsIncidentAlarmConditionAlarm, null, null);

		} catch (EntityPersistenceException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			return null;
		}
	}

	@Override
	public ImsIncidentTypeTask delete(ImsIncidentTypeTask imsIncidentTypeTask, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {

		Connection conn = null;
		boolean closeConnection = false;
		boolean commit = false;
		conn = connection;
		ImsIncidentTypeTask imsIncidentTypeTaskResult = null;
		ImsIncidentTypeTask imsIncidentTypeTaskAux = null;

		try {

			if (imsIncidentTypeTask == null || imsIncidentTypeTask.getId() == null) {

				throw new NullPointerException("imsIncidentTypeTask is null");
			}

			imsIncidentTypeTaskAux = get(imsIncidentTypeTask.getId(), false);

			if (imsIncidentTypeTaskAux == null) {

				throw new NullPointerException("imsIncidentTypeTask not found");
			}

			if (conn == null) {

				conn = DataSourceConnection.getInstance().getConnection();
				conn.setAutoCommit(false);
				closeConnection = true;
				commit = true;
				persistOperations = new ArrayList<>();
			}

			// Delete all ImsIncidentTaskValues
			{
				ImsIncidentTypeTaskValueRepository entityRepository = (ImsIncidentTypeTaskValueRepository) getRepository(
						ImsIncidentTypeTaskValue.class);

				if (entityRepository != null) {

					List<ImsIncidentTypeTaskValue> imsIncidentTypeTaskValues = entityRepository
							.getsByIncidentTypeTask(imsIncidentTypeTask.getId(), false);

					if (!CollectionUtils.isEmpty(imsIncidentTypeTaskValues)) {

						for (ImsIncidentTypeTaskValue ImsIncidentTypeTaskValue : imsIncidentTypeTaskValues) {

							entityRepository.delete(ImsIncidentTypeTaskValue, conn, persistOperations);
						}
					}
				}
			}

			// Delete all ImsIncidentTaskPlans
			{
				ImsIncidentTaskPlanRepository entityRepository = (ImsIncidentTaskPlanRepository) getRepository(
						ImsIncidentTaskPlan.class);

				if (entityRepository != null) {

					List<ImsIncidentTaskPlan> imsIncidentTaskPlans = entityRepository
							.getsByTypeTask(imsIncidentTypeTask.getId(), false);

					if (!CollectionUtils.isEmpty(imsIncidentTaskPlans)) {

						for (ImsIncidentTaskPlan imsIncidentTaskPlan : imsIncidentTaskPlans) {

							entityRepository.delete(imsIncidentTaskPlan, conn, persistOperations);
						}
					}
				}
			}

			// Delete imsIncidentTypeTask in BBDD
			ImsIncidentTypeTask imsIncidentTypeTaskDeleted = null;

			imsIncidentTypeTaskDeleted = (ImsIncidentTypeTask) getObjectDao().delete(conn, imsIncidentTypeTaskAux);

			if (imsIncidentTypeTaskDeleted == null) {

				throw new EntityDBException(EntityDBException.Operation.DELETE, imsIncidentTypeTaskAux);
			}

			persistOperations.add(new MutablePair<>(PersistOperation.DELETE, imsIncidentTypeTaskAux));

			if (commit) {

				conn.commit();

				// Update cache
				persistOperations(persistOperations);
			}

			imsIncidentTypeTaskResult = imsIncidentTypeTaskDeleted;

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
			throw new EntityPersistenceException(EntityPersistenceException.Operation.DELETE, imsIncidentTypeTask);

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

		return imsIncidentTypeTaskResult;
	}

}
