-- 
-- Table: master.element_types
--
INSERT INTO master.element_types(element_type_id, alias, description, label_alias, label_description, initial_element_type_state_id, connected_element_type_state_id, disconnected_element_type_state_id, enabled, visible, created_at, updated_at)
VALUES(17, 'GALIBO', 'GALIBO','LBL_ELEMENT_TYPE_GALIBO', 'LBL_ELEMENT_TYPE_GALIBO_DESC', 0, 1, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

--
-- Table: master.element_type_params
--
INSERT INTO master.element_type_params (element_type_id, param_type_id, element_type_param_id, element_type_param_unit_id, data_type_id, default_value, alias, description, label_alias, label_description, enabled, visible, editable, created_at, updated_at) VALUES
--CONFIG
(17,1,1, NULL,2, NULL, 'brand', 'brand', 'LBL_ELEMENT_TYPE_PARAM_BRAND' , 'LBL_ELEMENT_TYPE_PARAM_BRAND_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(17,1,2, NULL,2, NULL, 'model', 'model', 'LBL_ELEMENT_TYPE_PARAM_MODEL' , 'LBL_ELEMENT_TYPE_PARAM_MODEL_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(17,1,1003, NULL,2, NULL, 'MANGO SESSION_UID', 'MANGO SESSION_UID', 'LBL_ELEMENT_TYPE_PARAM_MANGO_SESSION_UID' , 'LBL_ELEMENT_TYPE_PARAM_MANGO_SESSION_UID_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: master.element_type_states
--
INSERT INTO master.element_type_states(element_type_id, element_type_state_id, alias, description, label_alias, label_description, enabled, visible, created_at, updated_at) VALUES
(17, 0, 'UNKNOWN', 'Unknown state', 'LBL_ELEMENT_TYPE_STATE_UNKNOWN', 'LBL_ELEMENT_TYPE_STATE_UNKNOWN_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(17, 1, 'UP', 'The state of the equipment is: comunicates', 'LBL_ELEMENT_TYPE_STATE_UP', 'LBL_ELEMENT_TYPE_STATE_UP_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(17, 2, 'DOWN', 'The state of the equipment is: out of communication', 'LBL_ELEMENT_TYPE_STATE_DOWN', 'LBL_ELEMENT_TYPE_STATE_DOWN_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(17, 3, 'ALARMS', 'The state of the equipment is: with alarms', 'LBL_ELEMENT_TYPE_STATE_ALARMS', 'LBL_ELEMENT_TYPE_STATE_ALARMS_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(17, 4, 'MAINTENANCE', 'The state of the equipment is: on Maintenance', 'LBL_ELEMENT_TYPE_STATE_MAINTENANCE', 'LBL_ELEMENT_TYPE_STATE_MAINTENANCE_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: master.element_type_state_transitions
--
INSERT INTO master.element_type_state_transitions (element_type_id, current_element_type_state_id, next_element_type_state_id, enabled, visible, editable, created_at, updated_at) VALUES
(17, 1, 2, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(17, 1, 3, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(17, 2, 1, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(17, 2, 3, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(17, 3, 1, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(17, 3, 2, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(17, 0, 1, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(17, 0, 2, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: master.element_hierarchies_allowed
--
INSERT INTO master.element_hierarchies_allowed
(parent_element_type_id, child_element_type_id, enabled, visible, created_at, updated_at) VALUES
(17, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(17, 71, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: master.io_controller_module_type_element_types
--
INSERT INTO master.io_controller_module_type_element_types
(module_type_id, element_type_id, enabled, visible, created_at, updated_at) VALUES
(1, 17, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

--
-- Table: conf.alarm_configs
--
INSERT INTO conf.alarm_configs 
(alarm_config_id, alarm_type_id, alarm_level_id, element_type_id, element_id, element_type_state_id, mine, treatment, alias, description, label_alias, label_description, enabled, visible, created_at, updated_at) values
(010017, 2, 3, 17, NULL, NULL, true, true, 'ALARM GALIBO NO CONNECTION', 'ALARM GALIBO NO CONNECTION', 'LBL_ALARM_CONFIGS_GALIBO_NO_CONNECTION', 'LBL_ALARM_CONFIGS_GALIBO_NO_CONNECTION', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(020017, 2, 3, 17, NULL, 3, true, true, 'ALARM GALIBO CONTROLLER CABINET DOOR OPEN', 'ALARM GALIBO CONTROLLER CABINET DOOR OPEN', 'LBL_ALARM_CONFIGS_GALIBO_CONTROLLER_CABINET_DOOR_OPEN', 'LBL_ALARM_CONFIGS_GALIBO_CONTROLLER_CABINET_DOOR_OPEN', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(030017, 2, 3, 17, NULL, 3, true, true, 'ALARM GALIBO PANEL EXCESS DOOR OPEN', 'ALARM GALIBO PANEL EXCESS DOOR OPEN', 'LBL_ALARM_CONFIGS_GALIBO_PANEL_EXCESS_DOOR_OPEN', 'LBL_ALARM_CONFIGS_GALIBO_PANEL_EXCESS_DOOR_OPEN', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(040017, 2, 3, 17, NULL, 3, true, true, 'ALARM GALIBO POWER SUPPLY CONTROLLER FAIL', 'ALARM GALIBO POWER SUPPLY CONTROLLER FAIL', 'LBL_ALARM_CONFIGS_GALIBO_POWER_SUPPLY_CONTROLLER_FAIL', 'LBL_ALARM_CONFIGS_GALIBO_POWER_SUPPLY_CONTROLLER_FAIL', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(050017, 2, 3, 17, NULL, 3, true, true, 'ALARM GALIBO SIGNAL POWER FAILURE', 'ALARM GALIBO SIGNAL POWER FAILURE', 'LBL_ALARM_CONFIGS_GALIBO_SIGNAL_POWER_FAILURE', 'LBL_ALARM_CONFIGS_GALIBO_SIGNAL_POWER_FAILURE', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(060017, 2, 3, 17, NULL, 3, true, true, 'ALARM GALIBO LED FAIL', 'ALARM GALIBO LED FAIL', 'LBL_ALARM_CONFIGS_GALIBO_LED_FAIL', 'LBL_ALARM_CONFIGS_GALIBO_LED_FAIL', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(070017, 2, 3, 17, NULL, 3, true, true, 'ALARM GALIBO LOOP FAIL', 'ALARM GALIBO LOOP_FAIL', 'LBL_ALARM_CONFIGS_GALIBO_LOOP_FAIL', 'LBL_ALARM_CONFIGS_GALIBO_LOOP_FAIL', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(080017, 2, 3, 17, NULL, 3, true, true, 'ALARM GALIBO PHOTOCELL 1 FAILURE', 'ALARM GALIBO PHOTOCELL 1 FAILURE', 'LBL_ALARM_CONFIGS_GALIBO_PHOTOCELL_1_FAILURE', 'LBL_ALARM_CONFIGS_GALIBO_PHOTOCELL_1_FAILURE', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(090017, 2, 3, 17, NULL, 3, true, true, 'ALARM GALIBO PHOTOCELL 2 FAILURE', 'ALARM GALIBO PHOTOCELL 2 FAILURE', 'LBL_ALARM_CONFIGS_GALIBO_PHOTOCELL_2_FAILURE', 'LBL_ALARM_CONFIGS_GALIBO_PHOTOCELL_2_FAILURE', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(100017, 2, 3, 17, NULL, 3, true, true, 'ALARM GALIBO PANEL COMMUNICATION FAILURE', 'ALARM GALIBO PANEL COMMUNICATION FAILURE', 'LBL_ALARM_CONFIGS_GALIBO_PANEL_COMMUNICATION_FAILURE', 'LBL_ALARM_CONFIGS_GALIBO_PANEL_COMMUNICATION_FAILURE', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(110017, 2, 3, 17, NULL, 3, true, true, 'ALARM GALIBO ALARM', 'ALARM GALIBO ALARM', 'LBL_ALARM_CONFIGS_GALIBO_ALARM', 'LBL_ALARM_CONFIGS_GALIBO_ALARM', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(120017, 2, 3, 17, NULL, 3, true, true, 'ALARM GALIBO FAULT CONTROLLER', 'ALARM GALIBO FAULT CONTROLLER', 'LBL_ALARM_CONFIGS_GALIBO_FAULT_CONTROLLER', 'LBL_ALARM_CONFIGS_GALIBO_FAULT_CONTROLLER', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: conf.element_state_alarms
--
INSERT INTO conf.element_state_alarms
(element_type_id, element_type_state_id, alarm_config_id, created_at, updated_at) VALUES
(17, 2, 010017, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
