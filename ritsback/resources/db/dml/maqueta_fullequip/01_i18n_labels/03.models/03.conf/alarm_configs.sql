-- 
-- Type:   model
-- Entity: conf.alarm_configs
-- File:   alarm_configs.sql
-- Langs:  es_ES, en_GB, sk_SK, es_CL
--

-- 
-- Table: master.i18n_labels
--

--es_ES
INSERT INTO master.i18n_labels (locale_code,module_id,"label","translation",created_at,updated_at) VALUES
--CO
('es_ES', 1, 'LBL_ALARM_CONFIGS_ALARM_CO_HIGH_CONCENTRATION', 'Detector CO  alta concentración (50 ppm< CO <200 ppm)', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_ES', 1, 'LBL_ALARM_CONFIGS_ALARM_CO_EXCESIVE_CONCENTRATION', 'Detector CO excesiva concentración (>200 ppm)', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--Deposito
('es_ES', 1, 'LBL_WATER_LEVEL_LESS_90', 'Depósito de agua PCI nivel bajo <90%', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_ES', 1, 'LBL_DIESEL_LEVEL_LESS_60', 'Depósito diesel bomba PCI nivel bajo <60%', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
 
--Estación meteorologica
('es_ES', 1, 'LBL_ALARM_CONFIGS_ALARM_WS_LOW_TEMPERATURE', 'Baja temperatura (<5º)', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_ES', 1, 'LBL_ALARM_CONFIGS_ALARM_WS_VERY_LOW_TEMPERATURE', 'Baja temperatura (<2º)', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_ES', 1, 'LBL_ALARM_CONFIGS_ALARM_WS_MODERATE_PRECIPITATION', 'Precipitaciones moderadas (>=2 mm/h) y <10mm/h)', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_ES', 1, 'LBL_ALARM_CONFIGS_ALARM_WS_HEAVY_PRECIPITATION', 'Precipitaciones moderadas (>=10 mm/h)', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_ES', 1, 'LBL_ALARM_CONFIGS_ALARM_WS_STRONG_WIND', 'Vientos fuertes (>60 km/h)', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_ES', 1, 'LBL_ALARM_CONFIGS_ALARM_WS_HEAVY_WIND', 'Vientos muy fuertes (>80 km/h)', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--section
('es_ES', 1, 'LBL_ALARM_CONDITIONS_CRITICAL_HEAVY_TRAFFIC', 'Tráfico intenso', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--NO
('es_ES', 1, 'LBL_ALARM_CONFIGS_ALARM_NO2_HIGH_CONCENTRATION', 'Detector NO  alta concentarción  (0.4 ppm < NO2 <5 ppm)', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_ES', 1, 'LBL_ALARM_CONFIGS_ALARM_NO2_EXCESIVE_CONCENTRATION', 'Detector No excesiva concentración (>5 ppm)', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--Opacimetro
('es_ES', 1, 'LBL_ALARM_CONFIGS_ALARM_OPAC_REDUCED_VISIBILITY', 'Opacímetros visibilidad reducida (0.005 m-1 < k <0.012 m-1)', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_ES', 1, 'LBL_ALARM_CONFIGS_ALARM_OPAC_EXCESSIVE_OPACITY', 'Opacímetro visibilidad muy reducida (k > 0.012 m-1))', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--SAI
('es_ES', 1, 'LBL_ALARM_CONFIGS_ALARM_SAI_AUTONOMY_LESS_50', 'SAI con autonomía <50%', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_ES', 1, 'LBL_ALARM_CONFIGS_ALARM_SAI_AUTONOMY_LESS_5', 'SAI con autonomía <5%', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--Veleta
('es_ES', 1, 'LBL_ALARM_CONFIGS_ALARM_VANE_HIGH_WIND_SPEED', 'Veleta viento >60 km/h', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_ES', 1, 'LBL_ALARM_CONFIGS_ALARM_VANE_HIGH_WIND_SPEED_80', 'Veleta viento >80 km/h', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--Ventilador
('es_ES', 1, 'LBL_ALARM_CONFIGS_ALARM_HIGH_VIBRATION', 'Nivel alto de vibración (>= 7.1 m/s y <= 11.2) ', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_ES', 1, 'LBL_ALARM_CONFIGS_ALARM_MAX_VIBRATION', 'Nivel máximo de vibración (>= 11.2) ', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_ES', 1, 'LBL_ALARM_CONFIGS_ALARM_MAX_FRONT_BEARING_TEMP', 'Ventilador exceso temperatura en motor delantero (>80ºC)', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_ES', 1, 'LBL_ALARM_CONFIGS_ALARM_MAX_BACK_BEARING_TEMP', 'Ventilador exceso temperatura en motor trasero (>80ºC)', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);;


