package com.revenga.rits.back.front.core.model.helper;

import org.apache.commons.lang3.ObjectUtils;

import com.revenga.rits.back.data.core.model.SystemParam;
import com.revenga.rits.back.data.core.model.SystemParamValue;
import com.revenga.rits.back.front.core.model.vo.SystemParamVO;


public class SystemParamHelper {

	private SystemParamHelper() {

		throw new IllegalStateException(this.getClass().getSimpleName());
	}

	public static SystemParamVO get(final SystemParam systemParam, SystemParamValue systemParamValue) {

		SystemParamVO systemParamVO = null;

		if (systemParam != null) {

			systemParamVO = new SystemParamVO();

			assign(systemParam, systemParamVO);
			
			if(systemParamValue != null) {
				systemParamVO.setValue(systemParamValue.getValue());
			}
		}

		return systemParamVO;
	}
	
	public static void assign(final SystemParam systemParam, SystemParamVO systemParamVO) {

		if (ObjectUtils.allNotNull(systemParam, systemParamVO)) {

			BasicElementHelper.fillBasicProperties(systemParamVO, systemParam);

		}
	}
}
