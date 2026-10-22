package com.revenga.rits.back.service.graphql.type;

import com.revenga.rits.back.data.core.model.SystemPermission;
import com.revenga.rits.back.front.core.model.helper.SystemPermissionHelper;
import com.revenga.rits.back.front.core.model.vo.SystemPermissionVO;

public class SystemPermissionGraphql extends SystemPermissionVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -917229244859533052L;

	/**
	 * 
	 */

	public SystemPermissionGraphql(final SystemPermission systemPermission) {

		SystemPermissionHelper.assign(systemPermission, this);

	}
}
