package com.revenga.rits.back.data.core.model;

import com.revenga.rits.back.data.core.annotation.PrimaryKeys;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@PrimaryKeys(fields = { "incidentTypeId", "stretchId", "roadImpactId" })
public class ImsIncidentTypeRoadImpact extends BasicElement {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4628441741092572501L;

	private Long incidentTypeId;
	private Long stretchId;
	private Long roadImpactId;
}
