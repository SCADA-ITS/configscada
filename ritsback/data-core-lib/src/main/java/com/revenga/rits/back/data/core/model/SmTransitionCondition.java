package com.revenga.rits.back.data.core.model;

import com.revenga.rits.back.data.core.annotation.AssociatedKeys;
import com.revenga.rits.back.data.core.annotation.PrimaryKeys;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = false)
@PrimaryKeys(fields = { "stateMachineId", "stateId", "transitionId", "id" })
public class SmTransitionCondition extends BasicElement {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7033789028591110419L;

	private Long stateMachineId;
	private Long stateId;
	private Long transitionId;

	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@AssociatedKeys(references = { "id=id" })
	private SmCondition condition;

}
