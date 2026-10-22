package com.revenga.rits.back.service.graphql.mutation;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.revenga.rits.back.front.core.model.vo.PlanActionVO;
import com.revenga.rits.back.front.core.model.vo.PlanNodeVO;
import com.revenga.rits.back.front.core.model.vo.PlanOrderVO;
import com.revenga.rits.back.front.core.model.vo.PlanSchedulesVO;
import com.revenga.rits.back.front.core.model.vo.PlanVO;
import com.revenga.rits.back.service.graphql.service.EntitiesManager;
import com.revenga.rits.back.service.graphql.service.PlanNodeService;
import com.revenga.rits.back.service.graphql.service.PlanOrderService;
import com.revenga.rits.back.service.graphql.service.PlanSchedulesService;
import com.revenga.rits.back.service.graphql.service.PlanService;

import graphql.schema.DataFetchingEnvironment;


@Component
public class PlanMutation implements GraphQLMutationResolver{

	@Autowired
	private PlanService planService;
	
	@Autowired
	private PlanOrderService planOrderService;
	
	@Autowired
	private PlanSchedulesService planSchedulesService;
	
	@Autowired
	private PlanNodeService planNodeService;
	
	public Optional<PlanVO> createPlan(final PlanVO plan, DataFetchingEnvironment env) {

		return planService.create(plan, EntitiesManager.getManagementAreaId(env));
	}

	public Optional<PlanVO> updatePlan(final PlanVO plan) {

		return planService.update(plan);
	}

	public Optional<PlanVO> deletePlan(final String planId) {

		return planService.delete(planId);
	}
	
	//
	// Command
	//
	public Boolean sendPlayPlan(final List<String> planIds) {

		return planService.sendPlayPlan(planIds);
	}
	
	public Boolean sendPlanActions(final List<PlanActionVO> planActions) {

		return planService.sendPlanActions(planActions);
	}
	
	
	public Optional<PlanOrderVO> createPlanOrder(final PlanOrderVO planOrder) {

		return planOrderService.create(planOrder);
	}

	public Optional<PlanOrderVO> updatePlanOrder(final PlanOrderVO planOrder) {

		return planOrderService.update(planOrder);
	}

	public Optional<PlanOrderVO> deletePlanOrder(final String planOrderId) {

		return planOrderService.delete(planOrderId);
	}
	
	public Optional<PlanSchedulesVO> createPlanSchedules(final PlanSchedulesVO planSchedules) {

		return planSchedulesService.create(planSchedules);
	}

	public Optional<PlanSchedulesVO> updatePlanSchedules(final PlanSchedulesVO planSchedules) {

		return planSchedulesService.update(planSchedules);
	}

	public Optional<PlanSchedulesVO> deletePlanSchedules(final String planSchedulesId) {

		return planSchedulesService.delete(planSchedulesId);
	}
	
	public List<PlanSchedulesVO> sendPlanScheduleActions(final List<PlanSchedulesVO>  planSchedules) {

		return planSchedulesService.sendPlanScheduleActions(planSchedules);
	}
	
	public Optional<PlanNodeVO> createPlanNode(final PlanNodeVO planNode, DataFetchingEnvironment env) {
		
		return planNodeService.create(planNode, EntitiesManager.getManagementAreaId(env));
	}
	
	public Optional<PlanNodeVO> deletePlanNode(final String planNodeId) {

		return planNodeService.delete(planNodeId);
	}
}

