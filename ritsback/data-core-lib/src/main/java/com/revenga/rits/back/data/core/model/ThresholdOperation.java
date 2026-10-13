package com.revenga.rits.back.data.core.model;

import com.revenga.rits.back.data.core.annotation.PrimaryKeys;
import com.revenga.rits.back.data.core.util.ConfigProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 
 * Operations on thresholds defined in the system
 * 
 * @author aborrajo
 */
@Data
@EqualsAndHashCode(callSuper = false)
@PrimaryKeys(fields = {"id"})
public class ThresholdOperation extends BasicElement {
	
	// region constants
	/**
	 * 
	 */
	private static final long serialVersionUID = 1952254409965563165L;
	
	// region data types constants
	
	public static final long OPERATION_BETWEEN = Long.parseLong(ConfigProperty.getInstance()
			.getProperty(BasicElement.FILE_CONSTANTS, "threshold.operation.between"));
	public static final long OPERATION_EQUAL = Long.parseLong(ConfigProperty.getInstance()
			.getProperty(BasicElement.FILE_CONSTANTS, "threshold.operation.equal"));
	public static final long OPERATION_NOT_EQUAL = Long.parseLong(ConfigProperty.getInstance()
			.getProperty(BasicElement.FILE_CONSTANTS, "threshold.operation.not_equal"));
	public static final long OPERATION_GREATER = Long.parseLong(ConfigProperty.getInstance()
			.getProperty(BasicElement.FILE_CONSTANTS, "threshold.operation.greater"));
	public static final long OPERATION_GREATER_OR_EQUAL = Long.parseLong(ConfigProperty.getInstance()
			.getProperty(BasicElement.FILE_CONSTANTS, "threshold.operation.greater_or_equal"));
	public static final long OPERATION_LESS = Long.parseLong(ConfigProperty.getInstance()
			.getProperty(BasicElement.FILE_CONSTANTS, "threshold.operation.less"));
	public static final long OPERATION_LESS_OR_EQUAL = Long.parseLong(ConfigProperty.getInstance()
			.getProperty(BasicElement.FILE_CONSTANTS, "threshold.operation.less_or_equal"));
	public static final long OPERATION_EMPTY = Long.parseLong(ConfigProperty.getInstance()
			.getProperty(BasicElement.FILE_CONSTANTS, "threshold.operation.empty"));
	public static final long OPERATION_NOT_EMPTY = Long.parseLong(ConfigProperty.getInstance()
			.getProperty(BasicElement.FILE_CONSTANTS, "threshold.operation.not_empty"));
	public static final long OPERATION_NOT_BETWEEN = Long.parseLong(ConfigProperty.getInstance()
			.getProperty(BasicElement.FILE_CONSTANTS, "threshold.operation.not_between"));
	// endregion data types constants
	
	// endregion constants
}
