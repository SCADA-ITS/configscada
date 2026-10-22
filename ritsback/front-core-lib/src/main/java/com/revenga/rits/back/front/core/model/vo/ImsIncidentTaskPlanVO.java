package com.revenga.rits.back.front.core.model.vo;

import javax.persistence.Entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
public class ImsIncidentTaskPlanVO extends BasicElementVO {/**
	 * 
	 */
	private static final long serialVersionUID = -5392476442653497851L;
	
	private String incidentTypeTaskId;
	private String incidentTypeId;
	private String stretchId;
	private String planId;
	private Integer position;
	
	@EqualsAndHashCode.Exclude
	protected PlanVO plan;

	@EqualsAndHashCode.Exclude
	protected ImsIncidentTypeTaskVO incidentTypeTask;
	
	@EqualsAndHashCode.Exclude
	protected ImsIncidentTypeVO incidentType;
	
	@EqualsAndHashCode.Exclude
	protected StretchVO stretch;

}
