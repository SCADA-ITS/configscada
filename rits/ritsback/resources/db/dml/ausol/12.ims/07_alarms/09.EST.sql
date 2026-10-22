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
(17011076, 17, 1076, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(17021076, 17, 1076, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(17011077, 17, 1077, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(17021077, 17, 1077, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--E1-AVE-01
(31011075, 31, 1075, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(31021075, 31, 1075, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(31031075, 31, 1075, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(31041075, 31, 1075, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(31051075, 31, 1075, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(31061075, 31, 1075, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(31071075, 31, 1075, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(31081075, 31, 1075, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(31091075, 31, 1075, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(31101075, 31, 1075, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(31111075, 31, 1075, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(31121075, 31, 1075, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(31131075, 31, 1075, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(31141075, 31, 1075, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(31151075, 31, 1075, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(31161075, 31, 1075, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(31171075, 31, 1075, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(31181075, 31, 1075, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(31191075, 31, 1075, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(31201075, 31, 1075, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(31211075, 31, 1075, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(31221075, 31, 1075, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--E2-AVE-01
(40011075, 40, 1075, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(40021075, 40, 1075, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(40031075, 40, 1075, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(40041075, 40, 1075, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(40051075, 40, 1075, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(40061075, 40, 1075, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--E2-FUE-01
(44011075, 44, 1075, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(44021075, 44, 1075, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(44031075, 44, 1075, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--E2-FUE-02
(45011076, 45, 1076, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(45011077, 45, 1077, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


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
(1701107601, 17011076, 2, 36, null, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(1702107601, 17021076, 2, 36, null, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(1701107701, 17011077, 2, 36, null, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(1702107701, 17021077, 2, 36, null, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-----------------
--- E1-AVE-01 ---
-----------------

-- Fallo en sistema de deteccion lineal de incendios

(3111107501, 31111075, 2, 257, null, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3112107501, 31121075, 2, 49, null, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- Fallo en 3 o mas camaras por tunel
(3122107504, 31221075, 2, 29, null, null, 3, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- Fallo de comunicaciones Túnel – Centro de Control
(3113107504, 31131075, 2, 218, null, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- Fallo SAI
(3114107505, 31141075, 2, 03000065, null, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3115107506, 31151075, 2, 03010065, null, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3116107507, 31161075, 2, 40065, null, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3117107508, 31171075, 2, 50065, null, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),


-- Fallo de pérdida de comunicación con PLC que afecte a varios equipos del túnel
(3118107509, 31181075, 1, 10027, 27, 90, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3119107500, 31191075, 1, 10027, 27, 91, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- Fallo de sistema de extinción de incendios 
(3120107501, 31201075, 2, 8602, null, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3121107502, 31211075, 2, 8603, null, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- Fallo en el sistema de iluminación del túnel con un tramo sin iluminar.
(3101107501, 31011075, 1, 20057, 57, 969, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3102107501, 31021075, 1, 20057, 57, 970, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3103107501, 31031075, 1, 20057, 57, 971, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3104107501, 31041075, 1, 20057, 57, 972, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- Fallo de sistema de ventilación

-- Fallo acometida eléctrica y funcionamiento del túnel con grupo
(3105107501, 31051075, 1, 80057, 57, 914, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- Cuadros principales sin energía
(3106107501, 31061075, 1, 20057, 57, 911, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3107107501, 31071075, 1, 20057, 57, 985, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3108107501, 31081075, 1, 20057, 57, 979, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3109107501, 31091075, 1, 20057, 57, 928, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3110107501, 31101075, 1, 20057, 57, 912, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),


-----------------
--- E2-AVE-01 ---
-----------------

-- Fallo completo sistema iluminación en tubo (Permanente + Guiado)
(4001107501, 40011075, 1, 20057, 57, 969, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4001107502, 40011075, 1, 20057, 57, 970, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4001107503, 40011075, 1, 20057, 57, 974, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),


(4002107501, 40021075, 1, 20057, 57, 971, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4002107502, 40021075, 1, 20057, 57, 972, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4002107503, 40021075, 1, 20057, 57, 973, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(4003107501, 40031075, 1, 20057, 57, 967, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4004107501, 40041075, 1, 20057, 57, 927, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4005107501, 40051075, 1, 20057, 57, 926, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- Fallo simultáneo acometida eléctrica y grupo electrógeno
(4006107501, 40061075, 1, 80057, 57, 914, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4006107502, 40061075, 2, 224, null, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-----------------
--- E2-FUE-01 ---
-----------------
(4401107501, 44011075, 2, 184, null, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4402107501, 44021075, 2, 186, null, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4403107501, 44031075, 2, 182, null, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),


-----------------
--- E2-FUE-02 ---
-----------------
(4501107601, 45011076, 2, 256, null, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(4501107701, 45011077, 2, 256, null, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);





