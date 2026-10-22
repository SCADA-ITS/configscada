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
import com.revenga.rits.back.data.core.model.ElementStateAlarm;
import com.revenga.rits.back.entities.lib.exception.EntityDBException;
import com.revenga.rits.back.entities.lib.exception.EntityPersistenceException;
import com.revenga.rits.back.entities.lib.repository.entity.EntityCacheable;
import com.revenga.rits.back.entities.lib.repository.entity.EntityPersistent;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepositoryOptions;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class ElementStateAlarmRepository extends EntityRepository implements EntityCacheable<ElementStateAlarm>, EntityPersistent<ElementStateAlarm> {

	// region Constructors
	public ElementStateAlarmRepository() {
		
		super(ElementStateAlarm.class);
	}
	
	public ElementStateAlarmRepository(Boolean cacheable) {
		
		super(ElementStateAlarm.class, cacheable);
	}
	
	public ElementStateAlarmRepository(EntityRepositoryOptions options) {

		super(ElementStateAlarm.class, options);
	}
	
	public ElementStateAlarmRepository(Boolean cacheable, Long cacheRetentionTime) {
		
		super(ElementStateAlarm.class, cacheable, cacheRetentionTime);
	}
	// endregion Constructors

	// region getters
	public ElementStateAlarm get(Long elementTypeId, Long elementTypeStateId, Boolean fillObject) {
		
		return (ElementStateAlarm) getFromCache(new String[] { String.valueOf(elementTypeId),
				String.valueOf(elementTypeStateId) }, fillObject);
	}
	
	public List<ElementStateAlarm> getsByElementType(Long elementTypeId, Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(fillObject);

	    if (!CollectionUtils.isEmpty(basicElements)) {

	      List<ElementStateAlarm> elementStateAlarms = basicElements.stream().map(be -> (ElementStateAlarm) be)
	          .collect(Collectors.toList());

	      return elementStateAlarms.stream().filter(e -> e.getElementTypeId().equals(elementTypeId)).collect(Collectors.toList());
	    }

	    return Collections.emptyList();
	}
	
	public List<ElementStateAlarm> getsByAlarmConfig(Long alarmConfigId, Boolean fillObject) {
		
	//	List<ElementStateAlarm> allStateAlarms elementStateRepository = (ElementStateRepository) getRepository(ElementState.class);
		
		List<ElementStateAlarm> elementStatesAlarm = this.getAll(false).stream()
				.filter(e -> alarmConfigId
						.equals(e.getAlarmConfigId()))
				.collect(Collectors.toList());
		
		//List<BasicElement> basicElements = getsFromCache(new String[] { "alarmConfigId" },
		//		new String[] { String.valueOf(alarmConfigId) }, fillObject);
		
	//	if (!CollectionUtils.isEmpty(basicElements)) {
			
		//	return basicElements.stream()
		//			.map(be -> (ElementStateAlarm) be).collect(Collectors.toList());
	//	}
		
		if (!CollectionUtils.isEmpty(elementStatesAlarm)) {
			
			return elementStatesAlarm;
		}
		
		return Collections.emptyList();	
	}
	
	@Override
	public List<ElementStateAlarm> getAll(Boolean fillObject) {
		
		List<BasicElement> basicElements = getsFromCache(fillObject);
		
		if (!CollectionUtils.isEmpty(basicElements)) {
			
			return basicElements.stream()
					.map(be -> (ElementStateAlarm) be).collect(Collectors.toList());
		}
		
		return Collections.emptyList();
	}
	// endregion getters

	// region puts
	@Override
	public ElementStateAlarm put(ElementStateAlarm entity) {

		return (ElementStateAlarm) putFromCache(entity);
	}

	@Override
	public List<ElementStateAlarm> put(List<ElementStateAlarm> entities) {
		
		List<BasicElement> basicElements = entities.stream()
												   .map(be -> (BasicElement) be)
												   .collect(Collectors.toList());
		
		
		basicElements = putsFromCache(basicElements);
		
		return basicElements.stream()
						    .map(be -> (ElementStateAlarm) be)
						    .collect(Collectors.toList());
	}
	// endregion puts

	// region dels
	@Override
	public ElementStateAlarm del(ElementStateAlarm entity) {
		
		return (ElementStateAlarm) delFromCache(entity);
		
	}

	@Override
	public List<ElementStateAlarm> del(List<ElementStateAlarm> entities) {
		
		List<BasicElement> basicElements = entities.stream()
												   .map(be -> (BasicElement) be)
												   .collect(Collectors.toList());

		basicElements = delFromCache(basicElements);
		
		return basicElements.stream()
							.map(be -> (ElementStateAlarm) be)
							.collect(Collectors.toList());
	}
	// endregion dels

	@Override
	public ElementStateAlarm create(ElementStateAlarm elementStateAlarm) {

		try {

			return create(elementStateAlarm, null, null);

		} catch (EntityPersistenceException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			return null;
		}
	}

	@Override
	public ElementStateAlarm create(ElementStateAlarm elementStateAlarm, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {

		Connection conn = null;
		boolean closeConnection = false;
		boolean commit = false;
		conn = connection;
		ElementStateAlarm elementStateAlarmResult = null;

		try {

			if (conn == null) {

				conn = DataSourceConnection.getInstance().getConnection();
				conn.setAutoCommit(false);
				closeConnection = true;
				commit = true;
				persistOperations = new ArrayList<>();
			}

			// Insert ElementStateAlarm in BBDD
			ElementStateAlarm elementStateAlarmInserted = null;

			elementStateAlarmInserted = (ElementStateAlarm) getObjectDao().insert(conn, elementStateAlarm);

			if (elementStateAlarmInserted == null) {

				throw new EntityDBException(EntityDBException.Operation.INSERT, elementStateAlarm);
			}

			persistOperations.add(new MutablePair<>(PersistOperation.PUT, elementStateAlarmInserted));

			

			if (commit) {
				conn.commit();

				persistOperations(persistOperations);

				elementStateAlarmResult = get(elementStateAlarmInserted.getElementTypeId(),
						elementStateAlarmInserted.getStateId(), false);
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
			throw new EntityPersistenceException(EntityPersistenceException.Operation.CREATE, elementStateAlarm);

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

		return elementStateAlarmResult;
	}

	@Override
	public ElementStateAlarm clone(ElementStateAlarm entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ElementStateAlarm clone(ElementStateAlarm entity, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ElementStateAlarm update(ElementStateAlarm entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ElementStateAlarm update(ElementStateAlarm entity, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ElementStateAlarm delete(ElementStateAlarm elementStateAlarm) {

		try {

			return delete(elementStateAlarm, null, null);

		} catch (EntityPersistenceException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			return null;
		}
	}

	@Override
	public ElementStateAlarm delete(ElementStateAlarm elementStateAlarm, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {

		Connection conn = null;
		boolean closeConnection = false;
		boolean commit = false;
		conn = connection;
		ElementStateAlarm elementStateAlarmResult = null;
		ElementStateAlarm elementStateAlarmAux = null;

		try {

			if (elementStateAlarm == null || elementStateAlarm.getElementTypeId() == null
					|| elementStateAlarm.getStateId() == null) {

				throw new NullPointerException("elementStateAlarm is null");
			}

			elementStateAlarmAux = get(elementStateAlarm.getElementTypeId(), elementStateAlarm.getStateId(), false);

			if (elementStateAlarmAux == null) {

				throw new NullPointerException("elementStateAlarm not found");
			}

			if (conn == null) {

				conn = DataSourceConnection.getInstance().getConnection();
				conn.setAutoCommit(false);
				closeConnection = true;
				commit = true;
				persistOperations = new ArrayList<>();
			}

			// Delete AlarmConfig in BBDD
			ElementStateAlarm elementStateAlarmDeleted = null;

			elementStateAlarmDeleted = (ElementStateAlarm) getObjectDao().delete(conn, elementStateAlarmAux);

			if (elementStateAlarmDeleted == null) {

				throw new EntityDBException(EntityDBException.Operation.DELETE, elementStateAlarmAux);
			}

			persistOperations.add(new MutablePair<>(PersistOperation.DELETE, elementStateAlarmAux));

			if (commit) {

				conn.commit();

				// Update cache
				persistOperations(persistOperations);
			}

			elementStateAlarmResult = elementStateAlarmDeleted;

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
			throw new EntityPersistenceException(EntityPersistenceException.Operation.DELETE, elementStateAlarm);

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

		return elementStateAlarmResult;
	}

}
