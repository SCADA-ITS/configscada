package com.revenga.rits.back.data.core.model.command;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import com.revenga.rits.back.data.core.util.ConfigProperty;

import lombok.Data;

@Data
public class Command implements Cloneable, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7154674497180094132L;

	protected static final String FILE_CONSTANTS = "constants.properties";

	private static final AtomicLong idSequence = new AtomicLong(Instant.now().toEpochMilli());

	public static final int DEFAULT_EXPIRE_TIME = 60; // seconds

	public static final int ACTION_CREATE = 1;
	public static final int ACTION_UPDATE = 2;
	public static final int ACTION_DELETE = 3;
	public static final int ACTION_BLOCK = 4;

	// region data types constants

	public static final long GENERIC_COMMAND = Long
			.parseLong(ConfigProperty.getInstance().getProperty(FILE_CONSTANTS, "command.generic_command"));

	public static final long SET_ALARMS = Long
			.parseLong(ConfigProperty.getInstance().getProperty(FILE_CONSTANTS, "command.set_alarms"));

	public static final long SET_ALARMS_FILTERED = Long
			.parseLong(ConfigProperty.getInstance().getProperty(FILE_CONSTANTS, "command.set_alarms_filtered"));

	public static final long SET_RECOGNIZE_ALARMS = Long
			.parseLong(ConfigProperty.getInstance().getProperty(FILE_CONSTANTS, "command.set_recognize_alarms"));

	public static final long SET_ACTIVATION_ALARMS = Long
			.parseLong(ConfigProperty.getInstance().getProperty(FILE_CONSTANTS, "command.set_activation_alarms"));

	public static final long SET_DEACTIVATION_ALARMS = Long
			.parseLong(ConfigProperty.getInstance().getProperty(FILE_CONSTANTS, "command.set_deactivation_alarms"));

	public static final long SET_ELEMENT_MEASURES_CHANGE = Long
			.parseLong(ConfigProperty.getInstance().getProperty(FILE_CONSTANTS, "command.set_element_measures_change"));

	public static final long SET_ELEMENT_STATES_CHANGE = Long
			.parseLong(ConfigProperty.getInstance().getProperty(FILE_CONSTANTS, "command.set_element_states_change"));

	public static final long SET_KILL_USER = Long
			.parseLong(ConfigProperty.getInstance().getProperty(FILE_CONSTANTS, "command.set_kill_user"));

	public static final long SIGNALLING_COMMAND = Long
			.parseLong(ConfigProperty.getInstance().getProperty(FILE_CONSTANTS, "command.signalling_command"));

	public static final long SET_SIGNALLINGS_COMMAND = Long
			.parseLong(ConfigProperty.getInstance().getProperty(FILE_CONSTANTS, "command.set_signallings_command"));

	public static final long SET_ADD_COMMANDS_COMMAND = Long
			.parseLong(ConfigProperty.getInstance().getProperty(FILE_CONSTANTS, "command.set_add_commands_command"));

	public static final long SET_DELETE_COMMANDS_COMMAND = Long
			.parseLong(ConfigProperty.getInstance().getProperty(FILE_CONSTANTS, "command.set_delete_commands_command"));

	public static final long SET_PRIORITY_COMMAND = Long
			.parseLong(ConfigProperty.getInstance().getProperty(FILE_CONSTANTS, "command.set_priority_command"));

	public static final long REQUEST_COMMAND = Long
			.parseLong(ConfigProperty.getInstance().getProperty(FILE_CONSTANTS, "command.request_command"));

	public static final long SET_PLAN_ACTIONS = Long
			.parseLong(ConfigProperty.getInstance().getProperty(FILE_CONSTANTS, "command.set_plan_actions"));

	public static final long SET_ELEMENT_VALUES_PUBLISH = Long
			.parseLong(ConfigProperty.getInstance().getProperty(FILE_CONSTANTS, "command.set_element_values_publish"));

	public static final long SET_ALARMS_PUBLISH = Long
			.parseLong(ConfigProperty.getInstance().getProperty(FILE_CONSTANTS, "command.set_alarms_publish"));

	public static final long SET_ELEMENT_STATES_PUBLISH = Long
			.parseLong(ConfigProperty.getInstance().getProperty(FILE_CONSTANTS, "command.set_element_states_publish"));

	public static final long SET_CREATE_INCIDENT_REPORT = Long
			.parseLong(ConfigProperty.getInstance().getProperty(FILE_CONSTANTS, "command.set_create_incident_report"));

	public static final long SET_CHANGE_INCIDENT_REPORT = Long
			.parseLong(ConfigProperty.getInstance().getProperty(FILE_CONSTANTS, "command.set_change_incident_report"));

	public static final long SET_FORWARD_INCIDENT_REPORT = Long
			.parseLong(ConfigProperty.getInstance().getProperty(FILE_CONSTANTS, "command.set_forward_incident_report"));

	public static final long SET_BACKWARD_INCIDENT_REPORT = Long.parseLong(
			ConfigProperty.getInstance().getProperty(FILE_CONSTANTS, "command.set_backward_incident_report"));

	public static final long SET_CHANGE_ROAD_IMPACT_INCIDENT_REPORT = Long.parseLong(
			ConfigProperty.getInstance().getProperty(FILE_CONSTANTS, "command.set_change_road_impact_incident_report"));

	public static final long SET_CANCEL_INCIDENT_REPORT = Long
			.parseLong(ConfigProperty.getInstance().getProperty(FILE_CONSTANTS, "command.set_cancel_incident_report"));

	public static final long SET_ASSIGN_USER_INCIDENT_REPORT = Long.parseLong(
			ConfigProperty.getInstance().getProperty(FILE_CONSTANTS, "command.set_assign_user_incident_report"));

	public static final long SET_ASSIGN_CAUSE_INCIDENT_REPORT = Long.parseLong(
			ConfigProperty.getInstance().getProperty(FILE_CONSTANTS, "command.set_assign_cause_incident_report"));

	public static final long SET_CONFIRM_INCIDENT_REPORT = Long
			.parseLong(ConfigProperty.getInstance().getProperty(FILE_CONSTANTS, "command.set_confirm_incident_report"));

	public static final long SET_FINALIZE_TASK_INCIDENT_REPORT = Long.parseLong(
			ConfigProperty.getInstance().getProperty(FILE_CONSTANTS, "command.set_finalize_task_incident_report"));

	public static final long SET_EXECUTE_TASK_PLAN_INCICENT_REPORT = Long.parseLong(
			ConfigProperty.getInstance().getProperty(FILE_CONSTANTS, "command.set_execute_task_plan_incident_report"));

	public static final long SET_ADD_LOG_INCIDENT_REPORT = Long
			.parseLong(ConfigProperty.getInstance().getProperty(FILE_CONSTANTS, "command.set_add_log_incident_report"));

	public static final long SET_DELETE_LOG_INCIDENT_REPORT = Long.parseLong(
			ConfigProperty.getInstance().getProperty(FILE_CONSTANTS, "command.set_delete_log_incident_report"));

	public static final long SET_INCIDENT_REPORTS_PUBLISH = Long.parseLong(
			ConfigProperty.getInstance().getProperty(FILE_CONSTANTS, "command.set_incident_reports_publish"));

	public static final long SET_BLOCK_INCIDENT_REPORTS_PUBLISH = Long.parseLong(
			ConfigProperty.getInstance().getProperty(FILE_CONSTANTS, "command.set_block_incident_reports_publish"));

	public static final long SET_PLAN_SCHEDULE_ACTIONS = Long
			.parseLong(ConfigProperty.getInstance().getProperty(FILE_CONSTANTS, "command.set_plan_schedule_actions"));

	public static final long SET_PLAN_SCHEDULE_PUBLISH = Long
			.parseLong(ConfigProperty.getInstance().getProperty(FILE_CONSTANTS, "command.set_plan_schedule_publish"));

	public static final long SET_PLAN_SCHEDULE_STATE_PUBLISH = Long.parseLong(
			ConfigProperty.getInstance().getProperty(FILE_CONSTANTS, "command.set_plan_schedule_state_publish"));

	public static final long SET_PLAN_STATE_PUBLISH = Long
			.parseLong(ConfigProperty.getInstance().getProperty(FILE_CONSTANTS, "command.set_plan_state_publish"));

	public static final long SET_GENERIC_DATA = Long
			.parseLong(ConfigProperty.getInstance().getProperty(FILE_CONSTANTS, "command.set_generic_data"));

	public static final long SET_HISTORIC_ELEMENT = Long
			.parseLong(ConfigProperty.getInstance().getProperty(FILE_CONSTANTS, "command.set_historic_element"));

	public static final long SET_HISTORIC_AUDIT_LOG = Long
			.parseLong(ConfigProperty.getInstance().getProperty(FILE_CONSTANTS, "command.set_historic_audit_log"));

	public static final long SET_RECOGNIZE_AUDIT_LOG = Long
			.parseLong(ConfigProperty.getInstance().getProperty(FILE_CONSTANTS, "command.set_recognize_audit_log"));

	public static final long SET_EX_INCIDENT_PUBLISH = Long
			.parseLong(ConfigProperty.getInstance().getProperty(FILE_CONSTANTS, "command.set_ex_incident_publish"));

	public static final long SET_AUDIT_LOG_PUBLISH = Long
			.parseLong(ConfigProperty.getInstance().getProperty(FILE_CONSTANTS, "command.set_audit_log_publish"));

	public static final long SET_PM_QUEUE_PUBLISH = Long
			.parseLong(ConfigProperty.getInstance().getProperty(FILE_CONSTANTS, "command.set_pm_queue_publish"));

	public static final long SET_KEEP_ALIVE_DRIVER_INSTANCE = Long.parseLong(
			ConfigProperty.getInstance().getProperty(FILE_CONSTANTS, "command.set_keep_alive_driver_instance"));

	// VIRTUAL EQUIPMENT COMMAND

	// VENTILATION
	public static final long SET_FANS_STOP = Long
			.parseLong(ConfigProperty.getInstance().getProperty(FILE_CONSTANTS, "command.set_fans_stop"));

	public static final long SET_FANS_START = Long
			.parseLong(ConfigProperty.getInstance().getProperty(FILE_CONSTANTS, "command.set_fans_start"));

	public static final long SET_FANS_START_REVERSE = Long
			.parseLong(ConfigProperty.getInstance().getProperty(FILE_CONSTANTS, "command.set_fans_start_reverse"));

	public static final long SET_FANS_RESET_TOTAL_HOURS = Long
			.parseLong(ConfigProperty.getInstance().getProperty(FILE_CONSTANTS, "command.set_fans_reset_total_hours"));

	public static final long SET_FANS_RESET_PARTIAL_HOURS = Long.parseLong(
			ConfigProperty.getInstance().getProperty(FILE_CONSTANTS, "command.set_fans_reset_partial_hours"));

	public static final long SET_FANS_RESET_ALARMS = Long
			.parseLong(ConfigProperty.getInstance().getProperty(FILE_CONSTANTS, "command.set_fans_reset_alarms"));

	public static final long SET_VENTILATION_MODE = Long
			.parseLong(ConfigProperty.getInstance().getProperty(FILE_CONSTANTS, "command.set_ventilation_mode"));

	public static final long SET_VENTILATION_REGIME = Long
			.parseLong(ConfigProperty.getInstance().getProperty(FILE_CONSTANTS, "command.set_ventilation_regime"));

	public static final long SET_ALARMS_TO_VENTILATION = Long
			.parseLong(ConfigProperty.getInstance().getProperty(FILE_CONSTANTS, "command.set_alarms_to_ventilation"));

	public static final long SET_ELEMENT_VALUES_TO_VENTILATION = Long.parseLong(
			ConfigProperty.getInstance().getProperty(FILE_CONSTANTS, "command.set_element_values_to_ventilation"));

	public static final long SET_ELEMENT_STATES_TO_VENTILATION = Long.parseLong(
			ConfigProperty.getInstance().getProperty(FILE_CONSTANTS, "command.set_element_states_to_ventilation"));

	public static final long SET_FIRE_ALARM = Long
			.parseLong(ConfigProperty.getInstance().getProperty(FILE_CONSTANTS, "command.set_fire_alarm"));

	// ILLUMINATION
	public static final long SET_ILLUMINATION_MODE = Long
			.parseLong(ConfigProperty.getInstance().getProperty(FILE_CONSTANTS, "command.set_illumination_mode"));

	public static final long SET_ILLUMINATION_REGIME = Long
			.parseLong(ConfigProperty.getInstance().getProperty(FILE_CONSTANTS, "command.set_illumination_regime"));

	public static final long SET_DALI_ILLUMINATION_REGIME = Long.parseLong(
			ConfigProperty.getInstance().getProperty(FILE_CONSTANTS, "command.set_dali_illumination_regime"));

	public static final long SET_ALARMS_TO_ILLUMINATION = Long
			.parseLong(ConfigProperty.getInstance().getProperty(FILE_CONSTANTS, "command.set_alarms_to_illumination"));

	public static final long SET_CIRCUITS_OFF = Long
			.parseLong(ConfigProperty.getInstance().getProperty(FILE_CONSTANTS, "command.set_circuits_off"));

	public static final long SET_CIRCUITS_ON = Long
			.parseLong(ConfigProperty.getInstance().getProperty(FILE_CONSTANTS, "command.set_circuits_on"));

	public static final long SET_ELEMENT_VALUES_TO_ILLUMINATION = Long.parseLong(
			ConfigProperty.getInstance().getProperty(FILE_CONSTANTS, "command.set_element_values_to_illumination"));

	public static final long SET_ELEMENT_STATES_TO_ILLUMINATION = Long.parseLong(
			ConfigProperty.getInstance().getProperty(FILE_CONSTANTS, "command.set_element_states_to_illumination"));

	public static final long RESPONSE = Long
			.parseLong(ConfigProperty.getInstance().getProperty(FILE_CONSTANTS, "command.response"));

	public static final long CHANGE_ENTITY_REPOSITORY = Long
			.parseLong(ConfigProperty.getInstance().getProperty(FILE_CONSTANTS, "command.change_entity_repository"));

	public static final long STOP_PROCESSES = Long
			.parseLong(ConfigProperty.getInstance().getProperty(FILE_CONSTANTS, "command.stop_processes"));

	// endregion data types constants

	protected Long id;
	protected Long uId;
	protected String userid;
	protected String alias;
	protected String description;
	protected Long createdAt;
	protected Long processedAt;
	protected String processedBy;
	protected Integer action;
	protected Command responseCommand;
	protected String generatedBy;
	protected List<Long> managementAreaIds;

	public Command(Long id) {

		this.id = id;
		this.generatedBy = Thread.currentThread().getName() + "_" + Thread.currentThread().getId();
	}

	@Override
	public Object clone() throws CloneNotSupportedException {

		return super.clone();
	}

	/**
	 * 
	 * Generate UID for object Cannot generate UID if field id is already set
	 * 
	 * @return Unique identifier generated for object
	 */
	public Long generateId() {

		if (uId == null) {

			uId = idSequence.getAndIncrement();
		}

		return uId;
	}

	public void addManagementArea(Long managementAreaId) {
		
		if(this.managementAreaIds == null){
			this.managementAreaIds = new ArrayList<>();
		}
		
		this.managementAreaIds.add(managementAreaId);
		
	}
}
