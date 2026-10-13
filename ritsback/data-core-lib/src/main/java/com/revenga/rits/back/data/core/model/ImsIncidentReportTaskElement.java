package com.revenga.rits.back.data.core.model;

import com.revenga.rits.back.data.core.annotation.DbConfig;
import com.revenga.rits.back.data.core.annotation.PrimaryKeys;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@PrimaryKeys(fields = { "incidentReportId", "incidentTypeTaskId", "elementTypeId", "elementId" })
@DbConfig(db = "2")
public class ImsIncidentReportTaskElement extends BasicElement {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3675121285322205066L;

	private Long incidentReportId;

	private Long incidentTypeTaskId;
	
	private Long elementTypeId;

	private Long elementId;
}
