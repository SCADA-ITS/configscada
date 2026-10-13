package com.revenga.rits.back.service.graphql.type;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;

import com.revenga.rits.back.data.core.model.BasicElement;
import com.revenga.rits.back.data.core.model.CommandElement;
import com.revenga.rits.back.data.core.model.Plan;
import com.revenga.rits.back.data.core.model.PlanOrder;
import com.revenga.rits.back.data.core.model.PlanOrderCommandElement;
import com.revenga.rits.back.data.core.model.PlanOrderCondition;
import com.revenga.rits.back.data.core.model.PlanOrderElementTypeState;
import com.revenga.rits.back.data.core.model.PlanOrderElementValue;
import com.revenga.rits.back.data.core.model.PlanOrderState;
import com.revenga.rits.back.data.core.model.PlanOrderStateType;
import com.revenga.rits.back.data.core.model.PlanOrderType;
import com.revenga.rits.back.data.core.model.PlanOrderValue;
import com.revenga.rits.back.entities.lib.repository.PlanOrderStateRepository;
import com.revenga.rits.back.front.core.model.helper.PlanOrderHelper;
import com.revenga.rits.back.front.core.model.vo.CommandElementVO;
import com.revenga.rits.back.front.core.model.vo.PlanOrderConditionVO;
import com.revenga.rits.back.front.core.model.vo.PlanOrderElementTypeStateVO;
import com.revenga.rits.back.front.core.model.vo.PlanOrderElementValueVO;
import com.revenga.rits.back.front.core.model.vo.PlanOrderStateTypeVO;
import com.revenga.rits.back.front.core.model.vo.PlanOrderTypeVO;
import com.revenga.rits.back.front.core.model.vo.PlanOrderVO;
import com.revenga.rits.back.front.core.model.vo.PlanOrderValueVO;
import com.revenga.rits.back.front.core.model.vo.PlanVO;
import com.revenga.rits.back.service.graphql.helper.MapRequests;
import com.revenga.rits.back.service.graphql.service.EntitiesManager;

import lombok.EqualsAndHashCode;
import lombok.extern.log4j.Log4j2;

