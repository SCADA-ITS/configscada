-- 
-- Table: conf.ims_incident_types
--

INSERT INTO conf.ims_incident_types (incident_type_id, ini_incident_state_id, alias, description, incident_level_id, default_user_id, enabled, visible, created_at, updated_at) VALUES
 
-- INCIDENCIA DE EXPLOTACIÓN
(1, 1, 'RAD-01', 'Person Detection',  1, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 1, 'RAD-02', 'Debris Detection',  1, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 1, 'RAD-03', 'Slow Vehicle Detection', 1, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 1, 'RAD-04', 'Stop Vehicle Detection',  1, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 1, 'RAD-05', 'Reversing Vehicle Detection',  1, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(6, 1, 'RAD-06', 'Queue of Vehicles Detection',  1, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(7, 1, 'VIS-01', 'Low Visibility',  1, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(8, 1, 'RAD-10', 'Person Detection PTZ',  1, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(9, 1, 'RAD-11', 'Person Detection VMS',  1, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
