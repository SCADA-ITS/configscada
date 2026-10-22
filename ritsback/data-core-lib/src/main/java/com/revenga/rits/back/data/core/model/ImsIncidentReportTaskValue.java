package com.revenga.rits.back.data.core.model;

import com.revenga.rits.back.data.core.annotation.AssociatedKeys;
import com.revenga.rits.back.data.core.annotation.DbConfig;
import com.revenga.rits.back.data.core.annotation.PrimaryKeys;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;


@Data
@EqualsAndHashCode(callSuper = false)
@PrimaryKeys(fields = {"incidentReportId", "taskTypeId", "taskTypeParamId", "incidentTypeTaskId"})
@DbConfig(db = "2")
public class ImsIncidentReportTaskValue extends BasicElement {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3675121285322205066L;

	private Long incidentReportId;
	
	private Long taskTypeId;
	
	private Long taskTypeParamId;
	
	private Long incidentTypeTaskId;
	
	private String value;
	
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@AssociatedKeys(references = {"taskTypeParamId=id"})
	private ImsTaskTypeParam taskTypeParam;
	
}
