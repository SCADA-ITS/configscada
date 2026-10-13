-- 
-- Table: master.element_types
--
INSERT INTO master.element_types(element_type_id, alias, description,label_alias, label_description, initial_element_type_state_id, connected_element_type_state_id, disconnected_element_type_state_id, enabled, visible, created_at, updated_at)
VALUES(85, 'DALI', 'DALI','LBL_ELEMENT_TYPE_DALI', 'LBL_ELEMENT_TYPE_DALI_DESC', 0, 1, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: master.element_type_params.DALI
--
INSERT INTO master.element_type_params (element_type_id, param_type_id, element_type_param_id, element_type_param_unit_id, data_type_id, default_value, alias, description, label_alias, label_description, enabled, visible, editable, created_at, updated_at) VALUES
--CONFIG
(85, 1, 1, NULL, 2, ' ', 'config_json', 'config json', 'LBL_ELEMENT_TYPE_PARAM_DALI_CONFIG' , 'LBL_ELEMENT_TYPE_PARAM_DALI_CONFIG_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(85, 1, 1003, NULL, 2, ' ', 'MANGO_SESSION_UID', 'MANGO SESSION_UID', 'LBL_ELEMENT_TYPE_PARAM_MANGO_SESSION_UID' , 'LBL_ELEMENT_TYPE_PARAM_MANGO_SESSION_UID_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
--MEASURE
(85, 2, 1, null, 1, NULL, 'dali_regime', 'current dali illumination regime', 'LBL_ELEMENT_TYPE_PARAM_DALI_ILUMINATION_REGIME', 'LBL_ELEMENT_TYPE_PARAM_DALI_ILUMINATION_REGIME_DESC', true, true, false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(85, 2, 2, NULL, 2, '', 'data_json_group', 'data json group', 'LBL_ELEMENT_TYPE_PARAM_DALI_DATA' , 'LBL_ELEMENT_TYPE_PARAM_DALI_DATA_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


--
-- Table: master.element_type_params
--
INSERT INTO master.element_type_states(element_type_id, element_type_state_id, alias, description, label_alias, label_description, enabled, visible, created_at, updated_at) VALUES
(85, 0, 'UNKNOWN', 'Unknown state', 'LBL_ELEMENT_TYPE_STATE_UNKNOWN', 'LBL_ELEMENT_TYPE_STATE_UNKNOWN_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(85, 1, 'UP', 'The state of the equipment is: comunicates', 'LBL_ELEMENT_TYPE_STATE_UP', 'LBL_ELEMENT_TYPE_STATE_UP_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(85, 2, 'DOWN', 'The state of the equipment is: out of communication', 'LBL_ELEMENT_TYPE_STATE_DOWN', 'LBL_ELEMENT_TYPE_STATE_DOWN_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(85, 3, 'ALARMS', 'The state of the equipment is: with alarms', 'LBL_ELEMENT_TYPE_STATE_ALARMS', 'LBL_ELEMENT_TYPE_STATE_ALARMS_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(85, 4, 'MAINTENANCE', 'The state of the equipment is: on Maintenance', 'LBL_ELEMENT_TYPE_STATE_MAINTENANCE', 'LBL_ELEMENT_TYPE_STATE_MAINTENANCE_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: master.element_type_state_transitions
--
INSERT INTO master.element_type_state_transitions
(element_type_id, current_element_type_state_id, next_element_type_state_id, enabled, visible, editable, created_at, updated_at) VALUES
(85, 0, 1, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(85, 0, 2, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(85, 1, 2, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(85, 1, 3, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(85, 2, 1, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(85, 2, 3, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(85, 3, 1, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(85, 3, 2, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: master.command_element_types
--

INSERT INTO master.command_element_types
(command_element_type_id, element_type_id, alias, description, label_alias, label_description, on_change, enabled, visible, created_at, updated_at) VALUES
(1, 85, 'SEND_DALI_ILLUMINATION_REGIME', 'CHANGE DALI ILLUMINATION REGIME', 'LBL_COMMAND_ELEMENT_TYPE_SEND_DALI_ILLUMINATION_REGIME', null, 'TunnelIlluminationSystem/SetDaliIlluminationRegimeCommand', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 85, 'SEND_GROUP_VALUES', 'SEND GROUP_VALUES', 'LBL_COMMAND_ELEMENT_TYPE_SEND_GROUP', null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


INSERT INTO master.command_element_type_params
(command_element_type_id, element_type_id, command_element_type_param_id, data_type_id, alias, description, Label_alias, label_description, enabled, visible, created_at, updated_at) VALUES
(1, 85, 1, 1, 'DALI_ILLUMINATION_REGIME', 'New dali illumination regime', 'LBL_COMMAND_ELEMENT_TYPE_PARAM_DALI_ILLUMINATION_REGIME', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 85, 1, 2, 'JSON_VALUE', 'JSON_VALUE', 'LBL_COMMAND_ELEMENT_TYPE_PARAM_SEND_GROUP_VALUES_JSON_VALUE', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: master.io_controller_module_type_element_types
--
INSERT INTO master.io_controller_module_type_element_types
(module_type_id, element_type_id, enabled, visible, created_at, updated_at) VALUES
(1, 85, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: conf.alarm_configs
--
INSERT INTO conf.alarm_configs
(alarm_config_id, alarm_type_id, alarm_level_id, element_type_id, element_id, element_type_state_id, mine, treatment, alias, description, label_alias, label_description, enabled, visible, created_at, updated_at) VALUES
(8501, 2, 3, 85, NULL, NULL, true, true, 'ALARM DALI DISCONNECTED', 'ALARM DALI DISCONNECTED', 'LBL_ALARM_CONFIGS_ALARM_DALI_DISCONNECTED', 'LBL_ALARM_CONFIGS_ALARM_DALI_DISCONNECTED_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: conf.element_state_alarms
--
INSERT INTO conf.element_state_alarms
(element_type_id, element_type_state_id, alarm_config_id, created_at, updated_at) VALUES
(85, 2, 8501, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
