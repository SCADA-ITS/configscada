package com.revenga.rits.back.front.core.model.helper;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.Plan;
import com.revenga.rits.back.data.core.model.PlanOrder;
import com.revenga.rits.back.data.core.model.PlanOrderState;
import com.revenga.rits.back.data.core.model.PlanState;
import com.revenga.rits.back.data.core.model.PlanStateType;
import com.revenga.rits.back.front.core.model.vo.PlanOrderVO;
import com.revenga.rits.back.front.core.model.vo.PlanVO;

public class PlanHelper {

	private PlanHelper() {

		throw new IllegalStateException(this.getClass().getSimpleName());
	}

	public static PlanVO get(final Plan plan) {

		PlanVO planVO = null;

		if (plan != null) {

			planVO = new PlanVO();

			assign(plan, planVO);
		}

		return planVO;
	}

	public static Plan get(final PlanVO planVO) {

		Plan plan = null;

		if (planVO != null) {

			plan = new Plan();
			if (planVO.getId() != null) {

				Key key = new Key(planVO.getId());

				if (key.getClazz() == Plan.class) {

					plan.setId(Long.valueOf(key.getId(1)));
				}
			}

			plan.setAlias(planVO.getAlias());
			plan.setDescription((planVO.getDescription()));
			plan.setPlanModeTypeId(planVO.getPlanModeType());

			if (planVO.getPlanOrders() != null) {

				List<PlanOrder> planOrders = new ArrayList<>();

				for (PlanOrderVO planOrderVO : planVO.getPlanOrders()) {

					PlanOrder planOrder = new PlanOrder();

					planOrder.setId(Long.parseLong(planOrderVO.getId()));
					planOrder.setPlanId(plan.getId());
					planOrders.add(planOrder);
				}

				plan.setPlanOrders(planOrders);
			}

		}

		return plan;
	}

	public static void assign(final Plan plan, PlanVO planVO) {

		if (ObjectUtils.allNotNull(plan, planVO)) {

			BasicElementHelper.fillBasicProperties(planVO, plan);

			planVO.setPlanModeType(plan.getPlanModeTypeId());

			if (plan.getPlanOrders() != null) {

				List<PlanOrderVO> planOrders = new ArrayList<>();

				for (PlanOrder planOrder : plan.getPlanOrders()) {

					if (planOrder.getPlanId() != null) {

						planOrders.add(PlanOrderHelper.get(planOrder));
					}
				}

				planVO.setPlanOrders(planOrders);
			}

		}
	}

	public static PlanVO get(final PlanState planState) {

		PlanVO planVO = null;

		if (planState != null) {

			planVO = new PlanVO();

			assign(planState, planVO);
		}

		return planVO;
	}

	private static void assign(final PlanState planState, PlanVO planVO) {

		if (ObjectUtils.allNotNull(planState, planVO)) {

			if (planState.getPlanId() != null) {

				Plan plan = new Plan();
				plan.setId(planState.getPlanId());

				planVO.setId(BasicElementHelper.getIdFromElement(plan));
			}

			if (planState.getPlanStateTypeId() != null) {

				PlanStateType planStateType = new PlanStateType();
				planStateType.setId(planState.getPlanStateTypeId());

				planVO.setPlanStateType(PlanStateTypeHelper.get(planStateType));
				planVO.setChangedAt(planState.getChangedAt());
			}

			if (!CollectionUtils.isEmpty(planState.getPlanOrderStates())) {

				List<PlanOrderVO> planOrders = new ArrayList<>();

				for (PlanOrderState planOrderState : planState.getPlanOrderStates()) {

					PlanOrderVO planOrder = PlanOrderHelper.get(planOrderState);

					if (planOrder != null) {
						
						planOrders.add(planOrder);
					}
				}
				planVO.setPlanOrders(planOrders);
			}
			
			if (planState.getCurrentPlanOrderId() != null && planState.getPlanId() != null) {

				PlanOrder planOrder = new PlanOrder();
				planOrder.setPlanId(planState.getPlanId());
				planOrder.setId(planState.getCurrentPlanOrderId());
				
				planVO.setCurrentPlanOrder(PlanOrderHelper.get(planOrder));
			}
			
			if(planState.getCurrentPlanOrder() != null){
				
				PlanOrderVO planOrder = PlanOrderHelper.get(planState.getCurrentPlanOrder());
				planVO.setCurrentPlanOrder(planOrder);
			}
		}

	}
}
