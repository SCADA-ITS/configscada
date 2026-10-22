package com.revenga.rits.back.data.core.model;

import com.revenga.rits.back.data.core.annotation.AssociatedKeys;
import com.revenga.rits.back.data.core.annotation.PrimaryKeys;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@PrimaryKeys(fields = { "id" })
public class SmTimer extends BasicElement {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8675141776868104817L;

	private Long thresholdId;
	private Long elementTypeId;
	private Long paramTypeId;
	private Long elementTypeParamId;
	private Integer minElementsMatchThreshold;

	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@AssociatedKeys(references = { "thresholdId=id" })
	private SmThreshold threshold;

	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@AssociatedKeys(references = { "elementTypeId=elementTypeId", "paramTypeId=paramTypeId", "elementTypeParamId=id" })
	private ElementTypeParam elementTypeParam;

	@Override
	public int hashCode() {

		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {

		return super.equals(obj);
	}

}
