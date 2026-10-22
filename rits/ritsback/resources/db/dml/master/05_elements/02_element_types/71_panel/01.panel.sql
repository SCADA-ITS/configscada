-- 
-- Table: master.element_types
--
INSERT INTO master.element_types(element_type_id, element_group_id, alias, description,label_alias, label_description, initial_element_type_state_id, connected_element_type_state_id, disconnected_element_type_state_id, check_if_connected, enabled, visible, created_at, updated_at) VALUES
(71, NULL, 'Panel', 'Panel', 'LBL_ELEMENT_TYPE_PANEL', 'LBL_ELEMENT_TYPE_PANEL', 0, 1, 2, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

--
-- Table: master.element_type_params
--
INSERT INTO master.element_type_params (element_type_id, param_type_id, element_type_param_id, element_type_param_unit_id, data_type_id, default_value, alias, description, label_alias, label_description, enabled, visible, editable, created_at, updated_at) VALUES
(71,1,1, NULL,2, ' ', 'brand', 'brand', 'LBL_ELEMENT_TYPE_PARAM_BRAND' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(71,1,2, NULL,2, ' ', 'model', 'model', 'LBL_ELEMENT_TYPE_PARAM_MODEL' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(71,1,3, NULL,2, ' ', 'protocolo', 'protocolo', 'LBL_PROTOCOL' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(71,1,4, NULL,2, ' ', 'config json', 'config json', 'LBL_CONFIG' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(71,1,5, 1,2, ' ', 'entrance_tunnel_distance', 'entrance_tunnel_distance', 'LBL_ELEMENT_TYPE_PARAM_ENTRANCE_TUNNEL_DISTANCE' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(71,1,6, NULL,2, ' ', 'armario asociado', 'armario asociado', 'LBL_ELECTRICAL_CABINET' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(71,1,7, NULL,2, ' ', 'CH', 'CH', 'LBL_ELEMENT_TYPE_PARAM_PK' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(71,1,8, NULL,2, ' ', 'panel code', 'panel code', 'LBL_ELEMENT_TYPE_PARAM_PANEL_CODE' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(71,1,1003, NULL,2, '', 'MANGO SESSION_UID', 'MANGO SESSION_UID', 'LBL_ELEMENT_TYPE_PARAM_MANGO_SESSION_UID' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(71,2,1,NULL,1, '', 'brightness', 'brightness', 'LBL_ELEMENT_TYPE_PARAM_BRIGHTNESS' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(71,2,2,8,1, '', 'temperature', 'temperature', 'LBL_ELEMENT_TYPE_PARAM_TEMPERATURE' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(71,2,3,NULL,2, '', 'data_json', 'data_json', 'LBL_DATA' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(71,2,4,NULL,1, '', 'actual_brightness_state', 'actual_brightness_state', 'LBL_ELEMENT_TYPE_PARAM_ACTUAL_BRIGHTNESS_STATE' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(71,2,5,NULL,1, '', 'beacons', 'beacons', 'LBL_ELEMENT_TYPE_PARAM_ACTUAL_BEACONS' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(71,2,6,NULL,1, '', 'pixel_service', 'pixel_service', 'LBL_ELEMENT_TYPE_PARAM_ACTUAL_PIXEL_SERVICE' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(71,2,7,NULL,5, '', 'enabled', 'enabled', 'LBL_ELEMENT_TYPE_PARAM_ENABLED' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(71,2,8,NULL,2, '', 'rate', 'rate', 'LBL_ELEMENT_TYPE_PARAM_RATE' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(71,2,9,NULL,2, '', 'crc', 'crc', 'LBL_ELEMENT_TYPE_PARAM_CRC' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(71,2,1001,NULL,2, '', 'graphic_conversion', 'provisional data conversion', 'LBL_DATA' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: master.element_type_states
--
INSERT INTO master.element_type_states(element_type_id, element_type_state_id, alias, description, label_alias, label_description, enabled, visible, created_at, updated_at) VALUES
(71, 0, 'UNKNOWN', 'Unknown state', 'LBL_ELEMENT_TYPE_STATE_UNKNOWN', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(71, 1, 'UP', 'The state of the equipment is: comunicates', 'LBL_ELEMENT_TYPE_STATE_UP', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(71, 2, 'DOWN', 'The state of the equipment is: out of communication', 'LBL_ELEMENT_TYPE_STATE_DOWN', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(71, 3, 'ALARMS', 'The state of the equipment is: with alarms', 'LBL_ELEMENT_TYPE_STATE_ALARMS', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: master.element_type_state_transitions
--
INSERT INTO master.element_type_state_transitions (element_type_id, current_element_type_state_id, next_element_type_state_id, enabled, visible, editable, created_at, updated_at) VALUES
(71, 1, 2, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(71, 1, 3, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(71, 2, 1, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(71, 2, 3, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(71, 3, 1, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(71, 3, 2, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(71, 0, 1, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(71, 0, 2, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: master.element_hierarchies_allowed
--
INSERT INTO master.element_hierarchies_allowed
(parent_element_type_id, child_element_type_id, enabled, visible, created_at, updated_at) VALUES
(71, 72, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: master.command_element_types
--
INSERT INTO master.command_element_types
(command_element_type_id, element_type_id, alias, description, label_alias, label_description, enabled, visible, created_at, updated_at) VALUES
(1, 71, 'RESET', 'RESET', 'LBL_COMMAND_ELEMENT_TYPE_RESET', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 71, 'SWITCH OFF', 'SWITCH OFF', 'LBL_COMMAND_ELEMENT_TYPE_SWITCH_OFF', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 71, 'SWITCH ON', 'SWITCH ON', 'LBL_COMMAND_ELEMENT_TYPE_SWITCH_ON', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 71, 'BRIGHTNESS', 'BRIGHTNESS', 'LBL_COMMAND_ELEMENT_TYPE_BRIGHTNESS', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(6, 71, 'TEXT PIXEL', 'TEXT PIXEL', 'LBL_COMMAND_ELEMENT_TYPE_TEXT_PIXEL', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(7, 71, 'AUTOMATIC BRIGHTNESS', 'AUTOMATIC BRIGHTNESS', 'LBL_COMMAND_ELEMENT_TYPE_AUTOMATIC_BRIGHTNESS', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


INSERT INTO master.command_element_types
(command_element_type_id, element_type_id, alias, description, label_alias, label_description, pm_queue_compatibility, enabled, visible, created_at, updated_at) VALUES
(2, 71, 'SEND MESSAGE', 'SEND MESSAGE', 'LBL_COMMAND_ELEMENT_TYPE_SEND_MESSAGE', null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


INSERT INTO master.command_element_type_params
(command_element_type_id, element_type_id, command_element_type_param_id, data_type_id, alias, description, Label_alias, label_description, element_type_param_id, param_type_id, enabled, visible, created_at, updated_at) VALUES
(2, 71, 1, 2, 'JSON_VALUE', 'JSON_VALUE', 'LBL_COMMAND_SEND_MESSAGE_TYPE_PARAM_JSON_VALUE', null, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 71, 1, 1, 'BRIGHTNESS_VALUE', 'BRIGHTNESS_VALUE', 'LBL_COMMAND_SEND_MESSAGE_TYPE_PARAM_BRIGHTNESS_VALUE', null, 1, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


-- 
-- Table: master.io_controller_module_type_element_types
--
INSERT INTO master.io_controller_module_type_element_types
(module_type_id, element_type_id, enabled, visible, created_at, updated_at) VALUES
(1, 71, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


--
-- Table: conf.alarm_configs
--
INSERT INTO conf.alarm_configs 
(alarm_config_id, alarm_type_id, alarm_level_id, element_type_id, element_id, element_type_state_id, mine, treatment, alias, description, label_alias, label_description, enabled, visible, created_at, updated_at)VALUES
(336, 4, 6, 71, NULL, NULL, true, true, 'ALARM PMV NO CONNECTION', 'ALARM PMV NO CONNECTION', 'LBL_ALARM_CONFIGS_ALARM_PMV_NO_CONNECTION', 'LBL_ALARM_CONFIGS_ALARM_PMV_NO_CONNECTION_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(337, 2, 5, 71, NULL, 3, true, true, 'ALARM PMV DOOR OPEN', 'ALARM PMV DOOR OPEN', 'LBL_ALARM_CONFIGS_ALARM_PMV_DOOR_OPEN', 'LBL_ALARM_CONFIGS_ALARM_PMV_DOOR_OPEN_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(338, 4, 3, 71, NULL, 3, true, true, 'ALARM PMV DISPLAY ERROR', 'ALARM PMV DISPLAY ERROR', 'LBL_ALARM_CONFIGS_ALARM_PMV_DISPLAY_ERROR', 'LBL_ALARM_CONFIGS_ALARM_PMV_DISPLAY_ERROR_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(340, 4, 3, 71, NULL, 3, true, true, 'ALARM PMV PIXEL TEST BOARD FAIL 0', 'ALARM PMV PIXEL TEST BOARD FAIL 0', 'LBL_ALARM_CONFIGS_ALARM_PMV_PIXEL_TEST_BOARD_FAIL_0', 'LBL_ALARM_CONFIGS_ALARM_PMV_PIXEL_TEST_BOARD_FAIL_0_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(341, 4, 3, 71, NULL, 3, true, true, 'ALARM PMV PIXEL TEST BOARD FAIL 1', 'ALARM PMV PIXEL TEST BOARD FAIL 1', 'LBL_ALARM_CONFIGS_ALARM_PMV_PIXEL_TEST_BOARD_FAIL_1', 'LBL_ALARM_CONFIGS_ALARM_PMV_PIXEL_TEST_BOARD_FAIL_1_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(342, 4, 3, 71, NULL, 3, true, true, 'ALARM PMV PIXEL TEST BOARD FAIL 2', 'ALARM PMV PIXEL TEST BOARD FAIL 2', 'LBL_ALARM_CONFIGS_ALARM_PMV_PIXEL_TEST_BOARD_FAIL_2', 'LBL_ALARM_CONFIGS_ALARM_PMV_PIXEL_TEST_BOARD_FAIL_2_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(343, 4, 3, 71, NULL, 3, true, true, 'ALARM PMV PIXEL TEST BOARD FAIL 3', 'ALARM PMV PIXEL TEST BOARD FAIL 3', 'LBL_ALARM_CONFIGS_ALARM_PMV_PIXEL_TEST_BOARD_FAIL_3', 'LBL_ALARM_CONFIGS_ALARM_PMV_PIXEL_TEST_BOARD_FAIL_3_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(344, 4, 3, 71, NULL, 3, true, true, 'ALARM PMV PIXEL TEST BOARD FAIL 4', 'ALARM PMV PIXEL TEST BOARD FAIL 4', 'LBL_ALARM_CONFIGS_ALARM_PMV_PIXEL_TEST_BOARD_FAIL_4', 'LBL_ALARM_CONFIGS_ALARM_PMV_PIXEL_TEST_BOARD_FAIL_4_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(345, 4, 3, 71, NULL, 3, true, true, 'ALARM PMV PIXEL TEST BOARD FAIL 5', 'ALARM PMV PIXEL TEST BOARD FAIL 5', 'LBL_ALARM_CONFIGS_ALARM_PMV_PIXEL_TEST_BOARD_FAIL_5', 'LBL_ALARM_CONFIGS_ALARM_PMV_PIXEL_TEST_BOARD_FAIL_5_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(71349, 2, 3, 71, NULL, 3, true, true, 'ALARM PMV OVERTEMPERATURE', 'ALARM PMV OVERTEMPERATURE', 'LBL_ALARM_CONFIGS_ALARM_PMV_OVERTEMPERATURE', 'LBL_ALARM_CONFIGS_ALARM_PMV_OVERTEMPERATURE_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(71350, 2, 3, 71, NULL, 3, true, true, 'ALARM PMV PIXEL ERROR', 'ALARM PMV PIXEL ERROR', 'LBL_ALARM_CONFIGS_ALARM_PMV_PIXEL_ERROR', 'LBL_ALARM_CONFIGS_ALARM_PMV_PIXEL_ERROR_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(71351, 2, 3, 71, NULL, 3, true, true, 'ALARM PMV LUXES SENSOR ERROR', 'ALARM PMV LUXES SENSOR ERROR', 'LBL_ALARM_CONFIGS_ALARM_PMV_LUXES_SENSOR_ERROR', 'LBL_ALARM_CONFIGS_ALARM_PMV_LUXES_SENSOR_ERROR_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(71353, 2, 3, 71, NULL, 3, true, true, 'ALARM PMV IMAGE MODIFICATION STATE', 'ALARM PMV IMAGE MODIFICATION STATE', 'LBL_ALARM_CONFIGS_ALARM_PMV_IMAGE_MODIFICATION_STATE', 'LBL_ALARM_CONFIGS_ALARM_PMV_IMAGE_MODIFICATION_STATE_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(71346, 2, 5, 71, NULL, 3, true, false, 'ALARM VMS PHOTOCELL ERROR', 'ALARM VMS PHOTOCELL ERROR','LBL_ALARM_CONFIGS_PMV_PHOTOCELL_ERROR', 'LBL_ALARM_CONFIGS_PMV_PHOTOCELL_ERROR', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(71347, 2, 5, 71, NULL, 3, true, false, 'ALARM VMS WARNING TEMPERATURE ERROR', 'ALARM VMS WARNING TEMPERATURE WARNING ERROR', 'LBL_ALARM_CONFIGS_WARNING_TEMPERATURE_ERROR', 'LBL_ALARM_CONFIGS_WARNING_TEMPERATURE_ERROR', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(71348, 2, 5, 71, NULL, 3, true, false, 'ALARM VMS CRITICAL TEMPERATURE ERROR', 'ALARM VMS CRITICAL TEMPERATURE ERROR', 'LBL_ALARM_CONFIGS_CRITICAL_TEMPERATURE_ERROR', 'LBL_ALARM_CONFIGS_CRITICAL_TEMPERATURE_ERROR', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(375, 2, 5, 71, NULL, 3, true, true, 'ALARM VMS INTERNAL POWER FAULT', 'ALARM VMS INTERNAL POWER FAULT', 'LBL_ALARM_CONFIGS_ALARM_VMS_INTERNAL_POWER_FAULT', 'LBL_ALARM_CONFIGS_ALARM_VMS_INTERNAL_POWER_FAULT',true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(376, 2, 5, 71, NULL, 3, true, true, 'ALARM VMS EXTERNAL COMMS FAULT', 'ALARM VMS EXTERNAL COMMS FAULT', 'LBL_ALARM_CONFIGS_ALARM_VMS_EXTERNAL_COMMS_FAULT', 'LBL_ALARM_CONFIGS_ALARM_VMS_EXTERNAL_COMMS_FAULT',true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(377, 2, 5, 71, NULL, 3, true, true, 'ALARM VMS MESSAGE FAULT', 'ALARM VMS MESSAGE FAULT', 'LBL_ALARM_CONFIGS_ALARM_VMS_MESSAGE_FAULT', 'LBL_ALARM_CONFIGS_ALARM_VMS_EXTERNAL_COMMS_FAULT',true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(378, 2, 5, 71, NULL, 3, true, true, 'ALARM VMS CONTROLLER FAULT', 'ALARM VMS CONTROLLER FAULT', 'LBL_ALARM_CONFIGS_ALARM_VMS_CONTROLLER_FAULT', 'LBL_ALARM_CONFIGS_ALARM_VMS_EXTERNAL_COMMS_FAULT',true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(379, 2, 5, 71, NULL, 3, true, true, 'ALARM VMS TEMPERATURE WARNING', 'ALARM VMS TEMPERATURE WARNING', 'LBL_ALARM_CONFIGS_ALARM_VMS_TEMPERATURE_WARNING', 'LBL_ALARM_CONFIGS_ALARM_VMS_EXTERNAL_COMMS_FAULT',true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(380, 2, 5, 71, NULL, 3, true, true, 'ALARM VMS FAN FAULT', 'ALARM VMS FAN FAULT', 'LBL_ALARM_CONFIGS_ALARM_VMS_FAN_FAULT', 'LBL_ALARM_CONFIGS_ALARM_VMS_EXTERNAL_COMMS_FAULT',true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(381, 2, 5, 71, NULL, 3, true, true, 'ALARM VMS OTHER FAULT', 'ALARM VMS OTHER FAULT', 'LBL_ALARM_CONFIGS_ALARM_VMS_OTHER_FAULT', 'LBL_ALARM_CONFIGS_ALARM_VMS_OTHER_FAULT',true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);



-- 
-- Table: conf.element_state_alarms
--
INSERT INTO conf.element_state_alarms
(element_type_id, element_type_state_id, alarm_config_id, created_at, updated_at) VALUES
(71, 2, 336, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

