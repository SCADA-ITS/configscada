-- 
-- Type:   model
-- Entity: ui.window_values
-- File:   window_values.sql
-- Langs:  es_ES, en_GB, sk_SK, es_CL
--

-- 
-- Table: master.i18n_labels
--
INSERT INTO master.i18n_labels (locale_code,module_id,"label","translation",created_at,updated_at) VALUES
('es_ES',1,'LBL_ALARM_LEVEL_LABEL_ALIAS','Nivel de alarma',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
('es_ES', 1, 'LBL_ALARM_LEVEL', 'Nivel', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_ES', 1, 'LBL_ALARM_LEVEL_INFO', 'Nivel info alarma', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_ES', 1, 'LBL_ALARM_LEVEL_WARN', 'Nivel advertencia alarma', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_ES', 1, 'LBL_ALARM_LEVEL_CRITICAL', 'Alarma nivel crítico', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);