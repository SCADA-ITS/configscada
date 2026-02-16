-- 
-- Type:   model
-- Entity: ui.vo_entity_fields
-- File:   vo_entity_fields.sql
-- Langs:  es_ES, en_GB, sk_SK, es_CL
--

-- 
-- Table: master.i18n_labels
--


INSERT INTO master.i18n_labels (locale_code,module_id,"label","translation",created_at,updated_at) VALUES
-- es_ES
('es_ES', 1, 'LBL_EXECUTE_INCIDENT_TYPE_TASK', 'Ejecutar', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_ES', 1, 'LBL_USER_UP_STREAM_EVACUATED', '¿Están los usuarios agua arriba evacuados?', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_ES', 1, 'LBL_CONFIRM_FIRE', '¿Confirma la existencia de fuego?', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--en_GB
('en_GB', 1, 'LBL_EXECUTE_INCIDENT_TYPE_TASK', 'Execute', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('en_GB', 1, 'LBL_USER_UP_STREAM_EVACUATED', 'Are upstream users evacuated?', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('en_GB', 1, 'LBL_CONFIRM_FIRE', 'Does it confirm the existence of fire?', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- es_CL
('es_CL', 1, 'LBL_EXECUTE_INCIDENT_TYPE_TASK', 'Ejecutar', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_CL', 1, 'LBL_USER_UP_STREAM_EVACUATED', '¿Están los usuarios agua arriba evacuados?', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_CL', 1, 'LBL_CONFIRM_FIRE', '¿Confirma la existencia de fuego?', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);