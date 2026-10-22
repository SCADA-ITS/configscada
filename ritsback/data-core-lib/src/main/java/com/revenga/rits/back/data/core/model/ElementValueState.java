package com.revenga.rits.back.data.core.model;

import com.revenga.rits.back.data.core.annotation.PrimaryKeys;
import com.revenga.rits.back.data.core.util.ConfigProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@PrimaryKeys(fields = {"id"})
public class ElementValueState extends BasicElement {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5453478378306655427L;
	
	// region element value states constants
	
	public static final long STATE_INITIALIZED = Integer.valueOf(ConfigProperty.getInstance()
			.getProperty(BasicElement.FILE_CONSTANTS, "element.value.state.initialized"));
	public static final long STATE_ONLINE = Integer.valueOf(ConfigProperty.getInstance()
			.getProperty(BasicElement.FILE_CONSTANTS, "element.value.state.online"));
	public static final long STATE_ALERTED = Integer.valueOf(ConfigProperty.getInstance()
			.getProperty(BasicElement.FILE_CONSTANTS, "element.value.state.alerted"));
	public static final long STATE_OFFLINE = Integer.valueOf(ConfigProperty.getInstance()
			.getProperty(BasicElement.FILE_CONSTANTS, "element.value.state.offline"));
	public static final long STATE_OUT_OF_RANGE = Integer.valueOf(ConfigProperty.getInstance()
			.getProperty(BasicElement.FILE_CONSTANTS, "element.value.state.out_of_range"));

	// endregion element value states constants
	
	public ElementValueState() {
		super();
	}	
}