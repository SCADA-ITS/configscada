-- 
-- Table: conf.alarm_measures.VANE
--
INSERT INTO conf.alarm_configs
(alarm_config_id, alarm_type_id, alarm_level_id, element_type_id, element_id, element_type_state_id, mine, treatment, alias, description, label_alias, label_description, enabled, visible, created_at, updated_at) VALUES
(03010066, 2, 5, 66, NULL, 3, true, true, 'ALARM WIND SPEED', 'WIND SPEED GREATER THAN 60', 'LBL_ALARM_CONFIGS_ALARM_VANE_HIGH_WIND_SPEED', 'LBL_ALARM_CONFIGS_ALARM_VANE_HIGH_WIND_SPEED_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(03020066, 2, 5, 66, NULL, 3, true, true, 'ALARM WIND SPEED', 'WIND SPEED GREATER THAN 80', 'LBL_ALARM_CONFIGS_ALARM_VANE_HIGH_WIND_SPEED_80', 'LBL_ALARM_CONFIGS_ALARM_VANE_HIGH_WIND_SPEED_80_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.alarm_config_measures
(alarm_config_id, element_type_id, param_type_id, element_type_param_id, alarm_threshold_operation_id, threshold_val1, threshold_val2, created_at, updated_at) VALUES
(03010066, 66, 2, 2, 1, '60', '65534', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- Velocidad del viento > 60 m/s
(03020066, 66, 2, 2, 1, '80', '65534', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP); -- Velocidad del viento > 80 m/s