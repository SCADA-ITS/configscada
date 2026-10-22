package com.revenga.rits.back.front.core.model.vo;

import java.util.List;

import javax.persistence.Entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
public class AlarmConfigVO extends BasicElementVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2893675450649732113L;
	
	private String elementId;
	
	private String alarmTypeId;
	@EqualsAndHashCode.Exclude
	private AlarmTypeVO alarmType;

	private String alarmLevelId;
	@EqualsAndHashCode.Exclude
	private AlarmLevelVO alarmLevel;

	private String elementTypeId;
	@EqualsAndHashCode.Exclude
	private ElementTypeVO elementType;
	
	private String elementSubtypeId;
	@EqualsAndHashCode.Exclude
	private ElementSubtypeVO elementSubtype;
	
	private String elementTypeStateId;
	@EqualsAndHashCode.Exclude
	private ElementTypeStateVO elementTypeState;

	@EqualsAndHashCode.Exclude
	private ElementVO element;

	private Boolean mine;
	private Boolean treatment;
	
	private Long minTimeAlarmed;
	private Long thresholdTime;

	@EqualsAndHashCode.Exclude
	private List<AlarmConfigMeasureVO> alarmConfigMeasures;

	@EqualsAndHashCode.Exclude
	private List<AlarmTargetElementVO> alarmTargetElements;
	
	@EqualsAndHashCode.Exclude
	private List<ElementStateAlarmVO> alarmConfigStates;
}
