package com.revenga.rits.back.front.core.model.helper;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.ObjectUtils;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.Role;
import com.revenga.rits.back.data.core.model.RolePermission;
import com.revenga.rits.back.data.core.model.SystemPermission;
import com.revenga.rits.back.front.core.model.vo.RoleVO;
import com.revenga.rits.back.front.core.model.vo.SystemPermissionVO;

public class RoleHelper {

	private RoleHelper() {

		throw new IllegalStateException(this.getClass().getSimpleName());
	}

	public static RoleVO get(final Role role) {

		RoleVO roleVO = null;

		if (role != null) {

			roleVO = new RoleVO();

			assign(role, roleVO);
		}

		return roleVO;
	}

	public static Role get(final RoleVO roleVO) {

		Role role = null;

		if (roleVO != null) {

			role = new Role();

			if (roleVO.getId() != null) {

				Key key = new Key(roleVO.getId());

				if (key.getClazz() == Role.class) {

					role.setId(Long.valueOf(key.getId(1)));
				}
			}

			role.setAlias(roleVO.getAlias());
			role.setDescription((roleVO.getDescription()));
			role.setPreset(roleVO.getPreset());

			if (roleVO.getRolePermissionIds() != null) {

				Key key = null;

				List<RolePermission> rolePermissions = new ArrayList<>();

				for (String rolePermissionId : roleVO.getRolePermissionIds()) {

					key = new Key(rolePermissionId);

					if (key.getClazz() == SystemPermission.class) {

						RolePermission rolePermission = new RolePermission();
						rolePermission.setRoleId(role.getId());
						rolePermission.setSystemPermissionId(Long.valueOf(key.getId(1)));
						rolePermissions.add(rolePermission);
					}
				}
				role.setRolePermissions(rolePermissions);
			}
		}

		return role;

	}

	public static void assign(final Role role, RoleVO roleVO) {

		if (ObjectUtils.allNotNull(role, roleVO)) {

			BasicElementHelper.fillBasicProperties(roleVO, role);

			roleVO.setPreset(role.getPreset());

			if (role.getRolePermissions() != null) {

				List<SystemPermissionVO> rolePermissions = new ArrayList<>();

				for (RolePermission rolePermission : role.getRolePermissions()) {

					if (rolePermission.getSystemPermission() != null) {

						rolePermissions.add(SystemPermissionHelper.get(rolePermission.getSystemPermission()));
					}
				}
			}
		}
	}

}
