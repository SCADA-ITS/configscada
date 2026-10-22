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
import com.revenga.rits.back.data.core.model.AlarmTargetElement;
import com.revenga.rits.back.data.core.model.BasicElement;
import com.revenga.rits.back.entities.lib.exception.EntityDBException;
import com.revenga.rits.back.entities.lib.exception.EntityPersistenceException;
import com.revenga.rits.back.entities.lib.repository.entity.EntityCacheable;
import com.revenga.rits.back.entities.lib.repository.entity.EntityPersistent;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class AlarmTargetElementRepository extends EntityRepository
		implements EntityCacheable<AlarmTargetElement>, EntityPersistent<AlarmTargetElement> {

	// region Constructors
	public AlarmTargetElementRepository() {

		super(AlarmTargetElement.class);
	}

	public AlarmTargetElementRepository(Boolean cacheable) {

		super(AlarmTargetElement.class, cacheable);
	}

	public AlarmTargetElementRepository(Boolean cacheable, Long cacheRetentionTime) {

		super(AlarmTargetElement.class, cacheable, cacheRetentionTime);
	}
	// endregion Constructors

	// region getters
	public AlarmTargetElement get(Long alarmConfigId, Long sourceElementTypeId, Long sourceElementId,
			Long targetElementTypeId, Long targetElementId, Boolean fillObject) {

		return (AlarmTargetElement) getFromCache(new String[] { String.valueOf(alarmConfigId),
				String.valueOf(sourceElementTypeId), String.valueOf(sourceElementId),
				String.valueOf(targetElementTypeId), String.valueOf(targetElementId) }, fillObject);
	}

	public List<AlarmTargetElement> getsByAlarmConfig(Long alarmConfigId, Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(
				new String[] { String.valueOf(alarmConfigId), null, null, null, null }, fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(be -> (AlarmTargetElement) be).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}

	@Override
	public List<AlarmTargetElement> getAll(Boolean fillObject) {
		List<BasicElement> basicElements = getsFromCache(fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(be -> (AlarmTargetElement) be).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}

	@Override
	public AlarmTargetElement put(AlarmTargetElement entity) {
		return (AlarmTargetElement) putFromCache(entity);
	}

	@Override
	public List<AlarmTargetElement> put(List<AlarmTargetElement> entities) {
		List<BasicElement> basicElements = entities.stream().map(element -> (BasicElement) element)
				.collect(Collectors.toList());

		basicElements = putsFromCache(basicElements);

		return basicElements.stream().map(be -> (AlarmTargetElement) be).collect(Collectors.toList());
	}

	@Override
	public AlarmTargetElement del(AlarmTargetElement entity) {
		return (AlarmTargetElement) delFromCache(entity);
	}

	@Override
	public List<AlarmTargetElement> del(List<AlarmTargetElement> entities) {
		List<BasicElement> basicElements = entities.stream().map(element -> (BasicElement) element)
				.collect(Collectors.toList());

		basicElements = delFromCache(basicElements);

		return basicElements.stream().map(be -> (AlarmTargetElement) be).collect(Collectors.toList());
	}

	@Override
	public AlarmTargetElement create(AlarmTargetElement alarmTargetElement) {
		try {

			return create(alarmTargetElement, null, null);

		} catch (EntityPersistenceException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			return null;
		}
	}

	@Override
	public AlarmTargetElement create(AlarmTargetElement alarmTargetElement, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {
		Connection conn = null;
		boolean closeConnection = false;
		boolean commit = false;
		conn = connection;
		AlarmTargetElement alarmTargetElementResult = null;

		try {

			if (conn == null) {

				conn = DataSourceConnection.getInstance().getConnection();
				conn.setAutoCommit(false);
				closeConnection = true;
				commit = true;
				persistOperations = new ArrayList<>();
			}

			// Insert AlarmConfig in BBDD
			AlarmTargetElement alarmTargetElementInserted = null;

			alarmTargetElementInserted = (AlarmTargetElement) getObjectDao().insert(conn, alarmTargetElement);

			if (alarmTargetElementInserted == null) {

				throw new EntityDBException(EntityDBException.Operation.INSERT, alarmTargetElement);
			}

			persistOperations.add(new MutablePair<>(PersistOperation.PUT, alarmTargetElementInserted));

			if (commit) {
				conn.commit();

				persistOperations(persistOperations);
				
				alarmTargetElementResult = get(alarmTargetElementInserted.getAlarmConfigId(),
						alarmTargetElementInserted.getSourceElementTypeId(),
						alarmTargetElementInserted.getSourceElementId(),
						alarmTargetElementInserted.getTargetElementTypeId(),
						alarmTargetElementInserted.getTargetElementId(), false);
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
			throw new EntityPersistenceException(EntityPersistenceException.Operation.CREATE, alarmTargetElement);

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

		return alarmTargetElementResult;
	}

	@Override
	public AlarmTargetElement clone(AlarmTargetElement entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AlarmTargetElement clone(AlarmTargetElement entity, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AlarmTargetElement update(AlarmTargetElement entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AlarmTargetElement update(AlarmTargetElement entity, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AlarmTargetElement delete(AlarmTargetElement alarmTargetElement) {
		try {

			return delete(alarmTargetElement, null, null);

		} catch (EntityPersistenceException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			return null;
		}
	}

	@Override
	public AlarmTargetElement delete(AlarmTargetElement alarmTargetElement, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {

		Connection conn = null;
		boolean closeConnection = false;
		boolean commit = false;
		conn = connection;
		AlarmTargetElement alarmTargetElementResult = null;
		AlarmTargetElement alarmTargetElementAux = null;
		try {

			if (alarmTargetElement == null || !ObjectUtils.allNotNull(alarmTargetElement.getAlarmConfigId(),
					alarmTargetElement.getSourceElementTypeId(), alarmTargetElement.getSourceElementId(),
					alarmTargetElement.getTargetElementTypeId(), alarmTargetElement.getTargetElementId())) {

				throw new NullPointerException("alarmTargetElement is null");
			}

			alarmTargetElementAux = get(alarmTargetElement.getAlarmConfigId(),
					alarmTargetElement.getSourceElementTypeId(), alarmTargetElement.getSourceElementId(),
					alarmTargetElement.getTargetElementTypeId(), alarmTargetElement.getTargetElementId(), false);

			if (alarmTargetElementAux == null) {

				throw new NullPointerException("alarmTargetElement not found");
			}

			if (conn == null) {

				conn = DataSourceConnection.getInstance().getConnection();
				conn.setAutoCommit(false);
				closeConnection = true;
				commit = true;
				persistOperations = new ArrayList<>();
			}

			// Delete alarmConfigMeasure in BBDD
			AlarmTargetElement alarmConfigDeleted = null;

			alarmConfigDeleted = (AlarmTargetElement) getObjectDao().delete(conn, alarmTargetElementAux);

			if (alarmConfigDeleted == null) {

				throw new EntityDBException(EntityDBException.Operation.DELETE, alarmTargetElementAux);
			}

			persistOperations.add(new MutablePair<>(PersistOperation.DELETE, alarmTargetElementAux));

			if (commit) {

				conn.commit();

				// Update cache
				persistOperations(persistOperations);
			}

			alarmTargetElementResult = alarmConfigDeleted;

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
			throw new EntityPersistenceException(EntityPersistenceException.Operation.DELETE, alarmTargetElement);

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

		return alarmTargetElementResult;
	}

}