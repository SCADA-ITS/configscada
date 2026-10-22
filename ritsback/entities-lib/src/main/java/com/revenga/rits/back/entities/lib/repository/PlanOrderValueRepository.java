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
import com.revenga.rits.back.data.core.model.PlanOrderValue;
import com.revenga.rits.back.entities.lib.exception.EntityDBException;
import com.revenga.rits.back.entities.lib.exception.EntityPersistenceException;
import com.revenga.rits.back.entities.lib.repository.entity.EntityCacheable;
import com.revenga.rits.back.entities.lib.repository.entity.EntityPersistent;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository.PersistOperation;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepositoryOptions;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class PlanOrderValueRepository extends EntityRepository
		implements EntityCacheable<PlanOrderValue>, EntityPersistent<PlanOrderValue> {

	// region Constructors
	public PlanOrderValueRepository() {

		super(PlanOrderValue.class);
	}

	public PlanOrderValueRepository(EntityRepositoryOptions options) {

		super(PlanOrderValue.class, options);
	}
	
	public PlanOrderValueRepository(Boolean cacheable) {

		super(PlanOrderValue.class, cacheable);
	}

	public PlanOrderValueRepository(Boolean cacheable, Long cacheRetentionTime) {

		super(PlanOrderValue.class, cacheable, cacheRetentionTime);
	}
	// endregion Constructors

	// region getters

	public PlanOrderValue get(Long planId, Long planOrderId, Long planOrderTypeId, Long planOrderTypeParamId,
			Boolean fillObject) {

		return (PlanOrderValue) getFromCache(new String[] { String.valueOf(planId), String.valueOf(planOrderId),
				String.valueOf(planOrderTypeId), String.valueOf(planOrderTypeParamId) }, fillObject);
	}

	public List<PlanOrderValue> getsByPlanOrder(Long planId, Long planOrderId, Long planOrderTypeId,
			Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(new String[] { String.valueOf(planId),
				String.valueOf(planOrderId), String.valueOf(planOrderTypeId), null }, fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(be -> (PlanOrderValue) be).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}

	@Override
	public List<PlanOrderValue> getAll(Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(be -> (PlanOrderValue) be).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}
	// endregion getters

	// region puts
	@Override
	public PlanOrderValue put(PlanOrderValue entity) {

		return (PlanOrderValue) putFromCache(entity);
	}

	@Override
	public List<PlanOrderValue> put(List<PlanOrderValue> entities) {

		List<BasicElement> basicElements = entities.stream().map(elementTypeParam -> (BasicElement) elementTypeParam)
				.collect(Collectors.toList());

		basicElements = putsFromCache(basicElements);

		return basicElements.stream().map(be -> (PlanOrderValue) be).collect(Collectors.toList());
	}
	// endregion puts

	// region dels
	@Override
	public PlanOrderValue del(PlanOrderValue entity) {

		return (PlanOrderValue) delFromCache(entity);

	}

	@Override
	public List<PlanOrderValue> del(List<PlanOrderValue> entities) {

		List<BasicElement> basicElements = entities.stream().map(elementTypeParam -> (BasicElement) elementTypeParam)
				.collect(Collectors.toList());

		basicElements = delFromCache(basicElements);

		return basicElements.stream().map(be -> (PlanOrderValue) be).collect(Collectors.toList());
	}
	// endregion dels

	@Override
	public PlanOrderValue create(PlanOrderValue planOrderValue) {

		try {

			return create(planOrderValue, null, null);

		} catch (EntityPersistenceException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			return null;
		}

	}

	@Override
	public PlanOrderValue create(PlanOrderValue planOrderValue, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {

		Connection conn = null;
		boolean closeConnection = false;
		boolean commit = false;
		conn = connection;
		PlanOrderValue planOrderValueResult = null;

		try {

			if (conn == null) {

				conn = DataSourceConnection.getInstance().getConnection();
				conn.setAutoCommit(false);
				closeConnection = true;
				commit = true;
				persistOperations = new ArrayList<>();
			}

			// Insert planOrderValue in BBDD
			PlanOrderValue planOrderValueInserted = null;

			planOrderValueInserted = (PlanOrderValue) getObjectDao().insert(conn, planOrderValue);

			if (planOrderValueInserted == null) {

				throw new EntityDBException(EntityDBException.Operation.INSERT, planOrderValue);
			}

			persistOperations.add(new MutablePair<>(PersistOperation.PUT, planOrderValueInserted));

			if (commit) {
				conn.commit();

				persistOperations(persistOperations);
				
				planOrderValueResult = get(planOrderValue.getPlanId(), planOrderValue.getPlanOrderId(),
						planOrderValue.getPlanOrderTypeId(), planOrderValue.getPlanOrderTypeParamId(), false);
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
			throw new EntityPersistenceException(EntityPersistenceException.Operation.CREATE, planOrderValue);

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

		return planOrderValueResult;

	}

	@Override
	public PlanOrderValue clone(PlanOrderValue entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PlanOrderValue clone(PlanOrderValue entity, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PlanOrderValue update(PlanOrderValue planOrderValue) {
		try {

			return update(planOrderValue, null, null);

		} catch (EntityPersistenceException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			return null;
		}
	}

	@Override
	public PlanOrderValue update(PlanOrderValue planOrderValue, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {

		Connection conn = null;
		boolean closeConnection = false;
		boolean commit = false;
		conn = connection;
		PlanOrderValue planOrderValueResult = null;
		PlanOrderValue planOrderValueAux = null;

		try {

			if (planOrderValue == null || !ObjectUtils.allNotNull(planOrderValue.getPlanId(), planOrderValue.getPlanOrderId(),
							planOrderValue.getPlanOrderTypeId(), planOrderValue.getPlanOrderTypeParamId())) {

				throw new NullPointerException("PlanOrderValue is null");
			}

			planOrderValueAux = get(planOrderValue.getPlanId(), planOrderValue.getPlanOrderId(),
					planOrderValue.getPlanOrderTypeId(), planOrderValue.getPlanOrderTypeParamId(), false);

			if (planOrderValueAux == null) {

				throw new NullPointerException("PlanOrderValue not found");
			}

			if (planOrderValue.getValue() != null)
				planOrderValueAux.setValue(planOrderValue.getValue());

			if (conn == null) {

				conn = DataSourceConnection.getInstance().getConnection();
				conn.setAutoCommit(false);
				closeConnection = true;
				commit = true;
				persistOperations = new ArrayList<>();
			}

			// Update planOrder in BBDD
			PlanOrderValue planOrderValueUpdated = null;

			planOrderValueUpdated = (PlanOrderValue) getObjectDao().update(conn, planOrderValueAux);
			if (planOrderValueUpdated == null) {

				throw new EntityDBException(EntityDBException.Operation.UPDATE, planOrderValueAux);
			}

			persistOperations.add(new MutablePair<>(PersistOperation.PUT, planOrderValueUpdated));

			if (commit) {

				conn.commit();

				persistOperations(persistOperations);
			}

			planOrderValueResult = planOrderValueUpdated;

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
			throw new EntityPersistenceException(EntityPersistenceException.Operation.UPDATE, planOrderValue);

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

		return planOrderValueResult;
	}

	@Override
	public PlanOrderValue delete(PlanOrderValue planOrderValue) {
		try {

			return delete(planOrderValue, null, null);

		} catch (EntityPersistenceException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			return null;
		}
	}

	@Override
	public PlanOrderValue delete(PlanOrderValue planOrderValue, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {
		Connection conn = null;
		boolean closeConnection = false;
		boolean commit = false;
		conn = connection;
		PlanOrderValue planOrderValueResult = null;
		PlanOrderValue planOrderValueAux = null;

		try {

			if (planOrderValue == null || !ObjectUtils.allNotNull(planOrderValue.getPlanId(), planOrderValue.getPlanOrderId(),
					planOrderValue.getPlanOrderTypeId(), planOrderValue.getPlanOrderTypeParamId())) {

				throw new NullPointerException("PlanOrderValue is null");
			}

			planOrderValueAux = get(planOrderValue.getPlanId(), planOrderValue.getPlanOrderId(),
					planOrderValue.getPlanOrderTypeId(), planOrderValue.getPlanOrderTypeParamId(), false);

			if (planOrderValueAux == null) {

				throw new NullPointerException("PlanOrderValue not found");
			}

			if (conn == null) {

				conn = DataSourceConnection.getInstance().getConnection();
				conn.setAutoCommit(false);
				closeConnection = true;
				commit = true;
				persistOperations = new ArrayList<>();
			}

			// Delete planOrderValue in BBDD
			PlanOrderValue planOrderValueDeleted = null;

			planOrderValueDeleted = (PlanOrderValue) getObjectDao().delete(conn, planOrderValueAux);

			if (planOrderValueDeleted == null) {

				throw new EntityDBException(EntityDBException.Operation.DELETE, planOrderValueAux);
			}

			persistOperations.add(new MutablePair<>(PersistOperation.DELETE, planOrderValueAux));

			if (commit) {

				conn.commit();

				// Update cache
				persistOperations(persistOperations);
			}

			planOrderValueResult = planOrderValueDeleted;

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
			throw new EntityPersistenceException(EntityPersistenceException.Operation.DELETE, planOrderValue);

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

		return planOrderValueResult;
	}
}
