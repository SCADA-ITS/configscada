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
import com.revenga.rits.back.data.core.model.AuditLog;
import com.revenga.rits.back.data.core.model.AuditLogValue;
import com.revenga.rits.back.data.core.model.BasicElement;
import com.revenga.rits.back.data.core.model.LogTypeParam;
import com.revenga.rits.back.entities.lib.exception.EntityDBException;
import com.revenga.rits.back.entities.lib.exception.EntityPersistenceException;
import com.revenga.rits.back.entities.lib.repository.entity.EntityCacheable;
import com.revenga.rits.back.entities.lib.repository.entity.EntityPersistent;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class AuditLogRepository extends EntityRepository
		implements EntityCacheable<AuditLog>, EntityPersistent<AuditLog> {

	// region Constructors
	public AuditLogRepository() {

		super(AuditLog.class);
	}

	public AuditLogRepository(Boolean cacheable) {

		super(AuditLog.class, cacheable);
	}

	public AuditLogRepository(Boolean cacheable, Long cacheRetentionTime) {

		super(AuditLog.class, cacheable, cacheRetentionTime);
	}

	public AuditLogRepository(Boolean cacheable, List<EntityRepository> repositories) {

		super(AuditLog.class, cacheable, repositories);
	}

	public AuditLogRepository(Boolean cacheable, Long cacheRetentionTime, List<EntityRepository> repositories) {

		super(AuditLog.class, cacheable, cacheRetentionTime, repositories);
	}

	// endregion Constructors

	// region getters
	public AuditLog get(Long logTypeId, Long id, Boolean fillObject) {

		return (AuditLog) getFromCache(new String[] { String.valueOf(id), String.valueOf(logTypeId) }, fillObject);
	}

	public List<AuditLog> getsByLogType(Long logTypeId, Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(new String[] { null, String.valueOf(logTypeId) }, fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(be -> (AuditLog) be).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}

	@Override
	public List<AuditLog> getAll(Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(be -> (AuditLog) be).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}
	// endregion getters

	// region puts
	@Override
	public AuditLog put(AuditLog entity) {

		return (AuditLog) putFromCache(entity);
	}

	@Override
	public List<AuditLog> put(List<AuditLog> entities) {

		List<BasicElement> basicElements = entities.stream().map(element -> (BasicElement) element)
				.collect(Collectors.toList());

		basicElements = putsFromCache(basicElements);

		return basicElements.stream().map(be -> (AuditLog) be).collect(Collectors.toList());
	}
	// endregion puts

	// region dels
	@Override
	public AuditLog del(AuditLog entity) {

		return (AuditLog) delFromCache(entity);

	}

	@Override
	public List<AuditLog> del(List<AuditLog> entities) {

		List<BasicElement> basicElements = entities.stream().map(element -> (BasicElement) element)
				.collect(Collectors.toList());

		basicElements = delFromCache(basicElements);

		return basicElements.stream().map(be -> (AuditLog) be).collect(Collectors.toList());
	}
	// endregion dels

	@Override
	public AuditLog create(AuditLog auditLog) {

		try {
			return create(auditLog, null, null);
		} catch (EntityPersistenceException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			return null;
		}
	}

	@Override
	public AuditLog create(AuditLog auditLog, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {

		Connection conn = null;
		boolean closeConnection = false;
		boolean commit = false;
		conn = connection;
		AuditLog auditLogResult = null;
		List<LogTypeParam> logTypeParams = null;

		try {

			if (conn == null) {

				conn = DataSourceConnection.getInstance().getConnection();
				conn.setAutoCommit(false);
				closeConnection = true;
				commit = true;
				persistOperations = new ArrayList<>();
			}

			// Insert element in BBDD
			AuditLog auditLogInserted = null;

			auditLogInserted = (AuditLog) getObjectDao().insert(conn, auditLog);

			if (auditLogInserted == null) {

				throw new EntityDBException(EntityDBException.Operation.INSERT, auditLog);
			}

			persistOperations.add(new MutablePair<>(PersistOperation.PUT, auditLogInserted));

			if (!CollectionUtils.isEmpty(getRepositories())) {

				LogTypeParamRepository entityRepositoryElementTypeParam = (LogTypeParamRepository) getRepositories()
						.stream().filter(repository -> repository.getClazz().equals(LogTypeParam.class)).findFirst()
						.orElse(null);

				if (entityRepositoryElementTypeParam != null) {

					logTypeParams = entityRepositoryElementTypeParam.getsByLogType(auditLogInserted.getLogTypeId(),
							false);

					// auditLogValue
					if (!CollectionUtils.isEmpty(logTypeParams)) {

						AuditLogValueRepository entityRepositoryAuditLogtValue = (AuditLogValueRepository) getRepositories()
								.stream().filter(repository -> repository.getClazz().equals(AuditLogValue.class))
								.findFirst().orElse(null);

						if (entityRepositoryAuditLogtValue != null) {

							for (LogTypeParam logTypeParam : logTypeParams) {

								AuditLogValue value = new AuditLogValue();

								value.setAuditLogId(auditLogInserted.getId());
								value.setLogTypeId(auditLogInserted.getLogTypeId());
								value.setLogParamId(logTypeParam.getId());

								if (!CollectionUtils.isEmpty(auditLog.getAuditLogValues())) {

									for (AuditLogValue auditLogValue : auditLog.getAuditLogValues()) {

										if (auditLogValue.getLogParamId() != null
												&& auditLogValue.getLogTypeId() != null
												&& auditLogValue.getLogTypeId().equals(logTypeParam.getLogTypeId())
												&& auditLogValue.getLogParamId().equals(logTypeParam.getId())) {

											value.setValue(auditLogValue.getValue());
										}
									}
								}

								entityRepositoryAuditLogtValue.create(value, conn, persistOperations);
							}
						}
					}
				}
			}

			if (commit) {

				conn.commit();

				persistOperations(persistOperations);

				auditLogResult = get(auditLogInserted.getLogTypeId(), auditLogInserted.getId(), false);
			}

			if (auditLogResult == null) {

				throw new NullPointerException("Element not found");
			}

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
			throw new EntityPersistenceException(EntityPersistenceException.Operation.CREATE, auditLog);

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

		return auditLogResult;
	}

	@Override
	public AuditLog delete(AuditLog auditLog) {

		try {

			return delete(auditLog, null, null);

		} catch (EntityPersistenceException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			return null;
		}
	}

	@Override
	public AuditLog delete(AuditLog auditLog, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {

		Connection conn = null;
		boolean closeConnection = false;
		boolean commit = false;
		conn = connection;
		AuditLog auditLogResult = null;
		AuditLog auditLogAux = null;

		try {

			if (auditLog == null || auditLog.getId() == null) {

				throw new NullPointerException("AuditLog is null");
			}

			auditLogAux = get(auditLog.getLogTypeId(), auditLog.getId(), false);

			if (auditLogAux == null) {

				throw new NullPointerException("AuditLog not found");
			}

			if (conn == null) {

				conn = DataSourceConnection.getInstance().getConnection();
				conn.setAutoCommit(false);
				closeConnection = true;
				commit = true;
				persistOperations = new ArrayList<>();
			}

			if (!CollectionUtils.isEmpty(getRepositories())) {

				AuditLogValueRepository entityRepository = (AuditLogValueRepository) getRepositories().stream()
						.filter(repository -> repository.getClazz().equals(AuditLogValue.class)).findFirst()
						.orElse(null);

				if (entityRepository != null) {

					List<AuditLogValue> auditLogValues = entityRepository.getsByAuditLog(auditLogAux.getLogTypeId(),
							auditLogAux.getId(), false);

					if (!CollectionUtils.isEmpty(auditLogValues)) {

						for (AuditLogValue auditLogValue : auditLogValues) {

							entityRepository.delete(auditLogValue, conn, persistOperations);
						}
					}
				}
			}

			AuditLog auditLogDeleted = (AuditLog) getObjectDao().delete(conn, auditLogAux);

			if (auditLogDeleted == null) {

				throw new EntityDBException(EntityDBException.Operation.DELETE, auditLogAux);
			}

			persistOperations.add(new MutablePair<>(PersistOperation.DELETE, auditLogAux));

			if (commit) {

				conn.commit();

				// Update cache
				persistOperations(persistOperations);
			}

			auditLogResult = auditLogDeleted;

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
			throw new EntityPersistenceException(EntityPersistenceException.Operation.DELETE, auditLog);

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

		return auditLogResult;
	}

	@Override
	public AuditLog clone(AuditLog entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AuditLog clone(AuditLog entity, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AuditLog update(AuditLog entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AuditLog update(AuditLog entity, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {
		// TODO Auto-generated method stub
		return null;
	}

	public List<AuditLog> delete(final List<AuditLog> auditLogs){

		List<AuditLog> auditLogResults = null;
		boolean closeConnection = false;
		Connection conn = null;

		if (!CollectionUtils.isEmpty(auditLogs)) {

			try {

				conn = DataSourceConnection.getInstance().getConnection();

				if (conn != null) {

					List<Pair<PersistOperation, BasicElement>> persistOperations = new ArrayList<>();
					auditLogResults = new ArrayList<>();
					AuditLog auditLogAux = null;
					AuditLog auditLogDeleted = null;

					conn.setAutoCommit(false);
					closeConnection = true;

					AuditLogValueRepository auditLogValueRepository = (AuditLogValueRepository) getRepository(
							AuditLogValue.class);

					for (AuditLog auditLog : auditLogs) {

						if (auditLog == null || auditLog.getId() == null) {

							throw new NullPointerException("AuditLog is null");
						}

						auditLogAux = get(auditLog.getLogTypeId(), auditLog.getId(), false);

						if (auditLogAux == null) {

							throw new NullPointerException("AuditLog not found");
						}

						if (auditLogValueRepository != null) {

							List<AuditLogValue> auditLogValues = auditLogValueRepository
									.getsByAuditLog(auditLogAux.getLogTypeId(), auditLogAux.getId(), false);

							if (!CollectionUtils.isEmpty(auditLogValues)) {

								for (AuditLogValue auditLogValue : auditLogValues) {

									auditLogValueRepository.delete(auditLogValue, conn, persistOperations);
								}
							}
						}

						auditLogDeleted = (AuditLog) getObjectDao().delete(conn, auditLogAux);

						if (auditLogDeleted == null) {

							throw new EntityDBException(EntityDBException.Operation.DELETE, auditLogAux);
						}

						persistOperations.add(new MutablePair<>(PersistOperation.DELETE, auditLogAux));

						auditLogResults.add(auditLogDeleted);

					}

					conn.commit();

					// Update cache
					persistOperations(persistOperations);
				}

			} catch (EntityDBException | SQLException | NullPointerException | IOException | EntityPersistenceException e) {

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
		return auditLogResults;
	}

}
