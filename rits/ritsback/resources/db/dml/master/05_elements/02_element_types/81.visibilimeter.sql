-- 
-- Table: master.element_types
--
INSERT INTO master.element_types(element_type_id, alias, description, label_alias, label_description, initial_element_type_state_id, connected_element_type_state_id, disconnected_element_type_state_id, enabled, visible, created_at, updated_at)
VALUES(81, 'VISIBILIMETER', 'Visibilimeter','LBL_ELEMENT_TYPE_VISIBILIMETER', 'LBL_ELEMENT_TYPE_VISIBILIMETER_DESC', 0, 1, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

--
-- Table: master.element_type_params
--
INSERT INTO master.element_type_params (element_type_id, param_type_id, element_type_param_id, data_type_id, default_value, alias, description, label_alias, label_description, enabled, visible, editable, created_at, updated_at) VALUES
--CONFIG
(81, 1, 1, 2, ' ', 'brand', 'brand', 'LBL_ELEMENT_TYPE_PARAM_BRAND' , 'LBL_ELEMENT_TYPE_PARAM_BRAND_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(81, 1, 2, 2, ' ', 'model', 'model', 'LBL_ELEMENT_TYPE_PARAM_MODEL' , 'LBL_ELEMENT_TYPE_PARAM_MODEL_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(81, 1, 3, 2, ' ', 'plc_alias', 'plc_alias', 'LBL_ELEMENT_TYPE_PARAM_VISIBILIMETER_PLC_ALIAS' , 'LBL_ELEMENT_TYPE_PARAM_VISIBILIMETER_PLC_ALIAS_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(81, 1, 4, 2, ' ', 'road', 'road', 'LBL_ELEMENT_TYPE_PARAM_ROAD' , 'LBL_ELEMENT_TYPE_PARAM_ROAD_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(81, 1, 5, 2, ' ', 'alias_section', 'alias_section', 'LBL_ELEMENT_TYPE_PARAM_ALIAS_SECTION' , 'LBL_ELEMENT_TYPE_PARAM_ALIAS_SECTION_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(81, 1, 6, 2, '', 'CH', 'CH', 'LBL_ELEMENT_TYPE_PARAM_PK' , 'LBL_ELEMENT_TYPE_PARAM_PK_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(81, 1, 1003, 2, ' ', 'MANGO SESSION_UID', 'MANGO SESSION_UID', 'LBL_ELEMENT_TYPE_PARAM_MANGO_SESSION_UID' , 'LBL_ELEMENT_TYPE_PARAM_MANGO_SESSION_UID_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
--MEASURE
(81, 2, 1, 3, NULL, 'visibility_pv', 'Visibility PV', 'LBL_ELEMENT_TYPE_PARAM_VISIBILITY_PV', 'LBL_ELEMENT_TYPE_PARAM_VISIBILITY_PV_DESC', true, true, false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(81, 2, 2, 3, NULL, 'visibility_trigger_1_sp', 'Visibility Trigger 1 SP', 'LBL_ELEMENT_TYPE_PARAM_VISIBILITY_TRIGGER_1_SP', 'LBL_ELEMENT_TYPE_PARAM_VISIBILITY_TRIGGER_1_SP_DESC', true, true, false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(81, 2, 3, 3, NULL, 'visibility_trigger_2_sp', 'Visibility Trigger 2 SP', 'LBL_ELEMENT_TYPE_PARAM_VISIBILITY_TRIGGER_2_SP', 'LBL_ELEMENT_TYPE_PARAM_VISIBILITY_TRIGGER_2_SP_DESC', true, true, false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: master.element_type_states
--
INSERT INTO master.element_type_states(element_type_id, element_type_state_id, alias, description, label_alias, label_description, enabled, visible, created_at, updated_at) VALUES
(81, 0, 'UNKNOWN', 'Unknown state', 'LBL_ELEMENT_TYPE_STATE_UNKNOWN', 'LBL_ELEMENT_TYPE_STATE_UNKNOWN_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(81, 1, 'UP', 'The state of the equipment is: comunicates', 'LBL_ELEMENT_TYPE_STATE_UP', 'LBL_ELEMENT_TYPE_STATE_UP_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(81, 2, 'DOWN', 'The state of the equipment is: out of communication', 'LBL_ELEMENT_TYPE_STATE_DOWN', 'LBL_ELEMENT_TYPE_STATE_DOWN_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(81, 3, 'ALARMS', 'The state of the equipment is: with alarms', 'LBL_ELEMENT_TYPE_STATE_ALARMS', 'LBL_ELEMENT_TYPE_STATE_ALARMS_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(81, 4, 'MAINTENANCE', 'The state of the equipment is: on Maintenance', 'LBL_ELEMENT_TYPE_STATE_MAINTENANCE', 'LBL_ELEMENT_TYPE_STATE_MAINTENANCE_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

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
