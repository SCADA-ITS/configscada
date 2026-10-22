package com.revenga.rits.back.front.core.model.vo;

import javax.persistence.Entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
public class ElementStateVO extends BasicElementVO {/**
	 * 
	 */
	private static final long serialVersionUID = 3130857402555037817L;

	@EqualsAndHashCode.Exclude
	private ElementVO element;
	private String elementId;
	
	@EqualsAndHashCode.Exclude
	private ElementTypeVO elementType;
	
	@EqualsAndHashCode.Exclude
	private ElementTypeStateVO previousElementTypeState;
	
	@EqualsAndHashCode.Exclude
	private ElementTypeStateVO currentElementTypeState;
	private String currentElementTypeStateId;
	
	@EqualsAndHashCode.Exclude
	private AlarmLevelVO maxAlarmLevel;
	
	private String maintenanceComment;
	
}
