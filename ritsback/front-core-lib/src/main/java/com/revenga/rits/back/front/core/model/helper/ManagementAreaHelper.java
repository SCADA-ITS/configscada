package com.revenga.rits.back.front.core.model.helper;

import com.revenga.rits.back.data.core.model.ManagementArea;
import com.revenga.rits.back.front.core.model.vo.ManagementAreaVO;

public class ManagementAreaHelper {
	
	private ManagementAreaHelper() {

		super();
	}

	public static ManagementAreaVO get(ManagementArea managementArea) {

		ManagementAreaVO managementAreaVO = null;

		if (managementArea != null) {

			managementAreaVO = new ManagementAreaVO();

			assign(managementArea, managementAreaVO);
		}

		return managementAreaVO;
	}

	public static void assign(final ManagementArea managementArea, ManagementAreaVO managementAreaVO) {

		if (managementArea != null && managementAreaVO != null) {

			BasicElementHelper.fillBasicProperties(managementAreaVO, managementArea);

		}
	}

}
