package com.revenga.rits.back.front.core.model.vo;

import javax.persistence.Entity;

import com.revenga.rits.back.data.core.model.AlarmConfig;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
public class ElementStateAlarmVO extends BasicElementVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 570476988247109495L;

	private String elementTypeId;
	@EqualsAndHashCode.Exclude
	private ElementTypeVO elementType;
	
	private String stateId;
	@EqualsAndHashCode.Exclude
	private ElementTypeStateVO elementTypeState;
	
	private Long alarmConfigId;
	@EqualsAndHashCode.Exclude
	private AlarmConfigVO alarmConfig;

}
