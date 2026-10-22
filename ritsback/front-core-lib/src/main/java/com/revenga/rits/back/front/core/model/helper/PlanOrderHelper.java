package com.revenga.rits.back.front.core.model.helper;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.ObjectUtils;

import com.revenga.rits.back.cache.core.model.Key;
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
import com.revenga.rits.back.front.core.model.vo.PlanOrderConditionVO;
import com.revenga.rits.back.front.core.model.vo.PlanOrderElementTypeStateVO;
import com.revenga.rits.back.front.core.model.vo.PlanOrderElementValueVO;
import com.revenga.rits.back.front.core.model.vo.PlanOrderVO;
import com.revenga.rits.back.front.core.model.vo.PlanOrderValueVO;

public class PlanOrderHelper {

	private PlanOrderHelper() {

		throw new IllegalStateException(this.getClass().getSimpleName());
	}

	public static void assign(final PlanOrder planOrder, PlanOrderVO planOrderVO) {

		if (ObjectUtils.allNotNull(planOrder, planOrderVO)) {

			BasicElementHelper.fillBasicProperties(planOrderVO, planOrder);

			if (planOrder.getPlanId() != null) {

				Plan plan = new Plan();
				plan.setId(planOrder.getPlanId());
				planOrderVO.setPlanId(BasicElementHelper.getIdFromElement(plan));
			}

			if (planOrder.getPlanOrderTypeId() != null) {

				PlanOrderType planOrderType = new PlanOrderType();
				planOrderType.setId(planOrder.getPlanOrderTypeId());
				planOrderVO.setPlanOrderTypeId(BasicElementHelper.getIdFromElement(planOrderType));
			}

			if (planOrder.getNextPlanId() != null) {

				Plan plan = new Plan();
				plan.setId(planOrder.getPlanId());
				planOrderVO.setNextPlanId(BasicElementHelper.getIdFromElement(plan));
			}

			if (planOrder.getNextPlanOrderId() != null) {

				PlanOrder planOrderNext = new PlanOrder();
				planOrderNext.setId(planOrder.getNextPlanOrderId());
				planOrderNext.setPlanId(planOrder.getNextPlanId());
				planOrderVO.setNextPlanOrderId(BasicElementHelper.getIdFromElement(planOrderNext));
			}

			planOrderVO.setPlanOrderType(PlanOrderTypeHelper.get(planOrder.getPlanOrderType()));

			if (planOrder.getPlanOrderTypeId() == null && planOrder.getPlanOrderType() != null
					&& planOrder.getPlanOrderType().getId() != null) {

				planOrderVO.setPlanOrderTypeId(BasicElementHelper.getIdFromElement(planOrder.getPlanOrderType()));
			}

			if (planOrder.getPlanOrderValues() != null) {

				List<PlanOrderValueVO> planOrderValues = new ArrayList<>();

				for (PlanOrderValue planOrderValue : planOrder.getPlanOrderValues()) {

					if (planOrder.getPlanId() != null) {

						planOrderValues.add(PlanOrderValueHelper.get(planOrderValue));
					}
				}

				planOrderVO.setPlanOrderValues(planOrderValues);
			}

			if (planOrder.getPlanOrderElementValues() != null) {

				List<PlanOrderElementValueVO> planOrderElementValues = new ArrayList<>();

				for (PlanOrderElementValue planOrderElementValue : planOrder.getPlanOrderElementValues()) {

					if (planOrderElementValue.getPlanOrderId() != null) {

						planOrderElementValues.add(PlanOrderElementValueHelper.get(planOrderElementValue));
					}
				}

				planOrderVO.setPlanOrderElementValues(planOrderElementValues);
			}

			if (planOrder.getPlanOrderConditions() != null) {

				List<PlanOrderConditionVO> planOrderConditions = new ArrayList<>();

				for (PlanOrderCondition planOrderCondition : planOrder.getPlanOrderConditions()) {

					if (planOrderCondition.getId() != null) {

						planOrderConditions.add(PlanOrderConditionHelper.get(planOrderCondition));
					}
				}

				planOrderVO.setPlanOrderConditions(planOrderConditions);
			}

			if (planOrder.getPlanOrderElementTypeStates() != null) {

				List<PlanOrderElementTypeStateVO> planOrderElementTypeStates = new ArrayList<>();

				for (PlanOrderElementTypeState planOrderElementTypeState : planOrder.getPlanOrderElementTypeStates()) {

					if (planOrderElementTypeState.getElementTypeStateId() != null) {

						planOrderElementTypeStates.add(PlanOrderElementTypeStateHelper.get(planOrderElementTypeState));
					}
				}

				planOrderVO.setPlanOrderElementTypeStates(planOrderElementTypeStates);
			}

			if (planOrder.getPlanOrderCommandElement() != null) {

				planOrderVO.setComandElement(
						CommandElementHelper.get(planOrder.getPlanOrderCommandElement().getCommandElement()));
			}

			planOrderVO.setPlanOrderOrder(planOrder.getPlanOrderOrder());

		}

	}

