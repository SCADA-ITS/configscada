package com.revenga.rits.back.plan.manager.service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;

import com.revenga.rits.back.cache.core.manager.CacheManager;
import com.revenga.rits.back.data.core.model.CommandElement;
import com.revenga.rits.back.data.core.model.CommandElementType;
import com.revenga.rits.back.data.core.model.CommandElementValue;
import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.ElementState;
import com.revenga.rits.back.data.core.model.ElementTypeParam;
import com.revenga.rits.back.data.core.model.ElementValue;
import com.revenga.rits.back.data.core.model.Plan;
import com.revenga.rits.back.data.core.model.PlanOrder;
import com.revenga.rits.back.data.core.model.PlanOrderCommandElement;
import com.revenga.rits.back.data.core.model.PlanOrderCondition;
import com.revenga.rits.back.data.core.model.PlanOrderElementTypeState;
import com.revenga.rits.back.data.core.model.PlanOrderElementValue;
import com.revenga.rits.back.data.core.model.PlanOrderState;
import com.revenga.rits.back.data.core.model.PlanOrderStateType;
import com.revenga.rits.back.data.core.model.PlanOrderType;
import com.revenga.rits.back.data.core.model.PlanOrderTypeParam;
import com.revenga.rits.back.data.core.model.PlanOrderValue;
import com.revenga.rits.back.data.core.model.PlanScheduleState;
import com.revenga.rits.back.data.core.model.PlanSchedules;
import com.revenga.rits.back.data.core.model.PlanState;
import com.revenga.rits.back.data.core.model.PlanStateType;
import com.revenga.rits.back.data.core.model.PmElementTypeQueue;
import com.revenga.rits.back.data.core.model.PmQueuePriority;
import com.revenga.rits.back.data.core.model.command.Command;
import com.revenga.rits.back.data.core.model.command.SetPlanStatePublishCommand;
import com.revenga.rits.back.data.core.util.ConfigProperty;
import com.revenga.rits.back.entities.lib.helper.EntitiesManagerHelper;
import com.revenga.rits.back.entities.lib.repository.CommandElementRepository;
import com.revenga.rits.back.entities.lib.repository.CommandElementTypeRepository;
import com.revenga.rits.back.entities.lib.repository.CommandElementValueRepository;
import com.revenga.rits.back.entities.lib.repository.ElementRepository;
import com.revenga.rits.back.entities.lib.repository.ElementStateRepository;
import com.revenga.rits.back.entities.lib.repository.ElementTypeParamRepository;
import com.revenga.rits.back.entities.lib.repository.ElementValueRepository;
import com.revenga.rits.back.entities.lib.repository.PlanOrderCommandElementRepository;
import com.revenga.rits.back.entities.lib.repository.PlanOrderConditionRepository;
import com.revenga.rits.back.entities.lib.repository.PlanOrderElementTypeStateRepository;
import com.revenga.rits.back.entities.lib.repository.PlanOrderElementValueRepository;
import com.revenga.rits.back.entities.lib.repository.PlanOrderRepository;
import com.revenga.rits.back.entities.lib.repository.PlanOrderStateRepository;
import com.revenga.rits.back.entities.lib.repository.PlanOrderValueRepository;
import com.revenga.rits.back.entities.lib.repository.PlanRepository;
import com.revenga.rits.back.entities.lib.repository.PlanScheduleStateRepository;
import com.revenga.rits.back.entities.lib.repository.PlanSchedulesRepository;
import com.revenga.rits.back.entities.lib.repository.PlanStateRepository;
import com.revenga.rits.back.entities.lib.repository.PmElementTypeQueueRepository;
import com.revenga.rits.back.entities.lib.repository.PmQueuePriorityRepository;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepositoryOptions;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class EntitiesManager {

	private static final String FILE_PROPERTIES = "application.properties";

	private static final Long CACHE_RETENTION_TIME_CONF = Long.parseLong(
			ConfigProperty.getInstance().getProperty(FILE_PROPERTIES, "entities.manager.retention_time_conf"));
	private static final Long CACHE_RETENTION_TIME_MASTER = Long.parseLong(
			ConfigProperty.getInstance().getProperty(FILE_PROPERTIES, "entities.manager.retention_time_master"));

	private PlanStateRepository planStateRepository;
	private PlanOrderStateRepository planOrderStateRepository;
	private PlanRepository planRepository;
	private PlanOrderRepository planOrderRepository;
	private PlanOrderValueRepository planOrderValueRepository;
	private PlanOrderCommandElementRepository planOrderCommandElementRepository;
	private CommandElementRepository commandElementRepository;
	private CommandElementValueRepository commandElementValueRepository;
	private PlanOrderElementValueRepository planOrderElementValueRepository;
	private PlanOrderElementTypeStateRepository planOrderElementTypeStateRepository;
	private PlanOrderConditionRepository planOrderConditionRepository;

	private PlanSchedulesRepository planSchedulesRepository;
	private PlanScheduleStateRepository planScheduleStateRepository;
	private ElementValueRepository elementValueRepository;
	private ElementStateRepository elementStateRepository;
	private ElementRepository elementRepository;
	private ElementTypeParamRepository elementTypeParamRepository;
	private CommandElementTypeRepository commandElementTypeRepository;
	private PmQueuePriorityRepository pmQueuePriorityRepository;
	private PmElementTypeQueueRepository pmElementTypeQueueRepository;

	private static EntitiesManager instance;

	public static EntitiesManager getInstance() {

		if (instance == null) {

			instance = new EntitiesManager();
		}

		return instance;
	}

	private EntitiesManager() {

		this.planStateRepository = new PlanStateRepository(false);
		this.planOrderStateRepository = new PlanOrderStateRepository(false);
		
		this.planRepository = new PlanRepository(new EntityRepositoryOptions(true,
				CACHE_RETENTION_TIME_CONF, null, false, true, null));
		this.planOrderRepository = new PlanOrderRepository(new EntityRepositoryOptions(true,
				CACHE_RETENTION_TIME_CONF, null, false, true, null));
		this.planOrderValueRepository = new PlanOrderValueRepository(new EntityRepositoryOptions(true,
				CACHE_RETENTION_TIME_CONF, null, false, true, null));
		this.planOrderCommandElementRepository = new PlanOrderCommandElementRepository(new EntityRepositoryOptions(true,
				CACHE_RETENTION_TIME_CONF, null, false, true, null));
		this.commandElementRepository = new CommandElementRepository(new EntityRepositoryOptions(true,
				CACHE_RETENTION_TIME_CONF, null, false, true, null));
		this.commandElementValueRepository = new CommandElementValueRepository(new EntityRepositoryOptions(true,
				CACHE_RETENTION_TIME_CONF, null, false, true, null));
		this.planOrderElementValueRepository = new PlanOrderElementValueRepository(new EntityRepositoryOptions(true,
				CACHE_RETENTION_TIME_CONF, null, false, true, null));
		this.planOrderElementTypeStateRepository = new PlanOrderElementTypeStateRepository(new EntityRepositoryOptions(true,
				CACHE_RETENTION_TIME_CONF, null, false, true, null));
		this.planOrderConditionRepository = new PlanOrderConditionRepository(new EntityRepositoryOptions(true,
				CACHE_RETENTION_TIME_CONF, null, false, true, null));

		this.planSchedulesRepository = new PlanSchedulesRepository(false);
		this.planScheduleStateRepository = new PlanScheduleStateRepository(false);
		this.elementValueRepository = new ElementValueRepository(false);
		this.elementStateRepository = new ElementStateRepository(false);
		this.elementRepository = new ElementRepository(true, CACHE_RETENTION_TIME_CONF);
		this.elementTypeParamRepository = new ElementTypeParamRepository(true, CACHE_RETENTION_TIME_MASTER);
		this.commandElementTypeRepository = new CommandElementTypeRepository(true, CACHE_RETENTION_TIME_MASTER);
		this.pmQueuePriorityRepository = new PmQueuePriorityRepository(true, CACHE_RETENTION_TIME_MASTER);
		this.pmElementTypeQueueRepository = new PmElementTypeQueueRepository(true, CACHE_RETENTION_TIME_MASTER);
		
		EntitiesManagerHelper.cacheRepository(this);
		
		this.planRepository.startSubscription();
		this.planOrderRepository.startSubscription();
		this.planOrderValueRepository.startSubscription();
		this.planOrderCommandElementRepository.startSubscription();
		this.commandElementRepository.startSubscription();
		this.commandElementValueRepository.startSubscription();
		this.planOrderElementValueRepository.startSubscription();
		this.planOrderElementTypeStateRepository.startSubscription();
		this.planOrderConditionRepository.startSubscription();
	}

	private void fillPlanOrderLoop(PlanOrder planOrder) {

		if (planOrder != null) {

			PlanOrderValue numberTime = planOrderValueRepository.get(planOrder.getPlanId(), planOrder.getId(),
					planOrder.getPlanOrderTypeId(),
					Long.valueOf(PlanOrderTypeParam.PLAN_ORDER_TYPE_LOOP_NUMBER_OF_TIME), false);

			if (numberTime != null) {

				List<PlanOrderValue> planOrderValues = new ArrayList<>();
				planOrderValues.add(numberTime);
				planOrder.setPlanOrderValues(planOrderValues);
			}
		}
	}

	private void fillPlanOrderCondition(PlanOrder planOrder) {

		if (planOrder != null) {

			List<PlanOrderCondition> planOrderConditions = planOrderConditionRepository
					.getsByPlanOrder(planOrder.getPlanId(), planOrder.getId(), false);

			planOrder.setPlanOrderConditions(planOrderConditions);
		}
	}

	private void fillPlanOrderPlayPlan(PlanOrder planOrder) {
		// default implementation ignored
	}

	private void fillPlanOrderEvent(PlanOrder planOrder) {

		if (planOrder != null) {

			PlanOrderValue message = planOrderValueRepository.get(planOrder.getPlanId(), planOrder.getId(),
					planOrder.getPlanOrderTypeId(), Long.valueOf(PlanOrderTypeParam.PLAN_ORDER_TYPE_EVENT_MESSAGE),
					false);

			if (message != null) {

				List<PlanOrderValue> planOrderValues = new ArrayList<>();
				planOrderValues.add(message);
				planOrder.setPlanOrderValues(planOrderValues);
			}
		}
	}
	
	private void fillPlanOrderAutomatism(PlanOrder planOrder) {

		if (planOrder != null) {

			PlanOrderValue groovyPath = planOrderValueRepository.get(planOrder.getPlanId(), planOrder.getId(),
					planOrder.getPlanOrderTypeId(), Long.valueOf(PlanOrderTypeParam.PLAN_ORDER_TYPE_AUTOMATISM_GROOVY_PATH),
					false);
			
			PlanOrderValue automatismParams = planOrderValueRepository.get(planOrder.getPlanId(), planOrder.getId(),
					planOrder.getPlanOrderTypeId(), Long.valueOf(PlanOrderTypeParam.PLAN_ORDER_TYPE_AUTOMATISM_AUTOMATISM_PARAM),
					false);

			if (groovyPath != null && automatismParams != null) {

				List<PlanOrderValue> planOrderValues = new ArrayList<>();
				planOrderValues.add(groovyPath);
				planOrderValues.add(automatismParams);
				planOrder.setPlanOrderValues(planOrderValues);
			}
		}
	}

	private void fillPlanOrderWait(PlanOrder planOrder) {

		if (planOrder != null) {

			PlanOrderValue waitTime = planOrderValueRepository.get(planOrder.getPlanId(), planOrder.getId(),
					planOrder.getPlanOrderTypeId(), Long.valueOf(PlanOrderTypeParam.PLAN_ORDER_TYPE_WAIT_WAITING_TIME),
					false);

			if (waitTime != null) {

				List<PlanOrderValue> planOrderValues = new ArrayList<>();
				planOrderValues.add(waitTime);
				planOrder.setPlanOrderValues(planOrderValues);
			}
		}
	}

	private void fillPlanOrderChangeElementParamConfig(PlanOrder planOrder) {

		if (planOrder != null) {

			List<PlanOrderElementValue> elementValues = planOrderElementValueRepository
					.getsByPlanOrder(planOrder.getPlanId(), planOrder.getId(), false);

			planOrder.setPlanOrderElementValues(elementValues);
		}
	}

	private void fillPlanOrderChangeStatus(PlanOrder planOrder) {

		if (planOrder != null) {

			List<PlanOrderElementTypeState> planOrderElementTypeStates = planOrderElementTypeStateRepository
					.getsByPlanOrder(planOrder.getPlanId(), planOrder.getId(), false);

			planOrder.setPlanOrderElementTypeStates(planOrderElementTypeStates);
		}
	}

	private void fillPlanOrderConfirmation(PlanOrder planOrder) {
		// default implementation ignored
	}

	private void fillPlanOrderSignalling(PlanOrder planOrder) {

		if (planOrder != null) {

			PlanOrderCommandElement planOrderCommandElement = planOrderCommandElementRepository
					.get(planOrder.getPlanId(), planOrder.getId(), false);

			if (planOrderCommandElement != null) {

				CommandElement commandElement = commandElementRepository.get(
						planOrderCommandElement.getCommandElementTypeId(), planOrderCommandElement.getElementTypeId(),
						planOrderCommandElement.getCommandElementId(), false);

				if (commandElement != null) {

					List<CommandElementValue> commandElementValues = commandElementValueRepository.getsByCommandElement(
							commandElement.getCommandElementTypeId(), commandElement.getElementTypeId(),
							commandElement.getId(), false);

					if (!CollectionUtils.isEmpty(commandElementValues)) {

						commandElement.setCommandElementValues(commandElementValues);
					}

					planOrderCommandElement.setCommandElement(commandElement);
				}

				planOrder.setPlanOrderCommandElement(planOrderCommandElement);
			}
		}
	}

	private void fillPlanOrder(PlanOrder planOrder) {

		if (planOrder != null) {

			if (Long.valueOf(PlanOrderType.PLAN_ORDER_TYPE_SIGNALLING).equals(planOrder.getPlanOrderTypeId())) {

				fillPlanOrderSignalling(planOrder);

			} else if (Long.valueOf(PlanOrderType.PLAN_ORDER_TYPE_CONFIRMATION)
					.equals(planOrder.getPlanOrderTypeId())) {

				fillPlanOrderConfirmation(planOrder);

			} else if (Long.valueOf(PlanOrderType.PLAN_ORDER_TYPE_CHANGE_STATUS)
					.equals(planOrder.getPlanOrderTypeId())) {

				fillPlanOrderChangeStatus(planOrder);

			} else if (Long.valueOf(PlanOrderType.PLAN_ORDER_TYPE_CHANGE_ELEMENT_PARAM_CONFIG)
					.equals(planOrder.getPlanOrderTypeId())) {

				fillPlanOrderChangeElementParamConfig(planOrder);

			} else if (Long.valueOf(PlanOrderType.PLAN_ORDER_TYPE_WAIT).equals(planOrder.getPlanOrderTypeId())) {

				fillPlanOrderWait(planOrder);

			} else if (Long.valueOf(PlanOrderType.PLAN_ORDER_TYPE_PLAY_PLAN).equals(planOrder.getPlanOrderTypeId())) {

				fillPlanOrderPlayPlan(planOrder);

			} else if (Long.valueOf(PlanOrderType.PLAN_ORDER_TYPE_CONDITION).equals(planOrder.getPlanOrderTypeId())) {

				fillPlanOrderCondition(planOrder);

			} else if (Long.valueOf(PlanOrderType.PLAN_ORDER_TYPE_LOOP).equals(planOrder.getPlanOrderTypeId())) {

				fillPlanOrderLoop(planOrder);

			} else if (Long.valueOf(PlanOrderType.PLAN_ORDER_TYPE_EVENT).equals(planOrder.getPlanOrderTypeId())) {

				fillPlanOrderEvent(planOrder);

			} else if (Long.valueOf(PlanOrderType.PLAN_ORDER_TYPE_AUTOMATISM).equals(planOrder.getPlanOrderTypeId())) {

				fillPlanOrderAutomatism(planOrder);

			}

		}

	}

	public Long getStateByPlan(Long planId) {

		PlanState planState = planStateRepository.get(planId, false);

		if (planState != null) {

			return planState.getPlanStateTypeId();

		}
		return null;
	}

	public Long getStateByPlanOrder(Long planId, Long planOrderId) {

		PlanOrderState planOrderState = planOrderStateRepository.get(planId, planOrderId, false);

		if (planOrderState != null) {

			return planOrderState.getPlanOrderStateTypeId();

		}
		return null;
	}

	public Long getStateByPlanSchedule(Long planScheduleId) {

		PlanScheduleState planScheduleState = planScheduleStateRepository.get(planScheduleId, false);

		if (planScheduleState != null) {

			return planScheduleState.getPlanScheduleStateTypeId();

		}
		return null;
	}

	public PlanState putStateInPlan(Long planId, Long planStateTypeId) {

		PlanState planState = planStateRepository.get(planId, false);

		if (planState == null) {

			planState = new PlanState();
			planState.setPlanId(planId);
		}

		planState.setPlanStateTypeId(planStateTypeId);

		if (planStateTypeId != PlanStateType.PLAN_STATE_TYPE_UNKNOWN) {

			planState.setChangedAt(Instant.now().toEpochMilli());
		}

		planState = ((planStateRepository.put(planState)) != null) ? planState : null;

		return planState;
	}

	public PlanScheduleState putStateInPlanSchedule(Long planScheduleId, Long planScheduleStateTypeId) {

		PlanScheduleState planScheduleState = planScheduleStateRepository.get(planScheduleId, false);

		if (planScheduleState == null) {

			planScheduleState = new PlanScheduleState();
			planScheduleState.setPlanScheduleId(planScheduleId);
		}

		planScheduleState.setPlanScheduleStateTypeId(planScheduleStateTypeId);

		planScheduleState = ((planScheduleStateRepository.put(planScheduleState)) != null) ? planScheduleState : null;

		return planScheduleState;
	}

	public Plan getPlanWithOrder(Long planId) {

		Plan plan = null;

		plan = planRepository.get(planId, false);

		if (plan != null) {

			plan.setPlanOrders(planOrderRepository.getsByPlan(planId, false));
		}

		return plan;
	}

	public Plan getPlan(Long planId) {

		return planRepository.get(planId, false);
	}

	public List<Plan> getAllPlanWithOrder() {

		List<Plan> plans = null;

		plans = planRepository.getAll(false);

		if (!CollectionUtils.isEmpty(plans)) {

			List<PlanOrder> planOrders = planOrderRepository.getAll(false);

			if (!CollectionUtils.isEmpty(planOrders)) {

				for (Plan plan : plans) {

					if (plan != null && plan.getId() != null) {

						plan.setPlanOrders(planOrders.stream().filter(be -> be.getPlanId().equals(plan.getId()))
								.collect(Collectors.toList()));

					}
				}
			}
		}

		return plans;
	}

	public void fillPlanOrder(Plan plan) {

		if (plan != null && !CollectionUtils.isEmpty(plan.getPlanOrders())) {

			for (PlanOrder planOrder : plan.getPlanOrders()) {

				fillPlanOrder(planOrder);
			}
		}
	}

	public List<PlanOrder> getPlanOrdersByPlan(Long planId) {

		return planOrderRepository.getsByPlan(planId, true);
	}

	public PlanOrderState putStateInOrder(Long planId, Long planOrderId, Long planOrderStateTypeId) {

		PlanOrderState planOrderState = planOrderStateRepository.get(planId, planOrderId, false);

		if (planOrderState == null) {

			planOrderState = new PlanOrderState();
			planOrderState.setPlanId(planId);
			planOrderState.setPlanOrderId(planOrderId);

		}

		if (planOrderStateTypeId != PlanOrderStateType.PLAN_ORDER_STATE_TYPE_UNKNOWN) {

			planOrderState.setChangedAt(Instant.now().toEpochMilli());
		}

		planOrderState.setPlanOrderStateTypeId(planOrderStateTypeId);

		planOrderState = ((planOrderStateRepository.put(planOrderState)) != null) ? planOrderState : null;

		return planOrderState;
	}

	public PlanOrderState putStateInOrder(Long planId, Long planOrderId, Long planOrderStateTypeId, Integer nTimes) {

		PlanOrderState planOrderState = planOrderStateRepository.get(planId, planOrderId, false);

		if (planOrderState == null) {

			planOrderState = new PlanOrderState();
			planOrderState.setPlanId(planId);
			planOrderState.setPlanOrderId(planOrderId);
		}

		if (planOrderStateTypeId != PlanOrderStateType.PLAN_ORDER_STATE_TYPE_UNKNOWN) {

			planOrderState.setChangedAt(Instant.now().toEpochMilli());
		}

		planOrderState.setPlanOrderStateTypeId(planOrderStateTypeId);
		planOrderState.setNTimes(nTimes);

		planOrderState = ((planOrderStateRepository.put(planOrderState)) != null) ? planOrderState : null;

		return planOrderState;
	}

	public PlanOrderState addNTimes(Long planId, Long planOrderId) {

		PlanOrderState planOrderState = planOrderStateRepository.get(planId, planOrderId, false);

		if (planOrderState != null) {

			planOrderState.setNTimes(planOrderState.getNTimes() != null ? planOrderState.getNTimes() + 1 : 1);

			planOrderState = planOrderStateRepository.put(planOrderState);
		}
		return planOrderState;
	}

	public Command sendCommand(Command command) {

		log.info("SendCommand " + command);
		return CacheManager.getInstance().sendCommand(command, Command.DEFAULT_EXPIRE_TIME);
	}

	public Command sendCommand(List<PlanState> planStates, List<Long> managementAreaIds) {

		SetPlanStatePublishCommand setPlanStatePublishCommand = null;

		if (!CollectionUtils.isEmpty(planStates)) {

			setPlanStatePublishCommand = new SetPlanStatePublishCommand();
			setPlanStatePublishCommand.setPlanStates(planStates);
			setPlanStatePublishCommand.setManagementAreaIds(managementAreaIds);
		}
		return setPlanStatePublishCommand != null
				? EntitiesManager.getInstance().sendCommand(setPlanStatePublishCommand)
				: null;
	}

	public Command sendCommand(PlanState planState, List<Long> managementAreaIds) {

		SetPlanStatePublishCommand setPlanStatePublishCommand = null;

		if (planState != null) {

			setPlanStatePublishCommand = new SetPlanStatePublishCommand();
			setPlanStatePublishCommand.add(planState);
			setPlanStatePublishCommand.setManagementAreaIds(managementAreaIds);

		}
		return setPlanStatePublishCommand != null
				? EntitiesManager.getInstance().sendCommand(setPlanStatePublishCommand)
				: null;
	}

	public Command sendCommand(PlanOrderState planOrderState, List<Long> managementAreaIds) {

		SetPlanStatePublishCommand setPlanStatePublishCommand = null;

		if (planOrderState != null && planOrderState.getPlanId() != null && planOrderState.getPlanOrderId() != null) {

			PlanState planState = new PlanState();
			planState.setPlanId(planOrderState.getPlanId());

			if (planOrderState.getPlanOrderStateTypeId() != PlanOrderStateType.PLAN_ORDER_STATE_TYPE_UNKNOWN) {

				planState.setChangedAt(planOrderState.getChangedAt());
			}

			planState.setPlanOrderStates(new ArrayList<>());
			planState.getPlanOrderStates().add(planOrderState);

			setPlanStatePublishCommand = new SetPlanStatePublishCommand();
			setPlanStatePublishCommand.add(planState);
			setPlanStatePublishCommand.setManagementAreaIds(managementAreaIds);

		}
		return setPlanStatePublishCommand != null
				? EntitiesManager.getInstance().sendCommand(setPlanStatePublishCommand)
				: null;
	}

	public PlanSchedules getPlanSchedule(Long planScheduleId) {

		return planSchedulesRepository.get(planScheduleId, false);
	}

	public List<PlanSchedules> getAllPlanSchedule() {

		return planSchedulesRepository.getAll(false);
	}

	public void initPlans() {

		List<Plan> plans = getAllPlanWithOrder();

		if (!CollectionUtils.isEmpty(plans)) {

			List<PlanState> plansState = new ArrayList<>();
			List<PlanOrderState> planOrdersState = new ArrayList<>();

			for (Plan plan : plans) {

				if (plan.getId() != null) {

					PlanState planState = new PlanState();
					planState.setPlanId(plan.getId());
					planState.setPlanStateTypeId(PlanStateType.PLAN_STATE_TYPE_UNKNOWN);

					plansState.add(planState);

					if (CollectionUtils.isNotEmpty(plan.getPlanOrders())) {

						for (PlanOrder planOrder : plan.getPlanOrders()) {

							if (ObjectUtils.allNotNull(planOrder.getId(), planOrder.getPlanId())) {

								PlanOrderState planOrderState = new PlanOrderState();
								planOrderState.setPlanId(planOrder.getPlanId());
								planOrderState.setPlanOrderId(planOrder.getId());
								planOrderState
										.setPlanOrderStateTypeId(PlanOrderStateType.PLAN_ORDER_STATE_TYPE_UNKNOWN);

								planOrdersState.add(planOrderState);
							}
						}
					}
				}
			}

			if (CollectionUtils.isNotEmpty(plansState)) {

				planStateRepository.put(plansState);
			}

			if (CollectionUtils.isNotEmpty(planOrdersState)) {

				planOrderStateRepository.put(planOrdersState);
			}

		}
	}

	public PlanState initPlan(Plan plan) {

		PlanState planState = null;

		if (plan != null && plan.getId() != null) {

			planState = putStateInPlan(plan.getId(), PlanStateType.PLAN_STATE_TYPE_RUNNING);

			if (planState != null && CollectionUtils.isNotEmpty(plan.getPlanOrders())) {

				List<PlanOrderState> planOrdersState = planOrderStateRepository.getsByPlan(plan.getId(), false);

				planState.setPlanOrderStates(new ArrayList<>());

				for (PlanOrder planOrder : plan.getPlanOrders()) {

					if (ObjectUtils.allNotNull(planOrder.getId(), planOrder.getPlanId())) {

						PlanOrderState planOrderState = null;

						if (CollectionUtils.isNotEmpty(planOrdersState)) {

							planOrderState = planOrdersState.stream()
									.filter(be -> planOrder.getId().equals(be.getPlanOrderId())
											&& planOrder.getPlanId().equals(be.getPlanId()))
									.findFirst().orElse(null);
						}

						if (planOrderState == null) {

							planOrderState = new PlanOrderState();
							planOrderState.setPlanId(planOrder.getPlanId());
							planOrderState.setPlanOrderId(planOrder.getId());
						}

						planOrderState.setPlanOrderStateTypeId(PlanOrderStateType.PLAN_ORDER_STATE_TYPE_UNKNOWN);
						planOrderState.setNTimes(null);

						planState.getPlanOrderStates().add(planOrderState);
					}
				}

				if (CollectionUtils.isNotEmpty(planState.getPlanOrderStates())) {

					planOrderStateRepository.put(planState.getPlanOrderStates());
				}

			}
		}
		return planState;
	}

	public PlanState initPlanOrder(Long planId, Long planOrderId) {

		PlanState planState = null;

		if (planId != null && planOrderId != null) {

			planState = planStateRepository.get(planId, false);

			if (planState != null) {

				PlanOrderState planOrderState = planOrderStateRepository.get(planId, planOrderId, false);

				if (planOrderState == null) {

					planOrderState = new PlanOrderState();
					planOrderState.setPlanId(planId);
					planOrderState.setPlanOrderId(planOrderId);
				}

				planOrderState.setChangedAt(Instant.now().toEpochMilli());
				planOrderState.setPlanOrderStateTypeId(PlanOrderStateType.PLAN_ORDER_STATE_TYPE_RUNNING);
				planOrderState = ((planOrderStateRepository.put(planOrderState)) != null) ? planOrderState : null;

				if (planOrderState != null) {

					planState.setCurrentPlanOrderId(planOrderId);
					planState = ((planStateRepository.put(planState)) != null) ? planState : null;

					if (planState != null) {
						planState.setPlanOrderStates(new ArrayList<>());
						planState.getPlanOrderStates().add(planOrderState);
					}
				}
			}
		}
		return planState;

	}

	public ElementValue updateElementValue(Long elementTypeId, Long elementId, Long elementTypeParamId,
			Long paramTypeId, String value) {

		ElementValue elementValue = new ElementValue();
		elementValue.setElementId(elementId);
		elementValue.setElementTypeId(elementTypeId);
		elementValue.setParamTypeId(paramTypeId);
		elementValue.setElementTypeParamId(elementTypeParamId);
		elementValue.setValue(value);
		return elementValueRepository.update(elementValue);
	}

	public ElementValue getElementValue(Long elementTypeId, Long elementId, Long elementTypeParamId, Long paramTypeId) {

		return elementValueRepository.get(elementTypeId, elementId, elementTypeParamId, paramTypeId, false);
	}

	public ElementTypeParam getElementTypeParam(Long elementTypeId, Long elementTypeParamId, Long paramTypeId) {

		return elementTypeParamRepository.get(elementTypeId, paramTypeId, elementTypeParamId, false);
	}

	public ElementState getElementState(Long elementTypeId, Long elementId) {

		return elementStateRepository.get(elementTypeId, elementId, false);
	}

	public Element getElement(Long elementTypeId, Long elementId) {

		return elementRepository.get(elementTypeId, elementId, false);
	}

	public CommandElementType getCommandElementType(Long elementTypeId, Long commandElementTypeId) {

		return commandElementTypeRepository.get(commandElementTypeId, elementTypeId, false);
	}

	public PmQueuePriority getPlanPriority(Long elementTypeId) {

		PmQueuePriority pmQueuePriority = null;
		if (elementTypeId != null) {

			List<PmElementTypeQueue> pmElementTypeQueues = this.pmElementTypeQueueRepository
					.getsByElementType(elementTypeId, false);

			if (CollectionUtils.isNotEmpty(pmElementTypeQueues)) {

				List<PmQueuePriority> pmQueuePriorities = this.pmQueuePriorityRepository
						.getsPlanPriorityByQueue(pmElementTypeQueues.get(0).getQueueId(), false);

				if (CollectionUtils.isNotEmpty(pmQueuePriorities)) {

					pmQueuePriority = pmQueuePriorities.get(0);
				}
			}
		}

		return pmQueuePriority;
	}

	public PmQueuePriority getIncidentPriority(Long elementTypeId) {

		PmQueuePriority pmQueuePriority = null;

		if (elementTypeId != null) {

			List<PmElementTypeQueue> pmElementTypeQueues = this.pmElementTypeQueueRepository
					.getsByElementType(elementTypeId, false);

			if (CollectionUtils.isNotEmpty(pmElementTypeQueues)) {

				List<PmQueuePriority> pmQueuePriorities = this.pmQueuePriorityRepository
						.getsIncidentPriorityByQueue(pmElementTypeQueues.get(0).getQueueId(), false);

				if (CollectionUtils.isNotEmpty(pmQueuePriorities)) {

					pmQueuePriority = pmQueuePriorities.get(0);
				}
			}
		}

		return pmQueuePriority;
	}
}
