-- 
-- Table: master.element_types
--
INSERT INTO master.element_types(element_type_id, alias, description, label_alias, label_description, initial_element_type_state_id, connected_element_type_state_id, disconnected_element_type_state_id, enabled, visible, created_at, updated_at)
VALUES(2, 'CAMERA', 'Camera','LBL_ELEMENT_TYPE_CAMERA', 'LBL_ELEMENT_TYPE_CAMERA_DESC', 0, 1, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

--
-- Table: master.element_type_params
--
INSERT INTO master.element_type_params (element_type_id, param_type_id, element_type_param_id, element_type_param_unit_id, data_type_id, default_value, alias, description, label_alias, label_description, enabled, visible, editable, created_at, updated_at) VALUES
(2,1,1, NULL,2, ' ', 'brand', 'brand', 'LBL_ELEMENT_TYPE_PARAM_BRAND' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2,1,2, NULL,2, ' ', 'model', 'model', 'LBL_ELEMENT_TYPE_PARAM_MODEL' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2,1,3, NULL,2, ' ', 'stream_mjpeg', 'stream mjpeg', 'LBL_ELEMENT_TYPE_PARAM_STREAM_MJPEG' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2,1,4, NULL,2, ' ', 'selection_code_keyboard', 'selection_code_keyboard', 'LBL_ELEMENT_TYPE_PARAM_SELECTION CODE KEYBOARD' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2,1,5, NULL,2, ' ', 'associated_cabinet', 'associated_cabinet', 'LBL_ELEMENT_TYPE_PARAM_ASSOCIATED_CABINET' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2,1,6, NULL,2, ' ', 'preset_config_json', 'preset config json', 'LBL_ELEMENT_TYPE_PARAM_PRESET_CONFIG_JSON' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2,1,7, NULL,2, ' ', 'features (PTZ)', 'features (Config with chars PTZ)', 'LBL_ELEMENT_TYPE_PARAM_FEATURES_CONFIG_JSON' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2,1,8, 1, 2, ' ', 'entrance_tunnel_distance', 'entrance_tunnel_distance', 'LBL_ELEMENT_TYPE_PARAM_ENTRANCE_TUNNEL_DISTANCE' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2,1,9, NULL,2, NULL, 'onvif_hostname', 'Hostname for onvif protoco', 'LBL_ELEMENT_TYPE_PARAM_ONVIF_HOSTNAME' , null, true, true, false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2,1,10, NULL,2, NULL, 'onvif_username', 'User name for onvif protoco', 'LBL_ELEMENT_TYPE_PARAM_ONVIF_USERNAME' , null, true, true, false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2,1,11, NULL,2, NULL, 'onvif_password', 'Password for onvif protoco', 'LBL_ELEMENT_TYPE_PARAM_ONVIF_PASSWORD' , null, true, true, false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2,1,12, NULL,2, NULL, 'onvif_ptz_params', 'Params for onvif ptz control', 'LBL_ELEMENT_TYPE_PARAM_ONVIF_PTZ_PARAMS' , null, true, true, false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2,1,13, NULL,2, ' ', 'road', 'road', 'LBL_ELEMENT_TYPE_PARAM_ROAD' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2,1,14, NULL,2, ' ', 'alias_section', 'alias_section', 'LBL_ELEMENT_TYPE_PARAM_ALIAS_SECTION' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2,1,15, NULL,1, '', 'selection code keyboard', 'selection code keyboard', 'LBL_ELEMENT_TYPE_PARAM_SELECTION CODE KEYBOARD' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2,1,16, NULL,2, '', 'Location', 'Location', 'LBL_ELEMENT_TYPE_PARAM_LOCATION' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2,1,17, NULL,2, '', 'CH', 'CH', 'LBL_ELEMENT_TYPE_PARAM_PK' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2,2,1, NULL,2, NULL, 'image', 'image', 'LBL_ELEMENT_TYPE_PARAM_IMAGE' , null, true, true, false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2,2,2, NULL,2, NULL, 'video', 'video', 'LBL_ELEMENT_TYPE_PARAM_VIDEO' , null, true, true, false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2,2,3, NULL,2, NULL, 'id_incident', 'id_incident', 'LBL_ELEMENT_TYPE_PARAM_ID_INCIDENT' , null, true, true, false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2,2,4, NULL,2, NULL, 'place', 'place', 'LBL_ELEMENT_TYPE_PARAM_PLACE' , null, true, true, false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2,2,5, NULL,2, NULL, 'type_incident', 'type_incident', 'LBL_ELEMENT_TYPE_TYPE_INCIDENT' , null, true, true, false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2,1,1003, NULL,2, ' ', 'MANGO SESSION_UID', 'MANGO SESSION_UID', 'LBL_ELEMENT_TYPE_PARAM_MANGO SESSION_UID' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: master.element_subtypes
--
INSERT INTO master.element_subtypes(element_subtype_id, element_type_id, alias, description,label_alias, label_description, enabled, visible, created_at, updated_at)
VALUES
(1, 2, 'Camera', 'Camera', 'LBL_ELEMENT_SUBTYPE_CAMERA', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 2, 'Camera DAI', 'Camera DAI', 'LBL_ELEMENT_SUBTYPE_CAMERA_DAI', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 2, 'Camera DOMO_MODBUS', 'Camera DOMO Modbus', 'LBL_ELEMENT_SUBTYPE_CAMERA_DOMO_MODBUS', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 2, 'Camera DAI_MODBUS', 'Camera DAI Modbus', 'LBL_ELEMENT_SUBTYPE_CAMERA_DAI_MODBUS', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 2, 'Camera Presets', 'Camera Presets', 'LBL_ELEMENT_SUBTYPE_CAMERA_PRESETS', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: master.element_subtype_active_params
--
INSERT INTO master.element_subtype_active_params
(element_subtype_id, element_type_id, param_type_id, element_type_param_id, enabled, visible, created_at, updated_at)VALUES
-- CAMERA DAI
(2, 2, 2, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 2, 2, 3, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 2, 2, 4, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 2, 2, 5, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: master.element_type_states
--
INSERT INTO master.element_type_states(element_type_id, element_type_state_id, alias, description, label_alias, label_description, enabled, visible, created_at, updated_at) VALUES
(2, 0, 'UNKNOWN', 'Unknown state', 'LBL_ELEMENT_TYPE_STATE_UNKNOWN', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 1, 'UP', 'The state of the equipment is: comunicates', 'LBL_ELEMENT_TYPE_STATE_UP', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 2, 'DOWN', 'The state of the equipment is: out of communication', 'LBL_ELEMENT_TYPE_STATE_DOWN', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 3, 'ALARMS', 'The state of the equipment is: with alarms', 'LBL_ELEMENT_TYPE_STATE_ALARMS', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: master.element_type_state_transitions
--
INSERT INTO master.element_type_state_transitions 
(element_type_id, current_element_type_state_id, next_element_type_state_id, enabled, visible, editable, created_at, updated_at) VALUES
(2, 0, 1, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 0, 2, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 1, 2, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 1, 3, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 2, 1, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 2, 3, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 3, 1, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 3, 2, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: master.element_hierarchies_allowed
--
INSERT INTO master.element_hierarchies_allowed
(parent_element_type_id, child_element_type_id, enabled, visible, created_at, updated_at) VALUES
(2, 52, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: master.io_controller_module_type_element_types
--
INSERT INTO master.io_controller_module_type_element_types
(module_type_id, element_type_id, enabled, visible, created_at, updated_at) VALUES
(1, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: master.command_element_types
--
INSERT INTO master.command_element_types
(command_element_type_id, element_type_id, alias, description, label_alias, label_description, enabled, visible, created_at, updated_at) VALUES
(1, 2, 'LOCK CAMERA', 'LOCK CAMERA', 'LBL_COMMAND_ELEMENT_TYPE_CAMERA_LOCK', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 2, 'PRESET', 'PRESET', 'LBL_COMMAND_ELEMENT_TYPE_CAMERA_PRESET', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 2, 'PTZ', 'PTZ', 'LBL_COMMAND_ELEMENT_TYPE_CAMERA_PTZ', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 2, 'PRESET 1', 'PRESET 1', 'LBL_COMMAND_ELEMENT_TYPE_CAMERA_PRESET_1', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 2, 'PRESET 2', 'PRESET 2', 'LBL_COMMAND_ELEMENT_TYPE_CAMERA_PRESET_2', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(6, 2, 'PRESET 3', 'PRESET 3', 'LBL_COMMAND_ELEMENT_TYPE_CAMERA_PRESET_3', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(7, 2, 'PRESET 4', 'PRESET 4', 'LBL_COMMAND_ELEMENT_TYPE_CAMERA_PRESET_4', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(8, 2, 'PRESET 5', 'PRESET 5', 'LBL_COMMAND_ELEMENT_TYPE_CAMERA_PRESET_5', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(9, 2, 'PRESET 6', 'PRESET 6', 'LBL_COMMAND_ELEMENT_TYPE_CAMERA_PRESET_6', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO master.command_element_type_params
(command_element_type_id, element_type_id, command_element_type_param_id, data_type_id, threshold_1, threshold_2, command_element_type_param_unit_id, alias, description, Label_alias, label_description, enabled, visible, created_at, updated_at) VALUES
(2, 2, 1, 2, null , null, null, 'PRESET_ID', 'Preset id', 'LBL_COMMAND_ELEMENT_TYPE_PARAM_PRESET_ID', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 2, 1, 1,  '0' , '6', null,'PTZ_VALUE', 'PTZ value STOP(0), UP(1), DOWN(2), LEFT(3), RIGHT(4), ZOOM_I(5), ZOOM_D(6)', 'LBL_COMMAND_SEND_GROUP_VALUES_TYPE_PARAM_PTZ_VALUE', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
-- 
-- Table: master.element_subtype_active_commands
--
INSERT INTO master.element_subtype_active_commands (element_subtype_id, command_element_type_id, element_type_id, enabled, visible, created_at, updated_at) VALUES
(3, 1, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 2, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 3, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(4, 1, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 2, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 3, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(5, 1, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 4, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 5, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 6, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 7, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 8, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 9, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: conf.alarm_configs
--
INSERT INTO conf.alarm_configs
(alarm_config_id, alarm_type_id, alarm_level_id, element_type_id, element_id, element_type_state_id, mine, treatment, alias, description, label_alias, label_description, enabled, visible, created_at, updated_at) VALUES
--Común a todos los subtipos
(29, 2, 6, 2, NULL, NULL, true, true, 'ALARM CAMERA NO CONNECTION', 'ALARM CAMERA NO CONNECTION', 'LBL_ALARM_CONFIGS_ALARM_CAMERA_NO_CONNECTION', 'LBL_ALARM_CONFIGS_ALARM_CAMERA_NO_CONNECTION_DESC', false, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(30, 2, 5, 2, NULL, 3, true, true, 'ALARM CAMERA DOOR OPEN', 'ALARM CAMERA DOOR OPEN', 'LBL_ALARM_CONFIGS_ALARM_CAMERA_DOOR_OPEN', 'LBL_ALARM_CONFIGS_ALARM_CAMERA_DOOR_OPEN_DESC', false, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(31, 2, 6, 2, NULL, 3, true, true, 'ALARM CAMERA POWER SUPPLY FAIL', 'ALARM CAMERA POWER SUPPLY FAIL', 'LBL_ALARM_CONFIGS_ALARM_CAMERA_POWER_SUPPLY_FAIL', 'LBL_ALARM_CONFIGS_ALARM_CAMERA_POWER_SUPPLY_FAIL_DESC', false, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(32, 2, 6, 2, NULL, 3, true, true, 'ALARM CAMERA BATTERY LOW', 'ALARM CAMERA BATTERY LOW', 'LBL_ALARM_CONFIGS_ALARM_CAMERA_BATTERY_LOW', 'LBL_ALARM_CONFIGS_ALARM_CAMERA_BATTERY_LOW_DESC', false, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(33, 2, 5, 2, NULL, 3, true, true, 'ALARM CAMERA PLC STATUS', 'ALARM CAMERA PLC STATUS', 'LBL_ALARM_CONFIGS_ALARM_CAMERA_PLC_STATUS', 'LBL_ALARM_CONFIGS_ALARM_CAMERA_PLC_STATUS_DESC', false, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(313, 2, 6, 2, NULL, 3, true, true, 'ALARM CAMERA FAIL', 'ALARM CAMERA FAIL', 'LBL_ALARM_CONFIGS_ALARM_CAMERA_FAIL', 'LBL_ALARM_CONFIGS_ALARM_CAMERA_PAIL_DESC', false, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

--
-- Table: conf.element_state_alarms
--
INSERT INTO conf.element_state_alarms
(element_type_id, element_type_state_id, alarm_config_id, created_at, updated_at) VALUES
(2, 2, 29, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
