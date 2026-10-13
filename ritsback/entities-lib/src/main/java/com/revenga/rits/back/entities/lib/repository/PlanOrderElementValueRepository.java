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
import com.revenga.rits.back.data.core.model.PlanOrderElementValue;
import com.revenga.rits.back.entities.lib.exception.EntityDBException;
import com.revenga.rits.back.entities.lib.exception.EntityPersistenceException;
import com.revenga.rits.back.entities.lib.repository.entity.EntityCacheable;
import com.revenga.rits.back.entities.lib.repository.entity.EntityPersistent;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository.PersistOperation;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepositoryOptions;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class PlanOrderElementValueRepository extends EntityRepository
		implements EntityCacheable<PlanOrderElementValue>, EntityPersistent<PlanOrderElementValue> {

	// region Constructors
	public PlanOrderElementValueRepository() {

		super(PlanOrderElementValue.class);
	}

	public PlanOrderElementValueRepository(EntityRepositoryOptions options) {

		super(PlanOrderElementValue.class, options);
	}
	
	public PlanOrderElementValueRepository(Boolean cacheable) {

		super(PlanOrderElementValue.class, cacheable);
	}

	public PlanOrderElementValueRepository(Boolean cacheable, Long cacheRetentionTime) {

		super(PlanOrderElementValue.class, cacheable, cacheRetentionTime);
	}
	// endregion Constructors

	// region getters

	public PlanOrderElementValue get(Long planId, Long planOrderId, Long elementTypeId, Long paramTypeId,
			Long elementTypeParamId, Long elementId, Boolean fillObject) {

		return (PlanOrderElementValue) getFromCache(
				new String[] { String.valueOf(planId), String.valueOf(planOrderId), String.valueOf(elementTypeId),
						String.valueOf(paramTypeId), String.valueOf(elementTypeParamId), String.valueOf(elementId) },
				fillObject);
	}

	public List<PlanOrderElementValue> getsByPlanOrder(Long planId, Long planOrderId, Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(
				new String[] { String.valueOf(planId), String.valueOf(planOrderId), null, null, null, null },
				fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(be -> (PlanOrderElementValue) be).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}

	@Override
	public List<PlanOrderElementValue> getAll(Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(be -> (PlanOrderElementValue) be).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}
	// endregion getters

	// region puts
	@Override
	public PlanOrderElementValue put(PlanOrderElementValue entity) {

		return (PlanOrderElementValue) putFromCache(entity);
	}

	@Override
	public List<PlanOrderElementValue> put(List<PlanOrderElementValue> entities) {

		List<BasicElement> basicElements = entities.stream().map(elementTypeParam -> (BasicElement) elementTypeParam)
				.collect(Collectors.toList());

		basicElements = putsFromCache(basicElements);

		return basicElements.stream().map(be -> (PlanOrderElementValue) be).collect(Collectors.toList());
	}
	// endregion puts

	// region dels
	@Override
	public PlanOrderElementValue del(PlanOrderElementValue entity) {

		return (PlanOrderElementValue) delFromCache(entity);

	}

	@Override
	public List<PlanOrderElementValue> del(List<PlanOrderElementValue> entities) {

		List<BasicElement> basicElements = entities.stream().map(elementTypeParam -> (BasicElement) elementTypeParam)
				.collect(Collectors.toList());

		basicElements = delFromCache(basicElements);

		return basicElements.stream().map(be -> (PlanOrderElementValue) be).collect(Collectors.toList());
	}
	// endregion dels

	@Override
	public PlanOrderElementValue create(PlanOrderElementValue planOrderElementValue) {

		try {

			return create(planOrderElementValue, null, null);

		} catch (EntityPersistenceException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			return null;
		}
	}

	@Override
	public PlanOrderElementValue create(PlanOrderElementValue planOrderElementValue, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {

		Connection conn = null;
		boolean closeConnection = false;
		boolean commit = false;
		conn = connection;
		PlanOrderElementValue planOrderElementValueResult = null;

		try {

			if (conn == null) {

				conn = DataSourceConnection.getInstance().getConnection();
				conn.setAutoCommit(false);
				closeConnection = true;
				commit = true;
				persistOperations = new ArrayList<>();
			}

			// Insert planOrderElementValue in BBDD
			PlanOrderElementValue planOrderElementValueInserted = null;

			planOrderElementValueInserted = (PlanOrderElementValue) getObjectDao().insert(conn, planOrderElementValue);

			if (planOrderElementValueInserted == null) {

				throw new EntityDBException(EntityDBException.Operation.INSERT, planOrderElementValue);
			}

			persistOperations.add(new MutablePair<>(PersistOperation.PUT, planOrderElementValueInserted));

			if (commit) {
				conn.commit();

				persistOperations(persistOperations);
				
				planOrderElementValueResult = get(planOrderElementValueInserted.getPlanId(),
						planOrderElementValueInserted.getPlanOrderId(), planOrderElementValueInserted.getElementTypeId(),
						planOrderElementValueInserted.getParamTypeId(),
						planOrderElementValueInserted.getElementTypeParamId(), planOrderElementValueInserted.getElementId(),
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
			throw new EntityPersistenceException(EntityPersistenceException.Operation.CREATE, planOrderElementValue);

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

		return planOrderElementValueResult;
	}

	@Override
	public PlanOrderElementValue clone(PlanOrderElementValue entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PlanOrderElementValue clone(PlanOrderElementValue entity, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PlanOrderElementValue update(PlanOrderElementValue entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PlanOrderElementValue update(PlanOrderElementValue entity, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PlanOrderElementValue delete(PlanOrderElementValue planOrderElementValue) {

		try {

			return delete(planOrderElementValue, null, null);

		} catch (EntityPersistenceException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			return null;
		}
	}

	@Override
	public PlanOrderElementValue delete(PlanOrderElementValue planOrderElementValue, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {

		Connection conn = null;
		boolean closeConnection = false;
		boolean commit = false;
		conn = connection;
		PlanOrderElementValue planOrderElementValueResult = null;
		PlanOrderElementValue planOrderElementValueAux = null;

		try {

			if (planOrderElementValue == null || !ObjectUtils.allNotNull(planOrderElementValue.getPlanId(),
					planOrderElementValue.getPlanOrderId(), planOrderElementValue.getElementTypeId(),
					planOrderElementValue.getParamTypeId(), planOrderElementValue.getElementTypeParamId(),
					planOrderElementValue.getElementId())) {

				throw new NullPointerException("PlanOrderElementValue is null");
			}

			planOrderElementValueAux = get(planOrderElementValue.getPlanId(),
					planOrderElementValue.getPlanOrderId(), planOrderElementValue.getElementTypeId(),
					planOrderElementValue.getParamTypeId(), planOrderElementValue.getElementTypeParamId(),
					planOrderElementValue.getElementId(), false);

			if (planOrderElementValueAux == null) {

				throw new NullPointerException("PlanOrderElementValue not found");
			}

			if (conn == null) {

				conn = DataSourceConnection.getInstance().getConnection();
				conn.setAutoCommit(false);
				closeConnection = true;
				commit = true;
				persistOperations = new ArrayList<>();
			}

			// Delete PlanOrderElementValue in BBDD
			PlanOrderElementValue planOrderElementValueDeleted = null;

			planOrderElementValueDeleted = (PlanOrderElementValue) getObjectDao().delete(conn, planOrderElementValueAux);

			if (planOrderElementValueDeleted == null) {

				throw new EntityDBException(EntityDBException.Operation.DELETE, planOrderElementValueAux);
			}

			persistOperations.add(new MutablePair<>(PersistOperation.DELETE, planOrderElementValueAux));

			if (commit) {

				conn.commit();

				// Update cache
				persistOperations(persistOperations);
			}

			planOrderElementValueResult = planOrderElementValueDeleted;

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
			throw new EntityPersistenceException(EntityPersistenceException.Operation.DELETE, planOrderElementValue);

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

		return planOrderElementValueResult;
	}
}
