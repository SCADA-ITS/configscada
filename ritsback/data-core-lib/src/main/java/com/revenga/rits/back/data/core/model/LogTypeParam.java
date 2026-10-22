package com.revenga.rits.back.data.core.model;

import com.revenga.rits.back.data.core.annotation.PrimaryKeys;
import com.revenga.rits.back.data.core.util.ConfigProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 
 * Defines param of log
 * 
 * @author agomez
 * 
 */
@Data
@EqualsAndHashCode(callSuper = false)
@PrimaryKeys(fields = { "logTypeId", "id" })
public class LogTypeParam extends BasicElement {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5859644687524343563L;
	
	public static final long LOG_TYPE_PARAM_PLANS_PLAN = Integer
			.parseInt(ConfigProperty.getInstance().getProperty(BasicElement.FILE_CONSTANTS, "log.type.param.plans.plan"));
	
	public static final long LOG_TYPE_PARAM_PLANS_ARG = Integer
			.parseInt(ConfigProperty.getInstance().getProperty(BasicElement.FILE_CONSTANTS, "log.type.param.plans.arg"));
	
	public static final long LOG_TYPE_PARAM_COMMANDS_COMMAND_VALUES = Integer
			.parseInt(ConfigProperty.getInstance().getProperty(BasicElement.FILE_CONSTANTS, "log.type.param.commands.command_values"));
	
	public static final long LOG_TYPE_PARAM_COMMANDS_COMMAND_TYPE = Integer
			.parseInt(ConfigProperty.getInstance().getProperty(BasicElement.FILE_CONSTANTS, "log.type.param.commands.command_type"));

	public static final long LOG_TYPE_PARAM_COMMANDS_ELEMENT = Integer
			.parseInt(ConfigProperty.getInstance().getProperty(BasicElement.FILE_CONSTANTS, "log.type.param.commands.element"));
	
	public static final long LOG_TYPE_PARAM_INCIDENT_REPORT_STATE = Integer
			.parseInt(ConfigProperty.getInstance().getProperty(BasicElement.FILE_CONSTANTS, "log.type.param.ims.state"));
	
	public static final long LOG_TYPE_PARAM_INCIDENT_REPORT_ARG_1 = Integer
			.parseInt(ConfigProperty.getInstance().getProperty(BasicElement.FILE_CONSTANTS, "log.type.param.ims.arg1"));
	

	
	private Long logTypeId;

}
