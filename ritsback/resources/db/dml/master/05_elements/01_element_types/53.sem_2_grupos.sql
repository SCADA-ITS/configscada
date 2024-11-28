-- 
-- Table: master.element_types
--
INSERT INTO master.element_types
(element_type_id, alias, description,label_alias, label_description, initial_element_type_state_id, connected_element_type_state_id, disconnected_element_type_state_id, enabled, visible, created_at, updated_at) VALUES
(53, 'SEM_2_LUCES', 'SEM_2_LUCES', 'LBL_ELEMENT_TYPE_SEM_2_LUCES', 'LBL_ELEMENT_TYPE_SEM_2_LUCES_DESC', 0, 1, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

--
-- Table: master.element_type_params
--
INSERT INTO master.element_type_params (element_type_id, param_type_id, element_type_param_id, element_type_param_unit_id, data_type_id, default_value, alias, description, label_alias, label_description, enabled, visible, editable, created_at, updated_at) VALUES
--CONFIG
(53,1,1, NULL,2, NULL, 'brand', 'brand', 'LBL_ELEMENT_TYPE_PARAM_BRAND' , 'LBL_ELEMENT_TYPE_PARAM_BRAND_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(53,1,2, NULL,2, NULL, 'model', 'model', 'LBL_ELEMENT_TYPE_PARAM_MODEL' , 'LBL_ELEMENT_TYPE_PARAM_MODEL_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(53,1,1003, NULL,2, NULL, 'MANGO SESSION_UID', 'MANGO SESSION_UID', 'LBL_ELEMENT_TYPE_PARAM_MANGO_SESSION_UID' , 'LBL_ELEMENT_TYPE_PARAM_MANGO_SESSION_UID_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
--MEASURE
(53,2,1, NULL, 1, NULL, 'light_status', 'light_status', 'LBL_ELEMENT_TYPE_PARAM_LIGHT_AMBER' , 'LBL_ELEMENT_TYPE_PARAM_LIGHT_AMBER_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO master.element_subtypes
(element_subtype_id, element_type_id, alias, description,label_alias, label_description, enabled, visible, created_at, updated_at)VALUES
(1, 53, 'SEM_AA', 'SEM_AA', 'LBL_ELEMENT_SUBTYPE_SEM_AMB_AMB_ERU', 'LBL_ELEMENT_SUBTYPE_SEM_AMB_AMB_ERU_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 53, 'SEM_RR', 'SEM_ROJO_ROJO_ERU', 'LBL_ELEMENT_SUBTYPE_SEM_ROJO_ROJO_ERU', 'LBL_ELEMENT_SUBTYPE_SEM_ROJO_ROJO_ERU_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO master.element_subtype_active_params
(element_subtype_id, element_type_id, param_type_id, element_type_param_id, enabled, visible, created_at, updated_at)VALUES
(1, 53, 2, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(2, 53, 2, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: master.element_type_states.SEM
--
INSERT INTO master.element_type_states(element_type_id, element_type_state_id, alias, description, label_alias, label_description, enabled, visible, created_at, updated_at) VALUES
(53, 0, 'UNKNOWN', 'Unknown state', 'LBL_ELEMENT_TYPE_STATE_UNKNOWN', 'LBL_ELEMENT_TYPE_STATE_UNKNOWN_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(53, 1, 'UP', 'The state of the equipment is: comunicates', 'LBL_ELEMENT_TYPE_STATE_UP', 'LBL_ELEMENT_TYPE_STATE_UP_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(53, 2, 'DOWN', 'The state of the equipment is: out of communication', 'LBL_ELEMENT_TYPE_STATE_DOWN', 'LBL_ELEMENT_TYPE_STATE_DOWN_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(53, 3, 'ALARMS', 'The state of the equipment is: with alarms', 'LBL_ELEMENT_TYPE_STATE_ALARMS', 'LBL_ELEMENT_TYPE_STATE_ALARMS_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(53, 4, 'MAINTENANCE', 'The state of the equipment is: on Maintenance', 'LBL_ELEMENT_TYPE_STATE_MAINTENANCE', 'LBL_ELEMENT_TYPE_STATE_MAINTENANCE_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: master.element_type_state_transitions
--
INSERT INTO master.element_type_state_transitions (element_type_id, current_element_type_state_id, next_element_type_state_id, enabled, visible, editable, created_at, updated_at) VALUES
(53, 1, 2, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(53, 1, 3, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(53, 2, 1, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(53, 2, 3, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(53, 3, 1, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(53, 3, 2, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(53, 0, 1, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(53, 0, 2, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: master.command_element_types
--
INSERT INTO master.command_element_types
(command_element_type_id, element_type_id, alias, description, label_alias, label_description, enabled, visible, created_at, updated_at) VALUES
(1, 53, 'SWITCH ON', 'SWITCH ON', 'LBL_COMMAND_ELEMENT_TYPE_SEM_2_LIGHTS_SWITCH_ON', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 53, 'SWITCH OFF', 'SWITCH OFF', 'LBL_COMMAND_ELEMENT_TYPE_SEM_2_LIGHTS_SWITCH_OFF', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 53, 'AMBER ON', 'AMBER ON', 'LBL_COMMAND_ELEMENT_TYPE_AMBER_ON', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 53, 'FLASHING AMBER ON', 'FLASHING AMBER ON', 'LBL_COMMAND_ELEMENT_TYPE_FLASHIHNG_AMBER_ON', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 53, 'ALTERNANT AMBER ON', 'ALTERNANT AMBER ON', 'LBL_COMMAND_ELEMENT_TYPE_ALTERNANT_AMBER_ON', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: master.element_subtype_active_commands
--
INSERT INTO master.element_subtype_active_commands (element_subtype_id, command_element_type_id, element_type_id, enabled, visible, created_at, updated_at) VALUES
(1, 2, 53, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
--(1, 3, 53, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
--(1, 4, 53, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 5, 53, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(2, 1, 53, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 2, 53, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: master.io_controller_module_type_element_types
--
INSERT INTO master.io_controller_module_type_element_types
(module_type_id, element_type_id, enabled, visible, created_at, updated_at) VALUES
(1, 53, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: conf.alarm_configs
--
INSERT INTO conf.alarm_configs 
(alarm_config_id, alarm_type_id, alarm_level_id, element_type_id, element_id, element_type_state_id, mine, treatment, alias, description, label_alias, label_description, enabled, visible, created_at, updated_at)VALUES
(010053, 2, 3, 53, NULL, NULL, true, true, 'ALARM SEM NO CONNECTION', 'ALARM SEM NO CONNECTION', 'LBL_ALARM_CONFIGS_ALARM_SEM_NO_CONNECTION', 'LBL_ALARM_CONFIGS_ALARM_SEM_NO_CONNECTION_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


-- 
-- Table: conf.element_state_alarms
--
INSERT INTO conf.element_state_alarms
(element_type_id, element_type_state_id, alarm_config_id, created_at, updated_at) VALUES
(53, 2, 010053, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
