package com.revenga.rits.back.data.core.model;

import java.util.List;

import com.revenga.rits.back.data.core.annotation.AssociatedKeys;
import com.revenga.rits.back.data.core.annotation.PrimaryKeys;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = false)
@PrimaryKeys(fields = { "stateMachineId", "id" })
public class SmState extends BasicElement {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6845421225750032852L;

	private Long stateMachineId;
	private Boolean initState;
	private Long tMin;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@AssociatedKeys(references = { "stateMachineId=stateMachineId", "id=stateId" })
	private List<SmTransition> transition;

	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@AssociatedKeys(references = { "stateMachineId=stateMachineId", "id=stateId" })
	private List<SmStateTimer> stateTimers;
}
