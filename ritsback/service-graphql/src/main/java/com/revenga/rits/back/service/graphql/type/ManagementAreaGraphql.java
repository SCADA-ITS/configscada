package com.revenga.rits.back.service.graphql.type;

import com.revenga.rits.back.data.core.model.ManagementArea;
import com.revenga.rits.back.front.core.model.helper.ManagementAreaHelper;
import com.revenga.rits.back.front.core.model.vo.ManagementAreaVO;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
public class ManagementAreaGraphql extends ManagementAreaVO {

	private static final long serialVersionUID = -5081806387727870958L;

	private ManagementArea managementArea;

	public ManagementAreaGraphql(final ManagementArea managementArea) {

		ManagementAreaHelper.assign(managementArea, this);
	}
}
