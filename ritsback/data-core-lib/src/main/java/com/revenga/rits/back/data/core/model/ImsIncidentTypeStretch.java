package com.revenga.rits.back.data.core.model;

import java.util.List;

import com.revenga.rits.back.data.core.annotation.AssociatedKeys;
import com.revenga.rits.back.data.core.annotation.PrimaryKeys;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;


@Data
@EqualsAndHashCode(callSuper = false)
@PrimaryKeys(fields = {"incidentTypeId", "stretchId"})
public class ImsIncidentTypeStretch extends BasicElement {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4628441741092572501L;
	
	private Long incidentTypeId;
	private Long stretchId;
	private Long defaultLocationId;
	private String operationalPlanUrl;
	private Boolean multiInstance;
	private Long cctvScenarioId;
	private Long spathId;
	private Boolean checkStretchStretch;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@AssociatedKeys(references = {"incidentTypeId=incidentTypeId", "stretchId=stretchId"})
	private List<ImsIncidentTypeRoadImpact> incidentTypeRoadImpacts;
}

