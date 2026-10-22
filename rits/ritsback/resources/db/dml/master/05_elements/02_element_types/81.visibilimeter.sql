-- 
-- Table: master.element_types
--
INSERT INTO master.element_types(element_type_id, alias, description, label_alias, label_description, initial_element_type_state_id, connected_element_type_state_id, disconnected_element_type_state_id, enabled, visible, created_at, updated_at)
VALUES(81, 'VISIBILIMETER', 'Visibilimeter','LBL_ELEMENT_TYPE_VISIBILIMETER', 'LBL_ELEMENT_TYPE_VISIBILIMETER', 0, 1, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

--
-- Table: master.element_type_params
--
INSERT INTO master.element_type_params (element_type_id, param_type_id, element_type_param_id, data_type_id, default_value, alias, description, label_alias, label_description, enabled, visible, editable, created_at, updated_at) VALUES
(81, 1, 1, 2, ' ', 'brand', 'brand', 'LBL_ELEMENT_TYPE_PARAM_BRAND' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(81, 1, 2, 2, ' ', 'model', 'model', 'LBL_ELEMENT_TYPE_PARAM_MODEL' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(81, 1, 3, 2, ' ', 'plc_alias', 'plc_alias', 'LBL_ELEMENT_TYPE_PARAM_PLC_ALIAS' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(81, 1, 4, 2, ' ', 'road', 'road', 'LBL_ELEMENT_TYPE_PARAM_ROAD' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(81, 1, 5, 2, ' ', 'alias_section', 'alias_section', 'LBL_ELEMENT_TYPE_PARAM_ALIAS_SECTION' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(81, 1, 6, 2, '', 'CH', 'CH', 'LBL_ELEMENT_TYPE_PARAM_PK' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(81, 1, 1003, 2, ' ', 'MANGO SESSION_UID', 'MANGO SESSION_UID', 'LBL_ELEMENT_TYPE_PARAM_MANGO SESSION_UID' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(81, 2, 1, 3, NULL, 'visibility_pv', 'Visibility PV', 'LBL_ELEMENT_TYPE_PARAM_VISIBILITY_PV', null, true, true, false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(81, 2, 2, 3, NULL, 'visibility_trigger_1_sp', 'Visibility Trigger 1 SP', 'LBL_ELEMENT_TYPE_PARAM_VISIBILITY_TRIGGER_1_SP', null, true, true, false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(81, 2, 3, 3, NULL, 'visibility_trigger_2_sp', 'Visibility Trigger 2 SP', 'LBL_ELEMENT_TYPE_PARAM_VISIBILITY_TRIGGER_2_SP', null, true, true, false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: master.element_type_states
--
INSERT INTO master.element_type_states(element_type_id, element_type_state_id, alias, description, label_alias, label_description, enabled, visible, created_at, updated_at) VALUES
(81, 0, 'UNKNOWN', 'Unknown state', 'LBL_ELEMENT_TYPE_STATE_UNKNOWN', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(81, 1, 'UP', 'The state of the equipment is: comunicates', 'LBL_ELEMENT_TYPE_STATE_UP', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(81, 2, 'DOWN', 'The state of the equipment is: out of communication', 'LBL_ELEMENT_TYPE_STATE_DOWN', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(81, 3, 'ALARMS', 'The state of the equipment is: with alarms', 'LBL_ELEMENT_TYPE_STATE_ALARMS', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: master.element_type_state_transitions
--
INSERT INTO master.element_type_state_transitions (element_type_id, current_element_type_state_id, next_element_type_state_id, enabled, visible, editable, created_at, updated_at) VALUES
(81, 1, 2, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(81, 1, 3, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(81, 2, 1, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(81, 2, 3, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(81, 3, 1, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(81, 3, 2, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(81, 0, 1, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(81, 0, 2, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: master.io_controller_module_type_element_types
--
INSERT INTO master.io_controller_module_type_element_types
(module_type_id, element_type_id, enabled, visible, created_at, updated_at) VALUES
(1, 81, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

--
-- Table: conf.alarm_configs
--
INSERT INTO conf.alarm_configs 
(alarm_config_id, alarm_type_id, alarm_level_id, element_type_id, element_id, element_type_state_id, mine, treatment, alias, description, label_alias, label_description, enabled, visible, created_at, updated_at) values
(229, 2, 5, 81, NULL, NULL, true, true, 'ALARM VISIBILIMETER NO CONNECTION', 'ALARM VISIBILIMETER NO CONNECTION', 'LBL_ALARM_CONFIGS_VISIBILIMETER_NO_CONNECTION', 'LBL_ALARM_CONFIGS_VISIBILIMETER_NO_CONNECTION', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(230, 2, 5, 81, NULL, 3, true, true, 'ALARM VISIBILIMETER UNDER 1000M', 'ALARM VISIBILIMETER UNDER 1000M', 'LBL_ALARM_CONFIGS_VISIBILIMETER_UNDER_1000M', 'LBL_ALARM_CONFIGS_VISIBILIMETER_UNDER_1000M',true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(231, 2, 6, 81, NULL, 3, true, true, 'ALARM VISIBILIMETER UNDER 120M', 'ALARM VISIBILIMETER UNDER 120M', 'LBL_ALARM_CONFIGS_VISIBILIMETER_UNDER_120M', 'LBL_ALARM_CONFIGS_VISIBILIMETER_UNDER_120M',true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(232, 2, 5, 81, NULL, 3, true, true, 'ALARM VISIBILIMETER SYSTEM_FAULT', 'ALARM VISIBILIMETER SYSTEM_FAULT', 'LBL_ALARM_CONFIGS_VISIBILIMETER_SYSTEM_FAULT', 'LBL_ALARM_CONFIGS_VISIBILIMETER_SYSTEM_FAULT',true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: conf.element_state_alarms
--
INSERT INTO conf.element_state_alarms
(element_type_id, element_type_state_id, alarm_config_id, created_at, updated_at) VALUES
(81, 2, 229, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
