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
import com.revenga.rits.back.data.core.model.Plan;
import com.revenga.rits.back.data.core.model.PlanOrder;
import com.revenga.rits.back.data.core.model.PlanState;
import com.revenga.rits.back.data.core.model.PlanStateType;
import com.revenga.rits.back.entities.lib.exception.EntityDBException;
import com.revenga.rits.back.entities.lib.exception.EntityPersistenceException;
import com.revenga.rits.back.entities.lib.repository.entity.EntityCacheable;
import com.revenga.rits.back.entities.lib.repository.entity.EntityPersistent;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository.PersistOperation;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepositoryOptions;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class PlanRepository extends EntityRepository implements EntityCacheable<Plan>, EntityPersistent<Plan> {

	// region Constructors
	public PlanRepository() {

		super(Plan.class);
	}

	public PlanRepository(EntityRepositoryOptions options) {

		super(Plan.class, options);
	}
	
	public PlanRepository(Boolean cacheable) {

		super(Plan.class, cacheable);
	}

	public PlanRepository(Boolean cacheable, Long cacheRetentionTime) {

		super(Plan.class, cacheable, cacheRetentionTime);
	}

	public PlanRepository(Boolean cacheable, List<EntityRepository> repositories) {

		super(Plan.class, cacheable, repositories);
	}

	public PlanRepository(Boolean cacheable, Long cacheRetentionTime, List<EntityRepository> repositories) {

		super(Plan.class, cacheable, cacheRetentionTime, repositories);
	}

	// endregion Constructors

	// region getters
	public Plan get(Long id, Boolean fillObject) {

		return (Plan) getFromCache(new String[] { String.valueOf(id) }, fillObject);
	}

	@Override
	public List<Plan> getAll(Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(Plan.class::cast).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}
	// endregion getters

	// region puts
	@Override
	public Plan put(Plan entity) {

		return (Plan) putFromCache(entity);
	}

	@Override
	public List<Plan> put(List<Plan> entities) {

		List<BasicElement> basicElements = entities.stream().map(BasicElement.class::cast)
				.collect(Collectors.toList());

		basicElements = putsFromCache(basicElements);

		return basicElements.stream().map(Plan.class::cast).collect(Collectors.toList());
	}
	// endregion puts

	// region dels
	@Override
	public Plan del(Plan entity) {

		return (Plan) delFromCache(entity);

	}

	@Override
	public List<Plan> del(List<Plan> entities) {

		List<BasicElement> basicElements = entities.stream().map(BasicElement.class::cast)
				.collect(Collectors.toList());

		basicElements = delFromCache(basicElements);

		return basicElements.stream().map(Plan.class::cast).collect(Collectors.toList());
	}
	// endregion dels

	@Override
	public Plan create(Plan plan) {

		try {

			return create(plan, null, null);

		} catch (EntityPersistenceException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			return null;
		}
	}

	@Override
	public Plan create(Plan plan, Connection connection, List<Pair<PersistOperation, BasicElement>> persistOperations)
			throws EntityPersistenceException {

		Connection conn = null;
		boolean closeConnection = false;
		boolean commit = false;
		conn = connection;
		Plan planResult = null;
		PlanState planState = null;

		try {

			if (conn == null) {

				conn = DataSourceConnection.getInstance().getConnection();
				conn.setAutoCommit(false);
				closeConnection = true;
				commit = true;
				persistOperations = new ArrayList<>();
			}

			// Insert plan in BBDD
			Plan planInserted = null;

			planInserted = (Plan) getObjectDao().insert(conn, plan);

			if (planInserted == null) {

				throw new EntityDBException(EntityDBException.Operation.INSERT, plan);
			}

			persistOperations.add(new MutablePair<>(PersistOperation.PUT, planInserted));

			planState = new PlanState();
			planState.setPlanId(planInserted.getId());
			planState.setPlanStateTypeId(PlanStateType.PLAN_STATE_TYPE_UNKNOWN);

			persistOperations.add(new MutablePair<>(PersistOperation.PUT, planState));

			if (commit) {
				conn.commit();

				persistOperations(persistOperations);
			}

			planResult = get(planInserted.getId(), false);

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
			throw new EntityPersistenceException(EntityPersistenceException.Operation.CREATE, plan);

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

		return planResult;

	}

	@Override
	public Plan clone(Plan entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Plan clone(Plan entity, Connection connection, List<Pair<PersistOperation, BasicElement>> persistOperations)
			throws EntityPersistenceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Plan update(Plan plan) {
		try {

			return update(plan, null, null);

		} catch (EntityPersistenceException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			return null;
		}
	}

	@Override
	public Plan update(Plan plan, Connection connection, List<Pair<PersistOperation, BasicElement>> persistOperations)
			throws EntityPersistenceException {

		Connection conn = null;
		boolean closeConnection = false;
		boolean commit = false;
		conn = connection;
		Plan planResult = null;
		Plan planAux = null;

		try {

			if (plan == null || plan.getId() == null) {

				throw new NullPointerException("Plan is null");
			}

			planAux = get(plan.getId(), false);

			if (planAux == null) {

				throw new NullPointerException("Plan not found");
			}

			if (plan.getAlias() != null)
				planAux.setAlias(plan.getAlias());
			if (plan.getDescription() != null)
				planAux.setDescription(plan.getDescription());
			if (plan.getPlanModeTypeId() != null)
				planAux.setPlanModeTypeId(plan.getPlanModeTypeId());

			if (conn == null) {

				conn = DataSourceConnection.getInstance().getConnection();
				conn.setAutoCommit(false);
				closeConnection = true;
				commit = true;
				persistOperations = new ArrayList<>();
			}

			// Update plan in BBDD
			Plan planUpdated = null;

			planUpdated = (Plan) getObjectDao().update(conn, planAux);

			if (planUpdated == null) {

				throw new EntityDBException(EntityDBException.Operation.UPDATE, planAux);
			}

			persistOperations.add(new MutablePair<>(PersistOperation.PUT, planUpdated));

			if (commit) {

				conn.commit();

				persistOperations(persistOperations);
			}

			planResult = planUpdated;

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
			throw new EntityPersistenceException(EntityPersistenceException.Operation.UPDATE, plan);

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

		return planResult;

	}

	@Override
	public Plan delete(Plan plan) {

		try {

			return delete(plan, null, null);

		} catch (EntityPersistenceException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			return null;
		}
	}

	@Override
	public Plan delete(Plan plan, Connection connection, List<Pair<PersistOperation, BasicElement>> persistOperations)
			throws EntityPersistenceException {
		Connection conn = null;
		boolean closeConnection = false;
		boolean commit = false;
		conn = connection;
		Plan planResult = null;
		Plan planAux = null;
		List<PlanOrder> planOrders = null;
		PlanState planState = null;

		try {

			if (plan == null || plan.getId() == null) {

				throw new NullPointerException("Plan is null");
			}

			planAux = get(plan.getId(), false);

			if (planAux == null) {

				throw new NullPointerException("Plan not found");
			}

			if (conn == null) {

				conn = DataSourceConnection.getInstance().getConnection();
				conn.setAutoCommit(false);
				closeConnection = true;
				commit = true;
				persistOperations = new ArrayList<>();
			}

			// Delete all planOrders
			if (!CollectionUtils.isEmpty(getRepositories())) {

				PlanOrderRepository entityRepository = (PlanOrderRepository) getRepositories().stream()
						.filter(repository -> repository.getClazz().equals(PlanOrder.class)).findFirst().orElse(null);

				if (entityRepository != null) {

					planOrders = entityRepository.getsByPlan(plan.getId(), false);

					if (!CollectionUtils.isEmpty(planOrders)) {

						for (PlanOrder planOrder : planOrders) {

							entityRepository.delete(planOrder, conn, persistOperations);
						}
					}
				}
			}

			// Delete plan in BBDD
			Plan planDeleted = null;

			planDeleted = (Plan) getObjectDao().delete(conn, planAux);

			if (planDeleted == null) {

				throw new EntityDBException(EntityDBException.Operation.DELETE, planAux);
			}

			persistOperations.add(new MutablePair<>(PersistOperation.DELETE, planAux));

			planState = new PlanState();
			planState.setPlanId(planAux.getId());

			persistOperations.add(new MutablePair<>(PersistOperation.DELETE, planState));

			if (commit) {

				conn.commit();

				// Update cache
				persistOperations(persistOperations);
			}

			planResult = planDeleted;

		} catch (EntityDBException | EntityPersistenceException | SQLException | NullPointerException | IOException e) {

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
			throw new EntityPersistenceException(EntityPersistenceException.Operation.DELETE, plan);

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

		return planResult;
	}
}
