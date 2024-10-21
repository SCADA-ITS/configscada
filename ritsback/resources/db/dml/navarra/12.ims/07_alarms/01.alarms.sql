--
-- Table: conf.ims_incident_alarm_conditions
--
INSERT INTO conf.ims_incident_alarm_conditions (incident_alarm_condition_id, incident_type_id, stretch_id, auto, enabled, visible, created_at, updated_at)
VALUES
(010101, 101, 1000, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(010201, 201, 1000, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(010301, 301, 1000, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


-- 
-- Table: conf.ims_incident_alarm_condition_alarms
--
INSERT INTO conf.ims_incident_alarm_condition_alarms (incident_alarm_condition_alarm_id, incident_alarm_condition_id, incident_alarm_condition_alarm_type_id, alarm_config_id, element_type_id, element_id, min_alarmed_elements, enabled, visible, created_at, updated_at)
VALUES
(010101, 010101, 2, 3000002, null, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(010201, 010201, 2, 3010002, null, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(010301, 010301, 2, 3020002, null, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);







