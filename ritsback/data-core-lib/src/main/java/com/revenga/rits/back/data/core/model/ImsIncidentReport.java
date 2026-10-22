package com.revenga.rits.back.data.core.model;

import java.util.List;

import com.revenga.rits.back.data.core.annotation.AssociatedKeys;
import com.revenga.rits.back.data.core.annotation.DbConfig;
import com.revenga.rits.back.data.core.annotation.PrimaryKeys;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = false)
@PrimaryKeys(fields = { "id" })
@DbConfig(db = "2")
public class ImsIncidentReport extends BasicElement {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8482242987658695912L;

	private Long incidentTypeId;
	private Long incidentTypeCauseId;	
	private Long affectionStretchId;
	private Long locationId;
	private Long incidentStateId;
	private Long incidentLevelId;
	private Long currentUserId;
	private Integer validForMin;
	private Boolean confirm;
	private Long generatedAt;
	private Long lastUpdate;
	private Long lastUpdateState;
	private Long roadImpactId;
	private Boolean backward;
	private Long managementAreaId;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@AssociatedKeys(references = {"id=incidentReportId"})
	private List<ImsIncidentReportTaskState> incidentReportTaskStates;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@AssociatedKeys(references = {"id=incidentReportId"})
	private List<ImsIncidentReportTaskElement> incidentReportTaskElements;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@AssociatedKeys(references = {"id=incidentReportId"})
	private List<ImsIncidentReportTaskPlanState> incidentReportTaskPlanStates;

	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@AssociatedKeys(references = {"id=incidentReportId"})
	private List<ImsIncidentReportAlarm> incidentReportAlarms;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private RoadImpact roadImpact;
}
