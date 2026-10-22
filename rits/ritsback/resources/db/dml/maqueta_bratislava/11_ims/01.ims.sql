
--DELETE FROM static.ims_incident_type_states;


-- 
-- Table: static.ims_incident_type_states
--
INSERT INTO static.ims_incident_states (incident_state_id, "final", alias, description, label_alias, label_description, enabled, visible, created_at, updated_at) VALUES
(1, false, 'ON GOING', 'ON GOING', 'LBL_IMS_INCIDENT_STATE_ON_GOING', NULL, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, false, 'RESTITUTION WORKS', 'RESTITUTION WORKS', 'LBL_IMS_INCIDENT_STATE_RESTITUTION_WORKS', NULL, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(100, true, 'CLOSED', 'FINALIZED', 'LBL_IMS_INCIDENT_STATE_CLOSED', NULL, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: static.ims_incident_levels
--
INSERT INTO static.ims_incident_levels 
(incident_level_id, priority, alias, description, label_alias, label_description, enabled, visible, created_at, updated_at) VALUES
(1, 0, 'LEVEL_1', 'Stav dopravy', 'LBL_INCIDENT_LEVEL_1_TRAFFIC', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 0, 'LEVEL_1', 'typ incidentu - meteo', 'LBL_INCIDENT_LEVEL_1_WEATHER', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 0, 'LEVEL_1', 'Cestná nehoda', 'LBL_INCIDENT_LEVEL_1_ROAD', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
