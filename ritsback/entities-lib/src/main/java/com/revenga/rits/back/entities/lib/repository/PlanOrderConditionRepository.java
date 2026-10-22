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
import com.revenga.rits.back.data.core.model.PlanOrder;
import com.revenga.rits.back.data.core.model.PlanOrderCondition;
import com.revenga.rits.back.entities.lib.exception.EntityDBException;
import com.revenga.rits.back.entities.lib.exception.EntityPersistenceException;
import com.revenga.rits.back.entities.lib.repository.entity.EntityCacheable;
import com.revenga.rits.back.entities.lib.repository.entity.EntityPersistent;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepositoryOptions;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository.PersistOperation;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class PlanOrderConditionRepository extends EntityRepository
		implements EntityCacheable<PlanOrderCondition>, EntityPersistent<PlanOrderCondition> {

	// region Constructors
	public PlanOrderConditionRepository() {

		super(PlanOrderCondition.class);
	}

	public PlanOrderConditionRepository(EntityRepositoryOptions options) {

		super(PlanOrderCondition.class, options);
	}
	
	public PlanOrderConditionRepository(Boolean cacheable) {

		super(PlanOrderCondition.class, cacheable);
	}

	public PlanOrderConditionRepository(Boolean cacheable, Long cacheRetentionTime) {

		super(PlanOrderCondition.class, cacheable, cacheRetentionTime);
	}
	// endregion Constructors

	// region getters
	public PlanOrderCondition get(Long id, Long planId, Long planOrderId, Boolean fillObject) {

		return (PlanOrderCondition) getFromCache(
				new String[] { String.valueOf(id), String.valueOf(planId), String.valueOf(planOrderId) }, fillObject);
	}

	public List<PlanOrderCondition> getsByPlanOrder(Long planId, Long planOrderId, Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(
				new String[] { null, String.valueOf(planId), String.valueOf(planOrderId) }, fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(be -> (PlanOrderCondition) be).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}

	@Override
	public List<PlanOrderCondition> getAll(Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(be -> (PlanOrderCondition) be).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}
	// endregion getters

	// region puts
	@Override
	public PlanOrderCondition put(PlanOrderCondition entity) {

		return (PlanOrderCondition) putFromCache(entity);
	}

	@Override
	public List<PlanOrderCondition> put(List<PlanOrderCondition> entities) {

		List<BasicElement> basicElements = entities.stream().map(element -> (BasicElement) element)
				.collect(Collectors.toList());

		basicElements = putsFromCache(basicElements);

		return basicElements.stream().map(be -> (PlanOrderCondition) be).collect(Collectors.toList());
	}
	// endregion puts

	// region dels
	@Override
	public PlanOrderCondition del(PlanOrderCondition entity) {

		return (PlanOrderCondition) delFromCache(entity);

	}

	@Override
	public List<PlanOrderCondition> del(List<PlanOrderCondition> entities) {

		List<BasicElement> basicElements = entities.stream().map(element -> (BasicElement) element)
				.collect(Collectors.toList());

		basicElements = delFromCache(basicElements);

		return basicElements.stream().map(be -> (PlanOrderCondition) be).collect(Collectors.toList());
	}
	// endregion dels

	@Override
	public PlanOrderCondition create(PlanOrderCondition planOrderCondition) {

		try {

			return create(planOrderCondition, null, null);

		} catch (EntityPersistenceException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			return null;
		}
	}

	@Override
	public PlanOrderCondition create(PlanOrderCondition planOrderCondition, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {

		Connection conn = null;
		boolean closeConnection = false;
		boolean commit = false;
		conn = connection;
		PlanOrderCondition planOrderConditionResult = null;

		try {

			if (conn == null) {

				conn = DataSourceConnection.getInstance().getConnection();
				conn.setAutoCommit(false);
				closeConnection = true;
				commit = true;
				persistOperations = new ArrayList<>();
			}

			// Insert planOrderCondition in BBDD
			PlanOrderCondition planOrderConditionInserted = null;

			planOrderConditionInserted = (PlanOrderCondition) getObjectDao().insert(conn, planOrderCondition);

			if (planOrderConditionInserted == null) {

				throw new EntityDBException(EntityDBException.Operation.INSERT, planOrderCondition);
			}

			persistOperations.add(new MutablePair<>(PersistOperation.PUT, planOrderConditionInserted));

			if (commit) {
				conn.commit();

				persistOperations(persistOperations);
				
				planOrderConditionResult = get(planOrderConditionInserted.getId(), planOrderConditionInserted.getPlanId(),
						planOrderConditionInserted.getPlanOrderId(), false);
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
			throw new EntityPersistenceException(EntityPersistenceException.Operation.CREATE, planOrderCondition);

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

		return planOrderConditionResult;
	}

	@Override
	public PlanOrderCondition clone(PlanOrderCondition entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PlanOrderCondition clone(PlanOrderCondition entity, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PlanOrderCondition update(PlanOrderCondition entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PlanOrderCondition update(PlanOrderCondition entity, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PlanOrderCondition delete(PlanOrderCondition planOrderCondition) {

		try {

			return delete(planOrderCondition, null, null);

		} catch (EntityPersistenceException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			return null;
		}
	}

	@Override
	public PlanOrderCondition delete(PlanOrderCondition planOrderCondition, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {

		Connection conn = null;
		boolean closeConnection = false;
		boolean commit = false;
		conn = connection;
		PlanOrderCondition planOrderConditionResult = null;
		PlanOrderCondition planOrderConditionAux = null;

		try {

			if (planOrderCondition == null || !ObjectUtils.allNotNull(planOrderCondition.getId(),
					planOrderCondition.getPlanId(), planOrderCondition.getPlanOrderId())) {

				throw new NullPointerException("PlanOrderCondition is null");
			}

			planOrderConditionAux = get(planOrderCondition.getId(), planOrderCondition.getPlanId(),
					planOrderCondition.getPlanOrderId(), false);

			if (planOrderConditionAux == null) {

				throw new NullPointerException("PlanOrderCondition not found");
			}

			if (conn == null) {

				conn = DataSourceConnection.getInstance().getConnection();
				conn.setAutoCommit(false);
				closeConnection = true;
				commit = true;
				persistOperations = new ArrayList<>();
			}

			// Delete planOrderCondition in BBDD
			PlanOrderCondition planOrderConditionDeleted = null;

			planOrderConditionDeleted = (PlanOrderCondition) getObjectDao().delete(conn, planOrderConditionAux);

			if (planOrderConditionDeleted == null) {

				throw new EntityDBException(EntityDBException.Operation.DELETE, planOrderConditionAux);
			}

			persistOperations.add(new MutablePair<>(PersistOperation.DELETE, planOrderConditionAux));

			if (commit) {

				conn.commit();

				// Update cache
				persistOperations(persistOperations);
			}

			planOrderConditionResult = planOrderConditionDeleted;

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
			throw new EntityPersistenceException(EntityPersistenceException.Operation.DELETE, planOrderCondition);

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

		return planOrderConditionResult;
	}

}
