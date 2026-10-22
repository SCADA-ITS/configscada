--
-- Table: conf.ims_incident_alarm_conditions
--

-- Para el id seguiremos el siguiente criterio:
-- 1.- Id incident_type
-- 2.- Orden (2 cifras)
-- 3.- stretch_id (4 cifras)
INSERT INTO conf.ims_incident_alarm_conditions (incident_alarm_condition_id, incident_type_id, stretch_id, auto, enabled, visible, created_at, updated_at)
VALUES

-- P-MET-01
(7011000, 7, 1000, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- Visibilidad reducida(<300m)
-- (702, 7, 1000, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- Calzada mojada
-- (705, 7, 1000, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(7021000, 7, 1000, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(7031000, 7, 1000, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- P-AMB-01
(8011001, 8, 1001, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(8021001, 8, 1001, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(8011002, 8, 1002, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(8021002, 8, 1002, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- P-AMB-02
(9011001, 9, 1001, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(9011002, 9, 1002, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- P-TRA-08
(17011001, 17, 1001, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(17021001, 17, 1001, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(17011002, 17, 1002, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(17021002, 17, 1002, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- E1-MET-01
(21011000, 21, 1000, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(21021000, 21, 1000, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(21031000, 21, 1000, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- E1-AMB-01
(22011001, 22, 1001, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(22021001, 22, 1001, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(22011002, 22, 1002, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(22021002, 22, 1002, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- E1-AMB-02
(23011001, 23, 1001, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(23011002, 23, 1002, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--E1-AVE-01
(31011000, 31, 1000, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(31021000, 31, 1000, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(31031000, 31, 1000, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(31041000, 31, 1000, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(31051000, 31, 1000, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(31061000, 31, 1000, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(31071000, 31, 1000, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(31081000, 31, 1000, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(31091000, 31, 1000, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(31101000, 31, 1000, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(31111000, 31, 1000, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(31121000, 31, 1000, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(31131000, 31, 1000, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(31141000, 31, 1000, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(31151000, 31, 1000, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(31161000, 31, 1000, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(31171000, 31, 1000, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(31181000, 31, 1000, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(31191000, 31, 1000, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(31201000, 31, 1000, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(31211000, 31, 1000, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(31221000, 31, 1000, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(31231000, 31, 1000, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(31241000, 31, 1000, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(31251000, 31, 1000, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(31261000, 31, 1000, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(31271000, 31, 1000, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--E2-AVE-01
(40011000, 40, 1000, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(40021000, 40, 1000, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(40031000, 40, 1000, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(40041000, 40, 1000, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(40051000, 40, 1000, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(40061000, 40, 1000, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(40071000, 40, 1000, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--E2-FUE-01
(44011000, 44, 1000, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(44021000, 44, 1000, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(44031000, 44, 1000, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--E2-FUE-02
(45011003, 45, 1003, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(45011004, 45, 1004, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(45011005, 45, 1005, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(45011006, 45, 1006, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: conf.ims_incident_alarm_condition_alarms
--
-- Para el id seguiremos el siguiente criterio:
-- 1.- Id ims_incident_alarm_conditions
-- 2.- Orden (2 cifras)
INSERT INTO conf.ims_incident_alarm_condition_alarms (incident_alarm_condition_alarm_id, incident_alarm_condition_id, incident_alarm_condition_alarm_type_id, alarm_config_id, element_type_id, element_id, min_alarmed_elements, enabled, visible, created_at, updated_at)
VALUES

----------------
--- P-MET-01 ---
----------------
-- RV-MET-1MA, Precipitación moderada (<=10mm/h)
(701100001, 7011000, 2, 3000001, null, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- RV-MET-1MA, Vientos fuertes(>60km/h)
(702100001, 7021000, 2, 3010001, null, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- RV-MET-1MA, Baja Temperatura (<5 C)
(703100001, 7031000, 2, 3020001, null, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

----------------
--- P-AMB-01 ---
----------------
-- Co alto (50ppm < Co < 200  ppm)
(801100101, 8011001, 2, 3000019, null, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(801100201, 8011002, 2, 3000019, null, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- No2 Alto (0.4 ppm < NO2 < 5ppm)
(802100101, 8021001, 2, 3000020, null, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(802100201, 8021002, 2, 3000020, null, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

----------------
--- P-AMB-02 ---
----------------
-- Visibilidad reducida (0.005 m-1 < k < 0.012 m-1)
(901100101, 9011001, 2, 3000021, null, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(901100102, 9011002, 2, 3000021, null, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

----------------
--- P-TRA-08 ---
----------------
(1701100101, 17011001, 2, 36, null, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(1702100101, 17021001, 2, 36, null, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(1701100201, 17011002, 2, 36, null, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(1702100201, 17021002, 2, 36, null, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

----------------
--- E1-MET-01 ---
----------------
-- RV-MET-1MA, Vientos fuertes(>80km/h)
(2101100001, 21011000, 2, 3030001, null, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- RV-MET-1MA, Baja Temperatura (<2 C)
(2102100001, 21021000, 2, 3040001, null, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- RV-MET-1MA, Balsas en la calzada
(2103100001, 21031000, 2, 3050001, null, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),



-----------------
--- E1-AMB-01 ---
-----------------
-- Excesiva concentración de CO (> 200ppm)
(2201100101, 22011001, 2, 3010019, null, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(2201100201, 22011002, 2, 3010019, null, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- Excesiva concentración de NO2 (> 5ppm)
(2202100101, 22021001, 2, 3010020, null, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(2202100201, 22021002, 2, 3010020, null, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),


-----------------
--- E1-AMB-02 ---
-----------------
(2301100101, 23011001, 2, 3010021, null, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(2301100201, 23011002, 2, 3010021, null, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-----------------
--- E1-AVE-01 ---
-----------------
-- Fallo en sistema de deteccion lineal de incendios

(3101100001, 31011000, 2, 257, null, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3102100001, 31021000, 2, 49, null, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- Fallo en 3 o mas camaras por tunel
(3103100003, 31031000, 2, 29, null, null, 3, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- Fallo de comunicaciones Túnel – Centro de Control
(3104100004, 31041000, 2, 218, null, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- Fallo SAI
(3105100005, 31051000, 2, 03000065, null, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3106100006, 31061000, 2, 03010065, null, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3107100007, 31071000, 2, 40065, null, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3108100008, 31081000, 2, 50065, null, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),


-- Fallo de pérdida de comunicación con PLC que afecte a varios equipos del túnel
(3109100009, 31091000, 1, 10027, 27, 3, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3110100000, 31101000, 1, 10027, 27, 5, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- Fallo de sistema de extinción de incendios 
(3111100001, 31111000, 2, 8602, null, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3112100002, 31121000, 2, 8603, null, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- Fallo en el sistema de iluminación del túnel con un tramo sin iluminar.
(311310001, 31131000, 1, 20057, 57, 19, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(311410002, 31141000, 1, 20057, 57, 20, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(311510003, 31151000, 1, 20057, 57, 21, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(311610004, 31161000, 1, 20057, 57, 22, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- Fallo de sistema de ventilación
(311710001, 31171000, 1, 20057, 57, 89, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(311810007, 31181000, 1, 20057, 57, 88, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(311910008, 31191000, 1, 20057, 57, 57, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- Fallo acometida eléctrica y funcionamiento del túnel con grupo
(312010009, 31201000, 1, 80057, 57, 61, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- Cuadros principales sin energía
(312110021, 31211000, 1, 20057, 57, 64, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(312210022, 31221000, 1, 20057, 57, 41, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(312310023, 31231000, 1, 20057, 57, 1, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(312410024, 31241000, 1, 20057, 57, 69, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(312510025, 31251000, 1, 20057, 57, 79, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(312610026, 31261000, 1, 20057, 57, 80, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(312710027, 31271000, 1, 20057, 57, 52, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-----------------
--- E2-AVE-01 ---
-----------------

-- Fallo completo sistema iluminación en tubo (Permanente + Guiado)
(4001100001, 40011000, 1, 20057, 57, 19, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4001100002, 40011000, 1, 20057, 57, 20, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4001100003, 40011000, 1, 20057, 57, 23, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4001100004, 40011000, 1, 20057, 57, 26, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(4002100001, 40021000, 1, 20057, 57, 21, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4002100002, 40021000, 1, 20057, 57, 22, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4002100003, 40021000, 1, 20057, 57, 24, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4002100004, 40021000, 1, 20057, 57, 25, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(4003100001, 40031000, 1, 20057, 57, 68, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4004100002, 40041000, 1, 20057, 57, 78, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4005100003, 40051000, 1, 20057, 57, 77, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- Fallo simultáneo acometida eléctrica y grupo electrógeno
(4007100001, 40071000, 1, 80057, 57, 61, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4007100002, 40071000, 2, 224, null, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-----------------
--- E2-FUE-01 ---
-----------------
(4401100001, 44011000, 2, 184, null, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4402100001, 44021000, 2, 186, null, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4403100001, 44031000, 2, 182, null, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-----------------
--- E2-FUE-02 ---
-----------------
(4501100501, 45011005, 2, 256, null, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4501100601, 45011006, 2, 256, null, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(4501100301, 45011003, 2, 256, null, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4501100401, 45011004, 2, 256, null, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);