	public static PlanOrderVO get(final PlanOrder planOrder) {

		PlanOrderVO planOrderVO = null;

		if (planOrder != null) {

			planOrderVO = new PlanOrderVO();

			assign(planOrder, planOrderVO);
		}

		return planOrderVO;
	}

	public static PlanOrder get(final PlanOrderVO planOrderVO) {

		PlanOrder planOrder = null;

		if (planOrderVO != null) {

			planOrder = new PlanOrder();

			if (planOrderVO.getPlanId() != null) {

				Key key = new Key(planOrderVO.getPlanId());

				if (key.getClazz() == Plan.class) {

					planOrder.setPlanId(Long.valueOf(key.getId(1)));
				}
			}

			if (planOrderVO.getId() != null) {

				Key key = new Key(planOrderVO.getId());

				if (key.getClazz() == PlanOrder.class) {

					planOrder.setPlanId(Long.valueOf(key.getId(1)));
					planOrder.setId(Long.valueOf(key.getId(2)));
				}
			}

			planOrder.setAlias(planOrderVO.getAlias());
			planOrder.setDescription((planOrderVO.getDescription()));
			planOrder.setPlanOrderOrder(planOrderVO.getPlanOrderOrder());

			if (planOrderVO.getPlanOrderTypeId() != null) {

				Key key = new Key(planOrderVO.getPlanOrderTypeId());

				if (key.getClazz() == PlanOrderType.class) {

					planOrder.setPlanOrderTypeId(Long.valueOf(key.getId(1)));
				}
			}

			if (planOrderVO.getPlanOrderType() != null) {

				PlanOrderType planOrderType = PlanOrderTypeHelper.get(planOrderVO.getPlanOrderType());

				if (planOrderType != null) {

					if (planOrderVO.getPlanOrderTypeId() == null) {

						planOrder.setPlanOrderTypeId(planOrderType.getId());
					}
					planOrder.setPlanOrderType(planOrderType);
				}
			}

			if (planOrderVO.getNextPlanId() != null) {

				Key key = new Key(planOrderVO.getNextPlanId());

				if (key.getClazz() == Plan.class) {

					planOrder.setNextPlanId(Long.valueOf(key.getId(1)));
				}
			}

			if (planOrderVO.getNextPlanOrderId() != null) {

				Key key = new Key(planOrderVO.getNextPlanOrderId());

				if (key.getClazz() == PlanOrder.class) {

					planOrder.setNextPlanOrderId(Long.valueOf(key.getId(2)));
				}
			}

			if (planOrderVO.getPlanOrderValues() != null) {

				List<PlanOrderValue> planOrderValues = new ArrayList<>();

				for (PlanOrderValueVO planOrderValueVO : planOrderVO.getPlanOrderValues()) {

					PlanOrderValue planOrderValue = PlanOrderValueHelper.get(planOrderValueVO);

					planOrderValues.add(planOrderValue);
				}

				planOrder.setPlanOrderValues(planOrderValues);
			}

			if (planOrderVO.getPlanOrderConditions() != null) {

				List<PlanOrderCondition> planOrderConditions = new ArrayList<>();

				for (PlanOrderConditionVO planOrderConditionVO : planOrderVO.getPlanOrderConditions()) {

					PlanOrderCondition planOrderCondition = PlanOrderConditionHelper.get(planOrderConditionVO);

					planOrderConditions.add(planOrderCondition);
				}

				planOrder.setPlanOrderConditions(planOrderConditions);
			}

			if (planOrderVO.getPlanOrderElementValues() != null) {

				List<PlanOrderElementValue> planOrderElementValues = new ArrayList<>();

				for (PlanOrderElementValueVO planOrderElementValueVO : planOrderVO.getPlanOrderElementValues()) {

					PlanOrderElementValue planOrderElementValue = PlanOrderElementValueHelper
							.get(planOrderElementValueVO);

					planOrderElementValues.add(planOrderElementValue);
				}

				planOrder.setPlanOrderElementValues(planOrderElementValues);
			}

			if (planOrderVO.getPlanOrderElementTypeStates() != null) {

				List<PlanOrderElementTypeState> planOrderElementTypeStates = new ArrayList<>();

				for (PlanOrderElementTypeStateVO planOrderElementTypeStateVO : planOrderVO
						.getPlanOrderElementTypeStates()) {

					PlanOrderElementTypeState planOrderElementTypeState = PlanOrderElementTypeStateHelper
							.get(planOrderElementTypeStateVO);

					planOrderElementTypeStates.add(planOrderElementTypeState);
				}

				planOrder.setPlanOrderElementTypeStates(planOrderElementTypeStates);
			}

			if (planOrderVO.getPlanOrderTypeId() != null) {

				Key key = new Key(planOrderVO.getPlanOrderTypeId());

				if (key.getClazz() == PlanOrderType.class) {

					planOrder.setPlanOrderTypeId(Long.valueOf(key.getId(1)));
				}
			}

			if (planOrderVO.getComandElement() != null) {

				CommandElement comandElement = CommandElementHelper.get(planOrderVO.getComandElement());
				PlanOrderCommandElement planOrderComandElement = new PlanOrderCommandElement();

				planOrderComandElement.setPlanId(planOrder.getPlanId());
				planOrderComandElement.setPlanOrderId(planOrder.getId());
				planOrderComandElement.setElementTypeId(comandElement.getElementTypeId());
				planOrderComandElement.setCommandElementTypeId(comandElement.getCommandElementTypeId());

				if (comandElement.getId() != null) {

					planOrderComandElement.setCommandElementId(comandElement.getId());
				}

				if (comandElement != null) {
					planOrderComandElement.setCommandElement(comandElement);
				}

				planOrder.setPlanOrderCommandElement(planOrderComandElement);

			}

		}

		return planOrder;
	}

