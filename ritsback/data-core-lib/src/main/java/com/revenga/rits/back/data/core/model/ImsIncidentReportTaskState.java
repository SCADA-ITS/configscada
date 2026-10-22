package com.revenga.rits.back.data.core.model;

import com.revenga.rits.back.data.core.annotation.DbConfig;
import com.revenga.rits.back.data.core.annotation.PrimaryKeys;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@PrimaryKeys(fields = {"incidentReportId", "incidentTypeTaskId"})
@DbConfig(db = "2")
public class ImsIncidentReportTaskState extends BasicElement {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2449767741216252915L;

    private Long incidentReportId;
    private Long incidentTypeTaskId;
    private Boolean error;
    private Long completedAt;
}

