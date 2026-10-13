package com.revenga.rits.back.data.core.model;

import com.revenga.rits.back.data.core.util.ConfigProperty;

import lombok.Data;

@Data
public class LogLevel {

	public static final long CRITICAL = Long
			.parseLong(ConfigProperty.getInstance().getProperty(BasicElement.FILE_CONSTANTS, "log.level.critical"));
	
	public static final long ERROR = Long
			.parseLong(ConfigProperty.getInstance().getProperty(BasicElement.FILE_CONSTANTS, "log.level.error"));
	
	public static final long WARN = Long
			.parseLong(ConfigProperty.getInstance().getProperty(BasicElement.FILE_CONSTANTS, "log.level.warn"));
	
	public static final long INFO = Long
			.parseLong(ConfigProperty.getInstance().getProperty(BasicElement.FILE_CONSTANTS, "log.level.info"));
	
	public static final long DEBUG = Long
			.parseLong(ConfigProperty.getInstance().getProperty(BasicElement.FILE_CONSTANTS, "log.level.debug"));
	
	public static final long TRACE = Long
			.parseLong(ConfigProperty.getInstance().getProperty(BasicElement.FILE_CONSTANTS, "log.level.trace"));

}
