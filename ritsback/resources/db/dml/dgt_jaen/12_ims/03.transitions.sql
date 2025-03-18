-- 
-- Table: onf.ims_incident_type_state_transitions 
--

INSERT INTO conf.ims_incident_type_state_transitions (incident_type_id, ini_incident_state_id, end_incident_state_id, auto, enabled, visible, created_at, updated_at) VALUES
(101, 1, 3, false, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),(101, 3, 100, false, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(201, 1, 3, false, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),(201, 3, 100, false, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(301, 1, 3, false, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),(301, 3, 100, false, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
