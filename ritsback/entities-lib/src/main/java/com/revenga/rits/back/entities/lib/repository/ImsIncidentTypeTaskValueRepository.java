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
import com.revenga.rits.back.data.core.model.ImsIncidentTypeTaskValue;
import com.revenga.rits.back.entities.lib.exception.EntityDBException;
import com.revenga.rits.back.entities.lib.exception.EntityPersistenceException;
import com.revenga.rits.back.entities.lib.repository.entity.EntityCacheable;
import com.revenga.rits.back.entities.lib.repository.entity.EntityPersistent;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository.PersistOperation;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class ImsIncidentTypeTaskValueRepository extends EntityRepository
		implements EntityCacheable<ImsIncidentTypeTaskValue>, EntityPersistent<ImsIncidentTypeTaskValue> {

	// region Constructors
	public ImsIncidentTypeTaskValueRepository() {

		super(ImsIncidentTypeTaskValue.class);
	}

	public ImsIncidentTypeTaskValueRepository(Boolean cacheable) {

		super(ImsIncidentTypeTaskValue.class, cacheable);
	}

	public ImsIncidentTypeTaskValueRepository(Boolean cacheable, Long cacheRetentionTime) {

		super(ImsIncidentTypeTaskValue.class, cacheable, cacheRetentionTime);
	}

	// endregion Constructors

	// region getters
	public ImsIncidentTypeTaskValue get(final Long taskTypeId, final Long taskTypeParamId,
			final Long incidentTypeTaskId, Boolean fillObject) {

		return (ImsIncidentTypeTaskValue) getFromCache(new String[] { String.valueOf(taskTypeId),
				String.valueOf(taskTypeParamId), String.valueOf(incidentTypeTaskId) }, fillObject);
	}

	@Override
	public List<ImsIncidentTypeTaskValue> getAll(Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(be -> (ImsIncidentTypeTaskValue) be).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}

	public List<ImsIncidentTypeTaskValue> getsByIncidentTypeTask(Long incidentTypeTaskId, Boolean fillObject) {

		if (incidentTypeTaskId != null) {
			
			List<BasicElement> basicElements = getsFromCache(
					new String[] { null, null, String.valueOf(incidentTypeTaskId) }, fillObject);

			if (!CollectionUtils.isEmpty(basicElements)) {

				return basicElements.stream().map(be -> (ImsIncidentTypeTaskValue) be).collect(Collectors.toList());
			}
		}

		return Collections.emptyList();
	}
	// endregion getters

	// region puts
	@Override
	public ImsIncidentTypeTaskValue put(ImsIncidentTypeTaskValue entity) {

		return (ImsIncidentTypeTaskValue) putFromCache(entity);
	}

	@Override
	public List<ImsIncidentTypeTaskValue> put(List<ImsIncidentTypeTaskValue> entities) {

		List<BasicElement> basicElements = entities.stream().map(alarmConfig -> (BasicElement) alarmConfig)
				.collect(Collectors.toList());

		basicElements = putsFromCache(basicElements);

		return basicElements.stream().map(be -> (ImsIncidentTypeTaskValue) be).collect(Collectors.toList());
	}
	// endregion puts

	// region dels
	@Override
	public ImsIncidentTypeTaskValue del(ImsIncidentTypeTaskValue entity) {

		return (ImsIncidentTypeTaskValue) delFromCache(entity);

	}

	@Override
	public List<ImsIncidentTypeTaskValue> del(List<ImsIncidentTypeTaskValue> entities) {

		List<BasicElement> basicElements = entities.stream().map(alarmConfig -> (BasicElement) alarmConfig)
				.collect(Collectors.toList());

		basicElements = delFromCache(basicElements);

		return basicElements.stream().map(be -> (ImsIncidentTypeTaskValue) be).collect(Collectors.toList());
	}
	// endregion dels

	@Override
	public ImsIncidentTypeTaskValue delete(ImsIncidentTypeTaskValue imsIncidentTypeTaskValue) {

		try {

			return delete(imsIncidentTypeTaskValue, null, null);

		} catch (EntityPersistenceException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			return null;
		}
	}

	@Override
	public ImsIncidentTypeTaskValue delete(ImsIncidentTypeTaskValue imsIncidentTypeTaskValue, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {

		Connection conn = null;
		boolean closeConnection = false;
		boolean commit = false;
		conn = connection;
		ImsIncidentTypeTaskValue imsIncidentTypeTaskValueResult = null;
		ImsIncidentTypeTaskValue imsIncidentTypeTaskValueAux = null;

		try {

			if (imsIncidentTypeTaskValue == null || imsIncidentTypeTaskValue.getTaskTypeId() == null
					|| imsIncidentTypeTaskValue.getTaskTypeParamId() == null
					|| imsIncidentTypeTaskValue.getIncidentTypeTaskId() == null) {

				throw new NullPointerException("imsIncidentTypeTask is null");
			}

			imsIncidentTypeTaskValueAux = get(imsIncidentTypeTaskValue.getTaskTypeId(),
					imsIncidentTypeTaskValue.getTaskTypeParamId(), imsIncidentTypeTaskValue.getIncidentTypeTaskId(),
					false);

			if (imsIncidentTypeTaskValueAux == null) {

				throw new NullPointerException("imsIncidentTypeTaskValue not found");
			}

			if (conn == null) {

				conn = DataSourceConnection.getInstance().getConnection();
				conn.setAutoCommit(false);
				closeConnection = true;
				commit = true;
				persistOperations = new ArrayList<>();
			}

			// Delete ImsIncidentTypeTaskValue in BBDD
			ImsIncidentTypeTaskValue imsIncidentTypeTaskValueDeleted = null;

			imsIncidentTypeTaskValueDeleted = (ImsIncidentTypeTaskValue) getObjectDao().delete(conn,
					imsIncidentTypeTaskValueAux);

			if (imsIncidentTypeTaskValueDeleted == null) {

				throw new EntityDBException(EntityDBException.Operation.DELETE, imsIncidentTypeTaskValueAux);
			}

			persistOperations.add(new MutablePair<>(PersistOperation.DELETE, imsIncidentTypeTaskValueAux));

			if (commit) {

				conn.commit();

				// Update cache
				persistOperations(persistOperations);
			}

			imsIncidentTypeTaskValueResult = imsIncidentTypeTaskValueDeleted;

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
			throw new EntityPersistenceException(EntityPersistenceException.Operation.DELETE, imsIncidentTypeTaskValue);

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

		return imsIncidentTypeTaskValueResult;
	}

	@Override
	public ImsIncidentTypeTaskValue create(ImsIncidentTypeTaskValue entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ImsIncidentTypeTaskValue create(ImsIncidentTypeTaskValue entity, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ImsIncidentTypeTaskValue clone(ImsIncidentTypeTaskValue entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ImsIncidentTypeTaskValue clone(ImsIncidentTypeTaskValue entity, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ImsIncidentTypeTaskValue update(ImsIncidentTypeTaskValue entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ImsIncidentTypeTaskValue update(ImsIncidentTypeTaskValue entity, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {
		// TODO Auto-generated method stub
		return null;
	}
}
