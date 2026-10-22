package com.revenga.rits.back.data.core.model;

import com.revenga.rits.back.data.core.annotation.PrimaryKeys;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@PrimaryKeys(fields = { "id" })
public class SmThreshold extends BasicElement {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3723756927276766080L;

	private Long dataTypeId;	
	private String value;
	

}