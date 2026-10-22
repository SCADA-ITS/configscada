package com.revenga.rits.back.data.core.model;

import java.util.List;

import com.revenga.rits.back.data.core.annotation.PrimaryKeys;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = false)
@PrimaryKeys(fields = { "commandElementTypeId", "elementTypeId", "elementId", "id" })
public class CommandElementPrecondition extends BasicElement {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2897155345480722699L;
	
	private Long commandElementTypeId;
	private Long elementTypeId;
	
	private Long elementId;
	
	private Long targetElementTypeId; 
	private Long targetElementId;
	
	private Long targetElementTypeParamId;
	private Long targetParamTypeId;
	
	private Long thresholdOperationId;
	private String thresholdVal1;
	private String thresholdVal2;
	
	private Long elementTypeStateId;
	private Long activeAlarmConfigId;
	
	private Boolean negative;
	
	private String toStr;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private List<CommandElementValuePrecondition> commandElementValuePrecondition;
	
}
