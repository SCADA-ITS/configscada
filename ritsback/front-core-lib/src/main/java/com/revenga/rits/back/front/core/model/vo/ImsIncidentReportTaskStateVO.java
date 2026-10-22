package com.revenga.rits.back.front.core.model.vo;

import java.util.List;

import javax.persistence.Entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
public class ImsIncidentReportTaskStateVO extends BasicElementVO {/**
	 * 
	 */
	private static final long serialVersionUID = 6669415824493362625L;

    private Long completedAt;
    private Boolean error;
	
	@EqualsAndHashCode.Exclude
	private ImsIncidentTypeTaskVO incidentTypeTask;
	
	@EqualsAndHashCode.Exclude
	private List <ImsIncidentReportTaskPlanStateVO> taskPlanStates;
	
	@EqualsAndHashCode.Exclude
	private List <ImsIncidentReportTaskValueVO> incidentReportTaskValues;	
	
	@EqualsAndHashCode.Exclude
	private List <ElementVO> elements;	 
}
