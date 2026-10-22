package com.revenga.rits.back.data.core.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 
 * @author revenga
 *
 */

@Data
@EqualsAndHashCode(callSuper = false)

public class ParamHist extends BasicElement {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8710563807832488387L;
	
	public enum OperationEnum {
		GREATER,
		GREATER_OR_EQUAL,
		LESS,
		LESS_OR_EQUAL,
		EQUALS,
		IN
	}
	
	private String columnName;
	private OperationEnum operation;
	private String value;

}
