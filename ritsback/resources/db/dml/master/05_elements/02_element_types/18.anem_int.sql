-- 
-- Table: master.element_types
--
INSERT INTO master.element_types
(element_type_id, alias, description, label_alias, label_description, initial_element_type_state_id, connected_element_type_state_id, disconnected_element_type_state_id, enabled, visible, created_at, updated_at) VALUES
(18, 'ANEM_INT', 'ANEM_INT', 'LBL_ELEMENT_TYPE_ANEM_INT', 'LBL_ELEMENT_TYPE_ANEM_INT_DESC', 0, 1, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

--
-- Table: master.element_type_params
--
INSERT INTO master.element_type_params (element_type_id, param_type_id, element_type_param_id, element_type_param_unit_id, data_type_id, threshold_1, threshold_2, default_value, alias, description, label_alias, label_description, enabled, visible, editable, created_at, updated_at) VALUES
--CONFIG
(18, 1, 1, NULL, 2, NULL, NULL, '', 'brand', 'brand', 'LBL_ELEMENT_TYPE_PARAM_BRAND' , 'LBL_ELEMENT_TYPE_PARAM_BRAND_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(18, 1, 2, NULL, 2, NULL, NULL, '', 'model', 'model', 'LBL_ELEMENT_TYPE_PARAM_MODEL' , 'LBL_ELEMENT_TYPE_PARAM_MODEL_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(18, 1, 1003, NULL, 2, NULL, NULL, '', 'MANGO SESSION_UID', 'MANGO SESSION_UID', 'LBL_ELEMENT_TYPE_PARAM_MANGO_SESSION_UID' , 'LBL_ELEMENT_TYPE_PARAM_MANGO_SESSION_UID_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
--MEASURE
(18, 2, 1, 6, 3, '-65534', '65534', '32767', 'wind_speed', 'wind_speed', 'LBL_ELEMENT_TYPE_PARAM_WIND_SPEED' , 'LBL_ELEMENT_TYPE_PARAM_WIND_SPEED_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(18, 2, 2, NULL,5, NULL, NULL, '', 'traffic_direction', 'traffic_direction', 'LBL_ELEMENT_TYPE_PARAM_TRAFFIC_DIRECTION' , 'LBL_ELEMENT_TYPE_PARAM_TRAFFIC_DIRECTION_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

--
-- Table: master.element_type_states
--
INSERT INTO master.element_type_states(element_type_id, element_type_state_id, alias, description, label_alias, label_description, enabled, visible, created_at, updated_at) VALUES
(18, 0, 'UNKNOWN', 'Unknown state', 'LBL_ELEMENT_TYPE_STATE_UNKNOWN', 'LBL_ELEMENT_TYPE_STATE_UNKNOWN_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(18, 1, 'UP', 'The state of the equipment is: comunicates', 'LBL_ELEMENT_TYPE_STATE_UP', 'LBL_ELEMENT_TYPE_STATE_UP_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(18, 2, 'DOWN', 'The state of the equipment is: out of communication', 'LBL_ELEMENT_TYPE_STATE_DOWN', 'LBL_ELEMENT_TYPE_STATE_DOWN_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(18, 3, 'ALARMS', 'The state of the equipment is: with alarms', 'LBL_ELEMENT_TYPE_STATE_ALARMS', 'LBL_ELEMENT_TYPE_STATE_ALARMS_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(18, 4, 'MAINTENANCE', 'The state of the equipment is: on Maintenance', 'LBL_ELEMENT_TYPE_STATE_MAINTENANCE', 'LBL_ELEMENT_TYPE_STATE_MAINTENANCE_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: master.element_type_state_transitions
--
INSERT INTO master.element_type_state_transitions 
(element_type_id, current_element_type_state_id, next_element_type_state_id, enabled, visible, editable, created_at, updated_at) VALUES
(18, 1, 2, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(18, 1, 3, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(18, 2, 1, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(18, 2, 3, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(18, 3, 1, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(18, 3, 2, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(18, 0, 1, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(18, 0, 2, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: master.io_controller_module_type_element_types
--
INSERT INTO master.io_controller_module_type_element_types
(module_type_id, element_type_id, enabled, visible, created_at, updated_at) VALUES
(1, 18, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: conf.alarm_configs
--
INSERT INTO conf.alarm_configs 
(alarm_config_id, alarm_type_id, alarm_level_id, element_type_id, element_id, element_type_state_id, mine, treatment, alias, description, label_alias, label_description, enabled, visible, created_at, updated_at)VALUES
(010018, 2, 3, 18, NULL, NULL, true, true, 'ALARM ANEM_INT NO CONNECTION', 'ALARM ANEM_INT NO CONNECTION', 'LBL_ALARM_CONFIGS_ALARM_ANEM_INT_NO_CONNECTION', 'LBL_ALARM_CONFIGS_ALARM_ANEM_INT_NO_CONNECTION_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(020018, 2, 3, 18, NULL, 3, true, true, 'ALARM SENSOR', 'ALARM SENSOR', 'LBL_ALARM_CONFIGS_ALARM_ANEM_SENSOR', 'LBL_ALARM_CONFIGS_ALARM_SENSOR_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(030018, 2, 3, 18, NULL, 3, true, true, 'ALARM FA', 'ALARM FA', 'LBL_ALARM_CONFIGS_ALARM_ANEM_FA', 'LBL_ALARM_CONFIGS_ALARM_FA_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: conf.element_state_alarms
--
INSERT INTO conf.element_state_alarms
(element_type_id, element_type_state_id, alarm_config_id, created_at, updated_at) VALUES
(18, 2, 010018, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
