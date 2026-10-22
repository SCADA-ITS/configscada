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
import com.revenga.rits.back.data.core.model.ImsIncidentReportTaskValue;
import com.revenga.rits.back.entities.lib.exception.EntityDBException;
import com.revenga.rits.back.entities.lib.exception.EntityPersistenceException;
import com.revenga.rits.back.entities.lib.repository.entity.EntityCacheable;
import com.revenga.rits.back.entities.lib.repository.entity.EntityPersistent;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository.PersistOperation;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class ImsIncidentReportTaskValueRepository extends EntityRepository
		implements EntityCacheable<ImsIncidentReportTaskValue>, EntityPersistent<ImsIncidentReportTaskValue> {

	// region Constructors
	public ImsIncidentReportTaskValueRepository() {

		super(ImsIncidentReportTaskValue.class);
	}

	public ImsIncidentReportTaskValueRepository(Boolean cacheable) {

		super(ImsIncidentReportTaskValue.class, cacheable);
	}

	public ImsIncidentReportTaskValueRepository(Boolean cacheable, Long cacheRetentionTime) {

		super(ImsIncidentReportTaskValue.class, cacheable, cacheRetentionTime);
	}

	// endregion Constructors

	// region getters

	public ImsIncidentReportTaskValue get(Long incidentReportId, Long taskTypeId, Long taskTypeParamId,
			Long incidentTypeTaskId, Boolean fillObject) {

		return (ImsIncidentReportTaskValue) getFromCache(new String[] { String.valueOf(incidentReportId),
				String.valueOf(taskTypeId), String.valueOf(taskTypeParamId), String.valueOf(incidentTypeTaskId) },
				fillObject);
	}

	@Override
	public List<ImsIncidentReportTaskValue> getAll(Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(ImsIncidentReportTaskValue.class::cast).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}

	public List<ImsIncidentReportTaskValue> getsByIncidentReport(Long incidentReportId, Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(
				new String[] { String.valueOf(incidentReportId), null, null, null }, fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(ImsIncidentReportTaskValue.class::cast).collect(Collectors.toList());
		}
		return Collections.emptyList();
	}

	public List<ImsIncidentReportTaskValue> gets(Long incidentReportId, Long taskTypeId, Long incidentTypeTaskId,
			Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(new String[] { String.valueOf(incidentReportId),
				String.valueOf(taskTypeId), null, String.valueOf(incidentTypeTaskId) }, fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(ImsIncidentReportTaskValue.class::cast).collect(Collectors.toList());
		}
		return Collections.emptyList();
	}
	// endregion getters

	// region puts
	/**
	 * 
	 */
	@Override
	public ImsIncidentReportTaskValue put(ImsIncidentReportTaskValue entity) {

		return (ImsIncidentReportTaskValue) putFromCache(entity);
	}

	/**
	 * 
	 */
	@Override
	public List<ImsIncidentReportTaskValue> put(List<ImsIncidentReportTaskValue> entities) {

		List<BasicElement> basicElements = entities.stream().map(BasicElement.class::cast)
				.collect(Collectors.toList());

		basicElements = putsFromCache(basicElements);

		return basicElements.stream().map(ImsIncidentReportTaskValue.class::cast).collect(Collectors.toList());
	}
	// endregion puts

	// region dels
	/**
	 * 
	 */
	@Override
	public ImsIncidentReportTaskValue del(ImsIncidentReportTaskValue entity) {

		return (ImsIncidentReportTaskValue) delFromCache(entity);
	}

	/**
	 * 
	 */
	@Override
	public List<ImsIncidentReportTaskValue> del(List<ImsIncidentReportTaskValue> entities) {

		List<BasicElement> basicElements = entities.stream().map(BasicElement.class::cast)
				.collect(Collectors.toList());

		basicElements = delFromCache(basicElements);

		return basicElements.stream().map(ImsIncidentReportTaskValue.class::cast).collect(Collectors.toList());
	}
	// endregion dels

	@Override
	public ImsIncidentReportTaskValue create(ImsIncidentReportTaskValue entity) {
		try {

			return create(entity, null, null);

		} catch (EntityPersistenceException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			return null;
		}
	}

	@Override
	public ImsIncidentReportTaskValue create(ImsIncidentReportTaskValue entity, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {

		Connection conn = null;
		boolean closeConnection = false;
		boolean commit = false;
		conn = connection;
		ImsIncidentReportTaskValue result = null;

		try {

			if (conn == null) {

				conn = DataSourceConnection.getInstance().getConnection();
				conn.setAutoCommit(false);
				closeConnection = true;
				commit = true;
				persistOperations = new ArrayList<>();
			}

			// Insert planOrderValue in BBDD
			ImsIncidentReportTaskValue inserted = null;

			inserted = (ImsIncidentReportTaskValue) getObjectDao().insert(conn, entity);

			if (inserted == null) {

				throw new EntityDBException(EntityDBException.Operation.INSERT, entity);
			}

			persistOperations.add(new MutablePair<>(PersistOperation.PUT, inserted));

			if (commit) {
				conn.commit();

				persistOperations(persistOperations);

				result = get(entity.getIncidentReportId(), entity.getTaskTypeId(), entity.getTaskTypeParamId(),
						entity.getIncidentTypeTaskId(), false);
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
			throw new EntityPersistenceException(EntityPersistenceException.Operation.CREATE, entity);

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

		return result;
	}

	@Override
	public ImsIncidentReportTaskValue clone(ImsIncidentReportTaskValue entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ImsIncidentReportTaskValue clone(ImsIncidentReportTaskValue entity, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ImsIncidentReportTaskValue update(ImsIncidentReportTaskValue entity) {
		try {

			return update(entity, null, null);

		} catch (EntityPersistenceException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			return null;
		}
	}

	@Override
	public ImsIncidentReportTaskValue update(ImsIncidentReportTaskValue incidentReportTaskValue, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {

		Connection conn = null;
		boolean closeConnection = false;
		boolean commit = false;
		conn = connection;
		ImsIncidentReportTaskValue result = null;

		try {

			if (incidentReportTaskValue == null || incidentReportTaskValue.getIncidentReportId() == null
					|| incidentReportTaskValue.getTaskTypeId() == null
					|| incidentReportTaskValue.getTaskTypeParamId() == null
					|| incidentReportTaskValue.getIncidentTypeTaskId() == null) {

				throw new NullPointerException("incidentTypeTask is null");
			}

			if (get(incidentReportTaskValue.getIncidentReportId(), incidentReportTaskValue.getTaskTypeId(),
					incidentReportTaskValue.getTaskTypeParamId(), incidentReportTaskValue.getIncidentTypeTaskId(),
					false) == null) {

				throw new NullPointerException("incidentTypeTask not found");
			}

			if (conn == null) {

				conn = DataSourceConnection.getInstance().getConnection();
				conn.setAutoCommit(false);
				closeConnection = true;
				commit = true;
				persistOperations = new ArrayList<>();
			}

			// Delete ImsIncidentReportTaskValue in BBDD
			ImsIncidentReportTaskValue updated = null;

			updated = (ImsIncidentReportTaskValue) getObjectDao().update(conn, incidentReportTaskValue);

			if (updated == null) {

				throw new EntityDBException(EntityDBException.Operation.UPDATE, updated);
			}

			persistOperations.add(new MutablePair<>(PersistOperation.PUT, updated));

			if (commit) {

				conn.commit();

				// Update cache
				persistOperations(persistOperations);
			}

			result = updated;

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
			throw new EntityPersistenceException(EntityPersistenceException.Operation.UPDATE, incidentReportTaskValue);

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

		return result;
	}

	@Override
	public ImsIncidentReportTaskValue delete(ImsIncidentReportTaskValue imsIncidentTypeTaskValue) {
		try {

			return delete(imsIncidentTypeTaskValue, null, null);

		} catch (EntityPersistenceException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			return null;
		}
	}

	@Override
	public ImsIncidentReportTaskValue delete(ImsIncidentReportTaskValue imsIncidentReportTaskValue,
			Connection connection, List<Pair<PersistOperation, BasicElement>> persistOperations)
			throws EntityPersistenceException {
		Connection conn = null;
		boolean closeConnection = false;
		boolean commit = false;
		conn = connection;
		ImsIncidentReportTaskValue imsIncidentReportTaskValueResult = null;
		ImsIncidentReportTaskValue imsIncidentReportTaskValueAux = null;

		try {

			if (imsIncidentReportTaskValue == null || imsIncidentReportTaskValue.getIncidentReportId() == null
					|| imsIncidentReportTaskValue.getTaskTypeId() == null
					|| imsIncidentReportTaskValue.getTaskTypeParamId() == null
					|| imsIncidentReportTaskValue.getIncidentTypeTaskId() == null) {

				throw new NullPointerException("imsIncidentTypeTask is null");
			}

			imsIncidentReportTaskValueAux = get(imsIncidentReportTaskValue.getIncidentReportId(),
					imsIncidentReportTaskValue.getTaskTypeId(), imsIncidentReportTaskValue.getTaskTypeParamId(),
					imsIncidentReportTaskValue.getIncidentTypeTaskId(), false);

			if (imsIncidentReportTaskValueAux == null) {

				throw new NullPointerException("imsIncidentReportTaskValue not found");
			}

			if (conn == null) {

				conn = DataSourceConnection.getInstance().getConnection();
				conn.setAutoCommit(false);
				closeConnection = true;
				commit = true;
				persistOperations = new ArrayList<>();
			}

			// Delete ImsIncidentReportTaskValue in BBDD
			ImsIncidentReportTaskValue imsIncidentReportTaskValueDeleted = null;

			imsIncidentReportTaskValueDeleted = (ImsIncidentReportTaskValue) getObjectDao().delete(conn,
					imsIncidentReportTaskValueAux);

			if (imsIncidentReportTaskValueDeleted == null) {

				throw new EntityDBException(EntityDBException.Operation.DELETE, imsIncidentReportTaskValueAux);
			}

			persistOperations.add(new MutablePair<>(PersistOperation.DELETE, imsIncidentReportTaskValueAux));

			if (commit) {

				conn.commit();

				// Update cache
				persistOperations(persistOperations);
			}

			imsIncidentReportTaskValueResult = imsIncidentReportTaskValueDeleted;

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
					imsIncidentReportTaskValue);

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

		return imsIncidentReportTaskValueResult;
	}

}
