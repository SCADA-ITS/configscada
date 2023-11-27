-- 
-- Table: static.ims_incident_type_states
--
INSERT INTO static.ims_incident_states (incident_state_id, final, alias, description, label_alias, label_description, enabled, visible, created_at, updated_at)
VALUES
(1, false, 'INITIAL', 'INITIAL', 'LBL_IMS_INCIDENT_STATE_INITIAL', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, false, 'AVISO EMERGENCIAS', 'AVISO EMERGENCIAS', 'LBL_IMS_INCIDENT_STATE_AVISO_EMERGENCIAS', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, false, 'RESTITUTION WORKS', 'RESTITUTION WORKS', 'LBL_IMS_INCIDENT_STATE_RESTITUTION_WORKS', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(100, true, 'CLOSED', 'FINALIZED', 'LBL_IMS_INCIDENT_STATE_CLOSED', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: static.ims_incident_levels
--
INSERT INTO static.ims_incident_levels 
(incident_level_id, priority, alias, description, label_alias, label_description, enabled, visible, created_at, updated_at) VALUES
(1, 0, 'LEVEL_0', 'Incidencia de Explotación', 'LBL_INCIDENT_LEVEL_0', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 1, 'LEVEL_1', 'Preemergencia', 'LBL_INCIDENT_LEVEL_1', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 2, 'LEVEL_2', 'Emergencia de Nivel 1', 'LBL_INCIDENT_LEVEL_2', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 3, 'LEVEL_3', 'Emergencia de Nivel 2', 'LBL_INCIDENT_LEVEL_3', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
