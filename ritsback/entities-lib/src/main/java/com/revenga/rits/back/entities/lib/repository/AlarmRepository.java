package com.revenga.rits.back.entities.lib.repository;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.dao.manager.DataSourceConnection;
import com.revenga.rits.back.data.core.model.Alarm;
import com.revenga.rits.back.data.core.model.BasicElement;
import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.entities.lib.exception.EntityDBException;
import com.revenga.rits.back.entities.lib.exception.EntityPersistenceException;
import com.revenga.rits.back.entities.lib.repository.entity.EntityCacheable;
import com.revenga.rits.back.entities.lib.repository.entity.EntityPersistent;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepositoryOptions;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class AlarmRepository extends EntityRepository implements EntityCacheable<Alarm>, EntityPersistent<Alarm> {

	private static final String HIST_BASE_QUERY = "SELECT created_at, id, alias, alarm_config_id, source_element, source_element_id, "
			+ "source_element_type_id, target_element, target_element_id, target_element_type_id, activation, activation_comments, "
			+ "recognized, recognized_user, recognized_user_id, recognized_comments, deactivation_user_id, deactivation, "
			+ "deactivation_comments, disagree "
			+ "FROM hist.alarms WHERE target_element_type_id = %elementTypeId AND target_element_id IN(%elementIds) ";

	private static final String AND = " AND ";
	private static final String GREATER_OR_EQUAL = " >= ";
	private static final String LESS_OR_EQUAL = " <= ";
	private static final String EQUALS = " = ";
	static final String MAX_ACTIVATION_AT = "(SELECT MAX(activation) FROM hist.alarms  "
			+ "WHERE target_element_type_id = %elementTypeId AND target_element_id IN( %elementIds ) AND activation < %initDate AND deactivation > %initDate AND deactivation <= %finalDate)";
	
	private static final String DEACTIVATION = "deactivation";
	private static final String ACTIVATION = "activation";
	private static final String SOURCE_ELEMENT_TYPE_ID = "source_element_type_id";
	private static final String SOURCE_ELEMENT_ID = "source_element_id";
	private static final String ALARM_ID = "id";
	private static final String ACTIVATION_COMMENTS = "activation_comments";
	private static final String DEACTIVATION_COMMENTS = "activation_comments";
	private static final String ALARM_CONFIG_ID = "alarm_config_id";
	private static final String ALIAS = "alias";
	private static final String RECOGNIZED = "recognized";
	private static final String RECOGNIZED_COMMENTS = "recognized_comments";
	private static final String RECOGNIZED_USER_ID = "recognized_user_id";
	private static final String TARGET_ELEMENT_TYPE_ID = "target_element_type_id";
	private static final String TARGET_ELEMENT_ID = "target_element_id";
	private static final String DISAGREE = "disagree";
	
	private static final String UNION = " UNION ";
	
	// region Constructors
	public AlarmRepository() {

		super(Alarm.class);
	}

	public AlarmRepository(Boolean cacheable) {

		super(Alarm.class, cacheable);
	}
	
	public AlarmRepository(EntityRepositoryOptions options) {

		super(Alarm.class, options);
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

	@Deprecated
	public List<Alarm> getAll(Long alarmType, Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			if (alarmType != null) {
				
				AlarmConfigRepository alarmConfigRepository = new AlarmConfigRepository(true);
				
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
	
	public List<Alarm> getHistoricalData(List<String> elementIds, Long initDate, Long finalDate) {

		List<Alarm> alarms = new ArrayList<>();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Connection conn = null;
		Statement st = null;

		try {
			
			conn = DataSourceConnection.getInstance().getConnection();
			
			st = conn.createStatement();
			
			Long elementTypeId = null;

			if (CollectionUtils.isNotEmpty(elementIds)) {

				StringBuilder dbQuery = new StringBuilder();
				StringBuilder targetElementIds = new StringBuilder();
				dbQuery.append(HIST_BASE_QUERY);

				for (int i = 0; i < elementIds.size(); i++) {

					if (elementIds.get(i) != null) {

						Key key = new Key(elementIds.get(i));

						if (key.getClazz() == Element.class) {

							Element element = new Element();
							
							element.setPks(key.getIds());
							
							if (i == 0) {
								
								elementTypeId = element.getElementTypeId();
								targetElementIds.append(element.getId());
							}
							
							if (i != 0 && elementIds.size() > 1) {
								
								targetElementIds.append(",");
								targetElementIds.append(element.getId());
							}
						}
					}
				}
				
				String firstQueryPart = dbQuery.toString();
				
				firstQueryPart = firstQueryPart.replace("%elementTypeId", String.valueOf(elementTypeId));
				
				firstQueryPart = firstQueryPart.replace("%elementIds", targetElementIds);
				
				StringBuilder finalFirstQueryPart = new StringBuilder();
				
				StringBuilder finalSecondQueryPart = new StringBuilder();
				
				finalFirstQueryPart.append(firstQueryPart);
				
				finalSecondQueryPart.append(firstQueryPart);
				
				String finalQueryPart ="";
				
				if (initDate != null) {
					
					Timestamp initTimeStamp = new Timestamp(initDate);
					finalFirstQueryPart.append(AND);
					finalFirstQueryPart.append(ACTIVATION);
					finalFirstQueryPart.append(GREATER_OR_EQUAL);
					finalFirstQueryPart.append("'"+dateFormat.format(initTimeStamp)+"'");
					
					//second part
					finalSecondQueryPart.append(AND);
					finalSecondQueryPart.append(ACTIVATION);
					finalSecondQueryPart.append(EQUALS);
					
					finalQueryPart = MAX_ACTIVATION_AT.replace("%elementTypeId", String.valueOf(elementTypeId));
					finalQueryPart = finalQueryPart.replace("%elementIds", targetElementIds);
					finalQueryPart = finalQueryPart.replace("%initDate", "'"+dateFormat.format(initTimeStamp)+"'");

				}
				
				Timestamp finalTimeStamp = null;
				
				if (finalDate != null) {

					finalTimeStamp = new Timestamp(finalDate);
					
					finalFirstQueryPart.append(AND);
					finalFirstQueryPart.append(ACTIVATION);
					finalFirstQueryPart.append(LESS_OR_EQUAL);
					finalFirstQueryPart.append("'"+dateFormat.format(finalTimeStamp)+"'");
				} else {
					
					finalTimeStamp = new Timestamp(Instant.now().toEpochMilli());
				}
				
				finalQueryPart = finalQueryPart.replace("%finalDate", "'"+dateFormat.format(finalTimeStamp)+"'");
				
				finalSecondQueryPart.append(finalQueryPart);
				
				finalFirstQueryPart.append(UNION);
				finalFirstQueryPart.append(finalSecondQueryPart);
				
				ResultSet rs = st.executeQuery(finalFirstQueryPart.toString());

				while (rs.next()) {
					
					Alarm alarm = new Alarm();
					
					alarm.setActivation(rs.getTimestamp(ACTIVATION) != null ? rs.getTimestamp(ACTIVATION).getTime() : null);
					alarm.setId(rs.getLong(ALARM_ID));
					alarm.setActivationComments(rs.getString(ACTIVATION_COMMENTS));
					alarm.setAlarmConfigId(rs.getLong(ALARM_CONFIG_ID));
					alarm.setAlias(rs.getString(ALIAS));
					alarm.setSourceElementId(rs.getLong(SOURCE_ELEMENT_ID));
					alarm.setSourceElementTypeId(rs.getLong(SOURCE_ELEMENT_TYPE_ID));
					alarm.setDeactivation(rs.getTimestamp(DEACTIVATION) != null ? rs.getTimestamp(DEACTIVATION).getTime() : null);
					alarm.setDeactivationComments(rs.getString(DEACTIVATION_COMMENTS));
					alarm.setRecognized(rs.getTimestamp(RECOGNIZED) != null ? rs.getTimestamp(RECOGNIZED).getTime() : null);
					alarm.setRecognizedComments(rs.getString(RECOGNIZED_COMMENTS));
					alarm.setRecognizedUserId(rs.getLong(RECOGNIZED_USER_ID));
					alarm.setTargetElementId(rs.getLong(TARGET_ELEMENT_ID));
					alarm.setTargetElementTypeId(rs.getLong(TARGET_ELEMENT_TYPE_ID));
					alarm.setDisagree(rs.getBoolean(DISAGREE));
					
					alarms.add(alarm);
				}
			}

		} catch (SQLException | IOException | IllegalArgumentException | SecurityException e) {
			
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			
			return new ArrayList<>();
			
		} finally {
			
			try {
				
				if (conn != null && st != null) {
					
					conn.close();
					st.close();
				}
				
			} catch (SQLException e) {
				
				log.error(e.getMessage());
				log.debug(ExceptionUtils.getStackTrace(e));
			}
		}
		
		return alarms;
	}

}
