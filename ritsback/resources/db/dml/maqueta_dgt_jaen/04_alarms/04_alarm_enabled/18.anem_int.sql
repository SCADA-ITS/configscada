UPDATE conf.alarm_configs SET alarm_level_id = 3, enabled = true, treatment = True WHERE element_type_id = 18 AND alarm_config_id = 10018; --LBL_ALARM_CONFIGS_ALARM_ANEM_INT_NO_CONNECTION
UPDATE conf.alarm_configs SET alarm_level_id = 3, enabled = false, treatment = True WHERE element_type_id = 18 AND alarm_config_id = 20018; --LBL_ALARM_CONFIGS_ALARM_ANEM_SENSOR
UPDATE conf.alarm_configs SET alarm_level_id = 3, enabled = false, treatment = True WHERE element_type_id = 18 AND alarm_config_id = 30018; --LBL_ALARM_CONFIGS_ALARM_ANEM_FA
