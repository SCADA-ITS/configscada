package com.revenga.rits.back.plan.manager.executor.service;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.map.HashedMap;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

import com.revenga.rits.back.data.core.model.LogLevel;
import com.revenga.rits.back.data.core.model.LogSubtype;
import com.revenga.rits.back.data.core.model.LogType;
import com.revenga.rits.back.data.core.model.Plan;
import com.revenga.rits.back.data.core.model.PlanOrder;
import com.revenga.rits.back.data.core.model.PlanOrderState;
import com.revenga.rits.back.data.core.model.PlanOrderStateType;
import com.revenga.rits.back.data.core.model.PlanOrderType;
import com.revenga.rits.back.data.core.model.PlanState;
import com.revenga.rits.back.data.core.model.PlanStateType;
import com.revenga.rits.back.data.core.model.User;
import com.revenga.rits.back.data.core.util.ConfigProperty;
import com.revenga.rits.back.entities.lib.helper.AuditHelper;
import com.revenga.rits.back.plan.manager.executor.CommandPlanExecutor;
import com.revenga.rits.back.plan.manager.service.EntitiesManager;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class PlanService {

	public static final Integer MAX_PLAN_LEVEL = Integer.parseInt(
			ConfigProperty.getInstance().getProperty("application.properties", "plan.service.max_plan_level"));

	public static final Long NEXT_ORDER = -1L;

	private static final String PARAM_NUMBER_COMSUMER = "number_consumers";

	private CommandPlanExecutor genericExecutor;
	private Map<String, CommandPlanThread> consumers;
	private Map<String, List<Long>> planIdMap;
	private PlayOrderService playOrderService;
	
	@Getter
	private Map<Long, String> planIdMapSources;

	public PlanService(CommandPlanExecutor commandPlanExecutor) {

		this.genericExecutor = commandPlanExecutor;
		consumers = new HashedMap<>();
		planIdMap = new HashedMap<>();
		planIdMapSources = new HashedMap<>();
		playOrderService = new PlayOrderService(this);
	}

	public Boolean init() {

		Boolean ok = true;
		EntitiesManager.getInstance().initPlans();

		// Init comsumer
		String value = genericExecutor.getParamValue(PARAM_NUMBER_COMSUMER);

		if (value == null) {

			throw new NullPointerException("number_consumers not found in the executor");

		} else {

			long numberConsumers = Long.parseLong(value);

			for (long i = 0; i < numberConsumers; i++) {

				CommandPlanThread consumer = new CommandPlanThread(this);

				String threadNameRouterService = "Thread" + i + "-" + consumer.getClass().getSimpleName();
				(new Thread(consumer, threadNameRouterService)).start();

				List<Long> planIdList = Collections.synchronizedList(new ArrayList<>());

				planIdMap.put(threadNameRouterService, planIdList);
				consumer.setPlanIdList(planIdList);

				consumers.put(threadNameRouterService, consumer);
			}
		}
		return ok;
	}

	public Boolean deInit() {

		Boolean ok = true;

		if (consumers != null) {

			for (Entry<String, CommandPlanThread> consumer : consumers.entrySet()) {

				try {
					consumer.getValue().stop();

					Instant start;
					Duration timeElapsed;
					boolean stopped;
					start = Instant.now();

					do {
						stopped = !consumer.getValue().isAlive();
						Thread.sleep(1L);

						timeElapsed = Duration.between(start, Instant.now());

					} while (timeElapsed.toMillis() < 5000L && !stopped);

				}

				catch (InterruptedException e) {

					log.error(e.getMessage());
					log.debug(ExceptionUtils.getStackTrace(e));
					Thread.currentThread().interrupt();
					ok = false;
				}
			}
		}
		return ok;
	}

	public void playPlanAction(Long planId, String source) {

		if (planId != null) {

			if (!containsPlanId(planId)) {

				addPlanId(planId, source);

			} else {
				CommandPlanThread consumer = getConsumer(planId);

				if (consumer != null) {
					consumer.setNextPlanState(PlanStateType.PLAN_STATE_TYPE_RUNNING);
				}
			}
		}
	}

	public void pausePlanAction(Long planId) {

		if (planId != null) {

			CommandPlanThread consumer = getConsumer(planId);
			if (consumer != null)
				consumer.setNextPlanState(PlanStateType.PLAN_STATE_TYPE_PAUSE);
		}

	}

	public void stopPlanAction(Long planId) {

		if (planId != null) {

			CommandPlanThread consumer = getConsumer(planId);
			if (consumer != null)
				consumer.setNextPlanState(PlanStateType.PLAN_STATE_TYPE_STOPPED);
		}

	}

	public void cancelPlanAction(Long planId) {

		if (planId != null) {

			CommandPlanThread consumer = getConsumer(planId);
			if (consumer != null)
				consumer.setNextPlanState(PlanStateType.PLAN_STATE_TYPE_CANCEL);
		}

	}

	public void playPlan(Plan plan, CommandPlanThread th, int level) {

		if (plan != null && plan.getId() != null) {

			PlanState planState = null;

			planState = EntitiesManager.getInstance().initPlan(plan);

			EntitiesManager.getInstance().sendCommand(planState);

			log.info("Plan " + plan.getId() + " Running");

			if (!CollectionUtils.isEmpty(plan.getPlanOrders())) {

				Collections.sort(plan.getPlanOrders());

				EntitiesManager.getInstance().fillPlanOrder(plan);
				boolean end = false;
				boolean pause = false;

				int index = 0;
				int size = plan.getPlanOrders().size();

				while (!th.getStop() && !end) {

					if (index >= size) {

						planState = EntitiesManager.getInstance().putStateInPlan(plan.getId(),
								PlanStateType.PLAN_STATE_TYPE_SUCCESS);
						EntitiesManager.getInstance().sendCommand(planState);

						log.info("Plan " + plan.getId() + " Success");

						end = true;

					} else if (level == 0
							&& Long.valueOf(PlanStateType.PLAN_STATE_TYPE_STOPPED).equals(th.getNextPlanState())) {

						planState = EntitiesManager.getInstance().putStateInPlan(plan.getId(),
								PlanStateType.PLAN_STATE_TYPE_STOPPED);
						EntitiesManager.getInstance().sendCommand(planState);

						log.info("Plan " + plan.getId() + " Stopped");
						end = true;

					} else if (level == 0
							&& Long.valueOf(PlanStateType.PLAN_STATE_TYPE_CANCEL).equals(th.getNextPlanState())) {

						planState = EntitiesManager.getInstance().putStateInPlan(plan.getId(),
								PlanStateType.PLAN_STATE_TYPE_CANCEL);
						EntitiesManager.getInstance().sendCommand(planState);

						log.info("Plan " + plan.getId() + " Cancel");
						end = true;

					} else if (level == 0
							&& Long.valueOf(PlanStateType.PLAN_STATE_TYPE_PAUSE).equals(th.getNextPlanState())
							&& !pause) {

						th.setNextPlanState(null);
						planState = EntitiesManager.getInstance().putStateInPlan(plan.getId(),
								PlanStateType.PLAN_STATE_TYPE_PAUSE);
						EntitiesManager.getInstance().sendCommand(planState);

						log.info("Plan " + plan.getId() + " Pause");
						pause = true;

					} else if ((level == 0
							&& Long.valueOf(PlanStateType.PLAN_STATE_TYPE_RUNNING).equals(th.getNextPlanState())
							&& pause) || !pause) {

						if (pause) {

							th.setNextPlanState(null);
							planState = EntitiesManager.getInstance().putStateInPlan(plan.getId(),
									PlanStateType.PLAN_STATE_TYPE_RUNNING);
							EntitiesManager.getInstance().sendCommand(planState);

							log.info("Plan " + plan.getId() + " Running");
							pause = false;
						}

						try {
							Long nextOrderId = playPlanOrder(plan.getPlanOrders().get(index), th, level);

							if (nextOrderId != null && !NEXT_ORDER.equals(nextOrderId)) {

								for (int i = 0; i < size; i++) {

									if (plan.getPlanOrders().get(i).getId() != null
											&& plan.getPlanOrders().get(i).getId().equals(nextOrderId)) {
										index = i;
										break;

									} else if (i == size - 1) {
										index++;
									}
								}
							} else {
								index++;
							}
						} catch (NumberFormatException e) {

							log.error(e.getMessage());
							log.debug(ExceptionUtils.getStackTrace(e));

							planState = EntitiesManager.getInstance().putStateInPlan(plan.getId(),
									PlanStateType.PLAN_STATE_TYPE_FAILED);
							EntitiesManager.getInstance().sendCommand(planState);

							log.info("Plan " + plan.getId() + " error");

							end = true;
						}

					}

					try {

						Thread.sleep(10);

					} catch (InterruptedException e) {

						log.error(e.getMessage());
						log.debug(ExceptionUtils.getStackTrace(e));
						Thread.currentThread().interrupt();
					}
				}
			} else {
				planState = EntitiesManager.getInstance().putStateInPlan(plan.getId(),
						PlanStateType.PLAN_STATE_TYPE_SUCCESS);
				EntitiesManager.getInstance().sendCommand(planState);

				log.info("Plan " + plan.getId() + " Success");
			}

			AuditHelper.Audit(LogType.LOG_TYPE_PLANS, LogSubtype.LOG_SUBTYPE_PLAN_EXECUTED, User.USER_SYSTEM,
					LogLevel.DEBUG, plan.getAlias());
		}

	}

	private Long playPlanOrder(PlanOrder planOrder, CommandPlanThread th, int level) {

		Long nextOrderId = NEXT_ORDER;
		Boolean ok = false;
		PlanOrderState planOrderState = null;

		try {
			if (planOrder != null && ObjectUtils.allNotNull(planOrder.getId(), planOrder.getPlanId())) {

				PlanState planState = EntitiesManager.getInstance().initPlanOrder(planOrder.getPlanId(),
						planOrder.getId());
				planState.setPlanStateTypeId(null);
				EntitiesManager.getInstance().sendCommand(planState);

				log.info(planOrder.toString() + " Running");

				if (Long.valueOf(PlanOrderType.PLAN_ORDER_TYPE_SIGNALLING).equals(planOrder.getPlanOrderTypeId())) {

					ok = playOrderService.playOrderTypeSignalling(planOrder);

				} else if (level == 0 && Long.valueOf(PlanOrderType.PLAN_ORDER_TYPE_CONFIRMATION)
						.equals(planOrder.getPlanOrderTypeId())) {

					ok = playOrderService.playOrderTypeConfirmation(planOrder);

				} else if (Long.valueOf(PlanOrderType.PLAN_ORDER_TYPE_CHANGE_STATUS)
						.equals(planOrder.getPlanOrderTypeId())) {

					ok = playOrderService.playOrderTypeChangeStatus(planOrder);

				} else if (Long.valueOf(PlanOrderType.PLAN_ORDER_TYPE_CHANGE_ELEMENT_PARAM_CONFIG)
						.equals(planOrder.getPlanOrderTypeId())) {

					ok = playOrderService.playOrderTypeChangeParamConfig(planOrder);

				} else if (Long.valueOf(PlanOrderType.PLAN_ORDER_TYPE_WAIT).equals(planOrder.getPlanOrderTypeId())) {

					ok = playOrderService.playOrderTypeWait(planOrder, th);

				} else if (level < MAX_PLAN_LEVEL.intValue() - 1 && Long
						.valueOf(PlanOrderType.PLAN_ORDER_TYPE_PLAY_PLAN).equals(planOrder.getPlanOrderTypeId())) {

					ok = playOrderService.playOrderTypePlayPlan(planOrder, th, level);

				} else if (Long.valueOf(PlanOrderType.PLAN_ORDER_TYPE_CONDITION)
						.equals(planOrder.getPlanOrderTypeId())) {

					nextOrderId = playOrderService.playOrderTypeCondition(planOrder);

					if (nextOrderId != null) {

						ok = true;
					}

				} else if (Long.valueOf(PlanOrderType.PLAN_ORDER_TYPE_LOOP).equals(planOrder.getPlanOrderTypeId())) {

					nextOrderId = playOrderService.playOrderTypeLoop(planOrder);

					if (nextOrderId != null) {

						ok = true;
					}

				} else if (Long.valueOf(PlanOrderType.PLAN_ORDER_TYPE_EVENT).equals(planOrder.getPlanOrderTypeId())) {

					ok = playOrderService.playOrderTypeEvent(planOrder);
				} else if (Long.valueOf(PlanOrderType.PLAN_ORDER_TYPE_AUTOMATISM).equals(planOrder.getPlanOrderTypeId())) {

					ok = playOrderService.playOrderTypeAutomatism(planOrder);
				}
			}

		} catch (NumberFormatException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}

		if (planOrder != null && ObjectUtils.allNotNull(planOrder.getId(), planOrder.getPlanId())) {

			if (ok.booleanValue()) {

				planOrderState = EntitiesManager.getInstance().putStateInOrder(planOrder.getPlanId(), planOrder.getId(),
						PlanOrderStateType.PLAN_ORDER_STATE_TYPE_SUCCESS);

				if (planOrderState != null) {
					EntitiesManager.getInstance().sendCommand(planOrderState);
				}

				log.info(planOrder.toString() + " Success");

			} else {

				planOrderState = EntitiesManager.getInstance().putStateInOrder(planOrder.getPlanId(), planOrder.getId(),
						PlanOrderStateType.PLAN_ORDER_STATE_TYPE_FAILED);

				if (planOrderState != null) {
					EntitiesManager.getInstance().sendCommand(planOrderState);
				}
				log.info(planOrder.toString() + " Failed");

				if (!Long.valueOf(PlanOrderType.PLAN_ORDER_TYPE_SIGNALLING).equals(planOrder.getPlanOrderTypeId())){
					
					throw new NumberFormatException();
				}

			}

		}

		return nextOrderId;
	}

	public boolean containsPlanId(Long planId) {
		boolean found = false;

		for (Entry<String, List<Long>> entry : planIdMap.entrySet()) {
			synchronized (entry.getValue()) {

				if (entry.getValue().contains(planId)) {
					found = true;
					break;
				}
			}
		}
		return found;
	}

	private CommandPlanThread getConsumer(Long planId) {

		CommandPlanThread consumer = null;

		for (Entry<String, List<Long>> entry : planIdMap.entrySet()) {
			synchronized (entry.getValue()) {

				if (entry.getValue().contains(planId)) {

					consumer = consumers.get(entry.getKey());
					break;
				}
			}
		}
		return consumer;
	}

	private void addPlanId(Long planId, String source) {
		List<Long> planIdList = null;
		Integer i = null;

		for (Entry<String, List<Long>> entry : planIdMap.entrySet()) {

			if (entry != null && (i == null || entry.getValue().size() < i)) {

				planIdList = entry.getValue();
				i = entry.getValue().size();
			}
		}

		if (planIdList != null) {
			
			planIdList.add(planId);
			planIdMapSources.put(planId, source);
		}
	}
}