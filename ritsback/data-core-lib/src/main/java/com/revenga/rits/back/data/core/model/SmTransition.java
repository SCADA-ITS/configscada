package com.revenga.rits.back.data.core.model;

import java.util.List;

import com.revenga.rits.back.data.core.annotation.AssociatedKeys;
import com.revenga.rits.back.data.core.annotation.PrimaryKeys;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = false)
@PrimaryKeys(fields = { "stateMachineId", "stateId", "id" })
public class SmTransition extends BasicElement implements Comparable<SmTransition> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 860312219886418584L;
	private Long stateMachineId;
	private Long stateId;

	private Long nextStateId;
	private Boolean nexusAnd;

	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@AssociatedKeys(references = { "stateMachineId=stateMachineId", "stateId=stateId", "id=transitionId" })
	private List<SmTransitionCondition> transitionConditions;

	@Override
	public int compareTo(SmTransition transition) {

		return (transition == null || transition.getId() == null) ? 1
				: (this.getId() == null ? -1 : this.getId().compareTo(transition.getId()));
	}
}
