-- 
-- Table: master.element_types
--
INSERT INTO master.element_types
(element_type_id, alias, description, label_alias, label_description, initial_element_type_state_id, connected_element_type_state_id, disconnected_element_type_state_id, enabled, visible, created_at, updated_at) VALUES
(11, 'SOS', 'SOS', 'LBL_ELEMENT_TYPE_SOS', 'LBL_ELEMENT_TYPE_SOS_DESC', 0, 1, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

--
-- Table: master.element_type_params
--
INSERT INTO master.element_type_params (element_type_id, param_type_id, element_type_param_id, element_type_param_unit_id, data_type_id, default_value, alias, description, label_alias, label_description, enabled, visible, editable, created_at, updated_at) VALUES
--CONFIG
(11,1,1, NULL,2, NULL, 'brand', 'brand', 'LBL_ELEMENT_TYPE_PARAM_BRAND' , 'LBL_ELEMENT_TYPE_PARAM_BRAND_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(11,1,2, NULL,2, NULL, 'model', 'model', 'LBL_ELEMENT_TYPE_PARAM_MODEL' , 'LBL_ELEMENT_TYPE_PARAM_MODEL_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(11,1,3, NULL,2, NULL, 'extension', 'extension', 'LBL_ELEMENT_TYPE_PARAM_EXTENSION' , 'LBL_ELEMENT_TYPE_PARAM_EXTENSION_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(11,1,1003, NULL,2, NULL, 'MANGO SESSION_UID', 'MANGO SESSION_UID', 'LBL_ELEMENT_TYPE_PARAM_MANGO_SESSION_UID' , 'LBL_ELEMENT_TYPE_PARAM_MANGO_SESSION_UID_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--MEASURE
(11,2,1,NULL,1, NULL, 'vol_mic', 'vol_mic', 'LBL_ELEMENT_TYPE_PARAM_VOL_MIC', 'LBL_ELEMENT_TYPE_PARAM_VOL_MIC_DESC', true, true, false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(11,2,3,NULL,5, NULL, 'sip_register', 'sip_register', 'LBL_ELEMENT_TYPE_PARAM_SIP_REGISTER', 'LBL_ELEMENT_TYPE_PARAM_SIP_REGISTER_DESC', true, true, false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(11,2,4,NULL,1, NULL, 'vol_audio', 'vol_audio', 'LBL_ELEMENT_TYPE_PARAM_VOL_AUDIO', 'LBL_ELEMENT_TYPE_PARAM_VOL_AUDIO_DESC', true, true, false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(11,2,5,NULL,5, NULL, 'test_audio', 'test_audio', 'LBL_ELEMENT_TYPE_PARAM_TEST_AUDIO', 'LBL_ELEMENT_TYPE_PARAM_TEST_AUDIO_DESC', true, true, false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(11,2,6,NULL,1, NULL, 'last_day_test', 'last_day_test', 'LBL_ELEMENT_TYPE_PARAM_LAST_DAY_TEST', 'LBL_ELEMENT_TYPE_PARAM_LAST_DAY_TEST_DESC', true, true, false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(11,2,7,NULL,1, NULL, 'last_month_test', 'last_month_test', 'LBL_ELEMENT_TYPE_PARAM_LAST_MONTH_TEST', 'LBL_ELEMENT_TYPE_PARAM_LAST_MONTH_TEST_DESC', true, true, false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(11,2,8,NULL,1, NULL, 'last_year_test', 'last_year_test', 'LBL_ELEMENT_TYPE_PARAM_LAST_YEAR_TEST', 'LBL_ELEMENT_TYPE_PARAM_LAST_YEAR_TEST_DESC', true, true, false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(11,2,9,NULL,1, NULL, 'last_hour_test', 'last_hour_test', 'LBL_ELEMENT_TYPE_PARAM_LAST_HOUR_TEST', 'LBL_ELEMENT_TYPE_PARAM_LAST_HOUR_TEST_DESC', true, true, false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(11,2,10,NULL,1, NULL, 'last_minute_test', 'last_minute_test', 'LBL_ELEMENT_TYPE_PARAM_LAST_MINUTE_TEST', 'LBL_ELEMENT_TYPE_PARAM_LAST_MINUTE_TEST_DESC', true, true, false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(11,2,11,NULL,1, NULL, 'last_second_test', 'last_second_test', 'LBL_ELEMENT_TYPE_PARAM_LAST_SECOND_TEST', 'LBL_ELEMENT_TYPE_PARAM_LAST_SECOND_TEST_DESC', true, true, false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(11,2,13,NULL,5, NULL, 'beacon_state', 'beacon', 'LBL_ELEMENT_TYPE_PARAM_BEACON', 'LBL_ELEMENT_TYPE_PARAM_BEACON_DESC', true, true, false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(11,2,14,NULL,5, NULL, 'foghorn_state', 'foghorn', 'LBL_ELEMENT_TYPE_PARAM_FOGHORN', 'LBL_ELEMENT_TYPE_PARAM_FOGHORN_DESC', true, true, false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: master.element_subtypes
--
INSERT INTO master.element_subtypes
(element_subtype_id, element_type_id, alias, description,label_alias, label_description, enabled, visible, created_at, updated_at)VALUES
(1, 11, 'SOS', 'SOS', 'LBL_ELEMENT_SUBTYPE_SOS', 'LBL_ELEMENT_SUBTYPE_SOS_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 11, 'SOS Modbus', 'SOS Modbus', 'LBL_ELEMENT_SUBTYPE_SOS_MODBUS', 'LBL_ELEMENT_SUBTYPE_SOS_MODBUS_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 11, 'SOS cabinet Modbus', 'SOS cabinet Modbus', 'LBL_ELEMENT_SUBTYPE_SOS_CABINET_MODBUS', 'LBL_ELEMENT_SUBTYPE_SOS_CABINET_MODBUS_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: master.element_subtype_active_params 
--
INSERT INTO master.element_subtype_active_params
(element_subtype_id, element_type_id, param_type_id, element_type_param_id, enabled, visible, created_at, updated_at)VALUES
(2, 11, 2, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 11, 2, 3, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 11, 2, 4, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 11, 2, 5, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 11, 2, 6, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 11, 2, 7, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 11, 2, 8, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 11, 2, 9, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 11, 2, 10, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 11, 2, 11, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(3, 11, 2, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 11, 2, 3, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 11, 2, 4, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 11, 2, 5, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 11, 2, 6, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 11, 2, 7, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 11, 2, 8, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 11, 2, 9, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 11, 2, 10, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 11, 2, 11, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 11, 2, 13, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 11, 2, 14, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: master.element_type_states
--
INSERT INTO master.element_type_states(element_type_id, element_type_state_id, alias, description, label_alias, label_description, enabled, visible, created_at, updated_at) VALUES
(11, 0, 'UNKNOWN', 'Unknown state', 'LBL_ELEMENT_TYPE_STATE_UNKNOWN', 'LBL_ELEMENT_TYPE_STATE_UNKNOWN_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(11, 1, 'UP', 'The state of the equipment is: comunicates', 'LBL_ELEMENT_TYPE_STATE_UP', 'LBL_ELEMENT_TYPE_STATE_UP_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(11, 2, 'DOWN', 'The state of the equipment is: out of communication', 'LBL_ELEMENT_TYPE_STATE_DOWN', 'LBL_ELEMENT_TYPE_STATE_DOWN_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(11, 3, 'ALARMS', 'The state of the equipment is: with alarms', 'LBL_ELEMENT_TYPE_STATE_ALARMS', 'LBL_ELEMENT_TYPE_STATE_ALARMS_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(11, 4, 'MAINTENANCE', 'The state of the equipment is: on Maintenance', 'LBL_ELEMENT_TYPE_STATE_MAINTENANCE', 'LBL_ELEMENT_TYPE_STATE_MAINTENANCE_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: master.element_type_state_transitions
--
INSERT INTO master.element_type_state_transitions
(element_type_id, current_element_type_state_id, next_element_type_state_id, enabled, visible, editable, created_at, updated_at) VALUES
(11, 0, 1, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(11, 0, 2, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(11, 1, 2, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(11, 1, 3, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(11, 2, 1, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(11, 2, 3, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(11, 3, 1, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(11, 3, 2, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: master.command_element_types
--

INSERT INTO master.command_element_types
(command_element_type_id, element_type_id, alias, description, label_alias, label_description, enabled, visible, created_at, updated_at) VALUES
(1, 11, 'TEST_AUDIO', 'TEST AUDIO', 'LBL_COMMAND_ELEMENT_TYPE_TEST_AUDIO', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 11, 'VOL_AUDIO', 'VOL AUDIO', 'LBL_COMMAND_ELEMENT_TYPE_VOL_AUDIO', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 11, 'VOL_MICRO', 'VOL MICRO', 'LBL_COMMAND_ELEMENT_TYPE_VOL_MICRO', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 11, 'TEST_LLAMADA', 'TEST LLAMADA', 'LBL_COMMAND_ELEMENT_TYPE_CALL_TEST', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


-- 
-- Table: master.command_element_type_params
--
INSERT INTO master.command_element_type_params
(command_element_type_id, element_type_id, command_element_type_param_id, data_type_id, alias, description, Label_alias, label_description, element_type_param_id, param_type_id, enabled, visible, created_at, updated_at) VALUES
(2, 11, 1, 1, 'VOL_AUDIO', 'vol audio', 'LBL_COMMAND_ELEMENT_TYPE_PARAM_VOL_AUDIO', null, 4, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 11, 2, 1, 'VOL_MICRO', 'vol micro', 'LBL_COMMAND_ELEMENT_TYPE_PARAM_VOL_MICRO', null, 1, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


-- 
-- Table: master.element_subtype_active_commands
--
INSERT INTO master.element_subtype_active_commands (element_subtype_id, command_element_type_id, element_type_id, enabled, visible, created_at, updated_at) VALUES
(2, 1, 11, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 2, 11, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 3, 11, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 4, 11, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 1, 11, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 2, 11, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 3, 11, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 4, 11, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);



-- 
-- Table: master.io_controller_module_type_element_types
--
INSERT INTO master.io_controller_module_type_element_types
(module_type_id, element_type_id, enabled, visible, created_at, updated_at) VALUES
(1, 11, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: master.element_hierarchies_allowed
INSERT INTO master.element_hierarchies_allowed
(parent_element_type_id, child_element_type_id, enabled, visible, created_at, updated_at) VALUES
(11, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: conf.alarm_configs
--
INSERT INTO conf.alarm_configs 
(alarm_config_id, alarm_type_id, alarm_level_id, element_type_id, element_id, element_type_state_id, mine, treatment, alias, description, label_alias, label_description, enabled, visible, created_at, updated_at)VALUES
--Comunes a todos los subtipos
(010011, 4, 3, 11, NULL, NULL, true, true, 'ALARM SOS NO CONNECTION', 'ALARM SOS NO CONNECTION', 'LBL_ALARM_CONFIGS_ALARM_SOS_NO_CONNECTION', 'LBL_ALARM_CONFIGS_ALARM_SOS_NO_CONNECTION_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- Subtipo SOS
(020011, 2, 3, 11, NULL, 3, true, true, 'ALARM SOS DOOR OPEN', 'ALARM SOS DOOR OPEN', 'LBL_ALARM_CONFIGS_ALARM_SOS_DOOR_OPEN', 'LBL_ALARM_CONFIGS_ALARM_SOS_DOOR_OPEN_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(030011, 2, 3, 11, NULL, 3, true, true, 'ALARM SOS FA BOARD', 'ALARM SOS FA BOARD', 'LBL_ALARM_CONFIGS_ALARM_SOS_FA_BOARD', 'LBL_ALARM_CONFIGS_ALARM_SOS_FA_BOARD_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- Subtipos SOS Modbus y SOS cabinet Modbus
(040011, 2, 3, 11, NULL, 3, true, true, 'ALARM SOS LEFT EXTINGUISHER DOOR OPEN', 'ALARM SOS LEFT EXTINGUISHER DOOR OPEN', 'LBL_ALARM_CONFIGS_ALARM_SOS_LEFT_EXTINGUISHER_DOOR_OPEN', 'LBL_ALARM_CONFIGS_ALARM_SOS_LEFT_EXTINGUISHER_DOOR_OPEN_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(050011, 2, 3, 11, NULL, 3, true, true, 'ALARM SOS RIGHT EXTINGUISHER DOOR OPEN', 'ALARM SOS RIGHT EXTINGUISHER DOOR OPEN', 'LBL_ALARM_CONFIGS_ALARM_SOS_RIGHT_EXTINGUISHER_DOOR_OPEN', 'LBL_ALARM_CONFIGS_ALARM_SOS_RIGHT_EXTINGUISHER_DOOR_OPEN_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(060011, 2, 3, 11, NULL, 3, true, true, 'ALARM SOS EXTINGUISHER DOOR OPEN', 'ALARM SOS EXTINGUISHER DOOR OPEN', 'LBL_ALARM_CONFIGS_ALARM_SOS_EXTINGUISHER_DOOR_OPEN', 'LBL_ALARM_CONFIGS_ALARM_SOS_EXTINGUISHER_DOOR_OPEN_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(070011, 2, 3, 11, NULL, 3, true, true, 'ALARM SOS EXTINGUISHER EXTRACTION', 'ALARM SOS EXTINGUISHER EXTRACTION', 'LBL_ALARM_CONFIGS_ALARM_SOS_EXTINGUISHER_EXTRACTION', 'LBL_ALARM_CONFIGS_ALARM_SOS_EXTINGUISHER_EXTRACTION_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(080011, 2, 5, 11, NULL, 3, true, true, 'ALARM SOS SIP', 'ALARM SOS SIP', 'LBL_ALARM_CONFIGS_ALARM_SOS_SIP', 'LBL_ALARM_CONFIGS_ALARM_SOS_SIP_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: conf.element_state_alarms
--
INSERT INTO conf.element_state_alarms
(element_type_id, element_type_state_id, alarm_config_id, created_at, updated_at) VALUES
(11, 2, 010011, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);