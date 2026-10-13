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
import com.revenga.rits.back.data.core.model.PlanOrderCommandElement;
import com.revenga.rits.back.data.core.model.PlanOrderCondition;
import com.revenga.rits.back.data.core.model.PlanOrderElementTypeState;
import com.revenga.rits.back.data.core.model.PlanOrderElementValue;
import com.revenga.rits.back.data.core.model.PlanOrderState;
import com.revenga.rits.back.data.core.model.PlanOrderStateType;
import com.revenga.rits.back.data.core.model.PlanOrderValue;
import com.revenga.rits.back.entities.lib.exception.EntityDBException;
import com.revenga.rits.back.entities.lib.exception.EntityPersistenceException;
import com.revenga.rits.back.entities.lib.repository.entity.EntityCacheable;
import com.revenga.rits.back.entities.lib.repository.entity.EntityPersistent;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepositoryOptions;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class PlanOrderRepository extends EntityRepository
		implements EntityCacheable<PlanOrder>, EntityPersistent<PlanOrder> {

	// region Constructors
	public PlanOrderRepository() {

		super(PlanOrder.class);
	}

	public PlanOrderRepository(EntityRepositoryOptions options) {

		super(PlanOrder.class, options);
	}

	
	public PlanOrderRepository(Boolean cacheable) {

		super(PlanOrder.class, cacheable);
	}

	public PlanOrderRepository(Boolean cacheable, Long cacheRetentionTime) {

		super(PlanOrder.class, cacheable, cacheRetentionTime);
	}

	public PlanOrderRepository(Boolean cacheable, List<EntityRepository> repositories) {

		super(PlanOrder.class, cacheable, repositories);
	}

	public PlanOrderRepository(Boolean cacheable, Long cacheRetentionTime, List<EntityRepository> repositories) {

		super(PlanOrder.class, cacheable, cacheRetentionTime, repositories);
	}


	// endregion Constructors

	// region getters
	public PlanOrder get(Long planId, Long id, Boolean fillObject) {

		return (PlanOrder) getFromCache(new String[] { String.valueOf(planId), String.valueOf(id) }, fillObject);
	}

	public List<PlanOrder> getsByPlan(Long planId, Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(new String[] { String.valueOf(planId), null }, fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(be -> (PlanOrder) be).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}

	@Override
	public List<PlanOrder> getAll(Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(be -> (PlanOrder) be).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}
	// endregion getters

	// region puts
	@Override
	public PlanOrder put(PlanOrder entity) {

		return (PlanOrder) putFromCache(entity);
	}

	@Override
	public List<PlanOrder> put(List<PlanOrder> entities) {

		List<BasicElement> basicElements = entities.stream().map(element -> (BasicElement) element)
				.collect(Collectors.toList());

		basicElements = putsFromCache(basicElements);

		return basicElements.stream().map(be -> (PlanOrder) be).collect(Collectors.toList());
	}
	// endregion puts

	// region dels
	@Override
	public PlanOrder del(PlanOrder entity) {

		return (PlanOrder) delFromCache(entity);

	}

	@Override
	public List<PlanOrder> del(List<PlanOrder> entities) {

		List<BasicElement> basicElements = entities.stream().map(element -> (BasicElement) element)
				.collect(Collectors.toList());

		basicElements = delFromCache(basicElements);

		return basicElements.stream().map(be -> (PlanOrder) be).collect(Collectors.toList());
	}
	// endregion dels

	@Override
	public PlanOrder create(PlanOrder planOrder) {
		try {

			return create(planOrder, null, null);

		} catch (EntityPersistenceException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			return null;
		}
	}

	@Override
	public PlanOrder create(PlanOrder planOrder, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {

		Connection conn = null;
		boolean closeConnection = false;
		boolean commit = false;
		conn = connection;
		PlanOrder planOrderResult = null;
		PlanOrderState planOrderState = null;

		try {

			if (conn == null) {

				conn = DataSourceConnection.getInstance().getConnection();
				conn.setAutoCommit(false);
				closeConnection = true;
				commit = true;
				persistOperations = new ArrayList<>();
			}

			// Insert planOrder in BBDD
			PlanOrder planOrderToInsert = new PlanOrder();

			planOrderToInsert.setPlanId(planOrder.getPlanId());
			planOrderToInsert.setPlanOrderTypeId(planOrder.getPlanOrderTypeId());
			planOrderToInsert.setPlanOrderOrder(planOrder.getPlanOrderOrder());
			planOrderToInsert.setNextPlanId(planOrder.getNextPlanId());
			planOrderToInsert.setNextPlanOrderId(planOrder.getNextPlanOrderId());

			planOrderToInsert.setAlias(planOrder.getAlias());
			planOrderToInsert.setDescription(planOrder.getDescription());

			planOrderToInsert = (PlanOrder) getObjectDao().insert(conn, planOrderToInsert);

			if (planOrderToInsert == null) {

				throw new EntityDBException(EntityDBException.Operation.INSERT, planOrder);
			}

			persistOperations.add(new MutablePair<>(PersistOperation.PUT, planOrderToInsert));

			// Insert all planOrderValues
			if (!CollectionUtils.isEmpty(planOrder.getPlanOrderValues())
					&& !CollectionUtils.isEmpty(getRepositories())) {

				PlanOrderValueRepository entityRepository = (PlanOrderValueRepository) getRepositories().stream()
						.filter(repository -> repository.getClazz().equals(PlanOrderValue.class)).findFirst()
						.orElse(null);

				if (entityRepository != null) {

					for (PlanOrderValue planOrderValue : planOrder.getPlanOrderValues()) {

						planOrderValue.setPlanId(planOrderToInsert.getPlanId());
						planOrderValue.setPlanOrderId(planOrderToInsert.getId());
						planOrderValue.setPlanOrderTypeId(planOrderToInsert.getPlanOrderTypeId());
						entityRepository.create(planOrderValue, conn, persistOperations);
					}
				}

			}

			// Insert all planOrderElementValues
			if (!CollectionUtils.isEmpty(planOrder.getPlanOrderElementValues())
					&& !CollectionUtils.isEmpty(getRepositories())) {

				PlanOrderElementValueRepository entityRepository = (PlanOrderElementValueRepository) getRepositories()
						.stream().filter(repository -> repository.getClazz().equals(PlanOrderElementValue.class))
						.findFirst().orElse(null);

				if (entityRepository != null) {

					for (PlanOrderElementValue planOrderElementValue : planOrder.getPlanOrderElementValues()) {

						planOrderElementValue.setPlanId(planOrderToInsert.getPlanId());
						planOrderElementValue.setPlanOrderId(planOrderToInsert.getId());
						entityRepository.create(planOrderElementValue, conn, persistOperations);
					}
				}

			}

			// Insert all planOrderElementTypeStates
			if (!CollectionUtils.isEmpty(planOrder.getPlanOrderElementTypeStates())
					&& !CollectionUtils.isEmpty(getRepositories())) {

				PlanOrderElementTypeStateRepository entityRepository = (PlanOrderElementTypeStateRepository) getRepositories()
						.stream().filter(repository -> repository.getClazz().equals(PlanOrderElementTypeState.class))
						.findFirst().orElse(null);

				if (entityRepository != null) {

					for (PlanOrderElementTypeState planOrderElementTypeState : planOrder
							.getPlanOrderElementTypeStates()) {

						planOrderElementTypeState.setPlanId(planOrderToInsert.getPlanId());
						planOrderElementTypeState.setPlanOrderId(planOrderToInsert.getId());
						entityRepository.create(planOrderElementTypeState, conn, persistOperations);
					}
				}

			}

			// Insert all planOrderConditions
			if (!CollectionUtils.isEmpty(planOrder.getPlanOrderConditions())
					&& !CollectionUtils.isEmpty(getRepositories())) {

				PlanOrderConditionRepository entityRepository = (PlanOrderConditionRepository) getRepositories()
						.stream().filter(repository -> repository.getClazz().equals(PlanOrderCondition.class))
						.findFirst().orElse(null);

				if (entityRepository != null) {

					for (PlanOrderCondition planOrderCondition : planOrder.getPlanOrderConditions()) {

						planOrderCondition.setPlanId(planOrderToInsert.getPlanId());
						planOrderCondition.setPlanOrderId(planOrderToInsert.getId());
						entityRepository.create(planOrderCondition, conn, persistOperations);
					}
				}

			}

			// Insert all planOrderCommandElement
			if (!ObjectUtils.isEmpty(planOrder.getPlanOrderCommandElement())
					&& !CollectionUtils.isEmpty(getRepositories())) {

				PlanOrderCommandElementRepository entityRepository = (PlanOrderCommandElementRepository) getRepositories()
						.stream().filter(repository -> repository.getClazz().equals(PlanOrderCommandElement.class))
						.findFirst().orElse(null);

				if (entityRepository != null) {

					PlanOrderCommandElement planOrderCommandElement = planOrder.getPlanOrderCommandElement();

					planOrderCommandElement.setPlanId(planOrderToInsert.getPlanId());
					planOrderCommandElement.setPlanOrderId(planOrderToInsert.getId());

					entityRepository.create(planOrderCommandElement, conn, persistOperations);
				}

			}

			planOrderState = new PlanOrderState();
			planOrderState.setPlanId(planOrderToInsert.getPlanId());
			planOrderState.setPlanOrderId(planOrderToInsert.getId());
			planOrderState.setPlanOrderStateTypeId(PlanOrderStateType.PLAN_ORDER_STATE_TYPE_UNKNOWN);

			persistOperations.add(new MutablePair<>(PersistOperation.PUT, planOrderState));

			if (commit) {
				conn.commit();

				persistOperations(persistOperations);
			}

			planOrderResult = get(planOrderToInsert.getPlanId(), planOrderToInsert.getId(), false);

		} catch (EntityDBException | SQLException | EntityPersistenceException | IOException e) {

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
			throw new EntityPersistenceException(EntityPersistenceException.Operation.CREATE, planOrder);

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

		return planOrderResult;

	}

	@Override
	public PlanOrder clone(PlanOrder entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PlanOrder clone(PlanOrder entity, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PlanOrder update(PlanOrder planOrder) {
		try {

			return update(planOrder, null, null);

		} catch (EntityPersistenceException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			return null;
		}
	}

	@Override
	public PlanOrder update(PlanOrder planOrder, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {

		Connection conn = null;
		boolean closeConnection = false;
		boolean commit = false;
		conn = connection;
		PlanOrder planOrderResult = null;
		PlanOrder planOrderAux = null;
		PlanOrderCommandElement planOrderCommandElement;
		List<PlanOrderElementValue> planOrderElementValues = null;
		List<PlanOrderElementTypeState> planOrderElementTypeStates = null;
		List<PlanOrderCondition> planOrderConditions = null;

		try {

			if (planOrder == null || planOrder.getId() == null) {

				throw new NullPointerException("PlanOrder is null");
			}

			planOrderAux = get(planOrder.getPlanId(), planOrder.getId(), false);

			if (planOrderAux == null) {

				throw new NullPointerException("PlanOrder not found");
			}

			if (planOrder.getAlias() != null) {

				planOrderAux.setAlias(planOrder.getAlias());
			}

			if (planOrder.getDescription() != null) {

				planOrderAux.setDescription(planOrder.getDescription());
			}

			if (planOrder.getNextPlanId() != null) {

				planOrderAux.setNextPlanId(planOrder.getNextPlanId());
			}

			if (planOrder.getNextPlanOrderId() != null) {

				planOrderAux.setNextPlanOrderId(planOrder.getNextPlanOrderId());
			}

			if (planOrder.getPlanOrderOrder() != null) {

				planOrderAux.setPlanOrderOrder(planOrder.getPlanOrderOrder());
			}

			if (conn == null) {

				conn = DataSourceConnection.getInstance().getConnection();
				conn.setAutoCommit(false);
				closeConnection = true;
				commit = true;
				persistOperations = new ArrayList<>();
			}

			// Update plaOrderValues
			if (!CollectionUtils.isEmpty(planOrder.getPlanOrderValues())
					&& !CollectionUtils.isEmpty(getRepositories())) {

				PlanOrderValueRepository entityRepository = (PlanOrderValueRepository) getRepositories().stream()
						.filter(repository -> repository.getClazz().equals(PlanOrderValue.class)).findFirst()
						.orElse(null);

				if (entityRepository != null) {

					for (PlanOrderValue planOrderValue : planOrder.getPlanOrderValues()) {

						entityRepository.update(planOrderValue, conn, persistOperations);
					}

				}
			}

			// Update all planOrderElementValues
			if (planOrder.getPlanOrderElementValues() != null && !CollectionUtils.isEmpty(getRepositories())) {

				// Delete all planOrderElementValues
				PlanOrderElementValueRepository entityRepository = (PlanOrderElementValueRepository) getRepositories()
						.stream().filter(repository -> repository.getClazz().equals(PlanOrderElementValue.class))
						.findFirst().orElse(null);

				if (entityRepository != null) {

					planOrderElementValues = entityRepository.getsByPlanOrder(planOrder.getPlanId(), planOrder.getId(),
							false);

					if (!CollectionUtils.isEmpty(planOrderElementValues)) {

						for (PlanOrderElementValue planOrderElementValueDeleted : planOrderElementValues) {

							entityRepository.delete(planOrderElementValueDeleted, conn, persistOperations);
						}
					}

					if (!CollectionUtils.isEmpty(planOrder.getPlanOrderElementValues())) {

						for (PlanOrderElementValue planOrderElementValueCreated : planOrder
								.getPlanOrderElementValues()) {

							planOrderElementValueCreated.setPlanId(planOrder.getPlanId());
							planOrderElementValueCreated.setPlanOrderId(planOrder.getId());
							entityRepository.create(planOrderElementValueCreated, conn, persistOperations);
						}

					}
				}
			}

			// Update all planOrderElementTypeStates
			if (planOrder.getPlanOrderElementTypeStates() != null && !CollectionUtils.isEmpty(getRepositories())) {

				// Delete all planOrderElementTypeStates
				PlanOrderElementTypeStateRepository entityRepository = (PlanOrderElementTypeStateRepository) getRepositories()
						.stream().filter(repository -> repository.getClazz().equals(PlanOrderElementTypeState.class))
						.findFirst().orElse(null);

				if (entityRepository != null) {

					planOrderElementTypeStates = entityRepository.getsByPlanOrder(planOrder.getPlanId(),
							planOrder.getId(), false);

					if (!CollectionUtils.isEmpty(planOrderElementTypeStates)) {

						for (PlanOrderElementTypeState planOrderElementTypeStateDeleted : planOrderElementTypeStates) {

							entityRepository.delete(planOrderElementTypeStateDeleted, conn, persistOperations);
						}
					}

					if (!CollectionUtils.isEmpty(planOrder.getPlanOrderElementTypeStates())) {

						for (PlanOrderElementTypeState planOrderElementTypeStateCreated : planOrder
								.getPlanOrderElementTypeStates()) {

							planOrderElementTypeStateCreated.setPlanId(planOrder.getPlanId());
							planOrderElementTypeStateCreated.setPlanOrderId(planOrder.getId());
							entityRepository.create(planOrderElementTypeStateCreated, conn, persistOperations);
						}

					}
				}
			}

			// Update all planOrderConditions
			if (planOrder.getPlanOrderConditions() != null && !CollectionUtils.isEmpty(getRepositories())) {

				// Delete all planOrderConditions
				PlanOrderConditionRepository entityRepository = (PlanOrderConditionRepository) getRepositories()
						.stream().filter(repository -> repository.getClazz().equals(PlanOrderCondition.class))
						.findFirst().orElse(null);

				if (entityRepository != null) {

					planOrderConditions = entityRepository.getsByPlanOrder(planOrder.getPlanId(), planOrder.getId(),
							false);

					if (!CollectionUtils.isEmpty(planOrderConditions)) {

						for (PlanOrderCondition planOrderConditionDeleted : planOrderConditions) {

							entityRepository.delete(planOrderConditionDeleted, conn, persistOperations);
						}
					}

					if (!CollectionUtils.isEmpty(planOrder.getPlanOrderConditions())) {

						for (PlanOrderCondition planOrderConditionCreated : planOrder.getPlanOrderConditions()) {

							planOrderConditionCreated.setPlanId(planOrder.getPlanId());
							planOrderConditionCreated.setPlanOrderId(planOrder.getId());
							entityRepository.create(planOrderConditionCreated, conn, persistOperations);
						}

					}
				}
			}

			if (!ObjectUtils.isEmpty(planOrder.getPlanOrderCommandElement())
					&& (!CollectionUtils.isEmpty(getRepositories()))) {

				PlanOrderCommandElementRepository entityRepository = (PlanOrderCommandElementRepository) getRepositories()
						.stream().filter(repository -> repository.getClazz().equals(PlanOrderCommandElement.class))
						.findFirst().orElse(null);

				if (entityRepository != null) {

					planOrderCommandElement = entityRepository.get(planOrder.getPlanId(), planOrder.getId(), false);

					// Delete planOrderCommandElement
					if (planOrderCommandElement != null) {

						entityRepository.delete(planOrderCommandElement, conn, persistOperations);
					}

					entityRepository.create(planOrder.getPlanOrderCommandElement(), conn, persistOperations);
				}

			}

			// Update planOrder in BBDD
			PlanOrder planOrderUpdated = null;

			planOrderUpdated = (PlanOrder) getObjectDao().update(conn, planOrderAux);
			if (planOrderUpdated == null) {

				throw new EntityDBException(EntityDBException.Operation.UPDATE, planOrderAux);
			}

			persistOperations.add(new MutablePair<>(PersistOperation.PUT, planOrderUpdated));

			if (commit) {

				conn.commit();

				persistOperations(persistOperations);
			}

			planOrderResult = planOrderUpdated;

		} catch (EntityDBException | SQLException | NullPointerException | EntityPersistenceException | IOException e) {

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
			throw new EntityPersistenceException(EntityPersistenceException.Operation.UPDATE, planOrder);

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

		return planOrderResult;
	}

	@Override
	public PlanOrder delete(PlanOrder planOrder) {
		try {

			return delete(planOrder, null, null);

		} catch (EntityPersistenceException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			return null;
		}
	}

	@Override
	public PlanOrder delete(PlanOrder planOrder, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {

		Connection conn = null;
		boolean closeConnection = false;
		boolean commit = false;
		conn = connection;
		PlanOrder planOrderResult = null;
		PlanOrder planOrderAux = null;
		List<PlanOrderValue> planOrderValues = null;
		List<PlanOrderElementValue> planOrderElementValues = null;
		List<PlanOrderElementTypeState> planOrderElementTypeStates = null;
		List<PlanOrderCondition> planOrderConditions = null;
		PlanOrderCommandElement planOrderCommandElement = null;
		PlanOrderState planOrderState = null;

		try {

			if (planOrder == null || planOrder.getId() == null) {

				throw new NullPointerException("PlanOrder is null");
			}

			planOrderAux = get(planOrder.getPlanId(), planOrder.getId(), false);

			if (planOrderAux == null) {

				throw new NullPointerException("PlanOrder not found");
			}

			if (conn == null) {

				conn = DataSourceConnection.getInstance().getConnection();
				conn.setAutoCommit(false);
				closeConnection = true;
				commit = true;
				persistOperations = new ArrayList<>();
			}

			if (!CollectionUtils.isEmpty(getRepositories())) {

				PlanOrderCommandElementRepository entityRepository = (PlanOrderCommandElementRepository) getRepositories()
						.stream().filter(repository -> repository.getClazz().equals(PlanOrderCommandElement.class))
						.findFirst().orElse(null);

				if (entityRepository != null) {

					planOrderCommandElement = entityRepository.get(planOrder.getPlanId(), planOrder.getId(), false);

					if (!ObjectUtils.isEmpty(planOrderCommandElement)) {

						entityRepository.delete(planOrderCommandElement, conn, persistOperations);
					}
				}
			}

			// Delete all planOrderValues
			if (!CollectionUtils.isEmpty(getRepositories())) {

				PlanOrderValueRepository entityRepository = (PlanOrderValueRepository) getRepositories().stream()
						.filter(repository -> repository.getClazz().equals(PlanOrderValue.class)).findFirst()
						.orElse(null);

				if (entityRepository != null) {

					planOrderValues = entityRepository.getsByPlanOrder(planOrderAux.getPlanId(), planOrderAux.getId(),
							planOrderAux.getPlanOrderTypeId(), false);

					if (!CollectionUtils.isEmpty(planOrderValues)) {

						for (PlanOrderValue planOrderValue : planOrderValues) {

							entityRepository.delete(planOrderValue, conn, persistOperations);
						}
					}
				}
			}

			// Delete all planOrderElementValues
			if (!CollectionUtils.isEmpty(getRepositories())) {

				PlanOrderElementValueRepository entityRepository = (PlanOrderElementValueRepository) getRepositories()
						.stream().filter(repository -> repository.getClazz().equals(PlanOrderElementValue.class))
						.findFirst().orElse(null);

				if (entityRepository != null) {

					planOrderElementValues = entityRepository.getsByPlanOrder(planOrderAux.getPlanId(),
							planOrderAux.getId(), false);

					if (!CollectionUtils.isEmpty(planOrderElementValues)) {

						for (PlanOrderElementValue planOrderElementValue : planOrderElementValues) {

							entityRepository.delete(planOrderElementValue, conn, persistOperations);
						}
					}
				}
			}

			// Delete all planOrderElementTypeStates
			if (!CollectionUtils.isEmpty(getRepositories())) {

				PlanOrderElementTypeStateRepository entityRepository = (PlanOrderElementTypeStateRepository) getRepositories()
						.stream().filter(repository -> repository.getClazz().equals(PlanOrderElementTypeState.class))
						.findFirst().orElse(null);

				if (entityRepository != null) {

					planOrderElementTypeStates = entityRepository.getsByPlanOrder(planOrderAux.getPlanId(),
							planOrderAux.getId(), false);

					if (!CollectionUtils.isEmpty(planOrderElementTypeStates)) {

						for (PlanOrderElementTypeState planOrderElementTypeState : planOrderElementTypeStates) {

							entityRepository.delete(planOrderElementTypeState, conn, persistOperations);
						}
					}
				}
			}

			// Delete all planOrderConditions
			if (!CollectionUtils.isEmpty(getRepositories())) {

				PlanOrderConditionRepository entityRepository = (PlanOrderConditionRepository) getRepositories()
						.stream().filter(repository -> repository.getClazz().equals(PlanOrderCondition.class))
						.findFirst().orElse(null);

				if (entityRepository != null) {

					planOrderConditions = entityRepository.getsByPlanOrder(planOrderAux.getPlanId(),
							planOrderAux.getId(), false);

					if (!CollectionUtils.isEmpty(planOrderConditions)) {

						for (PlanOrderCondition planOrderCondition : planOrderConditions) {

							entityRepository.delete(planOrderCondition, conn, persistOperations);
						}
					}
				}
			}

			// Delete planOrder in BBDD
			PlanOrder planOrderDeleted = null;

			planOrderDeleted = (PlanOrder) getObjectDao().delete(conn, planOrderAux);

			if (planOrderDeleted == null) {

				throw new EntityDBException(EntityDBException.Operation.DELETE, planOrderAux);
			}

			persistOperations.add(new MutablePair<>(PersistOperation.DELETE, planOrderAux));

			planOrderState = new PlanOrderState();
			planOrderState.setPlanId(planOrderAux.getPlanId());
			planOrderState.setPlanOrderId(planOrderAux.getId());

			persistOperations.add(new MutablePair<>(PersistOperation.DELETE, planOrderState));

			if (commit) {

				conn.commit();

				// Update cache
				persistOperations(persistOperations);
			}

			planOrderResult = planOrderDeleted;

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
			throw new EntityPersistenceException(EntityPersistenceException.Operation.DELETE, planOrder);

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

		return planOrderResult;
	}
}
