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
import com.revenga.rits.back.data.core.model.ImsIncidentReportLogValue;
import com.revenga.rits.back.entities.lib.exception.EntityDBException;
import com.revenga.rits.back.entities.lib.exception.EntityPersistenceException;
import com.revenga.rits.back.entities.lib.repository.entity.EntityCacheable;
import com.revenga.rits.back.entities.lib.repository.entity.EntityPersistent;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository.PersistOperation;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class ImsIncidentReportLogValueRepository extends EntityRepository
		implements EntityCacheable<ImsIncidentReportLogValue>, EntityPersistent<ImsIncidentReportLogValue> {

	// region Constructors
	public ImsIncidentReportLogValueRepository() {

		super(ImsIncidentReportLogValue.class);
	}

	public ImsIncidentReportLogValueRepository(Boolean cacheable) {

		super(ImsIncidentReportLogValue.class, cacheable);
	}

	public ImsIncidentReportLogValueRepository(Boolean cacheable, Long cacheRetentionTime) {

		super(ImsIncidentReportLogValue.class, cacheable, cacheRetentionTime);
	}

	// endregion Constructors

	// region getters
	public ImsIncidentReportLogValue get(Long incidentReportLogId, Long logTypeId, Long logTypeParamId,
			Boolean fillObject) {

		return (ImsIncidentReportLogValue) getFromCache(new String[] { String.valueOf(incidentReportLogId),
				String.valueOf(logTypeId), String.valueOf(logTypeParamId) }, fillObject);
	}

	@Override
	public List<ImsIncidentReportLogValue> getAll(Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(be -> (ImsIncidentReportLogValue) be).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}

	public List<ImsIncidentReportLogValue> getsByIncidentLog(Long imsIncidentReportLogId, Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(
				new String[] { String.valueOf(imsIncidentReportLogId), null, null }, fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(be -> (ImsIncidentReportLogValue) be).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}

	// endregion getters

	// region puts
	@Override
	public ImsIncidentReportLogValue put(ImsIncidentReportLogValue entity) {

		return (ImsIncidentReportLogValue) putFromCache(entity);
	}

	@Override
	public List<ImsIncidentReportLogValue> put(List<ImsIncidentReportLogValue> entities) {

		List<BasicElement> basicElements = entities.stream().map(alarmConfig -> (BasicElement) alarmConfig)
				.collect(Collectors.toList());

		basicElements = putsFromCache(basicElements);

		return basicElements.stream().map(be -> (ImsIncidentReportLogValue) be).collect(Collectors.toList());
	}
	// endregion puts

	// region dels
	
	@Override
	public ImsIncidentReportLogValue del(ImsIncidentReportLogValue entity) {

		return (ImsIncidentReportLogValue) delFromCache(entity);

	}

	@Override
	public List<ImsIncidentReportLogValue> del(List<ImsIncidentReportLogValue> entities) {

		List<BasicElement> basicElements = entities.stream().map(alarmConfig -> (BasicElement) alarmConfig)
				.collect(Collectors.toList());

		basicElements = delFromCache(basicElements);

		return basicElements.stream().map(be -> (ImsIncidentReportLogValue) be).collect(Collectors.toList());
	}
	// endregion dels

	@Override
	public ImsIncidentReportLogValue create(ImsIncidentReportLogValue imsIncidentReportLogValue) {

		try {

			return create(imsIncidentReportLogValue, null, null);

		} catch (EntityPersistenceException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			return null;
		}
	}

	@Override
	public ImsIncidentReportLogValue create(ImsIncidentReportLogValue imsIncidentReportLogValue, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {

		Connection conn = null;
		boolean closeConnection = false;
		boolean commit = false;
		conn = connection;
		ImsIncidentReportLogValue imsIncidentReportLogValueResult = null;

		try {

			if (conn == null) {

				conn = DataSourceConnection.getInstance().getConnection();
				conn.setAutoCommit(false);
				closeConnection = true;
				commit = true;
				persistOperations = new ArrayList<>();
			}

			// Insert imsIncidentReportLogValue in BBDD
			ImsIncidentReportLogValue imsIncidentReportLogValueInserted = null;

			imsIncidentReportLogValueInserted = (ImsIncidentReportLogValue) getObjectDao().insert(conn,
					imsIncidentReportLogValue);

			if (imsIncidentReportLogValueInserted == null) {

				throw new EntityDBException(EntityDBException.Operation.INSERT, imsIncidentReportLogValue);
			}

			persistOperations.add(new MutablePair<>(PersistOperation.PUT, imsIncidentReportLogValueInserted));

			if (commit) {
				conn.commit();

				persistOperations(persistOperations);

				imsIncidentReportLogValueResult = get(imsIncidentReportLogValueInserted.getIncidentReportLogId(),
						imsIncidentReportLogValueInserted.getLogTypeId(),
						imsIncidentReportLogValueInserted.getLogTypeParamId(), false);
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
					imsIncidentReportLogValue);

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

		return imsIncidentReportLogValueResult;
	}

	@Override
	public ImsIncidentReportLogValue clone(ImsIncidentReportLogValue entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ImsIncidentReportLogValue clone(ImsIncidentReportLogValue entity, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ImsIncidentReportLogValue update(ImsIncidentReportLogValue imsIncidentReportLogValue) {

		try {

			return update(imsIncidentReportLogValue, null, null);

		} catch (EntityPersistenceException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			return null;
		}
	}

	@Override
	public ImsIncidentReportLogValue update(ImsIncidentReportLogValue imsIncidentReportLogValue, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {

		Connection conn = null;
		boolean closeConnection = false;
		boolean commit = false;
		conn = connection;
		ImsIncidentReportLogValue imsIncidentReportLogValueResult = null;
		ImsIncidentReportLogValue imsIncidentReportLogValueAux = null;

		try {

			if (imsIncidentReportLogValue == null || imsIncidentReportLogValue.getId() == null) {

				throw new NullPointerException("imsIncidentReportLogValue is null");
			}

			imsIncidentReportLogValueAux = get(imsIncidentReportLogValue.getIncidentReportLogId(),
					imsIncidentReportLogValue.getLogTypeId(), imsIncidentReportLogValue.getLogTypeParamId(), false);

			if (imsIncidentReportLogValueAux == null) {

				throw new NullPointerException("imsIncidentReportLogValue not found");
			}

			if (imsIncidentReportLogValue.getAlias() != null)
				imsIncidentReportLogValueAux.setAlias(imsIncidentReportLogValue.getAlias());
			if (imsIncidentReportLogValue.getDescription() != null)
				imsIncidentReportLogValueAux.setDescription(imsIncidentReportLogValue.getDescription());
			if (imsIncidentReportLogValue.getValue() != null)
				imsIncidentReportLogValueAux.setValue(imsIncidentReportLogValue.getValue());

			if (conn == null) {

				conn = DataSourceConnection.getInstance().getConnection();
				conn.setAutoCommit(false);
				closeConnection = true;
				commit = true;
				persistOperations = new ArrayList<>();
			}

			// Update imsIncidentReportLogValue in BBDD
			ImsIncidentReportLogValue imsIncidentReportLogValueUpdated = null;

			imsIncidentReportLogValueUpdated = (ImsIncidentReportLogValue) getObjectDao().update(conn,
					imsIncidentReportLogValueAux);

			if (imsIncidentReportLogValueUpdated == null) {

				throw new EntityDBException(EntityDBException.Operation.UPDATE, imsIncidentReportLogValueAux);
			}

			persistOperations.add(new MutablePair<>(PersistOperation.PUT, imsIncidentReportLogValueUpdated));

			if (commit) {

				conn.commit();

				persistOperations(persistOperations);
			}

			imsIncidentReportLogValueResult = imsIncidentReportLogValueUpdated;

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
					imsIncidentReportLogValue);

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

		return imsIncidentReportLogValueResult;
	}

	@Override
	public ImsIncidentReportLogValue delete(ImsIncidentReportLogValue imsIncidentReportLogValue) {

		try {

			return delete(imsIncidentReportLogValue, null, null);

		} catch (EntityPersistenceException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			return null;
		}
	}

	@Override
	public ImsIncidentReportLogValue delete(ImsIncidentReportLogValue imsIncidentReportLogValue, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {

		Connection conn = null;
		boolean closeConnection = false;
		boolean commit = false;
		conn = connection;
		ImsIncidentReportLogValue imsIncidentReportLogValueResult = null;
		ImsIncidentReportLogValue imsIncidentReportLogValueAux = null;

		try {

			if (imsIncidentReportLogValue == null || imsIncidentReportLogValue.getIncidentReportLogId() == null
					|| imsIncidentReportLogValue.getLogTypeId() == null
					|| imsIncidentReportLogValue.getLogTypeParamId() == null) {

				throw new NullPointerException("imsIncidentReportLogValue is null");
			}

			imsIncidentReportLogValueAux = get(imsIncidentReportLogValue.getIncidentReportLogId(),
					imsIncidentReportLogValue.getLogTypeId(), imsIncidentReportLogValue.getLogTypeParamId(), false);

			if (imsIncidentReportLogValueAux == null) {

				throw new NullPointerException("imsIncidentReportLogValue not found");
			}

			if (conn == null) {

				conn = DataSourceConnection.getInstance().getConnection();
				conn.setAutoCommit(false);
				closeConnection = true;
				commit = true;
				persistOperations = new ArrayList<>();
			}

			// Delete imsIncidentReportLogValue in BBDD
			ImsIncidentReportLogValue imsIncidentReportLogValueDeleted = null;

			imsIncidentReportLogValueDeleted = (ImsIncidentReportLogValue) getObjectDao().delete(conn,
					imsIncidentReportLogValueAux);

			if (imsIncidentReportLogValueDeleted == null) {

				throw new EntityDBException(EntityDBException.Operation.DELETE, imsIncidentReportLogValueAux);
			}

			persistOperations.add(new MutablePair<>(PersistOperation.DELETE, imsIncidentReportLogValueAux));

			if (commit) {

				conn.commit();

				// Update cache
				persistOperations(persistOperations);
			}

			imsIncidentReportLogValueResult = imsIncidentReportLogValueDeleted;

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
					imsIncidentReportLogValue);

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

		return imsIncidentReportLogValueResult;
	}

}
