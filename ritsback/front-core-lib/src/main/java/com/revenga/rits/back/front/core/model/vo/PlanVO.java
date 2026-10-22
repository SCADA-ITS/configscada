package com.revenga.rits.back.front.core.model.vo;

import java.util.List;

import javax.persistence.Entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
public class PlanVO extends BasicElementVO {/**
	 * 
	 */
	private static final long serialVersionUID = -1311260409626516993L;
	 
	private Long planModeType;
	
	private Long changedAt;
	
	@EqualsAndHashCode.Exclude
	protected List<PlanOrderVO> planOrders;
	
	@EqualsAndHashCode.Exclude
	protected PlanStateTypeVO planStateType;
	
	@EqualsAndHashCode.Exclude
	protected PlanOrderVO currentPlanOrder;
	
}
