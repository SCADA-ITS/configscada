package com.revenga.rits.back.data.core.model;

import com.revenga.rits.back.data.core.annotation.PrimaryKeys;
import com.revenga.rits.back.data.core.util.ConfigProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 
 * Defines log type
 * 
 * @author agomez
 */

@Data
@EqualsAndHashCode(callSuper = false)
@PrimaryKeys(fields = { "id" })
public class LogSubtype extends BasicElement {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8482242987658695912L;

	public static final long LOG_SUBTYPE_EXECUTE_COMMAND = Long.parseLong(
			ConfigProperty.getInstance().getProperty(BasicElement.FILE_CONSTANTS, "log.subtype.execute_command"));

	public static final long LOG_SUBTYPE_COMMAND_EXECUTED = Long.parseLong(
			ConfigProperty.getInstance().getProperty(BasicElement.FILE_CONSTANTS, "log.subtype.command_executed"));
	
	public static final long LOG_SUBTYPE_COMMAND_NOT_EXECUTED = Long.parseLong(
			ConfigProperty.getInstance().getProperty(BasicElement.FILE_CONSTANTS, "log.subtype.command_not_executed"));

	public static final long LOG_SUBTYPE_EXECUTE_PLAN = Long.parseLong(
			ConfigProperty.getInstance().getProperty(BasicElement.FILE_CONSTANTS, "log.subtype.execute_plan"));

	public static final long LOG_SUBTYPE_PLAN_EXECUTED = Long.parseLong(
			ConfigProperty.getInstance().getProperty(BasicElement.FILE_CONSTANTS, "log.subtype.plan_executed"));

	public static final long LOG_SUBTYPE_SCHEDULE_PLAN = Long.parseLong(
			ConfigProperty.getInstance().getProperty(BasicElement.FILE_CONSTANTS, "log.subtype.shedule_plan"));

	public static final long LOG_SUBTYPE_PLAN_SCHEDULED = Long.parseLong(
			ConfigProperty.getInstance().getProperty(BasicElement.FILE_CONSTANTS, "log.subtype.plan_sheduled"));
	
	public static final long LOG_SUBTYPE_PLAN_ORDER_EVENT = Long.parseLong(
			ConfigProperty.getInstance().getProperty(BasicElement.FILE_CONSTANTS, "log.subtype.plan_order_event"));

	
	public static final long LOG_SUBTYPE_INCIDENT_REPORT_CREATED = Long
			.parseLong(ConfigProperty.getInstance().getProperty(BasicElement.FILE_CONSTANTS, "log.subtype.ims.report_created"));
	
	public static final long LOG_SUBTYPE_INCIDENT_REPORT_CONFIRMED = Long
			.parseLong(ConfigProperty.getInstance().getProperty(BasicElement.FILE_CONSTANTS, "log.subtype.ims.report_confirmed"));
	
	public static final long LOG_SUBTYPE_INCIDENT_REPORT_FORWARDED = Long
			.parseLong(ConfigProperty.getInstance().getProperty(BasicElement.FILE_CONSTANTS, "log.subtype.ims.report_forwarderd"));
	
	public static final long LOG_SUBTYPE_INCIDENT_REPORT_CANCELED = Long
			.parseLong(ConfigProperty.getInstance().getProperty(BasicElement.FILE_CONSTANTS, "log.subtype.ims.report_candeled"));
	
	public static final long LOG_SUBTYPE_ASSIGNED_USER = Long
			.parseLong(ConfigProperty.getInstance().getProperty(BasicElement.FILE_CONSTANTS, "log.subtype.ims.report_assigned_user"));
	
	public static final long LOG_SUBTYPE_ASSIGNED_CAUSE = Long
			.parseLong(ConfigProperty.getInstance().getProperty(BasicElement.FILE_CONSTANTS, "log.subtype.ims.report_assigned_cause"));
	
	public static final long LOG_SUBTYPE_FINALIZED_TASK = Long
			.parseLong(ConfigProperty.getInstance().getProperty(BasicElement.FILE_CONSTANTS, "log.subtype.ims.report_finalized_task"));
	
	public static final long LOG_SUBTYPE_FINALIZED_TASK_ERROR = Long
			.parseLong(ConfigProperty.getInstance().getProperty(BasicElement.FILE_CONSTANTS, "log.subtype.ims.report_finalized_task_error"));
	
	public static final long LOG_SUBTYPE_EXECUTED_TASK_PLAN = Long
			.parseLong(ConfigProperty.getInstance().getProperty(BasicElement.FILE_CONSTANTS, "log.subtype.ims.report_execured_task_plan"));
	
	public static final long LOG_SUBTYPE_ATTACHED_DOCUMENT = Long
			.parseLong(ConfigProperty.getInstance().getProperty(BasicElement.FILE_CONSTANTS, "log.subtype.ims.report_attached_document"));
	
	public static final long LOG_SUBTYPE_DELETED_DOCUMENT = Long
			.parseLong(ConfigProperty.getInstance().getProperty(BasicElement.FILE_CONSTANTS, "log.subtype.ims.report_deleted_document"));

	public static final long LOG_SUBTYPE_PROCESS_DOWN= Long
			.parseLong(ConfigProperty.getInstance().getProperty(BasicElement.FILE_CONSTANTS, "log.subtype.processes.process_down"));
	
	public static final long LOG_SUBTYPE_PROCESS_OUT_OF_THRESHOLD= Long
			.parseLong(ConfigProperty.getInstance().getProperty(BasicElement.FILE_CONSTANTS, "log.subtype.processes.process_out_of_threshold"));
	
	public static final long LOG_SUBTYPE_PROCESS_UP= Long
			.parseLong(ConfigProperty.getInstance().getProperty(BasicElement.FILE_CONSTANTS, "log.subtype.processes.process_up"));
	
	public static final long LOG_SUBTYPE_PROCESS_FEATURE_UP= Long
			.parseLong(ConfigProperty.getInstance().getProperty(BasicElement.FILE_CONSTANTS, "log.subtype.processes.process_feature_up"));
	
	public static final long LOG_SUBTYPE_PROCESS_FEATURE_DOWN= Long
			.parseLong(ConfigProperty.getInstance().getProperty(BasicElement.FILE_CONSTANTS, "log.subtype.processes.process_feature_down"));
	
	private Long logTypeId;
	private Long levelOutputDb;
	private Long levelOutputStomp;
	private Long levelTreatment;

}
