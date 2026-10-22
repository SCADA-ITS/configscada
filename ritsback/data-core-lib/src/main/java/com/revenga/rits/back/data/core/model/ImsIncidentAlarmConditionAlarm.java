package com.revenga.rits.back.data.core.model;

import com.revenga.rits.back.data.core.annotation.PrimaryKeys;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@PrimaryKeys(fields = { "id" })
public class ImsIncidentAlarmConditionAlarm extends BasicElement {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4628441741092572501L;

	private Long incidentAlarmConditionId;
	private Long alarmConfigId;
	private Long elementTypeId;
	private Long elementId;
	private Long incidentAlarmConditionAlarmTypeId;
	private Integer minAlarmedElements;

}
