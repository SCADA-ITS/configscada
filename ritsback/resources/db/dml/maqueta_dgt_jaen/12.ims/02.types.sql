DELETE FROM master.ims_incident_groups;

-- 
-- Table: conf.ims_incident_types
--
INSERT INTO master.ims_incident_groups (incident_group_id,alias,description,label_alias,label_description,enabled,visible,created_at,updated_at) VALUES
(1,'Explotación','Incidencia de Explotación','LBL_IMS_INCIDENT_GROUP_LEVEL_0', 'LBL_IMS_INCIDENT_GROUP_LEVEL_0',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
(2,'Preemergencia','Preemergencia','LBL_IMS_INCIDENT_GROUP_LEVEL_1', 'LBL_IMS_INCIDENT_GROUP_LEVEL_1',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
(3,'Nivel 1','Emergencia de Nivel 1','LBL_IMS_INCIDENT_GROUP_LEVEL_2', 'LBL_IMS_INCIDENT_GROUP_LEVEL_2',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
(4,'Nivel 2','Emergencia de Nivel 2','LBL_IMS_INCIDENT_GROUP_LEVEL_3', 'LBL_IMS_INCIDENT_GROUP_LEVEL_3',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP);

-- 
-- Table: conf.ims_incident_types
--
