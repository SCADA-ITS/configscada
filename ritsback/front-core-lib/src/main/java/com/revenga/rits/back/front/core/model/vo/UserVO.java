package com.revenga.rits.back.front.core.model.vo;

import java.util.List;

import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@EqualsAndHashCode(callSuper = false)
@Entity
public class UserVO extends BasicElementVO {
	/**
	* 
	*/
	private static final long serialVersionUID = 7383975199039479894L;

	private String name;
	private String password;
	private Long passwordExpiredAt;
	private Integer sessionExpireTimeS;
	private Long lockedUntil;
	private Boolean preset;
	private Boolean allowMultipleSessions;

	private String fullName;
	private String email;
	private String localeId;
	private String roleId;
	private Boolean ldap;
	
	@EqualsAndHashCode.Exclude
	private RoleVO role;

	@EqualsAndHashCode.Exclude
	private List<UserSessionVO> userSessions;
	
	@EqualsAndHashCode.Exclude
	private List<SystemPermissionVO> userPermissions;
	
	@EqualsAndHashCode.Exclude
	private List<RoleVO> roles;
	
	@EqualsAndHashCode.Exclude
	private List<String> userPermissionIds;
	
	@EqualsAndHashCode.Exclude
	private List<String> rolesUsersIds;
	
	@EqualsAndHashCode.Exclude
	private I18nLocaleVO locale;
	
	@EqualsAndHashCode.Exclude
	private List<ManagementAreaVO> managementAreas;
	
	@EqualsAndHashCode.Exclude
	private List<String> managementAreaIds;
	
}
