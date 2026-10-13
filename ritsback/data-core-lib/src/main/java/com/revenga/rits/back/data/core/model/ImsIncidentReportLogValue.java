package com.revenga.rits.back.data.core.model;

import com.revenga.rits.back.data.core.annotation.AssociatedKeys;
import com.revenga.rits.back.data.core.annotation.DbConfig;
import com.revenga.rits.back.data.core.annotation.PrimaryKeys;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 
 * Defines value of log
 * 
 * @author agomez
 * 
 */
@Data
@EqualsAndHashCode(callSuper = false)
@PrimaryKeys(fields = { "incidentReportLogId", "logTypeId", "logTypeParamId" })
@DbConfig(db = "2")
public class ImsIncidentReportLogValue extends BasicElement {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5859644687524343563L;

	private Long incidentReportLogId;
	private Long logTypeId;
	private Long logTypeParamId;
	private String value;

	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@AssociatedKeys(references = { "logTypeId=logTypeId", "logTypeParamId=id" })
	private LogTypeParam logTypeParam;
}
