package com.revenga.rits.back.service.graphql.type;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;

import com.revenga.rits.back.data.core.model.BasicElement;
import com.revenga.rits.back.data.core.model.Plan;
import com.revenga.rits.back.data.core.model.PlanOrder;
import com.revenga.rits.back.data.core.model.PlanState;
import com.revenga.rits.back.data.core.model.PlanStateType;
import com.revenga.rits.back.entities.lib.repository.PlanStateRepository;
import com.revenga.rits.back.front.core.model.helper.PlanHelper;
import com.revenga.rits.back.front.core.model.vo.PlanOrderVO;
import com.revenga.rits.back.front.core.model.vo.PlanStateTypeVO;
import com.revenga.rits.back.front.core.model.vo.PlanVO;
import com.revenga.rits.back.service.graphql.helper.MapRequests;
import com.revenga.rits.back.service.graphql.service.EntitiesManager;

import lombok.EqualsAndHashCode;
import lombok.extern.log4j.Log4j2;

@Log4j2
@EqualsAndHashCode(callSuper = false)
public class PlanGraphql extends PlanVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3908796455858736265L;
	private Plan plan;
	private PlanState planState;
	private MapRequests mapRequests;

	private PlanState getPlanState() {

		if (plan != null && plan.getId() != null && planState == null) {

			if (mapRequests != null && mapRequests.isGetAll()) {

				List<BasicElement> basicElement = null;

				basicElement = mapRequests.get(PlanStateRepository.class, "getAll", null);

				if (!CollectionUtils.isEmpty(basicElement)) {

					planState = (PlanState) basicElement.stream()
							.filter(be -> plan.getId().equals(((PlanState) be).getPlanId())).findFirst().orElse(null);
				}

			} else {
				
				planState = EntitiesManager.getInstance().getPlanStateRepository().get(plan.getId(), false);
			}

		}

		return planState;
	}

	public PlanGraphql(final Plan plan, MapRequests mapRequests) {

		PlanHelper.assign(plan, this);
		this.plan = plan;
		this.mapRequests = mapRequests;
	}

	public PlanGraphql(final Plan plan) {

		PlanHelper.assign(plan, this);
		this.plan = plan;
	}

	@Override
	public List<PlanOrderVO> getPlanOrders() {

		if (super.getPlanOrders() == null) {

			if (plan != null) {

				List<PlanOrder> orders = EntitiesManager.getInstance().getPlanOrderRepository().getsByPlan(plan.getId(),
						false);

				if (!CollectionUtils.isEmpty(orders)) {

					Collections.sort(orders);

					List<PlanOrderVO> planOrdersVO = new ArrayList<>();

					for (PlanOrder planOrder : orders) {

						planOrdersVO.add(new PlanOrderGraphql(planOrder, this.mapRequests));
					}

					super.setPlanOrders(planOrdersVO);
				}

			} else {

				log.error("plan has null pointer");
			}
		}

		return super.getPlanOrders();
	}

	@Override
	public PlanStateTypeVO getPlanStateType() {

		if (super.getPlanStateType() == null) {

			planState = getPlanState();

			if (planState != null) {

				PlanStateType planStateType = EntitiesManager.getInstance().getPlanStateTypeRepository()
						.get(planState.getPlanStateTypeId(), false);

				if (planStateType != null) {

					super.setPlanStateType(new PlanStateTypeGraphql(planStateType));
				}
			}

		}

		return super.getPlanStateType();

	}

	@Override
	public Long getChangedAt() {

		if (super.getChangedAt() == null) {

			planState = getPlanState();

			if (planState != null) {

				super.setChangedAt(planState.getChangedAt());
			}

		}

		return super.getChangedAt();

	}

	@Override
	public PlanOrderVO getCurrentPlanOrder() {

		if (super.getCurrentPlanOrder() == null) {

			if (plan != null && plan.getId() != null) {

				planState = getPlanState();

				if (planState != null && planState.getCurrentPlanOrderId() != null) {

					PlanOrder planOrder = EntitiesManager.getInstance().getPlanOrderRepository().get(plan.getId(),
							planState.getCurrentPlanOrderId(), false);

					if (planOrder != null) {

						super.setCurrentPlanOrder(new PlanOrderGraphql(planOrder));
					}
				}

			} else {

				log.error("plan has null pointer");
			}
		}

		return super.getCurrentPlanOrder();
	}
}
