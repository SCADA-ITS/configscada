-- 
-- Type:   modules
-- Entity: Lighting manager
-- File:   lighting_manager.sql
-- Langs:  es_ES, en_GB, sk_SK, es_CL
--

-- 
-- Table: master.i18n_labels
--

-- es_ES
INSERT INTO master.i18n_labels (locale_code,module_id,"label","translation",created_at,updated_at) VALUES
('es_ES', 1, 'LBL_MODE_LIGHT_AUTO', 'Automático', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_ES', 1, 'LBL_MODE_LIGHT_AUTO_LIGHTING_DESCRIPTION', 'Control automático de la iluminacion', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_ES', 1, 'LBL_MODE_LIGHT_MANUAL', 'Control manual', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_ES', 1, 'LBL_MODE_LIGHT_MANUAL_LIGHTING_DESCRIPTION', 'Control manual de la iluminacion', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- en_GB
INSERT INTO master.i18n_labels (locale_code,module_id,"label","translation",created_at,updated_at) VALUES
('en_GB', 1, 'LBL_MODE_LIGHT_AUTO', 'Auto', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('en_GB', 1, 'LBL_MODE_LIGHT_AUTO_LIGHTING_DESCRIPTION', 'Automatic lighting control', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('en_GB', 1, 'LBL_MODE_LIGHT_MANUAL', 'Manual', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('en_GB', 1, 'LBL_MODE_LIGHT_MANUAL_LIGHTING_DESCRIPTION', 'Manual lighting control', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- es_CL
INSERT INTO master.i18n_labels (locale_code,module_id,"label","translation",created_at,updated_at) VALUES
('es_CL', 1, 'LBL_MODE_LIGHT_AUTO', 'Automático', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_CL', 1, 'LBL_MODE_LIGHT_AUTO_LIGHTING_DESCRIPTION', 'Gestión automática de la iluminación a través del PLC', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_CL', 1, 'LBL_MODE_LIGHT_MANUAL', 'Control manual', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_CL', 1, 'LBL_MODE_LIGHT_MANUAL_LIGHTING_DESCRIPTION', 'Gestión por parte del operador de la iluminación', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
