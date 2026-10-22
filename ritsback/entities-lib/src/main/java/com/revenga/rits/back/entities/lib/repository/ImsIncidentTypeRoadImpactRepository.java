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
import com.revenga.rits.back.data.core.model.BasicElement;
import com.revenga.rits.back.data.core.model.ImsIncidentTypeRoadImpact;
import com.revenga.rits.back.entities.lib.exception.EntityDBException;
import com.revenga.rits.back.entities.lib.exception.EntityPersistenceException;
import com.revenga.rits.back.entities.lib.repository.entity.EntityCacheable;
import com.revenga.rits.back.entities.lib.repository.entity.EntityPersistent;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepositoryOptions;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class ImsIncidentTypeRoadImpactRepository extends EntityRepository
		implements EntityCacheable<ImsIncidentTypeRoadImpact>, EntityPersistent<ImsIncidentTypeRoadImpact> {

	// region Constructors
	/**
	 * 
	 */
	public ImsIncidentTypeRoadImpactRepository() {

		super(ImsIncidentTypeRoadImpact.class);
	}

	public ImsIncidentTypeRoadImpactRepository(EntityRepositoryOptions options) {

		super(ImsIncidentTypeRoadImpact.class, options);
	}

	// Getters Region

	public ImsIncidentTypeRoadImpact get(Long incidentTypeId, Long stretchId, Long roadImpactId, Boolean fillObject) {

		return (ImsIncidentTypeRoadImpact) getFromCache(new String[] { String.valueOf(incidentTypeId),
				String.valueOf(stretchId), String.valueOf(roadImpactId) }, fillObject);
	}

	@Override
	public List<ImsIncidentTypeRoadImpact> getAll(Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(ImsIncidentTypeRoadImpact.class::cast).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}

	public List<ImsIncidentTypeRoadImpact> getsByIncidentTypeStretch(Long incidentTypeId, Long stretchId,
			Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(
				new String[] { String.valueOf(incidentTypeId), String.valueOf(stretchId) }, fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(ImsIncidentTypeRoadImpact.class::cast).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}

	// end Getters Region

	@Override
	public ImsIncidentTypeRoadImpact create(ImsIncidentTypeRoadImpact incidentTypeRoadImpact) {

		try {

			return create(incidentTypeRoadImpact, null, null);

		} catch (EntityPersistenceException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			return null;
		}
	}

	@Override
	public ImsIncidentTypeRoadImpact create(ImsIncidentTypeRoadImpact incidentTypeRoadImpact, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {
		Connection conn = null;
		boolean closeConnection = false;
		boolean commit = false;
		conn = connection;
		ImsIncidentTypeRoadImpact incidentTypeRoadImpactResult = null;

		try {

			if (conn == null) {

				conn = DataSourceConnection.getInstance().getConnection();
				conn.setAutoCommit(false);
				closeConnection = true;
				commit = true;
				persistOperations = new ArrayList<>();
			}

			// ImsIncidentTypeRoadImpact
			ImsIncidentTypeRoadImpact incidentTypeRoadImpactInserted = null;

			incidentTypeRoadImpactInserted = (ImsIncidentTypeRoadImpact) getObjectDao().insert(conn,
					incidentTypeRoadImpact);

			if (incidentTypeRoadImpactInserted == null) {

				throw new EntityDBException(EntityDBException.Operation.INSERT, incidentTypeRoadImpact);
			}

			persistOperations.add(new MutablePair<>(PersistOperation.PUT, incidentTypeRoadImpactInserted));

			if (commit) {
				conn.commit();

				persistOperations(persistOperations);

				incidentTypeRoadImpactResult = get(incidentTypeRoadImpactInserted.getIncidentTypeId(),
						incidentTypeRoadImpactInserted.getStretchId(), incidentTypeRoadImpactInserted.getRoadImpactId(),
						false);
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
			throw new EntityPersistenceException(EntityPersistenceException.Operation.CREATE, incidentTypeRoadImpact);

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

		return incidentTypeRoadImpactResult;
	}

	@Override
	public ImsIncidentTypeRoadImpact clone(ImsIncidentTypeRoadImpact entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ImsIncidentTypeRoadImpact clone(ImsIncidentTypeRoadImpact entity, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ImsIncidentTypeRoadImpact delete(ImsIncidentTypeRoadImpact incidentTypeRoadImpact) {

		try {

			return delete(incidentTypeRoadImpact, null, null);

		} catch (EntityPersistenceException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			return null;
		}
	}

	@Override
	public ImsIncidentTypeRoadImpact delete(ImsIncidentTypeRoadImpact incidentTypeRoadImpact, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {

		Connection conn = null;
		boolean closeConnection = false;
		boolean commit = false;
		conn = connection;
		ImsIncidentTypeRoadImpact incidentTypeRoadImpactResult = null;
		ImsIncidentTypeRoadImpact incidentTypeRoadImpactAux = null;
		try {

			if (incidentTypeRoadImpact == null || !ObjectUtils.allNotNull(incidentTypeRoadImpact.getIncidentTypeId(),
					incidentTypeRoadImpact.getStretchId(), incidentTypeRoadImpact.getRoadImpactId())) {

				throw new NullPointerException("incidentTypeRoadImpact is null");
			}

			incidentTypeRoadImpactAux = get(incidentTypeRoadImpact.getIncidentTypeId(),
					incidentTypeRoadImpact.getStretchId(), incidentTypeRoadImpact.getRoadImpactId(), false);

			if (incidentTypeRoadImpactAux == null) {

				throw new NullPointerException("incidentTypeRoadImpact not found");
			}

			if (conn == null) {

				conn = DataSourceConnection.getInstance().getConnection();
				conn.setAutoCommit(false);
				closeConnection = true;
				commit = true;
				persistOperations = new ArrayList<>();
			}

			// Delete ImsIncidentTypeRoadImpact in BBDD
			ImsIncidentTypeRoadImpact incidentTypeRoadImpactDeleted = null;

			incidentTypeRoadImpactDeleted = (ImsIncidentTypeRoadImpact) getObjectDao().delete(conn,
					incidentTypeRoadImpactAux);

			if (incidentTypeRoadImpactDeleted == null) {

				throw new EntityDBException(EntityDBException.Operation.DELETE, incidentTypeRoadImpactAux);
			}

			persistOperations.add(new MutablePair<>(PersistOperation.DELETE, incidentTypeRoadImpactAux));

			if (commit) {

				conn.commit();

				// Update cache
				persistOperations(persistOperations);
			}

			incidentTypeRoadImpactResult = incidentTypeRoadImpactDeleted;

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
			throw new EntityPersistenceException(EntityPersistenceException.Operation.DELETE, incidentTypeRoadImpact);

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

		return incidentTypeRoadImpactResult;
	}

	@Override
	public ImsIncidentTypeRoadImpact put(ImsIncidentTypeRoadImpact entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ImsIncidentTypeRoadImpact> put(List<ImsIncidentTypeRoadImpact> entities) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ImsIncidentTypeRoadImpact del(ImsIncidentTypeRoadImpact entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ImsIncidentTypeRoadImpact> del(List<ImsIncidentTypeRoadImpact> entities) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ImsIncidentTypeRoadImpact update(ImsIncidentTypeRoadImpact entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ImsIncidentTypeRoadImpact update(ImsIncidentTypeRoadImpact entity, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {
		// TODO Auto-generated method stub
		return null;
	}
}