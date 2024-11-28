-- 
-- Table: master.element_types
--
INSERT INTO master.element_types(element_type_id, alias, description,label_alias, label_description, initial_element_type_state_id, connected_element_type_state_id, disconnected_element_type_state_id, enabled, visible, created_at, updated_at) VALUES
(48, 'TOXIC_CONTROL', 'TOXIC_CONTROL', 'LBL_ELEMENT_TYPE_TOXIC_CONTROL', 'LBL_ELEMENT_TYPE_TOXIC_CONTROL_DESC', 0, 1, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

--
-- Table: master.element_type_params
--
INSERT INTO master.element_type_params (element_type_id, param_type_id, element_type_param_id, element_type_param_unit_id, data_type_id, default_value, alias, description, label_alias, label_description, enabled, visible, editable, created_at, updated_at) VALUES
--CONFIG
(48,1,1, NULL,2, NULL,'brand', 'brand', 'LBL_ELEMENT_TYPE_PARAM_BRAND' , 'LBL_ELEMENT_TYPE_PARAM_BRAND_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(48,1,2, NULL,2, NULL, 'model', 'model', 'LBL_ELEMENT_TYPE_PARAM_MODEL' , 'LBL_ELEMENT_TYPE_PARAM_MODEL_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(48,1,1003, NULL,2, NULL, 'MANGO SESSION_UID', 'MANGO SESSION_UID', 'LBL_ELEMENT_TYPE_PARAM_MANGO_SESSION_UID' , 'LBL_ELEMENT_TYPE_PARAM_MANGO_SESSION_UID_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--MEASURES
(48, 2, 1, NULL, 5, NULL, 'opening_gate', 'opening_gate', 'LBL_ELEMENT_TYPE_PARAM_TOXIC_CONTROL_OPENING_GATE' , 'LBL_ELEMENT_TYPE_PARAM_TOXIC_CONTROL_OPENING_GATE_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(48, 2, 2, NULL, 5, NULL, 'gate_opened', 'gate_opened', 'LBL_ELEMENT_TYPE_PARAM_TOXIC_CONTROL_GATE_OPENED' , 'LBL_ELEMENT_TYPE_PARAM_TOXIC_CONTROL_GATE_OPENED_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);



-- 
-- Table: master.element_type_states
--
INSERT INTO master.element_type_states(element_type_id, element_type_state_id, alias, description, label_alias, label_description, enabled, visible, created_at, updated_at) VALUES
(48, 0, 'UNKNOWN', 'Unknown state', 'LBL_ELEMENT_TYPE_STATE_UNKNOWN', 'LBL_ELEMENT_TYPE_STATE_UNKNOWN_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(48, 1, 'UP', 'The state of the equipment is: comunicates', 'LBL_ELEMENT_TYPE_STATE_UP', 'LBL_ELEMENT_TYPE_STATE_UP_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(48, 2, 'DOWN', 'The state of the equipment is: out of communication', 'LBL_ELEMENT_TYPE_STATE_DOWN', 'LBL_ELEMENT_TYPE_STATE_DOWN_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(48, 3, 'ALARMS', 'The state of the equipment is: with alarms', 'LBL_ELEMENT_TYPE_STATE_ALARMS', 'LBL_ELEMENT_TYPE_STATE_ALARMS_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(48, 4, 'MAINTENANCE', 'The state of the equipment is: on Maintenance', 'LBL_ELEMENT_TYPE_STATE_MAINTENANCE', 'LBL_ELEMENT_TYPE_STATE_MAINTENANCE_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: master.element_type_state_transitions
--
INSERT INTO master.element_type_state_transitions (element_type_id, current_element_type_state_id, next_element_type_state_id, enabled, visible, editable, created_at, updated_at) VALUES
(48, 1, 2, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(48, 1, 3, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(48, 2, 1, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(48, 2, 3, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(48, 3, 1, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(48, 3, 2, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(48, 0, 1, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(48, 0, 2, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: master.io_controller_module_type_element_types
--
INSERT INTO master.io_controller_module_type_element_types
(module_type_id, element_type_id, enabled, visible, created_at, updated_at) VALUES
(1, 48, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: master.command_element_types
--
INSERT INTO master.command_element_types
(command_element_type_id, element_type_id, alias, description, label_alias, label_description, enabled, visible, created_at, updated_at) VALUES
(1, 48, 'OPEN GATE', 'OPEN GATE', 'LBL_COMMAND_ELEMENT_TYPE_TOXIC_CONTROL_OPEN_GATE', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 48, 'CLOSE GATE', 'CLOSE GATE', 'LBL_COMMAND_ELEMENT_TYPE_TOXIC_CONTROL_CLOSE_GATE', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: conf.alarm_configs
--
INSERT INTO conf.alarm_configs
(alarm_config_id, alarm_type_id, alarm_level_id, element_type_id, element_id, element_type_state_id, mine, treatment, alias, description, label_alias, label_description, enabled, visible, created_at, updated_at) VALUES
(010048, 2, 1, 48, NULL, NULL, true, true, 'ALARM TOXIC_CONTROL NO CONNECTION', 'ALARM TOXIC_CONTROL NO CONNECTION', 'LBL_ALARM_CONFIGS_ALARM_TOXIC_CONTROL_NO_CONNECTION', 'LBL_ALARM_CONFIGS_ALARM_TOXIC_CONTROL_NO_CONNECTION_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(020048, 2, 1, 48, NULL, 3, true, true, 'ALARM TOXIC_CONTROL SELECTOR AUTO MANUAL', 'ALARM TOXIC_CONTROL SELECTOR AUTO MANUAL', 'LBL_ALARM_CONFIGS_ALARM_TOXIC_CONTROL_SELECTOR_AUTO_MANUAL', 'LBL_ALARM_CONFIGS_ALARM_TOXIC_CONTROL_SELECTOR_AUTO_MANUAL_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(030048, 2, 1, 48, NULL, 3, true, true, 'ALARM TOXIC_CONTROL TOXIC GATE GOING DOWN', 'ALARM TOXIC_CONTROL TOXIC GATE GOING DOWN', 'LBL_ALARM_CONFIGS_ALARM_TOXIC_CONTROL_TOXIC_GATE_GOING_DOWN', 'LBL_ALARM_CONFIGS_ALARM_TOXIC_CONTROL_TOXIC_GATE_GOING_DOWN_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(040048, 2, 1, 48, NULL, 3, true, true, 'ALARM TOXIC_CONTROL TOXIC GATE MALFUNCTION', 'ALARM TOXIC_CONTROL TOXIC GATE MALFUNCTION', 'LBL_ALARM_CONFIGS_ALARM_TOXIC_CONTROL_TOXIC_GATE_MALFUNCTION', 'LBL_ALARM_CONFIGS_ALARM_TOXIC_CONTROL_TOXIC_GATE_MALFUNCTION_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(050048, 2, 1, 48, NULL, 3, true, true, 'ALARM TOXIC_CONTROL TOXIC GATE LOWERED', 'ALARM TOXIC_CONTROL TOXIC GATE LOWERED', 'LBL_ALARM_CONFIGS_ALARM_TOXIC_CONTROL_TOXIC_GATE_LOWERED', 'LBL_ALARM_CONFIGS_ALARM_TOXIC_CONTROL_TOXIC_GATE_LOWERED_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: conf.element_state_alarms
--
INSERT INTO conf.element_state_alarms
(element_type_id, element_type_state_id, alarm_config_id, created_at, updated_at) VALUES
(48, 2, 010048, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);