DELETE FROM master.ims_incident_groups;

-- 
-- Table: conf.ims_incident_types
--
INSERT INTO master.ims_incident_groups (incident_group_id,alias,description,label_alias,label_description,enabled,visible,created_at,updated_at) VALUES
(1,'Traffic status','Traffic status','LBL_IMS_INCIDENT_GROUP_TYPE_TRAFFIC_STATUS', 'LBL_IMS_INCIDENT_GROUP_TYPE_TRAFFIC_STATUS_DESC',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
(2,'Weather data','Weather data','LBL_IMS_INCIDENT_GROUP_TYPE_WEATHER_DATA', 'LBL_IMS_INCIDENT_GROUP_TYPE_WEATHER_DATA_DESC',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
(3,'Road incidents','Road incidents','LBL_IMS_INCIDENT_GROUP_TYPE_ROAD_INCIDENTS', 'LBL_IMS_INCIDENT_GROUP_TYPE_ROAD_INCIDENTS_DESC',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP);


-- 
-- Table: conf.ims_incident_types
--
INSERT INTO conf.ims_incident_types (incident_type_id, ini_incident_state_id, incident_group_id, alias, description, incident_level_id, default_user_id, auto_confirm_incident, cancel_incident, valid_for_min, on_create_execute, enabled, visible, created_at, updated_at) VALUES
(101, 1, 1, 'D9', 'Tráfico lento',  2, 1, 30, false, 2880, 'BackOfficeInitIncident.groovy', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(201, 1, 2, 'E1-MET-01', 'Lluvia intensa, niebla, viento, nieve o hielo',  3, 1, 50, true, 2880, 'BackOfficeInitIncident.groovy', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(301, 1, 3, 'Corte_carril', 'Trabajos con corte de carril en túnel',  1, 1, null, false, 2880, 'BackOfficeInitIncident.groovy', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

