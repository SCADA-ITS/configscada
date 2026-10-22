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
import com.revenga.rits.back.data.core.model.AlarmConfigMeasure;
import com.revenga.rits.back.data.core.model.BasicElement;
import com.revenga.rits.back.data.core.model.SmThreshold;
import com.revenga.rits.back.entities.lib.exception.EntityDBException;
import com.revenga.rits.back.entities.lib.exception.EntityPersistenceException;
import com.revenga.rits.back.entities.lib.repository.entity.EntityCacheable;
import com.revenga.rits.back.entities.lib.repository.entity.EntityPersistent;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepositoryOptions;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository.PersistOperation;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class SmThresholdRepository extends EntityRepository
		implements EntityCacheable<SmThreshold>, EntityPersistent<SmThreshold> {

	// region Constructors
	public SmThresholdRepository() {

		super(SmThreshold.class);
	}

	public SmThresholdRepository(EntityRepositoryOptions options) {

		super(SmThreshold.class, options);
	}

	public SmThresholdRepository(Boolean cacheable) {

		super(SmThreshold.class, cacheable);
	}

	public SmThresholdRepository(Boolean cacheable, Long cacheRetentionTime) {

		super(SmThreshold.class, cacheable, cacheRetentionTime);
	}
	// endregion Constructors

	// region getters
	public SmThreshold get(Long smTresholdId, Boolean fillObject) {

		return (SmThreshold) getFromCache(new String[] { String.valueOf(smTresholdId) }, fillObject);
	}

	@Override
	public List<SmThreshold> getAll(Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(be -> (SmThreshold) be).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}

	@Override
	public SmThreshold put(SmThreshold entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SmThreshold> put(List<SmThreshold> entities) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SmThreshold update(SmThreshold smThreshold) {

		try {

			return update(smThreshold, null, null);

		} catch (EntityPersistenceException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			return null;
		}
	}

	@Override
	public SmThreshold update(SmThreshold smThreshold, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {

		Connection conn = null;
		boolean closeConnection = false;
		boolean commit = false;
		conn = connection;
		SmThreshold smThresholdResult = null;
		SmThreshold smThresholdAux = null;

		try {

			if (smThreshold == null || smThreshold.getId() == null) {

				throw new NullPointerException("smThreshold is null");
			}

			smThresholdAux = get(smThreshold.getId(), false);

			if (smThresholdAux == null) {

				throw new NullPointerException("smThreshold not found");
			} else {

				smThresholdAux = (SmThreshold) smThresholdAux.clone();
			}

			smThresholdAux.setValue(smThreshold.getValue());

			if (conn == null) {

				conn = DataSourceConnection.getInstance().getConnection();
				conn.setAutoCommit(false);
				closeConnection = true;
				commit = true;
				persistOperations = new ArrayList<>();
			}

			// Update imsIncidentType in BBDD
			SmThreshold smThresholdUpdated = null;

			smThresholdUpdated = (SmThreshold) getObjectDao().update(conn, smThresholdAux);

			if (smThresholdUpdated == null) {

				throw new EntityDBException(EntityDBException.Operation.UPDATE, smThresholdAux);
			}

			persistOperations.add(new MutablePair<>(PersistOperation.PUT, smThresholdUpdated));

			if (commit) {

				conn.commit();

				persistOperations(persistOperations);
			}

			smThresholdResult = smThresholdUpdated;

		} catch (CloneNotSupportedException | EntityDBException | SQLException | NullPointerException | IOException e) {

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
			throw new EntityPersistenceException(EntityPersistenceException.Operation.UPDATE, smThreshold);

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

		return smThresholdResult;
	}

	@Override
	public SmThreshold del(SmThreshold entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SmThreshold> del(List<SmThreshold> entities) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SmThreshold create(SmThreshold entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SmThreshold create(SmThreshold entity, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SmThreshold clone(SmThreshold entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SmThreshold clone(SmThreshold entity, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SmThreshold delete(SmThreshold entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SmThreshold delete(SmThreshold entity, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {
		// TODO Auto-generated method stub
		return null;
	}

}
