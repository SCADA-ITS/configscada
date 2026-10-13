package com.revenga.rits.back.data.core.model;

import com.revenga.rits.back.data.core.annotation.PrimaryKeys;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@PrimaryKeys(fields = {"elementTypeId", "elementId"})
public class ElementState extends BasicElement {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4622343366586425656L;

	private Long elementTypeId;
	private Long elementId;
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private Element element;
	private Long previousStateId;
	private Long stateId;
	private Boolean InheritedState;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private ElementTypeState previousState;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private ElementTypeState state;
	
	public ElementState() {
		super();
		
		this.previousStateId = ElementTypeState.STATE_UNKNOWN;
		this.stateId = ElementTypeState.STATE_UNKNOWN;
	}
	
	@Override
	public int hashCode() {

		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {

		return super.equals(obj);
	}
}