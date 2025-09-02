-- 
-- Type:   others
-- Entity: others
-- File:   others.sql
-- Langs:  es_ES, en_GB, sk_SK, es_CL
--

-- 
-- Table: master.i18n_labels
--

--PTZ speeds
INSERT INTO master.i18n_labels (locale_code,module_id,"label","translation",created_at,updated_at) VALUES
-- es_ES
('es_ES', 1, 'LBL_X0', 'velocidad lenta', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_ES', 1, 'LBL_X1', 'velocidad rápida', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- en_GB
('en_GB', 1, 'LBL_X0', 'low speed', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('en_GB', 1, 'LBL_X1', 'high speed', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- es_ES
('es_CL', 1, 'LBL_X0', 'velocidad lenta', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_CL', 1, 'LBL_X1', 'velocidad rápida', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
