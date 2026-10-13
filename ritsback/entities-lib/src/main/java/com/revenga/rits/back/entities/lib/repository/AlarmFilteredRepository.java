package com.revenga.rits.back.entities.lib.repository;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.NotImplementedException;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;

import com.revenga.rits.back.data.core.dao.manager.DataSourceConnection;
import com.revenga.rits.back.data.core.model.AlarmFiltered;
import com.revenga.rits.back.data.core.model.BasicElement;
import com.revenga.rits.back.entities.lib.exception.EntityDBException;
import com.revenga.rits.back.entities.lib.exception.EntityPersistenceException;
import com.revenga.rits.back.entities.lib.repository.entity.EntityCacheable;
import com.revenga.rits.back.entities.lib.repository.entity.EntityPersistent;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class AlarmFilteredRepository extends EntityRepository
		implements EntityCacheable<AlarmFiltered>, EntityPersistent<AlarmFiltered> {

	// region Constructors
	public AlarmFilteredRepository() {

		super(AlarmFiltered.class);
	}

	public AlarmFilteredRepository(Boolean cacheable) {

		super(AlarmFiltered.class, cacheable);
	}

	public AlarmFilteredRepository(Boolean cacheable, Long cacheRetentionTime) {

		super(AlarmFiltered.class, cacheable, cacheRetentionTime);
	}
	// endregion Constructors

	// region getters
	public AlarmFiltered get(Long alarmConfigId, Long elementTypeId, Long elementId, Boolean fillObject) {

		return (AlarmFiltered) getFromCache(new String[] { String.valueOf(alarmConfigId), String.valueOf(elementTypeId),
				String.valueOf(elementId) }, fillObject);
	}

	public List<AlarmFiltered> getsByAlarmConfig(Long alarmConfigId, Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(new String[] { String.valueOf(alarmConfigId) }, fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(be -> (AlarmFiltered) be).collect(Collectors.toList());
		}
		return Collections.emptyList();
	}

	public List<AlarmFiltered> getsByElement(Long elementTypeId, Long elementId, Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(new String[] { "elementTypeId", "elementId" },
				new String[] { String.valueOf(elementTypeId), String.valueOf(elementId) }, fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(be -> (AlarmFiltered) be).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}

	@Override
	public List<AlarmFiltered> getAll(Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(be -> (AlarmFiltered) be).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}
	// endregion getters

	// region puts
	@Override
	public AlarmFiltered put(AlarmFiltered entity) {

		return (AlarmFiltered) putFromCache(entity);
	}

	@Override
	public List<AlarmFiltered> put(List<AlarmFiltered> entities) {

		List<BasicElement> basicElements = entities.stream().map(alarmConfig -> (BasicElement) alarmConfig)
				.collect(Collectors.toList());

		basicElements = putsFromCache(basicElements);

		return basicElements.stream().map(be -> (AlarmFiltered) be).collect(Collectors.toList());
	}
	// endregion puts

	// region dels
	@Override
	public AlarmFiltered del(AlarmFiltered entity) {

		return (AlarmFiltered) delFromCache(entity);

	}

	@Override
	public List<AlarmFiltered> del(List<AlarmFiltered> entities) {

		List<BasicElement> basicElements = entities.stream().map(alarmConfig -> (BasicElement) alarmConfig)
				.collect(Collectors.toList());

		basicElements = delFromCache(basicElements);

		return basicElements.stream().map(be -> (AlarmFiltered) be).collect(Collectors.toList());
	}
	// endregion dels

	@Override
	public AlarmFiltered create(AlarmFiltered alarmFiltered) {

		try {

			return create(alarmFiltered, null, null);

		} catch (EntityPersistenceException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			return null;
		}
	}

	@Override
	public AlarmFiltered create(AlarmFiltered alarmFiltered, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {

		Connection conn = null;
		boolean closeConnection = false;
		boolean commit = false;
		conn = connection;
		AlarmFiltered alarmFilteredResult = null;

		try {

			if (conn == null) {

				conn = DataSourceConnection.getInstance().getConnection();
				conn.setAutoCommit(false);
				closeConnection = true;
				commit = true;
				persistOperations = new ArrayList<>();
			}

			// Insert AlarmFiltered in BBDD
			AlarmFiltered alarmFilteredInserted = null;

			alarmFilteredInserted = (AlarmFiltered) getObjectDao().insert(conn, alarmFiltered);

			if (alarmFilteredInserted == null) {

				throw new EntityDBException(EntityDBException.Operation.INSERT, alarmFiltered);
			}

			persistOperations.add(new MutablePair<>(PersistOperation.PUT, alarmFilteredInserted));

			if (commit) {
				conn.commit();

				persistOperations(persistOperations);
				
				alarmFilteredResult = get(alarmFilteredInserted.getAlarmConfigId(), alarmFilteredInserted.getElementTypeId(),
						alarmFilteredInserted.getElementId(), false);
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
			throw new EntityPersistenceException(EntityPersistenceException.Operation.CREATE, alarmFiltered);

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

		return alarmFilteredResult;
	}

	@Override
	public AlarmFiltered clone(AlarmFiltered entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AlarmFiltered clone(AlarmFiltered entity, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AlarmFiltered update(AlarmFiltered alarmFiltered) {
		try {

			return update(alarmFiltered, null, null);

		} catch (EntityPersistenceException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			return null;
		}
	}

	@Override
	public AlarmFiltered update(AlarmFiltered alarmFiltered, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {

		throw new NotImplementedException(
				"Method " + Thread.currentThread().getStackTrace()[1].getMethodName() + " without implementation");
	}

	@Override
	public AlarmFiltered delete(AlarmFiltered alarmFiltered) {

		try {

			return delete(alarmFiltered, null, null);

		} catch (EntityPersistenceException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			return null;
		}
	}

	@Override
	public AlarmFiltered delete(AlarmFiltered alarmFiltered, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {

		Connection conn = null;
		boolean closeConnection = false;
		boolean commit = false;
		conn = connection;
		AlarmFiltered alarmFilteredResult = null;
		AlarmFiltered alarmFilteredAux = null;

		try {

			if (alarmFiltered == null || !ObjectUtils.allNotNull(alarmFiltered.getAlarmConfigId(),
					alarmFiltered.getElementTypeId(), alarmFiltered.getElementId())) {

				throw new NullPointerException("alarmFiltered is null");
			}

			alarmFilteredAux = get(alarmFiltered.getAlarmConfigId(), alarmFiltered.getElementTypeId(), alarmFiltered.getElementId(),
					false);

			if (alarmFilteredAux == null) {

				throw new NullPointerException("alarmFiltered not found");
			}

			if (conn == null) {

				conn = DataSourceConnection.getInstance().getConnection();
				conn.setAutoCommit(false);
				closeConnection = true;
				commit = true;
				persistOperations = new ArrayList<>();
			}

			// Delete AlarmFiltered in BBDD
			AlarmFiltered alarmFilteredDeleted = null;

			alarmFilteredDeleted = (AlarmFiltered) getObjectDao().delete(conn, alarmFilteredAux);

			if (alarmFilteredDeleted == null) {

				throw new EntityDBException(EntityDBException.Operation.DELETE, alarmFilteredAux);
			}

			persistOperations.add(new MutablePair<>(PersistOperation.DELETE, alarmFilteredAux));

			if (commit) {

				conn.commit();

				// Update cache
				persistOperations(persistOperations);
			}

			alarmFilteredResult = alarmFilteredDeleted;

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
			throw new EntityPersistenceException(EntityPersistenceException.Operation.DELETE, alarmFiltered);

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

		return alarmFilteredResult;
	}

}
