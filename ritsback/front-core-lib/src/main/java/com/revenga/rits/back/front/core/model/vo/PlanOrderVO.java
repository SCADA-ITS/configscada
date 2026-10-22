package com.revenga.rits.back.front.core.model.vo;

import java.util.List;

import javax.persistence.Entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
public class PlanOrderVO extends BasicElementVO {/**
	 * 
	 */
	private static final long serialVersionUID = -1311260409626516993L;
	 
	private String planId;
	private String nextPlanOrderId;
	private String nextPlanId;
	
	private String planOrderTypeId;
		
	private Integer nTimes;
	private Integer planOrderOrder;
	
	private Long changedAt;
	
	@EqualsAndHashCode.Exclude
	private PlanOrderTypeVO planOrderType;
	
	@EqualsAndHashCode.Exclude
	private PlanOrderVO nextPlanOrder;
	
	@EqualsAndHashCode.Exclude
	private PlanVO nextPlan;
	
	@EqualsAndHashCode.Exclude
	private List<PlanOrderValueVO> planOrderValues;
	
	@EqualsAndHashCode.Exclude
	private List<PlanOrderElementValueVO> planOrderElementValues;
	
	@EqualsAndHashCode.Exclude
	private List<PlanOrderElementTypeStateVO> planOrderElementTypeStates;
	
	@EqualsAndHashCode.Exclude
	private List<PlanOrderConditionVO> planOrderConditions;
	
	@EqualsAndHashCode.Exclude
	private CommandElementVO comandElement;
	
	@EqualsAndHashCode.Exclude
	private PlanOrderStateTypeVO planOrderStateType;
}
