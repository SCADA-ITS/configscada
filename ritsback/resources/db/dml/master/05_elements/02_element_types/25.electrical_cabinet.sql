-- 
-- Table: master.element_types
--
INSERT INTO master.element_types(element_type_id, element_group_id, alias, description,label_alias, label_description, initial_element_type_state_id, connected_element_type_state_id, disconnected_element_type_state_id, enabled, visible, created_at, updated_at) VALUES
(25, 2, 'ELECTRICAL_CABINET', 'ELECTRICAL_CABINET', 'LBL_ELEMENT_TYPE_ELECTRICAL_CABINET', 'LBL_ELEMENT_TYPE_ELECTRICAL_CABINET_DESC', 0, 1, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

--
-- Table: master.element_type_params
--
INSERT INTO master.element_type_params (element_type_id, param_type_id, element_type_param_id, element_type_param_unit_id, data_type_id, default_value, alias, description, label_alias, label_description, enabled, visible, editable, created_at, updated_at) VALUES
--CONFIG
(25,1,1, NULL,2, ' ', 'brand', 'brand', 'LBL_ELEMENT_TYPE_PARAM_BRAND' , 'LBL_ELEMENT_TYPE_PARAM_BRAND_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(25,1,2, NULL,2, ' ', 'model', 'model', 'LBL_ELEMENT_TYPE_PARAM_MODEL' , 'LBL_ELEMENT_TYPE_PARAM_MODEL_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(25,1,1003, NULL,2, ' ', 'MANGO SESSION_UID', 'MANGO SESSION_UID', 'LBL_ELEMENT_TYPE_PARAM_MANGO_SESSION_UID' , 'LBL_ELEMENT_TYPE_PARAM_MANGO_SESSION_UID_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: master.element_type_states
--
INSERT INTO master.element_type_states(element_type_id, element_type_state_id, alias, description, label_alias, label_description, enabled, visible, created_at, updated_at) VALUES
(25, 0, 'UNKNOWN', 'Unknown state', 'LBL_ELEMENT_TYPE_STATE_UNKNOWN', 'LBL_ELEMENT_TYPE_STATE_UNKNOWN_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(25, 1, 'UP', 'The state of the equipment is: comunicates', 'LBL_ELEMENT_TYPE_STATE_UP', 'LBL_ELEMENT_TYPE_STATE_UP_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(25, 2, 'DOWN', 'The state of the equipment is: out of communication', 'LBL_ELEMENT_TYPE_STATE_DOWN', 'LBL_ELEMENT_TYPE_STATE_DOWN_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(25, 3, 'ALARMS', 'The state of the equipment is: with alarms', 'LBL_ELEMENT_TYPE_STATE_ALARMS', 'LBL_ELEMENT_TYPE_STATE_ALARMS_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(25, 4, 'MAINTENANCE', 'The state of the equipment is: on Maintenance', 'LBL_ELEMENT_TYPE_STATE_MAINTENANCE', 'LBL_ELEMENT_TYPE_STATE_MAINTENANCE_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


-- 
-- Table: master.element_type_state_transitions
--
INSERT INTO master.element_type_state_transitions 
(element_type_id, current_element_type_state_id, next_element_type_state_id, enabled, visible, editable, created_at, updated_at) VALUES
(25, 0, 1, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(25, 0, 2, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(25, 1, 2, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(25, 1, 3, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(25, 2, 1, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(25, 2, 3, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(25, 3, 1, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(25, 3, 2, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: master.io_controller_module_type_element_types
--
INSERT INTO master.io_controller_module_type_element_types
(module_type_id, element_type_id, enabled, visible, created_at, updated_at) VALUES
(1, 25, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: conf.alarm_configs
--
INSERT INTO conf.alarm_configs
(alarm_config_id, alarm_type_id, alarm_level_id, element_type_id, element_id, element_type_state_id, mine, treatment, alias, description, label_alias, label_description, enabled, visible, created_at, updated_at) VALUES
(68, 2, 5, 25, NULL, 3, true, true, 'ALARM PLC_CABINET SURGE PROTECTION', 'ALARM PLC_CABINET SURGE PROTECTION', 'LBL_ALARM_CONFIGS_ALARM_PLC_CABINET_SURGE_PROTECTION', 'LBL_ALARM_CONFIGS_ALARM_PLC_CABINET_SURGE_PROTECTION_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(69, 2, 5, 25, NULL, 3, true, true, 'ALARM PLC_CABINET DOOR OPEN', 'ALARM PLC_CABINET DOOR OPEN', 'LBL_ALARM_CONFIGS_ALARM_PLC_CABINET_DOOR_OPEN', 'LBL_ALARM_CONFIGS_ALARM_PLC_CABINET_DOOR_OPEN_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(70, 2, 5, 25, NULL, 3, true, true, 'ALARM PLC_CABINET LOW POTENCIAL', 'ALARM PLC_CABINET LOW POTENCIAL', 'LBL_ALARM_CONFIGS_ALARM_PLC_CABINET_LOW_POTENCIAL', 'LBL_ALARM_CONFIGS_ALARM_PLC_CABINET_LOW_POTENCIAL_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(71, 2, 5, 25, NULL, 3, true, true, 'ALARM PLC_CABINET INSULATION LOSSES-1', 'ALARM PLC_CABINET INSULATION LOSSES-1', 'LBL_ALARM_CONFIGS_ALARM_PLC_CABINET_INSULATION_LOSSES-1', 'LBL_ALARM_CONFIGS_ALARM_PLC_CABINET_INSULATION_LOSSES-1_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(72, 2, 5, 25, NULL, 3, true, true, 'ALARM PLC_CABINET INSULATION LOSSES-2', 'ALARM PLC_CABINET INSULATION LOSSES-2', 'LBL_ALARM_CONFIGS_ALARM_PLC_CABINET_INSULATION_LOSSES-2', 'LBL_ALARM_CONFIGS_ALARM_PLC_CABINET_INSULATION_LOSSES-2_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(73, 2, 5, 25, NULL, 3, true, true, 'ALARM PLC_CABINET TRAFOSTATION DOOR OPEN', 'ALARM PLC_CABINET TRAFOSTATION DOOR OPEN', 'LBL_ALARM_CONFIGS_ALARM_PLC_CABINET_TRAFOSTATION_DOOR_OPEN', 'LBL_ALARM_CONFIGS_ALARM_PLC_CABINET_TRAFOSTATION_DOOR_OPEN_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(74, 2, 5, 25, NULL, NULL, true, true, 'ALARM PLC_CABINET NO CONNECTION', 'ALARM PLC_CABINET NO CONNECTION', 'LBL_ALARM_CONFIGS_ALARM_PLC_CABINET_NO_CONNECTION', 'LBL_ALARM_CONFIGS_ALARM_PLC_CABINET_NO_CONNECTION_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2010025, 2, 5, 25, NULL, 3, true, true, 'ALARM CABINET OVERVOLTAGE', 'ALARM CABINET OVERVOLTAGE', 'LBL_ALARM_CONFIGS_ALARM_CABINET_OVERVOLTAGE', 'LBL_ALARM_CONFIGS_ALARM_CABINET_OVERVOLTAGE', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),    
(2020025, 2, 5, 25, NULL, 3, true, true, 'ALARM CABINET SOLAR SYSTEM ALARM', 'ALARM CABINET SOLAR SYSTEM ALARM', 'LBL_ALARM_CONFIGS_ALARM_CABINET_SOLAR_SYSTEM_ALARM', 'LBL_ALARM_CONFIGS_ALARM_CABINET_SOLAR_SYSTEM_ALARM', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);    

-- 
-- Table: conf.element_state_alarms
--
INSERT INTO conf.element_state_alarms
(element_type_id, element_type_state_id, alarm_config_id, created_at, updated_at) VALUES
(25, 2, 74, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
