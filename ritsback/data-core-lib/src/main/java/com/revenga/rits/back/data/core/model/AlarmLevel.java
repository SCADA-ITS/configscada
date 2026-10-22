package com.revenga.rits.back.data.core.model;

import com.revenga.rits.back.data.core.annotation.PrimaryKeys;
import com.revenga.rits.back.data.core.util.ConfigProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 
 * Defines level of alarms
 * 
 * @author aborrajo
 */
@Data
@EqualsAndHashCode(callSuper = false)
@PrimaryKeys(fields = {"id"})
public class AlarmLevel extends BasicElement {
	
	// region region Constants
	/**
	 * 
	 */
	private static final long serialVersionUID = -3166439535016524163L;

	// region data types constants
	
	public static final long LEVEL_MAINTENANCE = Long.parseLong(ConfigProperty.getInstance()
			.getProperty(BasicElement.FILE_CONSTANTS, "alarm.level.maintenance"));
	public static final long LEVEL_0 = Long.parseLong(ConfigProperty.getInstance()
			.getProperty(BasicElement.FILE_CONSTANTS, "alarm.level.0"));
	public static final long LEVEL_1 = Long.parseLong(ConfigProperty.getInstance()
			.getProperty(BasicElement.FILE_CONSTANTS, "alarm.level.1"));
	public static final long LEVEL_2 = Long.parseLong(ConfigProperty.getInstance()
			.getProperty(BasicElement.FILE_CONSTANTS, "alarm.level.2"));
	public static final long LEVEL_3 = Long.parseLong(ConfigProperty.getInstance()
			.getProperty(BasicElement.FILE_CONSTANTS, "alarm.level.3"));
	public static final long LEVEL_4 = Long.parseLong(ConfigProperty.getInstance()
			.getProperty(BasicElement.FILE_CONSTANTS, "alarm.level.4"));
	
	// endregion data types constants
	
	// endregion Constants	
}
