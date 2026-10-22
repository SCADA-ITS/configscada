-- 
-- Table: master.element_types
--
INSERT INTO master.element_types
(element_type_id, alias, description,label_alias, label_description,  initial_element_type_state_id, connected_element_type_state_id, disconnected_element_type_state_id, enabled, visible, created_at, updated_at) VALUES
(23, 'BATTERY', 'BATTERY', 'LBL_ELEMENT_TYPE_BATTERY', 'LBL_ELEMENT_TYPE_BATTERY_DESC', 0, 1, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

--
-- Table: master.element_type_params
--
INSERT INTO master.element_type_params (element_type_id, param_type_id, element_type_param_id, element_type_param_unit_id, data_type_id, default_value, alias, description, label_alias, label_description, enabled, visible, editable, created_at, updated_at) VALUES
(23,1,1, NULL,2, ' ', 'PK', 'PK', 'LBL_ELEMENT_TYPE_PARAM_PK' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(23,1,2,1,2, ' ', 'entrance_tunnel_distance', 'entrance_tunnel_distance', 'LBL_ELEMENT_TYPE_PARAM_ENTRANCE_TUNNEL_DISTANCE' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(23,1,3, NULL,2, ' ', 'road', 'road', 'LBL_ELEMENT_TYPE_PARAM_ROAD' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(23,1,4, NULL,2, ' ', 'alias_section', 'alias_section', 'LBL_ELEMENT_TYPE_PARAM_ALIAS_SECTION' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(23,1,1003, NULL,2, '', 'MANGO SESSION_UID', 'MANGO SESSION_UID', 'LBL_ELEMENT_TYPE_PARAM_MANGO SESSION_UID' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

--
-- Table: master.element_type_states
--
INSERT INTO master.element_type_states(element_type_id, element_type_state_id, alias, description, label_alias, label_description, enabled, visible, created_at, updated_at) VALUES
(23, 0, 'UNKNOWN', 'Unknown state', 'LBL_ELEMENT_TYPE_STATE_UNKNOWN', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(23, 1, 'UP', 'The state of the equipment is: comunicates', 'LBL_ELEMENT_TYPE_STATE_UP', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(23, 2, 'DOWN', 'The state of the equipment is: out of communication', 'LBL_ELEMENT_TYPE_STATE_DOWN', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(23, 3, 'ALARMS', 'The state of the equipment is: with alarms', 'LBL_ELEMENT_TYPE_STATE_ALARMS', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: master.element_type_state_transitions
--
INSERT INTO master.element_type_state_transitions (element_type_id, current_element_type_state_id, next_element_type_state_id, enabled, visible, editable, created_at, updated_at) VALUES
(23, 1, 2, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(23, 1, 3, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(23, 2, 1, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(23, 2, 3, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(23, 3, 1, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(23, 3, 2, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(23, 0, 1, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(23, 0, 2, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: master.io_controller_module_type_element_types
--
INSERT INTO master.io_controller_module_type_element_types
(module_type_id, element_type_id, enabled, visible, created_at, updated_at) VALUES
(1, 23, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: conf.alarm_configs
--
INSERT INTO conf.alarm_configs 
(alarm_config_id, alarm_type_id, alarm_level_id, element_type_id, element_id, element_type_state_id, mine, treatment, alias, description, label_alias, label_description, enabled, visible, created_at, updated_at)VALUES
(58, 2, 3, 23, NULL, NULL, true, true, 'ALARM BATTERY NO CONNECTION', 'ALARM BATTERY NO CONNECTION', 'LBL_ALARM_CONFIGS_ALARM_BATTERY_NO_CONNECTION', 'LBL_ALARM_CONFIGS_ALARM_BATTERY_NO_CONNECTION_DESC', false, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(59, 2, 2, 23, NULL, 3, true, true, 'ALARM BATTERY BATERIA CONDENSADOR', 'ALARM BATTERY BATERIA_CONDENSADOR', 'LBL_ALARM_CONFIGS_ALARM_BATTERY_BATERIA_CONDENSADOR', 'LBL_ALARM_CONFIGS_ALARM_BATTERY_BATERIA_CONDENSADOR_DESC', false, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: conf.element_state_alarms
--
INSERT INTO conf.element_state_alarms
(element_type_id, element_type_state_id, alarm_config_id, created_at, updated_at) VALUES
(23, 2, 58, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);