	public static PlanOrderVO get(final PlanOrderState planOrderState) {

		PlanOrderVO planOrderVO = null;

		if (planOrderState != null) {

			planOrderVO = new PlanOrderVO();

			assign(planOrderState, planOrderVO);
		}

		return planOrderVO;
	}

	private static void assign(final PlanOrderState planOrderState, PlanOrderVO planOrderVO) {

		if (ObjectUtils.allNotNull(planOrderState, planOrderVO)) {

			if (planOrderState.getPlanId() != null && planOrderState.getPlanOrderId() != null) {

				PlanOrder planOrder = new PlanOrder();
				planOrder.setId(planOrderState.getPlanOrderId());
				planOrder.setPlanId(planOrderState.getPlanId());

				planOrderVO.setId(BasicElementHelper.getIdFromElement(planOrder));
				planOrderVO.setChangedAt(planOrderState.getChangedAt());
			}

			if (planOrderState.getPlanOrderStateTypeId() != null) {

				PlanOrderStateType planOrderStateType = new PlanOrderStateType();
				planOrderStateType.setId(planOrderState.getPlanOrderStateTypeId());

				planOrderVO.setPlanOrderStateType(PlanOrderStateTypeHelper.get(planOrderStateType));
			}

			planOrderVO.setNTimes(planOrderState.getNTimes());

		}
	}

}
