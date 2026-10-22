package com.revenga.rits.back.plan.manager.executor.service;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.CommandElement;
import com.revenga.rits.back.data.core.model.CommandElementPrecondition;
import com.revenga.rits.back.data.core.model.CommandElementType;
import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.ElementState;
import com.revenga.rits.back.data.core.model.ElementTypeParam;
import com.revenga.rits.back.data.core.model.ElementValue;
import com.revenga.rits.back.data.core.model.LogLevel;
import com.revenga.rits.back.data.core.model.LogSubtype;
import com.revenga.rits.back.data.core.model.LogType;
import com.revenga.rits.back.data.core.model.Plan;
import com.revenga.rits.back.data.core.model.PlanOrder;
import com.revenga.rits.back.data.core.model.PlanOrderCondition;
import com.revenga.rits.back.data.core.model.PlanOrderElementTypeState;
import com.revenga.rits.back.data.core.model.PlanOrderElementValue;
import com.revenga.rits.back.data.core.model.PlanOrderState;
import com.revenga.rits.back.data.core.model.PlanOrderTypeParam;
import com.revenga.rits.back.data.core.model.PlanOrderValue;
import com.revenga.rits.back.data.core.model.PlanStateType;
import com.revenga.rits.back.data.core.model.PmCommandElement;
import com.revenga.rits.back.data.core.model.PmQueuePriority;
import com.revenga.rits.back.data.core.model.User;
import com.revenga.rits.back.data.core.model.command.ResponseCommand;
import com.revenga.rits.back.data.core.model.command.ResponseToPlanManagerCommand;
import com.revenga.rits.back.data.core.model.command.SetAddCommandsCommand;
import com.revenga.rits.back.data.core.model.command.SetElementStatesChangeCommand;
import com.revenga.rits.back.data.core.model.helper.command.PmCommandElementHelper;
import com.revenga.rits.back.data.core.util.ConfigProperty;
import com.revenga.rits.back.data.core.util.GroovyManager;
import com.revenga.rits.back.data.core.util.OperationProcessor;
import com.revenga.rits.back.entities.lib.helper.AuditHelper;
import com.revenga.rits.back.manager.lib.service.ResponseManager;
import com.revenga.rits.back.plan.manager.service.EntitiesManager;

