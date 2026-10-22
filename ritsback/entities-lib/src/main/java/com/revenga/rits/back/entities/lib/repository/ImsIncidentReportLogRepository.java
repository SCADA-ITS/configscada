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
import com.revenga.rits.back.data.core.model.ImsIncidentReportLog;
import com.revenga.rits.back.data.core.model.ImsIncidentReportLogValue;
import com.revenga.rits.back.entities.lib.exception.EntityDBException;
import com.revenga.rits.back.entities.lib.exception.EntityPersistenceException;
import com.revenga.rits.back.entities.lib.repository.entity.EntityCacheable;
import com.revenga.rits.back.entities.lib.repository.entity.EntityPersistent;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository.PersistOperation;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class ImsIncidentReportLogRepository extends EntityRepository
		implements EntityCacheable<ImsIncidentReportLog>, EntityPersistent<ImsIncidentReportLog> {

	// region Constructors
	public ImsIncidentReportLogRepository() {

		super(ImsIncidentReportLog.class);
	}

	public ImsIncidentReportLogRepository(Boolean cacheable) {

		super(ImsIncidentReportLog.class, cacheable);
	}

	public ImsIncidentReportLogRepository(Boolean cacheable, Long cacheRetentionTime) {

		super(ImsIncidentReportLog.class, cacheable, cacheRetentionTime);
	}

	public ImsIncidentReportLogRepository(Boolean cacheable, List<EntityRepository> repositories) {

		super(ImsIncidentReportLog.class, cacheable, repositories);
	}

	public ImsIncidentReportLogRepository(Boolean cacheable, Long cacheRetentionTime,
			List<EntityRepository> repositories) {

		super(ImsIncidentReportLog.class, cacheable, cacheRetentionTime, repositories);
	}

	// endregion Constructors

	// region getters
	public ImsIncidentReportLog get(Long id, Boolean fillObject) {

		return (ImsIncidentReportLog) getFromCache(new String[] { String.valueOf(id) }, fillObject);
	}

	@Override
	public List<ImsIncidentReportLog> getAll(Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(be -> (ImsIncidentReportLog) be).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}

	public List<ImsIncidentReportLog> getsByIncidentReport(Long incidentReportId, Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			List<ImsIncidentReportLog> imsIncidentReportLogs = basicElements.stream()
					.map(be -> (ImsIncidentReportLog) be).collect(Collectors.toList());

			return imsIncidentReportLogs.stream().filter(e -> e.getIncidentReportId().equals(incidentReportId))
					.collect(Collectors.toList());
		}

		return Collections.emptyList();
	}

	// endregion getters

	// region puts
	@Override
	public ImsIncidentReportLog put(ImsIncidentReportLog entity) {

		return (ImsIncidentReportLog) putFromCache(entity);
	}

	@Override
	public List<ImsIncidentReportLog> put(List<ImsIncidentReportLog> entities) {

		List<BasicElement> basicElements = entities.stream().map(alarmConfig -> (BasicElement) alarmConfig)
				.collect(Collectors.toList());

		basicElements = putsFromCache(basicElements);

		return basicElements.stream().map(be -> (ImsIncidentReportLog) be).collect(Collectors.toList());
	}
	// endregion puts

	// region dels
	@Override
	public ImsIncidentReportLog del(ImsIncidentReportLog entity) {

		return (ImsIncidentReportLog) delFromCache(entity);

	}

	@Override
	public List<ImsIncidentReportLog> del(List<ImsIncidentReportLog> entities) {

		List<BasicElement> basicElements = entities.stream().map(alarmConfig -> (BasicElement) alarmConfig)
				.collect(Collectors.toList());

		basicElements = delFromCache(basicElements);

		return basicElements.stream().map(be -> (ImsIncidentReportLog) be).collect(Collectors.toList());
	}
	// endregion dels

	@Override
	public ImsIncidentReportLog create(ImsIncidentReportLog imsIncidentReportLog) {

		try {

			return create(imsIncidentReportLog, null, null);

		} catch (EntityPersistenceException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			return null;
		}
	}

	@Override
	public ImsIncidentReportLog create(ImsIncidentReportLog imsIncidentReportLog, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {

		Connection conn = null;
		boolean closeConnection = false;
		boolean commit = false;
		conn = connection;
		ImsIncidentReportLog imsIncidentReportLogResult = null;

		try {

			if (conn == null) {

				conn = DataSourceConnection.getInstance().getConnection();
				conn.setAutoCommit(false);
				closeConnection = true;
				commit = true;
				persistOperations = new ArrayList<>();
			}

			// Insert imsIncidentReportLog in BBDD
			ImsIncidentReportLog imsIncidentReportLogInserted = null;

			imsIncidentReportLogInserted = (ImsIncidentReportLog) getObjectDao().insert(conn, imsIncidentReportLog);

			if (imsIncidentReportLogInserted == null) {

				throw new EntityDBException(EntityDBException.Operation.INSERT, imsIncidentReportLog);
			}

			persistOperations.add(new MutablePair<>(PersistOperation.PUT, imsIncidentReportLogInserted));

			// Insert all imsIncidentReportLogValues
			if (!CollectionUtils.isEmpty(imsIncidentReportLog.getIncidentReportLogValues())
					&& !CollectionUtils.isEmpty(getRepositories())) {

				ImsIncidentReportLogValueRepository entityRepository = (ImsIncidentReportLogValueRepository) getRepositories()
						.stream().filter(repository -> repository.getClazz().equals(ImsIncidentReportLogValue.class))
						.findFirst().orElse(null);

				if (entityRepository != null) {

					for (ImsIncidentReportLogValue imsIncidentReportLogValue : imsIncidentReportLog
							.getIncidentReportLogValues()) {

						imsIncidentReportLogValue.setIncidentReportLogId(imsIncidentReportLogInserted.getId());
						entityRepository.create(imsIncidentReportLogValue, conn, persistOperations);
					}
				}

			}

			if (commit) {
				conn.commit();

				persistOperations(persistOperations);
				imsIncidentReportLogResult = get(imsIncidentReportLogInserted.getId(), false);
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
			throw new EntityPersistenceException(EntityPersistenceException.Operation.CREATE, imsIncidentReportLog);

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

		return imsIncidentReportLogResult;
	}

	public boolean create(List<ImsIncidentReportLog> imsIncidentReportLogs)  {

		Connection conn = null;
		boolean closeConnection = false;
		boolean commit = false;
		boolean bok = false;
		List<Pair<PersistOperation, BasicElement>> persistOperations;

		if (CollectionUtils.isNotEmpty(imsIncidentReportLogs)) {

			try {

				conn = DataSourceConnection.getInstance().getConnection();
				conn.setAutoCommit(false);
				closeConnection = true;
				commit = true;
				persistOperations = new ArrayList<>();
				
				for (ImsIncidentReportLog imsIncidentReportLog : imsIncidentReportLogs) {

					// Insert imsIncidentReportLog in BBDD
					ImsIncidentReportLog imsIncidentReportLogInserted = null;

					imsIncidentReportLogInserted = (ImsIncidentReportLog) getObjectDao().insert(conn,
							imsIncidentReportLog);

					if (imsIncidentReportLogInserted == null) {

						throw new EntityDBException(EntityDBException.Operation.INSERT, imsIncidentReportLog);
					}

					persistOperations.add(new MutablePair<>(PersistOperation.PUT, imsIncidentReportLogInserted));

					// Insert all imsIncidentReportLogValues
					if (!CollectionUtils.isEmpty(imsIncidentReportLog.getIncidentReportLogValues())
							&& !CollectionUtils.isEmpty(getRepositories())) {

						ImsIncidentReportLogValueRepository entityRepository = (ImsIncidentReportLogValueRepository) getRepositories()
								.stream()
								.filter(repository -> repository.getClazz().equals(ImsIncidentReportLogValue.class))
								.findFirst().orElse(null);

						if (entityRepository != null) {

							for (ImsIncidentReportLogValue imsIncidentReportLogValue : imsIncidentReportLog
									.getIncidentReportLogValues()) {

								imsIncidentReportLogValue.setIncidentReportLogId(imsIncidentReportLogInserted.getId());
								entityRepository.create(imsIncidentReportLogValue, conn, persistOperations);
							}
						}

					}
				}
				if (commit) {

					conn.commit();

					persistOperations(persistOperations);
				}

			} catch (EntityDBException | SQLException | IOException | EntityPersistenceException e) {

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
		}

		return bok;
	}

	@Override
	public ImsIncidentReportLog clone(ImsIncidentReportLog entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ImsIncidentReportLog clone(ImsIncidentReportLog entity, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ImsIncidentReportLog update(ImsIncidentReportLog imsIncidentReportLog) {

		try {

			return update(imsIncidentReportLog, null, null);

		} catch (EntityPersistenceException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			return null;
		}
	}

	@Override
	public ImsIncidentReportLog update(ImsIncidentReportLog imsIncidentReportLog, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {

		Connection conn = null;
		boolean closeConnection = false;
		boolean commit = false;
		conn = connection;
		ImsIncidentReportLog imsIncidentReportLogResult = null;
		ImsIncidentReportLog imsIncidentReportLogAux = null;

		try {

			if (imsIncidentReportLog == null || imsIncidentReportLog.getId() == null) {

				throw new NullPointerException("imsIncidentReportLog is null");
			}

			imsIncidentReportLogAux = get(imsIncidentReportLog.getId(), false);

			if (imsIncidentReportLogAux == null) {

				throw new NullPointerException("imsIncidentReportLog not found");
			}

			if (imsIncidentReportLog.getAlias() != null)
				imsIncidentReportLogAux.setAlias(imsIncidentReportLog.getAlias());
			if (imsIncidentReportLog.getDescription() != null)
				imsIncidentReportLogAux.setDescription(imsIncidentReportLog.getDescription());
			if (imsIncidentReportLog.getIncidentReportId() != null)
				imsIncidentReportLogAux.setIncidentReportId(imsIncidentReportLog.getIncidentReportId());
			if (imsIncidentReportLog.getTime() != null)
				imsIncidentReportLogAux.setTime(imsIncidentReportLog.getTime());
			if (imsIncidentReportLog.getUserId() != null)
				imsIncidentReportLogAux.setUserId(imsIncidentReportLog.getUserId());
			if (imsIncidentReportLog.getLevel() != null)
				imsIncidentReportLogAux.setLevel(imsIncidentReportLog.getLevel());

			if (conn == null) {

				conn = DataSourceConnection.getInstance().getConnection();
				conn.setAutoCommit(false);
				closeConnection = true;
				commit = true;
				persistOperations = new ArrayList<>();
			}

			// Update imsIncidentReportLog in BBDD
			ImsIncidentReportLog imsIncidentReportLogUpdated = null;

			imsIncidentReportLogUpdated = (ImsIncidentReportLog) getObjectDao().update(conn, imsIncidentReportLogAux);

			if (imsIncidentReportLogUpdated == null) {

				throw new EntityDBException(EntityDBException.Operation.UPDATE, imsIncidentReportLogAux);
			}

			persistOperations.add(new MutablePair<>(PersistOperation.PUT, imsIncidentReportLogUpdated));

			if (commit) {

				conn.commit();

				persistOperations(persistOperations);
			}

			imsIncidentReportLogResult = imsIncidentReportLogUpdated;

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
			throw new EntityPersistenceException(EntityPersistenceException.Operation.UPDATE, imsIncidentReportLog);

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

		return imsIncidentReportLogResult;
	}

	@Override
	public ImsIncidentReportLog delete(ImsIncidentReportLog imsIncidentReportLog) {

		try {

			return delete(imsIncidentReportLog, null, null);

		} catch (EntityPersistenceException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			return null;
		}
	}

	@Override
	public ImsIncidentReportLog delete(ImsIncidentReportLog imsIncidentReportLog, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {

		Connection conn = null;
		boolean closeConnection = false;
		boolean commit = false;
		conn = connection;
		ImsIncidentReportLog imsIncidentReportLogResult = null;
		ImsIncidentReportLog imsIncidentReportLogAux = null;
		List<ImsIncidentReportLogValue> imsIncidentReportLogValues = null;

		try {

			if (imsIncidentReportLog == null || imsIncidentReportLog.getId() == null) {

				throw new NullPointerException("imsIncidentReportLog is null");
			}

			imsIncidentReportLogAux = get(imsIncidentReportLog.getId(), false);

			if (imsIncidentReportLogAux == null) {

				throw new NullPointerException("imsIncidentReportLog not found");
			}

			if (conn == null) {

				conn = DataSourceConnection.getInstance().getConnection();
				conn.setAutoCommit(false);
				closeConnection = true;
				commit = true;
				persistOperations = new ArrayList<>();
			}

			// Delete all imsIncidentReportLogValues
			if (!CollectionUtils.isEmpty(getRepositories())) {

				ImsIncidentReportLogValueRepository entityRepository = (ImsIncidentReportLogValueRepository) getRepositories()
						.stream().filter(repository -> repository.getClazz().equals(ImsIncidentReportLogValue.class))
						.findFirst().orElse(null);

				if (entityRepository != null) {

					imsIncidentReportLogValues = entityRepository.getsByIncidentLog(imsIncidentReportLog.getId(),
							false);

					if (!CollectionUtils.isEmpty(imsIncidentReportLogValues)) {

						for (ImsIncidentReportLogValue imsIncidentReportLogValue : imsIncidentReportLogValues) {

							entityRepository.delete(imsIncidentReportLogValue, conn, persistOperations);
						}
					}
				}
			}

			// Delete imsIncidentReportLog in BBDD
			ImsIncidentReportLog imsIncidentReportLogDeleted = null;

			imsIncidentReportLogDeleted = (ImsIncidentReportLog) getObjectDao().delete(conn, imsIncidentReportLogAux);

			if (imsIncidentReportLogDeleted == null) {

				throw new EntityDBException(EntityDBException.Operation.DELETE, imsIncidentReportLogAux);
			}

			persistOperations.add(new MutablePair<>(PersistOperation.DELETE, imsIncidentReportLogAux));

			if (commit) {

				conn.commit();

				// Update cache
				persistOperations(persistOperations);
			}

			imsIncidentReportLogResult = imsIncidentReportLogDeleted;

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
			throw new EntityPersistenceException(EntityPersistenceException.Operation.DELETE, imsIncidentReportLog);

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

		return imsIncidentReportLogResult;
	}

}
