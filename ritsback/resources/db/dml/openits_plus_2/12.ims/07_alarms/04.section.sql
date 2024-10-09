INSERT INTO conf.ims_incident_alarm_conditions (incident_alarm_condition_id, incident_type_id, stretch_id, auto, enabled, visible, created_at, updated_at)
VALUES
--trafico lento
(101011001, 101, 4001, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(101011002, 101, 4002, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.ims_incident_alarm_condition_alarms (incident_alarm_condition_alarm_id, incident_alarm_condition_id, incident_alarm_condition_alarm_type_id, alarm_config_id, element_type_id, element_id, min_alarmed_elements, enabled, visible, created_at, updated_at)
VALUES
---------------------
--- Trafico lento ---
---------------------

(10101100101, 101011001, 2, 3100004, null, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(10101100201, 101011002, 2, 3100004, null, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);



