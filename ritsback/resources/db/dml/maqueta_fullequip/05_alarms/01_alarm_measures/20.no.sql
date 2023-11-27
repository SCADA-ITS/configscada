-- 
-- Table: conf.alarm_configs
--
INSERT INTO conf.alarm_configs
(alarm_config_id, alarm_type_id, alarm_level_id, element_type_id, element_id, element_type_state_id, mine, treatment, alias, description, label_alias, label_description, enabled, visible, created_at, updated_at) VALUES
(3000020, 3, 4, 20, NULL, 3, true, false, 'ALARM NO HIGH CONCENTRATION', 'High conentration of NO2 , 0.4 ppm < NO2 < 5ppm', 'LBL_ALARM_CONFIGS_ALARM_NO2_HIGH_CONCENTRATION', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3010020, 3, 5, 20, NULL, 3, true, false, 'ALARM NO EXCESSIVE CONCENTRATION', 'Excesive conentration of NO2 , NO2 > 5 ppm', 'LBL_ALARM_CONFIGS_ALARM_NO2_EXCESIVE_CONCENTRATION', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: conf.alarm_measures
--
INSERT INTO conf.alarm_config_measures
(alarm_config_id, element_type_id, param_type_id, element_type_param_id, alarm_threshold_operation_id, threshold_val1, threshold_val2, created_at, updated_at) VALUES
(3000020, 20, 2, 1, 1, '0.4', '5', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3010020, 20, 2, 1, 1, '5', '65534', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP); 
