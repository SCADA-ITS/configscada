package com.revenga.rits.back.front.core.model.vo;

import java.util.List;

import javax.persistence.Entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
public class ImsIncidentReportVO extends BasicElementVO  {/**
	 * 
	 */
	private static final long serialVersionUID = -1616162528019065047L;

	private Integer validForMin;
	private Boolean confirm;
	
	/**
	 * Generation date of the report
	 */
	private Long generatedAt;
	
	/**
	 * Last actualization date of the report
	 */
	private Long lastUpdate;
	
	@EqualsAndHashCode.Exclude
	protected ImsIncidentTypeVO incidentType;
	
	@EqualsAndHashCode.Exclude
	protected ImsIncidentTypeCauseVO incidentTypeCause;	
	
	@EqualsAndHashCode.Exclude
	protected StretchVO affectionStretch;
	
	@EqualsAndHashCode.Exclude
	protected LocationVO location;

	@EqualsAndHashCode.Exclude
	protected ImsIncidentStateVO incidentState;
	
	@EqualsAndHashCode.Exclude
	protected ImsIncidentLevelVO incidentLevel;

	@EqualsAndHashCode.Exclude
	protected UserVO currentUser;
	
	@EqualsAndHashCode.Exclude
	private List<ImsIncidentReportLogVO> incidentLogs;
	
	@EqualsAndHashCode.Exclude
	private List<ImsIncidentSequenceStateVO> sequenceOfStates;
	
	@EqualsAndHashCode.Exclude
	private List <ImsIncidentReportTaskStateVO> taskStates;
	
	@EqualsAndHashCode.Exclude
	private List<ImsIncidentReportAlarmVO> alarms;
	
	@EqualsAndHashCode.Exclude
	private List<ImsIncidentReportParamValueVO> incidentParamValues;
	
	@EqualsAndHashCode.Exclude
	private String operationalPlanUrl;
	
	@EqualsAndHashCode.Exclude
	private CCTVScenarioVO scenario;
	
	@EqualsAndHashCode.Exclude
	private List<StretchVO> spath;
	
	@EqualsAndHashCode.Exclude
	private RoadImpactVO roadImpact;
	
	@EqualsAndHashCode.Exclude
	private List<RoadImpactVO> roadImpacts;
	
}
