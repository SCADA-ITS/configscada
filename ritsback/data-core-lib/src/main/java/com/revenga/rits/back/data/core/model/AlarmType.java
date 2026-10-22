package com.revenga.rits.back.data.core.model;

import com.revenga.rits.back.data.core.annotation.PrimaryKeys;
import com.revenga.rits.back.data.core.util.ConfigProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 
 * Defines type of alarms
 * 
 * @author aborrajo
 */
@Data
@EqualsAndHashCode(callSuper = false)
@PrimaryKeys(fields = {"id"})
public class AlarmType extends BasicElement {
		
	// region constants
	/**
	 * 
	 */
	private static final long serialVersionUID = 8114195603455400337L;

	// region data types constants
	
	public static final long ALARM = Integer.valueOf(ConfigProperty.getInstance()
			.getProperty(BasicElement.FILE_CONSTANTS, "alarm.type.alarm"));
	public static final long STATE_ALERT = Integer.valueOf(ConfigProperty.getInstance()
			.getProperty(BasicElement.FILE_CONSTANTS, "alarm.type.state_alert"));
	public static final long MEASURE_ALERT = Integer.valueOf(ConfigProperty.getInstance()
			.getProperty(BasicElement.FILE_CONSTANTS, "alarm.type.measure_alert"));
	public static final long NOTIFICATION  = Integer.valueOf(ConfigProperty.getInstance()
			.getProperty(BasicElement.FILE_CONSTANTS, "alarm.type.notification"));		
	
	// endregion data types constants
	
	// endregion
}
