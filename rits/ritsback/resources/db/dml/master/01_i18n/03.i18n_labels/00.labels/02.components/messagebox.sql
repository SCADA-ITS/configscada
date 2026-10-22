-- 
-- Type:   component
-- Entity: Messagebox
-- File:   message_box.sql
-- Langs:  es_ES, en_GB, sk_SK, es_CL
--

-- 
-- Table: master.i18n_labels
--
INSERT INTO master.i18n_labels (locale_code,module_id,"label","translation",created_at,updated_at) VALUES
--es_ES
('es_ES', 1, 'LBL_INFO', 'Información', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_ES', 1, 'LBL_READ','Leído',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
('es_ES', 1, 'LBL_WARN', 'Atención', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--en_GB
('en_GB', 1, 'LBL_INFO', 'Info', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('en_GB', 1, 'LBL_READ','Read',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
('en_GB', 1, 'LBL_WARN', 'Warning', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
 	
--sk_SK
('sk_SK', 1, 'LBL_INFO','Info',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
('sk_SK', 1, 'LBL_READ','Čítať',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
('sk_SK', 1, 'LBL_WARN','Upozornenie',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),

--es_CL
('es_CL', 1, 'LBL_INFO', 'Información', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_CL', 1, 'LBL_READ','Leído',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
('es_CL', 1, 'LBL_WARN', 'Atención', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);