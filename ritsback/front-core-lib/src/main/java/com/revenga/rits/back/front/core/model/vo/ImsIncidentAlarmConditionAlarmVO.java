package com.revenga.rits.back.front.core.model.vo;

import javax.persistence.Entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
public class ImsIncidentAlarmConditionAlarmVO extends BasicElementVO {/**
	 * 
	 */
	private static final long serialVersionUID = -239989003302361624L;
	
	private String alarmConfigId;
	private String elementTypeId;
	private String elementId;
	private String incidentAlarmConditionAlarmTypeId;
	
	@EqualsAndHashCode.Exclude
	protected AlarmConfigVO alarmConfig;
	
	@EqualsAndHashCode.Exclude
	protected ElementTypeVO elementType;

	@EqualsAndHashCode.Exclude
	protected ElementVO element;
	
	@EqualsAndHashCode.Exclude
	protected ImsIncidentAlarmConditionAlarmTypeVO incidentAlarmConditionAlarmType;

}
