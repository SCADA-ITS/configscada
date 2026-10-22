package com.revenga.rits.back.data.core.model;

import java.util.List;

import com.revenga.rits.back.data.core.annotation.AssociatedKeys;
import com.revenga.rits.back.data.core.annotation.PrimaryKeys;
import com.revenga.rits.back.data.core.util.ConfigProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = false)
@PrimaryKeys(fields = {"id"})
public class Role extends BasicElement {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1186294472151574952L;

	private Boolean preset;
	
	public static final int ROL_SYSTEM = Integer.parseInt(
			ConfigProperty.getInstance().getProperty(BasicElement.FILE_CONSTANTS, "rol.system"));
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@AssociatedKeys(references = {"id=roleId"})
	private List<RolePermission> rolePermissions;
}
