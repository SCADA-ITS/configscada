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
(101011043, 101, 1043, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(101011044, 101, 1044, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- E1-MET-01
(201011042, 201, 1042, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(201021042, 201, 1042, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(201031042, 201, 1042, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);



-- 
-- Table: conf.ims_incident_alarm_condition_alarms
--
-- Para el id seguiremos el siguiente criterio:
-- 1.- Id ims_incident_alarm_conditions
-- 2.- Orden (2 cifras)
INSERT INTO conf.ims_incident_alarm_condition_alarms (incident_alarm_condition_alarm_id, incident_alarm_condition_id, incident_alarm_condition_alarm_type_id, alarm_config_id, element_type_id, element_id, min_alarmed_elements, enabled, visible, created_at, updated_at)
VALUES
---------------------
--- Trafico lento ---
---------------------

(10101104301, 101011043, 2, 3100004, null, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(10101104401, 101011044, 2, 3100004, null, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),


----------------
--- E1-MET-01 ---
----------------
-- RV-MET-1MA, Vientos fuertes(>80km/h)
(20101104201, 201011042, 2, 3030001, null, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- RV-MET-1MA, Baja Temperatura (<2 C)
(20102104201, 201021042, 2, 3040001, null, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- RV-MET-1MA, Balsas en la calzada
(20103104201, 201031042, 2, 3050001, null, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);










