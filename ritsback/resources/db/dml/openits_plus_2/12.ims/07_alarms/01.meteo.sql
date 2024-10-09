--
-- Table: conf.ims_incident_alarm_conditions
--

-- Para el id seguiremos el siguiente criterio:
-- 1.- Id incident_type
-- 2.- Orden (2 cifras)
-- 3.- stretch_id (4 cifras)
INSERT INTO conf.ims_incident_alarm_conditions (incident_alarm_condition_id, incident_type_id, stretch_id, auto, enabled, visible, created_at, updated_at)
VALUES
-- E1-MET-01
(201011000, 201, 4000, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
--(201021000, 201, 4000, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(201031000, 201, 4000, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


-- 
-- Table: conf.ims_incident_alarm_condition_alarms
--
-- Para el id seguiremos el siguiente criterio:
-- 1.- Id ims_incident_alarm_conditions
-- 2.- Orden (2 cifras)
INSERT INTO conf.ims_incident_alarm_condition_alarms (incident_alarm_condition_alarm_id, incident_alarm_condition_id, incident_alarm_condition_alarm_type_id, alarm_config_id, element_type_id, element_id, min_alarmed_elements, enabled, visible, created_at, updated_at)
VALUES
----------------
--- E1-MET-01 ---
----------------
--Vientos fuertes(>80km/h)
(20101100001, 201011000, 2, 3030001, null, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
--Baja Temperatura (<2 C)
--(20102100001, 201021000, 2, 3040001, null, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
--Balsas en la calzada
(20103100001, 201031000, 2, 3050001, null, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);







