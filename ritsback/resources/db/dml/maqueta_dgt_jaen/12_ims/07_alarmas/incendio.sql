--
-- Table: conf.ims_incident_alarm_conditions
--

-- Para el id seguiremos el siguiente criterio:
-- 1.- Id incident_type
-- 2.- Orden (2 cifras)
-- 3.- stretch_id (4 cifras)
INSERT INTO conf.ims_incident_alarm_conditions (incident_alarm_condition_id, incident_type_id, stretch_id, auto, road_impact_id, enabled, visible, created_at, updated_at)
VALUES
--incendio
(10115011001, 101, 1003, true, 10, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(10115021001, 101, 1004, true, 10, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(10115012001, 101, 2003, true, 10, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(10115022001, 101, 2004, true, 10, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),


(10115013001, 101, 3003, true, 10, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(10115023001, 101, 3004, true, 10, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: conf.ims_incident_alarm_condition_alarms
--
-- Para el id seguiremos el siguiente criterio:
-- 1.- Id ims_incident_alarm_conditions
-- 2.- Orden (2 cifras)
INSERT INTO conf.ims_incident_alarm_condition_alarms (incident_alarm_condition_alarm_id, incident_alarm_condition_id, incident_alarm_condition_alarm_type_id, alarm_config_id, element_type_id, element_id, min_alarmed_elements, enabled, visible, created_at, updated_at)
VALUES
(10115011001, 10115011001, 2, 20015, 15, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(10115021001, 10115021001, 2, 20015, 15, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(10115012001, 10115012001, 2, 20015, 15, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(10115022001, 10115022001, 2, 20015, 15, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--EL CORZO
(10115013001, 10115013001, 2, 20015, 15, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(10115023001, 10115023001, 2, 20015, 15, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


