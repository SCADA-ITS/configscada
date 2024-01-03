-- 
-- Table: master.element_types
--
INSERT INTO master.element_types(element_type_id, alias, description,label_alias, label_description, initial_element_type_state_id, connected_element_type_state_id, disconnected_element_type_state_id, enabled, visible, created_at, updated_at)
VALUES(38, 'BUS_VAO', 'BUS_VAO','LBL_ELEMENT_TYPE_BUS_VAO', 'LBL_ELEMENT_TYPE_BUS_VAO_DESC', 0, 1, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


-- 
-- Table: master.element_type_states.BUS_VAO
--
INSERT INTO master.element_type_params (element_type_id, param_type_id, element_type_param_id, element_type_param_unit_id, data_type_id, default_value, alias, description, label_alias, label_description, enabled, visible, editable, created_at, updated_at) VALUES
--CONFIG
(38, 1, 1, NULL, 2, ' ', 'brand', 'brand', 'LBL_ELEMENT_TYPE_PARAM_BRAND' , 'LBL_ELEMENT_TYPE_PARAM_BRAND_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(38, 1, 2, NULL, 2, ' ', 'model', 'model', 'LBL_ELEMENT_TYPE_PARAM_MODEL' , 'LBL_ELEMENT_TYPE_PARAM_MODEL_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(38, 1, 1003, NULL, 2, ' ', 'MANGO SESSION_UID', 'MANGO SESSION_UID', 'LBL_ELEMENT_TYPE_PARAM_MANGO_SESSION_UID' , 'LBL_ELEMENT_TYPE_PARAM_MANGO_SESSION_UID_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


--
-- Table: master.element_type_params
--
INSERT INTO master.element_type_states(element_type_id, element_type_state_id, alias, description, label_alias, label_description, enabled, visible, created_at, updated_at) VALUES
(38, 0, 'UNKNOWN', 'Unknown state', 'LBL_ELEMENT_TYPE_STATE_UNKNOWN', 'LBL_ELEMENT_TYPE_STATE_UNKNOWN_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(38, 1, 'UP', 'The state of the equipment is: comunicates', 'LBL_ELEMENT_TYPE_STATE_UP', 'LBL_ELEMENT_TYPE_STATE_UP_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(38, 2, 'DOWN', 'The state of the equipment is: out of communication', 'LBL_ELEMENT_TYPE_STATE_DOWN', 'LBL_ELEMENT_TYPE_STATE_DOWN_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(38, 3, 'ALARMS', 'The state of the equipment is: with alarms', 'LBL_ELEMENT_TYPE_STATE_ALARMS', 'LBL_ELEMENT_TYPE_STATE_ALARMS_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(38, 4, 'MAINTENANCE', 'The state of the equipment is: on Maintenance', 'LBL_ELEMENT_TYPE_STATE_MAINTENANCE', 'LBL_ELEMENT_TYPE_STATE_MAINTENANCE_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: master.element_type_state_transitions
--
INSERT INTO master.element_type_state_transitions
(element_type_id, current_element_type_state_id, next_element_type_state_id, enabled, visible, editable, created_at, updated_at) VALUES
(38, 0, 1, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(38, 0, 2, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(38, 1, 2, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(38, 1, 3, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(38, 2, 1, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(38, 2, 3, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(38, 3, 1, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(38, 3, 2, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: master.command_element_types
--
INSERT INTO master.command_element_types
(command_element_type_id, element_type_id, alias, description, label_alias, label_description, enabled, visible, created_at, updated_at) VALUES
(1, 38, 'APAGADO', 'APAGADO', 'LBL_COMMAND_ELEMENT_TYPE_BUS_VAO_MODE_OFF', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 38, 'ENCENDIDO 100%', 'ENCENDIDO 100%', 'LBL_COMMAND_ELEMENT_TYPE_BUS_VAO_MODE_ON_100', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 38, 'ENCENDIDO 70%', 'ENCENDIDO 70%', 'LBL_COMMAND_ELEMENT_TYPE_BUS_VAO_MODE_ON_70', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 38, 'FLASHING 100%', 'FLASHING 100%', 'LBL_COMMAND_ELEMENT_TYPE_BUS_VAO_MODE_FLASHING_100', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 38, 'FLASHING 70%', 'FLASHING 70%', 'LBL_COMMAND_ELEMENT_TYPE_BUS_VAO_MODE_FLASHING_70', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


-- 
-- Table: master.io_controller_module_type_element_types
--
INSERT INTO master.io_controller_module_type_element_types
(module_type_id, element_type_id, enabled, visible, created_at, updated_at) VALUES
(1, 38, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: conf.alarm_configs
--
INSERT INTO conf.alarm_configs
(alarm_config_id, alarm_type_id, alarm_level_id, element_type_id, element_id, element_type_state_id, mine, treatment, alias, description, label_alias, label_description, enabled, visible, created_at, updated_at) VALUES
(010038, 2, 3, 38, NULL, NULL, true, true, 'ALARM BUS_VAO DISCONNECTED', 'ALARM BUS_VAO DISCONNECTED', 'LBL_ALARM_CONFIGS_ALARM_BUS_VAO_DISCONNECTED', 'LBL_ALARM_CONFIGS_ALARM_BUS_VAO_DISCONNECTED_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: conf.element_state_alarms
--
INSERT INTO conf.element_state_alarms
(element_type_id, element_type_state_id, alarm_config_id, created_at, updated_at) VALUES
(38, 2, 010038, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
