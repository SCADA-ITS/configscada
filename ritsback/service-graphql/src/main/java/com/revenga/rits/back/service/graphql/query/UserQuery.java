package com.revenga.rits.back.service.graphql.query;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.revenga.rits.back.front.core.model.vo.RoleVO;
import com.revenga.rits.back.front.core.model.vo.SystemPermissionVO;
import com.revenga.rits.back.front.core.model.vo.UserVO;
import com.revenga.rits.back.service.graphql.service.RoleService;
import com.revenga.rits.back.service.graphql.service.SystemPermissionService;
import com.revenga.rits.back.service.graphql.service.UserService;

@Component
public class UserQuery implements GraphQLQueryResolver {

	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private SystemPermissionService systemPermissionService;
	
	public Optional<UserVO> getUser(final String userId) {

		return userService.get(userId);
	}
	
	public List<UserVO> getUsers() {

		return userService.getAll();
	}
	
	public Optional<RoleVO> getRole(final String roleId) {

		return roleService.get(roleId);
	}
	
	public List<RoleVO> getRoles() {

		return roleService.getAll();
	}
	
	public Optional<SystemPermissionVO> getSystemPermission(final String systemPermissionId) {

		return systemPermissionService.get(systemPermissionId);
	}
	
	public List<SystemPermissionVO> getSystemPermissions() {

		return systemPermissionService.getAll();
	}
}
