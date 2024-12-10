-- 
-- Table: master.element_types
--
INSERT INTO master.element_types(element_type_id, alias, description,label_alias, label_description, initial_element_type_state_id, connected_element_type_state_id, disconnected_element_type_state_id, enabled, visible, created_at, updated_at)
VALUES(87, 'TANK', 'TANK','LBL_ELEMENT_TYPE_TANK', 'LBL_ELEMENT_TYPE_TANK_DESC', 0, 1, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: master.element_type_params.PUMP
--

INSERT INTO master.element_type_params (element_type_id, param_type_id, element_type_param_id, element_type_param_unit_id, data_type_id, default_value, alias, description, label_alias, label_description, enabled, visible, editable, created_at, updated_at) VALUES
(87, 1, 1, NULL, 2, NULL, 'brand', 'brand', 'LBL_ELEMENT_TYPE_PARAM_BRAND' , 'LBL_ELEMENT_TYPE_PARAM_BRAND_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(87, 1, 2, NULL, 2, NULL, 'model', 'model', 'LBL_ELEMENT_TYPE_PARAM_MODEL' , 'LBL_ELEMENT_TYPE_PARAM_MODEL_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(87, 1, 1003, NULL, 2, NULL, 'MANGO SESSION_UID', 'MANGO SESSION_UID', 'LBL_ELEMENT_TYPE_PARAM_MANGO_SESSION_UID' , 'LBL_ELEMENT_TYPE_PARAM_MANGO_SESSION_UID_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(87, 2, 1, 3, 1, NULL, 'tank_capacity', 'tank_capacity for tank water', 'LBL_ELEMENT_TYPE_PARAM_WATER_TANK_CAPACITY' , 'LBL_ELEMENT_TYPE_PARAM_WATER_TANK_CAPACITY_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(87, 2, 2, 3, 1, NULL, 'tank_capacity', 'tank_capacity for diesel tank', 'LBL_ELEMENT_TYPE_PARAM_DIESEL_TANK_CAPACITY' , 'LBL_ELEMENT_TYPE_PARAM_DIESEL_TANK_CAPACITY_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(87, 2, 3, 3, 1, NULL, 'toxic_capacity', 'toxic capacity for toxic tank', 'LBL_ELEMENT_TYPE_PARAM_TOXIC_TANK_CAPACITY' , 'LBL_ELEMENT_TYPE_PARAM_TOXIC_TANK_CAPACITY_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(87, 2, 4, NULL, 5, NULL, 'full', 'Full tank', 'LBL_ELEMENT_TYPE_PARAM_TANK_FULL' , 'LBL_ELEMENT_TYPE_PARAM_TANK_FULL_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


--
-- Table: master.element_type_params
--
INSERT INTO master.element_type_states(element_type_id, element_type_state_id, alias, description, label_alias, label_description, enabled, visible, created_at, updated_at) VALUES
(87, 0, 'UNKNOWN', 'Unknown state', 'LBL_ELEMENT_TYPE_STATE_UNKNOWN', 'LBL_ELEMENT_TYPE_STATE_UNKNOWN_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(87, 1, 'UP', 'The state of the equipment is: comunicates', 'LBL_ELEMENT_TYPE_STATE_UP', 'LBL_ELEMENT_TYPE_STATE_UP_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(87, 2, 'DOWN', 'The state of the equipment is: out of communication', 'LBL_ELEMENT_TYPE_STATE_DOWN', 'LBL_ELEMENT_TYPE_STATE_DOWN_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(87, 3, 'ALARMS', 'The state of the equipment is: with alarms', 'LBL_ELEMENT_TYPE_STATE_ALARMS', 'LBL_ELEMENT_TYPE_STATE_ALARMS_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(87, 4, 'MAINTENANCE', 'The state of the equipment is: on Maintenance', 'LBL_ELEMENT_TYPE_STATE_MAINTENANCE', 'LBL_ELEMENT_TYPE_STATE_MAINTENANCE_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: master.element_type_state_transitions
--
INSERT INTO master.element_type_state_transitions
(element_type_id, current_element_type_state_id, next_element_type_state_id, enabled, visible, editable, created_at, updated_at) VALUES
(87, 0, 1, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(87, 0, 2, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(87, 1, 2, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(87, 1, 3, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(87, 2, 1, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(87, 2, 3, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(87, 3, 1, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(87, 3, 2, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


-- 
-- Table: master.io_controller_module_type_element_types
--
INSERT INTO master.io_controller_module_type_element_types
(module_type_id, element_type_id, enabled, visible, created_at, updated_at) VALUES
(1, 87, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: conf.alarm_configs
--
INSERT INTO conf.alarm_configs
(alarm_config_id, alarm_type_id, alarm_level_id, element_type_id, element_id, element_type_state_id, mine, treatment, alias, description, label_alias, label_description, enabled, visible, created_at, updated_at) VALUES
(010087, 2, 3, 87, NULL, NULL, true, true, 'ALARM TANK DISCONNECTED', 'ALARM TANK DISCONNECTED', 'LBL_ALARM_CONFIGS_ALARM_TANK_DISCONNECTED', 'LBL_ALARM_CONFIGS_ALARM_PUMP_DISCONNECTED_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(020087, 2, 3, 87, NULL, 3, true, true, 'ALARM CRITICAL POND LEVEL', 'ALARM CRITICAL POND LEVEL', 'LBL_ALARM_CONFIGS_ALARM_CRITICAL_POND_LEVEL', 'LBL_ALARM_CONFIGS_ALARM_CRITICAL_POND_LEVEL_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: conf.element_state_alarms
--
INSERT INTO conf.element_state_alarms
(element_type_id, element_type_state_id, alarm_config_id, created_at, updated_at) VALUES
(87, 2, 010087, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
