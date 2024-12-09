-- 
-- Table: master.element_types
--
INSERT INTO master.element_types(element_type_id, alias, description, label_alias, label_description, initial_element_type_state_id, connected_element_type_state_id, disconnected_element_type_state_id, enabled, visible, created_at, updated_at)
VALUES(2, 'CAMERA', 'Camera','LBL_ELEMENT_TYPE_CAMERA', 'LBL_ELEMENT_TYPE_CAMERA_DESC', 0, 1, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

--
-- Table: master.element_type_params
--
INSERT INTO master.element_type_params (element_type_id, param_type_id, element_type_param_id, element_type_param_unit_id, data_type_id, default_value, alias, description, label_alias, label_description, enabled, visible, editable, created_at, updated_at) VALUES
--CONFIG
(2,1,1, NULL,2, NULL, 'brand', 'brand', 'LBL_ELEMENT_TYPE_PARAM_BRAND' , 'LBL_ELEMENT_TYPE_PARAM_BRAND_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2,1,2, NULL,2, NULL, 'model', 'model', 'LBL_ELEMENT_TYPE_PARAM_MODEL' , 'LBL_ELEMENT_TYPE_PARAM_MODEL_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2,1,3, NULL,2, NULL, 'stream_mjpeg', 'stream mjpeg', 'LBL_ELEMENT_TYPE_PARAM_STREAM_MJPEG' , 'LBL_ELEMENT_TYPE_PARAM_STREAM_MJPEG_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2,1,4, NULL,2, NULL, 'selection_code_keyboard', 'selection_code_keyboard', 'LBL_ELEMENT_TYPE_PARAM_SELECTION_CODE_KEYBOARD_ALPHANUMERIC' , 'LBL_ELEMENT_TYPE_PARAM_SELECTION_CODE_KEYBOARD_ALPHANUMERIC_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2,1,5, NULL,2, NULL, 'associated_cabinet', 'associated_cabinet', 'LBL_ELEMENT_TYPE_PARAM_CAMERA_ASSOCIATED_CABINET' , 'LBL_ELEMENT_TYPE_PARAM_CAMERA_ASSOCIATED_CABINET_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2,1,6, NULL,2, NULL, 'preset_config_json', 'preset config json', 'LBL_ELEMENT_TYPE_PARAM_PRESET_CONFIG_JSON' , 'LBL_ELEMENT_TYPE_PARAM_PRESET_CONFIG_JSON_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2,1,7, NULL,2, NULL, 'features (PTZ)', 'features (Config with chars PTZ)', 'LBL_ELEMENT_TYPE_PARAM_FEATURES_CONFIG_JSON' , 'LBL_ELEMENT_TYPE_PARAM_FEATURES_CONFIG_JSON_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2,1,9, NULL,2, NULL, 'onvif_hostname', 'Hostname for onvif protoco', 'LBL_ELEMENT_TYPE_PARAM_ONVIF_HOSTNAME' , 'LBL_ELEMENT_TYPE_PARAM_ONVIF_HOSTNAME_DESC', true, true, false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2,1,10, NULL,2, NULL, 'onvif_username', 'User name for onvif protoco', 'LBL_ELEMENT_TYPE_PARAM_ONVIF_USERNAME' , 'LBL_ELEMENT_TYPE_PARAM_ONVIF_USERNAME_DESC', true, true, false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2,1,11, NULL,2, NULL, 'onvif_password', 'Password for onvif protoco', 'LBL_ELEMENT_TYPE_PARAM_ONVIF_PASSWORD' , 'LBL_ELEMENT_TYPE_PARAM_ONVIF_PASSWORD_DESC', true, true, false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2,1,12, NULL,2, NULL, 'onvif_ptz_params', 'Params for onvif ptz control', 'LBL_ELEMENT_TYPE_PARAM_ONVIF_PTZ_PARAMS' , 'LBL_ELEMENT_TYPE_PARAM_ONVIF_PTZ_PARAMS_DESC', true, true, false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2,1,13, NULL,2, '', 'id_camara', 'id_camara', 'LBL_ELEMENT_TYPE_PARAM_ID_CAMARA' , 'LBL_ELEMENT_TYPE_PARAM_ID_CAMARA_DESC', true, true, false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2,1,15, NULL,1, '', 'selection code keyboard', 'selection code keyboard', 'LBL_ELEMENT_TYPE_PARAM_SELECTION_CODE_KEYBOARD_INTEGER' , 'LBL_ELEMENT_TYPE_PARAM_SELECTION_CODE_KEYBOARD_INTEGER_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2,1,16, NULL,2, NULL, 'url_snapshot', 'url_snapshot', 'LBL_ELEMENT_TYPE_PARAM_URL_SNAPSHOT' , 'LBL_ELEMENT_TYPE_PARAM_URL_SNAPSHOT_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--MEASURE
(2,2,1, NULL,2, NULL, 'image', 'image', 'LBL_ELEMENT_TYPE_PARAM_IMAGE' , 'LBL_ELEMENT_TYPE_PARAM_IMAGE_DESC', true, true, false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2,2,2, NULL,2, NULL, 'video', 'video', 'LBL_ELEMENT_TYPE_PARAM_VIDEO' , 'LBL_ELEMENT_TYPE_PARAM_VIDEO_DESC', true, true, false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2,2,3, NULL,2, NULL, 'id_incident', 'id_incident', 'LBL_ELEMENT_TYPE_PARAM_ID_INCIDENT' , 'LBL_ELEMENT_TYPE_PARAM_ID_INCIDENT_DESC', true, true, false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2,2,4, NULL,2, NULL, 'place', 'place', 'LBL_ELEMENT_TYPE_PARAM_PLACE' , 'LBL_ELEMENT_TYPE_PARAM_PLACE_DESC', true, true, false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2,2,5, NULL,2, NULL, 'type_incident', 'type_incident', 'LBL_ELEMENT_TYPE_PARAM_TYPE_INCIDENT' , 'LBL_ELEMENT_TYPE_PARAM_TYPE_INCIDENT_DESC', true, true, false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2,2,6, NULL,2, NULL, 'plate', 'plate', 'LBL_ELEMENT_TYPE_PARAM_LICENSE_NUMBER' , 'LBL_ELEMENT_TYPE_PARAM_LICENSE_NUMBER_DESC', true, true, false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2,2,7, NULL,2, NULL, 'datehour', 'datehour', 'LBL_ELEMENT_TYPE_PARAM_CAMERA_DATE' , 'LBL_ELEMENT_TYPE_PARAM_CAMERA_DATE_DESC', true, true, false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2,2,8, NULL,3, NULL, 'speed', 'speed', 'LBL_ELEMENT_TYPE_PARAM_SPEED', 'LBL_ELEMENT_TYPE_PARAM_SPEED_DESC', true, true, false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2,2,9, NULL,2, NULL, 'jsonMedidas', 'jsonMedidas', 'LBL_ELEMENT_TYPE_PARAM_JSON_MEDIDAS', 'LBL_ELEMENT_TYPE_PARAM_JSON_MEDIDAS_DESC', true, true, false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2,1,1003, NULL,2, NULL, 'MANGO SESSION_UID', 'MANGO SESSION_UID', 'LBL_ELEMENT_TYPE_PARAM_MANGO_SESSION_UID' , 'LBL_ELEMENT_TYPE_PARAM_MANGO_SESSION_UID_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2,1,1004, NULL,2, NULL, 'CITILOG CAMERA_UID', 'CITILOG CAMERA_UID', 'LBL_ELEMENT_TYPE_PARAM_CITILOG_CAMERA_UID' , 'LBL_ELEMENT_TYPE_PARAM_CITILOG_CAMERA_UID_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: master.element_subtypes
--
INSERT INTO master.element_subtypes(element_subtype_id, element_type_id, alias, description,label_alias, label_description, enabled, visible, created_at, updated_at)
VALUES
(1, 2, 'Camera Fix', 'Camera Fix', 'LBL_ELEMENT_SUBTYPE_CAMERA', 'LBL_ELEMENT_SUBTYPE_CAMERA_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 2, 'Camera DAI', 'Camera DAI', 'LBL_ELEMENT_SUBTYPE_CAMERA_DAI', 'LBL_ELEMENT_SUBTYPE_CAMERA_DAI_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 2, 'Camera Domo', 'Camera DOMO', 'LBL_ELEMENT_SUBTYPE_CAMERA_DOMO_MODBUS', 'LBL_ELEMENT_SUBTYPE_CAMERA_DOMO_MODBUS_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 2, 'Camera Sanctioning', 'Camera SANCTIONING', 'LBL_ELEMENT_SUBTYPE_CAMERA_SANCTIONING', 'LBL_ELEMENT_SUBTYPE_CAMERA_SANCTIONING_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: master.element_subtype_active_params
--
INSERT INTO master.element_subtype_active_params
(element_subtype_id, element_type_id, param_type_id, element_type_param_id, enabled, visible, created_at, updated_at)VALUES
-- CAMERA Fix
(1, 2, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 2, 1, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 2, 1, 3, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 2, 1, 16, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- CAMERA DAI
(2, 2, 2, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 2, 2, 3, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 2, 2, 4, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 2, 2, 5, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- CAMERA DOMO
(3, 2, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 2, 1, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 2, 1, 3, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 2, 1, 6, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 2, 1, 7, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 2, 1, 9, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 2, 1, 10, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 2, 1, 11, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 2, 1, 12, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 2, 1, 16, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),


-- CAMERA SANCTIONING
(4, 2, 2, 6, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 2, 2, 7, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 2, 2, 8, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 2, 2, 9, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: master.element_type_states
--
INSERT INTO master.element_type_states(element_type_id, element_type_state_id, alias, description, label_alias, label_description, enabled, visible, created_at, updated_at) VALUES
(2, 0, 'UNKNOWN', 'Unknown state', 'LBL_ELEMENT_TYPE_STATE_UNKNOWN', 'LBL_ELEMENT_TYPE_STATE_UNKNOWN_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 1, 'UP', 'The state of the equipment is: comunicates', 'LBL_ELEMENT_TYPE_STATE_UP', 'LBL_ELEMENT_TYPE_STATE_UP_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 2, 'DOWN', 'The state of the equipment is: out of communication', 'LBL_ELEMENT_TYPE_STATE_DOWN', 'LBL_ELEMENT_TYPE_STATE_DOWN_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 3, 'ALARMS', 'The state of the equipment is: with alarms', 'LBL_ELEMENT_TYPE_STATE_ALARMS', 'LBL_ELEMENT_TYPE_STATE_ALARMS_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 4, 'MAINTENANCE', 'The state of the equipment is: on Maintenance', 'LBL_ELEMENT_TYPE_STATE_MAINTENANCE', 'LBL_ELEMENT_TYPE_STATE_MAINTENANCE_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

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
(3, 2, 'PTZ', 'PTZ', 'LBL_COMMAND_ELEMENT_TYPE_CAMERA_PTZ', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO master.command_element_type_params
(command_element_type_id, element_type_id, command_element_type_param_id, data_type_id, threshold_1, threshold_2, command_element_type_param_unit_id, alias, description, Label_alias, label_description, enabled, visible, created_at, updated_at) VALUES
(2, 2, 1, 2, null , null, null, 'PRESET_ID', 'Preset id', 'LBL_COMMAND_ELEMENT_TYPE_PARAM_PRESET_ID', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 2, 1, 1,  '0' , '6', null,'PTZ_VALUE', 'PTZ value STOP(0), UP(1), DOWN(2), LEFT(3), RIGHT(4), ZOOM_I(5), ZOOM_D(6)', 'LBL_COMMAND_ELEMENT_TYPE_PARAM_SEND_GROUP_VALUES_TYPE_PARAM_PTZ_VALUE', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 2, 2, 1,  null , null, null,'speedId', 'Speed id', 'LBL_COMMAND_ELEMENT_TYPE_PARAM_SEND_GROUP_VALUES_TYPE_PARAM_SPEED', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: master.element_subtype_active_commands
--
INSERT INTO master.element_subtype_active_commands (element_subtype_id, command_element_type_id, element_type_id, enabled, visible, created_at, updated_at) VALUES
(1, 1, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(3, 1, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 2, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 3, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: conf.alarm_configs
--
INSERT INTO conf.alarm_configs
(alarm_config_id, alarm_type_id, alarm_level_id, element_type_id, element_id, element_type_state_id, mine, treatment, alias, description, label_alias, label_description, enabled, visible, created_at, updated_at) VALUES
--Común a todos los subtipos
(010002, 4, 6, 2, NULL, NULL, true, true, 'ALARM CAMERA NO CONNECTION', 'ALARM CAMERA NO CONNECTION', 'LBL_ALARM_CONFIGS_ALARM_CAMERA_NO_CONNECTION', 'LBL_ALARM_CONFIGS_ALARM_CAMERA_NO_CONNECTION_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(020002, 2, 5, 2, NULL, 3, true, true, 'ALARM CAMERA DOOR OPEN', 'ALARM CAMERA DOOR OPEN', 'LBL_ALARM_CONFIGS_ALARM_CAMERA_DOOR_OPEN', 'LBL_ALARM_CONFIGS_ALARM_CAMERA_DOOR_OPEN_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(030002, 2, 6, 2, NULL, 3, true, true, 'ALARM CAMERA POWER SUPPLY FAIL', 'ALARM CAMERA POWER SUPPLY FAIL', 'LBL_ALARM_CONFIGS_ALARM_CAMERA_POWER_SUPPLY_FAIL', 'LBL_ALARM_CONFIGS_ALARM_CAMERA_POWER_SUPPLY_FAIL_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(040002, 2, 6, 2, NULL, 3, true, true, 'ALARM CAMERA BATTERY LOW', 'ALARM CAMERA BATTERY LOW', 'LBL_ALARM_CONFIGS_ALARM_CAMERA_BATTERY_LOW', 'LBL_ALARM_CONFIGS_ALARM_CAMERA_BATTERY_LOW_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(050002, 2, 5, 2, NULL, 3, true, true, 'ALARM CAMERA PLC STATUS', 'ALARM CAMERA PLC STATUS', 'LBL_ALARM_CONFIGS_ALARM_CAMERA_PLC_STATUS', 'LBL_ALARM_CONFIGS_ALARM_CAMERA_PLC_STATUS_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(060002, 2, 6, 2, NULL, 3, true, true, 'ALARM CAMERA FAIL', 'ALARM CAMERA FAIL', 'LBL_ALARM_CONFIGS_ALARM_CAMERA_FAIL', 'LBL_ALARM_CONFIGS_ALARM_CAMERA_FAIL_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(070002, 2, 4, 2, NULL, 3, true, true, 'ALARM CAMERA OVERSPEED', 'ALARM CAMERA OVERSPEED', 'LBL_ALARM_CONFIGS_ALARM_CAMERA_OVERSPEED', 'LBL_ALARM_CONFIGS_ALARM_CAMERA_OVERSPEED', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

--
-- Table: conf.element_state_alarms
--
INSERT INTO conf.element_state_alarms
(element_type_id, element_type_state_id, alarm_config_id, created_at, updated_at) VALUES
(2, 2, 010002, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
