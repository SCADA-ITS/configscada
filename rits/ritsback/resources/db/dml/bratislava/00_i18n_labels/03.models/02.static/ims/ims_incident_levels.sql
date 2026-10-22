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
	 ('es_ES', 1, 'LBL_INCIDENT_LEVEL_1_ROAD', 'Incidencia en la vía', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	 ('es_ES', 1, 'LBL_INCIDENT_LEVEL_1_TRAFFIC', 'Estado del tráfico', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	 ('es_ES', 1, 'LBL_INCIDENT_LEVEL_1_WEATHER', 'Incidencia Meteorológica', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

	 
--en_GB
	 ('en_GB', 1, 'LBL_INCIDENT_LEVEL_1_ROAD', 'Road Incident', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	 ('en_GB', 1, 'LBL_INCIDENT_LEVEL_1_TRAFFIC', 'Traffic Status', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	 ('en_GB', 1, 'LBL_INCIDENT_LEVEL_1_WEATHER', 'Weather Data', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),


--sk_SK
	 ('sk_SK', 1, 'LBL_INCIDENT_LEVEL_1_ROAD', 'Cestná nehoda', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	 ('sk_SK', 1, 'LBL_INCIDENT_LEVEL_1_TRAFFIC', 'Stav dopravy', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	 ('sk_SK', 1, 'LBL_INCIDENT_LEVEL_1_WEATHER', 'typ incidentu - meteo', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

	
--es_CL
	 ('es_CL', 1, 'LBL_INCIDENT_LEVEL_1_ROAD', 'Incidencia en la vía', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	 ('es_CL', 1, 'LBL_INCIDENT_LEVEL_1_TRAFFIC', 'Estado del tráfico', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	 ('es_CL', 1, 'LBL_INCIDENT_LEVEL_1_WEATHER', 'Incidencia Meteorológica', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
