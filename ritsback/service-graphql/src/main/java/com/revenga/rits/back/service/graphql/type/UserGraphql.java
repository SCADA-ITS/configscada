package com.revenga.rits.back.service.graphql.type;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;

import com.revenga.rits.back.data.core.model.I18nLocale;
import com.revenga.rits.back.data.core.model.Role;
import com.revenga.rits.back.data.core.model.User;
import com.revenga.rits.back.data.core.model.UserPermission;
import com.revenga.rits.back.data.core.model.UserSession;
import com.revenga.rits.back.data.core.model.RolesUsers;
import com.revenga.rits.back.data.core.model.SystemPermission;
import com.revenga.rits.back.front.core.model.helper.UserHelper;
import com.revenga.rits.back.front.core.model.vo.I18nLocaleVO;
import com.revenga.rits.back.front.core.model.vo.RoleVO;
import com.revenga.rits.back.front.core.model.vo.SystemPermissionVO;
import com.revenga.rits.back.front.core.model.vo.UserSessionVO;
import com.revenga.rits.back.front.core.model.vo.UserVO;
import com.revenga.rits.back.service.graphql.service.EntitiesManager;

import lombok.EqualsAndHashCode;
import lombok.extern.log4j.Log4j2;

@Log4j2
@EqualsAndHashCode(callSuper = false)
public class UserGraphql extends UserVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6751489471120347085L;
	private User user;

	public UserGraphql(final User user) {

		UserHelper.assign(user, this);

		this.user = user;
	}

	@Override
	public RoleVO getRole() {

		if (super.getRole() == null) {

			if (user != null) {

				Role role = EntitiesManager.getInstance().getRoleRepository().get(user.getRoleId(), false);

				if (role != null) {

					super.setRole(new RoleGraphql(role));
				}

			} else {

				log.error("user has null pointer");
			}

		}

		return super.getRole();
	}

	@Override
	public List<UserSessionVO> getUserSessions() {

		if (super.getUserSessions() == null) {

			if (user != null) {

				List<UserSession> sessions = EntitiesManager.getInstance().getUserSessionRepository()
						.getSessionsByUser(user.getId(), false);

				if (!CollectionUtils.isEmpty(sessions)) {
					List<UserSessionVO> userSessions = new ArrayList<>();

					for (UserSession session : sessions) {

						userSessions.add(new UserSessionGraphql(session));
					}

					super.setUserSessions(userSessions);
				}
			} else {

				log.error("user has null pointer");
			}
		}
		return super.getUserSessions();
	}

	@Override
	public List<SystemPermissionVO> getUserPermissions() {

		if (super.getUserPermissions() == null) {

			if (user != null) {

				List<UserPermission> permissions = EntitiesManager.getInstance().getUserPermissionRepository()
						.getsByUser(user.getId(), false);

				if (!CollectionUtils.isEmpty(permissions)) {

					List<SystemPermissionVO> userPermissions = new ArrayList<>();

					for (UserPermission userPermission : permissions) {

						SystemPermission systemPermission = EntitiesManager.getInstance()
								.getSystemPermissionRepository().get(userPermission.getSystemPermissionId(), false);

						if (systemPermission != null) {

							userPermissions.add(new SystemPermissionGraphql(systemPermission));
						}
					}
					
					super.setUserPermissions(userPermissions);
				}
			} else {

				log.error("user has null pointer");
			}
		}
		return super.getUserPermissions();
	}

	@Override
	public List<RoleVO> getRoles() {

		if (super.getRoles() == null) {

			if (user != null && user.getId() != null) {

				List<RolesUsers> usersRoles = EntitiesManager.getInstance().getRolesUsersRepository()
						.getsByUser(user.getId(), false);

				if (!CollectionUtils.isEmpty(usersRoles)) {

					List<RoleVO> roles = null;

					for (RolesUsers usersRole : usersRoles) {

						Role role = EntitiesManager.getInstance().getRoleRepository().get(usersRole.getRoleId(), false);

						if (role != null) {

							if (roles == null) {
								roles = new ArrayList<>();
							}
							roles.add(new RoleGraphql(role));
						}
					}

					super.setRoles(roles);
				}
			} else {

				log.error("user has null pointer");
			}
		}
		return super.getRoles();
	}

	@Override
	public I18nLocaleVO getLocale() {

		if (super.getLocale() == null) {

			if (user != null) {

				I18nLocale locale = EntitiesManager.getInstance().getI18nLocaleRepository().get(user.getLocaleCode(),
						false);

				if (locale != null) {

					super.setLocale(new I18nLocaleGraphql(locale));
				}

			} else {

				log.error("user has null pointer");
			}
		}

		return super.getLocale();
	}
}
