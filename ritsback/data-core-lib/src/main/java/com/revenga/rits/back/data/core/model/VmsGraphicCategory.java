package com.revenga.rits.back.data.core.model;

import com.revenga.rits.back.data.core.annotation.PrimaryKeys;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@PrimaryKeys(fields = { "id" })
public class VmsGraphicCategory extends BasicElement {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1146566713164442025L;

	

}
