package com.revenga.rits.back.front.core.model.vo;

import java.util.List;

import javax.persistence.Entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
public class ImsIncidentTypeStretchVO extends BasicElementVO {/**
	 * 
	 */
	private static final long serialVersionUID = -7270363160859556360L;
	
	private String incidentTypeId;
	private String stretchId;
	private String defaultLocationId;
	private String operationalPlanUrl;
	private Integer validForMin;
	private Boolean multiInstance;
	private String scenarioId;
	private Boolean checkStretchStretch;
	
	@EqualsAndHashCode.Exclude
	protected ImsIncidentTypeVO incidentType;
	
	@EqualsAndHashCode.Exclude
	protected StretchVO stretch;
	
	@EqualsAndHashCode.Exclude
	protected LocationVO defaultLocation;
	
	@EqualsAndHashCode.Exclude
	private List<ImsIncidentAlarmConditionVO> imsIncidentAlarmConditions;
	
	@EqualsAndHashCode.Exclude
	private CCTVScenarioVO scenario;
	
	@EqualsAndHashCode.Exclude
	private List<StretchVO> spath;
	
	private String spathId;
	
	@EqualsAndHashCode.Exclude
	private List<RoadImpactVO> roadImpacts;
	
	@EqualsAndHashCode.Exclude
	private List<String> roadImpactIds;
}
