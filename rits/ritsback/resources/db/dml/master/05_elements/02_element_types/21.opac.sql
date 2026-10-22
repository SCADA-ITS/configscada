-- 
-- Table: master.element_types
--
INSERT INTO master.element_types
(element_type_id, alias, description,label_alias, label_description,  initial_element_type_state_id, connected_element_type_state_id, disconnected_element_type_state_id, enabled, visible, created_at, updated_at) VALUES
(21, 'OPAC', 'OPAC', 'LBL_ELEMENT_TYPE_OPAC', 'LBL_ELEMENT_TYPE_OPAC_DESC', 0, 1, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

--
-- Table: master.element_type_params
--
INSERT INTO master.element_type_params (element_type_id, param_type_id, element_type_param_id, element_type_param_unit_id, data_type_id, default_value, alias, description, label_alias, label_description, enabled, visible, editable, created_at, updated_at) VALUES
(21,1,1, NULL,2, ' ', 'PK', 'PK', 'LBL_ELEMENT_TYPE_PARAM_PK' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(21,1,2,1,2, ' ', 'entrance_tunnel_distance', 'entrance_tunnel_distance', 'LBL_ELEMENT_TYPE_PARAM_ENTRANCE_TUNNEL_DISTANCE' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(21,1,3, NULL,2, ' ', 'road', 'road', 'LBL_ELEMENT_TYPE_PARAM_ROAD' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(21,1,4, NULL,2, ' ', 'alias_section', 'alias_section', 'LBL_ELEMENT_TYPE_PARAM_ALIAS_SECTION' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(21,1,1003, NULL,2, '', 'MANGO SESSION_UID', 'MANGO SESSION_UID', 'LBL_ELEMENT_TYPE_PARAM_MANGO SESSION_UID' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(21,2,1,30,3, 65535, 'opac_concentration', 'OPAC_concentration', 'LBL_ELEMENT_TYPE_PARAM_OPAC_CONCENTRATION' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

--
-- Table: master.element_type_states
--
INSERT INTO master.element_type_states(element_type_id, element_type_state_id, alias, description, label_alias, label_description, enabled, visible, created_at, updated_at) VALUES
(21, 0, 'UNKNOWN', 'Unknown state', 'LBL_ELEMENT_TYPE_STATE_UNKNOWN', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(21, 1, 'UP', 'The state of the equipment is: comunicates', 'LBL_ELEMENT_TYPE_STATE_UP', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(21, 2, 'DOWN', 'The state of the equipment is: out of communication', 'LBL_ELEMENT_TYPE_STATE_DOWN', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(21, 3, 'ALARMS', 'The state of the equipment is: with alarms', 'LBL_ELEMENT_TYPE_STATE_ALARMS', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: master.element_type_state_transitions
--
INSERT INTO master.element_type_state_transitions 
(element_type_id, current_element_type_state_id, next_element_type_state_id, enabled, visible, editable, created_at, updated_at) VALUES
(21, 1, 2, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(21, 1, 3, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(21, 2, 1, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(21, 2, 3, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(21, 3, 1, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(21, 3, 2, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(21, 0, 1, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(21, 0, 2, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: master.io_controller_module_type_element_types
--
INSERT INTO master.io_controller_module_type_element_types
(module_type_id, element_type_id, enabled, visible, created_at, updated_at) VALUES
(1, 21, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: conf.alarm_configs
--
INSERT INTO conf.alarm_configs 
(alarm_config_id, alarm_type_id, alarm_level_id, element_type_id, element_id, element_type_state_id, mine, treatment, alias, description, label_alias, label_description, enabled, visible, created_at, updated_at)VALUES
(56, 2, 3, 21, NULL, NULL, true, true, 'ALARM OPAC NO CONNECTION', 'ALARM OPAC NO CONNECTION', 'LBL_ALARM_CONFIGS_ALARM_OPAC_NO_CONNECTION', 'LBL_ALARM_CONFIGS_ALARM_OPAC_NO_CONNECTION_DESC', false, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(248, 2, 5, 21, NULL, 3, true, true, 'ALARM SENSOR', 'ALARM SENSOR', 'LBL_ALARM_CONFIGS_ALARM_SENSOR', 'LBL_ALARM_CONFIGS_ALARM_SENSOR_DESC', false, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(261, 2, 3, 21, NULL, 3, true, true, 'ALARM FA', 'ALARM FA', 'LBL_ALARM_CONFIGS_ALARM_FA', 'LBL_ALARM_CONFIGS_ALARM_FA_DESC', false, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2101, 2, 5, 20, NULL, 3, true, true, 'ALARM OPAC OUT OF RANGE', 'ALARM OPAC OUT OF RANGE', 'LBL_ALARM_CONFIGS_ALARM_OPAC_OUT_OF_RANGE', 'LBL_ALARM_CONFIGS_ALARM_OPAC_OUT_OF_RANGE_DESC', false, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2102, 2, 5, 20, NULL, 3, true, true, 'ALARM OPAC BROKEN THREAD', 'ALARM OPAC BROKEN THREAD', 'LBL_ALARM_CONFIGS_ALARM_OPAC_BROKEN_THREAD', 'LBL_ALARM_CONFIGS_ALARM_OPAC_BROKEN_THREAD_DESC', false, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2103, 2, 5, 20, NULL, 3, true, true, 'ALARM EQUIPMENT FAIL', 'ALARM EQUIPMENT FAIL', 'LBL_ALARM_CONFIGS_ALARM_EQUIPMENT_FAIL', 'LBL_ALARM_CONFIGS_ALARM_EQUIPMENT_FAIL_DESC', false, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: conf.element_state_alarms
--
INSERT INTO conf.element_state_alarms
(element_type_id, element_type_state_id, alarm_config_id, created_at, updated_at) VALUES
(21, 2, 56, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
