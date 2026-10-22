package com.revenga.rits.back.front.core.model.helper;

import org.apache.commons.lang3.ObjectUtils;

import com.revenga.rits.back.data.core.model.LogSubtype;
import com.revenga.rits.back.front.core.model.vo.LogSubtypeVO;

public class LogSubtypeHelper {

	private LogSubtypeHelper() {

		throw new IllegalStateException(this.getClass().getSimpleName());
	}

	public static LogSubtypeVO get(final LogSubtype logSubType) {

		LogSubtypeVO logSubtypeVO = null;

		if (logSubType != null) {

			logSubtypeVO = new LogSubtypeVO();

			assign(logSubType, logSubtypeVO);
		}

		return logSubtypeVO;
	}

	public static void assign(final LogSubtype logSubtype, LogSubtypeVO logSubtypeVO) {

		if (ObjectUtils.allNotNull(logSubtype, logSubtypeVO)) {

			BasicElementHelper.fillBasicProperties(logSubtypeVO, logSubtype);

		}
	}

}
