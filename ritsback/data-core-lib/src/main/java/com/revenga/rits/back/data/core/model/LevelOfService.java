package com.revenga.rits.back.data.core.model;

import com.revenga.rits.back.data.core.annotation.PrimaryKeys;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@PrimaryKeys(fields = { "id" })
public class LevelOfService extends BasicElement {

	/**
	 * 
	 */
	private static final long serialVersionUID = -989905503871604443L;
	
	private Integer los;
	private String colour;
}
