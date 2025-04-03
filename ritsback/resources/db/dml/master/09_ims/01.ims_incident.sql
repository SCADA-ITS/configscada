-- 
-- Table: master.ims_task_types
--
INSERT INTO master.ims_task_types (task_type_id, alias, description, label_alias, label_description, enabled, visible, created_at, updated_at) VALUES
(1, 'SIGNALING', 'Signaling', 'LBL_IMS_TASK_TYPE_SIGNALING', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 'TUNNEL LIGHTING', 'Lighting task in all tunnel', 'LBL_IMS_TASK_TYPE_TUNNEL_LIGHTING', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 'TUBE LIGHTING', 'Lighting task in tube', 'LBL_IMS_TASK_TYPE_TUBE_LIGHTING', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 'TUNNEL VENTILATION', 'Ventilation task in all tunnel ', 'LBL_IMS_TASK_TYPE_TUNNEL_VENTILATION', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 'TUBE VENTILATION', 'Ventilation task in tube', 'LBL_IMS_TASK_TYPE_TUBE_VENTILATION', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(6, 'PERSONAL ALERT', 'Personal Alert', 'LBL_IMS_TASK_TYPE_PERSONAL_ALERT', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(7, 'CCTV', 'CCTV', 'LBL_IMS_TASK_TYPE_CCTV', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(8, 'SEND MAIL', 'Send mail', 'LBL_IMS_TASK_TYPE_SEND_MAIL', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(9, 'PAS', 'PAS', 'LBL_IMS_TASK_TYPE_PAS', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(10, 'SEND TELEGRAM', 'Send telegram notification', 'LBL_IMS_TASK_TYPE_SEND_TELEGRAM', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO master.ims_task_type_params
(task_type_id, task_type_param_id, param_type_id, data_type_id, threshold_1, threshold_2, task_type_param_unit_id, alias, description, label_alias, label_description, enabled, visible, created_at, updated_at)
VALUES
(1, 1, 1, 2, null, null, null, 'SignallingsToSpath', 'Json with signallings to spath', 'LBL_IMS_TASK_TYPE_PARAM_IMS_SIGNALING_TO_SPATH', NULL, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(2, 1, 2, 1, null, null, null, 'mode', 'mode', 'LBL_IMS_TASK_TYPE_PARAM_IMS_TUNNEL_LIGHTING_MODE', NULL, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 2, 2, 1, null, null, null, 'regime', 'regime', 'LBL_IMS_TASK_TYPE_PARAM_IMS_TUNNEL_LIGHTING_REGIME', NULL, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(3, 1, 2, 1, null, null, null, 'mode', 'mode', 'LBL_IMS_TASK_TYPE_PARAM_IMS_TUBE_LIGHTING_MODE', NULL, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 2, 2, 1, null, null, null, 'regime', 'regime', 'LBL_IMS_TASK_TYPE_PARAM_IMS_TUBE_LIGHTING_REGIME', NULL, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(4, 1, 2, 1, null, null, null, 'mode', 'mode', 'LBL_IMS_TASK_TYPE_PARAM_IMS_TUNNEL_VENTILATION_MODE', NULL, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 2, 2, 1, null, null, null, 'regime', 'regime', 'LBL_IMS_TASK_TYPE_PARAM_IMS_TUNNEL_VENTILATION_REGIME', NULL, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(5, 1, 2, 1, null, null, null, 'mode', 'mode', 'LBL_IMS_TASK_TYPE_PARAM_IMS_TUBE_VENTILATION_MODE', NULL, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 2, 2, 1, null, null, null, 'regime', 'regime', 'LBL_IMS_TASK_TYPE_PARAM_IMS_TUBE_VENTILATION_REGIME', NULL, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(7, 1, 2, 1, null, null, null, 'cameraId', 'cameraId', 'LBL_IMS_TASK_TYPE_PARAM_IMS_CCTV_CAMERA_ID', NULL, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(8, 1, 1, 2, null, null, null, 'addressTO', 'addressTO', 'LBL_IMS_TASK_TYPE_PARAM_IMS_SEND_MAIL_ADDRESS_TO', NULL, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(8, 2, 1, 2, null, null, null, 'addressCC', 'addressCC', 'LBL_IMS_TASK_TYPE_PARAM_IMS_SEND_MAIL_ADDRESS_CC', NULL, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(8, 3, 1, 2, null, null, null, 'addressBCC', 'addressBCC', 'LBL_IMS_TASK_TYPE_PARAM_IMS_SEND_MAIL_ADDRESS_BCC', NULL, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(8, 4, 1, 2, null, null, null, 'subject', 'subject', 'LBL_IMS_TASK_TYPE_PARAM_IMS_SEND_MAIL_SUBJECT', NULL, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(8, 5, 1, 2, null, null, null, 'body', 'body', 'LBL_IMS_TASK_TYPE_PARAM_IMS_SEND_MAIL_BODY', NULL, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(9, 1, 2, 2, null, null, null, 'messageIds', 'messageIds', 'LBL_IMS_TASK_TYPE_PARAM_IMS_PAS_MESSAGE_IDS', NULL, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(10, 1, 1, 2, null, null, null, 'tokenBot', 'tokenBot', 'LBL_IMS_TASK_TYPE_PARAM_IMS_SEND_TELEGRAM_TOKEN_BOT', NULL, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(10, 2, 1, 2, null, null, null, 'chatID', 'chatID', 'LBL_IMS_TASK_TYPE_PARAM_IMS_SEND_TELEGRAM_CHAT_ID', NULL, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(10, 3, 1, 2, null, null, null, 'messageText', 'messageText', 'LBL_IMS_TASK_TYPE_PARAM_IMS_SEND_TELEGRAM_MESSAGE', NULL, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: master.ims_incident_alarm_condition_alarm_types
--
-- Solo si tipo 1 en ims_incident_alarm_condition_alarms obligatorio element_type_id y element_id
INSERT INTO master.ims_incident_alarm_condition_alarm_types (incident_alarm_condition_alarm_type_id, alias, description, label_alias, label_description, enabled, visible, created_at, updated_at) VALUES
(1, 'EQUIPMENT ALERTED', 'Alarma en un equipo', 'LBL_IMS_INCIDENT_ALARM_CONDITION_ALARM_TYPE_EQUIPMENT_ALERTED', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 'ANY_EQUIPMENT_TYPE_ALARMED', ' Alarma en uno o más equipos de un tipo', 'LBL_IMS_INCIDENT_ALARM_CONDITION_ALARM_TYPE_ANY_EQUIPMENT_TYPE_ALARMED', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 'ALL_EQUIPMENT_TYPE_ALARMED', ' Alarma en todos los equipo de un tipo', 'LBL_IMS_INCIDENT_ALARM_CONDITION_ALARM_TYPE_ALL_EQUIPMENT_TYPE_ALARMED', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: master.ims_delayed_incident_state_types
--
INSERT INTO master.ims_delayed_incident_state_types (delayed_incident_state_type_id, alias, description, label_alias, label_description, enabled, visible, created_at, updated_at) VALUES
(0, 'INITIALIZED', 'initialized', 'LBL_DELAYED_STATE_TYPE_INITIALIZED', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 'RUNNING', 'running', 'LBL_DELAYED_STATE_TYPE_RUNNING', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 'STOPPED', 'stopped', 'LBL_DELAYED_STATE_TYPE_STOPPED', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 'ERROR', 'error', 'LBL_DELAYED_STATE_TYPE_ERROR', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
-- 
-- Table: static.ims_incident_type_states
--
INSERT INTO static.ims_incident_states (incident_state_id, final, alias, description, label_alias, label_description, enabled, visible, created_at, updated_at)
VALUES(101, true, 'CANCELED', 'CANCELED', 'LBL_IMS_INCIDENT_STATE_CANCELED', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);