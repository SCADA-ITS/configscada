DELETE FROM master.ims_incident_groups;

-- 
-- Table: conf.ims_incident_types
--
INSERT INTO master.ims_incident_groups (incident_group_id,alias,description,label_alias,label_description,enabled,visible,created_at,updated_at) VALUES
(1,'INCIDENCIA EN CARRETERA','INCIDENCIA EN CARRETERA','LBL_IMS_INCIDENT_GROUP_TYPE_TRAFFIC_STATUS', 'LBL_IMS_INCIDENT_GROUP_TYPE_TRAFFIC_STATUS_DESC',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP);

 
-- 
-- Table: conf.ims_incident_types
--
INSERT INTO conf.ims_incident_types (incident_type_id, ini_incident_state_id, incident_group_id, alias, description, incident_level_id, default_user_id, auto_confirm_incident, cancel_incident, valid_for_min, on_create_execute, enabled, visible, created_at, updated_at) VALUES
(101, 1, 1, 'Asistencia en carretera', 'Asistencia en carretera',  2, 1, 30, false, 2880, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(201, 1, 1, 'Vehículo detenido', 'Vehículo detenido',  3, 1, 50, true, 2880, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(301, 1, 1, 'Prueba deportiva', 'Prueba deportiva',  1, 1, null, false, 2880, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(401, 1, 1, 'Obras', 'Obras',  1, 1, null, false, 2880, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(501, 1, 1, 'Vehículos lentos', 'Vehículos lentos',  1, 1, null, false, 2880, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

