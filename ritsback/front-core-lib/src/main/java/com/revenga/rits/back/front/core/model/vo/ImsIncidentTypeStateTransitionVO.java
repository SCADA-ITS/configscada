package com.revenga.rits.back.front.core.model.vo;

import javax.persistence.Entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
public class ImsIncidentTypeStateTransitionVO extends BasicElementVO {/**
	 * 
	 */
	private static final long serialVersionUID = 5800150609074965719L;
	
	private String incidentTypeId;
	private String iniIncidentStateId;
	private String endIncidentStateId;
	private Boolean auto;
	
	@EqualsAndHashCode.Exclude
	protected ImsIncidentTypeVO incidentType;
	
	@EqualsAndHashCode.Exclude
	protected ImsIncidentStateVO iniIncidentState;
	
	@EqualsAndHashCode.Exclude
	protected ImsIncidentStateVO endIncidentState;

}
