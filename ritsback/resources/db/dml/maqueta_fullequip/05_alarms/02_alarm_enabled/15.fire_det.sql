-- 
-- Table: conf.alarm_measures.firedet
--
INSERT INTO conf.alarm_config_measures
(alarm_config_id, element_type_id, param_type_id, element_type_param_id, alarm_threshold_operation_id, threshold_val1, threshold_val2, created_at, updated_at) VALUES
(010015, 15, 2, 3, 7, '-1', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP); -- Temperatura Minima (<= 3 ºC)

-- 
-- Table: conf.alarm_configs.FIRE_DET
--
UPDATE conf.alarm_configs SET alarm_level_id = 2, enabled = True, treatment = True WHERE element_type_id = 15 AND alarm_config_id = 49; --LBL_ALARM_CONFIGS_ALARM_FIRE_DET_NO_CONNECTION
UPDATE conf.alarm_configs SET alarm_level_id = 6, enabled = True, treatment = True WHERE element_type_id = 15 AND alarm_config_id = 256; --LBL_ALARM_CONFIGS_ALARM_FIRE_ZONE
UPDATE conf.alarm_configs SET alarm_level_id = 3, enabled = True, treatment = True WHERE element_type_id = 15 AND alarm_config_id = 257; --LBL_ALARM_CONFIGS_ALARM_FIBER_FAULT
UPDATE conf.alarm_configs SET alarm_level_id = 1, enabled = True, treatment = False WHERE element_type_id = 15 AND alarm_config_id = 10015; --LBL_ALARM_CONFIGS_ALARM_MIN_TEMP
