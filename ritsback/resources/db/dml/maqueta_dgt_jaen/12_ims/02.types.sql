DELETE FROM master.ims_incident_groups;

-- 
-- Table: conf.ims_incident_types
--
INSERT INTO master.ims_incident_groups (incident_group_id,alias,description,label_alias,label_description,enabled,visible,created_at,updated_at) VALUES
(1,'Incidentes de tráfico','Incidentes de tráfico','LBL_IMS_INCIDENT_GROUP_TYPE_ROAD_INCIDENTS', 'LBL_IMS_INCIDENT_GROUP_TYPE_ROAD_INCIDENTS_DESC',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP);

-- 
-- Table: conf.ims_incident_types
--
INSERT INTO conf.ims_incident_types (incident_type_id, ini_incident_state_id, incident_group_id, alias, description, incident_level_id, default_user_id, auto_confirm_incident, cancel_incident, valid_for_min, on_create_execute, enabled, visible, created_at, updated_at) VALUES
(101, 1, 1, 'F9', 'Incendio',  4, 1001, 180, false, 2880, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(201, 1, 1, 'F13', 'Alarma de CO',  4, 1001, 180, false, 2880, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(301, 1, 1, 'F12', 'Alarma de opacidad',  4, 1001, 180, false, 2880, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
