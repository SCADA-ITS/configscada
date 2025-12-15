-- 
-- Table: static.ims_incident_type_states
--
INSERT INTO static.ims_incident_states (incident_state_id, final, alias, description, label_alias, label_description, enabled, visible, created_at, updated_at)
VALUES(1, false, 'INITIAL', 'INITIAL', 'LBL_IMS_INCIDENT_STATE_INITIAL', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, false, 'ON GOING', 'ON GOING', 'LBL_IMS_INCIDENT_STATE_ON_GOING', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, false, 'RESTITUTION WORKS', 'RESTITUTION WORKS', 'LBL_IMS_INCIDENT_STATE_RESTITUTION_WORKS', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(100, true, 'CLOSED', 'FINALIZED', 'LBL_IMS_INCIDENT_STATE_CLOSED', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: static.ims_incident_levels
--
INSERT INTO static.ims_incident_levels 
(incident_level_id, priority, alias, description, label_alias, label_description, enabled, visible, created_at, updated_at) VALUES
(1, 0, 'Explotación', 'Explotación', 'Explotación', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 1, 'Emergencia', 'Emergencia', 'Emergencia', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 2, 'Eventos', 'Eventos', 'Eventos', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
