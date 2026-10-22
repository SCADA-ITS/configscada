package com.revenga.rits.back.service.graphql.type;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;

import com.revenga.rits.back.data.core.model.Role;
import com.revenga.rits.back.data.core.model.RolePermission;
import com.revenga.rits.back.data.core.model.SystemPermission;
import com.revenga.rits.back.front.core.model.helper.RoleHelper;
import com.revenga.rits.back.front.core.model.vo.RoleVO;
import com.revenga.rits.back.front.core.model.vo.SystemPermissionVO;
import com.revenga.rits.back.service.graphql.service.EntitiesManager;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class RoleGraphql extends RoleVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7093429182127552758L;
	private Role role;

	public RoleGraphql(final Role role) {

		RoleHelper.assign(role, this);

		this.role = role;
	}

	@Override
	public List<SystemPermissionVO> getRolePermissions() {

		if (super.getRolePermissions() == null) {

			if (role != null) {

				List<RolePermission> permissions = EntitiesManager.getInstance().getRolePermissionRepository()
						.getsByRole(role.getId(), false);

				if (!CollectionUtils.isEmpty(permissions)) {

					List<SystemPermissionVO> rolePermissions = new ArrayList<>();

					for (RolePermission rolePermission : permissions) {

						SystemPermission systemPermission = EntitiesManager.getInstance()
								.getSystemPermissionRepository().get(rolePermission.getSystemPermissionId(), false);

						if (systemPermission != null) {

							rolePermissions.add(new SystemPermissionGraphql(systemPermission));
						}
					}
					
					super.setRolePermissions(rolePermissions);
				}
			} else {

				log.error("role has null pointer");
			}
		}
		return super.getRolePermissions();
	}

}
