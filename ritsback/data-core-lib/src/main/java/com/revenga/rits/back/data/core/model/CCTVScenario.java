package com.revenga.rits.back.data.core.model;

import com.revenga.rits.back.data.core.annotation.PrimaryKeys;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@PrimaryKeys(fields = {"id"})
public class CCTVScenario extends BasicElement {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3134332149044934185L;
		
	/**
	 * nRows Propertie
	 */
	private int nRows;
	
	/**
	 * nColumns Propertie
	 */
	private int nColumns;
}
