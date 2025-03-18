insert into incidents_gip.incidents
(incident_report_id, type_detection, source_id, element_id, incident_type, incident_sub_type, level, km_start, km_end, user_n, phone, comment) values
(100, 'LBL_AUTO', 1, 48, 'LBL_IMS_INCIDENT_GROUP_TYPE_ACCIDENTE', 'A01', 'LBL_INCIDENT_LEVEL_3', '25.9', '27.4', 'Mario Rivera', '123456789', 'Descripción incidente recibido de Scada -> 29455. 

Informan de colisión por alcance entre bus Transantiago y camión 3/4, en pista de salida 5. C.Empresarial. 

A la llegada de los móviles se retira Bus Transantiago. 

No se toman antecedentes. A posterior fueron entregados en forma parcial por conductor de camión. 

Se informa a Héctor Rosales. 

Paramedicos dde M1 y M2. Informan en evaluación primaria, que se encuentran sin lesionados. Se deja negativa A1. A las 09:32. Llegada de bomberos. Voluntario a cargo Marco Espinoza. 6°Cia. de Conchali. Móviles
R6 y R3.');

insert into incidents_gip.vehicles
(incident_id, vehicle_type_id, vehicle_brand_id, vehicle_model_id, patent, color, vehicle_damage_id, attended_id, assistance_type_id, transfer_place_id, comment) values 
(1, 2, 78, NULL, 'LWTK-52', NULL, NULL, NULL, NULL, NULL,'Bus Transantiago. Se retira antes de la llegada de los móviles. Conductor: Antonio Miranda'),
(1, 3, 80, 1009, 'KTVT-59', 'Blanco', NULL, NULL, NULL, NULL,'Conductor: Cristopher De La Rosa');

insert into incidents_gip.participants
(incident_id, name, rut, address, phone, age, gender_id, stage_id, user_type_id, injury_type_id, injury_level_id, participant_assistance_type_id, participant_transfer_place_id) values 
(1, 'Cristopher De La Rosa Miño', '19915.370-6', 'Monseñor La Rama 4442', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(1, 'Antonio Miranda', 'Sin datos', 'Sin datos', 'Sin datos', NULL, 2, NULL, 2, NULL, NULL, NULL, NULL);

insert into incidents_gip.unavailable_lanes
(incident_id, comment, lane_action_id, lane_id, start_action) values
(1, NULL, 1, 1, '2024-08-05 10:00:00.000 +0200'),
(1, NULL, 2, 1, '2024-08-05 10:20:00.000 +0200');

insert into incidents_gip.assigned_resources
(incident_id, action_id, typology_id, resource_id, notice_date, arrival_date, release_date, comment) values
(1, 2, 2, 5, '2024-08-05 10:00:00.000 +0200', '2024-08-05 10:20:00.000 +0200', '2024-08-05 10:40:00.000 +0200', '1-1'),
(1, 2, 1, 2, '2024-08-05 10:00:00.000 +0200', '2024-08-05 10:20:00.000 +0200', '2024-08-05 10:40:00.000 +0200', 'G1.'),
(1, 2, 1, 3, '2024-08-05 10:00:00.000 +0200', '2024-08-05 10:20:00.000 +0200', '2024-08-05 10:40:00.000 +0200', 'M1.'),
(1, 2, 1, 4, '2024-08-05 10:00:00.000 +0200', '2024-08-05 10:20:00.000 +0200', '2024-08-05 10:40:00.000 +0200', 'M2.'),
(1, 2, 1, 1, '2024-08-05 10:00:00.000 +0200', '2024-08-05 10:20:00.000 +0200', '2024-08-05 10:40:00.000 +0200', 'A1.'),
(1, 2, 1, 7, '2024-08-05 10:00:00.000 +0200', '2024-08-05 10:20:00.000 +0200', '2024-08-05 10:40:00.000 +0200', 'R2.');
			
insert into incidents_gip.interrupt_agents
(incident_id, agents_id, inicio, final, comment) values
(1, 1, '2024-08-05 10:20:00.000 +0200', '2024-08-05 10:40:00.000 +0200', 'Voluntario a cargo: Marco Espinoza');

