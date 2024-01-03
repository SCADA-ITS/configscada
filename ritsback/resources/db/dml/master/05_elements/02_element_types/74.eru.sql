-- 
-- Table: master.element_types
--
INSERT INTO master.element_types(element_type_id, element_group_id, alias, description,label_alias, label_description, initial_element_type_state_id, connected_element_type_state_id, disconnected_element_type_state_id, enabled, visible, created_at, updated_at) VALUES
(74, NULL, 'ERU', 'ERU', 'LBL_ELEMENT_TYPE_ERU', 'LBL_ELEMENT_TYPE_ERU_DESC', 0, 1, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

--
-- Table: master.element_type_params
--
INSERT INTO master.element_type_params (element_type_id, param_type_id, element_type_param_id, element_type_param_unit_id, data_type_id, default_value, alias, description, label_alias, label_description, enabled, visible, editable, created_at, updated_at) VALUES
--CONFIG
(74, 1, 1, NULL, 2, ' ', 'brand', 'brand', 'LBL_ELEMENT_TYPE_PARAM_BRAND' , 'LBL_ELEMENT_TYPE_PARAM_BRAND_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(74, 1, 2, NULL, 2, ' ', 'model', 'model', 'LBL_ELEMENT_TYPE_PARAM_MODEL' , 'LBL_ELEMENT_TYPE_PARAM_MODEL_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(74,1,5, NULL,2, ' ', 'eru_type', 'eru_type', 'LBL_ELEMENT_TYPE_PARAM_ERU_TYPE', 'LBL_ELEMENT_TYPE_PARAM_ERU_TYPE_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(74,1,6, NULL,2, ' ', 'redundant_eru_type', 'reduntdant_eru_type', 'LBL_ELEMENT_TYPE_PARAM_REDUNDANT_ERU_TYPE', 'LBL_ELEMENT_TYPE_PARAM_REDUNDANT_ERU_TYPE_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(74,1,7, NULL,2, ' ', 'redundant_eru_alias', 'reduntdant_eru_alias', 'LBL_ELEMENT_TYPE_PARAM_REDUNDANT_ERU_ALIAS', 'LBL_ELEMENT_TYPE_PARAM_REDUNDANT_ERU_ALIAS_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(74,1,1003, NULL,2, '', 'MANGO SESSION_UID', 'MANGO SESSION_UID', 'LBL_ELEMENT_TYPE_PARAM_MANGO_SESSION_UID' , 'LBL_ELEMENT_TYPE_PARAM_MANGO_SESSION_UID_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
--MEASURE
(74,2,1, NULL,5, ' ', 'pheripheral_control', 'pheripheral_control', 'LBL_ELEMENT_TYPE_PARAM_PHERIPHERAL_CONTROL', 'LBL_ELEMENT_TYPE_PARAM_PHERIPHERAL_CONTROL_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);  

-- 
-- Table: master.element_type_states
--
INSERT INTO master.element_type_states(element_type_id, element_type_state_id, alias, description, label_alias, label_description, enabled, visible, created_at, updated_at) VALUES
(74, 0, 'UNKNOWN', 'Unknown state', 'LBL_ELEMENT_TYPE_STATE_UNKNOWN', 'LBL_ELEMENT_TYPE_STATE_UNKNOWN_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(74, 1, 'UP', 'The state of the equipment is: comunicates', 'LBL_ELEMENT_TYPE_STATE_UP', 'LBL_ELEMENT_TYPE_STATE_UP_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(74, 2, 'DOWN', 'The state of the equipment is: out of communication', 'LBL_ELEMENT_TYPE_STATE_DOWN', 'LBL_ELEMENT_TYPE_STATE_DOWN_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(74, 3, 'ALARMS', 'The state of the equipment is: with alarms', 'LBL_ELEMENT_TYPE_STATE_ALARMS', 'LBL_ELEMENT_TYPE_STATE_ALARMS_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(74, 4, 'MAINTENANCE', 'The state of the equipment is: on Maintenance', 'LBL_ELEMENT_TYPE_STATE_MAINTENANCE', 'LBL_ELEMENT_TYPE_STATE_MAINTENANCE_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: master.element_type_state_transitions
--
INSERT INTO master.element_type_state_transitions (element_type_id, current_element_type_state_id, next_element_type_state_id, enabled, visible, editable, created_at, updated_at) VALUES
(74, 1, 2, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(74, 1, 3, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(74, 2, 1, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(74, 2, 3, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(74, 3, 1, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(74, 3, 2, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(74, 0, 1, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(74, 0, 2, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: master.command_element_types
--
INSERT INTO master.command_element_types
(command_element_type_id, element_type_id, alias, description, label_alias, label_description, enabled, visible, created_at, updated_at) VALUES
(1, 74, 'TAKE CONTROL', 'TAKE CONTROL', 'LBL_COMMAND_ELEMENT_TYPE_TAKE_CONTROL', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: master.io_controller_module_type_element_types
--
INSERT INTO master.io_controller_module_type_element_types
(module_type_id, element_type_id, enabled, visible, created_at, updated_at) VALUES
(1, 74, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

--
-- Table: conf.alarm_configs
--
INSERT INTO conf.alarm_configs 
(alarm_config_id, alarm_type_id, alarm_level_id, element_type_id, element_id, element_type_state_id, mine, treatment, alias, description, label_alias, label_description, enabled, visible, created_at, updated_at)VALUES
(010074, 2, 3, 74, NULL, NULL, true, true, 'ALARM ERU NO CONNECTION', 'ALARM ERU NO CONNECTION', 'LBL_ALARM_CONFIGS_ALARM_ERU_NO_CONNECTION', 'LBL_ALARM_CONFIGS_ALARM_ERU_NO_CONNECTION_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(020074, 2, 3, 74, NULL, 3, true, true, 'ALARM ERU MASTER WITH CONTROL AND NO COMMUNICATION WITH REDUNDANT ERU', 'ALARM ERU MASTER WITH CONTROL AND NO COMMUNICATION WITH REDUNDANT ERU', 'LBL_ALARM_CONFIGS_ALARM_ERU_MASTER_WITH_CONTROL_AND_NO_COMMUNICATION_WITH_REDUNDANT_ERU', 'LBL_ALARM_CONFIGS_ALARM_ERU_MASTER_WITH_CONTROL_AND_NO_COMMUNICATION_WITH_REDUNDANT_ERU_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(030074, 2, 3, 74, NULL, 3, true, true, 'ALARM ERU SLAVE WITH CONTROL AND NO COMMUNICATION WITH REDUNDANT ERU', 'ALARM ERU SLAVE WITH CONTROL AND NO COMMUNICATION WITH REDUNDANT ERU', 'LBL_ALARM_CONFIGS_ALARM_ERU_SLAVE_WITH_CONTROL_AND_NO_COMMUNICATION_WITH_REDUNDANT_ERU', 'LBL_ALARM_CONFIGS_ALARM_ERU_SLAVE_WITH_CONTROL_AND_NO_COMMUNICATION_WITH_REDUNDANT_ERU_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(040074, 2, 3, 74, NULL, 3, true, true, 'ALARM ERU SLAVE WITH CONTROL AND COMMUNICATION WITH REDUNDANT ERU', 'ALARM ERU SLAVE WITH CONTROL AND COMMUNICATION WITH REDUNDANT ERU', 'LBL_ALARM_CONFIGS_ALARM_ERU_SLAVE_WITH_CONTROL_AND_COMMUNICATION_WITH_REDUNDANT_ERU', 'LBL_ALARM_CONFIGS_ALARM_ERU_SLAVE_WITH_CONTROL_AND_COMMUNICATION_WITH_REDUNDANT_ERU_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: conf.element_state_alarms
--
INSERT INTO conf.element_state_alarms
(element_type_id, element_type_state_id, alarm_config_id, created_at, updated_at) VALUES
(74, 2, 010074, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);