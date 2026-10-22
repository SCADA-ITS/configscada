package com.revenga.rits.back.front.core.model.helper;

import org.apache.commons.lang3.ObjectUtils;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.SystemPermission;
import com.revenga.rits.back.front.core.model.vo.SystemPermissionVO;


public class SystemPermissionHelper {

	private SystemPermissionHelper() {

		throw new IllegalStateException(this.getClass().getSimpleName());
	}

	public static SystemPermissionVO get(final SystemPermission systemPermission) {

		SystemPermissionVO systemPermissionVO = null;

		if (systemPermission != null) {

			systemPermissionVO = new SystemPermissionVO();

			assign(systemPermission, systemPermissionVO);
		}

		return systemPermissionVO;
	}

	public static SystemPermission get(final SystemPermissionVO systemPermissionVO) {

		SystemPermission systemPermission = null;

		if (systemPermissionVO != null) {

			systemPermission = new SystemPermission();

			if (systemPermissionVO.getId() != null) {

				Key key = new Key(systemPermissionVO.getId());

				if (key.getClazz() == SystemPermission.class) {

					systemPermission.setId(Long.valueOf(key.getId(1)));
				}

			}
			systemPermission.setAlias(systemPermissionVO.getAlias());
			systemPermission.setDescription((systemPermissionVO.getDescription()));
		}

		return systemPermission;
	}

	public static void assign(final SystemPermission systemPermission, SystemPermissionVO systemPermissionVO) {

		if (ObjectUtils.allNotNull(systemPermission, systemPermissionVO)) {

			BasicElementHelper.fillBasicProperties(systemPermissionVO, systemPermission);

		}
	}
}
