package com.revenga.rits.back.data.core.model;

import com.revenga.rits.back.data.core.annotation.AssociatedKeys;
import com.revenga.rits.back.data.core.annotation.PrimaryKeys;
import com.revenga.rits.back.data.core.annotation.TimeStampField;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 
 * Inventory of alarms in the system
 * 
 * @author aborrajo
 */
@Data
@EqualsAndHashCode(callSuper = false)
@PrimaryKeys(fields = { "alarmConfigId", "elementTypeId", "elementId" })
public class AlarmFiltered extends BasicElement {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1878132322270898851L;
	
	private Long alarmConfigId;
	private Long elementTypeId;
	private Long elementId;
	
	private Boolean alarmDisabled;
	
	@TimeStampField	
	private Long filteredUntil;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@AssociatedKeys(references = { "alarmConfigId=id"})
	private AlarmConfig alarmConfig;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@AssociatedKeys(references = { "elementId=id", "elementTypeId=elementTypeId"})
	private Element element;
	
	private Long userId;
	private String comment;
}
