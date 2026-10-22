package com.revenga.rits.back.front.core.model.helper;

import com.revenga.rits.back.data.core.model.LogLevel;
import com.revenga.rits.back.front.core.model.vo.BasicElementVO.LogLevelEnum;

public class LogLevelHelper {

	private LogLevelHelper() {

		throw new IllegalStateException(this.getClass().getSimpleName());
	}

	public static Long get(final LogLevelEnum level) {

		Long resp = null;

		if (level != null) {

			switch (level) {

			case CRITICAL:
				resp = LogLevel.CRITICAL;
				break;

			case ERROR:
				resp = LogLevel.ERROR;
				break;

			case WARN:
				resp = LogLevel.WARN;
				break;

			case INFO:
				resp = LogLevel.INFO;
				break;

			case DEBUG:
				resp = LogLevel.DEBUG;
				break;

			case TRACE:
				resp = LogLevel.TRACE;
				break;
			}
		}

		return resp;
	}

	public static LogLevelEnum get(final Long level) {

		LogLevelEnum resp = null;

		if (level != null) {

			if (LogLevel.CRITICAL == level.intValue()) {
				
				resp = LogLevelEnum.CRITICAL;
				
			} else if (LogLevel.ERROR == level.intValue()) {
				
				resp = LogLevelEnum.ERROR;
				
			} else if (LogLevel.WARN == level.intValue()) {
				
				resp = LogLevelEnum.WARN;
				
			} else if (LogLevel.INFO == level.intValue()) {
				
				resp = LogLevelEnum.INFO;
				
			} else if (LogLevel.DEBUG == level.intValue()) {
				
				resp = LogLevelEnum.DEBUG;
				
			} else if (LogLevel.TRACE == level.intValue()) {
				
				resp = LogLevelEnum.TRACE;
			}
		}

		return resp;
	}
}
