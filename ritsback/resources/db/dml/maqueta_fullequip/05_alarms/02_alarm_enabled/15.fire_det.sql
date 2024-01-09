UPDATE conf.alarm_configs SET alarm_level_id = 2, enabled = True, treatment = True WHERE element_type_id = 15 AND alarm_config_id = 010015; --LBL_ALARM_CONFIGS_ALARM_FIRE_DET_NO_CONNECTION
UPDATE conf.alarm_configs SET alarm_level_id = 6, enabled = True, treatment = True WHERE element_type_id = 15 AND alarm_config_id = 020015; --LBL_ALARM_CONFIGS_ALARM_FIRE_ZONE
UPDATE conf.alarm_configs SET alarm_level_id = 3, enabled = True, treatment = True WHERE element_type_id = 15 AND alarm_config_id = 030015; --LBL_ALARM_CONFIGS_ALARM_FIBER_FAULT
UPDATE conf.alarm_configs SET alarm_level_id = 1, enabled = True, treatment = False WHERE element_type_id = 15 AND alarm_config_id = 03010015; --LBL_ALARM_CONFIGS_ALARM_MIN_TEMP
