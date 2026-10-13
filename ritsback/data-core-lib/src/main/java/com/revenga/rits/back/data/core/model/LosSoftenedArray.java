package com.revenga.rits.back.data.core.model;

import com.revenga.rits.back.data.core.annotation.AssociatedKeys;
import com.revenga.rits.back.data.core.annotation.PrimaryKeys;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = false)
@PrimaryKeys(fields = { "id" })
public class LosSoftenedArray extends BasicElement {

	/**
	 * 
	 */
	private static final long serialVersionUID = -989905503871604443L;

	private Long initLosId;
	private Long endLosId;
	private Integer countSoftened;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@AssociatedKeys(references = {"initLosId=id"})
	private LevelOfService initLos;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@AssociatedKeys(references = {"endLosId=id"})
	private LevelOfService endLos;
}
