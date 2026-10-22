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
import com.revenga.rits.back.data.core.model.ImsIncidentAlarmConditionAlarm;
import com.revenga.rits.back.entities.lib.exception.EntityDBException;
import com.revenga.rits.back.entities.lib.exception.EntityPersistenceException;
import com.revenga.rits.back.entities.lib.repository.entity.EntityCacheable;
import com.revenga.rits.back.entities.lib.repository.entity.EntityPersistent;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository.PersistOperation;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class ImsIncidentAlarmConditionAlarmRepository extends EntityRepository
		implements EntityCacheable<ImsIncidentAlarmConditionAlarm>, EntityPersistent<ImsIncidentAlarmConditionAlarm> {

	// region Constructors
	public ImsIncidentAlarmConditionAlarmRepository() {

		super(ImsIncidentAlarmConditionAlarm.class);
	}

	public ImsIncidentAlarmConditionAlarmRepository(Boolean cacheable) {

		super(ImsIncidentAlarmConditionAlarm.class, cacheable);
	}

	public ImsIncidentAlarmConditionAlarmRepository(Boolean cacheable, Long cacheRetentionTime) {

		super(ImsIncidentAlarmConditionAlarm.class, cacheable, cacheRetentionTime);
	}
	
	/**
	 * 
	 * @param cacheable
	 * @param repositories
	 */
	public ImsIncidentAlarmConditionAlarmRepository(Boolean cacheable, List<EntityRepository> repositories) {

		super(ImsIncidentAlarmConditionAlarm.class, cacheable, repositories);
	}

	/**
	 * 
	 * @param cacheable
	 * @param cacheRetentionTime
	 * @param repositories
	 */
	public ImsIncidentAlarmConditionAlarmRepository(Boolean cacheable, Long cacheRetentionTime,
			List<EntityRepository> repositories) {

		super(ImsIncidentAlarmConditionAlarm.class, cacheable, cacheRetentionTime, repositories);
	}

	// endregion Constructors

	// region getters
	public ImsIncidentAlarmConditionAlarm get(Long id, Boolean fillObject) {

		return (ImsIncidentAlarmConditionAlarm) getFromCache(new String[] { String.valueOf(id) }, fillObject);
	}

	@Override
	public List<ImsIncidentAlarmConditionAlarm> getAll(Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(be -> (ImsIncidentAlarmConditionAlarm) be).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}
	
	public List<ImsIncidentAlarmConditionAlarm> getsByIncidentAlarmCondition(Long incidentAlarmConditionId, Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			List<ImsIncidentAlarmConditionAlarm> imsIncidentAlarmConditionAlarms = basicElements.stream().map(be -> (ImsIncidentAlarmConditionAlarm) be)
					.collect(Collectors.toList());

			return imsIncidentAlarmConditionAlarms.stream().filter(e -> e.getIncidentAlarmConditionId().equals(incidentAlarmConditionId)).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}
	
	// endregion getters

	// region puts
	@Override
	public ImsIncidentAlarmConditionAlarm put(ImsIncidentAlarmConditionAlarm entity) {

		return (ImsIncidentAlarmConditionAlarm) putFromCache(entity);
	}

	@Override
	public List<ImsIncidentAlarmConditionAlarm> put(List<ImsIncidentAlarmConditionAlarm> entities) {

		List<BasicElement> basicElements = entities.stream().map(alarmConfig -> (BasicElement) alarmConfig)
				.collect(Collectors.toList());

		basicElements = putsFromCache(basicElements);

		return basicElements.stream().map(be -> (ImsIncidentAlarmConditionAlarm) be).collect(Collectors.toList());
	}
	// endregion puts

	// region dels
	@Override
	public ImsIncidentAlarmConditionAlarm del(ImsIncidentAlarmConditionAlarm entity) {

		return (ImsIncidentAlarmConditionAlarm) delFromCache(entity);

	}

	@Override
	public List<ImsIncidentAlarmConditionAlarm> del(List<ImsIncidentAlarmConditionAlarm> entities) {

		List<BasicElement> basicElements = entities.stream().map(alarmConfig -> (BasicElement) alarmConfig)
				.collect(Collectors.toList());

		basicElements = delFromCache(basicElements);

		return basicElements.stream().map(be -> (ImsIncidentAlarmConditionAlarm) be).collect(Collectors.toList());
	}
	// endregion dels

	@Override
	public ImsIncidentAlarmConditionAlarm create(ImsIncidentAlarmConditionAlarm imsIncidentAlarmConditionAlarm) {

		try {

			return create(imsIncidentAlarmConditionAlarm, null, null);

		} catch (EntityPersistenceException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			return null;
		}
	}

	@Override
	public ImsIncidentAlarmConditionAlarm create(ImsIncidentAlarmConditionAlarm imsIncidentAlarmConditionAlarm, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {

		Connection conn = null;
		boolean closeConnection = false;
		boolean commit = false;
		conn = connection;
		ImsIncidentAlarmConditionAlarm imsIncidentAlarmConditionAlarmResult = null;

		try {

			if (conn == null) {

				conn = DataSourceConnection.getInstance().getConnection();
				conn.setAutoCommit(false);
				closeConnection = true;
				commit = true;
				persistOperations = new ArrayList<>();
			}

			// Insert imsIncidentAlarmConditionAlarm in BBDD
			ImsIncidentAlarmConditionAlarm imsIncidentAlarmConditionAlarmInserted = null;

			imsIncidentAlarmConditionAlarmInserted = (ImsIncidentAlarmConditionAlarm) getObjectDao().insert(conn, imsIncidentAlarmConditionAlarm);

			if (imsIncidentAlarmConditionAlarmInserted == null) {

				throw new EntityDBException(EntityDBException.Operation.INSERT, imsIncidentAlarmConditionAlarm);
			}

			persistOperations.add(new MutablePair<>(PersistOperation.PUT, imsIncidentAlarmConditionAlarmInserted));


			if (commit) {
				conn.commit();

				persistOperations(persistOperations);
				
				imsIncidentAlarmConditionAlarmResult = get(imsIncidentAlarmConditionAlarmInserted.getId(), false);
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
			throw new EntityPersistenceException(EntityPersistenceException.Operation.CREATE, imsIncidentAlarmConditionAlarm);

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

	@Override
	public ImsIncidentAlarmConditionAlarm clone(ImsIncidentAlarmConditionAlarm entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ImsIncidentAlarmConditionAlarm clone(ImsIncidentAlarmConditionAlarm entity, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ImsIncidentAlarmConditionAlarm update(ImsIncidentAlarmConditionAlarm imsIncidentAlarmConditionAlarm) {

		try {

			return update(imsIncidentAlarmConditionAlarm, null, null);

		} catch (EntityPersistenceException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			return null;
		}
	}

	@Override
	public ImsIncidentAlarmConditionAlarm update(ImsIncidentAlarmConditionAlarm imsIncidentAlarmConditionAlarm, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {

		Connection conn = null;
		boolean closeConnection = false;
		boolean commit = false;
		conn = connection;
		ImsIncidentAlarmConditionAlarm imsIncidentAlarmConditionAlarmResult = null;
		ImsIncidentAlarmConditionAlarm imsIncidentAlarmConditionAlarmAux = null;

		try {

			if (imsIncidentAlarmConditionAlarm == null || imsIncidentAlarmConditionAlarm.getId() == null) {

				throw new NullPointerException("ImsIncidentAlarmConditionAlarm is null");
			}

			imsIncidentAlarmConditionAlarmAux = get(imsIncidentAlarmConditionAlarm.getId(), false);

			if (imsIncidentAlarmConditionAlarmAux == null) {

				throw new NullPointerException("ImsIncidentAlarmConditionAlarm not found");
			} else {

				imsIncidentAlarmConditionAlarmAux = (ImsIncidentAlarmConditionAlarm) imsIncidentAlarmConditionAlarmAux
						.clone();
			}
			
			if (imsIncidentAlarmConditionAlarm.getAlarmConfigId() != null)
				imsIncidentAlarmConditionAlarmAux.setAlarmConfigId(imsIncidentAlarmConditionAlarm.getAlarmConfigId());
			if (imsIncidentAlarmConditionAlarm.getElementTypeId() != null)
				imsIncidentAlarmConditionAlarmAux.setElementTypeId(imsIncidentAlarmConditionAlarm.getElementTypeId());
			if (imsIncidentAlarmConditionAlarm.getElementId() != null)
				imsIncidentAlarmConditionAlarmAux.setElementId(imsIncidentAlarmConditionAlarm.getElementId());


			if (conn == null) {

				conn = DataSourceConnection.getInstance().getConnection();
				conn.setAutoCommit(false);
				closeConnection = true;
				commit = true;
				persistOperations = new ArrayList<>();
			}

			// Update ImsIncidentAlarmConditionAlarm in BBDD
			ImsIncidentAlarmConditionAlarm imsIncidentAlarmConditionAlarmUpdated = null;

			imsIncidentAlarmConditionAlarmUpdated = (ImsIncidentAlarmConditionAlarm) getObjectDao().update(conn, imsIncidentAlarmConditionAlarmAux);

			if (imsIncidentAlarmConditionAlarmUpdated == null) {

				throw new EntityDBException(EntityDBException.Operation.UPDATE, imsIncidentAlarmConditionAlarmAux);
			}

			persistOperations.add(new MutablePair<>(PersistOperation.PUT, imsIncidentAlarmConditionAlarmUpdated));

			if (commit) {

				conn.commit();

				persistOperations(persistOperations);
			}

			imsIncidentAlarmConditionAlarmResult = imsIncidentAlarmConditionAlarmUpdated;

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
			throw new EntityPersistenceException(EntityPersistenceException.Operation.UPDATE, imsIncidentAlarmConditionAlarm);

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

	@Override
	public ImsIncidentAlarmConditionAlarm delete(ImsIncidentAlarmConditionAlarm imsIncidentAlarmConditionAlarm) {

		try {

			return delete(imsIncidentAlarmConditionAlarm, null, null);

		} catch (EntityPersistenceException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			return null;
		}
	}

	@Override
	public ImsIncidentAlarmConditionAlarm delete(ImsIncidentAlarmConditionAlarm imsIncidentAlarmConditionAlarm, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {

		Connection conn = null;
		boolean closeConnection = false;
		boolean commit = false;
		conn = connection;
		ImsIncidentAlarmConditionAlarm imsIncidentAlarmConditionAlarmResult = null;
		ImsIncidentAlarmConditionAlarm imsIncidentAlarmConditionAlarmAux = null;

		try {

			if (imsIncidentAlarmConditionAlarm == null || imsIncidentAlarmConditionAlarm.getId() == null) {

				throw new NullPointerException("ImsIncidentAlarmConditionAlarm is null");
			}

			imsIncidentAlarmConditionAlarmAux = get(imsIncidentAlarmConditionAlarm.getId(), false);

			if (imsIncidentAlarmConditionAlarmAux == null) {

				throw new NullPointerException("ImsIncidentAlarmConditionAlarm not found");
			}

			if (conn == null) {

				conn = DataSourceConnection.getInstance().getConnection();
				conn.setAutoCommit(false);
				closeConnection = true;
				commit = true;
				persistOperations = new ArrayList<>();
			}

			// Delete imsIncidentAlarmConditionAlarm in BBDD
			ImsIncidentAlarmConditionAlarm imsIncidentAlarmConditionAlarmDeleted = null;

			imsIncidentAlarmConditionAlarmDeleted = (ImsIncidentAlarmConditionAlarm) getObjectDao().delete(conn, imsIncidentAlarmConditionAlarmAux);

			if (imsIncidentAlarmConditionAlarmDeleted == null) {

				throw new EntityDBException(EntityDBException.Operation.DELETE, imsIncidentAlarmConditionAlarmAux);
			}

			persistOperations.add(new MutablePair<>(PersistOperation.DELETE, imsIncidentAlarmConditionAlarmAux));

			if (commit) {

				conn.commit();

				// Update cache
				persistOperations(persistOperations);
			}

			imsIncidentAlarmConditionAlarmResult = imsIncidentAlarmConditionAlarmDeleted;

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
			throw new EntityPersistenceException(EntityPersistenceException.Operation.DELETE, imsIncidentAlarmConditionAlarm);

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
