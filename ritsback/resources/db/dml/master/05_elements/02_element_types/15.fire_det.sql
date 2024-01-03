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
--CONFIG
(15,1,1, NULL,2, ' ', 'brand', 'brand', 'LBL_ELEMENT_TYPE_PARAM_BRAND' , 'LBL_ELEMENT_TYPE_PARAM_BRAND_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(15,1,2, NULL,2, ' ', 'model', 'model', 'LBL_ELEMENT_TYPE_PARAM_MODEL' , 'LBL_ELEMENT_TYPE_PARAM_MODEL_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(15,1,5, NULL,2, ' ', 'pk_ini', 'pk_ini', 'LBL_ELEMENT_TYPE_PARAM_FIRE_DET_PK_INI' , 'LBL_ELEMENT_TYPE_PARAM_FIRE_DET_PK_INI_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(15,1,6, NULL,2, ' ', 'pk_end', 'pk_end', 'LBL_ELEMENT_TYPE_PARAM_FIRE_DET_PK_END' , 'LBL_ELEMENT_TYPE_PARAM_FIRE_DET_PK_END_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(15,1,1003, NULL,2, '', 'MANGO SESSION_UID', 'MANGO SESSION_UID', 'LBL_ELEMENT_TYPE_PARAM_MANGO_SESSION_UID', 'LBL_ELEMENT_TYPE_PARAM_MANGO_SESSION_UID_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
--MEASURE
(15,2,1,8,3, NULL, 'avg_temperature', 'avg temperature', 'LBL_ELEMENT_TYPE_PARAM_AVG_TEMPERATURE', 'LBL_ELEMENT_TYPE_PARAM_AVG_TEMPERATURE_DESC', true, true, false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(15,2,2,8,3, NULL, 'max_temperature', 'max temperature', 'LBL_ELEMENT_TYPE_PARAM_MAX_TEMPERATURE', 'LBL_ELEMENT_TYPE_PARAM_MAX_TEMPERATURE_DESC', true, true, false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(15,2,3,8,3, NULL, 'min_temperature', 'min temperature', 'LBL_ELEMENT_TYPE_PARAM_MIN_TEMPERATURE', 'LBL_ELEMENT_TYPE_PARAM_MIN_TEMPERATURE_DESC', true, true, false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(15,2,4,1,3, NULL, 'fiber_failure_position', 'fiber failure position', 'LBL_ELEMENT_TYPE_PARAM_FIBER_FAILURE_POSITION', 'LBL_ELEMENT_TYPE_PARAM_FIBER_FAILURE_POSITION_DESC', true, true, false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: master.element_type_states
--
INSERT INTO master.element_type_states(element_type_id, element_type_state_id, alias, description, label_alias, label_description, enabled, visible, created_at, updated_at) VALUES
(15, 0, 'UNKNOWN', 'Unknown state', 'LBL_ELEMENT_TYPE_STATE_UNKNOWN', 'LBL_ELEMENT_TYPE_STATE_UNKNOWN_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(15, 1, 'UP', 'The state of the equipment is: comunicates', 'LBL_ELEMENT_TYPE_STATE_UP', 'LBL_ELEMENT_TYPE_STATE_UP_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(15, 2, 'DOWN', 'The state of the equipment is: out of communication', 'LBL_ELEMENT_TYPE_STATE_DOWN', 'LBL_ELEMENT_TYPE_STATE_DOWN_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(15, 3, 'ALARMS', 'The state of the equipment is: with alarms', 'LBL_ELEMENT_TYPE_STATE_ALARMS', 'LBL_ELEMENT_TYPE_STATE_ALARMS_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(15, 4, 'MAINTENANCE', 'The state of the equipment is: on Maintenance', 'LBL_ELEMENT_TYPE_STATE_MAINTENANCE', 'LBL_ELEMENT_TYPE_STATE_MAINTENANCE_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

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
-- Table: master.element_hierarchies_allowed
--
INSERT INTO master.element_hierarchies_allowed
(parent_element_type_id, child_element_type_id, enabled, visible, created_at, updated_at) VALUES
(15, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

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
(010015, 2, 3, 15, NULL, NULL, true, true, 'ALARM FIRE_DET NO CONNECTION', 'ALARM FIRE_DET NO CONNECTION', 'LBL_ALARM_CONFIGS_ALARM_FIRE_DET_NO_CONNECTION', 'LBL_ALARM_CONFIGS_ALARM_FIRE_DET_NO_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(020015, 2, 6, 15, NULL, 3, true, true, 'ALARM ZONE', 'ALARM ZONE', 'LBL_ALARM_CONFIGS_ALARM_FIRE_ZONE', 'LBL_ALARM_CONFIGS_ALARM_FIRE_ZONE_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(030015, 2, 3, 15, NULL, 3, true, true, 'ALARM FIBER FAULT', 'ALARM FIBER FAULT', 'LBL_ALARM_CONFIGS_ALARM_FIBER_FAULT', 'LBL_ALARM_CONFIGS_ALARM_FIBER_FAULT_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: conf.element_state_alarms
--
INSERT INTO conf.element_state_alarms
(element_type_id, element_type_state_id, alarm_config_id, created_at, updated_at) VALUES
(15, 2, 010015, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
