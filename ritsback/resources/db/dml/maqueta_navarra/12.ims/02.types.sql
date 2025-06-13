DELETE FROM master.ims_incident_groups;

-- 
-- Table: conf.ims_incident_types
--
INSERT INTO master.ims_incident_groups (incident_group_id,alias,description,label_alias,label_description,enabled,visible,created_at,updated_at) VALUES
(1,'Actividades deportivas','Actividades deportivas','LBL_IMS_INCIDENT_GROUP_TYPE_SPORT_EVENT', 'LBL_IMS_INCIDENT_GROUP_TYPE_SPORT_EVENT_DESC',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
(2,'Actividades','Actividades','LBL_IMS_INCIDENT_GROUP_TYPE_ACTIVITIES', 'LBL_IMS_INCIDENT_GROUP_TYPE_ACTIVITIES_DESC',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
(3,'Rodaje','Rodaje','LBL_IMS_INCIDENT_GROUP_TYPE_RODAJE', 'LBL_IMS_INCIDENT_GROUP_TYPE_RODAJE_DESC',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
(4,'Accidente','Accidente','LBL_IMS_INCIDENT_GROUP_TYPE_ACCIDENT', 'LBL_IMS_INCIDENT_GROUP_TYPE_ACCIDENT_DESC',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP);

 
-- 
-- Table: conf.ims_incident_types
--
INSERT INTO conf.ims_incident_types (incident_type_id, ini_incident_state_id, incident_group_id, alias, description, incident_level_id, default_user_id, auto_confirm_incident, cancel_incident, valid_for_min, on_create_execute, enabled, visible, created_at, updated_at) VALUES
(101, 1, 1, 'DEP-001', 'Atletismo - Carrera a pie o por asfalto',  1, 1, null, false, 2880, 'BackOfficeInitIncident.groovy', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(102, 1, 1, 'DEP-002', 'Atletismo - Carrera por montaña o campo a través',  1, 1, null, false, 2880, 'BackOfficeInitIncident.groovy', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(103, 1, 1, 'DEP-003', 'Atletismo - Carrera sobre patines',  1, 1, null, false, 2880, 'BackOfficeInitIncident.groovy', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(104, 1, 1, 'DEP-004', 'Ciclismo - Carrera ciclista',  1, 1, null, false, 2880, 'BackOfficeInitIncident.groovy', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(105, 1, 1, 'DEP-005', 'Ciclismo - Ciclodeportivo',  1, 1, null, false, 2880, 'BackOfficeInitIncident.groovy', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(106, 1, 1, 'DEP-006', 'Ciclismo - Cicloturista',  1, 1, null, false, 2880, 'BackOfficeInitIncident.groovy', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(107, 1, 1, 'DEP-007', 'Triatlon - Triatlón y similares',  1, 1, null, false, 2880, 'BackOfficeInitIncident.groovy', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(108, 1, 1, 'DEP-008', 'Automovilismo - Carrera con vehículos a motor',  1, 1, null, false, 2880, 'BackOfficeInitIncident.groovy', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(201, 1, 2, 'ACT-001', 'Vehículos históricos',  1, 1, null, false, 2880, 'BackOfficeInitIncident.groovy', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(202, 1, 2, 'ACT-002', 'Festejo popular',  1, 1, null, false, 2880, 'BackOfficeInitIncident.groovy', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(203, 1, 2, 'ACT-003', 'Transhumancia - Otros con animales',  1, 1, null, false, 2880, 'BackOfficeInitIncident.groovy', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(301, 1, 3, 'ROD-001', 'Rodaje',  1, 1, null, false, 2880, 'BackOfficeInitIncident.groovy', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(401, 1, 4, 'ACC-001', 'Accidente', 1,1, null, false, 2880, 'BackOfficeInitIncident.groovy', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

