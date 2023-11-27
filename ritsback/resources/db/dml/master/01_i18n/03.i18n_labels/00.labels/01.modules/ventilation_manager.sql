-- 
-- Type:   modules
-- Entity: Ventilation manager
-- File:   ventilation_manager.sql
-- Langs:  es_ES, en_GB, sk_SK, es_CL
--

-- 
-- Table: master.i18n_labels
--


-- es_ES
INSERT INTO master.i18n_labels (locale_code,module_id,"label","translation",created_at,updated_at) VALUES
('es_ES', 1, 'LBL_SEND_VENTILATION_STOP_CONFIRM_DESCRIPTION', '¿Esta seguro de parar la ventilación del tunel?', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_ES', 1, 'LBL_START', 'Arrancar', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_ES', 1, 'LBL_SEND_VENTILATION_START_CONFIRM_DESCRIPTION', '¿Esta seguro de arrancar la ventilación del tunel?', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_ES', 1, 'LBL_FAN_COMMAND_NOT_ALLOWED_WITH_THIS_STATE', 'No es posible la comandancia de ventiladores con este estado', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_ES', 1, 'LBL_VENTILATION_CHANGE_MODE_NOT_ALLOWED', 'No es posible cambiar a este modo', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_ES', 1, 'LBL_STOP_DESCRIPTION', 'Parar la ventilación', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_ES', 1, 'LBL_START_DESCRIPTION', 'Arrancar la ventilación', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_ES', 1, 'LBL_MODE_VENT_AUTO', 'Automático', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_ES', 1, 'LBL_MODE_VENT_AUTO_MAINTENANCE', 'Mantenimiento', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_ES', 1, 'LBL_MODE_VENT_MANUAL', 'Manual', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_ES', 1, 'LBL_MODE_VENT_AUTO_VENTILATION_DESCRIPTION', 'Control automático - Ventilación sanitaria', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_ES', 1, 'LBL_MODE_VENT_AUTO_MAINTENANCE_VENTILATION_DESCRIPTION', 'Control automático - Ventilación para mantenimiento', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_ES', 1, 'LBL_MODE_VENT_MANUAL_VENTILATION_DESCRIPTION', 'Control manual de la ventilación', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- en_GB
INSERT INTO master.i18n_labels (locale_code,module_id,"label","translation",created_at,updated_at) VALUES

('en_GB', 1, 'LBL_SEND_VENTILATION_STOP_CONFIRM_DESCRIPTION', 'Are you sure to stop the tunnel ventilation?', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('en_GB', 1, 'LBL_START', 'Arrancar', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('en_GB', 1, 'LBL_SEND_VENTILATION_START_CONFIRM_DESCRIPTION', 'Are you sure to start the tunnel ventilation?', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('en_GB', 1, 'LBL_FAN_COMMAND_NOT_ALLOWED_WITH_THIS_STATE', 'Fan commands are not alowed with this state', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('en_GB', 1, 'LBL_VENTILATION_CHANGE_MODE_NOT_ALLOWED', 'It is not possible to change to this mode', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('en_GB', 1, 'LBL_STOP_DESCRIPTION', 'Stop', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('en_GB', 1, 'LBL_START_DESCRIPTION', 'Start', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('en_GB', 1, 'LBL_MODE_VENT_AUTO', 'Auto', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('en_GB', 1, 'LBL_MODE_VENT_AUTO_MAINTENANCE', 'Maintenance', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('en_GB', 1, 'LBL_MODE_VENT_MANUAL', 'Manual', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('en_GB', 1, 'LBL_MODE_VENT_AUTO_VENTILATION_DESCRIPTION', 'Automatic control - Sanitary ventilation', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('en_GB', 1, 'LBL_MODE_VENT_AUTO_MAINTENANCE_VENTILATION_DESCRIPTION', 'Automatic control - Ventilation for maintenance', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('en_GB', 1, 'LBL_MODE_VENT_MANUAL_VENTILATION_DESCRIPTION', 'Manual ventilation control', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- es_CL
INSERT INTO master.i18n_labels (locale_code,module_id,"label","translation",created_at,updated_at) VALUES
('es_CL', 1, 'LBL_FAN_COMMAND_NOT_ALLOWED_WITH_THIS_STATE', 'No es posible la comandancia de ventiladores con este estado', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_CL', 1, 'LBL_STOP_DESCRIPTION', 'Parar la ventilación', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_CL', 1, 'LBL_START_DESCRIPTION', 'Arrancar la ventilación', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_CL', 1, 'LBL_MODE_VENT_AUTO', 'Automático', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_CL', 1, 'LBL_MODE_VENT_MANUAL_FAN', 'Manual', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_CL', 1, 'LBL_MODE_VENT_MANUAL_REGIME', 'Regimenes', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_CL', 1, 'LBL_MODE_VENT_FIRE', 'Incendio', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_CL', 1, 'LBL_MODE_VENT_AUTO_VENTILATION_DESCRIPTION', 'Control automático por parte del PLC del algoritmo de ventilación', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_CL', 1, 'LBL_MODE_VENT_MANUAL_FAN_VENTILATION_DESCRIPTION', 'Control manual por parte del operador de la ventilación', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_CL', 1, 'LBL_MODE_VENT_MANUAL_REGIME_VENTILATION_DESCRIPTION', 'Permite cambiar el régimen de actuación de la ventilación de forma manual', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_CL', 1, 'LBL_MODE_VENT_FIRE_VENTILATION_DESCRIPTION', 'Iniciar algoritmo automático de incendio en la zona de ventilación actual', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
