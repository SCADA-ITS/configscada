package com.revenga.rits.back.front.core.model.vo;

import java.util.List;

import javax.persistence.Entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
public class ImsIncidentTypeVO extends BasicElementVO {/**
	 * 
	 */
	private static final long serialVersionUID = 2605884436065022837L;
	
	private String iniIncidentStateId;
	private String incidentGroupId;
	private String incidentLevelId;
	private String defaultUserId;
	private Integer autoConfirmIncident;
	private Integer validForMin;
	private Boolean cancelIncident;
	
	@EqualsAndHashCode.Exclude
	private ImsIncidentStateVO iniIncidentState;
	
	@EqualsAndHashCode.Exclude
	private ImsIncidentGroupVO incidentGroup;	
	
	@EqualsAndHashCode.Exclude
	private List<ImsIncidentTypeCauseVO> incidentTypeCauses;		
	
	@EqualsAndHashCode.Exclude
	private ImsIncidentLevelVO incidentLevel;
	
	@EqualsAndHashCode.Exclude
	private UserVO defaultUser;
	
	@EqualsAndHashCode.Exclude
	private List<ImsIncidentTypeStretchVO> incidentTypeStretchs;
	
	@EqualsAndHashCode.Exclude
	private List<ImsIncidentTypeStateTransitionVO> incidentTypeStateTransitions;

}
