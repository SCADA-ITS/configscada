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
INSERT INTO conf.ims_incident_types (incident_type_id, ini_incident_state_id, incident_group_id, alias, description, incident_level_id, default_user_id, enabled, visible, created_at, updated_at) VALUES
 
-- INCIDENCIA DE EXPLOTACIÓN
(3, 1, 1, 'I-TRA-01', 'Túnel en pruebas en remoto',  1, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 1, 1, 'I-TRA-02', 'Trabajos con señalización móvil',  1, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 1, 1, 'I-TRA-03', 'Trabajos con corte de arcén en túnel', 1, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(6, 1, 1, 'I-TRA-04', 'Trabajos con corte de carril en túnel',  1, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(47, 1, 1, 'I-TRA-05', 'Trabajos con cierre de tubo y transfer',  1, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

 -- PREEMERGENCIA
(7, 1, 2, 'P-MET-01', 'Presencia de fenómenos meteorológicos adversos.',  2, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(8, 1, 2, 'P-AMB-01', 'Alta concentración de CO y/o NOx.',  2, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(9, 1, 2, 'P-AMB-02', 'Visibilidad Reducida (humos y/o polvo)',  2, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(10, 1, 2, 'P-TRA-01', 'Retirada de un extintor.',  2, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(11, 1, 2, 'P-TRA-02', 'Llamada desde un Poste SOS.',  2, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(12, 1, 2, 'P-TRA-03', 'Vehículo circulando a velocidad inadecuada(lento/velocidad excesiva).',  2, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(13, 1, 2, 'P-TRA-04', 'Obstáculo/s en la calzada permitiendo el paso.',  2, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(14, 1, 2, 'P-TRA-05', 'Vehículo/s detenido/s en la calzada permitiendo el paso.',  2, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(15, 1, 2, 'P-TRA-06', 'Presencia de peatones, bicicletas o vehículos de tracción animal en el interior del túnel o en los tramos de acceso.',  2, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(17, 1, 2, 'P-TRA-08', 'Retenciones en el interior del túnel.',  2, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(18, 1, 2, 'P-TRA-09', 'Paso de vehículos especiales con autorización.',  2, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(20, 1, 2, 'P-AVE-02', 'Filtraciones de agua (Pavimento Húmedo)',  2, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- EMERGENCIA NIVEL 1
(21, 1, 3, 'E1-MET-01', 'Lluvia intensa, niebla, viento, nieve o hielo',  3, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(22, 1, 3, 'E1-AMB-01', 'Excesiva concentración de CO y/o NOx',  3, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(23, 1, 3, 'E1-AMB-02', 'Excesivo nivel de opacidad',  3, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(24, 1, 3, 'E1-TRA-01', 'Presencia de animales sueltos', 3, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(25, 1, 3, 'E1-TRA-02', 'Derrame de líquidos (firme deslizante).',  3, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(26, 1, 3, 'E1-TRA-03', 'Conducción temeraria.',  3, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(27, 1, 3, 'E1-TRA-04', 'Obstáculo en la calzada obstruyendo parcialmente el paso.',  3, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(28, 1, 3, 'E1-TRA-05', 'Vehículo/s detenido/s en la calzada obstruyendo parcialmente el paso.',  3, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(30, 1, 3, 'E1-ACC-01', 'Accidente que afecta a un carril de circulación sin implicación de SSPP.',  3, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(31, 1, 3, 'E1-AVE-01', 'Condiciones de servicio degradado - Nivel 1.',  3, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(32, 1, 3, 'E1-ILI-01', 'Intrusión en dependencias del túnel: galerías de evacuación, locales técnicos, etc.',  3, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- EMERGENCIA NIVEL 2
(33, 1, 4, 'E2-TRA-01', 'Vehículo u obstáculo en la calzada obstruyendo totalmente el paso.',  4, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(34, 1, 4, 'E2-ACC-01', 'Accidente que afecta a dos carriles de circulación sin implicación de SSPP.',  4, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(35, 1, 4, 'E2-ACC-02', 'Accidente con implicación de SSPP, sin fuga, derrame, incendio o explosión.',  4, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(36, 1, 4, 'E2-ACC-03', 'Accidente con implicación de SSPP, con fuga, derrame, incendio o explosión.',  4, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(37, 1, 4, 'E2-ILI-01', 'Vehículo u objeto sospechoso en interior de túnel.',  4, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(38, 1, 4, 'E2-ILI-02', 'Amenaza de bomba o amenaza de atentado.',  4, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(39, 1, 4, 'E2-ILI-03', 'Sabotajes, manifestación o desórdenes públicos.',  4, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(40, 1, 4, 'E2-AVE-01', 'Condiciones de servicio degradado - Nivel 2.',  4, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(41, 1, 4, 'E2-AVE-02', 'Inundación en túnel.',  4, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(42, 1, 4, 'E2-RUI-01', 'Socavones en la calzada.',  4, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(43, 1, 4, 'E2-RUI-02', 'Daños estructurales con riesgo inminente.',  4, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(44, 1, 4, 'E2-FUE-01', 'Fuego en instalación.',  4, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(45, 1, 4, 'E2-FUE-02', 'Fuego en vehículo con o sin implicación de MMPP.',  4, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
