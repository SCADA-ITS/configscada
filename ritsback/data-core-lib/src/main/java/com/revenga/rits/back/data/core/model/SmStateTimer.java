package com.revenga.rits.back.data.core.model;

import com.revenga.rits.back.data.core.annotation.AssociatedKeys;
import com.revenga.rits.back.data.core.annotation.PrimaryKeys;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = false)
@PrimaryKeys(fields = { "stateMachineId", "stateId", "timerId" })
public class SmStateTimer extends BasicElement {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -269078543862290671L;
	
	private Long stateMachineId;
	private Long stateId;
	private Long timerId;
		
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@AssociatedKeys(references = {"timerId=id"})
	private SmTimer timer;
	
}