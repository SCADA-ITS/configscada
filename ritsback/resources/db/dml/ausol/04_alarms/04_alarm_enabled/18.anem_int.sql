-- 
-- Table: conf.alarm_configs.ANEM_INT
--
UPDATE conf.alarm_configs SET alarm_level_id = 1, enabled = True, treatment = False WHERE element_type_id = 18 AND alarm_config_id = 53; --LBL_ALARM_CONFIGS_ALARM_ANEM_INT_NO_CONNECTION
UPDATE conf.alarm_configs SET alarm_level_id = 2, enabled = True, treatment = True WHERE element_type_id = 18 AND alarm_config_id = 245; --LBL_ALARM_CONFIGS_ALARM_ANEM_SENSOR
UPDATE conf.alarm_configs SET alarm_level_id = 1, enabled = True, treatment = False WHERE element_type_id = 18 AND alarm_config_id = 259; --LBL_ALARM_CONFIGS_ALARM_ANEM_FA
UPDATE conf.alarm_configs SET alarm_level_id = 2, enabled = True, treatment = True WHERE element_type_id = 18 AND alarm_config_id = 10018; --LBL_ALARM_CONFIGS_ALARM_WIND_SPEED_OPPOSITE_WAY
UPDATE conf.alarm_configs SET alarm_level_id = 3, enabled = True, treatment = True WHERE element_type_id = 18 AND alarm_config_id = 20018; --LBL_ALARM_CONFIGS_ALARM_WIND_SPEED
