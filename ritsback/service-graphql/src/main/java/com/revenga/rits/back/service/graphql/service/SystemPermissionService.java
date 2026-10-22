package com.revenga.rits.back.service.graphql.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.SystemPermission;
import com.revenga.rits.back.front.core.model.vo.SystemPermissionVO;
import com.revenga.rits.back.service.graphql.type.SystemPermissionGraphql;




@Service
public class SystemPermissionService implements QueryInterface<SystemPermissionVO> {

	@Override
	public List<SystemPermissionVO> getAll() {

		List<SystemPermissionVO> systemPermissions = new ArrayList<>();

		for (SystemPermission systemPermission : EntitiesManager.getInstance().getSystemPermissionRepository()
				.getAll(false)) {

			systemPermissions.add(new SystemPermissionGraphql(systemPermission));
		}

		return systemPermissions;
	}

	@Override
	public Optional<SystemPermissionVO> get(String systemPermissionId) {

		Optional<SystemPermissionVO> systemPermissionVO = null;

		Key key = new Key(systemPermissionId);
		
		if (key.getClazz() == SystemPermission.class) {

			SystemPermission systemPermission = EntitiesManager.getInstance().getSystemPermissionRepository()
					.get(Long.valueOf(key.getId(1)), false);

			if (systemPermission != null) {

				systemPermissionVO = Optional.of(new SystemPermissionGraphql(systemPermission));

			}
		}
		return systemPermissionVO;
	}

	@Override
	public List<SystemPermissionVO> gets(List<String> idItems) {
		// TODO Auto-generated method stub
		return null;
	}

}
