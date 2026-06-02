-- 
-- Table: master.element_types
--
INSERT INTO master.element_types(element_type_id, alias, description, label_alias, label_description, initial_element_type_state_id, connected_element_type_state_id, disconnected_element_type_state_id, enabled, visible, created_at, updated_at) VALUES
(8, 'BARRIER', 'Barrier', 'LBL_ELEMENT_TYPE_BARRIER', 'LBL_ELEMENT_TYPE_BARRIER_DESC', 0, 1, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);



--
-- Table: master.element_type_params
--
INSERT INTO master.element_type_params (element_type_id, param_type_id, element_type_param_id, element_type_param_unit_id, data_type_id, default_value, alias, description, label_alias, label_description, enabled, visible, editable, created_at, updated_at) VALUES
--CONFIG
(8,1,1, NULL,2, NULL, 'brand', 'brand', 'LBL_ELEMENT_TYPE_PARAM_BRAND' , 'LBL_ELEMENT_TYPE_PARAM_BRAND_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(8,1,2, NULL,2, NULL, 'model', 'model', 'LBL_ELEMENT_TYPE_PARAM_MODEL' , 'LBL_ELEMENT_TYPE_PARAM_MODEL_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(8,1,1003, NULL,2, NULL, 'MANGO SESSION_UID', 'MANGO SESSION_UID', 'LBL_ELEMENT_TYPE_PARAM_MANGO_SESSION_UID' , 'LBL_ELEMENT_TYPE_PARAM_MANGO_SESSION_UID_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--MEASURE
(8,2,1, NULL,1, '1', 'bar_state', 'state for barrier', 'LBL_ELEMENT_TYPE_PARAM_BAR_STATE' , 'LBL_ELEMENT_TYPE_PARAM_BAR_STATE_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(8,2,2, NULL,5, NULL, 'power_supply', 'Power supply for barrier', 'LBL_ELEMENT_TYPE_PARAM_POWER_SUPPLY' , 'LBL_ELEMENT_TYPE_PARAM_POWER_SUPPLY_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


-- 
-- Table: master.element_subtypes
--
INSERT INTO master.element_subtypes(element_subtype_id, element_type_id, alias, description,label_alias, label_description, enabled, visible, created_at, updated_at)
VALUES
(1, 8, 'Barrier AVO', 'Barrier AVO', 'LBL_ELEMENT_SUBTYPE_BARRIER_AVO', 'LBL_ELEMENT_SUBTYPE_BARRIER_AVO_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 8, 'Barrier AUS', 'Barrier AUS', 'LBL_ELEMENT_SUBTYPE_BARRIER_AUS', 'LBL_ELEMENT_SUBTYPE_BARRIER_AUS_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


INSERT INTO master.element_subtype_active_params
(element_subtype_id, element_type_id, param_type_id, element_type_param_id, enabled, visible, created_at, updated_at)VALUES
(1, 8, 2, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 8, 2, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(2, 8, 2, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 8, 2, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: master.element_type_states
--
INSERT INTO master.element_type_states(element_type_id, element_type_state_id, alias, description, label_alias, label_description, enabled, visible, created_at, updated_at) VALUES
(8, 0, 'UNKNOWN', 'Unknown state', 'LBL_ELEMENT_TYPE_STATE_UNKNOWN', 'LBL_ELEMENT_TYPE_STATE_UNKNOWN_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(8, 1, 'UP', 'The state of the equipment is: comunicates', 'LBL_ELEMENT_TYPE_STATE_UP', 'LBL_ELEMENT_TYPE_STATE_UP_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(8, 2, 'DOWN', 'The state of the equipment is: out of communication', 'LBL_ELEMENT_TYPE_STATE_DOWN', 'LBL_ELEMENT_TYPE_STATE_DOWN_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(8, 3, 'ALARMS', 'The state of the equipment is: with alarms', 'LBL_ELEMENT_TYPE_STATE_ALARMS', 'LBL_ELEMENT_TYPE_STATE_ALARMS_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(8, 4, 'MAINTENANCE', 'The state of the equipment is: on Maintenance', 'LBL_ELEMENT_TYPE_STATE_MAINTENANCE', 'LBL_ELEMENT_TYPE_STATE_MAINTENANCE_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

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
(2, 8, 'BARRIER PULL UP', 'BARRIER PULL UP', 'LBL_COMMAND_ELEMENT_TYPE_BARRIER_PULL_UP', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 8, 'BARRIER FORCE PULL DOWN', 'BARRIER FORCE PULL DOWN', 'LBL_COMMAND_ELEMENT_TYPE_BARRIER_FORCE_PULL_DOWN', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 8, 'BARRIER RESET', 'BARRIER RESET', 'LBL_COMMAND_ELEMENT_TYPE_BARRIER_RESET', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
--comandos exclusivos de jaen
(5, 8, 'BARRIER ON', 'BARRIER ON', 'LBL_COMMAND_ELEMENT_TYPE_BARRIER_ON', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(6, 8, 'BARRIER OFF', 'BARRIER OFF', 'LBL_COMMAND_ELEMENT_TYPE_BARRIER_OFF', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);



-- 
-- Table: master.element_subtype_active_commands
--
INSERT INTO master.element_subtype_active_commands (element_subtype_id, command_element_type_id, element_type_id, enabled, visible, created_at, updated_at) VALUES
(1, 1, 8, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 2, 8, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(2, 1, 8, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 2, 8, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 3, 8, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 4, 8, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

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
(010008, 4, 3, 8, NULL, NULL, true, true, 'ALARM BARRIER DISCONNECTED', 'ALARM BARRIER DISCONNECTED', 'LBL_ALARM_CONFIGS_ALARM_BARRIER_DISCONNECTED', 'LBL_ALARM_CONFIGS_ALARM_BARRIER_DISCONNECTED_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(020008, 2, 5, 8, NULL, 3, true, true, 'ALARM BARRIER RISE FAILURE', 'ALARM BARRIER RISE FAILURE', 'LBL_ALARM_CONFIGS_ALARM_BARRIER_RISE_FAILURE', 'LBL_ALARM_CONFIGS_ALARM_BARRIER_RISE_FAILURE_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(030008, 2, 5, 8, NULL, 3, true, true, 'ALARM BARRIER LOWERING FAILURE', 'ALARM BARRIER LOWERING FAILURE', 'LBL_ALARM_CONFIGS_ALARM_BARRIER_LOWERING_FAILURE', 'LBL_ALARM_CONFIGS_ALARM_BARRIER_LOWERING_FAILURE_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(040008, 2, 5, 8, NULL, 3, true, true, 'ALARM BARRIER ENGINE', 'ALARM BARRIER ENGINE', 'LBL_ALARM_CONFIGS_ALARM_BARRIER_ENGINE', 'LBL_ALARM_CONFIGS_ALARM_BARRIER_ENGINE_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(050008, 2, 5, 8, NULL, 3, true, true, 'ALARM BARRIER FAIL', 'ALARM BARRIER FAIL', 'LBL_ALARM_CONFIGS_ALARM_BARRIER_FAIL', 'LBL_ALARM_CONFIGS_ALARM_BARRIER_FAIL_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(060008, 2, 5, 8, NULL, 3, true, true, 'ALARM BARRIER DOWN', 'ALARM BARRIER DOWN', 'LBL_ALARM_CONFIGS_ALARM_BARRIER_DOWN', 'LBL_ALARM_CONFIGS_ALARM_BARRIER_DOWN_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(070008, 2, 5, 8, NULL, 3, true, true, 'ALARM BARRIER DOWN VEH DET', 'ALARM BARRIER DOWN VEH DET', 'LBL_ALARM_CONFIGS_ALARM_BARRIER_DOWN_VEHICLE_DET', 'LBL_ALARM_CONFIGS_ALARM_BARRIER_DOWN_VEHICLE_DET_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(080008, 2, 5, 8, NULL, 3, true, true, 'ALARM BARRIER NO RETURN', 'ALARM BARRIER NO RETURN', 'LBL_ALARM_CONFIGS_ALARM_BARRIER_NO_RETURN', 'LBL_ALARM_CONFIGS_ALARM_BARRIER_NO_RETURN_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(090008, 2, 5, 8, NULL, 3, true, true, 'ALARM BARRIER DOWN NO RED SEM', 'ALARM BARRIER DOWN NO RED SEM', 'LBL_ALARM_CONFIGS_ALARM_BARRIER_DOWN_NO_RED_SEM', 'LBL_ALARM_CONFIGS_ALARM_BARRIER_DOWN_NO_RED_SEM_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(100008, 2, 5, 8, NULL, 3, true, true, 'ALARM BARRIER COMMAND FAIL', 'ALARM BARRIER COMMAND FAIL', 'LBL_ALARM_CONFIGS_ALARM_BARRIER_COMMAND_FAIL', 'LBL_ALARM_CONFIGS_ALARM_BARRIER_COMMAND_FAIL_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: conf.element_state_alarms
--
INSERT INTO conf.element_state_alarms
(element_type_id, element_type_state_id, alarm_config_id, created_at, updated_at) VALUES
(8, 2, 010008, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
