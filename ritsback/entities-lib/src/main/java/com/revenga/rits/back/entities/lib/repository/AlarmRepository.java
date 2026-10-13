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
import com.revenga.rits.back.data.core.model.Alarm;
import com.revenga.rits.back.data.core.model.BasicElement;
import com.revenga.rits.back.entities.lib.exception.EntityDBException;
import com.revenga.rits.back.entities.lib.exception.EntityPersistenceException;
import com.revenga.rits.back.entities.lib.repository.entity.EntityCacheable;
import com.revenga.rits.back.entities.lib.repository.entity.EntityPersistent;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class AlarmRepository extends EntityRepository implements EntityCacheable<Alarm>, EntityPersistent<Alarm> {

	// region Constructors
	public AlarmRepository() {

		super(Alarm.class);
	}

	public AlarmRepository(Boolean cacheable) {

		super(Alarm.class, cacheable);
	}

	public AlarmRepository(Boolean cacheable, Long cacheRetentionTime) {

		super(Alarm.class, cacheable, cacheRetentionTime);
	}
	// endregion Constructors

	// region getters
	public Alarm get(Long id, Boolean fillObject) {

		return (Alarm) getFromCache(new String[] { String.valueOf(id) }, fillObject);
	}

	public List<Alarm> getsByTargetElement(Long targetElementTypeId, Long targetElementId, Boolean fillObject) {

		return getsByTargetElement(targetElementTypeId, targetElementId, null, fillObject);
	}

	public List<Alarm> getsByTargetElement(Long targetElementTypeId, Long targetElementId, Long alarmType,
			Boolean fillObject) {

		AlarmConfigRepository alarmConfigRepository = new AlarmConfigRepository(true);

		List<BasicElement> basicElements = getsFromCache(new String[] { "targetElementTypeId", "targetElementId" },
				new String[] { String.valueOf(targetElementTypeId), String.valueOf(targetElementId) }, fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			if (alarmType != null) {

				return basicElements.stream().map(Alarm.class::cast)
						.filter(be -> alarmType
								.equals(alarmConfigRepository.get(be.getAlarmConfigId(), false).getAlarmTypeId()))
						.collect(Collectors.toList());

			} else {

				return basicElements.stream().map(Alarm.class::cast).collect(Collectors.toList());
			}

		}
		return Collections.emptyList();
	}

	public List<Alarm> getsByElementType(Long targetElementTypeId, Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(new String[] { "targetElementTypeId" },
				new String[] { String.valueOf(targetElementTypeId) }, fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(Alarm.class::cast).collect(Collectors.toList());

		}
		return Collections.emptyList();
	}

	public List<Alarm> getsBySourceElement(Long sourceElementTypeId, Long sourceElementId, Boolean fillObject) {

		return getsBySourceElement(sourceElementTypeId, sourceElementId, null, fillObject);
	}

	public List<Alarm> getsBySourceElement(Long sourceElementTypeId, Long sourceElementId, Long alarmType,
			Boolean fillObject) {

		AlarmConfigRepository alarmConfigRepository = new AlarmConfigRepository(true);

		List<BasicElement> basicElements = getsFromCache(new String[] { "sourceElementTypeId", "sourceElementId" },
				new String[] { String.valueOf(sourceElementTypeId), String.valueOf(sourceElementId) }, fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			if (alarmType != null) {

				return basicElements.stream().map(Alarm.class::cast)
						.filter(be -> alarmType
								.equals(alarmConfigRepository.get(be.getAlarmConfigId(), false).getAlarmTypeId()))
						.collect(Collectors.toList());

			} else {

				return basicElements.stream().map(Alarm.class::cast).collect(Collectors.toList());
			}

		}

		return Collections.emptyList();
	}

	public List<Alarm> getsByAlarmConfig(Long alarmConfigId, Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			List<Alarm> alarms = basicElements.stream().map(Alarm.class::cast).collect(Collectors.toList());

			return alarms.stream().filter(e -> e.getAlarmConfigId().equals(alarmConfigId)).collect(Collectors.toList());
		}

		return Collections.emptyList();

	}

	@Override
	public List<Alarm> getAll(Boolean fillObject) {

		return getAll(null, fillObject);
	}

	public List<Alarm> getAll(Long alarmType, Boolean fillObject) {

		AlarmConfigRepository alarmConfigRepository = new AlarmConfigRepository(true);

		List<BasicElement> basicElements = getsFromCache(fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			if (alarmType != null) {

				return basicElements.stream().map(Alarm.class::cast)
						.filter(be -> alarmType
								.equals(alarmConfigRepository.get(be.getAlarmConfigId(), false).getAlarmTypeId()))
						.collect(Collectors.toList());

			} else {

				return basicElements.stream().map(Alarm.class::cast).collect(Collectors.toList());
			}
		}

		return Collections.emptyList();
	}

	// endregion getters

	// region puts
	@Override
	public Alarm put(Alarm entity) {

		return (Alarm) putFromCache(entity);
	}

	@Override
	public List<Alarm> put(List<Alarm> entities) {

		List<BasicElement> basicElements = entities.stream().map(BasicElement.class::cast)
				.collect(Collectors.toList());

		basicElements = putsFromCache(basicElements);

		return basicElements.stream().map(Alarm.class::cast).collect(Collectors.toList());
	}
	// endregion puts

	// region dels
	@Override
	public Alarm del(Alarm entity) {

		return (Alarm) delFromCache(entity);

	}

	@Override
	public List<Alarm> del(List<Alarm> entities) {

		List<BasicElement> basicElements = entities.stream().map(BasicElement.class::cast)
				.collect(Collectors.toList());

		basicElements = delFromCache(basicElements);

		return basicElements.stream().map(Alarm.class::cast).collect(Collectors.toList());
	}

	public void delAll() {

		delAllFromCache();
	}

	// endregion dels

	@Override
	public Alarm create(Alarm alarm) {

		try {

			return create(alarm, null, null);

		} catch (EntityPersistenceException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			return null;
		}
	}

	@Override
	public Alarm create(Alarm alarm, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {

		Connection conn = null;
		boolean closeConnection = false;
		boolean commit = false;
		conn = connection;
		Alarm alarmResult = null;

		try {

			if (conn == null) {

				conn = DataSourceConnection.getInstance().getConnection();
				conn.setAutoCommit(false);
				closeConnection = true;
				commit = true;
				persistOperations = new ArrayList<>();
			}

			// Insert Alarm in BBDD
			Alarm alarmInserted = null;

			alarmInserted = (Alarm) getObjectDao().insert(conn, alarm);

			if (alarmInserted == null) {

				throw new EntityDBException(EntityDBException.Operation.INSERT, alarm);
			}

			persistOperations.add(new MutablePair<>(PersistOperation.PUT, alarmInserted));

			if (commit) {
				conn.commit();

				persistOperations(persistOperations);
				
				alarmResult = get(alarmInserted.getId(), false);
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
			throw new EntityPersistenceException(EntityPersistenceException.Operation.CREATE, alarm);

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

		return alarmResult;
	}

	@Override
	public Alarm clone(Alarm entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Alarm clone(Alarm entity, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Alarm update(Alarm alarm) {

		try {

			return update(alarm, null, null);

		} catch (EntityPersistenceException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			return null;
		}
	}

	@Override
	public Alarm update(Alarm alarm, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {

		Connection conn = null;
		boolean closeConnection = false;
		boolean commit = false;
		conn = connection;
		Alarm alarmResult = null;
		Alarm alarmAux = null;

		try {

			if (alarm == null || alarm.getId() == null) {

				throw new NullPointerException("alarm is null");
			}

			alarmAux = get(alarm.getId(), false);

			if (alarmAux == null) {

				throw new NullPointerException("alarm not found");
			}

			if (alarm.getEnabled() != null)
				alarmAux.setEnabled(alarm.getEnabled());
			if (alarm.getVisible() != null)
				alarmAux.setVisible(alarm.getVisible());
			if (alarm.getAlias() != null)
				alarmAux.setAlias(alarm.getAlias());
			if (alarm.getDescription() != null)
				alarmAux.setDescription(alarm.getDescription());
			
			if(alarm.getCreationUserId() != null)
				alarmAux.setCreationUserId(alarm.getCreationUserId());
			if(alarm.getRecognizedUserId() != null)
				alarmAux.setRecognizedUserId(alarm.getRecognizedUserId());
			if(alarm.getDeactivationUserId() != null)
				alarmAux.setDeactivationUserId(alarm.getDeactivationUserId());
								
			if (alarm.getActivation() != null)
				alarmAux.setActivation(alarm.getActivation());
			if (alarm.getActivationComments() != null)
				alarmAux.setActivationComments(alarm.getActivationComments());
			if (alarm.getAlarmConfigId() != null)
				alarmAux.setAlarmConfigId(alarm.getAlarmConfigId());
			if (alarm.getDeactivation() != null)
				alarmAux.setDeactivation(alarm.getDeactivation());
			if (alarm.getDeactivationComments() != null)
				alarmAux.setDeactivationComments(alarm.getDeactivationComments());
			if (alarm.getFilteredUntil() != null)
				alarmAux.setFilteredUntil(alarm.getFilteredUntil());
			if (alarm.getRecognized() != null)
				alarmAux.setRecognized(alarm.getRecognized());
			if (alarm.getRecognizedComments() != null)
				alarmAux.setRecognizedComments(alarm.getRecognizedComments());
			if (alarm.getSourceElementTypeId() != null)
				alarmAux.setSourceElementTypeId(alarm.getSourceElementTypeId());
			if (alarm.getSourceElementId() != null)
				alarmAux.setSourceElementId(alarm.getSourceElementId());
			if (alarm.getTargetElementTypeId() != null)
				alarmAux.setTargetElementTypeId(alarm.getTargetElementTypeId());
			if (alarm.getTargetElementId() != null)
				alarmAux.setTargetElementId(alarm.getTargetElementId());

			if (conn == null) {

				conn = DataSourceConnection.getInstance().getConnection();
				conn.setAutoCommit(false);
				closeConnection = true;
				commit = true;
				persistOperations = new ArrayList<>();
			}

			// Update Alarm in BBDD
			Alarm alarmConfigUpdated = null;

			alarmConfigUpdated = (Alarm) getObjectDao().update(conn, alarmAux);
			if (alarmConfigUpdated == null) {

				throw new EntityDBException(EntityDBException.Operation.UPDATE, alarmAux);
			}

			persistOperations.add(new MutablePair<>(PersistOperation.PUT, alarmConfigUpdated));

			if (commit) {

				conn.commit();

				persistOperations(persistOperations);
			}

			alarmResult = alarmConfigUpdated;

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
			throw new EntityPersistenceException(EntityPersistenceException.Operation.UPDATE, alarm);

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

		return alarmResult;
	}

	@Override
	public Alarm delete(Alarm alarm) {

		try {

			return delete(alarm, null, null);

		} catch (EntityPersistenceException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			return null;
		}
	}

	@Override
	public Alarm delete(Alarm alarm, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {

		Connection conn = null;
		boolean closeConnection = false;
		boolean commit = false;
		conn = connection;
		Alarm alarmResult = null;
		Alarm alarmAux = null;

		try {

			if (alarm == null || alarm.getId() == null) {

				throw new NullPointerException("alarm is null");
			}

			alarmAux = get(alarm.getId(), false);

			if (alarmAux == null) {

				throw new NullPointerException("alarm not found");
			}

			if (conn == null) {

				conn = DataSourceConnection.getInstance().getConnection();
				conn.setAutoCommit(false);
				closeConnection = true;
				commit = true;
				persistOperations = new ArrayList<>();
			}

			// Delete Alarm in BBDD
			Alarm alarmDeleted = null;

			alarmDeleted = (Alarm) getObjectDao().delete(conn, alarmAux);

			if (alarmDeleted == null) {

				throw new EntityDBException(EntityDBException.Operation.DELETE, alarmAux);
			}

			persistOperations.add(new MutablePair<>(PersistOperation.DELETE, alarmAux));

			if (commit) {

				conn.commit();

				// Update cache
				persistOperations(persistOperations);
			}

			alarmResult = alarmDeleted;

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
			throw new EntityPersistenceException(EntityPersistenceException.Operation.DELETE, alarm);

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

		return alarmResult;
	}

}
