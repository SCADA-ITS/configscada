-- 
-- Type:   model
-- Entity: static.ims_incident_states.sql
-- File:   ims_incident_states.sql.sql
-- Langs:  es_ES, en_GB, sk_SK, es_CL
--


INSERT INTO master.i18n_labels (locale_code,module_id,"label","translation",created_at,updated_at) VALUES
--es_ES
('es_ES', 1, 'LBL_ENTER_VALUE_TAGS', 'Introduzca un nuevo valor. Tags disponibles: 
@tipo
@localizacion
@afeccion
@fecha', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
-- 
-- Table: master.i18n_labels
--
