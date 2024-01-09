UPDATE conf.alarm_configs SET alarm_level_id = 1, enabled = True, treatment = False WHERE element_type_id = 66 AND alarm_config_id = 010066; --LBL_ALARM_CONFIGS_ALARM_VANE_NO_CONNECTION
UPDATE conf.alarm_configs SET alarm_level_id = 2, enabled = True, treatment = True WHERE element_type_id = 66 AND alarm_config_id = 020066; --LBL_ALARM_CONFIGS_VANE_SENSOR_OUT_OF_RANGE
UPDATE conf.alarm_configs SET alarm_level_id = 4, enabled = True, treatment = True WHERE element_type_id = 66 AND alarm_config_id = 03010066; --LBL_ALARM_CONFIGS_ALARM_VANE_HIGH_WIND_SPEED
UPDATE conf.alarm_configs SET alarm_level_id = 5, enabled = True, treatment = True WHERE element_type_id = 66 AND alarm_config_id = 03020066; --LBL_ALARM_CONFIGS_ALARM_VANE_HIGH_WIND_SPEED_80
