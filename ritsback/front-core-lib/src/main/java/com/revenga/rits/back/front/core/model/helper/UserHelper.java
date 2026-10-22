package com.revenga.rits.back.front.core.model.helper;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.ObjectUtils;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.I18nLocale;
import com.revenga.rits.back.data.core.model.ManagementArea;
import com.revenga.rits.back.data.core.model.ManagementAreaUser;
import com.revenga.rits.back.data.core.model.Role;
import com.revenga.rits.back.data.core.model.SystemPermission;
import com.revenga.rits.back.data.core.model.User;
import com.revenga.rits.back.data.core.model.UserPermission;
import com.revenga.rits.back.data.core.model.RolesUsers;
import com.revenga.rits.back.front.core.model.vo.RoleVO;
import com.revenga.rits.back.front.core.model.vo.SystemPermissionVO;
import com.revenga.rits.back.front.core.model.vo.UserVO;


public class UserHelper {

	private UserHelper() {

		throw new IllegalStateException(this.getClass().getSimpleName());
	}

	public static UserVO get(final User user) {

		UserVO userVO = null;

		if (user != null) {

			userVO = new UserVO();

			assign(user, userVO);
		}

		return userVO;
	}

	public static User get(final UserVO userVO) {

		User user = null;

		if (userVO != null) {

			user = new User();
			if (userVO.getId() != null) {

				Key key = new Key(userVO.getId());

				if (key.getClazz() == User.class) {

					user.setId(Long.valueOf(key.getId(1)));
				}
			}

			user.setAlias(userVO.getAlias());
			user.setDescription((userVO.getDescription()));
			user.setName(userVO.getName());
			user.setPassword(userVO.getPassword());
			user.setPasswordExpiredAt(userVO.getPasswordExpiredAt());
			user.setSessionExpireTimeS(userVO.getSessionExpireTimeS());
			user.setLockedUntil(userVO.getLockedUntil());
			user.setPreset(userVO.getPreset());
			user.setAllowMultipleSessions((userVO.getAllowMultipleSessions()));
			user.setFullName(userVO.getFullName());
			user.setEmail(userVO.getEmail());

			if (userVO.getRoleId() != null) {

			Key key = new Key(userVO.getRoleId());

				if (key.getClazz() == Role.class) {
	
					user.setRoleId(Long.valueOf(key.getId(1)));
				}
			}

			if (userVO.getRole() != null) {

				Role role = RoleHelper.get(userVO.getRole());

				if (role != null) {

					if (userVO.getRoleId() == null) {

						user.setRoleId(role.getId());
					}
					user.setRole(role);
				}
			}

			if (userVO.getUserPermissionIds() != null) {

				Key key = null;

				List<UserPermission> userPermissions = new ArrayList<>();

				for (String userPermissionId : userVO.getUserPermissionIds()) {

					key = new Key(userPermissionId);

					if (key.getClazz() == SystemPermission.class) {

						UserPermission userPermission = new UserPermission();
						userPermission.setUserId(user.getId());
						userPermission.setSystemPermissionId(Long.valueOf(key.getId(1)));
						userPermissions.add(userPermission);
					}
				}
				user.setUserPermissions(userPermissions);
			}
			
			if (userVO.getRolesUsersIds() != null) {

				Key key = null;

				List<RolesUsers> rolesUsers = new ArrayList<>();

				for (String rolesUserId : userVO.getRolesUsersIds()) {

					key = new Key(rolesUserId);

					if (key.getClazz() == Role.class) {

						RolesUsers rolesUser = new RolesUsers();
						rolesUser.setUserId(user.getId());
						rolesUser.setRoleId(Long.valueOf(key.getId(1)));
						rolesUsers.add(rolesUser);
					}
				}
				user.setRolesUsers(rolesUsers);
			}
			
			if (userVO.getManagementAreaIds()!= null) {

				Key key = null;

				List<ManagementAreaUser> userManagementAreas = new ArrayList<>();

				for (String managementAreaId : userVO.getManagementAreaIds()) {

					key = new Key(managementAreaId);

					if (key.getClazz() == ManagementArea.class) {

						ManagementAreaUser userManagementArea = new ManagementAreaUser();
						userManagementArea.setId(Long.valueOf(key.getId(1)));
						userManagementArea.setUserId(user.getId());
						userManagementAreas.add(userManagementArea);
					}
				}
				
				user.setUserManagementAreas(userManagementAreas);
			}
			
			if (userVO.getLocaleId() != null) {

				Key key = new Key(userVO.getLocaleId());

				if (key.getClazz() == I18nLocale.class) {

					user.setLocaleCode(key.getId(1));
				}
			}
		}

		return user;
	}

	public static void assign(final User user, UserVO userVO) {

		if (ObjectUtils.allNotNull(user, userVO)) {

			BasicElementHelper.fillBasicProperties(userVO, user);

			userVO.setName(user.getName());
			userVO.setPassword(user.getPassword());
			userVO.setPasswordExpiredAt(user.getPasswordExpiredAt());
			userVO.setSessionExpireTimeS(user.getSessionExpireTimeS());
			userVO.setLockedUntil(user.getLockedUntil());
			userVO.setPreset(user.getPreset());
			userVO.setAllowMultipleSessions(user.getAllowMultipleSessions());
			userVO.setFullName(user.getFullName());
			userVO.setEmail(user.getEmail());

			if (user.getRoleId() != null) {

				Role role = new Role();
				role.setId(user.getRoleId());
				userVO.setRoleId(BasicElementHelper.getIdFromElement(role));
			}

			userVO.setRole(RoleHelper.get(user.getRole()));

			if (user.getRoleId() == null && user.getRole() != null && user.getRole().getId() != null) {

				userVO.setRoleId(BasicElementHelper.getIdFromElement(user.getRole()));
			}

			if (user.getUserPermissions() != null) {

				List<SystemPermissionVO> userPermissions = null;

				for (UserPermission userPermission : user.getUserPermissions()) {

					if (userPermission.getSystemPermission() != null) {

						if(userPermissions == null){
							
							userPermissions = new ArrayList<>();
						}

						userPermissions.add(SystemPermissionHelper.get(userPermission.getSystemPermission()));
					}
				}
					
				userVO.setUserPermissions(userPermissions);
			}
			
			if (user.getRolesUsers() != null) {

				List<RoleVO> roles = null;

				for (RolesUsers rolesUser : user.getRolesUsers()) {

					if (rolesUser.getRole() != null) {
						
						if(roles == null){
							
							roles =  new ArrayList<>();
						}
						roles.add(RoleHelper.get(rolesUser.getRole()));
					}
				}
					
				userVO.setRoles(roles);
			}
		}
	}
}
