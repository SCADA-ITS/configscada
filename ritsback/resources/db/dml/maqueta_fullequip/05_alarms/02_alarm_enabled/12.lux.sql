-- 
-- Table: conf.alarm_measures.jetfan
--
INSERT INTO conf.alarm_config_measures
(alarm_config_id, element_type_id, param_type_id, element_type_param_id, alarm_threshold_operation_id, threshold_val1, threshold_val2, created_at, updated_at) VALUES
(010012, 12, 2, 1, 7, '1', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP); -- Nivel de luminosidad menor de 1 lux

-- 
-- Table: conf.alarm_configs.LUX
--
UPDATE conf.alarm_configs SET alarm_level_id = 1, enabled = True, treatment = False WHERE element_type_id = 12 AND alarm_config_id = 46; --LBL_ALARM_CONFIGS_ALARM_LUX_NO_CONNECTION
UPDATE conf.alarm_configs SET alarm_level_id = 1, enabled = True, treatment = False WHERE element_type_id = 12 AND alarm_config_id = 244; --LBL_ALARM_CONFIGS_ALARM_LUX_SENSOR
UPDATE conf.alarm_configs SET alarm_level_id = 1, enabled = True, treatment = False WHERE element_type_id = 12 AND alarm_config_id = 255; --LBL_ALARM_CONFIGS_ALARM_LUX_FA
UPDATE conf.alarm_configs SET alarm_level_id = 2, enabled = True, treatment = True WHERE element_type_id = 12 AND alarm_config_id = 10012; --LBL_ALARM_CONFIGS_ALARM_LOW_LUMINOSITY
