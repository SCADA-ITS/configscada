-- 
-- Table: master.element_types
--
INSERT INTO master.element_types
(element_type_id, alias, description, label_alias, label_description, initial_element_type_state_id, connected_element_type_state_id, disconnected_element_type_state_id, enabled, visible, created_at, updated_at) VALUES
(15, 'FIRE_DET', 'Fire detector', 'LBL_ELEMENT_TYPE_FIRE_DET', 'LBL_ELEMENT_TYPE_FIRE_DET_DESC', 0, 1, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

--
-- Table: master.element_type_params
--
INSERT INTO master.element_type_params (element_type_id, param_type_id, element_type_param_id, element_type_param_unit_id, data_type_id, default_value, alias, description, label_alias, label_description, enabled, visible, editable, created_at, updated_at) VALUES
(15,1,1, NULL,2, ' ', 'PK', 'PK', 'LBL_ELEMENT_TYPE_PARAM_PK' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(15,1,2,1,2, ' ', 'entrance_tunnel_distance', 'entrance_tunnel_distance', 'LBL_ELEMENT_TYPE_PARAM_ENTRANCE_TUNNEL_DISTANCE' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(15,1,3, NULL,2, ' ', 'road', 'road', 'LBL_ELEMENT_TYPE_PARAM_ROAD' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(15,1,4, NULL,2, ' ', 'alias_section', 'alias_section', 'LBL_ELEMENT_TYPE_PARAM_ALIAS_SECTION' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(15,1,5, NULL,2, ' ', 'pk_ini', 'pk_ini', 'LBL_ELEMENT_TYPE_PARAM_PK_INI' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(15,1,6, NULL,2, ' ', 'pk_end', 'pk_end', 'LBL_ELEMENT_TYPE_PARAM_PK_END' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(15,1,1003, NULL,2, '', 'MANGO SESSION_UID', 'MANGO SESSION_UID', 'LBL_ELEMENT_TYPE_PARAM_MANGO SESSION_UID', null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(15,2,1,8,3, NULL, 'avg_temperature', 'avg temperature', 'LBL_ELEMENT_TYPE_PARAM_AVG_TEMPERATURE', null, true, true, false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(15,2,2,8,3, NULL, 'max_temperature', 'max temperature', 'LBL_ELEMENT_TYPE_PARAM_MAX_TEMPERATURE', null, true, true, false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(15,2,3,8,3, NULL, 'min_temperature', 'min temperature', 'LBL_ELEMENT_TYPE_PARAM_MIN_TEMPERATURE', null, true, true, false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(15,2,4,1,3, NULL, 'fiber_failure_position', 'fiber failure position', 'LBL_ELEMENT_TYPE_PARAM_FIBER_FAILURE_POSITION', null, true, true, false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: master.element_type_states
--
INSERT INTO master.element_type_states(element_type_id, element_type_state_id, alias, description, label_alias, label_description, enabled, visible, created_at, updated_at) VALUES
(15, 0, 'UNKNOWN', 'Unknown state', 'LBL_ELEMENT_TYPE_STATE_UNKNOWN', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(15, 1, 'UP', 'The state of the equipment is: comunicates', 'LBL_ELEMENT_TYPE_STATE_UP', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(15, 2, 'DOWN', 'The state of the equipment is: out of communication', 'LBL_ELEMENT_TYPE_STATE_DOWN', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(15, 3, 'ALARMS', 'The state of the equipment is: with alarms', 'LBL_ELEMENT_TYPE_STATE_ALARMS', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: master.element_type_state_transitions
--
INSERT INTO master.element_type_state_transitions
(element_type_id, current_element_type_state_id, next_element_type_state_id, enabled, visible, editable, created_at, updated_at) VALUES
(15, 0, 1, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(15, 0, 2, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(15, 1, 2, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(15, 1, 3, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(15, 2, 1, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(15, 2, 3, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(15, 3, 1, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(15, 3, 2, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: master.io_controller_module_type_element_types
--
INSERT INTO master.io_controller_module_type_element_types
(module_type_id, element_type_id, enabled, visible, created_at, updated_at) VALUES
(1, 15, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: conf.alarm_configs
--
INSERT INTO conf.alarm_configs 
--Alarmas que hagan referencia a measures, las añado como <id_alarma_media_2cifras><element_type_4cifras>
(alarm_config_id, alarm_type_id, alarm_level_id, element_type_id, element_id, element_type_state_id, mine, treatment, alias, description, label_alias, label_description, enabled, visible, created_at, updated_at)VALUES
(49, 2, 3, 15, NULL, NULL, true, true, 'ALARM FIRE_DET NO CONNECTION', 'ALARM FIRE_DET NO CONNECTION', 'LBL_ALARM_CONFIGS_ALARM_FIRE_DET_NO CONNECTION', 'LBL_ALARM_CONFIGS_ALARM_FIRE_DET_NO_DESC', false, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(256, 2, 3, 15, NULL, 3, true, true, 'ALARM ZONE', 'ALARM ZONE', 'LBL_ALARM_CONFIGS_ALARM_ZONE', 'LBL_ALARM_CONFIGS_ALARM_FIRE_ZONE_DESC', false, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(257, 2, 3, 15, NULL, 3, true, true, 'ALARM FIBER FAULT', 'ALARM FIBER FAULT', 'LBL_ALARM_CONFIGS_ALARM_FIBER_FAULT', 'LBL_ALARM_CONFIGS_ALARM_FIBER_FAULT_DESC', false, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(010015, 2, 3, 15, NULL, 3, true, true, 'ALARM MIN TEMP', 'ALARM MIN TEMP', 'LBL_ALARM_CONFIGS_ALARM_MIN_TEMP', 'LBL_ALARM_CONFIGS_ALARM_MIN_TEMP', false, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: conf.element_state_alarms
--
INSERT INTO conf.element_state_alarms
(element_type_id, element_type_state_id, alarm_config_id, created_at, updated_at) VALUES
(15, 2, 49, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
