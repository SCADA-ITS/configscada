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
	 ('es_ES', 1, 'LBL_INCIDENT_LEVEL_0', 'Explotación', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	 ('es_ES', 1, 'LBL_INCIDENT_LEVEL_1', 'Emergencia', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	 ('es_ES', 1, 'LBL_INCIDENT_LEVEL_2', 'Eventos', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	 
--en_GB
	 ('en_GB', 1, 'LBL_INCIDENT_LEVEL_0', 'Exploitation', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	 ('en_GB', 1, 'LBL_INCIDENT_LEVEL_1', 'Emergency', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	 ('en_GB', 1, 'LBL_INCIDENT_LEVEL_2', 'Events', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--sk_SK
	 --('sk_SK', 1, 'LBL_INCIDENT_LEVEL_0', 'Exploitation Incident', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	 --('sk_SK', 1, 'LBL_INCIDENT_LEVEL_1', 'Pre-emergency', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	 --('sk_SK', 1, 'LBL_INCIDENT_LEVEL_2', 'Level 1 emergency', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	 --('sk_SK', 1, 'LBL_INCIDENT_LEVEL_3', 'Level 2 emergency', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	
--es_CL
	 ('es_CL', 1, 'LBL_INCIDENT_LEVEL_0', 'Explotación', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	 ('es_CL', 1, 'LBL_INCIDENT_LEVEL_1', 'Emergencia', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	 ('es_CL', 1, 'LBL_INCIDENT_LEVEL_2', 'Eventos', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
	 