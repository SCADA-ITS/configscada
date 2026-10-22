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
import com.revenga.rits.back.data.core.model.ImsIncidentReportTaskElement;
import com.revenga.rits.back.entities.lib.exception.EntityDBException;
import com.revenga.rits.back.entities.lib.exception.EntityPersistenceException;
import com.revenga.rits.back.entities.lib.repository.entity.EntityCacheable;
import com.revenga.rits.back.entities.lib.repository.entity.EntityPersistent;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository.PersistOperation;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class ImsIncidentReportTaskElementRepository extends EntityRepository
		implements EntityCacheable<ImsIncidentReportTaskElement>, EntityPersistent<ImsIncidentReportTaskElement> {

	// region Constructors
	public ImsIncidentReportTaskElementRepository() {

		super(ImsIncidentReportTaskElement.class);
	}

	public ImsIncidentReportTaskElementRepository(Boolean cacheable) {

		super(ImsIncidentReportTaskElement.class, cacheable);
	}

	public ImsIncidentReportTaskElementRepository(Boolean cacheable, Long cacheRetentionTime) {

		super(ImsIncidentReportTaskElement.class, cacheable, cacheRetentionTime);
	}

	// endregion Constructors

	// region getters

	public ImsIncidentReportTaskElement get(Long incidentReportId, Long incidentTypeTaskId, Long elementTypeId,
			Long elementId, Boolean fillObject) {

		return (ImsIncidentReportTaskElement) getFromCache(new String[] { String.valueOf(incidentReportId),
				String.valueOf(incidentTypeTaskId), String.valueOf(elementTypeId), String.valueOf(elementId) },
				fillObject);
	}

	@Override
	public List<ImsIncidentReportTaskElement> getAll(Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(ImsIncidentReportTaskElement.class::cast).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}

	public List<ImsIncidentReportTaskElement> getsByIncidentReport(Long incidentReportId, Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(
				new String[] { String.valueOf(incidentReportId), null, null, null }, fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(ImsIncidentReportTaskElement.class::cast).collect(Collectors.toList());
		}
		return Collections.emptyList();
	}

	// endregion getters

	// region puts
	/**
	 * 
	 */
	@Override
	public ImsIncidentReportTaskElement put(ImsIncidentReportTaskElement entity) {

		return (ImsIncidentReportTaskElement) putFromCache(entity);
	}

	/**
	 * 
	 */
	@Override
	public List<ImsIncidentReportTaskElement> put(List<ImsIncidentReportTaskElement> entities) {

		List<BasicElement> basicElements = entities.stream().map(BasicElement.class::cast).collect(Collectors.toList());

		basicElements = putsFromCache(basicElements);

		return basicElements.stream().map(ImsIncidentReportTaskElement.class::cast).collect(Collectors.toList());
	}
	// endregion puts

	// region dels
	/**
	 * 
	 */
	@Override
	public ImsIncidentReportTaskElement del(ImsIncidentReportTaskElement entity) {

		return (ImsIncidentReportTaskElement) delFromCache(entity);
	}

	/**
	 * 
	 */
	@Override
	public List<ImsIncidentReportTaskElement> del(List<ImsIncidentReportTaskElement> entities) {

		List<BasicElement> basicElements = entities.stream().map(BasicElement.class::cast).collect(Collectors.toList());

		basicElements = delFromCache(basicElements);

		return basicElements.stream().map(ImsIncidentReportTaskElement.class::cast).collect(Collectors.toList());
	}
	// endregion dels

	@Override
	public ImsIncidentReportTaskElement create(ImsIncidentReportTaskElement entity) {
		try {

			return create(entity, null, null);

		} catch (EntityPersistenceException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			return null;
		}
	}

	@Override
	public ImsIncidentReportTaskElement create(ImsIncidentReportTaskElement entity, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {

		Connection conn = null;
		boolean closeConnection = false;
		boolean commit = false;
		conn = connection;
		ImsIncidentReportTaskElement result = null;

		try {

			if (conn == null) {

				conn = DataSourceConnection.getInstance().getConnection();
				conn.setAutoCommit(false);
				closeConnection = true;
				commit = true;
				persistOperations = new ArrayList<>();
			}

			// Insert planOrderElement in BBDD
			ImsIncidentReportTaskElement inserted = null;
			 
			inserted = (ImsIncidentReportTaskElement) getObjectDao().insert(conn, entity);

			if (inserted == null) {

				throw new EntityDBException(EntityDBException.Operation.INSERT, entity);
			}

			persistOperations.add(new MutablePair<>(PersistOperation.PUT, entity));

			if (commit) {
				conn.commit();

				persistOperations(persistOperations);
				
				result = get(entity.getIncidentReportId(), entity.getIncidentTypeTaskId(), entity.getElementTypeId(),
						entity.getElementId(), false);
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
	public ImsIncidentReportTaskElement clone(ImsIncidentReportTaskElement entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ImsIncidentReportTaskElement clone(ImsIncidentReportTaskElement entity, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ImsIncidentReportTaskElement update(ImsIncidentReportTaskElement entity) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public ImsIncidentReportTaskElement update(ImsIncidentReportTaskElement entity, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ImsIncidentReportTaskElement delete(ImsIncidentReportTaskElement imsIncidentTypeTaskElement) {
		try {

			return delete(imsIncidentTypeTaskElement, null, null);

		} catch (EntityPersistenceException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			return null;
		}
	}

	@Override
	public ImsIncidentReportTaskElement delete(ImsIncidentReportTaskElement imsIncidentReportTaskElement,
			Connection connection, List<Pair<PersistOperation, BasicElement>> persistOperations)
			throws EntityPersistenceException {
		Connection conn = null;
		boolean closeConnection = false;
		boolean commit = false;
		conn = connection;
		ImsIncidentReportTaskElement imsIncidentReportTaskElementResult = null;
		ImsIncidentReportTaskElement imsIncidentReportTaskElementAux = null;

		try {

			if (imsIncidentReportTaskElement == null || imsIncidentReportTaskElement.getIncidentReportId() == null
					|| imsIncidentReportTaskElement.getIncidentTypeTaskId() == null
					|| imsIncidentReportTaskElement.getElementTypeId() == null
					|| imsIncidentReportTaskElement.getElementId() == null) {

				throw new NullPointerException("imsIncidentReportTaskElement is null");
			}

			imsIncidentReportTaskElementAux = get(imsIncidentReportTaskElement.getIncidentReportId(),
					imsIncidentReportTaskElement.getIncidentTypeTaskId(),
					imsIncidentReportTaskElement.getElementTypeId(), imsIncidentReportTaskElement.getElementId(),
					false);

			if (imsIncidentReportTaskElementAux == null) {

				throw new NullPointerException("imsIncidentReportTaskElement not found");
			}

			if (conn == null) {

				conn = DataSourceConnection.getInstance().getConnection();
				conn.setAutoCommit(false);
				closeConnection = true;
				commit = true;
				persistOperations = new ArrayList<>();
			}

			// Delete ImsIncidentReportTaskElement in BBDD
			ImsIncidentReportTaskElement imsIncidentReportTaskElementDeleted = null;
	
			imsIncidentReportTaskElementDeleted = (ImsIncidentReportTaskElement) getObjectDao().delete(conn,
					imsIncidentReportTaskElementAux);

			if (imsIncidentReportTaskElementDeleted == null) {

				throw new EntityDBException(EntityDBException.Operation.DELETE, imsIncidentReportTaskElementAux);
			}

			persistOperations.add(new MutablePair<>(PersistOperation.DELETE, imsIncidentReportTaskElementAux));

			if (commit) {

				conn.commit();

				// Update cache
				persistOperations(persistOperations);
			}

			imsIncidentReportTaskElementResult = imsIncidentReportTaskElementDeleted;

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
					imsIncidentReportTaskElement);

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
		return imsIncidentReportTaskElementResult;
	}

}
