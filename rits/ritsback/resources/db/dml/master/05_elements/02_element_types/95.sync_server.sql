-- 
-- Table: master.element_types
--
INSERT INTO master.element_types(element_type_id, element_group_id, alias, description,label_alias, label_description, initial_element_type_state_id, connected_element_type_state_id, disconnected_element_type_state_id, enabled, visible, created_at, updated_at) VALUES
(95, 2, 'SYNC_SERVER', 'SYNC_SERVER', 'LBL_ELEMENT_TYPE_SYNC_SERVER', 'LBL_ELEMENT_TYPE_SYNC_SERVER_DESC', 0, 1, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

--
-- Table: master.element_type_params
--
INSERT INTO master.element_type_params (element_type_id, param_type_id, element_type_param_id, element_type_param_unit_id, data_type_id, default_value, alias, description, label_alias, label_description, enabled, visible, editable, created_at, updated_at) VALUES
(95,1,1,1,2, ' ', 'entrance_tunnel_distance', 'entrance_tunnel_distance', 'LBL_ELEMENT_TYPE_PARAM_ENTRANCE_TUNNEL_DISTANCE' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(95,1,1003, NULL,2, ' ', 'MANGO SESSION_UID', 'MANGO SESSION_UID', 'LBL_ELEMENT_TYPE_PARAM_MANGO_SESSION_UID' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(95,2,1,null,1, ' ', 'modulo_alimentacion1_state', 'modulo_alimentacion1_state', 'LBL_ELEMENT_TYPE_PARAM_MODULO_ALIMENTACION1_STATE' , 'LBL_ELEMENT_TYPE_PARAM_MODULO_ALIMENTACION1_STATE_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(95,2,2,null,1, ' ', 'modulo_alimentacion2_state', 'modulo_alimentacion2_state', 'LBL_ELEMENT_TYPE_PARAM_MODULO_ALIMENTACION2_STATE' , 'LBL_ELEMENT_TYPE_PARAM_MODULO_ALIMENTACION2_STATE_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(95,2,3,null,1, ' ', 'modulo_basetiempos1_state', 'modulo_basetiempos1_state', 'LBL_ELEMENT_TYPE_PARAM_MODULO_BASETIEMPOS1_STATE' , 'LBL_ELEMENT_TYPE_PARAM_MODULO_BASETIEMPOS1_STATE_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(95,2,4,null,1, ' ', 'modulo_basetiempos1_nosync', 'modulo_basetiempos1_nosync', 'LBL_ELEMENT_TYPE_PARAM_MODULO_BASETIEMPOS1_NOSYNC' , 'LBL_ELEMENT_TYPE_PARAM_MODULO_BASETIEMPOS1_NOSYNC_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(95,2,5,null,1, ' ', 'modulo_basetiempos2_state', 'modulo_basetiempos2_state', 'LBL_ELEMENT_TYPE_PARAM_MODULO_BASETIEMPOS2_STATE' , 'LBL_ELEMENT_TYPE_PARAM_MODULO_BASETIEMPOS2_STATE_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(95,2,6,null,1, ' ', 'modulo_basetiempos2_nosync', 'modulo_basetiempos2_nosync', 'LBL_ELEMENT_TYPE_PARAM_MODULO_BASETIEMPOS2_NOSYNC' , 'LBL_ELEMENT_TYPE_PARAM_MODULO_BASETIEMPOS2_NOSYNC_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: master.element_type_states
--
INSERT INTO master.element_type_states(element_type_id, element_type_state_id, alias, description, label_alias, label_description, enabled, visible, created_at, updated_at) VALUES
(95, 0, 'UNKNOWN', 'Unknown state', 'LBL_ELEMENT_TYPE_STATE_UNKNOWN', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(95, 1, 'UP', 'The state of the equipment is: comunicates', 'LBL_ELEMENT_TYPE_STATE_UP', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(95, 2, 'DOWN', 'The state of the equipment is: out of communication', 'LBL_ELEMENT_TYPE_STATE_DOWN', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(95, 3, 'ALARMS', 'The state of the equipment is: with alarms', 'LBL_ELEMENT_TYPE_STATE_ALARMS', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


-- 
-- Table: master.element_type_state_transitions
--
INSERT INTO master.element_type_state_transitions 
(element_type_id, current_element_type_state_id, next_element_type_state_id, enabled, visible, editable, created_at, updated_at) VALUES
(95, 0, 1, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(95, 0, 2, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(95, 1, 2, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(95, 1, 3, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(95, 2, 1, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(95, 2, 3, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(95, 3, 1, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(95, 3, 2, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: master.io_controller_module_type_element_types
--
INSERT INTO master.io_controller_module_type_element_types
(module_type_id, element_type_id, enabled, visible, created_at, updated_at) VALUES
(1, 95, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: conf.alarm_configs
--
INSERT INTO conf.alarm_configs
(alarm_config_id, alarm_type_id, alarm_level_id, element_type_id, element_id, element_type_state_id, mine, treatment, alias, description, label_alias, label_description, enabled, visible, created_at, updated_at) VALUES
(010095, 2, 3, 95, NULL, NULL, true, true, 'ALARM SYNC_SERVER NO CONNECTION', 'ALARM SYNC_SERVER NO CONNECTION', 'LBL_ALARM_CONFIGS_ALARM_SYNC_SERVER_NO_CONNECTION', 'LBL_ALARM_CONFIGS_ALARM_SYNC_SERVER_NO_CONNECTION_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: conf.element_state_alarms
--
INSERT INTO conf.element_state_alarms
(element_type_id, element_type_state_id, alarm_config_id, created_at, updated_at) VALUES
(95, 2, 010095, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
