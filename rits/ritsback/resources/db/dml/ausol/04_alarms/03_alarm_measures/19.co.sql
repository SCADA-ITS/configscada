-- 
-- Table: conf.alarm_configs
--
INSERT INTO conf.alarm_configs
(alarm_config_id, alarm_type_id, alarm_level_id, element_type_id, element_id, element_type_state_id, mine, treatment, alias, description, label_alias, label_description, enabled, visible, created_at, updated_at) VALUES
(3000019, 3, 4, 19, NULL, 3, true, false, 'ALARM CO HIGH CONCENTRATION', 'High conentration of CO , 50ppm < CO < 200ppm', 'LBL_ALARM_CONFIGS_ALARM_CO_HIGH_CONCENTRATION', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3010019, 3, 5, 19, NULL, 3, true, false, 'ALARM CO EXCESSIVE CONCENTRATION', 'Excesive conentration of CO , CO > 200ppm', 'LBL_ALARM_CONFIGS_ALARM_CO_EXCESIVE_CONCENTRATION', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: conf.alarm_measures
--
INSERT INTO conf.alarm_config_measures
(alarm_config_id, element_type_id, param_type_id, element_type_param_id, alarm_threshold_operation_id, threshold_val1, threshold_val2, created_at, updated_at) VALUES
(3000019, 19, 2, 1, 1, '50', '200', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3010019, 19, 2, 1, 1, '200', '65534', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP); 
