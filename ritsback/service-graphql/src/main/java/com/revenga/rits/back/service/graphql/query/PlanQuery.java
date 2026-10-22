package com.revenga.rits.back.service.graphql.query;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.revenga.rits.back.front.core.model.vo.PlanHierarchyVO;
import com.revenga.rits.back.front.core.model.vo.PlanNodeVO;
import com.revenga.rits.back.front.core.model.vo.PlanOrderStateTypeVO;
import com.revenga.rits.back.front.core.model.vo.PlanOrderTypeVO;
import com.revenga.rits.back.front.core.model.vo.PlanOrderVO;
import com.revenga.rits.back.front.core.model.vo.PlanScheduleStateTypeVO;
import com.revenga.rits.back.front.core.model.vo.PlanSchedulesVO;
import com.revenga.rits.back.front.core.model.vo.PlanStateTypeVO;
import com.revenga.rits.back.front.core.model.vo.PlanVO;
import com.revenga.rits.back.service.graphql.service.EntitiesManager;
import com.revenga.rits.back.service.graphql.service.PlanHierarchyService;
import com.revenga.rits.back.service.graphql.service.PlanNodeService;
import com.revenga.rits.back.service.graphql.service.PlanOrderService;
import com.revenga.rits.back.service.graphql.service.PlanOrderTypeService;
import com.revenga.rits.back.service.graphql.service.PlanSchedulesService;
import com.revenga.rits.back.service.graphql.service.PlanService;

import graphql.schema.DataFetchingEnvironment;

@Component
public class PlanQuery implements GraphQLQueryResolver {

	@Autowired
	private PlanOrderTypeService planOrderTypeService;
	
	@Autowired
	private PlanService planService;
	
	@Autowired
	private PlanOrderService planOrderService;
	
	@Autowired
	private PlanSchedulesService planSchedulesService;
	
	@Autowired
	private PlanHierarchyService planHierarchyService;
	
	@Autowired
	private PlanNodeService planNodeService;
	
	public Optional<PlanOrderTypeVO> getPlanOrderType(final String planOrderTypeId) {

		return planOrderTypeService.get(planOrderTypeId);
	}
	
	public List<PlanOrderTypeVO> getPlanOrderTypes() {

		return planOrderTypeService.getAll();
	}
	
	public Optional<PlanVO> getPlan(final String planId) {

		return planService.get(planId);
	}
	
	public List<PlanVO> getPlans(DataFetchingEnvironment env) {

		return planService.getAll(EntitiesManager.getManagementAreaId(env));
	}
	
	public Optional<PlanSchedulesVO> getPlanSchedule(final String planScheduleId) {

		return planSchedulesService.get(planScheduleId);
	}
	
	public List<PlanSchedulesVO> getPlanSchedules(DataFetchingEnvironment env) {

		return planSchedulesService.getAll(EntitiesManager.getManagementAreaId(env));
	}
	
	public List<PlanSchedulesVO> getPlanScheduleByPlan(final String planId) {

		return planSchedulesService.getByPlan(planId);
	}
	
	public List<PlanStateTypeVO> getPlanStateTypes() {
		
		return planService.getPlanStateTypes();
	}
	
	public List<PlanOrderStateTypeVO> getPlanOrderStateTypes() {
		
		return planOrderService.getPlanOrderStateTypes();
	}
	
	public List<PlanOrderVO> getPlanOrders() {
		
		return planOrderService.getAll();
	}
	
	public Optional<PlanOrderVO> getPlanOrder(final String planOrderId) {

		return planOrderService.get(planOrderId);
	}
	
	public List<PlanScheduleStateTypeVO> getPlanScheduleStateTypes() {
		
		return planSchedulesService.getPlanScheduleStateTypes();
	}
	
	public List<PlanHierarchyVO> getPlanHierarchies(DataFetchingEnvironment env) {

		return planHierarchyService.getAll(EntitiesManager.getManagementAreaId(env));
	}
	
	public List<PlanNodeVO> getPlanNodes(DataFetchingEnvironment env) {

		return planNodeService.getAll(EntitiesManager.getManagementAreaId(env));
	}
}
