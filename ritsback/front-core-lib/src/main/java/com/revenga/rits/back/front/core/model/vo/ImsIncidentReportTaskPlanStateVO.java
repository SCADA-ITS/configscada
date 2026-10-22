package com.revenga.rits.back.front.core.model.vo;

import javax.persistence.Entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
public class ImsIncidentReportTaskPlanStateVO extends BasicElementVO {/**
	 * 
	 */
	private static final long serialVersionUID = -6560844460679171102L;

	private Long completedAt;
	
	@EqualsAndHashCode.Exclude
	protected ImsIncidentTaskPlanVO incidentTaskPlan;
}