import groovy.lang.GroovyObject;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class PlayOrderService {

	PlanService planService;

	List<CommandElementPrecondition> commandElementPreconditions = new ArrayList<>();
	
	private static final String FILE_APP_PROPS = "application.properties";

	private static final String GROOVY_PATH = ConfigProperty.getInstance().getProperty(FILE_APP_PROPS,
			"plan.manager.groovy_path");
	

	public PlayOrderService(PlanService planService) {

		this.planService = planService;
	}

	public Boolean playOrderTypeSignalling(PlanOrder planOrder) {

		Boolean ok = false;

		if (planOrder.getPlanOrderCommandElement() != null
				&& planOrder.getPlanOrderCommandElement().getCommandElement() != null) {

			CommandElement commandElement = planOrder.getPlanOrderCommandElement().getCommandElement();

			if (ObjectUtils.allNotNull(commandElement.getCommandElementTypeId(), commandElement.getElementTypeId(),
					commandElement.getElementId())) {

				Element element = EntitiesManager.getInstance().getElement(commandElement.getElementTypeId(),
						commandElement.getElementId());

				CommandElementType commandElementType = EntitiesManager.getInstance().getCommandElementType(
						commandElement.getElementTypeId(), commandElement.getCommandElementTypeId());

				if (ObjectUtils.allNotNull(element, commandElementType)) {

					PmQueuePriority pmQueuePriority = null;

					String source = this.planService.getPlanIdMapSources().get(planOrder.getPlanId());
					Plan plan = new Plan();
					plan.setId(planOrder.getPlanId());
					
					if (source == null) {

						pmQueuePriority = EntitiesManager.getInstance().getPlanPriority(element.getElementTypeId());
						source = (new Key(plan)).getSimpleValue();
						
					} else {

						pmQueuePriority = EntitiesManager.getInstance().getIncidentPriority(element.getElementTypeId());
					}

					PmCommandElement pmCommandElement = PmCommandElementHelper.get(commandElement, pmQueuePriority, source);

					if (pmCommandElement != null) {

						SetAddCommandsCommand setAddCommandsCommand = new SetAddCommandsCommand();
						setAddCommandsCommand.setResponseCommand(new ResponseToPlanManagerCommand());
						setAddCommandsCommand.addCommand(pmCommandElement);
						setAddCommandsCommand.setSendWithAnyError(true);

						try {

							AuditHelper.Audit(LogType.LOG_TYPE_COMMANDS, LogSubtype.LOG_SUBTYPE_EXECUTE_COMMAND,
									User.USER_SYSTEM, LogLevel.INFO, commandElementType.getLabelAlias(),
									element.getAlias(),
									new ObjectMapper().writeValueAsString(commandElement.getCommandElementValues()), (new Key(plan)).getSimpleValue());

						} catch (JsonProcessingException e) {

							log.error(e.getMessage());
							log.debug(ExceptionUtils.getStackTrace(e));
						}

						ResponseCommand respounse = ResponseManager.getInstance()
								.sendCommandSync(setAddCommandsCommand);

						if (respounse != null && CollectionUtils.isEmpty(respounse.getBasicElements())) {

							ok = true;
						}
					}
				} else {
					log.error("Not found element, CommandElementType");
				}
			}

		}
		return ok;
	}

	public Boolean playOrderTypeEvent(PlanOrder planOrder) {

		Boolean ok = false;

		if (planOrder != null && planOrder.getPlanId() != null && planService != null
				&& !CollectionUtils.isEmpty(planOrder.getPlanOrderValues())) {

			PlanOrderValue message = planOrder.getPlanOrderValues().stream().filter(e -> Long
					.valueOf(PlanOrderTypeParam.PLAN_ORDER_TYPE_EVENT_MESSAGE).equals(e.getPlanOrderTypeParamId()))
					.findFirst().orElse(null);

			if (message != null && message.getValue() != null) {

				String messageValue = message.getValue();

				AuditHelper.Audit(LogType.LOG_TYPE_PLANS, LogSubtype.LOG_SUBTYPE_PLAN_ORDER_EVENT, User.USER_SYSTEM,
						LogLevel.INFO, EntitiesManager.getInstance().getPlan(planOrder.getPlanId()).getAlias(),
						messageValue);

				ok = true;
			}
		}

		return ok;
	}
	
	public Boolean playOrderTypeAutomatism(PlanOrder planOrder) {

		Boolean ok = false;

		if (planOrder != null && planOrder.getPlanId() != null && planService != null
				&& !CollectionUtils.isEmpty(planOrder.getPlanOrderValues())) {

			PlanOrderValue groovyPath = planOrder.getPlanOrderValues().stream().filter(e -> Long
					.valueOf(PlanOrderTypeParam.PLAN_ORDER_TYPE_AUTOMATISM_GROOVY_PATH).equals(e.getPlanOrderTypeParamId()))
					.findFirst().orElse(null);
			
			PlanOrderValue automatismParams = planOrder.getPlanOrderValues().stream().filter(e -> Long
					.valueOf(PlanOrderTypeParam.PLAN_ORDER_TYPE_AUTOMATISM_AUTOMATISM_PARAM).equals(e.getPlanOrderTypeParamId()))
					.findFirst().orElse(null);

			if (groovyPath != null && groovyPath.getValue() != null && automatismParams != null
					&& automatismParams.getValue() != null) {
				
				try {
					
				GroovyObject groovyObj = GroovyManager.getInstance().getGroovyObject(GROOVY_PATH + groovyPath.getValue());

				if (groovyObj != null) {

					ok = (Boolean) groovyObj.invokeMethod("process", automatismParams.getValue());
				}
				} catch(Exception e) {
					
					log.error(ExceptionUtils.getStackTrace(e));
				}
			}
		}

		return ok;
	}

	public Boolean playOrderTypeConfirmation(PlanOrder planOrder) {

		Boolean ok = false;
		if (planOrder != null && planOrder.getPlanId() != null && planService != null) {

			planService.pausePlanAction(planOrder.getPlanId());
			ok = true;
		}

		return ok;
	}

	public Boolean playOrderTypeChangeStatus(PlanOrder planOrder) {

		Boolean ok = false;

		if (planOrder != null && CollectionUtils.isNotEmpty(planOrder.getPlanOrderElementTypeStates())) {

			SetElementStatesChangeCommand setElementStatesChangeCommand = null;

			for (PlanOrderElementTypeState planOrderElementTypeState : planOrder.getPlanOrderElementTypeStates()) {

				if (ObjectUtils.allNotNull(planOrderElementTypeState.getElementId(),
						planOrderElementTypeState.getElementTypeId(),
						planOrderElementTypeState.getElementTypeStateId())) {

					if (setElementStatesChangeCommand == null) {
						setElementStatesChangeCommand = new SetElementStatesChangeCommand();
					}

					ElementState elementState = new ElementState();
					elementState.setElementId(planOrderElementTypeState.getElementId());
					elementState.setElementTypeId(planOrderElementTypeState.getElementTypeId());
					elementState.setStateId(planOrderElementTypeState.getElementTypeStateId());

					setElementStatesChangeCommand.addElementState(elementState);
				}
			}
			if (setElementStatesChangeCommand != null
					&& !CollectionUtils.isEmpty(setElementStatesChangeCommand.getElementStates())) {

				EntitiesManager.getInstance().sendCommand(setElementStatesChangeCommand);
				ok = true;
			}
		}
		return ok;
	}

	public Boolean playOrderTypeChangeParamConfig(PlanOrder planOrder) {

		Boolean ok = false;

		if (planOrder != null && CollectionUtils.isNotEmpty(planOrder.getPlanOrderElementValues())) {

			for (PlanOrderElementValue planOrderElementValue : planOrder.getPlanOrderElementValues()) {

				if (ObjectUtils.allNotNull(planOrderElementValue.getElementId(),
						planOrderElementValue.getElementTypeId(), planOrderElementValue.getElementTypeParamId(),
						planOrderElementValue.getParamTypeId(), planOrderElementValue.getValue())) {

					if (EntitiesManager.getInstance().updateElementValue(planOrderElementValue.getElementTypeId(),
							planOrderElementValue.getElementId(), planOrderElementValue.getElementTypeParamId(),
							planOrderElementValue.getParamTypeId(), planOrderElementValue.getValue()) == null) {
						ok = false;
						break;
					} else {
						ok = true;
					}
				}
			}
		}
		return ok;
	}

	public Boolean playOrderTypeWait(PlanOrder planOrder, CommandPlanThread th) {

		Boolean ok = false;
		if (planOrder != null && planOrder.getPlanId() != null && planService != null && th != null
				&& !CollectionUtils.isEmpty(planOrder.getPlanOrderValues())) {

			PlanOrderValue waitTime = planOrder.getPlanOrderValues().stream().filter(e -> Long
					.valueOf(PlanOrderTypeParam.PLAN_ORDER_TYPE_WAIT_WAITING_TIME).equals(e.getPlanOrderTypeParamId()))
					.findFirst().orElse(null);

			if (waitTime != null && waitTime.getValue() != null) {

				try {

					Integer waitTimeValue = Integer.valueOf(waitTime.getValue());

					if (waitTimeValue != null) {
						waitTimeValue = waitTimeValue * 1000;

						Instant totalTime = Instant.now();

						while (!th.getStop()
								&& (Duration.between(totalTime, Instant.now()).toMillis() < waitTimeValue.intValue())
								&& (!Long.valueOf(PlanStateType.PLAN_STATE_TYPE_STOPPED).equals(th.getNextPlanState())
										&& !Long.valueOf(PlanStateType.PLAN_STATE_TYPE_PAUSE)
												.equals(th.getNextPlanState()))) {

							Thread.sleep(100);
						}
						ok = true;
					}
				} catch (InterruptedException e) {

					log.error(e.getMessage());
					log.debug(ExceptionUtils.getStackTrace(e));
					Thread.currentThread().interrupt();
				}
			}
		}
		return ok;
	}

	public Boolean playOrderTypePlayPlan(PlanOrder planOrder, CommandPlanThread th, int level) {

		Boolean ok = false;
		if (planOrder != null && planOrder.getNextPlanId() != null && planService != null) {

			if (!planService.containsPlanId(planOrder.getNextPlanId())) {

				Plan plan = EntitiesManager.getInstance().getPlanWithOrder(planOrder.getNextPlanId());

				if (plan != null) {

					th.getPlanIdList().add(planOrder.getNextPlanId());

					planService.getPlanIdMapSources().put(planOrder.getNextPlanId(),
							planService.getPlanIdMapSources().get(planOrder.getPlanId()));

					planService.playPlan(plan, th, ++level);

					synchronized (th.getPlanIdList()) {
						th.getPlanIdList().remove(planOrder.getNextPlanId());
					}

					ok = true;

				} else {
					log.error("Plan not found");
				}
			} else {
				log.error("Plan is running");
			}

		}
		return ok;
	}

	public Long playOrderTypeCondition(PlanOrder planOrder) {

		Long nextPlanOrder = null;

		if (planOrder != null && planOrder.getPlanId() != null
				&& CollectionUtils.isNotEmpty(planOrder.getPlanOrderConditions())) {

			Collections.sort(planOrder.getPlanOrderConditions());
			boolean conditions = true;
			Boolean nexusAnd = null;

			for (PlanOrderCondition planOrderCondition : planOrder.getPlanOrderConditions()) {

				boolean condition = true;

				if (ObjectUtils.allNotNull(planOrderCondition.getElementId(), planOrderCondition.getElementTypeId(),
						planOrderCondition.getElementTypeStateId())) {

					ElementState elementState = EntitiesManager.getInstance()
							.getElementState(planOrderCondition.getElementTypeId(), planOrderCondition.getElementId());

					if (elementState == null
							|| !planOrderCondition.getElementTypeStateId().equals(elementState.getStateId())) {

						condition = false;
					}
				}

				if (condition && ObjectUtils.allNotNull(planOrderCondition.getElementId(),
						planOrderCondition.getElementTypeId(), planOrderCondition.getElementTypeParamId(),
						planOrderCondition.getParamTypeId(), planOrderCondition.getThresholdOperationId())) {

					ElementValue elementValue = EntitiesManager.getInstance().getElementValue(
							planOrderCondition.getElementTypeId(), planOrderCondition.getElementId(),
							planOrderCondition.getElementTypeParamId(), planOrderCondition.getParamTypeId());

					ElementTypeParam elementTypeParam = EntitiesManager.getInstance().getElementTypeParam(
							planOrderCondition.getElementTypeId(), planOrderCondition.getElementTypeParamId(),
							planOrderCondition.getParamTypeId());

					if (elementValue == null || elementTypeParam == null || elementValue.getValue() == null
							|| elementTypeParam.getDataTypeId() == null) {

						condition = false;

					} else {
						condition = (OperationProcessor.check(planOrderCondition.getThresholdOperationId(),
								planOrderCondition.getThresholdVal1(), planOrderCondition.getThresholdVal2(),
								elementValue.getValue(), elementTypeParam.getDataTypeId()) > 0L);
					}

				}

				if (nexusAnd == null) {
					conditions = condition;
				} else if (nexusAnd.booleanValue()) {
					conditions = conditions && condition;

				} else {
					conditions = conditions || condition;
				}

				nexusAnd = planOrderCondition.getNexusAnd();
			}

			if (conditions) {

				nextPlanOrder = PlanService.NEXT_ORDER;

			} else if (planOrder.getNextPlanOrderId() != null) {

				nextPlanOrder = planOrder.getNextPlanOrderId();

			} else {
				planService.stopPlanAction(planOrder.getPlanId());
				nextPlanOrder = PlanService.NEXT_ORDER;

			}
		}

		return nextPlanOrder;
	}

	public Long playOrderTypeLoop(PlanOrder planOrder) {

		Long nextPlanOrder = null;

		if (planOrder != null && planOrder.getId() != null && planOrder.getPlanId() != null
				&& planOrder.getNextPlanOrderId() != null && planService != null
				&& !CollectionUtils.isEmpty(planOrder.getPlanOrderValues())) {

			PlanOrderValue numberTime = planOrder.getPlanOrderValues().stream()
					.filter(e -> Long.valueOf(PlanOrderTypeParam.PLAN_ORDER_TYPE_LOOP_NUMBER_OF_TIME)
							.equals(e.getPlanOrderTypeParamId()))
					.findFirst().orElse(null);

			if (numberTime != null && numberTime.getValue() != null) {

				Integer numberTimeValue = Integer.valueOf(numberTime.getValue());

				if (numberTimeValue != null) {

					PlanOrderState planOrderState = EntitiesManager.getInstance().addNTimes(planOrder.getPlanId(),
							planOrder.getId());

					if (planOrderState != null && planOrderState.getNTimes() != null) {

						if (planOrderState.getNTimes() <= numberTimeValue.intValue()) {

							nextPlanOrder = planOrder.getNextPlanOrderId();

						} else {
							nextPlanOrder = PlanService.NEXT_ORDER;
						}
					}
				}
			}
		}
		return nextPlanOrder;
	}
}
