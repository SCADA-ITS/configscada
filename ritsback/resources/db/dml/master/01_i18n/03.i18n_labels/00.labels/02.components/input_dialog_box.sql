-- 
-- Type:   component
-- Entity: Input dialog box
-- File:   input_dialog_box.sql
-- Langs:  es_ES, en_GB, sk_SK, es_CL
--

-- 
-- Table: master.i18n_labels
--
INSERT INTO master.i18n_labels (locale_code,module_id,"label","translation",created_at,updated_at) VALUES
--es_ES
('es_ES', 1, 'LBL_INVALID_VALUE', 'Valor invalido', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_ES', 1, 'LBL_VALUE_OUTSIDE_THRESHOLS', 'Valor fuera de rango', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--en_GB
('en_GB', 1, 'LBL_INVALID_VALUE', 'Invalid value', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('en_GB', 1, 'LBL_VALUE_OUTSIDE_THRESHOLS', 'Value outside thresholds', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
 	
--sk_SK
('sk_SK', 1, 'LBL_INVALID_VALUE', '', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--es_CL
('es_CL', 1, 'LBL_INVALID_VALUE', 'Valor invalido', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_CL', 1, 'LBL_VALUE_OUTSIDE_THRESHOLS', 'Valor fuera de rango', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
