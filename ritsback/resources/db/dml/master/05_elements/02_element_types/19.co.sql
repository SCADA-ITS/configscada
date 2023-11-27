-- 
-- Table: master.element_types
--
INSERT INTO master.element_types
(element_type_id, alias, description,label_alias, label_description, initial_element_type_state_id, connected_element_type_state_id, disconnected_element_type_state_id, enabled, visible, created_at, updated_at) VALUES
(19, 'CO', 'CO', 'LBL_ELEMENT_TYPE_CO', 'LBL_ELEMENT_TYPE_CO_DESC', 0, 1, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

--
-- Table: master.element_type_params
--
INSERT INTO master.element_type_params (element_type_id, param_type_id, element_type_param_id, element_type_param_unit_id, data_type_id, threshold_1, threshold_2, default_value, alias, description, label_alias, label_description, enabled, visible, editable, created_at, updated_at) VALUES
--CONFIG
(19,1,1, NULL,2, NULL, NULL, '', 'brand', 'brand', 'LBL_ELEMENT_TYPE_PARAM_BRAND' , 'LBL_ELEMENT_TYPE_PARAM_BRAND_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(19,1,2, NULL,2, NULL, NULL, '', 'model', 'model', 'LBL_ELEMENT_TYPE_PARAM_MODEL' , 'LBL_ELEMENT_TYPE_PARAM_MODEL_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(19, 1, 1003, NULL,2, NULL, NULL, '', 'MANGO SESSION_UID', 'MANGO SESSION_UID', 'LBL_ELEMENT_TYPE_PARAM_MANGO_SESSION_UID' , 'LBL_ELEMENT_TYPE_PARAM_MANGO_SESSION_UID_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
--MEASURE
(19, 2, 1, 29, 3, '0', '65534', '65535', 'co_concentration', 'CO_concentration', 'LBL_ELEMENT_TYPE_PARAM_CO_CONCENTRATION' , 'LBL_ELEMENT_TYPE_PARAM_CO_CONCENTRATION_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

--
-- Table: master.element_type_states
--
INSERT INTO master.element_type_states(element_type_id, element_type_state_id, alias, description, label_alias, label_description, enabled, visible, created_at, updated_at) VALUES
(19, 0, 'UNKNOWN', 'Unknown state', 'LBL_ELEMENT_TYPE_STATE_UNKNOWN', 'LBL_ELEMENT_TYPE_STATE_UNKNOWN_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(19, 1, 'UP', 'The state of the equipment is: comunicates', 'LBL_ELEMENT_TYPE_STATE_UP', 'LBL_ELEMENT_TYPE_STATE_UP_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(19, 2, 'DOWN', 'The state of the equipment is: out of communication', 'LBL_ELEMENT_TYPE_STATE_DOWN', 'LBL_ELEMENT_TYPE_STATE_DOWN_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(19, 3, 'ALARMS', 'The state of the equipment is: with alarms', 'LBL_ELEMENT_TYPE_STATE_ALARMS', 'LBL_ELEMENT_TYPE_STATE_ALARMS_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(19, 4, 'MAINTENANCE', 'The state of the equipment is: on Maintenance', 'LBL_ELEMENT_TYPE_STATE_MAINTENANCE', 'LBL_ELEMENT_TYPE_STATE_MAINTENANCE_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: master.element_type_state_transitions
--
INSERT INTO master.element_type_state_transitions 
(element_type_id, current_element_type_state_id, next_element_type_state_id, enabled, visible, editable, created_at, updated_at) VALUES
(19, 1, 2, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(19, 1, 3, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(19, 2, 1, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(19, 2, 3, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(19, 3, 1, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(19, 3, 2, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(19, 0, 1, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(19, 0, 2, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: master.element_hierarchies_allowed
--
INSERT INTO master.element_hierarchies_allowed
(parent_element_type_id, child_element_type_id, enabled, visible, created_at, updated_at) VALUES
(19, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: master.io_controller_module_type_element_types
--
INSERT INTO master.io_controller_module_type_element_types
(module_type_id, element_type_id, enabled, visible, created_at, updated_at) VALUES
(1, 19, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: conf.alarm_configs
--
INSERT INTO conf.alarm_configs 
(alarm_config_id, alarm_type_id, alarm_level_id, element_type_id, element_id, element_type_state_id, mine, treatment, alias, description, label_alias, label_description, enabled, visible, created_at, updated_at)VALUES
(54, 2, 3, 19, NULL, NULL, true, true, 'ALARM CO NO CONNECTION', 'ALARM CO NO CONNECTION', 'LBL_ALARM_CONFIGS_ALARM_CO_NO_CONNECTION', 'LBL_ALARM_CONFIGS_ALARM_CO_NO_CONNECTION_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(246, 2, 5, 19, NULL, 3, true, true, 'ALARM SENSOR', 'ALARM SENSOR', 'LBL_ALARM_CONFIGS_ALARM_CO_SENSOR', 'LBL_ALARM_CONFIGS_ALARM_SENSOR_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(258, 2, 3, 19, NULL, 3, true, true, 'ALARM FA', 'ALARM FA', 'LBL_ALARM_CONFIGS_ALARM_CO_FA', 'LBL_ALARM_CONFIGS_ALARM_FA_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(308, 2, 5, 19, NULL, 3, true, true, 'ALARM CO OUT OF RANGE', 'ALARM CO OUT OF RANGE', 'LBL_ALARM_CONFIGS_ALARM_CO_OUT_OF_RANGE', 'LBL_ALARM_CONFIGS_ALARM_CO_OUT_OF_RANGE_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1901, 2, 5, 19, NULL, 3, true, true, 'ALARM CO BROKEN THREAD', 'ALARM CO BROKEN THREAD', 'LBL_ALARM_CONFIGS_ALARM_CO_BROKEN_THREAD', 'LBL_ALARM_CONFIGS_ALARM_CO_BROKEN_THREAD_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1902, 2, 5, 19, NULL, 3, true, true, 'ALARM EQUIPMENT FAIL', 'ALARM EQUIPMENT FAIL', 'LBL_ALARM_CONFIGS_ALARM_EQUIPMENT_FAIL', 'LBL_ALARM_CONFIGS_ALARM_EQUIPMENT_FAIL_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1903, 2, 3, 19, NULL, 3, true, true, 'ALARM TEMPERATURE OUT OF RANGE', 'ALARM TEMPERATURE OUT OF RANGE', 'LBL_ALARM_CONFIGS_ALARM_TEMPERATURE_OUT_OF_RANGE', 'LBL_ALARM_CONFIGS_ALARM_TEMPERATURE_OUT_OF_RANGE_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1904, 2, 3, 19, NULL, 3, true, true, 'ALARM TEMPERATURE BROKEN THREAD', 'ALARM TEMPERATURE BROKEN THREAD', 'LBL_ALARM_CONFIGS_ALARM_TEMPERATURE_BROKEN_THREAD', 'LBL_ALARM_CONFIGS_ALARM_TEMPERATURE_BROKEN_THREAD_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


-- 
-- Table: conf.element_state_alarms
--
INSERT INTO conf.element_state_alarms
(element_type_id, element_type_state_id, alarm_config_id, created_at, updated_at) VALUES
(19, 2, 54, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
