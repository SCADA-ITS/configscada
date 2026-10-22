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
import com.revenga.rits.back.data.core.model.ImsIncidentReportTaskState;
import com.revenga.rits.back.entities.lib.exception.EntityDBException;
import com.revenga.rits.back.entities.lib.exception.EntityPersistenceException;
import com.revenga.rits.back.entities.lib.repository.entity.EntityCacheable;
import com.revenga.rits.back.entities.lib.repository.entity.EntityPersistent;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository.PersistOperation;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class ImsIncidentReportTaskStateRepository extends EntityRepository
		implements EntityCacheable<ImsIncidentReportTaskState>, EntityPersistent<ImsIncidentReportTaskState> {

	// region Constructors
	public ImsIncidentReportTaskStateRepository() {

		super(ImsIncidentReportTaskState.class);
	}

	public ImsIncidentReportTaskStateRepository(Boolean cacheable) {

		super(ImsIncidentReportTaskState.class, cacheable);
	}

	public ImsIncidentReportTaskStateRepository(Boolean cacheable, Long cacheRetentionTime) {

		super(ImsIncidentReportTaskState.class, cacheable, cacheRetentionTime);
	}

	// endregion Constructors

	// region getters
	public ImsIncidentReportTaskState get(Long incidentReportId, Long incidentTypeTaskId, Boolean fillObject) {

		return (ImsIncidentReportTaskState) getFromCache(
				new String[] { String.valueOf(incidentReportId), String.valueOf(incidentTypeTaskId) }, fillObject);
	}

	@Override
	public List<ImsIncidentReportTaskState> getAll(Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(be -> (ImsIncidentReportTaskState) be).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}

	public List<ImsIncidentReportTaskState> getByIncidentReport(Long incidentReportId, Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(new String[] { String.valueOf(incidentReportId), null },
				fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(be -> (ImsIncidentReportTaskState) be).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}
	// endregion getters

	// region puts
	@Override
	public ImsIncidentReportTaskState put(ImsIncidentReportTaskState entity) {

		return (ImsIncidentReportTaskState) putFromCache(entity);
	}

	@Override
	public List<ImsIncidentReportTaskState> put(List<ImsIncidentReportTaskState> entities) {

		List<BasicElement> basicElements = entities.stream().map(alarmConfig -> (BasicElement) alarmConfig)
				.collect(Collectors.toList());

		basicElements = putsFromCache(basicElements);

		return basicElements.stream().map(be -> (ImsIncidentReportTaskState) be).collect(Collectors.toList());
	}
	// endregion puts

	// region dels
	@Override
	public ImsIncidentReportTaskState del(ImsIncidentReportTaskState entity) {

		return (ImsIncidentReportTaskState) delFromCache(entity);

	}

	@Override
	public List<ImsIncidentReportTaskState> del(List<ImsIncidentReportTaskState> entities) {

		List<BasicElement> basicElements = entities.stream().map(alarmConfig -> (BasicElement) alarmConfig)
				.collect(Collectors.toList());

		basicElements = delFromCache(basicElements);

		return basicElements.stream().map(be -> (ImsIncidentReportTaskState) be).collect(Collectors.toList());
	}
	// endregion dels

	@Override
	public ImsIncidentReportTaskState create(ImsIncidentReportTaskState imsIncidentReportTaskState) {

		try {

			return create(imsIncidentReportTaskState, null, null);

		} catch (EntityPersistenceException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			return null;
		}
	}

	@Override
	public ImsIncidentReportTaskState create(ImsIncidentReportTaskState imsIncidentReportTaskState,
			Connection connection, List<Pair<PersistOperation, BasicElement>> persistOperations)
			throws EntityPersistenceException {

		Connection conn = null;
		boolean closeConnection = false;
		boolean commit = false;
		conn = connection;
		ImsIncidentReportTaskState imsIncidentReportTaskStateResult = null;

		try {

			if (conn == null) {

				conn = DataSourceConnection.getInstance().getConnection();
				conn.setAutoCommit(false);
				closeConnection = true;
				commit = true;
				persistOperations = new ArrayList<>();
			}

			// Insert imsIncidentReportTaskState in BBDD
			ImsIncidentReportTaskState imsIncidentReportTaskStateInserted = null;

			imsIncidentReportTaskStateInserted = (ImsIncidentReportTaskState) getObjectDao().insert(conn,
					imsIncidentReportTaskState);

			if (imsIncidentReportTaskStateInserted == null) {

				throw new EntityDBException(EntityDBException.Operation.INSERT, imsIncidentReportTaskState);
			}

			persistOperations.add(new MutablePair<>(PersistOperation.PUT, imsIncidentReportTaskStateInserted));

			if (commit) {
				conn.commit();

				persistOperations(persistOperations);
				
				imsIncidentReportTaskStateResult = get(imsIncidentReportTaskStateInserted.getIncidentReportId(),
						imsIncidentReportTaskStateInserted.getIncidentTypeTaskId(), false);
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
					imsIncidentReportTaskState);

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

		return imsIncidentReportTaskStateResult;
	}

	@Override
	public ImsIncidentReportTaskState clone(ImsIncidentReportTaskState entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ImsIncidentReportTaskState clone(ImsIncidentReportTaskState entity, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ImsIncidentReportTaskState update(ImsIncidentReportTaskState imsIncidentReportTaskState) {

		try {

			return update(imsIncidentReportTaskState, null, null);

		} catch (EntityPersistenceException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			return null;
		}
	}

	@Override
	public ImsIncidentReportTaskState update(ImsIncidentReportTaskState imsIncidentReportTaskState,
			Connection connection, List<Pair<PersistOperation, BasicElement>> persistOperations)
			throws EntityPersistenceException {

		Connection conn = null;
		boolean closeConnection = false;
		boolean commit = false;
		conn = connection;
		ImsIncidentReportTaskState imsIncidentReportTaskStateResult = null;

		try {

			if (imsIncidentReportTaskState == null || imsIncidentReportTaskState.getIncidentReportId() == null
					|| imsIncidentReportTaskState.getIncidentTypeTaskId() == null) {

				throw new NullPointerException("imsIncidentReportTaskState is null");
			}

			if (imsIncidentReportTaskState.getCompletedAt() == null) {

				throw new NullPointerException("CompletedAt is null");
			}

			if (imsIncidentReportTaskState.getError() == null) {

				throw new NullPointerException("Error is null");
			}
			
			if (get(imsIncidentReportTaskState.getIncidentReportId(),
					imsIncidentReportTaskState.getIncidentTypeTaskId(), false) == null) {

				throw new NullPointerException("imsIncidentReportTaskState not found");
			}

			if (conn == null) {

				conn = DataSourceConnection.getInstance().getConnection();
				conn.setAutoCommit(false);
				closeConnection = true;
				commit = true;
				persistOperations = new ArrayList<>();
			}

			// Update imsIncidentReportTaskState in BBDD
			ImsIncidentReportTaskState imsIncidentReportTaskStateUpdated = null;

			imsIncidentReportTaskStateUpdated = (ImsIncidentReportTaskState) getObjectDao().update(conn,
					imsIncidentReportTaskState);

			if (imsIncidentReportTaskStateUpdated == null) {

				throw new EntityDBException(EntityDBException.Operation.UPDATE, imsIncidentReportTaskState);
			}

			persistOperations.add(new MutablePair<>(PersistOperation.PUT, imsIncidentReportTaskStateUpdated));

			if (commit) {

				conn.commit();

				persistOperations(persistOperations);
			}

			imsIncidentReportTaskStateResult = imsIncidentReportTaskStateUpdated;

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
					imsIncidentReportTaskState);

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

		return imsIncidentReportTaskStateResult;
	}

	@Override
	public ImsIncidentReportTaskState delete(ImsIncidentReportTaskState imsIncidentReportTaskState) {

		try {

			return delete(imsIncidentReportTaskState, null, null);

		} catch (EntityPersistenceException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			return null;
		}
	}

	@Override
	public ImsIncidentReportTaskState delete(ImsIncidentReportTaskState imsIncidentReportTaskState,
			Connection connection, List<Pair<PersistOperation, BasicElement>> persistOperations)
			throws EntityPersistenceException {

		Connection conn = null;
		boolean closeConnection = false;
		boolean commit = false;
		conn = connection;
		ImsIncidentReportTaskState imsIncidentReportTaskPlanStateResult = null;

		try {

			if (imsIncidentReportTaskState == null || imsIncidentReportTaskState.getIncidentReportId() == null
					|| imsIncidentReportTaskState.getIncidentTypeTaskId() == null) {

				throw new NullPointerException("imsIncidentReportTaskState is null");
			}

			if (get(imsIncidentReportTaskState.getIncidentReportId(),
					imsIncidentReportTaskState.getIncidentTypeTaskId(), false) == null) {

				throw new NullPointerException("imsIncidentReportTaskState not found");
			}

			if (conn == null) {

				conn = DataSourceConnection.getInstance().getConnection();
				conn.setAutoCommit(false);
				closeConnection = true;
				commit = true;
				persistOperations = new ArrayList<>();
			}

			// Delete imsIncidentReportTaskState in BBDD
			ImsIncidentReportTaskState imsIncidentReportTaskStateDeleted = null;

			imsIncidentReportTaskStateDeleted = (ImsIncidentReportTaskState) getObjectDao().delete(conn,
					imsIncidentReportTaskState);

			if (imsIncidentReportTaskStateDeleted == null) {

				throw new EntityDBException(EntityDBException.Operation.DELETE, imsIncidentReportTaskState);
			}

			persistOperations.add(new MutablePair<>(PersistOperation.DELETE, imsIncidentReportTaskStateDeleted));

			if (commit) {

				conn.commit();

				// Update cache
				persistOperations(persistOperations);
			}

			imsIncidentReportTaskPlanStateResult = imsIncidentReportTaskStateDeleted;

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
					imsIncidentReportTaskState);

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

		return imsIncidentReportTaskPlanStateResult;
	}
}
