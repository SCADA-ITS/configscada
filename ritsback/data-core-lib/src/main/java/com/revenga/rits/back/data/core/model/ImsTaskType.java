package com.revenga.rits.back.data.core.model;

import com.revenga.rits.back.data.core.annotation.PrimaryKeys;
import com.revenga.rits.back.data.core.util.ConfigProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 
 * Defines task of alarms
 * 
 * @author aborrajo
 */
@Data
@EqualsAndHashCode(callSuper = false)
@PrimaryKeys(fields = {"id"})
public class ImsTaskType extends BasicElement {
		
	// region constants
	/**
	 * 
	 */
	private static final long serialVersionUID = 8114195603455400337L;

	// region data types constants
	
	public static final long SIGNALING = Long.parseLong(ConfigProperty.getInstance()
			.getProperty(BasicElement.FILE_CONSTANTS, "ims.task.type.signaling"));

	// endregion data types constants
	
	// endregion
}
