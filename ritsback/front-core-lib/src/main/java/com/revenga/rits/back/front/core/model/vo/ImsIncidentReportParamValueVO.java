package com.revenga.rits.back.front.core.model.vo;

import javax.persistence.Entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
public class ImsIncidentReportParamValueVO extends BasicElementVO {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8384498126218327285L;

	protected String value;
	protected String incidentParamId;
	protected String incidentReportId;

	@EqualsAndHashCode.Exclude
	protected ImsIncidentParamVO incidentParam;
	
	@EqualsAndHashCode.Exclude
	protected ImsIncidentReportVO incidentReport;
	
}
