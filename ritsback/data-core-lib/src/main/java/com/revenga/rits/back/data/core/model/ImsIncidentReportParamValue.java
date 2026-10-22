package com.revenga.rits.back.data.core.model;

import com.revenga.rits.back.data.core.annotation.DbConfig;
import com.revenga.rits.back.data.core.annotation.PrimaryKeys;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@PrimaryKeys(fields = { "incidentReportId", "incidentParamId" })
@DbConfig(db = "2")
public class ImsIncidentReportParamValue extends BasicElement {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5849066537734139317L;

	private Long incidentReportId;
	private Long incidentParamId;
	private String value;
	
}
