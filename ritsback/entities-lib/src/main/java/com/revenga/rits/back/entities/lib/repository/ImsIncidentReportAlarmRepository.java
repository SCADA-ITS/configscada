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
import com.revenga.rits.back.data.core.model.ImsIncidentReportAlarm;
import com.revenga.rits.back.entities.lib.exception.EntityDBException;
import com.revenga.rits.back.entities.lib.exception.EntityPersistenceException;
import com.revenga.rits.back.entities.lib.repository.entity.EntityCacheable;
import com.revenga.rits.back.entities.lib.repository.entity.EntityPersistent;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository.PersistOperation;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class ImsIncidentReportAlarmRepository extends EntityRepository
		implements EntityCacheable<ImsIncidentReportAlarm>, EntityPersistent<ImsIncidentReportAlarm> {

	// region Constructors
	public ImsIncidentReportAlarmRepository() {

		super(ImsIncidentReportAlarm.class);
	}

	public ImsIncidentReportAlarmRepository(Boolean cacheable) {

		super(ImsIncidentReportAlarm.class, cacheable);
	}

	public ImsIncidentReportAlarmRepository(Boolean cacheable, Long cacheRetentionTime) {

		super(ImsIncidentReportAlarm.class, cacheable, cacheRetentionTime);
	}
	// endregion Constructors

	// region getters
	public ImsIncidentReportAlarm get(Long incidentReportId, Long incidentReportAlarmConfigId, Long elementTypeId, Long elementId,
			Boolean fillObject) {

		return (ImsIncidentReportAlarm) getFromCache(new String[] { String.valueOf(incidentReportId),
				String.valueOf(incidentReportAlarmConfigId), String.valueOf(elementTypeId), String.valueOf(elementId) }, fillObject);
	}

	public List<ImsIncidentReportAlarm> getsByIncidentReport(Long incidentReportId, Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(
				new String[] { String.valueOf(incidentReportId), null, null, null }, fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(be -> (ImsIncidentReportAlarm) be).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}

	public List<ImsIncidentReportAlarm> getAll(Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(be -> (ImsIncidentReportAlarm) be).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}

	// endregion getters

	// region puts
	@Override
	public ImsIncidentReportAlarm put(ImsIncidentReportAlarm entity) {

		return (ImsIncidentReportAlarm) putFromCache(entity);
	}

	@Override
	public List<ImsIncidentReportAlarm> put(List<ImsIncidentReportAlarm> entities) {

		List<BasicElement> basicElements = entities.stream().map(incidentReportAlarm -> (BasicElement) incidentReportAlarm)
				.collect(Collectors.toList());

		basicElements = putsFromCache(basicElements);

		return basicElements.stream().map(be -> (ImsIncidentReportAlarm) be).collect(Collectors.toList());
	}
	// endregion puts

	// region dels
	@Override
	public ImsIncidentReportAlarm del(ImsIncidentReportAlarm entity) {

		return (ImsIncidentReportAlarm) delFromCache(entity);

	}

	@Override
	public List<ImsIncidentReportAlarm> del(List<ImsIncidentReportAlarm> entities) {

		List<BasicElement> basicElements = entities.stream().map(incidentReportAlarm -> (BasicElement) incidentReportAlarm)
				.collect(Collectors.toList());

		basicElements = delFromCache(basicElements);

		return basicElements.stream().map(be -> (ImsIncidentReportAlarm) be).collect(Collectors.toList());
	}
	// endregion dels

	@Override
	public ImsIncidentReportAlarm create(ImsIncidentReportAlarm incidentReportAlarm) {

		try {

			return create(incidentReportAlarm, null, null);

		} catch (EntityPersistenceException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			return null;
		}
	}

	@Override
	public ImsIncidentReportAlarm create(ImsIncidentReportAlarm incidentReportAlarm, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {

		Connection conn = null;
		boolean closeConnection = false;
		boolean commit = false;
		conn = connection;
		ImsIncidentReportAlarm incidentReportAlarmResult = null;

		try {

			if (conn == null) {

				conn = DataSourceConnection.getInstance().getConnection();
				conn.setAutoCommit(false);
				closeConnection = true;
				commit = true;
				persistOperations = new ArrayList<>();
			}

			// Insert ImsIncidentReportAlarm in BBDD
			ImsIncidentReportAlarm incidentReportAlarmInserted = null;

			incidentReportAlarmInserted = (ImsIncidentReportAlarm) getObjectDao().insert(conn, incidentReportAlarm);

			if (incidentReportAlarmInserted == null) {

				throw new EntityDBException(EntityDBException.Operation.INSERT, incidentReportAlarm);
			}

			persistOperations.add(new MutablePair<>(PersistOperation.PUT, incidentReportAlarmInserted));

			if (commit) {
				conn.commit();

				persistOperations(persistOperations);
				
			}
			
			incidentReportAlarmResult = incidentReportAlarmInserted;


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
			throw new EntityPersistenceException(EntityPersistenceException.Operation.CREATE, incidentReportAlarm);

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

		return incidentReportAlarmResult;
	}

	@Override
	public ImsIncidentReportAlarm clone(ImsIncidentReportAlarm entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ImsIncidentReportAlarm clone(ImsIncidentReportAlarm entity, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ImsIncidentReportAlarm update(ImsIncidentReportAlarm incidentReportAlarm) {

		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ImsIncidentReportAlarm update(ImsIncidentReportAlarm entity, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public ImsIncidentReportAlarm delete(ImsIncidentReportAlarm incidentReportAlarm) {

		try {

			return delete(incidentReportAlarm, null, null);

		} catch (EntityPersistenceException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			return null;
		}
	}

	@Override
	public ImsIncidentReportAlarm delete(ImsIncidentReportAlarm incidentReportAlarm, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {

		Connection conn = null;
		boolean closeConnection = false;
		boolean commit = false;
		conn = connection;
		ImsIncidentReportAlarm incidentReportAlarmResult = null;

		try {

			if (incidentReportAlarm == null) {

				throw new NullPointerException("incidentReportAlarm is null");
			}

			if (conn == null) {

				conn = DataSourceConnection.getInstance().getConnection();
				conn.setAutoCommit(false);
				closeConnection = true;
				commit = true;
				persistOperations = new ArrayList<>();
			}

			// Delete ImsIncidentReportAlarm in BBDD
			ImsIncidentReportAlarm incidentReportAlarmDeleted = null;

			incidentReportAlarmDeleted = (ImsIncidentReportAlarm) getObjectDao().delete(conn, incidentReportAlarm);

			if (incidentReportAlarmDeleted == null) {

				throw new EntityDBException(EntityDBException.Operation.DELETE, incidentReportAlarm);
			}

			persistOperations.add(new MutablePair<>(PersistOperation.DELETE, incidentReportAlarm));

			if (commit) {

				conn.commit();

				// Update cache
				persistOperations(persistOperations);
			}

			incidentReportAlarmResult = incidentReportAlarmDeleted;

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
			throw new EntityPersistenceException(EntityPersistenceException.Operation.DELETE, incidentReportAlarm);

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

		return incidentReportAlarmResult;
	}

}
