package com.revenga.rits.back.data.core.model;

import com.revenga.rits.back.data.core.annotation.PrimaryKeys;
import com.revenga.rits.back.data.core.util.ConfigProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 
 * Type of param
 * 
 * @author aborrajo
 * 
 */

@Data
@EqualsAndHashCode(callSuper = false)
@PrimaryKeys(fields = {"id"})
public class ParamType extends BasicElement {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5746979971218269570L;
	// region region Constants

	// region data types constants
	
	public static final long CONFIG = Long.parseLong(ConfigProperty.getInstance()
			.getProperty(BasicElement.FILE_CONSTANTS, "paramtype.config"));
	public static final long MEASURE = Long.parseLong(ConfigProperty.getInstance()
			.getProperty(BasicElement.FILE_CONSTANTS, "paramtype.measure"));

	// endregion data types constants
	
	// endregion Constants
	
//	private ParamType() {
//	    
//		throw new IllegalStateException(this.getClass().getSimpleName());
//	}
}
