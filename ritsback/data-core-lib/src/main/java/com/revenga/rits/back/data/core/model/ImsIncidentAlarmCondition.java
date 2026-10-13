package com.revenga.rits.back.data.core.model;

import java.util.List;

import com.revenga.rits.back.data.core.annotation.PrimaryKeys;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;


@Data
@EqualsAndHashCode(callSuper = false)
@PrimaryKeys(fields = {"id"})
public class ImsIncidentAlarmCondition extends BasicElement {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4628441741092572501L;
	
	private Long incidentTypeId;
	private Long stretchId;
	private Boolean auto;
	private Long roadImpactId;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private List<ImsIncidentAlarmConditionAlarm> imsIncidentAlarmConditionAlarms;

}

