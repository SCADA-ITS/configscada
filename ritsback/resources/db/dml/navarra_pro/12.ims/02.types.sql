DELETE FROM master.ims_incident_groups;

-- 
-- Table: conf.ims_incident_types
--
INSERT INTO master.ims_incident_groups (incident_group_id,alias,description,label_alias,label_description,enabled,visible,created_at,updated_at) VALUES
(1,'Actividades deportivas','Actividades deportivas','LBL_IMS_INCIDENT_GROUP_TYPE_SPORT_EVENT', 'LBL_IMS_INCIDENT_GROUP_TYPE_SPORT_EVENT_DESC',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
(2,'Actividades','Actividades','LBL_IMS_INCIDENT_GROUP_TYPE_ACTIVITIES', 'LBL_IMS_INCIDENT_GROUP_TYPE_ACTIVITIES_DESC',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
(3,'Rodaje','Rodaje','LBL_IMS_INCIDENT_GROUP_TYPE_RODAJE', 'LBL_IMS_INCIDENT_GROUP_TYPE_RODAJE_DESC',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
(4,'Tráfico','Tráfico','LBL_IMS_INCIDENT_GROUP_TYPE_TRAFFIC', 'LBL_IMS_INCIDENT_GROUP_TYPE_TRAFFIC_DESC',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
(5,'Meteorológicas','Meteorológicas','LBL_IMS_INCIDENT_GROUP_TYPE_WEATHER', 'LBL_IMS_INCIDENT_GROUP_TYPE_WEATHER_DESC',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
(6,'Varios','Varios','LBL_IMS_INCIDENT_GROUP_TYPE_SEVERAL', 'LBL_IMS_INCIDENT_GROUP_TYPE_SEVERAL_DESC',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP);

 
-- 
-- Table: conf.ims_incident_types
--
INSERT INTO conf.ims_incident_types (incident_type_id, ini_incident_state_id, incident_group_id, alias, description, incident_level_id, default_user_id, auto_confirm_incident, cancel_incident, valid_for_min, on_create_execute, enabled, visible, created_at, updated_at) VALUES
(101, 1, 1, 'DEP-001', 'Atletismo - Carrera a pie o por asfalto',  3, 1, null, false, null, 'BackOfficeInitIncident.groovy', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(102, 1, 1, 'DEP-002', 'Atletismo - Carrera por montaña o campo a través',  3, 1, null, false, null, 'BackOfficeInitIncident.groovy', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(103, 1, 1, 'DEP-003', 'Atletismo - Carrera sobre patines',  3, 1, null, false, null, 'BackOfficeInitIncident.groovy', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(104, 1, 1, 'DEP-004', 'Ciclismo - Carrera ciclista',  3, 1, null, false, null, 'BackOfficeInitIncident.groovy', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(105, 1, 1, 'DEP-005', 'Ciclismo - Ciclodeportivo',  3, 1, null, false, null, 'BackOfficeInitIncident.groovy', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(106, 1, 1, 'DEP-006', 'Ciclismo - Cicloturista',  3, 1, null, false, null, 'BackOfficeInitIncident.groovy', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(107, 1, 1, 'DEP-007', 'Triatlon - Triatlón y similares',  3, 1, null, false, null, 'BackOfficeInitIncident.groovy', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(108, 1, 1, 'DEP-008', 'Automovilismo - Carrera con vehículos a motor',  3, 1, null, false, null, 'BackOfficeInitIncident.groovy', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(201, 1, 2, 'ACT-001', 'Vehículos históricos',  3, 1, null, false, null, 'BackOfficeInitIncident.groovy', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(202, 1, 2, 'ACT-002', 'Festejo popular',  3, 1, null, false, null, 'BackOfficeInitIncident.groovy', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(203, 1, 2, 'ACT-003', 'Transhumancia - Otros con animales',  3, 1, null, false, null, 'BackOfficeInitIncident.groovy', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(301, 1, 3, 'ROD-001', 'Rodaje',  3, 1, null, false, null, 'BackOfficeInitIncident.groovy', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(401, 1, 4, 'TRA-001', 'Accidente', 2, 1, null, false, null, 'BackOfficeInitIncident.groovy', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(402, 1, 4, 'TRA-002', 'Accidente con implicación de sustancias peligrosas', 2, 1, null, false, null, 'BackOfficeInitIncident.groovy', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(403, 1, 4, 'TRA-003', 'Conducción temeraria', 2, 1, null, false, null, 'BackOfficeInitIncident.groovy', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(404, 1, 4, 'TRA-004', 'Congestión', 1, 1, null, false, null, 'BackOfficeInitIncident.groovy', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(405, 1, 4, 'TRA-005', 'Derrame de líquidos - firme deslizante', 1, 1, null, false, null, 'BackOfficeInitIncident.groovy', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(406, 1, 4, 'TRA-006', 'Obstáculos en la calzada', 1, 1, null, false, null, 'BackOfficeInitIncident.groovy', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(407, 1, 4, 'TRA-007', 'Presencia de animales sueltos', 1, 1, null, false, null, 'BackOfficeInitIncident.groovy', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(408, 1, 4, 'TRA-008', 'Vehículo en sentido contrario', 2, 1, null, false, null, 'BackOfficeInitIncident.groovy', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(409, 1, 4, 'TRA-009', 'Vehículo circulando a velocidad inadecuada - lento o velocidad excesiva', 1, 1, null, false, null, 'BackOfficeInitIncident.groovy', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(410, 1, 4, 'TRA-010', 'Vehículos detenidos en la calzada', 1, 1, null, false, null, 'BackOfficeInitIncident.groovy', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(501, 1, 5, 'MET-001', 'Humo, polvo o niebla', 1, 1, null, false, null, 'BackOfficeInitIncident.groovy', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(502, 1, 5, 'MET-002', 'Helada o hielo', 1, 1, null, false, null, 'BackOfficeInitIncident.groovy', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(503, 1, 5, 'MET-003', 'Lluvia o granizo', 1, 1, null, false, null, 'BackOfficeInitIncident.groovy', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(504, 1, 5, 'MET-004', 'Nevada', 2, 1, null, false, null, 'BackOfficeInitIncident.groovy', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(505, 1, 5, 'MET-005', 'Viento', 1, 1, null, false, null, 'BackOfficeInitIncident.groovy', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(506, 1, 5, 'MET-006', 'Inundación', 2, 1, null, false, null, 'BackOfficeInitIncident.groovy', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(601, 1, 6, 'VAR-001', 'Obras', 1, 1, null, false, null, 'BackOfficeInitIncident.groovy', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(602, 1, 6, 'VAR-002', 'Señalización', 1, 1, null, false, null, 'BackOfficeInitIncident.groovy', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(603, 1, 6, 'VAR-003', 'Limpieza calzada', 1, 1, null, false, null, 'BackOfficeInitIncident.groovy', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(604, 1, 6, 'VAR-004', 'Incendio forestal', 2, 1, null, false, null, 'BackOfficeInitIncident.groovy', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(605, 1, 6, 'VAR-005', 'Otros', 1, 1, null, false, null, 'BackOfficeInitIncident.groovy', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


