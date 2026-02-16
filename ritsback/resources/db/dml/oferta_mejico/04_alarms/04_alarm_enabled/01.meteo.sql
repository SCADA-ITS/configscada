UPDATE conf.alarm_configs SET alarm_level_id = 1, enabled = True, treatment = False WHERE element_type_id = 1 AND alarm_config_id = 010001; --LBL_ALARM_CONFIGS_ALARM_WS_NO_CONNECTION
UPDATE conf.alarm_configs SET alarm_level_id = 4, enabled = True, treatment = True WHERE element_type_id = 1 AND alarm_config_id = 3000001; --LBL_ALARM_CONFIGS_ALARM_WS_MODERATE_PRECIPITATION
UPDATE conf.alarm_configs SET alarm_level_id = 4, enabled = True, treatment = True WHERE element_type_id = 1 AND alarm_config_id = 3010001; --LBL_ALARM_CONFIGS_ALARM_WS_STRONG_WIND
UPDATE conf.alarm_configs SET alarm_level_id = 4, enabled = True, treatment = True WHERE element_type_id = 1 AND alarm_config_id = 3020001; --LBL_ALARM_CONFIGS_ALARM_WS_LOW_TEMPERATURE
UPDATE conf.alarm_configs SET alarm_level_id = 5, enabled = True, treatment = True WHERE element_type_id = 1 AND alarm_config_id = 3030001; --LBL_ALARM_CONFIGS_ALARM_WS_HEAVY_WIND
UPDATE conf.alarm_configs SET alarm_level_id = 5, enabled = True, treatment = True WHERE element_type_id = 1 AND alarm_config_id = 3040001; --LBL_ALARM_CONFIGS_ALARM_WS_VERY_LOW_TEMPERATURE
UPDATE conf.alarm_configs SET alarm_level_id = 5, enabled = True, treatment = True WHERE element_type_id = 1 AND alarm_config_id = 3050001; --LBL_ALARM_CONFIGS_ALARM_WS_HEAVY_PRECIPITATION
