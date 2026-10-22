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
import com.revenga.rits.back.data.core.model.ImsIncidentReportTaskPlanState;
import com.revenga.rits.back.entities.lib.exception.EntityDBException;
import com.revenga.rits.back.entities.lib.exception.EntityPersistenceException;
import com.revenga.rits.back.entities.lib.repository.entity.EntityCacheable;
import com.revenga.rits.back.entities.lib.repository.entity.EntityPersistent;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository.PersistOperation;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class ImsIncidentReportTaskPlanStateRepository extends EntityRepository
		implements EntityCacheable<ImsIncidentReportTaskPlanState>, EntityPersistent<ImsIncidentReportTaskPlanState> {

	// region Constructors
	public ImsIncidentReportTaskPlanStateRepository() {

		super(ImsIncidentReportTaskPlanState.class);
	}

	public ImsIncidentReportTaskPlanStateRepository(Boolean cacheable) {

		super(ImsIncidentReportTaskPlanState.class, cacheable);
	}

	public ImsIncidentReportTaskPlanStateRepository(Boolean cacheable, Long cacheRetentionTime) {

		super(ImsIncidentReportTaskPlanState.class, cacheable, cacheRetentionTime);
	}

	// endregion Constructors

	// region getters
	public ImsIncidentReportTaskPlanState get(Long incidentReportId, Long incidentTaskPlanId, Boolean fillObject) {

		return (ImsIncidentReportTaskPlanState) getFromCache(
				new String[] { String.valueOf(incidentReportId), String.valueOf(incidentTaskPlanId) }, fillObject);
	}

	@Override
	public List<ImsIncidentReportTaskPlanState> getAll(Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(be -> (ImsIncidentReportTaskPlanState) be).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}

	public List<ImsIncidentReportTaskPlanState> getByIncidentReport(Long incidentReportId, Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(new String[] { String.valueOf(incidentReportId), null },
				fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(be -> (ImsIncidentReportTaskPlanState) be).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}
	
	public List<ImsIncidentReportTaskPlanState> getByIncidentTaskPlan(Long incidentTaskPlanId, Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(new String[] {null, String.valueOf(incidentTaskPlanId)},
				fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(be -> (ImsIncidentReportTaskPlanState) be).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}

	// endregion getters

	// region puts
	@Override
	public ImsIncidentReportTaskPlanState put(ImsIncidentReportTaskPlanState entity) {

		return (ImsIncidentReportTaskPlanState) putFromCache(entity);
	}

	@Override
	public List<ImsIncidentReportTaskPlanState> put(List<ImsIncidentReportTaskPlanState> entities) {

		List<BasicElement> basicElements = entities.stream().map(alarmConfig -> (BasicElement) alarmConfig)
				.collect(Collectors.toList());

		basicElements = putsFromCache(basicElements);

		return basicElements.stream().map(be -> (ImsIncidentReportTaskPlanState) be).collect(Collectors.toList());
	}
	// endregion puts

	// region dels
	@Override
	public ImsIncidentReportTaskPlanState del(ImsIncidentReportTaskPlanState entity) {

		return (ImsIncidentReportTaskPlanState) delFromCache(entity);

	}

	@Override
	public List<ImsIncidentReportTaskPlanState> del(List<ImsIncidentReportTaskPlanState> entities) {

		List<BasicElement> basicElements = entities.stream().map(alarmConfig -> (BasicElement) alarmConfig)
				.collect(Collectors.toList());

		basicElements = delFromCache(basicElements);

		return basicElements.stream().map(be -> (ImsIncidentReportTaskPlanState) be).collect(Collectors.toList());
	}
	// endregion dels

	@Override
	public ImsIncidentReportTaskPlanState create(ImsIncidentReportTaskPlanState imsIncidentReportTaskPlanState) {

		try {

			return create(imsIncidentReportTaskPlanState, null, null);

		} catch (EntityPersistenceException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			return null;
		}
	}

	@Override
	public ImsIncidentReportTaskPlanState create(ImsIncidentReportTaskPlanState imsIncidentReportTaskPlanState,
			Connection connection, List<Pair<PersistOperation, BasicElement>> persistOperations)
			throws EntityPersistenceException {

		Connection conn = null;
		boolean closeConnection = false;
		boolean commit = false;
		conn = connection;
		ImsIncidentReportTaskPlanState imsIncidentReportTaskPlanStateResult = null;

		try {

			if (conn == null) {

				conn = DataSourceConnection.getInstance().getConnection();
				conn.setAutoCommit(false);
				closeConnection = true;
				commit = true;
				persistOperations = new ArrayList<>();
			}

			// Insert imsIncidentReportTaskPlanState in BBDD
			ImsIncidentReportTaskPlanState imsIncidentReportTaskPlanStateInserted = null;

			imsIncidentReportTaskPlanStateInserted = (ImsIncidentReportTaskPlanState) getObjectDao().insert(conn,
					imsIncidentReportTaskPlanState);

			if (imsIncidentReportTaskPlanStateInserted == null) {

				throw new EntityDBException(EntityDBException.Operation.INSERT, imsIncidentReportTaskPlanState);
			}

			persistOperations.add(new MutablePair<>(PersistOperation.PUT, imsIncidentReportTaskPlanStateInserted));

			if (commit) {
				conn.commit();

				persistOperations(persistOperations);
				
				imsIncidentReportTaskPlanStateResult = get(imsIncidentReportTaskPlanStateInserted.getIncidentReportId(),
						imsIncidentReportTaskPlanStateInserted.getIncidentTaskPlanId(), false);	
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
					imsIncidentReportTaskPlanState);

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

	@Override
	public ImsIncidentReportTaskPlanState clone(ImsIncidentReportTaskPlanState entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ImsIncidentReportTaskPlanState clone(ImsIncidentReportTaskPlanState entity, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ImsIncidentReportTaskPlanState update(ImsIncidentReportTaskPlanState imsIncidentReportTaskPlanState) {

		try {

			return update(imsIncidentReportTaskPlanState, null, null);

		} catch (EntityPersistenceException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			return null;
		}
	}

	@Override
	public ImsIncidentReportTaskPlanState update(ImsIncidentReportTaskPlanState imsIncidentReportTaskPlanState,
			Connection connection, List<Pair<PersistOperation, BasicElement>> persistOperations)
			throws EntityPersistenceException {

		Connection conn = null;
		boolean closeConnection = false;
		boolean commit = false;
		conn = connection;
		ImsIncidentReportTaskPlanState imsIncidentReportTaskPlanStateResult = null;

		try {

			if (imsIncidentReportTaskPlanState == null || imsIncidentReportTaskPlanState.getIncidentReportId() == null
					|| imsIncidentReportTaskPlanState.getIncidentTaskPlanId() == null) {

				throw new NullPointerException("imsIncidentReportTaskPlanState is null");
			}

			if (imsIncidentReportTaskPlanState.getCompletedAt() == null) {

				throw new NullPointerException("CompletedAt is null");
			}

			if (get(imsIncidentReportTaskPlanState.getIncidentReportId(),
					imsIncidentReportTaskPlanState.getIncidentTaskPlanId(), false) == null) {

				throw new NullPointerException("imsIncidentReportTaskPlanState not found");
			}

			if (conn == null) {

				conn = DataSourceConnection.getInstance().getConnection();
				conn.setAutoCommit(false);
				closeConnection = true;
				commit = true;
				persistOperations = new ArrayList<>();
			}

			// Update imsIncidentReportTaskPlanState in BBDD
			ImsIncidentReportTaskPlanState imsIncidentReportTaskPlanStateUpdated = null;

			imsIncidentReportTaskPlanStateUpdated = (ImsIncidentReportTaskPlanState) getObjectDao().update(conn,
					imsIncidentReportTaskPlanState);

			if (imsIncidentReportTaskPlanStateUpdated == null) {

				throw new EntityDBException(EntityDBException.Operation.UPDATE, imsIncidentReportTaskPlanStateUpdated);
			}

			persistOperations.add(new MutablePair<>(PersistOperation.PUT, imsIncidentReportTaskPlanStateUpdated));

			if (commit) {

				conn.commit();

				persistOperations(persistOperations);
			}

			imsIncidentReportTaskPlanStateResult = imsIncidentReportTaskPlanStateUpdated;

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
					imsIncidentReportTaskPlanState);

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

	@Override
	public ImsIncidentReportTaskPlanState delete(final ImsIncidentReportTaskPlanState imsIncidentReportTaskPlanState) {

		try {

			return delete(imsIncidentReportTaskPlanState, null, null);

		} catch (EntityPersistenceException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			return null;
		}
	}

	@Override
	public ImsIncidentReportTaskPlanState delete(final ImsIncidentReportTaskPlanState imsIncidentReportTaskPlanState,
			Connection connection, List<Pair<PersistOperation, BasicElement>> persistOperations)
			throws EntityPersistenceException {

		Connection conn = null;
		boolean closeConnection = false;
		boolean commit = false;
		conn = connection;
		ImsIncidentReportTaskPlanState imsIncidentReportTaskPlanStateResult = null;

		try {

			if (imsIncidentReportTaskPlanState == null || imsIncidentReportTaskPlanState.getIncidentReportId() == null
					|| imsIncidentReportTaskPlanState.getIncidentTaskPlanId() == null) {

				throw new NullPointerException("imsIncidentReportTaskPlanState is null");
			}

			if (get(imsIncidentReportTaskPlanState.getIncidentReportId(),
					imsIncidentReportTaskPlanState.getIncidentTaskPlanId(), false) == null) {

				throw new NullPointerException("imsIncidentReportTaskPlanState not found");
			}

			if (conn == null) {

				conn = DataSourceConnection.getInstance().getConnection();
				conn.setAutoCommit(false);
				closeConnection = true;
				commit = true;
				persistOperations = new ArrayList<>();
			}

			// Delete imsIncidentReportTaskPlanState in BBDD
			ImsIncidentReportTaskPlanState imsIncidentReportTaskPlanStateDeleted = null;

			imsIncidentReportTaskPlanStateDeleted = (ImsIncidentReportTaskPlanState) getObjectDao().delete(conn,
					imsIncidentReportTaskPlanState);

			if (imsIncidentReportTaskPlanStateDeleted == null) {

				throw new EntityDBException(EntityDBException.Operation.DELETE, imsIncidentReportTaskPlanState);
			}

			persistOperations.add(new MutablePair<>(PersistOperation.DELETE, imsIncidentReportTaskPlanStateDeleted));

			if (commit) {

				conn.commit();

				// Update cache
				persistOperations(persistOperations);
			}

			imsIncidentReportTaskPlanStateResult = imsIncidentReportTaskPlanStateDeleted;

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
					imsIncidentReportTaskPlanState);

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
