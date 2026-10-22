--
-- Table: conf.ims_incident_alarm_conditions
--

-- Para el id seguiremos el siguiente criterio:
-- 1.- Id incident_type
-- 2.- Orden (2 cifras)
-- 3.- stretch_id (4 cifras)
INSERT INTO conf.ims_incident_alarm_conditions (incident_alarm_condition_id, incident_type_id, stretch_id, auto, enabled, visible, created_at, updated_at)
VALUES
-- P-TRA-08
(17011019, 17, 1019, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(17021019, 17, 1019, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(17011020, 17, 1020, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(17021020, 17, 1020, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--E1-AVE-01
(31011018, 31, 1018, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(31021018, 31, 1018, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(31031018, 31, 1018, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(31041018, 31, 1018, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(31051018, 31, 1018, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(31061018, 31, 1018, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(31071018, 31, 1018, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(31081018, 31, 1018, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(31091018, 31, 1018, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(31101018, 31, 1018, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(31111018, 31, 1018, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(31121018, 31, 1018, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(31131018, 31, 1018, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(31141018, 31, 1018, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(31151018, 31, 1018, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(31161018, 31, 1018, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(31171018, 31, 1018, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(31181018, 31, 1018, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(31191018, 31, 1018, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(31201018, 31, 1018, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(31211018, 31, 1018, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(31221018, 31, 1018, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--E2-AVE-01
(40011018, 40, 1018, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(40021018, 40, 1018, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(40031018, 40, 1018, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(40041018, 40, 1018, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(40051018, 40, 1018, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(40061018, 40, 1018, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--E2-FUE-01
(44011018, 44, 1018, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(44021018, 44, 1018, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(44031018, 44, 1018, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--E2-FUE-02
(45011019, 45, 1019, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(45011020, 45, 1020, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: conf.ims_incident_alarm_condition_alarms
--
-- Para el id seguiremos el siguiente criterio:
-- 1.- Id ims_incident_alarm_conditions
-- 2.- Orden (2 cifras)
INSERT INTO conf.ims_incident_alarm_condition_alarms (incident_alarm_condition_alarm_id, incident_alarm_condition_id, incident_alarm_condition_alarm_type_id, alarm_config_id, element_type_id, element_id, min_alarmed_elements, enabled, visible, created_at, updated_at)
VALUES
----------------
--- P-TRA-08 ---
----------------
(1701101901, 17011019, 2, 36, null, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(1702101901, 17021019, 2, 36, null, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(1701102001, 17011020, 2, 36, null, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(1702102001, 17021020, 2, 36, null, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-----------------
--- E1-AVE-01 ---
-----------------
-- Fallo sistema de detección lineal de incendios
(3101101801, 31011018, 2, 257, null, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3102101802, 31021018, 2, 49, null, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- Fallo de 3 o más cámaras por tunel
(3103101801, 31031018, 2, 29, null, null, 3, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- Fallo de sistema de extinción de incendios 
(3104101801, 31041018, 2, 8602, null, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3105101802, 31051018, 2, 8603, null, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- Fallo de comunicaciones Túnel – Centro de Control
(3106101801, 31061018, 2, 218, null, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- Fallo SAI
(3107101801, 31071018, 2, 03000065, null, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3108101802, 31081018, 2, 03010065, null, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3109101803, 31091018, 2, 40065, null, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3110101804, 31101018, 2, 50065, null, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- Fallo de pérdida de comunicación con PLC que afecte a varios equipos del túnel
(3111101801, 31111018, 1, 10027, 27, 19, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3112101802, 31121018, 1, 10027, 27, 20, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- Fallo en el sistema de iluminación del túnel con un tramo sin iluminar
(3113101801, 31131018, 1, 10027, 57, 189, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3114101802, 31141018, 1, 10027, 57, 190, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3115101803, 31151018, 1, 10027, 57, 191, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3116101804, 31161018, 1, 10027, 57, 192, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- Fallo de sistema de ventilación

-- Fallo acometida 
(3117101801, 31171018, 1, 10027, 57, 239, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- Cuadros principales sin energía
(3118101801, 31181018, 1, 10027, 57, 235, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3119101801, 31191018, 1, 10027, 57, 207, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3120101801, 31201018, 1, 10027, 57, 201, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3121101801, 31211018, 1, 10027, 57, 246, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3122101801, 31221018, 1, 10027, 57, 236, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-----------------
--- E2-AVE-01 ---
-----------------

-- Fallo completo sistema iluminación en tubo (Permanente + Guiado)
(4001101801, 40011018, 1, 20057, 57, 189, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4001101802, 40011018, 1, 20057, 57, 190, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4001101803, 40011018, 1, 20057, 57, 193, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(4002101801, 40021018, 1, 20057, 57, 191, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4002101802, 40021018, 1, 20057, 57, 192, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4002101803, 40021018, 1, 20057, 57, 194, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(4003101801, 40031018, 1, 20057, 57, 194, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4004101801, 40041018, 1, 20057, 57, 245, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4005101801, 40051018, 1, 20057, 57, 244, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- Fallo simultáneo acometida eléctrica y grupo electrógeno
(4006101801, 40061018, 1, 80057, 57, 239, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4006101802, 40061018, 2, 224, null, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-----------------
--- E2-FUE-01 ---
-----------------
(4401101801, 44011018, 2, 184, null, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4402101801, 44021018, 2, 186, null, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4403101801, 44031018, 2, 182, null, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),


-----------------
--- E2-FUE-02 ---
-----------------
(4501101901, 45011019, 2, 256, null, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(4501102001, 45011020, 2, 256, null, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);




