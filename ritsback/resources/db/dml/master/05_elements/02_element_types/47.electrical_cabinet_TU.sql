-- 
-- Table: master.element_types
--
INSERT INTO master.element_types(element_type_id, element_group_id, alias, description,label_alias, label_description, initial_element_type_state_id, connected_element_type_state_id, disconnected_element_type_state_id, enabled, visible, created_at, updated_at) VALUES
(47, 2, 'ELECTRICAL_CABINET_TU', 'ELECTRICAL_CABINET_TU', 'LBL_ELEMENT_TYPE_ELECTRICAL_CABINET_TU', 'LBL_ELEMENT_TYPE_ELECTRICAL_CABINET_TU_DESC', 0, 1, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

--
-- Table: master.element_type_params
--
INSERT INTO master.element_type_params (element_type_id, param_type_id, element_type_param_id, element_type_param_unit_id, data_type_id, default_value, alias, description, label_alias, label_description, enabled, visible, editable, created_at, updated_at) VALUES
--CONFIG
(47,1,1, NULL,2, ' ','brand', 'brand', 'LBL_ELEMENT_TYPE_PARAM_BRAND' , 'LBL_ELEMENT_TYPE_PARAM_BRAND_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(47,1,2, NULL,2, ' ', 'model', 'model', 'LBL_ELEMENT_TYPE_PARAM_MODEL' , 'LBL_ELEMENT_TYPE_PARAM_MODEL_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(47,1,1003, NULL,2, ' ', 'MANGO SESSION_UID', 'MANGO SESSION_UID', 'LBL_ELEMENT_TYPE_PARAM_MANGO_SESSION_UID' , 'LBL_ELEMENT_TYPE_PARAM_MANGO_SESSION_UID_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: master.element_type_states
--
INSERT INTO master.element_type_states(element_type_id, element_type_state_id, alias, description, label_alias, label_description, enabled, visible, created_at, updated_at) VALUES
(47, 0, 'UNKNOWN', 'Unknown state', 'LBL_ELEMENT_TYPE_STATE_UNKNOWN', 'LBL_ELEMENT_TYPE_STATE_UNKNOWN_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(47, 1, 'UP', 'The state of the equipment is: comunicates', 'LBL_ELEMENT_TYPE_STATE_UP', 'LBL_ELEMENT_TYPE_STATE_UP_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(47, 2, 'DOWN', 'The state of the equipment is: out of communication', 'LBL_ELEMENT_TYPE_STATE_DOWN', 'LBL_ELEMENT_TYPE_STATE_DOWN_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(47, 3, 'ALARMS', 'The state of the equipment is: with alarms', 'LBL_ELEMENT_TYPE_STATE_ALARMS', 'LBL_ELEMENT_TYPE_STATE_ALARMS_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(47, 4, 'MAINTENANCE', 'The state of the equipment is: on Maintenance', 'LBL_ELEMENT_TYPE_STATE_MAINTENANCE', 'LBL_ELEMENT_TYPE_STATE_MAINTENANCE_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: master.element_type_state_transitions
--
INSERT INTO master.element_type_state_transitions (element_type_id, current_element_type_state_id, next_element_type_state_id, enabled, visible, editable, created_at, updated_at) VALUES
(47, 1, 2, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(47, 1, 3, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(47, 2, 1, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(47, 2, 3, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(47, 3, 1, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(47, 3, 2, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(47, 0, 1, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(47, 0, 2, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: master.io_controller_module_type_element_types
--
INSERT INTO master.io_controller_module_type_element_types
(module_type_id, element_type_id, enabled, visible, created_at, updated_at) VALUES
(1, 47, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: conf.alarm_configs
--
INSERT INTO conf.alarm_configs
(alarm_config_id, alarm_type_id, alarm_level_id, element_type_id, element_id, element_type_state_id, mine, treatment, alias, description, label_alias, label_description, enabled, visible, created_at, updated_at) VALUES
(161, 2, 5, 47, NULL, 3, true, true, 'ALARM PLC_CABINET DOOR OPEN', 'ALARM PLC_CABINET DOOR OPEN', 'LBL_ALARM_CONFIGS_ALARM_PLC_CABINET_DOOR_OPEN', 'LBL_ALARM_CONFIGS_ALARM_PLC_CABINET_DOOR_OPEN_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(162, 2, 5, 47, NULL, 3, true, true, 'ALARM PLC_CABINET POWER SUPPLY FAIL', 'ALARM PLC_POWER SUPPLY FAIL', 'LBL_ALARM_CONFIGS_ALARM_PLC_POWER_SUPPLY_FAIL', 'LBL_ALARM_CONFIGS_ALARM_PLC_POWER_SUPPLY_FAIL_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(163, 2, 5, 47, NULL, 3, true, true, 'ALARM PLC_CABINET BATTERY LOW', 'ALARM PLC_CABINET BATTERY LOW', 'LBL_ALARM_CONFIGS_ALARM_PLC_CABINET_BATTERY_LOW', 'LBL_ALARM_CONFIGS_ALARM_PLC_CABINET_BATTERY_LOW_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(164, 2, 5, 47, NULL, 3, true, true, 'ALARM PLC_CABINET OVERLOAD UPS', 'ALARM PLC_CABINET OVERLOAD UPS', 'LBL_ALARM_CONFIGS_ALARM_PLC_CABINET_OVERLOAD_UPS', 'LBL_ALARM_CONFIGS_ALARM_PLC_CABINET_OVERLOAD_UPS_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(165, 2, 6, 47, NULL, NULL, true, true, 'ALARM PLC_CABINET NO CONNECTION', 'ALARM PLC_CABINET NO CONNECTION', 'LBL_ALARM_CONFIGS_ALARM_PLC_CABINET_NO_CONNECTION', 'LBL_ALARM_CONFIGS_ALARM_PLC_CABINET_NO_CONNECTION_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: conf.element_state_alarms
--
INSERT INTO conf.element_state_alarms
(element_type_id, element_type_state_id, alarm_config_id, created_at, updated_at) VALUES
(47, 2, 165, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);