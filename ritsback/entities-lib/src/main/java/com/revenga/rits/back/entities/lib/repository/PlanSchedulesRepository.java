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
import com.revenga.rits.back.data.core.model.PlanScheduleState;
import com.revenga.rits.back.data.core.model.PlanScheduleStateType;
import com.revenga.rits.back.data.core.model.PlanSchedules;
import com.revenga.rits.back.entities.lib.exception.EntityDBException;
import com.revenga.rits.back.entities.lib.exception.EntityPersistenceException;
import com.revenga.rits.back.entities.lib.repository.entity.EntityCacheable;
import com.revenga.rits.back.entities.lib.repository.entity.EntityPersistent;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository.PersistOperation;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class PlanSchedulesRepository extends EntityRepository
		implements EntityCacheable<PlanSchedules>, EntityPersistent<PlanSchedules> {

	// region Constructors
	public PlanSchedulesRepository() {

		super(PlanSchedules.class);
	}

	public PlanSchedulesRepository(Boolean cacheable) {

		super(PlanSchedules.class, cacheable);
	}

	public PlanSchedulesRepository(Boolean cacheable, Long cacheRetentionTime) {

		super(PlanSchedules.class, cacheable, cacheRetentionTime);
	}

	public PlanSchedulesRepository(Boolean cacheable, List<EntityRepository> repositories) {

		super(PlanSchedules.class, cacheable, repositories);
	}

	public PlanSchedulesRepository(Boolean cacheable, Long cacheRetentionTime, List<EntityRepository> repositories) {

		super(PlanSchedules.class, cacheable, cacheRetentionTime, repositories);
	}
	// endregion Constructors

	// region getters
	public PlanSchedules get(Long id, Boolean fillObject) {

		return (PlanSchedules) getFromCache(new String[] { String.valueOf(id) }, fillObject);
	}

	public List<PlanSchedules> getsByPlan(Long planId, Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			List<PlanSchedules> planSchedules = basicElements.stream().map(be -> (PlanSchedules) be)
					.collect(Collectors.toList());

			return planSchedules.stream().filter(e -> e.getPlanId().equals(planId)).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}

	@Override
	public List<PlanSchedules> getAll(Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(be -> (PlanSchedules) be).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}
	// endregion getters

	// region puts
	@Override
	public PlanSchedules put(PlanSchedules entity) {

		return (PlanSchedules) putFromCache(entity);
	}

	@Override
	public List<PlanSchedules> put(List<PlanSchedules> entities) {

		List<BasicElement> basicElements = entities.stream().map(element -> (BasicElement) element)
				.collect(Collectors.toList());

		basicElements = putsFromCache(basicElements);

		return basicElements.stream().map(be -> (PlanSchedules) be).collect(Collectors.toList());
	}
	// endregion puts

	// region dels
	@Override
	public PlanSchedules del(PlanSchedules entity) {

		return (PlanSchedules) delFromCache(entity);

	}

	@Override
	public List<PlanSchedules> del(List<PlanSchedules> entities) {

		List<BasicElement> basicElements = entities.stream().map(element -> (BasicElement) element)
				.collect(Collectors.toList());

		basicElements = delFromCache(basicElements);

		return basicElements.stream().map(be -> (PlanSchedules) be).collect(Collectors.toList());
	}
	// endregion dels

	@Override
	public PlanSchedules create(PlanSchedules planSchedules) {

		try {

			return create(planSchedules, null, null);

		} catch (EntityPersistenceException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			return null;
		}
	}

	@Override
	public PlanSchedules create(PlanSchedules planSchedules, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {

		Connection conn = null;
		boolean closeConnection = false;
		boolean commit = false;
		conn = connection;
		PlanSchedules planSchedulesResult = null;
		PlanScheduleState planScheduleState = null;

		try {

			if (conn == null) {

				conn = DataSourceConnection.getInstance().getConnection();
				conn.setAutoCommit(false);
				closeConnection = true;
				commit = true;
				persistOperations = new ArrayList<>();
			}

			// Insert planSchedules in BBDD
			PlanSchedules planSchedulesInserted = null;

			planSchedulesInserted = (PlanSchedules) getObjectDao().insert(conn, planSchedules);

			if (planSchedulesInserted == null) {

				throw new EntityDBException(EntityDBException.Operation.INSERT, planSchedules);
			}

			persistOperations.add(new MutablePair<>(PersistOperation.PUT, planSchedulesInserted));

			planScheduleState = new PlanScheduleState();
			planScheduleState.setPlanScheduleId(planSchedulesInserted.getId());
			planScheduleState.setPlanScheduleStateTypeId(PlanScheduleStateType.PLAN_SCHEDULE_STATE_TYPE_UNKNOWN);

			persistOperations.add(new MutablePair<>(PersistOperation.PUT, planScheduleState));

			if (commit) {
				conn.commit();

				persistOperations(persistOperations);
				
				planSchedulesResult = get(planSchedulesInserted.getId(), false);
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
			throw new EntityPersistenceException(EntityPersistenceException.Operation.CREATE, planSchedules);

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

		return planSchedulesResult;
	}

	@Override
	public PlanSchedules clone(PlanSchedules entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PlanSchedules clone(PlanSchedules entity, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PlanSchedules update(PlanSchedules planSchedules) {

		try {

			return update(planSchedules, null, null);

		} catch (EntityPersistenceException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			return null;
		}
	}

	@Override
	public PlanSchedules update(PlanSchedules planSchedules, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {

		Connection conn = null;
		boolean closeConnection = false;
		boolean commit = false;
		conn = connection;
		PlanSchedules planSchedulesResult = null;
		PlanSchedules planSchedulesAux = null;

		try {

			if (planSchedules == null || planSchedules.getId() == null) {

				throw new NullPointerException("PlanSchedules is null");
			}

			planSchedulesAux = get(planSchedules.getId(), false);

			if (planSchedulesAux == null) {

				throw new NullPointerException("PlanSchedules not found");
			}

			if (planSchedules.getAlias() != null)
				planSchedulesAux.setAlias(planSchedules.getAlias());
			if (planSchedules.getDescription() != null)
				planSchedulesAux.setDescription(planSchedules.getDescription());
			if (planSchedules.getPlanId() != null)
				planSchedulesAux.setPlanId(planSchedules.getPlanId());
			if (planSchedules.getCronExpression() != null)
				planSchedulesAux.setCronExpression(planSchedules.getCronExpression());

			if (conn == null) {

				conn = DataSourceConnection.getInstance().getConnection();
				conn.setAutoCommit(false);
				closeConnection = true;
				commit = true;
				persistOperations = new ArrayList<>();
			}

			// Update PlanSchedules in BBDD
			PlanSchedules planSchedulesUpdated = null;

			planSchedulesUpdated = (PlanSchedules) getObjectDao().update(conn, planSchedulesAux);

			if (planSchedulesUpdated == null) {

				throw new EntityDBException(EntityDBException.Operation.UPDATE, planSchedulesAux);
			}

			persistOperations.add(new MutablePair<>(PersistOperation.PUT, planSchedulesUpdated));

			if (commit) {

				conn.commit();

				persistOperations(persistOperations);
			}

			planSchedulesResult = planSchedulesUpdated;

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
			throw new EntityPersistenceException(EntityPersistenceException.Operation.UPDATE, planSchedules);

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

		return planSchedulesResult;
	}

	@Override
	public PlanSchedules delete(PlanSchedules planSchedules) {

		try {

			return delete(planSchedules, null, null);

		} catch (EntityPersistenceException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			return null;
		}
	}

	@Override
	public PlanSchedules delete(PlanSchedules planSchedules, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {

		Connection conn = null;
		boolean closeConnection = false;
		boolean commit = false;
		conn = connection;
		PlanSchedules planSchedulesResult = null;
		PlanSchedules planSchedulesAux = null;
		PlanScheduleState planScheduleState = null;

		try {

			if (planSchedules == null || planSchedules.getId() == null) {

				throw new NullPointerException("PlanSchedules is null");
			}

			planSchedulesAux = get(planSchedules.getId(), false);

			if (planSchedulesAux == null) {

				throw new NullPointerException("PlanSchedules not found");
			}

			if (conn == null) {

				conn = DataSourceConnection.getInstance().getConnection();
				conn.setAutoCommit(false);
				closeConnection = true;
				commit = true;
				persistOperations = new ArrayList<>();
			}

			// Delete planSchedules in BBDD
			PlanSchedules planSchedulesDeleted = null;

			planSchedulesDeleted = (PlanSchedules) getObjectDao().delete(conn, planSchedulesAux);

			if (planSchedulesDeleted == null) {

				throw new EntityDBException(EntityDBException.Operation.DELETE, planSchedulesAux);
			}

			persistOperations.add(new MutablePair<>(PersistOperation.DELETE, planSchedulesAux));

			planScheduleState = new PlanScheduleState();
			planScheduleState.setPlanScheduleId(planSchedulesAux.getId());

			persistOperations.add(new MutablePair<>(PersistOperation.DELETE, planScheduleState));

			if (commit) {

				conn.commit();

				// Update cache
				persistOperations(persistOperations);
			}

			planSchedulesResult = planSchedulesDeleted;

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
			throw new EntityPersistenceException(EntityPersistenceException.Operation.DELETE, planSchedules);

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

		return planSchedulesResult;
	}
}
