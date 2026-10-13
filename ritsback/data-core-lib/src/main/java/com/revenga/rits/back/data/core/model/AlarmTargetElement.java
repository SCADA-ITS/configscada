package com.revenga.rits.back.data.core.model;

import com.revenga.rits.back.data.core.annotation.AssociatedKeys;
import com.revenga.rits.back.data.core.annotation.PrimaryKeys;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 
 * Table of equipment that is alarmed by an alarm
 * 
 * @author aborrajo
 */
@Deprecated
@Data
@EqualsAndHashCode(callSuper = false)
@PrimaryKeys(fields = {"alarmConfigId", "sourceElementTypeId", "sourceElementId", "targetElementTypeId", "targetElementId"})
public class AlarmTargetElement extends BasicElement {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2577716551583216084L;
	
	Long alarmConfigId;
	Long sourceElementTypeId;
	Long sourceElementId;
	Long targetElementTypeId;
	Long targetElementId;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@AssociatedKeys(references = { "sourceElementId=id", "sourceElementTypeId=elementTypeId"})
	private Element sourceElement;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@AssociatedKeys(references = { "targetElementId=id", "targetElementTypeId=elementTypeId"})
	private Element targetElement;
}
