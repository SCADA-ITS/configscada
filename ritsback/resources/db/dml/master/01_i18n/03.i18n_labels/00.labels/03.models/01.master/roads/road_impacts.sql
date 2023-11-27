-- 
-- Type:   model
-- Entity: static.ims_incident_levels.sql
-- File:   ims_incident_levels.sql.sql
-- Langs:  es_ES, en_GB, sk_SK, es_CL
--

-- 
-- Table: master.i18n_labels
--
INSERT INTO master.i18n_labels (locale_code,module_id,"label","translation",created_at,updated_at) VALUES
--es_ES
	('es_ES', 1, 'LBL_ROAD_IMPACT_DEFAULT', 'Por defecto', CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	('es_ES', 1, 'LBL_ROAD_IMPACT_UNKNOWN', 'Desconocido', CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	
--en_GB
	('en_GB', 1, 'LBL_ROAD_IMPACT_DEFAULT', 'Default', CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	('en_GB', 1, 'LBL_ROAD_IMPACT_UNKNOWN', 'Unknown', CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),	
	
--sk_SK
	('sk_SK', 1, 'LBL_ROAD_IMPACT_DEFAULT', 'Prednostné nastavenie', CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	('sk_SK', 1, 'LBL_ROAD_IMPACT_UNKNOWN', 'Neuvedené', CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),	
	
--es_CL
	('es_CL', 1, 'LBL_ROAD_IMPACT_DEFAULT', 'Por defecto', CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	('es_CL', 1, 'LBL_ROAD_IMPACT_UNKNOWN', 'Desconocido', CURRENT_TIMESTAMP,CURRENT_TIMESTAMP);