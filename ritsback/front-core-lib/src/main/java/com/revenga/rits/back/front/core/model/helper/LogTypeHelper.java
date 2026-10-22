package com.revenga.rits.back.front.core.model.helper;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.ObjectUtils;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.LogType;
import com.revenga.rits.back.data.core.model.LogTypeParam;
import com.revenga.rits.back.front.core.model.vo.LogTypeParamVO;
import com.revenga.rits.back.front.core.model.vo.LogTypeVO;

public class LogTypeHelper {

	private LogTypeHelper() {

		throw new IllegalStateException(this.getClass().getSimpleName());
	}

	public static LogTypeVO get(final LogType logType) {

		LogTypeVO logTypeVO = null;

		if (logType != null) {

			logTypeVO = new LogTypeVO();

			assign(logType, logTypeVO);
		}

		return logTypeVO;
	}

	public static LogType get(final LogTypeVO logTypeVO) {

		LogType logType = null;

		if (logTypeVO != null) {

			logType = new LogType();

			if (logTypeVO.getId() != null) {

				Key key = new Key(logTypeVO.getId());

				if (key.getClazz() == LogType.class) {

					logType.setId(Long.valueOf(key.getId(1)));
				}
			}

			logType.setAlias(logTypeVO.getAlias());
			logType.setDescription((logTypeVO.getDescription()));

			if (logTypeVO.getLogTypeParams() != null) {

				List<LogTypeParam> logTypeParams = new ArrayList<>();

				for (LogTypeParamVO logTypeParamVO : logTypeVO.getLogTypeParams()) {

					LogTypeParam logTypeParam = LogTypeParamHelper.get(logTypeParamVO);

					logTypeParams.add(logTypeParam);
				}

				logType.setLogTypeParams(logTypeParams);
			}

		}

		return logType;
	}

	public static void assign(final LogType logType, LogTypeVO logTypeVO) {

		if (ObjectUtils.allNotNull(logType, logTypeVO)) {

			BasicElementHelper.fillBasicProperties(logTypeVO, logType);

			if (logType.getLogTypeParams() != null) {

				List<LogTypeParamVO> logTypeParamsVO = new ArrayList<>();

				for (LogTypeParam logTypeParam : logType.getLogTypeParams()) {

					if (logTypeParam.getLogTypeId() != null) {

						logTypeParamsVO.add(LogTypeParamHelper.get(logTypeParam));
					}
				}

				logTypeVO.setLogTypeParams(logTypeParamsVO);
			}

		}
	}

}
