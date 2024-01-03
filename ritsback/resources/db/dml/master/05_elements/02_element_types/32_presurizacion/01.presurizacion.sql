-- 
-- Table: master.element_types
--
INSERT INTO master.element_types(element_type_id, element_group_id, alias, description,label_alias, label_description, initial_element_type_state_id, connected_element_type_state_id, disconnected_element_type_state_id, enabled, visible, created_at, updated_at) VALUES
(32, 2, 'PRESURIZACION', 'PRESURIZACION', 'LBL_ELEMENT_TYPE_PRESURIZACION', 'LBL_ELEMENT_TYPE_PRESURIZACION_DESC', 0, 1, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

--
-- Table: master.element_type_params.PRESURIZACION
--
INSERT INTO master.element_type_params (element_type_id, param_type_id, element_type_param_id, element_type_param_unit_id, data_type_id, default_value, alias, description, label_alias, label_description, enabled, visible, editable, created_at, updated_at) VALUES
--CONFIG
(32,1,1, NULL,2, ' ', 'brand', 'brand', 'LBL_ELEMENT_TYPE_PARAM_BRAND' , 'LBL_ELEMENT_TYPE_PARAM_BRAND_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(32,1,2, NULL,2, ' ', 'model', 'model', 'LBL_ELEMENT_TYPE_PARAM_MODEL' , 'LBL_ELEMENT_TYPE_PARAM_MODEL_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(32,1,4, NULL,2, ' ', 'associated_cabinet', 'associated_cabinet', 'LBL_ELEMENT_TYPE_PARAM_PRESSURIZATION_ASSOCIATED_CABINET' , 'LBL_ELEMENT_TYPE_PARAM_PRESSURIZATION_ASSOCIATED_CABINET_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(32,1,1003, NULL,2, ' ', 'MANGO SESSION_UID', 'MANGO SESSION_UID', 'LBL_ELEMENT_TYPE_PARAM_MANGO_SESSION_UID' , 'LBL_ELEMENT_TYPE_PARAM_MANGO_SESSION_UID_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
--MEASURE
(32,2,1, NULL,5, NULL, 'remote_control', 'remote_control', 'LBL_ELEMENT_TYPE_PARAM_FIRE_PRESSURIZATION_REMOTE_CONTROL' , 'LBL_ELEMENT_TYPE_PARAM_FIRE_PRESSURIZATION_REMOTE_CONTROL_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(32,2,2, NULL,5, NULL, 'pres_fan_1', 'pres_fan_1', 'LBL_ELEMENT_TYPE_PARAM_FIRE_PRESSURIZATION_FAN_1' , 'LBL_ELEMENT_TYPE_PARAM_FIRE_PRESSURIZATION_FAN_1_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(32,2,3, NULL,5, NULL, 'pres_fan_2', 'pres_fan_2', 'LBL_ELEMENT_TYPE_PARAM_FIRE_PRESSURIZATION_FAN_2' , 'LBL_ELEMENT_TYPE_PARAM_FIRE_PRESSURIZATION_FAN_2_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(32,2,4, NULL,5, NULL, 'fan_running', 'fan running', 'LBL_ELEMENT_TYPE_PARAM_PRESSURIZATION_FAN_RUNNING' , 'LBL_ELEMENT_TYPE_PARAM_PRESSURIZATION_FAN_RUNNING_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(32,2,5, NULL,5, NULL, 'reset_alarms', 'reset_alarms', 'LBL_ELEMENT_TYPE_PARAM_reset_alarms' , 'LBL_ELEMENT_TYPE_PARAM_reset_alarms_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(32,2,6, 31,1, NULL, 'parcial_operating_hours', 'parcial_operating_hours', 'LBL_ELEMENT_TYPE_PARAM_PRESSURIZATION_PARCIAL_OPERATING_HOURS' , 'LBL_ELEMENT_TYPE_PARAM_PRESSURIZATION_PARCIAL_OPERATING_HOURS_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(32,2,7, 31,1, NULL, 'total_operating_hours', 'total_operating_hours', 'LBL_ELEMENT_TYPE_PARAM_PRESSURIZATION_TOTAL_OPERATING_HOURS' , 'LBL_ELEMENT_TYPE_PARAM_PRESSURIZATION_TOTAL_OPERATING_HOURS_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: master.element_type_states.PRESURIZACION
--
INSERT INTO master.element_type_states(element_type_id, element_type_state_id, alias, description, label_alias, label_description, enabled, visible, created_at, updated_at) VALUES
(32, 0, 'UNKNOWN', 'Unknown state', 'LBL_ELEMENT_TYPE_STATE_UNKNOWN', 'LBL_ELEMENT_TYPE_STATE_UNKNOWN_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(32, 1, 'UP', 'The state of the equipment is: comunicates', 'LBL_ELEMENT_TYPE_STATE_UP', 'LBL_ELEMENT_TYPE_STATE_UP_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(32, 2, 'DOWN', 'The state of the equipment is: out of communication', 'LBL_ELEMENT_TYPE_STATE_DOWN', 'LBL_ELEMENT_TYPE_STATE_DOWN_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(32, 3, 'ALARMS', 'The state of the equipment is: with alarms', 'LBL_ELEMENT_TYPE_STATE_ALARMS', 'LBL_ELEMENT_TYPE_STATE_ALARMS_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(32, 4, 'MAINTENANCE', 'The state of the equipment is: on Maintenance', 'LBL_ELEMENT_TYPE_STATE_MAINTENANCE', 'LBL_ELEMENT_TYPE_STATE_MAINTENANCE_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: master.element_type_state_transitions
--
INSERT INTO master.element_type_state_transitions (element_type_id, current_element_type_state_id, next_element_type_state_id, enabled, visible, editable, created_at, updated_at) VALUES
(32, 1, 2, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(32, 1, 3, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(32, 2, 1, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(32, 2, 3, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(32, 3, 1, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(32, 3, 2, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(32, 0, 1, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(32, 0, 2, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


-- 
-- Table: master.command_element_types
--
INSERT INTO master.command_element_types
(command_element_type_id, element_type_id, alias, description, label_alias, label_description, enabled, visible, created_at, updated_at) VALUES
(1, 32, 'PRESSURIZATION_ON', 'PRESSURIZATION_ON', 'LBL_COMMAND_ELEMENT_TYPE_FIRE_PRESSURIZATION_ON', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 32, 'PRESSURIZATION_OFF', 'PRESSURIZATION_OFF', 'LBL_COMMAND_ELEMENT_TYPE_FIRE_PRESSURIZATION_OFF', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 32, 'RESET ALARMS', 'RESET ALARMS', 'LBL_COMMAND_ELEMENT_TYPE_RESET_ALARMS', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 32, 'RESET PARTIAL HOURS', 'RESET PARTIAL HOURS', 'LBL_COMMAND_ELEMENT_TYPE_RESET_PARTIAL_HOURS', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


-- 
-- Table: master.io_controller_module_type_element_types
--
INSERT INTO master.io_controller_module_type_element_types
(module_type_id, element_type_id, enabled, visible, created_at, updated_at) VALUES
(1, 32, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: conf.alarm_configs
--
INSERT INTO conf.alarm_configs
(alarm_config_id, alarm_type_id, alarm_level_id, element_type_id, element_id, element_type_state_id, mine, treatment, alias, description, label_alias, label_description, enabled, visible, created_at, updated_at) VALUES
(010032, 2, 6, 32, NULL, NULL, true, true, 'ALARM PRESSURIZATION NO CONNECTION', 'ALARM PRESSURIZATION NO CONNECTION', 'LBL_ALARM_CONFIGS_ALARM_PRESSURIZATION_NO_CONNECTION', 'LBL_ALARM_CONFIGS_ALARM_PRESSURIZATION_NO_CONNECTION_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(020032, 2, 5, 32, NULL, 3, true, true, 'ALARM FAN ON OFF', 'ALARM FAN ON OFF', 'LBL_ALARM_CONFIGS_ALARM_FAN_ON_OFF', 'LBL_ALARM_CONFIGS_ALARM_CONFIGS_ALARM_FAN_ON_OFF_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(030032, 2, 5, 32, NULL, 3, true, true, 'ALARM FAN FAIL STOP', 'ALARM FAN FAIL STOP', 'LBL_ALARM_CONFIGS_ALARM_FAN_FAIL_STOP', 'LBL_ALARM_CONFIGS_ALARM_FAN_FAIL_STOP_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(040032, 2, 5, 32, NULL, 3, true, true, 'ALARM FAN FAIL START', 'ALARM FAN FAIL START', 'LBL_ALARM_CONFIGS_ALARM_FAN_FAIL_START', 'LBL_ALARM_CONFIGS_ALARM_FAN_FAIL_START_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(050032, 2, 5, 32, NULL, 3, true, true, 'ALARM FAN FAIL', 'ALARM FAN FAIL', 'LBL_ALARM_CONFIGS_ALARM_FAN_FAIL', 'LBL_ALARM_CONFIGS_ALARM_FAN_FAIL_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(060032, 2, 3, 32, NULL, 3, true, true, 'ALARM FIRE PRESSURIZATION EMERGENCY BUTTON ACTIVATED', 'ALARM FIRE PRESSURIZATION EMERGENCY BUTTON ACTIVATED', 'LBL_ALARM_CONFIGS_ALARM_FIRE_PRESSURIZATION_EMERGENCY_BUTTON_ACTIVATED', 'LBL_ALARM_CONFIGS_ALARM_FIRE_PRESSURIZATION_EMERGENCY_BUTTON_ACTIVATED_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(070032, 2, 3, 32, NULL, 3, true, true, 'ALARM FIRE PRESSURIZATION FAN 1 FAIL', 'ALARM FIRE PRESSURIZATION FAN 1 FAIL', 'LBL_ALARM_CONFIGS_ALARM_FIRE_PRESSURIZATION_FAN_1_FAIL', 'LBL_ALARM_CONFIGS_ALARM_FIRE_PRESSURIZATION_FAN_1_FAIL_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(080032, 2, 3, 32, NULL, 3, true, true, 'ALARM FIRE PRESSURIZATION FAN 2 FAIL', 'ALARM FIRE PRESSURIZATION FAN 2 FAIL', 'LBL_ALARM_CONFIGS_ALARM_FIRE_PRESSURIZATION_FAN_2_FAIL', 'LBL_ALARM_CONFIGS_ALARM_FIRE_PRESSURIZATION_FAN_2_FAIL_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: conf.element_state_alarms
--
INSERT INTO conf.element_state_alarms
(element_type_id, element_type_state_id, alarm_config_id, created_at, updated_at) VALUES
(32, 2, 010032, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

