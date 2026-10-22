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
-- es_ES
INSERT INTO master.i18n_labels (locale_code,module_id,"label","translation",created_at,updated_at) VALUES
('es_ES', 1, 'LBL_X0', 'velocidad lenta', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_ES', 1, 'LBL_X1', 'velocidad rápida', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);