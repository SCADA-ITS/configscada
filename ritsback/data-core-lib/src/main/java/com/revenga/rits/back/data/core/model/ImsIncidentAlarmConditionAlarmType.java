package com.revenga.rits.back.data.core.model;

import com.revenga.rits.back.data.core.annotation.PrimaryKeys;
import com.revenga.rits.back.data.core.util.ConfigProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@PrimaryKeys(fields = { "id" })
public class ImsIncidentAlarmConditionAlarmType extends BasicElement {

	// region constants
	/**
	 * 
	 */
	private static final long serialVersionUID = 8114195603455400337L;

	// region data types constants

	public static final long EQUIPMENT_ALERTED = Long.parseLong(ConfigProperty.getInstance()
			.getProperty(BasicElement.FILE_CONSTANTS, "ims.alarm_condition.type.equipment_alerted"));

	public static final long ANY_EQUIPMENT_TYPE_ALARMED = Long.parseLong(ConfigProperty.getInstance()
			.getProperty(BasicElement.FILE_CONSTANTS, "ims.alarm_condition.type.any_equipment_type_alarmed"));

	public static final long ALL_EQUIPMENT_TYPE_ALARMED = Long.parseLong(ConfigProperty.getInstance()
			.getProperty(BasicElement.FILE_CONSTANTS, "ims.alarm_condition.type.all_equipment_type_alarmed"));

	// endregion data types constants

	// endregion
}
