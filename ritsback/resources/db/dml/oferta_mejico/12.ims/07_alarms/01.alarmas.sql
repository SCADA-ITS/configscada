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
(101011001, 101, 1000, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(101011002, 101, 1001, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(101011003, 101, 1002, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(101011004, 101, 1003, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),


-- E1-MET-01
(201011000, 201, 4001, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(201021000, 201, 4002, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


-- 
-- Table: conf.ims_incident_alarm_condition_alarms
--
 --Para el id seguiremos el siguiente criterio:
-- 1.- Id ims_incident_alarm_conditions
 --2.- Orden (2 cifras)
INSERT INTO conf.ims_incident_alarm_condition_alarms (incident_alarm_condition_alarm_id, incident_alarm_condition_id, incident_alarm_condition_alarm_type_id, alarm_config_id, element_type_id, element_id, min_alarmed_elements, enabled, visible, created_at, updated_at)
VALUES
---------------------
--- Trafico lento ---
---------------------

(10101100101, 101011001, 2, 3100004, null, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(10101100201, 101011002, 2, 3100004, null, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(10101100301, 101011003, 2, 3100004, null, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(10101100401, 101011004, 2, 3100004, null, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),


----------------
--- E1-MET-01 ---
----------------
-- RV-MET-1MA, Vientos fuertes(>80km/h)
(20101100001, 201011000, 2, 3030001, null, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- RV-MET-1MA, Baja Temperatura (<2 C)
(20104100001, 201021000, 2, 3030001, null, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);








