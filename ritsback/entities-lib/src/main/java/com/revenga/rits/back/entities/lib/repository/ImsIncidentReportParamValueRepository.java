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
import com.revenga.rits.back.data.core.model.ImsIncidentReportParamValue;
import com.revenga.rits.back.entities.lib.exception.EntityDBException;
import com.revenga.rits.back.entities.lib.exception.EntityPersistenceException;
import com.revenga.rits.back.entities.lib.repository.entity.EntityCacheable;
import com.revenga.rits.back.entities.lib.repository.entity.EntityPersistent;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository.PersistOperation;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class ImsIncidentReportParamValueRepository extends EntityRepository
		implements EntityCacheable<ImsIncidentReportParamValue>, EntityPersistent<ImsIncidentReportParamValue> {

	// region Constructors
	public ImsIncidentReportParamValueRepository() {

		super(ImsIncidentReportParamValue.class);
	}

	public ImsIncidentReportParamValueRepository(Boolean cacheable) {

		super(ImsIncidentReportParamValue.class, cacheable);
	}

	public ImsIncidentReportParamValueRepository(Boolean cacheable, Long cacheRetentionTime) {

		super(ImsIncidentReportParamValue.class, cacheable, cacheRetentionTime);
	}
	// endregion Constructors

	// region getters
	//"id", "incidentReportId", "incidentParamId"

	public ImsIncidentReportParamValue get(Long incidentReportId, Long incidentParamId, Boolean fillObject) {

		return (ImsIncidentReportParamValue) getFromCache(new String[] {String.valueOf(incidentReportId), String.valueOf(incidentParamId) ,null }, fillObject);
	}
	
	public List<ImsIncidentReportParamValue> gets(Long incidentReportId, Long incidentParamId, Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(
				new String[] { String.valueOf(incidentReportId), String.valueOf(incidentParamId), null, null }, fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(ImsIncidentReportParamValue.class::cast).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}
	
	public List<ImsIncidentReportParamValue> getsByIncidentReport(Long incidentReportId, Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(
				new String[] { String.valueOf(incidentReportId), null, null}, fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(ImsIncidentReportParamValue.class::cast).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}
	
	@Override
	public List<ImsIncidentReportParamValue> getAll(Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(ImsIncidentReportParamValue.class::cast).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}
	// endregion getters

	// region puts
	@Override
	public ImsIncidentReportParamValue put(ImsIncidentReportParamValue entity) {

		return (ImsIncidentReportParamValue) putFromCache(entity);
	}

	@Override
	public List<ImsIncidentReportParamValue> put(List<ImsIncidentReportParamValue> entities) {

		List<BasicElement> basicElements = entities.stream().map(BasicElement.class::cast)
				.collect(Collectors.toList());

		basicElements = putsFromCache(basicElements);

		return basicElements.stream().map(ImsIncidentReportParamValue.class::cast).collect(Collectors.toList());
	}
	// endregion puts

	// region dels
	@Override
	public ImsIncidentReportParamValue del(ImsIncidentReportParamValue entity) {

		return (ImsIncidentReportParamValue) delFromCache(entity);

	}

	@Override
	public List<ImsIncidentReportParamValue> del(List<ImsIncidentReportParamValue> entities) {

		List<BasicElement> basicElements = entities.stream().map(BasicElement.class::cast)
				.collect(Collectors.toList());

		basicElements = delFromCache(basicElements);

		return basicElements.stream().map(ImsIncidentReportParamValue.class::cast).collect(Collectors.toList());
	}
	// endregion dels

	@Override
	public ImsIncidentReportParamValue create(ImsIncidentReportParamValue incidentParamValue) {
		try {
			return create(incidentParamValue, null, null);
		} catch (EntityPersistenceException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			return null;
		}
	}

	@Override
	public ImsIncidentReportParamValue create(ImsIncidentReportParamValue incidentParamValue, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {

		Connection conn = null;
		boolean closeConnection = false;
		boolean commit = false;
		conn = connection;
		ImsIncidentReportParamValue incidentParamtValueResult = null;

		try {

			if (conn == null) {

				conn = DataSourceConnection.getInstance().getConnection();
				conn.setAutoCommit(false);
				closeConnection = true;
				commit = true;
				persistOperations = new ArrayList<>();
			}

			// Insert ElementValue in BBDD
			ImsIncidentReportParamValue incidentParamValueInserted = (ImsIncidentReportParamValue) incidentParamValue.clone();

			incidentParamValueInserted = (ImsIncidentReportParamValue) getObjectDao().insert(conn, incidentParamValueInserted);

			if (incidentParamValueInserted == null) {

				throw new EntityDBException(EntityDBException.Operation.INSERT, incidentParamValue);
			}

			persistOperations.add(new MutablePair<>(PersistOperation.PUT, incidentParamValueInserted));

			if (commit) {
				conn.commit();

				persistOperations(persistOperations);
				
				incidentParamtValueResult = get(incidentParamValueInserted.getIncidentReportId(),
							incidentParamValueInserted.getIncidentParamId(), false);
			}
	
		} catch (EntityDBException | SQLException | IOException | CloneNotSupportedException e) {

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
			throw new EntityPersistenceException(EntityPersistenceException.Operation.CREATE, incidentParamValue);

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

		return incidentParamtValueResult;

	}

	@Override
	public ImsIncidentReportParamValue clone(ImsIncidentReportParamValue entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ImsIncidentReportParamValue clone(ImsIncidentReportParamValue entity, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ImsIncidentReportParamValue update(ImsIncidentReportParamValue incidentParamValue) {

		try {

			return update(incidentParamValue, null, null);

		} catch (EntityPersistenceException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			return null;
		}
	}

	@Override
	public ImsIncidentReportParamValue update(ImsIncidentReportParamValue incidentParamValue, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {

		Connection conn = null;
		boolean closeConnection = false;
		boolean commit = false;
		conn = connection;
		ImsIncidentReportParamValue incidentParamValueResult = null;
		ImsIncidentReportParamValue incidentParamValueAux = null;

		try {

			if (incidentParamValue == null || incidentParamValue.getIncidentParamId() == null || incidentParamValue.getIncidentReportId()==null) {

				throw new NullPointerException("incidentParamValue is null");
			}

			incidentParamValueAux = get(incidentParamValue.getIncidentReportId(),
					incidentParamValue.getIncidentParamId(), false);

			if (incidentParamValueAux == null) {

				throw new NullPointerException("incidentParamValue not found");
			} else {
	
				incidentParamValueAux = (ImsIncidentReportParamValue) incidentParamValueAux.clone();
			}

			incidentParamValueAux.setValue(incidentParamValue.getValue());
			
			if (conn == null) {

				conn = DataSourceConnection.getInstance().getConnection();
				conn.setAutoCommit(false);
				closeConnection = true;
				commit = true;
				persistOperations = new ArrayList<>();
			}

			// Update imsIncidentType in BBDD
			ImsIncidentReportParamValue incidentParamValueUpdated = null;

			incidentParamValueUpdated = (ImsIncidentReportParamValue) getObjectDao().update(conn, incidentParamValueAux);

			if (incidentParamValueUpdated == null) {

				throw new EntityDBException(EntityDBException.Operation.UPDATE, incidentParamValueAux);
			}

			persistOperations.add(new MutablePair<>(PersistOperation.PUT, incidentParamValueUpdated));

			if (commit) {

				conn.commit();

				persistOperations(persistOperations);
			}

			incidentParamValueResult = incidentParamValueUpdated;

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
			throw new EntityPersistenceException(EntityPersistenceException.Operation.UPDATE, incidentParamValue);

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

		return incidentParamValueResult;
	}

	@Override
	public ImsIncidentReportParamValue delete(ImsIncidentReportParamValue incidentParamValue) {

		try {

			return delete(incidentParamValue, null, null);

		} catch (EntityPersistenceException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			return null;
		}
	}

	@Override
	public ImsIncidentReportParamValue delete(ImsIncidentReportParamValue incidentParamValue, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {
		Connection conn = null;
		boolean closeConnection = false;
		boolean commit = false;
		conn = connection;
		ImsIncidentReportParamValue incidentParamValueResult = null;
		ImsIncidentReportParamValue incidentParamValueAux = null;

		try {

			if (incidentParamValue == null || incidentParamValue.getIncidentParamId() == null
					|| incidentParamValue.getIncidentReportId() == null) {

				throw new NullPointerException("elementValue is null");
			}

			incidentParamValueAux = get(incidentParamValue.getIncidentReportId(),
					incidentParamValue.getIncidentParamId(), false);
			
			if (incidentParamValueAux == null) {

				throw new NullPointerException("elementValue not found");
			}

			if (conn == null) {

				conn = DataSourceConnection.getInstance().getConnection();
				conn.setAutoCommit(false);
				closeConnection = true;
				commit = true;
				persistOperations = new ArrayList<>();
			}

			// Delete elementValue in BBDD
			ImsIncidentReportParamValue incidentParamValueDeleted = null;

			incidentParamValueDeleted = (ImsIncidentReportParamValue) getObjectDao().delete(conn, incidentParamValueAux);

			if (incidentParamValueDeleted == null) {

				throw new EntityDBException(EntityDBException.Operation.DELETE, incidentParamValueAux);
			}

			persistOperations.add(new MutablePair<>(PersistOperation.DELETE, incidentParamValueAux));

			if (commit) {

				conn.commit();

				// Update cache
				persistOperations(persistOperations);
			}

			incidentParamValueResult = incidentParamValueDeleted;

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
			throw new EntityPersistenceException(EntityPersistenceException.Operation.DELETE, incidentParamValue);

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

		return incidentParamValueResult;
	}

}