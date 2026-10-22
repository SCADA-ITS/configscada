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
import com.revenga.rits.back.data.core.model.ImsIncidentType;
import com.revenga.rits.back.data.core.model.ImsIncidentTypeCause;
import com.revenga.rits.back.data.core.model.ImsIncidentTypeStateTransition;
import com.revenga.rits.back.data.core.model.ImsIncidentTypeStretch;
import com.revenga.rits.back.data.core.model.ImsIncidentTypeTask;
import com.revenga.rits.back.entities.lib.exception.EntityDBException;
import com.revenga.rits.back.entities.lib.exception.EntityPersistenceException;
import com.revenga.rits.back.entities.lib.repository.entity.EntityCacheable;
import com.revenga.rits.back.entities.lib.repository.entity.EntityPersistent;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class ImsIncidentTypeRepository extends EntityRepository
		implements EntityCacheable<ImsIncidentType>, EntityPersistent<ImsIncidentType> {

	// region Constructors
	/**
	 * 
	 */
	public ImsIncidentTypeRepository() {

		super(ImsIncidentType.class);
	}

	/**
	 * 
	 * @param cacheable
	 */
	public ImsIncidentTypeRepository(Boolean cacheable) {

		super(ImsIncidentType.class, cacheable);
	}

	/**
	 * 
	 * @param cacheable
	 * @param cacheRetentionTime
	 */
	public ImsIncidentTypeRepository(Boolean cacheable, Long cacheRetentionTime) {

		super(ImsIncidentType.class, cacheable, cacheRetentionTime);
	}
	
	/**
	 * 
	 * @param cacheable
	 * @param repositories
	 */
	public ImsIncidentTypeRepository(Boolean cacheable, List<EntityRepository> repositories) {

		super(ImsIncidentType.class, cacheable, repositories);
	}

	/**
	 * 
	 * @param cacheable
	 * @param cacheRetentionTime
	 * @param repositories
	 */
	public ImsIncidentTypeRepository(Boolean cacheable, Long cacheRetentionTime,
			List<EntityRepository> repositories) {

		super(ImsIncidentType.class, cacheable, cacheRetentionTime, repositories);
	}
	// endregion Constructors

	// region getters
	/**
	 * 
	 * @param id
	 * @param fillObject
	 * @return An object of ImsIncidentType
	 */
	public ImsIncidentType get(Long id, Boolean fillObject) {

		return (ImsIncidentType) getFromCache(new String[] { String.valueOf(id) }, fillObject);
	}

	/**
	 * Return a list of all ImsIncidentType object that exist into the database.
	 */
	@Override
	public List<ImsIncidentType> getAll(Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(be -> (ImsIncidentType) be).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}
	
	/**
	 * 
	 * @param incidentGroupId
	 * @param fillObject
	 * @return Return a list of the Ims Incident Type objects that exist into the database than they incident GroupId is equal to the incidentGroupId passed as parameter
	 */
	public List<ImsIncidentType> getByIncidentGroup(Long incidentGroupId, Boolean fillObject) {

		if (incidentGroupId != null) {
			List<BasicElement> basicElements = getsFromCache(fillObject);

			if (!CollectionUtils.isEmpty(basicElements)) {

				List<ImsIncidentType> incidentTypes = basicElements.stream().map(be -> (ImsIncidentType) be)
						.collect(Collectors.toList());
				
				return incidentTypes.stream().filter(be -> incidentGroupId.equals(be.getIncidentGroupId()))
						.collect(Collectors.toList());
			}
		}
		return Collections.emptyList();
	}	
	// endregion getters

	// region puts
	/**
	 * 
	 */
	@Override
	public ImsIncidentType put(ImsIncidentType entity) {

		return (ImsIncidentType) putFromCache(entity);
	}

	/**
	 * 
	 */
	@Override
	public List<ImsIncidentType> put(List<ImsIncidentType> entities) {

		List<BasicElement> basicElements = entities.stream().map(alarmConfig -> (BasicElement) alarmConfig)
				.collect(Collectors.toList());

		basicElements = putsFromCache(basicElements);

		return basicElements.stream().map(be -> (ImsIncidentType) be).collect(Collectors.toList());
	}
	// endregion puts

	// region dels
	/**
	 * 
	 */
	@Override
	public ImsIncidentType del(ImsIncidentType entity) {

		return (ImsIncidentType) delFromCache(entity);

	}

	/**
	 * 
	 */
	@Override
	public List<ImsIncidentType> del(List<ImsIncidentType> entities) {

		List<BasicElement> basicElements = entities.stream().map(alarmConfig -> (BasicElement) alarmConfig)
				.collect(Collectors.toList());

		basicElements = delFromCache(basicElements);

		return basicElements.stream().map(be -> (ImsIncidentType) be).collect(Collectors.toList());
	}
	// endregion dels

	/**
	 * 
	 */
	@Override
	public ImsIncidentType create(ImsIncidentType imsIncidentType) {

		try {

			return create(imsIncidentType, null, null);

		} catch (EntityPersistenceException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			return null;
		}
	}

	/**
	 * 
	 */
	@Override
	public ImsIncidentType create(ImsIncidentType imsIncidentType, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {

		Connection conn = null;
		boolean closeConnection = false;
		boolean commit = false;
		conn = connection;
		ImsIncidentType imsIncidentTypeResult = null;

		try {

			if (conn == null) {

				conn = DataSourceConnection.getInstance().getConnection();
				conn.setAutoCommit(false);
				closeConnection = true;
				commit = true;
				persistOperations = new ArrayList<>();
			}

			// Insert imsIncidentType in BBDD
			ImsIncidentType imsIncidentTypeInserted = null;

			imsIncidentTypeInserted = (ImsIncidentType) getObjectDao().insert(conn, imsIncidentType);

			if (imsIncidentTypeInserted == null) {

				throw new EntityDBException(EntityDBException.Operation.INSERT, imsIncidentType);
			}

			persistOperations.add(new MutablePair<>(PersistOperation.PUT, imsIncidentTypeInserted));
			
			if (!CollectionUtils.isEmpty(getRepositories())) {

				// ImsIncidentTypeCauses
				if (!CollectionUtils.isEmpty(imsIncidentType.getIncidentTypeCauses())) {

					ImsIncidentTypeCauseRepository entityRepository = (ImsIncidentTypeCauseRepository) getRepository(
							ImsIncidentTypeCause.class);

					if (entityRepository != null) {

						for (ImsIncidentTypeCause imsIncidentTypeCause : imsIncidentType
								.getIncidentTypeCauses()) {

							imsIncidentTypeCause.setIncidentTypeId(imsIncidentType.getId());
							entityRepository.create(imsIncidentTypeCause, conn, persistOperations);
						}
					}
				}
			}	

			if (commit) {
				conn.commit();

				persistOperations(persistOperations);
				
				imsIncidentTypeResult = get(imsIncidentTypeInserted.getId(), false);
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
			throw new EntityPersistenceException(EntityPersistenceException.Operation.CREATE, imsIncidentType);

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

		return imsIncidentTypeResult;
	}

	/**
	 * 
	 */
	@Override
	public ImsIncidentType clone(ImsIncidentType entity) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 
	 */
	@Override
	public ImsIncidentType clone(ImsIncidentType entity, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 
	 */
	@Override
	public ImsIncidentType update(ImsIncidentType imsIncidentType) {

		try {

			return update(imsIncidentType, null, null);

		} catch (EntityPersistenceException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			return null;
		}
	}

	/**
	 * 
	 */
	@Override
	public ImsIncidentType update(ImsIncidentType imsIncidentType, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {

		Connection conn = null;
		boolean closeConnection = false;
		boolean commit = false;
		conn = connection;
		ImsIncidentType imsIncidentTypeResult = null;
		ImsIncidentType imsIncidentTypeAux = null;

		try {

			if (imsIncidentType == null || imsIncidentType.getId() == null) {

				throw new NullPointerException("imsIncidentType is null");
			}

			imsIncidentTypeAux = get(imsIncidentType.getId(), false);

			if (imsIncidentTypeAux == null) {

				throw new NullPointerException("imsIncidentType not found");
			} else {
	
				imsIncidentTypeAux = (ImsIncidentType) imsIncidentTypeAux.clone();
			}

			if (imsIncidentType.getAlias() != null)
				imsIncidentTypeAux.setAlias(imsIncidentType.getAlias());
			if (imsIncidentType.getDescription() != null)
				imsIncidentTypeAux.setDescription(imsIncidentType.getDescription());
			if (imsIncidentType.getIniIncidentStateId() != null)
				imsIncidentTypeAux.setIniIncidentStateId(imsIncidentType.getIniIncidentStateId());

			if (imsIncidentType.getIncidentGroupId() != null)
				imsIncidentTypeAux.setIncidentGroupId(imsIncidentType.getIncidentGroupId());
			
			if (imsIncidentType.getIncidentLevelId() != null)
				imsIncidentTypeAux.setIncidentLevelId(imsIncidentType.getIncidentLevelId());
			if (imsIncidentType.getDefaultUserId() != null)
				imsIncidentTypeAux.setDefaultUserId(imsIncidentType.getDefaultUserId());

			if (imsIncidentType.getValidForMin() != null)
				imsIncidentTypeAux.setValidForMin(imsIncidentType.getValidForMin());
			

			if (imsIncidentType.getAutoConfirmIncident() != null)
				imsIncidentTypeAux.setAutoConfirmIncident(imsIncidentType.getAutoConfirmIncident());

			if (imsIncidentType.getCancelIncident() != null)
				imsIncidentTypeAux.setCancelIncident(imsIncidentType.getCancelIncident());
			
			if (conn == null) {

				conn = DataSourceConnection.getInstance().getConnection();
				conn.setAutoCommit(false);
				closeConnection = true;
				commit = true;
				persistOperations = new ArrayList<>();
			}

			// Update imsIncidentType in BBDD
			ImsIncidentType imsIncidentTypeUpdated = null;

			imsIncidentTypeUpdated = (ImsIncidentType) getObjectDao().update(conn, imsIncidentTypeAux);

			if (imsIncidentTypeUpdated == null) {

				throw new EntityDBException(EntityDBException.Operation.UPDATE, imsIncidentTypeAux);
			}

			persistOperations.add(new MutablePair<>(PersistOperation.PUT, imsIncidentTypeUpdated));
			
			if (!CollectionUtils.isEmpty(getRepositories())) {
				
				if (imsIncidentType.getIncidentTypeCauses() != null) {

					ImsIncidentTypeCauseRepository entityRepository = (ImsIncidentTypeCauseRepository) getRepository(
							ImsIncidentTypeCause.class);

					if (entityRepository != null) {

						List<ImsIncidentTypeCause> imsIncidentTypeCauses = entityRepository
								.getsByIncidentType(imsIncidentType.getId(), false);

						// Delete all incidentTypeCauses
						if (!CollectionUtils.isEmpty(imsIncidentTypeCauses)) {

							for (ImsIncidentTypeCause imsIncidentTypeCause : imsIncidentTypeCauses) {

								entityRepository.delete(imsIncidentTypeCause, conn, persistOperations);
							}
						}

						// Create all incidentTypeCauses
						for (ImsIncidentTypeCause imsIncidentTypeCause : imsIncidentType.getIncidentTypeCauses()) {

							imsIncidentTypeCause.setIncidentTypeId(imsIncidentType.getId());
							entityRepository.create(imsIncidentTypeCause, conn, persistOperations);
						}
					}
				}
			}

			if (commit) {

				conn.commit();

				persistOperations(persistOperations);
			}

			imsIncidentTypeResult = imsIncidentTypeUpdated;

		} catch (CloneNotSupportedException | EntityDBException | SQLException | NullPointerException | IOException e) {

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
			throw new EntityPersistenceException(EntityPersistenceException.Operation.UPDATE, imsIncidentType);

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

		return imsIncidentTypeResult;
	}

	/**
	 * 
	 */
	@Override
	public ImsIncidentType delete(ImsIncidentType imsIncidentType) {

		try {

			return delete(imsIncidentType, null, null);

		} catch (EntityPersistenceException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			return null;
		}
	}

	/**
	 * 
	 */
	@Override
	public ImsIncidentType delete(ImsIncidentType imsIncidentType, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {

		Connection conn = null;
		boolean closeConnection = false;
		boolean commit = false;
		conn = connection;
		ImsIncidentType imsIncidentAlarmConditionAlarmResult = null;
		ImsIncidentType imsIncidentAlarmConditionAlarmAux = null;

		try {

			if (imsIncidentType == null || imsIncidentType.getId() == null) {

				throw new NullPointerException("imsIncidentType is null");
			}

			imsIncidentAlarmConditionAlarmAux = get(imsIncidentType.getId(), false);

			if (imsIncidentAlarmConditionAlarmAux == null) {

				throw new NullPointerException("imsIncidentType not found");
			}

			if (conn == null) {

				conn = DataSourceConnection.getInstance().getConnection();
				conn.setAutoCommit(false);
				closeConnection = true;
				commit = true;
				persistOperations = new ArrayList<>();
			}
			
			if (!CollectionUtils.isEmpty(getRepositories())) {
				
				// Delete all ImsIncidentTypeCauses
				{
					ImsIncidentTypeCauseRepository entityRepository = (ImsIncidentTypeCauseRepository) getRepository(
							ImsIncidentTypeCause.class);

					if (entityRepository != null) {

						List<ImsIncidentTypeCause> imsIncidentTypeCauses = entityRepository
								.getsByIncidentType(imsIncidentType.getId(), false);

						if (!CollectionUtils.isEmpty(imsIncidentTypeCauses)) {

							for (ImsIncidentTypeCause imsIncidentTypeCause : imsIncidentTypeCauses) {

								entityRepository.delete(imsIncidentTypeCause, conn, persistOperations);
							}
						}
					}
				}
				
				
				// Delete all ImsIncidentTypeStateTransitions
				{
					ImsIncidentTypeStateTransitionRepository entityRepository = (ImsIncidentTypeStateTransitionRepository) getRepository(
							ImsIncidentTypeStateTransition.class);

					if (entityRepository != null) {

						List<ImsIncidentTypeStateTransition> incidentTypeStateTransitions = entityRepository
								.getsByIncidentType(imsIncidentType.getId(), false);

						if (!CollectionUtils.isEmpty(incidentTypeStateTransitions)) {

							for (ImsIncidentTypeStateTransition imsIncidentTypeStateTransition : incidentTypeStateTransitions) {

								entityRepository.delete(imsIncidentTypeStateTransition, conn, persistOperations);
							}
						}
					}
				}
				
				
				
				// Delete all ImsIncidentTypeTasks
				{
					ImsIncidentTypeTaskRepository entityRepository = (ImsIncidentTypeTaskRepository) getRepository(
							ImsIncidentTypeTask.class);

					if (entityRepository != null) {

						List<ImsIncidentTypeTask> imsIncidentTypeTasks = entityRepository
								.getByIncidentType(imsIncidentType.getId(), false);

						if (!CollectionUtils.isEmpty(imsIncidentTypeTasks)) {

							for (ImsIncidentTypeTask ImsIncidentTypeTask : imsIncidentTypeTasks) {

								entityRepository.delete(ImsIncidentTypeTask, conn, persistOperations);
							}
						}
					}
				}
				
				// Delete all ImsIncidentTypeStretchs
				{
					ImsIncidentTypeStretchRepository entityRepository = (ImsIncidentTypeStretchRepository) getRepository(
							ImsIncidentTypeStretch.class);

					if (entityRepository != null) {

						List<ImsIncidentTypeStretch> imsIncidentTypeStretchs = entityRepository
								.getsByIncidentType(imsIncidentType.getId(), false);

						if (!CollectionUtils.isEmpty(imsIncidentTypeStretchs)) {

							for (ImsIncidentTypeStretch imsIncidentTypeStretch : imsIncidentTypeStretchs) {

								entityRepository.delete(imsIncidentTypeStretch, conn, persistOperations);
							}
						}
					}
				}
				
				
			}

			// Delete imsIncidentType in BBDD
			ImsIncidentType imsIncidentTypeDeleted = null;

			imsIncidentTypeDeleted = (ImsIncidentType) getObjectDao().delete(conn, imsIncidentAlarmConditionAlarmAux);

			if (imsIncidentTypeDeleted == null) {

				throw new EntityDBException(EntityDBException.Operation.DELETE, imsIncidentAlarmConditionAlarmAux);
			}

			persistOperations.add(new MutablePair<>(PersistOperation.DELETE, imsIncidentAlarmConditionAlarmAux));

			if (commit) {

				conn.commit();

				// Update cache
				persistOperations(persistOperations);
			}

			imsIncidentAlarmConditionAlarmResult = imsIncidentTypeDeleted;

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
			throw new EntityPersistenceException(EntityPersistenceException.Operation.DELETE, imsIncidentType);

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

		return imsIncidentAlarmConditionAlarmResult;
	}

}
