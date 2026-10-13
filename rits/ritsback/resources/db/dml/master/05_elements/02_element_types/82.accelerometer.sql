-- 
-- Table: master.element_types
--
INSERT INTO master.element_types(element_type_id, alias, description, label_alias, label_description, initial_element_type_state_id, connected_element_type_state_id, disconnected_element_type_state_id, enabled, visible, created_at, updated_at)
VALUES(82, 'ACCELEROMETER', 'Accelerometer','LBL_ELEMENT_TYPE_ACCELEROMETER', 'LBL_ELEMENT_TYPE_ACCELEROMETER_DESC', 0, 1, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

--
-- Table: master.element_type_params
--
INSERT INTO master.element_type_params (element_type_id, param_type_id, element_type_param_id, data_type_id, default_value, alias, description, label_alias, label_description, enabled, visible, editable, created_at, updated_at) VALUES
---CONFIG
(82, 1, 1, 2, ' ', 'brand', 'brand', 'LBL_ELEMENT_TYPE_PARAM_BRAND' , 'LBL_ELEMENT_TYPE_PARAM_BRAND_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(82, 1, 2, 2, ' ', 'model', 'model', 'LBL_ELEMENT_TYPE_PARAM_MODEL' , 'LBL_ELEMENT_TYPE_PARAM_MODEL_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(82, 1, 3, 2, ' ', 'plc_alias', 'plc_alias', 'LBL_ELEMENT_TYPE_PARAM_ACCELEROMETER_PLC_ALIAS' , 'LBL_ELEMENT_TYPE_PARAM_ACCELEROMETER_PLC_ALIAS_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(82, 1, 4, 2, ' ', 'road', 'road', 'LBL_ELEMENT_TYPE_PARAM_ROAD' , 'LBL_ELEMENT_TYPE_PARAM_ROAD_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(82, 1, 5, 2, ' ', 'alias_section', 'alias_section', 'LBL_ELEMENT_TYPE_PARAM_ALIAS_SECTION' , 'LBL_ELEMENT_TYPE_PARAM_ALIAS_SECTION_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(82, 1, 6, 2, '', 'CH', 'CH', 'LBL_ELEMENT_TYPE_PARAM_PK' , 'LBL_ELEMENT_TYPE_PARAM_PK_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(82, 1, 1003, 2, ' ', 'MANGO SESSION_UID', 'MANGO SESSION_UID', 'LBL_ELEMENT_TYPE_PARAM_MANGO_SESSION_UID' , 'LBL_ELEMENT_TYPE_PARAM_MANGO_SESSION_UID_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
-- 
-- Table: master.element_type_states
--
INSERT INTO master.element_type_states(element_type_id, element_type_state_id, alias, description, label_alias, label_description, enabled, visible, created_at, updated_at) VALUES
(82, 0, 'UNKNOWN', 'Unknown state', 'LBL_ELEMENT_TYPE_STATE_UNKNOWN', 'LBL_ELEMENT_TYPE_STATE_UNKNOWN_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(82, 1, 'UP', 'The state of the equipment is: comunicates', 'LBL_ELEMENT_TYPE_STATE_UP', 'LBL_ELEMENT_TYPE_STATE_UP_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(82, 2, 'DOWN', 'The state of the equipment is: out of communication', 'LBL_ELEMENT_TYPE_STATE_DOWN', 'LBL_ELEMENT_TYPE_STATE_DOWN_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(82, 3, 'ALARMS', 'The state of the equipment is: with alarms', 'LBL_ELEMENT_TYPE_STATE_ALARMS', 'LBL_ELEMENT_TYPE_STATE_ALARMS_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(82, 4, 'MAINTENANCE', 'The state of the equipment is: on Maintenance', 'LBL_ELEMENT_TYPE_STATE_MAINTENANCE', 'LBL_ELEMENT_TYPE_STATE_MAINTENANCE_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: master.element_type_state_transitions
--
INSERT INTO master.element_type_state_transitions (element_type_id, current_element_type_state_id, next_element_type_state_id, enabled, visible, editable, created_at, updated_at) VALUES
(82, 1, 2, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(82, 1, 3, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(82, 2, 1, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(82, 2, 3, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(82, 3, 1, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(82, 3, 2, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(82, 0, 1, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(82, 0, 2, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: master.io_controller_module_type_element_types
--
INSERT INTO master.io_controller_module_type_element_types
(module_type_id, element_type_id, enabled, visible, created_at, updated_at) VALUES
(1, 82, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

--
-- Table: conf.alarm_configs
--
INSERT INTO conf.alarm_configs 
(alarm_config_id, alarm_type_id, alarm_level_id, element_type_id, element_id, element_type_state_id, mine, treatment, alias, description, label_alias, label_description, enabled, visible, created_at, updated_at) values
(233, 2, 3, 82, NULL, NULL, true, true, 'ALARM ACCELEROMETER NO CONNECTION', 'ALARM ACCELEROMETER NO CONNECTION', 'LBL_ALARM_CONFIGS_ACCELEROMETER_NO_CONNECTION', 'LBL_ALARM_CONFIGS_ACCELEROMETER_NO_CONNECTION', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(234, 2, 5, 82, NULL, 3, true, true, 'ALARM ACCELEROMETER EVENT', 'ALARM ACCELEROMETER EVENT', 'LBL_ALARM_CONFIGS_ACCELEROMETER_EVENT', 'LBL_ALARM_CONFIGS_ACCELEROMETER_EVENT', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: conf.element_state_alarms
--
INSERT INTO conf.element_state_alarms
(element_type_id, element_type_state_id, alarm_config_id, created_at, updated_at) VALUES
(82, 2, 233, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
