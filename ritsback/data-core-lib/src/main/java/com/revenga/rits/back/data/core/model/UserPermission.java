package com.revenga.rits.back.data.core.model;

import com.revenga.rits.back.data.core.annotation.AssociatedKeys;
import com.revenga.rits.back.data.core.annotation.PrimaryKeys;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = false)
@PrimaryKeys(fields = {"userId", "systemPermissionId"})
public class UserPermission extends BasicElement {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5974915588787093450L;
	
	private Long userId;
	private Long systemPermissionId;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@AssociatedKeys(references = {"systemPermissionId=id"})
	private SystemPermission systemPermission;
	
}
