-- 
-- Type:   model
-- Entity: conf.alarm_configs
-- File:   alarm_configs.sql
-- Langs:  es_ES, en_GB, sk_SK, es_CL
--

-- 
-- Table: master.i18n_labels
--

INSERT INTO master.i18n_labels (locale_code, module_id, "label", "translation", created_at, updated_at) VALUES
('es_ES', 1, 'LBL_ALARM_CONFIGS_ALARM_BALIZA_DISCONNECTED', 'Baliza fuera de comunicación', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_ES', 1, 'LBL_ALARM_CONFIGS_ALARM_BALIZA_DISCONNECTED_DESC', 'Baliza fuera de comunicación', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

('es_ES', 1, 'LBL_ALARM_CONFIGS_ALARM_IPL_DISCONNECTED', 'Baliza fuera de comunicación', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_ES', 1, 'LBL_ALARM_CONFIGS_ALARM_IPL_DISCONNECTED_DESC', 'Baliza fuera de comunicación', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

('es_ES', 1, 'LBL_ALARM_IPL_TEMPERATURA_LINEA_DE_BALIZAS', 'Alarma Temperatura línea de balizas', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_ES', 1, 'LBL_ALARM_IPL_TEMPERATURA_LINEA_DE_BALIZAS_DESC', 'Alarma Temperatura línea de balizas', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

('es_ES', 1, 'LBL_ALARM_IPL_TEMPERATURA_EQUIPO', 'Alarma Temperatura equipo', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_ES', 1, 'LBL_ALARM_IPL_TEMPERATURA_EQUIPO_DESC', 'Alarma Temperatura equipo', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

('es_ES', 1, 'LBL_ALARM_IPL_FALLA', 'Alarma falla IPL', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_ES', 1, 'LBL_ALARM_IPL_FALLA_DESC', 'Alarma falla IPL', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

('es_ES', 1, 'LBL_ALARM_CONFIGS_ALARM_BUS_VAO_DISCONNECTED', 'BUS-VAO fuera de comunicación', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_ES', 1, 'LBL_ALARM_CONFIGS_ALARM_BUS_VAO_DISCONNECTED_DESC', 'BUS-VAO fuera de comunicación', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