@Log4j2
@EqualsAndHashCode(callSuper = false)
public class PlanOrderGraphql extends PlanOrderVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3908796455858736265L;
	private PlanOrder planOrder;
	private PlanOrderState planOrderState;
	private MapRequests mapRequests;

	private PlanOrderState getPlanOrderState() {

		if (planOrder != null && planOrder.getId() != null && planOrder.getPlanId() != null && planOrderState == null) {

			if (mapRequests == null) {

				planOrderState = EntitiesManager.getInstance().getPlanOrderStateRepository().get(planOrder.getPlanId(),
						planOrder.getId(), false);

			} else {

				List<BasicElement> basicElement = null;

				if (mapRequests.isGetAll()) {

					basicElement = mapRequests.get(PlanOrderStateRepository.class, "getAll", null);

				} else {

					basicElement = mapRequests.get(PlanOrderStateRepository.class, "getsByPlan", planOrder.getPlanId());
				}

				if (!CollectionUtils.isEmpty(basicElement)) {

					planOrderState = (PlanOrderState) basicElement.stream()
							.filter(be -> planOrder.getPlanId().equals(((PlanOrderState) be).getPlanId())
									&& planOrder.getId().equals(((PlanOrderState) be).getPlanOrderId()))
							.findFirst().orElse(null);
				}

			}

		}

		return planOrderState;
	}

	public PlanOrderGraphql(final PlanOrder planOrder, MapRequests mapRequests) {

		PlanOrderHelper.assign(planOrder, this);
		this.planOrder = planOrder;
		this.mapRequests = mapRequests;
	}

	public PlanOrderGraphql(final PlanOrder planOrder) {

		PlanOrderHelper.assign(planOrder, this);
		this.planOrder = planOrder;
	}

	@Override
	public PlanOrderTypeVO getPlanOrderType() {

		if (super.getPlanOrderType() == null) {

			if (planOrder != null) {

				PlanOrderType planOrderType = EntitiesManager.getInstance().getPlanOrderTypeRepository()
						.get(planOrder.getPlanOrderTypeId(), false);

				if (planOrderType != null) {

					super.setPlanOrderType(new PlanOrderTypeGraphql(planOrderType));
				}

			} else {

				log.error("planOrder has null pointer");
			}

		}

		return super.getPlanOrderType();
	}

	@Override
	public PlanOrderVO getNextPlanOrder() {

		if (super.getNextPlanOrder() == null && planOrder != null && planOrder.getNextPlanOrderId() != null) {

			PlanOrder planOrderNext = EntitiesManager.getInstance().getPlanOrderRepository().get(planOrder.getPlanId(),
					planOrder.getNextPlanOrderId(), false);

			if (planOrderNext != null) {

				super.setNextPlanOrder(new PlanOrderGraphql(planOrderNext));
			}
		}

		return super.getNextPlanOrder();
	}

	@Override
	public PlanVO getNextPlan() {

		if (super.getNextPlan() == null && planOrder != null && planOrder.getNextPlanId() != null) {

			Plan planNext = EntitiesManager.getInstance().getPlanRepository().get(planOrder.getNextPlanId(), false);

			if (planNext != null) {

				super.setNextPlan(new PlanGraphql(planNext));
			}

		}

		return super.getNextPlan();
	}

	@Override
	public List<PlanOrderValueVO> getPlanOrderValues() {

		if (super.getPlanOrderValues() == null) {

			if (planOrder != null) {

				List<PlanOrderValue> orderValues = EntitiesManager.getInstance().getPlanOrderValueRepository()
						.getsByPlanOrder(planOrder.getPlanId(), planOrder.getId(), planOrder.getPlanOrderTypeId(),
								false);

				if (!CollectionUtils.isEmpty(orderValues)) {

					List<PlanOrderValueVO> planOrderValues = new ArrayList<>();

					for (PlanOrderValue planOrderValue : orderValues) {

						planOrderValues.add(new PlanOrderValueGraphql(planOrderValue));
					}
					super.setPlanOrderValues(planOrderValues);
				}

			} else {

				log.error("planOrder has null pointer");
			}
		}

		return super.getPlanOrderValues();
	}

	@Override
	public List<PlanOrderElementValueVO> getPlanOrderElementValues() {

		if (super.getPlanOrderElementValues() == null) {

			if (planOrder != null) {

				List<PlanOrderElementValue> elementValues = EntitiesManager.getInstance()
						.getPlanOrderElementValueRepository()
						.getsByPlanOrder(planOrder.getPlanId(), planOrder.getId(), false);

				if (!CollectionUtils.isEmpty(elementValues)) {

					List<PlanOrderElementValueVO> planOrderElementValues = new ArrayList<>();

					for (PlanOrderElementValue planOrderElementValue : elementValues) {

						planOrderElementValues.add(new PlanOrderElementValueGraphql(planOrderElementValue));
					}
					super.setPlanOrderElementValues(planOrderElementValues);
				}

			} else {

				log.error("planOrder has null pointer");
			}
		}

		return super.getPlanOrderElementValues();
	}

	@Override
	public List<PlanOrderElementTypeStateVO> getPlanOrderElementTypeStates() {

		if (super.getPlanOrderElementTypeStates() == null) {

			if (planOrder != null) {

				List<PlanOrderElementTypeState> planOrderElementTypeStates = EntitiesManager.getInstance()
						.getPlanOrderElementTypeStateRepository()
						.getsByPlanOrder(planOrder.getPlanId(), planOrder.getId(), false);

				if (!CollectionUtils.isEmpty(planOrderElementTypeStates)) {

					List<PlanOrderElementTypeStateVO> planOrderElementTypeStatesVO = new ArrayList<>();

					for (PlanOrderElementTypeState planOrderElementTypeState : planOrderElementTypeStates) {

						planOrderElementTypeStatesVO
								.add(new PlanOrderElementTypeStateGraphql(planOrderElementTypeState));
					}
					super.setPlanOrderElementTypeStates(planOrderElementTypeStatesVO);
				}

			} else {

				log.error("planOrder has null pointer");
			}
		}

		return super.getPlanOrderElementTypeStates();
	}

	@Override
	public List<PlanOrderConditionVO> getPlanOrderConditions() {

		if (super.getPlanOrderConditions() == null) {

			if (planOrder != null) {

				List<PlanOrderCondition> planOrderConditions = EntitiesManager.getInstance()
						.getPlanOrderConditionRepository()
						.getsByPlanOrder(planOrder.getPlanId(), planOrder.getId(), false);

				if (!CollectionUtils.isEmpty(planOrderConditions)) {

					List<PlanOrderConditionVO> planOrderConditionsVO = new ArrayList<>();

					for (PlanOrderCondition planOrderCondition : planOrderConditions) {

						planOrderConditionsVO.add(new PlanOrderConditionGraphql(planOrderCondition));
					}
					super.setPlanOrderConditions(planOrderConditionsVO);
				}

			} else {

				log.error("planOrder has null pointer");
			}
		}

		return super.getPlanOrderConditions();
	}

	@Override
	public CommandElementVO getComandElement() {

		if (super.getComandElement() == null) {

			if (planOrder != null) {

				PlanOrderCommandElement planOrderCommandElement = EntitiesManager.getInstance()
						.getPlanOrderCommandElementRepository().get(planOrder.getPlanId(), planOrder.getId(), false);

				if (planOrderCommandElement != null) {

					CommandElement commandElement = EntitiesManager.getInstance().getCommandElementRepository().get(
							planOrderCommandElement.getCommandElementTypeId(),
							planOrderCommandElement.getElementTypeId(), planOrderCommandElement.getCommandElementId(),
							false);

					if (commandElement != null) {

						super.setComandElement(new CommandElementGraphql(commandElement));
					}
				}

			} else {

				log.error("planOrder has null pointer");
			}

		}

		return super.getComandElement();
	}

	@Override
	public PlanOrderStateTypeVO getPlanOrderStateType() {

		if (super.getPlanOrderStateType() == null) {

			planOrderState = getPlanOrderState();

			if (planOrderState != null) {

				PlanOrderStateType planOrderStateType = EntitiesManager.getInstance().getPlanOrderStateTypeRepository()
						.get(planOrderState.getPlanOrderStateTypeId(), false);

				if (planOrderStateType != null) {

					super.setPlanOrderStateType(new PlanOrderStateTypeGraphql(planOrderStateType));
				}
			} else {

				log.error("planOrderState has null pointer");
			}
		}

		return super.getPlanOrderStateType();
	}

	@Override
	public Long getChangedAt() {

		if (super.getPlanOrderStateType() == null) {

			planOrderState = getPlanOrderState();

			if (planOrderState != null) {

				super.setChangedAt(planOrderState.getChangedAt());

			} else {

				log.error("planOrder has null pointer");
			}
		}

		return super.getChangedAt();
	}

	@Override
	public Integer getNTimes() {

		if (super.getNTimes() == null) {

			planOrderState = getPlanOrderState();

			if (planOrderState != null) {

				super.setNTimes(planOrderState.getNTimes());

			} else {

				log.error("planOrderState has null pointer");
			}
		}

		return super.getNTimes();
	}
}
