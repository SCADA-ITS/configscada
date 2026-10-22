package com.revenga.rits.back.data.core.model;

import com.revenga.rits.back.data.core.annotation.PrimaryKeys;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@PrimaryKeys(fields = {"id", "userId"})
public class ManagementAreaUser extends BasicElement {/**
	 * 
	 */
	private static final long serialVersionUID = -3837905819942696648L;
	private Long userId;

}
