-- 
-- Type:   modules
-- Entity: Audit Logs
-- File:   audit_logs.sql
-- Langs:  es_ES, en_GB, sk_SK, es_CL
--

-- 
-- Table: master.i18n_labels
--
INSERT INTO master.i18n_labels (locale_code,module_id,"label","translation",created_at,updated_at) VALUES
--es_ES
('es_ES', 1, 'LBL_AUDIT_LOG_REZOGNIZE_CONFIRM', 'Reconocer', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_ES', 1, 'LBL_AUDIT_LOG_REZOGNIZE_CONFIRM_DESCRIPTION', '¿Esta seguro que desea reconocer las auditorías seleccionadas?', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--en_GB
('en_GB', 1, 'LBL_AUDIT_LOG_REZOGNIZE_CONFIRM', 'Recognize', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('en_GB', 1, 'LBL_AUDIT_LOG_REZOGNIZE_CONFIRM_DESCRIPTION', 'Are your sure you want to recognize the selected audits', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--sk_SK

--es_CL
('es_CL', 1, 'LBL_AUDIT_LOG_REZOGNIZE_CONFIRM', 'Reconocer', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_CL', 1, 'LBL_AUDIT_LOG_REZOGNIZE_CONFIRM_DESCRIPTION', '¿Esta seguro que desea reconocer las auditorías seleccionadas?', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);