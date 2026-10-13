package com.revenga.rits.back.data.core.model;

import com.revenga.rits.back.data.core.annotation.PrimaryKeys;
import com.revenga.rits.back.data.core.util.ConfigProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@PrimaryKeys(fields = {"id"})
public class GenericData extends BasicElement {

	/**
	 * 
	 */
	private static final long serialVersionUID = -508564161472374345L;

	public static final long GENERIC_DATA_ACTIVE_ALARMS = Integer.valueOf(ConfigProperty.getInstance()
			.getProperty(BasicElement.FILE_CONSTANTS, "generic_data.active_alarms"));
	
	public static final long GENERIC_DATA_NO_RECOGNIZE_ALARMS = Integer.valueOf(ConfigProperty.getInstance()
			.getProperty(BasicElement.FILE_CONSTANTS, "generic_data.no_recognize_alarms"));
	
	public static final long GENERIC_DATA_FILTERED_ALARMS = Integer.valueOf(ConfigProperty.getInstance()
			.getProperty(BasicElement.FILE_CONSTANTS, "generic_data.filtered_alarms"));
	
	public static final long GENERIC_DATA_NO_RECOGNIZE_AUDIT_LOGS = Integer.valueOf(ConfigProperty.getInstance()
			.getProperty(BasicElement.FILE_CONSTANTS, "generic_data.no_recognize_audit_logs"));
	
	public static final long GENERIC_DATA_INCIDENT_PENDING_TO_CONFIRM = Integer.valueOf(ConfigProperty.getInstance()
			.getProperty(BasicElement.FILE_CONSTANTS, "generic_data.incident_pending_to_confirm"));
	
	public static final long GENERIC_DATA_CONFLICT_SIGNALINGS = Integer.valueOf(ConfigProperty.getInstance()
			.getProperty(BasicElement.FILE_CONSTANTS, "generic_data.conflict_signalings"));
	
	private String value;
}
