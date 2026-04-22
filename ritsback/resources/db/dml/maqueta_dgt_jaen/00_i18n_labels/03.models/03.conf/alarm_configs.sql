-- 
-- Type:   model
-- Entity: conf.alarm_configs
-- File:   alarm_configs.sql
-- Langs:  es_ES, en_GB, sk_SK, es_CL
--

-- 
-- Table: master.i18n_labels
--

Update master.i18n_labels
set "translation" = 'Fallo de tensión de red'
where "label" = 'LBL_ALARM_CONFIGS_ALARM_SAI_ALARM_COMMUNICATIONS_LOST' and locale_code in ('es_ES', 'es_CL');

Update master.i18n_labels
set "translation" = 'Lack of grid power'
where "label" = 'LBL_ALARM_CONFIGS_ALARM_SAI_ALARM_COMMUNICATIONS_LOST' and locale_code = 'en_GB';


INSERT INTO master.i18n_labels (locale_code,module_id,"label","translation",created_at,updated_at) VALUES
--es_ES
--Element:9 -> jetfan
('es_ES', 1, 'LBL_ALARM_CONFIGS_ALARM_JETFAN_STARTER_SHOT', 'Alarma de arranque de ventilador', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_ES', 1, 'LBL_ALARM_CONFIGS_ALARM_JETFAN_NO_AUTO', 'Alarma ventilador en local', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--es_ES
--ElementType:19 -> CO
('es_ES', 1, 'LBL_ALARM_CONFIGS_ALARM_CO_CONCENTRATION', 'Detector CO  alta concentración (30 ppm< CO <60 ppm)', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_ES', 1, 'LBL_ALARM_CONFIGS_ALARM_CO_HIGH_CONCENTRATION', 'Detector CO  alta concentración (60 ppm< CO <200 ppm)', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_ES', 1, 'LBL_ALARM_CONFIGS_ALARM_CO_EXCESIVE_CONCENTRATION', 'Detector CO excesiva concentración (>200 ppm)', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--ElementType:21 -> Opacimetro
('es_ES', 1, 'LBL_ALARM_CONFIGS_ALARM_OPAC_VISIBILITY', 'Opacímetros visibilidad reducida (20 km-1 < k < 40 km-1)', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_ES', 1, 'LBL_ALARM_CONFIGS_ALARM_OPAC_REDUCED_VISIBILITY', 'Opacímetros visibilidad muy reducida (40 km-1 < k < 66 km-1)', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_ES', 1, 'LBL_ALARM_CONFIGS_ALARM_OPAC_EXCESSIVE_OPACITY', 'Opacímetro visibilidad excesivamente reducida (k > 66 km-1))', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--ElementType:71 subtipo 27 -> panel gálibo
('es_ES', 1, 'LBL_ALARM_CONFIGS_ALARM_EXCESS_CLEARANCE', 'Exceso de Gálibo', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--en_GB
--Element:9 -> jetfan
('en_GB', 1, 'LBL_ALARM_CONFIGS_ALARM_JETFAN_STARTER_SHOT', 'Alarm Jetfan starter shot', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--ElementType:19 -> CO
('en_GB', 1, 'LBL_ALARM_CONFIGS_ALARM_CO_CONCENTRATION', 'High concentration (30 ppm< CO <60 ppm)', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('en_GB', 1, 'LBL_ALARM_CONFIGS_ALARM_CO_HIGH_CONCENTRATION', 'High concentration (60 ppm< CO <200 ppm)', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('en_GB', 1, 'LBL_ALARM_CONFIGS_ALARM_CO_EXCESIVE_CONCENTRATION', 'Excesive concentration (>200 ppm)', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--ElementType:21 -> Opacimetro
('en_GB', 1, 'LBL_ALARM_CONFIGS_ALARM_OPAC_VISIBILITY', 'Reduced visibility (0.0025 m-1 < k <0.005 m-1)', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('en_GB', 1, 'LBL_ALARM_CONFIGS_ALARM_OPAC_REDUCED_VISIBILITY', 'Reduced visibility (0.005 m-1 < k <0.009 m-1)', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('en_GB', 1, 'LBL_ALARM_CONFIGS_ALARM_OPAC_EXCESSIVE_OPACITY', 'Excessive opacity (k > 0.009 m-1))', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--ElementType:71 subtipo 27 -> panel gálibo
('en_GB', 1, 'LBL_ALARM_CONFIGS_ALARM_EXCESS_CLEARANCE', 'Clearance excess', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- es_CL
--Element:9 -> jetfan
('es_CL', 1, 'LBL_ALARM_CONFIGS_ALARM_JETFAN_STARTER_SHOT', 'Alarma de arranque de ventilador', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--ElementType:19 -> CO
('es_CL', 1, 'LBL_ALARM_CONFIGS_ALARM_CO_CONCENTRATION', 'Detector CO  alta concentración (30 ppm< CO <60 ppm)', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_CL', 1, 'LBL_ALARM_CONFIGS_ALARM_CO_HIGH_CONCENTRATION', 'Detector CO  alta concentración (60 ppm< CO <200 ppm)', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_CL', 1, 'LBL_ALARM_CONFIGS_ALARM_CO_EXCESIVE_CONCENTRATION', 'Detector CO excesiva concentración (>200 ppm)', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--ElementType:21 -> Opacimetro
('es_CL', 1, 'LBL_ALARM_CONFIGS_ALARM_OPAC_VISIBILITY', 'Opacímetros visibilidad reducida (0.0025 m-1 < k <0.005 m-1)', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_CL', 1, 'LBL_ALARM_CONFIGS_ALARM_OPAC_REDUCED_VISIBILITY', 'Opacímetros visibilidad reducida (0.005 m-1 < k <0.009 m-1)', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_CL', 1, 'LBL_ALARM_CONFIGS_ALARM_OPAC_EXCESSIVE_OPACITY', 'Opacímetro visibilidad muy reducida (k > 0.009 m-1))', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--ElementType:71 subtipo 27 -> panel gálibo
('es_CL', 1, 'LBL_ALARM_CONFIGS_ALARM_EXCESS_CLEARANCE', 'Exceso de Gálibo', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

