UPDATE conf.alarm_configs SET alarm_level_id = 1, enabled = True, treatment = False WHERE element_type_id = 12 AND alarm_config_id = 010012; --LBL_ALARM_CONFIGS_ALARM_LUX_NO_CONNECTION
UPDATE conf.alarm_configs SET alarm_level_id = 1, enabled = True, treatment = False WHERE element_type_id = 12 AND alarm_config_id = 020012; --LBL_ALARM_CONFIGS_ALARM_LUX_SENSOR
UPDATE conf.alarm_configs SET alarm_level_id = 1, enabled = True, treatment = False WHERE element_type_id = 12 AND alarm_config_id = 030012; --LBL_ALARM_CONFIGS_ALARM_LUX_FA
UPDATE conf.alarm_configs SET alarm_level_id = 2, enabled = True, treatment = True WHERE element_type_id = 12 AND alarm_config_id = 03010012; --LBL_ALARM_CONFIGS_ALARM_LOW_LUMINOSITY
