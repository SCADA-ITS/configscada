INSERT INTO master.element_types
(element_type_id, element_group_id, alias, description,label_alias, label_description, initial_element_type_state_id, connected_element_type_state_id, disconnected_element_type_state_id, enabled, visible, created_at, updated_at) VALUES
(55, NULL, 'CT', 'CT', 'LBL_ELEMENT_TYPE_CT', 'LBL_ELEMENT_TYPE_CT_DESC', 0, 1, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


--
-- Table: master.element_type_params
--
INSERT INTO master.element_type_params (element_type_id, param_type_id, element_type_param_id, element_type_param_unit_id, data_type_id, default_value, alias, description, label_alias, label_description, enabled, visible, editable, created_at, updated_at) VALUES
--CONFIG
(55, 1, 1, NULL, 2, NULL, 'brand', 'brand', 'LBL_ELEMENT_TYPE_PARAM_BRAND' , 'LBL_ELEMENT_TYPE_PARAM_BRAND_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(55, 1, 2, NULL, 2, NULL, 'model', 'model', 'LBL_ELEMENT_TYPE_PARAM_MODEL' , 'LBL_ELEMENT_TYPE_PARAM_MODEL_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(55, 1, 1003, NULL, 2, NULL, 'MANGO SESSION_UID', 'MANGO SESSION_UID', 'LBL_ELEMENT_TYPE_PARAM_MANGO_SESSION_UID' , 'LBL_ELEMENT_TYPE_PARAM_MANGO_SESSION_UID_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
--MEASURE
(55, 2, 1, 8, 1, NULL, 'temperature', 'temperature', 'LBL_ELEMENT_TYPE_PARAM_CT_TEMPERATURE' , 'LBL_ELEMENT_TYPE_PARAM_CT_TEMPERATURE_DESC', true, true, false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: master.element_type_states
--
INSERT INTO master.element_type_states(element_type_id, element_type_state_id, alias, description, label_alias, label_description, enabled, visible, created_at, updated_at) VALUES
(55, 0, 'UNKNOWN', 'Unknown state', 'LBL_ELEMENT_TYPE_STATE_UNKNOWN', 'LBL_ELEMENT_TYPE_STATE_UNKNOWN_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(55, 1, 'UP', 'The state of the equipment is: comunicates', 'LBL_ELEMENT_TYPE_STATE_UP', 'LBL_ELEMENT_TYPE_STATE_UP_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(55, 2, 'DOWN', 'The state of the equipment is: out of communication', 'LBL_ELEMENT_TYPE_STATE_DOWN', 'LBL_ELEMENT_TYPE_STATE_DOWN_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(55, 3, 'ALARMS', 'The state of the equipment is: with alarms', 'LBL_ELEMENT_TYPE_STATE_ALARMS', 'LBL_ELEMENT_TYPE_STATE_ALARMS_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(55, 4, 'MAINTENANCE', 'The state of the equipment is: on Maintenance', 'LBL_ELEMENT_TYPE_STATE_MAINTENANCE', 'LBL_ELEMENT_TYPE_STATE_MAINTENANCE_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: master.element_type_state_transitions
--
INSERT INTO master.element_type_state_transitions (element_type_id, current_element_type_state_id, next_element_type_state_id, enabled, visible, editable, created_at, updated_at) VALUES
(55, 1, 2, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(55, 1, 3, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(55, 2, 1, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(55, 2, 3, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(55, 3, 1, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(55, 3, 2, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(55, 0, 1, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(55, 0, 2, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO master.command_element_types
(command_element_type_id, element_type_id, alias, description, label_alias, label_description, on_change, enabled, visible, created_at, updated_at) VALUES
(1, 55, 'RESET DE ALARMAS GENERAL', 'RESET DE ALARMAS GENERAL', 'LBL_COMMAND_ELEMENT_TYPE_RESET_DE_ALARMAS_GENERAL', null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


-- 
-- Table: master.element_hierarchies_allowed
--
INSERT INTO master.element_hierarchies_allowed
(parent_element_type_id, child_element_type_id, enabled, visible, created_at, updated_at) VALUES
(55, 22, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(55, 23, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(55, 55, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(55, 56, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(55, 58, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(55, 59, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(55, 60, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(55, 61, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(55, 62, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(55, 63, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(55, 65, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(55, 70, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(55, 74, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(55, 75, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: master.io_controller_module_type_element_types
--
INSERT INTO master.io_controller_module_type_element_types
(module_type_id, element_type_id, enabled, visible, created_at, updated_at) VALUES
(1, 55, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: conf.alarm_configs
--
INSERT INTO conf.alarm_configs
(alarm_config_id, alarm_type_id, alarm_level_id, element_type_id, element_id, element_type_state_id, mine, treatment, alias, description, label_alias, label_description, enabled, visible, created_at, updated_at) VALUES
(010055, 2, 3, 55, NULL, 3, true, true, 'ALARM CT AIR CONDITIONER', 'ALARM CT AIR CONDITIONER', 'LBL_ALARM_CONFIGS_ALARM_CT_AIR_CONDITIONER', 'LBL_ALARM_CONFIGS_ALARM_CT_AIR_CONDITIONER_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(020055, 2, 3, 55, NULL, 3, true, true, 'ALARM CT DOOR OPEN', 'ALARM CT DOOR OPEN', 'LBL_ALARM_CONFIGS_ALARM_CT_DOOR_OPEN', 'LBL_ALARM_CONFIGS_ALARM_CT_DOOR_OPEN_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(030055, 2, 3, 55, NULL, 3, true, true, 'ALARM CT EXCESS TEMPERATURE', 'ALARM CT EXCESS TEMPERATURE', 'LBL_ALARM_CONFIGS_ALARM_CT_EXCESS_TEMPERATURE', 'LBL_ALARM_CONFIGS_ALARM_CT_EXCESS_TEMPERATURE_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
