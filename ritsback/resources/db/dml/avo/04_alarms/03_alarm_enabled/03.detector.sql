UPDATE conf.alarm_configs SET alarm_level_id = 4, enabled = True, treatment = True WHERE element_type_id = 3 AND alarm_config_id = 34; --LBL_ALARM_CONFIGS_ALARM_DETECTOR_FAIL
UPDATE conf.alarm_configs SET alarm_level_id = 2, enabled = True, treatment = False WHERE element_type_id = 3 AND alarm_config_id = 35; --LBL_ALARM_CONFIGS_ALARM_DETECTOR_NO_CONNECTION
UPDATE conf.alarm_configs SET alarm_level_id = 3, enabled = True, treatment = True WHERE element_type_id = 3 AND alarm_config_id = 36; --LBL_ALARM_CONFIGS_ALARM_DETECTOR_CONGESTION
UPDATE conf.alarm_configs SET alarm_level_id = 4, enabled = True, treatment = True WHERE element_type_id = 3 AND alarm_config_id = 40037; --LBL_ALARM_CONFIGS_ALARM_FALLA_PIEZO_ELECTRICO
UPDATE conf.alarm_configs SET alarm_level_id = 4, enabled = True, treatment = True WHERE element_type_id = 3 AND alarm_config_id = 40038; --LBL_ALARM_CONFIGS_ALARM_FALLA_ESPIRA_1
UPDATE conf.alarm_configs SET alarm_level_id = 4, enabled = True, treatment = True WHERE element_type_id = 3 AND alarm_config_id = 40039; --LBL_ALARM_CONFIGS_ALARM_FALLA_ESPIRA_2
