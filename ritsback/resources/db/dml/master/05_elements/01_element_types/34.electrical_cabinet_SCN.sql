-- 
-- Table: master.element_types
--
INSERT INTO master.element_types(element_type_id, alias, description,label_alias, label_description, initial_element_type_state_id, connected_element_type_state_id, disconnected_element_type_state_id, enabled, visible, created_at, updated_at) VALUES
(34, 'ELECTRICAL_CABINET_SCN', 'ELECTRICAL_CABINET_SCN', 'LBL_ELEMENT_TYPE_ELECTRICAL_CABINET_SCN', 'LBL_ELEMENT_TYPE_ELECTRICAL_CABINET_SCN_DESC', 0, 1, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

--
-- Table: master.element_type_params.ELECTRICAL_CABINET_SCN
--
INSERT INTO master.element_type_params (element_type_id, param_type_id, element_type_param_id, element_type_param_unit_id, data_type_id, default_value, alias, description, label_alias, label_description, enabled, visible, editable, created_at, updated_at) VALUES
--CONFIG
(34,1,1, NULL,2, NULL, 'brand', 'brand', 'LBL_ELEMENT_TYPE_PARAM_BRAND' , 'LBL_ELEMENT_TYPE_PARAM_BRAND_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(34,1,2, NULL,2, NULL, 'model', 'model', 'LBL_ELEMENT_TYPE_PARAM_MODEL' , 'LBL_ELEMENT_TYPE_PARAM_MODEL_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(34,1,1003, NULL,2, NULL, 'MANGO SESSION_UID', 'MANGO SESSION_UID', 'LBL_ELEMENT_TYPE_PARAM_MANGO_SESSION_UID' , 'LBL_ELEMENT_TYPE_PARAM_MANGO_SESSION_UID_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: master.element_type_states.ELECTRICAL_CABINET_SCN
--
INSERT INTO master.element_type_states(element_type_id, element_type_state_id, alias, description, label_alias, label_description, enabled, visible, created_at, updated_at) VALUES
(34, 0, 'UNKNOWN', 'Unknown state', 'LBL_ELEMENT_TYPE_STATE_UNKNOWN', 'LBL_ELEMENT_TYPE_STATE_UNKNOWN_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(34, 1, 'UP', 'The state of the equipment is: comunicates', 'LBL_ELEMENT_TYPE_STATE_UP', 'LBL_ELEMENT_TYPE_STATE_UP_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(34, 2, 'DOWN', 'The state of the equipment is: out of communication', 'LBL_ELEMENT_TYPE_STATE_DOWN', 'LBL_ELEMENT_TYPE_STATE_DOWN_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(34, 3, 'ALARMS', 'The state of the equipment is: with alarms', 'LBL_ELEMENT_TYPE_STATE_ALARMS', 'LBL_ELEMENT_TYPE_STATE_ALARMS_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(34, 4, 'MAINTENANCE', 'The state of the equipment is: on Maintenance', 'LBL_ELEMENT_TYPE_STATE_MAINTENANCE', 'LBL_ELEMENT_TYPE_STATE_MAINTENANCE_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: master.element_type_state_transitions
--
INSERT INTO master.element_type_state_transitions (element_type_id, current_element_type_state_id, next_element_type_state_id, enabled, visible, editable, created_at, updated_at) VALUES
(34, 1, 2, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(34, 1, 3, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(34, 2, 1, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(34, 2, 3, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(34, 3, 1, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(34, 3, 2, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(34, 0, 1, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(34, 0, 2, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: master.io_controller_module_type_element_types
--
INSERT INTO master.io_controller_module_type_element_types
(module_type_id, element_type_id, enabled, visible, created_at, updated_at) VALUES
(1, 34, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: conf.alarm_configs
--
INSERT INTO conf.alarm_configs
(alarm_config_id, alarm_type_id, alarm_level_id, element_type_id, element_id, element_type_state_id, mine, treatment, alias, description, label_alias, label_description, enabled, visible, created_at, updated_at) VALUES
(010034, 2, 6, 34, NULL, NULL, true, true, 'ALARM CABINET NO CONNECTION', 'ALARM CABINET NO CONNECTION', 'LBL_ALARM_CONFIGS_ALARM_CABINET_NO_CONNECTION', 'LBL_ALARM_CONFIGS_ALARM_CABINET_NO_CONNECTION_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(020034, 2, 5, 34, NULL, 3, true, true, 'ALARM CABINET DOOR OPEN', 'ALARM CABINET DOOR OPEN', 'LBL_ALARM_CONFIGS_ALARM_CABINET_DOOR_OPEN', 'LBL_ALARM_CONFIGS_ALARM_CABINET_DOOR_OPEN_DESC', false, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(030034, 2, 5, 34, NULL, 3, true, true, 'ALARM CABINET POWER SUPPLY ERROR - ANY CB OPEN', 'ALARM PLC_POWER SUPPLY ERROR - ANY CB OPEN', 'LBL_ALARM_CONFIGS_ALARM_CABINET_POWER_SUPPLY_ERROR_-_ANY_CB_OPEN', 'LBL_ALARM_CONFIGS_ALARM_CABINET_POWER_SUPPLY_ERROR_-_ANY_CB_OPEN_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(040034, 2, 6, 34, NULL, 3, true, true, 'ALARM CABINET UPS BATTERY LOW', 'ALARM CABINET UPS BATTERY LOW', 'LBL_ALARM_CONFIGS_ALARM_CABINET_UPS_BATTERY_LOW', 'LBL_ALARM_CONFIGS_ALARM_CABINET_UPS_BATTERY_LOW_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(050034, 2, 6, 34, NULL, 3, true, true, 'ALARM CABINET UPS ON BATTERY', 'ALARM CABINET UPS ON BATTERY', 'LBL_ALARM_CONFIGS_ALARM_CABINET_UPS_ON_BATTERY', 'LBL_ALARM_CONFIGS_ALARM_CABINET_UPS_ON_BATTERY_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(060034, 2, 5, 34, NULL, 3, true, true, 'ALARM CABINET UPS ANY ALARM', 'ALARM CABINET UPS ANY ALARM', 'LBL_ALARM_CONFIGS_ALARM_CABINET_UPS_ANY_ALARM', 'LBL_ALARM_CONFIGS_ALARM_CABINET_UPS_ANY_ALARM_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(070034, 2, 6, 34, NULL, 3, true, true, 'ALARM CABINET UPS ON BYPASS', 'ALARM CABINET UPS ON BYPASS', 'LBL_ALARM_CONFIGS_ALARM_CABINET_UPS_ON_BYPASS', 'LBL_ALARM_CONFIGS_ALARM_CABINET_UPS_ON_BYPASS_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(080034, 2, 5, 34, NULL, 3, true, true, 'ALARM CABINET AIRCONDITIONING ON', 'ALARM CABINET AIRCONDITIONING ON', 'LBL_ALARM_CONFIGS_ALARM_CABINET_AIRCONDITIONING_ON', 'LBL_ALARM_CONFIGS_ALARM_CABINET_AIRCONDITIONING_ON_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(090034, 2, 5, 34, NULL, 3, true, true, 'ALARM CABINET AIRCONDITIONING ALARM', 'ALARM CABINET AIRCONDITIONING ALARM', 'LBL_ALARM_CONFIGS_ALARM_CABINET_AIRCONDITIONING_ALARM', 'LBL_ALARM_CONFIGS_ALARM_CABINET_AIRCONDITIONING_ALARM_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: conf.element_state_alarms
--
INSERT INTO conf.element_state_alarms
(element_type_id, element_type_state_id, alarm_config_id, created_at, updated_at) VALUES
(34, 2, 010034, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
