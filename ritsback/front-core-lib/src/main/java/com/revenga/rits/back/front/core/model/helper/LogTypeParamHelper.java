package com.revenga.rits.back.front.core.model.helper;

import org.apache.commons.lang3.ObjectUtils;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.LogTypeParam;
import com.revenga.rits.back.front.core.model.vo.LogTypeParamVO;

public class LogTypeParamHelper {
	
	private LogTypeParamHelper() {

		throw new IllegalStateException(this.getClass().getSimpleName());
	}

	public static LogTypeParamVO get(final LogTypeParam logTypeParam) {

		LogTypeParamVO logTypeParamVO = null;

		if (logTypeParam != null) {

			logTypeParamVO = new LogTypeParamVO();

			assign(logTypeParam, logTypeParamVO);
		}

		return logTypeParamVO;
	}

	public static LogTypeParam get(final LogTypeParamVO logTypeParamVO) {

		LogTypeParam logTypeParam = null;

		if (logTypeParamVO != null) {

			logTypeParam = new LogTypeParam();
			if (logTypeParamVO.getId() != null) {

				Key key = new Key(logTypeParamVO.getId());

				if (key.getClazz() == LogTypeParam.class) {

					logTypeParam.setId(Long.valueOf(key.getId(1)));
				}
			}

			logTypeParam.setAlias(logTypeParamVO.getAlias());
			logTypeParam.setDescription((logTypeParamVO.getDescription()));

		}

		return logTypeParam;
	}

	public static void assign(final LogTypeParam logTypeParam, LogTypeParamVO logTypeParamVO) {

		if (ObjectUtils.allNotNull(logTypeParam, logTypeParamVO)) {

			BasicElementHelper.fillBasicProperties(logTypeParamVO, logTypeParam);


		}
	}


}
