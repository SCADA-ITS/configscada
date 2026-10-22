-- 
-- Table: conf.alarm_configs
--
INSERT INTO conf.alarm_configs
(alarm_config_id, alarm_type_id, alarm_level_id, element_type_id, element_id, element_type_state_id, mine, treatment, alias, description, label_alias, label_description, enabled, visible, created_at, updated_at) VALUES
(3010009, 3, 2, 9, NULL, 3, true, true, 'ALARM JETFAN HIGH VIBRATION', 'ALARM JETFAN HIGH VIBRATION', 'LBL_ALARM_CONFIGS_ALARM_HIGH_VIBRATION', 'LBL_ALARM_CONFIGS_ALARM_HIGH_VIBRATION', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3020009, 3, 3, 9, NULL, 3, true, true, 'ALARM JETFAN MAX VIBRATION', 'ALARM JETFAN MAX VIBRATION', 'LBL_ALARM_CONFIGS_ALARM_MAX_VIBRATION', 'LBL_ALARM_CONFIGS_ALARM_MAX_VIBRATION', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3030009, 3, 3, 9, NULL, 3, true, true, 'ALARM JETFAN MAX FRONT BEARING TEMPERATURE', 'ALARM JETFAN MAX FRONT BEARING TEMPERATURE', 'LBL_ALARM_CONFIGS_ALARM_MAX_FRONT_BEARING_TEMP', 'LBL_ALARM_CONFIGS_ALARM_MAX_FRONT_BEARING_TEMP', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3040009, 3, 3, 9, NULL, 3, true, true, 'ALARM JETFAN MAX BACK BEARING TEMPERATURE', 'ALARM JETFAN MAX BACK BEARING TEMPERATURE', 'LBL_ALARM_CONFIGS_ALARM_MAX_BACK_BEARING_TEMP', 'LBL_ALARM_CONFIGS_ALARM_MAX_BACK_BEARING_TEMP', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: conf.alarm_measures.jetfan
--
INSERT INTO conf.alarm_config_measures
(alarm_config_id, element_type_id, param_type_id, element_type_param_id, alarm_threshold_operation_id, threshold_val1, threshold_val2, created_at, updated_at) VALUES
(3010009, 9, 2, 2, 1, '7.1', '11.2', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- Nivel alto de vibración ventilador, mayor de 7.1 m/s y menor o igual 11.2
(3020009, 9, 2, 2, 1, '11.2', '65534', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- Nivel maximo de vibración ventilador, mayor o igual de 15.2 m/s
(3030009, 9, 2, 3, 1, '80', '65534', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),   -- Temperatura de Rodamientos del Motor delantero Mayor de 80ºC
(3040009, 9, 2, 4, 1, '80', '65534', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);   -- Temperatura de Rodamientos del Motor trasero Mayor de 80ºC