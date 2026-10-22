package com.revenga.rits.back.data.core.model;

import com.revenga.rits.back.data.core.annotation.PrimaryKeys;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@PrimaryKeys(fields = {"id"})
public class SystemParamValue extends BasicElement {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7782827747896828945L;
	
	String value;
}
