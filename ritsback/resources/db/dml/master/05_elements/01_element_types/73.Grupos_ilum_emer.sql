-- 
-- Table: master.element_types
--
INSERT INTO master.element_types
(element_type_id, alias, description,label_alias, label_description, initial_element_type_state_id, connected_element_type_state_id, disconnected_element_type_state_id, enabled, visible, created_at, updated_at) VALUES
(73, 'GRUPO iLUMINACION EMERGENCIA', 'GRUPO iLUMINACION EMERGENCIA', 'LBL_ELEMENT_TYPE_G_ILUM_EMER', 'LBL_ELEMENT_TYPE_G_ILUM_EMER_DESC', 0, 1, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

--
-- Table: master.element_type_params
--
INSERT INTO master.element_type_params (element_type_id, param_type_id, element_type_param_id, element_type_param_unit_id, data_type_id, default_value, alias, description, label_alias, label_description, enabled, visible, editable, created_at, updated_at) VALUES
--CONFIG
(73, 1, 1, NULL, 2, NULL, 'brand', 'brand', 'LBL_ELEMENT_TYPE_PARAM_BRAND' , 'LBL_ELEMENT_TYPE_PARAM_BRAND_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(73, 1, 2, NULL, 2, NULL, 'model', 'model', 'LBL_ELEMENT_TYPE_PARAM_MODEL' , 'LBL_ELEMENT_TYPE_PARAM_MODEL_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(73, 1, 1003, NULL, 2, NULL, 'MANGO SESSION_UID', 'MANGO SESSION_UID', 'LBL_ELEMENT_TYPE_PARAM_MANGO_SESSION_UID' , 'LBL_ELEMENT_TYPE_PARAM_MANGO_SESSION_UID_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--MEASURES
(73, 2, 1, NULL, 1, null, 'Arc_level', 'Arc_level', 'LBL_ELEMENT_TYPE_PARAM_ARC_LEVEL' , 'LBL_ELEMENT_TYPE_PARAM_ARC_LEVEL_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(73, 2, 2, NULL, 5, null, 'OFF', 'OFF', 'LBL_ELEMENT_TYPE_PARAM_OFF' , 'LBL_ELEMENT_TYPE_PARAM_OFF_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(73, 2, 3, NULL, 5, null, 'REMOTE', 'REMOTE', 'LBL_ELEMENT_TYPE_PARAM_REMOTE' , 'LBL_ELEMENT_TYPE_PARAM_REMOTE_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


-- 
-- Table: master.element_type_states
--
INSERT INTO master.element_type_states(element_type_id, element_type_state_id, alias, description, label_alias, label_description, enabled, visible, created_at, updated_at) VALUES
(73, 0, 'UNKNOWN', 'Unknown state', 'LBL_ELEMENT_TYPE_STATE_UNKNOWN', 'LBL_ELEMENT_TYPE_STATE_UNKNOWN_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(73, 1, 'UP', 'The state of the equipment is: comunicates', 'LBL_ELEMENT_TYPE_STATE_UP', 'LBL_ELEMENT_TYPE_STATE_UP_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(73, 2, 'DOWN', 'The state of the equipment is: out of communication', 'LBL_ELEMENT_TYPE_STATE_DOWN', 'LBL_ELEMENT_TYPE_STATE_DOWN_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(73, 3, 'ALARMS', 'The state of the equipment is: with alarms', 'LBL_ELEMENT_TYPE_STATE_ALARMS', 'LBL_ELEMENT_TYPE_STATE_ALARMS_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(73, 4, 'MAINTENANCE', 'The state of the equipment is: on Maintenance', 'LBL_ELEMENT_TYPE_STATE_MAINTENANCE', 'LBL_ELEMENT_TYPE_STATE_MAINTENANCE_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: master.element_type_state_transitions
--
INSERT INTO master.element_type_state_transitions (element_type_id, current_element_type_state_id, next_element_type_state_id, enabled, visible, editable, created_at, updated_at) VALUES
(73, 1, 2, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(73, 1, 3, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(73, 2, 1, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(73, 2, 3, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(73, 3, 1, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(73, 3, 2, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(73, 0, 1, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(73, 0, 2, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);



INSERT INTO master.command_element_types
(command_element_type_id, element_type_id, alias, description, label_alias, label_description, on_change, enabled, visible, created_at, updated_at) VALUES
(1, 73, 'OFF', 'OFF', 'LBL_COMMAND_ELEMENT_TYPE_OFF', null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 73, 'ON 0 percent', 'ON 0 percent', 'LBL_COMMAND_ELEMENT_TYPE_ON_0', null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 73, 'ON 0.02 percent', 'ON 0.02 percent', 'LBL_COMMAND_ELEMENT_TYPE_ON_0_02', null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 73, 'ON 0.1 percent', 'ON 0.1 percent', 'LBL_COMMAND_ELEMENT_TYPE_ON_0_1', null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 73, 'ON 0.2 percent', 'ON 0.2 percent', 'LBL_COMMAND_ELEMENT_TYPE_ON_0_2', null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(6, 73, 'ON 1 percent', 'ON 1 percent', 'LBL_COMMAND_ELEMENT_TYPE_ON_1', null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: master.io_controller_module_type_element_types
--
INSERT INTO master.io_controller_module_type_element_types
(module_type_id, element_type_id, enabled, visible, created_at, updated_at) VALUES
(1, 73, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

--
-- Table: conf.alarm_configs
--
INSERT INTO conf.alarm_configs
(alarm_config_id, alarm_type_id, alarm_level_id, element_type_id, element_id, element_type_state_id, mine, treatment, alias, description, label_alias, label_description, enabled, visible, created_at, updated_at) VALUES
(010073, 2, 3, 73, NULL, NULL, true, true, 'ALARM GRUPO NO CONNECTION', 'ALARM GRUPO NO CONNECTION', 'LBL_ALARM_CONFIGS_ALARM_GRUPO_NO_CONNECTION', 'LBL_ALARM_CONFIGS_ALARM_GRUPO_NO_CONNECTION_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: conf.element_state_alarms
--
INSERT INTO conf.element_state_alarms
(element_type_id, element_type_state_id, alarm_config_id, created_at, updated_at) VALUES
(73, 2, 010073, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
