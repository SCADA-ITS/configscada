package com.revenga.rits.back.service.graphql.mutation;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.revenga.rits.back.front.core.model.vo.RoleVO;
import com.revenga.rits.back.front.core.model.vo.UserVO;
import com.revenga.rits.back.service.graphql.service.RoleService;
import com.revenga.rits.back.service.graphql.service.UserService;

@Component
public class UserMutation implements GraphQLMutationResolver{

	@Autowired
	private UserService userService;

	@Autowired
	private RoleService roleService;
	
	public Optional<UserVO> createUser(final UserVO user) {

		return userService.create(user);
	}

	public Optional<UserVO> updateUser(final UserVO user) {

		return userService.update(user);
	}

	public Optional<UserVO> deleteUser(final String userId) {

		return userService.delete(userId);
	}

	//
	// Command
	//
	public Boolean sendKillUser(final List<String> userIds, final Integer lockedTimeS) {

		return userService.sendKillUser(userIds, lockedTimeS);
	}
	
	public Optional<RoleVO> createRole(final RoleVO role) {

		return roleService.create(role);
	}

	public Optional<RoleVO> updateRole(final RoleVO role) {

		return roleService.update(role);
	}

	public Optional<RoleVO> deleteRole(final String roleId) {

		return roleService.delete(roleId);
	}
}
