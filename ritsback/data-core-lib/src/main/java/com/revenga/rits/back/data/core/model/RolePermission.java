package com.revenga.rits.back.data.core.model;

import com.revenga.rits.back.data.core.annotation.AssociatedKeys;
import com.revenga.rits.back.data.core.annotation.PrimaryKeys;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = false)
@PrimaryKeys(fields = {"roleId", "systemPermissionId"})
public class RolePermission extends BasicElement {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3879797036799077118L;
	
	private Long roleId;
	private Long systemPermissionId;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@AssociatedKeys(references = {"systemPermissionId=id"})
	private SystemPermission systemPermission;
}
