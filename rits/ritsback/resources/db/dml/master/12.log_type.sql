-- 
-- Table: master.log_levels
--
INSERT INTO master.log_levels (log_level_id, alias, description, label_alias, label_description, enabled, visible, created_at, updated_at) VALUES
(1, 'LEVEL_TRACE', 'Log level trace', 'LBL_LOG_LEVEL_TRACE', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 'LEVEL_DEBUG', 'Log level debug', 'LBL_LOG_LEVEL_DEBUG', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 'LEVEL_INFO', 'Log level info', 'LBL_LOG_LEVEL_INFO', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 'LEVEL_WARN', 'Log level warn', 'LBL_LOG_LEVEL_WARN', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 'LEVEL_ERROR', 'Log level error', 'LBL_LOG_LEVEL_ERROR', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(6, 'LEVEL_CRITICAL', 'Log level critical', 'LBL_LOG_LEVEL_CRITICAL', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: master.log_types
--
INSERT INTO master.log_types (log_type_id, alias, description, label_alias, label_description, level_output_db, level_output_stomp, level_treatment, enabled, visible, created_at, updated_at) VALUES
(1, 'IMS', 'LOG INCIDENT MANAGEMENT SYSTEM', 'LBL_LOG_TYPE_IMS', null, 3, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 'PLANS', 'LOG PLANS', 'LBL_LOG_TYPE_PLANS', null, 3, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 'COMMANDS', 'LOG COMMANDS', 'LBL_LOG_TYPE_COMMANDS', null, 3, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 'USERS', 'LOG USERS', 'LBL_LOG_TYPE_USERS', null, 3, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 'PROCESSES', 'LOG PROCESSES', 'LBL_LOG_TYPE_PROCESSES', null, 3, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

--
-- Table: master.log_type_params
--
INSERT INTO master.log_type_params (log_type_id, log_type_param_id, alias, description, label_alias, label_description, enabled, visible, created_at, updated_at) VALUES
(1, 1, 'INCIDENT_REPORT_STATE', 'LOG_IMS_PARAM_INCIDENT_REPORT_STATE', 'LBL_LOG_TYPE_PARAM_STATE', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 2, 'INCIDENT_REPORT_ARG_1', 'LOG_IMS_PARAM_INCIDENT_REPORT_ARG_1', 'LBL_LOG_TYPE_PARAM_ARG_1', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(2, 1, 'PLAN', 'LOG_PLANS_PARAM_PLAN', 'LBL_LOG_TYPE_PARAM_PLAN', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 2, 'ARG', 'LOG_PLANS_PARAM_ARG', 'LBL_LOG_TYPE_PARAM_ARG', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 3, 'COMMENT', 'LOG_PLANS_PARAM_COMMENT', 'LOG_PLANS_PARAM_COMMENT', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(3, 1, 'COMMAND_TYPE', 'LOG_COMMANDS_PARAM_COMMAND_TYPE', 'LBL_LOG_TYPE_PARAM_COMMAND_TYPE', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 2, 'ELEMENT', 'LOG_COMMANDS_PARAM_ELEMENT', 'LBL_LOG_TYPE_PARAM_ELEMENT', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 3, 'COMMAND_VALUES', 'LOG_COMMANDS_PARAM_COMMAND_VALUES', 'LBL_LOG_TYPE_PARAM_COMMAND_VALUES', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 4, 'COMMENT', 'LOG_COMMANDS_PARAM_COMMENT', 'LBL_LOG_TYPE_PARAM_COMMENT', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(4, 1, 'SESSION', 'LOG_USERS_PARAM_SESSION', 'LOG_USERS_PARAM_SESSION', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 2, 'COMMENT', 'LOG_USERS_PARAM_COMMENT', 'LOG_USERS_PARAM_COMMENT', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(5, 1, 'PROCESS', 'LOG_PROCESSES_PARAM_PROCESS', 'LOG_PROCESSES_PARAM_PROCESS', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 2, 'RUNNING_SINCE', 'LOG_PROCESSES_PARAM_RUNNING_SINCE', 'LOG_PROCESSES_PARAM_RUNNING_SINCE', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 3, 'COMMENT', 'LOG_PROCESSES_PARAM_COMMENT', 'LOG_PROCESSES_PARAM_COMMENT', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


-- 
-- Table: master.log_subtypes
--
INSERT INTO master.log_subtypes (log_subtype_id, log_type_id, alias, description, label_alias, label_description, level_output_db, level_output_stomp, level_treatment, enabled, visible, created_at, updated_at) VALUES
(1, 3, 'EXECUTE_COMMAND', 'Execute command', 'LBL_LOG_SUBTYPE_EXECUTE_COMMAND', null, 2, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 3, 'COMMAND_EXECUTED', 'Command executed', 'LBL_LOG_SUBTYPE_COMMAND_EXECUTED', null, 3, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(36, 3, 'COMMAND_NOT_EXECUTED', 'Command not executed', 'LBL_LOG_SUBTYPE_COMMAND_NOT_EXECUTED', null, 3, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(3, 2, 'EXECUTE_PLAN', 'Execute plan', 'LBL_LOG_SUBTYPE_EXECUTE_PLAN', null, 2, 3, 3, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 2, 'PLAN_EXECUTED', 'Plan executed', 'LBL_LOG_SUBTYPE_PLAN_EXECUTED', null, 2, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 2, 'SCHEDULE_PLAN', 'Schedule plan', 'LBL_LOG_SUBTYPE_SHEDULE_PLAN', null, 3, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(6, 2, 'PLAN_SCHEDULED', 'Plan scheduled', 'LBL_LOG_SUBTYPE_PLAN_SCHEDULED', null, 3, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(33, 2, 'PLAN_ORDER_EVENT', 'Plan order event', 'LBL_LOG_SUBTYPE_PLAN_ORDER_EVENT', null, 3, 3, 3, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(34, 2, 'STOP_PLAN', 'Stop plan', 'LBL_LOG_SUBTYPE_STOP_PLAN', null, 2, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(35, 2, 'PAUSE_PLAN', 'Pause plan', 'LBL_LOG_SUBTYPE_PAUSE_PLAN', null, 2, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(7, 1, 'INCIDENT_REPORT_CREATED', 'Incident report created', 'LBL_LOG_SUBTYPE_INCIDENT_REPORT_CREATED', null, 3, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(8, 1, 'INCIDENT_REPORT_CONFIRMED', 'Incident report confirmed', 'LBL_LOG_SUBTYPE_INCIDENT_REPORT_CONFIRMED', null, 3, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(9, 1, 'INCIDENT_REPORT_CHANGE_STATE', 'Incident report change state', 'LBL_LOG_SUBTYPE_INCIDENT_REPORT_CHANGE_STATE', null, 3, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(10, 1, 'INCIDENT_REPORT_CANCELED', 'Incident report canceled', 'LBL_LOG_SUBTYPE_INCIDENT_REPORT_CANCELED', null, 3, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(11, 1, 'ASSIGNED_USER', 'Assigned user in incident report canceled', 'LBL_LOG_SUBTYPE_INCIDENT_REPORT_ASSIGNED_USER', null, 3, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(12, 1, 'ASSIGNED_CAUSE', 'Assigned cause in incident report canceled', 'LBL_LOG_SUBTYPE_INCIDENT_REPORT_ASSIGNED_CAUSE', null, 3, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(13, 1, 'FINALIZED_TASK', 'Finalized task in incident report', 'LBL_LOG_SUBTYPE_INCIDENT_REPORT_FINALIZED_TASK', null, 3, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(14, 1, 'EXECUTED_TASK_PLAN', 'Executed task plan in incident report', 'LBL_LOG_SUBTYPE_INCIDENT_REPORT_EXECUTED_TASK_PLAN', null, 3, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(15, 1, 'ATTACHED_DOCUMENT', 'Attached document in incident report', 'LBL_LOG_SUBTYPE_INCIDENT_REPORT_ATTACHED_DOCUMENT', null, 3, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(16, 1, 'DELETED_DOCUMENT', 'Deleted document in incident report', 'LBL_LOG_SUBTYPE_INCIDENT_REPORT_DELETED_DOCUMENT', null, 3, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(17, 1, 'MANUAL_USER_LOG', 'Log created by user', 'LBL_LOG_SUBTYPE_INCIDENT_LOG_MANUAL', null, 3, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(18, 1, 'INCIDENT_REPORT_CHANGED', 'Incident report changed', 'LBL_LOG_SUBTYPE_INCIDENT_REPORT_CHANGED', null, 3, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(37, 1, 'FINALIZED_TASK_ERROR', 'Finalized task with error', 'LBL_LOG_SUBTYPE_INCIDENT_REPORT_FINALIZED_TASK_ERROR', null, 3, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(41, 1, 'CHANGE_ROAD_IMPACT', 'Change road impact', 'LBL_LOG_SUBTYPE_INCIDENT_REPORT_CHANGE_ROAD_IMPACT', null, 3, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(38, 4, 'LOGIN', 'User login', 'LBL_LOG_SUBTYPE_USER_LOGIN', null, 3, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(39, 4, 'LOGOUT', 'User logout', 'LBL_LOG_SUBTYPE_USER_LOGOUT', null, 3, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(40, 4, 'EXPIRED', 'User expired', 'LBL_LOG_SUBTYPE_USER_EXPIRED', null, 3, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(42, 5, 'PROCESS_DOWN', 'Process down', 'LBL_LOG_SUBTYPE_PROCESS_DOWN', null, 3, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(43, 5, 'OUT_OF_THRESHOLD', 'Out of threhsold', 'LBL_LOG_SUBTYPE_OUT_OF_THRESHOLD', null, 3, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


