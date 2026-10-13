--
-- Table: conf.ims_incident_alarm_conditions
--

INSERT INTO conf.ims_incident_alarm_conditions (incident_alarm_condition_id, incident_type_id, stretch_id, auto, enabled, visible, created_at, updated_at)VALUES
(001065, 65, 213, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: conf.ims_incident_alarm_condition_alarms
--
INSERT INTO conf.ims_incident_alarm_condition_alarms (incident_alarm_condition_alarm_id, incident_alarm_condition_id, incident_alarm_condition_alarm_type_id, alarm_config_id, element_type_id, element_id, enabled, visible, created_at, updated_at)VALUES
(001065, 1065, 2, 3040001, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

