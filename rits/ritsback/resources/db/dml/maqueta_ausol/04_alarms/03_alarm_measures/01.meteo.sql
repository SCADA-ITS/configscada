-- 
-- Table: conf.alarm_configs
--
INSERT INTO conf.alarm_configs
(alarm_config_id, alarm_type_id, alarm_level_id, element_type_id, element_id, element_type_state_id, mine, treatment, alias, description, label_alias, label_description, enabled, visible, created_at, updated_at) VALUES
(3000001, 3, 3, 1, NULL, 3, true, false, 'ALARM WS MODERATE PRECIPITATION', 'Precipitation less than or equal to 10 mm/h', 'LBL_ALARM_CONFIGS_ALARM_WS_MODERATE_PRECIPITATION', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3010001, 3, 3, 1, NULL, 3, true, false, 'ALARM WS STRONG WIND', 'Strong wind greater than 60 km/h', 'LBL_ALARM_CONFIGS_ALARM_WS_STRONG_WIND', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3020001, 3, 3, 1, NULL, 3, true, false, 'ALARM WS LOW TEMPERATURE', 'Low temperature less than 5 ºC', 'LBL_ALARM_CONFIGS_ALARM_WS_LOW_TEMPERATURE', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(3030001, 3, 3, 1, NULL, 3, true, false, 'ALARM WS HEAVY WIND', 'Heavy  wind greater than 80 km/h', 'LBL_ALARM_CONFIGS_ALARM_WS_HEAVY_WIND', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3040001, 3, 3, 1, NULL, 3, true, false, 'ALARM WS VERY LOW TEMPERATURE', 'Very low temperature less than 2 ºC', 'LBL_ALARM_CONFIGS_ALARM_WS_VERY_LOW_TEMPERATURE', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3050001, 3, 3, 1, NULL, 3, true, false, 'ALARM WS HEAVY PRECIPITATION', 'Precipitation greater than 10 mm/h', 'LBL_ALARM_CONFIGS_ALARM_WS_HEAVY_PRECIPITATION', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: conf.alarm_measures
--
INSERT INTO conf.alarm_config_measures
(alarm_config_id, element_type_id, param_type_id, element_type_param_id, alarm_threshold_operation_id, threshold_val1, threshold_val2, created_at, updated_at) VALUES
(3000001, 1, 2, 11, 1, '1.0', '10.0', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3010001, 1, 2, 15, 1, '60', '79', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3020001, 1, 2, 1, 1, '2.1', '5.0', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(3030001, 1, 2, 15, 4, '79', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3040001, 1, 2, 1, 6, '2.1', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3050001, 1, 2, 11, 4, '10.0', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);