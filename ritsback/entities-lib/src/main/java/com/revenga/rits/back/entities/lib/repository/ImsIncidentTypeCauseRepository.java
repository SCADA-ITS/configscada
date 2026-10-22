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
import com.revenga.rits.back.data.core.model.ImsIncidentTypeCause;
import com.revenga.rits.back.entities.lib.exception.EntityDBException;
import com.revenga.rits.back.entities.lib.exception.EntityPersistenceException;
import com.revenga.rits.back.entities.lib.repository.entity.EntityCacheable;
import com.revenga.rits.back.entities.lib.repository.entity.EntityPersistent;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository.PersistOperation;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class ImsIncidentTypeCauseRepository extends EntityRepository implements EntityCacheable<ImsIncidentTypeCause>, EntityPersistent<ImsIncidentTypeCause> {
	
	// region Constructors
	/**
	 * 
	 */
	public ImsIncidentTypeCauseRepository() {

		super(ImsIncidentTypeCause.class);
	}

	/**
	 * 
	 * @param cacheable
	 */
	public ImsIncidentTypeCauseRepository(Boolean cacheable) {

		super(ImsIncidentTypeCause.class, cacheable);
	}

	/**
	 * 
	 * @param cacheable
	 * @param cacheRetentionTime
	 */
	public ImsIncidentTypeCauseRepository(Boolean cacheable, Long cacheRetentionTime) {

		super(ImsIncidentTypeCause.class, cacheable, cacheRetentionTime);
	}
	
	/**
	 * 
	 * @param cacheable
	 * @param repositories
	 */
	public ImsIncidentTypeCauseRepository(Boolean cacheable, List<EntityRepository> repositories) {

		super(ImsIncidentTypeCause.class, cacheable, repositories);
	}

	/**
	 * 
	 * @param cacheable
	 * @param cacheRetentionTime
	 * @param repositories
	 */
	public ImsIncidentTypeCauseRepository(Boolean cacheable, Long cacheRetentionTime,
			List<EntityRepository> repositories) {

		super(ImsIncidentTypeCause.class, cacheable, cacheRetentionTime, repositories);
	}

	// endregion Constructors

	// region getters
	/**
	 * 
	 * @param incidentTypeId
	 * @param id
	 * @param fillObject
	 * @return
	 */
	public ImsIncidentTypeCause get(Long incidentTypeId, Long id, Boolean fillObject) {

		return (ImsIncidentTypeCause) getFromCache(
				new String[] { String.valueOf(incidentTypeId), String.valueOf(id) }, fillObject);
	}
	
	/**
	 * 
	 * @param incidentTypeId
	 * @param fillObject
	 * @return
	 */
	public List<ImsIncidentTypeCause> getsByIncidentType(Long incidentTypeId, Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(new String[] { String.valueOf(incidentTypeId), null },
				fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(be -> (ImsIncidentTypeCause) be).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}

	/**
	 * 
	 */
	@Override
	public List<ImsIncidentTypeCause> getAll(Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(be -> (ImsIncidentTypeCause) be).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}

	// endregion getters

	// region puts
	/**
	 * 
	 */
	@Override
	public ImsIncidentTypeCause put(ImsIncidentTypeCause entity) {

		return (ImsIncidentTypeCause) putFromCache(entity);
	}

	/**
	 * 
	 */
	@Override
	public List<ImsIncidentTypeCause> put(List<ImsIncidentTypeCause> entities) {

		List<BasicElement> basicElements = entities.stream().map(alarmConfig -> (BasicElement) alarmConfig)
				.collect(Collectors.toList());

		basicElements = putsFromCache(basicElements);

		return basicElements.stream().map(be -> (ImsIncidentTypeCause) be).collect(Collectors.toList());
	}
	// endregion puts

	// region dels
	/**
	 * 
	 */
	@Override
	public ImsIncidentTypeCause del(ImsIncidentTypeCause entity) {

		return (ImsIncidentTypeCause) delFromCache(entity);

	}

	/**
	 * 
	 */
	@Override
	public List<ImsIncidentTypeCause> del(List<ImsIncidentTypeCause> entities) {

		List<BasicElement> basicElements = entities.stream().map(alarmConfig -> (BasicElement) alarmConfig)
				.collect(Collectors.toList());

		basicElements = delFromCache(basicElements);

		return basicElements.stream().map(be -> (ImsIncidentTypeCause) be).collect(Collectors.toList());
	}

	// endregion dels
	
	@Override
	public ImsIncidentTypeCause delete(ImsIncidentTypeCause incidentTypeCause) {

		try {

			return delete(incidentTypeCause, null, null);

		} catch (EntityPersistenceException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			return null;
		}
	}
	
	
	@Override
	public ImsIncidentTypeCause delete(ImsIncidentTypeCause incidentTypeCause, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {
		Connection conn = null;
		boolean closeConnection = false;
		boolean commit = false;
		conn = connection;
		ImsIncidentTypeCause incidentTypeCauseResult = null;
		ImsIncidentTypeCause incidentTypeCauseAux = null;

		try {

			if (incidentTypeCause == null || incidentTypeCause.getIncidentTypeId() == null
					|| incidentTypeCause.getId() == null) {

				throw new NullPointerException("incidentTypeCause is null");
			}

			incidentTypeCauseAux = get(incidentTypeCause.getIncidentTypeId(),
					incidentTypeCause.getId(), false);
			
			if (incidentTypeCauseAux == null) {

				throw new NullPointerException("incidentTypeCause not found");
			}

			if (conn == null) {

				conn = DataSourceConnection.getInstance().getConnection();
				conn.setAutoCommit(false);
				closeConnection = true;
				commit = true;
				persistOperations = new ArrayList<>();
			}

			// Delete ImsIncidentTypeCause in BBDD
			ImsIncidentTypeCause incidentTypeCauseDeleted = null;

			incidentTypeCauseDeleted = (ImsIncidentTypeCause) getObjectDao().delete(conn, incidentTypeCauseAux);

			if (incidentTypeCauseDeleted == null) {

				throw new EntityDBException(EntityDBException.Operation.DELETE, incidentTypeCauseAux);
			}

			persistOperations.add(new MutablePair<>(PersistOperation.DELETE, incidentTypeCauseAux));

			if (commit) {

				conn.commit();

				// Update cache
				persistOperations(persistOperations);
			}

			incidentTypeCauseResult = incidentTypeCauseDeleted;

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
			throw new EntityPersistenceException(EntityPersistenceException.Operation.DELETE, incidentTypeCause);

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

		return incidentTypeCauseResult;
	}

	@Override
	public ImsIncidentTypeCause create(ImsIncidentTypeCause imsIncidentTypeCause) {

		try {

			return create(imsIncidentTypeCause, null, null);

		} catch (EntityPersistenceException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			return null;
		}
	}
	
	@Override
	public ImsIncidentTypeCause create(ImsIncidentTypeCause imsIncidentTypeCause, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {

		Connection conn = null;
		boolean closeConnection = false;
		boolean commit = false;
		conn = connection;
		ImsIncidentTypeCause imsIncidentTypeCauseResult = null;

		try {

			if (conn == null) {

				conn = DataSourceConnection.getInstance().getConnection();
				conn.setAutoCommit(false);
				closeConnection = true;
				commit = true;
				persistOperations = new ArrayList<>();
			}

			// Insert imsIncidentType in BBDD
			ImsIncidentTypeCause imsIncidentTypeCauseInserted = null;

			imsIncidentTypeCauseInserted = (ImsIncidentTypeCause) getObjectDao().insert(conn, imsIncidentTypeCause);

			if (imsIncidentTypeCauseInserted == null) {

				throw new EntityDBException(EntityDBException.Operation.INSERT, imsIncidentTypeCause);
			}

			persistOperations.add(new MutablePair<>(PersistOperation.PUT, imsIncidentTypeCauseInserted));
			
			if (commit) {
				conn.commit();

				persistOperations(persistOperations);

				imsIncidentTypeCauseResult = get(imsIncidentTypeCauseInserted.getIncidentTypeId(),
						imsIncidentTypeCauseInserted.getId(), false);
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
			throw new EntityPersistenceException(EntityPersistenceException.Operation.CREATE, imsIncidentTypeCause);

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

		return imsIncidentTypeCauseResult;
	}

	
	@Override
	public ImsIncidentTypeCause clone(ImsIncidentTypeCause entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ImsIncidentTypeCause clone(ImsIncidentTypeCause entity, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ImsIncidentTypeCause update(ImsIncidentTypeCause entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ImsIncidentTypeCause update(ImsIncidentTypeCause entity, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {
		// TODO Auto-generated method stub
		return null;
	}
}
