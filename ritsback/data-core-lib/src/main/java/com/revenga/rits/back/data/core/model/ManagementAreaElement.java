package com.revenga.rits.back.data.core.model;

import com.revenga.rits.back.data.core.annotation.PrimaryKeys;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@PrimaryKeys(fields = {"id", "elementTypeId", "elementId"})
public class ManagementAreaElement extends BasicElement {/**
	 * 
	 */
	private static final long serialVersionUID = -3837905819942696648L;
	private Long elementTypeId;
	private Long elementId;
}
