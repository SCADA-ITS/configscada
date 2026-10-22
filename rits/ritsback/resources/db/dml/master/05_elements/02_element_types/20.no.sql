-- 
-- Table: master.element_types
--
INSERT INTO master.element_types
(element_type_id, alias, description,label_alias, label_description, initial_element_type_state_id, connected_element_type_state_id, disconnected_element_type_state_id, enabled, visible, created_at, updated_at) VALUES
(20, 'NO', 'NO', 'LBL_ELEMENT_TYPE_NO', 'LBL_ELEMENT_TYPE_NO_DESC', 0, 1, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

--
-- Table: master.element_type_params
--
INSERT INTO master.element_type_params (element_type_id, param_type_id, element_type_param_id, element_type_param_unit_id, data_type_id, threshold_1, threshold_2, default_value, alias, description, label_alias, label_description, enabled, visible, editable, created_at, updated_at) VALUES
(20,1,1, NULL,2,NULL, NULL, ' ','PK', 'PK', 'LBL_ELEMENT_TYPE_PARAM_PK' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(20,1,2,1,2, NULL, NULL, ' ', 'entrance_tunnel_distance', 'entrance_tunnel_distance', 'LBL_ELEMENT_TYPE_PARAM_ENTRANCE_TUNNEL_DISTANCE' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(20,1,3, NULL,2,NULL, NULL, ' ', 'road', 'road', 'LBL_ELEMENT_TYPE_PARAM_ROAD' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(20,1,4, NULL,2,NULL, NULL, ' ','alias_section', 'alias_section', 'LBL_ELEMENT_TYPE_PARAM_ALIAS_SECTION' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(20,1,1003, NULL,2, NULL, NULL, ' ','MANGO SESSION_UID', 'MANGO SESSION_UID', 'LBL_ELEMENT_TYPE_PARAM_MANGO_SESSION_UID' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(20,2,1,29,3, '0', '65534', '65535', 'no_concentration', 'NO_concentration', 'LBL_ELEMENT_TYPE_PARAM_NO_CONCENTRATION' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

--
-- Table: master.element_type_states
--
INSERT INTO master.element_type_states(element_type_id, element_type_state_id, alias, description, label_alias, label_description, enabled, visible, created_at, updated_at) VALUES
(20, 0, 'UNKNOWN', 'Unknown state', 'LBL_ELEMENT_TYPE_STATE_UNKNOWN', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(20, 1, 'UP', 'The state of the equipment is: comunicates', 'LBL_ELEMENT_TYPE_STATE_UP', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(20, 2, 'DOWN', 'The state of the equipment is: out of communication', 'LBL_ELEMENT_TYPE_STATE_DOWN', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(20, 3, 'ALARMS', 'The state of the equipment is: with alarms', 'LBL_ELEMENT_TYPE_STATE_ALARMS', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: master.element_type_state_transitions
--
INSERT INTO master.element_type_state_transitions 
(element_type_id, current_element_type_state_id, next_element_type_state_id, enabled, visible, editable, created_at, updated_at) VALUES
(20, 1, 2, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(20, 1, 3, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(20, 2, 1, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(20, 2, 3, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(20, 3, 1, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(20, 3, 2, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(20, 0, 1, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(20, 0, 2, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: master.element_hierarchies_allowed
--
INSERT INTO master.element_hierarchies_allowed
(parent_element_type_id, child_element_type_id, enabled, visible, created_at, updated_at) VALUES
(20, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: master.io_controller_module_type_element_types
--
INSERT INTO master.io_controller_module_type_element_types
(module_type_id, element_type_id, enabled, visible, created_at, updated_at) VALUES
(1, 20, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: conf.alarm_configs
--
INSERT INTO conf.alarm_configs 
(alarm_config_id, alarm_type_id, alarm_level_id, element_type_id, element_id, element_type_state_id, mine, treatment, alias, description, label_alias, label_description, enabled, visible, created_at, updated_at)VALUES
(55, 2, 3, 20, NULL, NULL, true, true, 'ALARM NO NO CONNECTION', 'ALARM NO NO CONNECTION', 'LBL_ALARM_CONFIGS_ALARM_NO_NO_CONNECTION', 'LBL_ALARM_CONFIGS_ALARM_NO_NO_CONNECTION_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(247, 2, 5, 20, NULL, 3, true, true, 'ALARM SENSOR', 'ALARM SENSOR', 'LBL_ALARM_CONFIGS_ALARM_SENSOR', 'LBL_ALARM_CONFIGS_ALARM_SENSOR_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(260, 2, 3, 20, NULL, 3, true, true, 'ALARM FA', 'ALARM FA', 'LBL_ALARM_CONFIGS_ALARM_FA', 'LBL_ALARM_CONFIGS_ALARM_FA_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(309, 2, 5, 20, NULL, 3, true, true, 'ALARM NO OUT OF RANGE', 'ALARM NO OUT OF RANGE', 'LBL_ALARM_CONFIGS_ALARM_NO_OUT_OF_RANGE', 'LBL_ALARM_CONFIGS_ALARM_NO_OUT_OF_RANGE_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2001, 2, 5, 20, NULL, 3, true, true, 'ALARM NO BROKEN THREAD', 'ALARM NO BROKEN THREAD', 'LBL_ALARM_CONFIGS_ALARM_NO_BROKEN_THREAD', 'LBL_ALARM_CONFIGS_ALARM_NO_BROKEN_THREAD_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2002, 2, 5, 20, NULL, 3, true, true, 'ALARM EQUIPMENT FAIL', 'ALARM EQUIPMENT FAIL', 'LBL_ALARM_CONFIGS_ALARM_EQUIPMENT_FAIL', 'LBL_ALARM_CONFIGS_ALARM_EQUIPMENT_FAIL_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2003, 2, 3, 20, NULL, 3, true, true, 'ALARM TEMPERATURE OUT OF RANGE', 'ALARM TEMPERATURE OUT OF RANGE', 'LBL_ALARM_CONFIGS_ALARM_TEMPERATURE_OUT_OF_RANGE', 'LBL_ALARM_CONFIGS_ALARM_TEMPERATURE_OUT_OF_RANGE_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2004, 2, 3, 20, NULL, 3, true, true, 'ALARM TEMPERATURE BROKEN THREAD', 'ALARM TEMPERATURE BROKEN THREAD', 'LBL_ALARM_CONFIGS_ALARM_TEMPERATURE_BROKEN_THREAD', 'LBL_ALARM_CONFIGS_ALARM_TEMPERATURE_BROKEN_THREAD_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);



-- 
-- Table: conf.element_state_alarms
--
INSERT INTO conf.element_state_alarms
(element_type_id, element_type_state_id, alarm_config_id, created_at, updated_at) VALUES
(20, 2, 55, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
