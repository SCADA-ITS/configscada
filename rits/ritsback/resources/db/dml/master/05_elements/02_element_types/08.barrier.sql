-- 
-- Table: master.element_types
--
INSERT INTO master.element_types(element_type_id, alias, description, label_alias, label_description, initial_element_type_state_id, connected_element_type_state_id, disconnected_element_type_state_id, enabled, visible, created_at, updated_at) VALUES
(8, 'BARRIER', 'Barrier', 'LBL_ELEMENT_TYPE_BARRIER', 'LBL_ELEMENT_TYPE_BARRIER_DESC', 0, 1, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: master.element_type_states
--
INSERT INTO master.element_type_params (element_type_id, param_type_id, element_type_param_id, element_type_param_unit_id, data_type_id, default_value, alias, description, label_alias, label_description, enabled, visible, editable, created_at, updated_at) VALUES
(8,1,1, NULL,2, ' ', 'PK', 'PK', 'LBL_ELEMENT_TYPE_PARAM_PK' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(8,1,2,1,2, ' ', 'entrance_tunnel_distance', 'entrance_tunnel_distance', 'LBL_ELEMENT_TYPE_PARAM_ENTRANCE_TUNNEL_DISTANCE' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(8,1,3, NULL,2, ' ', 'road', 'road', 'LBL_ELEMENT_TYPE_PARAM_ROAD' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(8,1,4, NULL,2, ' ', 'alias_section', 'alias_section', 'LBL_ELEMENT_TYPE_PARAM_ALIAS_SECTION' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(8,1,1003, NULL,2, ' ', 'MANGO SESSION_UID', 'MANGO SESSION_UID', 'LBL_ELEMENT_TYPE_PARAM_MANGO SESSION_UID' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(8,2,1, NULL,1, '1', 'bar_state', 'state for barrier', 'LBL_ELEMENT_TYPE_PARAM_BAR_STATE' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


INSERT INTO master.element_type_states(element_type_id, element_type_state_id, alias, description, label_alias, label_description, enabled, visible, created_at, updated_at) VALUES
(8, 0, 'UNKNOWN', 'Unknown state', 'LBL_ELEMENT_TYPE_STATE_UNKNOWN', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(8, 1, 'UP', 'The state of the equipment is: comunicates', 'LBL_ELEMENT_TYPE_STATE_UP', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(8, 2, 'DOWN', 'The state of the equipment is: out of communication', 'LBL_ELEMENT_TYPE_STATE_DOWN', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(8, 3, 'ALARMS', 'The state of the equipment is: with alarms', 'LBL_ELEMENT_TYPE_STATE_ALARMS', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: master.element_type_state_transitions
--
INSERT INTO master.element_type_state_transitions
(element_type_id, current_element_type_state_id, next_element_type_state_id, enabled, visible, editable, created_at, updated_at) VALUES
(8, 0, 1, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(8, 0, 2, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(8, 1, 2, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(8, 1, 3, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(8, 2, 1, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(8, 2, 3, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(8, 3, 1, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(8, 3, 2, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: master.command_element_types
--
INSERT INTO master.command_element_types
(command_element_type_id, element_type_id, alias, description, label_alias, label_description, enabled, visible, created_at, updated_at) VALUES
(1, 8, 'BARRIER PULL DOWN', 'BARRIER PULL DOWN', 'LBL_COMMAND_ELEMENT_TYPE_BARRIER_PULL_DOWN', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 8, 'BARRIER PULL UP', 'BARRIER PULL UP', 'LBL_COMMAND_ELEMENT_TYPE_BARRIER_PULL_UP', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: master.io_controller_module_type_element_types
--
INSERT INTO master.io_controller_module_type_element_types
(module_type_id, element_type_id, enabled, visible, created_at, updated_at) VALUES
(1, 8, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: conf.alarm_configs
--
INSERT INTO conf.alarm_configs
(alarm_config_id, alarm_type_id, alarm_level_id, element_type_id, element_id, element_type_state_id, mine, treatment, alias, description, label_alias, label_description, enabled, visible, created_at, updated_at) VALUES
(39, 2, 3, 8, NULL, NULL, true, true, 'ALARM BARRIER DISCONNECTED', 'ALARM BARRIER DISCONNECTED', 'LBL_ALARM_CONFIGS_ALARM_BARRIER_DISCONNECTED', 'LBL_ALARM_CONFIGS_ALARM_BARRIER_DISCONNECTED_DESC', false, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(283, 2, 5, 8, NULL, 3, true, true, 'ALARM BARRIER RISE FAILURE', 'ALARM BARRIER RISE FAILURE', 'LBL_ALARM_CONFIGS_ALARM_BARRIER_RISE_FAILURE', 'LBL_ALARM_CONFIGS_ALARM_BARRIER_RISE_FAILURE_DESC', false, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(284, 2, 5, 8, NULL, 3, true, true, 'ALARM BARRIER LOWERING FAILURE', 'ALARM BARRIER LOWERING FAILURE', 'LBL_ALARM_CONFIGS_ALARM_BARRIER_LOWERING_FAILURE', 'LBL_ALARM_CONFIGS_ALARM_BARRIER_LOWERING_FAILURE_DESC', false, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(285, 2, 5, 8, NULL, 3, true, true, 'ALARM BARRIER ENGINE', 'ALARM BARRIER ENGINE', 'LBL_ALARM_CONFIGS_ALARM_BARRIER_ENGINE', 'LBL_ALARM_CONFIGS_ALARM_BARRIER_ENGINE_DESC', false, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(347, 2, 5, 8, NULL, 3, true, true, 'ALARM BARRIER FAIL', 'ALARM BARRIER FAIL', 'LBL_ALARM_CONFIGS_ALARM_BARRIER_FAIL', 'LBL_ALARM_CONFIGS_ALARM_BARRIER_FAIL_DESC', false, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(0806, 2, 5, 8, NULL, 3, true, true, 'ALARM BARRIER DOWN', 'ALARM BARRIER DOWN', 'LBL_ALARM_CONFIGS_ALARM_BARRIER_DOWN', 'LBL_ALARM_CONFIGS_ALARM_BARRIER_DOWN', false, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


-- 
-- Table: conf.element_state_alarms
--
INSERT INTO conf.element_state_alarms
(element_type_id, element_type_state_id, alarm_config_id, created_at, updated_at) VALUES
(8, 2, 39, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
