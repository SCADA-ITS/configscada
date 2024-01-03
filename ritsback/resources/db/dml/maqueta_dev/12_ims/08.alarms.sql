--
-- Table: conf.ims_incident_alarm_conditions
--

INSERT INTO conf.ims_incident_alarm_conditions (incident_alarm_condition_id, incident_type_id, stretch_id, auto, enabled, visible, created_at, updated_at)
VALUES
(1, 1, 3, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 2, 3, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 3, 10, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- P-MET-01
(701, 7, 1000, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(702, 1, 1000, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


-- 
-- Table: conf.ims_incident_alarm_condition_alarms
--
/*INSERT INTO conf.ims_incident_alarm_condition_alarms (incident_alarm_condition_alarm_id, incident_alarm_condition_id, incident_alarm_condition_alarm_type_id, alarm_config_id, element_type_id, element_id, min_alarmed_elements, enabled, visible, created_at, updated_at)
VALUES
(1, 1, 2, 30009, null, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 2, 2, 30009, null, null, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 3, 3, 30009, null, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(701, 701, 2, 49, null, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(702, 702, 2, 55, NULL, NULL, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);*/



