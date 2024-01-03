-- 
-- Table: master.element_types
--
INSERT INTO master.element_types(element_type_id, element_group_id, alias, description,label_alias, label_description, initial_element_type_state_id, connected_element_type_state_id, disconnected_element_type_state_id, check_if_connected, enabled, visible, created_at, updated_at) VALUES
(71, NULL, 'Panel', 'Panel', 'LBL_ELEMENT_TYPE_PANEL', 'LBL_ELEMENT_TYPE_PANEL_DESC', 0, 1, 2, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

--
-- Table: master.element_type_params
--
INSERT INTO master.element_type_params (element_type_id, param_type_id, element_type_param_id, element_type_param_unit_id, data_type_id, default_value, alias, description, label_alias, label_description, enabled, visible, editable, created_at, updated_at) VALUES
--CONFIG
(71,1,1, NULL,2, ' ', 'brand', 'brand', 'LBL_ELEMENT_TYPE_PARAM_BRAND' , 'LBL_ELEMENT_TYPE_PARAM_BRAND_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(71,1,2, NULL,2, ' ', 'model', 'model', 'LBL_ELEMENT_TYPE_PARAM_MODEL' , 'LBL_ELEMENT_TYPE_PARAM_MODEL_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(71,1,3, NULL,2, ' ', 'protocolo', 'protocolo', 'LBL_ELEMENT_TYPE_PARAM_PROTOCOL' , 'LBL_ELEMENT_TYPE_PARAM_PROTOCOL_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(71,1,4, NULL,2, ' ', 'config json', 'config json', 'LBL_ELEMENT_TYPE_PARAM_CONFIG' , 'LBL_ELEMENT_TYPE_PARAM_CONFIG_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(71,1,6, NULL,2, ' ', 'armario asociado', 'armario asociado', 'LBL_ELEMENT_TYPE_PARAM_ELECTRICAL_CABINET' , 'LBL_ELEMENT_TYPE_PARAM_ELECTRICAL_CABINET_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(71,1,7, NULL,2, ' ', 'CH', 'CH', 'LBL_ELEMENT_TYPE_PARAM_PK' , 'LBL_ELEMENT_TYPE_PARAM_PK_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(71,1,8, NULL,2, ' ', 'panel code', 'panel code', 'LBL_ELEMENT_TYPE_PARAM_PANEL_CODE' , 'LBL_ELEMENT_TYPE_PARAM_PANEL_CODE_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(71,1,9, NULL,5, '', 'travel_time_configurable', 'travel_time_configurable', 'LBL_ELEMENT_TYPE_PARAM_TRAVEL_TIME_CONFIGURABLE' , 'LBL_ELEMENT_TYPE_PARAM_TRAVEL_TIME_CONFIGURABLE_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(71,1,1003, NULL,2, '', 'MANGO SESSION_UID', 'MANGO SESSION_UID', 'LBL_ELEMENT_TYPE_PARAM_MANGO_SESSION_UID' , 'LBL_ELEMENT_TYPE_PARAM_MANGO_SESSION_UID_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
--MEASURE
(71,2,1,NULL,1, '', 'brightness', 'brightness', 'LBL_ELEMENT_TYPE_PARAM_BRIGHTNESS' , 'LBL_ELEMENT_TYPE_PARAM_BRIGHTNESS_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(71,2,2,8,1, '', 'temperature', 'temperature', 'LBL_ELEMENT_TYPE_PARAM_PANEL_TEMPERATURE' , 'LBL_ELEMENT_TYPE_PARAM_PANEL_TEMPERATURE_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(71,2,3,NULL,2, '', 'data_json', 'data_json', 'LBL_ELEMENT_TYPE_PARAM_PANEL_DATA' , 'LBL_ELEMENT_TYPE_PARAM_PANEL_DATA_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(71,2,4,NULL,1, '', 'actual_brightness_state', 'actual_brightness_state', 'LBL_ELEMENT_TYPE_PARAM_ACTUAL_BRIGHTNESS_STATE' , 'LBL_ELEMENT_TYPE_PARAM_ACTUAL_BRIGHTNESS_STATE_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(71,2,5,NULL,1, '', 'beacons', 'beacons', 'LBL_ELEMENT_TYPE_PARAM_ACTUAL_BEACONS' , 'LBL_ELEMENT_TYPE_PARAM_ACTUAL_BEACONS_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(71,2,6,NULL,1, '', 'pixel_service', 'pixel_service', 'LBL_ELEMENT_TYPE_PARAM_ACTUAL_PIXEL_SERVICE' , 'LBL_ELEMENT_TYPE_PARAM_ACTUAL_PIXEL_SERVICE_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(71,2,7,NULL,5, 'true', 'enabled', 'enabled', 'LBL_ELEMENT_TYPE_PARAM_ENABLED' , 'LBL_ELEMENT_TYPE_PARAM_ENABLED_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(71,2,8,NULL,2, '', 'rate', 'rate', 'LBL_ELEMENT_TYPE_PARAM_RATE' , 'LBL_ELEMENT_TYPE_PARAM_RATE_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(71,2,9,NULL,2, '', 'crc', 'crc', 'LBL_ELEMENT_TYPE_PARAM_CRC' , 'LBL_ELEMENT_TYPE_PARAM_CRC_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(71,2,10,NULL,5, '', 'travel_time_enabled', 'travel_time_enabled', 'LBL_ELEMENT_TYPE_PARAM_TRAVEL_TIME_ENABLED' , 'LBL_ELEMENT_TYPE_PARAM_TRAVEL_TIME_ENABLED_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(71,2,11,NULL,1, '0', 'priority', 'priority', 'LBL_ELEMENT_TYPE_PARAM_PRIORITY' , 'LBL_ELEMENT_TYPE_PARAM_TRAVEL_TIME_ENABLED_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(71,2,1001,NULL,2, '', 'graphic_conversion', 'provisional data conversion', 'LBL_ELEMENT_TYPE_PARAM_PROVISIONAL_DATA' , 'LBL_ELEMENT_TYPE_PARAM_PROVISIONAL_DATA_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: master.element_type_states
--
INSERT INTO master.element_type_states(element_type_id, element_type_state_id, alias, description, label_alias, label_description, enabled, visible, created_at, updated_at) VALUES
(71, 0, 'UNKNOWN', 'Unknown state', 'LBL_ELEMENT_TYPE_STATE_UNKNOWN', 'LBL_ELEMENT_TYPE_STATE_UNKNOWN_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(71, 1, 'UP', 'The state of the equipment is: comunicates', 'LBL_ELEMENT_TYPE_STATE_UP', 'LBL_ELEMENT_TYPE_STATE_UP_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(71, 2, 'DOWN', 'The state of the equipment is: out of communication', 'LBL_ELEMENT_TYPE_STATE_DOWN', 'LBL_ELEMENT_TYPE_STATE_DOWN_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(71, 3, 'ALARMS', 'The state of the equipment is: with alarms', 'LBL_ELEMENT_TYPE_STATE_ALARMS', 'LBL_ELEMENT_TYPE_STATE_ALARMS_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(71, 4, 'MAINTENANCE', 'The state of the equipment is: on Maintenance', 'LBL_ELEMENT_TYPE_STATE_MAINTENANCE', 'LBL_ELEMENT_TYPE_STATE_MAINTENANCE_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

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
(71, 72, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(71, 4, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(71, 6, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: master.command_element_types
--
INSERT INTO master.command_element_types
(command_element_type_id, element_type_id, alias, description, label_alias, label_description, enabled, visible, created_at, updated_at) VALUES
(1, 71, 'RESET', 'RESET', 'LBL_COMMAND_ELEMENT_TYPE_RESET', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 71, 'SWITCH ON', 'SWITCH ON', 'LBL_COMMAND_ELEMENT_TYPE_PANEL_SWITCH_ON', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 71, 'BRIGHTNESS', 'BRIGHTNESS', 'LBL_COMMAND_ELEMENT_TYPE_BRIGHTNESS', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(6, 71, 'TEXT PIXEL', 'TEXT PIXEL', 'LBL_COMMAND_ELEMENT_TYPE_TEXT_PIXEL', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(7, 71, 'AUTOMATIC BRIGHTNESS', 'AUTOMATIC BRIGHTNESS', 'LBL_COMMAND_ELEMENT_TYPE_AUTOMATIC_BRIGHTNESS', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(8, 71, 'ENABLE TRAVEL TIME', 'ENABLE TRAVEL TIME', 'LBL_COMMAND_ELEMENT_TYPE_ENABLE_TRAVEL_TIME', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(9, 71, 'DISABLE TRAVEL TIME', 'DISABLE TRAVEL TIME', 'LBL_COMMAND_ELEMENT_TYPE_DISABLE_TRAVEL_TIME', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


INSERT INTO master.command_element_types
(command_element_type_id, element_type_id, alias, description, label_alias, label_description, pm_queue_compatibility, enabled, visible, created_at, updated_at) VALUES
(2, 71, 'SEND MESSAGE', 'SEND MESSAGE', 'LBL_COMMAND_ELEMENT_TYPE_SEND_MESSAGE', null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 71, 'SWITCH OFF', 'SWITCH OFF', 'LBL_COMMAND_ELEMENT_TYPE_PANEL_SWITCH_OFF', null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


INSERT INTO master.command_element_type_params
(command_element_type_id, element_type_id, command_element_type_param_id, data_type_id, alias, description, Label_alias, label_description, element_type_param_id, param_type_id, enabled, visible, created_at, updated_at) VALUES
(2, 71, 1, 2, 'JSON_VALUE', 'JSON_VALUE', 'LBL_COMMAND_ELEMENT_TYPE_PARAM_SEND_MESSAGE_JSON_VALUE', null, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 71, 1, 1, 'BRIGHTNESS_VALUE', 'BRIGHTNESS_VALUE', 'LBL_COMMAND_ELEMENT_TYPE_PARAM_SEND_MESSAGE_BRIGHTNESS_VALUE', null, 1, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 71, 2, 2, 'PRIORITY_VALUE', 'PRIORITY_VALUE', 'LBL_COMMAND_ELEMENT_TYPE_PARAM_SET_PRIORITY_VALUE', null, 11, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


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
(010071, 4, 6, 71, NULL, NULL, true, true, 'ALARM PMV NO CONNECTION', 'ALARM PMV NO CONNECTION', 'LBL_ALARM_CONFIGS_ALARM_PMV_NO_CONNECTION', 'LBL_ALARM_CONFIGS_ALARM_PMV_NO_CONNECTION_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(020071, 2, 5, 71, NULL, 3, true, true, 'ALARM PMV DOOR OPEN', 'ALARM PMV DOOR OPEN', 'LBL_ALARM_CONFIGS_ALARM_PMV_DOOR_OPEN', 'LBL_ALARM_CONFIGS_ALARM_PMV_DOOR_OPEN_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(030071, 4, 3, 71, NULL, 3, true, true, 'ALARM PMV DISPLAY ERROR', 'ALARM PMV DISPLAY ERROR', 'LBL_ALARM_CONFIGS_ALARM_PMV_DISPLAY_ERROR', 'LBL_ALARM_CONFIGS_ALARM_PMV_DISPLAY_ERROR_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(040071, 2, 3, 71, NULL, 3, true, true, 'ALARM PMV PIXEL ERROR', 'ALARM PMV PIXEL ERROR', 'LBL_ALARM_CONFIGS_ALARM_PMV_PIXEL_ERROR', 'LBL_ALARM_CONFIGS_ALARM_PMV_PIXEL_ERROR_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(050071, 2, 5, 71, NULL, 3, true, false, 'ALARM PMV PHOTOCELL ERROR', 'ALARM PMV PHOTOCELL ERROR','LBL_ALARM_CONFIGS_ALARM_PMV_PHOTOCELL_ERROR', 'LBL_ALARM_CONFIGS_ALARM_PMV_PHOTOCELL_ERROR', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(060071, 2, 5, 71, NULL, 3, true, false, 'ALARM PMV WARNING TEMPERATURE ERROR', 'ALARM PMV WARNING TEMPERATURE WARNING ERROR', 'LBL_ALARM_CONFIGS_ALARM_PMV_WARNING_TEMPERATURE_ERROR', 'LBL_ALARM_CONFIGS_ALARM_PMV_WARNING_TEMPERATURE_ERROR', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(070071, 2, 5, 71, NULL, 3, true, false, 'ALARM PMV CRITICAL TEMPERATURE ERROR', 'ALARM PMV CRITICAL TEMPERATURE ERROR', 'LBL_ALARM_CONFIGS_ALARM_PMV_CRITICAL_TEMPERATURE_ERROR', 'LBL_ALARM_CONFIGS_ALARM_PMV_CRITICAL_TEMPERATURE_ERROR', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: conf.element_state_alarms
--
INSERT INTO conf.element_state_alarms
(element_type_id, element_type_state_id, alarm_config_id, created_at, updated_at) VALUES
(71, 2, 010071, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

