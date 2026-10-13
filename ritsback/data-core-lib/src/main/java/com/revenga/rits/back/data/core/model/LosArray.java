package com.revenga.rits.back.data.core.model;

import com.revenga.rits.back.data.core.annotation.AssociatedKeys;
import com.revenga.rits.back.data.core.annotation.PrimaryKeys;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = false)
@PrimaryKeys(fields = { "losThresholdSpeed", "losThresholdOccupancy" })
public class LosArray extends BasicElement {

	/**
	 * 
	 */
	private static final long serialVersionUID = -989905503871604443L;

	private Integer losThresholdSpeed;
	private Integer losThresholdOccupancy;
	private Long levelOfServiceId;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@AssociatedKeys(references = {"levelOfServiceId=id"})
	private LevelOfService levelOfService;
	
}
