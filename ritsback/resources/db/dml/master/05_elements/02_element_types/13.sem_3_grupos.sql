-- 
-- Table: master.element_types
--
INSERT INTO master.element_types
(element_type_id, element_group_id, alias, description,label_alias, label_description, initial_element_type_state_id, connected_element_type_state_id, disconnected_element_type_state_id, enabled, visible, created_at, updated_at) VALUES
(13, 3, 'SEM', 'SEM', 'LBL_ELEMENT_TYPE_SEM', 'LBL_ELEMENT_TYPE_SEM_DESC', 0, 1, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

--
-- Table: master.element_type_params
--
INSERT INTO master.element_type_params (element_type_id, param_type_id, element_type_param_id, element_type_param_unit_id, data_type_id, default_value, alias, description, label_alias, label_description, enabled, visible, editable, created_at, updated_at) VALUES
--CONFIG
(13,1,1, NULL,2, ' ', 'brand', 'brand', 'LBL_ELEMENT_TYPE_PARAM_BRAND' , 'LBL_ELEMENT_TYPE_PARAM_BRAND_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(13,1,2, NULL,2, ' ', 'model', 'model', 'LBL_ELEMENT_TYPE_PARAM_MODEL' , 'LBL_ELEMENT_TYPE_PARAM_MODEL_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(13,1,1003, NULL,2, '', 'MANGO SESSION_UID', 'MANGO SESSION_UID', 'LBL_ELEMENT_TYPE_PARAM_MANGO_SESSION_UID' , 'LBL_ELEMENT_TYPE_PARAM_MANGO_SESSION_UID_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
--MEASURE
(13,2,1, NULL,1, NULL, 'light', 'light', 'LBL_ELEMENT_TYPE_PARAM_LIGHT' , 'LBL_ELEMENT_TYPE_PARAM_LIGHT_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: master.element_type_states
--
INSERT INTO master.element_type_states(element_type_id, element_type_state_id, alias, description, label_alias, label_description, enabled, visible, created_at, updated_at) VALUES
(13, 0, 'UNKNOWN', 'Unknown state', 'LBL_ELEMENT_TYPE_STATE_UNKNOWN', 'LBL_ELEMENT_TYPE_STATE_UNKNOWN_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(13, 1, 'UP', 'The state of the equipment is: comunicates', 'LBL_ELEMENT_TYPE_STATE_UP', 'LBL_ELEMENT_TYPE_STATE_UP_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(13, 2, 'DOWN', 'The state of the equipment is: out of communication', 'LBL_ELEMENT_TYPE_STATE_DOWN', 'LBL_ELEMENT_TYPE_STATE_DOWN_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(13, 3, 'ALARMS', 'The state of the equipment is: with alarms', 'LBL_ELEMENT_TYPE_STATE_ALARMS', 'LBL_ELEMENT_TYPE_STATE_ALARMS_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(13, 4, 'MAINTENANCE', 'The state of the equipment is: on Maintenance', 'LBL_ELEMENT_TYPE_STATE_MAINTENANCE', 'LBL_ELEMENT_TYPE_STATE_MAINTENANCE_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: master.element_type_state_transitions
--
INSERT INTO master.element_type_state_transitions
(element_type_id, current_element_type_state_id, next_element_type_state_id, enabled, visible, editable, created_at, updated_at) VALUES
(13, 0, 1, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(13, 0, 2, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(13, 1, 2, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(13, 1, 3, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(13, 2, 1, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(13, 2, 3, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(13, 3, 1, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(13, 3, 2, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: master.command_element_types
--
INSERT INTO master.command_element_types
(command_element_type_id, element_type_id, alias, description, label_alias, label_description, enabled, visible, created_at, updated_at) VALUES
(1, 13, 'GREEN_LIGHT', 'GREEN_LIGHT', 'LBL_COMMAND_ELEMENT_TYPE_GREEN_LIGHT', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 13, 'AMBER_LIGHT', 'AMBER_LIGHT', 'LBL_COMMAND_ELEMENT_TYPE_AMBER_LIGHT', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 13, 'RED_LIGHT', 'RED_LIGHT', 'LBL_COMMAND_ELEMENT_TYPE_RED_LIGHT', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 13, 'SWITCH_OFF', 'SWITCH_OFF', 'LBL_COMMAND_ELEMENT_TYPE_SEM_SWITCH_OFF', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 13, 'FLASHING_AMBER', 'FLASHING_AMBER', 'LBL_COMMAND_ELEMENT_TYPE_FLASHING_AMBER', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: master.io_controller_module_type_element_types
--
INSERT INTO master.io_controller_module_type_element_types
(module_type_id, element_type_id, enabled, visible, created_at, updated_at) VALUES
(1, 13, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: conf.alarm_configs
--
INSERT INTO conf.alarm_configs 
(alarm_config_id, alarm_type_id, alarm_level_id, element_type_id, element_id, element_type_state_id, mine, treatment, alias, description, label_alias, label_description, enabled, visible, created_at, updated_at)VALUES
(010013, 4, 3, 13, NULL, NULL, true, true, 'ALARM SEM NO CONNECTION', 'ALARM SEM NO CONNECTION', 'LBL_ALARM_CONFIGS_ALARM_SEM_3_LIGHTS_NO_CONNECTION', 'LBL_ALARM_CONFIGS_ALARM_SEM_3_LIGHTS_NO_CONNECTION_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: conf.element_state_alarms
--
INSERT INTO conf.element_state_alarms
(element_type_id, element_type_state_id, alarm_config_id, created_at, updated_at) VALUES
(13, 2, 010013, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
