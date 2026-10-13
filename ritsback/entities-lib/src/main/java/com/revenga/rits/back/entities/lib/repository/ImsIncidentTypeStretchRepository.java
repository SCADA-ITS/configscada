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
import com.revenga.rits.back.data.core.model.ImsIncidentTaskPlan;
import com.revenga.rits.back.data.core.model.ImsIncidentTypeRoadImpact;
import com.revenga.rits.back.data.core.model.ImsIncidentTypeStretch;
import com.revenga.rits.back.entities.lib.exception.EntityDBException;
import com.revenga.rits.back.entities.lib.exception.EntityPersistenceException;
import com.revenga.rits.back.entities.lib.repository.entity.EntityCacheable;
import com.revenga.rits.back.entities.lib.repository.entity.EntityPersistent;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class ImsIncidentTypeStretchRepository extends EntityRepository
		implements EntityCacheable<ImsIncidentTypeStretch>, EntityPersistent<ImsIncidentTypeStretch> {

	// region Constructors
	public ImsIncidentTypeStretchRepository() {

		super(ImsIncidentTypeStretch.class);
	}

	public ImsIncidentTypeStretchRepository(Boolean cacheable) {

		super(ImsIncidentTypeStretch.class, cacheable);
	}

	public ImsIncidentTypeStretchRepository(Boolean cacheable, Long cacheRetentionTime) {

		super(ImsIncidentTypeStretch.class, cacheable, cacheRetentionTime);
	}
	
	/**
	 * 
	 * @param cacheable
	 * @param repositories
	 */
	public ImsIncidentTypeStretchRepository(Boolean cacheable, List<EntityRepository> repositories) {

		super(ImsIncidentTypeStretch.class, cacheable, repositories);
	}

	/**
	 * 
	 * @param cacheable
	 * @param cacheRetentionTime
	 * @param repositories
	 */
	public ImsIncidentTypeStretchRepository(Boolean cacheable, Long cacheRetentionTime,
			List<EntityRepository> repositories) {

		super(ImsIncidentTypeStretch.class, cacheable, cacheRetentionTime, repositories);
	}

	// endregion Constructors

	// region getters
	public ImsIncidentTypeStretch get(Long incidentTypeId, Long stretchId, Boolean fillObject) {

		return (ImsIncidentTypeStretch) getFromCache(
				new String[] { String.valueOf(incidentTypeId), String.valueOf(stretchId) }, fillObject);
	}

	@Override
	public List<ImsIncidentTypeStretch> getAll(Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(be -> (ImsIncidentTypeStretch) be).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}
	
	/**
	 * 
	 * @param incidentTypeId
	 * @param fillObject
	 * @return
	 */
	public List<ImsIncidentTypeStretch> getsByIncidentType(Long incidentTypeId, Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(new String[] { String.valueOf(incidentTypeId), null },
				fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(be -> (ImsIncidentTypeStretch) be).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}
	
	// endregion getters

	// region puts
	@Override
	public ImsIncidentTypeStretch put(ImsIncidentTypeStretch entity) {

		return (ImsIncidentTypeStretch) putFromCache(entity);
	}

	@Override
	public List<ImsIncidentTypeStretch> put(List<ImsIncidentTypeStretch> entities) {

		List<BasicElement> basicElements = entities.stream().map(alarmConfig -> (BasicElement) alarmConfig)
				.collect(Collectors.toList());

		basicElements = putsFromCache(basicElements);

		return basicElements.stream().map(be -> (ImsIncidentTypeStretch) be).collect(Collectors.toList());
	}
	// endregion puts

	// region dels
	@Override
	public ImsIncidentTypeStretch del(ImsIncidentTypeStretch entity) {

		return (ImsIncidentTypeStretch) delFromCache(entity);

	}

	@Override
	public List<ImsIncidentTypeStretch> del(List<ImsIncidentTypeStretch> entities) {

		List<BasicElement> basicElements = entities.stream().map(alarmConfig -> (BasicElement) alarmConfig)
				.collect(Collectors.toList());

		basicElements = delFromCache(basicElements);

		return basicElements.stream().map(be -> (ImsIncidentTypeStretch) be).collect(Collectors.toList());
	}
	// endregion dels

	@Override
	public ImsIncidentTypeStretch create(ImsIncidentTypeStretch imsIncidentTypeStretch) {

		try {

			return create(imsIncidentTypeStretch, null, null);

		} catch (EntityPersistenceException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			return null;
		}
	}

	@Override
	public ImsIncidentTypeStretch create(ImsIncidentTypeStretch imsIncidentTypeStretch, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {

		Connection conn = null;
		boolean closeConnection = false;
		boolean commit = false;
		conn = connection;
		ImsIncidentTypeStretch imsIncidentTypeStretchResult = null;

		try {

			if (conn == null) {

				conn = DataSourceConnection.getInstance().getConnection();
				conn.setAutoCommit(false);
				closeConnection = true;
				commit = true;
				persistOperations = new ArrayList<>();
			}

			// Insert imsIncidentTypeStretch in BBDD
			ImsIncidentTypeStretch imsIncidentTypeStretchInserted = null;

			imsIncidentTypeStretchInserted = (ImsIncidentTypeStretch) getObjectDao().insert(conn, imsIncidentTypeStretch);

			if (imsIncidentTypeStretchInserted == null) {

				throw new EntityDBException(EntityDBException.Operation.INSERT, imsIncidentTypeStretch);
			}

			persistOperations.add(new MutablePair<>(PersistOperation.PUT, imsIncidentTypeStretchInserted));
			
			if (!CollectionUtils.isEmpty(getRepositories())) {

				// ImsIncidentTypeRoadImpacts
				if (!CollectionUtils.isEmpty(imsIncidentTypeStretch.getIncidentTypeRoadImpacts())) {

					ImsIncidentTypeRoadImpactRepository entityRepository = (ImsIncidentTypeRoadImpactRepository) getRepository(
							ImsIncidentTypeRoadImpact.class);

					if (entityRepository != null) {

						for (ImsIncidentTypeRoadImpact incidentTypeRoadImpact : imsIncidentTypeStretch
								.getIncidentTypeRoadImpacts()) {

							entityRepository.create(incidentTypeRoadImpact, conn, persistOperations);
						}
					}
				}
			}

			if (commit) {
				conn.commit();

				persistOperations(persistOperations);
			}

			imsIncidentTypeStretchResult = get(imsIncidentTypeStretchInserted.getIncidentTypeId(),
					imsIncidentTypeStretchInserted.getStretchId(), false);

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
			throw new EntityPersistenceException(EntityPersistenceException.Operation.CREATE, imsIncidentTypeStretch);

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

		return imsIncidentTypeStretchResult;
	}

	@Override
	public ImsIncidentTypeStretch clone(ImsIncidentTypeStretch entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ImsIncidentTypeStretch clone(ImsIncidentTypeStretch entity, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ImsIncidentTypeStretch update(ImsIncidentTypeStretch imsIncidentTypeStretch) {

		try {

			return update(imsIncidentTypeStretch, null, null);

		} catch (EntityPersistenceException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			return null;
		}
	}

	@Override
	public ImsIncidentTypeStretch update(ImsIncidentTypeStretch imsIncidentTypeStretch, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {

		Connection conn = null;
		boolean closeConnection = false;
		boolean commit = false;
		conn = connection;
		ImsIncidentTypeStretch imsIncidentTypeStretchResult = null;
		ImsIncidentTypeStretch imsIncidentTypeStretchAux = null;

		try {

			if (imsIncidentTypeStretch == null || imsIncidentTypeStretch.getIncidentTypeId() == null
					|| imsIncidentTypeStretch.getStretchId() == null) {

				throw new NullPointerException("imsIncidentTypeStretch is null");
			}

			imsIncidentTypeStretchAux = get(imsIncidentTypeStretch.getIncidentTypeId(),
					imsIncidentTypeStretch.getStretchId(), false);

			if (imsIncidentTypeStretchAux== null) {

				throw new NullPointerException("imsIncidentTypeStretch not found");
			} else {
	
				imsIncidentTypeStretchAux = (ImsIncidentTypeStretch) imsIncidentTypeStretchAux.clone();
			}

			if (imsIncidentTypeStretch.getAlias() != null)
				imsIncidentTypeStretchAux.setAlias(imsIncidentTypeStretch.getAlias());
			if (imsIncidentTypeStretch.getDescription() != null)
				imsIncidentTypeStretchAux.setDescription(imsIncidentTypeStretch.getDescription());
			
			imsIncidentTypeStretchAux.setDefaultLocationId(imsIncidentTypeStretch.getDefaultLocationId());

			if (imsIncidentTypeStretch.getOperationalPlanUrl() != null)
				imsIncidentTypeStretchAux.setOperationalPlanUrl(imsIncidentTypeStretch.getOperationalPlanUrl());
			if (imsIncidentTypeStretch.getMultiInstance() != null)
				imsIncidentTypeStretchAux.setMultiInstance(imsIncidentTypeStretch.getMultiInstance());
			if (imsIncidentTypeStretch.getCctvScenarioId() != null) {
				
				imsIncidentTypeStretchAux.setCctvScenarioId(imsIncidentTypeStretch.getCctvScenarioId());
			}
			else {
				
				imsIncidentTypeStretchAux.setCctvScenarioId(null);
			}
				

			if (conn == null) {

				conn = DataSourceConnection.getInstance().getConnection();
				conn.setAutoCommit(false);
				closeConnection = true;
				commit = true;
				persistOperations = new ArrayList<>();
			}

			// Update imsIncidentTypeStretch in BBDD
			ImsIncidentTypeStretch imsIncidentTypeStretchUpdated = null;

			imsIncidentTypeStretchUpdated = (ImsIncidentTypeStretch) getObjectDao().update(conn, imsIncidentTypeStretchAux);

			if (imsIncidentTypeStretchUpdated == null) {

				throw new EntityDBException(EntityDBException.Operation.UPDATE, imsIncidentTypeStretchAux);
			}

			persistOperations.add(new MutablePair<>(PersistOperation.PUT, imsIncidentTypeStretchUpdated));
			
			if (!CollectionUtils.isEmpty(getRepositories())) {

				// Update ImsIncidentTypeRoadImpacts
				ImsIncidentTypeRoadImpactRepository entityRepository = (ImsIncidentTypeRoadImpactRepository) getRepository(
						ImsIncidentTypeRoadImpact.class);

				if (entityRepository != null) {

					List<ImsIncidentTypeRoadImpact> incidentTypeRoadImpacts = entityRepository
							.getsByIncidentTypeStretch(imsIncidentTypeStretch.getIncidentTypeId(),
									imsIncidentTypeStretch.getStretchId(), false);

					// Create all ImsIncidentTypeRoadImpacts
					if (imsIncidentTypeStretch.getIncidentTypeRoadImpacts() != null) {
						
						// Delete all ImsIncidentTypeRoadImpacts
						if (!CollectionUtils.isEmpty(incidentTypeRoadImpacts)) {

							for (ImsIncidentTypeRoadImpact incidentTypeRoadImpact : incidentTypeRoadImpacts) {

								entityRepository.delete(incidentTypeRoadImpact, conn, persistOperations);
							}
						}
						
						for (ImsIncidentTypeRoadImpact incidentTypeRoadImpact : imsIncidentTypeStretch
								.getIncidentTypeRoadImpacts()) {

								entityRepository.create(incidentTypeRoadImpact, conn, persistOperations);
						}
					}
				}
			}
			

			if (commit) {

				conn.commit();

				persistOperations(persistOperations);
			}

			imsIncidentTypeStretchResult = imsIncidentTypeStretchUpdated;

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
			throw new EntityPersistenceException(EntityPersistenceException.Operation.UPDATE, imsIncidentTypeStretch);

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

		return imsIncidentTypeStretchResult;
	}

	@Override
	public ImsIncidentTypeStretch delete(ImsIncidentTypeStretch imsIncidentTypeStretch) {

		try {

			return delete(imsIncidentTypeStretch, null, null);

		} catch (EntityPersistenceException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			return null;
		}
	}

	@Override
	public ImsIncidentTypeStretch delete(ImsIncidentTypeStretch imsIncidentTypeStretch, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {

		Connection conn = null;
		boolean closeConnection = false;
		boolean commit = false;
		conn = connection;
		ImsIncidentTypeStretch imsIncidentAlarmConditionAlarmResult = null;
		ImsIncidentTypeStretch imsIncidentAlarmConditionAlarmAux = null;

		try {

			if (imsIncidentTypeStretch == null || imsIncidentTypeStretch.getIncidentTypeId() == null
					|| imsIncidentTypeStretch.getStretchId() == null) {

				throw new NullPointerException("imsIncidentTypeStretch is null");
			}

			imsIncidentAlarmConditionAlarmAux = get(imsIncidentTypeStretch.getIncidentTypeId(),
					imsIncidentTypeStretch.getStretchId(), false);

			if (imsIncidentAlarmConditionAlarmAux == null) {

				throw new NullPointerException("imsIncidentTypeStretch not found");
			}

			if (conn == null) {

				conn = DataSourceConnection.getInstance().getConnection();
				conn.setAutoCommit(false);
				closeConnection = true;
				commit = true;
				persistOperations = new ArrayList<>();
			}
			
			if (!CollectionUtils.isEmpty(getRepositories())) {
				
				//Delete all ImsIncidentTypeRoadImpacts
				{
					ImsIncidentTypeRoadImpactRepository entityRepository = (ImsIncidentTypeRoadImpactRepository) getRepository(
							ImsIncidentTypeRoadImpact.class);

					if (entityRepository != null) {

						List<ImsIncidentTypeRoadImpact> incidentTypeRoadImpacts = entityRepository
								.getsByIncidentTypeStretch(imsIncidentTypeStretch.getIncidentTypeId(),
										imsIncidentTypeStretch.getStretchId(), false);
						
						if (!CollectionUtils.isEmpty(incidentTypeRoadImpacts)) {

							for (ImsIncidentTypeRoadImpact incidentTypeRoadImpact : incidentTypeRoadImpacts) {

								entityRepository.delete(incidentTypeRoadImpact, conn, persistOperations);
							}
						}
					}
				}
				
				//Delete all ImsIncidentAlarmConditions
				{
					ImsIncidentAlarmConditionRepository entityRepository = (ImsIncidentAlarmConditionRepository) getRepository(
							ImsIncidentAlarmCondition.class);

					if (entityRepository != null) {

						List<ImsIncidentAlarmCondition> imsIncidentAlarmConditions = entityRepository.getsByTypeStretch(
								imsIncidentTypeStretch.getIncidentTypeId(), imsIncidentTypeStretch.getStretchId(),
								false);

						if (!CollectionUtils.isEmpty(imsIncidentAlarmConditions)) {

							for (ImsIncidentAlarmCondition imsIncidentAlarmCondition : imsIncidentAlarmConditions) {

								entityRepository.delete(imsIncidentAlarmCondition, conn, persistOperations);
							}
						}
					}
				}
				
				//Delete all ImsIncidentTaskPlans
				{
					ImsIncidentTaskPlanRepository entityRepository = (ImsIncidentTaskPlanRepository) getRepository(
							ImsIncidentTaskPlan.class);

					if (entityRepository != null) {

						List<ImsIncidentTaskPlan> imsIncidentTaskPlans = entityRepository.getsByIncidentTypeStretch(
								imsIncidentTypeStretch.getIncidentTypeId(), imsIncidentTypeStretch.getStretchId(),
								false);

						if (!CollectionUtils.isEmpty(imsIncidentTaskPlans)) {

							for (ImsIncidentTaskPlan imsIncidentTaskPlan : imsIncidentTaskPlans) {

								entityRepository.delete(imsIncidentTaskPlan, conn, persistOperations);
							}
						}
					}
				}
				
			}

			// Delete imsIncidentTypeStretch in BBDD
			ImsIncidentTypeStretch imsIncidentTypeStretchDeleted = null;

			imsIncidentTypeStretchDeleted = (ImsIncidentTypeStretch) getObjectDao().delete(conn,
					imsIncidentAlarmConditionAlarmAux);

			if (imsIncidentTypeStretchDeleted == null) {

				throw new EntityDBException(EntityDBException.Operation.DELETE, imsIncidentAlarmConditionAlarmAux);
			}

			persistOperations.add(new MutablePair<>(PersistOperation.DELETE, imsIncidentAlarmConditionAlarmAux));

			if (commit) {

				conn.commit();

				// Update cache
				persistOperations(persistOperations);
			}

			imsIncidentAlarmConditionAlarmResult = imsIncidentTypeStretchDeleted;

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
			throw new EntityPersistenceException(EntityPersistenceException.Operation.DELETE, imsIncidentTypeStretch);

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
