package com.revenga.rits.back.service.graphql.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.Role;
import com.revenga.rits.back.front.core.model.helper.RoleHelper;
import com.revenga.rits.back.front.core.model.vo.RoleVO;
import com.revenga.rits.back.service.graphql.type.RoleGraphql;

@Service
public class RoleService implements QueryInterface<RoleVO>, MutationInterface<RoleVO> {

	@Override
	public List<RoleVO> getAll() {

		List<RoleVO> roles = new ArrayList<>();

		for (Role role : EntitiesManager.getInstance().getRoleRepository().getAll(false)) {

			roles.add(new RoleGraphql(role));
		}

		return roles;
	}

	@Override
	public Optional<RoleVO> get(String roleId) {

		Optional<RoleVO> roleVO = null;

		Key key = new Key(roleId);

		if (key.getClazz() == Role.class) {
			
			Role role = EntitiesManager.getInstance().getRoleRepository().get(Long.valueOf(key.getId(1)), false);

			if (role != null) {

				roleVO = Optional.of(new RoleGraphql(role));

			}
		}
		return roleVO;
	}

	@Override
	public Optional<RoleVO> create(RoleVO roleVO) {

		Optional<RoleVO> resp = null;

		if (roleVO != null) {

			Role role = RoleHelper.get(roleVO);

			Role roleCreated = EntitiesManager.getInstance().getRoleRepository().create(role);

			if (roleCreated != null) {

				resp = Optional.of(new RoleGraphql(roleCreated));
			}
		}
		return resp;
	}

	@Override
	public Optional<RoleVO> clone(String item) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<RoleVO> update(RoleVO roleVO) {

		Optional<RoleVO> resp = null;

		if (roleVO != null) {

			Role role = RoleHelper.get(roleVO);

			Role roleCreated = EntitiesManager.getInstance().getRoleRepository().update(role);

			if (roleCreated != null) {

				resp = Optional.of(new RoleGraphql(roleCreated));
			}
		}
		return resp;
	}

	@Override
	public Optional<RoleVO> delete(final String roleId) {

		Optional<RoleVO> resp = null;

		if (roleId != null) {

			RoleVO roleVO = new RoleVO();
			roleVO.setId(roleId);

			Role role = RoleHelper.get(roleVO);

			Role roleDeleted = EntitiesManager.getInstance().getRoleRepository().delete(role);

			if (roleDeleted != null) {

				resp = Optional.of(new RoleGraphql(roleDeleted));
			}
		}

		return resp;
	}

	@Override
	public List<RoleVO> gets(List<String> idItems) {
		// TODO Auto-generated method stub
		return null;
	}

}
