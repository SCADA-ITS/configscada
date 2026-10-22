-- 
-- Table: master.element_types
--
INSERT INTO master.element_types(element_type_id, alias, description,label_alias, label_description, initial_element_type_state_id, connected_element_type_state_id, disconnected_element_type_state_id, enabled, visible, created_at, updated_at)
VALUES(86, 'PUMP', 'PUMP','LBL_ELEMENT_TYPE_PUMP', 'LBL_ELEMENT_TYPE_PUMP_DESC', 0, 1, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: master.element_type_params.PUMP
--

INSERT INTO master.element_type_params (element_type_id, param_type_id, element_type_param_id, element_type_param_unit_id, data_type_id, default_value, alias, description, label_alias, label_description, enabled, visible, editable, created_at, updated_at) VALUES
(86, 1, 1, NULL, 2, ' ', 'PK', 'PK', 'LBL_ELEMENT_TYPE_PARAM_PK' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(86, 1, 2, 1, 2, ' ', 'entrance_tunnel_distance', 'entrance_tunnel_distance', 'LBL_ELEMENT_TYPE_ENTRANCE_TUNNEL_DISTANCE' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(86, 1, 3, NULL, 2, ' ', 'road', 'road', 'LBL_ELEMENT_TYPE_PARAM_ROAD' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(86, 1, 4, NULL, 2, ' ', 'alias_section', 'alias_section', 'LBL_ELEMENT_TYPE_PARAM_ALIAS_SECTION' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(86, 1, 1003, NULL, 2, ' ', 'MANGO SESSION_UID', 'MANGO SESSION_UID', 'LBL_ELEMENT_TYPE_PARAM_MANGO SESSION_UID' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(86, 2,1, null,5, null, 'pump_on', 'pump_on', 'LBL_ELEMENT_TYPE_PARAM_PUMP_ON' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
--
-- Table: master.element_type_params
--
INSERT INTO master.element_type_states(element_type_id, element_type_state_id, alias, description, label_alias, label_description, enabled, visible, created_at, updated_at) VALUES
(86, 0, 'UNKNOWN', 'Unknown state', 'LBL_ELEMENT_TYPE_STATE_UNKNOWN', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(86, 1, 'UP', 'The state of the equipment is: comunicates', 'LBL_ELEMENT_TYPE_STATE_UP', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(86, 2, 'DOWN', 'The state of the equipment is: out of communication', 'LBL_ELEMENT_TYPE_STATE_DOWN', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(86, 3, 'ALARMS', 'The state of the equipment is: with alarms', 'LBL_ELEMENT_TYPE_STATE_ALARMS', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: master.element_type_state_transitions
--
INSERT INTO master.element_type_state_transitions
(element_type_id, current_element_type_state_id, next_element_type_state_id, enabled, visible, editable, created_at, updated_at) VALUES
(86, 0, 1, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(86, 0, 2, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(86, 1, 2, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(86, 1, 3, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(86, 2, 1, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(86, 2, 3, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(86, 3, 1, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(86, 3, 2, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


-- 
-- Table: master.io_controller_module_type_element_types
--
INSERT INTO master.io_controller_module_type_element_types
(module_type_id, element_type_id, enabled, visible, created_at, updated_at) VALUES
(1, 86, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: conf.alarm_configs
--
INSERT INTO conf.alarm_configs
(alarm_config_id, alarm_type_id, alarm_level_id, element_type_id, element_id, element_type_state_id, mine, treatment, alias, description, label_alias, label_description, enabled, visible, created_at, updated_at) VALUES
(8601, 2, 3, 86, NULL, NULL, true, true, 'ALARM PUMP DISCONNECTED', 'ALARM PUMP DISCONNECTED', 'LBL_ALARM_CONFIGS_ALARM_PUMP_DISCONNECTED', 'LBL_ALARM_CONFIGS_ALARM_PUMP_DISCONNECTED_DESC', false, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(8602, 2, 3, 86, NULL, 3, true, true, 'ALARM PUMP NO OPERATION', 'ALARM PUMP NO OPERATION', 'LBL_ALARM_CONFIGS_ALARM_PUMP_NO_OPERATION', 'LBL_ALARM_CONFIGS_ALARM_PUMP_NO_OPERATION_DESC', false, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: conf.element_state_alarms
--
INSERT INTO conf.element_state_alarms
(element_type_id, element_type_state_id, alarm_config_id, created_at, updated_at) VALUES
(86, 2, 8601, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
