package com.revenga.rits.back.data.core.model;

import com.revenga.rits.back.data.core.annotation.DbConfig;
import com.revenga.rits.back.data.core.annotation.PrimaryKeys;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 
 * 
 * @author agomez
 */
@Data
@EqualsAndHashCode(callSuper = false)
@PrimaryKeys(fields = {"incidentReportId", "alarmConfigId","elementTypeId","elementId"})
@DbConfig(db = "2")
public class ImsIncidentReportAlarm extends BasicElement {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -477342983930823461L;

	Long incidentReportId;
	Long alarmConfigId;
	Long elementTypeId;
	Long elementId;
	Long activation;
}
