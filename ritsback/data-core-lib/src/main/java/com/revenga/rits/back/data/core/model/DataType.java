package com.revenga.rits.back.data.core.model;

import com.revenga.rits.back.data.core.annotation.PrimaryKeys;
import com.revenga.rits.back.data.core.util.ConfigProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 
 * Data type: Integer, alphanumeric,...
 * 
 * @author aborrajo
 * 
 */

@Data
@EqualsAndHashCode(callSuper = false)
@PrimaryKeys(fields = {"id"})
public class DataType extends BasicElement {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6771840463666866756L;
	// region data types constants

	public static final int DATA_TYPE_INTEGER = Integer.parseInt(ConfigProperty.getInstance()
			.getProperty(BasicElement.FILE_CONSTANTS, "datatype.integer"));
	public static final int DATA_TYPE_ALPHANUMERIC = Integer.parseInt(ConfigProperty.getInstance()
			.getProperty(BasicElement.FILE_CONSTANTS, "datatype.alphanumeric"));
	public static final int DATA_TYPE_REAL = Integer.parseInt(ConfigProperty.getInstance()
			.getProperty(BasicElement.FILE_CONSTANTS, "datatype.real"));
	public static final int DATA_TYPE_TIMESTAMP = Integer.parseInt(ConfigProperty.getInstance()
			.getProperty(BasicElement.FILE_CONSTANTS, "datatype.timestamp"));
	public static final int DATA_TYPE_BOOLEAN = Integer.parseInt(ConfigProperty.getInstance()
			.getProperty(BasicElement.FILE_CONSTANTS, "datatype.boolean"));

	// endregion data types constants
	
//	private DataType() {
//	    
//		throw new IllegalStateException(this.getClass().getSimpleName());
//	}
}
