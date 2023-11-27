UPDATE conf.alarm_configs SET alarm_level_id = 1, enabled = True, treatment = False WHERE element_type_id = 3 AND alarm_config_id = 35; --LBL_ALARM_CONFIGS_ALARM_DETECTOR_NO_CONNECTION
UPDATE conf.alarm_configs SET alarm_level_id = 4, enabled = True, treatment = True WHERE element_type_id = 3 AND alarm_config_id = 36; --LBL_ALARM_CONFIGS_ALARM_DETECTOR_CONGESTION
