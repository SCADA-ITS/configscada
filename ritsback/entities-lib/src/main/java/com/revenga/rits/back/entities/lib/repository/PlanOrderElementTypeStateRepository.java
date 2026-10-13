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
import com.revenga.rits.back.data.core.model.PlanOrderCondition;
import com.revenga.rits.back.data.core.model.PlanOrderElementTypeState;
import com.revenga.rits.back.entities.lib.exception.EntityDBException;
import com.revenga.rits.back.entities.lib.exception.EntityPersistenceException;
import com.revenga.rits.back.entities.lib.repository.entity.EntityCacheable;
import com.revenga.rits.back.entities.lib.repository.entity.EntityPersistent;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepositoryOptions;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository.PersistOperation;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class PlanOrderElementTypeStateRepository extends EntityRepository
		implements EntityCacheable<PlanOrderElementTypeState>, EntityPersistent<PlanOrderElementTypeState> {

	// region Constructors
	public PlanOrderElementTypeStateRepository() {

		super(PlanOrderElementTypeState.class);
	}

	public PlanOrderElementTypeStateRepository(EntityRepositoryOptions options) {

		super(PlanOrderElementTypeState.class, options);
	}
	
	public PlanOrderElementTypeStateRepository(Boolean cacheable) {

		super(PlanOrderElementTypeState.class, cacheable);
	}

	public PlanOrderElementTypeStateRepository(Boolean cacheable, Long cacheRetentionTime) {

		super(PlanOrderElementTypeState.class, cacheable, cacheRetentionTime);
	}
	// endregion Constructors

	// region getters

	public PlanOrderElementTypeState get(Long planId, Long planOrderId, Long elementTypeId, Long elementTypeStateId,
			Long elementId, Boolean fillObject) {

		return (PlanOrderElementTypeState) getFromCache(
				new String[] { String.valueOf(planId), String.valueOf(planOrderId), String.valueOf(elementTypeId),
						String.valueOf(elementTypeStateId), String.valueOf(elementId) },
				fillObject);
	}

	public List<PlanOrderElementTypeState> getsByPlanOrder(Long planId, Long planOrderId, Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(
				new String[] { String.valueOf(planId), String.valueOf(planOrderId), null, null, null }, fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(PlanOrderElementTypeState.class::cast).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}

	@Override
	public List<PlanOrderElementTypeState> getAll(Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(PlanOrderElementTypeState.class::cast).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}
	// endregion getters

	// region puts
	@Override
	public PlanOrderElementTypeState put(PlanOrderElementTypeState entity) {

		return (PlanOrderElementTypeState) putFromCache(entity);
	}

	@Override
	public List<PlanOrderElementTypeState> put(List<PlanOrderElementTypeState> entities) {

		List<BasicElement> basicElements = entities.stream().map(BasicElement.class::cast)
				.collect(Collectors.toList());

		basicElements = putsFromCache(basicElements);

		return basicElements.stream().map(PlanOrderElementTypeState.class::cast).collect(Collectors.toList());
	}
	// endregion puts

	// region dels
	
	@Override
	public PlanOrderElementTypeState del(PlanOrderElementTypeState entity) {

		return (PlanOrderElementTypeState) delFromCache(entity);

	}

	@Override
	public List<PlanOrderElementTypeState> del(List<PlanOrderElementTypeState> entities) {

		List<BasicElement> basicElements = entities.stream().map(BasicElement.class::cast)
				.collect(Collectors.toList());

		basicElements = delFromCache(basicElements);

		return basicElements.stream().map(PlanOrderElementTypeState.class::cast).collect(Collectors.toList());
	}
	// endregion dels

	@Override
	public PlanOrderElementTypeState create(PlanOrderElementTypeState planOrderElementTypeState) {

		try {

			return create(planOrderElementTypeState, null, null);

		} catch (EntityPersistenceException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			return null;
		}
	}

	@Override
	public PlanOrderElementTypeState create(PlanOrderElementTypeState planOrderElementTypeState, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {

		Connection conn = null;
		boolean closeConnection = false;
		boolean commit = false;
		conn = connection;
		PlanOrderElementTypeState planOrderElementTypeStateResult = null;

		try {

			if (conn == null) {

				conn = DataSourceConnection.getInstance().getConnection();
				conn.setAutoCommit(false);
				closeConnection = true;
				commit = true;
				persistOperations = new ArrayList<>();
			}

			// Insert planOrderElementTypeState in BBDD
			PlanOrderElementTypeState planOrderElementTypeStateInserted = null;

			planOrderElementTypeStateInserted = (PlanOrderElementTypeState) getObjectDao().insert(conn,
					planOrderElementTypeState);

			if (planOrderElementTypeStateInserted == null) {

				throw new EntityDBException(EntityDBException.Operation.INSERT, planOrderElementTypeState);
			}

			persistOperations.add(new MutablePair<>(PersistOperation.PUT, planOrderElementTypeStateInserted));

			if (commit) {
				conn.commit();

				persistOperations(persistOperations);
			}

			planOrderElementTypeStateResult = get(planOrderElementTypeStateInserted.getPlanId(),
					planOrderElementTypeStateInserted.getPlanOrderId(),
					planOrderElementTypeStateInserted.getElementTypeId(),
					planOrderElementTypeStateInserted.getElementTypeStateId(),
					planOrderElementTypeStateInserted.getElementId(), false);

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
			throw new EntityPersistenceException(EntityPersistenceException.Operation.CREATE,
					planOrderElementTypeState);

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

		return planOrderElementTypeStateResult;
	}

	@Override
	public PlanOrderElementTypeState clone(PlanOrderElementTypeState entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PlanOrderElementTypeState clone(PlanOrderElementTypeState entity, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PlanOrderElementTypeState update(PlanOrderElementTypeState entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PlanOrderElementTypeState update(PlanOrderElementTypeState entity, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PlanOrderElementTypeState delete(PlanOrderElementTypeState planOrderElementTypeState) {

		try {

			return delete(planOrderElementTypeState, null, null);

		} catch (EntityPersistenceException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			return null;
		}
	}

	@Override
	public PlanOrderElementTypeState delete(PlanOrderElementTypeState planOrderElementTypeState, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {

		Connection conn = null;
		boolean closeConnection = false;
		boolean commit = false;
		conn = connection;
		PlanOrderElementTypeState planOrderElementTypeStateResult = null;
		PlanOrderElementTypeState planOrderElementTypeStateAux = null;

		try {

			if (planOrderElementTypeState == null || !ObjectUtils.allNotNull(planOrderElementTypeState.getPlanId(),
					planOrderElementTypeState.getPlanOrderId(), planOrderElementTypeState.getElementTypeId(),
					planOrderElementTypeState.getElementTypeStateId(), planOrderElementTypeState.getElementId())) {

				throw new NullPointerException("PlanOrderElementTypeState is null");
			}

			planOrderElementTypeStateAux = get(planOrderElementTypeState.getPlanId(),
					planOrderElementTypeState.getPlanOrderId(), planOrderElementTypeState.getElementTypeId(),
					planOrderElementTypeState.getElementTypeStateId(), planOrderElementTypeState.getElementId(), false);

			if (planOrderElementTypeStateAux == null) {

				throw new NullPointerException("PlanOrderElementTypeState not found");
			}

			if (conn == null) {

				conn = DataSourceConnection.getInstance().getConnection();
				conn.setAutoCommit(false);
				closeConnection = true;
				commit = true;
				persistOperations = new ArrayList<>();
			}

			// Delete PlanOrderElementTypeState in BBDD
			PlanOrderElementTypeState planOrderElementTypeStateDeleted = null;

			planOrderElementTypeStateDeleted = (PlanOrderElementTypeState) getObjectDao().delete(conn,
					planOrderElementTypeStateAux);

			if (planOrderElementTypeStateDeleted == null) {

				throw new EntityDBException(EntityDBException.Operation.DELETE, planOrderElementTypeStateAux);
			}

			persistOperations.add(new MutablePair<>(PersistOperation.DELETE, planOrderElementTypeStateAux));

			if (commit) {

				conn.commit();

				// Update cache
				persistOperations(persistOperations);
			}

			planOrderElementTypeStateResult = planOrderElementTypeStateDeleted;

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
			throw new EntityPersistenceException(EntityPersistenceException.Operation.DELETE,
					planOrderElementTypeState);

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

		return planOrderElementTypeStateResult;
	}
}
