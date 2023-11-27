UPDATE conf.alarm_configs SET alarm_level_id = 1, enabled = True, treatment = False WHERE element_type_id = 88 AND alarm_config_id = 8801; --LBL_ALARM_CONFIGS_LEAK_WATER_SENSOR_DISCONNECTED
UPDATE conf.alarm_configs SET alarm_level_id = 3, enabled = True, treatment = True WHERE element_type_id = 88 AND alarm_config_id = 8802; --LBL_ALARM_CONFIGS_ALARM_LEAK_WATER_DETECTED
