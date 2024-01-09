-- 
-- Table: conf.alarm_configs
--
INSERT INTO conf.alarm_configs
(alarm_config_id, alarm_type_id, alarm_level_id, element_type_id, element_id, element_type_state_id, mine, treatment, alias, description, label_alias, label_description, enabled, visible, created_at, updated_at) VALUES
(03010015, 3, 4, 15, NULL, 3, true, false, 'ALARM MIN TEMP', 'Temperature <= 3ºC', 'LBL_ALARM_CONFIGS_ALARM_MIN_TEMP', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: conf.alarm_measures.firedet
--
INSERT INTO conf.alarm_config_measures
(alarm_config_id, element_type_id, param_type_id, element_type_param_id, alarm_threshold_operation_id, threshold_val1, threshold_val2, created_at, updated_at) VALUES
(03010015, 15, 2, 3, 7, '-1', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP); -- Temperatura Minima (<= 3 ºC)

