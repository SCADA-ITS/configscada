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
public class User extends BasicElement {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3734751516954229421L;
	
	public static final long USER_SYSTEM = Long.parseLong(
			ConfigProperty.getInstance().getProperty(BasicElement.FILE_CONSTANTS, "user.system"));
	
	private Long roleId;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@AssociatedKeys(references = {"roleId=id"})
	private Role role;
	
	private String name;
	private String password;
	private Long passwordExpiredAt;
	private Integer sessionExpireTimeS;
	private Long lockedUntil;
	private Boolean preset;
	private Boolean allowMultipleSessions;
	
	private String fullName;
	private String email;
	private String localeCode;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@AssociatedKeys(references = {"id=userId"})
	private List<UserPermission> userPermissions;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@AssociatedKeys(references = {"id=userId"})
	private List<RolesUsers> rolesUsers;
}
