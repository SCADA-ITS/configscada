UPDATE conf.alarm_configs SET alarm_level_id = 5, enabled = True, treatment = True WHERE element_type_id = 66 AND alarm_config_id = 209; --LBL_ALARM_CONFIGS_ALARM_VANE_NO_CONNECTION
UPDATE conf.alarm_configs SET alarm_level_id = 2, enabled = True, treatment = False WHERE element_type_id = 66 AND alarm_config_id = 310; --LBL_ALARM_CONFIGS_VANE_SENSOR_OUT_OF_RANGE
UPDATE conf.alarm_configs SET alarm_level_id = 3, enabled = True, treatment = True WHERE element_type_id = 66 AND alarm_config_id = 6601; --LBL_ALARM_CONFIGS_ALARM_BROKEN_THREAD_SENSOR
