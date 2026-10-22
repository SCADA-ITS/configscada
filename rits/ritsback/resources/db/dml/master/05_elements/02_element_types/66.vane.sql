-- 
-- Table: master.element_types
--
INSERT INTO master.element_types
(element_type_id, element_group_id, alias, description,label_alias, label_description, initial_element_type_state_id, connected_element_type_state_id, disconnected_element_type_state_id, enabled, visible, created_at, updated_at) VALUES
(66, NULL, 'Vane', 'Vane', 'LBL_ELEMENT_TYPE_VANE', 'LBL_ELEMENT_TYPE_VANE_DESC', 0, 1, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

--
-- Table: master.element_type_params
--
INSERT INTO master.element_type_params (element_type_id, param_type_id, element_type_param_id, element_type_param_unit_id, data_type_id, threshold_1, threshold_2, default_value, alias, description, label_alias, label_description, enabled, visible, editable, created_at, updated_at) VALUES
(66,1,1, NULL,2, NULL, NULL, ' ', 'PK', 'PK', 'LBL_ELEMENT_TYPE_PARAM_PK' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(66,1,2,1,2, NULL, NULL,' ', 'entrance_tunnel_distance', 'entrance_tunnel_distance', 'LBL_ELEMENT_TYPE_PARAM_ENTRANCE_TUNNEL_DISTANCE' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(66,1,3, NULL,2, NULL, NULL, ' ', 'road', 'road', 'LBL_ELEMENT_TYPE_PARAM_ROAD' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(66,1,4, NULL,2, NULL, NULL, ' ', 'alias_section', 'alias_section', 'LBL_ELEMENT_TYPE_PARAM_ALIAS_SECTION' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(66,1,1003, NULL,2, NULL, NULL, ' ', 'MANGO SESSION_UID', 'MANGO SESSION_UID', 'LBL_ELEMENT_TYPE_PARAM_MANGO_SESSION_UID' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(66,2,1,12,1, NULL, NULL, '', 'wind_direction', 'wind_direction', 'LBL_ELEMENT_TYPE_PARAM_WIND_DIRECTION' , null, true, true, false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(66,2,2,6,3, '-65534', '65534', '', 'wind_speed', 'wind_speed', 'LBL_ELEMENT_TYPE_PARAM_WIND_SPEED' , null, true, true, false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(66,2,3,NULL,5, NULL, NULL, '', 'wind_direction', 'wind_direction', 'LBL_ELEMENT_TYPE_PARAM_WIND_DIRECTION' , null, true, true, false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


INSERT INTO master.element_subtypes
(element_subtype_id, element_type_id, alias, description,label_alias, label_description, enabled, visible, created_at, updated_at)VALUES
(1, 66, 'VANE', 'VANE', 'LBL_ELEMENT_SUBTYPE_VANE', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 66, 'VANE_PLC', 'VANE_PLC', 'LBL_ELEMENT_SUBTYPE_VANE_PLC', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO master.element_subtype_active_params
(element_subtype_id, element_type_id, param_type_id, element_type_param_id, enabled, visible, created_at, updated_at)VALUES

(1, 66, 2, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 66, 2, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(2, 66, 2, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 66, 2, 3, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


-- 
-- Table: master.element_type_states
--
INSERT INTO master.element_type_states(element_type_id, element_type_state_id, alias, description, label_alias, label_description, enabled, visible, created_at, updated_at) VALUES
(66, 0, 'UNKNOWN', 'Unknown state', 'LBL_ELEMENT_TYPE_STATE_UNKNOWN', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(66, 1, 'UP', 'The state of the equipment is: comunicates', 'LBL_ELEMENT_TYPE_STATE_UP', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(66, 2, 'DOWN', 'The state of the equipment is: out of communication', 'LBL_ELEMENT_TYPE_STATE_DOWN', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(66, 3, 'ALARMS', 'The state of the equipment is: with alarms', 'LBL_ELEMENT_TYPE_STATE_ALARMS', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: master.element_type_state_transitions
--
INSERT INTO master.element_type_state_transitions (element_type_id, current_element_type_state_id, next_element_type_state_id, enabled, visible, editable, created_at, updated_at) VALUES
(66, 1, 2, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(66, 1, 3, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(66, 2, 1, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(66, 2, 3, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(66, 3, 1, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(66, 3, 2, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(66, 0, 1, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(66, 0, 2, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: master.io_controller_module_type_element_types
--
INSERT INTO master.io_controller_module_type_element_types
(module_type_id, element_type_id, enabled, visible, created_at, updated_at) VALUES
(1, 66, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

--
-- Table: conf.alarm_configs
--
INSERT INTO conf.alarm_configs
(alarm_config_id, alarm_type_id, alarm_level_id, element_type_id, element_id, element_type_state_id, mine, treatment, alias, description, label_alias, label_description, enabled, visible, created_at, updated_at) VALUES
(209, 2, 3, 66, NULL, NULL, true, true, 'ALARM VANE NO CONNECTION', 'ALARM VANE NO CONNECTION', 'LBL_ALARM_CONFIGS_ALARM_VANE_NO_CONNECTION', 'LBL_ALARM_CONFIGS_ALARM_VANE_NO_CONNECTION_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(310, 2, 5, 66, NULL, 3, true, true, 'ALARM SENSOR OUT OF RANGE', 'ALARM SENSOR OUT OF RANGE', 'LBL_ALARM_CONFIGS_VANE_SENSOR_OUT_OF_RANGE', 'LBL_ALARM_CONFIGS_VANE_SENSOR_OUT_OF_RANGE_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(6601, 2, 5, 66, NULL, 3, true, true, 'ALARM BROKEN THREAD SENSOR', 'ALARM BROKEN THREAD SENSOR', 'LBL_ALARM_CONFIGS_ALARM_BROKEN_THREAD_SENSOR', 'LBL_ALARM_CONFIGS_ALARM_BROKEN_THREAD_SENSOR_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(660001, 2, 5, 66, NULL, 3, true, true, 'ALARM WIND SPEED', 'WIND SPEED GREATER THAN 60', 'LBL_ALARM_CONFIGS_ALARM_VANE_HIGH_WIND_SPEED', 'LBL_ALARM_CONFIGS_ALARM_VANE_HIGH_WIND_SPEED_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: conf.element_state_alarms
--
INSERT INTO conf.element_state_alarms
(element_type_id, element_type_state_id, alarm_config_id, created_at, updated_at) VALUES
(66, 2, 209, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);