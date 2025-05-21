-- 
-- Table: master.element_types
--
INSERT INTO master.element_types(element_type_id, alias, description,label_alias, label_description, initial_element_type_state_id, connected_element_type_state_id, disconnected_element_type_state_id, enabled, visible, created_at, updated_at)
VALUES(84, 'LIGHTING_CIRCUIT', 'Lighting circuit','LBL_ELEMENT_TYPE_LIGHTING_CIRCUIT', 'LBL_ELEMENT_TYPE_LIGHTING_CIRCUIT_DESC', 0, 1, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


-- 
-- Table: master.element_type_states.LIGHTING_CIRCUIT
--
INSERT INTO master.element_type_params (element_type_id, param_type_id, element_type_param_id, element_type_param_unit_id, data_type_id, default_value, alias, description, label_alias, label_description, enabled, visible, editable, created_at, updated_at) VALUES
--CONFIG
(84, 1, 1, NULL, 2, NULL, 'brand', 'brand', 'LBL_ELEMENT_TYPE_PARAM_BRAND' , 'LBL_ELEMENT_TYPE_PARAM_BRAND_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(84, 1, 2, NULL, 2, NULL, 'model', 'model', 'LBL_ELEMENT_TYPE_PARAM_MODEL' , 'LBL_ELEMENT_TYPE_PARAM_MODEL_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(84, 1, 5, NULL, 2, NULL, 'circuit_type', 'circuit_type', 'LBL_ELEMENT_TYPE_PARAM_CIRCUIT_TYPE' , 'LBL_ELEMENT_TYPE_PARAM_CIRCUIT_TYPE_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(84, 1, 1003, NULL, 2, NULL, 'MANGO SESSION_UID', 'MANGO SESSION_UID', 'LBL_ELEMENT_TYPE_PARAM_MANGO_SESSION_UID' , 'LBL_ELEMENT_TYPE_PARAM_MANGO_SESSION_UID_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
--MEASURE
(84, 2, 1, NULL, 5, '0', 'state', 'state for circuit 0 Off, 1 On', 'LBL_ELEMENT_TYPE_PARAM_CIRCUIT_STATE' , 'LBL_ELEMENT_TYPE_PARAM_CIRCUIT_STATE_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(84, 2, 2, NULL, 3, NULL, 'level', 'device dimming level', 'LBL_ELEMENT_TYPE_PARAM_CIRCUIT_LEVEL' , 'LBL_ELEMENT_TYPE_PARAM_CIRCUIT_LEVEL_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


--
-- Table: master.element_type_params
--
INSERT INTO master.element_type_states(element_type_id, element_type_state_id, alias, description, label_alias, label_description, enabled, visible, created_at, updated_at) VALUES
(84, 0, 'UNKNOWN', 'Unknown state', 'LBL_ELEMENT_TYPE_STATE_UNKNOWN', 'LBL_ELEMENT_TYPE_STATE_UNKNOWN_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(84, 1, 'UP', 'The state of the equipment is: comunicates', 'LBL_ELEMENT_TYPE_STATE_UP', 'LBL_ELEMENT_TYPE_STATE_UP_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(84, 2, 'DOWN', 'The state of the equipment is: out of communication', 'LBL_ELEMENT_TYPE_STATE_DOWN', 'LBL_ELEMENT_TYPE_STATE_DOWN_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(84, 3, 'ALARMS', 'The state of the equipment is: with alarms', 'LBL_ELEMENT_TYPE_STATE_ALARMS', 'LBL_ELEMENT_TYPE_STATE_ALARMS_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(84, 4, 'MAINTENANCE', 'The state of the equipment is: on Maintenance', 'LBL_ELEMENT_TYPE_STATE_MAINTENANCE', 'LBL_ELEMENT_TYPE_STATE_MAINTENANCE_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: master.element_type_state_transitions
--
INSERT INTO master.element_type_state_transitions
(element_type_id, current_element_type_state_id, next_element_type_state_id, enabled, visible, editable, created_at, updated_at) VALUES
(84, 0, 1, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(84, 0, 2, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(84, 1, 2, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(84, 1, 3, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(84, 2, 1, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(84, 2, 3, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(84, 3, 1, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(84, 3, 2, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: master.command_element_types
--
INSERT INTO master.command_element_types
(command_element_type_id, element_type_id, alias, description, label_alias, label_description, on_change, enabled, visible, created_at, updated_at) VALUES
(1, 84, 'CIRCUIT OFF', 'Circuit off', 'LBL_COMMAND_ELEMENT_TYPE_CIRCUIT_OFF', null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 84, 'CIRCUIT ON', 'Cicuit on', 'LBL_COMMAND_ELEMENT_TYPE_CIRCUIT_ON', null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: master.io_controller_module_type_element_types
--
INSERT INTO master.io_controller_module_type_element_types
(module_type_id, element_type_id, enabled, visible, created_at, updated_at) VALUES
(1, 84, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: conf.alarm_configs
--
INSERT INTO conf.alarm_configs
(alarm_config_id, alarm_type_id, alarm_level_id, element_type_id, element_id, element_type_state_id, mine, treatment, alias, description, label_alias, label_description, enabled, visible, created_at, updated_at) VALUES
(010084, 2, 3, 84, NULL, NULL, true, true, 'ALARM CIRCUIT DISCONNECTED', 'ALARM CIRCUIT DISCONNECTED', 'LBL_ALARM_CONFIGS_ALARM_CIRCUIT_DISCONNECTED', 'LBL_ALARM_CONFIGS_ALARM_CIRCUIT_DISCONNECTED_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(020084, 2, 3, 84, NULL, 3, true, true, 'ALARM PROTECCION AUTOMATICO', 'ALARM PROTECCION AUTOMATICO', 'LBL_ALARM_CONFIGS_ALARM_PROT_AUT', 'LBL_ALARM_CONFIGS_ALARM_PROT_AUT_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(030084, 2, 3, 84, NULL, 3, true, true, 'ALARM FALLO HASTIAL', 'ALARM FALLO HASTIAL', 'LBL_ALARM_CONFIGS_ALARM_FALLO_HASTIAL', 'LBL_ALARM_CONFIGS_ALARM_FALLO_HASTIAL_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(040084, 2, 3, 84, NULL, 3, true, true, 'ALARM CIRCUITO SIN TENSION', 'ALARM CIRCUITO SIN TENSION', 'LBL_ALARM_CONFIGS_ALARM_CIRCUITO_SIN_TENSION', 'LBL_ALARM_CONFIGS_ALARM_CIRCUITO_SIN_TENSION_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(050084, 2, 3, 84, NULL, 3, true, true, 'ALARM CIRCUITO MANUAL', 'ALARM CIRCUITO MANUAL', 'LBL_ALARM_CONFIGS_ALARM_CIRCUITO_MANUAL', 'LBL_ALARM_CONFIGS_ALARM_CIRCUITO_MANUAL_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--luminarias jaen
(060084, 2, 3, 84, NULL, 3, true, true, 'ALARM FALLO CIRCUITO', 'ALARM FALLO CIRCUITO', 'LBL_ALARM_CONFIGS_ALARM_FALLO_CIRCUITO', 'LBL_ALARM_CONFIGS_ALARM_FALLO_CIRCUITO_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(070084, 2, 3, 84, NULL, 3, true, true, 'ALARM FALLO DURACION BATERIA', 'ALARM FALLO DURACION BATERIA', 'LBL_ALARM_CONFIGS_ALARM_BATTERY_DURATION_FAILURE', 'LBL_ALARM_CONFIGS_ALARM_BATTERY_DURATION_FAILURE_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(080084, 2, 3, 84, NULL, 3, true, true, 'ALARM FALLO BATERIA', 'ALARM FALLO BATERIA', 'LBL_ALARM_CONFIGS_ALARM_BATTERY_FAILURE', 'LBL_ALARM_CONFIGS_ALARM_BATTERY_FAILURE_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(090084, 2, 3, 84, NULL, 3, true, true, 'ALARM FALLO LAMPARA EMERGENCIA', 'ALARM FALLO LAMPARA EMERGENCIA', 'LBL_ALARM_CONFIGS_ALARM_EMERGENCY_LAMP_FAILURE', 'LBL_ALARM_CONFIGS_ALARM_EMERGENCY_LAMP_FAILURE_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(100084, 2, 3, 84, NULL, 3, true, true, 'ALARM FUNCTION TEST DELAY EXCEEDED', 'ALARM FUNCTION TEST DELAY EXCEEDED', 'LBL_ALARM_CONFIGS_ALARM_FUNCTION_TEST_DELAY_EXCEEDED', 'LBL_ALARM_CONFIGS_ALARM_FUNCTION_TEST_DELAY_EXCEEDED_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(110084, 2, 3, 84, NULL, 3, true, true, 'ALARM DURATION TEST DELAY EXCEEDED', 'ALARM DURATION TEST DELAY EXCEEDED', 'LBL_ALARM_CONFIGS_ALARM_DURATION_TEST_DELAY_EXCEEDED', 'LBL_ALARM_CONFIGS_ALARM_DURATION_TEST_DELAY_EXCEEDED_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(120084, 2, 3, 84, NULL, 3, true, true, 'ALARM FUNCTION TEST FAILED', 'ALARM FUNCTION TEST FAILED', 'LBL_ALARM_CONFIGS_ALARM_FUNCTION_TEST_FAILED', 'LBL_ALARM_CONFIGS_ALARM_FUNCTION_TEST_FAILED_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(130084, 2, 3, 84, NULL, 3, true, true, 'ALARM COMM FAILURE', 'ALARM COMM FAILURE', 'LBL_ALARM_CONFIGS_ALARM_COMM_FAILURE', 'LBL_ALARM_CONFIGS_ALARM_COMM_FAILURE_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(140084, 2, 3, 84, NULL, 3, true, true, 'ALARM LAMP FAILURE', 'ALARM LAMP FAILURE', 'LBL_ALARM_CONFIGS_ALARM_LAMP_FAILURE', 'LBL_ALARM_CONFIGS_ALARM_LAMP_FAILURE_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


-- 
-- Table: conf.element_state_alarms
--
INSERT INTO conf.element_state_alarms
(element_type_id, element_type_state_id, alarm_config_id, created_at, updated_at) VALUES
(84, 2, 010084, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
