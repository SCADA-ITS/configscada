-- 
-- Type:   model
-- Entity: master.ims_incident_groups
-- File:   ims_incident_groups.sql
-- Langs:  es_ES, en_GB, sk_SK, es_CL
--

-- 
-- Table: master.i18n_labels
--

-- es_ES
INSERT INTO master.i18n_labels (locale_code,module_id,"label","translation",created_at,updated_at) VALUES
('es_ES',1,'LBL_IMS_INCIDENT_GROUP_TYPE_TRAFFIC_STATUS', 'Estado del tráfico', CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
('es_ES',1,'LBL_IMS_INCIDENT_GROUP_TYPE_WEATHER_DATA', 'Meteorológica', CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
('es_ES',1,'LBL_IMS_INCIDENT_GROUP_TYPE_ROAD_INCIDENTS', 'Incidente en la vía', CURRENT_TIMESTAMP,CURRENT_TIMESTAMP);

-- en_GB
INSERT INTO master.i18n_labels (locale_code,module_id,"label","translation",created_at,updated_at) VALUES
('en_GB',1,'LBL_IMS_INCIDENT_GROUP_TYPE_TRAFFIC_STATUS', 'Traffic status', CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
('en_GB',1,'LBL_IMS_INCIDENT_GROUP_TYPE_WEATHER_DATA', 'Weather data', CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
('en_GB',1,'LBL_IMS_INCIDENT_GROUP_TYPE_ROAD_INCIDENTS', 'Road incident', CURRENT_TIMESTAMP,CURRENT_TIMESTAMP);

--sk_SK
INSERT INTO master.i18n_labels (locale_code,module_id,"label","translation",created_at,updated_at) VALUES
('sk_SK',1,'LBL_IMS_INCIDENT_GROUP_TYPE_TRAFFIC_STATUS', 'Stav dopravy', CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
('sk_SK',1,'LBL_IMS_INCIDENT_GROUP_TYPE_ROAD_INCIDENTS', 'Cestná nehoda', CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
('sk_SK',1,'LBL_IMS_INCIDENT_GROUP_TYPE_WEATHER_DATA', 'typ incidentu - meteo', CURRENT_TIMESTAMP,CURRENT_TIMESTAMP);
