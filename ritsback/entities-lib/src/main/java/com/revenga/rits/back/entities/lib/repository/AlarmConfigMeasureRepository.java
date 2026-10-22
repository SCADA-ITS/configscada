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
import com.revenga.rits.back.data.core.model.AlarmConfig;
import com.revenga.rits.back.data.core.model.AlarmConfigMeasure;
import com.revenga.rits.back.data.core.model.BasicElement;
import com.revenga.rits.back.entities.lib.exception.EntityDBException;
import com.revenga.rits.back.entities.lib.exception.EntityPersistenceException;
import com.revenga.rits.back.entities.lib.repository.entity.EntityCacheable;
import com.revenga.rits.back.entities.lib.repository.entity.EntityPersistent;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepositoryOptions;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class AlarmConfigMeasureRepository extends EntityRepository
		implements EntityCacheable<AlarmConfigMeasure>, EntityPersistent<AlarmConfigMeasure> {

	// region Constructors
	public AlarmConfigMeasureRepository() {

		super(AlarmConfigMeasure.class);
	}
	
	public AlarmConfigMeasureRepository(EntityRepositoryOptions options) {

		super(AlarmConfigMeasure.class, options);
	}

	public AlarmConfigMeasureRepository(Boolean cacheable) {

		super(AlarmConfigMeasure.class, cacheable);
	}

	public AlarmConfigMeasureRepository(Boolean cacheable, Long cacheRetentionTime) {

		super(AlarmConfigMeasure.class, cacheable, cacheRetentionTime);
	}
	// endregion Constructors

	// region getters
	public AlarmConfigMeasure get(Long alarmConfigId, Long elementTypeId, Long paramTypeId, Long elementTypeParamId,
			Boolean fillObject) {

		return (AlarmConfigMeasure) getFromCache(new String[] { String.valueOf(alarmConfigId),
				String.valueOf(elementTypeId), String.valueOf(paramTypeId), String.valueOf(elementTypeParamId) },
				fillObject);
	}

	public List<AlarmConfigMeasure> getsByAlarmConfig(Long alarmConfigId, Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(
				new String[] { String.valueOf(alarmConfigId), null, null, null }, fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(be -> (AlarmConfigMeasure) be).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}

	@Override
	public List<AlarmConfigMeasure> getAll(Boolean fillObject) {
		List<BasicElement> basicElements = getsFromCache(fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(be -> (AlarmConfigMeasure) be).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}

	@Override
	public AlarmConfigMeasure put(AlarmConfigMeasure entity) {
		return (AlarmConfigMeasure) putFromCache(entity);
	}

	@Override
	public List<AlarmConfigMeasure> put(List<AlarmConfigMeasure> entities) {
		List<BasicElement> basicElements = entities.stream().map(element -> (BasicElement) element)
				.collect(Collectors.toList());

		basicElements = putsFromCache(basicElements);

		return basicElements.stream().map(be -> (AlarmConfigMeasure) be).collect(Collectors.toList());
	}

	@Override
	public AlarmConfigMeasure del(AlarmConfigMeasure entity) {
		return (AlarmConfigMeasure) delFromCache(entity);
	}

	@Override
	public List<AlarmConfigMeasure> del(List<AlarmConfigMeasure> entities) {
		List<BasicElement> basicElements = entities.stream().map(element -> (BasicElement) element)
				.collect(Collectors.toList());

		basicElements = delFromCache(basicElements);

		return basicElements.stream().map(be -> (AlarmConfigMeasure) be).collect(Collectors.toList());
	}

	@Override
	public AlarmConfigMeasure create(AlarmConfigMeasure alarmConfigMeasure) {

		try {

			return create(alarmConfigMeasure, null, null);

		} catch (EntityPersistenceException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			return null;
		}
	}

	@Override
	public AlarmConfigMeasure create(AlarmConfigMeasure alarmConfigMeasure, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {
		Connection conn = null;
		boolean closeConnection = false;
		boolean commit = false;
		conn = connection;
		AlarmConfigMeasure alarmConfigMeasureResult = null;

		try {

			if (conn == null) {

				conn = DataSourceConnection.getInstance().getConnection();
				conn.setAutoCommit(false);
				closeConnection = true;
				commit = true;
				persistOperations = new ArrayList<>();
			}

			// Insert AlarmConfig in BBDD
			AlarmConfigMeasure alarmConfigMeasureInserted = null;

			alarmConfigMeasureInserted = (AlarmConfigMeasure) getObjectDao().insert(conn, alarmConfigMeasure);

			if (alarmConfigMeasureInserted == null) {

				throw new EntityDBException(EntityDBException.Operation.INSERT, alarmConfigMeasure);
			}

			persistOperations.add(new MutablePair<>(PersistOperation.PUT, alarmConfigMeasureInserted));

			if (commit) {
				conn.commit();

				persistOperations(persistOperations);
				
				alarmConfigMeasureResult = get(alarmConfigMeasureInserted.getAlarmConfigId(),
						alarmConfigMeasureInserted.getElementTypeId(), alarmConfigMeasureInserted.getParamTypeId(),
						alarmConfigMeasureInserted.getElementTypeParamId(), false);
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
			throw new EntityPersistenceException(EntityPersistenceException.Operation.CREATE, alarmConfigMeasure);

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

		return alarmConfigMeasureResult;
	}

	@Override
	public AlarmConfigMeasure clone(AlarmConfigMeasure entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AlarmConfigMeasure clone(AlarmConfigMeasure entity, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AlarmConfigMeasure update(AlarmConfigMeasure entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AlarmConfigMeasure update(AlarmConfigMeasure entity, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AlarmConfigMeasure delete(AlarmConfigMeasure alarmConfigMeasure) {

		try {

			return delete(alarmConfigMeasure, null, null);

		} catch (EntityPersistenceException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			return null;
		}
	}

	@Override
	public AlarmConfigMeasure delete(AlarmConfigMeasure alarmConfigMeasure, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {

		Connection conn = null;
		boolean closeConnection = false;
		boolean commit = false;
		conn = connection;
		AlarmConfigMeasure alarmConfigMeasureResult = null;
		AlarmConfigMeasure alarmConfigMeasureAux = null;
		try {

			if (alarmConfigMeasure == null || !ObjectUtils.allNotNull(alarmConfigMeasure.getAlarmConfigId(), alarmConfigMeasure.getElementTypeId(),
					alarmConfigMeasure.getParamTypeId(), alarmConfigMeasure.getElementTypeParamId())) {

				throw new NullPointerException("alarmConfigMeasure is null");
			}

			alarmConfigMeasureAux = get(alarmConfigMeasure.getAlarmConfigId(), alarmConfigMeasure.getElementTypeId(),
					alarmConfigMeasure.getParamTypeId(), alarmConfigMeasure.getElementTypeParamId(), false);

			if (alarmConfigMeasureAux == null) {

				throw new NullPointerException("alarmConfigMeasure not found");
			}

			if (conn == null) {

				conn = DataSourceConnection.getInstance().getConnection();
				conn.setAutoCommit(false);
				closeConnection = true;
				commit = true;
				persistOperations = new ArrayList<>();
			}

			// Delete alarmConfigMeasure in BBDD
			AlarmConfigMeasure alarmConfigDeleted = null;

			alarmConfigDeleted = (AlarmConfigMeasure) getObjectDao().delete(conn, alarmConfigMeasureAux);

			if (alarmConfigDeleted == null) {

				throw new EntityDBException(EntityDBException.Operation.DELETE, alarmConfigMeasureAux);
			}

			persistOperations.add(new MutablePair<>(PersistOperation.DELETE, alarmConfigMeasureAux));

			if (commit) {

				conn.commit();

				// Update cache
				persistOperations(persistOperations);
			}

			alarmConfigMeasureResult = alarmConfigDeleted;

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
			throw new EntityPersistenceException(EntityPersistenceException.Operation.DELETE, alarmConfigMeasure);

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

		return alarmConfigMeasureResult;
	}

}
