--
-- Table: conf.ims_incident_alarm_conditions
--

-- Para el id seguiremos el siguiente criterio:
-- 1.- Id incident_type
-- 2.- Orden (2 cifras)
-- 3.- stretch_id (4 cifras)
INSERT INTO conf.ims_incident_alarm_conditions (incident_alarm_condition_id, incident_type_id, stretch_id, auto, enabled, visible, created_at, updated_at)
VALUES
--trafico lento
(101011019, 101, 1019, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(101011020, 101, 1020, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.ims_incident_alarm_condition_alarms (incident_alarm_condition_alarm_id, incident_alarm_condition_id, incident_alarm_condition_alarm_type_id, alarm_config_id, element_type_id, element_id, min_alarmed_elements, enabled, visible, created_at, updated_at)
VALUES
---------------------
--- Trafico lento ---
---------------------

(10101101901, 101011019, 2, 3100004, null, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(10101102001, 101011020, 2, 3100004, null, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
