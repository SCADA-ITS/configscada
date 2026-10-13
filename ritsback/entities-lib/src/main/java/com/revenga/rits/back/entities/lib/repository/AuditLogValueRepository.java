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
import com.revenga.rits.back.data.core.model.AuditLogValue;
import com.revenga.rits.back.data.core.model.BasicElement;
import com.revenga.rits.back.entities.lib.exception.EntityDBException;
import com.revenga.rits.back.entities.lib.exception.EntityPersistenceException;
import com.revenga.rits.back.entities.lib.repository.entity.EntityCacheable;
import com.revenga.rits.back.entities.lib.repository.entity.EntityPersistent;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class AuditLogValueRepository extends EntityRepository
		implements EntityCacheable<AuditLogValue>, EntityPersistent<AuditLogValue> {

	// region Constructors
	public AuditLogValueRepository() {

		super(AuditLogValue.class);
	}

	public AuditLogValueRepository(Boolean cacheable) {

		super(AuditLogValue.class, cacheable);
	}

	public AuditLogValueRepository(Boolean cacheable, Long cacheRetentionTime) {

		super(AuditLogValue.class, cacheable, cacheRetentionTime);
	}
	// endregion Constructors

	// region getters
	public AuditLogValue get(Long logTypeId, Long auditLogId, Long logTypeParamId, Boolean fillObject) {

		return (AuditLogValue) getFromCache(
				new String[] { String.valueOf(auditLogId), String.valueOf(logTypeId), String.valueOf(logTypeParamId) },
				fillObject);
	}

	public List<AuditLogValue> getsByAuditLog(Long logTypeId, Long auditLogId, Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(
				new String[] { String.valueOf(auditLogId), String.valueOf(logTypeId), null }, fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(be -> (AuditLogValue) be).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}

	public List<AuditLogValue> getsByLogType(Long logTypeId, Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(new String[] { null, String.valueOf(logTypeId), null },
				fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(be -> (AuditLogValue) be).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}

	@Override
	public List<AuditLogValue> getAll(Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(be -> (AuditLogValue) be).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}
	// endregion getters

	// region puts
	@Override
	public AuditLogValue put(AuditLogValue entity) {

		return (AuditLogValue) putFromCache(entity);
	}

	@Override
	public List<AuditLogValue> put(List<AuditLogValue> entities) {

		List<BasicElement> basicElements = entities.stream().map(auditLogValue -> (BasicElement) auditLogValue)
				.collect(Collectors.toList());

		basicElements = putsFromCache(basicElements);

		return basicElements.stream().map(be -> (AuditLogValue) be).collect(Collectors.toList());
	}
	// endregion puts

	// region dels
	@Override
	public AuditLogValue del(AuditLogValue entity) {

		return (AuditLogValue) delFromCache(entity);

	}

	@Override
	public List<AuditLogValue> del(List<AuditLogValue> entities) {

		List<BasicElement> basicElements = entities.stream().map(auditLogValue -> (BasicElement) auditLogValue)
				.collect(Collectors.toList());

		basicElements = delFromCache(basicElements);

		return basicElements.stream().map(be -> (AuditLogValue) be).collect(Collectors.toList());
	}
	// endregion dels

	@Override
	public AuditLogValue create(AuditLogValue auditLogValue) {
		try {
			return create(auditLogValue, null, null);
		} catch (EntityPersistenceException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			return null;
		}
	}

	@Override
	public AuditLogValue create(AuditLogValue auditLogValue, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {

		Connection conn = null;
		boolean closeConnection = false;
		boolean commit = false;
		conn = connection;
		AuditLogValue auditLogValueResult = null;

		try {

			if (conn == null) {

				conn = DataSourceConnection.getInstance().getConnection();
				conn.setAutoCommit(false);
				closeConnection = true;
				commit = true;
				persistOperations = new ArrayList<>();
			}

			// Insert AuditLogValue in BBDD
			AuditLogValue auditLogValueInserted = null;

			auditLogValueInserted = (AuditLogValue) getObjectDao().insert(conn, auditLogValue);

			if (auditLogValueInserted == null) {

				throw new EntityDBException(EntityDBException.Operation.INSERT, auditLogValue);
			}

			persistOperations.add(new MutablePair<>(PersistOperation.PUT, auditLogValue));

			if (commit) {
				conn.commit();

				persistOperations(persistOperations);
				
				auditLogValueResult = get(auditLogValueInserted.getLogTypeId(), auditLogValueInserted.getAuditLogId(),
						auditLogValueInserted.getLogParamId(), false);
			}

		} catch (EntityDBException | SQLException |

				IOException e) {

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
			throw new EntityPersistenceException(EntityPersistenceException.Operation.CREATE, auditLogValue);

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

		return auditLogValueResult;

	}

	@Override
	public AuditLogValue clone(AuditLogValue entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AuditLogValue clone(AuditLogValue entity, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AuditLogValue update(AuditLogValue auditLogValue) {

		try {

			return update(auditLogValue, null, null);

		} catch (EntityPersistenceException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			return null;
		}
	}

	@Override
	public AuditLogValue update(AuditLogValue auditLogValue, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {

		Connection conn = null;
		boolean closeConnection = false;
		boolean commit = false;
		conn = connection;
		AuditLogValue auditLogValueResult = null;
		AuditLogValue auditLogValueAux = null;

		try {

			if (auditLogValue == null || !ObjectUtils.allNotNull(auditLogValue.getLogTypeId(),
					auditLogValue.getAuditLogId(), auditLogValue.getLogParamId())) {

				throw new NullPointerException("auditLogValue is null");
			}

			auditLogValueAux = get(auditLogValue.getLogTypeId(), auditLogValue.getAuditLogId(),
					auditLogValue.getLogParamId(), false);

			if (auditLogValueAux == null) {

				throw new NullPointerException("auditLogValue not found");
			}

			if (auditLogValue.getValue() != null)
				auditLogValueAux.setValue(auditLogValue.getValue());

			if (conn == null) {

				conn = DataSourceConnection.getInstance().getConnection();
				conn.setAutoCommit(false);
				closeConnection = true;
				commit = true;
				persistOperations = new ArrayList<>();
			}

			// Update auditLogValue in BBDD
			AuditLogValue auditLogValueUpdated = null;

			auditLogValueUpdated = (AuditLogValue) getObjectDao().update(conn, auditLogValueAux);
			if (auditLogValueUpdated == null) {

				throw new EntityDBException(EntityDBException.Operation.UPDATE, auditLogValueAux);
			}

			persistOperations.add(new MutablePair<>(PersistOperation.PUT, auditLogValueUpdated));

			if (commit) {

				conn.commit();

				persistOperations(persistOperations);
			}

			auditLogValueResult = auditLogValueUpdated;

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
			throw new EntityPersistenceException(EntityPersistenceException.Operation.UPDATE, auditLogValue);

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

		return auditLogValueResult;
	}

	@Override
	public AuditLogValue delete(AuditLogValue auditLogValue) {

		try {

			return delete(auditLogValue, null, null);

		} catch (EntityPersistenceException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			return null;
		}
	}

	@Override
	public AuditLogValue delete(AuditLogValue auditLogValue, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {
		
		Connection conn = null;
		boolean closeConnection = false;
		boolean commit = false;
		conn = connection;
		AuditLogValue auditLogValueResult = null;
		AuditLogValue auditLogValueAux = null;

		try {

			if (auditLogValue == null || !ObjectUtils.allNotNull(auditLogValue.getLogTypeId(),
					auditLogValue.getAuditLogId(), auditLogValue.getLogParamId())) {

				throw new NullPointerException("auditLogValue is null");
			}

			auditLogValueAux = get(auditLogValue.getLogTypeId(), auditLogValue.getAuditLogId(),
					auditLogValue.getLogParamId(),false);

			if (auditLogValueAux == null) {

				throw new NullPointerException("auditLogValue not found");
			}

			if (conn == null) {

				conn = DataSourceConnection.getInstance().getConnection();
				conn.setAutoCommit(false);
				closeConnection = true;
				commit = true;
				persistOperations = new ArrayList<>();
			}

			// Delete auditLogValue in BBDD
			AuditLogValue auditLogValueDeleted = null;

			auditLogValueDeleted = (AuditLogValue) getObjectDao().delete(conn, auditLogValueAux);

			if (auditLogValueDeleted == null) {

				throw new EntityDBException(EntityDBException.Operation.DELETE, auditLogValueAux);
			}

			persistOperations.add(new MutablePair<>(PersistOperation.DELETE, auditLogValueAux));

			if (commit) {

				conn.commit();

				// Update cache
				persistOperations(persistOperations);
			}

			auditLogValueResult = auditLogValueDeleted;

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
			throw new EntityPersistenceException(EntityPersistenceException.Operation.DELETE, auditLogValue);

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

		return auditLogValueResult;
	}

}
